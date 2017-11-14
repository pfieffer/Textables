package np.com.ravi.textables;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import np.com.ravi.textables.model.Textable;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {//, ListView.OnItemLongClickListener {
    //json object response url
    private String urlForJsonArray = "https://raw.githubusercontent.com/OTGApps/Textables/master/resources/content.json";

    private ListView textablesListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView noConnectionTextView;

    private RequestQueue requestQueue;

    //temporary string to show the parsed response
    private String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        textablesListView = (ListView) findViewById(R.id.textables_list_view);
        noConnectionTextView = (TextView) findViewById(R.id.no_connection_textview);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);


        if (checkForConnection()) {
            makeNetworkRequestForTextables();
            noConnectionTextView.setVisibility(View.GONE);
            textablesListView.setVisibility(View.VISIBLE);
        } else {
            textablesListView.setVisibility(View.GONE);
            noConnectionTextView.setVisibility(View.VISIBLE);
        }

        textablesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Art", textablesListView.getItemAtPosition(position).toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, "Art copied to clipboard." ,Toast.LENGTH_LONG).show();
                return  true;
            }
        });
    }


    private void makeNetworkRequestForTextables() {
        final List<String> textablessArrayList = new ArrayList<>();


        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                urlForJsonArray,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("JSONArray is: ", response.toString());

                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject textablesCategory = response.getJSONObject(i);


                                Textable textableObject = new Textable();
                                textableObject.setCategory(textablesCategory.getString("category"));

                                JSONArray textablesItem = textablesCategory.getJSONArray("items");
                                for (int j = 0; j < textablesItem.length(); j++) {
                                    JSONObject textable = textablesItem.getJSONObject(j);
                                    String name = textable.getString("name");
                                    String art = textable.getString("art");
//                                    Log.d("name", name);
//                                    Log.d("art", art);

                                    textableObject.setName(name);
                                    textableObject.setArt(art);

//                                    Log.d("Category", textableObject.getCategory());
//                                    Log.d("Name", textableObject.getName());
//                                    Log.d("Art", textableObject.getArt());

                                    textablessArrayList.add(textableObject.toString());
                                }

                            }

                            ArrayAdapter<String> textableArrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                                    android.R.layout.simple_list_item_1,
                                    textablessArrayList);

                            textablesListView.setAdapter(textableArrayAdapter);
                            swipeRefreshLayout.setRefreshing(false);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d("Error: ", volleyError.getMessage());
                Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //adding request to request queue
        requestQueue.add(jsonArrReq);
    }


    private boolean checkForConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onRefresh() {
        if (checkForConnection()) {
            makeNetworkRequestForTextables();
            noConnectionTextView.setVisibility(View.GONE);
            textablesListView.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        } else {
            textablesListView.setVisibility(View.GONE);
            noConnectionTextView.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }

    }
    
}
