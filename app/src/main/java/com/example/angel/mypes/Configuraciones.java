package com.example.angel.mypes;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by anderson on 08/02/2015.
 */
public class Configuraciones extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);


    }
}
