package src.auth;

/**
 * Navigation hook used by {@link AuthManager} after a login state change.
 *
 * <p>Keeps authentication independent of the UI: AuthManager decides that the
 * user should now be on the start page or the login screen, and the front-end
 * decides how to get there.
 */
public interface AuthNavigator {

    /** Called after a successful login or verification. */
    void toStartPage();

    /** Called after a logout or account deletion. */
    void toLogin();
}
