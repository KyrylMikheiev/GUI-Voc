package src.ui.screens.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import src.App;
import src.auth.AuthManager;
import src.ui.ColorManager;
import src.ui.screens._BaseScreen;
import src.ui.screens.auth.Login;
import src.ui.screens.auth.SignUp;
import src.ui.screens.StartPage;
import src.ui.helper.*;

public class Verification extends _BaseScreen {
    private String email;

    public Verification(String email)  {
        super(false);
        this.email = email;
        setKeepInHistory(false);
        setHasNavbar(false);
        this.rebuildUI();
    }

    @Override
    public JPanel createUI() {
        JPanel verification = new JPanel();
        verification.setLayout(new GridLayout(5, 1));
        verification.setBorder(new ResponsiveBorder(0, 350, 0, 350));
        verification.setBackground(ColorManager.bodyPrimary());

        JLabel verificationLabel = new JLabel("Account-Verifizierung");
        verificationLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        verificationLabel.setForeground(ColorManager.text());
        verificationLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel verificationCodePanel = new JPanel();
        //verificationCodePanel.setOpaque(false);
        verificationCodePanel.setLayout(new GridLayout());
        verificationCodePanel.setBorder(new ResponsiveBorder(90, 450, 90, 450));

        PlaceholderTextField verificationCode = new PlaceholderTextField("Verifizierungs-Code", ColorManager.text());

        JPanel verificationNextPanel = new JPanel();
        verificationNextPanel.setLayout(new GridLayout());
        //verificationNextPanel.setOpaque(false);
        verificationNextPanel.setBorder(new ResponsiveBorder(90, 550, 90, 550));

        JButton verificationNext = new JButton("Verifizieren");

        JLabel wrongCodeLabel = new JLabel("Falscher Verifizierungs-Code!");
        wrongCodeLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        wrongCodeLabel.setForeground(ColorManager.text());
        wrongCodeLabel.setVisible(false);

        verificationNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = verificationCode.getText();
                if (!AuthManager.verifyAccount(email, code)) {
                    wrongCodeLabel.setVisible(true);
                }
            }
        });

        JPanel forgotPasswordSignUpPanel =  new JPanel();
        forgotPasswordSignUpPanel.setBorder(new ResponsiveBorder(40,50,80,70));
        forgotPasswordSignUpPanel.setBackground(ColorManager.bodyPrimary());
        forgotPasswordSignUpPanel.setLayout(new GridLayout(1, 2, 30, 0));
        JButton backToLogin = new JButton("Zurück zum Login");
        JButton signUpButton = new JButton("Ich habe keinen Account");
        backToLogin.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                App.switchScreen(new Login());
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("sign up button was clicked");
                App.switchScreen(new SignUp());
            }
        });
        forgotPasswordSignUpPanel.add(backToLogin);
        forgotPasswordSignUpPanel.add(signUpButton);

        verificationCodePanel.add(verificationCode);
        verificationNextPanel.add(verificationNext);
        verificationCode.requestFocusInWindow();
        verification.add(verificationLabel);
        verification.add(verificationCodePanel);
        verification.add(wrongCodeLabel);
        verification.add(verificationNextPanel);
        verification.add(forgotPasswordSignUpPanel);

        // BUG: Fix verification code & next panel

        return verification;
    }
}