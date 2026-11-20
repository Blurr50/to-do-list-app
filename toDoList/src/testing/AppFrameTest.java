package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import database.DatabaseManager;
import database.UserDAO;
import model.Task;
import model.User;
import pages.AddTask;
import pages.InviteReady;
import pages.JoinList;
import pages.TeamTodoList;
import pages.ToDodoLogin;

class AppFrameTest {

    // =====================================================================
    // Pure static logic: no frame needed, runs headless
    // =====================================================================

    @Nested
    class CheckNewAccount {

        @ParameterizedTest
        @CsvSource({
            "ab,        password1, Username must be at least 3 characters long.",
            "'',        password1, Username must be at least 3 characters long.",
            "'a b',     password1, Username can't contain spaces.",
            "'abc def', password1, Username can't contain spaces.",
            "abc,       12345,     Password must be at least 6 characters long.",
            "abc,       '',        Password must be at least 6 characters long."
        })
        void returnsMessageForInvalidInput(String username, String password, String expected) {
            assertEquals(expected, app.AppFrame.checkNewAccount(username, password));
        }

        @ParameterizedTest
        @CsvSource({
            "abc,      123456",
            "alice,    password",
            "bob_99,   a-long-password"
        })
        void returnsNullForValidInput(String username, String password) {
            assertNull(app.AppFrame.checkNewAccount(username, password));
        }

        @Test
        void usernameLengthIsCheckedBeforeSpaces() {
            // "a " is too short AND contains a space; the length message wins
            assertEquals("Username must be at least 3 characters long.",
            		app.AppFrame.checkNewAccount("a ", "password1"));
        }

        @Test
        void usernameProblemsAreReportedBeforePasswordProblems() {
            assertEquals("Username can't contain spaces.",
            		app.AppFrame.checkNewAccount("a b c", "123"));
        }
    }

    // =====================================================================
    // main(): AppFrame construction is mocked, so no display is needed
    // =====================================================================

    @Nested
    class Main {

        @Test
        void main_initializesDatabaseAndShowsFrameOnEventQueue() {
            try (MockedStatic<UIManager> ui = mockStatic(UIManager.class);
                 MockedStatic<DatabaseManager> db = mockStatic(DatabaseManager.class);
                 MockedStatic<EventQueue> eq = mockStatic(EventQueue.class);
                 MockedConstruction<app.AppFrame> frames = mockConstruction(app.AppFrame.class)) {

            	app.AppFrame.main(new String[0]);

                db.verify(DatabaseManager::initialize);

                ArgumentCaptor<Runnable> captor = ArgumentCaptor.forClass(Runnable.class);
                eq.verify(() -> EventQueue.invokeLater(captor.capture()));

                // Frame is only created once the event queue runs the task
                assertEquals(0, frames.constructed().size());
                captor.getValue().run();
                assertEquals(1, frames.constructed().size());
                verify(frames.constructed().get(0)).setVisible(true);
            }
        }

        @Test
        void main_continuesWhenLookAndFeelFails() {
            try (MockedStatic<UIManager> ui = mockStatic(UIManager.class);
                 MockedStatic<DatabaseManager> db = mockStatic(DatabaseManager.class);
                 MockedStatic<EventQueue> eq = mockStatic(EventQueue.class);
                 MockedConstruction<app.AppFrame> frames = mockConstruction(app.AppFrame.class)) {

                ui.when(UIManager::getCrossPlatformLookAndFeelClassName).thenReturn("fake.LookAndFeel");
                ui.when(() -> UIManager.setLookAndFeel(anyString()))
                        .thenThrow(new ClassNotFoundException("fake.LookAndFeel"));

                app.AppFrame.main(new String[0]);

                // The exception is swallowed and startup carries on
                db.verify(DatabaseManager::initialize);
                eq.verify(() -> EventQueue.invokeLater(any(Runnable.class)));
            }
        }

        @Test
        void main_setsLookAndFeelBeforeInitializingDatabase() {
            try (MockedStatic<UIManager> ui = mockStatic(UIManager.class);
                 MockedStatic<DatabaseManager> db = mockStatic(DatabaseManager.class);
                 MockedStatic<EventQueue> eq = mockStatic(EventQueue.class)) {

                ui.when(UIManager::getCrossPlatformLookAndFeelClassName).thenReturn("fake.LookAndFeel");

                app.AppFrame.main(new String[0]);

                ui.verify(() -> UIManager.setLookAndFeel("fake.LookAndFeel"));
                db.verify(DatabaseManager::initialize);
            }
        }
    }

    // =====================================================================
    // Instance behaviour. JFrame can't be created headless, so these are
    // skipped (not failed) on a machine without a display.
    // =====================================================================

    @Nested
    class Frame {

        private MockedConstruction<UserDAO> userDaoCtor;
        // If the page constructors or refreshTasks use other DAOs (e.g. TaskDAO),
        // add a mockConstruction for each of them here, the same way as UserDAO.

