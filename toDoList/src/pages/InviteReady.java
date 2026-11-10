package pages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

/**
 * To-Dodo List - "Your team list is ready" screen (Swing / WindowBuilder).
 * Images are loaded from the classpath: src/images/dodo.png and src/images/team.png
 */
public class InviteReady extends JPanel {

	private static final long serialVersionUID = 1L;

	// Palette
	private static final Color LEFT_BG    = new Color(0xF6, 0xF3, 0xE8);
	private static final Color RIGHT_BG   = new Color(0xFF, 0xFE, 0xF9);
	private static final Color DARK       = new Color(0x37, 0x3B, 0x30);
	private static final Color MUTED      = new Color(0x8A, 0x8D, 0x93);
	private static final Color LIGHT      = new Color(0xA9, 0xAB, 0xB3);
	private static final Color BORDER     = new Color(0xE2, 0xE3, 0xE6);
	private static final Color GOLD       = new Color(0xE0, 0xC9, 0x7F);
	private static final Color GOLD_LINE  = new Color(0xEB, 0xDF, 0xAE);
	private static final Color GOLD_DARK  = new Color(0x6B, 0x5A, 0x2A);

	private static final String FONT = "Segoe UI";

	/** The generated invite code shown on screen. Change with setInviteCode(). */
	private String inviteCode = "67DODO";

	private JPanel contentPane;
	private JLabel lblInviteCode;


