package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.GraphicsEnvironment;
import java.util.stream.Stream;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InOrder;
import org.mockito.MockedConstruction;

import database.UserDAO;
import model.Task;
import model.User;
import pages.AddTask;
import pages.InviteReady;
import pages.JoinList;
import pages.TeamTodoList;
import pages.ToDodoLogin;

/**
 * Needs JUnit 5 (+ junit-jupiter-params) and Mockito 5+ (or mockito-inline on Mockito 4).
 *
 * The page classes and UserDAO are replaced with Mockito construction mocks, so no real
 * pages or database are touched. Tests that build an AppFrame (a real JFrame) are skipped
 * on headless machines; run them with a display or under xvfb.
 */
class AppFrameTest {

	// =====================================================================
	// Static validation - no Swing needed, runs everywhere
	// =====================================================================
	@Nested
	class CheckNewAccount {

		@Test
		void validCredentials_returnNull() {
			assertNull(app.AppFrame.checkNewAccount("sam", "secret"));
		}

		@Test
		void usernameBoundary_threeCharsOk_twoCharsRejected() {
			assertNull(app.AppFrame.checkNewAccount("abc", "secret"));
			assertEquals("Username must be at least 3 characters long.", app.AppFrame.checkNewAccount("ab", "secret"));
		}

		@Test
		void emptyUsername_rejected() {
			assertEquals("Username must be at least 3 characters long.", app.AppFrame.checkNewAccount("", "secret"));
		}

		@Test
		void usernameWithSpace_rejected() {
			assertEquals("Username can't contain spaces.", app.AppFrame.checkNewAccount("sam smith", "secret"));
		}

		@Test
		void passwordBoundary_sixCharsOk_fiveCharsRejected() {
			assertNull(app.AppFrame.checkNewAccount("sam", "123456"));
			assertEquals("Password must be at least 6 characters long.", app.AppFrame.checkNewAccount("sam", "12345"));
		}

		@Test
		void usernameIsCheckedBeforePassword() {
			assertEquals("Username must be at least 3 characters long.", app.AppFrame.checkNewAccount("a", "b"));
		}

		/** Documents current behaviour: tabs/newlines in a username are NOT rejected (only ' '). */
		@Test
		void usernameWithTab_currentlyAccepted() {
			assertNull(app.AppFrame.checkNewAccount("sam\tsmith", "secret"));
		}

		/** Documents current behaviour: an all-whitespace password is accepted. */
		@Test
		void whitespaceOnlyPassword_currentlyAccepted() {
			assertNull(app.AppFrame.checkNewAccount("sam", "      "));
		}

		/** Documents current behaviour: null input crashes instead of returning a message. */
		@Test
		void nullInputs_currentlyThrowNpe() {
			assertThrows(NullPointerException.class, () -> app.AppFrame.checkNewAccount(null, "secret"));
			assertThrows(NullPointerException.class, () -> app.AppFrame.checkNewAccount("sam", null));
		}
	}

	// =====================================================================
	// Frame behaviour - pages are mocked
	// =====================================================================
	@Nested
	class WithFrame {

		private static final String INVITE = "ABC123";

		private MockedConstruction<ToDodoLogin> loginPages;
		private MockedConstruction<JoinList> joinPages;
		private MockedConstruction<InviteReady> invitePages;
		private MockedConstruction<TeamTodoList> teamPages;
		private MockedConstruction<AddTask> addTaskPages;
		private MockedConstruction<UserDAO> daos;
		private app.AppFrame frame;

		@BeforeEach
		void setUp() {
			assumeFalse(GraphicsEnvironment.isHeadless(), "JFrame needs a display (use xvfb on CI)");

			loginPages = mockConstruction(ToDodoLogin.class);
			joinPages = mockConstruction(JoinList.class);
			invitePages = mockConstruction(InviteReady.class);
			teamPages = mockConstruction(TeamTodoList.class);
			addTaskPages = mockConstruction(AddTask.class);
			daos = mockConstruction(UserDAO.class, (dao, ctx) -> when(dao.generateInviteCode()).thenReturn(INVITE));

			frame = new app.AppFrame();
		}

		@AfterEach
		void tearDown() {
			if (frame != null) {
				frame.dispose();
			}
			for (MockedConstruction<?> m : new MockedConstruction<?>[] { loginPages, joinPages, invitePages,
					teamPages, addTaskPages, daos }) {
				if (m != null) {
					m.close();
				}
			}
		}

		private TeamTodoList team() {
			return teamPages.constructed().get(0);
		}

		private AddTask addTask() {
			return addTaskPages.constructed().get(0);
		}

		private InviteReady invite() {
			return invitePages.constructed().get(0);
		}

		// ---------- construction ----------

		@Test
		void constructor_setsUpFrameAndStartsOnLogin() {
			assertEquals(JFrame.EXIT_ON_CLOSE, frame.getDefaultCloseOperation());
			assertEquals(1303, frame.getWidth());
			assertEquals(760, frame.getHeight());
			assertEquals("To-Dodo List - Login", frame.getTitle());
		}

		@Test
		void constructor_createsEachPageExactlyOnce() {
			assertEquals(1, loginPages.constructed().size());
			assertEquals(1, joinPages.constructed().size());
			assertEquals(1, invitePages.constructed().size());
			assertEquals(1, teamPages.constructed().size());
			assertEquals(1, addTaskPages.constructed().size());
		}

