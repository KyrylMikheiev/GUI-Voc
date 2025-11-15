package src.ui.screens.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import src.App;
import src.ui.screens._BaseScreen;
import src.ui.screens.auth.*;
import src.ui.ColorManager;
import src.ui.helper.*;

public class ForgotPassword extends _BaseScreen {
    public ForgotPassword() {
        super();
        setKeepInHistory(false);
        setHasNavbar(false);
    }

    @Override
    public JPanel createUI() {
        // TODO: Implement functionality
        JPanel forgotPasswordPage = new JPanel();
        forgotPasswordPage.setBackground(ColorManager.bodyPrimary());
        forgotPasswordPage.setBorder(new ResponsiveBorder(0, 350,0,350));
        JLabel forgotPasswordLabel = new JLabel("Forgot Password");
        forgotPasswordLabel.setForeground(ColorManager.text());
        forgotPasswordLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        forgotPasswordLabel.setHorizontalTextPosition(JLabel.CENTER);
        JLabel typeYourEmaiLabel = new JLabel("Type your email and get the verification code to get the access to your account");
        typeYourEmaiLabel.setForeground(ColorManager.text());
        PlaceholderTextField forgotPasswordField = new PlaceholderTextField("Email", Color.BLACK);
        JPanel submitButtonWrapper = new JPanel();
        JPanel backButtonsPanel = new JPanel();
        backButtonsPanel.setLayout(new GridLayout(1, 2, 30, 0));
        backButtonsPanel.setBackground(ColorManager.bodyPrimary());
        JButton backToLoginButton = new JButton("To Login");
        JButton backToSignUp = new JButton("To Sign Up");
        backButtonsPanel.add(backToLoginButton);
        backButtonsPanel.add(backToSignUp);

        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.switchScreen(new Login());
            }
        });
        
        backToSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.switchScreen(new SignUp());
            }
        });

        submitButtonWrapper.setLayout(new GridLayout(2, 1, 0, 30));
        submitButtonWrapper.setBorder(new ResponsiveBorder(0,0,90,0));
        submitButtonWrapper.setBackground(ColorManager.bodyPrimary());
        JButton submitButton = new JButton("Submit");
        submitButtonWrapper.add(submitButton);
        submitButtonWrapper.add(backButtonsPanel);
        JPanel forgotPasswordCenter = new JPanel();
        forgotPasswordCenter.setBorder(new ResponsiveBorder(0,0,130,0));
        forgotPasswordCenter.setLayout(new GridLayout(2, 1, 0, 15));
        forgotPasswordCenter.setOpaque(false);
        forgotPasswordCenter.add(typeYourEmaiLabel);
        forgotPasswordCenter.add(forgotPasswordField);

        forgotPasswordPage.setLayout(new GridLayout(3, 3, 0, 0));
        forgotPasswordPage.add(forgotPasswordLabel);
        forgotPasswordPage.add(forgotPasswordCenter);
        forgotPasswordPage.add(submitButtonWrapper);
        return forgotPasswordPage;
    }
}