package hellofx;

public class Session {
    private static boolean admin = false;

    public static boolean isAdmin() { return admin; }
    public static void toggleAdmin() { admin = !admin; }
}