        private app.AppFrame frame;

        @BeforeEach
        void setUp() {
            assumeFalse(GraphicsEnvironment.isHeadless(), "JFrame needs a display; skipping");

            userDaoCtor = mockConstruction(UserDAO.class,
                    (dao, ctx) -> when(dao.generateInviteCode()).thenReturn("NEW-CODE"));

            frame = new app.AppFrame();
        }

        @AfterEach
        void tearDown() {
            if (frame != null) {
                frame.dispose();
            }
            if (userDaoCtor != null) {
                userDaoCtor.close();
            }
        }

        // ---------- constructor ----------

        @Test
        void constructor_setsFrameProperties() {
            assertEquals(JFrame.EXIT_ON_CLOSE, frame.getDefaultCloseOperation());
            assertEquals(1303, frame.getWidth());
            assertEquals(760, frame.getHeight());
        }

        @Test
        void constructor_startsOnLoginPage() {
            assertEquals("To-Dodo List - Login", frame.getTitle());
        }

        @Test
        void constructor_registersAllFivePagesInContentPane() {
            assertEquals(5, frame.getContentPane().getComponentCount());
        }

        @Test
        void constructor_startsWithNoCurrentUser() {
            assertNull(frame.getCurrentUser());
        }

        // ---------- showPage ----------

        @ParameterizedTest
        @CsvSource({
            "login,         To-Dodo List - Login",
            "createAccount, To-Dodo List - Join",
            "inviteReady,   To-Dodo List - Invite ready",
            "main,          To-Dodo List - Team to-do list",
            "addTask,       To-Dodo List - Task"
        })
        void showPage_setsTitleForEachPage(String page, String expectedTitle) {
            frame.showPage(page);
            assertEquals(expectedTitle, frame.getTitle());
        }

        @Test
        void showPage_constantsMatchExpectedNames() {
            assertEquals("login", app.AppFrame.LOGIN);
            assertEquals("createAccount", app.AppFrame.CREATE_ACCOUNT);
            assertEquals("inviteReady", app.AppFrame.INVITE_READY);
            assertEquals("main", app.AppFrame.MAIN);
            assertEquals("addTask", app.AppFrame.ADD_TASK);
        }

        @Test
        void showPage_unknownNameLeavesTitleUnchanged() {
            frame.showPage(app.AppFrame.MAIN);
            String before = frame.getTitle();

            try {
                frame.showPage("doesNotExist");
            } catch (RuntimeException ignored) {
                // either way, the switch's default branch must not touch the title
            }

            assertEquals(before, frame.getTitle());
        }

        // ---------- log in / log out ----------

        @Test
        void logIn_storesUserAndShowsMainPage() {
            User user = mock(User.class);
            when(user.username()).thenReturn("alice");
            when(user.inviteCode()).thenReturn("ABC123");

            frame.logIn(user);

            assertSame(user, frame.getCurrentUser());
            assertEquals("To-Dodo List - Team to-do list", frame.getTitle());
        }

        @Test
        void logOut_clearsUserAndShowsLogin() {
            User user = mock(User.class);
            when(user.username()).thenReturn("alice");
            when(user.inviteCode()).thenReturn("ABC123");
            frame.logIn(user);

            frame.logOut();

            assertNull(frame.getCurrentUser());
            assertEquals("To-Dodo List - Login", frame.getTitle());
        }

        @Test
        void setCurrentUser_isReturnedByGetCurrentUser() {
            User user = mock(User.class);

            frame.setCurrentUser(user);

            assertSame(user, frame.getCurrentUser());
        }

        // ---------- pages that need data ----------

        @Test
        void openAddTask_showsTaskPage() {
            frame.openAddTask();

            assertEquals("To-Dodo List - Task", frame.getTitle());
        }

        @Test
        void openEditTask_showsTaskPage() {
            frame.openEditTask(mock(Task.class));

            assertEquals("To-Dodo List - Task", frame.getTitle());
        }

        @Test
        void openInviteReady_generatesCodeAndShowsPage() {
            frame.openInviteReady();

            assertEquals(1, userDaoCtor.constructed().size());
            verify(userDaoCtor.constructed().get(0)).generateInviteCode();
            assertEquals("To-Dodo List - Invite ready", frame.getTitle());
        }

        // ---------- task list ----------

        @ParameterizedTest
        @CsvSource({ "ALL", "USER", "COMPLETED" })
        void showTaskList_showsMainPage(String page) {
            frame.showPage(app.AppFrame.LOGIN);

            frame.showTaskList(page);

            assertEquals("To-Dodo List - Team to-do list", frame.getTitle());
        }

        @Test
        void refreshTasks_doesNotChangePage() {
            String titleBefore = frame.getTitle();

            frame.refreshTasks("USER");

            assertEquals(titleBefore, frame.getTitle());
        }
    }
}