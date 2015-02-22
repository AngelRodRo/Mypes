package com.example.angel.mypes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.widget.SearchView;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class ListMypeActivity extends SherlockActivity {

    String query;
    ListAdapter listAdapter;

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mype);
        handleIntent(getIntent());

        query = getIntent().getStringExtra("query");
        lv= (ListView) findViewById(R.id.listView8);


        SearchMype(query);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place select = (Place) parent.getItemAtPosition(position);

                Intent startPlace = new Intent(view.getContext(),MypeActivity.class);
                startPlace.putExtra("id",select.getId());

                startActivity(startPlace);
            }
        });
    }

    public void SearchMype(final String query)
    {
        new AsyncTask<Void, Void, ListAdapter>() {/*

*/

            private ProgressDialog pDialog;
            JSONArray lugares;
            JSONObject lugar;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(ListMypeActivity.this);
                pDialog.setMessage("Getting Data ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected ListAdapter doInBackground(Void... arg0) {
                // CREAMOS LA INSTANCIA DE LA CLASE
                String jsonStr="";
                ListAdapter listAdapter=null;
                JSONObject jsonparam;
                ArrayList<Place> data = new ArrayList<Place>();

                try{
                    jsonparam = new JSONObject();
                    jsonparam.put("nombre",query);
                    JSONParser sh = new JSONParser();
                    jsonStr = sh.makeServiceCall(Config.APP_SERVER_SEARCH_REQUEST, JSONParser.POST,jsonparam);
                }catch(JSONException e){
                    Log.e("JSONException", e.toString());
                }


                if (jsonStr != null) {
                    try {
                        try {
                            lugares = new JSONArray(jsonStr);
                            //Toast.makeText(getApplicationContext(),longitude+ " "+ latitude,Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Log.d("JSONException", e.toString());
                        }

                        // Getting JSON Array node

                        for (int i = 0; i < lugares.length(); i++) {
                            lugar = lugares.getJSONObject(i);
                            //Bitmap bitmap;
                            String _id = lugar.getString("_id");
                            String nombre = lugar.getString("nombre");
                            String direccion = lugar.getString("direccion");
                            String telefono = lugar.getString("telefono");
                            JSONObject jsonObject = lugar.getJSONObject("ubicacion");
                            Double longitude = jsonObject.getDouble("longitud");
                            Double latitude = jsonObject.getDouble("latitud");
                            String categoria = lugar.getString("categoria");

                            JSONArray fotos = lugar.getJSONArray("fotos");

                            String foto = fotos.getString(0);

                            Bitmap bitmap=null;

                            try {
                                bitmap = BitmapFactory.decodeStream((InputStream)new URL(foto).getContent());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            data.add(new Place(_id, bitmap, nombre, direccion, telefono, new LatLng(latitude, longitude), categoria));

                            listAdapter = new ListAdapter(ListMypeActivity.this,R.layout.entry,data){
                                @Override
                                public void onEntrada(Object entrada, View view) {
                                    if (entrada != null) {
                                        TextView textTopEntry = (TextView) view.findViewById(R.id.textView_superior);
                                        if (textTopEntry != null)
                                            textTopEntry.setText(((Place) entrada).getName());

                                        ImageView imageView = (ImageView) view.findViewById(R.id.imageView_imagen);
                                        if(imageView!=null) {
                                            imageView.setImageBitmap(((Place) entrada).getFoto());
                                        }
                                        TextView textBetweenEntry = (TextView) view.findViewById(R.id.textView_medio);
                                        if (textBetweenEntry != null)
                                            textBetweenEntry.setText(((Place) entrada).getAddress());
                                    }
                                }
                            };

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("ServiceHandler", "Esta habiendo problemas para cargar el JSON");
                }

                return listAdapter;
            }

            @Override
            protected void onPostExecute(ListAdapter result) {
                super.onPostExecute(result);
                pDialog.dismiss();
                updateList(result);
                // Dismiss the progress dialog


            }
        }.execute(null, null, null);
    }


    public void updateList(ListAdapter result)
    {
        lv.setAdapter(result);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportMenuInflater().inflate(R.menu.menu_list_mype, menu);


        //Set searchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQuery(query,true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);

    }

    private void handleIntent(Intent intent)
    {
        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    private void doSearch(String queryStr)
    {
        SearchMype(queryStr);
    }
}
