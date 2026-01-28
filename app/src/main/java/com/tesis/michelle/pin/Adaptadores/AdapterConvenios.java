package com.tesis.michelle.pin.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.Contracts.ContractInsertConvenios;
import com.tesis.michelle.pin.R;

public class AdapterConvenios extends RecyclerView.Adapter<AdapterConvenios.ViewHolder>{

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
        private TextView fecha;
        private TextView hora;
        private TextView codigo;
        private TextView usuario;
        private TextView negocio;
        private TextView tipo_exh;
        private TextView contratada;
        private TextView comentario;

        public ViewHolder(View v) {
            super(v);
            estado = (TextView) v.findViewById(R.id.lblEstado);
            fecha = (TextView) v.findViewById(R.id.lblfechainicial);
            hora = (TextView) v.findViewById(R.id.lblHora);
            codigo = (TextView) v.findViewById(R.id.lblCodigo);
            usuario = (TextView) v.findViewById(R.id.lblUsuario);
            negocio = (TextView) v.findViewById(R.id.lblNegocio);
            tipo_exh = (TextView) v.findViewById(R.id.lblTareas);
            contratada = (TextView) v.findViewById(R.id.lblEjecutada);
            comentario = (TextView) v.findViewById(R.id.lblComentario);

        }
    }

    public AdapterConvenios(Context context) {
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
                .inflate(R.layout.status_convenios, viewGroup, false);
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
        String usuario;
        String negocio;
        String tipo_exh;
        String contratada;
        String comentario;

        consulta = cursor.getString(cursor.getColumnIndexOrThrow(Constantes.PENDIENTE_INSERCION));
        if (consulta.equals("1")) {
            estado = "No Enviado";
        }else{
            estado = "Enviado";
        }

        codigo = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertConvenios.Columnas.CODIGO));
        fecha = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertConvenios.Columnas.FECHA));
        hora= cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertConvenios.Columnas.HORA));
        usuario = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertConvenios.Columnas.USUARIO));
        negocio = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertConvenios.Columnas.UNIDAD_DE_NEGOCIO));
        tipo_exh = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertConvenios.Columnas.TIPO_EXHIBICION));
        contratada = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertConvenios.Columnas.CONTRATADA));
        comentario = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertConvenios.Columnas.OBSERVACION));
       

        viewHolder.estado.setText(estado);
        viewHolder.fecha.setText(fecha);
        viewHolder.hora.setText(hora);
        viewHolder.codigo.setText(codigo);
        viewHolder.usuario.setText(usuario);
        viewHolder.negocio.setText(negocio);
        viewHolder.tipo_exh.setText(tipo_exh);
        viewHolder.contratada.setText(contratada);
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
