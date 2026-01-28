package com.tesis.michelle.pin.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.Contracts.ContractInsertPromocion;
import com.tesis.michelle.pin.R;

/**
 * Created by Lucky Ecuador on 18/04/2018.
 */

public class AdapterPromo extends RecyclerView.Adapter<AdapterPromo.ViewHolder> {

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
      //  private TextView fechainicio;
        private TextView fecha;
        private TextView hora;
        private TextView codigo;
        private TextView sku;
        private TextView tipo_promo;
        private TextView subcategoria;
        private TextView mecanica;
        private TextView presentacion;
        private TextView brand;
        private TextView sku_nuevo;
        private TextView otras_marcas;
        private TextView categoria;
        private TextView ini_promo;
        private TextView fin_promo;
        private TextView campana;
        private TextView stock;
        private TextView anterior;
        private TextView actual;

        public ViewHolder(View v) {
            super(v);
            estado = (TextView) v.findViewById(R.id.lblEstado);
          //  fechainicio = (TextView) v.findViewById(R.id.lblfechainicial);
            fecha = (TextView) v.findViewById(R.id.lblTipo);
            hora = (TextView) v.findViewById(R.id.lblMarca);
            codigo = (TextView) v.findViewById(R.id.lblCategoria);
            sku = (TextView) v.findViewById(R.id.lblPromocion);
            tipo_promo = (TextView) v.findViewById(R.id.lblProductos);
            subcategoria = (TextView) v.findViewById(R.id.lblPeriodo);
            mecanica = (TextView) v.findViewById(R.id.lblPrecio);
            presentacion =(TextView) v.findViewById(R.id.lblMecanica);
            brand = (TextView) v.findViewById(R.id.lblFecha);
            sku_nuevo = (TextView) v.findViewById(R.id.lblSku);
            otras_marcas = (TextView) v.findViewById(R.id.lblOtrasMarcas);
            categoria = (TextView) v.findViewById(R.id.lblHora);
            ini_promo = (TextView) v.findViewById(R.id.lblIniPromo);
            fin_promo = (TextView) v.findViewById(R.id.lblFinPromo);
            campana = (TextView) v.findViewById(R.id.lblStock);
            anterior = (TextView) v.findViewById(R.id.lblAnterior);
            actual = (TextView) v.findViewById(R.id.lblActual);
        }
    }

    public AdapterPromo (Context context) {
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
                .inflate(R.layout.row6, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        cursor.moveToPosition(i);

        String consulta;

        String estado;
       // String fechainicio;
        String codigo;
        String fecha;
        String hora;
        String sku;
        String tipo_promo;
        String subcategoria;
        String mecanica;
        String presentacion;
        String brand;
        String sku_nuevo;
        String otras_marcas;
        String categoria;
        String ini_promo;
        String fin_promo;
    //    String stock;
        String campana;
        String anterior;
        String actual;

        consulta = cursor.getString(cursor.getColumnIndexOrThrow(Constantes.PENDIENTE_INSERCION));
        if (consulta.equals("1")) {
            estado = "No Enviado";
        }else{
            estado = "Enviado";
        }

        //fechainicio = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.FECHACTIVIDAD));
        fecha = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.FECHA));
        hora = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.HORA));
        codigo= cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.POS_NAME));
        sku = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.USUARIO));
        tipo_promo = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.TIPO_PROMOCION));
        subcategoria = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.SUBCATEGORIA));
        mecanica = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.MECANICA));
        presentacion = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.DESCRIPCION_PROMOCION));
        brand = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.MARCA));
        sku_nuevo = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.SKU));
        otras_marcas = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.OTRAS_MARCAS));
        categoria = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.CATEGORIA));
        ini_promo = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.INI_PROMO));
        fin_promo = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.FIN_PROMO));
 //       stock = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.AGOTAR_STOCK));
        campana = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.CAMPANA));
        anterior = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.PVC_ANTERIOR));
        actual = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPromocion.Columnas.PVC_ACTUAL));

        viewHolder.estado.setText(estado);
       // viewHolder.fechainicio.setText(fechainicio);
        viewHolder.codigo.setText(codigo);
        viewHolder.fecha.setText(fecha);
        viewHolder.hora.setText(hora);
        viewHolder.sku.setText(sku);
        viewHolder.tipo_promo.setText(tipo_promo);
        viewHolder.subcategoria.setText(subcategoria);
        viewHolder.mecanica.setText(mecanica);
        viewHolder.presentacion.setText(presentacion);
        viewHolder.brand.setText(brand);
        viewHolder.sku_nuevo.setText(sku_nuevo);
        viewHolder.otras_marcas.setText(otras_marcas);
        viewHolder.categoria.setText(categoria);
        viewHolder.ini_promo.setText(ini_promo);
        viewHolder.fin_promo.setText(fin_promo);
//        viewHolder.stock.setText(stock);
        viewHolder.campana.setText(campana);
        viewHolder.anterior.setText(anterior);
        viewHolder.actual.setText(actual);

    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }
}
