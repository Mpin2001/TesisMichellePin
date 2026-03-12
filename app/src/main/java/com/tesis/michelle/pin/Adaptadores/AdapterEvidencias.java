package com.tesis.michelle.pin.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tesis.michelle.pin.DataBase.Provider;
import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.Contracts.ContractInsertEvidencias;
import com.tesis.michelle.pin.DataBase.DatabaseHelper;
import com.tesis.michelle.pin.R;

public class AdapterEvidencias extends RecyclerView.Adapter<AdapterEvidencias.ViewHolder> {

    private Cursor cursor;
    private Context context;

    interface OnItemClickListener {
        void onClick(ViewHolder holder, String idContacto);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView estado;
        private TextView fecha;
        private TextView hora;
        private TextView codigo;     // PDV / local
        private TextView usuario;
        private TextView nombres;
        private TextView cedula;
        private TextView celular;
        private TextView marca;
        private TextView categoria;
        private TextView comentario;

        public ViewHolder(View v) {
            super(v);
            estado    = v.findViewById(R.id.lblEstado);
            fecha     = v.findViewById(R.id.lblfechainicial);
            hora      = v.findViewById(R.id.lblMara);
            codigo    = v.findViewById(R.id.lblPDV);
            usuario   = v.findViewById(R.id.lblTipo);
            nombres   = v.findViewById(R.id.lblNombres);
            cedula    = v.findViewById(R.id.lblCedula);
            celular   = v.findViewById(R.id.lblCelular);
            marca     = v.findViewById(R.id.lblMarca);
              categoria = v.findViewById(R.id.lblCategoria);
            comentario = v.findViewById(R.id.lblSub);
        }
    }

    public AdapterEvidencias(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return (cursor != null) ? cursor.getCount() : 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.status_evidencias, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        cursor.moveToPosition(i);

        DatabaseHelper handler = new DatabaseHelper(context, Provider.DATABASE_NAME, null, 1);

        // Estado enviado / no enviado
        String pendiente = cursor.getString(cursor.getColumnIndexOrThrow(Constantes.PENDIENTE_INSERCION));
        String estado = pendiente.equals("1") ? "No Enviado" : "Enviado";

        // Campos del contract
        String codigoPdv  = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertEvidencias.Columnas.CODIGO));
        String fecha      = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertEvidencias.Columnas.FECHA));
        String hora       = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertEvidencias.Columnas.HORA));
        String usuario    = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertEvidencias.Columnas.USUARIO));
        String nombres    = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertEvidencias.Columnas.NOMBRES));
        String cedula     = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertEvidencias.Columnas.CEDULA));
        String celular    = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertEvidencias.Columnas.CELULAR));
        String marca      = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertEvidencias.Columnas.MARCA));
         String categoria  = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertEvidencias.Columnas.POS_NAME));
        String comentario = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertEvidencias.Columnas.COMENTARIO));

        // Bind en el ViewHolder
        viewHolder.estado.setText(estado);
        viewHolder.fecha.setText(fecha);
        viewHolder.hora.setText(hora);
        viewHolder.codigo.setText(handler.getPosNamePdv(codigoPdv));
        viewHolder.usuario.setText(usuario);
        viewHolder.nombres.setText(nombres != null ? nombres : "");
        viewHolder.cedula.setText(cedula != null ? cedula : "");
        viewHolder.celular.setText(celular != null ? celular : "");
        viewHolder.marca.setText(marca != null ? marca : "");
          viewHolder.categoria.setText(categoria != null ? categoria : "");
        viewHolder.comentario.setText(comentario != null ? comentario : "");
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }
}