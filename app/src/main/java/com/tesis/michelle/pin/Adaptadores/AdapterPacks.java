package com.tesis.michelle.pin.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.Contracts.ContractInsertPacks2;
import com.tesis.michelle.pin.R;

/**
 * Created by Lucky Ecuador on 21/02/2017.
 */

public class AdapterPacks extends RecyclerView.Adapter<AdapterPacks.ViewHolder> {
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
        private TextView sku;
        private TextView categoriasec;
        private TextView subcategoriasec;
        private TextView brandsec;
        private TextView skusec;
        private TextView pvc;
        private TextView cantidadarmada;
        private TextView cantidadencontrada;

        private TextView regular;

        public ViewHolder(View v) {
            super(v);
            estado = (TextView) v.findViewById(R.id.lblEstado);
            fecha = (TextView) v.findViewById(R.id.lblfechainicial);
            hora = (TextView) v.findViewById(R.id.lblMara);
            codigo = (TextView) v.findViewById(R.id.lblCodigo);
            usuario = (TextView) v.findViewById(R.id.lblTipo);
            catego = (TextView) v.findViewById(R.id.lblCategoria);
            sub = (TextView) v.findViewById(R.id.lblSubcategoria);
            brand = (TextView) v.findViewById(R.id.lblBrand);
            sku = (TextView) v.findViewById(R.id.lblSku);
            categoriasec = (TextView) v.findViewById(R.id.lblCategoriaSec);
            subcategoriasec = (TextView) v.findViewById(R.id.lblSubCategoriaSec);
            brandsec = (TextView) v.findViewById(R.id.lblBrandSec);
            skusec = (TextView) v.findViewById(R.id.lblSkuSec);
            pvc = (TextView) v.findViewById(R.id.lblPvc);
            cantidadarmada = (TextView) v.findViewById(R.id.lblCantidadArmada);
            cantidadencontrada = (TextView) v.findViewById(R.id.lblCantidadEncontrada);
            regular = (TextView) v.findViewById(R.id.lblObservacion);

        }
    }

    public AdapterPacks(Context context) {
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
                .inflate(R.layout.status_packs, viewGroup, false);
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
        String sku;
        String categoriasec;
        String subcategoriasec;
        String brandsec;
        String skusec;
        String pvc;
        String cantidad_armada;
        String cantidad_encontrada;
        String observ;
        String sub;

        consulta = cursor.getString(cursor.getColumnIndexOrThrow(Constantes.PENDIENTE_INSERCION));
        if (consulta.equals("1")) {
            estado = "No Enviado";
        }else{
            estado = "Enviado";
        }

        codigo = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPacks2.Columnas.POS_NAME));
        fecha = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPacks2.Columnas.FECHA));
        hora= cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPacks2.Columnas.HORA));
        usuario = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPacks2.Columnas.USUARIO));
        catego = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPacks2.Columnas.CATEGORIA));
        sub = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPacks2.Columnas.SUBCATEGORIA));
        brand = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPacks2.Columnas.BRAND));
        sku = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPacks2.Columnas.SKU_CODE));
        categoriasec = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPacks2.Columnas.CATEGORIASEC));
        subcategoriasec = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPacks2.Columnas.SUBCATEGORIASEC));
        brandsec = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPacks2.Columnas.BRANDSEC));
        skusec = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPacks2.Columnas.SKU_CODESEC));
        pvc = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPacks2.Columnas.PVC));
        cantidad_armada = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPacks2.Columnas.CANTIDAD_ARMADA));
        cantidad_encontrada = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPacks2.Columnas.CANTIDAD_ENCONTRADA));
        observ = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPacks2.Columnas.OBSERVACION));

        viewHolder.estado.setText(estado);
        viewHolder.fecha.setText(fecha);
        viewHolder.hora.setText(hora);
        viewHolder.codigo.setText(codigo);
        viewHolder.usuario.setText(usuario);
        viewHolder.catego.setText(catego);
        viewHolder.sub.setText(sub);
        viewHolder.brand.setText(brand);
        viewHolder.sku.setText(sku);
        viewHolder.categoriasec.setText(categoriasec);
        viewHolder.subcategoriasec.setText(subcategoriasec);
        viewHolder.brandsec.setText(brandsec);
        viewHolder.skusec.setText(skusec);
        viewHolder.pvc.setText(pvc);
        viewHolder.cantidadarmada.setText(cantidad_armada);
        viewHolder.cantidadencontrada.setText(cantidad_encontrada);
        viewHolder.regular.setText(observ);
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }
}