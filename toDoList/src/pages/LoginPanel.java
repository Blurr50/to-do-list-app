package pages;

import javax.swing.*;


public class LoginPanel extends JPanel {
	private static final long serialVersionUID = 1L;

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPanel(app.AppFrame frame) {
        setLayout(null); // or whatever layout you design with

        usernameField = new JTextField();
        usernameField.setBounds(200, 100, 200, 25);
        add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 140, 200, 25);
        add(passwordField);

        JButton loginBtn = new JButton("Log in");
        loginBtn.setBounds(200, 180, 95, 30);
        loginBtn.addActionListener(e -> {
            if (authenticate(usernameField.getText(), passwordField.getPassword())) {
            	//if correct show main page
                frame.showPage(app.AppFrame.MAIN);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }
        });
        add(loginBtn);

        JButton createBtn = new JButton("Create account");
        createBtn.setBounds(305, 180, 120, 30);
        createBtn.addActionListener(e -> frame.showPage(app.AppFrame.CREATE_ACCOUNT));
        add(createBtn);
    }

    private boolean authenticate(String user, char[] pass) {
    	// check if the username and password are correct
    	return false;
    }
}