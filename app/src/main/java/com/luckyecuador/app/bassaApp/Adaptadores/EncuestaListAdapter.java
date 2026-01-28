package com.luckyecuador.app.bassaApp.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luckyecuador.app.bassaApp.Clase.BaseEvaluacionPromotor;
import com.luckyecuador.app.bassaApp.R;

import java.util.ArrayList;

public class EncuestaListAdapter extends RecyclerView.Adapter<EncuestaListAdapter.EvaluacionViewHolder> implements View.OnClickListener {

    ArrayList<BaseEvaluacionPromotor> evaluacionList;
    private View.OnClickListener listener;

    public EncuestaListAdapter(ArrayList<BaseEvaluacionPromotor> evaluacionList){
        this.evaluacionList = evaluacionList;
    }

    @NonNull
    @Override
    public EvaluacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluacion,null,false);
        view.setOnClickListener(this);
        return new EvaluacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EvaluacionViewHolder holder, int position) {

        //String tipoEvaluacion = evaluacionList.get(position).getTipo();
/*
        if (tipoEvaluacion.equals("EXAMEN")){
            holder.viewImg.setImageResource(R.drawable.examen_item_icon);
        } else {
            holder.viewImg.setImageResource(R.drawable.simulacro_item_icon);
        }
*/
            holder.viewEvaluacion.setText(evaluacionList.get(position).getNombre_encuesta());
            holder.viewF_descripcion.setText(evaluacionList.get(position).getDescripcion());
            //holder.viewF_inicio.setText("Fecha Inicio : ");
            //holder.viewF_limite.setText("Fecha Limite : ");



    }

    @Override
    public int getItemCount() {
        return evaluacionList.size();
    }

    public void setOnClickListenner(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {

        if(listener != null){
            listener.onClick(v);
        }else{

        }

    }

    public void update(ArrayList<BaseEvaluacionPromotor> data){
        data.clear();
        data.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {

        if (evaluacionList.size() > 0){
            evaluacionList.clear();
            notifyDataSetChanged();
        }

    }



    public class EvaluacionViewHolder extends RecyclerView.ViewHolder {

        TextView viewEvaluacion , viewF_descripcion ,viewF_inicio , viewF_limite;
        ImageView viewImg;

        public EvaluacionViewHolder(@NonNull View itemView) {
            super(itemView);

            viewImg = itemView.findViewById(R.id.imgEvaluacion);
            viewEvaluacion = itemView.findViewById(R.id.tv_nombre);
            viewF_descripcion = itemView.findViewById(R.id.tv_descripcion);
            //viewF_inicio = itemView.findViewById(R.id.tv_fecha_inicio);
            //viewF_limite = itemView.findViewById(R.id.tv_fecha_limite);

        }
    }
}
