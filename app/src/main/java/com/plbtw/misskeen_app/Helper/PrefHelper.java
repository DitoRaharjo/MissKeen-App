package com.plbtw.misskeen_app.Helper;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Paulina on 5/15/2017.
 */
public class PrefHelper {
    public static void saveToPref(Context context, String key, String val) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, val).commit();
    }

    public static void clearAll(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().commit();

    }

    public static void remove(Context context, String key) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().remove(key).commit();

    }

    public static String getPref(Context context, String key) {
        if (PreferenceManager.getDefaultSharedPreferences(context).contains(key)) {
            return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null);
        } else {
            return null;
        }
    }
}
