package app;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import pages.AddTaskPanel;
import pages.CreateAccountPanel;
import pages.MainPanel;
import pages.ToDodoLogin;

public class AppFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final String LOGIN = "login";
	public static final String CREATE_ACCOUNT = "createAccount";
	public static final String INVITE_READY = "inviteReady";
	public static final String MAIN = "main";
	public static final String ADD_TASK = "addTask";

	private final CardLayout cards = new CardLayout();
	private final JPanel root = new JPanel(cards);

	// Keep direct references instead of root.getComponent(index)
	private final pages.TeamTodoList teamTodoList;

	public AppFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1303, 760);
		setLocationRelativeTo(null);

		// Register every page with a name
		teamTodoList = new pages.TeamTodoList(this);

		root.add(new ToDodoLogin(this), LOGIN);
		root.add(new pages.JoinList(this), CREATE_ACCOUNT);
		root.add(new pages.InviteReady(this), INVITE_READY);
		root.add(teamTodoList, MAIN);
		root.add(new pages.AddTask(this), ADD_TASK);

		setContentPane(root);

		// Start at the login page
		showPage(LOGIN);
	}

	/** Switch pages: frame.showPage(AppFrame.MAIN); */
	public void showPage(String name) {
		cards.show(root, name);
	}

	public void setWelcomeLabelText(String text) {
		teamTodoList.setWelcomeLabelText(text);
	}

	public void setInviteCodeText(String text) {
		teamTodoList.setInviteCodeText(text);
	}

	public static void main(String[] args) {
		// Set Look and Feel BEFORE creating any Swing components
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		EventQueue.invokeLater(() -> {
			new AppFrame().setVisible(true);
		});
	}
}
