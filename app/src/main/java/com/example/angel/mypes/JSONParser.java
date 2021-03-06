package com.example.angel.mypes;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by angel on 09/02/15.
 */
public class JSONParser {




    public final static int GET = 1;
    public final static int POST = 2;
    //CONSTRUCTOR
    public JSONParser() {
    }

    public String makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null);
    }
    //METODO PARA ESTABLECER CONEXIÓN
    public String makeServiceCall(String url, int method,JSONObject params) {
        String response = null;
        try {
            //HTTP CLIENT
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // AÑADIMOS PARAMETROS AL METODO POST
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {

                    StringEntity categoryEntity = new StringEntity(params.toString());

                    httpPost.addHeader("Content-Type", "application/json");
                    httpPost.setEntity(categoryEntity);
                }
                httpResponse = httpClient.execute(httpPost);
            } else if (method == GET) {
                // AÑADIMOS PARAMETROS AL METODO GET
                if (params != null) {
                    //String paramString = URLEncodedUtils.format(params, "utf-8");
                    //url += "?" + paramString;
                }
                //METODO GET
                HttpGet httpGet = new HttpGet(url);
                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
            //EXCEPCIONES
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //DEVOLVEMOS RESPUESTA
        return response;
    }

    public Boolean makeServiceCall_B(String url, int method,JSONObject params) {
        boolean response = false;
        try {
            //HTTP CLIENT
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // AÑADIMOS PARAMETROS AL METODO POST
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {

                    StringEntity categoryEntity = new StringEntity(params.toString());

                    httpPost.addHeader("Content-Type", "application/json");
                    httpPost.setEntity(categoryEntity);
                }
                httpResponse = httpClient.execute(httpPost);
            } else if (method == GET) {
                // AÑADIMOS PARAMETROS AL METODO GET
                if (params != null) {
                    //String paramString = URLEncodedUtils.format(params, "utf-8");
                    //url += "?" + paramString;
                }
                //METODO GET
                HttpGet httpGet = new HttpGet(url);
                httpResponse = httpClient.execute(httpGet);

            }
            int status = httpResponse.getStatusLine().getStatusCode();

            if(status==200)
                response = true;
            else
                response = false;
            //EXCEPCIONES
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //DEVOLVEMOS RESPUESTA
        return response;
    }
}
