package com.luckyecuador.app.bassaApp.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luckyecuador.app.bassaApp.Conexion.Constantes;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPrecios;
import com.luckyecuador.app.bassaApp.R;

/**
 * Created by Lucky Ecuador on 21/02/2017.
 */

public class AdapterPrecios extends RecyclerView.Adapter<AdapterPrecios.ViewHolder> {
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
        private TextView catego;
        private TextView sub;
        private TextView brand;
        private TextView fabricante; //nuevo
        private TextView sku;
        private TextView regular;
        private TextView promocion;
        private TextView precio_descuento; //nuevo
        private TextView mayorista;
        private TextView var_sugerido;
        private TextView ofer;

        public ViewHolder(View v) {
            super(v);
            estado = (TextView) v.findViewById(R.id.lblEstado);
            fecha = (TextView) v.findViewById(R.id.lblfechainicial);
            hora = (TextView) v.findViewById(R.id.lblMara);
            codigo = (TextView) v.findViewById(R.id.lblCategoria);
            usuario = (TextView) v.findViewById(R.id.lblTipo);
            catego = (TextView) v.findViewById(R.id.lblProductos);
            brand = (TextView) v.findViewById(R.id.lblCantidad);
            fabricante = (TextView) v.findViewById(R.id.lblFabricante); //nuevo
            sku = (TextView) v.findViewById(R.id.lblPcont);
            regular = (TextView) v.findViewById(R.id.lblFecha);
            promocion = (TextView) v.findViewById(R.id.lblHora);
            precio_descuento = (TextView) v.findViewById(R.id.lblPrecioDescuento); //nuevo
            mayorista = (TextView) v.findViewById(R.id.lblPmayorista);
            var_sugerido = (TextView) v.findViewById(R.id.lblVarSugerido);
            sub = (TextView) v.findViewById(R.id.lblSub);
            ofer = (TextView) v.findViewById(R.id.lblOfert);
        }
    }

    public AdapterPrecios(Context context) {
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
                .inflate(R.layout.status_precios, viewGroup, false);
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
        String catego;
        String brand;
        String fabricante;
        String precio_descuento;
        String sku;
        String regular;
        String promocion;
        String mayorista;
        String var_sugerido;
        String sub;
        String ofer;

        consulta = cursor.getString(cursor.getColumnIndexOrThrow(Constantes.PENDIENTE_INSERCION));
        if (consulta.equals("1")) {
            estado = "No Enviado";
        }else{
            estado = "Enviado";
        }

        codigo = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPrecios.Columnas.POS_NAME));
        fecha = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPrecios.Columnas.FECHA));
        hora= cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPrecios.Columnas.HORA));
        usuario = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPrecios.Columnas.USUARIO));
        catego = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPrecios.Columnas.CATEGORIA));
        sub = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPrecios.Columnas.SUBCATEGORIA));
        brand = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPrecios.Columnas.BRAND));
        fabricante = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPrecios.Columnas.FABRICANTE));
        precio_descuento = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPrecios.Columnas.PRECIO_DESCUENTO));
        sku = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPrecios.Columnas.SKU_CODE));
        regular = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPrecios.Columnas.PREGULAR));
        promocion = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPrecios.Columnas.PPROMOCION));
        mayorista = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPrecios.Columnas.POFERTA));
        var_sugerido = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPrecios.Columnas.VAR_SUGERIDO));
        ofer = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPrecios.Columnas.POFERTA));

        viewHolder.estado.setText(estado);
        viewHolder.fecha.setText(fecha);
        viewHolder.hora.setText(hora);
        viewHolder.codigo.setText(codigo);
        viewHolder.usuario.setText(usuario);
        viewHolder.catego.setText(catego);
        viewHolder.sub.setText(sub);
        viewHolder.brand.setText(brand);
        viewHolder.fabricante.setText(fabricante);
        viewHolder.precio_descuento.setText(precio_descuento);
        viewHolder.sku.setText(sku);
        viewHolder.regular.setText(regular);
        viewHolder.promocion.setText(promocion);
        viewHolder.mayorista.setText(mayorista);
        viewHolder.var_sugerido.setText(var_sugerido);
        viewHolder.ofer.setText(ofer);

    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }
}