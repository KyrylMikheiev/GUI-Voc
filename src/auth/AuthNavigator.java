package src.auth;

/**
 * Navigation hook used by {@link AuthManager} after a login state change.
 *
 * <p>AuthManager previously called {@code App.setFreshState(new StartPage())}
 * directly, hard-wiring authentication to the Swing UI. Routing those calls
 * through this interface lets the Swing and JavaFX front-ends share one
 * AuthManager during the migration.
 */
public interface AuthNavigator {

    /** Called after a successful login or verification. */
    void toStartPage();

    /** Called after a logout or account deletion. */
    void toLogin();
}
