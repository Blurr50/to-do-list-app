package page;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * To-Dodo List - login screen (Swing / WindowBuilder).
 * Put the "images" folder (dodo.png, team.png) in your project root,
 * or change the paths below.
 */
public class ToDodoLogin extends JFrame {

	private static final long serialVersionUID = 1L;

	// Palette
	private static final Color LEFT_BG   = new Color(0xF8, 0xF3, 0xE6);
	private static final Color RIGHT_BG  = new Color(0xFF, 0xFE, 0xF9);
	private static final Color DARK      = new Color(0x37, 0x3B, 0x30);
	private static final Color MUTED     = new Color(0x8A, 0x8D, 0x93);
	private static final Color LIGHT     = new Color(0xA9, 0xAB, 0xB3);
	private static final Color BORDER    = new Color(0xE2, 0xE3, 0xE6);
	private static final Color YELLOW    = new Color(0xE8, 0xB9, 0x3C);
	private static final Color BTN_GRAY  = new Color(0xA9, 0xAB, 0xB3);

	private static final String FONT = "Segoe UI";

	private JPanel contentPane;
	private PlaceholderTextField txtEmail;
	private PlaceholderPasswordField txtPassword;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
					ToDodoLogin frame = new ToDodoLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ToDodoLogin() {
		setTitle("To-Dodo List");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1303, 760);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(RIGHT_BG);
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// =============== LEFT PANEL ===============
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(LEFT_BG);
		leftPanel.setBounds(0, 0, 734, 732);
		leftPanel.setLayout(null);
		contentPane.add(leftPanel);

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(22, 20, 56, 56);
		lblLogo.setIcon(scaled("images/dodo.png", 56, 56));
		leftPanel.add(lblLogo);

		JLabel lblBrand = new JLabel("To-Dodo List");
		lblBrand.setForeground(DARK);
		lblBrand.setFont(new Font(FONT, Font.BOLD, 22));
		lblBrand.setBounds(81, 32, 200, 32);
		leftPanel.add(lblBrand);

		JLabel lblTag = new JLabel("GET STUFF DONE!");
		lblTag.setHorizontalAlignment(SwingConstants.CENTER);
		lblTag.setForeground(LIGHT);
		lblTag.setFont(new Font(FONT, Font.BOLD, 10));
		lblTag.setBounds(0, 104, 734, 16);
		leftPanel.add(lblTag);

		JLabel lblHeadline = new JLabel("<html><div style='text-align:center'>Good days start with<br>one small step.</div></html>");
		lblHeadline.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeadline.setForeground(DARK);
		lblHeadline.setFont(new Font(FONT, Font.BOLD, 38));
		lblHeadline.setBounds(0, 125, 734, 100);
		leftPanel.add(lblHeadline);

		JLabel lblSub = new JLabel("A home for your to-dos, your little wins, and everything in between.");
		lblSub.setHorizontalAlignment(SwingConstants.CENTER);
		lblSub.setForeground(new Color(0x5E, 0x61, 0x58));
		lblSub.setFont(new Font(FONT, Font.PLAIN, 14));
		lblSub.setBounds(0, 230, 734, 22);
		leftPanel.add(lblSub);

		JLabel lblIllustration = new JLabel("");
		lblIllustration.setHorizontalAlignment(SwingConstants.CENTER);
		lblIllustration.setIcon(scaled("images/team.png", 330, 332));
		lblIllustration.setBounds(0, 266, 734, 336);
		leftPanel.add(lblIllustration);

		JLabel lblWins = new JLabel("\u2714  Less overwhelm. More little wins.");
		lblWins.setHorizontalAlignment(SwingConstants.CENTER);
		lblWins.setForeground(YELLOW);
		lblWins.setFont(new Font(FONT, Font.BOLD, 11));
		lblWins.setBounds(0, 618, 734, 18);
		leftPanel.add(lblWins);

		JLabel lblRoom = new JLabel("Make room for what matters, one task at a time.");
		lblRoom.setHorizontalAlignment(SwingConstants.CENTER);
		lblRoom.setForeground(MUTED);
		lblRoom.setFont(new Font(FONT, Font.PLAIN, 11));
		lblRoom.setBounds(0, 645, 734, 16);
		leftPanel.add(lblRoom);

		// =============== RIGHT PANEL ===============
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(RIGHT_BG);
		rightPanel.setBounds(734, 0, 569, 732);
		rightPanel.setLayout(null);
		contentPane.add(rightPanel);

