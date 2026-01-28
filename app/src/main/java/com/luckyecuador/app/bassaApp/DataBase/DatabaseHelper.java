package com.luckyecuador.app.bassaApp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.luckyecuador.app.bassaApp.Clase.BaseAlertas;
import com.luckyecuador.app.bassaApp.Clase.BaseEvaluacionPromotor;
import com.luckyecuador.app.bassaApp.Clase.BasePharmaValue;
import com.luckyecuador.app.bassaApp.Clase.BasePortafolioProductos;
import com.luckyecuador.app.bassaApp.Clase.BasePreguntas;
import com.luckyecuador.app.bassaApp.Clase.Base_Modulos_Roles;
import com.luckyecuador.app.bassaApp.Clase.Base_campos_x_modulos;
import com.luckyecuador.app.bassaApp.Clase.Base_convenios;
import com.luckyecuador.app.bassaApp.Clase.Base_pharma_value;
import com.luckyecuador.app.bassaApp.Clase.Base_preguntas;
import com.luckyecuador.app.bassaApp.Clase.Base_productos_novedades;
import com.luckyecuador.app.bassaApp.Clase.Base_tests;
import com.luckyecuador.app.bassaApp.Clase.Base_tipo_exh;
import com.luckyecuador.app.bassaApp.Clase.Base_user;
import com.luckyecuador.app.bassaApp.Clase.InsertExhibiciones;
import com.luckyecuador.app.bassaApp.Clase.InsertCanjes;
import com.luckyecuador.app.bassaApp.Clase.InsertOsa;
import com.luckyecuador.app.bassaApp.Clase.LogUser;
import com.luckyecuador.app.bassaApp.Clase.Muestras;
import com.luckyecuador.app.bassaApp.Clase.Precio;
import com.luckyecuador.app.bassaApp.Clase.BaseTareas;
import com.luckyecuador.app.bassaApp.Clase.InsertValores;
import com.luckyecuador.app.bassaApp.Clase.BaseRotacion;
import com.luckyecuador.app.bassaApp.Clase.Tracking;
import com.luckyecuador.app.bassaApp.Clase.TrackingInsert;
import com.luckyecuador.app.bassaApp.Clase.Ventas;
import com.luckyecuador.app.bassaApp.Conexion.Constantes;
import com.luckyecuador.app.bassaApp.Conexion.ContractTipoInventario;
import com.luckyecuador.app.bassaApp.Contracts.ContractAlertas;
import com.luckyecuador.app.bassaApp.Contracts.ContractCamposPorModulos;
import com.luckyecuador.app.bassaApp.Contracts.ContractCanalesFotos;
import com.luckyecuador.app.bassaApp.Contracts.ContractCategoriaTipo;
import com.luckyecuador.app.bassaApp.Contracts.ContractCausalesMCI;
import com.luckyecuador.app.bassaApp.Contracts.ContractCausalesOSA;
import com.luckyecuador.app.bassaApp.Contracts.ContractComboCanjes;
import com.luckyecuador.app.bassaApp.Contracts.ContractConvenios;
import com.luckyecuador.app.bassaApp.Contracts.ContractEvaluacionEncuesta;
import com.luckyecuador.app.bassaApp.Contracts.ContractExhibicion;
import com.luckyecuador.app.bassaApp.Contracts.ContractExhibicionSupervisor;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertCanjes;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertConvenios;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertEjecucionMateriales;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertEvaluacionEncuesta;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertEvidencias;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertMCIPdv;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertCodificados;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertMaterialesRecibidos;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertMuestras;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertNovedades;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPDI;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPacks2;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertProbadores;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertResultadoPreguntas;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertSugeridos;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertTareas;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertTracking;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertVentas2;
import com.luckyecuador.app.bassaApp.Contracts.ContractJustificacion;
import com.luckyecuador.app.bassaApp.Contracts.ContractLinkOnedrive;
import com.luckyecuador.app.bassaApp.Contracts.ContractLog;
import com.luckyecuador.app.bassaApp.Contracts.ContractModulosRoles;
import com.luckyecuador.app.bassaApp.Contracts.ContractMotivoSugerido;
import com.luckyecuador.app.bassaApp.Contracts.ContractPDI;
import com.luckyecuador.app.bassaApp.Contracts.ContractPopSugerido;
import com.luckyecuador.app.bassaApp.Contracts.ContractPortafolioProductosAASS;
import com.luckyecuador.app.bassaApp.Contracts.ContractPortafolioProductosMAYO;
import com.luckyecuador.app.bassaApp.Contracts.ContractPrioritario;
import com.luckyecuador.app.bassaApp.Contracts.ContractProductosNovedades;
import com.luckyecuador.app.bassaApp.Contracts.ContractProductosSecundarios;
import com.luckyecuador.app.bassaApp.Contracts.ContractPromocionalVentas;
import com.luckyecuador.app.bassaApp.Contracts.ContractPromociones;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertFotografico;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertImpulso;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPacks;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPreguntas;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertProdCaducar;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertVenta;
import com.luckyecuador.app.bassaApp.Contracts.ContractPharmaValue;
import com.luckyecuador.app.bassaApp.Contracts.ContractPortafolioProductos;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertAgotados;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertExh;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertGps;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertImplementacion;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertInicial;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPdv;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPrecios;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPromocion;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertRotacion;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertRastreo;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertShare;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertValores;
import com.luckyecuador.app.bassaApp.Contracts.ContractNotificacion;
import com.luckyecuador.app.bassaApp.Contracts.ContractPrecios;
import com.luckyecuador.app.bassaApp.Contracts.ContractPreguntas;
import com.luckyecuador.app.bassaApp.Contracts.ContractRotacion;
import com.luckyecuador.app.bassaApp.Contracts.ContractSesiones.ContractInsertExhSesion;
import com.luckyecuador.app.bassaApp.Contracts.ContractSesiones.ContractInsertPreciosSesion;
import com.luckyecuador.app.bassaApp.Contracts.ContractSesiones.ContractInsertValoresSesion;
import com.luckyecuador.app.bassaApp.Contracts.ContractTareas;
import com.luckyecuador.app.bassaApp.Contracts.ContractTests;
import com.luckyecuador.app.bassaApp.Contracts.ContractTipoActividades;
import com.luckyecuador.app.bassaApp.Contracts.ContractTipoExh;
import com.luckyecuador.app.bassaApp.Contracts.ContractCategoriaTipo;
import com.luckyecuador.app.bassaApp.Contracts.ContractTipoImplementaciones;
import com.luckyecuador.app.bassaApp.Contracts.ContractTipoNovedades;
import com.luckyecuador.app.bassaApp.Contracts.ContractTipoRegistro;
import com.luckyecuador.app.bassaApp.Contracts.ContractTipoUnidades;
import com.luckyecuador.app.bassaApp.Contracts.ContractTipoVentas;
import com.luckyecuador.app.bassaApp.Contracts.ContractTracking;
import com.luckyecuador.app.bassaApp.Contracts.ContractUser;
import com.luckyecuador.app.bassaApp.Contracts.InsertExhBassa;
import com.luckyecuador.app.bassaApp.Contracts.InsertFlooring;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Lucky Ecuador on 15/07/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        createTablePuntos(database); // Crear taba "login"
        createTablePrecios(database); // Crear taba "precios"
        createTableFlooring(database);
        createTableConvenios(database);
        createTableProductosSecundarios(database);
        createTableLinkOndrive(database);
        createTableTests(database);
        createTableInsertVentas2(database);
        createTableJustificacion(database);
        createTablePromocionalVentas(database);
        createTableTipoInventario(database);
        createTableUser(database);
        createTableFlooringAASS(database);
        createTableFlooringMAYO(database);
        createTablePreguntas(database);
        createTableTipoExh(database);
        createTableCategoriaTipo(database);
        createTableMotivoSugerido(database);
        createTableInsertResultadoPreguntas(database);
        createTablePromociones(database);
        createTableTipoVentas(database); //mpin
        createTableTipoImplementaciones(database); //mpin
        createTableTipoActividades(database); //mpin - muestras medicas
        createTableTipoUnidades(database); //mpin
        createTableTipoRegistro(database); //mpin
        createTableModuloRoles(database); //mpin
        createTableRotacion(database);
        createTableTareas(database);
        createTableComboCanjes(database);
        createTableCausalesMCI(database);
        createTableCausalesOSA(database);
        createTableMaterialesAlertas(database);
        createTablePDI(database);
        createTablePopSugerido(database);
        createTablePrioritario(database);
        createTableCamposPorModulos(database);
        createTableTracking(database);
        createTableInsertPrecios(database);
        createTableInsertMuestras(database); //MPIN
        createTableInsertProbadores(database); //MPIN
        createTableInsertTracking(database);
        createTableInsertTareas(database);
        createTableInsertExh(database);
        createTableInsertGps(database);
        createTableInsertFotografico(database);
        createTableNotificacion(database);
        createTableInsertRastreo(database);
        createTableInsertFlooring(database);
        createTableInsertEvidencias(database);
        createTableInsertInicial(database);
        createTableInsertPromocion(database);
        createTableInsertImplementacion(database);
        createTableInsertValores(database);
        createTableInsertPDV(database);
        createTableInsertShare(database);
        createTableInsertPDI(database);
        createTableInsertAgotados(database);
        createTableInsertVenta(database);
        createTableInsertPreguntas(database);
        createTableInsertProdCaducar(database);
        createTableInsertSugeridos(database);
        createTableInsertRotacion(database);
        createTableInsertPacks(database);
        createTableInsertImpulso(database);
        createTableInsertCanjes(database);
        createTableInsertMaterialesRecibidos(database);
        createTableInsertEjecucionMateriales(database);
        createTableInsertMallaCodificados(database);
        createTableInsertMCIPDV(database);
        createTableInsertConvenios(database);
        createTableTipoNovedades(database);
        createTableCanalesFotos(database);
        createTableInsertNovedades(database);
        createTableLog(database);

        createTableEvaluacionEncuestas(database);
        createTableInsertEvaluacionEncuesta(database);


        createTableExhibicionSupervisor(database);
        createTableInsertExhibicionBassa(database);
        //SESIONES
        createTableInsertPreciosSesiones(database);
        createTableInsertValoresSesiones(database);
        createTableInsertExhSesiones(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("DATABASEHELPER", "Updating table from " + oldVersion + " to " + newVersion);
        try
        {
           /* if (newVersion > oldVersion) {
                db.execSQL(DATABASE_ALTER_PDV_1);
            }*/
        }
        catch (SQLiteException e) {
            Log.e("Upgrade",e.getMessage());
        }
        onCreate(db);
    }



    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    /*
     *  CREAR REPOSITORIOS
     */
    private void createTableEvaluacionEncuestas(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractEvaluacionEncuesta.EVALUACION_PROMOTOR + " (" +
                ContractEvaluacionEncuesta.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractEvaluacionEncuesta.Columnas.NOMBRE_ENCUESTA + " TEXT, " +
                ContractEvaluacionEncuesta.Columnas.DESCRIPCION + " TEXT, " +
                ContractEvaluacionEncuesta.Columnas.CATEGORIA + " TEXT, " +
                ContractEvaluacionEncuesta.Columnas.RE + " TEXT, " +
                ContractEvaluacionEncuesta.Columnas.PREGUNTA + " TEXT, " +
                ContractEvaluacionEncuesta.Columnas.TIPO_PREGUNTA + " TEXT, " +
                ContractEvaluacionEncuesta.Columnas.OPC_A + " TEXT, " +
                ContractEvaluacionEncuesta.Columnas.OPC_B + " TEXT, " +
                ContractEvaluacionEncuesta.Columnas.OPC_C + " TEXT, " +
                ContractEvaluacionEncuesta.Columnas.OPC_D + " TEXT, " +
                ContractEvaluacionEncuesta.Columnas.OPC_E + " TEXT, " +
                ContractEvaluacionEncuesta.Columnas.FOTO + " TEXT, " +
                ContractEvaluacionEncuesta.Columnas.TIPO_CAMPO + " TEXT, " +
                ContractEvaluacionEncuesta.Columnas.PUNTAJE_POR_PREGUNTA + " TEXT, " +
                ContractEvaluacionEncuesta.Columnas.HABILITADO + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK +"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertEvaluacionEncuesta(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertEvaluacionEncuesta.INSERT_EVALUACION + " (" +
                ContractInsertEvaluacionEncuesta.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                ContractInsertEvaluacionEncuesta.Columnas.CODIGO + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.USUARIO + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.CIUDAD + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.LOCAL + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.GESTOR_ASIGNADO + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.NOMBRE_ENCUESTA + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.RE + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.PREGUNTA + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.TIPO_PREGUNTA + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.RESPUESTA + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.FOTO_PREGUNTA + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.COMENTARIO_PREGUNTA + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.FOTO_FACHADA + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.CALIFICACION + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.CALIFICACION_INDIVIDUAL + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.CALIFICACION_TOTAL + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.SATISFACCION + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.COMENTARIO + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.APOYO + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.FECHA + " TEXT, " +
                ContractInsertEvaluacionEncuesta.Columnas.HORA + " TEXT, " +

                Constantes.ID_REMOTA + " TEXT ," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTablePuntos(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractPharmaValue.POS + " (" +
                ContractPharmaValue.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractPharmaValue.Columnas.CHANNEL + " TEXT, " +
                ContractPharmaValue.Columnas.SUBCHANNEL + " TEXT, " +
                ContractPharmaValue.Columnas.CHANNEL_SEGMENT + " TEXT, " +
                ContractPharmaValue.Columnas.FORMAT + " TEXT, " +
                ContractPharmaValue.Columnas.CUSTOMER_OWNER + " TEXT, " +
                ContractPharmaValue.Columnas.POS_ID + " TEXT, " +
                ContractPharmaValue.Columnas.POS_NAME + " TEXT, " +
                ContractPharmaValue.Columnas.POS_NAME_DPSM + " TEXT, " +
                ContractPharmaValue.Columnas.ZONA + " TEXT, " +
                ContractPharmaValue.Columnas.REGION + " TEXT, " +
                ContractPharmaValue.Columnas.PROVINCIA + " TEXT, " +
                ContractPharmaValue.Columnas.CIUDAD + " TEXT, " +
                ContractPharmaValue.Columnas.DIRECCION + " TEXT, " +
                ContractPharmaValue.Columnas.KAM + " TEXT, " +
                ContractPharmaValue.Columnas.SALES_EXECUTIVE + " TEXT, " +
                ContractPharmaValue.Columnas.MERCHANDISING + " TEXT, " +
                ContractPharmaValue.Columnas.SUPERVISOR + " TEXT, " +
                ContractPharmaValue.Columnas.ROL + " TEXT, " +
                ContractPharmaValue.Columnas.MERCADERISTA + " TEXT, " +
                ContractPharmaValue.Columnas.USER + " TEXT, " +
                ContractPharmaValue.Columnas.DPSM + " TEXT, " +
                ContractPharmaValue.Columnas.STATUS + " TEXT, " +
                ContractPharmaValue.Columnas.TIPO + " TEXT, " +
                ContractPharmaValue.Columnas.LATITUD + " TEXT, " +
                ContractPharmaValue.Columnas.LONGITUD + " TEXT, " +
                ContractPharmaValue.Columnas.FOTO + " TEXT, " +
                ContractPharmaValue.Columnas.SEGMENTACION + " TEXT, " +
                ContractPharmaValue.Columnas.COMPRAS + " TEXT, " +
                ContractPharmaValue.Columnas.PASS + " TEXT, " +
                ContractPharmaValue.Columnas.NUMERO_CONTROLLER + " TEXT, " +
                ContractPharmaValue.Columnas.FECHA_VISITA + " TEXT, " +
                ContractPharmaValue.Columnas.DEVICE_ID + " TEXT, " +
                ContractPharmaValue.Columnas.PERIMETRO + " TEXT, " +
                ContractPharmaValue.Columnas.DISTANCIA + " TEXT, " +
                ContractPharmaValue.Columnas.TERMOMETRO + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK +"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    
    //new basa
    private void createTableInsertExhibicionBassa(SQLiteDatabase database){
        String cmd = "CREATE TABLE " + InsertExhBassa.INSERT_EXHIBICION_COLGATE + " (" +
                InsertExhBassa.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                InsertExhBassa.Columnas.ID_PDV + " TEXT, " +
                InsertExhBassa.Columnas.ID_R_EXH + " TEXT, " +
                InsertExhBassa.Columnas.USUARIO + " TEXT, " +
                InsertExhBassa.Columnas.NOMBRE + " TEXT, " +
                InsertExhBassa.Columnas.SUPERVISOR + " TEXT, " +
                InsertExhBassa.Columnas.NOMBRE_PDV + " TEXT, " +
                InsertExhBassa.Columnas.CODIGO + " TEXT, " +
                InsertExhBassa.Columnas.REPORTE_NUEVA + " TEXT, " +
                InsertExhBassa.Columnas.REPORTE_MANT + " TEXT, " +
                InsertExhBassa.Columnas.HERRAMIENTA + " TEXT, " +
                InsertExhBassa.Columnas.TIPO_EXHIBICION + " TEXT, " +
                InsertExhBassa.Columnas.FABRICANTE + " TEXT, " +
                InsertExhBassa.Columnas.TIPO_HERRAMIENTA + " TEXT, " +
                InsertExhBassa.Columnas.TIPO_NOVEDAD + " TEXT, " +
                InsertExhBassa.Columnas.CONVENIO + " TEXT, " +
                InsertExhBassa.Columnas.ELIMINAR + " TEXT, " +
                InsertExhBassa.Columnas.SUBCAT + " TEXT, " +
                InsertExhBassa.Columnas.CAT + " TEXT, " +
                InsertExhBassa.Columnas.CAMPANA + " TEXT, " +
                InsertExhBassa.Columnas.OBSERVACION + " TEXT, " +
                InsertExhBassa.Columnas.CLASIFICACION + " TEXT, " +
                InsertExhBassa.Columnas.NUMEROEXH + " TEXT, " +
                InsertExhBassa.Columnas.RESPUESTA + " TEXT, " +
                InsertExhBassa.Columnas.KEY_IMAGE + " TEXT, " +
                InsertExhBassa.Columnas.PUNTO_APOYO + " TEXT, " +
                InsertExhBassa.Columnas.FECHA + " TEXT, " +
                InsertExhBassa.Columnas.HORA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableExhibicionSupervisor(SQLiteDatabase database){
        String cmd = "CREATE TABLE " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR + " (" +
                ContractExhibicionSupervisor.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractExhibicionSupervisor.Columnas.CP_CODE + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.CANAL + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.SUPERVISOR + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.MANUFACTURER + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.CATEGORY + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.SUBCATEGORY + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.ZONE_EXHIBITION + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.PERSONALIZATION + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.PRICE_TAG + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.OBSERVATION + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.PHOTO + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.DATE + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.VISUAL_ACCESS + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.PHOTO2 + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.CAMPANA + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.CONVENIO + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.CLASIFICACION + " TEXT, " +
                ContractExhibicionSupervisor.Columnas.ID_REMOTA + " TEXT UNIQUE," +
                ContractExhibicionSupervisor.Columnas.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                ContractExhibicionSupervisor.Columnas.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableTipoNovedades(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractTipoNovedades.TIPO_NOVEDADES + " (" +
                ContractTipoNovedades.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractTipoNovedades.Columnas.TIPO_NOVEDAD + " TEXT, " +
                ContractTipoNovedades.Columnas.CUENTA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT " + Constantes.ESTADO_OK + "," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableCanalesFotos(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractCanalesFotos.CANALES_FOTOS + " (" +
                ContractCanalesFotos.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractCanalesFotos.Columnas.PDV + " TEXT, " +
                ContractCanalesFotos.Columnas.CANAL + " TEXT, " +
                ContractCanalesFotos.Columnas.ACTIVAR + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT " + Constantes.ESTADO_OK + "," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }


    private void createTableInsertNovedades(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertNovedades.INSERT_NOVEDADES + " (" +
                ContractInsertNovedades.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertNovedades.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertNovedades.Columnas.CODIGO + " TEXT, " +
                ContractInsertNovedades.Columnas.USUARIO + " TEXT, " +
                ContractInsertNovedades.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertNovedades.Columnas.POS_NAME + " TEXT, " +
                ContractInsertNovedades.Columnas.TIPO_NOVEDAD + " TEXT, " +
                ContractInsertNovedades.Columnas.MARCA + " TEXT, " +
                ContractInsertNovedades.Columnas.LOTE + " TEXT, " +
                ContractInsertNovedades.Columnas.SKU + " TEXT, " +
                ContractInsertNovedades.Columnas.TIPO + " TEXT, " +
                ContractInsertNovedades.Columnas.FECHA_VENCIMIENTO + " TEXT, " +
                ContractInsertNovedades.Columnas.FECHA_ELABORACION + " TEXT, " +
                ContractInsertNovedades.Columnas.NUMERO_FACTURA + " TEXT, " +
                ContractInsertNovedades.Columnas.COMENTARIO_LOTE + " TEXT, " +
                ContractInsertNovedades.Columnas.COMENTARIO_FACTURA + " TEXT, " +
                ContractInsertNovedades.Columnas.COMENTARIO_SKU + " TEXT, " +
                ContractInsertNovedades.Columnas.OBSERVACION + " TEXT, " +
                ContractInsertNovedades.Columnas.TIPO_IMPLEMENTACION + " TEXT, " +
                ContractInsertNovedades.Columnas.MECANICA + " TEXT, " +
                ContractInsertNovedades.Columnas.FECHA_INICIO + " TEXT, " +
                ContractInsertNovedades.Columnas.AGOTAR_STOCK + " TEXT, " +
                ContractInsertNovedades.Columnas.PRECIO_ANTERIOR + " TEXT, " +
                ContractInsertNovedades.Columnas.PRECIO_PROMOCION + " TEXT, " +
                ContractInsertNovedades.Columnas.FOTO + " TEXT, " +
                ContractInsertNovedades.Columnas.SKU_CODE + " TEXT, " +
                ContractInsertNovedades.Columnas.CANTIDAD + " TEXT, " +
                ContractInsertNovedades.Columnas.FOTO_PRODUCTO + " TEXT, " +
                ContractInsertNovedades.Columnas.FOTO_FACTURA + " TEXT, " +
                ContractInsertNovedades.Columnas.FECHA + " TEXT, " +
                ContractInsertNovedades.Columnas.HORA + " TEXT, " +
                ContractInsertNovedades.Columnas.CUENTA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT " + Constantes.ESTADO_OK + "," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }


    private void createTablePromociones(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractPromociones.PROMOCIONES + " (" +
                ContractPromociones.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractPromociones.Columnas.CANAL + " TEXT, " +
                ContractPromociones.Columnas.TIPO + " TEXT, " +
                ContractPromociones.Columnas.CADENA + " TEXT, " +
                ContractPromociones.Columnas.FABRICANTE + " TEXT, " +
                ContractPromociones.Columnas.DESCRIPCION + " TEXT, " +
                ContractPromociones.Columnas.CATEGORIA + " TEXT, " +
                ContractPromociones.Columnas.SUBCATEGORIA + " TEXT, " +
                ContractPromociones.Columnas.MARCA + " TEXT, " +
                ContractPromociones.Columnas.SKU + " TEXT, " +
                ContractPromociones.Columnas.CAMPANA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK +"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }


//mpin
private void createTableTipoActividades(SQLiteDatabase database) {
    String cmd = "CREATE TABLE " + ContractTipoActividades.TIPO_ACTIVIDADES + " (" +
            ContractTipoActividades.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ContractTipoActividades.Columnas.TIPO + " TEXT, " +
            Constantes.ID_REMOTA + " TEXT UNIQUE," +
            Constantes.ESTADO + " INTEGER NOT NULL DEFAULT " + Constantes.ESTADO_OK + "," +
            Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
    database.execSQL(cmd);
}
    private void createTableTipoVentas(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractTipoVentas.TIPO_VENTAS + " (" +
                ContractTipoVentas.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractTipoVentas.Columnas.TIPO + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT " + Constantes.ESTADO_OK + "," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }
    //MPIN
    private void createTableTipoImplementaciones(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractTipoImplementaciones.TIPO_IMPLEMENTACIONES + " (" +
                ContractTipoImplementaciones.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractTipoImplementaciones.Columnas.TIPO + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT " + Constantes.ESTADO_OK + "," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }
    //min repounidades- novedades
    private void createTableTipoUnidades(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractTipoUnidades.TIPO_UNIDADES + " (" +
                ContractTipoUnidades.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractTipoUnidades.Columnas.TIPO + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT " + Constantes.ESTADO_OK + "," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableTipoRegistro(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractTipoRegistro.TIPO_REGISTRO + " (" +
                ContractTipoRegistro.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractTipoRegistro.Columnas.TIPO + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT " + Constantes.ESTADO_OK + "," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }
    private void createTableModuloRoles(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractModulosRoles.MODULO_ROLES + " (" +
                ContractModulosRoles.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractModulosRoles.Columnas.ROL + " TEXT, " +
                ContractModulosRoles.Columnas.MODULO + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT " + Constantes.ESTADO_OK + "," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }



    private void createTableComboCanjes(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractComboCanjes.COMBO_CANJES + " (" +
                ContractComboCanjes.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractComboCanjes.Columnas.TIPO_COMBO + " TEXT, " +
                ContractComboCanjes.Columnas.MECANICA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK +"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }


    private void createTableCausalesMCI(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractCausalesMCI.CAUSALES_MCI + " (" +
                ContractCausalesMCI.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractCausalesMCI.Columnas.CAUSAL + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK +"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableCausalesOSA(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractCausalesOSA.CAUSALES_OSA + " (" +
                ContractCausalesOSA.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractCausalesOSA.Columnas.CANAL + " TEXT, " +
                ContractCausalesOSA.Columnas.RESPONSABLE + " TEXT, " +
                ContractCausalesOSA.Columnas.CAUSAL + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK +"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableJustificacion(SQLiteDatabase database){
        String cmd = "CREATE TABLE " + ContractJustificacion.JUSTIFICACIONES + " (" +
                ContractJustificacion.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractJustificacion.Columnas.JUSTIFICACION + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK +"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTablePromocionalVentas(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractPromocionalVentas.PROMOCIONAL_VENTAS + " (" +
                ContractPromocionalVentas.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractPromocionalVentas.Columnas.CADENA + " TEXT, " +
                ContractPromocionalVentas.Columnas.PROMOCIONAL + " TEXT, " +
                ContractPromocionalVentas.Columnas.CUENTA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT " + Constantes.ESTADO_OK + "," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableTipoInventario(SQLiteDatabase database){
        String cmd = "CREATE TABLE " + ContractTipoInventario.TIPOINVENTARIO + " (" +
                ContractTipoInventario.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractTipoInventario.Columnas.CAUSALES + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK +"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableMaterialesAlertas(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractAlertas.ALERTAS + " (" +
                ContractAlertas.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractAlertas.Columnas.TIPO_ALERTA + " TEXT, " +
                ContractAlertas.Columnas.CATEGORIA + " TEXT, " +
                ContractAlertas.Columnas.MATERIAL + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK +"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTablePDI(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractPDI.PDI + " (" +
                ContractPDI.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractPDI.Columnas.CANAL + " TEXT, " +
                ContractPDI.Columnas.CATEGORIA + " TEXT, " +
                ContractPDI.Columnas.SUBCATEGORIA + " TEXT, " +
                ContractPDI.Columnas.MARCA + " TEXT, " +
                ContractPDI.Columnas.OBJETIVO + " TEXT, " +
                ContractPDI.Columnas.PLATAFORMA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK +"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableRotacion(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractRotacion.ROTACION + " (" +
                ContractRotacion.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractRotacion.Columnas.CATEGORIA + " TEXT, " +
                ContractRotacion.Columnas.SUBCATEGORIA + " TEXT, " +
                ContractRotacion.Columnas.MARCA + " TEXT, " +
                ContractRotacion.Columnas.PRODUCTO + " TEXT, " +
                ContractRotacion.Columnas.PROMOCIONAL + " TEXT, " +
                ContractRotacion.Columnas.MECANICA + " TEXT, " +
                ContractRotacion.Columnas.PESO + " TEXT, " +
                ContractRotacion.Columnas.TIPO + " TEXT, " +
                ContractRotacion.Columnas.PLATAFORMA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK +"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableTareas(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractTareas.TAREA + " (" +
                ContractTareas.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractTareas.Columnas.CANAL + " TEXT, " +
                ContractTareas.Columnas.CODIGOPDV + " TEXT, " +
                ContractTareas.Columnas.MERCADERISTA + " TEXT, " +
                ContractTareas.Columnas.TAREAS + " TEXT, " +
                ContractTareas.Columnas.PERIODO + " TEXT, " +
                ContractTareas.Columnas.FECHA_INGRESO + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK +"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTablePopSugerido(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractPopSugerido.POPSUGERIDO + " (" +
                ContractPopSugerido.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractPopSugerido.Columnas.CANAL + " TEXT, " +
                ContractPopSugerido.Columnas.CODIGO_PDV + " TEXT, " +
                ContractPopSugerido.Columnas.POP_SUGERIDO + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK +"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTablePrioritario(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractPrioritario.PRIORITARIO + " (" +
                ContractPrioritario.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractPrioritario.Columnas.CANAL + " TEXT, " +
                ContractPrioritario.Columnas.CODIGO_PDV + " TEXT, " +
                ContractPrioritario.Columnas.CATEGORIA + " TEXT, " +
                ContractPrioritario.Columnas.SUBCATEGORIA + " TEXT, " +
                ContractPrioritario.Columnas.MARCA + " TEXT, " +
                ContractPrioritario.Columnas.CONTENIDO + " TEXT, " +
                ContractPrioritario.Columnas.SKU + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK +"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }
    private void createTableCamposPorModulos(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractCamposPorModulos.CAMPOS_X_MODULOS + " (" +
                ContractCamposPorModulos.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractCamposPorModulos.Columnas.MODULO + " TEXT, " +
                ContractCamposPorModulos.Columnas.CAMPO + " TEXT, " +
                ContractCamposPorModulos.Columnas.OBLIGATORIO + " TEXT, " +
                ContractCamposPorModulos.Columnas.CUENTA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT " + Constantes.ESTADO_OK + "," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }
    private void createTableTracking(SQLiteDatabase database){
        String cmd = "CREATE TABLE " + ContractTracking.TRACKING + " (" +
                ContractTracking.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractTracking.Columnas.CUSTOMER + " TEXT, " +
                ContractTracking.Columnas.CUENTAS + " TEXT, " +
                ContractTracking.Columnas.MECANICA + " TEXT, " +
                ContractTracking.Columnas.CATEGORIA + " TEXT, " +
                ContractTracking.Columnas.SUBCATEGORIA + " TEXT, " +
                ContractTracking.Columnas.MARCA + " TEXT, " +
                ContractTracking.Columnas.DESCRIPCION + " TEXT, " +
                ContractTracking.Columnas.PRECIO_PROMOCION + " TEXT, " +
                ContractTracking.Columnas.VIGENCIA + " TEXT, " +
                ContractTracking.Columnas.MATERIAL_POP + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }


    private void createTableTests(SQLiteDatabase database){
        String cmd = "CREATE TABLE IF NOT EXISTS " + ContractTests.TABLE_TEST + " ( "
                + ContractTests.Columnas.KEY_TEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractTests.Columnas.KEY_TEST + " TEXT, " +
                ContractTests.Columnas.KEY_DESCRIPTION + " TEXT, " +
                ContractTests.Columnas.KEY_DATE_START +" TEXT, " +
                ContractTests.Columnas.KEY_HOUR_START +" TEXT, " +
                ContractTests.Columnas.KEY_DATE_LIMIT +" TEXT, "+
                ContractTests.Columnas.KEY_HOUR_LIMIT +" TEXT, "+
                ContractTests.Columnas.KEY_ACTIVE + " TEXT, "+
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertVentas2(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertVentas2.INSERT_PRECIOS + " (" +
                ContractInsertVentas2.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertVentas2.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertVentas2.Columnas.CODIGO + " TEXT, " +
                ContractInsertVentas2.Columnas.USUARIO + " TEXT, " +
                ContractInsertVentas2.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertVentas2.Columnas.FECHA + " TEXT, " +
                ContractInsertVentas2.Columnas.HORA + " TEXT, " +
                ContractInsertVentas2.Columnas.NOMBRE_IMPULSADOR + " TEXT, " +
                ContractInsertVentas2.Columnas.FECHA_VENTA + " TEXT, " +
                ContractInsertVentas2.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertVentas2.Columnas.SUBCATEGORIA + " TEXT, " +
                ContractInsertVentas2.Columnas.PRESENTACION + " TEXT, " +
                ContractInsertVentas2.Columnas.POFERTA + " TEXT, " +
                ContractInsertVentas2.Columnas.BRAND + " TEXT, " +
                /*ContractInsertVentas2.Columnas.TAMANO + " TEXT, " +*/
                ContractInsertVentas2.Columnas.SKU_CODE + " TEXT, " +
                ContractInsertVentas2.Columnas.TIPO_VENTA + " TEXT, " +
                ContractInsertVentas2.Columnas.STOCK_INICIAL + " TEXT, " +
                ContractInsertVentas2.Columnas.CANTIDAD + " TEXT, " +
                ContractInsertVentas2.Columnas.PREGULAR + " TEXT, " +
                ContractInsertVentas2.Columnas.PPROMOCION + " TEXT, " +
                ContractInsertVentas2.Columnas.STOCK_FINAL + " TEXT, " +
                ContractInsertVentas2.Columnas.VALOR_TOTAL + " TEXT, " +
                ContractInsertVentas2.Columnas.TOTAL_FACTURA + " TEXT, " +
                ContractInsertVentas2.Columnas.NUMERO_FACTURA + " TEXT, " +
                ContractInsertVentas2.Columnas.ENTREGO_PROMOCIONAL + " TEXT, " +
                ContractInsertVentas2.Columnas.PROMOCIONAL + " TEXT, " +
                ContractInsertVentas2.Columnas.CANT_PROMOCIONAL + " TEXT, " +
                ContractInsertVentas2.Columnas.MANUFACTURER + " TEXT, " +
                ContractInsertVentas2.Columnas.POS_NAME + " TEXT, " +
                ContractInsertVentas2.Columnas.FOTO + " TEXT, " +
                ContractInsertVentas2.Columnas.FOTO_ADICIONAL + " TEXT, " +
                ContractInsertVentas2.Columnas.CUENTA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT " + Constantes.ESTADO_OK + "," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertResultadoPreguntas(SQLiteDatabase database) {
        String cmd = "CREATE TABLE IF NOT EXISTS " + ContractInsertResultadoPreguntas.INSERT_RESULTADO_PREGUNTAS + " ( "
                + ContractInsertResultadoPreguntas.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertResultadoPreguntas.Columnas.KEY_USUARIO + " TEXT, " +
                ContractInsertResultadoPreguntas.Columnas.KEY_TEST_ID + " TEXT, " +
                ContractInsertResultadoPreguntas.Columnas.KEY_TEST + " TEXT, " +
                ContractInsertResultadoPreguntas.Columnas.KEY_QUES + " TEXT, " +
                ContractInsertResultadoPreguntas.Columnas.KEY_OPTA + " TEXT, " +
                ContractInsertResultadoPreguntas.Columnas.KEY_OPTB + " TEXT, " +
                ContractInsertResultadoPreguntas.Columnas.KEY_OPTC + " TEXT, " +
                ContractInsertResultadoPreguntas.Columnas.KEY_ANSWER + " TEXT, " +
                ContractInsertResultadoPreguntas.Columnas.KEY_ANSWER_USER + " TEXT, " +
                ContractInsertResultadoPreguntas.Columnas.KEY_RESULT + " TEXT, " +
                ContractInsertResultadoPreguntas.Columnas.KEY_FECHA + " TEXT, " +
                ContractInsertResultadoPreguntas.Columnas.KEY_HORA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT " + Constantes.ESTADO_OK + "," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }


    private void createTablePrecios(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractPrecios.PRECIOS + " (" +
                ContractPrecios.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractPrecios.Columnas.PRODUCTO + " TEXT, " +
                ContractPrecios.Columnas.SEGMENTO + " TEXT, " +
                ContractPrecios.Columnas.MARCA + " TEXT, " +
                ContractPrecios.Columnas.CATEGORIA + " TEXT, " +
                ContractPrecios.Columnas.SUBCATEGORIA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableFlooring(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " (" +
                ContractPortafolioProductos.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                //ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO + " TEXT, "+
                ContractPortafolioProductos.Columnas.SECTOR + " TEXT, "+
                ContractPortafolioProductos.Columnas.CATEGORY + " TEXT, "+
                ContractPortafolioProductos.Columnas.SUBCATEGORIA + " TEXT, "+
                ContractPortafolioProductos.Columnas.SEGMENTO + " TEXT, "+
                ContractPortafolioProductos.Columnas.PRESENTACION + " TEXT, "+
                ContractPortafolioProductos.Columnas.VARIANTE1 + " TEXT, "+
                ContractPortafolioProductos.Columnas.VARIANTE2 + " TEXT, "+
                ContractPortafolioProductos.Columnas.CONTENIDO + " TEXT, "+
                ContractPortafolioProductos.Columnas.SKU + " TEXT, "+
                ContractPortafolioProductos.Columnas.MARCA + " TEXT, "+
                ContractPortafolioProductos.Columnas.FABRICANTE + " TEXT, "+
                ContractPortafolioProductos.Columnas.PVP + " TEXT, "+
                ContractPortafolioProductos.Columnas.CADENAS + " TEXT, "+
                ContractPortafolioProductos.Columnas.FOTO + " TEXT, "+
                ContractPortafolioProductos.Columnas.PLATAFORMA + " TEXT, "+
                /*ContractPortafolioProductos.Columnas.MANUFACTURER + " TEXT, "+
                ContractPortafolioProductos.Columnas.FORMAT + " TEXT, "+*/
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableConvenios(SQLiteDatabase database){
        String cmd = "CREATE TABLE " + ContractConvenios.CONVENIOS + " (" +
                ContractConvenios.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractConvenios.Columnas.CODIGO + " TEXT, " +
                ContractConvenios.Columnas.PDV + " TEXT, " +
                ContractConvenios.Columnas.CATEGORIA + " TEXT, " +
                ContractConvenios.Columnas.UNIDAD_NEGOCIO + " TEXT, " +
                ContractConvenios.Columnas.FABRICANTE + " TEXT, " +
                ContractConvenios.Columnas.MARCA + " TEXT, " +
                ContractConvenios.Columnas.TIPO_EXHIBICION + " TEXT, " +
                ContractConvenios.Columnas.NUMERO_EXHIBICION + " TEXT, " +
                ContractConvenios.Columnas.FORMATO + " TEXT," +
                ContractConvenios.Columnas.FECHA_SUBIDA + " TEXT," +
                ContractConvenios.Columnas.ENLACE + " TEXT," +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }


    private void createTableProductosSecundarios(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractProductosSecundarios.PRODUCTOS_SECUNDARIOS + " (" +
                ContractPortafolioProductos.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                //ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO + " TEXT, "+
                ContractProductosSecundarios.Columnas.SECTOR + " TEXT, "+
                ContractProductosSecundarios.Columnas.CATEGORY + " TEXT, "+
                ContractProductosSecundarios.Columnas.SUBCATEGORIA + " TEXT, "+
                ContractProductosSecundarios.Columnas.SEGMENTO + " TEXT, "+
                ContractProductosSecundarios.Columnas.PRESENTACION + " TEXT, "+
                ContractProductosSecundarios.Columnas.VARIANTE1 + " TEXT, "+
                ContractProductosSecundarios.Columnas.VARIANTE2 + " TEXT, "+
                ContractProductosSecundarios.Columnas.CONTENIDO + " TEXT, "+
                ContractProductosSecundarios.Columnas.SKU + " TEXT, "+
                ContractProductosSecundarios.Columnas.MARCA + " TEXT, "+
                ContractProductosSecundarios.Columnas.FABRICANTE + " TEXT, "+
                ContractProductosSecundarios.Columnas.PVP + " TEXT, "+
                ContractProductosSecundarios.Columnas.CADENAS + " TEXT, "+
                ContractProductosSecundarios.Columnas.FOTO + " TEXT, "+
                ContractProductosSecundarios.Columnas.PLATAFORMA + " TEXT, "+
                /*ContractProductosSecundarios.Columnas.MANUFACTURER + " TEXT, "+
                ContractPortafolioProductos.Columnas.FORMAT + " TEXT, "+*/
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }


    private void createTableLinkOndrive(SQLiteDatabase database){
        String cmd = "CREATE TABLE " + ContractLinkOnedrive.LINK_ONDRIVE + " (" +
                ContractLinkOnedrive.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractLinkOnedrive.Columnas.MODULO + " TEXT, " +
                ContractLinkOnedrive.Columnas.LINK + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK +"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableFlooringAASS(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractPortafolioProductosAASS.PORTAFOLIOPRODUCTOS_AASS + " (" +
                ContractPortafolioProductosAASS.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractPortafolioProductosAASS.Columnas.CATEGORIA + " TEXT, "+
                ContractPortafolioProductosAASS.Columnas.SUBCATEGORIA + " TEXT, "+
                ContractPortafolioProductosAASS.Columnas.MARCA + " TEXT, "+
                ContractPortafolioProductosAASS.Columnas.FABRICANTE + " TEXT, "+
                ContractPortafolioProductosAASS.Columnas.SKU + " TEXT, "+
                ContractPortafolioProductosAASS.Columnas.CADENAS + " TEXT, "+
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableUser(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractUser.TABLE_USER + " (" +
                ContractUser.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractUser.Columnas.USER + " TEXT, " +
                ContractUser.Columnas.MERCADERISTA + " TEXT, " +
                ContractUser.Columnas.DEVICE_ID + " TEXT, " +
                ContractUser.Columnas.COLOR + " TEXT, " +
                ContractUser.Columnas.STATUS + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK +"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableFlooringMAYO(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractPortafolioProductosMAYO.PORTAFOLIOPRODUCTOS_MAYO + " (" +
                ContractPortafolioProductosMAYO.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractPortafolioProductosMAYO.Columnas.CODIGO + " TEXT, "+
                ContractPortafolioProductosMAYO.Columnas.USUARIO + " TEXT, "+
                ContractPortafolioProductosMAYO.Columnas.CATEGORIA + " TEXT, "+
                ContractPortafolioProductosMAYO.Columnas.SUBCATEGORIA + " TEXT, "+
                ContractPortafolioProductosMAYO.Columnas.MARCA + " TEXT, "+
                ContractPortafolioProductosMAYO.Columnas.FABRICANTE + " TEXT, "+
                ContractPortafolioProductosMAYO.Columnas.SKU + " TEXT, "+
                ContractPortafolioProductosMAYO.Columnas.STATUS + " TEXT, "+
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTablePreguntas(SQLiteDatabase database) {
        String cmd = "CREATE TABLE IF NOT EXISTS " + ContractPreguntas.TABLE_QUEST + " ( "
                + ContractPreguntas.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractPreguntas.Columnas.KEY_QUES + " TEXT, " +
                ContractPreguntas.Columnas.KEY_ANSWER + " TEXT, " +
                ContractPreguntas.Columnas.KEY_OPTA +" TEXT, " +
                ContractPreguntas.Columnas.KEY_OPTB +" TEXT, "+
                ContractPreguntas.Columnas.KEY_OPTC + " TEXT, "+
                ContractPreguntas.Columnas.KEY_CANAL + " TEXT, "+
                ContractPreguntas.Columnas.KEY_TIEMPO + " TEXT, "+
                ContractPreguntas.Columnas.KEY_TEST_ID + " TEXT, "+
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableTipoExh(SQLiteDatabase database) {
        String cmd = "CREATE TABLE IF NOT EXISTS " + ContractTipoExh.TABLE_NAME + " ( "
                + ContractTipoExh.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractTipoExh.Columnas.CANAL + " TEXT, " +
                ContractTipoExh.Columnas.EXHIBICION + " TEXT, " +
                ContractTipoExh.Columnas.FOTO +" TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableCategoriaTipo(SQLiteDatabase database) {
        String cmd = "CREATE TABLE IF NOT EXISTS " + ContractCategoriaTipo.CATEGORIA_TIPO + " ( "
                + ContractCategoriaTipo.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractCategoriaTipo.Columnas.CANAL + " TEXT, " +
                ContractCategoriaTipo.Columnas.TIPO + " TEXT, " +

                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableMotivoSugerido(SQLiteDatabase database) {
        String cmd = "CREATE TABLE IF NOT EXISTS " + ContractMotivoSugerido.TABLE_NAME + " ( "
                + ContractMotivoSugerido.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractMotivoSugerido.Columnas.CANAL + " TEXT, " +
                ContractMotivoSugerido.Columnas.MOTIVO + " TEXT, " +
                ContractMotivoSugerido.Columnas.FOTO + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertPrecios(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertPrecios.INSERT_PRECIOS + " (" +
                ContractInsertPrecios.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertPrecios.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertPrecios.Columnas.CODIGO + " TEXT, " +
                ContractInsertPrecios.Columnas.USUARIO + " TEXT, " +
                ContractInsertPrecios.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertPrecios.Columnas.FECHA + " TEXT, " +
                ContractInsertPrecios.Columnas.HORA + " TEXT, " +
                ContractInsertPrecios.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertPrecios.Columnas.SUBCATEGORIA + " TEXT, " +
                ContractInsertPrecios.Columnas.PRESENTACION + " TEXT, " +
                ContractInsertPrecios.Columnas.POFERTA + " TEXT, " +
                ContractInsertPrecios.Columnas.BRAND + " TEXT, " +
                ContractInsertPrecios.Columnas.FABRICANTE + " TEXT, " +
                ContractInsertPrecios.Columnas.PRECIO_DESCUENTO + " TEXT, " +
                ContractInsertPrecios.Columnas.C_DESCUENTO + " TEXT, " +
                /*ContractInsertPrecios.Columnas.TAMANO + " TEXT, " +
                ContractInsertPrecios.Columnas.CANTIDAD + " TEXT, " +*/
                ContractInsertPrecios.Columnas.SKU_CODE + " TEXT, " +
                ContractInsertPrecios.Columnas.PREGULAR + " TEXT, " +
                ContractInsertPrecios.Columnas.PPROMOCION + " TEXT, " +
                ContractInsertPrecios.Columnas.VAR_SUGERIDO + " TEXT, " +
                ContractInsertPrecios.Columnas.MANUFACTURER + " TEXT, " +
                ContractInsertPrecios.Columnas.POS_NAME + " TEXT, " +
                ContractInsertPrecios.Columnas.PLATAFORMA + " TEXT, " +
                ContractInsertPrecios.Columnas.FOTO + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertMuestras(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertMuestras.INSERT_MUESTRAS + " (" +
                ContractInsertMuestras.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertMuestras.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertMuestras.Columnas.CODIGO + " TEXT, " +
                ContractInsertMuestras.Columnas.USUARIO + " TEXT, " +
                ContractInsertMuestras.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertMuestras.Columnas.FECHA + " TEXT, " +
                ContractInsertMuestras.Columnas.HORA + " TEXT, " +
                ContractInsertMuestras.Columnas.MARCA + " TEXT, " +
                ContractInsertMuestras.Columnas.SKU + " TEXT, " +
                ContractInsertMuestras.Columnas.TIPO_ACTIVIDAD + " TEXT, " +
                ContractInsertMuestras.Columnas.CANTIDAD + " TEXT, " +
                ContractInsertMuestras.Columnas.POS_NAME + " TEXT, " +

                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertProbadores(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertProbadores.INSERT_PROBADORES + " (" +
                ContractInsertProbadores.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertProbadores.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertProbadores.Columnas.CODIGO + " TEXT, " +
                ContractInsertProbadores.Columnas.USUARIO + " TEXT, " +
                ContractInsertProbadores.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertProbadores.Columnas.FECHA + " TEXT, " +
                ContractInsertProbadores.Columnas.HORA + " TEXT, " +
                ContractInsertProbadores.Columnas.TIPO_REGISTRO + " TEXT, " +
                ContractInsertProbadores.Columnas.MARCA + " TEXT, " +
                ContractInsertProbadores.Columnas.SKU + " TEXT, " +
                ContractInsertProbadores.Columnas.CANTIDAD + " TEXT, " +
                ContractInsertProbadores.Columnas.LOTE + " TEXT, " +
                ContractInsertProbadores.Columnas.FECHA_VENCIMIENTO + " TEXT, " +
                ContractInsertProbadores.Columnas.FABRICANTE + " TEXT, " +
                ContractInsertProbadores.Columnas.FOTO + " TEXT, " +
                ContractInsertProbadores.Columnas.COMENTARIO + " TEXT, " +

                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }


    private void createTableInsertTracking(SQLiteDatabase database){
        String cmd = "CREATE TABLE " + ContractInsertTracking.INSERT_TRACKING + " (" +
                ContractInsertTracking.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertTracking.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertTracking.Columnas.FECHA + " TEXT, " +
                ContractInsertTracking.Columnas.HORA + " TEXT, " +
                ContractInsertTracking.Columnas.CODIGO + " TEXT, " +
                ContractInsertTracking.Columnas.LOCAL + " TEXT, " +
                ContractInsertTracking.Columnas.USUARIO + " TEXT, " +
                ContractInsertTracking.Columnas.LATITUD + " TEXT, " +
                ContractInsertTracking.Columnas.LONGITUD + " TEXT, " +
                ContractInsertTracking.Columnas.MECANICA  + " TEXT, " +
                ContractInsertTracking.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertTracking.Columnas.DESCRIPCION + " TEXT, " +
                ContractInsertTracking.Columnas.VIGENCIA + " TEXT, " +
                ContractInsertTracking.Columnas.STATUS_ACTIVIDAD + " TEXT, " +
                ContractInsertTracking.Columnas.COMENTARIO + " TEXT, " +
                ContractInsertTracking.Columnas.FOTO + " TEXT, " +
                ContractInsertTracking.Columnas.PRECIO_PROMOCION + " TEXT, " +
                ContractInsertTracking.Columnas.MATERIAL_POP + " TEXT, " +
                ContractInsertTracking.Columnas.IMPLEMENTACION_POP + " TEXT, " +
                ContractInsertTracking.Columnas.CUENTA + " TEXT, " +
                ContractInsertTracking.Columnas.MODULO + " TEXT, " +
                ContractInsertTracking.Columnas.UNIDAD_DE_NEGOCIO + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }


    private void createTableInsertMallaCodificados(SQLiteDatabase database){
        String cmd = "CREATE TABLE " + ContractInsertCodificados.INSERT_CODIFICADOS + " (" +
                ContractInsertCodificados.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertCodificados.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertCodificados.Columnas.CODIGO + " TEXT, " +
                ContractInsertCodificados.Columnas.POS_NAME + " TEXT, " +
                ContractInsertCodificados.Columnas.USUARIO + " TEXT, " +
                ContractInsertCodificados.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertCodificados.Columnas.FECHA + " TEXT, " +
                ContractInsertCodificados.Columnas.HORA + " TEXT, " +
                ContractInsertCodificados.Columnas.SECTOR + " TEXT, " +
                ContractInsertCodificados.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertCodificados.Columnas.SUBCATEGORIA + " TEXT, " +
                ContractInsertCodificados.Columnas.PRESENTACION + " TEXT, " +
                ContractInsertCodificados.Columnas.FABRICANTE + " TEXT, " +
                ContractInsertCodificados.Columnas.BRAND + " TEXT, " +
                ContractInsertCodificados.Columnas.CONTENIDO + " TEXT, " +
                ContractInsertCodificados.Columnas.VARIANTE + " TEXT, " +
                ContractInsertCodificados.Columnas.SKU_CODE + " TEXT, " +
                ContractInsertCodificados.Columnas.PRESENCIA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertPacks(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertPacks.INSERT_PACKS + " (" +
                ContractInsertPacks2.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertPacks2.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertPacks2.Columnas.CODIGO + " TEXT, " +
                ContractInsertPacks2.Columnas.USUARIO + " TEXT, " +
                ContractInsertPacks2.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertPacks2.Columnas.FECHA + " TEXT, " +
                ContractInsertPacks2.Columnas.HORA + " TEXT, " +
                ContractInsertPacks2.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertPacks2.Columnas.SUBCATEGORIA + " TEXT, " +
                ContractInsertPacks2.Columnas.PRESENTACION + " TEXT, " +
                ContractInsertPacks2.Columnas.BRAND + " TEXT, " +
                ContractInsertPacks2.Columnas.SKU_CODE + " TEXT, " +
                ContractInsertPacks2.Columnas.CATEGORIASEC + " TEXT, " +
                ContractInsertPacks2.Columnas.SUBCATEGORIASEC + " TEXT, " +
                ContractInsertPacks2.Columnas.PRESENTACIONSEC + " TEXT, " +
                ContractInsertPacks2.Columnas.BRANDSEC + " TEXT, " +
                ContractInsertPacks2.Columnas.SKU_CODESEC + " TEXT, " +
                ContractInsertPacks2.Columnas.PVC + " TEXT, " +
                ContractInsertPacks2.Columnas.CANTIDAD_ARMADA + " TEXT, " +
                ContractInsertPacks2.Columnas.CANTIDAD_ENCONTRADA + " TEXT, " +
                ContractInsertPacks2.Columnas.OBSERVACION + " TEXT, " +
                ContractInsertPacks2.Columnas.DESCRIPCION + " TEXT, " +
                ContractInsertPacks2.Columnas.FOTO + " TEXT, " +
                ContractInsertPacks2.Columnas.FOTO_GUIA + " TEXT, " +
                ContractInsertPacks2.Columnas.MANUFACTURER + " TEXT, " +
                ContractInsertPacks2.Columnas.POS_NAME + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }



    private void createTableInsertProdCaducar(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertProdCaducar.INSERT_PROD_CADUCAR + " (" +
                ContractInsertProdCaducar.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertProdCaducar.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertProdCaducar.Columnas.CODIGO + " TEXT, " +
                ContractInsertProdCaducar.Columnas.USUARIO + " TEXT, " +
                ContractInsertProdCaducar.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertProdCaducar.Columnas.FECHA + " TEXT, " +
                ContractInsertProdCaducar.Columnas.HORA + " TEXT, " +
                ContractInsertProdCaducar.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertProdCaducar.Columnas.SUBCATEGORIA + " TEXT, " +
                ContractInsertProdCaducar.Columnas.BRAND + " TEXT, " +
                ContractInsertProdCaducar.Columnas.SKU_CODE + " TEXT, " +
                ContractInsertProdCaducar.Columnas.FECHA_PROD + " TEXT, " +
                ContractInsertProdCaducar.Columnas.CANTIDAD_PROD + " TEXT, " +
                ContractInsertProdCaducar.Columnas.MANUFACTURER + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertSugeridos(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertSugeridos.INSERT_SUGERIDOS + " (" +
                ContractInsertSugeridos.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertSugeridos.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertSugeridos.Columnas.CODIGO + " TEXT, " +
                ContractInsertSugeridos.Columnas.USUARIO + " TEXT, " +
                ContractInsertSugeridos.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertSugeridos.Columnas.FECHA + " TEXT, " +
                ContractInsertSugeridos.Columnas.HORA + " TEXT, " +
                ContractInsertSugeridos.Columnas.LOCAL + " TEXT, " +
                ContractInsertSugeridos.Columnas.CODIGO_FABRIL + " TEXT, " +
                ContractInsertSugeridos.Columnas.VENDEDOR_FABRIL + " TEXT, " +
                ContractInsertSugeridos.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertSugeridos.Columnas.SUBCATEGORIA + " TEXT, " +
                ContractInsertSugeridos.Columnas.BRAND + " TEXT, " +
                ContractInsertSugeridos.Columnas.SKU_CODE + " TEXT, " +
                ContractInsertSugeridos.Columnas.QUIEBRE + " TEXT, " +
                ContractInsertSugeridos.Columnas.UNIDAD_DISPONIBLE + " TEXT, " +
                ContractInsertSugeridos.Columnas.SUGERIDO + " TEXT, " +
                ContractInsertSugeridos.Columnas.CANTIDAD + " TEXT, " +
                ContractInsertSugeridos.Columnas.OBSERVACIONES + " TEXT, " +
                ContractInsertSugeridos.Columnas.ENTREGA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertRotacion(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertRotacion.INSERT_ROTACION + " (" +
                ContractInsertRotacion.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertRotacion.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertRotacion.Columnas.CODIGO + " TEXT, " +
                ContractInsertRotacion.Columnas.USUARIO + " TEXT, " +
                ContractInsertRotacion.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertRotacion.Columnas.FECHA + " TEXT, " +
                ContractInsertRotacion.Columnas.HORA + " TEXT, " +
                ContractInsertRotacion.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertRotacion.Columnas.PRODUCTO + " TEXT, " +
                ContractInsertRotacion.Columnas.PROMOCIONAL + " TEXT, " +
                ContractInsertRotacion.Columnas.MECANICA + " TEXT, " +
                ContractInsertRotacion.Columnas.PESO + " TEXT, " +
                ContractInsertRotacion.Columnas.CANTIDAD + " TEXT, " +
                ContractInsertRotacion.Columnas.FECHA_ROT + " TEXT, " +
                ContractInsertRotacion.Columnas.FOTO_GUIA + " TEXT, " +
                ContractInsertRotacion.Columnas.OBSERVACIONES + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertCanjes(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertCanjes.INSERT_CANJES + " (" +
                ContractInsertCanjes.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertCanjes.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertCanjes.Columnas.CODIGO + " TEXT, " +
                ContractInsertCanjes.Columnas.CANAL + " TEXT, " +
                ContractInsertCanjes.Columnas.NOMBRE_COMERCIAL + " TEXT, " +
                ContractInsertCanjes.Columnas.LOCAL + " TEXT, " +
                ContractInsertCanjes.Columnas.REGION + " TEXT, " +
                ContractInsertCanjes.Columnas.PROVINCIA + " TEXT, " +
                ContractInsertCanjes.Columnas.CIUDAD + " TEXT, " +
                ContractInsertCanjes.Columnas.ZONA + " TEXT, " +
                ContractInsertCanjes.Columnas.DIRECCION + " TEXT, " +
                ContractInsertCanjes.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertCanjes.Columnas.MERCADERISTA + " TEXT, " +
                ContractInsertCanjes.Columnas.USUARIO + " TEXT, " +
                ContractInsertCanjes.Columnas.LATITUD + " TEXT, " +
                ContractInsertCanjes.Columnas.LONGITUD + " TEXT, " +
                ContractInsertCanjes.Columnas.TERRITORIO + " TEXT, " +
                ContractInsertCanjes.Columnas.ZONA_TERRITORIO + " TEXT, " +
                ContractInsertCanjes.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertCanjes.Columnas.SUBCATEGORIA + " TEXT, " +
                ContractInsertCanjes.Columnas.MARCA + " TEXT, " +
                ContractInsertCanjes.Columnas.PRODUCTO + " TEXT, " +
                ContractInsertCanjes.Columnas.TIPO_COMBO + " TEXT, " +
                ContractInsertCanjes.Columnas.MECANICA + " TEXT, " +
                ContractInsertCanjes.Columnas.COMBOS_ARMADOS + " TEXT, " +
                ContractInsertCanjes.Columnas.STOCK + " TEXT, " +
                ContractInsertCanjes.Columnas.PVC_COMBO + " TEXT, " +
                ContractInsertCanjes.Columnas.PVC_UNITARIO + " TEXT, " +
                ContractInsertCanjes.Columnas.VISITA + " TEXT, " +
                ContractInsertCanjes.Columnas.MES + " TEXT, " +
                ContractInsertCanjes.Columnas.OBSERVACIONES + " TEXT, " +
                ContractInsertCanjes.Columnas.FOTO + " TEXT, " +
                ContractInsertCanjes.Columnas.FOTO_GUIA + " TEXT, " +
                ContractInsertCanjes.Columnas.FECHA + " TEXT, " +
                ContractInsertCanjes.Columnas.HORA + " TEXT, " +
                ContractInsertCanjes.Columnas.POS_NAME + " TEXT, " +
                ContractInsertCanjes.Columnas.PLATAFORMA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertMaterialesRecibidos(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertMaterialesRecibidos.INSERT_MATERIALES_RECIBIDOS + " (" +
                ContractInsertMaterialesRecibidos.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertMaterialesRecibidos.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.CODIGO + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.CANAL + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.NOMBRE_COMERCIAL + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.LOCAL + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.REGION + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.PROVINCIA + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.CIUDAD + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.ZONA + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.DIRECCION + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.MERCADERISTA + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.USUARIO + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.LATITUD + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.LONGITUD + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.TERRITORIO + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.ZONA_TERRITORIO + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.ALERTA + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.TIPO + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.MATERIAL + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.CANTIDAD + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.ESTADO_MATERIAL + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.PRIORIDAD + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.OBSERVACIONES + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.FOTO + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.FECHA + " TEXT, " +
                ContractInsertMaterialesRecibidos.Columnas.HORA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertEjecucionMateriales(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertEjecucionMateriales.INSERT_EJECUCION_MATERIALES + " (" +
                ContractInsertEjecucionMateriales.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertEjecucionMateriales.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.CODIGO + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.CANAL + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.NOMBRE_COMERCIAL + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.LOCAL + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.REGION + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.PROVINCIA + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.CIUDAD + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.ZONA + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.DIRECCION + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.MERCADERISTA + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.USUARIO + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.LATITUD + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.LONGITUD + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.TERRITORIO + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.ZONA_TERRITORIO + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.TIPO + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.MATERIAL + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.ESTADO_MATERIAL + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.PRIORIDAD + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.OBSERVACIONES + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.FOTO + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.FECHA + " TEXT, " +
                ContractInsertEjecucionMateriales.Columnas.HORA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertMCIPDV(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertMCIPdv.INSERT_MCI + " (" +
                ContractInsertMCIPdv.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertMCIPdv.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertMCIPdv.Columnas.CODIGO + " TEXT, " +
                ContractInsertMCIPdv.Columnas.CANAL + " TEXT, " +
                ContractInsertMCIPdv.Columnas.NOMBRE_COMERCIAL + " TEXT, " +
                ContractInsertMCIPdv.Columnas.LOCAL + " TEXT, " +
                ContractInsertMCIPdv.Columnas.REGION + " TEXT, " +
                ContractInsertMCIPdv.Columnas.PROVINCIA + " TEXT, " +
                ContractInsertMCIPdv.Columnas.CIUDAD + " TEXT, " +
                ContractInsertMCIPdv.Columnas.ZONA + " TEXT, " +
                ContractInsertMCIPdv.Columnas.DIRECCION + " TEXT, " +
                ContractInsertMCIPdv.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertMCIPdv.Columnas.MERCADERISTA + " TEXT, " +
                ContractInsertMCIPdv.Columnas.USUARIO + " TEXT, " +
                ContractInsertMCIPdv.Columnas.LATITUD + " TEXT, " +
                ContractInsertMCIPdv.Columnas.LONGITUD + " TEXT, " +
                ContractInsertMCIPdv.Columnas.TERRITORIO + " TEXT, " +
                ContractInsertMCIPdv.Columnas.ZONA_TERRITORIO + " TEXT, " +
                ContractInsertMCIPdv.Columnas.CAUSAL + " TEXT, " +
                ContractInsertMCIPdv.Columnas.OBSERVACIONES + " TEXT, " +
                ContractInsertMCIPdv.Columnas.FOTO + " TEXT, " +
                ContractInsertMCIPdv.Columnas.FECHA + " TEXT, " +
                ContractInsertMCIPdv.Columnas.HORA + " TEXT, " +
                ContractInsertMCIPdv.Columnas.POS_NAME + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertConvenios(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertConvenios.INSERT_CONVENIOS + " (" +
                ContractInsertConvenios.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertConvenios.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertConvenios.Columnas.FECHA + " TEXT, " +
                ContractInsertConvenios.Columnas.HORA + " TEXT, " +
                ContractInsertConvenios.Columnas.CODIGO + " TEXT, " +
                ContractInsertConvenios.Columnas.POS_NAME + " TEXT, " +
                ContractInsertConvenios.Columnas.USUARIO + " TEXT, " +
                ContractInsertConvenios.Columnas.LATITUD + " TEXT, " +
                ContractInsertConvenios.Columnas.LONGITUD + " TEXT, " +
                ContractInsertConvenios.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertConvenios.Columnas.FABRICANTE + " TEXT, " +
                ContractInsertConvenios.Columnas.MARCA + " TEXT, " +
                ContractInsertConvenios.Columnas.TIPO_EXHIBICION + " TEXT, " +
                ContractInsertConvenios.Columnas.NUMERO_EXHIBICION + " TEXT, " +
                ContractInsertConvenios.Columnas.CONTRATADA + " TEXT, " +
                ContractInsertConvenios.Columnas.OBSERVACION + " TEXT, " +
                ContractInsertConvenios.Columnas.FOTO + " TEXT, " +
                ContractInsertConvenios.Columnas.MODULO + " TEXT, " +
                ContractInsertConvenios.Columnas.UNIDAD_DE_NEGOCIO + " TEXT, " +
                ContractInsertConvenios.Columnas.ROL + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT ," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }


    private void createTableLog(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractLog.LOG + " (" +
                ContractLog.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractLog.Columnas.USUARIO + " TEXT, " +
                ContractLog.Columnas.FECHA + " TEXT, " +
                ContractLog.Columnas.HORA + " TEXT, " +
                ContractLog.Columnas.ACCION + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertPreciosSesiones(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertPreciosSesion.INSERT_PRECIOS_SESION + " (" +
                ContractInsertPreciosSesion.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertPreciosSesion.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertPreciosSesion.Columnas.CODIGO + " TEXT, " +
                ContractInsertPreciosSesion.Columnas.USUARIO + " TEXT, " +
                ContractInsertPreciosSesion.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertPreciosSesion.Columnas.FECHA + " TEXT, " +
                ContractInsertPreciosSesion.Columnas.HORA + " TEXT, " +
                ContractInsertPreciosSesion.Columnas.SECTOR + " TEXT, " +
                ContractInsertPreciosSesion.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertPreciosSesion.Columnas.SEGMENTO1 + " TEXT, " +
                ContractInsertPreciosSesion.Columnas.SEGMENTO2 + " TEXT, " +
                ContractInsertPreciosSesion.Columnas.BRAND + " TEXT, " +
                ContractInsertPreciosSesion.Columnas.TAMANO + " TEXT, " +
                ContractInsertPreciosSesion.Columnas.CANTIDAD + " TEXT, " +
                ContractInsertPreciosSesion.Columnas.SKU_CODE + " TEXT, " +
                ContractInsertPreciosSesion.Columnas.PREGULAR + " TEXT, " +
                ContractInsertPreciosSesion.Columnas.PPROMOCION + " TEXT, " +
                ContractInsertPreciosSesion.Columnas.MANUFACTURER + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertAgotados(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertAgotados.INSERT_AGOTADOS + " (" +
                ContractInsertAgotados.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                ContractInsertAgotados.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertAgotados.Columnas.CODIGO + " TEXT, " +
                ContractInsertAgotados.Columnas.USUARIO + " TEXT, " +
                ContractInsertAgotados.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertAgotados.Columnas.TIEMPO_INICIO + " TEXT, " +
                ContractInsertAgotados.Columnas.TIEMPO_FIN + " TEXT, " +
                ContractInsertAgotados.Columnas.FECHA + " TEXT, " +
                ContractInsertAgotados.Columnas.HORA + " TEXT, " +

                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertTareas(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertTareas.INSERT_TAREAS + " (" +
                ContractInsertTareas.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                ContractInsertTareas.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertTareas.Columnas.CODIGO + " TEXT, " +
                ContractInsertTareas.Columnas.USUARIO + " TEXT, " +
                ContractInsertTareas.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertTareas.Columnas.FECHA + " TEXT, " +
                ContractInsertTareas.Columnas.HORA + " TEXT, " +
                ContractInsertTareas.Columnas.CHANNEL + " TEXT, " +
                ContractInsertTareas.Columnas.CODIGOPDV + " TEXT, " +
                ContractInsertTareas.Columnas.MERCADERISTA + " TEXT, " +
                ContractInsertTareas.Columnas.TAREAS + " TEXT, " +
                ContractInsertTareas.Columnas.REALIZADO + " TEXT, " +
                ContractInsertTareas.Columnas.FOTO + " TEXT, " +

                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertVenta(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertVenta.INSERT_VENTA + " (" +
                ContractInsertVenta.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertVenta.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertVenta.Columnas.CODIGO + " TEXT, " +
                ContractInsertVenta.Columnas.USUARIO + " TEXT, " +
                ContractInsertVenta.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertVenta.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertVenta.Columnas.SUBCATEGORIA + " TEXT, " +
                ContractInsertVenta.Columnas.SKU + " TEXT, " +
                ContractInsertVenta.Columnas.POS_NAME + " TEXT, " +
                ContractInsertVenta.Columnas.TIPO_FACTURA + " TEXT, " +
                ContractInsertVenta.Columnas.TIPO_VENTA + " TEXT, " +
                ContractInsertVenta.Columnas.TOTAL_FACTURA + " TEXT, " +
                ContractInsertVenta.Columnas.NUMERO_FACTURA + " TEXT, " +
                ContractInsertVenta.Columnas.ENTREGO_PROMOCIONAL + " TEXT, " +
                ContractInsertVenta.Columnas.PROMOCIONAL + " TEXT, " +
                ContractInsertVenta.Columnas.CANT_PROMOCIONAL + " TEXT, " +
                ContractInsertVenta.Columnas.NUM_FACTURA + " TEXT, " +
                ContractInsertVenta.Columnas.MECANICA + " TEXT, " +
                ContractInsertVenta.Columnas.MONTO_FACTURA + " TEXT, " +
                ContractInsertVenta.Columnas.TOTAL_STOCK + " TEXT, " +
                ContractInsertVenta.Columnas.FECHA_VENTA + " TEXT, " +
                ContractInsertVenta.Columnas.KEY_IMAGE +   " TEXT, " +
                ContractInsertVenta.Columnas.FOTO_ADICIONAL + " TEXT, " +
                ContractInsertVenta.Columnas.FOTO_ACTIVIDAD + " TEXT, " +
                ContractInsertVenta.Columnas.COMENTARIO_FACTURA + " TEXT, " +
                ContractInsertVenta.Columnas.COMENTARIO_ADICIONAL + " TEXT, " +
                ContractInsertVenta.Columnas.COMENTARIO_ACTIVIDAD + " TEXT, " +
                ContractInsertVenta.Columnas.FECHA + " TEXT, " +
                ContractInsertVenta.Columnas.HORA + " TEXT, " +

                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertPreguntas(SQLiteDatabase database){
        String cmd = "CREATE TABLE " + ContractInsertPreguntas.INSERT_PREGUNTAS + " (" +
                ContractInsertPreguntas.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertPreguntas.Columnas.USUARIO + " TEXT, " +
                ContractInsertPreguntas.Columnas.TEST_ID + " TEXT, " +
                ContractInsertPreguntas.Columnas.TEST + " TEXT, " +
                ContractInsertPreguntas.Columnas.P1 + " TEXT, " +
                ContractInsertPreguntas.Columnas.P2 + " TEXT, " +
                ContractInsertPreguntas.Columnas.P3 + " TEXT, " +
                ContractInsertPreguntas.Columnas.P4 + " TEXT, " +
                ContractInsertPreguntas.Columnas.P5 + " TEXT, " +
                ContractInsertPreguntas.Columnas.P6 + " TEXT, " +
                ContractInsertPreguntas.Columnas.P7 + " TEXT, " +
                ContractInsertPreguntas.Columnas.P8 + " TEXT, " +
                ContractInsertPreguntas.Columnas.P9 + " TEXT, " +
                ContractInsertPreguntas.Columnas.P10 + " TEXT, " +
                ContractInsertPreguntas.Columnas.P11 + " TEXT, " +
                ContractInsertPreguntas.Columnas.P12 + " TEXT, " +
                ContractInsertPreguntas.Columnas.P13 + " TEXT, " +
                ContractInsertPreguntas.Columnas.P14 + " TEXT, " +
                ContractInsertPreguntas.Columnas.P15 + " TEXT, " +
                ContractInsertPreguntas.Columnas.CORRECTAS + " TEXT, " +
                ContractInsertPreguntas.Columnas.INCORRECTAS + " TEXT, " +
                ContractInsertPreguntas.Columnas.CALIFICACION + " TEXT, " +
                ContractInsertPreguntas.Columnas.OBSERVACION + " TEXT, " +
                ContractInsertPreguntas.Columnas.FECHA + " TEXT, " +
                ContractInsertPreguntas.Columnas.HORA + " TEXT, " +
                ContractInsertPreguntas.Columnas.CRONOMETO + " TEXT, " +
                ContractInsertPreguntas.Columnas.ESTADO_TEST + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertFotografico(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertFotografico.INSERT_FOTOGRAFICO + " (" +
                ContractInsertFotografico.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertFotografico.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertFotografico.Columnas.CODIGO + " TEXT, " +
                ContractInsertFotografico.Columnas.POS_NAME + " TEXT, " +
                ContractInsertFotografico.Columnas.USUARIO + " TEXT, " +
                ContractInsertFotografico.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertFotografico.Columnas.SUBCATEGORIA + " TEXT, " +
                ContractInsertFotografico.Columnas.MARCA + " TEXT, " +
                ContractInsertFotografico.Columnas.LOGRO + " TEXT, " +
                ContractInsertFotografico.Columnas.STATUS + " TEXT, " +
                ContractInsertFotografico.Columnas.OBSERVACION + " TEXT, " +
                ContractInsertFotografico.Columnas.KEY_IMAGE + " TEXT, " +
                ContractInsertFotografico.Columnas.FECHA + " TEXT, " +
                ContractInsertFotografico.Columnas.HORA + " TEXT, " +
                ContractInsertFotografico.Columnas.TIPO_EXH + " TEXT, " +
                ContractInsertFotografico.Columnas.CONTRATADA + " TEXT, " +
                ContractInsertFotografico.Columnas.ID_REMOTA + " TEXT UNIQUE," +
                ContractInsertFotografico.Columnas.ESTADO + " INTEGER NOT NULL DEFAULT "+ ContractInsertFotografico.ESTADO_OK+"," +
                ContractInsertFotografico.Columnas.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertValores(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertValores.INSERT_VALORES + " (" +
                ContractInsertValores.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertValores.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertValores.Columnas.CODIGO + " TEXT, " +
                ContractInsertValores.Columnas.USUARIO + " TEXT, " +
                ContractInsertValores.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertValores.Columnas.FECHA + " TEXT, " +
                ContractInsertValores.Columnas.HORA + " TEXT, " +
                ContractInsertValores.Columnas.SECTOR + " TEXT, " +
                ContractInsertValores.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertValores.Columnas.SEGMENTO1 + " TEXT, " +
                //ContractInsertValores.Columnas.POFERTA + " TEXT, " +
                ContractInsertValores.Columnas.BRAND + " TEXT, " +
                /*ContractInsertValores.Columnas.TAMANO + " TEXT, " +
                ContractInsertValores.Columnas.CANTIDAD + " TEXT, " +*/
                ContractInsertValores.Columnas.SKU_CODE + " TEXT, " +
                ContractInsertValores.Columnas.CODIFICA + " TEXT, " +
                ContractInsertValores.Columnas.AUSENCIA + " TEXT, " +
                ContractInsertValores.Columnas.DISPONIBLE + " TEXT, " +
                ContractInsertValores.Columnas.RESPONSABLE + " TEXT, " +
                ContractInsertValores.Columnas.RAZONES + " TEXT, " +
                ContractInsertValores.Columnas.SUGERIDO + " TEXT, " +
                ContractInsertValores.Columnas.TIPO_SUGERIDO + " TEXT, " +
                ContractInsertValores.Columnas.PVP + " TEXT, " +
                ContractInsertValores.Columnas.PVC + " TEXT, " +
                ContractInsertValores.Columnas.POFERTA + " TEXT, " +
                ContractInsertValores.Columnas.MANUFACTURER + " TEXT, " +
                ContractInsertValores.Columnas.QUIEBRE_PERCHA + " TEXT, " +
                ContractInsertValores.Columnas.QUIEBRE_BODEGA + " TEXT, " +
                ContractInsertValores.Columnas.POS_NAME + " TEXT, " +
                ContractInsertValores.Columnas.PLATAFORMA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertValoresSesiones(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertValoresSesion.INSERT_VALORES_SESION + " (" +
                ContractInsertValoresSesion.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertValoresSesion.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertValoresSesion.Columnas.CODIGO + " TEXT, " +
                ContractInsertValoresSesion.Columnas.USUARIO + " TEXT, " +
                ContractInsertValoresSesion.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertValoresSesion.Columnas.FECHA + " TEXT, " +
                ContractInsertValoresSesion.Columnas.HORA + " TEXT, " +
                ContractInsertValoresSesion.Columnas.SECTOR + " TEXT, " +
                ContractInsertValoresSesion.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertValoresSesion.Columnas.SEGMENTO1 + " TEXT, " +
                ContractInsertValoresSesion.Columnas.SEGMENTO2 + " TEXT, " +
                ContractInsertValoresSesion.Columnas.BRAND + " TEXT, " +
                ContractInsertValoresSesion.Columnas.TAMANO + " TEXT, " +
                ContractInsertValoresSesion.Columnas.CANTIDAD + " TEXT, " +
                ContractInsertValoresSesion.Columnas.SKU_CODE + " TEXT, " +
                ContractInsertValoresSesion.Columnas.CODIFICA + " TEXT, " +
                ContractInsertValoresSesion.Columnas.AUSENCIA + " TEXT, " +
                ContractInsertValoresSesion.Columnas.RESPONSABLE + " TEXT, " +
                ContractInsertValoresSesion.Columnas.RAZONES + " TEXT, " +
                ContractInsertValoresSesion.Columnas.MANUFACTURER + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertShare(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertShare.INSERT_SHARE + " (" +
                ContractInsertShare.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertShare.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertShare.Columnas.CODIGO + " TEXT, " +
                ContractInsertShare.Columnas.USUARIO + " TEXT, " +
                ContractInsertShare.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertShare.Columnas.FECHA + " TEXT, " +
                ContractInsertShare.Columnas.HORA + " TEXT, " +
                ContractInsertShare.Columnas.SECTOR + " TEXT, " +
                ContractInsertShare.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertShare.Columnas.SEGMENTO + " TEXT, " +
                ContractInsertShare.Columnas.MARCA_SELECCIONADA + " TEXT, " +
                ContractInsertShare.Columnas.BRAND  + " TEXT, " +
                ContractInsertShare.Columnas.CTMS_PERCHA + " TEXT, " +
                ContractInsertShare.Columnas.CTMS_MARCA + " TEXT, " +
                ContractInsertShare.Columnas.OTROS + " TEXT, " +
                ContractInsertShare.Columnas.CUMPLIMIENTO + " TEXT, " +
                ContractInsertShare.Columnas.MANUFACTURER + " TEXT, " +
                ContractInsertShare.Columnas.RAZONES + " TEXT, " +
                ContractInsertShare.Columnas.FOTO + " TEXT, " +
                ContractInsertShare.Columnas.POS_NAME + " TEXT, " +
                ContractInsertShare.Columnas.PLATAFORMA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertPDI(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertPDI.INSERT_PDI + " (" +
                ContractInsertPDI.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertPDI.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertPDI.Columnas.CODIGO + " TEXT, " +
                ContractInsertPDI.Columnas.USUARIO + " TEXT, " +
                ContractInsertPDI.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertPDI.Columnas.FECHA + " TEXT, " +
                ContractInsertPDI.Columnas.HORA + " TEXT, " +
                ContractInsertPDI.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertPDI.Columnas.MARCA_SELECCIONADA + " TEXT, " +
                ContractInsertPDI.Columnas.CUMPLIMIENTO + " TEXT, " +
                ContractInsertPDI.Columnas.UNIVERSO + " TEXT, " +
                ContractInsertPDI.Columnas.CARAS + " TEXT, " +
                ContractInsertPDI.Columnas.OTROS + " TEXT, " +
                ContractInsertPDI.Columnas.OBJ_CATEGORIA + " TEXT, " +
                ContractInsertPDI.Columnas.PART_CATEGORIA + " TEXT, " +
                ContractInsertPDI.Columnas.FOTO + " TEXT, " +
                ContractInsertPDI.Columnas.CANAL + " TEXT, " +
                ContractInsertPDI.Columnas.POS_NAME + " TEXT, " +
                ContractInsertPDI.Columnas.PLATAFORMA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertPromocion(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertPromocion.INSERT_PROMO + " (" +
                ContractInsertPromocion.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertPromocion.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertPromocion.Columnas.CODIGO + " TEXT, " +
                ContractInsertPromocion.Columnas.USUARIO + " TEXT, " +
                ContractInsertPromocion.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertPromocion.Columnas.FECHA + " TEXT, " +
                ContractInsertPromocion.Columnas.HORA + " TEXT, " +
                ContractInsertPromocion.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertPromocion.Columnas.SUBCATEGORIA + " TEXT, " +
                ContractInsertPromocion.Columnas.MARCA + " TEXT, " +
                ContractInsertPromocion.Columnas.OTRAS_MARCAS  + " TEXT, " +
                ContractInsertPromocion.Columnas.CANAL  + " TEXT, " +
                ContractInsertPromocion.Columnas.TIPO_PROMOCION  + " TEXT, " +
                ContractInsertPromocion.Columnas.DESCRIPCION_PROMOCION  + " TEXT, " +
                ContractInsertPromocion.Columnas.MECANICA  + " TEXT, " +
                ContractInsertPromocion.Columnas.INI_PROMO + " TEXT, " +
                ContractInsertPromocion.Columnas.FIN_PROMO + " TEXT, " +
                ContractInsertPromocion.Columnas.AGOTAR_STOCK + " TEXT, " +
                ContractInsertPromocion.Columnas.PVC_ANTERIOR + " TEXT, " +
                ContractInsertPromocion.Columnas.PVC_ACTUAL + " TEXT, " +
                ContractInsertPromocion.Columnas.FOTO + " TEXT, " +
                ContractInsertPromocion.Columnas.MANUFACTURER + " TEXT, " +
                ContractInsertPromocion.Columnas.SKU + " TEXT, " +
                ContractInsertPromocion.Columnas.POS_NAME + " TEXT, " +
                ContractInsertPromocion.Columnas.PLATAFORMA + " TEXT, " +
                ContractInsertPromocion.Columnas.CAMPANA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }


    private void createTableInsertFlooring(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + InsertFlooring.INSERT_FLOORING + " (" +
                InsertFlooring.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                InsertFlooring.Columnas.PHARMA_ID + " TEXT, " +
                InsertFlooring.Columnas.CODIGO + " TEXT, " +
                InsertFlooring.Columnas.USUARIO + " TEXT, " +
                InsertFlooring.Columnas.SUPERVISOR + " TEXT, " +
                InsertFlooring.Columnas.FECHA + " TEXT, " +
                InsertFlooring.Columnas.HORA + " TEXT, " +
                InsertFlooring.Columnas.SECTOR + " TEXT, " +
                InsertFlooring.Columnas.CATEGORIA + " TEXT, " +
                InsertFlooring.Columnas.SUBCATEGORIA + " TEXT, " +
                InsertFlooring.Columnas.PRESENTACION + " TEXT, " +
                InsertFlooring.Columnas.BRAND + " TEXT, " +
                InsertFlooring.Columnas.CONTENIDO + " TEXT, " +
                InsertFlooring.Columnas.SKU_CODE + " TEXT, " +
                InsertFlooring.Columnas.INVENTARIOS + " TEXT, " +
                InsertFlooring.Columnas.SEMANA + " TEXT, " +
                InsertFlooring.Columnas.SUGERIDOS + " TEXT, " +
                InsertFlooring.Columnas.TIPO + " TEXT, " +
                InsertFlooring.Columnas.ENTREGA + " TEXT, " +
                InsertFlooring.Columnas.CAUSAL + " TEXT, " +
                InsertFlooring.Columnas.OTROS + " TEXT, " +
                InsertFlooring.Columnas.FECHA_CADUCIDAD + " TEXT, " +
                InsertFlooring.Columnas.POS_NAME + " TEXT, " +
                InsertFlooring.Columnas.PLATAFORMA + " TEXT, " +
                InsertFlooring.Columnas.MOTIVO_SUGERIDO + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertEvidencias(SQLiteDatabase database){
        String cmd = "CREATE TABLE " + ContractInsertEvidencias.INSERT_EVIDENCIAS + " (" +
                ContractInsertEvidencias.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertEvidencias.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertEvidencias.Columnas.CODIGO + " TEXT, " +
                ContractInsertEvidencias.Columnas.POS_NAME + " TEXT, " +
                ContractInsertEvidencias.Columnas.USUARIO + " TEXT, " +
                ContractInsertEvidencias.Columnas.MARCA + " TEXT, " +
                ContractInsertEvidencias.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertEvidencias.Columnas.SUBCATEGORIA + " TEXT, " +
                ContractInsertEvidencias.Columnas.COMENTARIO + " TEXT, " +
                ContractInsertEvidencias.Columnas.FOTO_ANTES + " TEXT, " +
                ContractInsertEvidencias.Columnas.FOTO_DESPUES + " TEXT, " +
                ContractInsertEvidencias.Columnas.FECHA + " TEXT, " +
                ContractInsertEvidencias.Columnas.HORA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertImpulso(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertImpulso.INSERT_IMPUSLO + " (" +
                ContractInsertImpulso.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertImpulso.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertImpulso.Columnas.CODIGO + " TEXT, " +
                ContractInsertImpulso.Columnas.USUARIO + " TEXT, " +
                ContractInsertImpulso.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertImpulso.Columnas.FECHA + " TEXT, " +
                ContractInsertImpulso.Columnas.HORA + " TEXT, " +
                ContractInsertImpulso.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertImpulso.Columnas.BRAND + " TEXT, " +
                ContractInsertImpulso.Columnas.SKU_CODE + " TEXT, " +
                ContractInsertImpulso.Columnas.CANTIDAD_ASIGNADA + " TEXT, " +
                ContractInsertImpulso.Columnas.CANTIDAD_VENDIDA + " TEXT, " +
                ContractInsertImpulso.Columnas.CANTIDAD_ADICIONAL + " TEXT, " +
                ContractInsertImpulso.Columnas.CUMPLIMIENTO + " TEXT, " +
                ContractInsertImpulso.Columnas.IMPULSADORA + " TEXT, " +
                ContractInsertImpulso.Columnas.OBSERVACION + " TEXT, " +
                ContractInsertImpulso.Columnas.FOTO + " TEXT, " +
                ContractInsertImpulso.Columnas.POS_NAME + " TEXT, " +
                ContractInsertImpulso.Columnas.PRECIO_VENTA + " TEXT, " +
                ContractInsertImpulso.Columnas.ALERTA_STOCK + " TEXT, " +
                ContractInsertImpulso.Columnas.PLATAFORMA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertExh(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertExh.INSERT_EXH + " (" +
                ContractInsertExh.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertExh.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertExh.Columnas.CODIGO + " TEXT, " +
                ContractInsertExh.Columnas.USUARIO + " TEXT, " +
                ContractInsertExh.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertExh.Columnas.FECHA + " TEXT, " +
                ContractInsertExh.Columnas.HORA + " TEXT, " +
                ContractInsertExh.Columnas.SECTOR + " TEXT, " +
                ContractInsertExh.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertExh.Columnas.SUBCATEGORIA + " TEXT, " +
                ContractInsertExh.Columnas.SEGMENTO + " TEXT, " +
                ContractInsertExh.Columnas.BRAND + " TEXT, " +
                ContractInsertExh.Columnas.TIPO_EXH + " TEXT, " +
                ContractInsertExh.Columnas.FABRICANTE + " TEXT, " +
                ContractInsertExh.Columnas.ZONA_EX + " TEXT, " +
                ContractInsertExh.Columnas.NIVEL + " TEXT, " +
                ContractInsertExh.Columnas.TIPO + " TEXT, " +
                ContractInsertExh.Columnas.CONTRATADA + " TEXT, " +
                ContractInsertExh.Columnas.CONDICION + " TEXT, " +
                ContractInsertExh.Columnas.FOTO + " TEXT, " +
                ContractInsertExh.Columnas.COMENTARIO_REVISOR + " TEXT, " +
                ContractInsertExh.Columnas.POS_NAME + " TEXT, " +
                ContractInsertExh.Columnas.PLATAFORMA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertExhSesiones(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertExhSesion.INSERT_EXH_SESION + " (" +
                ContractInsertExhSesion.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                ContractInsertExhSesion.Columnas.PHARMA_ID + " TEXT, " +
                ContractInsertExhSesion.Columnas.CODIGO + " TEXT, " +
                ContractInsertExhSesion.Columnas.USUARIO + " TEXT, " +
                ContractInsertExhSesion.Columnas.SUPERVISOR + " TEXT, " +
                ContractInsertExhSesion.Columnas.FECHA + " TEXT, " +
                ContractInsertExhSesion.Columnas.HORA + " TEXT, " +
                ContractInsertExhSesion.Columnas.SECTOR + " TEXT, " +
                ContractInsertExhSesion.Columnas.CATEGORIA + " TEXT, " +
                ContractInsertExhSesion.Columnas.BRAND + " TEXT, " +
                ContractInsertExhSesion.Columnas.TIPO_EXH + " TEXT, " +
                ContractInsertExhSesion.Columnas.ZONA_EX + " TEXT, " +
                ContractInsertExhSesion.Columnas.CONTRATADA + " TEXT, " +
                ContractInsertExhSesion.Columnas.CONDICION + " TEXT, " +
                ContractInsertExhSesion.Columnas.FOTO + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertPDV(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertPdv.INSERT_PDV + " (" +
                ContractInsertPdv.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertPdv.Columnas.IDPDV + " TEXT, " +
                ContractInsertPdv.Columnas.ESTADOVISITA + " TEXT, " +
                ContractInsertPdv.Columnas.NOVEDADES + " TEXT, " +
                ContractInsertPdv.Columnas.FOTO + " TEXT, " +
                ContractInsertPdv.Columnas.FECHA + " TEXT, " +
                ContractInsertPdv.Columnas.HORA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertImplementacion(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertImplementacion.INSERT_IMPLEM + " (" +
                ContractInsertImplementacion.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                ContractInsertImplementacion.Columnas.USUARIO + " TEXT, " +
                ContractInsertImplementacion.Columnas.FECHA + " TEXT, " +
                ContractInsertImplementacion.Columnas.HORA + " TEXT, " +
                ContractInsertImplementacion.Columnas.CIUDAD + " TEXT, " +
                ContractInsertImplementacion.Columnas.CANAL + " TEXT, " +
                ContractInsertImplementacion.Columnas.CLIENTE + " TEXT, " +
                ContractInsertImplementacion.Columnas.FORMATO + " TEXT, " +
                ContractInsertImplementacion.Columnas.ZONA + " TEXT, " +
                ContractInsertImplementacion.Columnas.PDV + " TEXT, " +
                ContractInsertImplementacion.Columnas.DIRECCION + " TEXT, " +
                ContractInsertImplementacion.Columnas.LOCAL + " TEXT, " +
                ContractInsertImplementacion.Columnas.LATITUD + " TEXT, " +
                ContractInsertImplementacion.Columnas.LONGITUD + " TEXT, " +
                ContractInsertImplementacion.Columnas.FOTO + " TEXT, " +

                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }



    private void createTableInsertGps(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertGps.INSERT_GPS + " (" +
                ContractInsertGps.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertGps.Columnas.IDPDV + " TEXT, " +
                ContractInsertGps.Columnas.USUARIO + " TEXT, " +
                ContractInsertGps.Columnas.TIPO + " TEXT, " +
                ContractInsertGps.Columnas.VERSION + " TEXT, " +
                ContractInsertGps.Columnas.LATITUDE + " TEXT, " +
                ContractInsertGps.Columnas.LONGITUDE + " TEXT, " +
                ContractInsertGps.Columnas.FECHA + " TEXT, " +
                ContractInsertGps.Columnas.HORA + " TEXT, " +
                ContractInsertGps.Columnas.CAUSAL + " TEXT, " +
                ContractInsertGps.Columnas.FOTO + " TEXT, " +
                ContractInsertGps.Columnas.DISTANCIA + " TEXT, " +
                ContractInsertGps.Columnas.TIPO_RELEVO + " TEXT, " +
                ContractInsertGps.Columnas.POS_NAME + " TEXT, " +
                Constantes.ID_REMOTA_RUTA + " TEXT," +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertRastreo(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertRastreo.INSERT_GEO + " (" +
                ContractInsertRastreo.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertRastreo.Columnas.USUARIO + " TEXT, " +
                ContractInsertRastreo.Columnas.LATITUD + " TEXT, " +
                ContractInsertRastreo.Columnas.LONGITUD + " TEXT, " +
                ContractInsertRastreo.Columnas.FECHA + " TEXT, " +
                ContractInsertRastreo.Columnas.HORA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableNotificacion(SQLiteDatabase database){
        String cmd = "CREATE TABLE " + ContractNotificacion.NOTIFICACION + " (" +
                ContractNotificacion.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractNotificacion.Columnas.USER + " TEXT, " +
                ContractNotificacion.Columnas.SUPERVISOR + " TEXT, " +
                ContractNotificacion.Columnas.DESCRIPCION + " TEXT, " +
                ContractNotificacion.Columnas.FECHA + " TEXT, " +
                ContractNotificacion.Columnas.HORA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    private void createTableInsertInicial(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractInsertInicial.INSERT_INICIAL + " (" +
                ContractInsertInicial.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractInsertInicial.Columnas.IDPDV + " TEXT, " +
                ContractInsertInicial.Columnas.CODIGO + " TEXT, " +
                ContractInsertInicial.Columnas.TIPO + " TEXT, " +
                ContractInsertInicial.Columnas.DEALER + " TEXT, " +
                ContractInsertInicial.Columnas.UBICACION + " TEXT, " +
                ContractInsertInicial.Columnas.CORREO + " TEXT, " +
                ContractInsertInicial.Columnas.LATITUD + " TEXT, " +
                ContractInsertInicial.Columnas.LONGITUD + " TEXT, " +
                ContractInsertInicial.Columnas.FECHA + " TEXT, " +
                ContractInsertInicial.Columnas.HORA + " TEXT, " +
                Constantes.ID_REMOTA + " TEXT UNIQUE," +
                Constantes.ESTADO + " INTEGER NOT NULL DEFAULT "+ Constantes.ESTADO_OK+"," +
                Constantes.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

   // Modificaciones

   /* private static final String DATABASE_ALTER_PDV_1 = "ALTER TABLE "
            + ContractPrecios.PRECIOS + " ADD COLUMN " + ContractPrecios.Columnas.TARGET + " TEXT;";*/

    /*
      **  OPERACIONES
     */

//    public Boolean verificarLogin(String name, String pass) {
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + ContractPharmaValue.POS + " WHERE "+
//                ContractPharmaValue.Columnas.USER + "=? AND " +
//                ContractPharmaValue.Columnas.PASS + "=?";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{name, pass});
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            return true;
//        }
//        // closing connection
//        cursor.close();
//        db.close();
//        // returning lables
//        return false;
//    }

    public Boolean verificarLogin(String name){

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractUser.TABLE_USER + " WHERE "+
                ContractUser.Columnas.USER + "=?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{name});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            return true;
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return false;
    }

    /*
     *  PUNTOVENTA
     */

    public String getIdPdv(String re, String sucursal, String user) {
        String id = "";
        /*String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO + " WHERE " +
              ContractPortafolioProductos.Columnas.PDV + "=? AND "+
                ContractPortafolioProductos.Columnas.POS_ID + "=? AND "+
                ContractPortafolioProductos.Columnas.USER + "=?";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{re, sucursal, user});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(Constantes.ID_REMOTA));
        }

        cursor.close();
        db.close();*/
        return id;
    }


    public String getIdPdv(String codigo) {
        String id = "";
        String query = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE " +
                ContractPharmaValue.Columnas.POS_ID +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{codigo});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(Constantes.ID_REMOTA));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getChannelSegmentPdv2(String codigo) {
        String id = "";
        String query = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE " +
                ContractPharmaValue.Columnas.POS_ID +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{codigo});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CHANNEL_SEGMENT));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getCityPdv(String codigo) {
        String id = "";
        String query = "SELECT " + ContractPharmaValue.Columnas.CIUDAD + " FROM " + ContractPharmaValue.POS + " WHERE " +
                ContractPharmaValue.Columnas.POS_ID +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{codigo});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CIUDAD));
        }

        cursor.close();
        db.close();
        return id;
    }


    public String getChannelSegmentPdv(String codigo) {
        String id = "";
        String query = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE " +
                ContractPharmaValue.Columnas.POS_ID +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{codigo});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CHANNEL));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getChannelSegmentPreguntas(String usuario) {
        String id = "";
        String query = "SELECT " + ContractPharmaValue.Columnas.CHANNEL + " FROM " + ContractPharmaValue.POS +
                " INNER JOIN " + ContractPreguntas.TABLE_QUEST +
                " ON " + ContractPreguntas.Columnas.KEY_CANAL + "=" + ContractPharmaValue.Columnas.CHANNEL +
                " WHERE " + ContractPharmaValue.Columnas.USER +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{usuario});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CHANNEL));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getZonePdv(String codigo) {
        String id = "";
        String query = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE " +
                ContractPharmaValue.Columnas.POS_ID +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{codigo});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.ZONA));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getSubcanalPdv(String codigo) {
        String id = "";
        String query = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE " +
                ContractPharmaValue.Columnas.POS_ID +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{codigo});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.SUBCHANNEL));
        }

        cursor.close();
        db.close();
        return id;
    }
//funcion para el  amnejo de modulos por roles mpin nuevo
public ArrayList<String> getModulosPermitidosPorRol(String rol) {
    ArrayList<String> modulosPermitidos = new ArrayList<>();
    Cursor cursor = null;

    try {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + ContractModulosRoles.Columnas.MODULO +
                " FROM " + ContractModulosRoles.MODULO_ROLES +
                " WHERE " + ContractModulosRoles.Columnas.ROL + "=?";

        cursor = db.rawQuery(selectQuery, new String[]{rol});

        if (cursor.moveToFirst()) {
            do {
                String moduloString = cursor.getString(0);
                if (moduloString != null && !moduloString.isEmpty()) {
                    // Si hay múltiples módulos separados por comas
                    if (moduloString.contains(",")) {
                        String[] modulosArray = moduloString.split(",");
                        for (String modulo : modulosArray) {
                            String moduloTrimmed = modulo.trim();
                            if (!moduloTrimmed.isEmpty()) {
                                modulosPermitidos.add(moduloTrimmed);
                            }
                        }
                    } else {
                        modulosPermitidos.add(moduloString.trim());
                    }
                }
            } while (cursor.moveToNext());
        }
    } catch (Exception e) {
        Log.e("DatabaseHelper", "Error obteniendo módulos por rol: " + e.getMessage());
    } finally {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    return modulosPermitidos;
}
    public String getRolNuevo(String rol) {
        String id = "";
        String query = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE " +
                ContractPharmaValue.Columnas.POS_ID +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{rol});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.ROL));
        }

        cursor.close();
        db.close();
        return id;
    }
    public String getCiudad(String codigo) {
        String id = "";
        String query = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE " +
                ContractPharmaValue.Columnas.POS_ID +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{codigo});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CIUDAD));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getFormatPdv(String codigo) {
        String id = "";
        String query = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE " +
                ContractPharmaValue.Columnas.POS_ID +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{codigo});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CHANNEL));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getDireccionPdv(String codigo) {
        String id = "";
        String query = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE " +
                ContractPharmaValue.Columnas.POS_ID +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{codigo});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.DIRECCION));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getPosNamePdv(String codigo) {
        String id = "";
        String query = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE " +
                ContractPharmaValue.Columnas.POS_ID +" =? ";
        db = this.getReadableDatabase();
        Log.i("CODIGO s", codigo);
        Cursor cursor =  db.rawQuery(query, new String[]{codigo});
        Log.i("cursor", String.valueOf(cursor));
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_NAME));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getLinkOnedrive(String modulo) {
        String id = "";
        String query = "SELECT DISTINCT "+ ContractLinkOnedrive.Columnas.LINK +" FROM " + ContractLinkOnedrive.LINK_ONDRIVE + " WHERE " +
                ContractLinkOnedrive.Columnas.MODULO +" LIKE ? ";
        db = this.getReadableDatabase();
        Log.i("CODIGO s", modulo);
        Cursor cursor =  db.rawQuery(query, new String[]{"%"+modulo+"%"});
        Log.i("cursor", String.valueOf(cursor));
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractLinkOnedrive.Columnas.LINK));
        }

        cursor.close();
        db.close();
        return id;
    }








    public List<String> getMarcaPacks2(String fabricante, String categoria){
        List<String> operadores = new ArrayList<String>();
        operadores.add("Seleccione");
        Cursor cursor;
        String selectQueryTodos = "", selectQuery = "";
        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                ContractPortafolioProductos.Columnas.SECTOR +"=? AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?" +
                " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{categoria,"%" + fabricante + "%"});


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return operadores;
    }

    public List<String> getProducto3Packs(String fabricante, String categoria, String marca){
        List<String> operadores = new ArrayList<String>();

        Log.i("FABRICANTE", fabricante);
        Log.i("CATEGORIA", categoria);
        //   Log.i("SUBCATEGORIA", subcategoria);
        Log.i("MARCA", marca);

        Cursor cursor;
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ? AND " +
                ContractPortafolioProductos.Columnas.SECTOR +"=? AND " +
                ContractPortafolioProductos.Columnas.MARCA +"=?" +
                " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, marca});
        //}

        // Select All Query
        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaPacksSec(){
        List<String> operadores = new ArrayList<String>();
        //format = "Cruz Azul";

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractProductosSecundarios.Columnas.SECTOR + " FROM " + ContractProductosSecundarios.PRODUCTOS_SECUNDARIOS +
                " ORDER BY " + ContractProductosSecundarios.Columnas.SECTOR;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractProductosSecundarios.Columnas.SECTOR)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getMarcaPacksSec2(String categoria){
        List<String> operadores = new ArrayList<String>();
        operadores.add("Seleccione");
        Log.i("OPERADORES MARCA SEC ", categoria);
       // if(!categoria.equalsIgnoreCase("OTROS")) {
            Cursor cursor;
            String selectQueryTodos = "", selectQuery = "";

            selectQuery = "SELECT DISTINCT " + ContractProductosSecundarios.Columnas.MARCA + " FROM " + ContractProductosSecundarios.PRODUCTOS_SECUNDARIOS + " WHERE " +
                    ContractProductosSecundarios.Columnas.SECTOR + "= ? " +
                    " ORDER BY " + ContractProductosSecundarios.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractProductosSecundarios.Columnas.MARCA)));
                } while (cursor.moveToNext());
            }
            // closing connection
            cursor.close();
            db.close();

//        } else {
//            operadores.add("Otros");
//        }

        Log.i("OPERADORES DESPUES", String.valueOf(operadores));

        return operadores;
    }

    public String getManufacturerPrecios3(String categoria, String brand, String descripcion){
        String manufacturer = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.FABRICANTE + " FROM " +
                ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                ContractPortafolioProductos.Columnas.SECTOR +"=? AND " +
                ContractPortafolioProductos.Columnas.MARCA +"=? AND " +
                ContractPortafolioProductos.Columnas.SKU +"=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{categoria,brand,descripcion});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                manufacturer = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FABRICANTE));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return manufacturer;
    }

    public String getPresentacionPacks2(String sku){
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if(cursor != null && cursor.moveToFirst()){
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PRESENTACION));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getPresentacionPacksSec(String sku){
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if(cursor != null && cursor.moveToFirst()){
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PRESENTACION));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getSubcatPacks(String sku){
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if(cursor != null && cursor.moveToFirst()){
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SUBCATEGORIA));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getSubcatPacksSec(String sku){
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if(cursor != null && cursor.moveToFirst()){
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SUBCATEGORIA));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getChannelPdv(String codigo) {
        String id = "";
        String query = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE " +
                ContractPharmaValue.Columnas.POS_ID +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{codigo});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CHANNEL));
        }

        cursor.close();
        db.close();
        return id;
    }

    public List<String> getProducto3PacksSec(String categoria, String marca){
        List<String> operadores = new ArrayList<String>();
        operadores.add("Seleccione");

        Log.i("CATEGORIA", categoria);
        Log.i("MARCA", marca);

        //if (!marca.equalsIgnoreCase("OTROS") && !categoria.equalsIgnoreCase("OTROS")) {
            Cursor cursor;
            String selectQuery = "SELECT DISTINCT " + ContractProductosSecundarios.Columnas.SKU + " FROM " + ContractProductosSecundarios.PRODUCTOS_SECUNDARIOS + " WHERE " +
                    ContractProductosSecundarios.Columnas.SECTOR + "=? AND " +
                    ContractProductosSecundarios.Columnas.MARCA + "=?" +
                    " ORDER BY " + ContractProductosSecundarios.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, marca});
            //}

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractProductosSecundarios.Columnas.SKU)));
                } while (cursor.moveToNext());
            }
            // closing connection
            cursor.close();
            db.close();
//        } else {
//            operadores.add("Otros");
//        }
        // returning lables
        return operadores;
    }

    public String getSegment1Valores(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SEGMENTO));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getPlataformaBySku(String sku) {
        String id = "";
        String query = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.PLATAFORMA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PLATAFORMA));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getPlataformaBySkuCanjes(String sku) {
        String id = "";
        String query = "SELECT DISTINCT " + ContractRotacion.Columnas.PLATAFORMA + " FROM " + ContractRotacion.ROTACION + " WHERE " +
                ContractRotacion.Columnas.PRODUCTO +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractRotacion.Columnas.PLATAFORMA));
        }

        cursor.close();
        db.close();
        return id;
    }


    public String getPlataformaByMarcaExh(String categoria, String marca) {
        String id = "";
        String query = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.PLATAFORMA +
                " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SECTOR +" =? AND " +
                ContractPortafolioProductos.Columnas.MARCA +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{categoria, marca});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PLATAFORMA));
        }

        cursor.close();
        db.close();
        return id;
    }




    public String getPlataformaByMarca(String categoria, String subcategoria, String marca) {
        String id = "";
        Cursor cursor = null;

        if (categoria.equalsIgnoreCase("Pastas") && subcategoria.equalsIgnoreCase("Todos")){

            String query = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.PLATAFORMA +
                    " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR +" =? AND " +
                    ContractPortafolioProductos.Columnas.MARCA +" =? ";
            db = this.getReadableDatabase();
            cursor =  db.rawQuery(query, new String[]{categoria ,marca});

        }else{
            String query = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.PLATAFORMA +
                    " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR +" =? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY +" =? AND " +
                    ContractPortafolioProductos.Columnas.MARCA +" =? ";
            db = this.getReadableDatabase();
            cursor =  db.rawQuery(query, new String[]{categoria ,subcategoria,marca});
        }




        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PLATAFORMA));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getPlataformaByMarcaPDI(String categoria, String marca) {
        String id = "";
        String query = "SELECT " + ContractPDI.Columnas.PLATAFORMA + ", " +
                ContractPDI.Columnas.MARCA + " || ' ' || " + ContractPDI.Columnas.SUBCATEGORIA + " AS " + ContractPDI.Columnas.MARCA_FULL +
                " FROM " + ContractPDI.PDI + " WHERE " +
                ContractPDI.Columnas.CATEGORIA + "=? AND " +
                ContractPDI.Columnas.MARCA_FULL + "=? " +
                " GROUP BY " + ContractPDI.Columnas.PLATAFORMA;
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{categoria, marca});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPDI.Columnas.PLATAFORMA));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getSegment1Sugeridos(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SEGMENTO));
        }

        cursor.close();
        db.close();
        return id;
    }


    public void eliminarExh(String codigo) {
        String query = "DELETE FROM " + ContractInsertExh.INSERT_EXH + " WHERE " +
                Constantes.PENDIENTE_INSERCION +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{codigo});
        db.execSQL(query);
        cursor.close();
        db.close();
    }

    public String getSegment2Valores(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SEGMENTO));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getTamanoValores(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA));
        }

        cursor.close();
        db.close();
        return id;
    }



    public String getCantidadValores(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FABRICANTE));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getSubcategoryPrecios(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SUBCATEGORIA));
        }

        cursor.close();
        db.close();
        return id;
    }


    public String getPresentacionCodificados(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PRESENTACION));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getVarianteCodificados(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.VARIANTE1));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getContenidoCodificados(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CONTENIDO));
        }

        cursor.close();
        db.close();
        return id;
    }


    public String getSubcategoryCodificados(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SUBCATEGORIA));
        }

        cursor.close();
        db.close();
        return id;
    }


    public String getFabricantePrecios(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PRESENTACION));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getContenidoPrecios(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CONTENIDO));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getSectorPrecios(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getCantidadPrecios(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FABRICANTE));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getSegment1Flooring(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SUBCATEGORIA));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getSegment2Flooring(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SEGMENTO));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getTamanoFlooring(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getCantidadFlooring(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FABRICANTE));
        }

        cursor.close();
        db.close();
        return id;
    }

//    public ArrayList<String> getCategoriasConvenios(String codigo){
//        ArrayList<String> operadores = new ArrayList<String>();
//        List<String> like_cuentas = new ArrayList<String>();
//
//        // Select All Query
//        Cursor cursor;
//        String selectQuery = "SELECT DISTINCT "+ ContractConvenios.Columnas.CATEGORIA + " FROM " + ContractConvenios.CONVENIOS + " WHERE " +
//                ContractConvenios.Columnas.CODIGO + " =? ";
//
//            Log.i("KKK","entro"+ selectQuery);
//
//        String[] selectionArgs = like_cuentas.toArray(new String[]{codigo});
//
//        db = this.getReadableDatabase();
//        cursor = db.rawQuery(selectQuery, new String[]{codigo});
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractConvenios.Columnas.CATEGORIA)));
//            } while (cursor.moveToNext() && cursor!=null);
//        }
//        // closing connection
//        cursor.close();
//        db.close();
//        if (operadores.size() == 0){
//            operadores.add("");
//        }
//        // returning lables
//        return operadores;
//    }
//Modificacion de la opcion "Seleccione" para el spinner de obtener categorias
    public ArrayList<String> getCategoriasConvenios(String codigo){
        ArrayList<String> operadores = new ArrayList<String>();
        List<String> like_cuentas = new ArrayList<String>();

        operadores.add("Seleccione");

        // Select All Query
        Cursor cursor;
        String selectQuery = "SELECT DISTINCT "+ ContractConvenios.Columnas.CATEGORIA + " FROM " + ContractConvenios.CONVENIOS + " WHERE " +
                ContractConvenios.Columnas.CODIGO + " =? ";

        Log.i("KKK","entro"+ selectQuery);

        String[] selectionArgs = like_cuentas.toArray(new String[]{codigo});

        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{codigo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractConvenios.Columnas.CATEGORIA)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        // closing connection
        cursor.close();
        db.close();
        if (operadores.size() == 0){
            operadores.add("");
        }
        // returning lables
        return operadores;
    }



    public ArrayList<String> getSubcategoriasConvenios(String codigo, String categoria){
        ArrayList<String> operadores = new ArrayList<String>();
        List<String> like_cuentas = new ArrayList<String>();
         operadores.add("Seleccione");

        // Select All Query
        Cursor cursor;
        String selectQuery = "SELECT DISTINCT "+ ContractConvenios.Columnas.UNIDAD_NEGOCIO + " FROM " + ContractConvenios.CONVENIOS + " WHERE " +
                ContractConvenios.Columnas.CODIGO + " =? AND "+
                ContractConvenios.Columnas.CATEGORIA +" =?;";



        String[] selectionArgs = like_cuentas.toArray(new String[]{codigo});

        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{codigo, categoria});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractConvenios.Columnas.UNIDAD_NEGOCIO)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        // closing connection
        cursor.close();
        db.close();
        if (operadores.size() == 0){
            operadores.add("");
        }
        // returning lables
        return operadores;
    }

    public ArrayList<String> getMarcasConvenios(String codigo, String categoria, String subcategoria){
        ArrayList<String> operadores = new ArrayList<String>();
        List<String> like_cuentas = new ArrayList<String>();

        operadores.add("Seleccione");

        // Select All Query
        Cursor cursor;
        String selectQuery = "SELECT DISTINCT "+ ContractConvenios.Columnas.MARCA + " FROM " + ContractConvenios.CONVENIOS + " WHERE " +
                ContractConvenios.Columnas.CODIGO + " =? AND "+
                ContractConvenios.Columnas.CATEGORIA + " =? AND "+
                ContractConvenios.Columnas.UNIDAD_NEGOCIO +" =?;";


        String[] selectionArgs = like_cuentas.toArray(new String[]{codigo});

        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{codigo, categoria, subcategoria});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractConvenios.Columnas.MARCA)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        // closing connection
        cursor.close();
        db.close();
        if (operadores.size() == 0){
            operadores.add("");
        }
        // returning lables
        return operadores;
    }

    public ArrayList<Base_convenios> filtrarListExhibiciones(String codigo_pdv, String categoria, String subcategoria, String marca) {
        ArrayList<Base_convenios> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = null;

        Log.i("Categoria c", categoria);
        Log.i("PDV5", codigo_pdv);

        selectQuery = "SELECT * FROM " + ContractConvenios.CONVENIOS + " WHERE " +
                ContractConvenios.Columnas.CODIGO + "=? AND " +
                ContractConvenios.Columnas.CATEGORIA + "=? AND " +
                ContractConvenios.Columnas.UNIDAD_NEGOCIO + "=? AND " +
                ContractConvenios.Columnas.MARCA + "=? ";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{codigo_pdv, categoria, subcategoria, marca});


        if (cursor.moveToFirst()) {
            do {
                Base_convenios p = new Base_convenios();
                p.setTipo_exhibicion(cursor.getString(cursor.getColumnIndexOrThrow(ContractConvenios.Columnas.TIPO_EXHIBICION)));
                p.setMarca(cursor.getString(cursor.getColumnIndexOrThrow(ContractConvenios.Columnas.MARCA)));
                p.setNumero_exhibicion(cursor.getString(cursor.getColumnIndexOrThrow(ContractConvenios.Columnas.NUMERO_EXHIBICION)));
                operadores.add(p);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }



    public String getManufacturerShare(String brand) {
        String manufacturer = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.FABRICANTE + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                ContractPortafolioProductos.Columnas.MARCA + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{brand});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                manufacturer = (cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FABRICANTE)));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return manufacturer;
    }

    public String getManufacturerOSA(String sector, String categoria, String brand) {
        String manufacturer = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.FABRICANTE + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                ContractPortafolioProductos.Columnas.CONTENIDO + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{sector,categoria,brand});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                manufacturer = (cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FABRICANTE)));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return manufacturer;
    }

    public List<String> getCodigo(String user) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractPharmaValue.Columnas.POS_ID + " FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.USER +"=? ORDER BY " + ContractPharmaValue.Columnas.POS_ID + " ASC " ;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_ID)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


    public ArrayList<Base_tipo_exh> getTipoExhByChannel(String canal) {
        ArrayList<Base_tipo_exh> operadores = new ArrayList<Base_tipo_exh>();

        // Select All Query
        String selectQuery = "SELECT * " + " FROM " + ContractTipoExh.TABLE_NAME + " WHERE "+
                ContractTipoExh.Columnas.CANAL +"=? ORDER BY " + ContractTipoExh.Columnas.EXHIBICION + " ASC " ;
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, new String[]{canal});

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                Base_tipo_exh tipo_exh = new Base_tipo_exh();
                tipo_exh.setCanal(c.getString(c.getColumnIndexOrThrow(ContractTipoExh.Columnas.CANAL)));
                tipo_exh.setExhibicion(c.getString(c.getColumnIndexOrThrow(ContractTipoExh.Columnas.EXHIBICION)));
                tipo_exh.setFoto(c.getString(c.getColumnIndexOrThrow(ContractTipoExh.Columnas.FOTO)));
                operadores.add(tipo_exh);
            } while (c.moveToNext());
        }
        // closing connection
        c.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<String> getFabricantesDistinct() {
        ArrayList<String> fabricantes = new ArrayList<String>();

        // Query para obtener fabricantes distintos donde sea igual a "BASSA"
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.FABRICANTE +
                " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = 'BASSA'" +
                " ORDER BY " + ContractPortafolioProductos.Columnas.FABRICANTE + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // Agregar opción por defecto
        fabricantes.add("Seleccione");

        // Recorrer resultados y agregar a la lista
        if (c.moveToFirst()) {
            do {
                String fabricante = c.getString(c.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FABRICANTE));
                if (fabricante != null && !fabricante.trim().isEmpty()) {
                    fabricantes.add(fabricante);
                }
            } while (c.moveToNext());
        }

        // Cerrar conexiones
        c.close();
        db.close();

        return fabricantes;
    }
    public ArrayList<String> getFabricantesHistorial(String pdv) {
        ArrayList<String> fabricantes = new ArrayList<String>();

        // Query para obtener fabricantes distintos ordenados alfabéticamente
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.FABRICANTE +
                " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " IS NOT NULL AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE + " != ''" +
                " ORDER BY " + ContractPortafolioProductos.Columnas.FABRICANTE + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // Agregar opción por defecto
        fabricantes.add("Seleccione");

        // Recorrer resultados y agregar a la lista
        if (c.moveToFirst()) {
            do {
                String fabricante = c.getString(c.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FABRICANTE));
                if (fabricante != null && !fabricante.trim().isEmpty()) {
                    fabricantes.add(fabricante);
                }
            } while (c.moveToNext());
        }

        // Cerrar conexiones
        c.close();
        db.close();

        return fabricantes;
    }



    public ArrayList<BasePharmaValue> consultarBasePharmaValue(String user, String por_codigo, String descripcion_codigo, String por_nombre, String descripcion_nombre) {
        ArrayList<BasePharmaValue> operadores = new ArrayList<BasePharmaValue>();

        String selectQuery = "";

        if (por_codigo.equals("TODOS") || por_nombre.equals("TODOS")) {
            selectQuery = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE "+
                    ContractPharmaValue.Columnas.USER +"=? ORDER BY " + ContractPharmaValue.Columnas.POS_ID + " ASC " ;
        }else{
            selectQuery = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE "+
                    ContractPharmaValue.Columnas.USER +"=? AND " +
                    por_codigo +" LIKE '%" + descripcion_codigo + "%' AND " +
                    por_nombre +" LIKE '%" + descripcion_nombre + "%'" +
                    " ORDER BY " + ContractPharmaValue.Columnas.POS_ID + " ASC " ;
        }
        // Select All Query

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePharmaValue bpv = new BasePharmaValue();
                bpv.setId(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.ID_REMOTA)));
                bpv.setPos_id(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_ID)));
                bpv.setPos_name(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_NAME)));
                bpv.setChannel(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CHANNEL)));
                bpv.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.DIRECCION)));
                bpv.setCity(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CIUDAD)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BasePharmaValue> consultarBasePharmaValue2(String user, String text, String fecha) {
        ArrayList<BasePharmaValue> operadores = new ArrayList<BasePharmaValue>();

        String selectQuery = "";

        if (text.trim().isEmpty()) {
            selectQuery = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE "+
                    ContractPharmaValue.Columnas.USER +"=? AND " +
                    ContractPharmaValue.Columnas.FECHA_VISITA +"=? " +
                    " ORDER BY " + ContractPharmaValue.Columnas.POS_ID + " ASC " ;
        }else{
            selectQuery = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE "+
                    ContractPharmaValue.Columnas.USER +"=? AND " +
                    ContractPharmaValue.Columnas.FECHA_VISITA +"=? AND (" +
                    ContractPharmaValue.Columnas.POS_ID +" LIKE '%" + text + "%' OR " +
                    ContractPharmaValue.Columnas.POS_NAME +" LIKE '%" + text + "%' OR " +
                    ContractPharmaValue.Columnas.CHANNEL +" LIKE '%" + text + "%' OR " +
                    ContractPharmaValue.Columnas.DIRECCION +" LIKE '%" + text + "%' OR " +
                    ContractPharmaValue.Columnas.CIUDAD +" LIKE '%" + text + "%'" +
                    ") ORDER BY " + ContractPharmaValue.Columnas.POS_ID + " ASC " ;
        }
        // Select All Query
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user, fecha});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePharmaValue bpv = new BasePharmaValue();
                bpv.setId(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.ID_REMOTA)));
                bpv.setPos_id(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_ID)));
                bpv.setPos_name(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_NAME)));
                bpv.setChannel(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CHANNEL)));
                bpv.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.DIRECCION)));
                bpv.setCity(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CIUDAD)));
                bpv.setTermometro(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.TERMOMETRO)));
                bpv.setModulo(Constantes.MODULO_PUNTOS_PRINCIPAL);
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BasePharmaValue> consultarBasePharmaValueTardio(String user, String text) {
        ArrayList<BasePharmaValue> operadores = new ArrayList<BasePharmaValue>();

        String selectQuery = "";

        if (text.trim().isEmpty()) {
            selectQuery = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE "+
                    ContractPharmaValue.Columnas.USER +"=? " +
                    " GROUP BY " + ContractPharmaValue.Columnas.POS_ID +
                    " ORDER BY " + ContractPharmaValue.Columnas.POS_ID + " ASC " ;
        }else{
            selectQuery = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE "+
                    ContractPharmaValue.Columnas.USER +"=? AND (" +
                    ContractPharmaValue.Columnas.POS_ID +" LIKE '%" + text + "%' OR " +
                    ContractPharmaValue.Columnas.POS_NAME +" LIKE '%" + text + "%' OR " +
                    ContractPharmaValue.Columnas.CHANNEL +" LIKE '%" + text + "%' OR " +
                    ContractPharmaValue.Columnas.DIRECCION +" LIKE '%" + text + "%' OR " +
                    ContractPharmaValue.Columnas.CIUDAD +" LIKE '%" + text + "%'" +
                    ") GROUP BY " + ContractPharmaValue.Columnas.POS_ID +
                    " ORDER BY " + ContractPharmaValue.Columnas.POS_ID + " ASC " ;
        }
        // Select All Query
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePharmaValue bpv = new BasePharmaValue();
                bpv.setId(cursor.getString(cursor.getColumnIndexOrThrow(Constantes.ID_REMOTA)));
                bpv.setPos_id(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_ID)));
                bpv.setPos_name(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_NAME)));
                bpv.setChannel(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CHANNEL)));
                bpv.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.DIRECCION)));
                bpv.setCity(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CIUDAD)));
                bpv.setTermometro(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.TERMOMETRO)));
                bpv.setModulo(Constantes.MODULO_PUNTOS_TARDIO);
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCausales() {
        List<String> operadores = new ArrayList<String>();
      //  Log.i("CANAL", canal);
        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractTipoInventario.Columnas.CAUSALES + " FROM " + ContractTipoInventario.TIPOINVENTARIO;

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});

        operadores.add("Seleccione");

        ////operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractTipoInventario.Columnas.CAUSALES)));
            } while (cursor.moveToNext() && cursor != null);
        }
        Log.i("OPERADORES", operadores.size() + "");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }
    public List<String> getTipo(String user, String codigo) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.CHANNEL_SEGMENT  + " FROM " + ContractPharmaValue.POS + " WHERE " +
                ContractPharmaValue.Columnas.USER +"=? AND " +
                ContractPharmaValue.Columnas.POS_ID + "=? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user,codigo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CHANNEL_SEGMENT)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getDealer(String user, String codigo, String tipo) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.ZONA + " FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.USER +"=? AND " +
                ContractPharmaValue.Columnas.POS_ID + "=? AND " +
                ContractPharmaValue.Columnas.CHANNEL_SEGMENT + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user, codigo, tipo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.ZONA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaTracking() {
        List<String> operadores = new ArrayList<String>();
        operadores.add("Seleccione");

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractTracking.Columnas.CATEGORIA + " FROM " + ContractTracking.TRACKING +
                " GROUP BY " + ContractTracking.Columnas.CATEGORIA;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractTracking.Columnas.CATEGORIA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        Log.i("database cat", ""+operadores);
        // returning lables
        return operadores;
    }
    public List<String> getSubCategoriaTracking(String categoria) {
        List<String> operadores = new ArrayList<String>();
        operadores.add("Seleccione");
        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractTracking.Columnas.SUBCATEGORIA + " FROM " + ContractTracking.TRACKING +
                " WHERE " + ContractTracking.Columnas.CATEGORIA + " = ?" +
                " GROUP BY " + ContractTracking.Columnas.SUBCATEGORIA;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{categoria});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractTracking.Columnas.SUBCATEGORIA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getMarcaTracking(String categoria, String subcategoria) {
        List<String> operadores = new ArrayList<String>();
        operadores.add("Seleccione");
        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractTracking.Columnas.MARCA + " FROM " + ContractTracking.TRACKING +
                " WHERE " + ContractTracking.Columnas.CATEGORIA + " = ?" +
                " AND " + ContractTracking.Columnas.SUBCATEGORIA + " = ?" +
                " GROUP BY " + ContractTracking.Columnas.MARCA;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractTracking.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<Tracking> filtrarListProductosTracking4(String categoria, String subcategoria, String marca) {
        ArrayList<Tracking> operadores = new ArrayList<Tracking>();


        Cursor cursor = null;
        String selectQuery = null;

        //String byFabricante = ContractTracking.Columnas.CUENTAS + " LIKE ? ";


        selectQuery = "SELECT *  FROM " +
                ContractTracking.TRACKING + " WHERE " +
                ContractTracking.Columnas.CATEGORIA + " = ? AND " +
                ContractTracking.Columnas.SUBCATEGORIA + " = ? AND " +
                ContractTracking.Columnas.MARCA + " = ?";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, marca});


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Tracking b = new Tracking();
                b.setId(cursor.getString(cursor.getColumnIndexOrThrow(ContractTracking.Columnas._ID)));
                b.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(ContractTracking.Columnas.DESCRIPCION)));
                b.setMecanica(cursor.getString(cursor.getColumnIndexOrThrow(ContractTracking.Columnas.MECANICA)));
                operadores.add(b);
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getViewSkuTracking(String id, String subchannel){
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractTracking.TRACKING + " WHERE "+
                ContractTracking.Columnas._ID + "=? AND " +
                ContractTracking.Columnas.MARCA + "=? " +
                " GROUP BY " + ContractTracking.Columnas.DESCRIPCION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{id, subchannel});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = (cursor.getString(cursor.getColumnIndexOrThrow(ContractTracking.Columnas.DESCRIPCION)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


    public String getViewCategoria(String id, String subchannel){
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractTracking.TRACKING + " WHERE "+
                ContractTracking.Columnas._ID + "=? AND " +
                ContractTracking.Columnas.MARCA + "=? " +
                " GROUP BY " + ContractTracking.Columnas.CATEGORIA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{id, subchannel});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = (cursor.getString(cursor.getColumnIndexOrThrow(ContractTracking.Columnas.CATEGORIA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getViewSubcategoria(String id, String subchannel, String categoria){
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractTracking.TRACKING + " WHERE "+
                ContractTracking.Columnas._ID + "=? AND " +
                ContractTracking.Columnas.MARCA + "=? AND " +
                ContractTracking.Columnas.CATEGORIA + "=? " +
                " GROUP BY " + ContractTracking.Columnas.SUBCATEGORIA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{id, subchannel, categoria});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = (cursor.getString(cursor.getColumnIndexOrThrow(ContractTracking.Columnas.SUBCATEGORIA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getViewVigenciaTracking(String id, String subchannel){
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractTracking.TRACKING + " WHERE "+
                ContractTracking.Columnas._ID + "=? AND " +
                ContractTracking.Columnas.MARCA + "=? " +
                " GROUP BY " + ContractTracking.Columnas.VIGENCIA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{id, subchannel});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = (cursor.getString(cursor.getColumnIndexOrThrow(ContractTracking.Columnas.VIGENCIA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getViewPrecioPromocionTracking(String id, String subchannel){
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractTracking.TRACKING + " WHERE "+
                ContractTracking.Columnas._ID + "=? AND " +
                ContractTracking.Columnas.MARCA + "=? " +
                " GROUP BY " + ContractTracking.Columnas.PRECIO_PROMOCION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{id, subchannel});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = (cursor.getString(cursor.getColumnIndexOrThrow(ContractTracking.Columnas.PRECIO_PROMOCION)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getViewMecanicaTracking(String id, String subchannel){
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractTracking.TRACKING + " WHERE "+
                ContractTracking.Columnas._ID + "=? AND " +
                ContractTracking.Columnas.MARCA + "=? " +
                " GROUP BY " + ContractTracking.Columnas.MECANICA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{id, subchannel});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = (cursor.getString(cursor.getColumnIndexOrThrow(ContractTracking.Columnas.MECANICA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getViewPOPTracking(String id, String subchannel){
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractTracking.TRACKING + " WHERE "+
                ContractTracking.Columnas._ID + "=? AND " +
                ContractTracking.Columnas.MARCA + "=? " +
                " GROUP BY " + ContractTracking.Columnas.MATERIAL_POP;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{id, subchannel});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = (cursor.getString(cursor.getColumnIndexOrThrow(ContractTracking.Columnas.MATERIAL_POP)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<TrackingInsert> getListProductosTrackingGuardados(String usuario, String codigo, String fecha){
        ArrayList<TrackingInsert> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = "";
        selectQuery = "SELECT * FROM " + ContractInsertTracking.INSERT_TRACKING + " WHERE " +
                ContractInsertTracking.Columnas.USUARIO + "=? AND " +
                ContractInsertTracking.Columnas.CODIGO + "=? AND " +
                ContractInsertTracking.Columnas.FECHA + "=? " +
                " ORDER BY " + ContractTracking.Columnas.DESCRIPCION;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{usuario, codigo, fecha});

        String header = "HEADER FOR RECYCLERVIEW";
        operadores.add(new TrackingInsert(header));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TrackingInsert bpv = new TrackingInsert();
                bpv.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertTracking.Columnas.DESCRIPCION)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getLatitudPdv(String codigo_pdv){
        String lat = "";

        String selectQuery = "SELECT "+ ContractPharmaValue.Columnas.LATITUD +" FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.POS_ID + "=? " +
                " GROUP BY " + ContractPharmaValue.Columnas.LATITUD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo_pdv});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                lat = (cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.LATITUD)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();

        return lat;
    }
    public String getLongitudPdv(String codigo_pdv){
        String lon = "";

        String selectQuery = "SELECT "+ ContractPharmaValue.Columnas.LONGITUD +" FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.POS_ID + "=? " +
                " GROUP BY " + ContractPharmaValue.Columnas.LONGITUD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo_pdv});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                lon = (cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.LONGITUD)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();

        return lon;
    }


    public List<String> getUbicacion(String user, String codigo, String tipo, String dealer) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.SUBCHANNEL + " FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.USER +"=? AND " +
                ContractPharmaValue.Columnas.POS_ID + "=? AND " +
                ContractPharmaValue.Columnas.CHANNEL_SEGMENT + "=? AND " +
                ContractPharmaValue.Columnas.ZONA + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user, codigo, tipo, dealer});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.SUBCHANNEL)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCorreo(String user, String codigo, String tipo, String dealer, String ubicacion) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.DIRECCION + " FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.USER +"=? AND " +
                ContractPharmaValue.Columnas.POS_ID + "=? AND " +
                ContractPharmaValue.Columnas.CHANNEL_SEGMENT + "=? AND " +
                ContractPharmaValue.Columnas.ZONA + "=? AND " +
                ContractPharmaValue.Columnas.SUBCHANNEL +  "=?";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user, codigo, tipo, dealer, ubicacion});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.DIRECCION)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCliente(String user, String codigo, String tipo, String dealer, String ubicacion) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.POS_NAME + " FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.USER +"=? AND " +
                ContractPharmaValue.Columnas.POS_ID + "=? AND " +
                ContractPharmaValue.Columnas.CHANNEL_SEGMENT + "=? AND " +
                ContractPharmaValue.Columnas.ZONA + "=? AND " +
                ContractPharmaValue.Columnas.DIRECCION +  "=?";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user, codigo, tipo, dealer, ubicacion});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_NAME)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables

        return operadores;
    }

    public List<String> getZona(String user, String codigo, String tipo, String dealer, String ubicacion) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.CHANNEL + " FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.USER +"=? AND " +
                ContractPharmaValue.Columnas.POS_ID + "=? AND " +
                ContractPharmaValue.Columnas.CHANNEL_SEGMENT + "=? AND " +
                ContractPharmaValue.Columnas.ZONA + "=? AND " +
                ContractPharmaValue.Columnas.POS_NAME +  "=?";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user, codigo, tipo, dealer, ubicacion});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CHANNEL)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables

        return operadores ;
    }

    public String getFormat(String codigo) {
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.FORMAT + " FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.POS_ID + "=?";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.FORMAT));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables

        return operadores ;
    }

    public String getNombreComercial(String codigo) {
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.CUSTOMER_OWNER + " FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.POS_ID + "=?";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CUSTOMER_OWNER));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables

        return operadores ;
    }

    public String getPosNameDpsm(String codigo) {
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.POS_NAME_DPSM + " FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.POS_ID + "=?";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_NAME_DPSM));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables

        return operadores ;
    }



    // funcion de prueba: mpin PARA VARIOS CONTACTOS:

    public List<String> getContactos() {
        List<String> contactos = new ArrayList<>(); // aqui se queria obtener una lista de todos los contactos de la base.
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.POS_NAME_DPSM +
                    " FROM " + ContractPharmaValue.POS;

            // Utilizamos rawQuery para ejecutar la consulta directamente.
            cursor = db.rawQuery(selectQuery, null);

            // Iteramos sobre los resultados para obtener cada número de teléfono.
            if (cursor.moveToFirst()) {
                do {
                    int columnIndex = cursor.getColumnIndex(ContractPharmaValue.Columnas.POS_NAME_DPSM);
                    if (columnIndex != -1) {
                        String celular = cursor.getString(columnIndex);
                        contactos.add(celular);
                    }
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error al obtener contactos: " + e.getMessage());
        } finally {
            // Asegúrate de cerrar el cursor y la base de datos en el bloque finally.
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return contactos;
    }

    public String getCanalPDV(String codigo) {
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.CHANNEL + " FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.POS_ID + "=?";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CHANNEL));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables

        return operadores ;
    }

    public String getNumeroController(String codigo) {
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.POS_NAME_DPSM + " FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.POS_ID + "=?";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_NAME_DPSM));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables

        return operadores ;
    }

    public List<String> getCategoria() {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
       String selectQuery = "SELECT DISTINCT "+ ContractPrecios.Columnas.CATEGORIA + " FROM " + ContractPrecios.PRECIOS;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPrecios.Columnas.CATEGORIA)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getPresentacionFlooring(String categoria, String subcategoria, String manufacturer) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQueryTodos = "", selectQuery = "";
        // Select All Query
        /*if (sector.equalsIgnoreCase("TODOS") && categoria.equalsIgnoreCase("TODOS")) {
            selectQueryTodos = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CUMPLIMIENTO + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO + " WHERE " +
                    ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQueryTodos, new String[]{"%" + manufacturer + "%"});
        }else if (sector.equalsIgnoreCase("TODOS") && !categoria.equalsIgnoreCase("TODOS")) {
            selectQueryTodos = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CUMPLIMIENTO + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO + " WHERE " +
                    ContractPortafolioProductos.Columnas.CATEGORY +"=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQueryTodos, new String[]{categoria,"%" + manufacturer + "%"});
        }else if (!sector.equalsIgnoreCase("TODOS") && categoria.equalsIgnoreCase("TODOS")) {
            selectQueryTodos = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CUMPLIMIENTO + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO + " WHERE " +
                    ContractPortafolioProductos.Columnas.CATEGORIA +"=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQueryTodos, new String[]{sector,"%" + manufacturer + "%"});
        }else{*/
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.PRESENTACION + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                    ContractPortafolioProductos.Columnas.CATEGORY +"=? AND " +
                    ContractPortafolioProductos.Columnas.SUBCATEGORIA +"=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,"%" + manufacturer + "%"});
        //}

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PRESENTACION)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getBrandFlooring(String categoria, String subcategoria, String codigo, String canal, String subcanal, String manufacturer) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, "%" + manufacturer + "%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, "%" + subcanal.trim() + "%", "%" + manufacturer + "%"});
        }
        //}

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }
//mpin invesntario-sugerido
public List<String> getBrandFlooring4(String codigo, String canal, String subcanal, String manufacturer) {
    List<String> operadores = new ArrayList<String>();
    Cursor cursor;
    String selectQuery = "";
    List<String> selectionArgs = new ArrayList<>();

    db = this.getReadableDatabase();

    // Construir la consulta base - SIEMPRE filtrar por fabricante
    selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA +
            " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
            " WHERE UPPER(" + ContractPortafolioProductos.Columnas.FABRICANTE + ") = UPPER(?)";
    selectionArgs.add(manufacturer);  // Esto siempre será "BASSA"

    // FILTRO ADICIONAL: Filtrar por cadena si NO es mayorista/droguerías
    if (!canal.equalsIgnoreCase("MAYORISTA") && !canal.equalsIgnoreCase("DROGUERIAS")) {
        selectQuery += " AND " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
        selectionArgs.add("%" + subcanal.trim() + "%");
    }

    // Ordenar
    selectQuery += " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;

    // Ejecutar consulta
    cursor = db.rawQuery(selectQuery, selectionArgs.toArray(new String[0]));

    operadores.add("Seleccione");
    if (cursor.moveToFirst()) {
        do {
            operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(
                    ContractPortafolioProductos.Columnas.MARCA
            )));
        } while (cursor.moveToNext());
    }

    cursor.close();
    db.close();
    return operadores;
}public List<String> getBrandFlooring3(String categoria, String subcategoria, String codigo, String canal, String subcanal, String manufacturer, String manufacturer2) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";

        if (canal.equalsIgnoreCase("MAYORISTA") || canal.equalsIgnoreCase("DROGUERIAS")) {
            if (categoria.equalsIgnoreCase("INSECTICIDAS")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND (" +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? OR " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + "='Sc Jhonson')" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, "%" + manufacturer + "%"});
            } else {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, "%" + manufacturer + "%"});
            }
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, "%" + subcanal.trim() + "%"});
        }
        //}

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }

        if(categoria.equalsIgnoreCase("DETERGENTES") && subcategoria.equalsIgnoreCase("POLVO")){
            operadores.add("Surf");
        }

        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


    public List<String> getGramajeFlooring2(String categoria, String subcategoria, String marca, String codigo, String canal, String subcanal, String manufacturer) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            if (categoria.equalsIgnoreCase("INSECTICIDAS")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CONTENIDO + " FROM " +
                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                        ContractPortafolioProductos.Columnas.MARCA + "=? AND (" +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? OR " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + "='Sc Jhonson')" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.CONTENIDO;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, marca, "%" + manufacturer + "%"});
            } else {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CONTENIDO + " FROM " +
                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                        ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.CONTENIDO;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, marca, "%" + manufacturer + "%"});
            }
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CONTENIDO + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.CONTENIDO;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, marca, "%" + subcanal.trim() + "%"});
        }
        //}

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CONTENIDO)));
            } while (cursor.moveToNext());
        }

        /*if(categoria.equalsIgnoreCase("DETERGENTES") && subcategoria.equalsIgnoreCase("POLVO")){
            operadores.add("Surf");
        }*/

        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


//    public List<String> getBrandFlooring2(String categoria, String subcategoria, String codigo, String canal, String subcanal, String manufacturer) {
//        List<String> operadores = new ArrayList<String>();
//
//        Cursor cursor;
//        String selectQuery = "";
//
//        if (canal.equalsIgnoreCase("MAYORISTA")) {
//            if (categoria.equalsIgnoreCase("INSECTICIDAS")) {
//                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
//                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
//                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
//                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND (" +
//                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? OR " +
//                        ContractPortafolioProductos.Columnas.FABRICANTE + "='Sc Jhonson')" +
//                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
//                db = this.getReadableDatabase();
//                cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, "%" + manufacturer + "%"});
//            } else {
//                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
//                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
//                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
//                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
//                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
//                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
//                db = this.getReadableDatabase();
//                cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, "%" + manufacturer + "%"});
//            }
//        }else{
//            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
//                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
//                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
//                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
//                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
//                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
//            db = this.getReadableDatabase();
//            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, "%" + subcanal.trim() + "%"});
//        }
//        //}
//
//        operadores.add("Seleccione");
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
//            } while (cursor.moveToNext());
//        }
//        // closing connection
//        cursor.close();
//        db.close();
//        // returning lables
//        return operadores;
//    }
// Función para obtener el fabricante de una marca
public String getFabricanteDeMarca(String marca) {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = null;
    String fabricante = "";

    String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.FABRICANTE +
            " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
            " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " = ?" +
            " LIMIT 1";

    cursor = db.rawQuery(selectQuery, new String[]{marca});

    if (cursor.moveToFirst()) {
        fabricante = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FABRICANTE));
    }

    cursor.close();
    db.close();
    return fabricante != null ? fabricante : "";
}

    public List<String> getBrandImpulso(String categoria, String subcategoria, String manufacturer) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQueryTodos = "", selectQuery = "";
        // Select All Query
        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                ContractPortafolioProductos.Columnas.SECTOR +"=? AND " +
                ContractPortafolioProductos.Columnas.CATEGORY +"=? AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?" +
                " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,"%" + manufacturer + "%"});
        //}

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSKUImpulso(String categoria, String subcategoria, String marca, String manufacturer) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQueryTodos = "", selectQuery = "";
        // Select All Query
        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                ContractPortafolioProductos.Columnas.SECTOR +"=? AND " +
                ContractPortafolioProductos.Columnas.CATEGORY +"=? AND " +
                ContractPortafolioProductos.Columnas.MARCA +"=? AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?" +
                " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,marca,"%" + manufacturer + "%"});
        //}

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getMarcasDisponiblesPrecios(String codigo, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();
        Cursor cursor = null;

        operadores.add("Seleccione marca");

        String selectQuery = "";
        SQLiteDatabase db = this.getReadableDatabase();

        // Filtro para excluir ciertos fabricantes (igual que en tus otras funciones)
        String excludeFabricantes = ContractPortafolioProductos.Columnas.FABRICANTE +
                " NOT LIKE '% Rosado%' AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Coral%' AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Favorita%' AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Maria%' AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Tia%'";

        if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA +
                    " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + excludeFabricantes + " AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            cursor = db.rawQuery(selectQuery, new String[]{"%" + subcanal.trim() + "%"});
        } else if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA +
                    " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + excludeFabricantes +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            cursor = db.rawQuery(selectQuery, null);
        } else {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA +
                    " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + excludeFabricantes +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            cursor = db.rawQuery(selectQuery, null);
        }

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    // Función para obtener fabricante de una marca específica
    public String getFabricantePorMarca(String marca) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String fabricante = "";

        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.FABRICANTE +
                " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " = ?" +
                " LIMIT 1";

        cursor = db.rawQuery(selectQuery, new String[]{marca});

        if (cursor.moveToFirst()) {
            fabricante = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FABRICANTE));
        }

        cursor.close();
        db.close();
        return fabricante != null ? fabricante : "";
    }

    // Función para verificar si es marca propia
    public boolean esMarcaPropia(String marca) {
        String fabricante = getFabricantePorMarca(marca);
        // Si el fabricante contiene "Bassa", es marca propia
        return fabricante != null && fabricante.contains("Bassa");
    }

    public List<String> getMarcaPrecios(String categoria, String subcategoria, String tipo, String fabricante, String codigo, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor = null;
        String selectQuery = "";
        // Select All Query

        operadores.add("Seleccione");

        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";

        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
        }

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Rosado%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Coral%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Favorita%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Maria%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Tia%' " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
                    cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, subcategoria});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, subcategoria, "%" + subcanal.trim() + "%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Rosado%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Coral%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Favorita%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Maria%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Tia%' " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, subcategoria});
        }

        // Select All Query
        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getMarcaProdCad(String fabricante, String categoria, String subcategoria) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQueryTodos = "", selectQuery = "";

        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                ContractPortafolioProductos.Columnas.CATEGORY +"=? AND " +
                ContractPortafolioProductos.Columnas.SUBCATEGORIA +"=? AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,"%" + fabricante + "%"});

        // Select All Query
        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getMarcaPacks(String fabricante, String categoria, String subcategoria) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQueryTodos = "", selectQuery = "";

        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                ContractPortafolioProductos.Columnas.CATEGORY +"=? AND " +
                ContractPortafolioProductos.Columnas.SUBCATEGORIA +"=? AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,"%" + fabricante + "%"});

        // Select All Query
        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getFabricantePrecios(String categoria, String subcategoria, String marca, String tipo, String fabricante, String codigo, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = null;

        operadores.add("Seleccione");

        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";

        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
        }

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.FABRICANTE + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, subcategoria, marca});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.FABRICANTE + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, subcategoria, marca, "%" + subcanal.trim() + "%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.FABRICANTE + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, subcategoria, marca});
        }

        // Select All Query
        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FABRICANTE)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }
//mpin
    public String getCategoriaVentas(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU + " =? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CATEGORY));
        }

        cursor.close();
        db.close();
        return id;
    }
//mpin
public String getSubCategoriaVentas(String sku) {
    String id = "";
    String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
            ContractPortafolioProductos.Columnas.SKU + " =? ";
    db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(query, new String[]{sku});

    if (cursor != null && cursor.moveToFirst()) {
        id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SUBCATEGORIA));
    }

    cursor.close();
    db.close();
    return id;
}

//mpin
public String getPresentacionVentas(String sku) {
    String id = "";
    String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
            ContractPortafolioProductos.Columnas.SKU + " =? ";
    db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(query, new String[]{sku});

    if (cursor != null && cursor.moveToFirst()) {
        id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PRESENTACION));
    }

    cursor.close();
    db.close();
    return id;
}

    //VENTAS MODULO NEW --

    public List<String> getCategoriaVentas(String fabricante, String canal, String subcanal) {
        Log.i("canal","" + canal);
        Log.i("canal","" + subcanal);
        Log.i("canal","" + fabricante);
        List<String> operadores = new ArrayList<String>();
        operadores.add("Seleccione");
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery;
        Cursor cursor;

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            // Consulta: Filtro por fabricante
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SECTOR + " FROM "
                    + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS
                    + " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ?";
            cursor = db.rawQuery(selectQuery, new String[]{fabricante});
        }
        else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            // Consulta: Filtro por fabricante y subcanal
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SECTOR + " FROM "
                    + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS
                    + " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ? AND "
                    + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, "%" + subcanal.trim() + "%"});
        }
        else {
            // Default: Solo filtro por fabricante
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SECTOR + " FROM "
                    + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS
                    + " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ?";
            cursor = db.rawQuery(selectQuery, new String[]{fabricante});
        }

        // Procesar resultados (igual para todos los casos)
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(
                        ContractPortafolioProductos.Columnas.SECTOR
                )));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return operadores;
    }

//mpin
    public List<String> getMarcaNew(String fabricante, String canal, String subcanal) {
        Log.i("canal","" + canal);
        Log.i("canal","" + subcanal);
        Log.i("canal","" + fabricante);
        List<String> operadores = new ArrayList<String>();
        operadores.add("Seleccione");
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery;
        Cursor cursor;

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            // Consulta: Filtro por fabricante
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM "
                    + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS
                    + " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ?";
            cursor = db.rawQuery(selectQuery, new String[]{fabricante});
        }
        else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            // Consulta: Filtro por fabricante y subcanal
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM "
                    + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS
                    + " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ? AND "
                    + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, "%" + subcanal.trim() + "%"});
        }
        else {
            // Default: Solo filtro por fabricante
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM "
                    + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS
                    + " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ?";
            cursor = db.rawQuery(selectQuery, new String[]{fabricante});
        }

        // Procesar resultados (igual para todos los casos)
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(
                        ContractPortafolioProductos.Columnas.MARCA
                )));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return operadores;
    }


    public String getCategoriaVentas2(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU + " =? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CATEGORY));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getSubCategoriaVentas2(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU + " =? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SUBCATEGORIA));
        }

        cursor.close();
        db.close();
        return id;
    }

    public String getPresentacionVentas2(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU + " =? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PRESENTACION));
        }

        cursor.close();
        db.close();
        return id;
    }


    public List<String> getSubcategoriaVentas(String fabricante, String categoria, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();
        operadores.add("Seleccione");
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery;
        Cursor cursor;

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            // Consulta: Filtro por fabricante y sector
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CATEGORY + " FROM "
                    + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS
                    + " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ? AND "
                    + ContractPortafolioProductos.Columnas.SECTOR + " = ?";
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, categoria});
        }
        else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            // Consulta: Filtro por fabricante, sector y subcanal
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CATEGORY + " FROM "
                    + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS
                    + " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ? AND "
                    + ContractPortafolioProductos.Columnas.SECTOR + " = ? AND "
                    + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, categoria, "%" + subcanal.trim() + "%"});
        }
        else {
            // Default: Filtro por fabricante y sector
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CATEGORY + " FROM "
                    + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS
                    + " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ? AND "
                    + ContractPortafolioProductos.Columnas.SECTOR + " = ?";
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, categoria});
        }

        // Procesar resultados
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(
                        ContractPortafolioProductos.Columnas.CATEGORY
                )));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return operadores;
    }

    public String getMarcaVentas(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU + " =? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA));
        }

        cursor.close();
        db.close();
        return id;
    }

    public List<String> getMarcaVentas(String fabricante, String categoria, String subcategoria, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();
        operadores.add("Seleccione");
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery;
        Cursor cursor;

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            // Consulta: Filtro por fabricante, sector y subcategoría
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM "
                    + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS
                    + " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ? AND "
                    + ContractPortafolioProductos.Columnas.SECTOR + " = ? AND "
                    + ContractPortafolioProductos.Columnas.CATEGORY + " = ?"
                    + " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, categoria, subcategoria});
        }
        else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            // Consulta: Filtro por fabricante, sector, subcategoría y subcanal
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM "
                    + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS
                    + " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ? AND "
                    + ContractPortafolioProductos.Columnas.SECTOR + " = ? AND "
                    + ContractPortafolioProductos.Columnas.CATEGORY + " = ? AND "
                    + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?"
                    + " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, categoria, subcategoria, "%" + subcanal.trim() + "%"});
        }
        else {
            // Default: Filtro por fabricante, sector y subcategoría
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM "
                    + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS
                    + " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ? AND "
                    + ContractPortafolioProductos.Columnas.SECTOR + " = ? AND "
                    + ContractPortafolioProductos.Columnas.CATEGORY + " = ?"
                    + " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, categoria, subcategoria});
        }

        // Procesar resultados
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(
                        ContractPortafolioProductos.Columnas.MARCA
                )));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return operadores;
    }

    public String getMarcaVentas2(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU + " =? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA));
        }

        cursor.close();
        db.close();
        return id;
    }


    public List<String> getMecanicaVentas2() {
        List<String> operadores = new ArrayList<>();
        Log.i("DB_PROMO", "=====================================");
        Log.i("DB_PROMO", "Iniciando getPromocionalVentas() SIN FILTROS");

        // Query simple sin WHERE
        String selectQuery = "SELECT DISTINCT " +
                ContractPromocionalVentas.Columnas.CADENA +
                " FROM " + ContractPromocionalVentas.PROMOCIONAL_VENTAS;

        Log.i("DB_PROMO", "QUERY: " + selectQuery);

        db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(selectQuery, null);
            operadores.add("Seleccione");

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String promo = cursor.getString(cursor.getColumnIndexOrThrow(ContractPromocionalVentas.Columnas.CADENA));
                    operadores.add(promo);
                    Log.i("DB_PROMO", "PROMOCIONAL encontrado: " + promo);
                } while (cursor.moveToNext());
            } else {
                Log.w("DB_PROMO", "⚠️ No se encontraron promocionales en la tabla.");
            }

        } catch (Exception e) {
            Log.e("DB_PROMO", "❌ Error ejecutando consulta: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }

        Log.i("DB_PROMO", "Total promocionales devueltos: " + operadores.size());
        Log.i("DB_PROMO", "=====================================");

        return operadores;
    }

    //mpin
    public List<String> getPromocionalVentas() {
        List<String> operadores = new ArrayList<>();
        Log.i("DB_PROMO", "=====================================");
        Log.i("DB_PROMO", "Iniciando getPromocionalVentas() SIN FILTROS");

        // Query simple sin WHERE
        String selectQuery = "SELECT DISTINCT " +
                ContractPromocionalVentas.Columnas.PROMOCIONAL +
                " FROM " + ContractPromocionalVentas.PROMOCIONAL_VENTAS;

        Log.i("DB_PROMO", "QUERY: " + selectQuery);

        db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(selectQuery, null);
            operadores.add("Seleccione");

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String promo = cursor.getString(cursor.getColumnIndexOrThrow(ContractPromocionalVentas.Columnas.PROMOCIONAL));
                    operadores.add(promo);
                    Log.i("DB_PROMO", "PROMOCIONAL encontrado: " + promo);
                } while (cursor.moveToNext());
            } else {
                Log.w("DB_PROMO", "⚠️ No se encontraron promocionales en la tabla.");
            }

        } catch (Exception e) {
            Log.e("DB_PROMO", "❌ Error ejecutando consulta: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }

        Log.i("DB_PROMO", "Total promocionales devueltos: " + operadores.size());
        Log.i("DB_PROMO", "=====================================");

        return operadores;
    }


    public List<String> getPromocionalVentas2() {
        List<String> operadores = new ArrayList<String>();
        String tipo = "PROMOCIONAL";


        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPromocionalVentas.Columnas.PROMOCIONAL + " FROM "
                + ContractPromocionalVentas.PROMOCIONAL_VENTAS
                + " WHERE " + ContractPromocionalVentas.Columnas.CADENA + " = ?";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{tipo});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPromocionalVentas.Columnas.PROMOCIONAL)));
            } while (cursor.moveToNext() && cursor != null);
        }
        Log.i("OPERADORES", operadores.size() + "");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getMecanicaVentas() {
        List<String> operadores = new ArrayList<String>();
        String tipo = "MECANICA";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPromocionalVentas.Columnas.PROMOCIONAL + " FROM "
                + ContractPromocionalVentas.PROMOCIONAL_VENTAS
                + " WHERE " + ContractPromocionalVentas.Columnas.CADENA + " = ?";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{tipo});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPromocionalVentas.Columnas.PROMOCIONAL)));
            } while (cursor.moveToNext() && cursor != null);
        }
        Log.i("OPERADORES", operadores.size() + "");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


    public List<String> getTipoVentas(String cuenta) {
        List<String> operadores = new ArrayList<String>();
        Log.i("CUENTA", cuenta);
        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractTipoVentas.Columnas.TIPO + " FROM " + ContractTipoVentas.TIPO_VENTAS;

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPromociones.Columnas.TIPO)));
            } while (cursor.moveToNext() && cursor != null);
        }
        Log.i("OPERADORES", operadores.size() + "");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getTipoVentas2() {
        List<String> operadores = new ArrayList<String>();
        String tipo = "TIPO VENTA";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPromocionalVentas.Columnas.PROMOCIONAL + " FROM "
                + ContractPromocionalVentas.PROMOCIONAL_VENTAS
                + " WHERE " + ContractPromocionalVentas.Columnas.CADENA + " = ?";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{tipo});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPromocionalVentas.Columnas.PROMOCIONAL)));
            } while (cursor.moveToNext() && cursor != null);
        }
        Log.i("OPERADORES", operadores.size() + "");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    // NUEVO METODO: Obtener todas las marcas directamente
    public List<String> getMarcasDirectasVentas(String fabricante, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();
        operadores.add("Seleccione");
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery;
        Cursor cursor;

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM "
                    + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS
                    + " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ?"
                    + " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante});
        }
        else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM "
                    + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS
                    + " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ? AND "
                    + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?"
                    + " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, "%" + subcanal.trim() + "%"});
        }
        else {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM "
                    + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS
                    + " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ?"
                    + " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante});
        }

        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(
                        ContractPortafolioProductos.Columnas.MARCA
                )));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return operadores;
    }


    public ArrayList<Muestras> filtrarProductosPorMarcaMuestras(String marca, String canal, String subcanal) {
        Log.d("FILTRAR_PRODUCTOS", "marca: " + marca + ", canal: " + canal + ", SUBCANAL: " + subcanal);
        ArrayList<Muestras> operadores = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String selectQuery;

        // Consulta MODIFICADA: sin filtro por fabricante
        String baseQuery = "SELECT DISTINCT " +
                ContractPortafolioProductos.Columnas.SKU + ", " +
                ContractPortafolioProductos.Columnas.PVP +
                " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " = ?";

        // Agregar condiciones según el canal
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = baseQuery + " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            cursor = db.rawQuery(selectQuery, new String[]{marca});
        }
        else if (canal.equalsIgnoreCase("AUTOSERVICIO") || canal.equalsIgnoreCase("DROGUERIAS")) {
            selectQuery = baseQuery + " AND " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            cursor = db.rawQuery(selectQuery, new String[]{marca, "%" + subcanal.trim() + "%"});
        }
        else {
            selectQuery = baseQuery + " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            cursor = db.rawQuery(selectQuery, new String[]{marca});
        }

        // Procesar resultados
        if (cursor.moveToFirst()) {
            do {
                Muestras venta = new Muestras();
                venta.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
                venta.setPvp(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PVP)));
                operadores.add(venta);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return operadores;
    }
    // NUEVO METODO: Filtrar productos solo por marca MPIN // hacer otro para novedades
    public ArrayList<Ventas> filtrarProductosPorMarcaVentas(String fabricante, String marca, String canal, String subcanal) {
        Log.d("FILTRAR_PRODUCTOS", "fabricante: " + fabricante + ", marca: " + marca + ", canal: " + canal+", SUBCANAL"+ subcanal);
        ArrayList<Ventas> operadores = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String selectQuery;

        // Consulta corregida
        String baseQuery = "SELECT DISTINCT " +
                ContractPortafolioProductos.Columnas.SKU + ", " +
                ContractPortafolioProductos.Columnas.PVP +
                " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ? AND " +
                ContractPortafolioProductos.Columnas.MARCA + " = ?";

        // Agregar condiciones según el canal
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = baseQuery + " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, marca});
        }
        else if (canal.equalsIgnoreCase("AUTOSERVICIO") || canal.equalsIgnoreCase("DROGUERIAS")) {
            selectQuery = baseQuery + " AND " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, marca, "%" + subcanal.trim() + "%"});
        }
        else {
            selectQuery = baseQuery + " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, marca});
        }

        // Procesar resultados
        if (cursor.moveToFirst()) {
            do {
                Ventas venta = new Ventas();
                venta.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
                venta.setPvp(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PVP)));
                operadores.add(venta);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return operadores;
    }
    //new mpin novedades
    public ArrayList<Base_productos_novedades> filtrarProductosPorMarcaNovedades(String fabricante, String marca, String canal, String subcanal) {
        Log.d("FILTRAR_PRODUCTOS", "fabricante: " + fabricante + ", marca: " + marca + ", canal: " + canal+", SUBCANAL"+ subcanal);
        ArrayList<Base_productos_novedades> operadores = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String selectQuery;

        // Consulta corregida
        String baseQuery = "SELECT DISTINCT " +
                ContractPortafolioProductos.Columnas.SKU +

                " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ? AND " +
                ContractPortafolioProductos.Columnas.MARCA + " = ?";

        // Agregar condiciones según el canal
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = baseQuery + " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, marca});
        }
        else if (canal.equalsIgnoreCase("AUTOSERVICIO") || canal.equalsIgnoreCase("DROGUERIAS")) {
            selectQuery = baseQuery + " AND " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, marca, "%" + subcanal.trim() + "%"});
        }
        else {
            selectQuery = baseQuery + " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, marca});
        }

        // Procesar resultados
        if (cursor.moveToFirst()) {
            do {
                Base_productos_novedades novedades = new Base_productos_novedades();
                novedades.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
               // venta.setPvp(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PVP)));
                operadores.add(novedades);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return operadores;
    }

    public ArrayList<Ventas> filtrarListProductosVentas(String fabricante, String categoria, String subcategoria,
                                                        String brand, String canal, String subcanal) {
        ArrayList<Ventas> operadores = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String selectQuery;

        // Consulta base para todos los casos
        String baseQuery = "SELECT DISTINCT " +
                ContractPortafolioProductos.Columnas.SKU + ", " +
                ContractPortafolioProductos.Columnas.PVP +
                " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ? AND " +
                ContractPortafolioProductos.Columnas.SECTOR + " = ? AND " +
                ContractPortafolioProductos.Columnas.CATEGORY + " = ? AND " +
                ContractPortafolioProductos.Columnas.MARCA + " = ?";

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = baseQuery + " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, categoria, subcategoria, brand});
        }
        else if (canal.equalsIgnoreCase("AUTOSERVICIO") || canal.equalsIgnoreCase("DROGUERIAS")) {
            selectQuery = baseQuery + " AND " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, categoria, subcategoria, brand, "%" + subcanal.trim() + "%"});
        }
        else {
            selectQuery = baseQuery + " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, categoria, subcategoria, brand});
        }

        // Procesar resultados
        if (cursor.moveToFirst()) {
            do {
                Ventas p = new Ventas();
                p.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
                p.setPvp(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PVP)));
                operadores.add(p);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return operadores;
    }



    

    public ArrayList<Base_campos_x_modulos> getCamposPorModulos(String modulo, String cuenta) {
        ArrayList<Base_campos_x_modulos> operadores = new ArrayList<Base_campos_x_modulos>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractCamposPorModulos.Columnas.CAMPO + " FROM " + ContractCamposPorModulos.CAMPOS_X_MODULOS + " WHERE " +
                ContractCamposPorModulos.Columnas.MODULO + "=? ";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{modulo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Base_campos_x_modulos bcxm = new Base_campos_x_modulos();
                //    bcxm.setModulo(cursor.getString(cursor.getColumnIndexOrThrow(ContractCamposPorModulos.Columnas.MODULO)));
                bcxm.setCampo(cursor.getString(cursor.getColumnIndexOrThrow(ContractCamposPorModulos.Columnas.CAMPO)));
                operadores.add(bcxm);
            } while (cursor.moveToNext() && cursor != null);
        }
        Log.i("OPERADORES", operadores.size() + "");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<String> filtrarListProductosVentasString(String fabricante, String categoria, String subcategoria,
                                                        String brand, String canal, String subcanal) {
        ArrayList<String> operadores = new ArrayList<>();
        operadores.add("Seleccione");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String selectQuery;

        // Consulta base para todos los casos
        String baseQuery = "SELECT DISTINCT " +
                ContractPortafolioProductos.Columnas.SKU + ", " +
                ContractPortafolioProductos.Columnas.PVP +
                " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " = ? AND " +
                ContractPortafolioProductos.Columnas.SECTOR + " = ? AND " +
                ContractPortafolioProductos.Columnas.CATEGORY + " = ? AND " +
                ContractPortafolioProductos.Columnas.MARCA + " = ?";

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = baseQuery + " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, categoria, subcategoria, brand});
        }
        else if (canal.equalsIgnoreCase("AUTOSERVICIO") || canal.equalsIgnoreCase("DROGUERIAS")) {
            selectQuery = baseQuery + " AND " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, categoria, subcategoria, brand, "%" + subcanal.trim() + "%"});
        }
        else {
            selectQuery = baseQuery + " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, categoria, subcategoria, brand});
        }

        // Procesar resultados
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return operadores;
    }

    public String getPresentacionPrecios(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU + " =? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PRESENTACION));
        }

        cursor.close();
        db.close();
        return id;
    }
    //FIN DE MODULO

    public List<String> getFabricanteCodificados(String categoria, String tipo, String fabricante, String codigo, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = null;

        operadores.add("Seleccione");

        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";

        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
        }

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.FABRICANTE + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.FABRICANTE + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, "%" + subcanal.trim() + "%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.FABRICANTE + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? ";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
        }

        // Select All Query
        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FABRICANTE)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getPresentacionPacks(String fabricante, String categoria, String subcategoria, String marca) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.PRESENTACION + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                ContractPortafolioProductos.Columnas.FABRICANTE +"=? AND " +
                ContractPortafolioProductos.Columnas.CATEGORY +"=? AND " +
                ContractPortafolioProductos.Columnas.SUBCATEGORIA +"=? AND " +
                ContractPortafolioProductos.Columnas.MARCA +"=?";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{fabricante,categoria,subcategoria,marca});
        //}

        // Select All Query
        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PRESENTACION)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getProductoPacks(String fabricante, String categoria, String subcategoria, String marca, String presentacion) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                ContractPortafolioProductos.Columnas.FABRICANTE +"=? AND " +
                ContractPortafolioProductos.Columnas.CATEGORY +"=? AND " +
                ContractPortafolioProductos.Columnas.SUBCATEGORIA +"=? AND " +
                ContractPortafolioProductos.Columnas.MARCA +"=? AND " +
                ContractPortafolioProductos.Columnas.PRESENTACION +"=?";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{fabricante,categoria,subcategoria,marca,presentacion});
        //}

        // Select All Query
        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSubcategoriaPromocion(String categoria, String tipo, String fabricante, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();

        String selectQuery = "";
        Cursor cursor = null;
        // Select All Query
//        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CATEGORY + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
//                ContractPortafolioProductos.Columnas.SECTOR + "=?";
//        db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{categoria});

        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";

        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
        }

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CATEGORY + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            Log.i("mayo", "si");
        }else{
            Log.i("auto serv", "si");
            Log.i("subcanal", subcanal);
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CATEGORY + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, "%" + subcanal.trim() + "%"});
        }
        Log.i("RESULTADO DE SUBCAT1", ""+cursor);
        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            Log.i("RESULTADO DE SUBCAT", ""+operadores);
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CATEGORY)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getPresentacionPromocion(String categoria, String marca, String segmento1) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.PRESENTACION + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                ContractPortafolioProductos.Columnas.SUBCATEGORIA + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{categoria,marca,segmento1});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PRESENTACION)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


    public List<String> getCanal(){

        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.CHANNEL + " FROM " +ContractPharmaValue.POS;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CHANNEL)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;

    }


    public ArrayList<Base_tests> getAllQuizByCanal(ArrayList<String> canales, String usuario) {
        ArrayList<Base_tests> quizList = new ArrayList<Base_tests>();

        Cursor c = null;

        // En caso de ver el gestor tener asignado pvs con diferentes canales
        if(canales.size() > 1){

        //    String canal1 = canales.get(0).toString();
        //    String canal2 = canales.get(1).toString();
          //  String canal3 = canales.get(2).toString();


            // Select All Query
            String selectQuery = "" +
                    "SELECT rt.test_id, rt.test , rt.descripcion , rt.f_inicio , rt.h_inicio , rt.f_limite , rt.h_limite , rt.active " +
                    "FROM " + ContractTests.TABLE_TEST  + " rt " +
                    " INNER JOIN " + ContractPreguntas.TABLE_QUEST + " rp " +
                    " ON rp.test_id=rt.test_id " +
                    " WHERE rp.canal IN ('MAYORISTA','AUTOSERVICIO','IMPULSO') " +  "AND rt.test NOT IN (select test FROM insert_preguntas WHERE user =? AND estado_test ='1' " + " ) " +
                    "GROUP BY rt.test_id,rt.test";
            db=this.getReadableDatabase();


             c = db.rawQuery(selectQuery, new String[]{usuario});

        }

        if(canales.size() == 1){

            String canal = canales.get(0).toString();

            // Select All Query
            String selectQuery = "" +
                    "SELECT rt.test_id, rt.test , rt.descripcion , rt.f_inicio , rt.h_inicio , rt.f_limite , rt.h_limite , rt.active " +
                    "FROM " + ContractTests.TABLE_TEST  + " rt " +
                    " INNER JOIN " + ContractPreguntas.TABLE_QUEST + " rp " +
                    " ON rp.test_id=rt.test_id " +
                    " WHERE rp.canal =? " +  "AND rt.test NOT IN (select test FROM insert_preguntas WHERE user =? AND estado_test ='1' " + " ) " +
                    "GROUP BY rt.test_id,rt.test";
            db=this.getReadableDatabase();


             c = db.rawQuery(selectQuery, new String[]{canal,usuario});


        }


        if(c.moveToFirst()){
            do {
                Base_tests quiz = new Base_tests();
                quiz.setTest_id(c.getString(c.getColumnIndexOrThrow(ContractTests.Columnas.KEY_TEST_ID)));
                quiz.setTest(c.getString(c.getColumnIndexOrThrow(ContractTests.Columnas.KEY_TEST)));
                quiz.setDescripcion(c.getString(c.getColumnIndexOrThrow(ContractTests.Columnas.KEY_DESCRIPTION)));
                quiz.setF_inicio(c.getString(c.getColumnIndexOrThrow(ContractTests.Columnas.KEY_DATE_START)));
                quiz.setH_inicio(c.getString(c.getColumnIndexOrThrow(ContractTests.Columnas.KEY_HOUR_START)));
                quiz.setF_limite(c.getString(c.getColumnIndexOrThrow(ContractTests.Columnas.KEY_DATE_LIMIT)));
                quiz.setH_limite(c.getString(c.getColumnIndexOrThrow(ContractTests.Columnas.KEY_HOUR_LIMIT)));
                quiz.setActive(c.getString(c.getColumnIndexOrThrow(ContractTests.Columnas.KEY_ACTIVE)));
                quizList.add(quiz);
            } while(c.moveToNext());

        }
        return quizList;
    }


    public List<Base_preguntas> getAllQuestionsById(String test_id){
        List<Base_preguntas> QuestionList = new ArrayList<>();
        // Iniciando la BaseDeDatos
        db = getReadableDatabase();
        String query = "SELECT * FROM " + ContractPreguntas.TABLE_QUEST + " WHERE " + ContractPreguntas.Columnas.KEY_TEST_ID + "=?";
        Cursor c = db.rawQuery(query, new String[]{test_id});

        if(c.moveToFirst()){
            do {
                Base_preguntas question = new Base_preguntas();
                question.setQuestion(c.getString(c.getColumnIndexOrThrow(ContractPreguntas.Columnas.KEY_QUES)));
                question.setAnswer(c.getString(c.getColumnIndexOrThrow(ContractPreguntas.Columnas.KEY_ANSWER)));
                question.setOpta(c.getString(c.getColumnIndexOrThrow(ContractPreguntas.Columnas.KEY_OPTA)));
                question.setOptb(c.getString(c.getColumnIndexOrThrow(ContractPreguntas.Columnas.KEY_OPTB)));
                question.setOptc(c.getString(c.getColumnIndexOrThrow(ContractPreguntas.Columnas.KEY_OPTC)));
                question.setCanal(c.getString(c.getColumnIndexOrThrow(ContractPreguntas.Columnas.KEY_CANAL)));
                question.setTiempo(c.getString(c.getColumnIndexOrThrow(ContractPreguntas.Columnas.KEY_TIEMPO)));
                question.setTest_id(c.getString(c.getColumnIndexOrThrow(ContractPreguntas.Columnas.KEY_TEST_ID)));

                QuestionList.add(question);
            } while(c.moveToNext());
        }
        return QuestionList;
    }



    public List<String> getTipoPromocion(String canal) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPromociones.Columnas.TIPO + " FROM " + ContractPromociones.PROMOCIONES +
                        " WHERE " + ContractPromociones.Columnas.CANAL + "=? ORDER BY " + ContractPromociones.Columnas.TIPO;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{canal});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPromociones.Columnas.TIPO)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getDescripcionPromocion(String canal) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPromociones.Columnas.DESCRIPCION + " FROM " + ContractPromociones.PROMOCIONES + " WHERE " +
                ContractPromociones.Columnas.CANAL +"=? ORDER BY " + ContractPromociones.Columnas.DESCRIPCION;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{canal});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPromociones.Columnas.DESCRIPCION)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCampanaPromocion(String canal, String cadena, String channel_segment, String tipo_promocion) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPromociones.Columnas.CAMPANA +
                " FROM " + ContractPromociones.PROMOCIONES +
                " WHERE " + ContractPromociones.Columnas.CANAL +"=? " +
                " AND (" + ContractPromociones.Columnas.CADENA +"=? OR " + ContractPromociones.Columnas.CADENA +"=?)" +
                " AND " + ContractPromociones.Columnas.DESCRIPCION +"=? " +
                " ORDER BY " + ContractPromociones.Columnas.CAMPANA;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{canal, cadena, channel_segment, tipo_promocion});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPromociones.Columnas.CAMPANA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


    public List<String> getCategoriaPromocion2(String canal, String cadena, String channel_segment, String tipo_promocion, String campana) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPromociones.Columnas.CATEGORIA +
                " FROM " + ContractPromociones.PROMOCIONES +
                " WHERE " + ContractPromociones.Columnas.CANAL +"=? " +
                " AND (" + ContractPromociones.Columnas.CADENA +"=? OR " + ContractPromociones.Columnas.CADENA +"=?)" +
                " AND " + ContractPromociones.Columnas.DESCRIPCION +"=? " +
                " AND " + ContractPromociones.Columnas.CAMPANA +"=? " +
                " ORDER BY " + ContractPromociones.Columnas.CATEGORIA;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{canal, cadena, channel_segment, tipo_promocion, campana});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPromociones.Columnas.CATEGORIA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


    public List<String> getSubcategoriaPromocion2(String canal, String cadena, String channel_segment, String tipo_promocion, String campana, String categoria) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPromociones.Columnas.SUBCATEGORIA +
                " FROM " + ContractPromociones.PROMOCIONES +
                " WHERE " + ContractPromociones.Columnas.CANAL +"=? " +
                " AND (" + ContractPromociones.Columnas.CADENA +"=? OR " + ContractPromociones.Columnas.CADENA +"=?)" +
                " AND " + ContractPromociones.Columnas.DESCRIPCION +"=? " +
                " AND " + ContractPromociones.Columnas.CAMPANA +"=? " +
                " AND " + ContractPromociones.Columnas.CATEGORIA +"=? " +
                " ORDER BY " + ContractPromociones.Columnas.SUBCATEGORIA;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{canal, cadena, channel_segment, tipo_promocion, campana, categoria});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPromociones.Columnas.SUBCATEGORIA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


    public List<String> getMarcaPorCampana(String canal, String cadena, String channel_segment, String descripcion, String campana, String fabricante) {
        List<String> operadores = new ArrayList<String>();

        Log.i("GET_MARCA_POR_CAMPANA", "canal: " + canal + ", cadena: " + cadena + ", campana: " + campana + ", fabricante: " + fabricante);

        String selectQuery = "";
        Cursor cursor = null;
        db = this.getReadableDatabase();

        try {
            // Para Marca Propia (Bassa) - filtra por fabricante Bassa y cadena
            if (canal.equalsIgnoreCase("MAYORISTA")) {
                selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA +
                        " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? " +
                        " AND " + ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Rosado%' " +
                        " AND " + ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Coral%' " +
                        " AND " + ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Favorita%' " +
                        " AND " + ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Maria%' " +
                        " AND " + ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Tia%' " +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;

                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
            } else {
                selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA +
                        " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? " +
                        " AND " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? " +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;

                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", "%" + cadena.trim() + "%"});
            }

            operadores.add("Seleccione");

            // Si NO es Bassa (es competencia), agregar "Otros"
            if (!fabricante.equalsIgnoreCase("BASSA")) {
                operadores.add("Otros");
            }

            // Recorrer resultados
            if (cursor.moveToFirst()) {
                do {
                    String marca = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA));
                    // Filtrar marcas vacías o nulas
                    if (marca != null && !marca.trim().isEmpty() && !operadores.contains(marca)) {
                        operadores.add(marca);
                    }
                } while (cursor.moveToNext());
            }

            Log.i("MARCAS_POR_CAMPANA", "Total marcas encontradas: " + (operadores.size() - 1));

        } catch (Exception e) {
            Log.e("GET_MARCA_POR_CAMPANA", "Error: " + e.getMessage());
            e.printStackTrace();
            operadores.clear();
            operadores.add("Seleccione");
            operadores.add("Error al cargar");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.close();
        }

        return operadores;
    }
    public List<String> getMarcaPromocion2new(String canal, String cadena, String channel_segment,
                                              String tipo_promocion, String campana, String fabricante) {
        List<String> operadores = new ArrayList<String>();

        // MODIFICACIÓN IMPORTANTE: Cambiar LIKE por = para buscar exactamente el fabricante
        String selectQuery = "SELECT DISTINCT " + ContractPromociones.Columnas.MARCA +
                " FROM " + ContractPromociones.PROMOCIONES +
                " WHERE " + ContractPromociones.Columnas.CANAL + "=? " +
                " AND (" + ContractPromociones.Columnas.CADENA + "=? OR " +
                ContractPromociones.Columnas.CADENA + "=?)" +
                " AND " + ContractPromociones.Columnas.DESCRIPCION + "=? " +
                " AND " + ContractPromociones.Columnas.CAMPANA + "=? " +
                // CAMBIADO: Usar = en lugar de LIKE para buscar exactamente
                " AND " + ContractPromociones.Columnas.FABRICANTE + "=? " +
                " ORDER BY " + ContractPromociones.Columnas.MARCA;

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                canal,
                cadena,
                channel_segment,
                tipo_promocion,
                campana,
                fabricante  // Sin % % porque queremos exactamente este fabricante
        });

        operadores.add("Seleccione");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(
                        ContractPromociones.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }

        // Si no encuentra marcas para ese fabricante exacto
        if (operadores.size() == 1) { // Solo tiene "Seleccione"
            Log.i("MARCA PROMO", "No se encontraron marcas para el fabricante: " + fabricante);
        }

        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }

        return operadores;
    }
    public List<String> getMarcaPromocion2(String canal, String cadena, String channel_segment, String tipo_promocion, String campana ) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPromociones.Columnas.MARCA +
                " FROM " + ContractPromociones.PROMOCIONES +
                " WHERE " + ContractPromociones.Columnas.CANAL +"=? " +
                " AND (" + ContractPromociones.Columnas.CADENA +"=? OR " + ContractPromociones.Columnas.CADENA +"=?)" +
                " AND " + ContractPromociones.Columnas.DESCRIPCION +"=? " +
                " AND " + ContractPromociones.Columnas.CAMPANA +"=? " +
               // " AND " + ContractPromociones.Columnas.CATEGORIA +"=? " +
              //  " AND " + ContractPromociones.Columnas.SUBCATEGORIA +"=? " +
                " ORDER BY " + ContractPromociones.Columnas.MARCA;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{canal, cadena, channel_segment, tipo_promocion, campana });

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPromociones.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSkuPromocion3(String canal, String cadena, String channel_segment, String tipo_promocion, String campana, String marca) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPromociones.Columnas.SKU +
                " FROM " + ContractPromociones.PROMOCIONES +
                " WHERE " + ContractPromociones.Columnas.CANAL +"=? " +
                " AND (" + ContractPromociones.Columnas.CADENA +"=? OR " + ContractPromociones.Columnas.CADENA +"=?)" +
                " AND " + ContractPromociones.Columnas.DESCRIPCION +"=? " +
                " AND " + ContractPromociones.Columnas.CAMPANA +"=? " +
              //  " AND " + ContractPromociones.Columnas.CATEGORIA +"=? " +
              //  " AND " + ContractPromociones.Columnas.SUBCATEGORIA +"=? " +
                " AND " + ContractPromociones.Columnas.MARCA +"=? " +
                " ORDER BY " + ContractPromociones.Columnas.SKU;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{canal, cadena, channel_segment, tipo_promocion, campana, marca});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPromociones.Columnas.SKU)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


    public List<String> getDescripcionMarcaPropia(String canal, String cadena, String channel_segment) {
        List<String> operadores = new ArrayList<String>();
        Log.i("getDescripcionMarcaPropia", "canal: " + canal + ", cadena: " + cadena);

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPromociones.Columnas.DESCRIPCION +
                " FROM " + ContractPromociones.PROMOCIONES +
                " WHERE " + ContractPromociones.Columnas.CANAL +"=? " +
                " AND (" + ContractPromociones.Columnas.CADENA +"=? OR " + ContractPromociones.Columnas.CADENA +"=?)" +
                " ORDER BY " + ContractPromociones.Columnas.DESCRIPCION;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{canal, cadena, channel_segment});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPromociones.Columnas.DESCRIPCION)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getBrandExhibicion(String categoria, String sector) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CONTENIDO + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.CATEGORY +"=? AND " +
                ContractPortafolioProductos.Columnas.SECTOR + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{categoria, sector});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CONTENIDO)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<LogUser> getLog(String usuario, String fecha) {
        ArrayList<LogUser> operadores = new ArrayList<LogUser>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + ContractLog.LOG + " WHERE " +
                ContractLog.Columnas.USUARIO +"=? AND " +
                ContractLog.Columnas.FECHA +"=? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{usuario, fecha});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LogUser logUser = new LogUser();
                logUser.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow(ContractLog.Columnas.USUARIO)));
                logUser.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(ContractLog.Columnas.FECHA)));
                logUser.setHora(cursor.getString(cursor.getColumnIndexOrThrow(ContractLog.Columnas.HORA)));
                logUser.setAccion(cursor.getString(cursor.getColumnIndexOrThrow(ContractLog.Columnas.ACCION)));
                operadores.add(logUser);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSubcategoriaExhibicion(String categoria, String tipo, String fabricante) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";

        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";

        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
        }

        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CATEGORY +
                " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                " WHERE "  + byFabricante + " AND " +
                ContractPortafolioProductos.Columnas.SECTOR +"=?";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CATEGORY)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSegmentoExhibicion(String categoria, String sector, String segmento1) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";
        /*if (sector.equalsIgnoreCase("TODOS") && categoria.equalsIgnoreCase("TODOS") && segmento1.equalsIgnoreCase("TODOS")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SEGMENTO + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{});
        }else if (sector.equalsIgnoreCase("TODOS") && categoria.equalsIgnoreCase("TODOS") && !segmento1.equalsIgnoreCase("TODOS")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SEGMENTO + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO + " WHERE " +
                    ContractPortafolioProductos.Columnas.CATEGORIA +"=?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{segmento1});
        }else if (sector.equalsIgnoreCase("TODOS") && !categoria.equalsIgnoreCase("TODOS") && segmento1.equalsIgnoreCase("TODOS")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SEGMENTO + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO + " WHERE " +
                    ContractPortafolioProductos.Columnas.CATEGORY +"=?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria});
        }else if (sector.equalsIgnoreCase("TODOS") && !categoria.equalsIgnoreCase("TODOS") && !segmento1.equalsIgnoreCase("TODOS")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SEGMENTO + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO + " WHERE " +
                    ContractPortafolioProductos.Columnas.CATEGORY +"=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORIA +"=?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria,segmento1});
        }else if (!sector.equalsIgnoreCase("TODOS") && categoria.equalsIgnoreCase("TODOS") && segmento1.equalsIgnoreCase("TODOS")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SEGMENTO + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO + " WHERE " +
                    ContractPortafolioProductos.Columnas.CATEGORIA +"=?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{sector});
        }else if (!sector.equalsIgnoreCase("TODOS") && categoria.equalsIgnoreCase("TODOS") && !segmento1.equalsIgnoreCase("TODOS")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SEGMENTO + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO + " WHERE " +
                    ContractPortafolioProductos.Columnas.CATEGORIA +"=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORIA +"=?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{sector,segmento1});
        }else if (!sector.equalsIgnoreCase("TODOS") && !categoria.equalsIgnoreCase("TODOS") && segmento1.equalsIgnoreCase("TODOS")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SEGMENTO + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO + " WHERE " +
                    ContractPortafolioProductos.Columnas.CATEGORIA +"=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY +"=?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{sector,categoria});
        }else{*/
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SEGMENTO + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY +"=? AND " +
                    ContractPortafolioProductos.Columnas.SUBCATEGORIA + "=?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{sector, categoria, segmento1});
        //}

        ////operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SEGMENTO)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getBrandValores(String categoria, String subcategoria, String codigo, String canal, String subcanal, String manufacturer) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, "%" + manufacturer + "%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, "%" + subcanal.trim() + "%", "%" + manufacturer + "%"});
        }
        //}

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getBrandValores2(String categoria, String subcategoria, String codigo, String canal, String subcanal, String manufacturer) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor = null;
        String selectQuery = "";

        if (subcategoria.equalsIgnoreCase("TODOS")) {
            if (canal.equalsIgnoreCase("MAYORISTA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + /*ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +*/
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? " +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,"%"+manufacturer+"%"});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("ALMACENES TIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? " +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,"%"+manufacturer+"%"});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("AKI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,"%"+manufacturer+"%"});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("CORAL HIPERMERCADOS")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,"%"+manufacturer+"%"});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SUPERMAXI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,"%"+manufacturer+"%"});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("MI COMISARIATO")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,"%"+manufacturer+"%"});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SANTA MARIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,"%"+manufacturer+"%"});
            }else{
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? " +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,"%"+manufacturer+"%"});
            }
        }else{
            if (canal.equalsIgnoreCase("MAYORISTA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + /*ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +*/
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,"%"+manufacturer+"%"});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("ALMACENES TIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,"%"+manufacturer+"%"});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("AKI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,"%"+manufacturer+"%"});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("CORAL HIPERMERCADOS")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND "  +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? "+
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,"%"+manufacturer+"%"});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SUPERMAXI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,"%"+manufacturer+"%"});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("MI COMISARIATO")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? " +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,"%"+manufacturer+"%"});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SANTA MARIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,"%"+manufacturer+"%"});
            }else{
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,"%"+manufacturer+"%"});
            }
        }

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getNombreMercaderista(String Usuario) {
        // List<Base_user> user = new ArrayList<Base_user>();
        String nombre = "";


        // Select All Query
        String selectQuery = "SELECT "+ ContractUser.Columnas.MERCADERISTA +" FROM " + ContractUser.TABLE_USER  +
                " WHERE " + ContractUser.Columnas.USER + "=? LIMIT 1";

        db=this.getReadableDatabase();
        //  Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery(selectQuery, new String[]{Usuario});


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Base_user u = new Base_user();
                nombre = (cursor.getString(cursor.getColumnIndexOrThrow(ContractUser.Columnas.MERCADERISTA)));

            } while (cursor.moveToNext());
        }

        //   Log.i("SIZE", user.size() + "");

        // return quest list
        return nombre;
    }

    public List<String> getBrandSugeridos(String categoria, String subcategoria, String codigo_pdv, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor = null;
        String selectQuery = "";

        if (subcategoria.equalsIgnoreCase("TODOS")) {
            if (canal.equalsIgnoreCase("MAYORISTA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.MARCA + " FROM " + ContractPrioritario.PRIORITARIO +
                        " WHERE " + ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.CODIGO_PDV + "=? " +
                        " ORDER BY " + ContractPrioritario.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria, codigo_pdv});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("ALMACENES TIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.MARCA + " FROM " + ContractPrioritario.PRIORITARIO +
                        " WHERE " + ContractPrioritario.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.CODIGO_PDV + "=? " +
                        " ORDER BY " + ContractPrioritario.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,codigo_pdv});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("AKI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.MARCA + " FROM " + ContractPrioritario.PRIORITARIO +
                        " WHERE " + ContractPrioritario.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.CODIGO_PDV + "=?" +
                        " ORDER BY " + ContractPrioritario.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,codigo_pdv});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("CORAL HIPERMERCADOS")) {
                selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.MARCA + " FROM " + ContractPrioritario.PRIORITARIO +
                        " WHERE " + ContractPrioritario.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.CODIGO_PDV + "=?" +
                        " ORDER BY " + ContractPrioritario.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,codigo_pdv});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SUPERMAXI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.MARCA + " FROM " + ContractPrioritario.PRIORITARIO +
                        " WHERE " + ContractPrioritario.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.CODIGO_PDV + "=?" +
                        " ORDER BY " + ContractPrioritario.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,codigo_pdv});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("MI COMISARIATO")) {
                selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.MARCA + " FROM " + ContractPrioritario.PRIORITARIO +
                        " WHERE " + ContractPrioritario.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.CODIGO_PDV + "=?" +
                        " ORDER BY " + ContractPrioritario.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,codigo_pdv});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SANTA MARIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.MARCA + " FROM " + ContractPrioritario.PRIORITARIO +
                        " WHERE " + ContractPrioritario.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.CODIGO_PDV + "=?" +
                        " ORDER BY " + ContractPrioritario.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,codigo_pdv});
            }else{
                selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.MARCA + " FROM " + ContractPrioritario.PRIORITARIO +
                        " WHERE " + ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPrioritario.Columnas.CODIGO_PDV + "=? " +
                        " ORDER BY " + ContractPrioritario.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,codigo_pdv});
            }
        }else{
            if (canal.equalsIgnoreCase("MAYORISTA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.MARCA + " FROM " + ContractPrioritario.PRIORITARIO +
                        " WHERE " + /*ContractPrioritario.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'La Universal' AND " +*/
                        ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.SUBCATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.CODIGO_PDV + "=?" +
                        " ORDER BY " + ContractPrioritario.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,codigo_pdv});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("ALMACENES TIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.MARCA + " FROM " + ContractPrioritario.PRIORITARIO +
                        " WHERE " + ContractPrioritario.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.SUBCATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.CODIGO_PDV + "=?" +
                        " ORDER BY " + ContractPrioritario.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,codigo_pdv});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("AKI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.MARCA + " FROM " + ContractPrioritario.PRIORITARIO +
                        " WHERE " + ContractPrioritario.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.SUBCATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.CODIGO_PDV + "=?" +
                        " ORDER BY " + ContractPrioritario.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,codigo_pdv});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("CORAL HIPERMERCADOS")) {
                selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.MARCA + " FROM " + ContractPrioritario.PRIORITARIO +
                        " WHERE " + ContractPrioritario.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.SUBCATEGORIA + "=? AND "  +
                        ContractPrioritario.Columnas.CODIGO_PDV + "=? "+
                        " ORDER BY " + ContractPrioritario.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,codigo_pdv});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SUPERMAXI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.MARCA + " FROM " + ContractPrioritario.PRIORITARIO +
                        " WHERE " + ContractPrioritario.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.SUBCATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.CODIGO_PDV + "=?" +
                        " ORDER BY " + ContractPrioritario.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,codigo_pdv});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("MI COMISARIATO")) {
                selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.MARCA + " FROM " + ContractPrioritario.PRIORITARIO +
                        " WHERE " + ContractPrioritario.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.SUBCATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.CODIGO_PDV + "=? " +
                        " ORDER BY " + ContractPrioritario.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,codigo_pdv});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SANTA MARIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.MARCA + " FROM " + ContractPrioritario.PRIORITARIO +
                        " WHERE " + ContractPrioritario.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.SUBCATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.CODIGO_PDV + "=?" +
                        " ORDER BY " + ContractPrioritario.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,codigo_pdv});
            }else{
                selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.MARCA + " FROM " + ContractPrioritario.PRIORITARIO +
                        " WHERE " + ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.SUBCATEGORIA + "=? AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPrioritario.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPrioritario.Columnas.CODIGO_PDV + "=?" +
                        " ORDER BY " + ContractPrioritario.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,codigo_pdv});
            }
        }

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPrioritario.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getProductosSugeridos(String categoria, String subcategoria, String codigo, String canal, String subcanal, String manufacturer) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor = null;
        String selectQuery = "";


        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,"%"+manufacturer+"%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria, "%" + subcanal + "%"});
        }

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getProductosSugeridos2(String categoria, String subcategoria, String codigo, String canal, String subcanal, String manufacturer) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor = null;
        String selectQuery = "";


        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SEGMENTO + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,"%"+manufacturer+"%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SEGMENTO + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria, "%" + subcanal + "%"});
        }

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SEGMENTO)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getBrandLogros(String categoria, String sector, String fabricante) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";
        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SECTOR +"=? AND " +
                ContractPortafolioProductos.Columnas.CATEGORY +"=? AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?" +
                " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{sector,categoria,"%" + fabricante + "%"});
        //}

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getBrandLogrosFragment(String sector, String fabricante) {
        List<String> operadores = new ArrayList<String>();
        operadores.add("Seleccione");

        Cursor cursor;
        String selectQuery = "";
        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SECTOR +"=? AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?" +
                " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{sector,"%" + fabricante + "%"});
        //}

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getExhLogrosFragment() {
        List<String> operadores = new ArrayList<String>();
        operadores.add("Seleccione");

        Cursor cursor;
        String selectQuery = "";
        selectQuery = "SELECT DISTINCT " + ContractTipoExh.Columnas.EXHIBICION + " FROM " + ContractTipoExh.TABLE_NAME +
                " ORDER BY " + ContractTipoExh.Columnas.EXHIBICION;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{});
        //}

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractTipoExh.Columnas.EXHIBICION)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSector() {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS;

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getSubCanalPDV(String codigo) {
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.SUBCHANNEL + " FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.POS_ID + "=?";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.SUBCHANNEL));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables

        return operadores ;
    }

    public String getSupervisorByUser(String user) {
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.SUPERVISOR + " FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.USER + "=? LIMIT 1";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.SUPERVISOR));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables

        return operadores ;
    }

    public String getSupervisorByCodigo(String codigo) {
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.SUPERVISOR + " FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.POS_ID + "=?";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.SUPERVISOR));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables

        return operadores ;
    }

    public List<String> getSectorShare() {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                " ORDER BY " + ContractPortafolioProductos.Columnas.SECTOR;

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaPDI(String canal) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractPDI.Columnas.CATEGORIA + " FROM " + ContractPDI.PDI +
                " WHERE " + ContractPDI.Columnas.CANAL + "=? " +
                " ORDER BY " + ContractPDI.Columnas.CATEGORIA;

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{canal});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPDI.Columnas.CATEGORIA)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getObjetivoCategoria(String categoria, String canal) {
        String operadores = "%";

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractPDI.Columnas.OBJETIVO + " FROM " + ContractPDI.PDI +
                " WHERE " + ContractPDI.Columnas.CATEGORIA + "=? " +
                " AND " + ContractPDI.Columnas.CANAL + "=? " +
                " ORDER BY " + ContractPDI.Columnas.CATEGORIA;

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{categoria, canal});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = cursor.getString(cursor.getColumnIndexOrThrow(ContractPDI.Columnas.OBJETIVO));
            } while (cursor.moveToNext() && cursor!=null);
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCausalMCI() {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery =
                "SELECT * FROM " + ContractCausalesMCI.CAUSALES_MCI +
                " GROUP BY " + ContractCausalesMCI.Columnas.CAUSAL +
                " ORDER BY " + ContractCausalesMCI.Columnas.CAUSAL;

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractCausalesMCI.Columnas.CAUSAL)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaSugeridos(String codigo_pdv) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.CATEGORIA +
                            " FROM " + ContractPrioritario.PRIORITARIO +
                            " WHERE " + ContractPrioritario.Columnas.CODIGO_PDV + "=? " +
                            " ORDER BY " + ContractPrioritario.Columnas.CATEGORIA;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo_pdv});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPrioritario.Columnas.CATEGORIA)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getTotalUniverso(String codigo, String categoria, String subcategoria) {
        String operadores = "";

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        cal.set(Calendar.DAY_OF_MONTH,1);
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        // Obtenemos la fecha inicio del mes
        String fecha_inicio_mes = formato.format(cal.getTime());
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        // Obtenemos la fecha final del mes
        String  fecha_fin_mes = formato.format(cal.getTime());


        // Select All Query
        String selectQuery = "SELECT "+ ContractInsertShare.Columnas.CTMS_PERCHA + " FROM " + ContractInsertShare.INSERT_SHARE +
                " WHERE " + ContractInsertShare.Columnas.CODIGO + "=? " +
                " AND datetime(substr(fecha, 7, 4) || '-' || substr(fecha, 4, 2) || '-' || substr(fecha, 1, 2))" +
                " BETWEEN ? AND ? AND " +
                ContractInsertShare.Columnas.SECTOR + "=? AND " +
                ContractInsertShare.Columnas.CATEGORIA + "=?" +
                " ORDER BY " + ContractInsertShare.Columnas._ID + " DESC LIMIT 1";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo,fecha_inicio_mes,fecha_fin_mes,categoria,subcategoria});

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertShare.Columnas.CTMS_PERCHA));
            } while (cursor.moveToNext() && cursor!=null);
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getOtros(String codigo, String categoria, String subcategoria) {
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT "+ ContractInsertShare.Columnas.CTMS_MARCA + " FROM " + ContractInsertShare.INSERT_SHARE +
                " WHERE " + ContractInsertShare.Columnas.MARCA_SELECCIONADA + "='Otros' AND " +
                ContractInsertShare.Columnas.CODIGO + "=? AND " +
                ContractInsertShare.Columnas.SECTOR + "=? AND " +
                ContractInsertShare.Columnas.CATEGORIA + "=?" +
                " ORDER BY " + ContractInsertShare.Columnas._ID + " DESC LIMIT 1";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo,categoria,subcategoria});

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertShare.Columnas.CTMS_MARCA));
            } while (cursor.moveToNext() && cursor!=null);
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getTotalCaras(String codigo, String categoria, String subcategoria, String marca) {
        String operadores = "";

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        cal.set(Calendar.DAY_OF_MONTH,1);
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        // Obtenemos la fecha inicio del mes
        String fecha_inicio_mes = formato.format(cal.getTime());
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        // Obtenemos la fecha final del mes
        String  fecha_fin_mes = formato.format(cal.getTime());


        // Select All Query
        String selectQuery = "SELECT "+ ContractInsertShare.Columnas.CTMS_MARCA + " FROM " + ContractInsertShare.INSERT_SHARE +
                " WHERE " + ContractInsertShare.Columnas.CODIGO + "=? " +
                " AND datetime(substr(fecha, 7, 4) || '-' || substr(fecha, 4, 2) || '-' || substr(fecha, 1, 2))" +
                " BETWEEN ? AND ? AND " +
                ContractInsertShare.Columnas.SECTOR + "=? AND " +
                ContractInsertShare.Columnas.CATEGORIA + "=? AND " +
                ContractInsertShare.Columnas.MARCA_SELECCIONADA + "=?" +
                " ORDER BY " + ContractInsertShare.Columnas._ID + " DESC LIMIT 1";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo,fecha_inicio_mes,fecha_fin_mes,categoria,subcategoria,marca});

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertShare.Columnas.CTMS_MARCA));
            } while (cursor.moveToNext() && cursor!=null);
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSector2Promocion(String marca, String categoria, String subcategoria, String presentacion) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                " WHERE " + ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                ContractPortafolioProductos.Columnas.SUBCATEGORIA + "=? AND " +
                ContractPortafolioProductos.Columnas.PRESENTACION + "=?";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{marca,categoria,subcategoria,presentacion});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getMarcaPromocion1(String fabricante, String tipo, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();

        String selectQuery = "";
        Cursor cursor = null;

        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " != ? ";

        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " = ? ";
        }

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA +
                    " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + byFabricante +
                    " AND " + ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Rosado%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Coral%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Favorita%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Maria%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Tia%' " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            // Usar exactamente el fabricante, no con %
            cursor = db.rawQuery(selectQuery, new String[]{fabricante});
        } else {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA +
                    " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + byFabricante +
                    " AND " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{fabricante, "%" + subcanal.trim() + "%"});
        }

        operadores.add("Seleccione");
        if (!tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            operadores.add("Otros");
        }

        if (cursor != null && cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }

        Log.i("MARCA PROMO 1", "Fabricante: " + fabricante + ", Marcas: " + (operadores.size() - 2));

        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }

        return operadores;
    }


    public List<String> getMarcaPromocion( String tipo, String fabricante, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();

       // Log.i("CATEGORIA", categoria);
        String selectQuery = "";
        Cursor cursor = null;
        // Select All Query
//        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
//                " WHERE " + ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
//                ContractPortafolioProductos.Columnas.CATEGORY + "=?" +
//                " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
//
//        db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria});

        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";

        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
        }

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                   // ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                   // ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Rosado%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Coral%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Favorita%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Maria%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Tia%' " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        } else {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                  //  ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                  //  ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%",  "%" + subcanal.trim() + "%"});
        }

        operadores.add("Seleccione");
        if (!tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            operadores.add("Otros");
        }
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }



//    public List<String> getMarcaCodificados(String categoria, String tipo, String fabricante, String canal, String subcanal) {
//        List<String> operadores = new ArrayList<String>();
//
//        Log.i("CATEGORIA", categoria);
//        String selectQuery = "";
//        Cursor cursor = null;
//        // Select All Query
////        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
////                " WHERE " + ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
////                ContractPortafolioProductos.Columnas.CATEGORY + "=?" +
////                " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
////
////        db = this.getReadableDatabase();
////        Cursor cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria});
//
//        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
//
//        /*
//        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
//            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
//        }*/
//
//        if (canal.equalsIgnoreCase("MAYORISTA")) {
//            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
//                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
//                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
//                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Rosado%' AND " +
//                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Coral%' AND " +
//                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Favorita%' AND " +
//                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Maria%' AND " +
//                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Tia%' " +
//                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
//            db = this.getReadableDatabase();
//            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
//        } else {
//            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
//                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
//                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
//                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
//                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
//            db = this.getReadableDatabase();
//            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, "%" + subcanal.trim() + "%"});
//        }
//
//        operadores.add("Seleccione");
//    //    operadores.add("Otros");
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
//            } while (cursor.moveToNext() && cursor!=null);
//        }
//        Log.i("OPERADORES",operadores.size()+"");
//        // closing connection
//        cursor.close();
//        db.close();
//        // returning lables
//        return operadores;
//    }


    public List<String> getMarcaCodificados(String categoria, String tipo, String fabricante, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor = null;
        String selectQuery = "";
        // Select All Query

        Log.i("CATEGORIA", categoria);
        Log.i("fabricante", fabricante);
        Log.i("CANAL", canal);
        Log.i("CADENA", subcanal);

        operadores.add("Seleccione");

    //    String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";
        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
/*

        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
        }
*/
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Rosado%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Coral%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Favorita%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Maria%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Tia%' " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, "%" + subcanal.trim() + "%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Rosado%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Coral%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Favorita%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Maria%' AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Tia%' " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
        }

        // Select All Query
        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
                Log.i("marca",""+cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }




    public List<String> getSKUPromocion2( String marca, String tipo, String fabricante, String canal, String subcanal) {

        List<String> operadores = new ArrayList<String>();
        operadores.add("Seleccione");

        // Select All Query
//        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
//                " WHERE " + ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
//                ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
//                ContractPortafolioProductos.Columnas.MARCA + "=?" +
//                " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
//
//        db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, marca});

        String selectQuery = "";
        Cursor cursor = null;

        if (!marca.equalsIgnoreCase("OTROS")){
            String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";

            if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
                byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
            }

            if (canal.equalsIgnoreCase("MAYORISTA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " +
                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                       // ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                       // ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                        ContractPortafolioProductos.Columnas.MARCA + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", marca});
            }else{
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " +
                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                       // ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                       // ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                        ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                        ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", marca, "%" + subcanal.trim() + "%"});
            }

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
                } while (cursor.moveToNext() && cursor!=null);
            }
            Log.i("OPERADORES",operadores.size()+"");
            // closing connection
            cursor.close();
            db.close();

        } else {
            if (!marca.equalsIgnoreCase("Seleccione")) {
                operadores.add("Otros");

            }
        }




        // returning lables
        return operadores;
    }

    public List<String> getCategoriaPromocion(String tipo, String fabricante, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
//        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS;
//
//        db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{});

        Cursor cursor = null;
        String selectQuery = null;

        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";

        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
        }

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        } else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            Log.i("opc1","" +"ENTROOOO");

            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + byFabricante + " AND " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", "%" + subcanal.trim() + "%"});
            Log.i("opc1","" +"ENTROOOO"+ selectQuery);
        } else {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
            Log.i("opc1","" +"ENTROOOO2222"+ selectQuery);

        }

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSectorExhibicion() {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS;

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        ////operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaFlooring(String codigo, String canal, String subcanal, String manufacturer) {
        List<String> operadores = new ArrayList<String>();
        //format = "Cruz Azul";

        // Select All Query
        Cursor cursor = null;
        String selectQuery = null;
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{manufacturer});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + subcanal + "%","%" + manufacturer + "%"});
        }

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaFlooring2(String codigo, String canal, String subcanal, String manufacturer) {
        List<String> operadores = new ArrayList<String>();
        //format = "Cruz Azul";

        // Select All Query
        Cursor cursor = null;
        String selectQuery = null;
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SECTOR;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + manufacturer + "%"});

        }else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SECTOR;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + subcanal + "%"});
        }else {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SECTOR;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + manufacturer + "%"});

        }

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getMotivoSugerido() {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractMotivoSugerido.Columnas.MOTIVO + " FROM " + ContractMotivoSugerido.TABLE_NAME;

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractMotivoSugerido.Columnas.MOTIVO)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES MOT",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaImpulso(String manufacturer) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS
                + " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{"%" + manufacturer + "%"});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSubCategoriaImpulso(String categoria, String manufacturer) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CATEGORY + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS
                + " WHERE " + ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{categoria, "%" + manufacturer + "%"});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CATEGORY)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getResponsablesValores(String nom_comercial) {
        List<String> operadores = new ArrayList<String>();
        //format = "Cruz Azul";

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractPromociones.Columnas.TIPO + " FROM " + ContractPromociones.PROMOCIONES + " WHERE " +
                ContractPromociones.Columnas.CANAL + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{nom_comercial});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPromociones.Columnas.TIPO)));
                Log.i("TIPO",cursor.getString(cursor.getColumnIndexOrThrow(ContractPromociones.Columnas.TIPO)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getResponsablesOSA(String canal) {
        List<String> operadores = new ArrayList<String>();
        //format = "Cruz Azul";

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractCausalesOSA.Columnas.RESPONSABLE + " FROM " + ContractCausalesOSA.CAUSALES_OSA + " WHERE " +
                ContractCausalesOSA.Columnas.CANAL + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{canal});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractCausalesOSA.Columnas.RESPONSABLE)));
                Log.i("TIPO",cursor.getString(cursor.getColumnIndexOrThrow(ContractCausalesOSA.Columnas.RESPONSABLE)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES RESPONSABLES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getRazonesOSA(String canal, String responsable) {
        List<String> operadores = new ArrayList<String>();
        //format = "Cruz Azul";

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractCausalesOSA.Columnas.CAUSAL + " FROM " + ContractCausalesOSA.CAUSALES_OSA + " WHERE " +
                ContractCausalesOSA.Columnas.CANAL + "=? AND " +
                ContractCausalesOSA.Columnas.RESPONSABLE + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{canal, responsable});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractCausalesOSA.Columnas.CAUSAL)));
                Log.i("TIPO",cursor.getString(cursor.getColumnIndexOrThrow(ContractCausalesOSA.Columnas.CAUSAL)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES CAUSALES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getRazonesValores(String nom_comercial, String responsable) {
        List<String> operadores = new ArrayList<String>();
        //format = "Cruz Azul";

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractPromociones.Columnas.DESCRIPCION + " FROM " + ContractPromociones.PROMOCIONES + " WHERE " +
                ContractPromociones.Columnas.CANAL + "=? AND " +
                ContractPromociones.Columnas.TIPO + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{nom_comercial, responsable});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPromociones.Columnas.DESCRIPCION)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaValores(String codigo, String canal, String subcanal, String manufacturer) {
        List<String> operadores = new ArrayList<String>();
        //format = "Cruz Azul";

        // Select All Query
        Cursor cursor = null;
        String selectQuery = null;
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{manufacturer});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + subcanal + "%","%" + manufacturer + "%"});
        }

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaValores2(String codigo, String canal, String subcanal, String manufacturer) {
        List<String> operadores = new ArrayList<String>();
        //format = "Cruz Azul";

        // Select All Query
        Cursor cursor = null;
        String selectQuery = null;
        if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + subcanal + "%", "%"+manufacturer+"%"});
        } else {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%"+manufacturer+"%"});
        }

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaSugeridos(String codigo, String canal, String subcanal, String manufacturer) {
        List<String> operadores = new ArrayList<String>();
        //format = "Cruz Azul";

        // Select All Query
        Cursor cursor = null;
        String selectQuery = null;
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%"+manufacturer+"%"});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + subcanal + "%"});
        }

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


    public List<String> getSectorLogros(String fabricante) {
        List<String> operadores = new ArrayList<String>();
        //format = "Cruz Azul";
        operadores.add("Seleccione");
        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                            " WHERE " +ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaPrecios(String tipo, String fabricante, String codigo, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();
        //format = "Cruz Azul";
        Log.i("DAVID5", tipo);
        Log.i("DAVID5", fabricante);
        Log.i("DAVID5", canal);
        Log.i("DAVID5", subcanal);
        // Select All Query
        String selectQuery = null;
        Cursor cursor = null;

        operadores.add("Seleccione");

        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";

        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
        }

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        } else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + byFabricante + " AND " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", "%" + subcanal.trim() + "%"});
        } else {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES_CATEGORIA",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


    public List<String> getCategoriaCodificados(String tipo, String fabricante, String codigo, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();
        //format = "Cruz Azul";
        Log.i("CANAL", canal);
        Log.i("SUBCANAL", subcanal);
        // Select All Query
        String selectQuery = null;
        Cursor cursor = null;

        operadores.add("Seleccione");

        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";

        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
        }

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        } else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + byFabricante + " AND " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", "%" + subcanal.trim() + "%"});
        } else {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES_CATEGORIA",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


    public String getCategoryCodificados(String sku) {
        String id = "";
        String query = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SKU +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{sku});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CATEGORY));
        }

        cursor.close();
        db.close();
        return id;
    }


// CAMBIO PARA ELEJIR POR MARCA PARA GESTION PDV mpin

    public List<String> getCategoriaEvidencia(String fabricante){
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";

        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " IS NOT NULL AND " +
                ContractPortafolioProductos.Columnas.MARCA + " !='' AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ORDER BY " + ContractPortafolioProductos.Columnas.MARCA + " ASC";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSubCategoriaEvidencia(String fabricante,String categoria){
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";

        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CATEGORY + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + ContractPortafolioProductos.Columnas.SECTOR + " IS NOT NULL AND " +
                ContractPortafolioProductos.Columnas.SECTOR + " !='' AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? AND " +
                ContractPortafolioProductos.Columnas.SECTOR + " LIKE ? ORDER BY " + ContractPortafolioProductos.Columnas.CATEGORY + " ASC";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%","%" + categoria + "%"});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CATEGORY)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }



    public List<String> getCategoriaProdCad(String fabricante) {
        List<String> operadores = new ArrayList<String>();
        //format = "Cruz Azul";

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CATEGORY + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                            ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CATEGORY)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getTipoComboCanjes() {
        List<String> operadores = new ArrayList<String>();
        Cursor cursor;
        String selectQuery = "SELECT DISTINCT " + ContractComboCanjes.Columnas.TIPO_COMBO +
                " FROM " + ContractComboCanjes.COMBO_CANJES +
                " ORDER BY " + ContractComboCanjes.Columnas.TIPO_COMBO;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{});
        //}

        operadores.add("Seleccione Combo");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractComboCanjes.Columnas.TIPO_COMBO)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getMecanica(String tipo_combo) {
        List<String> operadores = new ArrayList<String>();
        Cursor cursor;
        String selectQuery = "SELECT DISTINCT " + ContractComboCanjes.Columnas.MECANICA +
                " FROM " + ContractComboCanjes.COMBO_CANJES +
                " WHERE " + ContractComboCanjes.Columnas.TIPO_COMBO + "=? " +
                " ORDER BY " + ContractComboCanjes.Columnas.MECANICA;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{tipo_combo});
        //}

        operadores.add("Seleccione Mecanica");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractComboCanjes.Columnas.MECANICA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaRotacion(String tipo) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";

        /*if (sector.equalsIgnoreCase("TODOS")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CATEGORY + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{});
        }else{*/
        selectQuery = "SELECT DISTINCT " + ContractRotacion.Columnas.CATEGORIA +
                " FROM " + ContractRotacion.ROTACION +
                " WHERE " + ContractRotacion.Columnas.TIPO + "=? " +
                " ORDER BY " + ContractRotacion.Columnas.CATEGORIA;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{tipo});
        //}

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractRotacion.Columnas.CATEGORIA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getAlertas() {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";

        selectQuery = "SELECT DISTINCT " + ContractAlertas.Columnas.TIPO_ALERTA +
                " FROM " + ContractAlertas.ALERTAS +
                " ORDER BY " + ContractAlertas.Columnas.TIPO_ALERTA;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{});
        //}

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractAlertas.Columnas.TIPO_ALERTA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSubcategoriaRotacion(String tipo, String categoria) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";

        selectQuery = "SELECT DISTINCT " + ContractRotacion.Columnas.SUBCATEGORIA +
                " FROM " + ContractRotacion.ROTACION +
                " WHERE " + ContractRotacion.Columnas.TIPO + "=? AND " +
                ContractRotacion.Columnas.CATEGORIA + "=? " +
                " ORDER BY " + ContractRotacion.Columnas.CATEGORIA;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{tipo, categoria});
        //}

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractRotacion.Columnas.SUBCATEGORIA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getTipoAlerta(String alerta) {
        List<String> operadores = new ArrayList<String>();

        operadores.add("Seleccione");
        operadores.add("Ingreso");
        operadores.add("Solicitud");

        return operadores;
    }

    public List<String> getTipoEjecucionMateriales(String alerta) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";

        selectQuery = "SELECT DISTINCT " + ContractAlertas.Columnas.CATEGORIA +
                " FROM " + ContractAlertas.ALERTAS +
                " WHERE " + ContractAlertas.Columnas.TIPO_ALERTA + " LIKE ?" +
                " ORDER BY " + ContractAlertas.Columnas.CATEGORIA;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{"%" + alerta + "%"});
        //}

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractAlertas.Columnas.CATEGORIA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getMarcaRotacion(String tipo, String categoria, String subcategoria) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";

        selectQuery = "SELECT DISTINCT " + ContractRotacion.Columnas.MARCA +
                " FROM " + ContractRotacion.ROTACION +
                " WHERE " + ContractRotacion.Columnas.TIPO + "=? AND " +
                ContractRotacion.Columnas.CATEGORIA + "=? AND " +
                ContractRotacion.Columnas.SUBCATEGORIA + "=? " +
                " ORDER BY " + ContractRotacion.Columnas.MARCA;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{tipo, categoria, subcategoria});
        //}

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractRotacion.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaPacks(String fabricante) {
        List<String> operadores = new ArrayList<String>();
        //format = "Cruz Azul";
        operadores.add("Seleccione");
        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaShare(String categoria) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor = null;
        String selectQuery = "";

        operadores.add("Seleccione");

  /*      if (categoria.equalsIgnoreCase("Pastas") || categoria.equalsIgnoreCase("Cuidado de la Piel")) {
            operadores.add("Todos");*/

        if (categoria.equalsIgnoreCase("Pastas")) {
            operadores.add("Todos");
            operadores.add("Especiales");

        }else if (categoria.equalsIgnoreCase("Cuidado de la Piel")) {
            operadores.add("Todos");
        }else{
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CATEGORY + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.CATEGORY;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria});
            if (cursor.moveToFirst()) {
                do {
                    operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CATEGORY)));
                } while (cursor.moveToNext());
            }
            // closing connection
            cursor.close();
            db.close();
        }

        //operadores.add("Todos");
        // looping through all rows and adding to list

        // returning lables
        return operadores;
    }

    public List<String> getSubcategoriaSms(String categoria, String codigo_pdv) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor = null;
        String selectQuery = "";

            selectQuery = "SELECT DISTINCT " + ContractPrioritario.Columnas.SUBCATEGORIA +
                    " FROM " + ContractPrioritario.PRIORITARIO +
                    " WHERE " + ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                    ContractPrioritario.Columnas.CODIGO_PDV + "=?" +
                    " ORDER BY " + ContractPrioritario.Columnas.SUBCATEGORIA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, codigo_pdv});

        operadores.add("Seleccione");

        if (cursor.moveToFirst()) {
                do {
                    operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPrioritario.Columnas.SUBCATEGORIA)));
                } while (cursor.moveToNext());
            }
            // closing connection
            cursor.close();
            db.close();

        //operadores.add("Todos");
        // looping through all rows and adding to list

        // returning lables
        return operadores;
    }

    public List<String> getSegmentoShare(String sector, String categoria) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";
        /*if (categoria.equalsIgnoreCase("TODOS") && subcategoria.equalsIgnoreCase("TODOS")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SEGMENTO + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{});
        }else if (!categoria.equalsIgnoreCase("TODOS") && subcategoria.equalsIgnoreCase("TODOS")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SEGMENTO + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO + " WHERE " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria});
        }else if (categoria.equalsIgnoreCase("TODOS") && !subcategoria.equalsIgnoreCase("TODOS")) {
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SEGMENTO + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO + " WHERE " +
                    ContractPortafolioProductos.Columnas.CATEGORIA + "=?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{subcategoria});
        }else{*/
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SEGMENTO + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{sector, categoria});
        //}

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SEGMENTO)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaPromocion(String marca) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CATEGORY  + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.MARCA + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{marca});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CATEGORY)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaExhibicion(String tipo, String fabricante) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";

        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";

        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
        }

        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SECTOR + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    //AQUI

    public List<String> getTipoExhibicion() {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";


        selectQuery = "SELECT DISTINCT " + ContractTipoExh.Columnas.EXHIBICION + " FROM " + ContractTipoExh.TABLE_NAME;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractTipoExh.Columnas.EXHIBICION)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaTipo() {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";


        selectQuery = "SELECT DISTINCT " + ContractCategoriaTipo.Columnas.TIPO + " FROM " + ContractCategoriaTipo.CATEGORIA_TIPO;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractCategoriaTipo.Columnas.TIPO)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


    public List<String> getSubcategoriaFlooring(String categoria, String codigo, String canal, String subcanal, String manufacturer) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";


        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CATEGORY + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, "%" + manufacturer + "%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CATEGORY + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, "%" + subcanal + "%", "%" + manufacturer + "%"});
        }

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CATEGORY)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSubcategoriaFlooring2(String categoria, String codigo, String canal, String subcanal, String manufacturer) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";

        if (canal.equalsIgnoreCase("MAYORISTA") || canal.equalsIgnoreCase("DROGUERIAS")) {
            if (categoria.equalsIgnoreCase("INSECTICIDAS")) {
                selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CATEGORY + " FROM " +
                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND (" +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? OR " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + "='Sc Jhonson')" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.CATEGORY;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria, "%" + manufacturer + "%"});
            } else {
                selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CATEGORY + " FROM " +
                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.CATEGORY;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria, "%" + manufacturer + "%"});
            }
        }else{
            selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CATEGORY + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.CATEGORY;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, "%" + subcanal + "%"});
        }

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CATEGORY)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSubcategoriaPrecios(String categoria, String tipo, String fabricante, String codigo, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor = null;
        String selectQuery = "";
        // Select All Query

        operadores.add("Seleccione");

        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";

        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
        }

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CATEGORY + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CATEGORY + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, "%" + subcanal.trim() + "%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CATEGORY + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
        }

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CATEGORY)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSubcategoriaProdCad(String fabricante, String categoria) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQueryTodos = "", selectQuery = "";
        // Select All Query
        /*if (sector.equalsIgnoreCase("TODOS")) {
            selectQueryTodos = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CATEGORY + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQueryTodos, new String[]{});
        }else{*/
        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SUBCATEGORIA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{categoria,"%" + fabricante + "%"});
        //}

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SUBCATEGORIA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSubcategoriaPacks(String fabricante, String categoria) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQueryTodos = "", selectQuery = "";
        // Select All Query
        /*if (sector.equalsIgnoreCase("TODOS")) {
            selectQueryTodos = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CATEGORY + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQueryTodos, new String[]{});
        }else{*/
        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SUBCATEGORIA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{categoria,"%" + fabricante + "%"});
        //}

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SUBCATEGORIA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSubCategoriaValores(String categoria, String codigo, String canal, String subcanal, String manufacturer) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";


        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CATEGORY + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, "%" + manufacturer + "%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CATEGORY + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, "%" + subcanal + "%", "%" + manufacturer + "%"});
        }

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CATEGORY)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSubCategoriaSugeridos(String categoria, String codigo, String canal, String subcanal, String manufacturer) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor = null;
        String selectQuery = "";


        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CATEGORY + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria,"%"+manufacturer+"%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CATEGORY + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, "%" + subcanal + "%"});
        }

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CATEGORY)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getContenidoSugeridos(String categoria, String subcategoria, String brand, String codigo_pdv, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor = null;
        String selectQuery = "";


        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT " + ContractPrioritario.Columnas.CONTENIDO + " FROM " +
                    ContractPrioritario.PRIORITARIO + " WHERE " +
                    ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                    ContractPrioritario.Columnas.SUBCATEGORIA + "=? AND " +
                    ContractPrioritario.Columnas.MARCA + "=? AND " +
                    ContractPrioritario.Columnas.CODIGO_PDV + "=?" +
                    " ORDER BY " + ContractPrioritario.Columnas.CONTENIDO;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, codigo_pdv});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.CONTENIDO + " FROM " +
                    ContractPrioritario.PRIORITARIO + " WHERE " +
                    ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                    ContractPrioritario.Columnas.SUBCATEGORIA + "=? AND " +
                    ContractPrioritario.Columnas.MARCA + "=? AND " +
                    ContractPrioritario.Columnas.CODIGO_PDV + "=?" +
                    " ORDER BY " + ContractPrioritario.Columnas.CONTENIDO;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, codigo_pdv});
        }

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPrioritario.Columnas.CONTENIDO)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getBrandValores3(String categoria, String subcategoria, String codigo, String canal, String subcanal, String manufacturer) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor = null;
        String selectQuery = "";

        Log.i("CANAL-OSA", canal);
        Log.i("SUBCANAL-OSA", subcanal);
        Log.i("CATEGORIA-OSA", categoria);

        if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, "%" + subcanal + "%", "%"+manufacturer+"%"});
        } else {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria,"%"+manufacturer+"%"});
        }

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSubCategoriaValores2(String categoria, String codigo, String canal, String subcanal, String manufacturer) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor = null;
        String selectQuery = "";

        Log.i("CANAL-OSA", canal);
        Log.i("SUBCANAL-OSA", subcanal);
        Log.i("CATEGORIA-OSA", categoria);

        if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CATEGORY + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, "%" + subcanal + "%", "%"+manufacturer+"%"});
        } else {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.CATEGORY + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, "%"+manufacturer+"%"});
        }

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CATEGORY)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getCategoriaLogros(String sector, String fabricante) {
        List<String> operadores = new ArrayList<String>();

        Cursor cursor;
        String selectQuery = "";

        // Select All Query
        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.CATEGORY  + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                ContractPortafolioProductos.Columnas.SECTOR +"=? AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{sector,"%" + fabricante + "%"});
        //}

        //operadores.add("Todos");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.CATEGORY)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    //Para consumo en Share of Display
    /*public List<String> filtrarListProductos2Share(String sector, String categoria, String segmento) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS_MAYO + " WHERE "+
                ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                ContractPortafolioProductos.Columnas.SEGMENTO + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{sector,categoria,segmento});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }

        Log.i("SHARE",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }*/
    public ArrayList<BasePortafolioProductos> filtrarListProductos2Share(String categoria, String subcategoria, String canal, String subcanal) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<BasePortafolioProductos>();

        Log.i("CANAL", canal);
        Log.i("SUBCANAL", subcanal);
        Cursor cursor = null;
        String selectQuery = "";

        if (subcategoria.equalsIgnoreCase("TODOS")) {
            if (canal.equalsIgnoreCase("MAYORISTA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("ALMACENES TIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("AKI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("CORAL HIPERMERCADOS")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SUPERMAXI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("MI COMISARIATO")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SANTA MARIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria});
            }else{
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal'" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria});
            }
        }else{
            if (canal.equalsIgnoreCase("MAYORISTA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("ALMACENES TIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("AKI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("CORAL HIPERMERCADOS")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SUPERMAXI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("MI COMISARIATO")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SANTA MARIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria});
            }else{
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Frac' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Muuu' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Milo' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Nestle' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'La Universal'" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria});
            }
        }

        BasePortafolioProductos bpv_adicionales = new BasePortafolioProductos();
        BasePortafolioProductos bpv_adicionales2 = new BasePortafolioProductos();
        if (categoria.equalsIgnoreCase("Salsas") && subcategoria.equalsIgnoreCase("Salsa Roja")) {
            bpv_adicionales.setMarca("Gustadina");
            bpv_adicionales2.setMarca("Facundo");
            operadores.add(bpv_adicionales);
            operadores.add(bpv_adicionales2);
        }

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setMarca(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }

        Log.i("SHARE",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BasePortafolioProductos> filtrarListProductosPDI(String categoria, String canal) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<BasePortafolioProductos>();

        Log.i("CANAL", canal);
        Cursor cursor = null;
        String selectQuery = "SELECT * FROM " + ContractPDI.PDI +
                " WHERE " + ContractPDI.Columnas.CATEGORIA + "=? AND " +
                ContractPDI.Columnas.CANAL + "=?" +
//                " GROUP BY " + ContractPDI.Columnas.MARCA +
                " ORDER BY " + ContractPDI.Columnas.MARCA;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{categoria, canal});


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                String marca = cursor.getString(cursor.getColumnIndexOrThrow(ContractPDI.Columnas.MARCA)) + " " + cursor.getString(cursor.getColumnIndexOrThrow(ContractPDI.Columnas.SUBCATEGORIA));
                bpv.setMarca(marca);
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }

        Log.i("SHARE",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> filtrarListProductos4Promocion() {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        Log.i("OPERADORES CUMPLIMIENTO",operadores.size()+"");
        // returning lables
        return operadores;
    }
//se quita el filtro de categoria y subcategoria para promociones mpin
    public String getManufacturerPromocion( String marca) {
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.FABRICANTE + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
              //  ContractPortafolioProductos.Columnas.SECTOR +"=? AND " +
              //  ContractPortafolioProductos.Columnas.CATEGORY +"=? AND " +
                ContractPortafolioProductos.Columnas.MARCA +"=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{marca});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = (cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FABRICANTE)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public boolean faltaSalida(String codigo, String user, String fecha){
        boolean id = true;
        String query = "SELECT * FROM " + ContractInsertGps.INSERT_GPS + " WHERE " +
                ContractInsertGps.Columnas.IDPDV +" =? AND " +
                ContractInsertGps.Columnas.USUARIO +" =? AND " +
                ContractInsertGps.Columnas.FECHA +" =? AND " +
                ContractInsertGps.Columnas.TIPO +" ='SALIDA'";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{codigo, user, fecha});

        if(cursor != null && cursor.moveToFirst() && !codigo.equalsIgnoreCase(Constantes.NODATA)){
            id = false;
        }

        cursor.close();
        db.close();
        return id;
    }

    public boolean tieneEntrada(String codigo, String user, String fecha){
        boolean id = false;
        String query = "SELECT * FROM " + ContractInsertGps.INSERT_GPS + " WHERE " +
                ContractInsertGps.Columnas.IDPDV +" =? AND " +
                ContractInsertGps.Columnas.USUARIO +" =? AND " +
                ContractInsertGps.Columnas.FECHA +" =? AND " +
                ContractInsertGps.Columnas.TIPO +" ='ENTRADA' ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{codigo, user, fecha});

        if(cursor != null && cursor.moveToFirst()){
            id = true;
        }

        cursor.close();
        db.close();
        return id;
    }

    public int contadorNovedades(String codigo, String user, String fecha){

        int count = 0;
        String query = "SELECT COUNT(*) FROM " + ContractInsertNovedades.INSERT_NOVEDADES + " WHERE " +
                ContractInsertNovedades.Columnas.CODIGO +" =? AND " +
                ContractInsertNovedades.Columnas.USUARIO +" =? AND " +
                ContractInsertNovedades.Columnas.FECHA +" =? ";
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{codigo, user, fecha});
        if(cursor != null && cursor.moveToFirst()){
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }

    public String getSegmentacionMenu() {
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.SEGMENTACION + " FROM " + ContractPharmaValue.POS;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = (cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.SEGMENTACION)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getComprasMenu() {
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPharmaValue.Columnas.COMPRAS + " FROM " + ContractPharmaValue.POS;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = (cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.COMPRAS)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getSKUPromocion(String sector, String categoria, String subcategoria, String presentacion, String marca) {
        String operadores = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                ContractPortafolioProductos.Columnas.SECTOR +"=? AND " +
                ContractPortafolioProductos.Columnas.CATEGORY +"=? AND " +
                ContractPortafolioProductos.Columnas.SUBCATEGORIA +"=? AND " +
                ContractPortafolioProductos.Columnas.PRESENTACION +"=? AND " +
                ContractPortafolioProductos.Columnas.MARCA +"=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{sector,categoria,subcategoria,presentacion,marca});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = (cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }
/*
    public ArrayList<String> filtrarMarcaExhibicion(String categoria ,String subcategoria,String tipo, String fabricante, String canal, String subcanal) {
        ArrayList<String> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = "";


        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";

        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
        }

        if (subcategoria.equalsIgnoreCase("")) {
            if (canal.equalsIgnoreCase("MAYORISTA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("ALMACENES TIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND "
                        + byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("AKI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND "
                        + byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("CORAL HIPERMERCADOS")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SUPERMAXI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("MI COMISARIATO")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SANTA MARIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else{
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }
        }else{
            if (canal.equalsIgnoreCase("MAYORISTA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("ALMACENES TIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("AKI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("CORAL HIPERMERCADOS")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SUPERMAXI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("MI COMISARIATO")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SANTA MARIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else{
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }
        }

        String header = "HEADER FOR RECYCLERVIEW";
        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }
*/

    public ArrayList<String> filtrarMarcaPrecios(String subcategoria,String tipo, String fabricante, String canal, String subcanal) {
        ArrayList<String> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = "";


        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";

        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
        }


        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                    byFabricante +
                    // ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("ALMACENES TIA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                    byFabricante +
                    // ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("AKI")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                    byFabricante +
                    //  ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("CORAL HIPERMERCADOS")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                    byFabricante +
                    //   ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SUPERMAXI")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                    byFabricante +
                    // ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("MI COMISARIATO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                    byFabricante +
                    // ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SANTA MARIA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                    byFabricante +
                    //  ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + byFabricante +
                    // ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }


        String header = "HEADER FOR RECYCLERVIEW";
        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<String> filtrarMarcaExhibicion(String subcategoria,String tipo, String fabricante, String canal, String subcanal) {
        ArrayList<String> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = "";


        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";

        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
        }


        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                    byFabricante +
                   // ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("ALMACENES TIA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                    byFabricante +
                   // ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("AKI")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                    byFabricante +
                  //  ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("CORAL HIPERMERCADOS")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                    byFabricante +
                 //   ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SUPERMAXI")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                    byFabricante +
                   // ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("MI COMISARIATO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                    byFabricante +
                   // ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SANTA MARIA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                    ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                    byFabricante +
                  //  ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                    " WHERE " + byFabricante +
                   // ContractPortafolioProductos.Columnas.SECTOR + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%"});
        }


        String header = "HEADER FOR RECYCLERVIEW";
        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BasePortafolioProductos> filtrarListProductos3Exhibicion(String categoria, String subcategoria, String tipo, String fabricante, String canal, String subcanal) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = "";

        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";

        if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
        }

        if (subcategoria.equalsIgnoreCase("TODOS")) {
            if (canal.equalsIgnoreCase("MAYORISTA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("ALMACENES TIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND "
                        + byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("AKI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND "
                        + byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("CORAL HIPERMERCADOS")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SUPERMAXI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("MI COMISARIATO")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SANTA MARIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }else{
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria});
            }
        }else{
            if (canal.equalsIgnoreCase("MAYORISTA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, subcategoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("ALMACENES TIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, subcategoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("AKI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, subcategoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("CORAL HIPERMERCADOS")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, subcategoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SUPERMAXI")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, subcategoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("MI COMISARIATO")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Santa Maria' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, subcategoria});
            }else if (canal.equalsIgnoreCase("AUTOSERVICIO") && subcanal.equalsIgnoreCase("SANTA MARIA")) {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + ContractPortafolioProductos.Columnas.MARCA + " <> 'Ta´Riko' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Coral' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Aki' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mi Comisariato' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Herbal Supermaxi' AND " +
                        ContractPortafolioProductos.Columnas.MARCA + " <> 'Mayik' AND " +
                        byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, subcategoria});
            }else{
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                        " WHERE " + byFabricante + " AND " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.MARCA;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{"%" + fabricante + "%", categoria, subcategoria});
            }
        }

        String header = "HEADER FOR RECYCLERVIEW";
        operadores.add(new BasePortafolioProductos(header));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BasePortafolioProductos> filtrarListProductos4Flooring(String categoria, String subcategoria, String brand,
                                                                            String codigo, String canal, String subcanal, String manufacturer) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<BasePortafolioProductos>();

        Log.i("CATEGORIA", categoria);
        Log.i("SUBCATEGORIA", subcategoria);
        Log.i("MARCA", brand);

        Cursor cursor = null;
        String selectQuery = "";

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            if (brand.equalsIgnoreCase("SURF")) {
                Log.i("MARCA SURF", "SII "+ brand);
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " +
                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                        ContractPortafolioProductos.Columnas.MARCA + "=? AND (" +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? OR " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + "='Unilever S.A') AND (" +
                        ContractPortafolioProductos.Columnas.CONTENIDO + "= '400 gr' OR " +
                        ContractPortafolioProductos.Columnas.CONTENIDO + "= '500 gr') " +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, "%" + manufacturer + "%"});
                Log.i("RESULTADO CONSULTA", String.valueOf(cursor));
            } else {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " +
                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                        ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, "%" + manufacturer + "%"});
            }
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, "%" + subcanal.trim() + "%"});
        }

        // looping through all rows and adding to list
//        BasePortafolioProductos bpv_test = new BasePortafolioProductos();
//        bpv_test.setSku("TEST");
//        operadores.add(bpv_test);
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }
    public ArrayList<BasePortafolioProductos> filtrarListProductos6Flooring(String brand, String codigo,
                                                                            String canal, String subcanal,
                                                                            String manufacturer) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<BasePortafolioProductos>();

        Log.i("MARCA", brand);

        Cursor cursor = null;
        String selectQuery = "";

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            if (brand.equalsIgnoreCase("SURF")) {
                Log.i("MARCA SURF", "SII "+ brand);
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " +
                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                        ContractPortafolioProductos.Columnas.MARCA + "=? AND (" +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? OR " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + "='Unilever S.A') AND (" +
                        ContractPortafolioProductos.Columnas.CONTENIDO + "= '400 gr' OR " +
                        ContractPortafolioProductos.Columnas.CONTENIDO + "= '500 gr') " +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{brand, "%" + manufacturer + "%"});
                Log.i("RESULTADO CONSULTA", String.valueOf(cursor));
            } else {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " +
                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                        ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{brand, "%" + manufacturer + "%"});
            }
        } else {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{brand, "%" + subcanal.trim() + "%"});
        }

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


    public ArrayList<BasePortafolioProductos> filtrarListProductos5Flooring(String categoria, String subcategoria, String brand, String gramaje,
                                                                            String codigo, String canal, String subcanal, String manufacturer) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<BasePortafolioProductos>();

        Log.i("CATEGORIA", categoria);
        Log.i("SUBCATEGORIA", subcategoria);
        Log.i("MARCA", brand);
        Log.i("GRAMAJE", gramaje);

        Cursor cursor = null;
        String selectQuery = "";

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            if (brand.equalsIgnoreCase("SURF")) {
                Log.i("MARCA SURF", "SII "+ brand);
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " +
                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                        ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                        ContractPortafolioProductos.Columnas.CONTENIDO + "=? AND (" +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? OR " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + "='Unilever S.A') AND (" +
                        ContractPortafolioProductos.Columnas.CONTENIDO + "= '400 gr' OR " +
                        ContractPortafolioProductos.Columnas.CONTENIDO + "= '500 gr') " +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, gramaje, "%" + manufacturer + "%"});
                Log.i("RESULTADO CONSULTA", String.valueOf(cursor));
            } else {
                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " +
                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                        ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                        ContractPortafolioProductos.Columnas.CONTENIDO + "=? AND " +
                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                        " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
                db = this.getReadableDatabase();
                cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, gramaje, "%" + manufacturer + "%"});
            }
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.CONTENIDO + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, gramaje, "%" + subcanal.trim() + "%"});
        }

        // looping through all rows and adding to list
//        BasePortafolioProductos bpv_test = new BasePortafolioProductos();
//        bpv_test.setSku("TEST");
//        operadores.add(bpv_test);
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


//    public ArrayList<BasePortafolioProductos> filtrarListProductos3Flooring(String categoria, String subcategoria, String brand,
//                                                                            String codigo, String canal, String subcanal, String manufacturer) {
//        ArrayList<BasePortafolioProductos> operadores = new ArrayList<BasePortafolioProductos>();
//
//        Log.i("CATEGORIA", categoria);
//        Log.i("SUBCATEGORIA", subcategoria);
//        Log.i("MARCA", brand);
//
//        Cursor cursor = null;
//        String selectQuery = "";
//
//        if (canal.equalsIgnoreCase("MAYORISTA")) {
//            if (categoria.equalsIgnoreCase("INSECTICIDAS")) {
//                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " +
//                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
//                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
//                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
//                        ContractPortafolioProductos.Columnas.MARCA + "=? AND (" +
//                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? OR " +
//                        ContractPortafolioProductos.Columnas.FABRICANTE + "='Sc Jhonson')" +
//                        " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
//                db = this.getReadableDatabase();
//                cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, "%" + manufacturer + "%"});
//            } else {
//                selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " +
//                        ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
//                        ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
//                        ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
//                        ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
//                        ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
//                        " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
//                db = this.getReadableDatabase();
//                cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, "%" + manufacturer + "%"});
//            }
//        }else{
//            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " +
//                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
//                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
//                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
//                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
//                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
//                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
//            db = this.getReadableDatabase();
//            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, "%" + subcanal.trim() + "%"});
//        }
//
//        // looping through all rows and adding to list
////        BasePortafolioProductos bpv_test = new BasePortafolioProductos();
////        bpv_test.setSku("TEST");
////        operadores.add(bpv_test);
//        if (cursor.moveToFirst()) {
//            do {
//                BasePortafolioProductos bpv = new BasePortafolioProductos();
//                bpv.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
//                operadores.add(bpv);
//            } while (cursor.moveToNext());
//        }
//        // closing connection
//        cursor.close();
//        db.close();
//        // returning lables
//        return operadores;
//    }
//mpin
public ArrayList<Precio> filtrarProductosPorMarca(String brand, String fabricante,
                                                  String codigo, String canal, String subcanal,
                                                  String tipo) {
    ArrayList<Precio> operadores = new ArrayList<>();

    Cursor cursor = null;
    String selectQuery = null;

    Log.i("MARCA", brand);
    Log.i("FABRICANTE", fabricante);
    Log.i("CODIGO", codigo);
    Log.i("CANAL", canal);

    String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";
    String[] selectionArgs;

    if (tipo.equalsIgnoreCase("MARCA_PROPIA")) {
        byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
    }

    // Filtro para excluir ciertos fabricantes (igual que en tus otras funciones)
    String excludeFabricantes = ContractPortafolioProductos.Columnas.FABRICANTE +
            " NOT LIKE '% Rosado%' AND " +
            ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Coral%' AND " +
            ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Favorita%' AND " +
            ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Maria%' AND " +
            ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE '% Tia%'";

    if (canal.equalsIgnoreCase("MAYORISTA")) {
        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SKU +
                ", " + ContractPortafolioProductos.Columnas.PVP +
                " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                " WHERE " + ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                byFabricante + " AND " + excludeFabricantes +
                " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
        selectionArgs = new String[]{brand, "%" + fabricante + "%"};
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, selectionArgs);

    } else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SKU +
                ", " + ContractPortafolioProductos.Columnas.PVP +
                " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                " WHERE " + ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                byFabricante + " AND " + excludeFabricantes + " AND " +
                ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
        selectionArgs = new String[]{brand, "%" + fabricante + "%", "%" + subcanal.trim() + "%"};
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, selectionArgs);
        Log.i("entro auto ", selectQuery);

    } else {
        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SKU +
                ", " + ContractPortafolioProductos.Columnas.PVP +
                " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                " WHERE " + ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                byFabricante + " AND " + excludeFabricantes +
                " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
        selectionArgs = new String[]{brand, "%" + fabricante + "%"};
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, selectionArgs);
        Log.i("entro 222 ", selectQuery);
    }

    if (cursor != null && cursor.moveToFirst()) {
        do {
            Precio p = new Precio();
            p.setSku_code(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
            p.setPvp(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PVP)));
            operadores.add(p);
        } while (cursor.moveToNext());
    }

    // closing connection
    if (cursor != null) {
        cursor.close();
    }
    if (db != null && db.isOpen()) {
        db.close();
    }

    // returning lables
    return operadores;
}
    public ArrayList<Precio> filtrarListProductos2Precios(String categoria, String subcategoria, String brand,
                                                          String fabricante, String codigo, String canal, String subcanal, String tipo) {
        ArrayList<Precio> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = null;

        Log.i("CATEGORIA", categoria);
        Log.i("SUBCATEGORIA", subcategoria);
        Log.i("MARCA", brand);
        Log.i("FABRICANTE", fabricante);
        Log.i("CODIGO", codigo);
        Log.i("CANAL", canal);
        String byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ? ";

        if(tipo.equalsIgnoreCase("MARCA_PROPIA")){
            byFabricante = ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? ";
        }

        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + ", " + ContractPortafolioProductos.Columnas.PVP + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " + byFabricante +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, "%" + fabricante + "%"});
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO")  ) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + ", " + ContractPortafolioProductos.Columnas.PVP + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " + byFabricante + " AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, fabricante, "%" + subcanal.trim() + "%"});
            Log.i("entro auto ", selectQuery);
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + ", " + ContractPortafolioProductos.Columnas.PVP + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    byFabricante +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, fabricante});
            Log.i("entro 222 ", selectQuery);

        }

        if (cursor.moveToFirst()) {
            do {
                Precio p = new Precio();
                p.setSku_code(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
                p.setPvp(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PVP)));
                operadores.add(p);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


    public ArrayList<BaseTareas> filtrarListProductosTareas(String codigo_pdv, String user) {
        ArrayList<BaseTareas> operadores = new ArrayList<>();

        // Select All Query
//        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS;
//
//        db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{});

        Cursor cursor = null;
        String selectQuery = null;
//        if (canal.equalsIgnoreCase("MAYORISTA")) {
//            selectQuery = "SELECT DISTINCT "+ ContractTareas.Columnas.TAREAS + " FROM " +
//                    ContractTareas.TAREA + " WHERE " + ContractTareas.Columnas.CANAL + "=?";
//            db = this.getReadableDatabase();
//            cursor = db.rawQuery(selectQuery, new String[]{canal});
//        }else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT * FROM " + ContractTareas.TAREA +
                    " WHERE " + ContractTareas.Columnas.CODIGOPDV + "=? AND " +
                    ContractTareas.Columnas.MERCADERISTA + "=? " +
                    " GROUP BY " + ContractTareas.Columnas.TAREAS;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{codigo_pdv, user});
//        }//revisa xq no te trae todo, sino solo eso

//        operadores.add("Seleccione");
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                BaseTareas p = new BaseTareas();
                p.setTareas(cursor.getString(cursor.getColumnIndexOrThrow(ContractTareas.Columnas.TAREAS)));
                p.setPeriodo(cursor.getString(cursor.getColumnIndexOrThrow(ContractTareas.Columnas.PERIODO)));
                operadores.add(p);
            } while (cursor.moveToNext());
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> filtrarListPortafolioPrioritario(String canal, String codigo_pdv) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
//        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS;
//
//        db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{});
        Log.i("PRIORITARIO", "CANAL: " + canal + " - CODIGO PDV: " + codigo_pdv);
        Cursor cursor = null;
        String selectQuery = null;
       /* if (canal.equalsIgnoreCase("MAYORISTA")) {*/
            selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.SKU +
                    " FROM " + ContractPrioritario.PRIORITARIO +
                    " WHERE " + ContractPrioritario.Columnas.CANAL + "=? AND " +
                    ContractPrioritario.Columnas.CODIGO_PDV + "=?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{canal, codigo_pdv});
       /* }else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.SKU + " FROM " +
                    ContractPrioritario.PRIORITARIO;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{});
        }*/

//        operadores.add("Seleccione");
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPrioritario.Columnas.SKU)));
            } while (cursor.moveToNext());
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> filtrarListPop(String canal, String codigo_pdv) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
//        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS;
//
//        db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{});

        Cursor cursor = null;
        String selectQuery = null;
        /* if (canal.equalsIgnoreCase("MAYORISTA")) {*/
        selectQuery = "SELECT DISTINCT "+ ContractPopSugerido.Columnas.POP_SUGERIDO +
                " FROM " + ContractPopSugerido.POPSUGERIDO +
                " WHERE " + ContractPopSugerido.Columnas.CANAL + "=? AND " +
                ContractPopSugerido.Columnas.CODIGO_PDV + "=?";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{canal, codigo_pdv});
       /* }else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.SKU + " FROM " +
                    ContractPrioritario.PRIORITARIO;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{});
        }*/

//        operadores.add("Seleccione");
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPopSugerido.Columnas.POP_SUGERIDO)));
            } while (cursor.moveToNext());
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<Precio> filtrarListProductosProdCad(String fabricante, String categoria, String subcategoria, String brand) {
        ArrayList<Precio> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = "";
        // Select All Query
        selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                ContractPortafolioProductos.Columnas.CATEGORY +"=? AND " +
                ContractPortafolioProductos.Columnas.SUBCATEGORIA +"=? AND " +
                ContractPortafolioProductos.Columnas.MARCA +"=? AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,brand,"%" + fabricante + "%"});
        //}

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Precio p = new Precio();
                p.setSku_code(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
                operadores.add(p);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BaseRotacion> filtrarListRotacion(String tipo, String categoria, String subcategoria, String marca) {
        ArrayList<BaseRotacion> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = "";
        // Select All Query
        selectQuery = "SELECT DISTINCT " + ContractRotacion.Columnas.PRODUCTO +
                " FROM " + ContractRotacion.ROTACION +
                " WHERE " + ContractRotacion.Columnas.TIPO +"=? AND " +
                ContractRotacion.Columnas.CATEGORIA +"=? AND " +
                ContractRotacion.Columnas.SUBCATEGORIA +"=? AND " +
                ContractRotacion.Columnas.MARCA +"=?" +
                " ORDER BY " + ContractRotacion.Columnas.PRODUCTO;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{tipo, categoria, subcategoria, marca});
        //}

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BaseRotacion p = new BaseRotacion();
                p.setProducto(cursor.getString(cursor.getColumnIndexOrThrow(ContractRotacion.Columnas.PRODUCTO)));
                operadores.add(p);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BaseAlertas> filtrarListMateriales(String alerta) {
        ArrayList<BaseAlertas> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = "";
        // Select All Query
        selectQuery = "SELECT DISTINCT " + ContractAlertas.Columnas.MATERIAL +
                " FROM " + ContractAlertas.ALERTAS +
                " WHERE " + ContractAlertas.Columnas.TIPO_ALERTA + " LIKE ?" +
                " ORDER BY " + ContractAlertas.Columnas.MATERIAL;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{"%" + alerta + "%"});
        //}

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BaseAlertas p = new BaseAlertas();
                p.setMaterial(cursor.getString(cursor.getColumnIndexOrThrow(ContractAlertas.Columnas.MATERIAL)));
                operadores.add(p);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BaseAlertas> filtrarListEjecucionMateriales(String alerta, String categoria) {
        ArrayList<BaseAlertas> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = "";
        // Select All Query
        selectQuery = "SELECT DISTINCT " + ContractAlertas.Columnas.MATERIAL +
                " FROM " + ContractAlertas.ALERTAS +
                " WHERE " + ContractAlertas.Columnas.TIPO_ALERTA + " LIKE ? AND " +
                ContractAlertas.Columnas.CATEGORIA + "=?" +
                " ORDER BY " + ContractAlertas.Columnas.MATERIAL;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{"%" + alerta + "%", categoria});
        //}

        Log.i("ENTRA", "EJECUCION DE MATERIALES " + categoria);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BaseAlertas p = new BaseAlertas();
                p.setMaterial(cursor.getString(cursor.getColumnIndexOrThrow(ContractAlertas.Columnas.MATERIAL)));
                operadores.add(p);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BaseRotacion> filtrarListMateriales(String tipo, String categoria, String subcategoria, String marca) {
        ArrayList<BaseRotacion> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = "";
        // Select All Query
        selectQuery = "SELECT DISTINCT " + ContractRotacion.Columnas.PRODUCTO +
                " FROM " + ContractRotacion.ROTACION +
                " WHERE " + ContractRotacion.Columnas.TIPO +"=? AND " +
                ContractRotacion.Columnas.CATEGORIA +"=? AND " +
                ContractRotacion.Columnas.SUBCATEGORIA +"=? AND " +
                ContractRotacion.Columnas.MARCA +"=?" +
                " ORDER BY " + ContractRotacion.Columnas.PRODUCTO;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{tipo, categoria, subcategoria, marca});
        //}

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BaseRotacion p = new BaseRotacion();
                p.setProducto(cursor.getString(cursor.getColumnIndexOrThrow(ContractRotacion.Columnas.PRODUCTO)));
                operadores.add(p);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getManufacturerSoloPrecios(String categoria, String subcategoria, String brand, String descripcion) {
        String manufacturer = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.FABRICANTE + " FROM " +
                ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                ContractPortafolioProductos.Columnas.SECTOR +"=? AND " +
                ContractPortafolioProductos.Columnas.SUBCATEGORIA +"=? AND " +
                ContractPortafolioProductos.Columnas.MARCA +"=? AND " +
                ContractPortafolioProductos.Columnas.SKU +"=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,brand,descripcion});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                manufacturer = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FABRICANTE));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return manufacturer;
    }


    public String getManufacturerPrecios(String categoria, String subcategoria, String brand, String descripcion) {
        String manufacturer = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.FABRICANTE + " FROM " +
                ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                ContractPortafolioProductos.Columnas.SECTOR +"=? AND " +
                ContractPortafolioProductos.Columnas.SUBCATEGORIA +"=? AND " +
                ContractPortafolioProductos.Columnas.MARCA +"=? AND " +
                ContractPortafolioProductos.Columnas.SKU +"=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria,brand,descripcion});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                manufacturer = cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FABRICANTE));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return manufacturer;
    }

    public BasePharmaValue getPdv(String codigo) {
        BasePharmaValue bpv = null;

        // Select All Query
        String selectQuery = "SELECT * FROM " + ContractPharmaValue.POS + " WHERE " + ContractPharmaValue.Columnas.POS_ID +"=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                bpv = new BasePharmaValue();
                bpv.setChannel(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CHANNEL)));
                bpv.setSubchannel(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.SUBCHANNEL)));
                bpv.setChannel_segment(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CHANNEL_SEGMENT)));
                bpv.setFormat(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.FORMAT)));
                bpv.setCustomer_owner(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CUSTOMER_OWNER)));
                bpv.setPos_id(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_ID)));
                bpv.setPos_name(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_NAME)));
                bpv.setPos_name_dpsm(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_NAME_DPSM)));
                bpv.setZone(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.ZONA)));
                bpv.setRegion(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.REGION)));
                bpv.setProvince(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.PROVINCIA)));
                bpv.setCity(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CIUDAD)));
                bpv.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.DIRECCION)));
                bpv.setKam(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.KAM)));
                bpv.setSales_executive(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.SALES_EXECUTIVE)));
                bpv.setMerchandising(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.MERCHANDISING)));
                bpv.setSupervisor(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.SUPERVISOR)));
                bpv.setMercaderista(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.MERCADERISTA)));
                bpv.setUser(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.USER)));
                bpv.setDpsm(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.DPSM)));
                bpv.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.STATUS)));
                bpv.setTipo(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.TIPO)));
                bpv.setDistancia(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.DISTANCIA)));
                bpv.setLatitud(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.LATITUD)));
                bpv.setLongitud(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.LONGITUD)));
                bpv.setFoto(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.FOTO)));
                bpv.setSegmentacion(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.SEGMENTACION)));
                bpv.setCompras(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.COMPRAS)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return bpv;
    }

    public ArrayList<BasePortafolioProductos> filtrarListProductos2Valores(String categoria, String subcategoria, String brand,
                                                                           String codigo, String canal, String subcanal, String manufacturer) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = "";
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + ", " + ContractPortafolioProductos.Columnas.PVP + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, "%" + manufacturer + "%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + ", " + ContractPortafolioProductos.Columnas.PVP + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, "%" + subcanal + "%", "%" + manufacturer + "%"});
        }

        String header = "HEADER FOR RECYCLERVIEW";

        operadores.add(new BasePortafolioProductos(header));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> filtrarPdvsHistorial(String tabla_insert, String columna_pdv) {
        List<String> operadores = new ArrayList<String>();

        operadores.add("PDV");

        if (!tabla_insert.isEmpty() && !columna_pdv.isEmpty()){
            // Select All Query
            String selectQuery = "SELECT DISTINCT " + columna_pdv + " FROM " + tabla_insert ;
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(columna_pdv)));
                } while (cursor.moveToNext());
            }
            // closing connection
            cursor.close();
            db.close();
        }

        // returning lables
        return operadores;
    }


    public List<String> filtrarCategoriasHistorial(String tabla_insert, String columna_pdv, String columna_categoria, String pdv) {
        List<String> operadores = new ArrayList<String>();

        operadores.add("Categoria");

        if (!tabla_insert.isEmpty() && !columna_categoria.isEmpty()){
            // Select All Query
            String selectQuery = "SELECT DISTINCT " + columna_categoria + " FROM " + tabla_insert +
                    " WHERE "+ columna_pdv + "= ?";
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{pdv});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(columna_categoria)));
                } while (cursor.moveToNext());
            }
            // closing connection
            cursor.close();
            db.close();
        }

        // returning lables
        return operadores;
    }

    public List<String> filtrarFabriHistorial(String tabla_insert, String columna_pdv, String columna_fabricante, String pdv) {
        List<String> operadores = new ArrayList<String>();

        operadores.add("Fabricante");

        if (!tabla_insert.isEmpty() && !columna_fabricante.isEmpty()){
            // Select All Query
            String selectQuery = "SELECT DISTINCT " + columna_fabricante + " FROM " + tabla_insert +
                    " WHERE "+ columna_pdv + "= ?";
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{pdv});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(columna_fabricante)));
                } while (cursor.moveToNext());
            }
            // closing connection
            cursor.close();
            db.close();
        }

        // returning lables
        return operadores;
    }

    public List<String> filtrarMarcaHistorial(String tabla_insert, String columna_pdv, String columna_marca, String pdv) {
        List<String> operadores = new ArrayList<String>();

        operadores.add("Marca");

        if (!tabla_insert.isEmpty() && !columna_marca.isEmpty()){
            // Select All Query
            String selectQuery = "SELECT DISTINCT " + columna_marca + " FROM " + tabla_insert +
                    " WHERE "+ columna_pdv + "= ?";
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{pdv});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(columna_marca)));
                } while (cursor.moveToNext());
            }
            // closing connection
            cursor.close();
            db.close();
        }

        // returning lables
        return operadores;
    }


    public List<String> filtrarSubcategoriasHistorial(String tabla_insert, String columna_pdv, String columna_categoria, String columna_subcategoria,
                                                      String pdv, String categoria) {
        List<String> operadores = new ArrayList<String>();

        operadores.add("Subcategoria");

        if (!tabla_insert.isEmpty() && !columna_subcategoria.isEmpty()){
            // Select All Query
            String selectQuery = "SELECT DISTINCT " + columna_subcategoria + " FROM " + tabla_insert +
                    " WHERE "+ columna_pdv + "= ? AND " +
                    columna_categoria + "= ?";
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{pdv, categoria});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(columna_subcategoria)));
                } while (cursor.moveToNext());
            }
            // closing connection
            cursor.close();
            db.close();
        }

        // returning lables
        return operadores;
    }
    public List<String> filtrarMarcaNewHistorial(String tabla_insert, String columna_pdv, String columna_fabricante, String columna_marca,
                                                      String pdv, String fabricante) {
        List<String> operadores = new ArrayList<String>();

        operadores.add("Marca");

        if (!tabla_insert.isEmpty() && !columna_marca.isEmpty()){
            // Select All Query
            String selectQuery = "SELECT DISTINCT " + columna_marca + " FROM " + tabla_insert +
                    " WHERE "+ columna_pdv + "= ? AND " +
                    columna_fabricante + "= ?";
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{pdv, fabricante});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(columna_marca)));
                } while (cursor.moveToNext());
            }
            // closing connection
            cursor.close();
            db.close();
        }

        // returning lables
        return operadores;
    }

    public ArrayList<BasePortafolioProductos> filtrarListSugProductos3(String categoria, String subcategoria, String brand, String contenido,
                                                                       String codigo, String canal, String subcanal, String manufacturer) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = "";
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SEGMENTO + ", " + ContractPortafolioProductos.Columnas.PVP + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.CONTENIDO + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SEGMENTO;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, contenido, "%"+manufacturer+"%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SEGMENTO + ", " + ContractPortafolioProductos.Columnas.PVP + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.CONTENIDO + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, contenido, "%" + subcanal + "%"});
        }

    //    String header = "HEADER FOR RECYCLERVIEW";

    //    operadores.add(new BasePortafolioProductos(header));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SEGMENTO)));
                bpv.setPvp(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PVP)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BasePortafolioProductos> filtrarListSugProductos4(String categoria, String subcategoria, String brand, String contenido,
                                                                       String codigo, String canal, String subcanal) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = "";
//        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.SKU + " FROM " +
                    ContractPrioritario.PRIORITARIO + " WHERE " +
                    ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
                    ContractPrioritario.Columnas.SUBCATEGORIA + "=? AND " +
                    ContractPrioritario.Columnas.MARCA + "=? AND " +
                    ContractPrioritario.Columnas.CONTENIDO + "=? AND " +
                    ContractPrioritario.Columnas.CODIGO_PDV + "=?" +
                    " ORDER BY " + ContractPrioritario.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, contenido, codigo});
//        }else{
//            selectQuery = "SELECT DISTINCT "+ ContractPrioritario.Columnas.SKU + " FROM " +
//                    ContractPrioritario.PRIORITARIO + " WHERE " +
//                    ContractPrioritario.Columnas.CATEGORIA + "=? AND " +
//                    ContractPrioritario.Columnas.SUBCATEGORIA + "=? AND " +
//                    ContractPrioritario.Columnas.MARCA + "=? AND " +
//                    ContractPrioritario.Columnas.CONTENIDO + "=? AND " +
//                    ContractPrioritario.Columnas.CODIGO_PDV + "=?" +
//                    " ORDER BY " + ContractPrioritario.Columnas.SKU;
//            db = this.getReadableDatabase();
//            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, contenido, codigo});
//        }

        //    String header = "HEADER FOR RECYCLERVIEW";

        //    operadores.add(new BasePortafolioProductos(header));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPrioritario.Columnas.SKU)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BasePortafolioProductos> filtrarListSugProductos(String categoria, String subcategoria, String brand, String contenido,
                                                                      String codigo, String canal, String subcanal, String manufacturer) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = "";
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SEGMENTO + ", " + ContractPortafolioProductos.Columnas.PVP + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.CONTENIDO + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SEGMENTO;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, contenido, "%"+manufacturer+"%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SEGMENTO + ", " + ContractPortafolioProductos.Columnas.PVP + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.CONTENIDO + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, contenido, "%" + subcanal + "%"});
        }

        String header = "HEADER FOR RECYCLERVIEW";

        operadores.add(new BasePortafolioProductos(header));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SEGMENTO)));
                bpv.setPvp(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PVP)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BasePortafolioProductos> filtrarListProductos(String categoria, String subcategoria, String brand,
                                                                   String codigo, String canal, String subcanal, String manufacturer) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<>();

        Log.i("CADENA", subcanal);
        Cursor cursor = null;
        String selectQuery = "";
        if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + ", " + ContractPortafolioProductos.Columnas.PVP + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, "%" + subcanal + "%", "%"+manufacturer+"%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + ", " + ContractPortafolioProductos.Columnas.PVP + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, "%"+manufacturer+"%"});
        }

//        String header = "HEADER FOR RECYCLERVIEW";
//
//        operadores.add(new BasePortafolioProductos(header));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
                bpv.setPvp(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PVP)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BasePortafolioProductos> filtrarListProductosSinBrand(String categoria, String subcategoria, String producto,
                                                                           String codigo, String canal, String subcanal, String manufacturer) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = "";
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.FOTO + ", " + ContractPortafolioProductos.Columnas.PVP + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.SKU + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, producto, "%"+manufacturer+"%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.FOTO + ", " + ContractPortafolioProductos.Columnas.PVP + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.SKU + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, "%" + subcanal + "%"});
        }

        String header = "HEADER FOR RECYCLERVIEW";

        operadores.add(new BasePortafolioProductos(header));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setFoto(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FOTO)));
                bpv.setPvp(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PVP)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

 /*   public ArrayList<BasePortafolioProductos> filtrarListProductosSugeridos(String categoria, String subcategoria, String producto,
                                                                             String codigo, String canal, String subcanal, String manufacturer) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = "";
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.FOTO + ", " + ContractPortafolioProductos.Columnas.PVP + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.SEGMENTO + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SEGMENTO;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, producto, "%"+manufacturer+"%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.FOTO + ", " + ContractPortafolioProductos.Columnas.PVP + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.SEGMENTO + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SEGMENTO;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, "%" + subcanal + "%"});
        }

        String header = "HEADER FOR RECYCLERVIEW";

        operadores.add(new BasePortafolioProductos(header));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setFoto(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FOTO)));
                bpv.setPvp(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PVP)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }*/

    public ArrayList<BasePortafolioProductos> filtrarListProductosSugeridos(String categoria, String subcategoria, String brand, String contenido,
                                                                            String codigo, String canal, String subcanal, String manufacturer) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = "";
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.FOTO + ", " + ContractPortafolioProductos.Columnas.PVP + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.CONTENIDO + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.CONTENIDO;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, contenido, "%"+manufacturer+"%"});
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.FOTO + ", " + ContractPortafolioProductos.Columnas.PVP + " FROM " +
                    ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.CONTENIDO + "=? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.CONTENIDO;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, contenido, "%" + subcanal + "%"});
        }

        String header = "HEADER FOR RECYCLERVIEW";

        operadores.add(new BasePortafolioProductos(header));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setFoto(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FOTO)));
                bpv.setPvp(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.PVP)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BasePortafolioProductos> filtrarListProductosCodificanValores(String categoria, String subcategoria, String brand,
                                                                                   String codigo, String canal, String subcanal, String manufacturer) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<BasePortafolioProductos>();

        Log.i("CATEGORIA", categoria);
        Log.i("SUBCATEGORIA", subcategoria);
        Log.i("MARCA", brand);
        Log.i("CANAL", canal);
        Log.i("SUBCANAL", subcanal);
        Log.i("FABRICANTE", manufacturer);

        Cursor cursor = null;
        String selectQuery = "";
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, "%" + manufacturer + "%"});
            Log.i("MAYORISTA", "MAYORISTA");
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, "%" + manufacturer + "%", "%" + subcanal + "%"});
            Log.i("AASS", "AASS");
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, "%" + manufacturer + "%"});
            Log.i("NA", "NA");
        }

//        String header = "HEADER FOR RECYCLERVIEW";
//        operadores.add(new BasePortafolioProductos(header));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BasePortafolioProductos> filtrarListProductosCodificanValoresSinBrand(String categoria, String subcategoria,
                                                                                           String codigo, String canal, String subcanal, String manufacturer) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<BasePortafolioProductos>();

        Log.i("CATEGORIA", categoria);
        Log.i("SUBCATEGORIA", subcategoria);
        Log.i("CANAL", canal);
        Log.i("SUBCANAL", subcanal);
        Log.i("FABRICANTE", manufacturer);

        Cursor cursor = null;
        String selectQuery = "";
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, "%" + manufacturer + "%"});
            Log.i("MAYORISTA", "MAYORISTA");
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ? AND " +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, "%" + manufacturer + "%", "%" + subcanal + "%"});
            Log.i("AASS", "AASS");
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, "%" + manufacturer + "%"});
            Log.i("NA", "NA");
        }

//        String header = "HEADER FOR RECYCLERVIEW";
//        operadores.add(new BasePortafolioProductos(header));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BasePortafolioProductos> filtrarListProductosPreciosValores(String categoria, String subcategoria, String brand,
                                                                                 String codigo, String canal, String subcanal, String manufacturer) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<BasePortafolioProductos>();

        Log.i("CATEGORIA", categoria);
        Log.i("SUBCATEGORIA", subcategoria);
        Log.i("MARCA", brand);
        Log.i("CANAL", canal);
        Log.i("SUBCANAL", subcanal);
        Log.i("FABRICANTE", manufacturer);

        Cursor cursor = null;
        String selectQuery = "";
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand});
            Log.i("MAYORISTA", "MAYORISTA");
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? AND (" +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? OR " +
                    ContractPortafolioProductos.Columnas.CADENAS + " REGEXP 'N/A')" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand, "%" + subcanal + "%"});
            Log.i("AASS", "AASS");
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND " +
                    ContractPortafolioProductos.Columnas.MARCA + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, brand});
            Log.i("NA", "NA");
        }

//        String header = "HEADER FOR RECYCLERVIEW";
//        operadores.add(new BasePortafolioProductos(header));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BasePortafolioProductos> filtrarListProductosPreciosValoresSinBrand(String categoria, String subcategoria,
                                                                                         String codigo, String canal, String subcanal, String manufacturer) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<BasePortafolioProductos>();

        Log.i("CATEGORIA", categoria);
        Log.i("SUBCATEGORIA", subcategoria);
        Log.i("CANAL", canal);
        Log.i("SUBCANAL", subcanal);
        Log.i("FABRICANTE", manufacturer);

        Cursor cursor = null;
        String selectQuery = "";
        if (canal.equalsIgnoreCase("MAYORISTA")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria});
            Log.i("MAYORISTA", "MAYORISTA");
        }else if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? AND (" +
                    ContractPortafolioProductos.Columnas.CADENAS + " LIKE ? OR " +
                    ContractPortafolioProductos.Columnas.CADENAS + " REGEXP 'N/A')" +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria, "%" + subcanal + "%"});
            Log.i("AASS", "AASS");
        }else{
            selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE " +
                    ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                    ContractPortafolioProductos.Columnas.CATEGORY + "=? " +
                    " ORDER BY " + ContractPortafolioProductos.Columnas.SKU;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{categoria, subcategoria});
            Log.i("NA", "NA");
        }

//        String header = "HEADER FOR RECYCLERVIEW";
//        operadores.add(new BasePortafolioProductos(header));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> filtrarListDescripcion(String marca, String categoria, String segmento1, String segmento2, String brand, String tamano, String cantidad) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPortafolioProductos.Columnas.SKU + " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                ContractPortafolioProductos.Columnas.SECTOR +"=? AND " +
                ContractPortafolioProductos.Columnas.CATEGORY +"=? AND " +
                ContractPortafolioProductos.Columnas.SUBCATEGORIA +"=? AND " +
                ContractPortafolioProductos.Columnas.SEGMENTO +"=? AND " +
                ContractPortafolioProductos.Columnas.CONTENIDO +"=? AND " +
                ContractPortafolioProductos.Columnas.MARCA +"=? AND " +
                ContractPortafolioProductos.Columnas.FABRICANTE +" LIKE ?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{marca,categoria,segmento1,segmento2,brand,tamano,cantidad});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> filtrarListShare(String categoria, String subcategoria) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractPrecios.Columnas.MARCA + " FROM " + ContractPrecios.PRECIOS + " WHERE "+
                ContractPrecios.Columnas.CATEGORIA +"=? AND " +
                ContractPrecios.Columnas.SUBCATEGORIA +"=? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{categoria,subcategoria});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPrecios.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


    public List<String> filtrarList(String marca, String categoria, String tipo) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
     String selectQuery = "SELECT DISTINCT "+ ContractPrecios.Columnas.PRODUCTO + " FROM " + ContractPrecios.PRECIOS + " WHERE "+
                ContractPrecios.Columnas.MARCA +"=? AND " +
                ContractPrecios.Columnas.CATEGORIA +"=? AND " +
                ContractPrecios.Columnas.SUBCATEGORIA +"=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{marca,categoria,tipo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPrecios.Columnas.PRODUCTO)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<String> filtrarListMecanicasRotacion(String producto, String categoria) {
        ArrayList<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractRotacion.Columnas.MECANICA + " FROM " + ContractRotacion.ROTACION + " WHERE "+
                ContractRotacion.Columnas.PRODUCTO +"=? AND " +
                ContractRotacion.Columnas.CATEGORIA +"=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{producto, categoria});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractRotacion.Columnas.MECANICA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    //HISTORIAL
    public List<String> getModulo(String basedatos, int dimension) {
        List<String> contenido = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        String strdata, estado;

        // Select All Query
        String selectQuery = "SELECT  * FROM " + basedatos;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
//        int i=2;


        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndexOrThrow(Constantes.PENDIENTE_INSERCION)).equals("1")) {
                estado = "No Enviado";
            }else{
                estado = "Enviado";
            }
            sb.append(estado);
            for(int i=2; i<dimension;i++) {
                String prefix = " | ";
                sb.append(prefix);
                sb.append(cursor.getString(i));
            }
            strdata = sb.toString();
            sb.setLength(0);
            contenido.add(strdata);
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return contenido;
    }

    public List<String> getNombreCom(String codigo) {
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.POS_ID + "=? "+
                "GROUP BY " + ContractPharmaValue.Columnas.POS_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_NAME)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public boolean SKUDuplicadoPrecios(String sku_code) {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractInsertPreciosSesion.INSERT_PRECIOS_SESION + " WHERE "+
                ContractInsertPreciosSesion.Columnas.SKU_CODE + "=? "+
                "GROUP BY " + ContractInsertPreciosSesion.Columnas.SKU_CODE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{sku_code});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            return true;
        }
        // closing connection
        cursor.close();
        db.close();

        return false;
    }

    public void eliminarSKUDuplicadoPrecios(String sku_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ContractInsertPreciosSesion.INSERT_PRECIOS_SESION, ContractInsertPreciosSesion.Columnas.SKU_CODE + "='" + sku_code + "'", null);
        db.close();
    }

    public ArrayList<Precio> getListGuardadoPrecios(String codigo) {
        ArrayList<Precio> operadores = new ArrayList<Precio>();

        String selectQuery = "SELECT " + ContractInsertPrecios.Columnas.SKU_CODE + ", " +
                ContractInsertPrecios.Columnas.PREGULAR + ", " +
                ContractInsertPrecios.Columnas.PPROMOCION +
                " FROM " + ContractInsertPrecios.INSERT_PRECIOS +
                " WHERE " + ContractInsertPrecios.Columnas.CODIGO + " =?"+
                "AND " + ContractInsertPrecios.Columnas.SKU_CODE + " != null ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Precio p = new Precio();
                p.setSku_code(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPreciosSesion.Columnas.SKU_CODE)));
                p.setPvp(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPreciosSesion.Columnas.PREGULAR)));
                p.setPvc(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPreciosSesion.Columnas.PPROMOCION)));
                operadores.add(p);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<Precio> getListGuardadoPrecios2(String codigo) {
        ArrayList<Precio> operadores = new ArrayList<Precio>();

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        cal.set(Calendar.DAY_OF_MONTH,1);
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        // Obtenemos la fecha inicio del mes
        String fecha_inicio_mes = formato.format(cal.getTime());
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        // Obtenemos la fecha final del mes
        String  fecha_fin_mes = formato.format(cal.getTime());



        String selectQuery = "SELECT " + ContractInsertPrecios.Columnas.SKU_CODE +
                " FROM " + ContractInsertPrecios.INSERT_PRECIOS +
                " WHERE " + ContractInsertPrecios.Columnas.CODIGO + " =?"+
                // Formateamos la fecha de dd/mm/yyyy a yyyy-mm-dd
                " AND datetime(substr(fecha, 7, 4) || '-' || substr(fecha, 4, 2) || '-' || substr(fecha, 1, 2))" +
                " BETWEEN ? AND ? " +
                " GROUP BY " + ContractInsertPrecios.Columnas.SKU_CODE +
                " ORDER BY " + ContractInsertPrecios.Columnas.SKU_CODE + " DESC";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo,fecha_inicio_mes,fecha_fin_mes});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Precio p = new Precio();
                p.setSku_code(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertPrecios.Columnas.SKU_CODE)));
                operadores.add(p);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<Ventas> getListGuardadoPrecios3(String codigo) {
        ArrayList<Ventas> operadores = new ArrayList<Ventas>();

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        cal.set(Calendar.DAY_OF_MONTH,1);
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        // Obtenemos la fecha inicio del mes
        String fecha_inicio_mes = formato.format(cal.getTime());
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        // Obtenemos la fecha final del mes
        String  fecha_fin_mes = formato.format(cal.getTime());



        String selectQuery = "SELECT " + ContractInsertVenta.Columnas.SKU +
                " FROM " + ContractInsertVenta.INSERT_VENTA +
                " WHERE " + ContractInsertVenta.Columnas.CODIGO + " =?"+
                // Formateamos la fecha de dd/mm/yyyy a yyyy-mm-dd
                " AND datetime(substr(fecha, 7, 4) || '-' || substr(fecha, 4, 2) || '-' || substr(fecha, 1, 2))" +
                " BETWEEN ? AND ? " +
                " GROUP BY " + ContractInsertVenta.Columnas.SKU +
                " ORDER BY " + ContractInsertVenta.Columnas.SKU + " DESC";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo,fecha_inicio_mes,fecha_fin_mes});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Ventas p = new Ventas();
                p.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertVenta.Columnas.SKU)));
                operadores.add(p);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

//    public ArrayList<InsertTareas> getListGuardadoTareas(String codigo, String user, String periodo) {
//        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
//        Date currentLocalTime = cal.getTime();
//        DateFormat date = new SimpleDateFormat("dd/MM/yyy");
//        date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
//        String fecha_actual = date.format(currentLocalTime);
//        String mes_actual = fecha_actual.substring(3);
//
//        Log.i("FECHAS", "FECHA ACTUAL: " + fecha_actual + " - " + "MES ACTUAL: " + mes_actual);
//
//        ArrayList<InsertTareas> operadores = new ArrayList<InsertTareas>();
//
//        String selectQuery = "";
//        Cursor cursor = null;
//        if (periodo.equalsIgnoreCase("DIARIO")) {
//            selectQuery = "SELECT * FROM " + ContractInsertTareas.INSERT_TAREAS +
//                    " WHERE " + ContractInsertTareas.Columnas.CODIGO + " =? AND " +
//                    ContractInsertTareas.Columnas.MERCADERISTA + "=? AND " +
//                    ContractInsertTareas.Columnas.FECHA + "=? AND " +
//                    ContractInsertTareas.Columnas.REALIZADO + "='SI'";
//            db = this.getReadableDatabase();
//            cursor = db.rawQuery(selectQuery, new String[]{codigo, user, fecha_actual});
//        } else if (periodo.equalsIgnoreCase("MENSUAL")) {
//            selectQuery = "SELECT * FROM " + ContractInsertTareas.INSERT_TAREAS +
//                    " WHERE " + ContractInsertTareas.Columnas.CODIGO + " =? AND " +
//                    ContractInsertTareas.Columnas.MERCADERISTA + "=? AND " +
//                    ContractInsertTareas.Columnas.FECHA + "=? AND " +
//                    ContractInsertTareas.Columnas.REALIZADO + "='SI'";
//            db = this.getReadableDatabase();
//            cursor = db.rawQuery(selectQuery, new String[]{codigo, user, mes_actual});
//        }
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                InsertTareas p = new InsertTareas();
//                p.setTareas(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertTareas.Columnas.TAREAS)));
//                p.setRealizado("REALIZADO");
//                operadores.add(p);
//            } while (cursor.moveToNext());
//        }
//        // closing connection
//        cursor.close();
//        db.close();
//        // returning lables
//        return operadores;
//    }

    public String getEstadoTarea(String codigo, String user, String periodo, String tarea) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd/MM/yyy");
        date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String fecha_actual = date.format(currentLocalTime);
        String mes_actual = fecha_actual.substring(3);

        Log.i("FECHAS", "PERIODO: " + periodo + " - FECHA ACTUAL: " + fecha_actual + " - MES ACTUAL: " + mes_actual);

        String operadores = "PENDIENTE";
        String selectQuery = "";
        Cursor cursor = null;

        if (periodo.equalsIgnoreCase("DIARIO")) {
            selectQuery = "SELECT * FROM " + ContractInsertTareas.INSERT_TAREAS +
                    " WHERE " + ContractInsertTareas.Columnas.CODIGO + " =? AND " +
                    ContractInsertTareas.Columnas.MERCADERISTA + "=? AND " +
                    ContractInsertTareas.Columnas.FECHA + "=? AND " +
                    ContractInsertTareas.Columnas.TAREAS + "=? AND " +
                    ContractInsertTareas.Columnas.REALIZADO + "='SI'";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{codigo, user, fecha_actual, tarea});
        } else if (periodo.equalsIgnoreCase("MENSUAL")) {
            selectQuery = "SELECT * FROM " + ContractInsertTareas.INSERT_TAREAS +
                    " WHERE " + ContractInsertTareas.Columnas.CODIGO + " =? AND " +
                    ContractInsertTareas.Columnas.MERCADERISTA + "=? AND " +
                    ContractInsertTareas.Columnas.FECHA + "=? AND " +
                    ContractInsertTareas.Columnas.TAREAS + "=? AND " +
                    ContractInsertTareas.Columnas.REALIZADO + "='SI'";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{codigo, user, mes_actual, tarea});
        }
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = "REALIZADO";
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<InsertOsa> getListGuardadoOSA(String codigo,String user) {
        ArrayList<InsertOsa> operadores = new ArrayList<InsertOsa>();

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        cal.set(Calendar.DAY_OF_MONTH,1);
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        // Obtenemos la fecha inicio del mes
        String fecha_inicio_mes = formato.format(cal.getTime());
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        // Obtenemos la fecha final del mes
        String  fecha_fin_mes = formato.format(cal.getTime());

        String selectQuery = "SELECT * FROM " + ContractInsertValores.INSERT_VALORES +
                " WHERE " + ContractInsertValores.Columnas.CODIGO + " =?" +
                " AND " + ContractInsertValores.Columnas.USUARIO + " =?" +
                " AND datetime(substr(fecha, 7, 4) || '-' || substr(fecha, 4, 2) || '-' || substr(fecha, 1, 2))" +
                " BETWEEN ? AND ? " +
                " GROUP BY " + ContractInsertValores.Columnas.SKU_CODE +
                " ORDER BY " + ContractInsertValores.Columnas.SKU_CODE + " DESC";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo,user,fecha_inicio_mes,fecha_fin_mes});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                InsertOsa p = new InsertOsa();
                p.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertValores.Columnas.SKU_CODE)));
                p.setSugerido(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertValores.Columnas.SUGERIDO)));
                operadores.add(p);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BasePortafolioProductos> getListGuardadoInventario(String codigo) {
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<BasePortafolioProductos>();


        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        cal.set(Calendar.DAY_OF_MONTH,1);
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        // Obtenemos la fecha inicio del mes
        String fecha_inicio_mes = formato.format(cal.getTime());
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        // Obtenemos la fecha final del mes
        String  fecha_fin_mes = formato.format(cal.getTime());

        Log.i("FECHA", fecha_inicio_mes + fecha_fin_mes);
        String selectQuery = "SELECT " + InsertFlooring.Columnas.SKU_CODE +
                " FROM " + InsertFlooring.INSERT_FLOORING +
                " WHERE " + InsertFlooring.Columnas.CODIGO + " =?" +
                // Formateamos la fecha de dd/mm/yyyy a yyyy-mm-dd
                " AND datetime(substr(fecha, 7, 4) || '-' || substr(fecha, 4, 2) || '-' || substr(fecha, 1, 2))" +
                " BETWEEN ? AND ? ";
        Log.i("CONSULTA", selectQuery);

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo,fecha_inicio_mes,fecha_fin_mes});
        Log.i("CURSOR", String.valueOf(cursor));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos p = new BasePortafolioProductos();
                p.setSku(cursor.getString(cursor.getColumnIndexOrThrow(InsertFlooring.Columnas.SKU_CODE)));
                Log.i("EL PP", ""+p);
                operadores.add(p);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        Log.i("operadores", String.valueOf(operadores));
        // returning lables
        return operadores;
    }


    public boolean SKUDuplicadoValores(String sku_code, String codigo) {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractInsertValoresSesion.INSERT_VALORES_SESION + " WHERE "+
                ContractInsertValoresSesion.Columnas.SKU_CODE + "=? "+ " AND "+
                ContractInsertValoresSesion.Columnas.CODIGO + "=? " +
                "GROUP BY " + ContractInsertValoresSesion.Columnas.SKU_CODE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{sku_code,codigo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            return true;
        }
        // closing connection
        cursor.close();
        db.close();

        return false;
    }

    public void eliminarSKUDuplicadoValores(String sku_code, String codigo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ContractInsertValoresSesion.INSERT_VALORES_SESION, ContractInsertValoresSesion.Columnas.SKU_CODE + "='" + sku_code + "'" + " AND " + ContractInsertValoresSesion.Columnas.CODIGO + "='" + codigo + "'", null);
        db.close();
    }

    public ArrayList<InsertValores> getListGuardadoValores(String codigo) {
        ArrayList<InsertValores> operadores = new ArrayList<InsertValores>();

        String selectQuery = "SELECT " + ContractInsertValoresSesion.Columnas.SKU_CODE + ", " +
                ContractInsertValoresSesion.Columnas.AUSENCIA + ", " +
                ContractInsertValoresSesion.Columnas.CODIFICA + ", " +
                ContractInsertValoresSesion.Columnas.RESPONSABLE + ", " +
                ContractInsertValoresSesion.Columnas.RAZONES +
                " FROM " + ContractInsertValoresSesion.INSERT_VALORES_SESION +
                " WHERE " + ContractInsertValoresSesion.Columnas.CODIGO + " =?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                InsertValores v = new InsertValores();
                v.setSku_code(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertValoresSesion.Columnas.SKU_CODE)));
                v.setAusencia(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertValoresSesion.Columnas.AUSENCIA)));
                v.setCodifica(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertValoresSesion.Columnas.CODIFICA)));
                v.setResponsable(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertValoresSesion.Columnas.RESPONSABLE)));
                v.setRazones(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertValoresSesion.Columnas.RAZONES)));
                operadores.add(v);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public boolean marcaDuplicadaExh(String brand) {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractInsertExhSesion.INSERT_EXH_SESION + " WHERE "+
                ContractInsertExhSesion.Columnas.BRAND + "=? "+
                "GROUP BY " + ContractInsertExhSesion.Columnas.BRAND;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{brand});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            return true;
        }
        // closing connection
        cursor.close();
        db.close();

        return false;
    }

    public void eliminarMarcaDuplicadaExh(String brand) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ContractInsertExhSesion.INSERT_EXH_SESION, ContractInsertExhSesion.Columnas.BRAND + "='" + brand + "'", null);
        db.close();
    }

    public ArrayList<InsertExhibiciones> getListGuardadoExh(String codigo) {
        ArrayList<InsertExhibiciones> operadores = new ArrayList<InsertExhibiciones>();

        String selectQuery = "SELECT " + ContractInsertExhSesion.Columnas.BRAND + ", " +
                ContractInsertExhSesion.Columnas.TIPO_EXH + ", " +
                ContractInsertExhSesion.Columnas.ZONA_EX + ", " +
                ContractInsertExhSesion.Columnas.CONTRATADA + ", " +
                ContractInsertExhSesion.Columnas.CONDICION + ", " +
                ContractInsertExhSesion.Columnas.FOTO +
                " FROM " + ContractInsertExhSesion.INSERT_EXH_SESION +
                " WHERE " + ContractInsertExhSesion.Columnas.CODIGO + " =?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                InsertExhibiciones v = new InsertExhibiciones();
                v.setMarca(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertExhSesion.Columnas.BRAND)));
                v.setTipo_exh(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertExhSesion.Columnas.TIPO_EXH)));
                v.setZona(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertExhSesion.Columnas.ZONA_EX)));
                v.setContratada(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertExhSesion.Columnas.CONTRATADA)));
                v.setCondicion(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertExhSesion.Columnas.CONDICION)));
                v.setFoto(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertExhSesion.Columnas.FOTO)));
                operadores.add(v);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<InsertCanjes> getListGuardadoCanjes(String usuario, String codigo) {
        ArrayList<InsertCanjes> operadores = new ArrayList<InsertCanjes>();

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        cal.set(Calendar.DAY_OF_MONTH,1);
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        // Obtenemos la fecha inicio del mes
        String fecha_inicio_mes = formato.format(cal.getTime());
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        // Obtenemos la fecha final del mes
        String  fecha_fin_mes = formato.format(cal.getTime());


        String selectQuery = "SELECT * FROM " + ContractInsertCanjes.INSERT_CANJES + " WHERE " +

                        ContractInsertCanjes.Columnas.USUARIO + " =? AND " +
                        ContractInsertCanjes.Columnas.CODIGO + " =?" +
                        // Formateamos la fecha de dd/mm/yyyy a yyyy-mm-dd
                        " AND datetime(substr(fecha, 7, 4) || '-' || substr(fecha, 4, 2) || '-' || substr(fecha, 1, 2))" +
                        " BETWEEN ? AND ? " +
                        " GROUP BY " + ContractInsertCanjes.Columnas.PRODUCTO +
                        " ORDER BY " + ContractInsertCanjes.Columnas._ID + " DESC";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{usuario, codigo,fecha_inicio_mes,fecha_fin_mes});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                InsertCanjes ins = new InsertCanjes();
                ins.setProducto(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCanjes.Columnas.PRODUCTO)));
                ins.setTipo_combo(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCanjes.Columnas.TIPO_COMBO)));
                ins.setMecanica(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCanjes.Columnas.MECANICA)));
                ins.setCombos_armados(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCanjes.Columnas.COMBOS_ARMADOS)));
                ins.setStock(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCanjes.Columnas.STOCK)));
                ins.setPvc_combo(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCanjes.Columnas.PVC_COMBO)));
                ins.setPvc_unitario(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCanjes.Columnas.PVC_UNITARIO)));
                ins.setVisita(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCanjes.Columnas.VISITA)));
                ins.setMes(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCanjes.Columnas.MES)));
                ins.setObservaciones(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertCanjes.Columnas.OBSERVACIONES)));
                operadores.add(ins);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BasePharmaValue> consultarRutaOptima(String user) {
        ArrayList<BasePharmaValue> operadores = new ArrayList<BasePharmaValue>();

//        String selectQuery = "SELECT " + ContractPharmaValue.Columnas.POS_ID + ", "+
//                    ContractPharmaValue.Columnas.POS_NAME + ", " +
//                    ContractPharmaValue.Columnas.CHANNEL + ", " +
//                    ContractPharmaValue.Columnas.DIRECCION + ", " +
//                    ContractPharmaValue.Columnas.CIUDAD + ", " +
//                    ContractPharmaValue.Columnas.FOTO + ", " +
//                    ContractPharmaValue.Columnas.LATITUD + ", " +
//                    ContractPharmaValue.Columnas.LONGITUD + ", " +
//                    " (3959 * ACOS(COS(RADIANS(37))" +
//                    " *COS(RADIANS(" + ContractPharmaValue.Columnas.LATITUD + "))" +
//                    " *COS(RADIANS(" + ContractPharmaValue.Columnas.LONGITUD + ") - RADIANS('-79.907835'))" +
//                    " +SIN(RADIANS('-2.172486')) * SIN(RADIANS(" + ContractPharmaValue.Columnas.LATITUD + ")))) AS distance" +
//                    " FROM " + ContractPharmaValue.POS +
//                    " WHERE "+ ContractPharmaValue.Columnas.USER +"=? " +
//                    " AND "+ ContractPharmaValue.Columnas.LATITUD +" NOT REGEXP '*' " +
//                    " AND "+ ContractPharmaValue.Columnas.LONGITUD +" NOT REGEXP '*' " +
//                    " GROUP BY distance" +
//                    " HAVING distance < 1000 " +
//                    " ORDER BY distance";

        String selectQuery = "SELECT " + ContractPharmaValue.Columnas.POS_ID + ", "+
                ContractPharmaValue.Columnas.POS_NAME + ", " +
                ContractPharmaValue.Columnas.CHANNEL + ", " +
                ContractPharmaValue.Columnas.DIRECCION + ", " +
                ContractPharmaValue.Columnas.CIUDAD + ", " +
                ContractPharmaValue.Columnas.FOTO + ", " +
                ContractPharmaValue.Columnas.LATITUD + ", " +
                ContractPharmaValue.Columnas.LONGITUD +
                " FROM " + ContractPharmaValue.POS +
                " WHERE "+ ContractPharmaValue.Columnas.USER +"=? " +
                " AND "+ ContractPharmaValue.Columnas.LATITUD +" NOT LIKE '%*%' " +
                " AND "+ ContractPharmaValue.Columnas.LONGITUD +" NOT LIKE '%*%'";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePharmaValue bpv = new BasePharmaValue();
                bpv.setPos_id(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_ID)));
                bpv.setPos_name(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_NAME)));
                bpv.setChannel(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CHANNEL)));
                bpv.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.DIRECCION)));
                bpv.setCity(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CIUDAD)));
                bpv.setLatitud(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.LATITUD)));
                bpv.setLongitud(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.LONGITUD)));
                bpv.setFoto(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.FOTO)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<BasePreguntas> getAllQuestions(String canal) {
        List<BasePreguntas> quesList = new ArrayList<BasePreguntas>();
        Log.i("CANAL", canal);
        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractPreguntas.TABLE_QUEST + " WHERE " + ContractPreguntas.Columnas.KEY_CANAL + "=? "+ "  ORDER BY " + Constantes.ID_REMOTA + " + 0";
//        String selectQuery = "SELECT  * FROM " + ContractPreguntas.TABLE_QUEST + " WHERE " + ContractPreguntas.Columnas.KEY_CANAL + "=? "+ "  ORDER BY " + Constantes.ID_REMOTA;
        db=this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{canal});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePreguntas quest = new BasePreguntas();
                quest.setQuestion(cursor.getString(1));
                quest.setAnswer(cursor.getString(2));
                quest.setOpta(cursor.getString(3));
                quest.setOptb(cursor.getString(4));
                quest.setOptc(cursor.getString(5));
                quest.setCanal(cursor.getString(6));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        Log.i("SIZE", quesList.size() + "");
        // return quest list
        return quesList;
    }

    public int rowcount(String canal) {
        int row=0;
        String selectQuery = "SELECT * FROM " + ContractPreguntas.TABLE_QUEST + " WHERE " + ContractPreguntas.Columnas.KEY_CANAL + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{canal});
        row=cursor.getCount();
        return row;
    }

//    public int rowcount() {
//        int row=0;
//        String selectQuery = "SELECT  * FROM " + ContractPreguntas.TABLE_QUEST;
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        row=cursor.getCount();
//        return row;
//    }
    public ArrayList<BasePharmaValue> consultarBasePharmaValueByDay(String user, String fecha){
        Log.i("USUARIO", user);
        Log.i("FECHA", fecha);
        ArrayList<BasePharmaValue> operadores = new ArrayList<BasePharmaValue>();

        String selectQuery = "SELECT " +
                ContractPharmaValue.Columnas.POS_ID + ", "+
                ContractPharmaValue.Columnas.POS_NAME +
                " FROM " + ContractPharmaValue.POS + " WHERE "+
                ContractPharmaValue.Columnas.USER +"=? AND " +
                ContractPharmaValue.Columnas.FECHA_VISITA +"=? " +
                " ORDER BY " + ContractPharmaValue.Columnas.POS_ID + " ASC " ;
        // Select All Query

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user, fecha});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePharmaValue bpv = new BasePharmaValue();
                bpv.setPos_id(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_ID)));
                bpv.setPos_name(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_NAME)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public boolean tieneJustificacion(String codigo,String usuario){

        boolean id = false;

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String fechaser = date.format(currentLocalTime);

        String selectQuery = "";

        String tipo = "JUSTIFICACION";

        selectQuery = "SELECT * "+ " FROM " + ContractInsertGps.INSERT_GPS + " WHERE "+
                ContractInsertGps.Columnas.IDPDV +"=? AND " +
                ContractInsertGps.Columnas.USUARIO +"=? AND " +
                ContractInsertGps.Columnas.TIPO +"=? AND " +
                ContractInsertGps.Columnas.FECHA +"=? ";

        // Select All Query

        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(selectQuery, new String[]{codigo,usuario,tipo,fechaser});

        if(cursor != null && cursor.moveToFirst()){
            id = true;
        }

        cursor.close();
        db.close();
        return id;

    }


//    public boolean tieneJustificacion(String codigo){
//
//        boolean id = false;
//        List<String> operadores = new ArrayList<String>();
//
//        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
//        Date currentLocalTime = cal.getTime();
//        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
//        date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
//        String fechaser = date.format(currentLocalTime);
//
//        String selectQuery = "";
//
//
//        selectQuery = "SELECT * "+ " FROM " + ContractInsertGps.INSERT_GPS + " WHERE "+
//                ContractInsertGps.Columnas.IDPDV +"=? AND " +
//                ContractInsertGps.Columnas.FECHA +"=? ";
//
//        // Select All Query
//
//        db = this.getReadableDatabase();
//        Cursor cursor =  db.rawQuery(selectQuery, new String[]{codigo,fechaser});
//
//        if(cursor != null && cursor.moveToFirst()){
//            operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertGps.Columnas.POS_NAME)));
//            Log.i("RESULTADO CONSULTA",""+ operadores);
//            id = true;
//        }
//
//        cursor.close();
//        db.close();
//        return id;
//
//    }

    public List<String> getJustificacion(){
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ContractJustificacion.Columnas.JUSTIFICACION + " FROM " + ContractJustificacion.JUSTIFICACIONES + " ORDER BY _id ASC";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractJustificacion.Columnas.JUSTIFICACION)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public Base_pharma_value getInfoPDV(String codigo_pdv){
        Base_pharma_value bpv = new Base_pharma_value();

        String selectQuery = "SELECT * FROM " + ContractPharmaValue.POS +
                " WHERE "+ ContractPharmaValue.Columnas.POS_ID +"=?";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo_pdv});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                bpv.setPos_id(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_ID)));
                bpv.setChannel(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CHANNEL)));
                bpv.setCustomer_owner(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CUSTOMER_OWNER)));
                bpv.setPos_name(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_NAME)));
                bpv.setPos_name_dpsm(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.POS_NAME_DPSM)));
                bpv.setRegion(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.REGION)));
//                bpv.setKam(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.KAM)));
                bpv.setProvince(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.PROVINCIA)));
                bpv.setCity(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CIUDAD)));
                bpv.setZone(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.ZONA)));
                bpv.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.DIRECCION)));
                bpv.setSupervisor(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.SUPERVISOR)));
//                bpv.setMercaderista(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.MERCADERISTA)));
                bpv.setLatitud(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.LATITUD)));
                bpv.setLongitud(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.LONGITUD)));
//                bpv.setFoto(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.FOTO)));
//                bpv.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.STATUS)));
//                bpv.setDpsm(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.DPSM)));
                bpv.setTipo(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.TIPO)));
//                bpv.setMerchandising(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.MERCHANDISING)));
                bpv.setSubchannel(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.SUBCHANNEL)));
                bpv.setChannel_segment(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.CHANNEL_SEGMENT)));
                bpv.setFormat(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.FORMAT)));
//                bpv.setSales_executive(cursor.getString(cursor.getColumnIndexOrThrow(ContractPharmaValue.Columnas.SALES_EXECUTIVE)));

            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return bpv;
    }

    public ArrayList<String> getEstadoMarcacionPdv(String codigo, String usuario, String fecha){
        ArrayList<String> operadores = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + ContractInsertGps.INSERT_GPS + " WHERE "+
                ContractInsertGps.Columnas.IDPDV + "=? AND " +
                ContractInsertGps.Columnas.USUARIO + "=? AND " +
                ContractInsertGps.Columnas.FECHA + "=? " +
                " GROUP BY " + ContractInsertGps.Columnas.TIPO;

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo, usuario, fecha});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertGps.Columnas.TIPO)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables

        return operadores ;
    }

    public List<String> getCategoriaMallaCodificados(String tipo, String fabricante, String canal, String subcanal) {
        List<String> operadores = new ArrayList<String>();
        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SECTOR +
                " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE ";

        List<String> parametersList = new ArrayList<String>();
        parametersList.add("%" + fabricante + "%");

        if (tipo.equalsIgnoreCase("COMPETENCIA")) {
            selectQuery += ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ?";
        } else {
            selectQuery += ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
        }

        if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery += " AND " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            parametersList.add("%" + subcanal.trim() + "%");
        }

        String[] parametersArr = new String[parametersList.size()];
        parametersArr = parametersList.toArray(parametersArr);

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, parametersArr);

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
            } while (cursor.moveToNext() && cursor!=null);
        }
        Log.i("OPERADORES",operadores.size()+"");
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getSubcategoriaMallaCodificados(String categoria, String tipo, String manufacturer, String canal, String subcanal){
        List<String> operadores = new ArrayList<String>();

        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SUBCATEGORIA +
                " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                " WHERE " + ContractPortafolioProductos.Columnas.SECTOR + "=? AND ";

        List<String> parametersList = new ArrayList<String>();
        parametersList.add(categoria);
        parametersList.add("%" + manufacturer + "%");

        if (tipo.equalsIgnoreCase("COMPETENCIA")) {
            selectQuery += ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ?";
        } else {
            selectQuery += ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
        }

        if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery += " AND " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            parametersList.add("%" + subcanal.trim() + "%");
        }

        String[] parametersArr = new String[parametersList.size()];
        parametersArr = parametersList.toArray(parametersArr);

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, parametersArr);

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SUBCATEGORIA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> getBrand2MallaCodificados(String categoria, String subcategoria, String tipo, String manufacturer, String canal, String subcanal){
        List<String> operadores = new ArrayList<String>();

        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.MARCA +
                " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                " WHERE " + ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                ContractPortafolioProductos.Columnas.SUBCATEGORIA + "=? AND ";

        List<String> parametersList = new ArrayList<String>();
        parametersList.add(categoria);
        parametersList.add(subcategoria);
        parametersList.add("%" + manufacturer + "%");

        if (tipo.equalsIgnoreCase("COMPETENCIA")) {
            selectQuery += ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ?";
        } else {
            selectQuery += ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
        }

        if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery += " AND " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            parametersList.add("%" + subcanal.trim() + "%");
        }

        String[] parametersArr = new String[parametersList.size()];
        parametersArr = parametersList.toArray(parametersArr);

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, parametersArr);

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.MARCA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BasePortafolioProductos> filtrarListProductosMallaCodificados(String categoria, String tipo, String manufacturer, String brand, String canal, String subcanal){
        ArrayList<BasePortafolioProductos> operadores = new ArrayList<BasePortafolioProductos>();

        String selectQuery = "SELECT DISTINCT "+ ContractPortafolioProductos.Columnas.SKU +
                " FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS +
                " WHERE " + ContractPortafolioProductos.Columnas.SECTOR + "=? AND " +
                ContractPortafolioProductos.Columnas.MARCA + "=? AND ";

        List<String> parametersList = new ArrayList<String>();
        parametersList.add(categoria);
        parametersList.add(brand);
        parametersList.add("%" + manufacturer + "%");

        if (tipo.equalsIgnoreCase("COMPETENCIA")) {
            selectQuery += ContractPortafolioProductos.Columnas.FABRICANTE + " NOT LIKE ?";
        } else {
            selectQuery += ContractPortafolioProductos.Columnas.FABRICANTE + " LIKE ?";
        }

        if (canal.equalsIgnoreCase("AUTOSERVICIO")) {
            selectQuery += " AND " + ContractPortafolioProductos.Columnas.CADENAS + " LIKE ?";
            parametersList.add("%" + subcanal.trim() + "%");
        }

        String[] parametersArr = new String[parametersList.size()];
        parametersArr = parametersList.toArray(parametersArr);

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, parametersArr);

//        operadores.add(new Base_portafolio_productos("HEADER"));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BasePortafolioProductos bpv = new BasePortafolioProductos();
                bpv.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SKU)));
                operadores.add(bpv);
            } while (cursor.moveToNext());
        }

        /*
        if (!brand.equalsIgnoreCase("SELECCIONE")) {
            operadores.add(new BasePortafolioProductos("Otros"));
        }*/

        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getEstadoMallaCodificados(String codigo, String user, String sku) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
              //  cal.set(2023,Calendar.FEBRUARY,10);
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd/MM/yyy");
        date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String fecha_actual = date.format(currentLocalTime);
        String mes_actual = fecha_actual.substring(3);


        cal.set(Calendar.DAY_OF_MONTH,1);
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String fecha_inicio_mes = formato.format(cal.getTime());


        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String  fecha_fin_mes = formato.format(cal.getTime());



        Log.i("FECHAS", "FECHA ACTUAL: " + fecha_actual + " - MES ACTUAL: " + mes_actual);

        String operadores = "PENDIENTE";
        String selectQuery = "";
        Cursor cursor = null;

        selectQuery = "SELECT * FROM " + ContractInsertCodificados.INSERT_CODIFICADOS +
                " WHERE " + ContractInsertCodificados.Columnas.CODIGO + " =? AND " +
                ContractInsertCodificados.Columnas.USUARIO + "=? AND " +
                ContractInsertCodificados.Columnas.FECHA + "=? AND " +
                // Formateamos la fecha de dd/mm/yyyy a yyyy-mm-dd
                " datetime(substr(fecha, 7, 4) || '-' || substr(fecha, 4, 2) || '-' || substr(fecha, 1, 2))" +
                " BETWEEN ? AND ? AND " +
                ContractInsertCodificados.Columnas.SKU_CODE + "=?";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{codigo, user, fecha_actual,fecha_inicio_mes,fecha_fin_mes, sku});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = "REALIZADO";
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getSkuOsa(String codigo, String user,String sku) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        //  cal.set(2023,Calendar.FEBRUARY,10);
    //    Date currentLocalTime = cal.getTime();
    //    DateFormat date = new SimpleDateFormat("dd/MM/yyy");
    //    date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
    //     String fecha_actual = date.format(currentLocalTime);
   //      String mes_actual = fecha_actual.substring(3);


        cal.set(Calendar.DAY_OF_MONTH,1);
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String fecha_inicio_mes = formato.format(cal.getTime());


        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String  fecha_fin_mes = formato.format(cal.getTime());



     //   Log.i("FECHAS", "FECHA ACTUAL: " + fecha_actual + " - MES ACTUAL: " + mes_actual);

        String operadores = "NO REVELADO";
        String selectQuery = "";
        Cursor cursor = null;

        selectQuery = "SELECT * FROM " + ContractInsertValores.INSERT_VALORES +
                " WHERE " +
                ContractInsertValores.Columnas.CODIGO + " =? AND " +
                ContractInsertValores.Columnas.USUARIO + "=? AND " +
                ContractInsertValores.Columnas.SKU_CODE + "=? AND " +
                " strftime('%Y-%m-%d',fecha) " + " BETWEEN ? AND ?" +
                " ORDER BY fecha DESC LIMIT 1";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{codigo,user,sku,fecha_inicio_mes,fecha_fin_mes});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores = "REVELADO";
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public void actualizarCoordenadasPDV(String codigo_pdv, double latitud, double longitud) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ContractPharmaValue.Columnas.LATITUD,latitud);
        values.put(ContractPharmaValue.Columnas.LONGITUD, longitud);

        db.update(ContractPharmaValue.POS, values, "pos_id=?", new String[]{codigo_pdv});
        db.close();
    }


    public BasePortafolioProductos getProducto(String sku) {
        BasePortafolioProductos bpp = null;

        // Select All Query
        String selectQuery = "SELECT * FROM " + ContractPortafolioProductos.PORTAFOLIOPRODUCTOS + " WHERE "+
                ContractPortafolioProductos.Columnas.SKU + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{sku});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                bpp = new BasePortafolioProductos();
                bpp.setSector(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SECTOR)));
                bpp.setSegmento(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.SEGMENTO)));
                bpp.setFabricante(cursor.getString(cursor.getColumnIndexOrThrow(ContractPortafolioProductos.Columnas.FABRICANTE)));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return bpp;
    }

    // below is the method for updating our courses
    public void updateCourse() {

        // calling a method to get writable database.
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.putNull(Constantes.ID_REMOTA);
        values.put(Constantes.ESTADO, "0");
        values.put(Constantes.PENDIENTE_INSERCION, "1");

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(InsertFlooring.INSERT_FLOORING, values, InsertFlooring.Columnas.FECHA+" LIKE '%/04/2023%'", null);
        db.close();
    }



    //ENCUESTA
    public ArrayList<BaseEvaluacionPromotor> filtrarListEncuestas(String re, String txt) {
        ArrayList<BaseEvaluacionPromotor> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = null;

        String nom_encuesta = "";
        if(!txt.equalsIgnoreCase("")){

            selectQuery = "SELECT * FROM " + ContractEvaluacionEncuesta.EVALUACION_PROMOTOR +
                    " WHERE " + ContractEvaluacionEncuesta.Columnas.RE  + " =? " +
                    " AND " + ContractEvaluacionEncuesta.Columnas.NOMBRE_ENCUESTA + " LIKE ? " +
                    " GROUP BY " + ContractEvaluacionEncuesta.Columnas.NOMBRE_ENCUESTA + " " +
                    " ORDER BY " + ContractEvaluacionEncuesta.Columnas.CATEGORIA + ", " + ContractEvaluacionEncuesta.Columnas.PREGUNTA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{re, "%" + txt + "%"});
        }else{
            selectQuery = "SELECT * FROM " + ContractEvaluacionEncuesta.EVALUACION_PROMOTOR +
                    " WHERE " + ContractEvaluacionEncuesta.Columnas.RE  + " =? " +
                    " GROUP BY " + ContractEvaluacionEncuesta.Columnas.NOMBRE_ENCUESTA + " " +
                    " ORDER BY " + ContractEvaluacionEncuesta.Columnas.CATEGORIA + ", " + ContractEvaluacionEncuesta.Columnas.PREGUNTA;
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{re});
        }




        if (cursor.moveToFirst()) {
            do {
                BaseEvaluacionPromotor p = new BaseEvaluacionPromotor();
                p.setId(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas._ID)));
                p.setNombre_encuesta(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.NOMBRE_ENCUESTA)));
                p.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.DESCRIPCION)));
                p.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.CATEGORIA)));
                p.setRe(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.RE)));
                p.setPregunta(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.PREGUNTA)));
                p.setTipo_pregunta(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.TIPO_PREGUNTA)));
                p.setPuntaje_por_pregunta(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.PUNTAJE_POR_PREGUNTA)));
                operadores.add(p);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public ArrayList<BaseEvaluacionPromotor> filtrarListPreguntas(String encuesta, String re) {
        ArrayList<BaseEvaluacionPromotor> operadores = new ArrayList<>();

        Cursor cursor = null;
        String selectQuery = null;

        selectQuery = "SELECT * FROM " + ContractEvaluacionEncuesta.EVALUACION_PROMOTOR +
                " WHERE " + ContractEvaluacionEncuesta.Columnas.NOMBRE_ENCUESTA  + " =? AND " +
                ContractEvaluacionEncuesta.Columnas.RE + " =? " +
                " ORDER BY " + ContractEvaluacionEncuesta.Columnas.CATEGORIA + ", " + ContractEvaluacionEncuesta.Columnas.PREGUNTA;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, new String[]{encuesta, re});

        if (cursor.moveToFirst()) {
            do {
                BaseEvaluacionPromotor p = new BaseEvaluacionPromotor();
                p.setId(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas._ID)));
                p.setNombre_encuesta(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.NOMBRE_ENCUESTA)));
                p.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.DESCRIPCION)));
                p.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.CATEGORIA)));
                p.setRe(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.RE)));
                p.setPregunta(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.PREGUNTA)));
                p.setTipo_pregunta(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.TIPO_PREGUNTA)));
                p.setOpc_a(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.OPC_A)));
                p.setOpc_b(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.OPC_B)));
                p.setOpc_c(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.OPC_C)));
                p.setOpc_d(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.OPC_D)));
                p.setOpc_e(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.OPC_E)));
                p.setFoto(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.FOTO)));
                p.setTipo_campo(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.TIPO_CAMPO)));
                p.setPuntaje_por_pregunta(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.PUNTAJE_POR_PREGUNTA)));
                p.setHabilitado(cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.HABILITADO)));
                operadores.add(p);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public String getPuntajePregunta(String pregunta) {
        String puntaje = "";

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractEvaluacionEncuesta.Columnas.PUNTAJE_POR_PREGUNTA + " FROM " +
                ContractEvaluacionEncuesta.EVALUACION_PROMOTOR + " WHERE " +
                ContractEvaluacionEncuesta.Columnas.PREGUNTA + "=?";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{pregunta});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                puntaje = cursor.getString(cursor.getColumnIndexOrThrow(ContractEvaluacionEncuesta.Columnas.PUNTAJE_POR_PREGUNTA));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return puntaje;
    }

    public String getPuntajeTotalPregunta(String encuesta, String re) {
        String puntaje = "";

        // Select All Query
        String selectQuery = "SELECT SUM(" + ContractEvaluacionEncuesta.Columnas.PUNTAJE_POR_PREGUNTA + ") AS TOTAL FROM " +
                ContractEvaluacionEncuesta.EVALUACION_PROMOTOR +
                " WHERE " + ContractEvaluacionEncuesta.Columnas.NOMBRE_ENCUESTA + " =? AND " +
                ContractEvaluacionEncuesta.Columnas.RE + " =? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{encuesta, re});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                puntaje = cursor.getString(cursor.getColumnIndexOrThrow("TOTAL"));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return puntaje;
    }
    //exh adic

    public List<String> getHerramientasBassa(String cp_code, String fabricante){
        Log.i("CP-CODE", cp_code);
        List<String> categorias = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR +
                " WHERE " + ContractExhibicionSupervisor.Columnas.CP_CODE + "=? AND " +
                ContractExhibicionSupervisor.Columnas.MANUFACTURER + " LIKE ?" +
                " GROUP BY "+ ContractExhibicionSupervisor.Columnas.CONVENIO;

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{cp_code, "%" + fabricante + "%"});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                categorias.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.CONVENIO)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        Log.i("TipoExh", categorias.size()+"");
        // returning lables
        return categorias;
    }

    public List<String> getTipoExhSupervisor(String codigo, String herramienta, String fabricante){
        List<String> categorias = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR + " WHERE "+
                ContractExhibicionSupervisor.Columnas.CP_CODE + "=?AND " +
                ContractExhibicionSupervisor.Columnas.CONVENIO + "=? AND " +
                ContractExhibicionSupervisor.Columnas.MANUFACTURER + " LIKE ?" +
                " GROUP BY "+ ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo, herramienta, "%" + fabricante + "%"});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                categorias.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return categorias;
    }

    public List<String> getTipoHerrExhSupervisor(String codigo, String herramienta, String tipoexh){
        List<String> categorias = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR +
                " WHERE " + ContractExhibicionSupervisor.Columnas.CP_CODE + "=? AND " +
                ContractExhibicionSupervisor.Columnas.CONVENIO + "=? AND " +
                ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL + "=? " +
                " GROUP BY "+ ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo, herramienta, tipoexh});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                categorias.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return categorias;
    }

    public List<String> getCampana(String codigo, String herramienta, String tipoexh, String tipoherr){
        List<String> categorias = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR +
                " WHERE " + ContractExhibicionSupervisor.Columnas.CP_CODE + "=? AND " +
                ContractExhibicionSupervisor.Columnas.CONVENIO + "=? AND " +
                ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL + "=? AND " +
                ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA + "=? " +
                " GROUP BY "+ ContractExhibicionSupervisor.Columnas.CAMPANA;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo, herramienta, tipoexh, tipoherr});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                categorias.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.CAMPANA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return categorias;
    }


    public List<String> getTipoExhColgAdicionales(String reExtra){
        List<String> categorias = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractExhibicion.TIPOEXH + " WHERE "+
                ContractExhibicion.Columnas.RE + "=? AND " +
                ContractExhibicion.Columnas.TIPO + " LIKE '%Adicionales%'" +
                " GROUP BY "+ ContractExhibicion.Columnas.TIPO;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{reExtra});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                categorias.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicion.Columnas.TIPO)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return categorias;
    }

    public List<String> getTipoHerramientas(String reExtra){
        List<String> categorias = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractExhibicion.TIPOEXH + " WHERE "+
                ContractExhibicion.Columnas.RE + "=? AND " +
                ContractExhibicion.Columnas.TIPO_HERRAMIENTA + " <> 'CP' AND " +
                ContractExhibicion.Columnas.TIPO_HERRAMIENTA + " <> 'COMPETENCIA' " +
                " GROUP BY "+ ContractExhibicion.Columnas.TIPO_HERRAMIENTA;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{reExtra});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                categorias.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicion.Columnas.TIPO_HERRAMIENTA)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return categorias;
    }

    public List<String> getHerramientasExhibicionesBassa(String tipoHerramienta,String re){
        List<String> fabricante = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractExhibicion.TIPOEXH + " WHERE "+
                ContractExhibicion.Columnas.TIPO_HERRAMIENTA + "=? AND " +
                ContractExhibicion.Columnas.RE + "=?" +
                " GROUP BY " + ContractExhibicion.Columnas.TIPO;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{tipoHerramienta, re});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                fabricante.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicion.Columnas.TIPO)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return fabricante;
    }

    public List<String> getFabricanteHerramientasExhibicionesBassa(String tipoHerramienta, String tipoExhSelected, String re){
        List<String> fabricante = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ContractExhibicion.TIPOEXH + " WHERE "+
                ContractExhibicion.Columnas.TIPO_HERRAMIENTA + "=? AND " +
                ContractExhibicion.Columnas.TIPO + "=? AND " +
                ContractExhibicion.Columnas.RE + "=?" +
                " GROUP BY " + ContractExhibicion.Columnas.FABRICANTE;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{tipoHerramienta, tipoExhSelected, re});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                fabricante.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicion.Columnas.FABRICANTE)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return fabricante;
    }





    public List<String> filtrarListSubcategoria2(String tipoHerramienta, String tipo_exhibicion, String fabricante, String re){
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractExhibicion.Columnas.SUBCAT +
                " FROM " + ContractExhibicion.TIPOEXH + " WHERE "+
                ContractExhibicion.Columnas.TIPO_HERRAMIENTA +"=? AND " +
                ContractExhibicion.Columnas.TIPO +"=? AND " +
                ContractExhibicion.Columnas.FABRICANTE +"=? AND " +
                ContractExhibicion.Columnas.RE +"=? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{tipoHerramienta, tipo_exhibicion, fabricante, re});

        operadores.add("Seleccione");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicion.Columnas.SUBCAT)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }


    public String getNumExh(String cp_code, String tipoexh, String tipoherr, String campana, String subcategory){
        String num_exh = "";

        // Select All Query
        String selectQuery = "SELECT " + ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS + " FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR + " WHERE "+
                ContractExhibicionSupervisor.Columnas.CP_CODE +"=? AND " +
                ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL +"=? AND " +
                ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CAMPANA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.SUBCATEGORY +"=? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{cp_code,tipoexh,tipoherr,campana,subcategory});

        // looping through all rows and adding to list
        if (cursor!=null && cursor.moveToFirst()) {
            num_exh = (cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS)));
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return num_exh;
    }

    public String getNumExhCompetencia(String cp_code, String tipoexh, String tipoherr, String subcategory){
        String num_exh = "";

        // Select All Query
        String selectQuery = "SELECT " + ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS + " FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR + " WHERE "+
                ContractExhibicionSupervisor.Columnas.CP_CODE +"=? AND " +
                ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL +"=? AND " +
                ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.SUBCATEGORY +"=? AND " +
                ContractExhibicionSupervisor.Columnas.MANUFACTURER +" NOT LIKE '%COLGATE%'";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{cp_code,tipoexh,tipoherr,subcategory});

        // looping through all rows and adding to list
        if (cursor!=null && cursor.moveToFirst()) {
            num_exh = (cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS)));
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return num_exh;
    }

    public String getCategoriaExh(String cp_code, String tipoexh, String tipoherr, String campana, String subcategory, String num_exh){
        String categoria = "";

        // Select All Query
        String selectQuery = "SELECT " + ContractExhibicionSupervisor.Columnas.CATEGORY + " FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR + " WHERE "+
                ContractExhibicionSupervisor.Columnas.CP_CODE +"=? AND " +
                ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL +"=? AND " +
                ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CAMPANA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.SUBCATEGORY +"=? AND " +
                ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS +"=? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{cp_code,tipoexh,tipoherr,campana,subcategory,num_exh});

        // looping through all rows and adding to list
        if (cursor!=null && cursor.moveToFirst()) {
            categoria = (cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.CATEGORY)));
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return categoria;
    }

    public String getCategoriaExhCompetencia(String cp_code, String tipoexh, String tipoherr, String subcategory, String num_exh){
        String categoria = "";

        // Select All Query
        String selectQuery = "SELECT " + ContractExhibicionSupervisor.Columnas.CATEGORY + " FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR + " WHERE "+
                ContractExhibicionSupervisor.Columnas.CP_CODE +"=? AND " +
                ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL +"=? AND " +
                ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.SUBCATEGORY +"=? AND " +
                ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS +"=? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{cp_code,tipoexh,tipoherr,subcategory,num_exh});

        // looping through all rows and adding to list
        if (cursor!=null && cursor.moveToFirst()) {
            categoria = (cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.CATEGORY)));
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return categoria;
    }

    public String getObservacionExh(String cp_code, String tipoexh, String tipoherr, String campana, String subcategory, String numExh, String categoria){
        String observacion = "";

        // Select All Query
        String selectQuery = "SELECT " + ContractExhibicionSupervisor.Columnas.OBSERVATION + " FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR + " WHERE "+
                ContractExhibicionSupervisor.Columnas.CP_CODE +"=? AND " +
                ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL +"=? AND " +
                ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CAMPANA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.SUBCATEGORY +"=? AND " +
                ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CATEGORY +"=? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{cp_code,tipoexh,tipoherr,campana,subcategory,numExh,categoria});

        // looping through all rows and adding to list
        if (cursor!=null && cursor.moveToFirst()) {
            observacion = (cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.OBSERVATION)));
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return observacion;
    }

    public String getObservacionExhCompetencia(String cp_code, String tipoexh, String tipoherr, String subcategory, String numExh, String categoria){
        String observacion = "";

        // Select All Query
        String selectQuery = "SELECT " + ContractExhibicionSupervisor.Columnas.OBSERVATION + " FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR + " WHERE "+
                ContractExhibicionSupervisor.Columnas.CP_CODE +"=? AND " +
                ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL +"=? AND " +
                ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.SUBCATEGORY +"=? AND " +
                ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CATEGORY +"=? AND " +
                ContractExhibicionSupervisor.Columnas.MANUFACTURER +" NOT LIKE '%COLGATE%'";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{cp_code,tipoexh,tipoherr,subcategory,numExh,categoria});

        // looping through all rows and adding to list
        if (cursor!=null && cursor.moveToFirst()) {
            observacion = (cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.OBSERVATION)));
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return observacion;
    }

    public String getClasificacionExh(String cp_code, String tipoexh, String tipoherr, String campana, String subcategory, String numExh, String categoria, String observacion){
        String clasificacion = "";

        // Select All Query
        String selectQuery = "SELECT " + ContractExhibicionSupervisor.Columnas.CLASIFICACION + " FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR + " WHERE "+
                ContractExhibicionSupervisor.Columnas.CP_CODE +"=? AND " +
                ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL +"=? AND " +
                ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CAMPANA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.SUBCATEGORY +"=? AND " +
                ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CATEGORY +"=? AND " +
                ContractExhibicionSupervisor.Columnas.OBSERVATION +"=? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{cp_code,tipoexh,tipoherr,campana,subcategory,numExh,categoria,observacion});

        // looping through all rows and adding to list
        if (cursor!=null && cursor.moveToFirst()) {
            clasificacion = (cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.CLASIFICACION)));
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return clasificacion;
    }

    public String getClasificacionExhCompetencia(String cp_code, String tipoexh, String tipoherr, String subcategory, String numExh, String categoria, String observacion){
        String clasificacion = "";

        // Select All Query
        String selectQuery = "SELECT " + ContractExhibicionSupervisor.Columnas.CLASIFICACION + " FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR + " WHERE "+
                ContractExhibicionSupervisor.Columnas.CP_CODE +"=? AND " +
                ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL +"=? AND " +
                ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.SUBCATEGORY +"=? AND " +
                ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CATEGORY +"=? AND " +
                ContractExhibicionSupervisor.Columnas.OBSERVATION +"=? AND " +
                ContractExhibicionSupervisor.Columnas.MANUFACTURER +" NOT LIKE '%COLGATE%'";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{cp_code,tipoexh,tipoherr,subcategory,numExh,categoria,observacion});

        // looping through all rows and adding to list
        if (cursor!=null && cursor.moveToFirst()) {
            clasificacion = (cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.CLASIFICACION)));
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return clasificacion;
    }

    public String getCampanaExhCompetencia(String cp_code, String tipoexh, String tipoherr, String subcategory, String numExh, String categoria, String observacion, String clasificacion){
        String campana = "";

        // Select All Query
        String selectQuery = "SELECT " + ContractExhibicionSupervisor.Columnas.CAMPANA + " FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR + " WHERE "+
                ContractExhibicionSupervisor.Columnas.CP_CODE +"=? AND " +
                ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL +"=? AND " +
                ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.SUBCATEGORY +"=? AND " +
                ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CATEGORY +"=? AND " +
                ContractExhibicionSupervisor.Columnas.OBSERVATION +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CLASIFICACION +"=? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{cp_code,tipoexh,tipoherr,subcategory,numExh,categoria,observacion});

        // looping through all rows and adding to list
        if (cursor!=null && cursor.moveToFirst()) {
            campana = (cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.CAMPANA)));
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return campana;
    }

    public String getIDExh(String cp_code, String tipoexh, String tipoherr, String campana, String subcategory, String numExh, String categoria, String observacion, String clasificacion, String user){
        String id_r_exh = "";

        // Select All Query
        String selectQuery = "SELECT " + ContractExhibicionSupervisor.Columnas.ID_REMOTA + " FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR + " WHERE "+
                ContractExhibicionSupervisor.Columnas.CP_CODE +"=? AND " +
                ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL +"=? AND " +
                ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CAMPANA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.SUBCATEGORY +"=? AND " +
                ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CATEGORY +"=? AND " +
                ContractExhibicionSupervisor.Columnas.OBSERVATION +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CLASIFICACION +"=? AND " +
                ContractExhibicionSupervisor.Columnas.SUPERVISOR +"=? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{cp_code,tipoexh,tipoherr,campana,subcategory,numExh,categoria,observacion,clasificacion,user});

        // looping through all rows and adding to list
        if (cursor!=null && cursor.moveToFirst()) {
            id_r_exh = (cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.ID_REMOTA)));
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return id_r_exh;
    }

    public String getIDExhCompetencia(String cp_code, String tipoexh, String tipoherr, String subcategory, String numExh, String categoria, String observacion, String clasificacion, String campana, String user){
        String id_r_exh = "";

        // Select All Query
        String selectQuery = "SELECT " + ContractExhibicionSupervisor.Columnas.ID_REMOTA + " FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR + " WHERE "+
                ContractExhibicionSupervisor.Columnas.CP_CODE +"=? AND " +
                ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL +"=? AND " +
                ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.SUBCATEGORY +"=? AND " +
                ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CATEGORY +"=? AND " +
                ContractExhibicionSupervisor.Columnas.OBSERVATION +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CLASIFICACION +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CAMPANA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.SUPERVISOR +"=? AND " +
                ContractExhibicionSupervisor.Columnas.MANUFACTURER +" NOT LIKE '%COLGATE%'";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{cp_code,tipoexh,tipoherr,subcategory,numExh,categoria,observacion,clasificacion,campana,user});

        // looping through all rows and adding to list
        if (cursor!=null && cursor.moveToFirst()) {
            id_r_exh = (cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.ID_REMOTA)));
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return id_r_exh;
    }

    public List<String> filtrarListSubcategoriaColgate(String codigo, String herramienta, String tipoexh, String tipoherr, String campana){
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + ContractExhibicionSupervisor.Columnas.SUBCATEGORY + " FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR + " WHERE "+
                ContractExhibicionSupervisor.Columnas.CP_CODE +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CONVENIO +"=? AND " +
                ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL +"=? AND " +
                ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CAMPANA +"=? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo, herramienta, tipoexh, tipoherr, campana});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.SUBCATEGORY)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> filtrarListNumExhColgate(String codigo, String herramienta, String tipoexh, String tipoherr, String campana){
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT " + ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS + " FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR + " WHERE "+
                ContractExhibicionSupervisor.Columnas.CP_CODE +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CONVENIO +"=? AND " +
                ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL +"=? AND " +
                ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CAMPANA +"=? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo, herramienta, tipoexh, tipoherr, campana});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public List<String> filtrarListClasificacionColgate(String codigo, String herramienta, String tipoexh, String tipoherr, String campana){
        List<String> operadores = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT " + ContractExhibicionSupervisor.Columnas.CLASIFICACION + " FROM " + ContractExhibicionSupervisor.EXHIBICIONSUPERVISOR + " WHERE "+
                ContractExhibicionSupervisor.Columnas.CP_CODE +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CONVENIO +"=? AND " +
                ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL +"=? AND " +
                ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA +"=? AND " +
                ContractExhibicionSupervisor.Columnas.CAMPANA +"=? ";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo, herramienta, tipoexh, tipoherr, campana});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                operadores.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractExhibicionSupervisor.Columnas.CLASIFICACION)));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    // NOVEDADES NEW
//    public ArrayList<Base_productos_novedades> filtrarListProductosNovedades(String codigo_pdv, String cuenta) {
//        ArrayList<Base_productos_novedades> operadores = new ArrayList<Base_productos_novedades>();
//        // Select All Query
//        String selectQuery = null;
//        Cursor cursor = null;
//
//        Log.i("cuenta", cuenta);
//
//        selectQuery = "SELECT DISTINCT " + ContractProductosNovedades.Columnas.SKU + " FROM " +
//                ContractProductosNovedades.PRODUCTOS_NOVEDADES +
//                " WHERE " + ContractProductosNovedades.Columnas.CODIGO_PDV + " =? AND " +
//               ContractPortafolioProductos.Columnas.CUENTA + " =? " +
//                "ORDER BY " + ContractProductosNovedades.Columnas.SKU + " ASC";
//        db = this.getReadableDatabase();
//        cursor = db.rawQuery(selectQuery, new String[]{codigo_pdv,cuenta});
//
//
//        // operadores.add("Seleccione");
//        //operadores.add("Todos");
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Base_productos_novedades bpv = new Base_productos_novedades();
//                bpv.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractProductosNovedades.Columnas.SKU)));
//                operadores.add(bpv);
//            } while (cursor.moveToNext() && cursor != null);
//        }
//        Log.i("OPERADORES_CATEGORIA", operadores.size() + "");
//        // closing connection
//        cursor.close();
//        db.close();
//        // returning lables
//        return operadores;
//    }

//    public ArrayList<Base_productos_novedades> filtrarListProductosNovedades(String codigo_pdv) { // <- Se quitó el parámetro "cuenta"
//        ArrayList<Base_productos_novedades> operadores = new ArrayList<>();
//        Cursor cursor = null;
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String selectQuery = "SELECT DISTINCT " +
//                ContractProductosNovedades.Columnas.SKU +
//                " FROM " + ContractProductosNovedades.PRODUCTOS_NOVEDADES +
//                " WHERE " + ContractProductosNovedades.Columnas.CODIGO_PDV + " = ? " + // <- Se eliminó filtro por cuenta
//                " ORDER BY " + ContractProductosNovedades.Columnas.SKU + " ASC";
//
//        cursor = db.rawQuery(selectQuery, new String[]{codigo_pdv}); // <- Solo se envía codigo_pdv
//
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                Base_productos_novedades bpv = new Base_productos_novedades();
//                bpv.setSku(cursor.getString(cursor.getColumnIndexOrThrow(
//                        ContractProductosNovedades.Columnas.SKU
//                )));
//                operadores.add(bpv);
//            } while (cursor.moveToNext());
//        }
//
//        if (cursor != null) {
//            cursor.close();
//        }
//        db.close();
//
//        return operadores;
//    }

    public List<String> getTipoNovedades() {
        List<String> operadores = new ArrayList<>();
        String selectQuery;
        Cursor cursor = null;

        // Consulta sin filtrar por cuenta
        selectQuery = "SELECT DISTINCT " + ContractTipoNovedades.Columnas.TIPO_NOVEDAD +
                " FROM " + ContractTipoNovedades.TIPO_NOVEDADES;

        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);  // Sin parámetros

        operadores.add("Seleccione");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String tipo = cursor.getString(
                        cursor.getColumnIndexOrThrow(ContractTipoNovedades.Columnas.TIPO_NOVEDAD)
                );
                operadores.add(tipo);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return operadores;
    }

    //mpin tipo actividades
    public List<String> getTipoActividades() {
        List<String> operadores = new ArrayList<>();
        String selectQuery;
        Cursor cursor = null;

        // Consulta sin filtrar por cuenta
        selectQuery = "SELECT DISTINCT " + ContractTipoActividades.Columnas.TIPO +
                " FROM " + ContractTipoActividades.TIPO_ACTIVIDADES;

        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);  // Sin parámetros

        operadores.add("Seleccione");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String tipo = cursor.getString(
                        cursor.getColumnIndexOrThrow(ContractTipoActividades.Columnas.TIPO)
                );
                operadores.add(tipo);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return operadores;
    }

    //mpin tipo registro
    public List<String> getTipoRegistro() {
        List<String> operadores = new ArrayList<>();
        String selectQuery;
        Cursor cursor = null;

        // Consulta sin filtrar por cuenta
        selectQuery = "SELECT DISTINCT " + ContractTipoRegistro.Columnas.TIPO +
                " FROM " + ContractTipoRegistro.TIPO_REGISTRO;

        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);  // Sin parámetros

        operadores.add("Seleccione");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String tipo = cursor.getString(
                        cursor.getColumnIndexOrThrow(ContractTipoRegistro.Columnas.TIPO)
                );
                operadores.add(tipo);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return operadores;
    }

    //mpin implmentaciones
    public List<String> getTipoImplementacion() {
        List<String> operadores = new ArrayList<>();
        String selectQuery;
        Cursor cursor = null;

        // Consulta sin filtrar por cuenta
        selectQuery = "SELECT DISTINCT " + ContractTipoImplementaciones.Columnas.TIPO +
                " FROM " + ContractTipoImplementaciones.TIPO_IMPLEMENTACIONES;

        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);  // Sin parámetros

        operadores.add("Seleccione");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String tipo = cursor.getString(
                        cursor.getColumnIndexOrThrow(ContractTipoImplementaciones.Columnas.TIPO)
                );
                operadores.add(tipo);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return operadores;
    }

    //tipo unidades mpin
    //mpin implmentaciones
    public List<String> getTipoUnidades() {
        List<String> operadores = new ArrayList<>();
        String selectQuery;
        Cursor cursor = null;

        // Consulta sin filtrar por cuenta
        selectQuery = "SELECT DISTINCT " + ContractTipoUnidades.Columnas.TIPO +
                " FROM " + ContractTipoUnidades.TIPO_UNIDADES;

        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);  // Sin parámetros

        operadores.add("Seleccione");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String tipo = cursor.getString(
                        cursor.getColumnIndexOrThrow(ContractTipoUnidades.Columnas.TIPO)
                );
                operadores.add(tipo);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return operadores;
    }

    public ArrayList<Base_productos_novedades> getListGuardadoNovedades(String codigo) {
        ArrayList<Base_productos_novedades> operadores = new ArrayList<Base_productos_novedades>();


//        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
//        cal.set(Calendar.DAY_OF_MONTH,1);
//        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
//        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
//        // Obtenemos la fecha inicio del mes
//        String fecha_inicio_mes = formato.format(cal.getTime());
//        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
//        cal.set(Calendar.DATE,31);
//        formato.setTimeZone(TimeZone.getTimeZone("GMT-5"));
//        // Obtenemos la fecha final del mes
//        String  fecha_fin_mes = formato.format(cal.getTime());

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("MM/yyy");
        date.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String fechaser = date.format(currentLocalTime);

        Log.i("fechaser",""+fechaser);


        String selectQuery = "SELECT " + ContractInsertNovedades.Columnas.SKU_CODE +
                " FROM " + ContractInsertNovedades.INSERT_NOVEDADES +
                " WHERE " + ContractInsertNovedades.Columnas.CODIGO + " =? AND " +
                ContractInsertNovedades.Columnas.FECHA + " LIKE ? ";
        // Formateamos la fecha de dd/mm/yyyy a yyyy-mm-dd
        //  " AND datetime(substr(fecha, 7, 4) || '-' || substr(fecha, 4, 2) || '-' || substr(fecha, 1, 2))" +
        //  " BETWEEN ? AND ? ";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{codigo,"%" + fechaser + "%"});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Base_productos_novedades p = new Base_productos_novedades();
                p.setSku(cursor.getString(cursor.getColumnIndexOrThrow(ContractInsertNovedades.Columnas.SKU_CODE)));
                operadores.add(p);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return operadores;
    }

    public boolean validarCanales(String cadena){
        boolean id = false;
        String query = "SELECT * FROM " + ContractCanalesFotos.CANALES_FOTOS + " WHERE " +
                ContractCanalesFotos.Columnas.CANAL +" LIKE ? AND " +
                ContractCanalesFotos.Columnas.ACTIVAR +" = 'SI' " ;

        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, new String[]{'%'+cadena.trim()+'%'});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            return true;
        }

        cursor.close();
        db.close();
        return id;
    }





}