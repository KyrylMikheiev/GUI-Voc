package src.ui.screens.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import src.App;
import src.auth.AuthManager;
import src.auth.AuthState;
import src.ui.ColorManager;
import src.ui.screens._BaseScreen;
import src.ui.screens.StartPage;
import src.ui.screens.auth.SignUp;
import src.ui.screens.auth.ForgotPassword;
import src.ui.helper.*;

public class Login extends _BaseScreen {
    public Login() {
        super();
        setKeepInHistory(false);
        setHasNavbar(false);
    }

    @Override
    protected JPanel createUI() {
        JPanel login = new JPanel();
        login.setBackground(ColorManager.bodyPrimary());
        login.setLayout(new GridLayout(3, 0));

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        loginLabel.setForeground(ColorManager.text());
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel login_center = new JPanel();
        login_center.setOpaque(false);
        login_center.setLayout(new GridLayout(3, 1, 0, 20));
        login_center.setBorder(new ResponsiveBorder(0, 450, 30, 450));

        PlaceholderTextField loginEmail = new PlaceholderTextField("E-Mail", ColorManager.text());
        loginEmail.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));

        PlaceholderPasswordField loginPassword = new PlaceholderPasswordField("Passwort", ColorManager.text());
        loginPassword.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));

        JPanel loginButtonPanel = new JPanel();
        loginButtonPanel.setLayout(new GridLayout(2, 1, 0, 30));
        loginButtonPanel.setOpaque(false);
        loginButtonPanel.setBorder(new ResponsiveBorder(30, 450, 80, 450));

        JButton loginButton = new JButton("Login");

        JLabel loginWrongLabel = new JLabel("Ungültige E-Mail oder Passwort");
        loginWrongLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        loginWrongLabel.setForeground(ColorManager.text());
        loginWrongLabel.setVisible(false); // Initially hidden

        JPanel forgotPasswordSignUpPanel =  new JPanel();
        forgotPasswordSignUpPanel.setBackground(ColorManager.bodyPrimary());
        forgotPasswordSignUpPanel.setLayout(new GridLayout(1, 2, 30, 0));

        JButton forgotPasswordButton = new JButton("Passwort vergessen");
        JButton signUpButton = new JButton("Ich habe keinen Account");

        forgotPasswordSignUpPanel.add(signUpButton);
        forgotPasswordSignUpPanel.add(forgotPasswordButton);
        
        login_center.add(loginEmail);
        login_center.add(loginPassword);
        login_center.add(loginWrongLabel);
        
        loginEmail.requestFocusInWindow();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = loginEmail.getText();
                String password = new String(loginPassword.getPassword());
                
                boolean success = AuthManager.login(email, password);
                if (!success) {
                    if (AuthManager.getState() == AuthState.NEED_VERIFICATION) {
                       App.switchScreen(new Verification(email));
                    } else {
                        // Login failed, display an error message or handle accordingly
                        loginWrongLabel.setVisible(true);
                    }
                }
            }
        });

        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.switchScreen(new ForgotPassword());
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("sign up button was clicked");
                App.switchScreen(new SignUp());
            }
        });
        
        loginButtonPanel.add(loginButton);
        loginButtonPanel.add(forgotPasswordSignUpPanel);

        login.add(loginLabel);
        login.add(login_center);
        login.add(loginButtonPanel);

        return login;
    }
}