package com.example.angel.mypes;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RegisterActivity extends SherlockActivity {

    EditText edtUsername;
    EditText edtPassword;
    EditText edtName;
    EditText edtLastName;
    EditText edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtUsername = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtName = (EditText) findViewById(R.id.edtName);
        edtLastName = (EditText) findViewById(R.id.edtLastName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);

        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String name = edtName.getText().toString();
        String lastname = edtLastName.getText().toString();
        String email = edtEmail.getText().toString();

        JSONObject data;
        try {
            data = new JSONObject();
            data.put("username", username);
            data.put("password",password);
            data.put("name",name);
            data.put("lastname",lastname);
            data.put("email",email);
            data.put("tipo","N");
            registerNewUser(data);
        }catch (Exception e)
        {}



    }


    public void registerNewUser(final JSONObject user_data)
    {
        new AsyncTask<Void, Void, ListAdapter>() {

            private ProgressDialog pDialog;
            JSONArray lugares;
            JSONObject lugar;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(RegisterActivity.this);
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

                ArrayList<Place> data = new ArrayList<Place>();

                JSONParser sh = new JSONParser();
                jsonStr = sh.makeServiceCall(Config.APP_SERVER_CATEGORY_PLACES, JSONParser.POST,user_data);


                if (jsonStr != null) {
                    try {
                        try {
                            lugares = new JSONArray(jsonStr);
                            //Toast.makeText(getApplicationContext(),longitude+ " "+ latitude,Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Log.d("JSONException", e.toString());
                        }

                        // Getting JSON Array node
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


                // Dismiss the progress dialog


            }
        }.execute(null, null, null);
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
    }*/
}
