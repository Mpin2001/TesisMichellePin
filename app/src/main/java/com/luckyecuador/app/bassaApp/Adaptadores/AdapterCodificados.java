package com.luckyecuador.app.bassaApp.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.luckyecuador.app.bassaApp.Conexion.Constantes;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertCodificados;
import com.luckyecuador.app.bassaApp.R;

/**
 * Created by Lucky Ecuador on 18/04/2018.
 */

public class AdapterCodificados extends RecyclerView.Adapter<AdapterCodificados.ViewHolder> {

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
        private TextView pos_name;
        private TextView usuario;
        private TextView sector;
        private TextView categoria;

        private TextView subcategoria;
        private TextView presentacion;
        private TextView fabricante;
        private TextView marca;
        private TextView contenido;
        private TextView variante;
        private TextView sku_code;
        private TextView presencia;


        public ViewHolder(View v) {
            super(v);
            estado = (TextView) v.findViewById(R.id.lblEstado);
            //fechainicio = (TextView) v.findViewById(R.id.lblfechainicial);
            fecha = (TextView) v.findViewById(R.id.lblFecha);
            hora = (TextView) v.findViewById(R.id.lblHora);
            codigo = (TextView) v.findViewById(R.id.lblCodigo);
            pos_name = (TextView) v.findViewById(R.id.lblPDV);
            usuario = (TextView) v.findViewById(R.id.lblUsuario);
            sector = (TextView) v.findViewById(R.id.lblSector);
            categoria = (TextView) v.findViewById(R.id.lblCategoria);
            subcategoria = (TextView) v.findViewById(R.id.lblSubcategoria);
            presentacion = (TextView) v.findViewById(R.id.lblPresentacion);
            fabricante = (TextView) v.findViewById(R.id.lblFabricante);
            marca = (TextView) v.findViewById(R.id.lblBrand);
            contenido = (TextView) v.findViewById(R.id.lblContenido);
            variante = (TextView) v.findViewById(R.id.lblVariante);
            sku_code = (TextView) v.findViewById(R.id.lblSKU);
            presencia = (TextView) v.findViewById(R.id.lblPresencia);

        }
    }


    public AdapterCodificados(Context context) {
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
                .inflate(R.layout.status_codificados, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        cursor.moveToPosition(i);

        String consulta;

        String estado;
        String fecha;
        String hora;
        String codigo;
        String pos_name;
        String usuario;
        String sector;
        String categoria;
        String subcategoria;
        String presentacion;
        String fabricante;
        String marca;
        String contenido;
        String variante;
        String sku_code;
        String presencia;

        consulta = cursor.getString(cursor.getColumnIndexOrThrow(Constantes.PENDIENTE_INSERCION));
        if(consulta.equals("1")){
            estado = "No Enviado";
        }else{
            estado = "Enviado";
        }


        fecha = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCodificados.Columnas.FECHA));
        hora = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCodificados.Columnas.HORA));
        codigo = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCodificados.Columnas.CODIGO));
        pos_name = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCodificados.Columnas.POS_NAME));
        usuario = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCodificados.Columnas.USUARIO));
        sector = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCodificados.Columnas.SECTOR));
        categoria= cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCodificados.Columnas.CATEGORIA));
        subcategoria = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCodificados.Columnas.SUBCATEGORIA));
        presentacion = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCodificados.Columnas.PRESENTACION));
        fabricante = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCodificados.Columnas.FABRICANTE));
        marca = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCodificados.Columnas.BRAND));
        contenido = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCodificados.Columnas.CONTENIDO));
        variante = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCodificados.Columnas.VARIANTE));
        sku_code = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCodificados.Columnas.SKU_CODE));
        presencia = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCodificados.Columnas.PRESENCIA));



        viewHolder.estado.setText(estado);
        viewHolder.fecha.setText(fecha);
        viewHolder.hora.setText(hora);
        viewHolder.codigo.setText(codigo);
        viewHolder.pos_name.setText(pos_name);
        viewHolder.usuario.setText(usuario);
        viewHolder.sector.setText(sector);
        viewHolder.categoria.setText(categoria);
        viewHolder.subcategoria.setText(subcategoria);
        viewHolder.presentacion.setText(presentacion);
        viewHolder.fabricante.setText(fabricante);
        viewHolder.marca.setText(marca);
        viewHolder.contenido.setText(contenido);
        viewHolder.variante.setText(variante);
        viewHolder.sku_code.setText(sku_code);
        viewHolder.presencia.setText(presencia);


    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }
}
