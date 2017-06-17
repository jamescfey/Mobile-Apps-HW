package com.example.james.feyhw4;
//James Fey based on Luca de alfaro starter code

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by James on 5/8/2017.
 */

public class AppInfo {
    private static AppInfo instance = null;
    private static final String URL = "newUrl";

    protected AppInfo(){

    }
    public String chosenUrl;

    private Context my_context;

    public static AppInfo getInstance(Context context) {
        if(instance == null) {
            instance = new AppInfo();
            instance.my_context = context;
            SharedPreferences settings = context.getSharedPreferences(MainActivity.MYPREFS, 0);
            instance.chosenUrl = settings.getString(URL, null);
        }
        return instance;
    }

    public void setURL(String c) {
        instance.chosenUrl = c;
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(URL, c);
        editor.commit();
    }
}