	public InviteReady(app.AppFrame frame) {
		frame.setTitle("To-Dodo List - Invite ready");
		contentPane = new JPanel();
		contentPane.setBackground(RIGHT_BG);
		contentPane.setPreferredSize(new Dimension(1219, 882));
		contentPane.setLayout(null);

		// =============== LEFT PANEL ===============
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(LEFT_BG);
		leftPanel.setBounds(0, 0, 686, 733);
		leftPanel.setLayout(null);
		contentPane.add(leftPanel);

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(18, 13, 52, 52);
		lblLogo.setIcon(scaled("images/dodo.png", 52, 52));
		leftPanel.add(lblLogo);

		JLabel lblBrand = new JLabel("To-Dodo List");
		lblBrand.setForeground(DARK);
		lblBrand.setFont(new Font(FONT, Font.BOLD, 20));
		lblBrand.setBounds(74, 25, 200, 30);
		leftPanel.add(lblBrand);

		JLabel lblTag = new JLabel("GET STUFF DONE!");
		lblTag.setHorizontalAlignment(SwingConstants.CENTER);
		lblTag.setForeground(LIGHT);
		lblTag.setFont(new Font(FONT, Font.BOLD, 9));
		lblTag.setBounds(0, 91, 686, 14);
		leftPanel.add(lblTag);

		JLabel lblHeadline = new JLabel("<html><div style='text-align:center'>Good days start with<br>one small step.</div></html>");
		lblHeadline.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeadline.setForeground(DARK);
		lblHeadline.setFont(new Font(FONT, Font.BOLD, 36));
		lblHeadline.setBounds(0, 108, 686, 100);
		leftPanel.add(lblHeadline);

		JLabel lblSub = new JLabel("A home for your to-dos, your little wins, and everything in between.");
		lblSub.setHorizontalAlignment(SwingConstants.CENTER);
		lblSub.setForeground(new Color(0x5E, 0x61, 0x58));
		lblSub.setFont(new Font(FONT, Font.PLAIN, 13));
		lblSub.setBounds(0, 207, 686, 20);
		leftPanel.add(lblSub);

		JLabel lblIllustration = new JLabel("");
		lblIllustration.setHorizontalAlignment(SwingConstants.CENTER);
		lblIllustration.setIcon(scaled("images/team.png", 304, 306));
		lblIllustration.setBounds(0, 244, 686, 312);
		leftPanel.add(lblIllustration);

		JLabel lblWins = new JLabel("<html><span style='color:#E0C97F'>\u2714</span>&nbsp; Less overwhelm. More little wins.</html>");
		lblWins.setHorizontalAlignment(SwingConstants.CENTER);
		lblWins.setForeground(GOLD_DARK);
		lblWins.setFont(new Font(FONT, Font.BOLD, 10));
		lblWins.setBounds(0, 571, 686, 18);
		leftPanel.add(lblWins);

		JLabel lblRoom = new JLabel("Make room for what matters, one task at a time.");
		lblRoom.setHorizontalAlignment(SwingConstants.CENTER);
		lblRoom.setForeground(MUTED);
		lblRoom.setFont(new Font(FONT, Font.PLAIN, 10));
		lblRoom.setBounds(0, 661, 686, 16);
		leftPanel.add(lblRoom);

		// =============== RIGHT PANEL ===============
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(RIGHT_BG);
		rightPanel.setBounds(686, 0, 520, 733);
		rightPanel.setLayout(null);
		contentPane.add(rightPanel);

		final int fx = 115;   // form left
		final int fw = 306;   // form width

		JLabel lblHelp = new JLabel("\u24D8  Need a hand?");
		lblHelp.setForeground(MUTED);
		lblHelp.setFont(new Font(FONT, Font.PLAIN, 10));
		lblHelp.setBounds(397, 26, 110, 16);
		rightPanel.add(lblHelp);

		JLabel lblCheckBadge = new JLabel("\u2714");
		lblCheckBadge.setHorizontalAlignment(SwingConstants.CENTER);
		lblCheckBadge.setOpaque(true);
		lblCheckBadge.setBackground(LEFT_BG);
		lblCheckBadge.setForeground(GOLD_DARK);
		lblCheckBadge.setFont(new Font(FONT, Font.BOLD, 9));
		lblCheckBadge.setBorder(new LineBorder(LEFT_BG, 1, true));
		lblCheckBadge.setBounds(fx, 77, 16, 16);
		rightPanel.add(lblCheckBadge);

		JLabel lblReady = new JLabel("INVITE READY");
		lblReady.setForeground(GOLD_DARK);
		lblReady.setFont(new Font(FONT, Font.BOLD, 9));
		lblReady.setBounds(fx + 22, 77, 200, 16);
		rightPanel.add(lblReady);

		JLabel lblHeading = new JLabel("<html>Your team list<br>is ready.</html>");
		lblHeading.setVerticalAlignment(SwingConstants.TOP);
		lblHeading.setForeground(DARK);
		lblHeading.setFont(new Font(FONT, Font.BOLD, 28));
		lblHeading.setBounds(fx, 100, fw + 40, 80);
		rightPanel.add(lblHeading);

		JLabel lblShare = new JLabel("<html>Share this code or invite link with teammates so they can join your new to-do list.</html>");
		lblShare.setVerticalAlignment(SwingConstants.TOP);
		lblShare.setForeground(new Color(0x6B, 0x6E, 0x66));
		lblShare.setFont(new Font(FONT, Font.PLAIN, 10));
		lblShare.setBounds(fx, 179, fw, 36);
		rightPanel.add(lblShare);

		// ---- Invite code card ----
		JPanel codeCard = new JPanel();
		codeCard.setBackground(LEFT_BG);
		codeCard.setBorder(new LineBorder(GOLD_LINE, 1, true));
		codeCard.setBounds(fx, 230, fw, 123);
		codeCard.setLayout(null);
		rightPanel.add(codeCard);

		JLabel lblCodeCaption = new JLabel("6-character invite code");
		lblCodeCaption.setHorizontalAlignment(SwingConstants.CENTER);
		lblCodeCaption.setForeground(new Color(0x4A, 0x4D, 0x44));
		lblCodeCaption.setFont(new Font(FONT, Font.PLAIN, 9));
		lblCodeCaption.setBounds(0, 13, fw, 14);
		codeCard.add(lblCodeCaption);

		lblInviteCode = new JLabel(inviteCode);
		lblInviteCode.setHorizontalAlignment(SwingConstants.CENTER);
		lblInviteCode.setForeground(DARK);
		lblInviteCode.setFont(new Font(FONT, Font.BOLD, 28));
		lblInviteCode.setBounds(0, 31, fw, 40);
		codeCard.add(lblInviteCode);

		final JButton btnCopyCode = new JButton("\u29C9  Copy code");
		btnCopyCode.setForeground(DARK);
		btnCopyCode.setBackground(Color.WHITE);
		btnCopyCode.setFont(new Font(FONT, Font.BOLD, 10));
		btnCopyCode.setFocusPainted(false);
		btnCopyCode.setBorder(new LineBorder(BORDER, 1, true));
		btnCopyCode.setBounds(16, 78, 274, 31);
		btnCopyCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClipboard(inviteCode);
				flash(btnCopyCode, "Copied!");
			}
		});
		codeCard.add(btnCopyCode);

		// ---- Copy invite link ----
		final JButton btnCopyLink = new JButton("\uD83D\uDD17  Copy invite link");
		btnCopyLink.setForeground(DARK);
		btnCopyLink.setBackground(Color.WHITE);
		btnCopyLink.setFont(new Font(FONT, Font.BOLD, 10));
		btnCopyLink.setFocusPainted(false);
		btnCopyLink.setBorder(new LineBorder(BORDER, 1, true));
		btnCopyLink.setBounds(fx, 370, fw, 36);
		btnCopyLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: replace with your real invite URL
				copyToClipboard("https://tododolist.example/join/" + inviteCode);
				flash(btnCopyLink, "Copied!");
			}
		});
		rightPanel.add(btnCopyLink);

		// ---- QR card ----
		JPanel qrCard = new JPanel();
		qrCard.setBackground(Color.WHITE);
		qrCard.setBorder(new LineBorder(BORDER, 1, true));
		qrCard.setBounds(fx, 422, fw, 96);
		qrCard.setLayout(null);
		rightPanel.add(qrCard);

		JPanel qrThumb = new JPanel();
		qrThumb.setBackground(LEFT_BG);
		qrThumb.setBounds(12, 12, 72, 72);
		qrThumb.setLayout(null);
		qrCard.add(qrThumb);

		JLabel lblPreview = new JLabel("PREVIEW");
		lblPreview.setHorizontalAlignment(SwingConstants.CENTER);
		lblPreview.setOpaque(true);
		lblPreview.setBackground(Color.WHITE);
		lblPreview.setForeground(GOLD_DARK);
		lblPreview.setFont(new Font(FONT, Font.BOLD, 6));
		lblPreview.setBounds(14, 30, 44, 12);
		qrThumb.add(lblPreview);

		JLabel lblQr1 = new JLabel("");
		lblQr1.setBorder(new LineBorder(LIGHT, 1, true));
		lblQr1.setBounds(16, 18, 11, 11);
		qrThumb.add(lblQr1);

		JLabel lblQr2 = new JLabel("");
		lblQr2.setBorder(new LineBorder(LIGHT, 1, true));
		lblQr2.setBounds(16, 44, 11, 11);
		qrThumb.add(lblQr2);

		JLabel lblQr3 = new JLabel("");
		lblQr3.setBorder(new LineBorder(LEFT_BG.darker(), 1, true));
		lblQr3.setBounds(46, 18, 11, 11);
		qrThumb.add(lblQr3);

		JLabel lblQr4 = new JLabel("");
		lblQr4.setBorder(new LineBorder(LEFT_BG.darker(), 1, true));
		lblQr4.setBounds(46, 44, 11, 11);
		qrThumb.add(lblQr4);

		JLabel lblQrTitle = new JLabel("Share with a QR code");
		lblQrTitle.setForeground(DARK);
		lblQrTitle.setFont(new Font(FONT, Font.BOLD, 11));
		lblQrTitle.setBounds(97, 16, 200, 16);
		qrCard.add(lblQrTitle);

		JLabel lblQrDesc = new JLabel("<html>Another way to bring your teammates together.</html>");
		lblQrDesc.setVerticalAlignment(SwingConstants.TOP);
		lblQrDesc.setForeground(new Color(0x6B, 0x6E, 0x66));
		lblQrDesc.setFont(new Font(FONT, Font.PLAIN, 9));
		lblQrDesc.setBounds(97, 36, 190, 28);
		qrCard.add(lblQrDesc);

		JLabel lblQrNote = new JLabel("Preview only \u00B7 Not scannable");
		lblQrNote.setForeground(GOLD_DARK);
		lblQrNote.setFont(new Font(FONT, Font.PLAIN, 8));
		lblQrNote.setBounds(97, 66, 190, 14);
		qrCard.add(lblQrNote);

		// ---- Go to list ----
		JButton btnGo = new JButton("Go to team to-do list  \u2192");
		btnGo.setForeground(DARK);
		btnGo.setBackground(GOLD);
		btnGo.setFont(new Font(FONT, Font.BOLD, 11));
		btnGo.setFocusPainted(false);
		btnGo.setBorder(new LineBorder(GOLD, 1, true));
		btnGo.setBounds(fx, 539, fw, 38);
		btnGo.addActionListener(e -> {
			
			// TODO: real authentication here
			frame.setWelcomeLabelText("Welcome back, " + "!");
			frame.showPage(app.AppFrame.MAIN);
		});
		rightPanel.add(btnGo);

		JLabel lblNoWait = new JLabel("No need to wait \u2014 start your first task now.");
		lblNoWait.setHorizontalAlignment(SwingConstants.CENTER);
		lblNoWait.setForeground(MUTED);
		lblNoWait.setFont(new Font(FONT, Font.PLAIN, 10));
		lblNoWait.setBounds(fx, 585, fw, 16);
		rightPanel.add(lblNoWait);

		// ---- Footer ----
		JLabel lblCopy = new JLabel("\u00A9 2026 To-Dodo List");
		lblCopy.setForeground(MUTED);
		lblCopy.setFont(new Font(FONT, Font.PLAIN, 9));
		lblCopy.setBounds(64, 708, 200, 14);
		rightPanel.add(lblCopy);

		JLabel lblPrivacy = new JLabel("Privacy");
		lblPrivacy.setForeground(MUTED);
		lblPrivacy.setFont(new Font(FONT, Font.PLAIN, 9));
		lblPrivacy.setBounds(407, 708, 40, 14);
		rightPanel.add(lblPrivacy);

		JLabel lblTerms = new JLabel("Terms");
		lblTerms.setForeground(MUTED);
		lblTerms.setFont(new Font(FONT, Font.PLAIN, 9));
		lblTerms.setBounds(449, 708, 40, 14);
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

	/** Call this to show a real generated code (e.g. from the previous screen). */
	public void setInviteCode(String code) {
		this.inviteCode = code;
		lblInviteCode.setText(code);
	}

	private static void copyToClipboard(String text) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
	}

	/** Briefly shows a message on a button, then restores its original text. */
	private static void flash(final JButton button, String message) {
		final String original = button.getText();
		button.setText(message);
		Timer timer = new Timer(1200, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button.setText(original);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	/** Loads an image from the classpath (src/images/...) and scales it smoothly. */
	private static ImageIcon scaled(String path, int w, int h) {
		java.net.URL url = InviteReady.class.getResource("/" + path);
		if (url == null) {
			System.err.println("Image not found: " + path);
			return null;
		}
		ImageIcon raw = new ImageIcon(url);
		return new ImageIcon(raw.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}
}
