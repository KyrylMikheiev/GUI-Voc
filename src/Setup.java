package src;

import javax.swing.*;

import restAPI.APIClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import restAPI.APIClient;

public class Setup {
    private JPanel contentPanel;
    private JPanel bodyPanel;

    // UI components for design select
    private JPanel designSelect;
    private JLabel designLabel;
    private JButton lightMode;
    private JButton darkMode;
    private int designMode = 1;
    private JButton designNext;

    // UI components for registration 1
    private JPanel registration;
    private JLabel registrationLabel;
    private PlaceholderTextField firstName;
    private PlaceholderTextField lastName;
    private JComboBox<String> gradeLevel;
    private JButton registrationNext;

    // UI components for registration 2
    private JPanel registration2;
    private JLabel registrationLabel2;
    private PlaceholderTextField email;
    private PlaceholderTextField password;
    private PlaceholderTextField repeatPassword;
    private JButton registerButton;

    // UI components for verification
    private JPanel verification;
    private JLabel verificationLabel;
    private PlaceholderTextField verificationCode;
    private JButton verificationNext;

    // UI components for login
    private JPanel login;
    private JLabel loginLabel;
    private PlaceholderTextField loginEmail;
    private PlaceholderTextField loginPassword;
    private JLabel loginWrongLabel;
    private JButton loginButton;

    private String token = "";

    
    public void startScreen(JPanel content, Main main) {
        contentPanel = content;
        content.setLayout(new BorderLayout());
        bodyPanel = new JPanel();
        // Attempt to load session
        token = APIClient.loadSession();

        if (token.equals("LOGIN_REQUIRED")) {
            // If session couldn't be loaded, show login/register screen
            showLoginRegisterScreen();
        } else {
            // Session loaded successfully, forward to main menu
            // main.newMainMenu();
            System.out.println("Session loaded successfully. Proceed to main menu.");
        }
    }

