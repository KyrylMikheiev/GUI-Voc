package src;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class Setup {
    // design select
    private JPanel designSelect;
    private JLabel designLabel;
    private JButton lightMode;
    private JButton darkMode;
    private JButton designNext;
    // registration 1
    private JPanel registration;
    private JLabel registrationLabel;
    private PlaceholderTextField firstName;
    private PlaceholderTextField lastName;
    private JComboBox<String> gradeLevel;
    private JButton registrationNext;
    // registration 2
    private JPanel registration2;
    private JLabel registrationLabel2;
    private PlaceholderTextField email;
    private PlaceholderTextField password;
    private PlaceholderTextField repeatPassword;
    private JButton registerButton;
    // verification
    private JPanel verification;
    private JLabel verificationLabel;
    private PlaceholderTextField verificationCode;
    private JButton verificationNext;
    // login
    private JPanel login;
    private JLabel loginLabel;
    private PlaceholderTextField loginEmail;
    private PlaceholderTextField loginPassword;
    private JLabel loginWrongLabel;
    private JButton loginButton;


    public Setup(JPanel content) {
        JPanel bodyPanel = new JPanel();



        content.add(bodyPanel);
    }
    public void designSelect() {

    }
    public void registration() {

    }
    public void verification() {

    }
    public void login() {

    }
}
