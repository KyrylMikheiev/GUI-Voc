package src;

import javax.swing.*;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import restAPI.APIClient;

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
            main.loggedIn();
        }
    }

    public void showStartPage() {
        // newUI();

        login = new JPanel();
        login.setBackground(Main.bodyColorDarkMode);
        login.setLayout(new GridLayout(3, 0));
        loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel login_center = new JPanel();
        login_center.setOpaque(false);
        login_center.setLayout(new GridLayout(3, 1, 0, 20));
        login_center.setBorder(new ResponsiveBorder(0, 450, 30, 450));

        loginEmail = new PlaceholderTextField("E-Mail", Color.BLACK);
        loginEmail.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        loginPassword = new PlaceholderPasswordField("Passwort", Color.BLACK);
        loginPassword.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));

        JPanel loginButtonPanel = new JPanel();
        loginButtonPanel.setLayout(new GridLayout(2, 1, 0, 30));
        loginButtonPanel.setOpaque(false);
        loginButtonPanel.setBorder(new ResponsiveBorder(30, 450, 80, 450));
        loginButton = new JButton("Login");
        // loginButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        loginWrongLabel = new JLabel("Ungültige E-Mail oder Passwort");
        loginWrongLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        loginWrongLabel.setForeground(Color.WHITE);
        loginWrongLabel.setVisible(false); // Initially hidden
        JPanel forgotPasswordSignUpPanel =  new JPanel();
        forgotPasswordSignUpPanel.setBackground(Main.bodyColorDarkMode);
        forgotPasswordSignUpPanel.setLayout(new GridLayout(1, 2, 30, 0));
        JButton forgotPasswordButton = new JButton("Passwort vergessen");
        JButton signUpButton = new JButton("Ich habe keinen Account");
        // forgotPasswordButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        // signUpButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform login action using apiClient.login(email, password)
                String email = loginEmail.getText();
                String password = new String(loginPassword.getPassword());
                
                String sesssion_token = APIClient.login(email, password);
                token = sesssion_token;
                if (!sesssion_token.isEmpty() && sesssion_token != "VERIFY") {
                    // Login successful, navigate to next screen or perform actions
                    // For now, let's just print a message
                    System.out.println("Login successful.");
                    main.loggedIn();
                } else if (sesssion_token == "") {
                    // Login failed, display an error message or handle accordingly
                    loginWrongLabel.setVisible(true);
                }
                else if (sesssion_token == "VERIFY") {
                    verification(loginEmail.getText());
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

    /*public void showLoginRegisterScreen() {
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
        JButton registerButton = new JButton("Registration");
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
    }*/
    
    public void forgotPasswordPage() {
        newUI();

        JPanel forgotPasswordPage = new JPanel();
        forgotPasswordPage.setBackground(Main.bodyColorDarkMode);
        forgotPasswordPage.setBorder(new ResponsiveBorder(0, 350,0,350));
        JLabel forgotPasswordLabel = new JLabel("Forgot Password");
        forgotPasswordLabel.setForeground(Main.textColorDarkMode);
        forgotPasswordLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        forgotPasswordLabel.setHorizontalTextPosition(JLabel.CENTER);
        JLabel typeYourEmaiLabel = new JLabel("Type your email and get the verification code to get the access to your account");
        typeYourEmaiLabel.setForeground(Main.textColorDarkMode);
        PlaceholderTextField forgotPasswordField = new PlaceholderTextField("Email", Color.BLACK);
        JPanel submitButtonWrapper = new JPanel();
        JPanel backButtonsPanel = new JPanel();
        backButtonsPanel.setLayout(new GridLayout(1, 2, 30, 0));
        backButtonsPanel.setBackground(Main.bodyColorDarkMode);
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
        submitButtonWrapper.setBackground(Main.bodyColorDarkMode);
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
        registration.setBackground(Main.bodyColorDarkMode);
        registration.setLayout(new GridLayout(3, 1));
        JPanel registration_center = new JPanel();
        registration_center.setOpaque(false);
        registration_center.setLayout(new GridLayout(3, 1, 0, 20));	
        registration_center.setBorder(new ResponsiveBorder(30, 450, 30, 450));
        registrationLabel = new JLabel("Registration");
        registrationLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        registrationLabel.setForeground(Main.textColorDarkMode);
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
        registration2.setBackground(Main.bodyColorDarkMode);
        registration2.setLayout(new GridLayout(3, 0));
        registrationLabel2 = new JLabel("Registration");
        registrationLabel2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        registrationLabel2.setForeground(Main.textColorDarkMode);
        registrationLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel registration_center = new JPanel();
        registration_center.setOpaque(false);
        registration_center.setLayout(new GridLayout(3, 1, 0, 20));
        registration_center.setBorder(new ResponsiveBorder(30, 450, 30, 450));

        email = new PlaceholderTextField("Email", Color.BLACK);
        password = new PlaceholderPasswordField("Passwort", Color.BLACK);
        repeatPassword = new PlaceholderPasswordField("Passwort wiederholen", Color.BLACK);
        email.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        password.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        repeatPassword.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        JPanel registerButtonPanel = new JPanel();
        registerButtonPanel.setLayout(new GridLayout(1, 2, 30, 0));	
        registerButtonPanel.setOpaque(false);
        registerButtonPanel.setBorder(new ResponsiveBorder(90, 500, 90, 500));
        registerButton = new JButton("Registrieren");
        JButton backButton = new JButton("Zurück");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.repaint();
                registration();
                contentPanel.repaint();
                contentPanel.revalidate();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Registering...");
                String p1 = String.valueOf(password.getPassword());
                String p2 = String.valueOf(repeatPassword.getPassword());
                if (p1.equals(p2)) {
                    System.out.println("Passwords match");
                    if (registerUser()) {
                        verification(email.getText());
                    }
                    else {
                        registration2.removeAll();

                        registrationLabel2 = new JLabel("Registration");
                        registrationLabel2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                        registrationLabel2.setForeground(Main.textColorDarkMode);
                        registrationLabel2.setHorizontalAlignment(SwingConstants.CENTER);

                        JPanel forgotPasswordSignUpPanel =  new JPanel();
                        forgotPasswordSignUpPanel.setBorder(new ResponsiveBorder(0,50,160,70));
                        forgotPasswordSignUpPanel.setBackground(Main.bodyColorDarkMode);
                        forgotPasswordSignUpPanel.setLayout(new GridLayout(1, 2, 30, 0));
                        JButton backToLogin = new JButton("Zurück zum Login");
                        JButton signUpButton = new JButton("Ich habe keinen Account");
                        backToLogin.addActionListener(new ActionListener() {
                            @Override 
                            public void actionPerformed(ActionEvent e) {
                                contentPanel.removeAll();
                                contentPanel.repaint();
                                showStartPage();
                                contentPanel.repaint();
                                contentPanel.revalidate();
                            }
                        });
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
                        forgotPasswordSignUpPanel.add(backToLogin);
                        forgotPasswordSignUpPanel.add(signUpButton);
                        JLabel userExistsJLabel = new JLabel("Bereits angemeldete oder ungültige E-Mail!");
                        userExistsJLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                        userExistsJLabel.setForeground(Main.textColorDarkMode);

                        registration2.setBorder(new ResponsiveBorder(0,350,0,350));
                        registration2.add(registrationLabel2);
                        registration2.add(userExistsJLabel);
                        registration2.add(forgotPasswordSignUpPanel);
                    }
                }
                else {
                    JPanel forgotPasswordSignUpPanel =  new JPanel();
                    forgotPasswordSignUpPanel.setBackground(Color.BLACK);
                    forgotPasswordSignUpPanel.setLayout(new GridLayout(1, 2, 30, 0));
                    JButton backToLogin = new JButton("Zurücl zum Login");
                    JButton signUpButton = new JButton("Ich habe keinen Account");
                    forgotPasswordSignUpPanel.add(backToLogin);
                    forgotPasswordSignUpPanel.add(signUpButton);
                    registration2.add(new JLabel("Passwörter stimmen nicht überein!"));
                    registration2.add(forgotPasswordSignUpPanel);
                }
                registration2.revalidate();
                registration2.repaint();
            }
        });

        registration_center.add(email);
        registration_center.add(password);
        registration_center.add(repeatPassword);

        email.requestFocusInWindow();

        registerButtonPanel.add(backButton);
        registerButtonPanel.add(registerButton);

        registration2.add(registrationLabel2);
        registration2.add(registration_center);
        registration2.add(registerButtonPanel);

        contentPanel.add(registration2);
        repaint();
    }
    public void verification(String email) {
        newUI();

        verification = new JPanel();
        verification.setBackground(Main.bodyColorDarkMode);
        verification.setLayout(new GridLayout(3, 0));

        verificationLabel = new JLabel("Account-Verifizierung");
        verificationLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        verificationLabel.setForeground(Main.textColorDarkMode);
        verificationLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel verificationCodePanel = new JPanel();
        verificationCodePanel.setOpaque(false);
        verificationCodePanel.setLayout(new GridLayout());
        verificationCodePanel.setBorder(new ResponsiveBorder(90, 450, 90, 450));
        verificationCode = new PlaceholderTextField("Verifizierungs-Code", Color.BLACK);

        JPanel verificationNextPanel = new JPanel();
        verificationNextPanel.setLayout(new GridLayout());
        verificationNextPanel.setOpaque(false);
        verificationNextPanel.setBorder(new ResponsiveBorder(90, 550, 90, 550));
        verificationNext = new JButton("Next");

        verificationNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (verifyUser(email)) {
                    main.loggedIn();
                } else {
                    verification.removeAll();

                    verification.setLayout(new GridLayout(4, 1));
                    verification.setBorder(new ResponsiveBorder(0, 350, 0, 350));
                    verificationLabel = new JLabel("Verifizierung");
                    verificationLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                    verificationLabel.setForeground(Main.textColorDarkMode);
                    verificationLabel.setHorizontalAlignment(SwingConstants.CENTER);

                    JPanel forgotPasswordSignUpPanel =  new JPanel();
                    forgotPasswordSignUpPanel.setBorder(new ResponsiveBorder(40,50,80,70));
                    forgotPasswordSignUpPanel.setBackground(Main.bodyColorDarkMode);
                    forgotPasswordSignUpPanel.setLayout(new GridLayout(1, 2, 30, 0));
                    JButton backToLogin = new JButton("Zurück zum Login");
                    JButton signUpButton = new JButton("Ich habe keinen Account");
                    backToLogin.addActionListener(new ActionListener() {
                        @Override 
                        public void actionPerformed(ActionEvent e) {
                            contentPanel.removeAll();
                            contentPanel.repaint();
                            showStartPage();
                            contentPanel.repaint();
                            contentPanel.revalidate();
                        }
                    });
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
                    forgotPasswordSignUpPanel.add(backToLogin);
                    forgotPasswordSignUpPanel.add(signUpButton);
                    JLabel userExistsJLabel = new JLabel("Falscher Verifizierungs-Code!");
                    JPanel resendButtonPanel = new JPanel();
                    resendButtonPanel.setLayout(new GridLayout());
                    resendButtonPanel.setBackground(Main.bodyColorDarkMode);
                    resendButtonPanel.setBorder(new ResponsiveBorder(50, 100, 60, 100));
                    JButton resendButton = new JButton("Erneut senden");
                    resendButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            verification(email);
                        }
                    });
                    resendButtonPanel.add(resendButton);
                    userExistsJLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                    userExistsJLabel.setForeground(Main.textColorDarkMode);

                    verification.setBorder(new ResponsiveBorder(0,350,0,350));
                    verification.add(verificationLabel);
                    verification.add(userExistsJLabel);
                    verification.add(resendButtonPanel);
                    verification.add(forgotPasswordSignUpPanel);
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
                    main.loggedIn();
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
            contentPanel.removeAll();
        }
        catch (Exception e) {
            
        }
        contentPanel.setBackground(Main.BodyColor);
    }
    private void repaint() {
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public boolean verifyUser(String email) {
        // Verify the user using the verification code
        String code = verificationCode.getText();
        token = APIClient.verifyAccount(email, code);
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
            // Optionally, you can proceed with the verification process here
            // For now, let's just print a message
            return true;
        } else {
            System.out.println("Account creation failed.");
            return false;
        }
    }
}
