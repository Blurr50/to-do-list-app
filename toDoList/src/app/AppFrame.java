package app;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import pages.ToDodoLogin;
import pages.JoinList;
import pages.InviteReady;
import pages.TeamTodoList;
import pages.AddTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import pages.ToDodoLogin;
import pages.JoinList;
import pages.InviteReady;
import pages.TeamTodoList;
import pages.AddTask;

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
	private final TeamTodoList teamTodoList;
	private final InviteReady inviteReady;
	private final AddTask addTask;

	public AppFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1303, 760);
		setLocationRelativeTo(null);

		// Register every page with a name
		teamTodoList = new TeamTodoList(this);
		inviteReady = new InviteReady(this);
		addTask = new AddTask(this);

		root.add(new ToDodoLogin(this), LOGIN);
		root.add(new JoinList(this), CREATE_ACCOUNT);
		root.add(inviteReady, INVITE_READY);
		root.add(teamTodoList, MAIN);
		root.add(addTask, ADD_TASK);

		setContentPane(root);

		// Start at the login page
		showPage(LOGIN);
	}

	/** Switch pages: frame.showPage(AppFrame.MAIN); */
	public void showPage(String name) {
		cards.show(root, name);
		switch (name) {
		case LOGIN:
			setTitle("To-Dodo List - Login");
			break;
		case CREATE_ACCOUNT:
			setTitle("To-Dodo List - Join");
			break;
		case INVITE_READY:
			setTitle("To-Dodo List - Invite ready");
			break;
		case MAIN:
			setTitle("To-Dodo List - Team to-do list");
			break;
		case ADD_TASK:
			setTitle("To-Dodo List - Task");
			break;
		default:
			break;
		}
	}

	// =====================================================================
	// Log in / log out
	// =====================================================================

	/** Call after a successful login or sign-up: remembers the user and opens the team list. */
	public void logIn(model.User user) {
		setCurrentUser(user);
		setWelcomeLabelText("Welcome back, " + user.username() + "!");
		setInviteCodeText(user.inviteCode());
		teamTodoList.refreshTasks("ALL");
		showPage(MAIN);
	}

	public void logOut() {
		setCurrentUser(null);
		teamTodoList.clear();
		showPage(LOGIN);
	}

	public model.User getCurrentUser() {
		return teamTodoList.getCurrentUser();
	}

	public void setCurrentUser(model.User user) {
		teamTodoList.setCurrentUser(user);
	}

	// =====================================================================
	// Opening pages that need data
	// =====================================================================

	/** Opens the task form empty, ready for a new task. */
	public void openAddTask() {
		addTask.showForm(getCurrentUser(), null);
		showPage(ADD_TASK);
	}

	/** Opens the task form filled in with an existing task. */
	public void openEditTask(model.Task task) {
		addTask.showForm(getCurrentUser(), task);
		showPage(ADD_TASK);
	}

	/** Opens the "your team list is ready" page with a fresh, unused invite code. */
	public void openInviteReady() {
		inviteReady.setInviteCode(new database.UserDAO().generateInviteCode());
		showPage(INVITE_READY);
	}

	/** Shows the task list: page is ALL, USER (my tasks) or COMPLETED. */
	public void showTaskList(String page) {
		teamTodoList.refreshTasks(page);
		showPage(MAIN);
	}

	/** Shows a pop-up listing the members of the current user's team. */
	public void showTeamMembers() {
		teamTodoList.showMembersDialog();
	}

	public void setWelcomeLabelText(String text) {
		teamTodoList.setWelcomeLabelText(text);
	}

	public void setInviteCodeText(String text) {
		teamTodoList.setInviteCodeText(text);
	}

	/** Reloads the task list from the database. page: ALL, USER, COMPLETED, or null to keep the current one. */
	public void refreshTasks(String page) {
		teamTodoList.refreshTasks(page);
	}

	/** Returns a message describing what is wrong with a new username / password, or null if they are fine. */
	public static String checkNewAccount(String username, String password) {
		if (username.length() < 3) {
			return "Username must be at least 3 characters long.";
		}
		if (username.contains(" ")) {
			return "Username can't contain spaces.";
		}
		if (password.length() < 6) {
			return "Password must be at least 6 characters long.";
		}
		return null;
	}

	public static void main(String[] args) {
		// Set Look and Feel BEFORE creating any Swing components
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		database.DatabaseManager.initialize();
		EventQueue.invokeLater(() -> {
			new AppFrame().setVisible(true);
		});
	}
}

