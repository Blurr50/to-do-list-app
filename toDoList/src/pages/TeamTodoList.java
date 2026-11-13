package pages;

import java.awt.Color;
import java.awt.Container;
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

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * To-Dodo List - main "Team to-do list" screen (Swing / WindowBuilder). Images
 * are loaded from the classpath: src/images/dodo.png
 */
public class TeamTodoList extends JPanel {

	private static final long serialVersionUID = 1L;

	// Palette
	private static final Color PAGE_BG = new Color(0xFE, 0xFD, 0xF8);
	private static final Color CREAM = new Color(0xF6, 0xF3, 0xE8);
	private static final Color CREAM_SEL = new Color(0xED, 0xE4, 0xB8);
	private static final Color DARK = new Color(0x37, 0x3B, 0x30);
	private static final Color BODY = new Color(0x5E, 0x61, 0x58);
	private static final Color MUTED = new Color(0x8A, 0x8D, 0x93);
	private static final Color BORDER = new Color(0xE6, 0xE4, 0xDA);
	private static final Color GOLD = new Color(0xE0, 0xC9, 0x7F);
	private static final Color GOLD_LINE = new Color(0xEB, 0xDF, 0xAE);
	private static final Color GOLD_DARK = new Color(0x6B, 0x5A, 0x2A);
	private static final Color OLIVE = new Color(0x4A, 0x50, 0x3C);

	private static final String FONT = "Segoe UI";
	private static final String EMOJI = "Segoe UI Emoji";

	private JPanel contentPane;
	private JPanel rowsPanel;
	private JLabel lblShowing;
	private JLabel lblInviteCode;
	private model.User currentUser;
	
	private JLabel lblTotalNum, lblTotalText;
	private JLabel lblTodoNum, lblTodoText;
	private JLabel lblProgNum, lblProgText;
	private JLabel lblDoneNum, lblDoneText;
	
	private JLabel lblTabAll, lblTabTodo, lblTabProgress, lblTabDone;

