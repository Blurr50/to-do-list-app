package pages;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * To-Dodo List - "Join a to-do list" screen (Swing / WindowBuilder).
 * Images are loaded from the classpath: src/images/dodo.png and src/images/team.png
 */
public class JoinList extends JPanel {

	private static final long serialVersionUID = 1L;

	// Palette
	private static final Color LEFT_BG    = new Color(0xF6, 0xF3, 0xE8);
	private static final Color RIGHT_BG   = new Color(0xFF, 0xFE, 0xF9);
	private static final Color DARK       = new Color(0x37, 0x3B, 0x30);
	private static final Color MUTED      = new Color(0x8A, 0x8D, 0x93);
	private static final Color LIGHT      = new Color(0xA9, 0xAB, 0xB3);
	private static final Color BORDER     = new Color(0xE2, 0xE3, 0xE6);
	private static final Color GOLD       = new Color(0xE0, 0xC9, 0x7F);
	private static final Color GOLD_DARK  = new Color(0x6B, 0x5A, 0x2A);
	private static final Color CARD_BG    = new Color(0xFD, 0xFA, 0xF0);
	private static final Color CARD_LINE  = new Color(0xEB, 0xDF, 0xAE);
	private static final Color BTN_GRAY   = new Color(0xA9, 0xAB, 0xB3);

	private static final String FONT = "Segoe UI";

	private JPanel contentPane;
	private JTextField txtCode1, txtCode2, txtCode3, txtCode4, txtCode5, txtCode6;


