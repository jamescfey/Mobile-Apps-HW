//James Fey adapted this from Luca de alfaro's code
//on http get and posts.

package com.example.james.feyhw3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "androidhomephone";

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // We need the "final" keyword here to guarantee that the
        // value does not change, as it is used in the callbacks.

        //cool testing stuff
        //sendMsg("Anna", "Clara", "Tschuss!");
        //post(/*"abracadabra"*/);
        //getMessages("mario");
        //get(/*"abracadabra"*/);
    }

    public void post(View V/*final String sender*/) {
        final TextView postText = (TextView) findViewById(R.id.textView);
        //Request stuff from luca
        StringRequest sr = new StringRequest(Request.Method.POST,
                "https://luca-ucsc-teaching-backend.appspot.com/hw3/request_via_post",
                new Response.Listener<String>() {
                    @Override
                    //Handle how to respond to luca's secret message
                    public void onResponse(String response) {
                        Log.d(LOG_TAG, "Got:" + response.substring(12, response.length()-2));
                        postText.setText(response.substring(12, response.length()-2));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            //Gross method of packing
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("token", "abracadabra");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);

    }


    public void get(View V/*final String recipient*/) {
        final TextView getText = (TextView) findViewById(R.id.textView2);


        // Instantiate the RequestQueue.
        String url = "https://luca-ucsc-teaching-backend.appspot.com/hw3/request_via_get";

        String my_url = url + "?token=" + "abracadabra";
        //Parse the json stuff to print on screen
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, my_url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(LOG_TAG, "Received Maybe: " + response.getString("result"));

                            // print the text like a pro.
                            getText.setText(response.getString("result"));
                          // :P this garbage
                        } catch (Exception e) {
                        getText.setText("Aaauuugh, received bad json: " + e.getStackTrace());
                    }
                    }
                }, new Response.ErrorListener() {
                    //Just incase everything is ruined
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d(LOG_TAG, error.toString());
                    }
                });

        queue.add(jsObjRequest);
    }

}
