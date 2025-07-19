package android.util;

/**
 * @author adell
 * @created 19/07/2025 - 3:27 PM
 * @project FormTree
 */
public class Log {

    public static int d(String tag, String msg) {
        System.out.println("DEBUG: " + tag + ": " + msg);
        return 0;
    }

    public static int i(String tag, String msg) {
        System.out.println("INFO: " + tag + ": " + msg);
        return 0;
    }

    public static int w(String tag, String msg) {
        System.out.println("WARN: " + tag + ": " + msg);
        return 0;
    }

    public static int e(String tag, String msg) {
        System.out.println("ERROR: " + tag + ": " + msg);
        return 0;
    }

    public static int e(String tag, String msg, Throwable tr) {
        System.out.println("ERROR: " + tag + ": " + msg + " Exception: " + tr.getMessage());
        return 0;
    }
}
