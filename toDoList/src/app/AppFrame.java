package app;

import java.awt.EventQueue;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javax.swing.JFrame;

public class AppFrame extends JFrame {
    public static final String LOGIN = "login";
    public static final String CREATE_ACCOUNT = "createAccount";
    public static final String MAIN = "main";
    public static final String ADD_TASK = "addTask";

    private final CardLayout cards = new CardLayout();
    private final JPanel root = new JPanel(cards);

    public AppFrame() {
        setTitle("My App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        root.add(new pages.LoginPanel(this), LOGIN);
        //root.add(new pages.CreateAccountPanel(this), CREATE_ACCOUNT);
        //root.add(new pages.MainPanel(this), MAIN);
        //root.add(new pages.AddTaskPanel(this), ADD_TASK);

        setContentPane(root);
        
        //start at the login page
        showPage(LOGIN);
    }

    
    
    //show a page by calling frame.showPage("pageName")
    public void showPage(String name) {
        cards.show(root, name);
    }
}