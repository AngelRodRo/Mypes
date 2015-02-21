package com.example.angel.mypes;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
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
    String telefono;
    String latitude;
    String longitude;
    ListAdapter listAdapter;


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

        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollview);
        list = (ListView)findViewById(R.id.listView);

        getPlacesCategory(Integer.parseInt(id));

        list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                int action = motionEvent.getActionMasked();

                switch (action){
                    case MotionEvent.ACTION_UP:
                        scrollView.requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MypeActivity.this,CommentActivity.class);
                startActivity(intent);
            }
        });
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
                ArrayList<Comentario> arrayComentarios = new ArrayList<Comentario>();
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


                                arrayComentarios.add(new Comentario(opinion,fecha,usuario));
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
                telefono = result.getPhone().toString();
                latitude = String.valueOf(result.getPosition().latitude);
                longitude = String.valueOf(result.getPosition().longitude);
                listAdapter = new ListAdapter(MypeActivity.this,R.layout.comentario,result.getComments()) {
                    @Override
                    public void onEntrada(Object entrada, View view) {
                        if (entrada != null) {
                            TextView textNombre = (TextView) view.findViewById(R.id.textView_nombre);
                            if (textNombre != null)
                                textNombre.setText(((Comentario) entrada).getUsuario());

                            TextView textComentario = (TextView) view.findViewById(R.id.textView_comentario);
                            if (textComentario != null) {
                                textComentario.setText(((Comentario) entrada).getOpinion());
                            }
                            TextView textTiempo = (TextView) view.findViewById(R.id.textView_tiempo);
                            if (textTiempo != null)
                                textTiempo.setText(((Comentario) entrada).getFecha());
                        }
                    }
               };

                list.setAdapter(listAdapter);
                myImage.setImageBitmap(result.getFoto());

            }

        }.execute(null, null, null);
    }


    public void accionLlamar(View view)
    {

        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+telefono));
            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.e("dialing-example", "Call failed", activityException);
        }
    }

    public void accionGaleria (View view){
        Intent intentGaleria = new Intent(MypeActivity.this,GaleryActivity.class);
        ArrayList<String> urlList = new ArrayList<>();
        urlList.add("http://portal.rodferperu.com/img/imagen7.png");
        intentGaleria.putStringArrayListExtra("urlList",urlList);
        startActivity(intentGaleria);
    }

    public void accionIrMapa(View view){
        Intent intentMapa = new Intent(MypeActivity.this,MapActivity.class);
        intentMapa.putExtra("latitude",latitude);
        intentMapa.putExtra("longitude",longitude);
        startActivity(intentMapa);
    }

    public void accionComentar(View view) {
        final SharedPreferences prefs = getSharedPreferences(
                "RegisterData", Context.MODE_PRIVATE);
        Boolean login = prefs.getBoolean("login", false);
        if (login) {

        } else {
            Toast.makeText(MypeActivity.this,"Por favor debe iniciar sesion",Toast.LENGTH_LONG).show();
            Intent mainActivity = new Intent(MypeActivity.this,MainActivity.class);
            startActivity(mainActivity);
        }
    }
}
