package pages;

import javax.swing.*;

public class LoginPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField usernameField;
	private JPasswordField passwordField;

	public LoginPanel(app.AppFrame frame) {
		setLayout(null); // placeholder for now, will change to a better layout later

		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(100, 100, 80, 25);
		add(usernameLabel);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(100, 140, 80, 25);
		add(passwordLabel);

		usernameField = new JTextField();
		usernameField.setBounds(200, 100, 200, 25);
		add(usernameField);

		passwordField = new JPasswordField();
		passwordField.setBounds(200, 140, 200, 25);
		add(passwordField);

		JButton loginBtn = new JButton("Sign in");
		loginBtn.setBounds(200, 180, 95, 30);
		loginBtn.addActionListener(e -> {
			if (authenticate(usernameField.getText(), passwordField.getPassword())) {
				// if correct show main page
				frame.setWelcomeLabelText("Welcome, " + usernameField.getText() + "!"); // Update the welcome label in
																						// MainPanel
				frame.setInviteCodeText("INVITE123"); // REPLACE "INVITE123" WITH A METHOD TO GET THE INVITE CODE FROM
														// DATABASE // Update the invite code label in MainPanel
				frame.showPage(app.AppFrame.MAIN);

				// clear password field for security reasons
				// username field is not cleared to make it easier for the user to log in again
				// if they log out
				passwordField.setText("");

			} else {
				JOptionPane.showMessageDialog(this, "Invalid username or password");
			}
		});
		add(loginBtn);

		JButton createBtn = new JButton("Create account");
		createBtn.setBounds(305, 180, 120, 30);
		createBtn.addActionListener(e -> frame.showPage(app.AppFrame.CREATE_ACCOUNT));
		add(createBtn);

		JButton devBtn = new JButton("DEV BUTTON GO DIRECTLY TO MAIN"); // DELETE THIS BUTTON BEFORE RELEASE
		devBtn.setBounds(160, 220, 280, 30);
		devBtn.addActionListener(e -> {
			frame.setWelcomeLabelText("Welcome, dev!"); // Update the welcome label in MainPanel
			frame.setInviteCodeText("DEV123"); // Update the invite code label in MainPanel
			frame.showPage(app.AppFrame.MAIN);
		});
		add(devBtn);
	}

	private boolean authenticate(String user, char[] pass) {
		// check if the username and password are correct
		// probabaly should use key value pair to store the username and password in a
		// database

		return false;
	}

}