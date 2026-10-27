package pages;

import javax.swing.*;

public class AddTaskPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// add necessary fields such as task name, description, etc. and buttons for
	// adding a task

	private JTextField taskNameField;
	private JTextArea taskDescriptionArea;

	public AddTaskPanel(app.AppFrame frame) {
		setLayout(null);// placeholder for now, will change to a better layout later

		JLabel taskNameLabel = new JLabel("Task Name:");
		taskNameLabel.setBounds(20, 60, 80, 25);
		add(taskNameLabel);

		JLabel taskDescriptionLabel = new JLabel("Task Description:");
		taskDescriptionLabel.setBounds(20, 100, 120, 25);
		add(taskDescriptionLabel);

		taskNameField = new JTextField();
		taskNameField.setBounds(100, 60, 200, 25);
		add(taskNameField);

		taskDescriptionArea = new JTextArea();
		taskDescriptionArea.setBounds(100, 100, 200, 100);
		add(taskDescriptionArea);

		// placeholder for any tags or other fields that might be needed for a task
		// potentially will add later

		JButton addTaskBtn = new JButton("Add Task");
		addTaskBtn.setBounds(100, 220, 95, 30);
		addTaskBtn.addActionListener(e -> {
			String taskName = taskNameField.getText();
			String taskDescription = taskDescriptionArea.getText();

			// add the task to the database

			JOptionPane.showMessageDialog(this, "Task added successfully!");

			// clear the fields
			taskNameField.setText("");
			taskDescriptionArea.setText("");
		});
		add(addTaskBtn);

		JButton backToMainBtn = new JButton("Back to Main"); // Should be in the same place as the add task button in
																// the main panel
		backToMainBtn.setBounds(460, 10, 120, 30);
		backToMainBtn.addActionListener(e -> frame.showPage(app.AppFrame.MAIN));
		add(backToMainBtn);
	}

}
