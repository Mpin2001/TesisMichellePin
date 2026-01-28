package com.luckyecuador.app.bassaApp.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luckyecuador.app.bassaApp.Conexion.Constantes;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertFotografico;
import com.luckyecuador.app.bassaApp.DataBase.DatabaseHelper;
import com.luckyecuador.app.bassaApp.R;

public class AdapterFotografico extends RecyclerView.Adapter<AdapterFotografico.ViewHolder> {
    private Cursor cursor;
    private Context context;
    DatabaseHelper handler;

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
        private TextView subcategoria;
        private TextView marca;
        private TextView local;
        private TextView tipoLogro;

        private TextView status;
        private TextView observacion;
        private TextView categoria;

        public ViewHolder(View v) {
            super(v);

            estado = (TextView) v.findViewById(R.id.lblEstado);
            fecha = (TextView) v.findViewById(R.id.lblFecha);
            hora = (TextView) v.findViewById(R.id.lblHora);
            codigo = (TextView) v.findViewById(R.id.lblCodigo);
            local = (TextView) v.findViewById(R.id.lblNombrecomercial);
//            subcategoria = (TextView) v.findViewById(R.id.lblNombrecomercial);
            categoria = (TextView) v.findViewById(R.id.lblCategoria);
            marca = (TextView) v.findViewById(R.id.lblMarca);
            tipoLogro = (TextView) v.findViewById(R.id.lblTipoLogro);
            status = (TextView) v.findViewById(R.id.lblStatus);
            observacion = (TextView) v.findViewById(R.id.lblObservacion);
            categoria = (TextView) v.findViewById(R.id.lblCategoria);
        }
    }

    public AdapterFotografico(Context context) {
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
                .inflate(R.layout.row9, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        cursor.moveToPosition(i);

        String consulta;
        String estado;
        String codigo;
        String fecha;
        String hora;
        String subcategoria;
        String marca;
        String local;
        String tipoLogro;
        String status;
        String observacion;
        String categoria;

        consulta = cursor.getString(cursor.getColumnIndexOrThrow(Constantes.PENDIENTE_INSERCION));
        if (consulta.equals("1")) {
            estado = "No Enviado";
        }else{
            estado = "Enviado";
        }

        fecha = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertFotografico.Columnas.FECHA));
        hora = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertFotografico.Columnas.HORA));
        local = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertFotografico.Columnas.POS_NAME));
//        codigo = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertFotografico.Columnas.CODIGO));
//        subcategoria = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertFotografico.Columnas.SUBCATEGORIA));
        marca = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertFotografico.Columnas.MARCA));
        tipoLogro = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertFotografico.Columnas.LOGRO));
        status = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertFotografico.Columnas.STATUS));
        observacion = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertFotografico.Columnas.OBSERVACION));
        categoria = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertFotografico.Columnas.CATEGORIA));

        viewHolder.estado.setText(estado);
        viewHolder.fecha.setText(fecha);
        viewHolder.hora.setText(hora);
        viewHolder.local.setText(local);
//        viewHolder.codigo.setText(codigo);
//        viewHolder.subcategoria.setText(subcategoria);
        viewHolder.marca.setText(marca);
        viewHolder.tipoLogro.setText(tipoLogro);
        viewHolder.status.setText(status);
        viewHolder.observacion.setText(observacion);
        viewHolder.categoria.setText(categoria);
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }

}
