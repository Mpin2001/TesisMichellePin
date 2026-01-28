package com.luckyecuador.app.bassaApp.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.luckyecuador.app.bassaApp.Conexion.Constantes;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertEvaluacionEncuesta;
import com.luckyecuador.app.bassaApp.R;

public class AdapterEncuesta extends RecyclerView.Adapter<AdapterEncuesta.ViewHolder> {
    private Cursor cursor;
    private Context context;
    private String nom_test;
    private String nom_test_old;
    private String hora_test;
    private String hora_test_old;

    // Instancia de escucha
//    private AdapterPresencia.OnItemClickListener escucha;

    /**
     * Interfaz para escuchar clicks del recycler
     */
//    interface OnItemClickListener {
//        public void onClick(AdapterPresencia.ViewHolder holder, String idContacto);
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        //encabezados
        private TextView estado_encabezado;
        private TextView codigo_encabezado;
        private TextView fecha_encabezado;
        private TextView hora_encabezado;
        private TextView usuario_encabezado;
        private TextView encuesta_encabezado;
        private TextView categoria_encabezado;

        //descripcion
        private TextView estado;
        private TextView codigo;
        private TextView fecha;
        private TextView hora;
        private TextView usuario;
        private TextView encuesta;
        private TextView categoria;

        public ViewHolder(View v) {
            super(v);
            estado = (TextView) v.findViewById(R.id.lblEstado);
            codigo = (TextView) v.findViewById(R.id.lblCodigo);
            fecha = (TextView) v.findViewById(R.id.lblFecha);
            hora = (TextView) v.findViewById(R.id.lblHora);
            usuario = (TextView) v.findViewById(R.id.lblUsuario);
            encuesta = (TextView) v.findViewById(R.id.lblEncuesta);
            categoria = (TextView) v.findViewById(R.id.lblCategoria);

            estado_encabezado = (TextView) v.findViewById(R.id.encabezado_estado);
            codigo_encabezado = (TextView) v.findViewById(R.id.encabezado_codigo);
            fecha_encabezado = (TextView) v.findViewById(R.id.encabezado_fecha);
            hora_encabezado = (TextView) v.findViewById(R.id.encabezado_hora);
            usuario_encabezado = (TextView) v.findViewById(R.id.encabezado_usuario);
            encuesta_encabezado = (TextView) v.findViewById(R.id.encabezado_encuesta);
            categoria_encabezado = (TextView) v.findViewById(R.id.encabezado_categoria);

        }
    }


    public AdapterEncuesta(Context context) {
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
                .inflate(R.layout.status_encuesta, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        cursor.moveToPosition(i);

        /*SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(ContractInsertEvaluacionEncuesta.INSERT_EVALUACION);

        String[] projection = {
                ContractInsertEvaluacionEncuesta.Columnas.CODIGO,
                ContractInsertEvaluacionEncuesta.Columnas.USUARIO,
                ContractInsertEvaluacionEncuesta.Columnas.CIUDAD,
                ContractInsertEvaluacionEncuesta.Columnas.LOCAL,
                ContractInsertEvaluacionEncuesta.Columnas.GESTOR_ASIGNADO,
                ContractInsertEvaluacionEncuesta.Columnas.NOMBRE_ENCUESTA,
                ContractInsertEvaluacionEncuesta.Columnas.CATEGORIA,
                ContractInsertEvaluacionEncuesta.Columnas.RE,
                ContractInsertEvaluacionEncuesta.Columnas.PREGUNTA,
                ContractInsertEvaluacionEncuesta.Columnas.RESPUESTA,
                ContractInsertEvaluacionEncuesta.Columnas.FOTO_PREGUNTA,
                ContractInsertEvaluacionEncuesta.Columnas.COMENTARIO_PREGUNTA,
                ContractInsertEvaluacionEncuesta.Columnas.FOTO_FACHADA,
                ContractInsertEvaluacionEncuesta.Columnas.CALIFICACION,
                ContractInsertEvaluacionEncuesta.Columnas.CALIFICACION_INDIVIDUAL,
                ContractInsertEvaluacionEncuesta.Columnas.CALIFICACION_TOTAL,
                ContractInsertEvaluacionEncuesta.Columnas.SATISFACCION,
                ContractInsertEvaluacionEncuesta.Columnas.COMENTARIO,
                ContractInsertEvaluacionEncuesta.Columnas.APOYO,
                ContractInsertEvaluacionEncuesta.Columnas.SUPERVISOR,
                ContractInsertEvaluacionEncuesta.Columnas.FECHA,
                ContractInsertEvaluacionEncuesta.Columnas.HORA};


        String selection = null;
        String[] selectionArgs = null;
        String groupBy = ContractInsertEvaluacionEncuesta.Columnas.CODIGO + ", " + ContractInsertEvaluacionEncuesta.Columnas.HORA;


        SQLiteDatabase db = handler.getWritableDatabase();
        Uri uri = ContractInsertEvaluacionEncuesta.CONTENT_URI;
        String[] n = new String[]{String.valueOf(uri)};

        cursor = queryBuilder.query(
                db,
                n,
                projection.toString(),
                null,
                null,
                groupBy,
                null
        );*/


        String consulta;
        String estado = "";

        String codigo = "";
        String fecha = "";
        String hora = "";
        String usuario = "";
        String encuesta = "";
        String categoria = "";


        try {
            consulta = cursor.getString(cursor.getColumnIndexOrThrow(Constantes.PENDIENTE_INSERCION));
            estado = consulta.equals("1") ? "No Enviado" : "Enviado";

            codigo = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertEvaluacionEncuesta.Columnas.CODIGO));
            fecha = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertEvaluacionEncuesta.Columnas.FECHA));
            hora = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertEvaluacionEncuesta.Columnas.HORA));
            usuario = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertEvaluacionEncuesta.Columnas.USUARIO));
            encuesta = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertEvaluacionEncuesta.Columnas.NOMBRE_ENCUESTA));
            categoria = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertEvaluacionEncuesta.Columnas.CATEGORIA));

            // Asignar variables actuales para comparaciones posteriores
            nom_test_old = codigo;
            hora_test_old = hora;

        } catch (Exception e) {
            Log.i("STM", e.getMessage());
        }

