package crm.gobelins.materialplaylist.appinfo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

class VersionInfo {

    private static String sVersionName = "unknown";
    private static int sRevisionNumber;
    private static boolean sIsInfoRead = false;

    public static String getVersionName(Context context) {
        if (!sIsInfoRead) {
            readAll(context);
        }
        return sVersionName;
    }

    public static int getRevisionNumber(Context context) {
        if (!sIsInfoRead) {
            readAll(context);
        }
        return sRevisionNumber;
    }

    /**
     * Read version name and revision number
     * from the package info
     *
     * @param context context to get the packageManager
     */
    private static void readAll(Context context) {
        String packageName = context.getPackageName();
        PackageManager pm = context.getPackageManager();

        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(packageName, 0);
            sVersionName = pi.versionName;
            sRevisionNumber = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        sIsInfoRead = true;
    }
}