		// ---------- showPage ----------

		static Stream<Arguments> pageTitles() {
			return Stream.of(
					Arguments.of(app.AppFrame.LOGIN, "To-Dodo List - Login"),
					Arguments.of(app.AppFrame.CREATE_ACCOUNT, "To-Dodo List - Join"),
					Arguments.of(app.AppFrame.INVITE_READY, "To-Dodo List - Invite ready"),
					Arguments.of(app.AppFrame.MAIN, "To-Dodo List - Team to-do list"),
					Arguments.of(app.AppFrame.ADD_TASK, "To-Dodo List - Task"));
		}

		@ParameterizedTest
		@MethodSource("pageTitles")
		void showPage_setsMatchingTitle(String page, String expectedTitle) {
			frame.showPage(app.AppFrame.MAIN); // make sure we really change away from the start page
			frame.showPage(page);
			assertEquals(expectedTitle, frame.getTitle());
		}

		@Test
		void showPage_unknownName_keepsPreviousTitle() {
			frame.showPage(app.AppFrame.MAIN);
			frame.showPage("doesNotExist");
			assertEquals("To-Dodo List - Team to-do list", frame.getTitle());
		}

		/** Documents current behaviour: null page name crashes in the switch. */
		@Test
		void showPage_null_currentlyThrowsNpe() {
			assertThrows(NullPointerException.class, () -> frame.showPage(null));
		}

		// ---------- log in / log out ----------

		@Test
		void logIn_storesUserFillsLabels_loadsAllTasks_andOpensMain() {
			User user = mock(User.class);
			when(user.username()).thenReturn("sam");
			when(user.inviteCode()).thenReturn("XYZ789");

			frame.logIn(user);

			InOrder order = inOrder(team());
			order.verify(team()).setCurrentUser(user);
			order.verify(team()).setWelcomeLabelText("Welcome back, sam!");
			order.verify(team()).setInviteCodeText("XYZ789");
			order.verify(team()).refreshTasks("ALL");
			assertEquals("To-Dodo List - Team to-do list", frame.getTitle());
		}

		/** Documents current behaviour: logIn(null) crashes instead of being rejected. */
		@Test
		void logIn_null_currentlyThrowsNpe() {
			assertThrows(NullPointerException.class, () -> frame.logIn(null));
		}

		@Test
		void logOut_clearsUserAndList_andReturnsToLogin() {
			frame.showPage(app.AppFrame.MAIN);

			frame.logOut();

			InOrder order = inOrder(team());
			order.verify(team()).setCurrentUser(null);
			order.verify(team()).clear();
			assertEquals("To-Dodo List - Login", frame.getTitle());
		}

		@Test
		void getCurrentUser_delegatesToTeamList() {
			User user = mock(User.class);
			when(team().getCurrentUser()).thenReturn(user);

			assertEquals(user, frame.getCurrentUser());
		}

		@Test
		void setCurrentUser_delegatesToTeamList() {
			User user = mock(User.class);
			frame.setCurrentUser(user);
			verify(team()).setCurrentUser(user);
		}

		// ---------- pages that need data ----------

		@Test
		void openAddTask_showsEmptyForm_forCurrentUser() {
			User user = mock(User.class);
			when(team().getCurrentUser()).thenReturn(user);

			frame.openAddTask();

			verify(addTask()).showForm(user, null);
			assertEquals("To-Dodo List - Task", frame.getTitle());
		}

		@Test
		void openEditTask_showsFormWithTask() {
			User user = mock(User.class);
			Task task = mock(Task.class);
			when(team().getCurrentUser()).thenReturn(user);

			frame.openEditTask(task);

			verify(addTask()).showForm(user, task);
			assertEquals("To-Dodo List - Task", frame.getTitle());
		}

		@Test
		void openInviteReady_usesFreshInviteCode() {
			frame.openInviteReady();

			assertEquals(1, daos.constructed().size());
			verify(invite()).setInviteCode(INVITE);
			assertEquals("To-Dodo List - Invite ready", frame.getTitle());
		}

		@Test
		void openInviteReady_whenDaoFails_propagatesAndStaysOnCurrentPage() {
			daos.close();
			daos = mockConstruction(UserDAO.class, (dao, ctx) -> when(dao.generateInviteCode())
					.thenThrow(new IllegalStateException("db down")));

			assertThrows(IllegalStateException.class, () -> frame.openInviteReady());
			assertEquals("To-Dodo List - Login", frame.getTitle());
		}

		// ---------- delegating helpers ----------

		@Test
		void showTaskList_refreshesThenShowsMain() {
			frame.showTaskList("COMPLETED");

			verify(team()).refreshTasks("COMPLETED");
			assertEquals("To-Dodo List - Team to-do list", frame.getTitle());
		}

		@Test
		void refreshTasks_withNull_isPassedThrough() {
			frame.refreshTasks(null);
			verify(team()).refreshTasks(null);
		}

		@Test
		void showTeamMembers_opensDialog() {
			frame.showTeamMembers();
			verify(team()).showMembersDialog();
		}

		@Test
		void labelSetters_delegateToTeamList() {
			frame.setWelcomeLabelText("hi");
			frame.setInviteCodeText("CODE");

			verify(team()).setWelcomeLabelText("hi");
			verify(team()).setInviteCodeText("CODE");
		}
	}
}