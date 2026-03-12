package com.tesis.michelle.pin.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.Contracts.ContractInsertNovedades;
import com.tesis.michelle.pin.DataBase.DatabaseHelper;
import com.tesis.michelle.pin.DataBase.Provider;
import com.tesis.michelle.pin.R;

public class AdapterNovedades extends RecyclerView.Adapter<AdapterNovedades.ViewHolder> {
    private Cursor cursor;
    private Context context;

    private OnItemClickListener escucha;

    interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idContacto);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // ----------- HEADERS (fila 1 - txt) -----------
        private TextView txtEstado;
        private TextView txtFecha;
        private TextView txtHora;
        private TextView txtNombrePdv;
        private TextView txtUsuario;
        private TextView txtTipoNovedad;
        private TextView txtMarca;
        private TextView txtSku;
        private TextView txtLote;
        private TextView txtObservacion1;
        private TextView txtObservacion2;
        private TextView txtObservacion3;
        private TextView txtTipoUnidades;
        private TextView txtFechaVencimiento;
        private TextView txtFechaInicio;
        private TextView txtNumeroFactura;
        private TextView txtTipoImplementacion;
        private TextView txtMecanica;
        private TextView txtPrecioAnterior;
        private TextView txtPrecioPromocion;
        private TextView txtCantidad;

        // ----------- DATOS (fila 2 - lbl) -----------
        private TextView lblEstado;
        private TextView lblfecha;
        private TextView lblHora;
        private TextView lblNombrePdv;
        private TextView lblUsuario;
        private TextView lblTipoNovedad;
        private TextView lblMarca;
        //private TextView lblSku;
        private TextView lblLote;
        private TextView lblObservacion1;
        private TextView lblObservacion2;
        private TextView lblObservacion3;
        private TextView lblTipo;
        private TextView lblFechavencimento;
        private TextView lblFechaInicio;
        private TextView lblNumeroFactura;
        private TextView lblTipoImplementacion;
        private TextView lblTipoMecanica;
        private TextView lblPrecioAnterior;
        private TextView lblPrecioPromocio;
        private TextView lblCantidad;

        public ViewHolder(View v) {
            super(v);
            // ----------- HEADERS (fila 1 - txt) -----------
            txtEstado           = v.findViewById(R.id.txtEstado);
            txtFecha            = v.findViewById(R.id.txtFecha);
            txtHora             = v.findViewById(R.id.txtHora);
            txtNombrePdv        = v.findViewById(R.id.txtNombrePdv);
            txtUsuario          = v.findViewById(R.id.txtUsuario);
            txtTipoNovedad      = v.findViewById(R.id.txtTipoNovedad);
            txtMarca            = v.findViewById(R.id.txtMarca);
           // txtSku              = v.findViewById(R.id.txtSku);
            txtLote             = v.findViewById(R.id.txtLote);
            txtObservacion1     = v.findViewById(R.id.txtObservacion1);
            txtObservacion2     = v.findViewById(R.id.txtObservacion2);
            txtObservacion3     = v.findViewById(R.id.txtObservacion3);
            txtTipoUnidades     = v.findViewById(R.id.txtTipoUnidades);
            txtFechaVencimiento = v.findViewById(R.id.txtFechaVencimiento);
            txtFechaInicio      = v.findViewById(R.id.txtFechaInicio);
            txtNumeroFactura    = v.findViewById(R.id.txtNumeroFactura);
            txtTipoImplementacion = v.findViewById(R.id.txtTipoImplementacion);
            txtMecanica         = v.findViewById(R.id.txtMecanica);
            txtPrecioAnterior   = v.findViewById(R.id.txtPrecioAnterior);
            txtPrecioPromocion  = v.findViewById(R.id.txtPrecioPromocion);
            txtCantidad         = v.findViewById(R.id.txtCantidad);

            // ----------- DATOS (fila 2 - lbl) -----------
            lblEstado           = v.findViewById(R.id.lblEstado);
            lblfecha            = v.findViewById(R.id.lblfecha);
            lblHora             = v.findViewById(R.id.lblHora);
            lblNombrePdv        = v.findViewById(R.id.lblNombrePdv);
            lblUsuario          = v.findViewById(R.id.lblUsuario);
            lblTipoNovedad      = v.findViewById(R.id.lblTipoNovedad);
            lblMarca            = v.findViewById(R.id.lblMarca);
            //lblSku              = v.findViewById(R.id.lblSku);
            lblLote             = v.findViewById(R.id.lblLote);
            lblObservacion1     = v.findViewById(R.id.lblObservacion1);
            lblObservacion2     = v.findViewById(R.id.lblObservacion2);
            lblObservacion3     = v.findViewById(R.id.lblObservacion3);
            lblTipo             = v.findViewById(R.id.lblTipo);
            lblFechavencimento  = v.findViewById(R.id.lblFechavencimento);
            lblFechaInicio      = v.findViewById(R.id.lblFechaInicio);
            lblNumeroFactura    = v.findViewById(R.id.lblNumeroFactura);
            lblTipoImplementacion = v.findViewById(R.id.lblTipoImplementacion);
            lblTipoMecanica     = v.findViewById(R.id.lblTipoMecanica);
            lblPrecioAnterior   = v.findViewById(R.id.lblPrecioAnterior);
            lblPrecioPromocio   = v.findViewById(R.id.lblPrecioPromocio);
            lblCantidad         = v.findViewById(R.id.lblCantidad);
        }
    }

    public AdapterNovedades(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (cursor != null)
            return cursor.getCount();
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.status_novedades, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        cursor.moveToPosition(i);

        DatabaseHelper handler = new DatabaseHelper(context, Provider.DATABASE_NAME, null, 1);

        // ── Leer datos del cursor ──────────────────────────────────────────────
        String consulta = cursor.getString(cursor.getColumnIndexOrThrow(Constantes.PENDIENTE_INSERCION));
        String estado   = consulta.equals("1") ? "No Enviado" : "Enviado";

        String fecha       = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertNovedades.Columnas.FECHA));
        String hora        = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertNovedades.Columnas.HORA));
        String punto_venta = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertNovedades.Columnas.POS_NAME));
        String usuario     = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertNovedades.Columnas.USUARIO));
        String tipo_novedad= cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertNovedades.Columnas.TIPO_NOVEDAD));
        String observacion = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertNovedades.Columnas.OBSERVACION));
        //String sku         = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertNovedades.Columnas.SKU_CODE));
        String cantidad    = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertNovedades.Columnas.CANTIDAD));
        String fecha_vencimiento    = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertNovedades.Columnas.FECHA_VENCIMIENTO));
      //  String cantidad    = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertNovedades.Columnas.CANTIDAD));

        String marca              = "";
        String lote               = "";
        String fechaVencimiento   = "";
        String comentarioLote     = "";
        String comentarioFactura  = "";
     //   String comentarioSku      = "";
        String tipo               = "";
        String fechaInicio        = "";
        String mecanica           = "";
        String precioAnterior     = "";
        String precioPromocion    = "";

        // ── Ocultar todos los campos primero ──────────────────────────────────
        ocultarTodosLosCampos(viewHolder);

        // ── Mostrar campos según tipo de novedad ──────────────────────────────
        mostrarCamposPorTipoNovedad(viewHolder, tipo_novedad);

        // ── Asignar DATOS a la fila lbl (fila 2) ─────────────────────────────
        viewHolder.lblEstado.setText(estado);
        viewHolder.lblfecha.setText(fecha);
        viewHolder.lblHora.setText(hora);
        viewHolder.lblNombrePdv.setText(punto_venta);
        viewHolder.lblUsuario.setText(usuario);
        viewHolder.lblTipoNovedad.setText(tipo_novedad);

        // Campos específicos por tipo
        switch (tipo_novedad.toUpperCase()) {
            case "LUZ PÚBLICA DAÑADA":
                viewHolder.lblMarca.setText(marca);
               // viewHolder.lblSku.setText(sku);
               // viewHolder.lblLote.setText(lote);
              // viewHolder.lblObservacion1.setText(observacion);
              //  viewHolder.lblObservacion2.setText(comentarioFactura);
               // viewHolder.lblObservacion3.setText(comentarioSku);
               // viewHolder.lblTipo.setText(tipo);
                viewHolder.lblCantidad.setText(cantidad);
                viewHolder.lblFechavencimento.setText(fecha_vencimiento);
                break;

            case "RUIDO EXCESIVO / ESCÁNDALO":
                viewHolder.lblMarca.setText(marca);
             //   viewHolder.lblSku.setText(sku);
                viewHolder.lblObservacion1.setText(observacion);
               // viewHolder.lblCantidad.setText(cantidad);
                break;

            case "PERSONAS SOSPECHOSAS EN EL SECTOR":
                viewHolder.lblMarca.setText(marca);
               // viewHolder.lblSku.setText(sku);
                viewHolder.lblObservacion1.setText(observacion);
                break;

            case "PROMOCIONES NO AUTORIZADAS":
                viewHolder.lblMarca.setText(marca);
              //  viewHolder.lblSku.setText(sku);
                viewHolder.lblObservacion1.setText(observacion);
                viewHolder.lblFechaInicio.setText(fechaInicio);
                viewHolder.lblPrecioAnterior.setText(precioAnterior);
                viewHolder.lblPrecioPromocio.setText(precioPromocion);
                viewHolder.lblTipoMecanica.setText(mecanica);
                break;

            case "OTROS":
                viewHolder.lblObservacion1.setText(observacion);
                break;
        }
    }

    /**
     * Oculta todos los campos dinámicos (txt = headers, lbl = datos).
     * Los campos comunes (estado, fecha, hora, pdv, usuario, tipoNovedad)
     * se manejan en mostrarCamposPorTipoNovedad().
     */
    private void ocultarTodosLosCampos(ViewHolder viewHolder) {
        // Ocultar headers dinámicos (fila txt)
        viewHolder.txtMarca.setVisibility(View.GONE);
       // viewHolder.txtSku.setVisibility(View.GONE);
        viewHolder.txtLote.setVisibility(View.GONE);
        viewHolder.txtObservacion1.setVisibility(View.GONE);
        viewHolder.txtObservacion2.setVisibility(View.GONE);
        viewHolder.txtObservacion3.setVisibility(View.GONE);
        viewHolder.txtTipoUnidades.setVisibility(View.GONE);
        viewHolder.txtFechaVencimiento.setVisibility(View.GONE);
        viewHolder.txtFechaInicio.setVisibility(View.GONE);
        viewHolder.txtNumeroFactura.setVisibility(View.GONE);
        viewHolder.txtTipoImplementacion.setVisibility(View.GONE);
        viewHolder.txtMecanica.setVisibility(View.GONE);
        viewHolder.txtPrecioAnterior.setVisibility(View.GONE);
        viewHolder.txtPrecioPromocion.setVisibility(View.GONE);
        viewHolder.txtCantidad.setVisibility(View.GONE);

        // Ocultar datos dinámicos (fila lbl)
        viewHolder.lblMarca.setVisibility(View.GONE);
       // viewHolder.lblSku.setVisibility(View.GONE);
        viewHolder.lblLote.setVisibility(View.GONE);
        viewHolder.lblObservacion1.setVisibility(View.GONE);
        viewHolder.lblObservacion2.setVisibility(View.GONE);
        viewHolder.lblObservacion3.setVisibility(View.GONE);
        viewHolder.lblTipo.setVisibility(View.GONE);
        viewHolder.lblFechavencimento.setVisibility(View.GONE);
        viewHolder.lblFechaInicio.setVisibility(View.GONE);
        viewHolder.lblNumeroFactura.setVisibility(View.GONE);
        viewHolder.lblTipoImplementacion.setVisibility(View.GONE);
        viewHolder.lblTipoMecanica.setVisibility(View.GONE);
        viewHolder.lblPrecioAnterior.setVisibility(View.GONE);
        viewHolder.lblPrecioPromocio.setVisibility(View.GONE);
        viewHolder.lblCantidad.setVisibility(View.GONE);
    }

    /**
     * Muestra los campos según el tipo de novedad.
     * txt = fila de HEADERS (arriba)
     * lbl = fila de DATOS   (abajo)
     */
    private void mostrarCamposPorTipoNovedad(ViewHolder viewHolder, String tipoNovedad) {

        // Campos comunes siempre visibles — headers (txt)
        viewHolder.txtEstado.setVisibility(View.VISIBLE);
        viewHolder.txtFecha.setVisibility(View.VISIBLE);
        viewHolder.txtHora.setVisibility(View.VISIBLE);
        viewHolder.txtNombrePdv.setVisibility(View.VISIBLE);
        viewHolder.txtUsuario.setVisibility(View.VISIBLE);
        viewHolder.txtTipoNovedad.setVisibility(View.VISIBLE);

        // Campos comunes siempre visibles — datos (lbl)
        viewHolder.lblEstado.setVisibility(View.VISIBLE);
        viewHolder.lblfecha.setVisibility(View.VISIBLE);
        viewHolder.lblHora.setVisibility(View.VISIBLE);
        viewHolder.lblNombrePdv.setVisibility(View.VISIBLE);
        viewHolder.lblUsuario.setVisibility(View.VISIBLE);
        viewHolder.lblTipoNovedad.setVisibility(View.VISIBLE);

        switch (tipoNovedad.toUpperCase()) {

            case "LUZ PÚBLICA DAÑADA":
                // headers
            //    viewHolder.txtMarca.setVisibility(View.VISIBLE);
               // viewHolder.txtSku.setVisibility(View.VISIBLE);
             //   viewHolder.txtLote.setVisibility(View.VISIBLE);
             //  viewHolder.txtObservacion1.setVisibility(View.VISIBLE);
              ///  viewHolder.txtObservacion2.setVisibility(View.VISIBLE);
                viewHolder.txtCantidad.setVisibility(View.VISIBLE);
               // viewHolder.txtObservacion3.setVisibility(View.VISIBLE);
               //viewHolder.txtTipoUnidades.setVisibility(View.VISIBLE);
                viewHolder.txtFechaVencimiento.setVisibility(View.VISIBLE);
                // datos
              //  viewHolder.lblMarca.setVisibility(View.VISIBLE);
                viewHolder.lblCantidad.setVisibility(View.VISIBLE);
              //  viewHolder.lblSku.setVisibility(View.VISIBLE);
              //  viewHolder.lblLote.setVisibility(View.VISIBLE);
              //  viewHolder.lblObservacion1.setVisibility(View.VISIBLE);
               // viewHolder.lblObservacion2.setVisibility(View.VISIBLE);
               // viewHolder.lblObservacion3.setVisibility(View.VISIBLE);
              //  viewHolder.lblTipo.setVisibility(View.VISIBLE);

                viewHolder.lblFechavencimento.setVisibility(View.VISIBLE);
                break;

            case "RUIDO EXCESIVO / ESCÁNDALO":
                // headers
              //  viewHolder.txtMarca.setVisibility(View.VISIBLE);
              //  viewHolder.txtSku.setVisibility(View.VISIBLE);
                viewHolder.txtObservacion1.setVisibility(View.VISIBLE);
              //  viewHolder.txtCantidad.setVisibility(View.VISIBLE);
                // datos
               // viewHolder.lblMarca.setVisibility(View.VISIBLE);
              //  viewHolder.lblSku.setVisibility(View.VISIBLE);
               viewHolder.lblObservacion1.setVisibility(View.VISIBLE);
                //viewHolder.lblCantidad.setVisibility(View.VISIBLE);
                break;

            case "PERSONAS SOSPECHOSAS EN EL SECTOR":
                // headers
               // viewHolder.txtMarca.setVisibility(View.VISIBLE);
              //  viewHolder.lblMarca.setVisibility(View.VISIBLE);
//                viewHolder.txtSku.setVisibility(View.VISIBLE);
               viewHolder.txtObservacion1.setVisibility(View.VISIBLE);
                // datos
             //   viewHolder.lblSku.setVisibility(View.VISIBLE);
                viewHolder.lblObservacion1.setVisibility(View.VISIBLE);
                break;

            case "PROMOCIONES NO AUTORIZADAS":
                // headers
                viewHolder.txtMarca.setVisibility(View.VISIBLE);
             //   viewHolder.txtSku.setVisibility(View.VISIBLE);
                viewHolder.txtObservacion1.setVisibility(View.VISIBLE);
                viewHolder.txtFechaInicio.setVisibility(View.VISIBLE);
                viewHolder.txtPrecioAnterior.setVisibility(View.VISIBLE);
                viewHolder.txtPrecioPromocion.setVisibility(View.VISIBLE);
                viewHolder.txtMecanica.setVisibility(View.VISIBLE);
                // datos
                viewHolder.lblMarca.setVisibility(View.VISIBLE);
              //  viewHolder.lblSku.setVisibility(View.VISIBLE);
                viewHolder.lblObservacion1.setVisibility(View.VISIBLE);
                viewHolder.lblFechaInicio.setVisibility(View.VISIBLE);
                viewHolder.lblPrecioAnterior.setVisibility(View.VISIBLE);
                viewHolder.lblPrecioPromocio.setVisibility(View.VISIBLE);
                viewHolder.lblTipoMecanica.setVisibility(View.VISIBLE);
                break;

            case "OTROS":
                // headers
                viewHolder.txtObservacion1.setVisibility(View.VISIBLE);
                // datos
                viewHolder.lblObservacion1.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }
}