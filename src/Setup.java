package src;

import javax.swing.*;

import restAPI.APIClient;

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

    public String startRegistration(JPanel content, Main main) {
        contentPanel = content;
        bodyPanel = new JPanel();
        contentPanel.add(bodyPanel);
        
        designSelect();
        // main.newMainMenu();
        return token;
    }
    public String startLogin(JPanel content, Main main) {
        contentPanel = content;
        bodyPanel = new JPanel();
        contentPanel.add(bodyPanel);

        login();
        // main.newMainMenu();
        return token;
    }

    public void designSelect() {
        bodyPanel.removeAll();
        bodyPanel.revalidate();
        bodyPanel.repaint();

        designSelect = new JPanel();
        designLabel = new JLabel("Design Selection");
        lightMode = new JButton("Light Mode");
        darkMode = new JButton("Dark Mode");
        designNext = new JButton("Next");

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

        designSelect.add(designLabel);
        designSelect.add(lightMode);
        designSelect.add(darkMode);
        designSelect.add(designNext);

        bodyPanel.add(designSelect);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void registration() {
        bodyPanel.removeAll();
        bodyPanel.revalidate();
        bodyPanel.repaint();

        registration = new JPanel();
        registrationLabel = new JLabel("Registration");
        firstName = new PlaceholderTextField("First Name", Main.TextColor);
        lastName = new PlaceholderTextField("Last Name", Main.TextColor);
        String[] gradeLevels = {"Freshman", "Sophomore", "Junior", "Senior"};
        gradeLevel = new JComboBox<>(gradeLevels);
        registrationNext = new JButton("Next");

        registrationNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registration2();
            }
        });

        registration.add(registrationLabel);
        registration.add(firstName);
        registration.add(lastName);
        registration.add(gradeLevel);
        registration.add(registrationNext);

        bodyPanel.add(registration);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void registration2() {
        bodyPanel.removeAll();
        bodyPanel.revalidate();
        bodyPanel.repaint();

        registration2 = new JPanel();
        registrationLabel2 = new JLabel("Registration (Cont'd)");
        email = new PlaceholderTextField("Email", Main.TextColor);
        password = new PlaceholderTextField("Password", Main.TextColor);
        repeatPassword = new PlaceholderTextField("Repeat Password", Main.TextColor);
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

        registration2.add(registrationLabel2);
        registration2.add(email);
        registration2.add(password);
        registration2.add(repeatPassword);
        registration2.add(registerButton);

        bodyPanel.add(registration2);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    public void verification() {
        bodyPanel.removeAll();
        bodyPanel.revalidate();
        bodyPanel.repaint();

        verification = new JPanel();
        verificationLabel = new JLabel("Verification");
        verificationCode = new PlaceholderTextField("Verification Code", Main.TextColor);
        verificationNext = new JButton("Next");

        verificationNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyUser();
            }
        });

        verification.add(verificationLabel);
        verification.add(verificationCode);
        verification.add(verificationNext);

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