    public void showLoginRegisterScreen() {
        // Show login/register screen on the EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                contentPanel.removeAll();
                contentPanel.revalidate();
                contentPanel.repaint();
    
                JPanel loginRegisterPanel = new JPanel();
                loginRegisterPanel.setLayout(new GridLayout(2, 1));
    
                JButton loginButton = new JButton("Login");
                loginButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        startLogin(contentPanel, null);
                    }
                });
    
                JButton registerButton = new JButton("Register");
                registerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        startRegistration(contentPanel, null);
                    }
                });
    
                loginRegisterPanel.add(loginButton);
                loginRegisterPanel.add(registerButton);
    
                JPanel bodyPanel = new JPanel();
                bodyPanel.add(loginRegisterPanel);
                contentPanel.add(bodyPanel);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });
    }
    
    public String startRegistration(JPanel content, Main main) {
        contentPanel = content;
        JPanel bodyPanel = new JPanel();
        bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout());
        bodyPanel.setBackground(Main.BodyColor);

        contentPanel.add(bodyPanel);
        
        designSelect();
        bodyPanel.revalidate();
        bodyPanel.repaint();
        contentPanel.revalidate();
        contentPanel.repaint();
        // main.newMainMenu();
        return token;
    }
    public String startLogin(JPanel content, Main main) {
        contentPanel = content;
        bodyPanel = new JPanel();
        contentPanel.add(bodyPanel);

        login();
        bodyPanel.revalidate();
        bodyPanel.repaint();
        contentPanel.revalidate();
        contentPanel.repaint();
        // main.newMainMenu();
        return token;
    }

    public void designSelect() {
        bodyPanel.removeAll();
        bodyPanel.revalidate();
        bodyPanel.repaint();

        designSelect = new JPanel();
        designLabel = new JLabel("Design Selection");
        designLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        designLabel.setForeground(Main.TextColor);
        designLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lightMode = new JButton("Light Mode");
        // Image sunImage = new ImageIcon("resources/images/sun.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        // lightMode.setIcon(new ImageIcon(sunImage));
        darkMode = new JButton("Dark Mode");
        JPanel designNextPanel = new JPanel();
        designNextPanel.setOpaque(false);
        designNextPanel.setBorder(new ResponsiveBorder(90, 550, 90, 550));
        designNextPanel.setLayout(new GridLayout());
        designNext = new JButton("Next");
        designNextPanel.add(designNext);
        
        designSelect.setOpaque(false);
        designLabel.setOpaque(false);
        lightMode.setOpaque(false);
        lightMode.setFocusPainted(false);
        lightMode.setBorder(null);

        darkMode.setOpaque(false);
        darkMode.setFocusPainted(false);
        darkMode.setBorder(null);

        designNext.setOpaque(false);
        designNext.setFocusPainted(false);
        designNext.setBorder(null);
        
        JPanel designSelect_center = new JPanel(new GridLayout(1, 2, 50, 0));
        designSelect_center.setBorder(new ResponsiveBorder(90, 400, 90, 400));
        designSelect_center.setOpaque(false);
        designSelect_center.add(lightMode);
        designSelect_center.add(darkMode);
        designSelect.setLayout(new GridLayout(3, 1));


        designNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registration();
            }
        });
        lightMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                designMode = 2;
            }
        });
        darkMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                designMode = 1;
            }
        });

        designSelect.add(designLabel, BorderLayout.NORTH);
        designSelect.add(designSelect_center, BorderLayout.CENTER);
        designSelect.add(designNextPanel, BorderLayout.SOUTH);

        bodyPanel.add(designSelect);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void registration() {
        bodyPanel.removeAll();
        bodyPanel.revalidate();
        bodyPanel.repaint();

        registration = new JPanel();
        registration.setBackground(Main.BodyColor);
        registration.setLayout(new GridLayout(3, 1));
        JPanel registration_center = new JPanel();
        registration_center.setOpaque(false);
        registration_center.setLayout(new GridLayout(3, 1, 0, 20));	
        registration_center.setBorder(new ResponsiveBorder(30, 450, 30, 450));
        registrationLabel = new JLabel("Registration");
        registrationLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        registrationLabel.setForeground(Main.TextColor);
        registrationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        firstName = new PlaceholderTextField("First Name", Color.BLACK);
        lastName = new PlaceholderTextField("Last Name", Color.BLACK);
        String[] gradeLevels = {"Freshman", "Sophomore", "Junior", "Senior"};
        gradeLevel = new JComboBox<>(gradeLevels);
        JPanel registrationNextPanel = new JPanel();
        registrationNextPanel.setLayout(new GridLayout());
        registrationNextPanel.setOpaque(false);
        registrationNextPanel.setBorder(new ResponsiveBorder(90, 550, 90, 550));
        registrationNext = new JButton("Next");
        registrationNextPanel.add(registrationNext);

        registrationNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registration2();
            }
        });

        registration_center.add(firstName);
        registration_center.add(lastName);
        registration_center.add(gradeLevel);

        registration.add(registrationLabel);
        registration.add(registration_center);
        registration.add(registrationNextPanel);

        bodyPanel.add(registration);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void registration2() {
        bodyPanel.removeAll();
        bodyPanel.revalidate();
        bodyPanel.repaint();

        registration2 = new JPanel();
        registration2.setBackground(Main.BodyColor);
        registration2.setLayout(new GridLayout(3, 0));
        registrationLabel2 = new JLabel("Registration (Cont'd)");
        registrationLabel2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        registrationLabel2.setForeground(Main.TextColor);
        registrationLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel registration_center = new JPanel();
        registration_center.setOpaque(false);
        registration_center.setLayout(new GridLayout(3, 1, 0, 20));
        registration_center.setBorder(new ResponsiveBorder(30, 450, 30, 450));

        email = new PlaceholderTextField("Email", Color.BLACK);
        password = new PlaceholderTextField("Password", Color.BLACK);
        repeatPassword = new PlaceholderTextField("Repeat Password", Color.BLACK);

        JPanel registerButtonPanel = new JPanel();
        registerButtonPanel.setLayout(new GridLayout());
        registerButtonPanel.setOpaque(false);
        registerButtonPanel.setBorder(new ResponsiveBorder(90, 550, 90, 550));
        registerButton = new JButton("Register");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (password.getText().equals(repeatPassword.getText())) {
                    registerUser();
                    verification();
                }
                else {
                    registration2.add(new JLabel("Passwords do not match!"));
                }
            }
        });

        registration_center.add(email);
        registration_center.add(password);
        registration_center.add(repeatPassword);

        registerButtonPanel.add(registerButton);

        registration2.add(registrationLabel2);
        registration2.add(registration_center);
        registration2.add(registerButtonPanel);

        bodyPanel.add(registration2);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    public void verification() {
        bodyPanel.removeAll();
        bodyPanel.revalidate();
        bodyPanel.repaint();

        verification = new JPanel();
        verification.setBackground(Main.BodyColor);
        verification.setLayout(new GridLayout(3, 0));

        verificationLabel = new JLabel("Verification");
        verificationLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        verificationLabel.setForeground(Main.TextColor);
        verificationLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel verificationCodeLabel = new JLabel("Geben Sie den an Ihre E-Mail gesendeten Code ein");
        verificationCodeLabel.setForeground(Main.TextColor);
        verificationCodeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        
        verificationCode = new PlaceholderTextField("Verification Code", Color.BLACK);

        JPanel verificationCodePanel = new JPanel();
        verificationCodePanel.setOpaque(false);
        verificationCodePanel.setLayout(new GridLayout(2, 1, 0, 10));
        verificationCodePanel.setBorder(new ResponsiveBorder(20, 450, 90, 450));
        
        

        JPanel verificationNextPanel = new JPanel();
        verificationNextPanel.setLayout(new GridLayout());
        verificationNextPanel.setOpaque(false);
        verificationNextPanel.setBorder(new ResponsiveBorder(90, 550, 90, 550));
        verificationNext = new JButton("Next");

        verificationNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyUser();
            }
        });

