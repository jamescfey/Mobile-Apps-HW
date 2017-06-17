package com.example.james.feyhw2;
//James Fey based on Luca de alfaro starter code

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by James on 4/27/2017.
 */

public class AppInfo {

    private static AppInfo instance = null;
    private static final String COLOR_NAME1 = "color1";
    private static final String COLOR_NAME2 = "color2";
    private static final String COLOR_NAME3 = "color3";

    protected AppInfo() {
        // Exists only to defeat instantiation.
    }

    // Here are some values we want to keep global.
    public String sharedString1;
    public String sharedString2;
    public String sharedString3;

    private Context my_context;

    public static AppInfo getInstance(Context context) {
        if(instance == null) {
            instance = new AppInfo();
            instance.my_context = context;
            SharedPreferences settings = context.getSharedPreferences(Activity1.MYPREFS, 0);
            instance.sharedString1 = settings.getString(COLOR_NAME1, null);
            instance.sharedString2 = settings.getString(COLOR_NAME2, null);
            instance.sharedString3 = settings.getString(COLOR_NAME3, null);
        }
        return instance;
    }

    public void setColor(String c) {
        instance.sharedString1 = c;
        SharedPreferences settings = my_context.getSharedPreferences(Activity1.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(COLOR_NAME1, c);
        editor.commit();
    }
    public void setColor2(String c) {
        instance.sharedString2 = c;
        SharedPreferences settings = my_context.getSharedPreferences(Activity1.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(COLOR_NAME2, c);
        editor.commit();
    }
    public void setColor3(String c) {
        instance.sharedString3 = c;
        SharedPreferences settings = my_context.getSharedPreferences(Activity1.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(COLOR_NAME3, c);
        editor.commit();
    }
}
