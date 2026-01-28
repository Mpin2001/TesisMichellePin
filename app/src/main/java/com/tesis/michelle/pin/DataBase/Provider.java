package com.tesis.michelle.pin.DataBase;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.Conexion.ContractTipoInventario;
import com.tesis.michelle.pin.Contracts.ContractAlertas;
import com.tesis.michelle.pin.Contracts.ContractCamposPorModulos;
import com.tesis.michelle.pin.Contracts.ContractCanalesFotos;
import com.tesis.michelle.pin.Contracts.ContractCausalesMCI;
import com.tesis.michelle.pin.Contracts.ContractCausalesOSA;
import com.tesis.michelle.pin.Contracts.ContractComboCanjes;
import com.tesis.michelle.pin.Contracts.ContractConvenios;
import com.tesis.michelle.pin.Contracts.ContractEvaluacionEncuesta;
import com.tesis.michelle.pin.Contracts.ContractExhibicionSupervisor;
import com.tesis.michelle.pin.Contracts.ContractInsertCanjes;
import com.tesis.michelle.pin.Contracts.ContractInsertConvenios;
import com.tesis.michelle.pin.Contracts.ContractInsertEjecucionMateriales;
import com.tesis.michelle.pin.Contracts.ContractInsertEvaluacionEncuesta;
import com.tesis.michelle.pin.Contracts.ContractInsertEvidencias;
import com.tesis.michelle.pin.Contracts.ContractInsertMCIPdv;
import com.tesis.michelle.pin.Contracts.ContractInsertCodificados;
import com.tesis.michelle.pin.Contracts.ContractInsertMaterialesRecibidos;
import com.tesis.michelle.pin.Contracts.ContractInsertMuestras;
import com.tesis.michelle.pin.Contracts.ContractInsertNovedades;
import com.tesis.michelle.pin.Contracts.ContractInsertPDI;
import com.tesis.michelle.pin.Contracts.ContractInsertProbadores;
import com.tesis.michelle.pin.Contracts.ContractInsertResultadoPreguntas;
import com.tesis.michelle.pin.Contracts.ContractInsertSugeridos;
import com.tesis.michelle.pin.Contracts.ContractInsertTareas;
import com.tesis.michelle.pin.Contracts.ContractInsertTracking;
import com.tesis.michelle.pin.Contracts.ContractInsertVentas2;
import com.tesis.michelle.pin.Contracts.ContractJustificacion;
import com.tesis.michelle.pin.Contracts.ContractLinkOnedrive;
import com.tesis.michelle.pin.Contracts.ContractLog;
import com.tesis.michelle.pin.Contracts.ContractModulosRoles;
import com.tesis.michelle.pin.Contracts.ContractMotivoSugerido;
import com.tesis.michelle.pin.Contracts.ContractPDI;
import com.tesis.michelle.pin.Contracts.ContractPopSugerido;
import com.tesis.michelle.pin.Contracts.ContractPortafolioProductosAASS;
import com.tesis.michelle.pin.Contracts.ContractPortafolioProductosMAYO;
import com.tesis.michelle.pin.Contracts.ContractPrioritario;
import com.tesis.michelle.pin.Contracts.ContractProductosSecundarios;
import com.tesis.michelle.pin.Contracts.ContractPromocionalVentas;
import com.tesis.michelle.pin.Contracts.ContractPromociones;
import com.tesis.michelle.pin.Contracts.ContractRotacion;
import com.tesis.michelle.pin.Contracts.ContractInsertFotografico;
import com.tesis.michelle.pin.Contracts.ContractInsertImpulso;
import com.tesis.michelle.pin.Contracts.ContractInsertPacks;
import com.tesis.michelle.pin.Contracts.ContractInsertPreguntas;
import com.tesis.michelle.pin.Contracts.ContractInsertProdCaducar;
import com.tesis.michelle.pin.Contracts.ContractInsertRotacion;
import com.tesis.michelle.pin.Contracts.ContractInsertVenta;
import com.tesis.michelle.pin.Contracts.ContractPharmaValue;
import com.tesis.michelle.pin.Contracts.ContractPortafolioProductos;
import com.tesis.michelle.pin.Contracts.ContractInsertAgotados;
import com.tesis.michelle.pin.Contracts.ContractInsertExh;
import com.tesis.michelle.pin.Contracts.ContractInsertGps;
import com.tesis.michelle.pin.Contracts.ContractInsertImplementacion;
import com.tesis.michelle.pin.Contracts.ContractInsertInicial;
import com.tesis.michelle.pin.Contracts.ContractInsertPdv;
import com.tesis.michelle.pin.Contracts.ContractInsertPrecios;
import com.tesis.michelle.pin.Contracts.ContractInsertPromocion;
import com.tesis.michelle.pin.Contracts.ContractInsertRastreo;
import com.tesis.michelle.pin.Contracts.ContractInsertShare;
import com.tesis.michelle.pin.Contracts.ContractInsertValores;
import com.tesis.michelle.pin.Contracts.ContractNotificacion;
import com.tesis.michelle.pin.Contracts.ContractPrecios;
import com.tesis.michelle.pin.Contracts.ContractPreguntas;
import com.tesis.michelle.pin.Contracts.ContractSesiones.ContractInsertExhSesion;
import com.tesis.michelle.pin.Contracts.ContractSesiones.ContractInsertPreciosSesion;
import com.tesis.michelle.pin.Contracts.ContractSesiones.ContractInsertValoresSesion;
import com.tesis.michelle.pin.Contracts.ContractTareas;
import com.tesis.michelle.pin.Contracts.ContractTests;
import com.tesis.michelle.pin.Contracts.ContractTipoActividades;
import com.tesis.michelle.pin.Contracts.ContractTipoExh;
import com.tesis.michelle.pin.Contracts.ContractCategoriaTipo;
import com.tesis.michelle.pin.Contracts.ContractTipoNovedades;
import com.tesis.michelle.pin.Contracts.ContractTipoRegistro;
import com.tesis.michelle.pin.Contracts.ContractTipoVentas;
import com.tesis.michelle.pin.Contracts.ContractTipoImplementaciones;
import com.tesis.michelle.pin.Contracts.ContractTipoUnidades;
import com.tesis.michelle.pin.Contracts.ContractTracking;
import com.tesis.michelle.pin.Contracts.ContractUser;
import com.tesis.michelle.pin.Contracts.InsertExhBassa;
import com.tesis.michelle.pin.Contracts.InsertExhibicionesAdNu;
import com.tesis.michelle.pin.Contracts.InsertFlooring;


/**
 * Created by Lucky Ecuador on 15/07/2016.
 */
public class Provider extends ContentProvider {
    /**
     * Nombre de la base de datos
     */
    //public static final String DATABASE_NAME = "colgate_transf.db";
    public static final String DATABASE_NAME = "BassaV1.47db";
    /**
     * Versión actual de la base de datos
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * Instancia global del Content Resolver
     */
    private ContentResolver resolver;
    /**
     * Instancia del administrador de BD
     */
    private DatabaseHelper databaseHelper;

    @Override
    public boolean onCreate() {
        // Inicializando gestor BD
        databaseHelper = new DatabaseHelper(
                getContext(),
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );

        resolver = getContext().getContentResolver();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Obtener base de datos
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor c;
        // Comparar Uri
        if (ContractPharmaValue.uriMatcher.match(uri)== ContractPharmaValue.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractPharmaValue.POS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPharmaValue.CONTENT_URI);
        }else if (ContractPharmaValue.uriMatcher.match(uri)== ContractPharmaValue.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractPharmaValue.POS, projection,
                    ContractPharmaValue.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPharmaValue.CONTENT_URI);
        }

        else if(ContractUser.uriMatcher.match(uri)== ContractUser.ALLROWS){
            // Consultando todos los registros
            c = db.query(ContractUser.TABLE_USER, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractUser.CONTENT_URI);
        }else if(ContractUser.uriMatcher.match(uri)== ContractUser.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractUser.TABLE_USER, projection,
                    ContractUser.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractUser.CONTENT_URI);
        }
        else if(InsertExhBassa.uriMatcher.match(uri)==InsertExhBassa.ALLROWS){
            // Consultando todos los registros
            c = db.query(InsertExhBassa.INSERT_EXHIBICION_COLGATE, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    InsertExhBassa.CONTENT_URI);
        }else if(InsertExhBassa.uriMatcher.match(uri)==InsertExhBassa.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(InsertExhBassa.INSERT_EXHIBICION_COLGATE, projection,
                    InsertExhBassa.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    InsertExhBassa.CONTENT_URI);
        }
        else if(InsertExhibicionesAdNu.uriMatcher.match(uri)==InsertExhibicionesAdNu.ALLROWS){
            // Consultando todos los registros
            c = db.query(InsertExhibicionesAdNu.INSERT_EXHIBICION_AD_NU, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    InsertExhibicionesAdNu.CONTENT_URI);
        }else if(InsertExhibicionesAdNu.uriMatcher.match(uri)==InsertExhibicionesAdNu.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(InsertExhibicionesAdNu.INSERT_EXHIBICION_AD_NU, projection,
                    InsertExhibicionesAdNu.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    InsertExhibicionesAdNu.CONTENT_URI);
        }
        else if(ContractExhibicionSupervisor.uriMatcher.match(uri)== ContractExhibicionSupervisor.ALLROWS){
            // Consultando todos los registros
            c = db.query(ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractExhibicionSupervisor.CONTENT_URI);
        }else if(ContractExhibicionSupervisor.uriMatcher.match(uri)== ContractExhibicionSupervisor.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR, projection,
                    ContractExhibicionSupervisor.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractExhibicionSupervisor.CONTENT_URI);
        }
        else if (ContractEvaluacionEncuesta.uriMatcher.match(uri)== ContractEvaluacionEncuesta.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractEvaluacionEncuesta.EVALUACION_PROMOTOR, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractEvaluacionEncuesta.CONTENT_URI);
        }else if (ContractEvaluacionEncuesta.uriMatcher.match(uri)== ContractEvaluacionEncuesta.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractEvaluacionEncuesta.EVALUACION_PROMOTOR, projection,
                    ContractEvaluacionEncuesta.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractEvaluacionEncuesta.CONTENT_URI);
        }
        else if (ContractInsertEvaluacionEncuesta.uriMatcher.match(uri)== ContractInsertEvaluacionEncuesta.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertEvaluacionEncuesta.INSERT_EVALUACION, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertEvaluacionEncuesta.CONTENT_URI);
        }else if (ContractInsertEvaluacionEncuesta.uriMatcher.match(uri)== ContractInsertEvaluacionEncuesta.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertEvaluacionEncuesta.INSERT_EVALUACION, projection,
                    ContractInsertEvaluacionEncuesta.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertEvaluacionEncuesta.CONTENT_URI);
        }

        else if(ContractTipoNovedades.uriMatcher.match(uri)== ContractTipoNovedades.ALLROWS){
            // Consultando todos los registros
            c = db.query(ContractTipoNovedades.TIPO_NOVEDADES, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTipoNovedades.CONTENT_URI);
        }else if(ContractTipoNovedades.uriMatcher.match(uri)== ContractTipoNovedades.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractTipoNovedades.TIPO_NOVEDADES, projection,
                    ContractTipoNovedades.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTipoNovedades.CONTENT_URI);
        }

