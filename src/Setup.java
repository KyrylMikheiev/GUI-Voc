package src;

import javax.swing.*;

import restAPI.APIClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
    private PlaceholderPasswordField password;
    private PlaceholderPasswordField repeatPassword;
    // private PlaceholderPasswordField password;
    // private PlaceholderPasswordField repeatPassword;
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
    private PlaceholderPasswordField loginPassword;
    private JLabel loginWrongLabel;
    private JButton loginButton;

    private String token = "";
    private Main main;

    public Setup(JPanel content, Main main) {
        startScreen(content, main);
    }
    
    public void startScreen(JPanel content, Main main) {
        main.getNavBar().deactivate();
        contentPanel = content;
        this.main = main;
        content.setLayout(new BorderLayout());
        bodyPanel = new JPanel();
        bodyPanel.setBackground(Main.BodyColor);
        //bodyPanel.setLayout(new BorderLayout());
        // Attempt to load session
        token = APIClient.loadSession();

        if (token.equals("LOGIN_REQUIRED")) {
            // If session couldn't be loaded, show login/register screen
            showStartPage();
            // login();
        } else {
            // Session loaded successfully, forward to main menu
            System.out.println("Session loaded successfully. Proceed to main menu.");
            main.getNavBar().activate();
            main.newMainMenu();
        }
    }

    public void showStartPage() {
        // newUI();

        login = new JPanel();
        login.setBackground(Color.BLACK);
        login.setLayout(new GridLayout(3, 0));
        loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel login_center = new JPanel();
        login_center.setOpaque(false);
        login_center.setLayout(new GridLayout(3, 1, 0, 20));
        login_center.setBorder(new ResponsiveBorder(0, 450, 30, 450));

        loginEmail = new PlaceholderTextField("Email", Color.BLACK);
        loginEmail.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        loginPassword = new PlaceholderPasswordField("Password", Color.BLACK);
        loginPassword.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));

        JPanel loginButtonPanel = new JPanel();
        loginButtonPanel.setLayout(new GridLayout(2, 1, 0, 30));
        loginButtonPanel.setOpaque(false);
        loginButtonPanel.setBorder(new ResponsiveBorder(30, 450, 80, 450));
        loginButton = new JButton("Login");
        loginWrongLabel = new JLabel("Invalid email or password");
        loginWrongLabel.setForeground(Color.WHITE);
        loginWrongLabel.setVisible(false); // Initially hidden
        JPanel forgotPasswordSignUpPanel =  new JPanel();
        forgotPasswordSignUpPanel.setBackground(Color.BLACK);
        forgotPasswordSignUpPanel.setLayout(new GridLayout(1, 2, 30, 0));
        JButton forgotPasswordButton = new JButton("Forgot Password");
        JButton signUpButton = new JButton("I don't have an account");
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform login action using apiClient.login(email, password)
                String email = loginEmail.getText();
                String password = new String(loginPassword.getPassword());
                
                String sesssion_token = APIClient.login(email, password);
                token = sesssion_token;
                if (!sesssion_token.isEmpty()) {
                    // Login successful, navigate to next screen or perform actions
                    // For now, let's just print a message
                    System.out.println("Login successful.");
                    main.getNavBar().activate();
                    main.newMainMenu();
                } else {
                    // Login failed, display an error message or handle accordingly
                    loginWrongLabel.setVisible(true);
                }

                
            }
        });

        /* Redirecting to registration */
        
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("sign up button was clicked");
                contentPanel.removeAll();  
                registration();
                contentPanel.repaint();
                contentPanel.revalidate();
            }
        });

        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                forgotPasswordPage();
                contentPanel.repaint();
                contentPanel.revalidate();
            }
        });
        forgotPasswordSignUpPanel.add(signUpButton);
        forgotPasswordSignUpPanel.add(forgotPasswordButton);
        
        login_center.add(loginEmail);
        login_center.add(loginPassword);
        login_center.add(loginWrongLabel);
        
        loginEmail.requestFocusInWindow();
        
        loginButtonPanel.add(loginButton);
        loginButtonPanel.add(forgotPasswordSignUpPanel);

        login.add(loginLabel);
        login.add(login_center);
        login.add(loginButtonPanel);

        contentPanel.add(login);
        contentPanel.repaint();
    }

    public void showLoginRegisterScreen() {
        // Show login/register screen on the EDT

        JPanel loginRegisterPanel = new JPanel();
        loginRegisterPanel.setLayout(new GridLayout(2, 1));

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startLogin(contentPanel, main);
            }
        });
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startRegistration(contentPanel, main);
            }
        });

        loginRegisterPanel.add(loginButton);
        loginRegisterPanel.add(registerButton);

        //JPanel bodyPanel = new JPanel();
        bodyPanel.add(loginRegisterPanel);
        contentPanel.add(bodyPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    public void forgotPasswordPage() {
        newUI();

        JPanel forgotPasswordPage = new JPanel();
        forgotPasswordPage.setBorder(new ResponsiveBorder(0, 350,0,350));
        JLabel forgotPasswordLabel = new JLabel("Forgot Password");
        forgotPasswordLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        forgotPasswordLabel.setHorizontalTextPosition(JLabel.CENTER);
        JLabel typeYourEmaiLabel = new JLabel("Type your email and get the verification code to get the access to your account");
        PlaceholderTextField forgotPasswordField = new PlaceholderTextField("Email", Color.BLACK);
        JPanel submitButtonWrapper = new JPanel();
        JPanel backButtonsPanel = new JPanel();
        backButtonsPanel.setLayout(new GridLayout(1, 2, 30, 0));
        JButton backToLoginButton = new JButton("To Login");
        JButton backToSignUp = new JButton("To Sign Up");
        backButtonsPanel.add(backToLoginButton);
        backButtonsPanel.add(backToSignUp);

        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                showStartPage();
                contentPanel.repaint();
                contentPanel.revalidate();
            }
        });
        
        backToSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                registration();
                contentPanel.repaint();
                contentPanel.revalidate();
            }
        });

        submitButtonWrapper.setLayout(new GridLayout(2, 1, 0, 30));
        submitButtonWrapper.setBorder(new ResponsiveBorder(0,0,90,0));
        JButton submitButton = new JButton("Submit");
        submitButtonWrapper.add(submitButton);
        submitButtonWrapper.add(backButtonsPanel);
        JPanel forgotPasswordCenter = new JPanel();
        forgotPasswordCenter.setBorder(new ResponsiveBorder(0,0,130,0));
        forgotPasswordCenter.setLayout(new GridLayout(2, 1, 0, 15));
        forgotPasswordCenter.add(typeYourEmaiLabel);
        forgotPasswordCenter.add(forgotPasswordField);

        forgotPasswordPage.setLayout(new GridLayout(3, 3, 0, 0));
        forgotPasswordPage.add(forgotPasswordLabel);
        forgotPasswordPage.add(forgotPasswordCenter);
        forgotPasswordPage.add(submitButtonWrapper);

        contentPanel.add(forgotPasswordPage);
        repaint();
    }
    
    public void startRegistration(JPanel content, Main main) {

        bodyPanel.setLayout(new GridLayout());
        bodyPanel.setBackground(Main.BodyColor);

        contentPanel.add(bodyPanel);
        
        designSelect();
    }
    public void startLogin(JPanel content, Main main) {

        contentPanel.add(bodyPanel);

        login();
    }

    public void designSelect() {
        newUI();

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
                if (main.getDarkmodeState()) {
                    main.toggleDarkmode();
                }
                bodyPanel.setBackground(Main.BodyColor);
            }
        });
        darkMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                designMode = 1;
                if (!main.getDarkmodeState()) {
                    main.toggleDarkmode();
                }
                bodyPanel.setBackground(Main.BodyColor);
            }
        });


        designSelect.add(designLabel, BorderLayout.NORTH);
        designSelect.add(designSelect_center, BorderLayout.CENTER);
        designSelect.add(designNextPanel, BorderLayout.SOUTH);

        bodyPanel.add(designSelect);
        repaint();
    }

    public void registration() {
        newUI();

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
        firstName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        lastName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        String[] gradeLevels = {"Freshman", "Sophomore", "Junior", "Senior"};
        gradeLevel = new JComboBox<>(gradeLevels);
        JPanel registrationNextPanel = new JPanel();
        registrationNextPanel.setLayout(new GridLayout(2, 1, 0, 20));
        registrationNextPanel.setOpaque(false);
        registrationNextPanel.setBorder(new ResponsiveBorder(50, 500, 85, 500));
        registrationNext = new JButton("Next");
        
        JPanel backButtonsPanel = new JPanel();
        backButtonsPanel.setBackground(Main.BodyColor);
        backButtonsPanel.setLayout(new GridLayout(1, 2, 30, 0));
        JButton backToLoginButton = new JButton("To Login");
        
        registrationNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.repaint();
                registration2();
                contentPanel.repaint();
                contentPanel.revalidate();
            }
        });

        backToLoginButton.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.repaint();
                showStartPage();
                contentPanel.repaint();
                contentPanel.revalidate();
            }
        });
        registrationNextPanel.add(registrationNext);
        backButtonsPanel.add(backToLoginButton);

        registrationNextPanel.add(backButtonsPanel);

        registration_center.add(firstName);
        registration_center.add(lastName);
        registration_center.add(gradeLevel);

        registration.add(registrationLabel);
        registration.add(registration_center);
        registration.add(registrationNextPanel);

        firstName.requestFocusInWindow();

        // bodyPanel.add(registration);
        contentPanel.add(registration);
        repaint();
    }

    public void registration2() {
        newUI();

        registration2 = new JPanel();
        registration2.setBackground(Main.BodyColor);
        registration2.setLayout(new GridLayout(3, 0));
        registrationLabel2 = new JLabel("Registration");
        registrationLabel2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        registrationLabel2.setForeground(Main.TextColor);
        registrationLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel registration_center = new JPanel();
        registration_center.setOpaque(false);
        registration_center.setLayout(new GridLayout(3, 1, 0, 20));
        registration_center.setBorder(new ResponsiveBorder(30, 450, 30, 450));

        email = new PlaceholderTextField("Email", Color.BLACK);
        password = new PlaceholderPasswordField("Password", Color.BLACK);
        repeatPassword = new PlaceholderPasswordField("Repeat Password", Color.BLACK);
        email.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        password.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        repeatPassword.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        JPanel registerButtonPanel = new JPanel();
        registerButtonPanel.setLayout(new GridLayout());
        registerButtonPanel.setOpaque(false);
        registerButtonPanel.setBorder(new ResponsiveBorder(90, 550, 90, 550));
        registerButton = new JButton("Register");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Registering...");
                String p1 = String.valueOf(password.getPassword());
                String p2 = String.valueOf(repeatPassword.getPassword());
                System.out.println(p1 + " " + p2);
                if (p1.equals(p2)) {
                    System.out.println("Passwords match");
                    if (registerUser()) {
                        contentPanel.removeAll();  
                        verification();
                        contentPanel.repaint();
                        contentPanel.revalidate();
                    }
                    else {
                        registration2.add(new JLabel("User with that email already exists!"));
                    }
                }
                else {
                    registration2.add(new JLabel("Passwords do not match!"));
                }
                registration2.revalidate();
                registration2.repaint();
            }
        });

        registration_center.add(email);
        registration_center.add(password);
        registration_center.add(repeatPassword);

        email.requestFocusInWindow();

        registerButtonPanel.add(registerButton);

        registration2.add(registrationLabel2);
        registration2.add(registration_center);
        registration2.add(registerButtonPanel);

        contentPanel.add(registration2);
        repaint();
    }
    public void verification() {
        newUI();

        verification = new JPanel();
        verification.setBackground(Main.BodyColor);
        verification.setLayout(new GridLayout(3, 0));

        verificationLabel = new JLabel("Verification");
        verificationLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        verificationLabel.setForeground(Main.TextColor);
        verificationLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel verificationCodePanel = new JPanel();
        verificationCodePanel.setOpaque(false);
        verificationCodePanel.setLayout(new GridLayout());
        verificationCodePanel.setBorder(new ResponsiveBorder(90, 450, 90, 450));
        verificationCode = new PlaceholderTextField("Verification Code", Color.BLACK);

        JPanel verificationNextPanel = new JPanel();
        verificationNextPanel.setLayout(new GridLayout());
        verificationNextPanel.setOpaque(false);
        verificationNextPanel.setBorder(new ResponsiveBorder(90, 550, 90, 550));
        verificationNext = new JButton("Next");

        verificationNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (verifyUser()) {
                    main.getNavBar().activate();
                    main.newMainMenu();
                } else {
                    verification.add(new JLabel("Invalid verification code"));
                }
            }
        });

        verificationCodePanel.add(verificationCode);
        verificationNextPanel.add(verificationNext);

        verificationCode.requestFocusInWindow();

        verification.add(verificationLabel);
        verification.add(verificationCodePanel);
        verification.add(verificationNextPanel);

        contentPanel.add(verification);
        repaint();
    }

    public void login() {
        newUI();

        login = new JPanel();
        login.setBackground(Main.BodyColor);
        login.setLayout(new GridLayout(0, 1));

        loginLabel = new JLabel("Login");
        loginLabel.setForeground(Main.TextColor);
        loginEmail = new PlaceholderTextField("Email", Main.TextColor);
        loginPassword = new PlaceholderPasswordField("Password", Main.TextColor);
        loginWrongLabel = new JLabel("Invalid email or password");
        loginWrongLabel.setVisible(false); // Initially hidden
        loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform login action using apiClient.login(email, password)
                String email = loginEmail.getText();
                String password = new String(loginPassword.getPassword());

                String sesssion_token = APIClient.login(email, password);
                token = sesssion_token;
                if (!sesssion_token.isEmpty()) {
                    // Login successful, navigate to next screen or perform actions
                    // For now, let's just print a message
                    System.out.println("Login successful.");
                    main.getNavBar().activate();
                    main.newMainMenu();
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

        loginEmail.requestFocusInWindow();

        bodyPanel.add(login);
        // contentPanel.add(bodyPanel);
        repaint();
    }

    private void newUI() {
        try {
            bodyPanel.removeAll();
        }
        catch (Exception e) {
            
        }
        bodyPanel.setBackground(Main.BodyColor);
    }
    private void repaint() {
        bodyPanel.revalidate();
        bodyPanel.repaint();
    }

    public boolean verifyUser() {
        // Verify the user using the verification code
        String code = verificationCode.getText();
        token = APIClient.verifyAccount(email.getText(), code);
        return token != null && token != "";
    }


    public boolean registerUser() {
        System.out.println("Registering user...");
        String userFirstName = firstName.getText();
        String userLastName = lastName.getText();
        String userEmail = email.getText();
        String userPassword = String.valueOf(password.getPassword());
        int userModePreference = designMode; // You need to set this value based on the user's mode preference
        int userClass = gradeLevel.getSelectedIndex() + 1; // Assuming the index corresponds to class (e.g., Freshman = 1, Sophomore = 2, etc.)

        boolean accountCreated = APIClient.createUserAccount(userFirstName, userLastName, userEmail, userPassword, userModePreference, userClass);
        if (accountCreated) {
            System.out.println("Account created successfully.");
            //main.getNavBar().activate();
            // Optionally, you can proceed with the verification process here
            // For now, let's just print a message
            return true;
        } else {
            System.out.println("Account creation failed.");
            return false;
        }
    }
}
