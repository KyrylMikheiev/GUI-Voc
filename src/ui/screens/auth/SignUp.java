package src.ui.screens.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import src.App;
import src.api.APIClient;
import src.ui.ColorManager;
import src.ui.helper.*;
import src.ui.screens._BaseScreen;
import src.ui.screens.auth.*;

public class SignUp extends _BaseScreen {
    private int signupStage;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPassword;
    private int userModePreference = 2;
    private int userClass;

    public SignUp() {
        super(false);
        this.signupStage = 1;
        this.userClass = 1;
        setKeepInHistory(false);
        setHasNavbar(false);
        this.rebuildUI();
    }

    @Override
    public JPanel createUI() {
        if (signupStage == 1) {
            JPanel registration = new JPanel();
            registration.setBackground(ColorManager.bodyPrimary());
            registration.setLayout(new GridLayout(3, 1));
            JPanel registration_center = new JPanel();
            registration_center.setOpaque(false);
            registration_center.setLayout(new GridLayout(4, 1, 0, 20));	
            registration_center.setBorder(new ResponsiveBorder(30, 450, 30, 450));
            JLabel registrationLabel = new JLabel("Registration");
            registrationLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
            registrationLabel.setForeground(ColorManager.text());
            registrationLabel.setHorizontalAlignment(SwingConstants.CENTER);
            PlaceholderTextField firstName = new PlaceholderTextField("First Name", Color.BLACK);
            firstName.setText(userFirstName);
            PlaceholderTextField lastName = new PlaceholderTextField("Last Name", Color.BLACK);
            lastName.setText(userLastName);
            firstName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
            lastName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
            String[] gradeLevels = {"Freshman", "Sophomore", "Junior", "Senior"};
            JComboBox<String> gradeLevel = new JComboBox<>(gradeLevels);
            gradeLevel.setSelectedIndex(userClass-1);
            JPanel registrationNextPanel = new JPanel();
            registrationNextPanel.setLayout(new GridLayout(2, 1, 0, 20));
            registrationNextPanel.setOpaque(false);
            registrationNextPanel.setBorder(new ResponsiveBorder(50, 500, 85, 500));
            JButton registrationNext = new JButton("Next");
            
            JPanel backButtonsPanel = new JPanel();
            backButtonsPanel.setBackground(ColorManager.bodyPrimary());
            backButtonsPanel.setLayout(new GridLayout(1, 2, 30, 0));
            JButton backToLoginButton = new JButton("To Login");

            JLabel missingData = new JLabel("Vor- oder Nachname fehlen!");
            missingData.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
            missingData.setForeground(ColorManager.text());
            missingData.setVisible(false); // Initially hidden
            
            registrationNext.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    userFirstName = firstName.getText();
                    userLastName = lastName.getText();
                    userClass = gradeLevel.getSelectedIndex() +1; // Assuming the index corresponds to class (e.g., Freshman = 1, Sophomore = 2, etc.)
                    if (userFirstName.isEmpty() || userLastName.isEmpty()) {
                        missingData.setVisible(true);
                    } else {
                        signupStage = 2;
                        rebuildUI();
                    }
                }
            });

            backToLoginButton.addActionListener(new ActionListener() {
                @Override 
                public void actionPerformed(ActionEvent e) {
                    App.switchScreen(new Login());
                }
            });
            registrationNextPanel.add(registrationNext);
            backButtonsPanel.add(backToLoginButton);

            registrationNextPanel.add(backButtonsPanel);

            registration_center.add(firstName);
            registration_center.add(lastName);
            registration_center.add(gradeLevel);
            registration_center.add(missingData);

            registration.add(registrationLabel);
            registration.add(registration_center);
            registration.add(registrationNextPanel);

            firstName.requestFocusInWindow();
            return registration;
        } else {
            JPanel registration2 = new JPanel();
            registration2.setBackground(ColorManager.bodyPrimary());
            registration2.setLayout(new GridLayout(3, 0));
            JLabel registrationLabel2 = new JLabel("Registration");
            registrationLabel2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
            registrationLabel2.setForeground(ColorManager.text());
            registrationLabel2.setHorizontalAlignment(SwingConstants.CENTER);

            JPanel registration_center = new JPanel();
            registration_center.setOpaque(false);
            registration_center.setLayout(new GridLayout(4, 1, 0, 20));
            registration_center.setBorder(new ResponsiveBorder(30, 450, 30, 450));

            PlaceholderTextField email = new PlaceholderTextField("Email", Color.BLACK);
            PlaceholderPasswordField password = new PlaceholderPasswordField("Passwort", Color.BLACK);
            PlaceholderPasswordField repeatPassword = new PlaceholderPasswordField("Passwort wiederholen", Color.BLACK);
            email.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
            password.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
            repeatPassword.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

            JPanel registerButtonPanel = new JPanel();
            registerButtonPanel.setLayout(new GridLayout(1, 2, 30, 0));	
            registerButtonPanel.setOpaque(false);
            registerButtonPanel.setBorder(new ResponsiveBorder(90, 500, 90, 500));
            JButton registerButton = new JButton("Registrieren");
            JButton backButton = new JButton("Zurück");

            JLabel errorDisplay = new JLabel("Ein Fehler ist aufgetreten!");
            errorDisplay.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
            errorDisplay.setForeground(ColorManager.text());
            errorDisplay.setVisible(false); // Initially hidden

            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    signupStage = 1;
                    rebuildUI();
                }
            });

            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Registering...");
                    String p1 = String.valueOf(password.getPassword());
                    String p2 = String.valueOf(repeatPassword.getPassword());
                    if (p1.equals(p2)) {
                        userEmail = email.getText();
                        userPassword = p1;
                        System.out.println("Passwords match");
                        System.out.println("Registering user...");
                        boolean accountCreated = APIClient.createUserAccount(userFirstName, userLastName, userEmail, userPassword, userModePreference, userClass);
                        if (accountCreated) {
                            System.out.println("Account created successfully.");
                            App.switchScreen(new Verification(userEmail));
                        } else {
                            System.out.println("Account creation failed.");
                            errorDisplay.setText("Bereits angemeldete oder ungültige E-Mail!");
                            errorDisplay.setVisible(true);
                        }
                    } else {
                        errorDisplay.setText("Passwörter stimmen nicht überein!");
                        errorDisplay.setVisible(true);
                    }
                }
            });

            registration_center.add(email);
            registration_center.add(password);
            registration_center.add(repeatPassword);
            registration_center.add(errorDisplay);            

            email.requestFocusInWindow();

            registerButtonPanel.add(backButton);
            registerButtonPanel.add(registerButton);

            registration2.add(registrationLabel2);
            registration2.add(registration_center);
            registration2.add(registerButtonPanel);

            return registration2;
        }
    }
}