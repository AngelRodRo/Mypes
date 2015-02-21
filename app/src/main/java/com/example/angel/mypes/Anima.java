package com.example.angel.mypes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;


public class Anima extends Activity {

    public static int segundos =8;
    public static final int milisegundos = segundos*1000;
    private ProgressBar progreso;
    public static final int delay = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anima);

        progreso=(ProgressBar) findViewById(R.id.progressBar);
        progreso.setMax(maximo_progreso());
        empezaranimacion();
    }


    public void  empezaranimacion()
    {
        new CountDownTimer(milisegundos,1000)
        {

            @Override
            public void onTick(long millisUntilFinished) {


                progreso.setProgress(restableser(millisUntilFinished));


            }

            @Override
            public void onFinish() {


                //Intent nuevofrom=new Intent (SplashScreen.this,Principal.class);
                Intent nuevofrom=new Intent(Anima.this,SearchActivity.class);
                startActivity(nuevofrom);
                finish();

            }
        }.start();

    }

    public int restableser(long milisegunds)
    {
        return(int)((milisegundos-milisegunds)/1000);

    }
    public int maximo_progreso()
    {
        return segundos-delay;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_anima, menu);
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
}
