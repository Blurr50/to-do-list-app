package pages;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 * To-Dodo List - "Add task" screen (Swing / WindowBuilder). Images are loaded
 * from the classpath: src/images/dodo.png
 */
public class AddTask extends JPanel {

	private static final long serialVersionUID = 1L;

	// Palette
	private static final Color PAGE_BG = new Color(0xFE, 0xFD, 0xF8);
	private static final Color CREAM = new Color(0xF6, 0xF3, 0xE8);
	private static final Color CREAM_SEL = new Color(0xED, 0xE4, 0xB8);
	private static final Color DARK = new Color(0x37, 0x3B, 0x30);
	private static final Color BODY = new Color(0x5E, 0x61, 0x58);
	private static final Color MUTED = new Color(0x8A, 0x8D, 0x93);
	private static final Color LIGHT = new Color(0xA9, 0xAB, 0xB3);
	private static final Color BORDER = new Color(0xE6, 0xE4, 0xDA);
	private static final Color FIELD_LINE = new Color(0xE2, 0xE3, 0xE6);
	private static final Color GOLD = new Color(0xE0, 0xC9, 0x7F);
	private static final Color GOLD_LINE = new Color(0xEB, 0xDF, 0xAE);
	private static final Color GOLD_DARK = new Color(0x6B, 0x5A, 0x2A);
	private static final Color OLIVE = new Color(0x4A, 0x50, 0x3C);

	private static final String FONT = "Segoe UI";
	private static final String EMOJI = "Segoe UI Emoji";

	private JPanel contentPane;
	private PlaceholderTextField txtTitle;
	private PlaceholderTextArea txtDescription;
	private JComboBox<String> cmbDue;
	private JComboBox<String> cmbPriority;
	private JComboBox<String> cmbStatus;

