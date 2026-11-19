package app;

import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
	    try {
	        database.DatabaseManager.initialize();
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Could not start: " + e.getMessage(),
	                "Startup error", JOptionPane.ERROR_MESSAGE);
	        System.exit(1);
	    }
	    SwingUtilities.invokeLater(() -> new AppFrame().setVisible(true));
	}

}
