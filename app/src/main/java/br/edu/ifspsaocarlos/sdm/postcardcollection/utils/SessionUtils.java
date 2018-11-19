package br.edu.ifspsaocarlos.sdm.postcardcollection.utils;

public class SessionUtils {
    public static final String TAG = "PostcardApp";
    private static String currentUserId;

    public static String getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(String currentUserId) {
        SessionUtils.currentUserId = currentUserId;
    }
}
