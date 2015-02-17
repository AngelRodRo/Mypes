package com.example.angel.mypes;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.util.Log;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SearchMapActivity extends SherlockActivity {

    GoogleMap map;

    private MenuItem mSpinnerItem1 = null;
    final ArrayList<String> list = new ArrayList<String>();
    String URL="http://10.0.2.2:3000/mypes/listcategorymypes";
    JSONObject lugar;
    JSONArray lugares;
    ArrayList<Place> listLugares;
    Marker marker;

    EditText editsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list.add("elemento 1");

        setContentView(R.layout.activity_search_map);
        handleIntent(getIntent());
        //getGoogleMap();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportMenuInflater().inflate(R.menu.menu_search_map, menu);

        // Locate the EditText in menu.xml
        editsearch = (EditText) menu.findItem(R.id.menu_search).getActionView();

        MenuItem menuSearch = menu.findItem(R.id.menu_search);

        menuSearch.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

            // Menu Action Collapse
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Empty EditText to remove text filtering
                editsearch.setText("");
                editsearch.clearFocus();
                return true;
            }

            // Menu Action Expand
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Focus on EditText
                editsearch.requestFocus();

                // Force the keyboard to show on EditText focus
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                return true;
            }
        });

        // Show the settings menu item in menu.xml
        MenuItem menuSettings = menu.findItem(R.id.menu_settings);

        // Capture menu item clicks
        menuSettings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // TODO Auto-generated method stub
                // Do something here
                Toast.makeText(getApplicationContext(), "Nothing here!",
                        Toast.LENGTH_LONG).show();
                return false;
            }

        });

        /*searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        // searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(false);

        mSpinnerItem1 = menu.findItem( R.id.menu_spinner1);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(mSpinnerItem1);

        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);


        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
*/


        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.action_about)
        {
            return true;
        }
        if(id==R.id.action_login)
        {
            Intent intentLogin = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentLogin);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

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
            //doSearch(query);
        }
    }

    private void doSearch(String queryStr)
    {
        if(queryStr.isEmpty())
            if(map!=null) {
                map.clear();
                //getPlaces("");
            }
            else
                Log.e("SearchActivity","No se pudo realizar la busqueda");
        else if(map!=null) {
            map.clear();
            //getPlaces(queryStr);
        }

        else
            Log.e("SearchActivity","No se pudo realizar la busqueda");
        Toast.makeText(getApplicationContext(),queryStr,Toast.LENGTH_LONG).show();


    }
/*



    public void getGoogleMap(){
        try{
            if(map==null) {
                map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView)).getMap(); //Mostrar mapa nuevo
                map.setMyLocationEnabled(true);
                map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);
                        // Getting the position from the marker
                        String title = marker.getTitle();

                        TextView tvTitle = (TextView) v.findViewById(R.id.title);
                        TextView tvPhone = (TextView) v.findViewById(R.id.phone);
                        TextView tvAddress = (TextView) v.findViewById(R.id.address);


                        for(int i=0;i<listLugares.size();i++)
                        {
                            if(listLugares.get(i).getName().equals(title))
                            {
                                tvTitle.setText("Nombre : " + title);
                                tvAddress.setText("Direccion : " + listLugares.get(i).getAddress());
                                tvPhone.setText("Telefono : " + listLugares.get(i).getPhone());
                            }
                        }

                        return v;

                    }
                });
                map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        String title = marker.getTitle();
                        Intent intentDetails = new Intent(getApplicationContext(),MypeActivity.class);
                        intentDetails.putExtra("Title",title);
                        startActivity(intentDetails);
                    }
                });
            }
        }
        catch (Exception e)
        {
            Log.e("MapFragment","No se pudo obtener mapa");
        }
    }


    public void getPlaces(final String category){

        new AsyncTask<Void, Void, ArrayList<Place>>() {

            private ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(SearchMapActivity.this);
                pDialog.setMessage("Getting Data ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected ArrayList<Place> doInBackground(Void... arg0) {
                // CREAMOS LA INSTANCIA DE LA CLASE
                String jsonStr="";
                listLugares = new ArrayList<>();
                if(category=="") {
                    JSONParser sh = new JSONParser();
                    jsonStr = sh.makeServiceCall(URL, JSONParser.GET);
                    String result = "";

                }
                else{
                    JSONObject jsonparam;
                    try{
                        jsonparam = new JSONObject();
                        jsonparam.put("categoria",category);
                        JSONParser sh = new JSONParser();
                        jsonStr = sh.makeServiceCall(URL, JSONParser.POST,jsonparam);
                    }catch(JSONException e){
                        Log.e("JSONException",e.toString());
                    }

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

                            String _id = lugar.getString("_id");
                            String nombre = lugar.getString("nombre");
                            String direccion = lugar.getString("direccion");
                            String telefono = lugar.getString("telefono");
                            JSONObject jsonObject = lugar.getJSONObject("ubicacion");
                            Double longitude = jsonObject.getDouble("longitud");
                            Double latitude = jsonObject.getDouble("latitud");
                            String categoria = lugar.getString("categoria");
                            listLugares.add(new Place(_id, nombre, direccion, telefono, new LatLng(latitude, longitude), categoria));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("ServiceHandler", "Esta habiendo problemas para cargar el JSON");
                }

                return listLugares;
            }

            @Override
            protected void onPostExecute(ArrayList<Place> result) {
                super.onPostExecute(result);
                pDialog.dismiss();
                AddMarkerPlace(result);
                // Dismiss the progress dialog


            }
        }.execute(null, null, null);
    }


    public void AddMarkerPlace(List<Place> places){

        int iconos=1;

        for (int i=0;i<places.size();i++)
        {
            if(places.get(i).getCategory().equals("restaurante"))
                iconos = R.drawable.comida;
            else if(places.get(i).getCategory().equals("bar"))
                iconos = R.drawable.bar;
            else if(places.get(i).getCategory().equals("discoteca"))
                iconos = R.drawable.discoteca;
            else if(places.get(i).getCategory().equals("ropa"))
                iconos = R.drawable.ropa;
            else if(places.get(i).getCategory().equals("veterinaria"))
                iconos = R.drawable.animales;
            else if(places.get(i).getCategory().equals("autos"))
                iconos = R.drawable.autos;
            else if(places.get(i).getCategory().equals("pasteleria"))
                iconos = R.drawable.pastelerias;
            else
                iconos = R.drawable._default;
            map.addMarker(new MarkerOptions()
                            .title(places.get(i).getName())
                            .position(places.get(i).getPosition())
                            .icon(BitmapDescriptorFactory.fromResource(iconos))
            ).showInfoWindow();
        }
    }*/

}