verificationCodePanel.add(verificationCodeLabel);
        verificationCodePanel.add(verificationCode);
        verificationNextPanel.add(verificationNext);

        verification.add(verificationLabel);
        verification.add(verificationCodePanel);
        verification.add(verificationNextPanel);

        bodyPanel.add(verification);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void login() {
        bodyPanel.removeAll();
        bodyPanel.revalidate();
        bodyPanel.repaint();

        login = new JPanel();
        loginLabel = new JLabel("Login");
        loginEmail = new PlaceholderTextField("Email", Main.TextColor);
        loginPassword = new PlaceholderTextField("Password", Main.TextColor);
        loginPassword.setEchoChar('*'); // Mask the password input
        loginWrongLabel = new JLabel("Invalid email or password");
        loginWrongLabel.setVisible(false); // Initially hidden
        loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform login action using apiClient.login(email, password)
                String email = loginEmail.getText();
                String password = new String(loginPassword.getText());

                String sesssion_token = APIClient.login(email, password);
                token = sesssion_token;
                if (!sesssion_token.isEmpty()) {
                    // Login successful, navigate to next screen or perform actions
                    // For now, let's just print a message
                    System.out.println("Login successful. Token: " + sesssion_token);
                } else {
                    // Login failed, display an error message or handle accordingly
                    loginWrongLabel.setVisible(true);
                }
            }
        });

        login.add(loginLabel);
        login.add(loginEmail);
        login.add(loginPassword);
        login.add(loginWrongLabel);
        login.add(loginButton);

        bodyPanel.add(login);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    public void verifyUser() {
        // Verify the user using the verification code
        String code = verificationCode.getText();
        token = APIClient.verifyAccount(email.getText(), code);
    }


    public void registerUser() {
        String userFirstName = firstName.getText();
        String userLastName = lastName.getText();
        String userEmail = email.getText();
        String userPassword = password.getText();
        int userModePreference = designMode; // You need to set this value based on the user's mode preference
        int userClass = gradeLevel.getSelectedIndex() + 1; // Assuming the index corresponds to class (e.g., Freshman = 1, Sophomore = 2, etc.)

        boolean accountCreated = APIClient.createUserAccount(userFirstName, userLastName, userEmail, userPassword, userModePreference, userClass);
        if (accountCreated) {
            System.out.println("Account created successfully.");
            // Optionally, you can proceed with the verification process here
            // For now, let's just print a message
            System.out.println("Please verify your account.");
        } else {
            System.out.println("Account creation failed.");
        }
    }
}
