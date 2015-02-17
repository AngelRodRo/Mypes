package com.example.angel.mypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by angel on 30/01/15.
 */
public abstract class ListAdapter extends BaseAdapter {


    //creamos tres variables  privadas
    private ArrayList<?> entry;
    private int R_layout_IdView;
    private Context context;


    //esta funcion   donde se pondran los datos
    public ListAdapter(Context contexto, int R_layout_IdView, ArrayList<?> entry) {
        super();
        //donde entraran los id y lo demas de los layouts
        this.context = contexto;
        this.entry = entry;
        this.R_layout_IdView = R_layout_IdView;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup pariente) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R_layout_IdView, null);
        }
        onEntrada (entry.get(posicion), view);
        return view;
    }

    @Override
    public int getCount() {
        return entry.size();
    }

    @Override
    public Object getItem(int posicion) {
        return entry.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    /** Devuelve cada una de las entradas con cada una de las vistas a la que debe de ser asociada
     * @param entrada La entrada que será la asociada a la view. La entrada es del tipo del paquete/handler
     * @param view View particular que contendrá los datos del paquete/handler
     */
    public abstract void onEntrada (Object entrada, View view);
}