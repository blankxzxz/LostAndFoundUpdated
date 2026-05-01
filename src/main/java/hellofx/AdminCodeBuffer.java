package hellofx;

import hellofx.controllers.HomeController;

public class AdminCodeBuffer {

    private static String buffer = "";

    public static void add(char c) {
        buffer += c;

        if (buffer.length() > 6) {
            buffer = buffer.substring(buffer.length() - 6);
        }

        if (buffer.equals("062206")) {
            Session.toggleAdmin();
            HomeController.refreshAdmin();
            NotificationManager.showNotification(Session.isAdmin()
                    ? "Admin mode activated"
                    : "Admin mode deactivated");
            buffer = "";
        }
    }
}