package com.luckyecuador.app.bassaApp.ui.encuesta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.luckyecuador.app.bassaApp.Adaptadores.EncuestaListAdapter;
import com.luckyecuador.app.bassaApp.Clase.BaseEvaluacionPromotor;
import com.luckyecuador.app.bassaApp.Conexion.Constantes;
import com.luckyecuador.app.bassaApp.DataBase.DatabaseHelper;
import com.luckyecuador.app.bassaApp.DataBase.Provider;
import com.luckyecuador.app.bassaApp.EvaluacionEncuestaActivity;
import com.luckyecuador.app.bassaApp.R;

import java.util.ArrayList;


public class EncuestaFragment3 extends Fragment {

    private ViewGroup rootView;
    private RecyclerView listaEncuesta;
    private ArrayList<BaseEvaluacionPromotor> listPreguntas;
    private DatabaseHelper handler;
    private String re;
    private EncuestaListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout layoutItems;
    private EditText txtBuscador;

    final String TAG = "MODULO_ENCUESTAS_EVALUACION";
    public EncuestaFragment3() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_encuesta3, container, false);

        handler = new DatabaseHelper(getContext(), Provider.DATABASE_NAME, null, 1);
        LoadData();
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefresh);
        listaEncuesta = rootView.findViewById(R.id.listaEncuesta);
        layoutItems = rootView.findViewById(R.id.layoutItems);
        txtBuscador = rootView.findViewById(R.id.txtBuscador);
        String txt = txtBuscador.getText().toString();
        listPreguntas = handler.filtrarListEncuestas(re, txt);
        insertarElementos(container, listPreguntas);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String txt = txtBuscador.getText().toString();
                listPreguntas = handler.filtrarListEncuestas(re, txt);
                adapter.clear();
                insertarElementos(container, listPreguntas);
                swipeRefreshLayout.setRefreshing(false);
            }
        });



        txtBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!txtBuscador.getText().toString().equalsIgnoreCase("")){
                    //Toast.makeText(getContext(), "nueva busqueda", Toast.LENGTH_SHORT).show();
                    adapter.clear();
                    String txt = txtBuscador.getText().toString();
                    listPreguntas = handler.filtrarListEncuestas(re, txt);
                    insertarElementos(container, listPreguntas);
                    swipeRefreshLayout.setRefreshing(false);
                }else{
                    adapter.clear();
                    listPreguntas = handler.filtrarListEncuestas(re, "");
                    insertarElementos(container, listPreguntas);
                    swipeRefreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return rootView;
    }

    public void LoadData(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        //re =sharedPreferences.getString(Constantes.RE,Constantes.NODATA);
        re =sharedPreferences.getString(Constantes.SUBCANAL,Constantes.NODATA);
    }

    public void insertarElementos(ViewGroup container, ArrayList<BaseEvaluacionPromotor> listPreguntas){
        listaEncuesta.setLayoutManager(new LinearLayoutManager(getContext()));


        Log.i(TAG, re);

        Log.i(TAG,listPreguntas.toString());

        adapter = new EncuestaListAdapter(listPreguntas);

        adapter.setOnClickListenner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Log.i(TAG,listPreguntas.get(listaEncuesta.getChildAdapterPosition(v)).getNombre_encuesta());
                Log.i(TAG,listPreguntas.get(listaEncuesta.getChildAdapterPosition(v)).getDescripcion());
                Log.i(TAG,listPreguntas.get(listaEncuesta.getChildAdapterPosition(v)).getCategoria());
                Log.i(TAG,listPreguntas.get(listaEncuesta.getChildAdapterPosition(v)).getRe());
                Log.i(TAG,listPreguntas.get(listaEncuesta.getChildAdapterPosition(v)).getPregunta());
                Log.i(TAG,listPreguntas.get(listaEncuesta.getChildAdapterPosition(v)).getTipo_pregunta());
                Log.i(TAG,listPreguntas.get(listaEncuesta.getChildAdapterPosition(v)).getPuntaje_por_pregunta());*/
                String encuesta = listPreguntas.get(listaEncuesta.getChildAdapterPosition(v)).getNombre_encuesta();
                abrirEncuesta(encuesta, container);
            }
        });

        listaEncuesta.setAdapter(adapter);

    }

    public void abrirEncuesta(String encuesta, ViewGroup container){

        Intent n = new Intent(getActivity(), EvaluacionEncuestaActivity.class);
        n.putExtra("encuesta",encuesta);
        startActivity(n);
        //layoutItems.setVisibility(View.GONE);
        //Intent n = new Intent(getActivity(), EvaluacionEncuestaActivity.class);
        //startActivity(n);


    }



}