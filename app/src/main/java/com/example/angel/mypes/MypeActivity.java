package com.example.angel.mypes;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MypeActivity extends SherlockActivity {

    protected TextView customFont;
    Place place;

    TextView txtTitle;
    TextView txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mype);

        customFont = (TextView)findViewById(R.id.txtTitle);
        Typeface fontusuario = Typeface.createFromAsset(getAssets(),"BNMachine.ttf");
        customFont.setTypeface(fontusuario);


        txtDescription = (TextView)findViewById(R.id.txtDescription);


        String id = getIntent().getStringExtra("id");



        getPlacesCategory(Integer.parseInt(id));

        customFont.setText(place.getName());
        txtDescription.setText(place.getDescription());

    }


    public void getPlacesCategory(final int id)
    {

        new AsyncTask<Void, Void, Place>() {

            private ProgressDialog pDialog;
            JSONArray lugares;
            JSONObject lugar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(MypeActivity.this);
                pDialog.setMessage("Obteniendo datos ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected Place doInBackground(Void... arg0) {
                // CREAMOS LA INSTANCIA DE LA CLASE
                String jsonStr="";
                ListAdapter listAdapter=null;
                JSONObject jsonparam;
                ArrayList<Place> data = new ArrayList<Place>();
                ArrayList<ArrayList<String>> arrayComentarios = new ArrayList<ArrayList<String>>();
                ArrayList<String> datos = new ArrayList<String>();
                ArrayList<ArrayList<String>> arrayScore = new ArrayList<ArrayList<String>>();


                try{
                    jsonparam = new JSONObject();
                    jsonparam.put("_id",id);
                    JSONParser sh = new JSONParser();
                    jsonStr = sh.makeServiceCall(Config.APP_SERVER_ID_PLACE, JSONParser.POST,jsonparam);
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

                            String nombre = lugar.getString("nombre");
                            String direccion = lugar.getString("direccion");
                            String telefono = lugar.getString("telefono");
                            JSONObject jsonObject = lugar.getJSONObject("ubicacion");
                            Double longitude = jsonObject.getDouble("longitud");
                            Double latitude = jsonObject.getDouble("latitud");
                            String categoria = lugar.getString("categoria");
                            String descripcion = lugar.getString("descripcion");

                            /*JSONArray comentarios = lugar.getJSONArray("comentarios");
                            for(int j=0;j<comentarios.length();j++)
                            {
                                JSONObject comentario = comentarios.getJSONObject(j);
                                String opinion = comentario.getString("opinion");
                                String fecha = comentario.getString("fecha");
                                String usuario = comentario.getString("usuario");

                                datos.add(opinion);
                                datos.add(fecha);
                                datos.add(usuario);
                                arrayComentarios.add(datos);
                            }*/

                            place = new Place(nombre,direccion,telefono, new LatLng(latitude,longitude),categoria,arrayComentarios,descripcion);

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
            protected void onPostExecute(Place result) {
                super.onPostExecute(result);
                pDialog.dismiss();
                // Dismiss the progress dialog


            }
        }.execute(null, null, null);
    }

}