	public JoinList(app.AppFrame frame) {
		frame.setTitle("To-Dodo List - Join");
		contentPane = new JPanel();
		contentPane.setBackground(RIGHT_BG);
		contentPane.setPreferredSize(new Dimension(1219, 882));
		contentPane.setLayout(null);

		// =============== LEFT PANEL ===============
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(LEFT_BG);
		leftPanel.setBounds(0, 0, 682, 741);
		leftPanel.setLayout(null);
		contentPane.add(leftPanel);

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(16, 16, 52, 52);
		lblLogo.setIcon(scaled("images/dodo.png", 52, 52));
		leftPanel.add(lblLogo);

		JLabel lblBrand = new JLabel("To-Dodo List");
		lblBrand.setForeground(DARK);
		lblBrand.setFont(new Font(FONT, Font.BOLD, 20));
		lblBrand.setBounds(71, 28, 200, 30);
		leftPanel.add(lblBrand);

		JLabel lblTag = new JLabel("GET STUFF DONE!");
		lblTag.setHorizontalAlignment(SwingConstants.CENTER);
		lblTag.setForeground(LIGHT);
		lblTag.setFont(new Font(FONT, Font.BOLD, 9));
		lblTag.setBounds(0, 94, 682, 14);
		leftPanel.add(lblTag);

		JLabel lblHeadline = new JLabel("<html><div style='text-align:center'>Good days start with<br>one small step.</div></html>");
		lblHeadline.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeadline.setForeground(DARK);
		lblHeadline.setFont(new Font(FONT, Font.BOLD, 36));
		lblHeadline.setBounds(0, 112, 682, 100);
		leftPanel.add(lblHeadline);

		JLabel lblSub = new JLabel("A home for your to-dos, your little wins, and everything in between.");
		lblSub.setHorizontalAlignment(SwingConstants.CENTER);
		lblSub.setForeground(new Color(0x5E, 0x61, 0x58));
		lblSub.setFont(new Font(FONT, Font.PLAIN, 13));
		lblSub.setBounds(0, 211, 682, 20);
		leftPanel.add(lblSub);

		JLabel lblIllustration = new JLabel("");
		lblIllustration.setHorizontalAlignment(SwingConstants.CENTER);
		lblIllustration.setIcon(scaled("images/team.png", 306, 308));
		lblIllustration.setBounds(0, 248, 682, 312);
		leftPanel.add(lblIllustration);

		JLabel lblWins = new JLabel("<html><span style='color:#E0C97F'>\u2714</span>&nbsp; Less overwhelm. More little wins.</html>");
		lblWins.setHorizontalAlignment(SwingConstants.CENTER);
		lblWins.setForeground(GOLD_DARK);
		lblWins.setFont(new Font(FONT, Font.BOLD, 10));
		lblWins.setBounds(0, 575, 682, 18);
		leftPanel.add(lblWins);

		JLabel lblRoom = new JLabel("Make room for what matters, one task at a time.");
		lblRoom.setHorizontalAlignment(SwingConstants.CENTER);
		lblRoom.setForeground(MUTED);
		lblRoom.setFont(new Font(FONT, Font.PLAIN, 10));
		lblRoom.setBounds(0, 664, 682, 16);
		leftPanel.add(lblRoom);

		// =============== RIGHT PANEL ===============
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(RIGHT_BG);
		rightPanel.setBounds(682, 0, 524, 741);
		rightPanel.setLayout(null);
		contentPane.add(rightPanel);

		final int fx = 115;   // form left
		final int fw = 306;   // form width

		JLabel lblEyebrow = new JLabel("\u2014  JOIN A TO-DO LIST");
		lblEyebrow.setForeground(LIGHT);
		lblEyebrow.setFont(new Font(FONT, Font.BOLD, 9));
		lblEyebrow.setBounds(fx, 78, fw, 16);
		rightPanel.add(lblEyebrow);

		JLabel lblHeading = new JLabel("<html>Good things get<br>done together.</html>");
		lblHeading.setVerticalAlignment(SwingConstants.TOP);
		lblHeading.setForeground(DARK);
		lblHeading.setFont(new Font(FONT, Font.BOLD, 28));
		lblHeading.setBounds(fx, 98, fw + 40, 80);
		rightPanel.add(lblHeading);

		JLabel lblEnter = new JLabel("Enter the invite code shared with you.");
		lblEnter.setForeground(new Color(0x6B, 0x6E, 0x66));
		lblEnter.setFont(new Font(FONT, Font.PLAIN, 11));
		lblEnter.setBounds(fx, 179, fw, 18);
		rightPanel.add(lblEnter);

		// ---- Invite code ----
		JLabel lblCode = new JLabel("6-character invite code");
		lblCode.setForeground(DARK);
		lblCode.setFont(new Font(FONT, Font.BOLD, 10));
		lblCode.setBounds(fx, 214, fw, 14);
		rightPanel.add(lblCode);

		JPanel codePanel = new JPanel();
		codePanel.setBackground(Color.WHITE);
		codePanel.setBorder(new LineBorder(BORDER, 1, true));
		codePanel.setBounds(fx, 235, fw, 51);
		codePanel.setLayout(null);
		rightPanel.add(codePanel);

		txtCode1 = new JTextField();
		txtCode1.setBounds(1, 1, 50, 49);
		codePanel.add(txtCode1);

		txtCode2 = new JTextField();
		txtCode2.setBounds(51, 1, 50, 49);
		codePanel.add(txtCode2);

		txtCode3 = new JTextField();
		txtCode3.setBounds(101, 1, 50, 49);
		codePanel.add(txtCode3);

		txtCode4 = new JTextField();
		txtCode4.setBounds(151, 1, 50, 49);
		codePanel.add(txtCode4);

		txtCode5 = new JTextField();
		txtCode5.setBounds(201, 1, 50, 49);
		codePanel.add(txtCode5);

		txtCode6 = new JTextField();
		txtCode6.setBounds(251, 1, 52, 49);
		codePanel.add(txtCode6);

		JSeparator sep1 = new JSeparator(SwingConstants.VERTICAL);
		sep1.setForeground(BORDER);
		sep1.setBounds(51, 16, 2, 20);
		codePanel.add(sep1);

		JSeparator sep2 = new JSeparator(SwingConstants.VERTICAL);
		sep2.setForeground(BORDER);
		sep2.setBounds(101, 16, 2, 20);
		codePanel.add(sep2);

		JSeparator sep3 = new JSeparator(SwingConstants.VERTICAL);
		sep3.setForeground(BORDER);
		sep3.setBounds(151, 16, 2, 20);
		codePanel.add(sep3);

		JSeparator sep4 = new JSeparator(SwingConstants.VERTICAL);
		sep4.setForeground(BORDER);
		sep4.setBounds(201, 16, 2, 20);
		codePanel.add(sep4);

		JSeparator sep5 = new JSeparator(SwingConstants.VERTICAL);
		sep5.setForeground(BORDER);
		sep5.setBounds(251, 16, 2, 20);
		codePanel.add(sep5);

		// typing behaviour (uppercase, 1 char, auto-advance, backspace goes back)
		setupCodeField(txtCode1, null, txtCode2);
		setupCodeField(txtCode2, txtCode1, txtCode3);
		setupCodeField(txtCode3, txtCode2, txtCode4);
		setupCodeField(txtCode4, txtCode3, txtCode5);
		setupCodeField(txtCode5, txtCode4, txtCode6);
		setupCodeField(txtCode6, txtCode5, null);

		// ---- Join list ----
		JButton btnJoin = new JButton("Join list  \u2192");
		btnJoin.setForeground(Color.WHITE);
		btnJoin.setBackground(BTN_GRAY);
		btnJoin.setFont(new Font(FONT, Font.BOLD, 11));
		btnJoin.setFocusPainted(false);
		btnJoin.setBorder(new LineBorder(BTN_GRAY, 1, true));
		btnJoin.setBounds(fx, 302, fw, 38);
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code = (txtCode1.getText() + txtCode2.getText() + txtCode3.getText()
						+ txtCode4.getText() + txtCode5.getText() + txtCode6.getText());
				if (code.length() < 6) {
					JOptionPane.showMessageDialog(JoinList.this, "Please enter all 6 characters.");
				} else {
					JOptionPane.showMessageDialog(JoinList.this, "Joining list " + code + "...");
				}
			}
		});
		rightPanel.add(btnJoin);

		
		
		//REPLACE WITH LOGIN DETAILS
		// ---- Divider: scan ----
		JSeparator sepScanL = new JSeparator();
		sepScanL.setForeground(BORDER);
		sepScanL.setBounds(fx, 366, 88, 2);
		rightPanel.add(sepScanL);

		JLabel lblScanOr = new JLabel("Or scan an invite QR code");
		lblScanOr.setHorizontalAlignment(SwingConstants.CENTER);
		lblScanOr.setForeground(MUTED);
		lblScanOr.setFont(new Font(FONT, Font.PLAIN, 9));
		lblScanOr.setBounds(fx + 90, 359, fw - 180, 16);
		rightPanel.add(lblScanOr);

		JSeparator sepScanR = new JSeparator();
		sepScanR.setForeground(BORDER);
		sepScanR.setBounds(fx + fw - 90, 366, 90, 2);
		rightPanel.add(sepScanR);

		// ---- Scan QR ----
		JButton btnScan = new JButton("\u25A3  Scan QR code");
		btnScan.setForeground(LIGHT);
		btnScan.setBackground(RIGHT_BG);
		btnScan.setFont(new Font(FONT, Font.BOLD, 10));
		btnScan.setFocusPainted(false);
		btnScan.setBorder(new LineBorder(BORDER, 1, true));
		btnScan.setBounds(fx, 386, fw, 36);
		rightPanel.add(btnScan);

		// ---- Divider: create ----
		JSeparator sepNewL = new JSeparator();
		sepNewL.setForeground(BORDER);
		sepNewL.setBounds(fx, 449, 88, 2);
		rightPanel.add(sepNewL);

		JLabel lblNewOr = new JLabel("Or create a new team");
		lblNewOr.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewOr.setForeground(MUTED);
		lblNewOr.setFont(new Font(FONT, Font.PLAIN, 9));
		lblNewOr.setBounds(fx + 90, 442, fw - 180, 16);
		rightPanel.add(lblNewOr);

		JSeparator sepNewR = new JSeparator();
		sepNewR.setForeground(BORDER);
		sepNewR.setBounds(fx + fw - 90, 449, 90, 2);
		rightPanel.add(sepNewR);

		// ---- Team card ----
		JPanel cardPanel = new JPanel();
		cardPanel.setBackground(CARD_BG);
		cardPanel.setBorder(new LineBorder(CARD_LINE, 1, true));
		cardPanel.setBounds(fx, 474, fw, 183);
		cardPanel.setLayout(null);
		rightPanel.add(cardPanel);

		JLabel lblPlus = new JLabel("+");
		lblPlus.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlus.setForeground(GOLD);
		lblPlus.setFont(new Font(FONT, Font.PLAIN, 18));
		lblPlus.setBorder(new LineBorder(CARD_LINE, 1, true));
		lblPlus.setBounds(14, 20, 26, 26);
		cardPanel.add(lblPlus);

		JLabel lblCardTitle = new JLabel("Create a team to-do list");
		lblCardTitle.setForeground(DARK);
		lblCardTitle.setFont(new Font(FONT, Font.BOLD, 12));
		lblCardTitle.setBounds(48, 14, 240, 16);
		cardPanel.add(lblCardTitle);

		JLabel lblCardDesc = new JLabel("<html>You\u2019re the first person in? Start a new team list and generate an invite code to share with teammates.</html>");
		lblCardDesc.setVerticalAlignment(SwingConstants.TOP);
		lblCardDesc.setForeground(new Color(0x6B, 0x6E, 0x66));
		lblCardDesc.setFont(new Font(FONT, Font.PLAIN, 9));
		lblCardDesc.setBounds(48, 31, 245, 30);
		cardPanel.add(lblCardDesc);

		JLabel lblCheck1 = new JLabel("<html><span style='color:#E0C97F'>\u2714</span>&nbsp;&nbsp; Create a new team to-do list from scratch.</html>");
		lblCheck1.setForeground(new Color(0x4A, 0x4D, 0x44));
		lblCheck1.setFont(new Font(FONT, Font.PLAIN, 9));
		lblCheck1.setBounds(14, 67, 280, 16);
		cardPanel.add(lblCheck1);

		JLabel lblCheck2 = new JLabel("<html><span style='color:#E0C97F'>\u2714</span>&nbsp;&nbsp; Generate a unique invite code to share with teammates.</html>");
		lblCheck2.setForeground(new Color(0x4A, 0x4D, 0x44));
		lblCheck2.setFont(new Font(FONT, Font.PLAIN, 9));
		lblCheck2.setBounds(14, 87, 285, 16);
		cardPanel.add(lblCheck2);

		JLabel lblCheck3 = new JLabel("<html><span style='color:#E0C97F'>\u2714</span>&nbsp;&nbsp; Start assigning tasks and tracking progress together.</html>");
		lblCheck3.setForeground(new Color(0x4A, 0x4D, 0x44));
		lblCheck3.setFont(new Font(FONT, Font.PLAIN, 9));
		lblCheck3.setBounds(14, 107, 285, 16);
		cardPanel.add(lblCheck3);

		JButton btnGenerate = new JButton("Generate invite code  \u2192");
		btnGenerate.setForeground(DARK);
		btnGenerate.setBackground(GOLD);
		btnGenerate.setFont(new Font(FONT, Font.BOLD, 11));
		btnGenerate.setFocusPainted(false);
		btnGenerate.setBorder(new LineBorder(GOLD, 1, true));
		btnGenerate.setBounds(14, 132, 276, 36);
		btnGenerate.addActionListener(e -> {frame.showPage(app.AppFrame.INVITE_READY);});
		cardPanel.add(btnGenerate);

		// ---- Already a member ----
		JLabel lblMember = new JLabel("<html>Already a member? <span style='color:#8A8D93'>Sign in</span></html>");
		lblMember.setHorizontalAlignment(SwingConstants.CENTER);
		lblMember.setForeground(MUTED);
		lblMember.setFont(new Font(FONT, Font.PLAIN, 10));
		lblMember.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblMember.setBounds(fx, 676, fw, 16);
		lblMember.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				frame.showPage(app.AppFrame.LOGIN);
			}
		});
		rightPanel.add(lblMember);

		// ---- Footer ----
		JLabel lblCopy = new JLabel("\u00A9 2026 To-Dodo List");
		lblCopy.setForeground(MUTED);
		lblCopy.setFont(new Font(FONT, Font.PLAIN, 9));
		lblCopy.setBounds(64, 705, 200, 14);
		rightPanel.add(lblCopy);

		JLabel lblPrivacy = new JLabel("Privacy");
		lblPrivacy.setForeground(MUTED);
		lblPrivacy.setFont(new Font(FONT, Font.PLAIN, 9));
		lblPrivacy.setBounds(408, 705, 40, 14);
		rightPanel.add(lblPrivacy);

		JLabel lblTerms = new JLabel("Terms");
		lblTerms.setForeground(MUTED);
		lblTerms.setFont(new Font(FONT, Font.PLAIN, 9));
		lblTerms.setBounds(450, 705, 40, 14);
		rightPanel.add(lblTerms);
		
		JScrollPane scrollPane = new JScrollPane(contentPane);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		scrollPane.getViewport().setBackground(RIGHT_BG);

		setLayout(new java.awt.BorderLayout());
		add(scrollPane, java.awt.BorderLayout.CENTER);
	}

	/** Styles one invite-code box and wires up typing behaviour. */
	private void setupCodeField(final JTextField field, final JTextField prev, final JTextField next) {
		field.setHorizontalAlignment(SwingConstants.CENTER);
		field.setFont(new Font(FONT, Font.BOLD, 22));
		field.setForeground(DARK);
		field.setBorder(null);
		field.setOpaque(false);

		((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				if (text == null || text.isEmpty()) {
					super.replace(fb, offset, length, text, attrs);
					return;
				}
				char c = Character.toUpperCase(text.charAt(text.length() - 1));
				if (Character.isLetterOrDigit(c)) {
					super.replace(fb, 0, fb.getDocument().getLength(), String.valueOf(c), attrs);
					if (next != null) {
						next.requestFocusInWindow();
					}
				}
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
					throws BadLocationException {
				replace(fb, offset, 0, text, attr);
			}
		});

		field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && field.getText().isEmpty() && prev != null) {
					prev.setText("");
					prev.requestFocusInWindow();
				}
			}
		});
	}

	/** Loads an image from the classpath (src/images/...) and scales it smoothly. */
	private static ImageIcon scaled(String path, int w, int h) {
		java.net.URL url = JoinList.class.getResource("/" + path);
		if (url == null) {
			System.err.println("Image not found: " + path);
			return null;
		}
		ImageIcon raw = new ImageIcon(url);
		return new ImageIcon(raw.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}

}