		final int fx = 123;   // form left
		final int fw = 327;   // form width

		JLabel lblHelp = new JLabel("\u24D8  Need a hand?");
		lblHelp.setForeground(MUTED);
		lblHelp.setFont(new Font(FONT, Font.PLAIN, 11));
		lblHelp.setBounds(425, 36, 110, 16);
		rightPanel.add(lblHelp);

		JLabel lblEyebrow = new JLabel("\u2014  YOUR DAY, A LITTLE LIGHTER");
		lblEyebrow.setForeground(LIGHT);
		lblEyebrow.setFont(new Font(FONT, Font.BOLD, 10));
		lblEyebrow.setBounds(fx, 148, fw, 16);
		rightPanel.add(lblEyebrow);

		JLabel lblWelcome = new JLabel("Welcome back.");
		lblWelcome.setForeground(DARK);
		lblWelcome.setFont(new Font(FONT, Font.BOLD, 32));
		lblWelcome.setBounds(fx, 168, fw, 44);
		rightPanel.add(lblWelcome);

		JLabel lblPickUp = new JLabel("Let\u2019s pick up where you left off.");
		lblPickUp.setForeground(new Color(0x6B, 0x6E, 0x66));
		lblPickUp.setFont(new Font(FONT, Font.PLAIN, 12));
		lblPickUp.setBounds(fx, 219, fw, 18);
		rightPanel.add(lblPickUp);

		// ---- Email ----
		JLabel lblEmail = new JLabel("Email address");
		lblEmail.setForeground(DARK);
		lblEmail.setFont(new Font(FONT, Font.BOLD, 11));
		lblEmail.setBounds(fx, 257, fw, 16);
		rightPanel.add(lblEmail);

		JLabel lblEmailIcon = new JLabel("\u2709");
		lblEmailIcon.setForeground(MUTED);
		lblEmailIcon.setFont(new Font(FONT, Font.PLAIN, 15));
		lblEmailIcon.setBounds(fx + 12, 288, 20, 20);
		rightPanel.add(lblEmailIcon);

		txtEmail = new PlaceholderTextField("you@example.com");
		txtEmail.setFont(new Font(FONT, Font.PLAIN, 12));
		txtEmail.setBorder(new CompoundBorder(new LineBorder(BORDER, 1, true), new EmptyBorder(0, 36, 0, 10)));
		txtEmail.setBounds(fx, 278, fw, 41);
		rightPanel.add(txtEmail);

		// ---- Password ----
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(DARK);
		lblPassword.setFont(new Font(FONT, Font.BOLD, 11));
		lblPassword.setBounds(fx, 332, fw, 16);
		rightPanel.add(lblPassword);

		JLabel lblLockIcon = new JLabel("\uD83D\uDD12");
		lblLockIcon.setForeground(MUTED);
		lblLockIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		lblLockIcon.setBounds(fx + 12, 363, 20, 20);
		rightPanel.add(lblLockIcon);

		final JLabel lblEye = new JLabel("\uD83D\uDC41");
		lblEye.setForeground(MUTED);
		lblEye.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
		lblEye.setBounds(fx + fw - 30, 363, 20, 20);
		lblEye.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		rightPanel.add(lblEye);

		txtPassword = new PlaceholderPasswordField("Enter your password");
		txtPassword.setFont(new Font(FONT, Font.PLAIN, 12));
		txtPassword.setBorder(new CompoundBorder(new LineBorder(BORDER, 1, true), new EmptyBorder(0, 36, 0, 36)));
		txtPassword.setBounds(fx, 353, fw, 41);
		rightPanel.add(txtPassword);

