package testing;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
 
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
 
import javax.swing.SwingUtilities;
 
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
class MainTest {
 
	@BeforeEach
	void requireDisplay() {
		// Swing frames can't be created on a headless machine (e.g. CI without a display)
		assumeFalse(GraphicsEnvironment.isHeadless(), "Skipping: headless environment");
	}
 
	@AfterEach
	void disposeFrames() throws Exception {
		SwingUtilities.invokeAndWait(() -> {
			for (Frame frame : Frame.getFrames()) {
				frame.dispose();
			}
		});
	}
 
	@Test
	void mainRunsWithoutThrowing() {
		assertDoesNotThrow(() -> app.Main.main(new String[0]));
	}
 
	@Test
	void mainAcceptsNullArgs() {
		assertDoesNotThrow(() -> app.Main.main(null));
	}
 
	@Test
	void mainShowsVisibleAppFrame() throws Exception {
		app.Main.main(new String[0]);
 
		// Wait for the Event Dispatch Thread to process the invokeLater task
		SwingUtilities.invokeAndWait(() -> { });
 
		boolean visibleAppFrame = Arrays.stream(Frame.getFrames())
				.anyMatch(f -> f instanceof app.AppFrame && f.isVisible());
 
		assertTrue(visibleAppFrame, "Expected a visible AppFrame after calling main");
	}
 
	@Test
	void mainCanBeInstantiated() {
		assertDoesNotThrow(() -> new app.Main());
	}
}