//tipo_registros mpin probadores
        else if(ContractTipoRegistro.uriMatcher.match(uri)== ContractTipoRegistro.ALLROWS){
            // Consultando todos los registros
            c = db.query(ContractTipoRegistro.TIPO_REGISTRO, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTipoRegistro.CONTENT_URI);
        }else if(ContractTipoRegistro.uriMatcher.match(uri)== ContractTipoRegistro.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractTipoRegistro.TIPO_REGISTRO, projection,
                    ContractTipoRegistro.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTipoRegistro.CONTENT_URI);
        }

        else if(ContractTipoVentas.uriMatcher.match(uri)== ContractTipoVentas.ALLROWS){
            // Consultando todos los registros
            c = db.query(ContractTipoVentas.TIPO_VENTAS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTipoVentas.CONTENT_URI);
        }else if(ContractTipoVentas.uriMatcher.match(uri)== ContractTipoVentas.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractTipoVentas.TIPO_VENTAS, projection,
                    ContractTipoVentas.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTipoVentas.CONTENT_URI);
        }

        //TIPO ACTIVIDADES mpin
        else if(ContractTipoActividades.uriMatcher.match(uri)== ContractTipoActividades.ALLROWS){
            // Consultando todos los registros
            c = db.query(ContractTipoActividades.TIPO_ACTIVIDADES, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTipoActividades.CONTENT_URI);
        }else if(ContractTipoActividades.uriMatcher.match(uri)== ContractTipoActividades.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractTipoActividades.TIPO_ACTIVIDADES, projection,
                    ContractTipoActividades.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTipoActividades.CONTENT_URI);
        }

        //IMPLE mpin
        else if(ContractTipoImplementaciones.uriMatcher.match(uri)== ContractTipoImplementaciones.ALLROWS){
            // Consultando todos los registros
            c = db.query(ContractTipoImplementaciones.TIPO_IMPLEMENTACIONES, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTipoImplementaciones.CONTENT_URI);
        }else if(ContractTipoImplementaciones.uriMatcher.match(uri)== ContractTipoImplementaciones.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractTipoImplementaciones.TIPO_IMPLEMENTACIONES, projection,
                    ContractTipoImplementaciones.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTipoImplementaciones.CONTENT_URI);
        }

        //unidades repo novedades

        //IMPLE mpin
        else if(ContractTipoUnidades.uriMatcher.match(uri)== ContractTipoUnidades.ALLROWS){
            // Consultando todos los registros
            c = db.query(ContractTipoUnidades.TIPO_UNIDADES, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTipoUnidades.CONTENT_URI);
        }else if(ContractTipoUnidades.uriMatcher.match(uri)== ContractTipoUnidades.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractTipoUnidades.TIPO_UNIDADES, projection,
                    ContractTipoUnidades.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTipoUnidades.CONTENT_URI);
        }

        //MODULOS POR ROLES
        else if(ContractModulosRoles.uriMatcher.match(uri)== ContractModulosRoles.ALLROWS){
            // Consultando todos los registros
            c = db.query(ContractModulosRoles.MODULO_ROLES, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractModulosRoles.CONTENT_URI);
        }else if(ContractModulosRoles.uriMatcher.match(uri)== ContractModulosRoles.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractModulosRoles.MODULO_ROLES, projection,
                    ContractModulosRoles.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractModulosRoles.CONTENT_URI);
        }


        else if(ContractCanalesFotos.uriMatcher.match(uri)== ContractCanalesFotos.ALLROWS){
            // Consultando todos los registros
            c = db.query(ContractCanalesFotos.CANALES_FOTOS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractCanalesFotos.CONTENT_URI);
        }else if(ContractCanalesFotos.uriMatcher.match(uri)== ContractCanalesFotos.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractCanalesFotos.CANALES_FOTOS, projection,
                    ContractCanalesFotos.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractCanalesFotos.CONTENT_URI);
        }

        else if(ContractInsertNovedades.uriMatcher.match(uri)==ContractInsertNovedades.ALLROWS){
            // Consultando todos los registros
            c = db.query(ContractInsertNovedades.INSERT_NOVEDADES, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertNovedades.CONTENT_URI);
        }else if(ContractInsertNovedades.uriMatcher.match(uri)==ContractInsertNovedades.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertNovedades.INSERT_NOVEDADES, projection,
                    ContractInsertNovedades.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertNovedades.CONTENT_URI);
        }

        else if (ContractProductosSecundarios.uriMatcher.match(uri)== ContractProductosSecundarios.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractProductosSecundarios.PRODUCTOS_SECUNDARIOS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractProductosSecundarios.CONTENT_URI);
        }else if (ContractProductosSecundarios.uriMatcher.match(uri)== ContractProductosSecundarios.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractProductosSecundarios.PRODUCTOS_SECUNDARIOS, projection,
                    ContractProductosSecundarios.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractProductosSecundarios.CONTENT_URI);
        }

        else if (ContractTracking.uriMatcher.match(uri)== ContractTracking.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractTracking.TRACKING, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTracking.CONTENT_URI);
        }else if (ContractTracking.uriMatcher.match(uri)== ContractTracking.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractTracking.TRACKING, projection,
                    ContractTracking.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTracking.CONTENT_URI);
        }

        else if (ContractLinkOnedrive.uriMatcher.match(uri)== ContractLinkOnedrive.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractLinkOnedrive.LINK_ONDRIVE, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractLinkOnedrive.CONTENT_URI);
        }else if (ContractLinkOnedrive.uriMatcher.match(uri)== ContractLinkOnedrive.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractLinkOnedrive.LINK_ONDRIVE, projection,
                    ContractLinkOnedrive.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractLinkOnedrive.CONTENT_URI);
        }

        else if (ContractPrecios.uriMatcher.match(uri)== ContractPrecios.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractPrecios.PRECIOS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPrecios.CONTENT_URI);
        }else if (ContractPrecios.uriMatcher.match(uri)== ContractPrecios.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractPrecios.PRECIOS, projection,
                    ContractPrecios.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPrecios.CONTENT_URI);
        }

        else if (ContractConvenios.uriMatcher.match(uri)== ContractConvenios.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractConvenios.CONVENIOS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractConvenios.CONTENT_URI);
        }else if (ContractConvenios.uriMatcher.match(uri)== ContractConvenios.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractConvenios.CONVENIOS, projection,
                    ContractConvenios.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractConvenios.CONTENT_URI);
        }

        else if (ContractPopSugerido.uriMatcher.match(uri)== ContractPopSugerido.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractPopSugerido.POPSUGERIDO, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPopSugerido.CONTENT_URI);
        }else if (ContractPopSugerido.uriMatcher.match(uri)== ContractPopSugerido.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractPopSugerido.POPSUGERIDO, projection,
                    ContractPopSugerido.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPopSugerido.CONTENT_URI);
        }

        else if (ContractComboCanjes.uriMatcher.match(uri)== ContractComboCanjes.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractComboCanjes.COMBO_CANJES, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractComboCanjes.CONTENT_URI);
        }else if (ContractComboCanjes.uriMatcher.match(uri)== ContractComboCanjes.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractComboCanjes.COMBO_CANJES, projection,
                    ContractComboCanjes.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractComboCanjes.CONTENT_URI);
        }

        else if (ContractCausalesMCI.uriMatcher.match(uri)== ContractCausalesMCI.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractCausalesMCI.CAUSALES_MCI, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractCausalesMCI.CONTENT_URI);
        }else if (ContractCausalesMCI.uriMatcher.match(uri)== ContractCausalesMCI.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractCausalesMCI.CAUSALES_MCI, projection,
                    ContractCausalesMCI.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractCausalesMCI.CONTENT_URI);
        }

        else if (ContractCausalesOSA.uriMatcher.match(uri)== ContractCausalesOSA.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractCausalesOSA.CAUSALES_OSA, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractCausalesOSA.CONTENT_URI);
        }else if (ContractCausalesOSA.uriMatcher.match(uri)== ContractCausalesOSA.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractCausalesOSA.CAUSALES_OSA, projection,
                    ContractCausalesOSA.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractCausalesOSA.CONTENT_URI);
        }

        else if (ContractAlertas.uriMatcher.match(uri)== ContractAlertas.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractAlertas.ALERTAS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractAlertas.CONTENT_URI);
        }else if (ContractAlertas.uriMatcher.match(uri)== ContractAlertas.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractAlertas.ALERTAS, projection,
                    ContractAlertas.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractAlertas.CONTENT_URI);
        }

        else if (ContractPDI.uriMatcher.match(uri)== ContractPDI.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractPDI.PDI, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPDI.CONTENT_URI);
        }else if (ContractPDI.uriMatcher.match(uri)== ContractPDI.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractPDI.PDI, projection,
                    ContractPDI.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPDI.CONTENT_URI);
        }

        else if (ContractLog.uriMatcher.match(uri)== ContractLog.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractLog.LOG, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractLog.CONTENT_URI);
        }else if (ContractLog.uriMatcher.match(uri)== ContractLog.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractLog.LOG, projection,
                    ContractLog.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractLog.CONTENT_URI);
        }

        else if (ContractPrioritario.uriMatcher.match(uri)== ContractPrioritario.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractPrioritario.PRIORITARIO, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPrioritario.CONTENT_URI);
        }else if (ContractPrioritario.uriMatcher.match(uri)== ContractPrioritario.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractPrioritario.PRIORITARIO, projection,
                    ContractPrioritario.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPrioritario.CONTENT_URI);
        }

        else if(ContractCamposPorModulos.uriMatcher.match(uri)== ContractCamposPorModulos.ALLROWS){
            // Consultando todos los registros
            c = db.query(ContractCamposPorModulos.CAMPOS_X_MODULOS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractCamposPorModulos.CONTENT_URI);
        }else if(ContractCamposPorModulos.uriMatcher.match(uri)== ContractCamposPorModulos.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractCamposPorModulos.CAMPOS_X_MODULOS, projection,
                    ContractCamposPorModulos.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractCamposPorModulos.CONTENT_URI);
        }

        else if(ContractPromocionalVentas.uriMatcher.match(uri)== ContractPromocionalVentas.ALLROWS){
            // Consultando todos los registros
            c = db.query(ContractPromocionalVentas.PROMOCIONAL_VENTAS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPromocionalVentas.CONTENT_URI);
        }else if(ContractPromocionalVentas.uriMatcher.match(uri)== ContractPromocionalVentas.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractPromocionalVentas.PROMOCIONAL_VENTAS, projection,
                    ContractPromocionalVentas.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPromocionalVentas.CONTENT_URI);
        }

        else if (ContractTareas.uriMatcher.match(uri)== ContractTareas.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractTareas.TAREA, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTareas.CONTENT_URI);
        }else if (ContractTareas.uriMatcher.match(uri)== ContractTareas.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractTareas.TAREA, projection,
                    ContractTareas.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTareas.CONTENT_URI);
        }

        else if(ContractTests.uriMatcher.match(uri)== ContractTests.ALLROWS){
            // Consultando todos los registros
            c = db.query(ContractTests.TABLE_TEST, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTests.CONTENT_URI);
        }else if(ContractTests.uriMatcher.match(uri)== ContractTests.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractTests.TABLE_TEST, projection,
                    ContractTests.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTests.CONTENT_URI);
        }

        else if(ContractJustificacion.uriMatcher.match(uri)== ContractJustificacion.ALLROWS){
            // Consultando todos los registros
            c = db.query(ContractJustificacion.JUSTIFICACIONES, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractJustificacion.CONTENT_URI);
        }else if(ContractJustificacion.uriMatcher.match(uri)== ContractJustificacion.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractJustificacion.JUSTIFICACIONES, projection,
                    ContractJustificacion.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractJustificacion.CONTENT_URI);
        }

        else if(ContractTipoInventario.uriMatcher.match(uri)== ContractTipoInventario.ALLROWS){
            // Consultando todos los registros
            c = db.query(ContractTipoInventario.TIPOINVENTARIO, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTipoInventario.CONTENT_URI);
        }else if(ContractTipoInventario.uriMatcher.match(uri)== ContractTipoInventario.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractTipoInventario.TIPOINVENTARIO, projection,
                    ContractTipoInventario.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTipoInventario.CONTENT_URI);
        }


        else if (ContractPreguntas.uriMatcher.match(uri)== ContractPreguntas.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractPreguntas.TABLE_QUEST, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPreguntas.CONTENT_URI);
        }else if (ContractPreguntas.uriMatcher.match(uri)== ContractPreguntas.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractPreguntas.TABLE_QUEST, projection,
                    ContractPreguntas.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPreguntas.CONTENT_URI);
        }


        else if (ContractTipoExh.uriMatcher.match(uri)== ContractTipoExh.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractTipoExh.TABLE_NAME, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTipoExh.CONTENT_URI);
        }else if (ContractTipoExh.uriMatcher.match(uri)== ContractTipoExh.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractTipoExh.TABLE_NAME, projection,
                    ContractTipoExh.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractTipoExh.CONTENT_URI);
        }

        else if (ContractCategoriaTipo.uriMatcher.match(uri)== ContractCategoriaTipo.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractCategoriaTipo.CATEGORIA_TIPO, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractCategoriaTipo.CONTENT_URI);
        }else if (ContractCategoriaTipo.uriMatcher.match(uri)== ContractCategoriaTipo.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractCategoriaTipo.CATEGORIA_TIPO, projection,
                    ContractCategoriaTipo.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractCategoriaTipo.CONTENT_URI);
        }




        else if (ContractPromociones.uriMatcher.match(uri)== ContractPromociones.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractPromociones.PROMOCIONES, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPromociones.CONTENT_URI);
        }else if (ContractPromociones.uriMatcher.match(uri)== ContractPromociones.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractPromociones.PROMOCIONES, projection,
                    ContractPromociones.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPromociones.CONTENT_URI);
        }

        else if (ContractMotivoSugerido.uriMatcher.match(uri)== ContractMotivoSugerido.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractMotivoSugerido.TABLE_NAME, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractMotivoSugerido.CONTENT_URI);
        }else if (ContractMotivoSugerido.uriMatcher.match(uri)== ContractMotivoSugerido.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractMotivoSugerido.TABLE_NAME, projection,
                    ContractMotivoSugerido.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractMotivoSugerido.CONTENT_URI);
        }

        else if (ContractRotacion.uriMatcher.match(uri)== ContractRotacion.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractRotacion.ROTACION, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractRotacion.CONTENT_URI);
        }else if (ContractRotacion.uriMatcher.match(uri)== ContractRotacion.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractRotacion.ROTACION, projection,
                    ContractRotacion.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractRotacion.CONTENT_URI);
        }

        else if (ContractPortafolioProductos.uriMatcher.match(uri)== ContractPortafolioProductos.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractPortafolioProductos.PORTAFOLIOPRODUCTOS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPortafolioProductos.CONTENT_URI);
        }else if (ContractPortafolioProductos.uriMatcher.match(uri)== ContractPortafolioProductos.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractPortafolioProductos.PORTAFOLIOPRODUCTOS, projection,
                    ContractPortafolioProductos.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPortafolioProductos.CONTENT_URI);
        }

        else if (ContractPortafolioProductosAASS.uriMatcher.match(uri)== ContractPortafolioProductosAASS.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractPortafolioProductosAASS.PORTAFOLIOPRODUCTOS_AASS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPortafolioProductosAASS.CONTENT_URI);
        }else if (ContractPortafolioProductosAASS.uriMatcher.match(uri)== ContractPortafolioProductosAASS.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractPortafolioProductosAASS.PORTAFOLIOPRODUCTOS_AASS, projection,
                    ContractPortafolioProductosAASS.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPortafolioProductosAASS.CONTENT_URI);
        }

        else if (ContractPortafolioProductosMAYO.uriMatcher.match(uri)== ContractPortafolioProductosMAYO.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractPortafolioProductosMAYO.PORTAFOLIOPRODUCTOS_MAYO, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPortafolioProductosMAYO.CONTENT_URI);
        }else if (ContractPortafolioProductosMAYO.uriMatcher.match(uri)== ContractPortafolioProductosMAYO.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractPortafolioProductosMAYO.PORTAFOLIOPRODUCTOS_MAYO, projection,
                    ContractPortafolioProductosMAYO.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractPortafolioProductosMAYO.CONTENT_URI);
        }

        /*
         *      INSERTS
         */

        else if (ContractInsertPrecios.uriMatcher.match(uri)==ContractInsertPrecios.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertPrecios.INSERT_PRECIOS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertPrecios.CONTENT_URI);
        }else if (ContractInsertPrecios.uriMatcher.match(uri)==ContractInsertPrecios.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertPrecios.INSERT_PRECIOS, projection,
                    ContractInsertPrecios.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertPrecios.CONTENT_URI);
        }
        //insert nuevo modulo muestras medicas mpin
        else if (ContractInsertMuestras.uriMatcher.match(uri)==ContractInsertMuestras.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertMuestras.INSERT_MUESTRAS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertMuestras.CONTENT_URI);
        }else if (ContractInsertMuestras.uriMatcher.match(uri)==ContractInsertMuestras.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertMuestras.INSERT_MUESTRAS, projection,
                    ContractInsertMuestras.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertMuestras.CONTENT_URI);
        }

        //insert nuevo modulo probadores mpin
        else if (ContractInsertProbadores.uriMatcher.match(uri)==ContractInsertProbadores.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertProbadores.INSERT_PROBADORES, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertProbadores.CONTENT_URI);
        }else if (ContractInsertProbadores.uriMatcher.match(uri)==ContractInsertProbadores.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertProbadores.INSERT_PROBADORES, projection,
                    ContractInsertProbadores.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertProbadores.CONTENT_URI);
        }

        else if (ContractInsertConvenios.uriMatcher.match(uri)==ContractInsertConvenios.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertConvenios.INSERT_CONVENIOS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertConvenios.CONTENT_URI);
        }else if (ContractInsertConvenios.uriMatcher.match(uri)==ContractInsertConvenios.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertConvenios.INSERT_CONVENIOS, projection,
                    ContractInsertConvenios.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertConvenios.CONTENT_URI);
        }

        else if (ContractInsertTracking.uriMatcher.match(uri)== ContractInsertTracking.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertTracking.INSERT_TRACKING, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertTracking.CONTENT_URI);
        }else if (ContractInsertTracking.uriMatcher.match(uri)== ContractInsertTracking.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertTracking.INSERT_TRACKING, projection,
                    ContractInsertTracking.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertTracking.CONTENT_URI);
        }

        else if (ContractInsertPacks.uriMatcher.match(uri)==ContractInsertPacks.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertPacks.INSERT_PACKS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertPacks.CONTENT_URI);
        }else if (ContractInsertPacks.uriMatcher.match(uri)==ContractInsertPacks.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertPacks.INSERT_PACKS, projection,
                    ContractInsertPacks.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertPacks.CONTENT_URI);
        }

        else if (ContractInsertProdCaducar.uriMatcher.match(uri)==ContractInsertProdCaducar.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertProdCaducar.INSERT_PROD_CADUCAR, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertProdCaducar.CONTENT_URI);
        }else if (ContractInsertProdCaducar.uriMatcher.match(uri)==ContractInsertProdCaducar.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertProdCaducar.INSERT_PROD_CADUCAR, projection,
                    ContractInsertProdCaducar.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertProdCaducar.CONTENT_URI);
        }

        else if (ContractInsertSugeridos.uriMatcher.match(uri)==ContractInsertSugeridos.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertSugeridos.INSERT_SUGERIDOS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertSugeridos.CONTENT_URI);
        }else if (ContractInsertSugeridos.uriMatcher.match(uri)==ContractInsertSugeridos.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertSugeridos.INSERT_SUGERIDOS, projection,
                    ContractInsertSugeridos.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertSugeridos.CONTENT_URI);
        }

        else if (ContractInsertCanjes.uriMatcher.match(uri)==ContractInsertCanjes.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertCanjes.INSERT_CANJES, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertCanjes.CONTENT_URI);
        }else if (ContractInsertCanjes.uriMatcher.match(uri)==ContractInsertCanjes.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertCanjes.INSERT_CANJES, projection,
                    ContractInsertCanjes.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertCanjes.CONTENT_URI);
        }

        else if (ContractInsertMaterialesRecibidos.uriMatcher.match(uri)==ContractInsertMaterialesRecibidos.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertMaterialesRecibidos.INSERT_MATERIALES_RECIBIDOS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertMaterialesRecibidos.CONTENT_URI);
        }else if (ContractInsertMaterialesRecibidos.uriMatcher.match(uri)==ContractInsertMaterialesRecibidos.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertMaterialesRecibidos.INSERT_MATERIALES_RECIBIDOS, projection,
                    ContractInsertMaterialesRecibidos.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertMaterialesRecibidos.CONTENT_URI);
        }

        else if (ContractInsertEjecucionMateriales.uriMatcher.match(uri)==ContractInsertEjecucionMateriales.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertEjecucionMateriales.INSERT_EJECUCION_MATERIALES, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertEjecucionMateriales.CONTENT_URI);
        }else if (ContractInsertEjecucionMateriales.uriMatcher.match(uri)==ContractInsertEjecucionMateriales.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertEjecucionMateriales.INSERT_EJECUCION_MATERIALES, projection,
                    ContractInsertEjecucionMateriales.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertEjecucionMateriales.CONTENT_URI);
        }

        else if (ContractInsertMCIPdv.uriMatcher.match(uri)==ContractInsertMCIPdv.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertMCIPdv.INSERT_MCI, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertMCIPdv.CONTENT_URI);
        }else if (ContractInsertMCIPdv.uriMatcher.match(uri)==ContractInsertMCIPdv.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertMCIPdv.INSERT_MCI, projection,
                    ContractInsertMCIPdv.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertMCIPdv.CONTENT_URI);
        }

        else if (ContractInsertCodificados.uriMatcher.match(uri)== ContractInsertCodificados.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertCodificados.INSERT_CODIFICADOS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertCodificados.CONTENT_URI);
        }else if (ContractInsertCodificados.uriMatcher.match(uri)== ContractInsertCodificados.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertCodificados.INSERT_CODIFICADOS, projection,
                    ContractInsertCodificados.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertCodificados.CONTENT_URI);
        }

        else if (ContractInsertRotacion.uriMatcher.match(uri)==ContractInsertRotacion.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertRotacion.INSERT_ROTACION, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertRotacion.CONTENT_URI);
        }else if (ContractInsertRotacion.uriMatcher.match(uri)==ContractInsertRotacion.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertRotacion.INSERT_ROTACION, projection,
                    ContractInsertRotacion.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertRotacion.CONTENT_URI);
        }

        else if (ContractInsertPreguntas.uriMatcher.match(uri)==ContractInsertPreguntas.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertPreguntas.INSERT_PREGUNTAS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertPreguntas.CONTENT_URI);
        }else if (ContractInsertPreguntas.uriMatcher.match(uri)==ContractInsertPreguntas.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertPreguntas.INSERT_PREGUNTAS, projection,
                    ContractInsertPreguntas.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertPreguntas.CONTENT_URI);
        }


        else if(ContractInsertResultadoPreguntas.uriMatcher.match(uri)==ContractInsertResultadoPreguntas.ALLROWS){
            // Consultando todos los registros
            c = db.query(ContractInsertResultadoPreguntas.INSERT_RESULTADO_PREGUNTAS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertResultadoPreguntas.CONTENT_URI);
        }else if(ContractInsertResultadoPreguntas.uriMatcher.match(uri)==ContractInsertResultadoPreguntas.SINGLE_ROW){
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertResultadoPreguntas.INSERT_RESULTADO_PREGUNTAS, projection,
                    ContractInsertResultadoPreguntas.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertResultadoPreguntas.CONTENT_URI);
        }



        else if (ContractInsertPreciosSesion.uriMatcher.match(uri)==ContractInsertPreciosSesion.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertPreciosSesion.INSERT_PRECIOS_SESION, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertPreciosSesion.CONTENT_URI);
        }else if (ContractInsertPreciosSesion.uriMatcher.match(uri)==ContractInsertPreciosSesion.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertPreciosSesion.INSERT_PRECIOS_SESION, projection,
                    ContractInsertPreciosSesion.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertPreciosSesion.CONTENT_URI);
        }

        else if (ContractInsertExh.uriMatcher.match(uri)==ContractInsertExh.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertExh.INSERT_EXH, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertExh.CONTENT_URI);
        }else if (ContractInsertExh.uriMatcher.match(uri)==ContractInsertExh.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertExh.INSERT_EXH, projection,
                    ContractInsertExh.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertExh.CONTENT_URI);
        }

        else if (ContractInsertExhSesion.uriMatcher.match(uri)==ContractInsertExhSesion.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertExhSesion.INSERT_EXH_SESION, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertExhSesion.CONTENT_URI);
        }else if (ContractInsertExhSesion.uriMatcher.match(uri)==ContractInsertExhSesion.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertExhSesion.INSERT_EXH_SESION, projection,
                    ContractInsertExhSesion.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertExhSesion.CONTENT_URI);
        }

        else if (ContractInsertFotografico.uriMatcher.match(uri)==ContractInsertFotografico.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertFotografico.INSERT_FOTOGRAFICO, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertFotografico.CONTENT_URI);
        }else if (ContractInsertFotografico.uriMatcher.match(uri)==ContractInsertFotografico.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertFotografico.INSERT_FOTOGRAFICO, projection,
                    ContractInsertFotografico.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertFotografico.CONTENT_URI);
        }

        else if (ContractInsertEvidencias.uriMatcher.match(uri)==ContractInsertEvidencias.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertEvidencias.INSERT_EVIDENCIAS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertEvidencias.CONTENT_URI);
        }else if (ContractInsertEvidencias.uriMatcher.match(uri)==ContractInsertEvidencias.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertEvidencias.INSERT_EVIDENCIAS, projection,
                    ContractInsertEvidencias.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertEvidencias.CONTENT_URI);
        }

        else if (ContractInsertGps.uriMatcher.match(uri)==ContractInsertGps.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertGps.INSERT_GPS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertGps.CONTENT_URI);
        }else if (ContractInsertGps.uriMatcher.match(uri)==ContractInsertGps.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertGps.INSERT_GPS, projection,
                    ContractInsertGps.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertGps.CONTENT_URI);
        }

        else if (ContractInsertRastreo.uriMatcher.match(uri)==ContractInsertRastreo.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertRastreo.INSERT_GEO, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertRastreo.CONTENT_URI);
        }else if (ContractInsertRastreo.uriMatcher.match(uri)==ContractInsertRastreo.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertRastreo.INSERT_GEO, projection,
                    ContractInsertRastreo.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertRastreo.CONTENT_URI);
        }

        else if (InsertFlooring.uriMatcher.match(uri)==InsertFlooring.ALLROWS) {
            // Consultando todos los registros
            c = db.query(InsertFlooring.INSERT_FLOORING, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    InsertFlooring.CONTENT_URI);
        }else if (InsertFlooring.uriMatcher.match(uri)==InsertFlooring.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(InsertFlooring.INSERT_FLOORING, projection,
                    InsertFlooring.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    InsertFlooring.CONTENT_URI);
        }

        else if (ContractInsertImpulso.uriMatcher.match(uri)==ContractInsertImpulso.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertImpulso.INSERT_IMPUSLO, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertImpulso.CONTENT_URI);
        }else if (ContractInsertImpulso.uriMatcher.match(uri)==ContractInsertImpulso.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertImpulso.INSERT_IMPUSLO, projection,
                    ContractInsertImpulso.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertImpulso.CONTENT_URI);
        }

        else if (ContractNotificacion.uriMatcher.match(uri)==ContractNotificacion.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractNotificacion.NOTIFICACION, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractNotificacion.CONTENT_URI);
        }else if (ContractNotificacion.uriMatcher.match(uri)==ContractNotificacion.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractNotificacion.NOTIFICACION, projection,
                    ContractNotificacion.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractNotificacion.CONTENT_URI);
        }

        else if (ContractInsertInicial.uriMatcher.match(uri)==ContractInsertInicial.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertInicial.INSERT_INICIAL, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertInicial.CONTENT_URI);
        }else if (ContractInsertInicial.uriMatcher.match(uri)==ContractInsertInicial.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertInicial.INSERT_INICIAL, projection,
                    ContractInsertInicial.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertInicial.CONTENT_URI);
        }

        else if (ContractInsertPromocion.uriMatcher.match(uri)==ContractInsertPromocion.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertPromocion.INSERT_PROMO, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertPromocion.CONTENT_URI);
        }else if (ContractInsertPromocion.uriMatcher.match(uri)==ContractInsertPromocion.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertPromocion.INSERT_PROMO, projection,
                    ContractInsertPromocion.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertPromocion.CONTENT_URI);
        }

        else if (ContractInsertImplementacion.uriMatcher.match(uri)==ContractInsertImplementacion.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertImplementacion.INSERT_IMPLEM, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertImplementacion.CONTENT_URI);
        }else if (ContractInsertImplementacion.uriMatcher.match(uri)==ContractInsertImplementacion.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertImplementacion.INSERT_IMPLEM, projection,
                    ContractInsertImplementacion.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertImplementacion.CONTENT_URI);
        }

        else if (ContractInsertValores.uriMatcher.match(uri)==ContractInsertValores.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertValores.INSERT_VALORES, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertValores.CONTENT_URI);
        }else if (ContractInsertValores.uriMatcher.match(uri)==ContractInsertValores.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertValores.INSERT_VALORES, projection,
                    ContractInsertValores.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertValores.CONTENT_URI);
        }

        else if (ContractInsertValoresSesion.uriMatcher.match(uri)==ContractInsertValoresSesion.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertValoresSesion.INSERT_VALORES_SESION, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertValoresSesion.CONTENT_URI);
        }else if (ContractInsertValoresSesion.uriMatcher.match(uri)==ContractInsertValoresSesion.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertValoresSesion.INSERT_VALORES_SESION, projection,
                    ContractInsertValoresSesion.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertValoresSesion.CONTENT_URI);
        }

        else if (ContractInsertPdv.uriMatcher.match(uri)==ContractInsertPdv.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertPdv.INSERT_PDV, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertPdv.CONTENT_URI);
        }else if (ContractInsertPdv.uriMatcher.match(uri)==ContractInsertPdv.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertPdv.INSERT_PDV, projection,
                    ContractInsertPdv.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertPdv.CONTENT_URI);
        }

        else if (ContractInsertShare.uriMatcher.match(uri)==ContractInsertShare.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertShare.INSERT_SHARE, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertShare.CONTENT_URI);
        }else if (ContractInsertShare.uriMatcher.match(uri)==ContractInsertShare.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertShare.INSERT_SHARE, projection,
                    ContractInsertShare.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertShare.CONTENT_URI);
        }

        else if (ContractInsertPDI.uriMatcher.match(uri)==ContractInsertPDI.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertPDI.INSERT_PDI, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertPDI.CONTENT_URI);
        }else if (ContractInsertPDI.uriMatcher.match(uri)==ContractInsertPDI.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertPDI.INSERT_PDI, projection,
                    ContractInsertPDI.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertPDI.CONTENT_URI);
        }

        else if (ContractInsertAgotados.uriMatcher.match(uri)==ContractInsertAgotados.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertAgotados.INSERT_AGOTADOS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertAgotados.CONTENT_URI);
        }else if (ContractInsertAgotados.uriMatcher.match(uri)==ContractInsertAgotados.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertAgotados.INSERT_AGOTADOS, projection,
                    ContractInsertAgotados.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertAgotados.CONTENT_URI);
        }

        else if (ContractInsertTareas.uriMatcher.match(uri)==ContractInsertTareas.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertTareas.INSERT_TAREAS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertTareas.CONTENT_URI);
        }else if (ContractInsertTareas.uriMatcher.match(uri)==ContractInsertTareas.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertTareas.INSERT_TAREAS, projection,
                    ContractInsertTareas.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertTareas.CONTENT_URI);
        }

        else if (ContractInsertVenta.uriMatcher.match(uri)==ContractInsertVenta.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertVenta.INSERT_VENTA, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertVenta.CONTENT_URI);
        }else if (ContractInsertVenta.uriMatcher.match(uri)==ContractInsertVenta.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertVenta.INSERT_VENTA, projection,
                    ContractInsertVenta.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertVenta.CONTENT_URI);
        }

        else if (ContractInsertVentas2.uriMatcher.match(uri)==ContractInsertVentas2.ALLROWS) {
            // Consultando todos los registros
            c = db.query(ContractInsertVentas2.INSERT_PRECIOS, projection,
                    selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertVentas2.CONTENT_URI);
        }else if (ContractInsertVentas2.uriMatcher.match(uri)==ContractInsertVentas2.SINGLE_ROW) {
            // Consultando un solo registro basado en el Id del Uri
            long idGasto = ContentUris.parseId(uri);
            c = db.query(ContractInsertVentas2.INSERT_PRECIOS, projection,
                    ContractInsertVentas2.Columnas._ID + " = " + idGasto,
                    selectionArgs, null, null, sortOrder);
            c.setNotificationUri(
                    resolver,
                    ContractInsertVentas2.CONTENT_URI);
        }

        else{
            throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        if (ContractPharmaValue.uriMatcher.match(uri)== ContractPharmaValue.ALLROWS) {
            return ContractPharmaValue.MULTIPLE_MIME;
        }else if (ContractPharmaValue.uriMatcher.match(uri)== ContractPharmaValue.SINGLE_ROW) {
            return ContractPharmaValue.SINGLE_MIME;
        }

        if(ContractUser.uriMatcher.match(uri)== ContractUser.ALLROWS){
            return ContractUser.MULTIPLE_MIME;
        }else if(ContractUser.uriMatcher.match(uri)== ContractUser.SINGLE_ROW){
            return ContractUser.SINGLE_MIME;
        }


        if(ContractTipoVentas.uriMatcher.match(uri)== ContractTipoVentas.ALLROWS){
            return ContractTipoVentas.MULTIPLE_MIME;
        }else if(ContractTipoVentas.uriMatcher.match(uri)== ContractTipoVentas.SINGLE_ROW){
            return ContractTipoVentas.SINGLE_MIME;
        }

        //IMPLE mpin
        if(ContractTipoImplementaciones.uriMatcher.match(uri)== ContractTipoImplementaciones.ALLROWS){
            return ContractTipoImplementaciones.MULTIPLE_MIME;
        }else if(ContractTipoImplementaciones.uriMatcher.match(uri)== ContractTipoImplementaciones.SINGLE_ROW){
            return ContractTipoImplementaciones.SINGLE_MIME;
        }

        //UNIDADES mpin
        if(ContractTipoUnidades.uriMatcher.match(uri)== ContractTipoUnidades.ALLROWS){
            return ContractTipoUnidades.MULTIPLE_MIME;
        }else if(ContractTipoUnidades.uriMatcher.match(uri)== ContractTipoUnidades.SINGLE_ROW){
            return ContractTipoUnidades.SINGLE_MIME;
        }


        if (ContractProductosSecundarios.uriMatcher.match(uri)== ContractProductosSecundarios.ALLROWS) {
            return ContractProductosSecundarios.MULTIPLE_MIME;
        }else if (ContractProductosSecundarios.uriMatcher.match(uri)== ContractProductosSecundarios.SINGLE_ROW) {
            return ContractProductosSecundarios.SINGLE_MIME;
        }

        if (ContractTracking.uriMatcher.match(uri)== ContractTracking.ALLROWS) {
            return ContractTracking.MULTIPLE_MIME;
        }else if (ContractTracking.uriMatcher.match(uri)== ContractTracking.SINGLE_ROW) {
            return ContractTracking.SINGLE_MIME;
        }

        if (ContractLinkOnedrive.uriMatcher.match(uri)== ContractLinkOnedrive.ALLROWS) {
            return ContractLinkOnedrive.MULTIPLE_MIME;
        }else if (ContractLinkOnedrive.uriMatcher.match(uri)== ContractLinkOnedrive.SINGLE_ROW) {
            return ContractLinkOnedrive.SINGLE_MIME;
        }

        if (ContractPrecios.uriMatcher.match(uri)== ContractPrecios.ALLROWS) {
            return ContractPrecios.MULTIPLE_MIME;
        }else if (ContractPrecios.uriMatcher.match(uri)== ContractPrecios.SINGLE_ROW) {
            return ContractPrecios.SINGLE_MIME;
        }

        if (ContractConvenios.uriMatcher.match(uri)== ContractConvenios.ALLROWS) {
            return ContractConvenios.MULTIPLE_MIME;
        }else if (ContractConvenios.uriMatcher.match(uri)== ContractConvenios.SINGLE_ROW) {
            return ContractConvenios.SINGLE_MIME;
        }

        if (ContractTests.uriMatcher.match(uri)== ContractTests.ALLROWS) {
            return ContractTests.MULTIPLE_MIME;
        }else if (ContractTests.uriMatcher.match(uri)== ContractTests.SINGLE_ROW) {
            return ContractTests.SINGLE_MIME;
        }

        if(ContractExhibicionSupervisor.uriMatcher.match(uri)== ContractExhibicionSupervisor.ALLROWS){

            return ContractExhibicionSupervisor.MULTIPLE_MIME;

        }else if(ContractExhibicionSupervisor.uriMatcher.match(uri)== ContractExhibicionSupervisor.SINGLE_ROW){

            return ContractExhibicionSupervisor.SINGLE_MIME;

        }
        if(ContractEvaluacionEncuesta.uriMatcher.match(uri)== ContractEvaluacionEncuesta.ALLROWS){
            return ContractEvaluacionEncuesta.MULTIPLE_MIME;
        }else if(ContractEvaluacionEncuesta.uriMatcher.match(uri)== ContractEvaluacionEncuesta.SINGLE_ROW){
            return ContractEvaluacionEncuesta.SINGLE_MIME;
        }
        if(ContractInsertEvaluacionEncuesta.uriMatcher.match(uri)== ContractInsertEvaluacionEncuesta.ALLROWS){
            return ContractInsertEvaluacionEncuesta.MULTIPLE_MIME;
        }else if(ContractInsertEvaluacionEncuesta.uriMatcher.match(uri)== ContractInsertEvaluacionEncuesta.SINGLE_ROW){
            return ContractInsertEvaluacionEncuesta.SINGLE_MIME;
        }

        if(ContractTipoNovedades.uriMatcher.match(uri)== ContractTipoNovedades.ALLROWS){
            return ContractTipoNovedades.MULTIPLE_MIME;
        }else if(ContractTipoNovedades.uriMatcher.match(uri)== ContractTipoNovedades.SINGLE_ROW){
            return ContractTipoNovedades.SINGLE_MIME;
        }

        if(ContractCanalesFotos.uriMatcher.match(uri)== ContractCanalesFotos.ALLROWS){
            return ContractCanalesFotos.MULTIPLE_MIME;
        }else if(ContractCanalesFotos.uriMatcher.match(uri)== ContractCanalesFotos.SINGLE_ROW){
            return ContractCanalesFotos.SINGLE_MIME;
        }


        if (ContractTipoExh.uriMatcher.match(uri)== ContractTipoExh.ALLROWS) {
            return ContractTipoExh.MULTIPLE_MIME;
        }else if (ContractTipoExh.uriMatcher.match(uri)== ContractTipoExh.SINGLE_ROW) {
            return ContractTipoExh.SINGLE_MIME;
        }

        if (ContractCategoriaTipo.uriMatcher.match(uri)== ContractCategoriaTipo.ALLROWS) {
            return ContractCategoriaTipo.MULTIPLE_MIME;
        }else if (ContractCategoriaTipo.uriMatcher.match(uri)== ContractCategoriaTipo.SINGLE_ROW) {
            return ContractCategoriaTipo.SINGLE_MIME;
        }

        if (ContractPreguntas.uriMatcher.match(uri)== ContractPreguntas.ALLROWS) {
            return ContractPreguntas.MULTIPLE_MIME;
        }else if (ContractPreguntas.uriMatcher.match(uri)== ContractPreguntas.SINGLE_ROW) {
            return ContractPreguntas.SINGLE_MIME;
        }

        if(ContractJustificacion.uriMatcher.match(uri)== ContractJustificacion.ALLROWS){
            return ContractJustificacion.MULTIPLE_MIME;
        }else if(ContractJustificacion.uriMatcher.match(uri)== ContractJustificacion.SINGLE_ROW){
            return ContractJustificacion.SINGLE_MIME;
        }

        if(ContractTipoInventario.uriMatcher.match(uri)== ContractTipoInventario.ALLROWS){
            return ContractTipoInventario.MULTIPLE_MIME;
        }else if(ContractTipoInventario.uriMatcher.match(uri)== ContractTipoInventario.SINGLE_ROW){
            return ContractTipoInventario.SINGLE_MIME;
        }

        if (ContractPromociones.uriMatcher.match(uri)== ContractPromociones.ALLROWS) {
            return ContractPromociones.MULTIPLE_MIME;
        }else if (ContractPromociones.uriMatcher.match(uri)== ContractPromociones.SINGLE_ROW) {
            return ContractPromociones.SINGLE_MIME;
        }

        if (ContractMotivoSugerido.uriMatcher.match(uri)== ContractMotivoSugerido.ALLROWS) {
            return ContractMotivoSugerido.MULTIPLE_MIME;
        }else if (ContractMotivoSugerido.uriMatcher.match(uri)== ContractMotivoSugerido.SINGLE_ROW) {
            return ContractMotivoSugerido.SINGLE_MIME;
        }

        if (ContractRotacion.uriMatcher.match(uri)== ContractRotacion.ALLROWS) {
            return ContractRotacion.MULTIPLE_MIME;
        }else if (ContractRotacion.uriMatcher.match(uri)== ContractRotacion.SINGLE_ROW) {
            return ContractRotacion.SINGLE_MIME;
        }

        if (ContractPopSugerido.uriMatcher.match(uri)== ContractPopSugerido.ALLROWS) {
            return ContractPopSugerido.MULTIPLE_MIME;
        }else if (ContractPopSugerido.uriMatcher.match(uri)== ContractPopSugerido.SINGLE_ROW) {
            return ContractPopSugerido.SINGLE_MIME;
        }
        
        if (ContractComboCanjes.uriMatcher.match(uri)== ContractComboCanjes.ALLROWS) {
            return ContractComboCanjes.MULTIPLE_MIME;
        }else if (ContractComboCanjes.uriMatcher.match(uri)== ContractComboCanjes.SINGLE_ROW) {
            return ContractComboCanjes.SINGLE_MIME;
        }

        if (ContractCausalesMCI.uriMatcher.match(uri)== ContractCausalesMCI.ALLROWS) {
            return ContractCausalesMCI.MULTIPLE_MIME;
        }else if (ContractCausalesMCI.uriMatcher.match(uri)== ContractCausalesMCI.SINGLE_ROW) {
            return ContractCausalesMCI.SINGLE_MIME;
        }

        if (ContractCausalesOSA.uriMatcher.match(uri)== ContractCausalesOSA.ALLROWS) {
            return ContractCausalesOSA.MULTIPLE_MIME;
        }else if (ContractCausalesOSA.uriMatcher.match(uri)== ContractCausalesOSA.SINGLE_ROW) {
            return ContractCausalesOSA.SINGLE_MIME;
        }

        if (ContractAlertas.uriMatcher.match(uri)== ContractAlertas.ALLROWS) {
            return ContractAlertas.MULTIPLE_MIME;
        }else if (ContractAlertas.uriMatcher.match(uri)== ContractAlertas.SINGLE_ROW) {
            return ContractAlertas.SINGLE_MIME;
        }

        if (ContractPDI.uriMatcher.match(uri)== ContractPDI.ALLROWS) {
            return ContractPDI.MULTIPLE_MIME;
        }else if (ContractPDI.uriMatcher.match(uri)== ContractPDI.SINGLE_ROW) {
            return ContractPDI.SINGLE_MIME;
        }

        if (ContractLog.uriMatcher.match(uri)== ContractLog.ALLROWS) {
            return ContractLog.MULTIPLE_MIME;
        }else if (ContractLog.uriMatcher.match(uri)== ContractLog.SINGLE_ROW) {
            return ContractLog.SINGLE_MIME;
        }

        if (ContractPrioritario.uriMatcher.match(uri)== ContractPrioritario.ALLROWS) {
            return ContractPrioritario.MULTIPLE_MIME;
        }else if (ContractPrioritario.uriMatcher.match(uri)== ContractPrioritario.SINGLE_ROW) {
            return ContractPrioritario.SINGLE_MIME;
        }

        if(ContractCamposPorModulos.uriMatcher.match(uri)== ContractCamposPorModulos.ALLROWS){
            return ContractCamposPorModulos.MULTIPLE_MIME;
        }else if(ContractCamposPorModulos.uriMatcher.match(uri)== ContractCamposPorModulos.SINGLE_ROW){
            return ContractCamposPorModulos.SINGLE_MIME;
        }

        if(ContractPromocionalVentas.uriMatcher.match(uri)== ContractPromocionalVentas.ALLROWS){
            return ContractPromocionalVentas.MULTIPLE_MIME;
        }else if(ContractPromocionalVentas.uriMatcher.match(uri)== ContractPromocionalVentas.SINGLE_ROW){
            return ContractPromocionalVentas.SINGLE_MIME;
        }

        if (ContractTareas.uriMatcher.match(uri)== ContractTareas.ALLROWS) {
            return ContractTareas.MULTIPLE_MIME;
        }else if (ContractTareas.uriMatcher.match(uri)== ContractTareas.SINGLE_ROW) {
            return ContractTareas.SINGLE_MIME;
        }

        if (ContractPortafolioProductos.uriMatcher.match(uri)== ContractPortafolioProductos.ALLROWS) {
            return ContractPortafolioProductos.MULTIPLE_MIME;
        }else if (ContractPortafolioProductos.uriMatcher.match(uri)== ContractPortafolioProductos.SINGLE_ROW) {
            return ContractPortafolioProductos.SINGLE_MIME;
        }

        if (ContractPortafolioProductosAASS.uriMatcher.match(uri)== ContractPortafolioProductosAASS.ALLROWS) {
            return ContractPortafolioProductosAASS.MULTIPLE_MIME;
        }else if (ContractPortafolioProductosAASS.uriMatcher.match(uri)== ContractPortafolioProductosAASS.SINGLE_ROW) {
            return ContractPortafolioProductosAASS.SINGLE_MIME;
        }

        if (ContractPortafolioProductosMAYO.uriMatcher.match(uri)== ContractPortafolioProductosMAYO.ALLROWS) {
            return ContractPortafolioProductosMAYO.MULTIPLE_MIME;
        }else if (ContractPortafolioProductosMAYO.uriMatcher.match(uri)== ContractPortafolioProductosMAYO.SINGLE_ROW) {
            return ContractPortafolioProductosMAYO.SINGLE_MIME;
        }

        else{
            throw new IllegalArgumentException("Tipo de URI desconocida: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (ContractPharmaValue.uriMatcher.match(uri) == ContractPharmaValue.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractPharmaValue.POS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractPharmaValue.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }
        //probadores mpin
        if (ContractTipoRegistro.uriMatcher.match(uri) == ContractTipoRegistro.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractTipoRegistro.TIPO_REGISTRO, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractTipoRegistro.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }



        if (ContractTipoVentas.uriMatcher.match(uri) == ContractTipoVentas.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractTipoVentas.TIPO_VENTAS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractTipoVentas.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        //actividades mpin
        if (ContractTipoActividades.uriMatcher.match(uri) == ContractTipoActividades.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractTipoActividades.TIPO_ACTIVIDADES, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractTipoActividades.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        //imple mpin
        if (ContractTipoImplementaciones.uriMatcher.match(uri) == ContractTipoImplementaciones.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractTipoImplementaciones.TIPO_IMPLEMENTACIONES, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractTipoImplementaciones.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        //UNIDADES mpin
        if (ContractTipoUnidades.uriMatcher.match(uri) == ContractTipoUnidades.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractTipoUnidades.TIPO_UNIDADES, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractTipoUnidades.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        //MODULOS POR ROLES mpin
        if (ContractModulosRoles.uriMatcher.match(uri) == ContractModulosRoles.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractModulosRoles.MODULO_ROLES, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractModulosRoles.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractUser.uriMatcher.match(uri) == ContractUser.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractUser.TABLE_USER, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractUser.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractProductosSecundarios.uriMatcher.match(uri) == ContractProductosSecundarios.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractProductosSecundarios.PRODUCTOS_SECUNDARIOS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractProductosSecundarios.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }
        
        if (InsertExhBassa.uriMatcher.match(uri) == InsertExhBassa.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(InsertExhBassa.INSERT_EXHIBICION_COLGATE, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        InsertExhBassa.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (InsertExhibicionesAdNu.uriMatcher.match(uri) == InsertExhibicionesAdNu.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(InsertExhibicionesAdNu.INSERT_EXHIBICION_AD_NU, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        InsertExhibicionesAdNu.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }
        if(ContractExhibicionSupervisor.uriMatcher.match(uri) == ContractExhibicionSupervisor.ALLROWS){
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR, null, contentValues);
            if (rowId > 0) {
                System.out.println("INSERT ContractExhibicionSupervisor: insertando localmente");
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractExhibicionSupervisor.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en ContractExhibicionSupervisor: " + uri);
        }
        if (ContractEvaluacionEncuesta.uriMatcher.match(uri) == ContractEvaluacionEncuesta.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }


            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractEvaluacionEncuesta.EVALUACION_PROMOTOR, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractEvaluacionEncuesta.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }
        if (ContractInsertEvaluacionEncuesta.uriMatcher.match(uri) == ContractInsertEvaluacionEncuesta.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }


            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertEvaluacionEncuesta.INSERT_EVALUACION, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertEvaluacionEncuesta.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }
        if (ContractTipoNovedades.uriMatcher.match(uri) == ContractTipoNovedades.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractTipoNovedades.TIPO_NOVEDADES, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractTipoNovedades.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }
        if (ContractCanalesFotos.uriMatcher.match(uri) == ContractCanalesFotos.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractCanalesFotos.CANALES_FOTOS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractCanalesFotos.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertNovedades.uriMatcher.match(uri) == ContractInsertNovedades.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertNovedades.INSERT_NOVEDADES, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertNovedades.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractTracking.uriMatcher.match(uri) == ContractTracking.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractTracking.TRACKING, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractTracking.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractLinkOnedrive.uriMatcher.match(uri) == ContractLinkOnedrive.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractLinkOnedrive.LINK_ONDRIVE, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractLinkOnedrive.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractPrecios.uriMatcher.match(uri) == ContractPrecios.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractPrecios.PRECIOS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractPrecios.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractConvenios.uriMatcher.match(uri) == ContractConvenios.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractConvenios.CONVENIOS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractConvenios.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }


        if (ContractTests.uriMatcher.match(uri) == ContractTests.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractTests.TABLE_TEST, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractTests.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }


        if (ContractJustificacion.uriMatcher.match(uri) == ContractJustificacion.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractJustificacion.JUSTIFICACIONES, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractJustificacion.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }


        if (ContractTipoInventario.uriMatcher.match(uri) == ContractTipoInventario.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractTipoInventario.TIPOINVENTARIO, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractTipoInventario.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }


        if (ContractTipoExh.uriMatcher.match(uri) == ContractTipoExh.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractTipoExh.TABLE_NAME, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractTipoExh.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }


        if (ContractCategoriaTipo.uriMatcher.match(uri) == ContractCategoriaTipo.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractCategoriaTipo.CATEGORIA_TIPO, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractCategoriaTipo.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }


        if (ContractPreguntas.uriMatcher.match(uri) == ContractPreguntas.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractPreguntas.TABLE_QUEST, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractPreguntas.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }


        if (ContractPromociones.uriMatcher.match(uri) == ContractPromociones.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractPromociones.PROMOCIONES, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractPromociones.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractMotivoSugerido.uriMatcher.match(uri) == ContractMotivoSugerido.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractMotivoSugerido.TABLE_NAME, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractMotivoSugerido.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractRotacion.uriMatcher.match(uri) == ContractRotacion.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractRotacion.ROTACION, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractRotacion.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractPopSugerido.uriMatcher.match(uri) == ContractPopSugerido.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractPopSugerido.POPSUGERIDO, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractPopSugerido.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractComboCanjes.uriMatcher.match(uri) == ContractComboCanjes.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractComboCanjes.COMBO_CANJES, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractComboCanjes.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractCausalesMCI.uriMatcher.match(uri) == ContractCausalesMCI.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractCausalesMCI.CAUSALES_MCI, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractCausalesMCI.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractCausalesOSA.uriMatcher.match(uri) == ContractCausalesOSA.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractCausalesOSA.CAUSALES_OSA, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractCausalesOSA.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractAlertas.uriMatcher.match(uri) == ContractAlertas.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractAlertas.ALERTAS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractAlertas.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractPDI.uriMatcher.match(uri) == ContractPDI.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractPDI.PDI, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractPDI.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractLog.uriMatcher.match(uri) == ContractLog.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractLog.LOG, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractLog.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractPrioritario.uriMatcher.match(uri) == ContractPrioritario.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractPrioritario.PRIORITARIO, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractPrioritario.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractCamposPorModulos.uriMatcher.match(uri) == ContractCamposPorModulos.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractCamposPorModulos.CAMPOS_X_MODULOS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractCamposPorModulos.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractPromocionalVentas.uriMatcher.match(uri) == ContractPromocionalVentas.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractPromocionalVentas.PROMOCIONAL_VENTAS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractPromocionalVentas.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractTareas.uriMatcher.match(uri) == ContractTareas.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractTareas.TAREA, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractTareas.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractPortafolioProductos.uriMatcher.match(uri) == ContractPortafolioProductos.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractPortafolioProductos.PORTAFOLIOPRODUCTOS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractPortafolioProductos.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractPortafolioProductosAASS.uriMatcher.match(uri) == ContractPortafolioProductosAASS.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractPortafolioProductosAASS.PORTAFOLIOPRODUCTOS_AASS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractPortafolioProductosAASS.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractPortafolioProductosMAYO.uriMatcher.match(uri) == ContractPortafolioProductosMAYO.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractPortafolioProductosMAYO.PORTAFOLIOPRODUCTOS_MAYO, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractPortafolioProductosMAYO.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        //INSERTS//

        if (ContractInsertPrecios.uriMatcher.match(uri) == ContractInsertPrecios.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertPrecios.INSERT_PRECIOS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertPrecios.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        //NUEVO INSERT Mpin

        if (ContractInsertMuestras.uriMatcher.match(uri) == ContractInsertMuestras.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertMuestras.INSERT_MUESTRAS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertMuestras.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }


        //NUEVO INSERT Mpin probadores

        if (ContractInsertProbadores.uriMatcher.match(uri) == ContractInsertProbadores.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertProbadores.INSERT_PROBADORES, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertProbadores.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }


        if (ContractInsertConvenios.uriMatcher.match(uri) == ContractInsertConvenios.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertConvenios.INSERT_CONVENIOS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertConvenios.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertTracking.uriMatcher.match(uri) == ContractInsertTracking.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertTracking.INSERT_TRACKING, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertTracking.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }


        if (ContractInsertPacks.uriMatcher.match(uri) == ContractInsertPacks.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertPacks.INSERT_PACKS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertPacks.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertProdCaducar.uriMatcher.match(uri) == ContractInsertProdCaducar.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertProdCaducar.INSERT_PROD_CADUCAR, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertProdCaducar.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertSugeridos.uriMatcher.match(uri) == ContractInsertSugeridos.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertSugeridos.INSERT_SUGERIDOS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertSugeridos.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertCanjes.uriMatcher.match(uri) == ContractInsertCanjes.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertCanjes.INSERT_CANJES, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertCanjes.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertMaterialesRecibidos.uriMatcher.match(uri) == ContractInsertMaterialesRecibidos.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertMaterialesRecibidos.INSERT_MATERIALES_RECIBIDOS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertMaterialesRecibidos.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertEjecucionMateriales.uriMatcher.match(uri) == ContractInsertEjecucionMateriales.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertEjecucionMateriales.INSERT_EJECUCION_MATERIALES, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertEjecucionMateriales.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertMCIPdv.uriMatcher.match(uri) == ContractInsertMCIPdv.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertMCIPdv.INSERT_MCI, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertMCIPdv.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertCodificados.uriMatcher.match(uri) == ContractInsertCodificados.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertCodificados.INSERT_CODIFICADOS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertCodificados.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertTareas.uriMatcher.match(uri) == ContractInsertTareas.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertTareas.INSERT_TAREAS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertTareas.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertRotacion.uriMatcher.match(uri) == ContractInsertRotacion.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertRotacion.INSERT_ROTACION, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertRotacion.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertPreguntas.uriMatcher.match(uri) == ContractInsertPreguntas.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertPreguntas.INSERT_PREGUNTAS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertPreguntas.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }


        if (ContractInsertResultadoPreguntas.uriMatcher.match(uri) == ContractInsertResultadoPreguntas.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertResultadoPreguntas.INSERT_RESULTADO_PREGUNTAS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertResultadoPreguntas.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }



        if (ContractInsertPreciosSesion.uriMatcher.match(uri) == ContractInsertPreciosSesion.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertPreciosSesion.INSERT_PRECIOS_SESION, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertPreciosSesion.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (InsertFlooring.uriMatcher.match(uri) == InsertFlooring.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(InsertFlooring.INSERT_FLOORING, null, contentValues);
            Log.i("ROW ID", String.valueOf(rowId));
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        InsertFlooring.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertImpulso.uriMatcher.match(uri) == ContractInsertImpulso.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertImpulso.INSERT_IMPUSLO, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertImpulso.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertExh.uriMatcher.match(uri) == ContractInsertExh.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertExh.INSERT_EXH, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertExh.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertExhSesion.uriMatcher.match(uri) == ContractInsertExhSesion.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertExhSesion.INSERT_EXH_SESION, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertExhSesion.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertFotografico.uriMatcher.match(uri) == ContractInsertFotografico.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertFotografico.INSERT_FOTOGRAFICO, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertFotografico.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertEvidencias.uriMatcher.match(uri) == ContractInsertEvidencias.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertEvidencias.INSERT_EVIDENCIAS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertEvidencias.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertGps.uriMatcher.match(uri) == ContractInsertGps.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertGps.INSERT_GPS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertGps.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertRastreo.uriMatcher.match(uri) == ContractInsertRastreo.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertRastreo.INSERT_GEO, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertRastreo.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractNotificacion.uriMatcher.match(uri) == ContractNotificacion.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractNotificacion.NOTIFICACION, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractNotificacion.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertInicial.uriMatcher.match(uri) == ContractInsertInicial.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertInicial.INSERT_INICIAL, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertInicial.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertPromocion.uriMatcher.match(uri) == ContractInsertPromocion.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertPromocion.INSERT_PROMO, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertPromocion.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertImplementacion.uriMatcher.match(uri) == ContractInsertImplementacion.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertImplementacion.INSERT_IMPLEM, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertImplementacion.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertValores.uriMatcher.match(uri) == ContractInsertValores.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertValores.INSERT_VALORES, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertValores.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertValoresSesion.uriMatcher.match(uri) == ContractInsertValoresSesion.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertValoresSesion.INSERT_VALORES_SESION, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertValoresSesion.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertPdv.uriMatcher.match(uri) == ContractInsertPdv.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertPdv.INSERT_PDV, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertPdv.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertShare.uriMatcher.match(uri) == ContractInsertShare.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertShare.INSERT_SHARE, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertShare.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertPDI.uriMatcher.match(uri) == ContractInsertPDI.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertPDI.INSERT_PDI, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertPDI.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertAgotados.uriMatcher.match(uri) == ContractInsertAgotados.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertAgotados.INSERT_AGOTADOS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertAgotados.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertVenta.uriMatcher.match(uri) == ContractInsertVenta.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertVenta.INSERT_VENTA, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertVenta.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        if (ContractInsertVentas2.uriMatcher.match(uri) == ContractInsertVentas2.ALLROWS) {
            ContentValues contentValues;
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }

            // Inserción de nueva fila
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long rowId = db.insert(ContractInsertVentas2.INSERT_PRECIOS, null, contentValues);
            if (rowId > 0) {
                Uri uri_stock = ContentUris.withAppendedId(
                        ContractInsertVentas2.CONTENT_URI, rowId);
                resolver.notifyChange(uri_stock, null, false);
                return uri_stock;
            }
            throw new SQLException("Falla al insertar fila en : " + uri);
        }

        throw new IllegalArgumentException("URI desconocida : " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int affected;

        if (ContractPharmaValue.uriMatcher.match(uri)== ContractPharmaValue.ALLROWS) {
            affected = db.delete(ContractPharmaValue.POS,
                    selection,
                    selectionArgs);
        }else if (ContractPharmaValue.uriMatcher.match(uri)== ContractPharmaValue.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractPharmaValue.POS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

//mpin tipo REGISTRO probadores
        else if(ContractTipoRegistro.uriMatcher.match(uri)== ContractTipoRegistro.ALLROWS){
            affected = db.delete(ContractTipoRegistro.TIPO_REGISTRO,
                    selection,
                    selectionArgs);
        }else if(ContractTipoRegistro.uriMatcher.match(uri)== ContractTipoRegistro.SINGLE_ROW){
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractTipoRegistro.TIPO_REGISTRO,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }
        //mpin tipoactividades- muestras emdicas
        else if(ContractTipoActividades.uriMatcher.match(uri)== ContractTipoActividades.ALLROWS){
            affected = db.delete(ContractTipoActividades.TIPO_ACTIVIDADES,
                    selection,
                    selectionArgs);
        }else if(ContractTipoActividades.uriMatcher.match(uri)== ContractTipoActividades.SINGLE_ROW){
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractTipoActividades.TIPO_ACTIVIDADES,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if(ContractTipoVentas.uriMatcher.match(uri)== ContractTipoVentas.ALLROWS){
            affected = db.delete(ContractTipoVentas.TIPO_VENTAS,
                    selection,
                    selectionArgs);
        }else if(ContractTipoVentas.uriMatcher.match(uri)== ContractTipoVentas.SINGLE_ROW){
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractTipoVentas.TIPO_VENTAS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        //MPIN IMPLE
        else if(ContractTipoImplementaciones.uriMatcher.match(uri)== ContractTipoImplementaciones.ALLROWS){
            affected = db.delete(ContractTipoImplementaciones.TIPO_IMPLEMENTACIONES,
                    selection,
                    selectionArgs);
        }else if(ContractTipoImplementaciones.uriMatcher.match(uri)== ContractTipoImplementaciones.SINGLE_ROW){
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractTipoImplementaciones.TIPO_IMPLEMENTACIONES,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        //MPIN UNIDADES
        else if(ContractTipoUnidades.uriMatcher.match(uri)== ContractTipoUnidades.ALLROWS){
            affected = db.delete(ContractTipoUnidades.TIPO_UNIDADES,
                    selection,
                    selectionArgs);
        }else if(ContractTipoUnidades.uriMatcher.match(uri)== ContractTipoUnidades.SINGLE_ROW){
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractTipoUnidades.TIPO_UNIDADES,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        //MPIN MODULOS POR ROLES
        else if(ContractModulosRoles.uriMatcher.match(uri)== ContractModulosRoles.ALLROWS){
            affected = db.delete(ContractModulosRoles.MODULO_ROLES,
                    selection,
                    selectionArgs);
        }else if(ContractModulosRoles.uriMatcher.match(uri)== ContractModulosRoles.SINGLE_ROW){
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractModulosRoles.MODULO_ROLES,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }
        else if(ContractUser.uriMatcher.match(uri)== ContractUser.ALLROWS){
            affected = db.delete(ContractUser.TABLE_USER,
                    selection,
                    selectionArgs);
        }else if(ContractUser.uriMatcher.match(uri)== ContractUser.SINGLE_ROW){
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractUser.TABLE_USER,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractProductosSecundarios.uriMatcher.match(uri)==ContractProductosSecundarios.ALLROWS) {
            affected = db.delete(ContractProductosSecundarios.PRODUCTOS_SECUNDARIOS,
                    selection,
                    selectionArgs);
        }else if (ContractProductosSecundarios.uriMatcher.match(uri)==ContractProductosSecundarios.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractProductosSecundarios.PRODUCTOS_SECUNDARIOS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractTracking.uriMatcher.match(uri)==ContractTracking.ALLROWS) {
            affected = db.delete(ContractTracking.TRACKING,
                    selection,
                    selectionArgs);
        }else if (ContractTracking.uriMatcher.match(uri)==ContractTracking.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractTracking.TRACKING,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }
        else if(InsertExhBassa.uriMatcher.match(uri)==InsertExhBassa.ALLROWS){
            affected = db.delete(InsertExhBassa.INSERT_EXHIBICION_COLGATE,
                    selection,
                    selectionArgs);
        }else if(InsertExhBassa.uriMatcher.match(uri)==InsertExhBassa.SINGLE_ROW){
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(InsertExhBassa.INSERT_EXHIBICION_COLGATE,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }else
        if(InsertExhibicionesAdNu.uriMatcher.match(uri)==InsertExhibicionesAdNu.ALLROWS){
            affected = db.delete(InsertExhibicionesAdNu.INSERT_EXHIBICION_AD_NU,
                    selection,
                    selectionArgs);
        }else if(InsertExhibicionesAdNu.uriMatcher.match(uri)==InsertExhibicionesAdNu.SINGLE_ROW){
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(InsertExhibicionesAdNu.INSERT_EXHIBICION_AD_NU,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        } else if(ContractExhibicionSupervisor.uriMatcher.match(uri)== ContractExhibicionSupervisor.ALLROWS){
            affected = db.delete(ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR,
                    selection,
                    selectionArgs);
        }else if(ContractExhibicionSupervisor.uriMatcher.match(uri)== ContractExhibicionSupervisor.SINGLE_ROW){
            long idPos = ContentUris.parseId(uri);
            affected = db.delete(ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR,
                    ContractExhibicionSupervisor.Columnas.ID_REMOTA + "=" + idPos
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }else
        if(ContractEvaluacionEncuesta.uriMatcher.match(uri)==ContractEvaluacionEncuesta.ALLROWS){
            affected = db.delete(ContractEvaluacionEncuesta.EVALUACION_PROMOTOR,
                    selection,
                    selectionArgs);
        }else if(ContractEvaluacionEncuesta.uriMatcher.match(uri)==ContractEvaluacionEncuesta.SINGLE_ROW){
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractEvaluacionEncuesta.EVALUACION_PROMOTOR,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }else
        if(ContractInsertEvaluacionEncuesta.uriMatcher.match(uri)==ContractInsertEvaluacionEncuesta.ALLROWS){
            affected = db.delete(ContractInsertEvaluacionEncuesta.INSERT_EVALUACION,
                    selection,
                    selectionArgs);
        }else if(ContractInsertEvaluacionEncuesta.uriMatcher.match(uri)==ContractInsertEvaluacionEncuesta.SINGLE_ROW){
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertEvaluacionEncuesta.INSERT_EVALUACION,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }else
            if (ContractLinkOnedrive.uriMatcher.match(uri)==ContractLinkOnedrive.ALLROWS) {
            affected = db.delete(ContractLinkOnedrive.LINK_ONDRIVE,
                    selection,
                    selectionArgs);
        }else if (ContractLinkOnedrive.uriMatcher.match(uri)==ContractLinkOnedrive.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractLinkOnedrive.LINK_ONDRIVE,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }
        else if(ContractTipoNovedades.uriMatcher.match(uri)== ContractTipoNovedades.ALLROWS){
            affected = db.delete(ContractTipoNovedades.TIPO_NOVEDADES,
                    selection,
                    selectionArgs);
        }else if(ContractTipoNovedades.uriMatcher.match(uri)== ContractTipoNovedades.SINGLE_ROW){
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractTipoNovedades.TIPO_NOVEDADES,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }
            else if(ContractCanalesFotos.uriMatcher.match(uri)== ContractCanalesFotos.ALLROWS){
                affected = db.delete(ContractCanalesFotos.CANALES_FOTOS,
                        selection,
                        selectionArgs);
            }else if(ContractCanalesFotos.uriMatcher.match(uri)== ContractCanalesFotos.SINGLE_ROW){
                long idStock = ContentUris.parseId(uri);
                affected = db.delete(ContractCanalesFotos.CANALES_FOTOS,
                        Constantes.ID_REMOTA + "=" + idStock
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                resolver.
                        notifyChange(uri, null, false);
            }
        else if(ContractInsertNovedades.uriMatcher.match(uri)==ContractInsertNovedades.ALLROWS){
            affected = db.delete(ContractInsertNovedades.INSERT_NOVEDADES,
                    selection,
                    selectionArgs);
        }else if(ContractInsertNovedades.uriMatcher.match(uri)==ContractInsertNovedades.SINGLE_ROW){
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertNovedades.INSERT_NOVEDADES,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractPrecios.uriMatcher.match(uri)==ContractPrecios.ALLROWS) {
            affected = db.delete(ContractPrecios.PRECIOS,
                    selection,
                    selectionArgs);
        }else if (ContractPrecios.uriMatcher.match(uri)==ContractPrecios.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractPrecios.PRECIOS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractConvenios.uriMatcher.match(uri)==ContractConvenios.ALLROWS) {
            affected = db.delete(ContractConvenios.CONVENIOS,
                    selection,
                    selectionArgs);
        }else if (ContractConvenios.uriMatcher.match(uri)==ContractConvenios.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractConvenios.CONVENIOS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractPreguntas.uriMatcher.match(uri)==ContractPreguntas.ALLROWS) {
            affected = db.delete(ContractPreguntas.TABLE_QUEST,
                    selection,
                    selectionArgs);
        }else if (ContractPreguntas.uriMatcher.match(uri)==ContractPreguntas.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractPreguntas.TABLE_QUEST,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractTests.uriMatcher.match(uri)==ContractTests.ALLROWS) {
            affected = db.delete(ContractTests.TABLE_TEST,
                    selection,
                    selectionArgs);
        }else if (ContractTests.uriMatcher.match(uri)==ContractTests.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractTests.TABLE_TEST,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }


        else if(ContractJustificacion.uriMatcher.match(uri)==ContractJustificacion.ALLROWS){
            affected = db.delete(ContractJustificacion.JUSTIFICACIONES,
                    selection,
                    selectionArgs);
        }else if(ContractJustificacion.uriMatcher.match(uri)==ContractJustificacion.SINGLE_ROW){
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractJustificacion.JUSTIFICACIONES,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }


        else if(ContractTipoInventario.uriMatcher.match(uri)==ContractTipoInventario.ALLROWS){
            affected = db.delete(ContractTipoInventario.TIPOINVENTARIO,
                    selection,
                    selectionArgs);
        }else if(ContractTipoInventario.uriMatcher.match(uri)==ContractTipoInventario.SINGLE_ROW){
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractTipoInventario.TIPOINVENTARIO,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }



        else if (ContractTipoExh.uriMatcher.match(uri)==ContractTipoExh.ALLROWS) {
            affected = db.delete(ContractTipoExh.TABLE_NAME,
                    selection,
                    selectionArgs);
        }else if (ContractTipoExh.uriMatcher.match(uri)==ContractTipoExh.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractTipoExh.TABLE_NAME,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractCategoriaTipo.uriMatcher.match(uri)==ContractCategoriaTipo.ALLROWS) {
                affected = db.delete(ContractCategoriaTipo.CATEGORIA_TIPO,
                        selection,
                        selectionArgs);
            }else if (ContractCategoriaTipo.uriMatcher.match(uri)==ContractCategoriaTipo.SINGLE_ROW) {
                long idStock = ContentUris.parseId(uri);
                affected = db.delete(ContractCategoriaTipo.CATEGORIA_TIPO,
                        Constantes.ID_REMOTA + "=" + idStock
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                resolver.
                        notifyChange(uri, null, false);
        }


        else if (ContractPromociones.uriMatcher.match(uri)== ContractPromociones.ALLROWS) {
            affected = db.delete(ContractPromociones.PROMOCIONES,
                    selection,
                    selectionArgs);
        }else if (ContractPromociones.uriMatcher.match(uri)== ContractPromociones.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractPromociones.PROMOCIONES,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractMotivoSugerido.uriMatcher.match(uri)== ContractMotivoSugerido.ALLROWS) {
            affected = db.delete(ContractMotivoSugerido.TABLE_NAME,
                    selection,
                    selectionArgs);
        }else if (ContractMotivoSugerido.uriMatcher.match(uri)== ContractMotivoSugerido.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractMotivoSugerido.TABLE_NAME,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractRotacion.uriMatcher.match(uri)== ContractRotacion.ALLROWS) {
            affected = db.delete(ContractRotacion.ROTACION,
                    selection,
                    selectionArgs);
        }else if (ContractRotacion.uriMatcher.match(uri)== ContractRotacion.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractRotacion.ROTACION,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractPopSugerido.uriMatcher.match(uri)== ContractPopSugerido.ALLROWS) {
            affected = db.delete(ContractPopSugerido.POPSUGERIDO,
                    selection,
                    selectionArgs);
        }else if (ContractPopSugerido.uriMatcher.match(uri)== ContractPopSugerido.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractPopSugerido.POPSUGERIDO,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractComboCanjes.uriMatcher.match(uri)== ContractComboCanjes.ALLROWS) {
            affected = db.delete(ContractComboCanjes.COMBO_CANJES,
                    selection,
                    selectionArgs);
        }else if (ContractComboCanjes.uriMatcher.match(uri)== ContractComboCanjes.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractComboCanjes.COMBO_CANJES,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractCausalesMCI.uriMatcher.match(uri)== ContractCausalesMCI.ALLROWS) {
            affected = db.delete(ContractCausalesMCI.CAUSALES_MCI,
                    selection,
                    selectionArgs);
        }else if (ContractCausalesMCI.uriMatcher.match(uri)== ContractCausalesMCI.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractCausalesMCI.CAUSALES_MCI,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractCausalesOSA.uriMatcher.match(uri)== ContractCausalesOSA.ALLROWS) {
            affected = db.delete(ContractCausalesOSA.CAUSALES_OSA,
                    selection,
                    selectionArgs);
        }else if (ContractCausalesOSA.uriMatcher.match(uri)== ContractCausalesOSA.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractCausalesOSA.CAUSALES_OSA,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractAlertas.uriMatcher.match(uri)== ContractAlertas.ALLROWS) {
            affected = db.delete(ContractAlertas.ALERTAS,
                    selection,
                    selectionArgs);
        }else if (ContractAlertas.uriMatcher.match(uri)== ContractAlertas.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractAlertas.ALERTAS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractPDI.uriMatcher.match(uri)== ContractPDI.ALLROWS) {
            affected = db.delete(ContractPDI.PDI,
                    selection,
                    selectionArgs);
        }else if (ContractPDI.uriMatcher.match(uri)== ContractPDI.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractPDI.PDI,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractLog.uriMatcher.match(uri)== ContractLog.ALLROWS) {
            affected = db.delete(ContractLog.LOG,
                    selection,
                    selectionArgs);
        }else if (ContractLog.uriMatcher.match(uri)== ContractLog.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractLog.LOG,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractPrioritario.uriMatcher.match(uri)== ContractPrioritario.ALLROWS) {
            affected = db.delete(ContractPrioritario.PRIORITARIO,
                    selection,
                    selectionArgs);
        }else if (ContractPrioritario.uriMatcher.match(uri)== ContractPrioritario.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractPrioritario.PRIORITARIO,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

            else if(ContractCamposPorModulos.uriMatcher.match(uri)== ContractCamposPorModulos.ALLROWS){
                affected = db.delete(ContractCamposPorModulos.CAMPOS_X_MODULOS,
                        selection,
                        selectionArgs);
            }else if(ContractCamposPorModulos.uriMatcher.match(uri)== ContractCamposPorModulos.SINGLE_ROW){
                long idStock = ContentUris.parseId(uri);
                affected = db.delete(ContractCamposPorModulos.CAMPOS_X_MODULOS,
                        Constantes.ID_REMOTA + "=" + idStock
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                resolver.
                        notifyChange(uri, null, false);
            }

            else if(ContractPromocionalVentas.uriMatcher.match(uri)== ContractPromocionalVentas.ALLROWS){
                affected = db.delete(ContractPromocionalVentas.PROMOCIONAL_VENTAS,
                        selection,
                        selectionArgs);
            }else if(ContractPromocionalVentas.uriMatcher.match(uri)== ContractPromocionalVentas.SINGLE_ROW){
                long idStock = ContentUris.parseId(uri);
                affected = db.delete(ContractPromocionalVentas.PROMOCIONAL_VENTAS,
                        Constantes.ID_REMOTA + "=" + idStock
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                resolver.
                        notifyChange(uri, null, false);
            }

        else if (ContractTareas.uriMatcher.match(uri)== ContractTareas.ALLROWS) {
            affected = db.delete(ContractTareas.TAREA,
                    selection,
                    selectionArgs);
        }else if (ContractTareas.uriMatcher.match(uri)== ContractTareas.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractTareas.TAREA,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }


        else if (ContractPortafolioProductos.uriMatcher.match(uri)== ContractPortafolioProductos.ALLROWS) {
            affected = db.delete(ContractPortafolioProductos.PORTAFOLIOPRODUCTOS,
                    selection,
                    selectionArgs);
        }else if (ContractPortafolioProductos.uriMatcher.match(uri)== ContractPortafolioProductos.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractPortafolioProductos.PORTAFOLIOPRODUCTOS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractPortafolioProductosAASS.uriMatcher.match(uri)== ContractPortafolioProductosAASS.ALLROWS) {
            affected = db.delete(ContractPortafolioProductosAASS.PORTAFOLIOPRODUCTOS_AASS,
                    selection,
                    selectionArgs);
        }else if (ContractPortafolioProductosAASS.uriMatcher.match(uri)== ContractPortafolioProductosAASS.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractPortafolioProductosAASS.PORTAFOLIOPRODUCTOS_AASS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractPortafolioProductosMAYO.uriMatcher.match(uri)== ContractPortafolioProductosMAYO.ALLROWS) {
            affected = db.delete(ContractPortafolioProductosMAYO.PORTAFOLIOPRODUCTOS_MAYO,
                    selection,
                    selectionArgs);
        }else if (ContractPortafolioProductosMAYO.uriMatcher.match(uri)== ContractPortafolioProductosMAYO.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractPortafolioProductosMAYO.PORTAFOLIOPRODUCTOS_MAYO,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertPrecios.uriMatcher.match(uri)==ContractInsertPrecios.ALLROWS) {
            affected = db.delete(ContractInsertPrecios.INSERT_PRECIOS,
                    selection,
                    selectionArgs);
        }else if (ContractInsertPrecios.uriMatcher.match(uri)==ContractInsertPrecios.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertPrecios.INSERT_PRECIOS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

            else if (ContractInsertMuestras.uriMatcher.match(uri)==ContractInsertMuestras.ALLROWS) {
                affected = db.delete(ContractInsertMuestras.INSERT_MUESTRAS,
                        selection,
                        selectionArgs);
            }else if (ContractInsertMuestras.uriMatcher.match(uri)==ContractInsertMuestras.SINGLE_ROW) {
                long idStock = ContentUris.parseId(uri);
                affected = db.delete(ContractInsertMuestras.INSERT_MUESTRAS,
                        Constantes.ID_REMOTA + "=" + idStock
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                resolver.
                        notifyChange(uri, null, false);
            }
//probadores mpin
            else if (ContractInsertProbadores.uriMatcher.match(uri)==ContractInsertProbadores.ALLROWS) {
                affected = db.delete(ContractInsertProbadores.INSERT_PROBADORES,
                        selection,
                        selectionArgs);
            }else if (ContractInsertProbadores.uriMatcher.match(uri)==ContractInsertProbadores.SINGLE_ROW) {
                long idStock = ContentUris.parseId(uri);
                affected = db.delete(ContractInsertProbadores.INSERT_PROBADORES,
                        Constantes.ID_REMOTA + "=" + idStock
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                resolver.
                        notifyChange(uri, null, false);
            }

        else if (ContractInsertConvenios.uriMatcher.match(uri)==ContractInsertConvenios.ALLROWS) {
            affected = db.delete(ContractInsertConvenios.INSERT_CONVENIOS,
                    selection,
                    selectionArgs);
        }else if (ContractInsertConvenios.uriMatcher.match(uri)==ContractInsertConvenios.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertConvenios.INSERT_CONVENIOS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }


        else if (ContractInsertTracking.uriMatcher.match(uri)==ContractInsertTracking.ALLROWS) {
            affected = db.delete(ContractInsertTracking.INSERT_TRACKING,
                    selection,
                    selectionArgs);
        }else if (ContractInsertTracking.uriMatcher.match(uri)==ContractInsertTracking.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertTracking.INSERT_TRACKING,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertPacks.uriMatcher.match(uri)==ContractInsertPacks.ALLROWS) {
            affected = db.delete(ContractInsertPacks.INSERT_PACKS,
                    selection,
                    selectionArgs);
        }else if (ContractInsertPacks.uriMatcher.match(uri)==ContractInsertPacks.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertPacks.INSERT_PACKS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertProdCaducar.uriMatcher.match(uri)==ContractInsertProdCaducar.ALLROWS) {
            affected = db.delete(ContractInsertProdCaducar.INSERT_PROD_CADUCAR,
                    selection,
                    selectionArgs);
        }else if (ContractInsertProdCaducar.uriMatcher.match(uri)==ContractInsertProdCaducar.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertProdCaducar.INSERT_PROD_CADUCAR,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertSugeridos.uriMatcher.match(uri)==ContractInsertSugeridos.ALLROWS) {
            affected = db.delete(ContractInsertSugeridos.INSERT_SUGERIDOS,
                    selection,
                    selectionArgs);
        }else if (ContractInsertSugeridos.uriMatcher.match(uri)==ContractInsertSugeridos.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertSugeridos.INSERT_SUGERIDOS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertCanjes.uriMatcher.match(uri)==ContractInsertCanjes.ALLROWS) {
            affected = db.delete(ContractInsertCanjes.INSERT_CANJES,
                    selection,
                    selectionArgs);
        }else if (ContractInsertCanjes.uriMatcher.match(uri)==ContractInsertCanjes.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertCanjes.INSERT_CANJES,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertMaterialesRecibidos.uriMatcher.match(uri)==ContractInsertMaterialesRecibidos.ALLROWS) {
            affected = db.delete(ContractInsertMaterialesRecibidos.INSERT_MATERIALES_RECIBIDOS,
                    selection,
                    selectionArgs);
        }else if (ContractInsertMaterialesRecibidos.uriMatcher.match(uri)==ContractInsertMaterialesRecibidos.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertMaterialesRecibidos.INSERT_MATERIALES_RECIBIDOS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertEjecucionMateriales.uriMatcher.match(uri)==ContractInsertEjecucionMateriales.ALLROWS) {
            affected = db.delete(ContractInsertEjecucionMateriales.INSERT_EJECUCION_MATERIALES,
                    selection,
                    selectionArgs);
        }else if (ContractInsertEjecucionMateriales.uriMatcher.match(uri)==ContractInsertEjecucionMateriales.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertEjecucionMateriales.INSERT_EJECUCION_MATERIALES,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertMCIPdv.uriMatcher.match(uri)==ContractInsertMCIPdv.ALLROWS) {
            affected = db.delete(ContractInsertMCIPdv.INSERT_MCI,
                    selection,
                    selectionArgs);
        }else if (ContractInsertMCIPdv.uriMatcher.match(uri)==ContractInsertMCIPdv.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertMCIPdv.INSERT_MCI,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertCodificados.uriMatcher.match(uri)== ContractInsertCodificados.ALLROWS) {
            affected = db.delete(ContractInsertCodificados.INSERT_CODIFICADOS,
                    selection,
                    selectionArgs);
        }else if (ContractInsertCodificados.uriMatcher.match(uri)== ContractInsertCodificados.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertCodificados.INSERT_CODIFICADOS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertTareas.uriMatcher.match(uri)==ContractInsertTareas.ALLROWS) {
            affected = db.delete(ContractInsertTareas.INSERT_TAREAS,
                    selection,
                    selectionArgs);
        }else if (ContractInsertTareas.uriMatcher.match(uri)==ContractInsertTareas.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertTareas.INSERT_TAREAS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertRotacion.uriMatcher.match(uri)==ContractInsertRotacion.ALLROWS) {
            affected = db.delete(ContractInsertRotacion.INSERT_ROTACION,
                    selection,
                    selectionArgs);
        }else if (ContractInsertRotacion.uriMatcher.match(uri)==ContractInsertRotacion.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertRotacion.INSERT_ROTACION,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertPreguntas.uriMatcher.match(uri)==ContractInsertPreguntas.ALLROWS) {
            affected = db.delete(ContractInsertPreguntas.INSERT_PREGUNTAS,
                    selection,
                    selectionArgs);
        }else if (ContractInsertPreguntas.uriMatcher.match(uri)==ContractInsertPreguntas.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertPreguntas.INSERT_PREGUNTAS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertResultadoPreguntas.uriMatcher.match(uri)==ContractInsertResultadoPreguntas.ALLROWS) {
            affected = db.delete(ContractInsertResultadoPreguntas.INSERT_RESULTADO_PREGUNTAS,
                    selection,
                    selectionArgs);
        }else if (ContractInsertResultadoPreguntas.uriMatcher.match(uri)==ContractInsertResultadoPreguntas.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertResultadoPreguntas.INSERT_RESULTADO_PREGUNTAS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertPreciosSesion.uriMatcher.match(uri)==ContractInsertPreciosSesion.ALLROWS) {
            affected = db.delete(ContractInsertPreciosSesion.INSERT_PRECIOS_SESION,
                    selection,
                    selectionArgs);
        }else if (ContractInsertPreciosSesion.uriMatcher.match(uri)==ContractInsertPreciosSesion.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertPreciosSesion.INSERT_PRECIOS_SESION,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (InsertFlooring.uriMatcher.match(uri)==InsertFlooring.ALLROWS) {
            affected = db.delete(InsertFlooring.INSERT_FLOORING,
                    selection,
                    selectionArgs);
        }else if (InsertFlooring.uriMatcher.match(uri)==InsertFlooring.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(InsertFlooring.INSERT_FLOORING,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertImpulso.uriMatcher.match(uri)==ContractInsertImpulso.ALLROWS) {
            affected = db.delete(ContractInsertImpulso.INSERT_IMPUSLO,
                    selection,
                    selectionArgs);
        }else if (ContractInsertImpulso.uriMatcher.match(uri)==ContractInsertImpulso.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertImpulso.INSERT_IMPUSLO,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertExh.uriMatcher.match(uri)==ContractInsertExh.ALLROWS) {
            affected = db.delete(ContractInsertExh.INSERT_EXH,
                    selection,
                    selectionArgs);
        }else if (ContractInsertExh.uriMatcher.match(uri)==ContractInsertExh.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertExh.INSERT_EXH,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertExhSesion.uriMatcher.match(uri)==ContractInsertExhSesion.ALLROWS) {
            affected = db.delete(ContractInsertExhSesion.INSERT_EXH_SESION,
                    selection,
                    selectionArgs);
        }else if (ContractInsertExhSesion.uriMatcher.match(uri)==ContractInsertExhSesion.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertExhSesion.INSERT_EXH_SESION,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertFotografico.uriMatcher.match(uri)==ContractInsertFotografico.ALLROWS) {
            affected = db.delete(ContractInsertFotografico.INSERT_FOTOGRAFICO,
                    selection,
                    selectionArgs);
        }else if (ContractInsertFotografico.uriMatcher.match(uri)==ContractInsertFotografico.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertFotografico.INSERT_FOTOGRAFICO,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertEvidencias.uriMatcher.match(uri)==ContractInsertEvidencias.ALLROWS) {
            affected = db.delete(ContractInsertEvidencias.INSERT_EVIDENCIAS,
                    selection,
                    selectionArgs);
        }else if (ContractInsertEvidencias.uriMatcher.match(uri)==ContractInsertEvidencias.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertEvidencias.INSERT_EVIDENCIAS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertGps.uriMatcher.match(uri)==ContractInsertGps.ALLROWS) {
            affected = db.delete(ContractInsertGps.INSERT_GPS,
                    selection,
                    selectionArgs);
        }else if (ContractInsertGps.uriMatcher.match(uri)==ContractInsertGps.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertGps.INSERT_GPS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertRastreo.uriMatcher.match(uri)==ContractInsertRastreo.ALLROWS) {
            affected = db.delete(ContractInsertRastreo.INSERT_GEO,
                    selection,
                    selectionArgs);
        }else if (ContractInsertRastreo.uriMatcher.match(uri)==ContractInsertRastreo.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertRastreo.INSERT_GEO,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractNotificacion.uriMatcher.match(uri)==ContractNotificacion.ALLROWS) {
            affected = db.delete(ContractNotificacion.NOTIFICACION,
                    selection,
                    selectionArgs);
        }else if (ContractNotificacion.uriMatcher.match(uri)==ContractNotificacion.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractNotificacion.NOTIFICACION,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertInicial.uriMatcher.match(uri)==ContractInsertInicial.ALLROWS) {
            affected = db.delete(ContractInsertInicial.INSERT_INICIAL,
                    selection,
                    selectionArgs);
        }else if (ContractInsertInicial.uriMatcher.match(uri)==ContractInsertInicial.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertInicial.INSERT_INICIAL,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertPromocion.uriMatcher.match(uri)==ContractInsertPromocion.ALLROWS) {
            affected = db.delete(ContractInsertPromocion.INSERT_PROMO,
                    selection,
                    selectionArgs);
        }else if (ContractInsertPromocion.uriMatcher.match(uri)==ContractInsertPromocion.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertPromocion.INSERT_PROMO,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }


        else if (ContractInsertImplementacion.uriMatcher.match(uri)==ContractInsertImplementacion.ALLROWS) {
            affected = db.delete(ContractInsertImplementacion.INSERT_IMPLEM,
                    selection,
                    selectionArgs);
        }else if (ContractInsertImplementacion.uriMatcher.match(uri)==ContractInsertImplementacion.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertImplementacion.INSERT_IMPLEM,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertValores.uriMatcher.match(uri)==ContractInsertValores.ALLROWS) {
            affected = db.delete(ContractInsertValores.INSERT_VALORES,
                    selection,
                    selectionArgs);
        }else if (ContractInsertValores.uriMatcher.match(uri)==ContractInsertValores.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertValores.INSERT_VALORES,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertValoresSesion.uriMatcher.match(uri)==ContractInsertValoresSesion.ALLROWS) {
            affected = db.delete(ContractInsertValoresSesion.INSERT_VALORES_SESION,
                    selection,
                    selectionArgs);
        }else if (ContractInsertValoresSesion.uriMatcher.match(uri)==ContractInsertValoresSesion.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertValoresSesion.INSERT_VALORES_SESION,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertPdv.uriMatcher.match(uri)==ContractInsertPdv.ALLROWS) {
            affected = db.delete(ContractInsertPdv.INSERT_PDV,
                    selection,
                    selectionArgs);
        }else if (ContractInsertPdv.uriMatcher.match(uri)==ContractInsertPdv.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertPdv.INSERT_PDV,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertShare.uriMatcher.match(uri)==ContractInsertShare.ALLROWS) {
            affected = db.delete(ContractInsertShare.INSERT_SHARE,
                    selection,
                    selectionArgs);
        }else if (ContractInsertShare.uriMatcher.match(uri)==ContractInsertShare.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertShare.INSERT_SHARE,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertPDI.uriMatcher.match(uri)==ContractInsertPDI.ALLROWS) {
            affected = db.delete(ContractInsertPDI.INSERT_PDI,
                    selection,
                    selectionArgs);
        }else if (ContractInsertPDI.uriMatcher.match(uri)==ContractInsertPDI.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertPDI.INSERT_PDI,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertAgotados.uriMatcher.match(uri)==ContractInsertAgotados.ALLROWS) {
            affected = db.delete(ContractInsertAgotados.INSERT_AGOTADOS,
                    selection,
                    selectionArgs);
        }else if (ContractInsertAgotados.uriMatcher.match(uri)==ContractInsertAgotados.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertAgotados.INSERT_AGOTADOS,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

        else if (ContractInsertVenta.uriMatcher.match(uri)==ContractInsertVenta.ALLROWS) {
            affected = db.delete(ContractInsertVenta.INSERT_VENTA,
                    selection,
                    selectionArgs);
        }else if (ContractInsertVenta.uriMatcher.match(uri)==ContractInsertVenta.SINGLE_ROW) {
            long idStock = ContentUris.parseId(uri);
            affected = db.delete(ContractInsertVenta.INSERT_VENTA,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
            // Notificar cambio asociado a la uri
            resolver.
                    notifyChange(uri, null, false);
        }

            else if (ContractInsertVentas2.uriMatcher.match(uri)==ContractInsertVentas2.ALLROWS) {
                affected = db.delete(ContractInsertVentas2.INSERT_PRECIOS,
                        selection,
                        selectionArgs);
            }else if (ContractInsertVentas2.uriMatcher.match(uri)==ContractInsertVentas2.SINGLE_ROW) {
                long idStock = ContentUris.parseId(uri);
                affected = db.delete(ContractInsertVentas2.INSERT_PRECIOS,
                        Constantes.ID_REMOTA + "=" + idStock
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                resolver.
                        notifyChange(uri, null, false);
            }

        else{
            throw new IllegalArgumentException("Elemento local desconocido: " + uri);
        }

        return affected;
    }

    //Aqui es importante el ELSE
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected;

        if (ContractPharmaValue.uriMatcher.match(uri)== ContractPharmaValue.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractPharmaValue.POS, values,
                    selection, selectionArgs);
        }else if (ContractPharmaValue.uriMatcher.match(uri)== ContractPharmaValue.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractPharmaValue.POS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }
        else
        if(ContractUser.uriMatcher.match(uri)==ContractUser.ALLROWS){
            System.out.println("UPDATE");
            affected = db.update(ContractUser.TABLE_USER, values,
                    selection, selectionArgs);
        }else if(ContractUser.uriMatcher.match(uri)==ContractUser.SINGLE_ROW){
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractUser.TABLE_USER, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        //REGISTRO mpin
        else
        if(ContractTipoRegistro.uriMatcher.match(uri)== ContractTipoRegistro.ALLROWS){
            System.out.println("UPDATE");
            affected = db.update(ContractTipoRegistro.TIPO_REGISTRO, values,
                    selection, selectionArgs);
        }else if(ContractTipoRegistro.uriMatcher.match(uri)== ContractTipoRegistro.SINGLE_ROW){
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractTipoRegistro.TIPO_REGISTRO, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        //ACTIVIDADES mpin
        else
        if(ContractTipoActividades.uriMatcher.match(uri)== ContractTipoActividades.ALLROWS){
            System.out.println("UPDATE");
            affected = db.update(ContractTipoActividades.TIPO_ACTIVIDADES, values,
                    selection, selectionArgs);
        }else if(ContractTipoActividades.uriMatcher.match(uri)== ContractTipoActividades.SINGLE_ROW){
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractTipoActividades.TIPO_ACTIVIDADES, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }


        else
        if(ContractTipoVentas.uriMatcher.match(uri)== ContractTipoVentas.ALLROWS){
            System.out.println("UPDATE");
            affected = db.update(ContractTipoVentas.TIPO_VENTAS, values,
                    selection, selectionArgs);
        }else if(ContractTipoVentas.uriMatcher.match(uri)== ContractTipoVentas.SINGLE_ROW){
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractTipoVentas.TIPO_VENTAS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        //MPIN imple

        else
        if(ContractTipoImplementaciones.uriMatcher.match(uri)== ContractTipoImplementaciones.ALLROWS){
            System.out.println("UPDATE");
            affected = db.update(ContractTipoImplementaciones.TIPO_IMPLEMENTACIONES, values,
                    selection, selectionArgs);
        }else if(ContractTipoImplementaciones.uriMatcher.match(uri)== ContractTipoImplementaciones.SINGLE_ROW){
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractTipoImplementaciones.TIPO_IMPLEMENTACIONES, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        //MPIN unidades

        else
        if(ContractTipoUnidades.uriMatcher.match(uri)== ContractTipoUnidades.ALLROWS){
            System.out.println("UPDATE");
            affected = db.update(ContractTipoUnidades.TIPO_UNIDADES, values,
                    selection, selectionArgs);
        }else if(ContractTipoUnidades.uriMatcher.match(uri)== ContractTipoUnidades.SINGLE_ROW){
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractTipoUnidades.TIPO_UNIDADES, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        //MPIN MODULOS POR ROLES

        else
        if(ContractModulosRoles.uriMatcher.match(uri)== ContractModulosRoles.ALLROWS){
            System.out.println("UPDATE");
            affected = db.update(ContractModulosRoles.MODULO_ROLES, values,
                    selection, selectionArgs);
        }else if(ContractModulosRoles.uriMatcher.match(uri)== ContractModulosRoles.SINGLE_ROW){
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractModulosRoles.MODULO_ROLES, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractProductosSecundarios.uriMatcher.match(uri)==ContractProductosSecundarios.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractProductosSecundarios.PRODUCTOS_SECUNDARIOS, values,
                    selection, selectionArgs);
        }else if (ContractProductosSecundarios.uriMatcher.match(uri)==ContractProductosSecundarios.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractProductosSecundarios.PRODUCTOS_SECUNDARIOS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractTracking.uriMatcher.match(uri)==ContractTracking.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractTracking.TRACKING, values,
                    selection, selectionArgs);
        }else if (ContractTracking.uriMatcher.match(uri)==ContractTracking.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractTracking.TRACKING, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractLinkOnedrive.uriMatcher.match(uri)==ContractLinkOnedrive.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractLinkOnedrive.LINK_ONDRIVE, values,
                    selection, selectionArgs);
        }else if (ContractLinkOnedrive.uriMatcher.match(uri)==ContractLinkOnedrive.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractLinkOnedrive.LINK_ONDRIVE, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
              if(InsertExhBassa.uriMatcher.match(uri)==InsertExhBassa.ALLROWS){
            System.out.println("UPDATE");
            affected = db.update(InsertExhBassa.INSERT_EXHIBICION_COLGATE, values,
                    selection, selectionArgs);
        }else if(InsertExhBassa.uriMatcher.match(uri)==InsertExhBassa.SINGLE_ROW){
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(InsertExhBassa.INSERT_EXHIBICION_COLGATE, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        } else
              if(InsertExhibicionesAdNu.uriMatcher.match(uri)==InsertExhibicionesAdNu.ALLROWS){
            System.out.println("UPDATE");
            affected = db.update(InsertExhibicionesAdNu.INSERT_EXHIBICION_AD_NU, values,
                    selection, selectionArgs);
        }else if(InsertExhibicionesAdNu.uriMatcher.match(uri)==InsertExhibicionesAdNu.SINGLE_ROW){
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(InsertExhibicionesAdNu.INSERT_EXHIBICION_AD_NU, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }else if(ContractExhibicionSupervisor.uriMatcher.match(uri)== ContractExhibicionSupervisor.ALLROWS){
                  System.out.println("UPDATE ContractExhibicionSupervisor: Actualiza el contenido Local");
                  affected = db.update(ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR, values,
                          selection, selectionArgs);
              }else if(ContractExhibicionSupervisor.uriMatcher.match(uri)== ContractExhibicionSupervisor.SINGLE_ROW){
                  String idStock = uri.getPathSegments().get(1);
                  affected = db.update(ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR, values,
                          ContractExhibicionSupervisor.Columnas.ID_REMOTA + "=" + idStock
                                  + (!TextUtils.isEmpty(selection) ?
                                  " AND (" + selection + ')' : ""),
                          selectionArgs);
              }else


        if(ContractEvaluacionEncuesta.uriMatcher.match(uri)==ContractEvaluacionEncuesta.ALLROWS){
            System.out.println("UPDATE");
            affected = db.update(ContractEvaluacionEncuesta.EVALUACION_PROMOTOR, values,
                    selection, selectionArgs);
        }else if(ContractEvaluacionEncuesta.uriMatcher.match(uri)==ContractEvaluacionEncuesta.SINGLE_ROW){
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractEvaluacionEncuesta.EVALUACION_PROMOTOR, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }else
        if(ContractInsertEvaluacionEncuesta.uriMatcher.match(uri)==ContractInsertEvaluacionEncuesta.ALLROWS){
            System.out.println("UPDATE");
            affected = db.update(ContractInsertEvaluacionEncuesta.INSERT_EVALUACION, values,
                    selection, selectionArgs);
        }else if(ContractInsertEvaluacionEncuesta.uriMatcher.match(uri)==ContractInsertEvaluacionEncuesta.SINGLE_ROW){
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertEvaluacionEncuesta.INSERT_EVALUACION, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }else
        if (ContractPrecios.uriMatcher.match(uri)==ContractPrecios.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractPrecios.PRECIOS, values,
                    selection, selectionArgs);
        }else if (ContractPrecios.uriMatcher.match(uri)==ContractPrecios.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractPrecios.PRECIOS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }else
        if(ContractTipoNovedades.uriMatcher.match(uri)== ContractTipoNovedades.ALLROWS){
            System.out.println("UPDATE");
            affected = db.update(ContractTipoNovedades.TIPO_NOVEDADES, values,
                    selection, selectionArgs);
        }else if(ContractTipoNovedades.uriMatcher.match(uri)== ContractTipoNovedades.SINGLE_ROW){
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractTipoNovedades.TIPO_NOVEDADES, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }
        else
        if(ContractCanalesFotos.uriMatcher.match(uri)== ContractCanalesFotos.ALLROWS){
            System.out.println("UPDATE");
            affected = db.update(ContractCanalesFotos.CANALES_FOTOS, values,
                    selection, selectionArgs);
        }else if(ContractCanalesFotos.uriMatcher.match(uri)== ContractCanalesFotos.SINGLE_ROW){
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractCanalesFotos.CANALES_FOTOS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }
        else
        if(ContractInsertNovedades.uriMatcher.match(uri)==ContractInsertNovedades.ALLROWS){
            System.out.println("UPDATE");
            affected = db.update(ContractInsertNovedades.INSERT_NOVEDADES, values,
                    selection, selectionArgs);
        }else if(ContractInsertNovedades.uriMatcher.match(uri)==ContractInsertNovedades.SINGLE_ROW){
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertNovedades.INSERT_NOVEDADES, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractConvenios.uriMatcher.match(uri)==ContractConvenios.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractConvenios.CONVENIOS, values,
                    selection, selectionArgs);
        }else if (ContractConvenios.uriMatcher.match(uri)==ContractConvenios.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractConvenios.CONVENIOS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractPreguntas.uriMatcher.match(uri)==ContractPreguntas.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractPreguntas.TABLE_QUEST, values,
                    selection, selectionArgs);
        }else if (ContractPreguntas.uriMatcher.match(uri)==ContractPreguntas.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractPreguntas.TABLE_QUEST, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }


        else
        if (ContractTests.uriMatcher.match(uri)==ContractTests.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractTests.TABLE_TEST, values,
                    selection, selectionArgs);
        }else if (ContractTests.uriMatcher.match(uri)==ContractTests.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractTests.TABLE_TEST, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractJustificacion.uriMatcher.match(uri)==ContractJustificacion.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractJustificacion.JUSTIFICACIONES, values,
                    selection, selectionArgs);
        }else if (ContractJustificacion.uriMatcher.match(uri)==ContractJustificacion.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractJustificacion.JUSTIFICACIONES, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractTipoInventario.uriMatcher.match(uri)==ContractTipoInventario.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractTipoInventario.TIPOINVENTARIO, values,
                    selection, selectionArgs);
        }else if (ContractTipoInventario.uriMatcher.match(uri)==ContractTipoInventario.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractTipoInventario.TIPOINVENTARIO, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }


        else
        if (ContractTipoExh.uriMatcher.match(uri)==ContractTipoExh.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractTipoExh.TABLE_NAME, values,
                    selection, selectionArgs);
        }else if (ContractTipoExh.uriMatcher.match(uri)==ContractTipoExh.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractTipoExh.TABLE_NAME, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractCategoriaTipo.uriMatcher.match(uri)==ContractCategoriaTipo.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractCategoriaTipo.CATEGORIA_TIPO, values,
                    selection, selectionArgs);
        }else if (ContractCategoriaTipo.uriMatcher.match(uri)==ContractCategoriaTipo.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractCategoriaTipo.CATEGORIA_TIPO, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }




        else
        if (ContractPromociones.uriMatcher.match(uri)== ContractPromociones.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractPromociones.PROMOCIONES, values,
                    selection, selectionArgs);
        }else if (ContractPromociones.uriMatcher.match(uri)== ContractPromociones.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractPromociones.PROMOCIONES, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractMotivoSugerido.uriMatcher.match(uri)== ContractMotivoSugerido.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractMotivoSugerido.TABLE_NAME, values,
                    selection, selectionArgs);
        }else if (ContractMotivoSugerido.uriMatcher.match(uri)== ContractMotivoSugerido.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractMotivoSugerido.TABLE_NAME, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractRotacion.uriMatcher.match(uri)== ContractRotacion.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractRotacion.ROTACION, values,
                    selection, selectionArgs);
        }else if (ContractRotacion.uriMatcher.match(uri)== ContractRotacion.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractRotacion.ROTACION, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractPopSugerido.uriMatcher.match(uri)== ContractPopSugerido.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractPopSugerido.POPSUGERIDO, values,
                    selection, selectionArgs);
        }else if (ContractPopSugerido.uriMatcher.match(uri)== ContractPopSugerido.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractPopSugerido.POPSUGERIDO, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractComboCanjes.uriMatcher.match(uri)== ContractComboCanjes.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractComboCanjes.COMBO_CANJES, values,
                    selection, selectionArgs);
        }else if (ContractComboCanjes.uriMatcher.match(uri)== ContractComboCanjes.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractComboCanjes.COMBO_CANJES, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractCausalesMCI.uriMatcher.match(uri)== ContractCausalesMCI.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractCausalesMCI.CAUSALES_MCI, values,
                    selection, selectionArgs);
        }else if (ContractCausalesMCI.uriMatcher.match(uri)== ContractCausalesMCI.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractCausalesMCI.CAUSALES_MCI, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractCausalesOSA.uriMatcher.match(uri)== ContractCausalesOSA.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractCausalesOSA.CAUSALES_OSA, values,
                    selection, selectionArgs);
        }else if (ContractCausalesOSA.uriMatcher.match(uri)== ContractCausalesOSA.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractCausalesOSA.CAUSALES_OSA, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractAlertas.uriMatcher.match(uri)== ContractAlertas.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractAlertas.ALERTAS, values,
                    selection, selectionArgs);
        }else if (ContractAlertas.uriMatcher.match(uri)== ContractAlertas.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractAlertas.ALERTAS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractPDI.uriMatcher.match(uri)== ContractPDI.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractPDI.PDI, values,
                    selection, selectionArgs);
        }else if (ContractPDI.uriMatcher.match(uri)== ContractPDI.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractPDI.PDI, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractLog.uriMatcher.match(uri)== ContractLog.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractLog.LOG, values,
                    selection, selectionArgs);
        }else if (ContractLog.uriMatcher.match(uri)== ContractLog.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractLog.LOG, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractPrioritario.uriMatcher.match(uri)== ContractPrioritario.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractPrioritario.PRIORITARIO, values,
                    selection, selectionArgs);
        }else if (ContractPrioritario.uriMatcher.match(uri)== ContractPrioritario.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractPrioritario.PRIORITARIO, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if(ContractCamposPorModulos.uriMatcher.match(uri)== ContractCamposPorModulos.ALLROWS){
            System.out.println("UPDATE");
            affected = db.update(ContractCamposPorModulos.CAMPOS_X_MODULOS, values,
                    selection, selectionArgs);
        }else if(ContractCamposPorModulos.uriMatcher.match(uri)== ContractCamposPorModulos.SINGLE_ROW){
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractCamposPorModulos.CAMPOS_X_MODULOS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if(ContractPromocionalVentas.uriMatcher.match(uri)== ContractPromocionalVentas.ALLROWS){
            System.out.println("UPDATE");
            affected = db.update(ContractPromocionalVentas.PROMOCIONAL_VENTAS, values,
                    selection, selectionArgs);
        }else if(ContractPromocionalVentas.uriMatcher.match(uri)== ContractPromocionalVentas.SINGLE_ROW){
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractPromocionalVentas.PROMOCIONAL_VENTAS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractTareas.uriMatcher.match(uri)== ContractTareas.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractTareas.TAREA, values,
                    selection, selectionArgs);
        }else if (ContractTareas.uriMatcher.match(uri)== ContractTareas.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractTareas.TAREA, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }


        else
        if (ContractPortafolioProductos.uriMatcher.match(uri)== ContractPortafolioProductos.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractPortafolioProductos.PORTAFOLIOPRODUCTOS, values,
                    selection, selectionArgs);
        }else if (ContractPortafolioProductos.uriMatcher.match(uri)== ContractPortafolioProductos.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractPortafolioProductos.PORTAFOLIOPRODUCTOS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractPortafolioProductosAASS.uriMatcher.match(uri)== ContractPortafolioProductosAASS.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractPortafolioProductosAASS.PORTAFOLIOPRODUCTOS_AASS, values,
                    selection, selectionArgs);
        }else if (ContractPortafolioProductosAASS.uriMatcher.match(uri)== ContractPortafolioProductosAASS.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractPortafolioProductosAASS.PORTAFOLIOPRODUCTOS_AASS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractPortafolioProductosMAYO.uriMatcher.match(uri)== ContractPortafolioProductosMAYO.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractPortafolioProductosMAYO.PORTAFOLIOPRODUCTOS_MAYO, values,
                    selection, selectionArgs);
        }else if (ContractPortafolioProductosMAYO.uriMatcher.match(uri)== ContractPortafolioProductosMAYO.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractPortafolioProductosMAYO.PORTAFOLIOPRODUCTOS_MAYO, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertPrecios.uriMatcher.match(uri)==ContractInsertPrecios.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertPrecios.INSERT_PRECIOS, values,
                    selection, selectionArgs);
        }else if (ContractInsertPrecios.uriMatcher.match(uri)==ContractInsertPrecios.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertPrecios.INSERT_PRECIOS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertMuestras.uriMatcher.match(uri)==ContractInsertMuestras.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertMuestras.INSERT_MUESTRAS, values,
                    selection, selectionArgs);
        }else if (ContractInsertMuestras.uriMatcher.match(uri)==ContractInsertMuestras.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertMuestras.INSERT_MUESTRAS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertProbadores.uriMatcher.match(uri)==ContractInsertProbadores.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertProbadores.INSERT_PROBADORES, values,
                    selection, selectionArgs);
        }else if (ContractInsertProbadores.uriMatcher.match(uri)==ContractInsertProbadores.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertProbadores.INSERT_PROBADORES, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertConvenios.uriMatcher.match(uri)==ContractInsertConvenios.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertConvenios.INSERT_CONVENIOS, values,
                    selection, selectionArgs);
        }else if (ContractInsertConvenios.uriMatcher.match(uri)==ContractInsertConvenios.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertConvenios.INSERT_CONVENIOS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertTracking.uriMatcher.match(uri)==ContractInsertTracking.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertTracking.INSERT_TRACKING, values,
                    selection, selectionArgs);
        }else if (ContractInsertTracking.uriMatcher.match(uri)==ContractInsertTracking.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertTracking.INSERT_TRACKING, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertPacks.uriMatcher.match(uri)==ContractInsertPacks.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertPacks.INSERT_PACKS, values,
                    selection, selectionArgs);
        }else if (ContractInsertPacks.uriMatcher.match(uri)==ContractInsertPacks.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertPacks.INSERT_PACKS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertProdCaducar.uriMatcher.match(uri)==ContractInsertProdCaducar.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertProdCaducar.INSERT_PROD_CADUCAR, values,
                    selection, selectionArgs);
        }else if (ContractInsertProdCaducar.uriMatcher.match(uri)==ContractInsertProdCaducar.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertProdCaducar.INSERT_PROD_CADUCAR, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertSugeridos.uriMatcher.match(uri)==ContractInsertSugeridos.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertSugeridos.INSERT_SUGERIDOS, values,
                    selection, selectionArgs);
        }else if (ContractInsertSugeridos.uriMatcher.match(uri)==ContractInsertSugeridos.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertSugeridos.INSERT_SUGERIDOS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertCanjes.uriMatcher.match(uri)==ContractInsertCanjes.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertCanjes.INSERT_CANJES, values,
                    selection, selectionArgs);
        }else if (ContractInsertCanjes.uriMatcher.match(uri)==ContractInsertCanjes.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertCanjes.INSERT_CANJES, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertMaterialesRecibidos.uriMatcher.match(uri)==ContractInsertMaterialesRecibidos.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertMaterialesRecibidos.INSERT_MATERIALES_RECIBIDOS, values,
                    selection, selectionArgs);
        }else if (ContractInsertMaterialesRecibidos.uriMatcher.match(uri)==ContractInsertMaterialesRecibidos.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertMaterialesRecibidos.INSERT_MATERIALES_RECIBIDOS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertEjecucionMateriales.uriMatcher.match(uri)==ContractInsertEjecucionMateriales.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertEjecucionMateriales.INSERT_EJECUCION_MATERIALES, values,
                    selection, selectionArgs);
        }else if (ContractInsertEjecucionMateriales.uriMatcher.match(uri)==ContractInsertEjecucionMateriales.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertEjecucionMateriales.INSERT_EJECUCION_MATERIALES, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertMCIPdv.uriMatcher.match(uri)==ContractInsertMCIPdv.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertMCIPdv.INSERT_MCI, values,
                    selection, selectionArgs);
        }else if (ContractInsertMCIPdv.uriMatcher.match(uri)==ContractInsertMCIPdv.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertMCIPdv.INSERT_MCI, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertCodificados.uriMatcher.match(uri)== ContractInsertCodificados.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertCodificados.INSERT_CODIFICADOS, values,
                    selection, selectionArgs);
        }else if (ContractInsertCodificados.uriMatcher.match(uri)== ContractInsertCodificados.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertCodificados.INSERT_CODIFICADOS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertTareas.uriMatcher.match(uri)==ContractInsertTareas.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertTareas.INSERT_TAREAS, values,
                    selection, selectionArgs);
        }else if (ContractInsertTareas.uriMatcher.match(uri)==ContractInsertTareas.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertTareas.INSERT_TAREAS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertRotacion.uriMatcher.match(uri)==ContractInsertRotacion.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertRotacion.INSERT_ROTACION, values,
                    selection, selectionArgs);
        }else if (ContractInsertRotacion.uriMatcher.match(uri)==ContractInsertRotacion.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertRotacion.INSERT_ROTACION, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertPreguntas.uriMatcher.match(uri)==ContractInsertPreguntas.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertPreguntas.INSERT_PREGUNTAS, values,
                    selection, selectionArgs);
        }else if (ContractInsertPreguntas.uriMatcher.match(uri)==ContractInsertPreguntas.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertPreguntas.INSERT_PREGUNTAS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }


        else
        if (ContractInsertResultadoPreguntas.uriMatcher.match(uri)==ContractInsertResultadoPreguntas.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertResultadoPreguntas.INSERT_RESULTADO_PREGUNTAS, values,
                    selection, selectionArgs);
        }else if (ContractInsertResultadoPreguntas.uriMatcher.match(uri)==ContractInsertResultadoPreguntas.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertResultadoPreguntas.INSERT_RESULTADO_PREGUNTAS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }



        else
        if (ContractInsertPreciosSesion.uriMatcher.match(uri)==ContractInsertPreciosSesion.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertPreciosSesion.INSERT_PRECIOS_SESION, values,
                    selection, selectionArgs);
        }else if (ContractInsertPreciosSesion.uriMatcher.match(uri)==ContractInsertPreciosSesion.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertPreciosSesion.INSERT_PRECIOS_SESION, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (InsertFlooring.uriMatcher.match(uri)==InsertFlooring.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(InsertFlooring.INSERT_FLOORING, values,
                    selection, selectionArgs);
        }else if (InsertFlooring.uriMatcher.match(uri)==InsertFlooring.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(InsertFlooring.INSERT_FLOORING, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertImpulso.uriMatcher.match(uri)==ContractInsertImpulso.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertImpulso.INSERT_IMPUSLO, values,
                    selection, selectionArgs);
        }else if (ContractInsertImpulso.uriMatcher.match(uri)==ContractInsertImpulso.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertImpulso.INSERT_IMPUSLO, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertExh.uriMatcher.match(uri)==ContractInsertExh.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertExh.INSERT_EXH, values,
                    selection, selectionArgs);
        }else if (ContractInsertExh.uriMatcher.match(uri)==ContractInsertExh.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertExh.INSERT_EXH, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertExhSesion.uriMatcher.match(uri)==ContractInsertExhSesion.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertExhSesion.INSERT_EXH_SESION, values,
                    selection, selectionArgs);
        }else if (ContractInsertExhSesion.uriMatcher.match(uri)==ContractInsertExhSesion.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertExhSesion.INSERT_EXH_SESION, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertFotografico.uriMatcher.match(uri)==ContractInsertFotografico.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertFotografico.INSERT_FOTOGRAFICO, values,
                    selection, selectionArgs);
        }else if (ContractInsertFotografico.uriMatcher.match(uri)==ContractInsertFotografico.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertFotografico.INSERT_FOTOGRAFICO, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertEvidencias.uriMatcher.match(uri)==ContractInsertEvidencias.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertEvidencias.INSERT_EVIDENCIAS, values,
                    selection, selectionArgs);
        }else if (ContractInsertEvidencias.uriMatcher.match(uri)==ContractInsertEvidencias.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertEvidencias.INSERT_EVIDENCIAS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }


        else
        if (ContractInsertGps.uriMatcher.match(uri)==ContractInsertGps.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertGps.INSERT_GPS, values,
                    selection, selectionArgs);
        }else if (ContractInsertGps.uriMatcher.match(uri)==ContractInsertGps.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertGps.INSERT_GPS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }


        else
        if (ContractInsertRastreo.uriMatcher.match(uri)==ContractInsertRastreo.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertRastreo.INSERT_GEO, values,
                    selection, selectionArgs);
        }else if (ContractInsertRastreo.uriMatcher.match(uri)==ContractInsertRastreo.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertRastreo.INSERT_GEO, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }


        else
        if (ContractNotificacion.uriMatcher.match(uri)==ContractNotificacion.ALLROWS) {
            System.out.println("UPDATE NOTIFICACION");
            affected = db.update(ContractNotificacion.NOTIFICACION, values,
                    selection, selectionArgs);
        }else if (ContractNotificacion.uriMatcher.match(uri)==ContractNotificacion.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractNotificacion.NOTIFICACION, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }


        else
        if (ContractInsertInicial.uriMatcher.match(uri)==ContractInsertInicial.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertInicial.INSERT_INICIAL, values,
                    selection, selectionArgs);
        }else if (ContractInsertInicial.uriMatcher.match(uri)==ContractInsertInicial.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertInicial.INSERT_INICIAL, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertPromocion.uriMatcher.match(uri)==ContractInsertPromocion.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertPromocion.INSERT_PROMO, values,
                    selection, selectionArgs);
        }else if (ContractInsertPromocion.uriMatcher.match(uri)==ContractInsertPromocion.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertPromocion.INSERT_PROMO, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertImplementacion.uriMatcher.match(uri)==ContractInsertImplementacion.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertImplementacion.INSERT_IMPLEM, values,
                    selection, selectionArgs);
        }else if (ContractInsertImplementacion.uriMatcher.match(uri)==ContractInsertImplementacion.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertImplementacion.INSERT_IMPLEM, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertValores.uriMatcher.match(uri)==ContractInsertValores.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertValores.INSERT_VALORES, values,
                    selection, selectionArgs);
        }else if (ContractInsertValores.uriMatcher.match(uri)==ContractInsertValores.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertValores.INSERT_VALORES, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertValoresSesion.uriMatcher.match(uri)==ContractInsertValoresSesion.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertValoresSesion.INSERT_VALORES_SESION, values,
                    selection, selectionArgs);
        }else if (ContractInsertValoresSesion.uriMatcher.match(uri)==ContractInsertValoresSesion.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertValoresSesion.INSERT_VALORES_SESION, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertPdv.uriMatcher.match(uri)==ContractInsertPdv.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertPdv.INSERT_PDV, values,
                    selection, selectionArgs);
        }else if (ContractInsertPdv.uriMatcher.match(uri)==ContractInsertPdv.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertPdv.INSERT_PDV, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertShare.uriMatcher.match(uri)==ContractInsertShare.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertShare.INSERT_SHARE, values,
                    selection, selectionArgs);
        }else if (ContractInsertShare.uriMatcher.match(uri)==ContractInsertShare.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertShare.INSERT_SHARE, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertPDI.uriMatcher.match(uri)==ContractInsertPDI.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertPDI.INSERT_PDI, values,
                    selection, selectionArgs);
        }else if (ContractInsertPDI.uriMatcher.match(uri)==ContractInsertPDI.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertPDI.INSERT_PDI, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertAgotados.uriMatcher.match(uri)==ContractInsertAgotados.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertAgotados.INSERT_AGOTADOS, values,
                    selection, selectionArgs);
        }else if (ContractInsertAgotados.uriMatcher.match(uri)==ContractInsertAgotados.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertAgotados.INSERT_AGOTADOS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertVenta.uriMatcher.match(uri)==ContractInsertVenta.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertVenta.INSERT_VENTA, values,
                    selection, selectionArgs);
        }else if (ContractInsertVenta.uriMatcher.match(uri)==ContractInsertVenta.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertVenta.INSERT_VENTA, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else
        if (ContractInsertVentas2.uriMatcher.match(uri)==ContractInsertVentas2.ALLROWS) {
            System.out.println("UPDATE");
            affected = db.update(ContractInsertVentas2.INSERT_PRECIOS, values,
                    selection, selectionArgs);
        }else if (ContractInsertVentas2.uriMatcher.match(uri)==ContractInsertVentas2.SINGLE_ROW) {
            String idStock = uri.getPathSegments().get(1);
            affected = db.update(ContractInsertVentas2.INSERT_PRECIOS, values,
                    Constantes.ID_REMOTA + "=" + idStock
                            + (!TextUtils.isEmpty(selection) ?
                            " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        else{
            throw new IllegalArgumentException("URI desconocida: " + uri);
        }

        resolver.notifyChange(uri, null, true);
        return affected;
    }
}