	public TeamTodoList(app.AppFrame frame) {
		frame.setTitle("To-Dodo List - Team to-do list");
		contentPane = new JPanel();
		contentPane.setBackground(PAGE_BG);
		contentPane.setLayout(null);
		contentPane.setPreferredSize(new Dimension(1219, 882));

		// =============== TOP BAR ===============
		JPanel topBar = new JPanel();
		topBar.setBackground(PAGE_BG);
		topBar.setBorder(new MatteBorder(0, 0, 1, 0, BORDER));
		topBar.setBounds(0, 0, 1219, 73);
		topBar.setLayout(null);
		contentPane.add(topBar);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(scaled("images/dodo.png", 46, 46));
		lblLogo.setBounds(24, 12, 46, 46);
		topBar.add(lblLogo);

		JLabel lblBrand = new JLabel("To-Dodo List");
		lblBrand.setForeground(DARK);
		lblBrand.setFont(new Font(FONT, Font.BOLD, 21));
		lblBrand.setBounds(72, 18, 200, 32);
		topBar.add(lblBrand);
		
		JButton btnGo = new JButton("Log Out  \u2192");
		btnGo.setForeground(DARK);
		btnGo.setBackground(GOLD);
		btnGo.setFont(new Font(FONT, Font.BOLD, 11));
		btnGo.setFocusPainted(false);
		btnGo.setBorder(new LineBorder(GOLD, 1, true));
		btnGo.setBounds(870, 20, 80, 20);
		btnGo.addActionListener(e -> {
			// MAYBE: clear data here?
			
			
			
			
			
			frame.showPage(app.AppFrame.LOGIN);
		});
		topBar.add(btnGo);


		JPanel invitePanel = new JPanel();
		invitePanel.setBackground(new Color(0xF6, 0xF0, 0xD6));
		invitePanel.setBorder(new LineBorder(GOLD_LINE, 1, true));
		invitePanel.setBounds(1020, 7, 129, 53);
		invitePanel.setLayout(null);
		topBar.add(invitePanel);

		JLabel lblInviteCaption = new JLabel("TEAM INVITE CODE");
		lblInviteCaption.setForeground(GOLD_DARK);
		lblInviteCaption.setFont(new Font(FONT, Font.BOLD, 7));
		lblInviteCaption.setBounds(13, 7, 100, 12);
		invitePanel.add(lblInviteCaption);

		lblInviteCode = new JLabel();
		setInviteCodeText("67DODO"); // get from database
		lblInviteCode.setForeground(DARK);
		lblInviteCode.setFont(new Font(FONT, Font.BOLD, 17));
		lblInviteCode.setBounds(13, 21, 90, 26);
		invitePanel.add(lblInviteCode);

		JLabel lblCopyIcon = new JLabel("\u29C9");
		lblCopyIcon.setForeground(DARK);
		lblCopyIcon.setFont(new Font(FONT, Font.PLAIN, 14));
		lblCopyIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblCopyIcon.setBounds(100, 22, 20, 22);
		lblCopyIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Toolkit.getDefaultToolkit().getSystemClipboard()
						.setContents(new StringSelection(lblInviteCode.getText()), null);
			}
		});
		invitePanel.add(lblCopyIcon);

		CircleLabel lblProfile = new CircleLabel("\uD83D\uDC64");
		lblProfile.setBackground(new Color(0xEE, 0xEB, 0xDD));
		lblProfile.setFont(new Font(EMOJI, Font.PLAIN, 13));
		lblProfile.setBounds(1171, 17, 30, 30);
		topBar.add(lblProfile);

		// =============== SIDEBAR ===============
		JPanel sidebar = new JPanel();
		sidebar.setBackground(CREAM);
		sidebar.setBorder(new MatteBorder(0, 0, 0, 1, BORDER));
		sidebar.setBounds(0, 73, 187, 809);
		sidebar.setLayout(null);
		contentPane.add(sidebar);

		JLabel lblWorkspace = new JLabel("YOUR WORKSPACE");
		lblWorkspace.setForeground(MUTED);
		lblWorkspace.setFont(new Font(FONT, Font.BOLD, 7));
		lblWorkspace.setBounds(25, 23, 140, 12);
		sidebar.add(lblWorkspace);

		JLabel lblTeamName = new JLabel("My team");
		lblTeamName.setForeground(DARK);
		lblTeamName.setFont(new Font(FONT, Font.BOLD, 16));
		lblTeamName.setBounds(25, 36, 140, 24);
		sidebar.add(lblTeamName);

		JLabel lblMembers = new JLabel("1 member \u00B7 Just you for now");
		lblMembers.setForeground(MUTED);
		lblMembers.setFont(new Font(FONT, Font.PLAIN, 8));
		lblMembers.setBounds(25, 62, 150, 14);
		sidebar.add(lblMembers);

		NavItem navTeam = new NavItem("\u2630", "Team to-do list", "5", true);
		navTeam.setBounds(15, 97, 156, 36);
		sidebar.add(navTeam);

		NavItem navMine = new NavItem("\u2611", "My tasks", "4", false);
		navMine.setBounds(15, 140, 156, 36);
		sidebar.add(navMine);

		NavItem navPeople = new NavItem("\u263A", "Team", "1", false);
		navPeople.setBounds(15, 183, 156, 36);
		sidebar.add(navPeople);

		NavItem navDone = new NavItem("\u2714", "Completed", "1", false);
		navDone.setBounds(15, 226, 156, 36);
		sidebar.add(navDone);

		JPanel smallSteps = new JPanel();
		smallSteps.setBackground(PAGE_BG);
		smallSteps.setBorder(new LineBorder(BORDER, 1, true));
		smallSteps.setBounds(15, 578, 156, 126);
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
		sideSep.setBounds(15, 721, 156, 2);
		sidebar.add(sideSep);

		CircleLabel lblMeAvatar = new CircleLabel("\uD83D\uDC64");
		lblMeAvatar.setBackground(new Color(0xEE, 0xE3, 0xB4));
		lblMeAvatar.setFont(new Font(EMOJI, Font.PLAIN, 11));
		lblMeAvatar.setBounds(21, 738, 28, 28);
		sidebar.add(lblMeAvatar);

		JLabel lblMeName = new JLabel("You");
		lblMeName.setForeground(DARK);
		lblMeName.setFont(new Font(FONT, Font.BOLD, 9));
		lblMeName.setBounds(57, 738, 100, 13);
		sidebar.add(lblMeName);

		JLabel lblMeRole = new JLabel("Team creator");
		lblMeRole.setForeground(MUTED);
		lblMeRole.setFont(new Font(FONT, Font.PLAIN, 8));
		lblMeRole.setBounds(57, 752, 100, 13);
		sidebar.add(lblMeRole);

		JLabel lblCopy = new JLabel("\u00A9 2026 To-Dodo List");
		lblCopy.setForeground(MUTED);
		lblCopy.setFont(new Font(FONT, Font.PLAIN, 8));
		lblCopy.setBounds(15, 780, 150, 13);
		sidebar.add(lblCopy);

		// =============== MAIN: HEADING ===============
		JLabel lblEyebrow = new JLabel("A LITTLE PROGRESS, EVERY DAY");
		lblEyebrow.setForeground(GOLD_DARK);
		lblEyebrow.setFont(new Font(FONT, Font.BOLD, 8));
		lblEyebrow.setBounds(217, 101, 300, 14);
		contentPane.add(lblEyebrow);

		JLabel lblTitle = new JLabel("Team to-do list");
		lblTitle.setForeground(DARK);
		lblTitle.setFont(new Font(FONT, Font.BOLD, 30));
		lblTitle.setBounds(217, 116, 400, 44);
		contentPane.add(lblTitle);

		JLabel lblSubtitle = new JLabel("One shared space for your next steps and little wins.");
		lblSubtitle.setForeground(BODY);
		lblSubtitle.setFont(new Font(FONT, Font.PLAIN, 10));
		lblSubtitle.setBounds(217, 162, 400, 16);
		contentPane.add(lblSubtitle);

		JButton btnAdd = new JButton("+   Add task");
		btnAdd.setForeground(DARK);
		btnAdd.setBackground(GOLD);
		btnAdd.setFont(new Font(FONT, Font.BOLD, 10));
		btnAdd.setFocusPainted(false);
		btnAdd.setBorder(new LineBorder(GOLD, 1, true));
		btnAdd.setBounds(1099, 123, 96, 37);
		btnAdd.addActionListener(e -> frame.showPage(app.AppFrame.ADD_TASK));
		contentPane.add(btnAdd);

		// =============== STATS PANEL ===============
		JPanel statsPanel = new JPanel();
		statsPanel.setBackground(CREAM);
		statsPanel.setBorder(new LineBorder(CREAM, 1, true));
		statsPanel.setBounds(217, 201, 978, 78);
		statsPanel.setLayout(null);
		contentPane.add(statsPanel);

		lblTotalNum = new JLabel("5");
		lblTotalNum.setForeground(DARK);
		lblTotalNum.setFont(new Font(FONT, Font.BOLD, 22));
		lblTotalNum.setBounds(19, 17, 20, 30);
		statsPanel.add(lblTotalNum);

		lblTotalText = new JLabel("total tasks");
		lblTotalText.setForeground(BODY);
		lblTotalText.setFont(new Font(FONT, Font.PLAIN, 10));
		lblTotalText.setBounds(40, 24, 80, 16);
		statsPanel.add(lblTotalText);

		JLabel lblTotalNote = new JLabel("A simple start for your team");
		lblTotalNote.setForeground(MUTED);
		lblTotalNote.setFont(new Font(FONT, Font.PLAIN, 8));
		lblTotalNote.setBounds(19, 50, 150, 13);
		statsPanel.add(lblTotalNote);

		JSeparator statSep = new JSeparator(SwingConstants.VERTICAL);
		statSep.setForeground(new Color(0xDD, 0xDA, 0xC8));
		statSep.setBounds(163, 21, 2, 38);
		statsPanel.add(statSep);

		JLabel lblTodoIcon = new JLabel("\u25CB");
		lblTodoIcon.setForeground(DARK);
		lblTodoIcon.setFont(new Font(FONT, Font.PLAIN, 18));
		lblTodoIcon.setBounds(184, 28, 24, 24);
		statsPanel.add(lblTodoIcon);

		lblTodoNum = new JLabel("3");
		lblTodoNum.setForeground(DARK);
		lblTodoNum.setFont(new Font(FONT, Font.BOLD, 20));
		lblTodoNum.setBounds(209, 15, 40, 28);
		statsPanel.add(lblTodoNum);

		lblTodoText = new JLabel("To do");
		lblTodoText.setForeground(BODY);
		lblTodoText.setFont(new Font(FONT, Font.PLAIN, 9));
		lblTodoText.setBounds(209, 46, 80, 14);
		statsPanel.add(lblTodoText);

		JLabel lblProgIcon = new JLabel("\u25F7");
		lblProgIcon.setForeground(GOLD_DARK);
		lblProgIcon.setFont(new Font(FONT, Font.PLAIN, 18));
		lblProgIcon.setBounds(392, 28, 24, 24);
		statsPanel.add(lblProgIcon);

		lblProgNum = new JLabel("1");
		lblProgNum.setForeground(DARK);
		lblProgNum.setFont(new Font(FONT, Font.BOLD, 20));
		lblProgNum.setBounds(417, 15, 40, 28);
		statsPanel.add(lblProgNum);

		lblProgText = new JLabel("In progress");
		lblProgText.setForeground(BODY);
		lblProgText.setFont(new Font(FONT, Font.PLAIN, 9));
		lblProgText.setBounds(417, 46, 80, 14);
		statsPanel.add(lblProgText);

		JLabel lblDoneIcon = new JLabel("\u2714");
		lblDoneIcon.setForeground(OLIVE);
		lblDoneIcon.setFont(new Font(FONT, Font.PLAIN, 15));
		lblDoneIcon.setBounds(598, 28, 24, 24);
		statsPanel.add(lblDoneIcon);

		lblDoneNum = new JLabel("1");
		lblDoneNum.setForeground(DARK);
		lblDoneNum.setFont(new Font(FONT, Font.BOLD, 20));
		lblDoneNum.setBounds(624, 15, 40, 28);
		statsPanel.add(lblDoneNum);

		lblDoneText = new JLabel("Completed");
		lblDoneText.setForeground(BODY);
		lblDoneText.setFont(new Font(FONT, Font.PLAIN, 9));
		lblDoneText.setBounds(624, 46, 80, 14);
		statsPanel.add(lblDoneText);

		JLabel lblWins = new JLabel("Little wins so far");
		lblWins.setForeground(BODY);
		lblWins.setFont(new Font(FONT, Font.PLAIN, 9));
		lblWins.setBounds(806, 26, 110, 14);
		statsPanel.add(lblWins);

		JLabel lblPercent = new JLabel("20%");
		lblPercent.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPercent.setForeground(DARK);
		lblPercent.setFont(new Font(FONT, Font.BOLD, 9));
		lblPercent.setBounds(910, 26, 49, 14);
		statsPanel.add(lblPercent);

		JPanel barFill = new JPanel();
		barFill.setBackground(OLIVE);
		barFill.setBounds(806, 45, 31, 4);
		statsPanel.add(barFill);

		JPanel barTrack = new JPanel();
		barTrack.setBackground(new Color(0xDD, 0xDA, 0xC8));
		barTrack.setBounds(806, 45, 153, 4);
		statsPanel.add(barTrack);

		// =============== FILTER TABS + SEARCH ===============
		lblTabAll = new JLabel("All tasks  5", SwingConstants.CENTER);
		lblTabAll.setOpaque(true);
		lblTabAll.setBackground(new Color(0x3C, 0x40, 0x32));
		lblTabAll.setForeground(Color.WHITE);
		lblTabAll.setFont(new Font(FONT, Font.BOLD, 9));
		lblTabAll.setBorder(new LineBorder(new Color(0x3C, 0x40, 0x32), 1, true));
		lblTabAll.setBounds(217, 304, 68, 30);
		contentPane.add(lblTabAll);

		lblTabTodo = new JLabel("To do  3");
		lblTabTodo.setForeground(BODY);
		lblTabTodo.setFont(new Font(FONT, Font.PLAIN, 9));
		lblTabTodo.setBounds(299, 304, 50, 30);
		contentPane.add(lblTabTodo);

		lblTabProgress = new JLabel("In progress  1");
		lblTabProgress.setForeground(BODY);
		lblTabProgress.setFont(new Font(FONT, Font.PLAIN, 9));
		lblTabProgress.setBounds(357, 304, 70, 30);
		contentPane.add(lblTabProgress);

		lblTabDone = new JLabel("Completed  1");
		lblTabDone.setForeground(BODY);
		lblTabDone.setFont(new Font(FONT, Font.PLAIN, 9));
		lblTabDone.setBounds(438, 304, 70, 30);
		contentPane.add(lblTabDone);

		JLabel lblSearchIcon = new JLabel("\uD83D\uDD0D");
		lblSearchIcon.setFont(new Font(EMOJI, Font.PLAIN, 10));
		lblSearchIcon.setBounds(827, 311, 16, 16);
		contentPane.add(lblSearchIcon);

		PlaceholderTextField txtSearch = new PlaceholderTextField("Find a task");
		txtSearch.setFont(new Font(FONT, Font.PLAIN, 9));
		txtSearch.setBorder(new CompoundBorderHelper().create(BORDER, 26));
		txtSearch.setBounds(818, 305, 142, 28);
		contentPane.add(txtSearch);

		// =============== TASK TABLE ===============
		JPanel tableCard = new JPanel();
		tableCard.setBackground(PAGE_BG);
		tableCard.setBorder(new LineBorder(BORDER, 1, true));
		tableCard.setBounds(217, 347, 743, 482);
		tableCard.setLayout(null);
		contentPane.add(tableCard);

		JPanel tableHead = new JPanel();
		tableHead.setBackground(CREAM);
		tableHead.setBounds(1, 1, 741, 33);
		tableHead.setLayout(null);
		tableCard.add(tableHead);

		JLabel lblColTask = new JLabel("TASK");
		lblColTask.setForeground(MUTED);
		lblColTask.setFont(new Font(FONT, Font.BOLD, 7));
		lblColTask.setBounds(43, 10, 100, 12);
		tableHead.add(lblColTask);

		JLabel lblColStatus = new JLabel("STATUS");
		lblColStatus.setForeground(MUTED);
		lblColStatus.setFont(new Font(FONT, Font.BOLD, 7));
		lblColStatus.setBounds(422, 10, 80, 12);
		tableHead.add(lblColStatus);

		JLabel lblColDue = new JLabel("DUE");
		lblColDue.setForeground(MUTED);
		lblColDue.setFont(new Font(FONT, Font.BOLD, 7));
		lblColDue.setBounds(511, 10, 60, 12);
		tableHead.add(lblColDue);

		JLabel lblColActions = new JLabel("ACTIONS");
		lblColActions.setForeground(MUTED);
		lblColActions.setFont(new Font(FONT, Font.BOLD, 7));
		lblColActions.setBounds(574, 10, 80, 12);
		tableHead.add(lblColActions);

		JLabel lblColDelete = new JLabel("DELETE");
		lblColDelete.setHorizontalAlignment(SwingConstants.RIGHT);
		lblColDelete.setForeground(MUTED);
		lblColDelete.setFont(new Font(FONT, Font.PLAIN, 7));
		lblColDelete.setBounds(660, 10, 67, 12);
		tableHead.add(lblColDelete);

		rowsPanel = new JPanel();
		rowsPanel.setOpaque(false);
		rowsPanel.setLayout(new BoxLayout(rowsPanel, BoxLayout.Y_AXIS));
		rowsPanel.setBounds(1, 34, 741, 415);
		tableCard.add(rowsPanel);

		Runnable onDelete = new Runnable() {
			public void run() {
				updateShowing();
			}
		};
		rowsPanel.add(new TaskRow("Set the goal for our first project",
				"You \u00B7 Write a clear outcome to work toward", "In progress", "Today", true, false, onDelete));
		rowsPanel.add(new TaskRow("Outline the first week", "You \u00B7 Break the goal into small next steps", "To do",
				"Today", true, false, onDelete));
		rowsPanel.add(new TaskRow("Gather useful links and files", "You \u00B7 Keep project resources in one place",
				"To do", "Tomorrow", false, false, onDelete));
		rowsPanel.add(new TaskRow("Prepare a kickoff agenda", "You \u00B7 Note the topics to discuss when others join",
				"To do", "Oct 26", false, false, onDelete));
		rowsPanel.add(new TaskRow("Create the team to-do list", "You \u00B7 A home for tasks, ideas, and little wins",
				"Completed", "Today", false, true, onDelete));

		JSeparator tableSep = new JSeparator();
		tableSep.setForeground(BORDER);
		tableSep.setBounds(1, 448, 741, 2);
		tableCard.add(tableSep);

		lblShowing = new JLabel("Showing all 5 tasks");
		lblShowing.setForeground(MUTED);
		lblShowing.setFont(new Font(FONT, Font.PLAIN, 8));
		lblShowing.setBounds(15, 458, 200, 13);
		tableCard.add(lblShowing);

		JLabel lblAssigned = new JLabel("Assigned to you");
		lblAssigned.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAssigned.setForeground(MUTED);
		lblAssigned.setFont(new Font(FONT, Font.PLAIN, 8));
		lblAssigned.setBounds(577, 458, 150, 13);
		tableCard.add(lblAssigned);

		JLabel lblHint = new JLabel("\u24D8   Checkboxes mark completion. The X buttons delete tasks.");
		lblHint.setForeground(MUTED);
		lblHint.setFont(new Font(FONT, Font.PLAIN, 8));
		lblHint.setBounds(217, 842, 400, 14);
		contentPane.add(lblHint);

		// =============== RIGHT COLUMN: YOUR TEAM ===============
		JPanel teamCard = new JPanel();
		teamCard.setBackground(PAGE_BG);
		teamCard.setBorder(new LineBorder(BORDER, 1, true));
		teamCard.setBounds(982, 304, 213, 204);
		teamCard.setLayout(null);
		contentPane.add(teamCard);

		JLabel lblYourTeam = new JLabel("Your team");
		lblYourTeam.setForeground(DARK);
		lblYourTeam.setFont(new Font(FONT, Font.BOLD, 14));
		lblYourTeam.setBounds(16, 15, 120, 22);
		teamCard.add(lblYourTeam);

		JLabel lblOneMember = new JLabel("1 member");
		lblOneMember.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOneMember.setForeground(MUTED);
		lblOneMember.setFont(new Font(FONT, Font.PLAIN, 8));
		lblOneMember.setBounds(110, 20, 87, 13);
		teamCard.add(lblOneMember);

		CircleLabel lblTeamAvatar = new CircleLabel("\uD83D\uDC64");
		lblTeamAvatar.setBackground(new Color(0xEE, 0xE3, 0xB4));
		lblTeamAvatar.setFont(new Font(EMOJI, Font.PLAIN, 13));
		lblTeamAvatar.setBounds(16, 53, 33, 33);
		teamCard.add(lblTeamAvatar);

		JLabel lblTeamMe = new JLabel("You");
		lblTeamMe.setForeground(DARK);
		lblTeamMe.setFont(new Font(FONT, Font.BOLD, 9));
		lblTeamMe.setBounds(58, 56, 100, 13);
		teamCard.add(lblTeamMe);

		JLabel lblTeamRole = new JLabel("Team creator");
		lblTeamRole.setForeground(MUTED);
		lblTeamRole.setFont(new Font(FONT, Font.PLAIN, 8));
		lblTeamRole.setBounds(58, 70, 100, 13);
		teamCard.add(lblTeamRole);

		JSeparator teamSep = new JSeparator();
		teamSep.setForeground(BORDER);
		teamSep.setBounds(16, 99, 179, 2);
		teamCard.add(teamSep);

		JLabel lblGrow = new JLabel("Good things grow together.");
		lblGrow.setForeground(DARK);
		lblGrow.setFont(new Font(FONT, Font.BOLD, 11));
		lblGrow.setBounds(16, 116, 190, 18);
		teamCard.add(lblGrow);

		JLabel lblGrowText = new JLabel(
				"<html>No teammates have joined yet. Share the invite code at the top right when you\u2019re ready.</html>");
		lblGrowText.setVerticalAlignment(SwingConstants.TOP);
		lblGrowText.setForeground(BODY);
		lblGrowText.setFont(new Font(FONT, Font.PLAIN, 8));
		lblGrowText.setBounds(16, 141, 182, 48);
		teamCard.add(lblGrowText);

		// =============== RIGHT COLUMN: INSPIRATION ===============
		JPanel inspoCard = new JPanel();
		inspoCard.setBackground(CREAM);
		inspoCard.setBorder(new LineBorder(CREAM, 1, true));
		inspoCard.setBounds(982, 524, 213, 185);
		inspoCard.setLayout(null);
		contentPane.add(inspoCard);

		JLabel lblSpark = new JLabel("\u2726");
		lblSpark.setForeground(GOLD_DARK);
		lblSpark.setFont(new Font(FONT, Font.PLAIN, 12));
		lblSpark.setBounds(16, 16, 16, 16);
		inspoCard.add(lblSpark);

		JLabel lblInspoCaption = new JLabel("A LITTLE INSPIRATION");
		lblInspoCaption.setForeground(GOLD_DARK);
		lblInspoCaption.setFont(new Font(FONT, Font.BOLD, 7));
		lblInspoCaption.setBounds(36, 17, 150, 12);
		inspoCard.add(lblInspoCaption);

		JLabel lblInspoTitle = new JLabel("Your list, your pace.");
		lblInspoTitle.setForeground(DARK);
		lblInspoTitle.setFont(new Font(FONT, Font.BOLD, 14));
		lblInspoTitle.setBounds(16, 40, 190, 22);
		inspoCard.add(lblInspoTitle);

		JLabel lblInspoText = new JLabel(
				"<html>These example tasks show how your list can look. View the details, edit a task, or use X to remove one.</html>");
		lblInspoText.setVerticalAlignment(SwingConstants.TOP);
		lblInspoText.setForeground(BODY);
		lblInspoText.setFont(new Font(FONT, Font.PLAIN, 8));
		lblInspoText.setBounds(16, 72, 182, 52);
		inspoCard.add(lblInspoText);

		JSeparator inspoSep = new JSeparator();
		inspoSep.setForeground(new Color(0xDD, 0xDA, 0xC8));
		inspoSep.setBounds(16, 130, 181, 2);
		inspoCard.add(inspoSep);

		JLabel lblArrow = new JLabel("\u2197");
		lblArrow.setForeground(GOLD_DARK);
		lblArrow.setFont(new Font(FONT, Font.BOLD, 11));
		lblArrow.setBounds(16, 143, 16, 16);
		inspoCard.add(lblArrow);

		JLabel lblAddNote = new JLabel("<html>Add task opens a separate page for your next to-do.</html>");
		lblAddNote.setVerticalAlignment(SwingConstants.TOP);
		lblAddNote.setForeground(GOLD_DARK);
		lblAddNote.setFont(new Font(FONT, Font.PLAIN, 8));
		lblAddNote.setBounds(36, 143, 165, 28);
		inspoCard.add(lblAddNote);

		JLabel lblLess = new JLabel("Less overwhelm. More little wins.");
		lblLess.setHorizontalAlignment(SwingConstants.CENTER);
		lblLess.setForeground(MUTED);
		lblLess.setFont(new Font(FONT, Font.ITALIC, 8));
		lblLess.setBounds(982, 724, 213, 14);
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

	private void updateShowing() {
		int n = rowsPanel.getComponentCount();
		lblShowing.setText(n == 1 ? "Showing 1 task" : "Showing all " + n + " tasks");
	}

	/**
	 * Loads an image from the classpath (src/images/...) and scales it smoothly.
	 */
	private static ImageIcon scaled(String path, int w, int h) {
		java.net.URL url = TeamTodoList.class.getResource("/" + path);
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

	/** Small helper so the search box can have a rounded border + left padding. */
	static class CompoundBorderHelper {
		javax.swing.border.Border create(Color line, int leftPad) {
			return new javax.swing.border.CompoundBorder(new LineBorder(line, 1, true),
					new javax.swing.border.EmptyBorder(0, leftPad, 0, 8));
		}
	}

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
				g.setColor(new Color(0xA9, 0xAB, 0xB3));
				g.setFont(getFont());
				g.drawString(hint, getInsets().left,
						(getHeight() + g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent()) / 2);
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

	/** One row of the task table (checkbox, title, status, due, actions). */
	static class TaskRow extends JPanel {
		private static final long serialVersionUID = 1L;
		private final JLabel lblTitle;

		TaskRow(String title, String sub, String status, String due, boolean dueBold, boolean done,
				final Runnable onDelete) {
			setLayout(null);
			setBackground(done ? new Color(0xF7, 0xF6, 0xEE) : new Color(0xFF, 0xFE, 0xFA));
			setBorder(new MatteBorder(0, 0, 1, 0, BORDER));
			Dimension size = new Dimension(741, 83);
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);

			final JCheckBox chk = new JCheckBox();
			chk.setOpaque(false);
			chk.setSelected(done);
			chk.setFocusPainted(false);
			chk.setBounds(13, 30, 22, 22);
			add(chk);

			lblTitle = new JLabel(title);
			lblTitle.setForeground(done ? BODY : DARK);
			lblTitle.setFont(new Font(FONT, Font.BOLD, 11));
			lblTitle.setBounds(43, 22, 360, 18);
			add(lblTitle);

			JLabel lblSub = new JLabel(sub);
			lblSub.setForeground(MUTED);
			lblSub.setFont(new Font(FONT, Font.PLAIN, 8));
			lblSub.setBounds(43, 45, 360, 14);
			add(lblSub);

			JLabel lblStatus = new JLabel(status, SwingConstants.CENTER);
			lblStatus.setOpaque(true);
			lblStatus.setFont(new Font(FONT, Font.BOLD, 7));
			if (status.equals("In progress")) {
				lblStatus.setBackground(new Color(0xF0, 0xE4, 0xAA));
				lblStatus.setForeground(GOLD_DARK);
			} else {
				lblStatus.setBackground(new Color(0xEC, 0xEC, 0xE6));
				lblStatus.setForeground(BODY);
			}
			lblStatus.setBorder(new LineBorder(lblStatus.getBackground(), 1, true));
			lblStatus.setBounds(422, 32, status.equals("In progress") ? 54 : 46, 18);
			add(lblStatus);

			JLabel lblDue = new JLabel(due);
			lblDue.setForeground(dueBold ? GOLD_DARK : MUTED);
			lblDue.setFont(new Font(FONT, dueBold ? Font.BOLD : Font.PLAIN, 8));
			lblDue.setBounds(511, 32, 60, 18);
			add(lblDue);

			JButton btnView = new JButton("View task  \u2197");
			btnView.setForeground(DARK);
			btnView.setBackground(Color.WHITE);
			btnView.setFont(new Font(FONT, Font.BOLD, 8));
			btnView.setFocusPainted(false);
			btnView.setBorder(new LineBorder(BORDER, 1, true));
			btnView.setBounds(574, 28, 71, 26);
			add(btnView);

			JButton btnEdit = new JButton("\u270E Edit");
			btnEdit.setForeground(DARK);
			btnEdit.setFont(new Font(FONT, Font.PLAIN, 8));
			btnEdit.setFocusPainted(false);
			btnEdit.setContentAreaFilled(false);
			btnEdit.setBorder(null);
			btnEdit.setBounds(652, 28, 40, 26);
			add(btnEdit);

			JButton btnDelete = new JButton("\u2715");
			btnDelete.setForeground(new Color(0x9A, 0x4A, 0x3E));
			btnDelete.setBackground(new Color(0xF6, 0xE8, 0xE4));
			btnDelete.setFont(new Font(FONT, Font.BOLD, 9));
			btnDelete.setFocusPainted(false);
			btnDelete.setBorder(new LineBorder(new Color(0xF6, 0xE8, 0xE4), 1, true));
			btnDelete.setBounds(692, 30, 23, 23);
			add(btnDelete);

			chk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean on = chk.isSelected();
					setBackground(on ? new Color(0xF7, 0xF6, 0xEE) : new Color(0xFF, 0xFE, 0xFA));
					lblTitle.setForeground(on ? BODY : DARK);
				}
			});
			btnView.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(TaskRow.this, lblTitle.getText());
				}
			});
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Container parent = getParent();
					parent.remove(TaskRow.this);
					parent.revalidate();
					parent.repaint();
					if (onDelete != null) {
						onDelete.run();
					}
				}
			});
		}
	}

	public void setInviteCodeText(String text) {
		lblInviteCode.setText(text);
	}

	public model.User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(model.User user) {
		this.currentUser = user;
	}

	public void refreshTasks(String page, String filter) {
		//page to get tasks for each flag, (ALL, USER, TEAM, COMPLETED), (ALL, TODO, IN_PROGRESS, DONE)
		
		//highlight the selected tab on the left side of the page
		
		//highlight the selected tab in the middle of the page
		
		//
		
		//logic here to get latest tasks from database and update the rowsPanel
	}
}
