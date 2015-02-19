package com.example.angel.mypes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.image.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class MypeActivity extends SherlockActivity {

    protected TextView customFont;


    TextView txtTitle;
    TextView txtDescription;
    Place placea;
    ListView list;
    ImageView imgViewFondo;

    ImageView myImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mype);

        //customFont = (TextView)findViewById(R.id.txtTitle);
        Typeface fontusuario = Typeface.createFromAsset(getAssets(),"BNMachine.ttf");
        //customFont.setTypeface(fontusuario);

        myImage = (ImageView) this.findViewById(R.id.my_image);

        txtDescription = (TextView)findViewById(R.id.txtDescription);

        String id = getIntent().getStringExtra("id");

        list = (ListView)findViewById(R.id.listView);

        getPlacesCategory(Integer.parseInt(id));

    }

    public void getPlacesCategory(final int id)
    {

        new AsyncTask<Void, Void, Place>() {

            private ProgressDialog pDialog;
            JSONArray lugares;
            JSONObject lugar;
            Place place;
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
                JSONObject jsonparam;
                ArrayList<ArrayList<String>> arrayComentarios = new ArrayList<ArrayList<String>>();
                ArrayList<String> datos = new ArrayList<String>();
                Bitmap bitmap=null;

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
                            ArrayList<String> afotos = new ArrayList<String>();

                            JSONArray comentarios = lugar.getJSONArray("comentarios");
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
                            }

                            JSONArray fotos = lugar.getJSONArray("fotos");
                            for(int k=0;k<fotos.length();k++)
                            {
                                String url = fotos.getString(k);
                                afotos.add(url);
                            }

                            try {
                                bitmap = BitmapFactory.decodeStream((InputStream)new URL(afotos.get(0)).getContent());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            place = new Place(nombre,bitmap,direccion,telefono, new LatLng(latitude,longitude),categoria,arrayComentarios,"",descripcion,afotos);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("ServiceHandler", "Esta habiendo problemas para cargar el JSON");
                }

                return place;
            }

            @Override
            protected void onPostExecute(Place result) {
                super.onPostExecute(result);
                pDialog.dismiss();
                // Dismiss the progress dialog

                //Drawable foto = LoadImageFromWebOperations("http://3.bp.blogspot.com/-8ve3Fp7BQeA/UHPM2CPY60I/AAAAAAAADOY/JUty7z2uWOE/s1600/1920x1080-Blue-Waves-Beach-Wallpaper1080p-HD.jpeg");
                  //customFont.setText(result.getName());
                txtDescription.setText(result.getDescription());
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add(result.getComments().get(0).get(0));
                arrayList.add(result.getComments().get(1).get(0));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MypeActivity.this,android.R.layout.simple_list_item_1,arrayList);
                list.setAdapter(adapter);

                myImage.setImageBitmap(result.getFoto());


            }
        }.execute(null, null, null);
    }



    public void accionGaleria (View view){
        Intent intentGaleria = new Intent(MypeActivity.this,GaleryActivity.class);
        ArrayList<String> urlList = new ArrayList<>();
        urlList.add("http://portal.rodferperu.com/img/imagen7.png");
        intentGaleria.putStringArrayListExtra("urlList",urlList);
        startActivity(intentGaleria);
    }


    public void accionComentar(View view) {
        final SharedPreferences prefs = getSharedPreferences(
                "DataUser", Context.MODE_PRIVATE);
        Boolean registrado = prefs.getBoolean("registrado", false);
        if (registrado) {

        } else {
            Toast.makeText(MypeActivity.this,"Por favor debe registrarse",Toast.LENGTH_LONG).show();
            Intent loginActivity = new Intent(MypeActivity.this,RegisterActivity.class);
            startActivity(loginActivity);
        }
    }
}
