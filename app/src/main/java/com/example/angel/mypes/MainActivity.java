package com.example.angel.mypes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends SherlockActivity {

    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.edtLoginUserName);
        password = (EditText)findViewById(R.id.edtLoginPassword);
    }

    public void onRegister(View view)
    {
        Intent i = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(i);
    }

    public void onLogin(View view)
    {
        if(Validation.isOnline(MainActivity.this)){
            String user = username.getText().toString();
            String pass = username.getText().toString();
            actionLogin(user,pass);
        }
        else
        {
            DialogWarning.showNoConnectionDialog(MainActivity.this);
        }

    }

    public void actionLogin(final String username, final String password)
    {
        new AsyncTask<Void, Void, Boolean>() {/*

*/

            private ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Getting Data ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected Boolean doInBackground(Void... arg0) {
                // CREAMOS LA INSTANCIA DE LA CLASE
                Boolean jsonStr=false;
                JSONObject jsonparam;

                try{
                    jsonparam = new JSONObject();
                    jsonparam.put("username",username);
                    jsonparam.put("password",password);
                    JSONParser sh = new JSONParser();
                    jsonStr = sh.makeServiceCall_B(Config.APP_SERVER_LOGIN_USER, JSONParser.POST,jsonparam);
                }catch(JSONException e){
                    Log.e("JSONException", e.toString());
                }

                return jsonStr;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if(result) {
                    final SharedPreferences prefs = getSharedPreferences(
                            "RegisterData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("login",true);
                    editor.putString("username",username);
                    editor.commit();

                    Toast.makeText(MainActivity.this, "Se pudo ingresar correctamente", Toast.LENGTH_LONG).show();
                    finish();
                }else
                    Toast.makeText(MainActivity.this,"No se pudo ingresar, vuelva a intentarlo",Toast.LENGTH_LONG).show();
                pDialog.dismiss();
                // Dismiss the progress dialog


            }
        }.execute(null, null, null);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal,menu);
        //getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //noinspection SimplifiableIfStatement
        switch(item.getItemId())
        {
            case R.id.acerca:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("El texto................")
                        .setTitle("Acerca DE...")
                        .setCancelable(false)
                        .setNeutralButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            case R.id.configuraciones:
                Intent i=new Intent(this,Configuraciones.class);
                startActivity(i);
                break;


        }
        return true;
*/



}
