package com.example.james.feyhw4;
//James Fey based on Luca de alfaro starter code

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "lv-ex";
    static final public String MYPREFS = "myprefs";
    RequestQueue queue;
    AppInfo appInfo;

    private class ListElement {

        ListElement(String tl, String bl, String url) {
            titleLabel = tl;
            subtitleLabel = bl;
            urlHolder = url;
        }

        public String titleLabel;
        public String subtitleLabel;
        public String urlHolder;
    }

    private ArrayList<ListElement> aList;

    private class MyAdapter extends ArrayAdapter<ListElement> {

        int resource;
        Context context;

        public MyAdapter(Context _context, int _resource, List<ListElement> items) {
            super(_context, _resource, items);
            resource = _resource;
            context = _context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout newView;

            ListElement w = getItem(position);

            // Inflate a new view if necessary.
            if (convertView == null) {
                newView = new LinearLayout(getContext());
                LayoutInflater vi = (LayoutInflater)
                        getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi.inflate(resource,  newView, true);
            } else {
                newView = (LinearLayout) convertView;
            }

            // Fills in the view.
            TextView tv = (TextView) newView.findViewById(R.id.Title);
            TextView b = (TextView) newView.findViewById(R.id.Subtitle);

            tv.setText(w.titleLabel);
            b.setText(w.subtitleLabel);

            // Set a listener for the whole list item.
            newView.setTag(w.urlHolder);
            newView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = v.getTag().toString();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, s, duration);
                    toast.show();
                    String chosenURL = s;
                    appInfo.setURL(chosenURL);
                    openURL(v);


                }
            });

            return newView;
        }
    }

    private MyAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appInfo = AppInfo.getInstance(this);
        queue = Volley.newRequestQueue(this);
        aList = new ArrayList<ListElement>();
        aa = new MyAdapter(this, R.layout.list_element, aList);
        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(aa);
        getList();
        aa.notifyDataSetChanged();
    }


    public void clickRefresh (View v) {
        Log.i(LOG_TAG, "Requested a refresh of the list");
        getList();
    }

    public void openURL(View V){
        Intent intent = new Intent(this, WebActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



    private void getList() {
        // Instantiate the RequestQueue.
        String url = "https://luca-ucsc-teaching-backend.appspot.com/hw4/get_news_sites";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //mTextView.setText("Response json: " + response.toString());
                        Log.d(LOG_TAG, "Received: " + response.toString());
                        // Ok, let's disassemble a bit the json object.

                        try {
                            JSONArray receivedList = response.getJSONArray("news_sites");
                            aList.clear();
                            for (int i = 0; i < receivedList.length(); i++) {
                                JSONObject actor = receivedList.getJSONObject(i);
                                if (!actor.isNull("url") && !actor.isNull("title")){
                                    String name = actor.getString("title");
                                    if (actor.isNull("subtitle")){
                                        aList.add(new ListElement(actor.getString("title"), null, actor.getString("url")));
                                    }else{
                                        aList.add(new ListElement(
                                                actor.getString("title"), actor.getString("subtitle"), actor.getString("url")
                                        ));
                                    }

                                    Log.d(LOG_TAG, name);
                                }
                            }
                            aa.notifyDataSetChanged();
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d(LOG_TAG, error.toString());
                    }
                });

        // In some cases, we don't want to cache the request.
        // jsObjRequest.setShouldCache(false);

        queue.add(jsObjRequest);

    }

}

