package pages;

import javax.swing.*;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// add necessary fields
	private JLabel welcomeLabel;
	private JLabel inviteCode;
	private final DefaultListModel<String> taskModel = new DefaultListModel<String>();
	private JList<String> taskList;
	
	public MainPanel(app.AppFrame frame) {
		setLayout(null); // placeholder for now, will change to a better layout later

		// Add components for the main panel (e.g., welcome message, task list, etc.)
		welcomeLabel = new JLabel();
		welcomeLabel.setBounds(200, 50, 200, 25);
		add(welcomeLabel);

		JLabel inviteCodeLabel = new JLabel("Team Invite Code");
		inviteCodeLabel.setBounds(200, 80, 200, 25);
		add(inviteCodeLabel);

		inviteCode = new JLabel();
		inviteCode.setBounds(200, 110, 200, 25);
		add(inviteCode);

		JButton addTaskBtn = new JButton("Add Task");
		addTaskBtn.setBounds(460, 10, 120, 30);
		addTaskBtn.addActionListener(e -> frame.showPage(app.AppFrame.ADD_TASK));
		add(addTaskBtn);

		JButton logOutBtn = new JButton("Log out");
		logOutBtn.setBounds(10, 10, 120, 30);
		logOutBtn.addActionListener(e -> {
			frame.setCurrentUser(null);
			taskModel.clear();
			frame.showPage(app.AppFrame.LOGIN);
		});
		add(logOutBtn);
	
		taskList = new JList<String>(taskModel);
		JScrollPane taskScroll = new JScrollPane(taskList);
		taskScroll.setBounds(20, 150, 540, 190);
		add(taskScroll);
	
		
	}

	
	public void refreshTasks(model.User user) {
	    taskModel.clear();
	    for (model.Task t : new database.TaskDAO().getTasksForUser(user.id())) {
	        taskModel.addElement((t.complete() ? "[x] " : "[ ] ") + t.title());
	    }
	}
	
	
	public void setWelcomeLabelText(String text) {
		welcomeLabel.setText(text);
	}

	public void setInviteCodeText(String text) {
		inviteCode.setText(text);
	}

}
