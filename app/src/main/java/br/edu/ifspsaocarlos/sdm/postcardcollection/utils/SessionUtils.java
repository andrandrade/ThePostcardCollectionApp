package br.edu.ifspsaocarlos.sdm.postcardcollection.utils;

public class SessionUtils {
    public static final String TAG = "PostcardApp";
    public static final String USER_ID = "UserId";
    public static final String POSTCARD_ID = "PostcardId";
    public static final String POSTCARD_OBJ = "PostcardObj";

    private static String currentUserId;

    public static String getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(String currentUserId) {
        SessionUtils.currentUserId = currentUserId;
    }
}


/*
// From: https://medium.com/@programmerr47/singletons-in-android-63ddf972a7e7
public class MyApplication extends Application {
    private static Something something;

    public void onCreate() {
        super.onCreate();
        something = new Something();/new Something(getApplicationContext())/new Something(InitState state);
    }

    public static Something getSomething() {
        return something.
    }
}
*/