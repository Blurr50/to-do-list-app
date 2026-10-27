package pages;

import javax.swing.*;

public class CreateAccountPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldCheck; // repeat password to make sure the user typed it correctly
	private JTextField inviteCodeField;

	public CreateAccountPanel(app.AppFrame frame) {
		setLayout(null); // placeholder for now, will change to a better layout later

		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(100, 60, 80, 25);
		add(usernameLabel);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(100, 100, 80, 25);
		add(passwordLabel);

		JLabel passwordCheckLabel = new JLabel("Confirm Password:");
		passwordCheckLabel.setBounds(100, 140, 120, 25);
		add(passwordCheckLabel);

		JLabel inviteCodeLabel = new JLabel("Invite Code:");
		inviteCodeLabel.setBounds(100, 180, 80, 25);
		add(inviteCodeLabel);

		usernameField = new JTextField();
		usernameField.setBounds(200, 60, 200, 25);
		add(usernameField);

		passwordField = new JPasswordField();
		passwordField.setBounds(200, 100, 200, 25);
		add(passwordField);

		passwordFieldCheck = new JPasswordField();
		passwordFieldCheck.setBounds(200, 140, 200, 25);
		add(passwordFieldCheck);

		inviteCodeField = new JTextField();
		inviteCodeField.setBounds(200, 180, 200, 25);
		add(inviteCodeField);

		JButton createAccountBtn = new JButton("Create account");
		createAccountBtn.setBounds(200, 220, 95, 30);
		createAccountBtn.addActionListener(e -> {
			String username = usernameField.getText();
			char[] password = passwordField.getPassword();
			char[] passwordCheck = passwordFieldCheck.getPassword();

			if (!String.valueOf(password).equals(String.valueOf(passwordCheck))) {
				JOptionPane.showMessageDialog(this, "Passwords do not match");
				return;
			}
			if (username.isEmpty() || password.length == 0 || inviteCodeField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please fill in all fields");
				return;
			}
			if (!inviteCodeField.getText().matches("\\d+")) {
				JOptionPane.showMessageDialog(this, "Invite code must be a number");
				return;
			}
			if (String.valueOf(password).length() < 6) {
				JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long");
				return;
			}
			if (username.length() < 3) {
				JOptionPane.showMessageDialog(this, "Username must be at least 3 characters long");
				return;
			}
			if (username.toLowerCase() == "username" || String.valueOf(password).toLowerCase().equals("password")) {
				JOptionPane.showMessageDialog(this, "Use a better username or password");
				return;
			}

			// Add logic to save account info to database

			JOptionPane.showMessageDialog(this, "Account created successfully for user: " + username
					+ " with invite code: " + String.valueOf(inviteCodeField.getText()));
			frame.showPage(app.AppFrame.LOGIN); // Go back to login page after account creation
		});
		add(createAccountBtn);

		JButton newInviteCodeBtn = new JButton("Generate a new invite code");
		newInviteCodeBtn.setBounds(305, 220, 120, 30);
		newInviteCodeBtn.addActionListener(e -> {
			int newInviteCode = (int) (Math.random() * 900000 + 100000); // Generate a random 6-digit invite code
			// Add logic to save the new invite code to the database

			inviteCodeField.setText(newInviteCode + ""); // Small qol to automatically input the new invite code for the
															// user
		});
		add(newInviteCodeBtn);

		JButton loginPanelButton = new JButton("Back to Login");
		loginPanelButton.setBounds(10, 10, 120, 30);
		loginPanelButton.addActionListener(e -> frame.showPage(app.AppFrame.LOGIN));
		add(loginPanelButton);
	}

}
