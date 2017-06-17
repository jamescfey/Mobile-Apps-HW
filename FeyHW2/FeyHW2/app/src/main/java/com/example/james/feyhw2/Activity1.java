package com.example.james.feyhw2;
//James Fey based on Luca de alfaro starter code

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Activity1 extends AppCompatActivity {

    static final public String MYPREFS = "myprefs";

    AppInfo appInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        appInfo = AppInfo.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EditText edv1 = (EditText) findViewById(R.id.editText1);
        if (appInfo.sharedString1 != null) {
            edv1.setText(appInfo.sharedString1);
        }
        TextView tv = (TextView) findViewById(R.id.textView2);
        tv.setText(appInfo.sharedString2);

        // and the one from the singleton object
        TextView tv2 = (TextView) findViewById(R.id.textView3);
        tv2.setText(appInfo.sharedString3);
    /*
        EditText edv2 = (EditText) findViewById(R.id.editText2);
        if (appInfo.sharedString2 != null) {
            edv2.setText(appInfo.sharedString2);
        }
    */
    }

    public void commitCurrent1(View V) {
        // Grab the text, and store it in a preference.
        EditText edv = (EditText) findViewById(R.id.editText1);
        String text1 = edv.getText().toString();
        appInfo.setColor(text1);
    }

    public void goTwo(View V) {

/*
        // The second string we store it in the singleton class.
        EditText edv2 = (EditText) findViewById(R.id.editText2);
        String text2 = edv2.getText().toString();
        appInfo.setColor2(text2);
*/
        // Go to second activity
        Intent intent = new Intent(this, Activity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void goThree(View V) {

/*
        // The second string we store it in the singleton class.
        EditText edv2 = (EditText) findViewById(R.id.editText2);
        String text2 = edv2.getText().toString();
        appInfo.setColor2(text2);
*/
        // Go to second activity
        Intent intent = new Intent(this, Activity3.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Activity1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