		lblEye.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// toggle show / hide password
				boolean hidden = txtPassword.getEchoChar() != 0;
				txtPassword.setEchoChar(hidden ? (char) 0 : '\u2022');
			}
		});

		// ---- Remember / Forgot ----
		JCheckBox chkRemember = new JCheckBox("Remember me");
		chkRemember.setSelected(true);
		chkRemember.setOpaque(false);
		chkRemember.setForeground(new Color(0x6B, 0x6E, 0x66));
		chkRemember.setFont(new Font(FONT, Font.PLAIN, 11));
		chkRemember.setFocusPainted(false);
		chkRemember.setBounds(fx - 4, 404, 140, 22);
		rightPanel.add(chkRemember);

		JLabel lblForgot = new JLabel("Forgot password?");
		lblForgot.setHorizontalAlignment(SwingConstants.RIGHT);
		lblForgot.setForeground(MUTED);
		lblForgot.setFont(new Font(FONT, Font.PLAIN, 11));
		lblForgot.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		lblForgot.setBounds(fx + fw - 140, 406, 140, 18);
		rightPanel.add(lblForgot);

		// ---- Sign in ----
		JButton btnSignIn = new JButton("Sign in  \u2192");
		btnSignIn.setForeground(Color.WHITE);
		btnSignIn.setBackground(BTN_GRAY);
		btnSignIn.setFont(new Font(FONT, Font.BOLD, 12));
		btnSignIn.setFocusPainted(false);
		btnSignIn.setBorder(new LineBorder(BTN_GRAY, 1, true));
		btnSignIn.setBounds(fx, 436, fw, 41);
		rightPanel.add(btnSignIn);

		// ---- Divider ----
		JSeparator sepLeft = new JSeparator();
		sepLeft.setForeground(BORDER);
		sepLeft.setBounds(fx, 505, 110, 2);
		rightPanel.add(sepLeft);

		JLabel lblNew = new JLabel("NEW TO-DODO LIST?");
		lblNew.setHorizontalAlignment(SwingConstants.CENTER);
		lblNew.setForeground(LIGHT);
		lblNew.setFont(new Font(FONT, Font.PLAIN, 9));
		lblNew.setBounds(fx + 100, 497, fw - 200, 16);
		rightPanel.add(lblNew);

		JSeparator sepRight = new JSeparator();
		sepRight.setForeground(BORDER);
		sepRight.setBounds(fx + fw - 110, 505, 110, 2);
		rightPanel.add(sepRight);

		// ---- Create account ----
		JButton btnCreate = new JButton("Create a free account");
		btnCreate.setForeground(MUTED);
		btnCreate.setBackground(Color.WHITE);
		btnCreate.setFont(new Font(FONT, Font.BOLD, 11));
		btnCreate.setFocusPainted(false);
		btnCreate.setBorder(new LineBorder(BORDER, 1, true));
		btnCreate.setBounds(fx, 528, fw, 40);
		rightPanel.add(btnCreate);

		JLabel lblFresh = new JLabel("A fresh start. No credit card needed.");
		lblFresh.setHorizontalAlignment(SwingConstants.CENTER);
		lblFresh.setForeground(MUTED);
		lblFresh.setFont(new Font(FONT, Font.PLAIN, 10));
		lblFresh.setBounds(fx, 583, fw, 16);
		rightPanel.add(lblFresh);

		// ---- Footer ----
		JLabel lblCopy = new JLabel("\u00A9 2026 To-Dodo List");
		lblCopy.setForeground(MUTED);
		lblCopy.setFont(new Font(FONT, Font.PLAIN, 9));
		lblCopy.setBounds(68, 696, 200, 14);
		rightPanel.add(lblCopy);

		JLabel lblPrivacy = new JLabel("Privacy");
		lblPrivacy.setForeground(MUTED);
		lblPrivacy.setFont(new Font(FONT, Font.PLAIN, 9));
		lblPrivacy.setBounds(437, 696, 40, 14);
		rightPanel.add(lblPrivacy);

		JLabel lblTerms = new JLabel("Terms");
		lblTerms.setForeground(MUTED);
		lblTerms.setFont(new Font(FONT, Font.PLAIN, 9));
		lblTerms.setBounds(481, 696, 40, 14);
		rightPanel.add(lblTerms);
	}

	/** Loads an image from disk and scales it smoothly. Returns null icon if missing. */
	private static ImageIcon scaled(String path, int w, int h) {
		java.net.URL url = ToDodoLogin.class.getResource("/" + path);
	    if (url == null) {
	        System.err.println("Image not found: " + path);
	        return null;
	    }
	    ImageIcon raw = new ImageIcon(url);
	    return new ImageIcon(raw.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}
	// ---------- Placeholder helpers ----------
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
				g.drawString(hint, getInsets().left, (getHeight() + g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent()) / 2);
			}
		}
	}

	static class PlaceholderPasswordField extends JPasswordField {
		private static final long serialVersionUID = 1L;
		private final String hint;

		PlaceholderPasswordField(String hint) {
			this.hint = hint;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (getPassword().length == 0) {
				g.setColor(LIGHT);
				g.setFont(getFont());
				g.drawString(hint, getInsets().left, (getHeight() + g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent()) / 2);
			}
		}
	}
}