	public AddTask(app.AppFrame frame) {
		frame.setTitle("To-Dodo List - Add task");
		contentPane = new JPanel();
		contentPane.setBackground(PAGE_BG);
		contentPane.setPreferredSize(new Dimension(1219, 882));
		contentPane.setLayout(null);

		// =============== TOP BAR ===============
		JPanel topBar = new JPanel();
		topBar.setBackground(PAGE_BG);
		topBar.setBorder(new MatteBorder(0, 0, 1, 0, BORDER));
		topBar.setBounds(0, 0, 1213, 74);
		topBar.setLayout(null);
		contentPane.add(topBar);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(scaled("images/dodo.png", 46, 46));
		lblLogo.setBounds(18, 12, 46, 46);
		topBar.add(lblLogo);

		JLabel lblBrand = new JLabel("To-Dodo List");
		lblBrand.setForeground(DARK);
		lblBrand.setFont(new Font(FONT, Font.BOLD, 21));
		lblBrand.setBounds(66, 18, 200, 32);
		topBar.add(lblBrand);

		JPanel invitePanel = new JPanel();
		invitePanel.setBackground(new Color(0xF6, 0xF0, 0xD6));
		invitePanel.setBorder(new LineBorder(GOLD_LINE, 1, true));
		invitePanel.setBounds(1014, 8, 128, 52);
		invitePanel.setLayout(null);
		topBar.add(invitePanel);

		JLabel lblInviteCaption = new JLabel("TEAM INVITE CODE");
		lblInviteCaption.setForeground(GOLD_DARK);
		lblInviteCaption.setFont(new Font(FONT, Font.BOLD, 7));
		lblInviteCaption.setBounds(13, 7, 100, 12);
		invitePanel.add(lblInviteCaption);

		JLabel lblInviteCode = new JLabel("67DODO");
		lblInviteCode.setForeground(DARK);
		lblInviteCode.setFont(new Font(FONT, Font.BOLD, 17));
		lblInviteCode.setBounds(13, 20, 90, 26);
		invitePanel.add(lblInviteCode);

		JLabel lblCopyIcon = new JLabel("\u29C9");
		lblCopyIcon.setForeground(DARK);
		lblCopyIcon.setFont(new Font(FONT, Font.PLAIN, 14));
		lblCopyIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblCopyIcon.setBounds(99, 21, 20, 22);
		lblCopyIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection("67DODO"), null);
			}
		});
		invitePanel.add(lblCopyIcon);

		CircleLabel lblProfile = new CircleLabel("\uD83D\uDC64");
		lblProfile.setBackground(new Color(0xEE, 0xEB, 0xDD));
		lblProfile.setFont(new Font(EMOJI, Font.PLAIN, 13));
		lblProfile.setBounds(1164, 19, 30, 30);
		topBar.add(lblProfile);

		// =============== SIDEBAR ===============
		JPanel sidebar = new JPanel();
		sidebar.setBackground(CREAM);
		sidebar.setBorder(new MatteBorder(0, 0, 0, 1, BORDER));
		sidebar.setBounds(0, 74, 181, 758);
		sidebar.setLayout(null);
		contentPane.add(sidebar);

		JLabel lblWorkspace = new JLabel("YOUR WORKSPACE");
		lblWorkspace.setForeground(MUTED);
		lblWorkspace.setFont(new Font(FONT, Font.BOLD, 7));
		lblWorkspace.setBounds(18, 23, 140, 12);
		sidebar.add(lblWorkspace);

		JLabel lblTeamName = new JLabel("My team");
		lblTeamName.setForeground(DARK);
		lblTeamName.setFont(new Font(FONT, Font.BOLD, 16));
		lblTeamName.setBounds(18, 36, 140, 24);
		sidebar.add(lblTeamName);

		JLabel lblMembers = new JLabel("1 member \u00B7 Just you for now");
		lblMembers.setForeground(MUTED);
		lblMembers.setFont(new Font(FONT, Font.PLAIN, 8));
		lblMembers.setBounds(18, 62, 150, 14);
		sidebar.add(lblMembers);

		NavItem navTeam = new NavItem("\u2630", "Team to-do list", "5", true);
		navTeam.setBounds(9, 97, 156, 36);
		sidebar.add(navTeam);

		NavItem navMine = new NavItem("\u2611", "My tasks", "4", false);
		navMine.setBounds(9, 140, 156, 36);
		sidebar.add(navMine);

		NavItem navPeople = new NavItem("\u263A", "Team", "1", false);
		navPeople.setBounds(9, 183, 156, 36);
		sidebar.add(navPeople);

		NavItem navDone = new NavItem("\u2714", "Completed", "1", false);
		navDone.setBounds(9, 226, 156, 36);
		sidebar.add(navDone);

		JPanel smallSteps = new JPanel();
		smallSteps.setBackground(PAGE_BG);
		smallSteps.setBorder(new LineBorder(BORDER, 1, true));
		smallSteps.setBounds(9, 528, 156, 125);
		smallSteps.setLayout(null);
		sidebar.add(smallSteps);

		JLabel lblSprout = new JLabel("\u2698");
		lblSprout.setForeground(OLIVE);
		lblSprout.setFont(new Font(FONT, Font.PLAIN, 16));
		lblSprout.setBounds(13, 11, 24, 22);
		smallSteps.add(lblSprout);

		JLabel lblSmall = new JLabel("<html>Small steps.<br>Good things ahead.</html>");
		lblSmall.setVerticalAlignment(SwingConstants.TOP);
		lblSmall.setForeground(DARK);
		lblSmall.setFont(new Font(FONT, Font.BOLD, 12));
		lblSmall.setBounds(12, 41, 140, 38);
		smallSteps.add(lblSmall);

		JLabel lblRoom = new JLabel("<html>Make room for what matters,<br>one task at a time.</html>");
		lblRoom.setVerticalAlignment(SwingConstants.TOP);
		lblRoom.setForeground(BODY);
		lblRoom.setFont(new Font(FONT, Font.PLAIN, 8));
		lblRoom.setBounds(12, 84, 140, 30);
		smallSteps.add(lblRoom);

		JSeparator sideSep = new JSeparator();
		sideSep.setForeground(BORDER);
		sideSep.setBounds(9, 670, 156, 2);
		sidebar.add(sideSep);

		CircleLabel lblMeAvatar = new CircleLabel("\uD83D\uDC64");
		lblMeAvatar.setBackground(new Color(0xEE, 0xE3, 0xB4));
		lblMeAvatar.setFont(new Font(EMOJI, Font.PLAIN, 11));
		lblMeAvatar.setBounds(15, 688, 28, 28);
		sidebar.add(lblMeAvatar);

		JLabel lblMeName = new JLabel("You");
		lblMeName.setForeground(DARK);
		lblMeName.setFont(new Font(FONT, Font.BOLD, 9));
		lblMeName.setBounds(50, 689, 100, 13);
		sidebar.add(lblMeName);

		JLabel lblMeRole = new JLabel("Team creator");
		lblMeRole.setForeground(MUTED);
		lblMeRole.setFont(new Font(FONT, Font.PLAIN, 8));
		lblMeRole.setBounds(50, 702, 100, 13);
		sidebar.add(lblMeRole);

		JLabel lblCopy = new JLabel("\u00A9 2026 To-Dodo List");
		lblCopy.setForeground(MUTED);
		lblCopy.setFont(new Font(FONT, Font.PLAIN, 8));
		lblCopy.setBounds(9, 730, 150, 13);
		sidebar.add(lblCopy);

		// =============== MAIN: HEADING ===============
		JLabel lblBack = new JLabel("\u2190   Back to team to-do list");
		lblBack.setForeground(BODY);
		lblBack.setFont(new Font(FONT, Font.PLAIN, 9));
		lblBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblBack.setBounds(212, 103, 200, 16);
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO: open your TeamTodoList screen here, e.g.
				// new TeamTodoList().setVisible(true); dispose();
				frame.showPage(app.AppFrame.MAIN);

			}
		});
		contentPane.add(lblBack);

		JLabel lblEyebrow = new JLabel("A LITTLE PROGRESS, EVERY DAY");
		lblEyebrow.setForeground(GOLD_DARK);
		lblEyebrow.setFont(new Font(FONT, Font.BOLD, 8));
		lblEyebrow.setBounds(211, 137, 300, 14);
		contentPane.add(lblEyebrow);

		JLabel lblTitle = new JLabel("Add task");
		lblTitle.setForeground(DARK);
		lblTitle.setFont(new Font(FONT, Font.BOLD, 30));
		lblTitle.setBounds(211, 151, 400, 44);
		contentPane.add(lblTitle);

		JLabel lblSubtitle = new JLabel("Turn your next step into a little win.");
		lblSubtitle.setForeground(BODY);
		lblSubtitle.setFont(new Font(FONT, Font.PLAIN, 10));
		lblSubtitle.setBounds(211, 197, 400, 16);
		contentPane.add(lblSubtitle);

		// =============== FORM CARD ===============
		JPanel formCard = new JPanel();
		formCard.setBackground(Color.WHITE);
		formCard.setBorder(new LineBorder(BORDER, 1, true));
		formCard.setBounds(211, 237, 742, 538);
		formCard.setLayout(null);
		contentPane.add(formCard);

		JLabel lblStep = new JLabel("The next small step");
		lblStep.setForeground(DARK);
		lblStep.setFont(new Font(FONT, Font.BOLD, 15));
		lblStep.setBounds(25, 24, 300, 22);
		formCard.add(lblStep);

		JLabel lblRequired = new JLabel("* Required");
		lblRequired.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRequired.setForeground(MUTED);
		lblRequired.setFont(new Font(FONT, Font.PLAIN, 8));
		lblRequired.setBounds(618, 29, 100, 12);
		formCard.add(lblRequired);

		JLabel lblTaskTitle = new JLabel("<html>Task title <span style='color:#C0583A'>*</span></html>");
		lblTaskTitle.setForeground(DARK);
		lblTaskTitle.setFont(new Font(FONT, Font.BOLD, 9));
		lblTaskTitle.setBounds(25, 66, 200, 14);
		formCard.add(lblTaskTitle);

		txtTitle = new PlaceholderTextField("e.g. Plan the next project milestone");
		txtTitle.setFont(new Font(FONT, Font.PLAIN, 10));
		txtTitle.setBorder(new CompoundBorder(new LineBorder(FIELD_LINE, 1, true), new EmptyBorder(0, 12, 0, 12)));
		txtTitle.setBounds(25, 87, 693, 40);
		formCard.add(txtTitle);

		JLabel lblHint = new JLabel("Keep it clear and action-focused.");
		lblHint.setForeground(MUTED);
		lblHint.setFont(new Font(FONT, Font.PLAIN, 8));
		lblHint.setBounds(25, 134, 300, 13);
		formCard.add(lblHint);

		JLabel lblDescription = new JLabel(
				"<html>Description <span style='color:#8A8D93;font-weight:normal'>(optional)</span></html>");
		lblDescription.setForeground(DARK);
		lblDescription.setFont(new Font(FONT, Font.BOLD, 9));
		lblDescription.setBounds(25, 165, 300, 14);
		formCard.add(lblDescription);

		txtDescription = new PlaceholderTextArea("Add a little context, useful links, or what done looks like\u2026");
		txtDescription.setFont(new Font(FONT, Font.PLAIN, 10));
		txtDescription.setLineWrap(true);
		txtDescription.setWrapStyleWord(true);
		txtDescription.setBorder(new EmptyBorder(12, 12, 12, 12));

		JScrollPane scrollDescription = new JScrollPane(txtDescription);
		scrollDescription.setBorder(new LineBorder(FIELD_LINE, 1, true));
		scrollDescription.setBounds(25, 186, 693, 103);
		formCard.add(scrollDescription);

		JLabel lblDue = new JLabel("Due date");
		lblDue.setForeground(DARK);
		lblDue.setFont(new Font(FONT, Font.BOLD, 9));
		lblDue.setBounds(25, 309, 100, 14);
		formCard.add(lblDue);

		JLabel lblPriority = new JLabel("Priority");
		lblPriority.setForeground(DARK);
		lblPriority.setFont(new Font(FONT, Font.BOLD, 9));
		lblPriority.setBounds(261, 309, 100, 14);
		formCard.add(lblPriority);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setForeground(DARK);
		lblStatus.setFont(new Font(FONT, Font.BOLD, 9));
		lblStatus.setBounds(497, 309, 100, 14);
		formCard.add(lblStatus);

		// icons sit on top of the drop-downs, so they are added first
		JLabel lblCalIcon = new JLabel("\u25A6");
		lblCalIcon.setForeground(BODY);
		lblCalIcon.setFont(new Font(FONT, Font.PLAIN, 14));
		lblCalIcon.setBounds(35, 340, 18, 20);
		formCard.add(lblCalIcon);

		JPanel priorityBar = new JPanel();
		priorityBar.setBackground(GOLD);
		priorityBar.setBounds(272, 344, 4, 12);
		formCard.add(priorityBar);

		JLabel lblStatusIcon = new JLabel("\u25CB");
		lblStatusIcon.setForeground(BODY);
		lblStatusIcon.setFont(new Font(FONT, Font.PLAIN, 13));
		lblStatusIcon.setBounds(509, 340, 18, 20);
		formCard.add(lblStatusIcon);

		cmbDue = new JComboBox<String>(new String[] { "No due date", "Today", "Tomorrow", "Next week" });
		styleCombo(cmbDue);
		cmbDue.setBounds(25, 330, 220, 39);
		formCard.add(cmbDue);

		cmbPriority = new JComboBox<String>(new String[] { "Low", "Medium", "High" });
		cmbPriority.setSelectedItem("Medium");
		styleCombo(cmbPriority);
		cmbPriority.setBounds(261, 330, 220, 39);
		formCard.add(cmbPriority);

		cmbStatus = new JComboBox<String>(new String[] { "To do", "In progress", "Completed" });
		styleCombo(cmbStatus);
		cmbStatus.setBounds(497, 330, 221, 39);
		formCard.add(cmbStatus);

		JSeparator formSep = new JSeparator();
		formSep.setForeground(BORDER);
		formSep.setBounds(25, 389, 693, 2);
		formCard.add(formSep);

		CircleLabel lblAssignAvatar = new CircleLabel("\uD83D\uDC64");
		lblAssignAvatar.setBackground(new Color(0xEE, 0xE3, 0xB4));
		lblAssignAvatar.setFont(new Font(EMOJI, Font.PLAIN, 12));
		lblAssignAvatar.setBounds(25, 410, 30, 30);
		formCard.add(lblAssignAvatar);

		JLabel lblAssigned = new JLabel("Assigned to you");
		lblAssigned.setForeground(DARK);
		lblAssigned.setFont(new Font(FONT, Font.BOLD, 9));
		lblAssigned.setBounds(65, 410, 250, 14);
		formCard.add(lblAssigned);

		JLabel lblOnlyMember = new JLabel("You\u2019re the only member of My team for now.");
		lblOnlyMember.setForeground(MUTED);
		lblOnlyMember.setFont(new Font(FONT, Font.PLAIN, 8));
		lblOnlyMember.setBounds(65, 426, 300, 13);
		formCard.add(lblOnlyMember);

		JLabel lblCreator = new JLabel("Team creator");
		lblCreator.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCreator.setForeground(MUTED);
		lblCreator.setFont(new Font(FONT, Font.PLAIN, 8));
		lblCreator.setBounds(598, 420, 120, 13);
		formCard.add(lblCreator);

		JSeparator footSep = new JSeparator();
		footSep.setForeground(BORDER);
		footSep.setBounds(1, 465, 740, 2);
		formCard.add(footSep);

		JLabel lblAppear = new JLabel("This task will appear in your team to-do list.");
		lblAppear.setForeground(MUTED);
		lblAppear.setFont(new Font(FONT, Font.PLAIN, 8));
		lblAppear.setBounds(25, 495, 300, 13);
		formCard.add(lblAppear);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setForeground(DARK);
		btnCancel.setBackground(Color.WHITE);
		btnCancel.setFont(new Font(FONT, Font.BOLD, 10));
		btnCancel.setFocusPainted(false);
		btnCancel.setBorder(new LineBorder(BORDER, 1, true));
		btnCancel.setBounds(540, 484, 70, 36);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
			}
		});
		formCard.add(btnCancel);

		JButton btnAddTask = new JButton("+   Add task");
		btnAddTask.setForeground(DARK);
		btnAddTask.setBackground(GOLD);
		btnAddTask.setFont(new Font(FONT, Font.BOLD, 10));
		btnAddTask.setFocusPainted(false);
		btnAddTask.setBorder(new LineBorder(GOLD, 1, true));
		btnAddTask.setBounds(620, 484, 98, 36);
		btnAddTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTask(frame);
			}

		});
		formCard.add(btnAddTask);

		// =============== RIGHT COLUMN: INSPIRATION ===============
		JPanel inspoCard = new JPanel();
		inspoCard.setBackground(CREAM);
		inspoCard.setBorder(new LineBorder(CREAM, 1, true));
		inspoCard.setBounds(976, 237, 212, 321);
		inspoCard.setLayout(null);
		contentPane.add(inspoCard);

		JLabel lblSpark = new JLabel("\u2726");
		lblSpark.setForeground(GOLD_DARK);
		lblSpark.setFont(new Font(FONT, Font.PLAIN, 12));
		lblSpark.setBounds(16, 15, 16, 16);
		inspoCard.add(lblSpark);

		JLabel lblInspoCaption = new JLabel("A LITTLE INSPIRATION");
		lblInspoCaption.setForeground(GOLD_DARK);
		lblInspoCaption.setFont(new Font(FONT, Font.BOLD, 7));
		lblInspoCaption.setBounds(36, 17, 150, 12);
		inspoCard.add(lblInspoCaption);

		JLabel lblInspoTitle = new JLabel("<html>Big things start<br>small.</html>");
		lblInspoTitle.setVerticalAlignment(SwingConstants.TOP);
		lblInspoTitle.setForeground(DARK);
		lblInspoTitle.setFont(new Font(FONT, Font.BOLD, 15));
		lblInspoTitle.setBounds(16, 43, 190, 50);
		inspoCard.add(lblInspoTitle);

		JLabel lblInspoText = new JLabel(
				"<html>A good task is one clear next step. Give it a name that tells you exactly where to begin.</html>");
		lblInspoText.setVerticalAlignment(SwingConstants.TOP);
		lblInspoText.setForeground(BODY);
		lblInspoText.setFont(new Font(FONT, Font.PLAIN, 8));
		lblInspoText.setBounds(16, 102, 180, 46);
		inspoCard.add(lblInspoText);

		JSeparator inspoSep = new JSeparator();
		inspoSep.setForeground(new Color(0xDD, 0xDA, 0xC8));
		inspoSep.setBounds(16, 161, 180, 2);
		inspoCard.add(inspoSep);

		JLabel lblTip1 = new JLabel("Start with an action");
		lblTip1.setForeground(DARK);
		lblTip1.setFont(new Font(FONT, Font.BOLD, 8));
		lblTip1.setBounds(16, 175, 180, 13);
		inspoCard.add(lblTip1);

		JLabel lblTip1Text = new JLabel("Try \u201CDraft\u201D, \u201CReview\u201D, or \u201CGather\u201D.");
		lblTip1Text.setForeground(BODY);
		lblTip1Text.setFont(new Font(FONT, Font.PLAIN, 8));
		lblTip1Text.setBounds(16, 191, 180, 13);
		inspoCard.add(lblTip1Text);

		JLabel lblTip2 = new JLabel("Make done feel clear");
		lblTip2.setForeground(DARK);
		lblTip2.setFont(new Font(FONT, Font.BOLD, 8));
		lblTip2.setBounds(16, 217, 180, 13);
		inspoCard.add(lblTip2);

		JLabel lblTip2Text = new JLabel("<html>Use the description to define the outcome.</html>");
		lblTip2Text.setVerticalAlignment(SwingConstants.TOP);
		lblTip2Text.setForeground(BODY);
		lblTip2Text.setFont(new Font(FONT, Font.PLAIN, 8));
		lblTip2Text.setBounds(16, 233, 160, 28);
		inspoCard.add(lblTip2Text);

		JLabel lblTip3 = new JLabel("Set a realistic pace");
		lblTip3.setForeground(DARK);
		lblTip3.setFont(new Font(FONT, Font.BOLD, 8));
		lblTip3.setBounds(16, 274, 180, 13);
		inspoCard.add(lblTip3);

		JLabel lblTip3Text = new JLabel("Add a due date only when you need one.");
		lblTip3Text.setForeground(BODY);
		lblTip3Text.setFont(new Font(FONT, Font.PLAIN, 8));
		lblTip3Text.setBounds(16, 290, 190, 13);
		inspoCard.add(lblTip3Text);

		JLabel lblInfoIcon = new JLabel("\u24D8");
		lblInfoIcon.setForeground(BODY);
		lblInfoIcon.setFont(new Font(FONT, Font.PLAIN, 10));
		lblInfoIcon.setBounds(980, 573, 14, 14);
		contentPane.add(lblInfoIcon);

		JLabel lblInfo = new JLabel("<html>You can edit the details later. A small start is still a start.</html>");
		lblInfo.setVerticalAlignment(SwingConstants.TOP);
		lblInfo.setForeground(BODY);
		lblInfo.setFont(new Font(FONT, Font.PLAIN, 8));
		lblInfo.setBounds(997, 572, 190, 30);
		contentPane.add(lblInfo);

		JLabel lblLess = new JLabel("Less overwhelm. More little wins.");
		lblLess.setHorizontalAlignment(SwingConstants.CENTER);
		lblLess.setForeground(MUTED);
		lblLess.setFont(new Font(FONT, Font.ITALIC, 8));
		lblLess.setBounds(976, 619, 212, 14);
		contentPane.add(lblLess);

		JScrollPane scrollPane = new JScrollPane(contentPane);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		scrollPane.getViewport().setBackground(PAGE_BG);

		setLayout(new java.awt.BorderLayout());
		add(scrollPane, java.awt.BorderLayout.CENTER);
	}

	// =====================================================================
	// Behaviour
	// =====================================================================

	private void addTask(app.AppFrame frame) {
		String title = txtTitle.getText().trim();
		if (title.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please give your task a title.");
			return;
		}

		String taskName = txtTitle.getText();
		String taskDescription = txtDescription.getText();
		if (taskName.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please give your task a title.");
			txtTitle.requestFocusInWindow();

			return;
		}

		model.User me = frame.getCurrentUser();
		new database.TaskDAO().addTask(taskName.trim(), taskDescription, me.id(), null, me.teamId());
		frame.refreshTasks("ALL");

		String summary = "Task added!\n\nTitle: " + title + "\nDescription: " + txtDescription.getText().trim()
				+ "\nDue: " + cmbDue.getSelectedItem() + "\nPriority: " + cmbPriority.getSelectedItem() + "\nStatus: "
				+ cmbStatus.getSelectedItem();
		JOptionPane.showMessageDialog(this, summary);
		clearForm();
	}

	private void clearForm() {
		txtTitle.setText("");
		txtDescription.setText("");
		cmbDue.setSelectedIndex(0);
		cmbPriority.setSelectedItem("Medium");
		cmbStatus.setSelectedIndex(0);
	}

	/**
	 * Gives a drop-down the white, rounded look and room for the icon on its left.
	 */
	private void styleCombo(JComboBox<String> combo) {
		combo.setBackground(Color.WHITE);
		combo.setFont(new Font(FONT, Font.PLAIN, 10));
		combo.setBorder(new LineBorder(FIELD_LINE, 1, true));
		combo.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
						cellHasFocus);
				label.setBorder(new EmptyBorder(0, 28, 0, 0));
				return label;
			}
		});
	}

	/**
	 * Loads an image from the classpath (src/images/...) and scales it smoothly.
	 */
	private static ImageIcon scaled(String path, int w, int h) {
		java.net.URL url = AddTask.class.getResource("/" + path);
		if (url == null) {
			System.err.println("Image not found: " + path);
			return null;
		}
		ImageIcon raw = new ImageIcon(url);
		return new ImageIcon(raw.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}

	// =====================================================================
	// Helper components
	// =====================================================================

	/** A label painted as a filled circle (used for avatars). */
	static class CircleLabel extends JLabel {
		private static final long serialVersionUID = 1L;

		CircleLabel(String text) {
			super(text, SwingConstants.CENTER);
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(getBackground());
			g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
			g2.dispose();
			super.paintComponent(g);
		}
	}

	/** Text field with grey placeholder text. */
	static class PlaceholderTextField extends JTextField {
		private static final long serialVersionUID = 1L;
		private final String hint;

		PlaceholderTextField(String hint) {
			this.hint = hint;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (getText().isEmpty()) {
				g.setColor(LIGHT);
				g.setFont(getFont());
				g.drawString(hint, getInsets().left,
						(getHeight() + g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent()) / 2);
			}
		}
	}

	/** Multi-line text area with grey placeholder text. */
	static class PlaceholderTextArea extends JTextArea {
		private static final long serialVersionUID = 1L;
		private final String hint;

		PlaceholderTextArea(String hint) {
			this.hint = hint;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (getText().isEmpty()) {
				g.setColor(LIGHT);
				g.setFont(getFont());
				g.drawString(hint, getInsets().left, getInsets().top + g.getFontMetrics().getAscent());
			}
		}
	}

	/** One entry in the sidebar menu. */
	static class NavItem extends JPanel {
		private static final long serialVersionUID = 1L;

		NavItem(String icon, String text, String count, boolean selected) {
			setLayout(null);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			if (selected) {
				setBackground(CREAM_SEL);
				setBorder(new LineBorder(CREAM_SEL, 1, true));
			} else {
				setOpaque(false);
			}

			JLabel lblIcon = new JLabel(icon);
			lblIcon.setForeground(DARK);
			lblIcon.setFont(new Font(FONT, Font.PLAIN, 13));
			lblIcon.setBounds(12, 8, 20, 20);
			add(lblIcon);

			JLabel lblText = new JLabel(text);
			lblText.setForeground(DARK);
			lblText.setFont(new Font(FONT, selected ? Font.BOLD : Font.PLAIN, 10));
			lblText.setBounds(35, 8, 95, 20);
			add(lblText);

			JLabel lblCount = new JLabel(count, SwingConstants.RIGHT);
			lblCount.setForeground(BODY);
			lblCount.setFont(new Font(FONT, Font.BOLD, 8));
			lblCount.setBounds(120, 8, 24, 20);
			add(lblCount);
		}
	}
}
