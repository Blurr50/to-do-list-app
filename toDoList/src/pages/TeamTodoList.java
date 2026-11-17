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
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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

	// widgets that show real data
	private JLabel lblMembers, lblMeName, lblMeRole;
	private JLabel lblTotalNum, lblTodoNum, lblProgNum, lblDoneNum, lblPercent, lblWins;
	private JLabel lblOneMember, lblTeamMe, lblTeamRole, lblGrow, lblGrowText, lblAssignedToYou;
	private JLabel lblTitle, lblSubtitle;
	private JPanel barFill, barTrack;
	private NavItem navTeam, navMine, navPeople, navDone;
	private JLabel[] tabs = new JLabel[4];
	private PlaceholderTextField txtSearch;
	private JComboBox<String> cmbSort;

	// what is on screen
	private String currentPage = "ALL"; // ALL (team list), USER (my tasks) or COMPLETED
	private int currentTab = 0; // 0 = all, 1 = to do, 2 = in progress, 3 = completed
	private List<model.Task> teamTasks = new ArrayList<model.Task>(); // every task of the team
	private List<model.User> members = new ArrayList<model.User>();
	private app.AppFrame frame;

	public TeamTodoList(app.AppFrame frame) {
		this.frame = frame;
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

		lblInviteCode = new JLabel("67DODO");
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
		lblProfile.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblProfile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showProfileMenu(lblProfile);
			}
		});

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

		lblMembers = new JLabel("1 member \u00B7 Just you for now");
		lblMembers.setForeground(MUTED);
		lblMembers.setFont(new Font(FONT, Font.PLAIN, 8));
		lblMembers.setBounds(25, 62, 150, 14);
		sidebar.add(lblMembers);

		navTeam = new NavItem("\u2630", "Team to-do list", "0", true);
		navTeam.setBounds(15, 97, 156, 36);
		sidebar.add(navTeam);

		navMine = new NavItem("\u2611", "My tasks", "0", false);
		navMine.setBounds(15, 140, 156, 36);
		sidebar.add(navMine);

		navPeople = new NavItem("\u263A", "Team", "0", false);
		navPeople.setBounds(15, 183, 156, 36);
		sidebar.add(navPeople);

		navDone = new NavItem("\u2714", "Completed", "0", false);
		navDone.setBounds(15, 226, 156, 36);
		sidebar.add(navDone);

		navTeam.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				refreshTasks("ALL");
			}
		});
		navMine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				refreshTasks("USER");
			}
		});
		navPeople.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showMembersDialog();
			}
		});
		navDone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				refreshTasks("COMPLETED");
			}
		});

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
		lblMeAvatar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblMeAvatar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showProfileMenu(lblMeAvatar);
			}
		});

		lblMeName = new JLabel("You");
		lblMeName.setForeground(DARK);
		lblMeName.setFont(new Font(FONT, Font.BOLD, 9));
		lblMeName.setBounds(57, 738, 100, 13);
		sidebar.add(lblMeName);

		lblMeRole = new JLabel("Team creator");
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

		lblTitle = new JLabel("Team to-do list");
		lblTitle.setForeground(DARK);
		lblTitle.setFont(new Font(FONT, Font.BOLD, 30));
		lblTitle.setBounds(217, 116, 400, 44);
		contentPane.add(lblTitle);

		lblSubtitle = new JLabel("One shared space for your next steps and little wins.");
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
		btnAdd.addActionListener(e -> frame.openAddTask());
		contentPane.add(btnAdd);

		// =============== STATS PANEL ===============
		JPanel statsPanel = new JPanel();
		statsPanel.setBackground(CREAM);
		statsPanel.setBorder(new LineBorder(CREAM, 1, true));
		statsPanel.setBounds(217, 201, 978, 78);
		statsPanel.setLayout(null);
		contentPane.add(statsPanel);

		lblTotalNum = new JLabel("0");
		lblTotalNum.setForeground(DARK);
		lblTotalNum.setFont(new Font(FONT, Font.BOLD, 22));
		lblTotalNum.setBounds(19, 17, 20, 30);
		statsPanel.add(lblTotalNum);

		JLabel lblTotalText = new JLabel("total tasks");
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

		lblTodoNum = new JLabel("0");
		lblTodoNum.setForeground(DARK);
		lblTodoNum.setFont(new Font(FONT, Font.BOLD, 20));
		lblTodoNum.setBounds(209, 15, 40, 28);
		statsPanel.add(lblTodoNum);

		JLabel lblTodoText = new JLabel("To do");
		lblTodoText.setForeground(BODY);
		lblTodoText.setFont(new Font(FONT, Font.PLAIN, 9));
		lblTodoText.setBounds(209, 46, 80, 14);
		statsPanel.add(lblTodoText);

		JLabel lblProgIcon = new JLabel("\u25F7");
		lblProgIcon.setForeground(GOLD_DARK);
		lblProgIcon.setFont(new Font(FONT, Font.PLAIN, 18));
		lblProgIcon.setBounds(392, 28, 24, 24);
		statsPanel.add(lblProgIcon);

		lblProgNum = new JLabel("0");
		lblProgNum.setForeground(DARK);
		lblProgNum.setFont(new Font(FONT, Font.BOLD, 20));
		lblProgNum.setBounds(417, 15, 40, 28);
		statsPanel.add(lblProgNum);

		JLabel lblProgText = new JLabel("In progress");
		lblProgText.setForeground(BODY);
		lblProgText.setFont(new Font(FONT, Font.PLAIN, 9));
		lblProgText.setBounds(417, 46, 80, 14);
		statsPanel.add(lblProgText);

		JLabel lblDoneIcon = new JLabel("\u2714");
		lblDoneIcon.setForeground(OLIVE);
		lblDoneIcon.setFont(new Font(FONT, Font.PLAIN, 15));
		lblDoneIcon.setBounds(598, 28, 24, 24);
		statsPanel.add(lblDoneIcon);

		lblDoneNum = new JLabel("0");
		lblDoneNum.setForeground(DARK);
		lblDoneNum.setFont(new Font(FONT, Font.BOLD, 20));
		lblDoneNum.setBounds(624, 15, 40, 28);
		statsPanel.add(lblDoneNum);

		JLabel lblDoneText = new JLabel("Completed");
		lblDoneText.setForeground(BODY);
		lblDoneText.setFont(new Font(FONT, Font.PLAIN, 9));
		lblDoneText.setBounds(624, 46, 80, 14);
		statsPanel.add(lblDoneText);

		lblWins = new JLabel("Little wins so far");
		lblWins.setForeground(BODY);
		lblWins.setFont(new Font(FONT, Font.PLAIN, 9));
		lblWins.setBounds(806, 26, 110, 14);
		statsPanel.add(lblWins);

		lblPercent = new JLabel("0%");
		lblPercent.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPercent.setForeground(DARK);
		lblPercent.setFont(new Font(FONT, Font.BOLD, 9));
		lblPercent.setBounds(910, 26, 49, 14);
		statsPanel.add(lblPercent);

		barFill = new JPanel();
		barFill.setBackground(OLIVE);
		barFill.setBounds(806, 45, 0, 4);
		statsPanel.add(barFill);

		barTrack = new JPanel();
		barTrack.setBackground(new Color(0xDD, 0xDA, 0xC8));
		barTrack.setBounds(806, 45, 153, 4);
		statsPanel.add(barTrack);

		// =============== FILTER TABS + SEARCH ===============
		String[] tabNames = { "All tasks", "To do", "In progress", "Completed" };
		int[] tabX = { 217, 305, 373, 461 };
		int[] tabW = { 78, 60, 80, 80 };
		for (int i = 0; i < tabs.length; i++) {
			final int index = i;
			tabs[i] = new JLabel(tabNames[i] + "  0", SwingConstants.CENTER);
			tabs[i].setFont(new Font(FONT, Font.BOLD, 9));
			tabs[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			tabs[i].setBounds(tabX[i], 304, tabW[i], 30);
			tabs[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					currentTab = index;
					renderTasks();
				}
			});
			contentPane.add(tabs[i]);
		}

		JLabel lblSortCaption = new JLabel("Sort by");
		lblSortCaption.setForeground(MUTED);
		lblSortCaption.setFont(new Font(FONT, Font.PLAIN, 9));
		lblSortCaption.setBounds(560, 304, 40, 30);
		contentPane.add(lblSortCaption);

		cmbSort = new JComboBox<String>(new String[] { "Due date", "Priority", "Title", "Newest" });
		cmbSort.setFont(new Font(FONT, Font.PLAIN, 9));
		cmbSort.setBackground(Color.WHITE);
		cmbSort.setBounds(604, 305, 100, 28);
		cmbSort.addActionListener(e -> renderTasks());
		contentPane.add(cmbSort);

		JLabel lblSearchIcon = new JLabel("\uD83D\uDD0D");
		lblSearchIcon.setFont(new Font(EMOJI, Font.PLAIN, 10));
		lblSearchIcon.setBounds(827, 311, 16, 16);
		contentPane.add(lblSearchIcon);

		txtSearch = new PlaceholderTextField("Find a task");
		txtSearch.setFont(new Font(FONT, Font.PLAIN, 9));
		txtSearch.setBorder(new CompoundBorderHelper().create(BORDER, 26));
		txtSearch.setBounds(818, 305, 142, 28);
		txtSearch.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				renderTasks();
			}

			public void removeUpdate(DocumentEvent e) {
				renderTasks();
			}

			public void changedUpdate(DocumentEvent e) {
				renderTasks();
			}
		});
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

		JScrollPane rowsScroll = new JScrollPane(rowsPanel);
		rowsScroll.setBorder(null);
		rowsScroll.setOpaque(false);
		rowsScroll.getViewport().setOpaque(false);
		rowsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		rowsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		rowsScroll.getVerticalScrollBar().setUnitIncrement(16);
		rowsScroll.setBounds(1, 34, 741, 414);
		tableCard.add(rowsScroll);

		JSeparator tableSep = new JSeparator();
		tableSep.setForeground(BORDER);
		tableSep.setBounds(1, 448, 741, 2);
		tableCard.add(tableSep);

		lblShowing = new JLabel("Showing all 5 tasks");
		lblShowing.setForeground(MUTED);
		lblShowing.setFont(new Font(FONT, Font.PLAIN, 8));
		lblShowing.setBounds(15, 458, 200, 13);
		tableCard.add(lblShowing);

		lblAssignedToYou = new JLabel("Assigned to you");
		lblAssignedToYou.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAssignedToYou.setForeground(MUTED);
		lblAssignedToYou.setFont(new Font(FONT, Font.PLAIN, 8));
		lblAssignedToYou.setBounds(477, 458, 250, 13);
		tableCard.add(lblAssignedToYou);

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

		lblOneMember = new JLabel("1 member");
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

		lblTeamMe = new JLabel("You");
		lblTeamMe.setForeground(DARK);
		lblTeamMe.setFont(new Font(FONT, Font.BOLD, 9));
		lblTeamMe.setBounds(58, 56, 100, 13);
		teamCard.add(lblTeamMe);

		lblTeamRole = new JLabel("Team creator");
		lblTeamRole.setForeground(MUTED);
		lblTeamRole.setFont(new Font(FONT, Font.PLAIN, 8));
		lblTeamRole.setBounds(58, 70, 100, 13);
		teamCard.add(lblTeamRole);

		JSeparator teamSep = new JSeparator();
		teamSep.setForeground(BORDER);
		teamSep.setBounds(16, 99, 179, 2);
		teamCard.add(teamSep);

		lblGrow = new JLabel("Good things grow together.");
		lblGrow.setForeground(DARK);
		lblGrow.setFont(new Font(FONT, Font.BOLD, 11));
		lblGrow.setBounds(16, 116, 190, 18);
		teamCard.add(lblGrow);

		lblGrowText = new JLabel(
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
				"<html>This is your team\u2019s shared list. View the details, edit a task you created, or use X to remove one.</html>");
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

	// =====================================================================
	// Showing the real tasks
	// =====================================================================

	/** Redraws the stats, sidebar, tabs and task rows from the loaded tasks and the current filters. */
	private void renderTasks() {
		int meId = currentUser == null ? -1 : currentUser.id();

		// -- the tasks that belong on this page
		List<model.Task> pageTasks = new ArrayList<model.Task>();
		int mine = 0;
		int assignedToMe = 0;
		int done = 0;
		int inProgress = 0;
		int todo = 0;
		for (model.Task t : teamTasks) {
			boolean isMine = t.createdBy() == meId || (t.assignedTo() != null && t.assignedTo() == meId);
			if (isMine) {
				mine++;
			}
			if (t.assignedTo() != null && t.assignedTo() == meId && !t.complete()) {
				assignedToMe++;
			}
			if (t.complete()) {
				done++;
			} else if (model.Task.IN_PROGRESS.equals(t.status())) {
				inProgress++;
			} else {
				todo++;
			}
			if ("USER".equals(currentPage) && !isMine) {
				continue;
			}
			if ("COMPLETED".equals(currentPage) && !t.complete()) {
				continue;
			}
			pageTasks.add(t);
		}

		// -- heading and sidebar
		if ("USER".equals(currentPage)) {
			lblTitle.setText("My tasks");
			lblSubtitle.setText("Tasks you created or that were given to you.");
		} else if ("COMPLETED".equals(currentPage)) {
			lblTitle.setText("Completed");
			lblSubtitle.setText("Every little win your team has finished.");
		} else {
			lblTitle.setText("Team to-do list");
			lblSubtitle.setText("One shared space for your next steps and little wins.");
		}
		navTeam.setSelected("ALL".equals(currentPage));
		navMine.setSelected("USER".equals(currentPage));
		navDone.setSelected("COMPLETED".equals(currentPage));
		navTeam.setCount(teamTasks.size());
		navMine.setCount(mine);
		navPeople.setCount(members.size());
		navDone.setCount(done);

		// -- stats panel
		int total = teamTasks.size();
		int percent = total == 0 ? 0 : Math.round(100f * done / total);
		lblTotalNum.setText(String.valueOf(total));
		lblTodoNum.setText(String.valueOf(todo));
		lblProgNum.setText(String.valueOf(inProgress));
		lblDoneNum.setText(String.valueOf(done));
		lblPercent.setText(percent + "%");
		barFill.setBounds(806, 45, Math.round(153f * percent / 100), 4);

		// -- tabs (their counts are for this page)
		int[] counts = new int[4];
		for (model.Task t : pageTasks) {
			counts[0]++;
			if (t.complete()) {
				counts[3]++;
			} else if (model.Task.IN_PROGRESS.equals(t.status())) {
				counts[2]++;
			} else {
				counts[1]++;
			}
		}
		String[] tabNames = { "All tasks", "To do", "In progress", "Completed" };
		for (int i = 0; i < tabs.length; i++) {
			tabs[i].setText(tabNames[i] + "  " + counts[i]);
			if (i == currentTab) {
				tabs[i].setOpaque(true);
				tabs[i].setBackground(new Color(0x3C, 0x40, 0x32));
				tabs[i].setForeground(Color.WHITE);
				tabs[i].setFont(new Font(FONT, Font.BOLD, 9));
				tabs[i].setBorder(new LineBorder(new Color(0x3C, 0x40, 0x32), 1, true));
			} else {
				tabs[i].setOpaque(false);
				tabs[i].setForeground(BODY);
				tabs[i].setFont(new Font(FONT, Font.PLAIN, 9));
				tabs[i].setBorder(null);
			}
			tabs[i].repaint();
		}

		// -- filter by tab and search text
		String search = txtSearch.getText().trim().toLowerCase();
		List<model.Task> shown = new ArrayList<model.Task>();
		for (model.Task t : pageTasks) {
			if (currentTab == 1 && (t.complete() || model.Task.IN_PROGRESS.equals(t.status()))) {
				continue;
			}
			if (currentTab == 2 && !model.Task.IN_PROGRESS.equals(t.status())) {
				continue;
			}
			if (currentTab == 3 && !t.complete()) {
				continue;
			}
			if (!search.isEmpty() && !t.title().toLowerCase().contains(search)
					&& !(t.description() != null && t.description().toLowerCase().contains(search))) {
				continue;
			}
			shown.add(t);
		}

		// -- sort (finished tasks always sink to the bottom)
		Comparator<model.Task> byDue = Comparator.comparing(TeamTodoList::dueKey);
		Comparator<model.Task> byPriority = Comparator.comparingInt(model.Task::priority);
		Comparator<model.Task> byTitle = Comparator.comparing(t -> t.title().toLowerCase());
		Comparator<model.Task> newest = Comparator.comparingInt(model.Task::id).reversed();
		Comparator<model.Task> order;
		switch (cmbSort.getSelectedIndex()) {
		case 1:
			order = byPriority.thenComparing(byDue);
			break;
		case 2:
			order = byTitle;
			break;
		case 3:
			order = newest;
			break;
		default:
			order = byDue.thenComparing(byPriority);
			break;
		}
		shown.sort(Comparator.comparing(model.Task::complete).thenComparing(order));

		// -- rows
		TaskActions actions = new TaskActions() {
			public void setDone(model.Task task, boolean done) {
				changeStatus(task, done);
			}

			public void view(model.Task task) {
				viewTask(task);
			}

			public void edit(model.Task task) {
				editTask(task);
			}

			public void delete(model.Task task) {
				deleteTask(task);
			}
		};
		String me = currentUser == null ? "" : currentUser.username();
		rowsPanel.removeAll();
		for (model.Task t : shown) {
			rowsPanel.add(new TaskRow(t, me, actions));
		}
		if (shown.isEmpty()) {
			JLabel empty = new JLabel(total == 0 ? "No tasks yet \u2014 click \u201C+ Add task\u201D to create the first one."
					: "No tasks match here.", SwingConstants.CENTER);
			empty.setForeground(MUTED);
			empty.setFont(new Font(FONT, Font.PLAIN, 10));
			empty.setAlignmentX(0.0f);
			empty.setPreferredSize(new Dimension(741, 120));
			empty.setMaximumSize(new Dimension(741, 120));
			rowsPanel.add(empty);
		}
		rowsPanel.add(Box.createVerticalGlue());
		rowsPanel.revalidate();
		rowsPanel.repaint();

		lblShowing.setText(shown.size() == pageTasks.size()
				? (shown.size() == 1 ? "Showing 1 task" : "Showing all " + shown.size() + " tasks")
				: "Showing " + shown.size() + " of " + pageTasks.size() + " tasks");
		lblAssignedToYou.setText(assignedToMe == 1 ? "1 open task assigned to you"
				: assignedToMe + " open tasks assigned to you");

		renderTeam();
	}

	/** Fills the sidebar name/role and the "Your team" card from the team's members. */
	private void renderTeam() {
		if (currentUser == null) {
			return;
		}
		boolean creator = !members.isEmpty() && members.get(0).id() == currentUser.id();
		String role = creator ? "Team creator" : "Member";
		lblMeName.setText(currentUser.username());
		lblMeRole.setText(role);
		lblTeamMe.setText(currentUser.username() + " (you)");
		lblTeamRole.setText(role);
		lblMembers.setText(members.size() == 1 ? "1 member \u00B7 Just you for now" : members.size() + " members");
		lblOneMember.setText(members.size() == 1 ? "1 member" : members.size() + " members");

		if (members.size() <= 1) {
			lblGrow.setText("Good things grow together.");
			lblGrowText.setText(
					"<html>No teammates have joined yet. Share the invite code at the top right when you\u2019re ready.</html>");
		} else {
			lblGrow.setText("Your teammates");
			StringBuilder sb = new StringBuilder("<html>");
			int shownNames = 0;
			for (model.User m : members) {
				if (m.id() == currentUser.id()) {
					continue;
				}
				if (shownNames == 4) {
					sb.append("and more\u2026");
					break;
				}
				sb.append(m.username());
				sb.append(members.get(0).id() == m.id() ? " \u00B7 Team creator" : "").append("<br>");
				shownNames++;
			}
			lblGrowText.setText(sb.append("</html>").toString());
		}
	}

	// tasks without a due date sort after the ones that have one
	private static String dueKey(model.Task t) {
		return t.dueDate() == null ? "9999-99-99" : t.dueDate();
	}

	/** "Today", "Tomorrow", or a short date such as "Oct 26"; "No date" when there is none. */
	private static String formatDue(String isoDate) {
		if (isoDate == null) {
			return "No date";
		}
		try {
			LocalDate date = LocalDate.parse(isoDate);
			LocalDate today = LocalDate.now();
			if (date.equals(today)) {
				return "Today";
			}
			if (date.equals(today.plusDays(1))) {
				return "Tomorrow";
			}
			return date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + date.getDayOfMonth();
		} catch (java.time.format.DateTimeParseException e) {
			return isoDate;
		}
	}

	private static boolean isOverdue(model.Task t) {
		if (t.dueDate() == null || t.complete()) {
			return false;
		}
		try {
			return LocalDate.parse(t.dueDate()).isBefore(LocalDate.now());
		} catch (java.time.format.DateTimeParseException e) {
			return false;
		}
	}

	// =====================================================================
	// What the buttons on each row do
	// =====================================================================

	private void changeStatus(model.Task task, boolean done) {
		boolean ok = new database.TaskDAO().setStatus(task.id(), currentUser.id(),
				done ? model.Task.DONE : model.Task.TODO);
		if (!ok) {
			JOptionPane.showMessageDialog(this,
					"Only the person who created this task or the person it\u2019s assigned to can tick it off.");
		}
		refreshTasks(null);
	}

	private void viewTask(model.Task task) {
		String description = task.description() == null || task.description().isBlank() ? "(no description)"
				: task.description();
		String text = task.title() + "\n\n" + description + "\n\nStatus: " + model.Task.statusLabel(task.status())
				+ "\nPriority: " + model.Task.priorityLabel(task.priority()) + "\nDue: "
				+ (task.dueDate() == null ? "no due date" : task.dueDate()) + "\nCreated by: " + task.creatorName()
				+ "\nAssigned to: " + (task.assigneeName() == null ? "nobody yet" : task.assigneeName());
		JOptionPane.showMessageDialog(this, text, "Task details", JOptionPane.PLAIN_MESSAGE);
	}

	private void editTask(model.Task task) {
		if (task.createdBy() != currentUser.id()) {
			JOptionPane.showMessageDialog(this, "Only " + task.creatorName() + " (who created this task) can edit it.");
			return;
		}
		frame.openEditTask(task);
	}

	private void deleteTask(model.Task task) {
		if (task.createdBy() != currentUser.id()) {
			JOptionPane.showMessageDialog(this,
					"Only " + task.creatorName() + " (who created this task) can delete it.");
			return;
		}
		int answer = JOptionPane.showConfirmDialog(this, "Delete \"" + task.title() + "\"?", "Delete task",
				JOptionPane.YES_NO_OPTION);
		if (answer != JOptionPane.YES_OPTION) {
			return;
		}
		new database.TaskDAO().deleteTask(task.id(), currentUser.id());
		refreshTasks(null);
	}

	/** Pop-up listing the team: members, who created the team, and the invite code. */
	public void showMembersDialog() {
		if (currentUser == null) {
			return;
		}
		StringBuilder sb = new StringBuilder("Invite code: " + currentUser.inviteCode() + "\n\n");
		for (int i = 0; i < members.size(); i++) {
			sb.append(members.get(i).username());
			sb.append(i == 0 ? "  \u2014  Team creator" : "").append(members.get(i).id() == currentUser.id() ? "  (you)" : "")
					.append("\n");
		}
		JOptionPane.showMessageDialog(this, sb.toString().trim(), "Your team", JOptionPane.PLAIN_MESSAGE);
	}

	private void showProfileMenu(java.awt.Component anchor) {
		JPopupMenu menu = new JPopupMenu();
		JMenuItem who = new JMenuItem(currentUser == null ? "Not signed in" : "Signed in as " + currentUser.username());
		who.setEnabled(false);
		menu.add(who);
		JMenuItem logOut = new JMenuItem("Log out");
		logOut.addActionListener(e -> frame.logOut());
		menu.add(logOut);
		menu.show(anchor, 0, anchor.getHeight());
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
		private JLabel lblText;
		private JLabel lblCount;

		void setCount(int n) {
			lblCount.setText(String.valueOf(n));
		}

		void setSelected(boolean selected) {
			if (selected) {
				setOpaque(true);
				setBackground(CREAM_SEL);
				setBorder(new LineBorder(CREAM_SEL, 1, true));
			} else {
				setOpaque(false);
				setBorder(null);
			}
			lblText.setFont(new Font(FONT, selected ? Font.BOLD : Font.PLAIN, 10));
			repaint();
		}

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

			lblText = new JLabel(text);
			lblText.setForeground(DARK);
			lblText.setFont(new Font(FONT, selected ? Font.BOLD : Font.PLAIN, 10));
			lblText.setBounds(35, 8, 95, 20);
			add(lblText);

			lblCount = new JLabel(count, SwingConstants.RIGHT);
			lblCount.setForeground(BODY);
			lblCount.setFont(new Font(FONT, Font.BOLD, 8));
			lblCount.setBounds(120, 8, 24, 20);
			add(lblCount);
		}
	}

	/** What the buttons on a task row can do (implemented by the page). */
	interface TaskActions {
		void setDone(model.Task task, boolean done);

		void view(model.Task task);

		void edit(model.Task task);

		void delete(model.Task task);
	}

	/** One row of the task table (checkbox, title, status, due, actions). */
	static class TaskRow extends JPanel {
		private static final long serialVersionUID = 1L;

		TaskRow(final model.Task task, String me, final TaskActions actions) {
			boolean done = task.complete();
			setLayout(null);
			setAlignmentX(0.0f);
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

			JLabel lblTitle = new JLabel(task.title());
			lblTitle.setForeground(done ? BODY : DARK);
			lblTitle.setFont(new Font(FONT, Font.BOLD, 11));
			lblTitle.setBounds(43, 14, 360, 18);
			add(lblTitle);

			String creator = task.creatorName().equals(me) ? "You" : task.creatorName();
			String description = task.description() == null || task.description().isBlank() ? "No description"
					: task.description().replace('\n', ' ');
			JLabel lblSub = new JLabel(creator + " \u00B7 " + description);
			lblSub.setForeground(MUTED);
			lblSub.setFont(new Font(FONT, Font.PLAIN, 8));
			lblSub.setBounds(43, 36, 360, 14);
			add(lblSub);

			String assignee = task.assigneeName() == null ? "Unassigned"
					: "Assigned to " + (task.assigneeName().equals(me) ? "you" : task.assigneeName());
			JLabel lblMeta = new JLabel(model.Task.priorityLabel(task.priority()) + " priority \u00B7 " + assignee);
			lblMeta.setForeground(task.priority() == model.Task.HIGH && !done ? GOLD_DARK : MUTED);
			lblMeta.setFont(new Font(FONT, task.priority() == model.Task.HIGH && !done ? Font.BOLD : Font.PLAIN, 8));
			lblMeta.setBounds(43, 54, 360, 14);
			add(lblMeta);

			boolean inProgress = model.Task.IN_PROGRESS.equals(task.status());
			JLabel lblStatus = new JLabel(model.Task.statusLabel(task.status()), SwingConstants.CENTER);
			lblStatus.setOpaque(true);
			lblStatus.setFont(new Font(FONT, Font.BOLD, 7));
			if (inProgress) {
				lblStatus.setBackground(new Color(0xF0, 0xE4, 0xAA));
				lblStatus.setForeground(GOLD_DARK);
			} else {
				lblStatus.setBackground(new Color(0xEC, 0xEC, 0xE6));
				lblStatus.setForeground(BODY);
			}
			lblStatus.setBorder(new LineBorder(lblStatus.getBackground(), 1, true));
			lblStatus.setBounds(422, 32, inProgress ? 54 : 46, 18);
			add(lblStatus);

			String dueText = formatDue(task.dueDate());
			boolean urgent = !done && (dueText.equals("Today") || dueText.equals("Tomorrow"));
			boolean overdue = isOverdue(task);
			JLabel lblDue = new JLabel(overdue ? "<html>Overdue<br>" + dueText + "</html>" : dueText);
			lblDue.setForeground(overdue ? new Color(0x9A, 0x4A, 0x3E) : urgent ? GOLD_DARK : MUTED);
			lblDue.setFont(new Font(FONT, urgent || overdue ? Font.BOLD : Font.PLAIN, 8));
			lblDue.setBounds(511, overdue ? 24 : 32, 60, overdue ? 34 : 18);
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
					actions.setDone(task, chk.isSelected());
				}
			});
			btnView.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actions.view(task);
				}
			});
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actions.edit(task);
				}
			});
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actions.delete(task);
				}
			});
		}
	}

	public void setWelcomeLabelText(String text) {
		// the new design has no welcome line; the signed-in name is shown in the sidebar instead
	}

	public void setInviteCodeText(String text) {
		lblInviteCode.setText(text);
	}

	public model.User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(model.User user) {
		this.currentUser = user;
		if (user != null) {
			lblMeName.setText(user.username());
			lblInviteCode.setText(user.inviteCode());
		}
	}

	/** Forgets everything on screen (used when logging out). */
	public void clear() {
		currentUser = null;
		currentPage = "ALL";
		currentTab = 0;
		teamTasks = new ArrayList<model.Task>();
		members = new ArrayList<model.User>();
		txtSearch.setText("");
		cmbSort.setSelectedIndex(0);
		renderTasks();
	}

	/**
	 * Loads the latest tasks from the database and redraws the page.
	 * page: ALL (team to-do list), USER (my tasks) or COMPLETED; null keeps the page that is showing.
	 */
	public void refreshTasks(String page) {
		if (page != null) {
			String wanted = "USER".equals(page) || "COMPLETED".equals(page) ? page : "ALL";
			if (!wanted.equals(currentPage)) {
				currentTab = 0; // a different list starts on its "All tasks" tab
			}
			currentPage = wanted;
		}
		if (currentUser == null) {
			teamTasks = new ArrayList<model.Task>();
			members = new ArrayList<model.User>();
		} else {
			teamTasks = new database.TaskDAO().getTasksForTeam(currentUser.teamId());
			members = new database.UserDAO().getTeamMembers(currentUser.teamId());
		}
		renderTasks();
	}
}