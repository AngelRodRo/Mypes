package com.example.angel.mypes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by angel on 16/02/15.
 */
public class FragmentTab1 extends SherlockFragment {

    ListView lv;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragmenttab1,container,false);
        lv = (ListView) view.findViewById(R.id.listView);

        getPlacesCategory("Restaurante",view);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place select = (Place) parent.getItemAtPosition(position);

                Intent startPlace = new Intent(view.getContext(),MypeActivity.class);
                startPlace.putExtra("id",select.getId());

                startActivity(startPlace);
            }
        });


        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }


    @Override
    public void onResume() {
        super.onResume();
        getPlacesCategory("Restaurante",view);
    }

    public void getPlacesCategory(final String category,final View view)
    {
        new AsyncTask<Void, Void, ListAdapter>() {/*

*/

            private ProgressDialog pDialog;
            JSONArray lugares;
            JSONObject lugar;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(view.getContext());
                pDialog.setMessage("Obteniendo lugares..");
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

                            listAdapter = new ListAdapter(getActivity().getBaseContext(),R.layout.entry,data){
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

}
