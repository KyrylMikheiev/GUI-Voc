package src.auth;

import java.util.Map;

import src.App;
import src.api.APIClient;
import src.ui.screens.auth.Login;
import src.ui.screens.StartPage;

public class AuthManager {
    private static User loggedInUser;

    public static void startupCheck() {
        String token = TokenManager.loadToken();

        if (token == null) {
            System.out.println("Token loader returned null, prompting UI to request login.");
            return;
        }

        User validated = APIClient.validateToken(token);
        if (validated != null) {
            // Token loaded successfully
            setLoggedIn(validated);
            return;
        }

        // Token was loaded but invalid
        System.out.println("Loaded token seems to be invalid.");
    }

    public static boolean login(String email, String password) {
        User response = APIClient.login(email, password);
        if (response == null) {
            return false;
        }

        setLoggedIn(response);
        return true;
    }

    public static boolean verifyAccount(String email, String code) {
        User response = APIClient.verifyAccount(email, code);
        if (response == null) {
            // Invalid verification code
            return false;
        }

        setLoggedIn(response);
        return true;
    }

    public static AuthState getState() {
        if (loggedInUser == null)  {
            return AuthState.NOT_LOGGED_IN;
        } else {
            if (!loggedInUser.isVerified()) {
                return AuthState.NEED_VERIFICATION;
            }
            return AuthState.LOGGED_IN;
        }
    }

    public static String getToken() {
        return loggedInUser.getToken();
    }

    private static void setLoggedIn(User user) {
        loggedInUser = user;
        TokenManager.saveToken(getToken());
        App.setFreshState(new StartPage());
    }

    public static boolean deleteAccount(String password) {
        boolean success = APIClient.deleteAccount(password);
        loggedInUser = null;
        App.setFreshState(new Login());
        return success;
    }

    public static void logout() {
        APIClient.logout(getToken());
        loggedInUser = null;
        App.setFreshState(new Login());
    }
}