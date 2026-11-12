package pages;

import javax.swing.*;

public class LoginPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField usernameField;
	private JPasswordField passwordField;

	public LoginPanel(app.AppFrame frame) {

		/*
		 * Obselete code, replaced by ToDodoLogin.java this remains as a reference if
		 * necessary later
		 */
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
		// loginBtn.addActionListener(e -> handleLogin(frame));
		add(loginBtn);

		JButton createBtn = new JButton("Create account");
		createBtn.setBounds(305, 180, 120, 30);
		createBtn.addActionListener(e -> frame.showPage(app.AppFrame.CREATE_ACCOUNT));
		add(createBtn);

	}

	private boolean authenticate(String user, char[] pass) {
		// check if the username and password are correct
		// probabaly should use key value pair to store the username and password in a
		// database

		return false;
	}

}
