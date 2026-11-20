package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import database.DatabaseManager;

class MainTest {

    private Runnable originalExitHandler;
    private Runnable mockExitHandler;

    @BeforeEach
    void replaceExitHandler() {
        originalExitHandler = app.Main.exitHandler;
        mockExitHandler = mock(Runnable.class);
        app.Main.exitHandler = mockExitHandler;
    }

    @AfterEach
    void restoreExitHandler() {
    	app.Main.exitHandler = originalExitHandler;
    }

    @Test
    void main_whenDatabaseInitializes_schedulesFrameOnSwingThread() {
        try (MockedStatic<DatabaseManager> db = mockStatic(DatabaseManager.class);
             MockedStatic<SwingUtilities> swing = mockStatic(SwingUtilities.class);
             MockedStatic<JOptionPane> pane = mockStatic(JOptionPane.class)) {

        	app.Main.main(new String[0]);

            db.verify(DatabaseManager::initialize);
            swing.verify(() -> SwingUtilities.invokeLater(any(Runnable.class)));
            pane.verifyNoInteractions();
            verify(mockExitHandler, never()).run();
        }
    }

    @Test
    void main_runnablePassedToInvokeLater_createsAndShowsAppFrame() {
        try (MockedStatic<DatabaseManager> db = mockStatic(DatabaseManager.class);
             MockedStatic<SwingUtilities> swing = mockStatic(SwingUtilities.class);
             MockedConstruction<app.AppFrame> frames = mockConstruction(app.AppFrame.class)) {

        	app.Main.main(new String[0]);

            ArgumentCaptor<Runnable> captor = ArgumentCaptor.forClass(Runnable.class);
            swing.verify(() -> SwingUtilities.invokeLater(captor.capture()));

            // Nothing is built until the EDT runs the runnable
            assertEquals(0, frames.constructed().size());

            captor.getValue().run();

            assertEquals(1, frames.constructed().size());
            verify(frames.constructed().get(0)).setVisible(true);
        }
    }

    @Test
    void main_whenDatabaseFails_showsErrorDialogAndInvokesExitHandler() {
        try (MockedStatic<DatabaseManager> db = mockStatic(DatabaseManager.class);
             MockedStatic<SwingUtilities> swing = mockStatic(SwingUtilities.class);
             MockedStatic<JOptionPane> pane = mockStatic(JOptionPane.class)) {

            db.when(DatabaseManager::initialize).thenThrow(new RuntimeException("boom"));

            app.Main.main(new String[0]);

            pane.verify(() -> JOptionPane.showMessageDialog(
                    null,
                    "Could not start: boom",
                    "Startup error",
                    JOptionPane.ERROR_MESSAGE));
            verify(mockExitHandler).run();
        }
    }

    @Test
    void main_whenDatabaseFails_doesNotScheduleFrame() {
        try (MockedStatic<DatabaseManager> db = mockStatic(DatabaseManager.class);
             MockedStatic<SwingUtilities> swing = mockStatic(SwingUtilities.class);
             MockedStatic<JOptionPane> pane = mockStatic(JOptionPane.class);
             MockedConstruction<app.AppFrame> frames = mockConstruction(app.AppFrame.class)) {

            db.when(DatabaseManager::initialize).thenThrow(new RuntimeException("boom"));

            app.Main.main(new String[0]);

            // The `return` after exitHandler.run() matters because the handler is a mock
            // and doesn't actually terminate the JVM.
            swing.verify(() -> SwingUtilities.invokeLater(any(Runnable.class)), never());
            assertEquals(0, frames.constructed().size());
        }
    }

    @Test
    void exitHandler_defaultIsNotNull() {
    	app.Main.exitHandler = originalExitHandler;
        // Only checks that a default exists; invoking it would call System.exit(1).
        assertEquals(true, app.Main.exitHandler != null);
    }
}