// Validar si la encuesta y hora actual ya fueron dibujadas
        if (!nom_test_old.equals(nom_test)) {
            viewHolder.estado.setVisibility(View.VISIBLE);
            viewHolder.codigo.setVisibility(View.VISIBLE);
            viewHolder.fecha.setVisibility(View.VISIBLE);
            viewHolder.hora.setVisibility(View.VISIBLE);
            viewHolder.usuario.setVisibility(View.VISIBLE);
            viewHolder.encuesta.setVisibility(View.VISIBLE);
            viewHolder.categoria.setVisibility(View.VISIBLE);

            viewHolder.estado_encabezado.setVisibility(View.VISIBLE);
            viewHolder.codigo_encabezado.setVisibility(View.VISIBLE);
            viewHolder.fecha_encabezado.setVisibility(View.VISIBLE);
            viewHolder.hora_encabezado.setVisibility(View.VISIBLE);
            viewHolder.usuario_encabezado.setVisibility(View.VISIBLE);
            viewHolder.encuesta_encabezado.setVisibility(View.VISIBLE);
            viewHolder.categoria_encabezado.setVisibility(View.VISIBLE);


            viewHolder.estado.setText(estado);
            viewHolder.codigo.setText(codigo);
            viewHolder.fecha.setText(fecha);
            viewHolder.hora.setText(hora);
            viewHolder.usuario.setText(usuario);
            viewHolder.encuesta.setText(encuesta);
            viewHolder.categoria.setText(categoria);


            nom_test = nom_test_old;
            hora_test = hora_test_old;

        } else if (!hora_test.equals(hora_test_old)) {
            // Si el código es el mismo pero la hora es distinta, se muestra el registro
            viewHolder.estado.setVisibility(View.VISIBLE);
            viewHolder.codigo.setVisibility(View.VISIBLE);
            viewHolder.fecha.setVisibility(View.VISIBLE);
            viewHolder.hora.setVisibility(View.VISIBLE);
            viewHolder.usuario.setVisibility(View.VISIBLE);
            viewHolder.encuesta.setVisibility(View.VISIBLE);
            viewHolder.categoria.setVisibility(View.VISIBLE);


            viewHolder.estado.setText(estado);
            viewHolder.codigo.setText(codigo);
            viewHolder.fecha.setText(fecha);
            viewHolder.hora.setText(hora);
            viewHolder.usuario.setText(usuario);
            viewHolder.encuesta.setText(encuesta);
            viewHolder.categoria.setText(categoria);


            hora_test = hora_test_old;

        } else {
            // Si ya se dibujó la encuesta con la misma hora, ocultar las repeticiones
            viewHolder.estado.setVisibility(View.GONE);
            viewHolder.codigo.setVisibility(View.GONE);
            viewHolder.fecha.setVisibility(View.GONE);
            viewHolder.hora.setVisibility(View.GONE);
            viewHolder.usuario.setVisibility(View.GONE);
            viewHolder.encuesta.setVisibility(View.GONE);
            viewHolder.categoria.setVisibility(View.GONE);

            viewHolder.estado_encabezado.setVisibility(View.GONE);
            viewHolder.codigo_encabezado.setVisibility(View.GONE);
            viewHolder.fecha_encabezado.setVisibility(View.GONE);
            viewHolder.hora_encabezado.setVisibility(View.GONE);
            viewHolder.usuario_encabezado.setVisibility(View.GONE);
            viewHolder.encuesta_encabezado.setVisibility(View.GONE);
            viewHolder.categoria_encabezado.setVisibility(View.GONE);
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
