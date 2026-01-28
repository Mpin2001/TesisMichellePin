package com.tesis.michelle.pin.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.Contracts.ContractInsertTracking;
import com.tesis.michelle.pin.R;


public class AdapterTracking extends RecyclerView.Adapter<AdapterTracking.ViewHolder> {

    private Cursor cursor;
    private Context context;


    // Instancia de escucha
    private OnItemClickListener escucha;

    /**
     * Interfaz para escuchar clicks del recycler
     */
    interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idContacto);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        private TextView estado;
        private TextView codigo;
        private TextView fecha;
        private TextView hora;
        private TextView usuario;
        private TextView categoria;
        private TextView descripcion;
        private TextView status;
        private TextView pop;
        private TextView mecanica;
        private TextView comentario;

        public ViewHolder(View v) {
            super(v);
            estado = (TextView) v.findViewById(R.id.lblEstado);
            codigo = (TextView) v.findViewById(R.id.lblCodigo);
            fecha = (TextView) v.findViewById(R.id.lblFecha);
            hora = (TextView) v.findViewById(R.id.lblHora);
            usuario = (TextView) v.findViewById(R.id.lblUsuario);
            categoria = (TextView) v.findViewById(R.id.lblCategoria);
            descripcion = (TextView) v.findViewById(R.id.lblDescripcion);
            status = (TextView) v.findViewById(R.id.lblStatus);
            pop = (TextView) v.findViewById(R.id.lblImplementacion);
            mecanica = (TextView) v.findViewById(R.id.lblMecanica);
            comentario = (TextView) v.findViewById(R.id.lblComentario);
        }
    }

    public AdapterTracking(Context context) {
        this.context= context;

    }

    @Override
    public int getItemCount() {
        if (cursor!=null)
            return cursor.getCount();
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.status_tracking, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        cursor.moveToPosition(i);

        String consulta;
        String estado = "";

        String codigo = "";
        String fecha = "";
        String hora = "";
        String usuario = "";
        String categoria = "";
        String descripcion = "";
        String status = "";
        String pop = "";
        String mecanica = "";
        String comentario = "";

        try{
            consulta = cursor.getString(cursor.getColumnIndexOrThrow(Constantes.PENDIENTE_INSERCION));
            if(consulta.equals("1")){
                estado = "No Enviado";
            }else{
                estado = "Enviado";
            }

            codigo = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertTracking.Columnas.CODIGO));
            fecha = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertTracking.Columnas.FECHA));
            hora = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertTracking.Columnas.HORA));
            usuario = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertTracking.Columnas.USUARIO));
            categoria = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertTracking.Columnas.CATEGORIA));
            descripcion = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertTracking.Columnas.DESCRIPCION));
            status = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertTracking.Columnas.STATUS_ACTIVIDAD));
            pop = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertTracking.Columnas.IMPLEMENTACION_POP));
            mecanica = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertTracking.Columnas.MECANICA));
            comentario = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertTracking.Columnas.COMENTARIO));
        }catch (Exception e){
            Log.i("EXHIBICIONES", e.getMessage());
        }

        viewHolder.estado.setText(estado);
        viewHolder.codigo.setText(codigo);
        viewHolder.fecha.setText(fecha);
        viewHolder.hora.setText(hora);
        viewHolder.usuario.setText(usuario);
        viewHolder.categoria.setText(categoria);
        viewHolder.descripcion.setText(descripcion);
        viewHolder.status.setText(status);
        viewHolder.pop.setText(pop);
        viewHolder.mecanica.setText(mecanica);
        viewHolder.comentario.setText(comentario);
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }
}