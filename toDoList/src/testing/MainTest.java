package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import javax.swing.SwingUtilities;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import database.DatabaseManager;

/**
 * Requires JUnit 5 and Mockito 5+ (or mockito-inline on Mockito 4) for
 * static/constructor mocking. Runs fine in a headless environment because
 * AppFrame is never really constructed.
 */
class MainTest {

	/** Makes SwingUtilities.invokeLater run immediately on the calling thread,
	 *  so thread-local Mockito mocks (construction mocks) apply to the lambda. */
	private static void runInvokeLaterImmediately(MockedStatic<SwingUtilities> swing) {
		swing.when(() -> SwingUtilities.invokeLater(any(Runnable.class)))
				.thenAnswer(inv -> {
					((Runnable) inv.getArgument(0)).run();
					return null;
				});
	}

	@Test
	void main_initializesDatabase_thenShowsFrame() {
		try (MockedStatic<DatabaseManager> db = mockStatic(DatabaseManager.class);
				MockedStatic<SwingUtilities> swing = mockStatic(SwingUtilities.class);
				MockedConstruction<app.AppFrame> frames = mockConstruction(app.AppFrame.class)) {

			runInvokeLaterImmediately(swing);

			app.Main.main(new String[0]);

			db.verify(DatabaseManager::initialize);
			assertEquals(1, frames.constructed().size(), "exactly one AppFrame should be created");
			verify(frames.constructed().get(0)).setVisible(true);

			// DB must be ready before any UI work is scheduled
			InOrder order = inOrder(DatabaseManager.class, SwingUtilities.class);
			order.verify(db, () -> DatabaseManager.initialize());
		}
	}

	@Test
	void main_whenDatabaseInitFails_propagatesAndDoesNotCreateFrame() {
		try (MockedStatic<DatabaseManager> db = mockStatic(DatabaseManager.class);
				MockedStatic<SwingUtilities> swing = mockStatic(SwingUtilities.class);
				MockedConstruction<app.AppFrame> frames = mockConstruction(app.AppFrame.class)) {

			db.when(DatabaseManager::initialize).thenThrow(new RuntimeException("db down"));
			runInvokeLaterImmediately(swing);

			RuntimeException ex = assertThrows(RuntimeException.class, () -> app.Main.main(new String[0]));
			

			assertEquals("db down", ex.getMessage());
			assertEquals(0, frames.constructed().size(), "no UI should be created if DB init fails");
			swing.verifyNoInteractions();
		}
	}

	@Test
	void main_ignoresCommandLineArguments() {
		try (MockedStatic<DatabaseManager> db = mockStatic(DatabaseManager.class);
				MockedStatic<SwingUtilities> swing = mockStatic(SwingUtilities.class);
				MockedConstruction<app.AppFrame> frames = mockConstruction(app.AppFrame.class)) {

			runInvokeLaterImmediately(swing);

			app.Main.main(new String[] { "--unknown", "x" });

			assertEquals(1, frames.constructed().size());
		}
	}
}