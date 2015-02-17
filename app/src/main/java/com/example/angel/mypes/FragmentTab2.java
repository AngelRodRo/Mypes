package com.example.angel.mypes;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by angel on 16/02/15.
 */
public class FragmentTab2 extends SherlockFragment {

    ArrayList<Place> data = new ArrayList<Place>();
    ListView lv;
    ListAdapter listAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        listAdapter = new ListAdapter(getActivity().getBaseContext(),R.layout.entry,data){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView textTopEntry = (TextView) view.findViewById(R.id.textView_superior);
                    if (textTopEntry != null)
                        textTopEntry.setText(((Place) entrada).getName());

                    TextView textBottomEntry = (TextView) view.findViewById(R.id.textView_inferior);
                    if (textBottomEntry != null)
                        textBottomEntry.setText(((Place) entrada).getCategory());

                    TextView textBetweenEntry = (TextView) view.findViewById(R.id.textView_medio);
                    if (textBetweenEntry != null)
                        textBetweenEntry.setText(((Place) entrada).getAddress());
                }
            }
        };

        View view = inflater.inflate(R.layout.fragmenttab2,container,false);
        lv = (ListView) view.findViewById(R.id.listView);

        lv.setAdapter(listAdapter);

        getPlacesCategory("Vestimenta",view);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }

    public void getPlacesCategory(final String category,final View view)
    {
        new AsyncTask<Void, Void, ArrayList<Place>>() {

            private ProgressDialog pDialog;
            JSONArray lugares;
            JSONObject lugar;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(view.getContext());
                pDialog.setMessage("Getting Data ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected ArrayList<Place> doInBackground(Void... arg0) {
                // CREAMOS LA INSTANCIA DE LA CLASE
                String jsonStr="";

                JSONObject jsonparam;
                try{
                    jsonparam = new JSONObject();
                    jsonparam.put("categoria",category);
                    JSONParser sh = new JSONParser();
                    jsonStr = sh.makeServiceCall(Config.APP_SERVER_CATEGORY_PLACES, JSONParser.POST,jsonparam);
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

                            String _id = lugar.getString("_id");
                            String nombre = lugar.getString("nombre");
                            String direccion = lugar.getString("direccion");
                            String telefono = lugar.getString("telefono");
                            JSONObject jsonObject = lugar.getJSONObject("ubicacion");
                            Double longitude = jsonObject.getDouble("longitud");
                            Double latitude = jsonObject.getDouble("latitud");
                            String categoria = lugar.getString("categoria");
                            data.add(new Place(_id, nombre, direccion, telefono, new LatLng(latitude, longitude), categoria));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("ServiceHandler", "Esta habiendo problemas para cargar el JSON");
                }

                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<Place> result) {
                super.onPostExecute(result);
                pDialog.dismiss();
                updateList();


                // Dismiss the progress dialog


            }
        }.execute(null, null, null);
    }

    public void updateList()
    {
        listAdapter.notifyDataSetChanged();
    }
}
