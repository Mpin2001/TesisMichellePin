package com.tesis.michelle.pin.Sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tesis.michelle.pin.Clase.BaseCombosCanjes;
import com.tesis.michelle.pin.Clase.BaseEvaluacionPromotor;
import com.tesis.michelle.pin.Clase.Base_Modulos_Roles;
import com.tesis.michelle.pin.Clase.Base_Promociones;
import com.tesis.michelle.pin.Clase.Base_Tipo_Actividades;
import com.tesis.michelle.pin.Clase.Base_Tipo_Implementaciones;
import com.tesis.michelle.pin.Clase.Base_Tipo_Registro;
import com.tesis.michelle.pin.Clase.Base_Tipo_Unidades;
import com.tesis.michelle.pin.Clase.Base_Tipo_Ventas;
import com.tesis.michelle.pin.Clase.Base_campos_x_modulos;
import com.tesis.michelle.pin.Clase.Base_canales_fotos;
import com.tesis.michelle.pin.Clase.Base_categoria_tipo;
import com.tesis.michelle.pin.Clase.Base_convenios;
import com.tesis.michelle.pin.Clase.Base_justificacion;
import com.tesis.michelle.pin.Clase.Base_link_oneDrive;
import com.tesis.michelle.pin.Clase.Base_motivos_sugerido;
import com.tesis.michelle.pin.Clase.Base_productos_secundarios;
import com.tesis.michelle.pin.Clase.Base_promocional_ventas;
import com.tesis.michelle.pin.Clase.Base_tipo_inventario;
import com.tesis.michelle.pin.Clase.Base_tipo_novedades;
import com.tesis.michelle.pin.Clase.Base_user;
import com.tesis.michelle.pin.Clase.ExhibicionSupervisor;
import com.tesis.michelle.pin.Clase.Tracking;
import com.tesis.michelle.pin.Conexion.Constantes;
import com.tesis.michelle.pin.Conexion.ContractTipoInventario;
import com.tesis.michelle.pin.Contracts.ContractCamposPorModulos;
import com.tesis.michelle.pin.Contracts.ContractCanalesFotos;
import com.tesis.michelle.pin.Contracts.ContractCategoriaTipo;
import com.tesis.michelle.pin.Contracts.ContractConvenios;
import com.tesis.michelle.pin.Contracts.ContractEvaluacionEncuesta;
import com.tesis.michelle.pin.Contracts.ContractExhibicionSupervisor;
import com.tesis.michelle.pin.Contracts.ContractInsertConvenios;
import com.tesis.michelle.pin.Contracts.ContractInsertEvaluacionEncuesta;
import com.tesis.michelle.pin.Contracts.ContractInsertMuestras;
import com.tesis.michelle.pin.Contracts.ContractInsertNovedades;
import com.tesis.michelle.pin.Contracts.ContractInsertPacks2;
import com.tesis.michelle.pin.Contracts.ContractInsertProbadores;
import com.tesis.michelle.pin.Contracts.ContractInsertTracking;
import com.tesis.michelle.pin.Contracts.ContractInsertVentas2;
import com.tesis.michelle.pin.Contracts.ContractJustificacion;
import com.tesis.michelle.pin.Contracts.ContractLinkOnedrive;
import com.tesis.michelle.pin.Contracts.ContractModulosRoles;
import com.tesis.michelle.pin.Contracts.ContractMotivoSugerido;
import com.tesis.michelle.pin.Contracts.ContractProductosSecundarios;
import com.tesis.michelle.pin.Contracts.ContractPromocionalVentas;
import com.tesis.michelle.pin.Contracts.ContractTipoActividades;
import com.tesis.michelle.pin.Contracts.ContractTipoImplementaciones;
import com.tesis.michelle.pin.Contracts.ContractTipoNovedades;
import com.tesis.michelle.pin.Contracts.ContractTipoRegistro;
import com.tesis.michelle.pin.Contracts.ContractTipoUnidades;
import com.tesis.michelle.pin.Contracts.ContractTipoVentas;
import com.tesis.michelle.pin.Contracts.ContractTracking;
import com.tesis.michelle.pin.Contracts.ContractUser;
import com.tesis.michelle.pin.Contracts.InsertExhBassa;
import com.tesis.michelle.pin.Contracts.InsertExhibicionesAdNu;
import com.tesis.michelle.pin.Contracts.InsertFlooring;
import com.tesis.michelle.pin.Clase.BaseAlertas;
import com.tesis.michelle.pin.Clase.BaseCausalesMCI;
import com.tesis.michelle.pin.Clase.BaseCausalesOSA;
import com.tesis.michelle.pin.Clase.BasePDI;
import com.tesis.michelle.pin.Clase.BasePharmaValue;
import com.tesis.michelle.pin.Clase.BasePopSugerido;
import com.tesis.michelle.pin.Clase.BasePortafolioProductos;
import com.tesis.michelle.pin.Clase.BasePromociones;
import com.tesis.michelle.pin.Clase.BasePortafolioPrioritario;
import com.tesis.michelle.pin.Clase.BaseRotacion;
import com.tesis.michelle.pin.Clase.BaseTareas;
import com.tesis.michelle.pin.Clase.Base_preguntas;
import com.tesis.michelle.pin.Clase.Base_tests;
import com.tesis.michelle.pin.Clase.Base_tipo_exh;
import com.tesis.michelle.pin.Contracts.ContractAlertas;
import com.tesis.michelle.pin.Contracts.ContractCausalesMCI;
import com.tesis.michelle.pin.Contracts.ContractCausalesOSA;
import com.tesis.michelle.pin.Contracts.ContractComboCanjes;
import com.tesis.michelle.pin.Contracts.ContractInsertCanjes;
import com.tesis.michelle.pin.Contracts.ContractInsertEjecucionMateriales;
import com.tesis.michelle.pin.Contracts.ContractInsertEvidencias;
import com.tesis.michelle.pin.Contracts.ContractInsertMCIPdv;
import com.tesis.michelle.pin.Contracts.ContractInsertCodificados;
import com.tesis.michelle.pin.Contracts.ContractInsertMaterialesRecibidos;
import com.tesis.michelle.pin.Contracts.ContractInsertPDI;
import com.tesis.michelle.pin.Contracts.ContractInsertResultadoPreguntas;
import com.tesis.michelle.pin.Contracts.ContractInsertSugeridos;
import com.tesis.michelle.pin.Contracts.ContractInsertTareas;
import com.tesis.michelle.pin.Contracts.ContractLog;
import com.tesis.michelle.pin.Contracts.ContractPDI;
import com.tesis.michelle.pin.Contracts.ContractPopSugerido;
import com.tesis.michelle.pin.Contracts.ContractPrioritario;
import com.tesis.michelle.pin.Contracts.ContractPromociones;
import com.tesis.michelle.pin.Contracts.ContractRotacion;
import com.tesis.michelle.pin.Contracts.ContractInsertAgotados;
import com.tesis.michelle.pin.Contracts.ContractInsertFotografico;
import com.tesis.michelle.pin.Contracts.ContractInsertImplementacion;
import com.tesis.michelle.pin.Contracts.ContractInsertImpulso;
import com.tesis.michelle.pin.Contracts.ContractInsertInicial;
import com.tesis.michelle.pin.Contracts.ContractInsertPdv;
import com.tesis.michelle.pin.Contracts.ContractInsertPreguntas;
import com.tesis.michelle.pin.Contracts.ContractInsertProdCaducar;
import com.tesis.michelle.pin.Contracts.ContractInsertRotacion;
import com.tesis.michelle.pin.Contracts.ContractInsertPromocion;
import com.tesis.michelle.pin.Contracts.ContractInsertShare;
import com.tesis.michelle.pin.Contracts.ContractInsertValores;
import com.tesis.michelle.pin.Contracts.ContractInsertVenta;
import com.tesis.michelle.pin.Contracts.ContractPharmaValue;
import com.tesis.michelle.pin.Contracts.ContractPortafolioProductos;
import com.tesis.michelle.pin.Contracts.ContractPreguntas;
import com.tesis.michelle.pin.Contracts.ContractTareas;
import com.tesis.michelle.pin.Contracts.ContractTests;
import com.tesis.michelle.pin.Contracts.ContractTipoExh;
import com.tesis.michelle.pin.Utilidades.UtilidadesAgotados;
import com.tesis.michelle.pin.Utilidades.UtilidadesCanjes;
import com.tesis.michelle.pin.Utilidades.UtilidadesConvenios;
import com.tesis.michelle.pin.Utilidades.UtilidadesEjecucionMateriales;
import com.tesis.michelle.pin.Utilidades.UtilidadesEvaluacionEncuesta;
import com.tesis.michelle.pin.Utilidades.UtilidadesEvidencias;
import com.tesis.michelle.pin.Utilidades.UtilidadesExhBassa;
import com.tesis.michelle.pin.Utilidades.UtilidadesExhibicionesAdNu;
import com.tesis.michelle.pin.Utilidades.UtilidadesFotografico;
import com.tesis.michelle.pin.Utilidades.UtilidadesImplementacion;
import com.tesis.michelle.pin.Utilidades.UtilidadesImpulso;
import com.tesis.michelle.pin.Utilidades.UtilidadesInicial;
import com.tesis.michelle.pin.Utilidades.UtilidadesLog;
import com.tesis.michelle.pin.Utilidades.UtilidadesMCI;
import com.tesis.michelle.pin.Utilidades.UtilidadesCodificados;
import com.tesis.michelle.pin.Utilidades.UtilidadesMaterialesRecibidos;
import com.tesis.michelle.pin.Utilidades.UtilidadesMuestras;
import com.tesis.michelle.pin.Utilidades.UtilidadesNovedades;
import com.tesis.michelle.pin.Utilidades.UtilidadesPDI;
import com.tesis.michelle.pin.Utilidades.UtilidadesPacks;
import com.tesis.michelle.pin.Utilidades.UtilidadesPdv;
import com.tesis.michelle.pin.Utilidades.UtilidadesPreguntas;
import com.tesis.michelle.pin.Utilidades.UtilidadesProbadores;
import com.tesis.michelle.pin.Utilidades.UtilidadesProdCad;
import com.tesis.michelle.pin.Utilidades.UtilidadesResultadoPreguntas;
import com.tesis.michelle.pin.Utilidades.UtilidadesRotacion;
import com.tesis.michelle.pin.Utilidades.UtilidadesPromocion;
import com.tesis.michelle.pin.Utilidades.UtilidadesShare;
import com.tesis.michelle.pin.Utilidades.UtilidadesSugeridos;
import com.tesis.michelle.pin.Utilidades.UtilidadesTareas;
import com.tesis.michelle.pin.Utilidades.UtilidadesTracking;
import com.tesis.michelle.pin.Utilidades.UtilidadesValores;
import com.google.gson.Gson;
import com.tesis.michelle.pin.Clase.Precios;
import com.tesis.michelle.pin.Conexion.Mensajes;
import com.tesis.michelle.pin.Conexion.VolleySingleton;
import com.tesis.michelle.pin.Contracts.ContractInsertExh;
import com.tesis.michelle.pin.Contracts.ContractInsertGps;
import com.tesis.michelle.pin.Contracts.ContractInsertPrecios;
import com.tesis.michelle.pin.Contracts.ContractInsertRastreo;
import com.tesis.michelle.pin.Contracts.ContractNotificacion;
import com.tesis.michelle.pin.Contracts.ContractPrecios;
import com.tesis.michelle.pin.DataBase.Projection;
import com.tesis.michelle.pin.R;
import com.tesis.michelle.pin.Utilidades.UtilidadesExh;
import com.tesis.michelle.pin.Utilidades.UtilidadesFlooring;
import com.tesis.michelle.pin.Utilidades.UtilidadesGps;
import com.tesis.michelle.pin.Utilidades.UtilidadesNotificacion;
import com.tesis.michelle.pin.Utilidades.UtilidadesPrecios;
import com.tesis.michelle.pin.Utilidades.UtilidadesRastreo;
import com.tesis.michelle.pin.Utilidades.UtilidadesVenta;
import com.tesis.michelle.pin.Utilidades.UtilidadesVentas2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lucky Ecuador on 15/07/2016.
 */
//Maneja la transferencia de datos entre el servidor y el cliente
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = SyncAdapter.class.getSimpleName();

    public static final int MY_DEFAULT_TIMEOUT = 60000;
    private static Context mContext;
    private static String valueActivity;
    ContentResolver resolver;
    private Gson gson = new Gson();
    private static String operator;

    private static String tipo_usuario;


    SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        resolver = context.getContentResolver();
    }

    /**
     * Constructor para mantener compatibilidad en versiones inferiores a 3.0
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        resolver = context.getContentResolver();
    }

    public static void inicializarSyncAdapter(Context context) {
        obtenerCuentaASincronizar(context);
    }

    @Override
    public void onPerformSync(Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              final SyncResult syncResult) {

        Log.i(TAG, "onPerformSync()...");

        try {

            boolean soloSubida = extras.getBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, false);

            if (!soloSubida) {//Bajando
                //Llena la Base/Datos Local con los datos del Servidor
//                switch (valueActivity) {
//                    case Constantes.bajar_justificaciones:  //JUSTIFICACIONES NO VISITA
//                        realizarSincronizacionLocalJustificacion(syncResult, operator);
//                        break;
//                    case Constantes.bajar_tipo_inventario:  //TIPO INVENTARIO
//                        realizarSincronizacionLocalTipoInventario(syncResult, operator);
//                        break;
//                    case Constantes.bajar_prod_secundarios:  //PRODUCTOS SECUNDARIOS ON PACKS
//                        realizarSincronizacionLocalProdSecundarios(syncResult, operator);
//                        break;
//                    case Constantes.bajar_link_onedrive:  //LINK DE ONEDRIVE
//                        realizarSincronizacionLocalLinkOnedrive(syncResult, operator);
//                        break;
//                    case Constantes.bajar_Oper: //PDV
//                        realizarSincronizacionLocalPDV(syncResult, operator);
//                        break;
//                    case Constantes.bajar_floo: //PRODUCTOS
//                        realizarSincronizacionLocalFlooring2(syncResult, operator);
//                        break;
//                    case Constantes.bajar_tipo_exhibicion: //PDI
//                        realizarSincronizacionLocalTipoExh(syncResult, operator);
//                        break;
//                    case Constantes.bajar_promociones: //PROMOCIONES
//                        realizarSincronizacionLocalPromociones(syncResult, operator);
//                        break;
//                    case Constantes.bajar_tracking: //TRACKING
//                        realizarSincronizacionLocalTracking(syncResult,operator);
//                        break;
//                    case Constantes.bajar_convenios:  //CONVENIOS
//                        realizarSincronizacionLocalConvenios(syncResult, operator);
//                        break;
///*-------------------------------AQUI EMPIEZA-----------------------------*/
                if(valueActivity.equals(Constantes.bajar_operadores_mercaderismo)){//PDV
                    realizarSincronizacionLocalJustificacion(syncResult, operator);
                    realizarSincronizacionLocalTipoInventario(syncResult, operator);
                    realizarSincronizacionLocalProdSecundarios(syncResult, operator);
                    realizarSincronizacionLocalLinkOnedrive(syncResult, operator);
                    realizarSincronizacionLocalUser(syncResult,operator);
                    realizarSincronizacionLocalPDV(syncResult, operator);
                    realizarSincronizacionLocalFlooring2(syncResult, operator);
                    realizarSincronizacionLocalTipoExh(syncResult, operator);
                    realizarSincronizacionLocalCategoriaTipo(syncResult, operator);
                    realizarSincronizacionLocaLMotivosSugerido(syncResult, operator);
                    realizarSincronizacionLocalPromociones(syncResult, operator);
                    realizarSincronizacionLocalTipoVentas(syncResult, operator); // mpin
                    realizarSincronizacionLocalTipoImplementaciones(syncResult, operator); // mpin
                    realizarSincronizacionLocalTipoUnidades(syncResult, operator); // mpin
                    realizarSincronizacionLocalModulosRoles(syncResult, operator); // mpin
                    realizarSincronizacionLocalTracking(syncResult,operator);
                    realizarSincronizacionLocalTipoActividades(syncResult,operator);
                    realizarSincronizacionLocalTipoRegistro(syncResult,operator); //mpin
                    realizarSincronizacionLocalConvenios(syncResult, operator);
                    realizarSincronizacionLocalExhSupervisores(syncResult, operator);
                    realizarSincronizacionLocalEvaluacionPromotor(syncResult, operator);
                    realizarSincronizacionLocalTipoNovedades(syncResult,operator);
                    realizarSincronizacionLocalCanalFotos(syncResult,operator);
                    realizarSincronizacionLocalCamposPorModulos(syncResult,operator);

                } else if (valueActivity.equals(Constantes.bajar_justificaciones)) {
                    realizarSincronizacionLocalJustificacion(syncResult, operator);
                } else if (valueActivity.equals(Constantes.bajar_tipo_inventario)) {
                    realizarSincronizacionLocalTipoInventario(syncResult, operator);
                } else if (valueActivity.equals(Constantes.bajar_prod_secundarios)) {
                    realizarSincronizacionLocalProdSecundarios(syncResult, operator);
                } else if (valueActivity.equals(Constantes.bajar_link_onedrive)) {
                    realizarSincronizacionLocalLinkOnedrive(syncResult, operator);
                } else if (valueActivity.equals(Constantes.bajar_Oper)) {
                    realizarSincronizacionLocalPDV(syncResult, operator);
                } else if (valueActivity.equals(Constantes.bajar_floo)) {
                    realizarSincronizacionLocalFlooring2(syncResult, operator);
                } else if (valueActivity.equals(Constantes.bajar_tipo_exhibicion)) {
                    realizarSincronizacionLocalTipoExh(syncResult, operator);
                } else if (valueActivity.equals(Constantes.bajar_tipo_categoria)) {
                    realizarSincronizacionLocalCategoriaTipo(syncResult, operator);
                } else if (valueActivity.equals(Constantes.bajar_promociones)) {
                    realizarSincronizacionLocalPromociones(syncResult, operator);
                } else if (valueActivity.equals(Constantes.bajar_tipo_ventas)) {
                    realizarSincronizacionLocalTipoVentas(syncResult, operator);
                }  else if (valueActivity.equals(Constantes.bajar_tipo_implementaciones)) {
                    realizarSincronizacionLocalTipoImplementaciones(syncResult, operator);
                } else if (valueActivity.equals(Constantes.bajar_tipo_unidades)) {
                    realizarSincronizacionLocalTipoUnidades(syncResult, operator);
                } else if (valueActivity.equals(Constantes.bajar_tipo_registro)) {
                    realizarSincronizacionLocalTipoRegistro(syncResult, operator);
                } else if (valueActivity.equals(Constantes.bajar_modulo_roles)) {
                    realizarSincronizacionLocalModulosRoles(syncResult, operator);
                } else if (valueActivity.equals(Constantes.bajar_tracking)) {
                    realizarSincronizacionLocalTracking(syncResult, operator);
                } else if (valueActivity.equals(Constantes.bajar_convenios)) {
                    realizarSincronizacionLocalConvenios(syncResult, operator);
                }else if(valueActivity.equals(Constantes.bajar_tipo_novedades)){
                    realizarSincronizacionLocalTipoNovedades(syncResult,operator);
                }else if(valueActivity.equals(Constantes.bajar_canales_fotos)){
                    realizarSincronizacionLocalCanalFotos(syncResult,operator);
                } else if (valueActivity.equals(Constantes.bajar_user)) {
                    realizarSincronizacionLocalUser(syncResult, operator);
                } else if (valueActivity.equals(Constantes.bajar_motivos_sugerido)) {
                    realizarSincronizacionLocaLMotivosSugerido(syncResult, operator);
                } else if(valueActivity.equals(Constantes.bajar_campos_x_modulos)){
                    realizarSincronizacionLocalCamposPorModulos(syncResult,operator);
                } else if(valueActivity.equals(Constantes.bajar_promocional_ventas)){
                    realizarSincronizacionLocalPromocionalVentas(syncResult,operator);
                }

///*-------------------------------AQUI TERMINA-----------------------------*/
////                    case Constantes.bajar_preg: //PREGUNTAS
////                        realizarSincronizacionLocalPreguntas(syncResult, operator);
////                        break;
////                    case Constantes.bajar_tests: //TESTS
////                        realizarSincronizacionLocalTests(syncResult, operator);
////                        break;
//
////                    case Constantes.bajar_rotacion: //ROTACION
////                        realizarSincronizacionLocalRotacion(syncResult, operator);
////                        break;
////                    case Constantes.bajar_tareas: //TAREAS
////                        realizarSincronizacionLocalTareas(syncResult, operator);
////                        break;
////                    case Constantes.bajar_pop_sugerido: //POP
////                        realizarSincronizacionLocalPopSugerido(syncResult, operator);
////                        break;
////                    case Constantes.bajar_prioritario: //PRIORITARIO
////                        realizarSincronizacionLocalPrioritario(syncResult, operator);
////                        break;
////                    case Constantes.bajar_combo_canjes: //COMBO CANJES
////                        realizarSincronizacionLocalComboCanjes(syncResult, operator);
////                        break;
////                    case Constantes.bajar_causales_mci: //CAUSALES MCI
////                        realizarSincronizacionLocalCausalesMCI(syncResult, operator);
////                        break;
////                    case Constantes.bajar_causales_osa: //CAUSALES OSA
////                        realizarSincronizacionLocalCausalesOSA(syncResult, operator);
////                        break;
////                    case Constantes.bajar_materiales_alertas: //MATERIALES ALERTAS
////                        realizarSincronizacionLocalMaterialesAlertas(syncResult, operator);
////                        break;
////                    case Constantes.bajar_pdi: //PDI
////                        realizarSincronizacionLocalPDI(syncResult, operator);
////                        break;
//                }
            } else {
                Log.i("Sync", "valueActivity");
                switch (valueActivity) {
                    case Constantes.insertPrecio:
                        realizarSincronizacionRemotaInsertPrecio();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertMuestras:
                        realizarSincronizacionRemotaInsertMuestras(); //nuevo insert mpin modulo muestras medicas
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;

                    case Constantes.insertProbadores:
                        realizarSincronizacionRemotaInsertProbadores(); //nuevo insert mpin modulo probadores
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertCanjes:
                        realizarSincronizacionRemotaInsertCanjes();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertMaterialesRecibidos:
                        realizarSincronizacionRemotaInsertMaterialesRecibidos();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertEjecucionMateriales:
                        realizarSincronizacionRemotaInsertEjecucionMateriales();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertMci:
                        realizarSincronizacionRemotaInsertMCI();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertExh:
                        realizarSincronizacionRemotaInsertExh();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertGps:
                        realizarSincronizacionRemotaInsertGps();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertGeo:
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertFlooring:
                        realizarSincronizacionRemotaInsertFlooring();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertEvaluacionDemo:
                        Log.i("sdvid 2","entrooooo"  );
                        realizarSincronizacionRemotaInsertEvaluacion();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertNovedades:
                        realizarSincronizacionRemotaInsertNovedades();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertPromocion:
                        realizarSincronizacionRemotaInsertPromo();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertImplementacion:
                        realizarSincronizacionRemotaInsertImplementacion();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertvalores:
                        realizarSincronizacionRemotaInsertValores();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertpdv:
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertShare:
                        realizarSincronizacionRemotaInsertShare();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertarPdI:
                        realizarSincronizacionRemotaInsertPDI();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertAgotados:
                        realizarSincronizacionRemotaInsertAgotados();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertVenta:
                        realizarSincronizacionRemotaInsertVenta();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertVentas2:
                        realizarSincronizacionRemotaInsertVentas2();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertFoto:
                        realizarSincronizacionRemotaInsertFotografico();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertPreguntas:
                        realizarSincronizacionRemotaInsertPreguntas();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertResultadoPreguntas:
                        realizarSincronizacionRemotaInsertResultadoPreguntas();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertProdCad:
                        realizarSincronizacionRemotaInsertProdCad();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertSugeridos:
                        realizarSincronizacionRemotaInsertSugeridos();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertExhBassa:
                        realizarSincronizacionRemotaInsertExhBassa();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertExhAdNu:
                        realizarSincronizacionRemotaInsertExhAdNu();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.bajar_Exh_Supervisor:
                        realizarSincronizacionLocalExhSupervisores(syncResult, operator);
//                realizarSincronizacionLocalTipoNovedad(syncResult);
//                realizarSincronizacionLocalExh(syncResult, operator);
                        break;
                    case Constantes.insertRotacion:
                        realizarSincronizacionRemotaInsertRotacion();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertPacks:
                        realizarSincronizacionRemotaInsertPacks();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertTareas:
                        realizarSincronizacionRemotaInsertTareas();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertImpulso:
                        realizarSincronizacionRemotaInsertImpulso();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertCodificados:
                        realizarSincronizacionRemotaInsertCodificados();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertEvidencias:
                        realizarSincronizacionRemotaInsertEvidencias();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertTracking:
                        realizarSincronizacionRemotaInsertTracking();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.insertConvenios:
                        realizarSincronizacionRemotaInsertConvenios();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertLog();
                        break;
                    case Constantes.SUBIR_TODO:
                        realizarSincronizacionRemotaInsertPrecio();
                        realizarSincronizacionRemotaInsertMuestras(); //mpin 2do paso
                        realizarSincronizacionRemotaInsertProbadores(); //mpin 2do paso
                        realizarSincronizacionRemotaInsertFlooring();
                        realizarSincronizacionRemotaInsertExh();
                        realizarSincronizacionRemotaInsertGps();
                        realizarSincronizacionRemotaInsertPromo();
                        realizarSincronizacionRemotaInsertImplementacion();
                        realizarSincronizacionRemotaInsertValores();
                        realizarSincronizacionRemotaInsertInicial();
                        realizarSincronizacionRemotaInsertNot();
                        realizarSincronizacionRemotaInsertAgotados();
                        realizarSincronizacionRemotaInsertVenta();
                        realizarSincronizacionRemotaInsertShare();
                        realizarSincronizacionRemotaInsertGeo();
                        realizarSincronizacionRemotaInsertFotografico();
                        realizarSincronizacionRemotaInsertPreguntas();
                        realizarSincronizacionRemotaInsertProdCad();
                        realizarSincronizacionRemotaInsertPacks();
                        realizarSincronizacionRemotaInsertTareas();
                        realizarSincronizacionRemotaInsertImpulso();
                        realizarSincronizacionRemotaInsertSugeridos();
                        realizarSincronizacionRemotaInsertCanjes();
                        realizarSincronizacionRemotaInsertMCI();
                        realizarSincronizacionRemotaInsertMaterialesRecibidos();
                        realizarSincronizacionRemotaInsertPDI();
                        realizarSincronizacionRemotaInsertCodificados();
                        realizarSincronizacionRemotaInsertLog();
                        realizarSincronizacionRemotaInsertEvidencias();
                        realizarSincronizacionRemotaInsertExhAdNu();
                        realizarSincronizacionRemotaInsertExhBassa();
                        realizarSincronizacionRemotaInsertNovedades();
                        realizarSincronizacionRemotaInsertEvaluacion();
                        break;
                    default:
                        try {
                            final Activity activity = (Activity) mContext;
                            activity.runOnUiThread(new Runnable() {
                                public void run() {
                                    Log.i("SYNC", Mensajes.MOD_ERROR);
    //                                Toast.makeText(activity, Mensajes.MOD_ERROR, Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            Log.i("SYNC", e.getMessage());
                        }
                        break;
                }
            }
        } catch (Exception e) {
            Log.i("SYNC", e.getMessage());
        }
    }

    /**
     * Inicia manualmente la sincronización
     *
     * @param context    Contexto para crear la petición de sincronización
     * @param onlyUpload Usa true para sincronizar el servidor o false para sincronizar el cliente
     */
    public static void sincronizarAhora(Context context, boolean onlyUpload, String valueActivity1, String userName) {
        mContext = context;
        valueActivity = valueActivity1;
        operator = userName;
        //Toast.makeText(context,"Realizando petición de sincronización remota manual.",Toast.LENGTH_LONG).show();
        Log.i(TAG, "Realizando petición de sincronización remota manual.");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        if (onlyUpload)
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, true);
        ContentResolver.requestSync(obtenerCuentaASincronizar(context),
                context.getString(R.string.provider_authority), bundle);
    }

    public static void NewsincronizarAhora(Context context, boolean onlyUpload, String valueActivity1,
                                        String userName, String tipoUsuario, String userDate, String userTime1, String userTime2) {
        mContext = context;
        valueActivity = valueActivity1;
        operator = userName;
        tipo_usuario = tipoUsuario;
        //Toast.makeText(context,"Realizando petición de sincronización remota manual.",Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Realizando petición de sincronización remota manual.");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        if (onlyUpload)
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, true);
        ContentResolver.requestSync(obtenerCuentaASincronizar(context),
                context.getString(R.string.provider_authority), bundle);
    }

    /**
     * Crea u obtiene una cuenta existente
     *
     * @param context Contexto para acceder al administrador de cuentas
     * @return cuenta auxiliar.
     */
    private static Account obtenerCuentaASincronizar(Context context) {
        // Obtener instancia del administrador de cuentas
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Crear cuenta por defecto
        Account newAccount = new Account(
                context.getString(R.string.app_name), Constantes.ACCOUNT_TYPE);

        // Comprobar existencia de la cuenta
        if (null == accountManager.getPassword(newAccount)) {

            // Añadir la cuenta al account manager sin password y sin datos de user
            if (!accountManager.addAccountExplicitly(newAccount, "", null))
                return null;

        }
        Log.i(TAG, "SyncAdapter: Cuenta de user obtenida.");
        return newAccount;
    }


    /*
     *  SINCRONIZAR TABLA LOCAL
     */

    private void realizarSincronizacionLocalPDV(final SyncResult syncResult, String mercaderista) {
        Log.i(TAG, "Realizando Sincronizacion Local de BasePharmaValue." + mercaderista);
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operator", mercaderista);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_PUNTOVENTAS, jobject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetPDV(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetPDV(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesPos(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesPos(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.PDV_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        BasePharmaValue[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, BasePharmaValue[].class);
        List<BasePharmaValue> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, BasePharmaValue> expenseMap = new HashMap<>();
        for (BasePharmaValue e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractPharmaValue.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_PUNTOS, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos

        String id;
        String channel;
        String subchannel;
        String channel_segment;
        String format;
        String customer_owner;
        String pos_id;
        String pos_name;
        String pos_name_dpsm;
        String zone;
        String region;
        String province;
        String city;
        String address;
        String kam;
        String sales_executive;
        String merchandising;
        String supervisor;
        String rol;
        String mercaderista;
        String user;
        String dpsm;
        String status;
        String tipo;
        String latitud;
        String longitud;
        String foto;
        String segmentacion;
        String compras;
        String pass;
        String numero_controller;
        String fecha_visita;
        String device_id;
        String perimetro;
        String distancia;
        String termometro;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            channel = c.getString(Projection.CHANNEL);
            subchannel = c.getString(Projection.SUBCHANNEL);
            channel_segment = c.getString(Projection.CHANNEL_SEGMENT);
            format = c.getString(Projection.FORMAT);
            customer_owner = c.getString(Projection.CUSTOMER_OWNER);
            pos_id = c.getString(Projection.POS_ID);
            pos_name = c.getString(Projection.POS_NAME);
            pos_name_dpsm = c.getString(Projection.POS_NAME_DPSM);
            zone = c.getString(Projection.ZONA);
            region = c.getString(Projection.REGION);
            province = c.getString(Projection.SECTOR);
            city = c.getString(Projection.CIUDAD);
            address = c.getString(Projection.DIRECCION);
            kam = c.getString(Projection.KAM);
            sales_executive = c.getString(Projection.SALES_EXECUTIVE);
            merchandising = c.getString(Projection.MERCHANDISING);
            supervisor = c.getString(Projection.SUPERVISOR);
            rol = c.getString(Projection.ROL);
            mercaderista = c.getString(Projection.MERCADERISTA);
            user = c.getString(Projection.USUARIO);
            dpsm = c.getString(Projection.DPSM);
            status = c.getString(Projection.STATUS);
            tipo = c.getString(Projection.TIPO_PDV);
            latitud = c.getString(Projection.LATITUD);
            longitud = c.getString(Projection.LONGITUD);
            foto = c.getString(Projection.FOTO);
            segmentacion = c.getString(Projection.SEGMENTACION);
            compras = c.getString(Projection.COMPRAS);
            pass = c.getString(Projection.PASS);
            numero_controller = c.getString(Projection.NUMERO_CONTROLLER);
            fecha_visita = c.getString(Projection.FECHA_VISITA);
            device_id = c.getString(Projection.DEVICE_ID);
            perimetro = c.getString(Projection.PERIMETRO);
            distancia = c.getString(Projection.DISTANCIA);
            termometro = c.getString(Projection.TERMOMETRO);

            BasePharmaValue match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractPharmaValue.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                boolean b1 = match.channel != null && !match.channel.equals(channel);
                boolean b2 = match.subchannel != null && !match.subchannel.equals(subchannel);
                boolean b3 = match.channel_segment != null && !match.channel_segment.equals(channel_segment);
                boolean b4 = match.format != null && !match.format.equals(format);
                boolean b5 = match.customer_owner != null && !match.customer_owner.equals(customer_owner);
                boolean b6 = match.pos_id != null && !match.pos_id.equals(pos_id);
                boolean b7 = match.pos_name != null && !match.pos_name.equals(pos_name);
                boolean b8 = match.pos_name_dpsm != null && !match.pos_name_dpsm.equals(pos_name_dpsm);
                boolean b9 = match.zone != null && !match.zone.equals(zone);
                boolean b10 = match.region != null && !match.region.equals(region);
                boolean b11 = match.province != null && !match.province.equals(province);
                boolean b12 = match.city != null && !match.city.equals(city);
                boolean b13 = match.address != null && !match.address.equals(address);
                boolean b14 = match.kam != null && !match.kam.equals(kam);
                boolean b15 = match.sales_executive != null && !match.sales_executive.equals(sales_executive);
                boolean b16 = match.merchandising != null && !match.merchandising.equals(merchandising);
                boolean b17 = match.supervisor != null && !match.supervisor.equals(supervisor);
                boolean b18 = match.rol != null && !match.rol.equals(rol);
                boolean b19= match.mercaderista != null && !match.supervisor.equals(mercaderista);
                boolean b20 = match.user != null && !match.user.equals(user);
                boolean b21 = match.dpsm != null && !match.dpsm.equals(dpsm);
                boolean b22 = match.status != null && !match.status.equals(status);
                boolean b23 = match.tipo != null && !match.tipo.equals(tipo);
                boolean b24 = match.latitud != null && !match.latitud.equals(latitud);
                boolean b25 = match.longitud != null && !match.longitud.equals(longitud);
                boolean b26 = match.foto != null && !match.foto.equals(foto);
                boolean b27 = match.segmentacion != null && !match.segmentacion.equals(segmentacion);
                boolean b28 = match.compras != null && !match.compras.equals(compras);
                boolean b29 = match.pass != null && !match.pass.equals(pass);
                boolean b30 = match.numero_controller != null && !match.numero_controller.equals(numero_controller);
                boolean b31= match.fecha_visita != null && !match.fecha_visita.equals(fecha_visita);
                boolean b32 = match.device_id != null && !match.device_id.equals(device_id);
                boolean b33 = match.perimetro != null && !match.perimetro.equals(perimetro);
                boolean b34 = match.distancia != null && !match.distancia.equals(distancia);
                boolean b35 = match.termometro != null && !match.termometro.equals(termometro);

                if (b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10 || b11 || b12 || b13 ||
                    b14 || b15 || b16 || b17 || b18 || b19 || b20 || b21 || b22 || b23 || b24 || b25 ||
                    b26 || b27 || b28 || b29 || b30 || b31 || b32 || b33 || b34 || b35) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContractPharmaValue.Columnas.CHANNEL, match.channel)
                            .withValue(ContractPharmaValue.Columnas.SUBCHANNEL, match.subchannel)
                            .withValue(ContractPharmaValue.Columnas.CHANNEL_SEGMENT, match.channel_segment)
                            .withValue(ContractPharmaValue.Columnas.FORMAT, match.format)
                            .withValue(ContractPharmaValue.Columnas.CUSTOMER_OWNER, match.customer_owner)
                            .withValue(ContractPharmaValue.Columnas.POS_ID, match.pos_id)
                            .withValue(ContractPharmaValue.Columnas.POS_NAME, match.pos_name)
                            .withValue(ContractPharmaValue.Columnas.POS_NAME_DPSM, match.pos_name_dpsm)
                            .withValue(ContractPharmaValue.Columnas.ZONA, match.zone)
                            .withValue(ContractPharmaValue.Columnas.REGION, match.region)
                            .withValue(ContractPharmaValue.Columnas.PROVINCIA, match.province)
                            .withValue(ContractPharmaValue.Columnas.CIUDAD, match.city)
                            .withValue(ContractPharmaValue.Columnas.DIRECCION, match.address)
                            .withValue(ContractPharmaValue.Columnas.KAM, match.kam)
                            .withValue(ContractPharmaValue.Columnas.SALES_EXECUTIVE, match.sales_executive)
                            .withValue(ContractPharmaValue.Columnas.MERCHANDISING, match.merchandising)
                            .withValue(ContractPharmaValue.Columnas.SUPERVISOR, match.supervisor)
                            .withValue(ContractPharmaValue.Columnas.ROL, match.rol)
                            .withValue(ContractPharmaValue.Columnas.MERCADERISTA, match.mercaderista)
                            .withValue(ContractPharmaValue.Columnas.USER, match.user)
                            .withValue(ContractPharmaValue.Columnas.DPSM, match.dpsm)
                            .withValue(ContractPharmaValue.Columnas.STATUS, match.status)
                            .withValue(ContractPharmaValue.Columnas.TIPO, match.tipo)
                            .withValue(ContractPharmaValue.Columnas.LATITUD, match.latitud)
                            .withValue(ContractPharmaValue.Columnas.LONGITUD, match.longitud)
                            .withValue(ContractPharmaValue.Columnas.FOTO, match.foto)
                            .withValue(ContractPharmaValue.Columnas.SEGMENTACION, match.segmentacion)
                            .withValue(ContractPharmaValue.Columnas.COMPRAS, match.compras)
                            .withValue(ContractPharmaValue.Columnas.PASS, match.pass)
                            .withValue(ContractPharmaValue.Columnas.NUMERO_CONTROLLER, match.numero_controller)
                            .withValue(ContractPharmaValue.Columnas.FECHA_VISITA, match.fecha_visita)
                            .withValue(ContractPharmaValue.Columnas.DEVICE_ID, match.device_id)
                            .withValue(ContractPharmaValue.Columnas.PERIMETRO, match.perimetro)
                            .withValue(ContractPharmaValue.Columnas.DISTANCIA, match.distancia)
                            .withValue(ContractPharmaValue.Columnas.TERMOMETRO, match.termometro)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractPharmaValue.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (BasePharmaValue e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla BasePharmaValue en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractPharmaValue.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA, e.id) //Esta clave importante!!! ForeignKey del servidor
                    .withValue(ContractPharmaValue.Columnas.CHANNEL, e.channel)
                    .withValue(ContractPharmaValue.Columnas.SUBCHANNEL, e.subchannel)
                    .withValue(ContractPharmaValue.Columnas.CHANNEL_SEGMENT, e.channel_segment)
                    .withValue(ContractPharmaValue.Columnas.FORMAT, e.format)
                    .withValue(ContractPharmaValue.Columnas.CUSTOMER_OWNER, e.customer_owner)
                    .withValue(ContractPharmaValue.Columnas.POS_ID, e.pos_id)
                    .withValue(ContractPharmaValue.Columnas.POS_NAME, e.pos_name)
                    .withValue(ContractPharmaValue.Columnas.POS_NAME_DPSM, e.pos_name_dpsm)
                    .withValue(ContractPharmaValue.Columnas.ZONA, e.zone)
                    .withValue(ContractPharmaValue.Columnas.REGION, e.region)
                    .withValue(ContractPharmaValue.Columnas.PROVINCIA, e.province)
                    .withValue(ContractPharmaValue.Columnas.CIUDAD, e.city)
                    .withValue(ContractPharmaValue.Columnas.DIRECCION, e.address)
                    .withValue(ContractPharmaValue.Columnas.KAM, e.kam)
                    .withValue(ContractPharmaValue.Columnas.SALES_EXECUTIVE, e.sales_executive)
                    .withValue(ContractPharmaValue.Columnas.MERCHANDISING, e.merchandising)
                    .withValue(ContractPharmaValue.Columnas.SUPERVISOR, e.supervisor)
                    .withValue(ContractPharmaValue.Columnas.ROL, e.rol)
                    .withValue(ContractPharmaValue.Columnas.MERCADERISTA, e.mercaderista)
                    .withValue(ContractPharmaValue.Columnas.USER, e.user)
                    .withValue(ContractPharmaValue.Columnas.DPSM, e.dpsm)
                    .withValue(ContractPharmaValue.Columnas.STATUS, e.status)
                    .withValue(ContractPharmaValue.Columnas.TIPO, e.tipo)
                    .withValue(ContractPharmaValue.Columnas.LATITUD, e.latitud)
                    .withValue(ContractPharmaValue.Columnas.LONGITUD, e.longitud)
                    .withValue(ContractPharmaValue.Columnas.FOTO, e.foto)
                    .withValue(ContractPharmaValue.Columnas.SEGMENTACION, e.segmentacion)
                    .withValue(ContractPharmaValue.Columnas.COMPRAS, e.compras)
                    .withValue(ContractPharmaValue.Columnas.PASS, e.pass)
                    .withValue(ContractPharmaValue.Columnas.NUMERO_CONTROLLER, e.numero_controller)
                    .withValue(ContractPharmaValue.Columnas.FECHA_VISITA, e.fecha_visita)
                    .withValue(ContractPharmaValue.Columnas.DEVICE_ID, e.device_id)
                    .withValue(ContractPharmaValue.Columnas.PERIMETRO, e.perimetro)
                    .withValue(ContractPharmaValue.Columnas.DISTANCIA, e.distancia)
                    .withValue(ContractPharmaValue.Columnas.TERMOMETRO, e.termometro)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractPharmaValue.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de BasePharmaValue finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla BasePharmaValue finalizada.");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_PDV);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla BasePharmaValue");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "BasePharmaValue");
        }
    }

    /**
     * PRODUCTOS
     *
     * @param syncResult
     * @param operatorName
     */
    public void realizarSincronizacionLocalFlooring2(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de Flooring." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_FLOORING, jobject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetFlooring2(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetFlooring2(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesFlooring2(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesFlooring2(JSONObject response, SyncResult syncResult) {

        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.FLOORING_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        BasePortafolioProductos[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, BasePortafolioProductos[].class);
        List<BasePortafolioProductos> data = Arrays.asList(res);
        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, BasePortafolioProductos> expenseMap = new HashMap<>();
        for (BasePortafolioProductos e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractPortafolioProductos.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_FLOORING, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String sector;
        String categoria;
        String subcategoria;
        String segmento;
        String presentacion;
        String variante1;
        String variante2;
        String contenido;
        String sku;
        String marca;
        String fabricante;
        String pvp;
        String cadenas;
        String foto;
        String plataforma;
        /*String manufacturer;
        String format;*/

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;


            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            sector = c.getString(Projection.SECTOR);
            categoria = c.getString(Projection.CATEGORY);
            subcategoria = c.getString(Projection.SUBCATEGORIA);
            segmento = c.getString(Projection.SEGMENTO);
            presentacion = c.getString(Projection.PRESENTACION);
            variante1 = c.getString(Projection.VARIANTE1);
            variante2 = c.getString(Projection.VARIANTE2);
            contenido = c.getString(Projection.CONTENIDO);
            sku = c.getString(Projection.SKU);
            marca = c.getString(Projection.MARCA);
            fabricante = c.getString(Projection.FABRICANTE);
            pvp = c.getString(Projection.PVP);
            cadenas = c.getString(Projection.CADENAS);
            foto = c.getString(Projection.FOTO);
            plataforma = c.getString(Projection.PLATAFORMA);
            /*manufacturer = c.getString(Projection.MANUFACTURER);
            format = c.getString(Projection.FORMAT_PRODUCTO);*/

            BasePortafolioProductos match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractPortafolioProductos.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1 = match.sector != null && !match.sector.equals(sector);
                boolean b2 = match.categoria != null && !match.categoria.equals(categoria);
                boolean b3 = match.subcategoria != null && !match.subcategoria.equals(subcategoria);
                boolean b4 = match.segmento != null && !match.segmento.equals(segmento);
                boolean b5 = match.presentacion != null && !match.presentacion.equals(presentacion);
                boolean b6 = match.variante1 != null && !match.variante1.equals(variante1);
                boolean b7 = match.variante2 != null && !match.variante2.equals(variante2);
                boolean b9 = match.contenido != null && !match.contenido.equals(contenido);
                boolean b8 = match.sku != null && !match.sku.equals(sku);
                boolean b10 = match.marca != null && !match.marca.equals(marca);
                boolean b11 = match.fabricante != null && !match.fabricante.equals(fabricante);
                boolean b12 = match.pvp != null && !match.pvp.equals(pvp);
                boolean b13 = match.cadenas != null && !match.cadenas.equals(cadenas);
                boolean b14 = match.foto != null && !match.foto.equals(foto);
                boolean b15 = match.plataforma != null && !match.plataforma.equals(plataforma);
                /*boolean b12 = match.manufacturer != null && !match.manufacturer.equals(manufacturer);
                boolean b13 = match.format != null && !match.format.equals(format);*/

                if (b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10 || b11 || b12 || b13 || b14 || b15) {// || b12 || b13) {

                    Log.i(TAG, "Programando actualización de p: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractPortafolioProductos.Columnas.SECTOR, match.sector)
                            .withValue(ContractPortafolioProductos.Columnas.CATEGORY, match.categoria)
                            .withValue(ContractPortafolioProductos.Columnas.SUBCATEGORIA, match.subcategoria)
                            .withValue(ContractPortafolioProductos.Columnas.SEGMENTO, match.segmento)
                            .withValue(ContractPortafolioProductos.Columnas.PRESENTACION, match.presentacion)
                            .withValue(ContractPortafolioProductos.Columnas.VARIANTE1, match.variante1)
                            .withValue(ContractPortafolioProductos.Columnas.VARIANTE2, match.variante2)
                            .withValue(ContractPortafolioProductos.Columnas.CONTENIDO, match.contenido)
                            .withValue(ContractPortafolioProductos.Columnas.SKU, match.sku)
                            .withValue(ContractPortafolioProductos.Columnas.MARCA, match.marca)
                            .withValue(ContractPortafolioProductos.Columnas.FABRICANTE, match.fabricante)
                            .withValue(ContractPortafolioProductos.Columnas.PVP, match.pvp)
                            .withValue(ContractPortafolioProductos.Columnas.CADENAS, match.cadenas)
                            .withValue(ContractPortafolioProductos.Columnas.FOTO, match.foto)
                            .withValue(ContractPortafolioProductos.Columnas.PLATAFORMA, match.plataforma)
                            /*.withValue(ContractPortafolioProductos.Columnas.MANUFACTURER, match.manufacturer)
                            .withValue(ContractPortafolioProductos.Columnas.FORMAT,match.format)*/
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro p: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractPortafolioProductos.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (BasePortafolioProductos e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_Portafolio_Productos en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractPortafolioProductos.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA, e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractPortafolioProductos.Columnas.SECTOR, e.sector)
                    .withValue(ContractPortafolioProductos.Columnas.CATEGORY, e.categoria)
                    .withValue(ContractPortafolioProductos.Columnas.SUBCATEGORIA, e.subcategoria)
                    .withValue(ContractPortafolioProductos.Columnas.SEGMENTO, e.segmento)
                    .withValue(ContractPortafolioProductos.Columnas.PRESENTACION, e.presentacion)
                    .withValue(ContractPortafolioProductos.Columnas.VARIANTE1, e.variante1)
                    .withValue(ContractPortafolioProductos.Columnas.VARIANTE2, e.variante2)
                    .withValue(ContractPortafolioProductos.Columnas.CONTENIDO, e.contenido)
                    .withValue(ContractPortafolioProductos.Columnas.SKU, e.sku)
                    .withValue(ContractPortafolioProductos.Columnas.MARCA, e.marca)
                    .withValue(ContractPortafolioProductos.Columnas.FABRICANTE, e.fabricante)
                    .withValue(ContractPortafolioProductos.Columnas.PVP, e.pvp)
                    .withValue(ContractPortafolioProductos.Columnas.CADENAS, e.cadenas)
                    .withValue(ContractPortafolioProductos.Columnas.FOTO, e.foto)
                    .withValue(ContractPortafolioProductos.Columnas.PLATAFORMA, e.plataforma)
                    /*.withValue(ContractPortafolioProductos.Columnas.MANUFACTURER, e.manufacturer)
                    .withValue(ContractPortafolioProductos.Columnas.FORMAT,e.format)*/
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractPortafolioProductos.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de BasePharmaValue finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base_Portafolio_Productos finalizada.");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_PROD);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Productos");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Productos");
        }

    }

    /**
     * PREGUNTAS
     *
     * @param syncResult
     * @param operatorName
     */
    public void realizarSincronizacionLocalPreguntas(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de Preguntas." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_PREGUNTAS, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetPreguntas(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }

                                Log.i("error",""+error.getMessage());
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetPreguntas(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesPreguntas(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesPreguntas(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.PREGUNTAS_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_preguntas[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_preguntas[].class);
        List<Base_preguntas> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_preguntas> expenseMap = new HashMap<>();
        for (Base_preguntas e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractPreguntas.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_PREGUNTAS, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String question;
        String answer;
        String opta;
        String optb;
        String optc;
        String canal;
        String tiempo;
        String test_id;


        while (c.moveToNext()) {

            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            question = c.getString(Projection.QUESTION);
            answer = c.getString(Projection.ANSWER);
            opta = c.getString(Projection.OPTA);
            optb = c.getString(Projection.OPTB);
            optc = c.getString(Projection.OPTC);
            canal = c.getString(Projection.QCANAL);
            tiempo = c.getString(Projection.QTIEMPO);
            test_id = c.getString(Projection.QTEST_ID);


            Base_preguntas match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractPreguntas.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b2=  match.question!=null && !match.question.equals(question);
                boolean b3=  match.answer!=null && !match.answer.equals(answer);
                boolean b4=  match.opta!=null && !match.opta.equals(opta);
                boolean b5=  match.optb!=null && !match.optb.equals(optb);
                boolean b6 = match.optc != null && !match.optc.equals(optc);
                boolean b7 = match.canal != null && !match.canal.equals(canal);
                boolean b8 = match.tiempo != null && !match.tiempo.equals(tiempo);
                boolean b9 = match.test_id != null && !match.test_id.equals(test_id);

                if (b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractPreguntas.Columnas.KEY_QUES,match.question)
                            .withValue(ContractPreguntas.Columnas.KEY_ANSWER,match.answer)
                            .withValue(ContractPreguntas.Columnas.KEY_OPTA,match.opta)
                            .withValue(ContractPreguntas.Columnas.KEY_OPTB,match.optb)
                            .withValue(ContractPreguntas.Columnas.KEY_OPTC, match.optc)
                            .withValue(ContractPreguntas.Columnas.KEY_CANAL, match.canal)
                            .withValue(ContractPreguntas.Columnas.KEY_TIEMPO, match.tiempo)
                            .withValue(ContractPreguntas.Columnas.KEY_TEST_ID, match.test_id)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractPreguntas.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_preguntas e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_Preguntas en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractPreguntas.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractPreguntas.Columnas.KEY_QUES,e.question)
                    .withValue(ContractPreguntas.Columnas.KEY_ANSWER,e.answer)
                    .withValue(ContractPreguntas.Columnas.KEY_OPTA,e.opta)
                    .withValue(ContractPreguntas.Columnas.KEY_OPTB,e.optb)
                    .withValue(ContractPreguntas.Columnas.KEY_OPTC, e.optc)
                    .withValue(ContractPreguntas.Columnas.KEY_CANAL, e.canal)
                    .withValue(ContractPreguntas.Columnas.KEY_TIEMPO, e.tiempo)
                    .withValue(ContractPreguntas.Columnas.KEY_TEST_ID, e.test_id)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractPreguntas.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base_Preguntas finalizada.");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_PREG);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Preguntas");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Preguntas");
        }

    }


    /**
     * TESTS
     *
     * @param syncResult
     * @param operatorName
     */
    public void realizarSincronizacionLocalTests(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de Tests." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_TESTS, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetTests(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetTests(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesTests(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesTests(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.TESTS_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_tests[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_tests[].class);
        List<Base_tests> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_tests> expenseMap = new HashMap<>();
        for (Base_tests e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractTests.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_TESTS, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;


        String test;
        String descripcion;
        String f_inicio;
        String h_inicio;
        String f_limite;
        String h_limite;
        String active;


        while (c.moveToNext()) {

            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            test = c.getString(Projection.TEST);
            descripcion = c.getString(Projection.DESCRIPCION);
            f_inicio = c.getString(Projection.F_INICIO);
            h_inicio = c.getString(Projection.H_INICIO);
            f_limite = c.getString(Projection.F_LIMITE);
            h_limite = c.getString(Projection.H_LIMITE);
            active = c.getString(Projection.ACTIVE);




            Base_tests match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractTests.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);

                boolean b2=match.test!=null && !match.test.equals(test);
                boolean b3=match.descripcion!=null && !match.descripcion.equals(descripcion);
                boolean b4=match.f_inicio!=null && !match.f_inicio.equals(f_inicio);
                boolean b5=match.h_inicio!=null && !match.h_inicio.equals(h_inicio);
                boolean b6 = match.f_limite != null && !match.f_limite.equals(f_limite);
                boolean b7 = match.h_limite != null && !match.h_limite.equals(h_limite);
                boolean b8 = match.active != null && !match.active.equals(active);



                if (b2 || b3 || b4 || b5 || b6 || b7 || b8 ) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractTests.Columnas.KEY_TEST,match.test)
                            .withValue(ContractTests.Columnas.KEY_DESCRIPTION,match.descripcion)
                            .withValue(ContractTests.Columnas.KEY_DATE_START,match.f_inicio)
                            .withValue(ContractTests.Columnas.KEY_HOUR_START,match.h_inicio)
                            .withValue(ContractTests.Columnas.KEY_DATE_LIMIT,match.f_limite)
                            .withValue(ContractTests.Columnas.KEY_HOUR_LIMIT,match.h_limite)
                            .withValue(ContractTests.Columnas.KEY_ACTIVE, match.active)
                            .build());
                    syncResult.stats.numUpdates++;

                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractTests.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_tests e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_Test en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractTests.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractTests.Columnas.KEY_TEST,e.test)
                    .withValue(ContractTests.Columnas.KEY_DESCRIPTION,e.descripcion)
                    .withValue(ContractTests.Columnas.KEY_DATE_START,e.f_inicio)
                    .withValue(ContractTests.Columnas.KEY_HOUR_START,e.h_inicio)
                    .withValue(ContractTests.Columnas.KEY_DATE_LIMIT,e.f_limite)
                    .withValue(ContractTests.Columnas.KEY_HOUR_LIMIT,e.h_limite)
                    .withValue(ContractTests.Columnas.KEY_ACTIVE, e.active)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractTests.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(),"Sincronizacion finalizada",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base_Test finalizada.");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_TESTS);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Test");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Tests");
        }

    }




    /**
     * TIPO DE EXHIBICION
     *
     * @param syncResult
     * @param operatorName
     */
    public void realizarSincronizacionLocalTipoExh(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de Tipo de Exhibicion." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        Log.i("entra","entra");

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_TIPO_EXH, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetTipoExh(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                                Log.i("prueba",""+ error.getMessage());
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetTipoExh(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesTipoExh(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesTipoExh(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"

            loginResult = response.getJSONArray(Constantes.TIPO_EXH_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_tipo_exh[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_tipo_exh[].class);
        List<Base_tipo_exh> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_tipo_exh> expenseMap = new HashMap<>();
        for (Base_tipo_exh e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractTipoExh.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_TIPO_EXH, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;


        String canal;
        String exhibicion;
        String foto;



        while (c.moveToNext()) {

            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            canal = c.getString(Projection.CANAL_E);
            exhibicion = c.getString(Projection.EXHIBICION);
            foto = c.getString(Projection.FOTO_E);





            Base_tipo_exh match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractTipoExh.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);

                boolean b2=match.canal!=null && !match.canal.equals(canal);
                boolean b3=match.exhibicion!=null && !match.exhibicion.equals(exhibicion);
                boolean b4=match.foto!=null && !match.foto.equals(foto);




                if (b2 || b3 || b4) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(Constantes.ID_REMOTA,match.id)
                            .withValue(ContractTipoExh.Columnas.CANAL,match.canal)
                            .withValue(ContractTipoExh.Columnas.EXHIBICION,match.exhibicion)
                            .withValue(ContractTipoExh.Columnas.FOTO,match.foto)
                            .build());
                    syncResult.stats.numUpdates++;

                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractTipoExh.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_tipo_exh e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_Tipo_Exhibicion en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractTipoExh.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractTipoExh.Columnas.CANAL,e.canal)
                    .withValue(ContractTipoExh.Columnas.EXHIBICION,e.exhibicion)
                    .withValue(ContractTipoExh.Columnas.FOTO,e.foto)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractTipoExh.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(),"Sincronizacion finalizada",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base Tipo Exhibicion finalizada.");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_TIPO_EXH);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Tipo Exhibicion  ");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Tipo Exhibicion");
        }

    }



    /**
     * CATEGORIA TIPO EXHIBICION
     *
     * @param syncResult
     * @param operatorName
     */
    public void realizarSincronizacionLocalCategoriaTipo(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de Categoria Tipo" + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        Log.i("entra","entra");

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                //CAMBIAR AQUI
                (Request.Method.POST, Constantes.GET_CATEGORIA_TIPO, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetCategoriaTipo(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                                Log.i("prueba",""+ error.getMessage());
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetCategoriaTipo(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesCategoriaTipo(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesCategoriaTipo(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.CATEGORIA_TIPO_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_categoria_tipo[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_categoria_tipo[].class);
        List<Base_categoria_tipo> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_categoria_tipo> expenseMap = new HashMap<>();
        for (Base_categoria_tipo e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractCategoriaTipo.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_CATEGORIA_TIPO, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;


        String canal;
        String tipo;



        while (c.moveToNext()) {

            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //aqui cambia
            tipo = c.getString(Projection.TIPO_CAT);
            canal = c.getString(Projection.CANAL_CAT);





            Base_categoria_tipo match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractCategoriaTipo.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);

                boolean b2=match.tipo!=null && !match.tipo.equals(tipo);
                boolean b3=match.canal!=null && !match.canal.equals(canal);




                if (b2 || b3) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(Constantes.ID_REMOTA,match.id)
                            .withValue(ContractCategoriaTipo.Columnas.TIPO,match.tipo)
                            .withValue(ContractCategoriaTipo.Columnas.CANAL,match.canal)
                            .build());
                    syncResult.stats.numUpdates++;

                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractCategoriaTipo.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_categoria_tipo e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_Tipo_Exhibicion en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractCategoriaTipo.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractCategoriaTipo.Columnas.TIPO,e.tipo)
                    .withValue(ContractCategoriaTipo.Columnas.CANAL,e.canal)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractCategoriaTipo.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(),"Sincronizacion finalizada",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base Tipo Exhibicion finalizada.");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_TIPO_EXH);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Tipo Exhibicion  ");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Tipo Exhibicion");
        }

    }




    /**
     * MOTIVOS SUGERIDO
     *
     * @param syncResult
     * @param operatorName
     */
    public void realizarSincronizacionLocaLMotivosSugerido(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de Motivos Sugerido." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        Log.i("entra","entra");

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_MOTIVOS_SUGERIDO, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetMotivosSugerido(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                                Log.i("prueba",""+ error.getMessage());
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetMotivosSugerido(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesMotivosSugerido(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesMotivosSugerido(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"

            loginResult = response.getJSONArray(Constantes.TIPO_MOTIVOS_SUGERIDO);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_motivos_sugerido[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_motivos_sugerido[].class);
        List<Base_motivos_sugerido> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_motivos_sugerido> expenseMap = new HashMap<>();
        for (Base_motivos_sugerido e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractMotivoSugerido.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_MOTIVO_SUGERIDO, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;


        String canal;
        String motivo;
        String foto;



        while (c.moveToNext()) {

            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            canal = c.getString(Projection.CANAL_M);
            motivo = c.getString(Projection.MOTIVO_M);
            foto = c.getString(Projection.FOTO_M);


            Base_motivos_sugerido match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractMotivoSugerido.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);

                boolean b2=match.canal!=null && !match.canal.equals(canal);
                boolean b3=match.motivo!=null && !match.motivo.equals(motivo);
                boolean b4=match.foto!=null && !match.foto.equals(foto);




                if (b2 || b3 || b4) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(Constantes.ID_REMOTA,match.id)
                            .withValue(ContractMotivoSugerido.Columnas.CANAL,match.canal)
                            .withValue(ContractMotivoSugerido.Columnas.MOTIVO,match.motivo)
                            .withValue(ContractMotivoSugerido.Columnas.FOTO,match.foto)
                            .build());
                    syncResult.stats.numUpdates++;

                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractMotivoSugerido.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_motivos_sugerido e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_motivos_sugerido en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractMotivoSugerido.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractMotivoSugerido.Columnas.CANAL,e.canal)
                    .withValue(ContractMotivoSugerido.Columnas.MOTIVO,e.motivo)
                    .withValue(ContractMotivoSugerido.Columnas.FOTO,e.foto)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractMotivoSugerido.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(),"Sincronizacion finalizada",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base MotivosSugerido finalizada.");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_MOTIVOS_SUGERIDO);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla MotivosSugerido  ");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "MotivosSugerido");
        }

    }






    /**
     * PROMOCIONES
     *
     * @param syncResult
     * @param operatorName
     */
    public void realizarSincronizacionLocalPromociones(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de Promociones." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_PROMOCIONES, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetPromociones(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetPromociones(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesPromociones(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesPromociones(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.PROMOCIONES_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        BasePromociones[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, BasePromociones[].class);
        List<BasePromociones> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, BasePromociones> expenseMap = new HashMap<>();
        for (BasePromociones e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractPromociones.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_PROMOCIONES, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String canal;
        String tipo;
        String cadena;
        String fabricante;
        String descripcion;
        String categoria;
        String subcategoria;
        String marca;
        String sku;
        String campana;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            canal = c.getString(Projection.CANAL);
            tipo = c.getString(Projection.TIPO);
            cadena = c.getString(Projection.CADENA);
            fabricante = c.getString(Projection.FABRICANTE_P);
            descripcion = c.getString(Projection.DESCRIP);
            categoria = c.getString(Projection.CATEGORIA_P);
            subcategoria = c.getString(Projection.SUBCATEGORIA_P);
            marca = c.getString(Projection.MARCA_P);
            sku = c.getString(Projection.SKU_P);
            campana = c.getString(Projection.CAMPANA_P);

            BasePromociones match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractPromociones.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1 = match.canal != null && !match.canal.equals(canal);
                boolean b2 = match.tipo != null && !match.tipo.equals(tipo);
                boolean b3 = match.cadena != null && !match.cadena.equals(cadena);
                boolean b4 = match.fabricante != null && !match.fabricante.equals(fabricante);
                boolean b5 = match.descripcion != null && !match.descripcion.equals(descripcion);
                boolean b6 = match.categoria != null && !match.categoria.equals(categoria);
                boolean b7 = match.subcategoria != null && !match.subcategoria.equals(subcategoria);
                boolean b8 = match.marca != null && !match.marca.equals(marca);
                boolean b9 = match.sku != null && !match.sku.equals(sku);
                boolean b10 = match.campana != null && !match.campana.equals(campana);

                if (b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractPromociones.Columnas.CANAL, match.canal)
                            .withValue(ContractPromociones.Columnas.TIPO, match.tipo)
                            .withValue(ContractPromociones.Columnas.CADENA, match.cadena)
                            .withValue(ContractPromociones.Columnas.FABRICANTE, match.fabricante)
                            .withValue(ContractPromociones.Columnas.DESCRIPCION, match.descripcion)
                            .withValue(ContractPromociones.Columnas.CATEGORIA, match.categoria)
                            .withValue(ContractPromociones.Columnas.SUBCATEGORIA, match.subcategoria)
                            .withValue(ContractPromociones.Columnas.MARCA, match.marca)
                            .withValue(ContractPromociones.Columnas.SKU, match.sku)
                            .withValue(ContractPromociones.Columnas.CAMPANA, match.campana)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractPromociones.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (BasePromociones e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla BasePromociones en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractPromociones.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA, e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractPromociones.Columnas.CANAL, e.canal)
                    .withValue(ContractPromociones.Columnas.TIPO, e.tipo)
                    .withValue(ContractPromociones.Columnas.CADENA, e.cadena)
                    .withValue(ContractPromociones.Columnas.FABRICANTE, e.fabricante)
                    .withValue(ContractPromociones.Columnas.DESCRIPCION, e.descripcion)
                    .withValue(ContractPromociones.Columnas.CATEGORIA, e.categoria)
                    .withValue(ContractPromociones.Columnas.SUBCATEGORIA, e.subcategoria)
                    .withValue(ContractPromociones.Columnas.MARCA, e.marca)
                    .withValue(ContractPromociones.Columnas.SKU, e.sku)
                    .withValue(ContractPromociones.Columnas.CAMPANA, e.campana)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractPromociones.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de BasePharmaValue finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla BasePromociones finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_PROMO);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Promociones");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Promociones");
        }

    }


    /**
     * TRACKING
     *
     * @param syncResult
     * @param operatorName
     */
    public void realizarSincronizacionLocalTracking(final SyncResult syncResult, String operatorName){
        Log.i(TAG, "Realizando Sincronizacion Local de Tracking." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_TRACKING, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetTracking(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetTracking(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesTracking(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesTracking(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.TRACKING_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Tracking[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Tracking[].class);
        List<Tracking> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Tracking> expenseMap = new HashMap<>();
        for (Tracking e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractTracking.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_TRACKING, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        String customer;
        String cuentas;
        String mecanica;
        String categoria;
        String subcategoria;
        String marca;
        String descripcion;
        String precio_promocion;
        String vigencia;
        String material_pop;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;

            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            customer = c.getString(Projection.COLUMNA_CUSTOMER_T);
            cuentas = c.getString(Projection.COLUMNA_CUENTAS_T);
            mecanica = c.getString(Projection.COLUMNA_MECANICA_T);
            categoria = c.getString(Projection.COLUMNA_CATEGORIA_T);
            subcategoria = c.getString(Projection.COLUMNA_SUBCATEGORIA_T);
            marca = c.getString(Projection.COLUMNA_MARCA_T);
            descripcion = c.getString(Projection.COLUMNA_DESCRIPCION_T);
            precio_promocion = c.getString(Projection.COLUMNA_PRECIO_PROMOCION_T);
            vigencia = c.getString(Projection.COLUMNA_VIGENCIA_T);
            material_pop = c.getString(Projection.COLUMNA_MATERIAL_POP_T);

            Tracking match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractTracking.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b2 = match.customer != null && !match.customer.equals(customer);
                boolean b3 = match.cuentas != null && !match.cuentas.equals(cuentas);
                boolean b4 = match.mecanica != null && !match.mecanica.equals(mecanica);
                boolean b5 = match.categoria != null && !match.categoria.equals(categoria);
                boolean b6 = match.subcategoria != null && !match.subcategoria.equals(subcategoria);
                boolean b7 = match.marca != null && !match.marca.equals(marca);
                boolean b8 = match.descripcion != null && !match.descripcion.equals(descripcion);
                boolean b9 = match.precio_promocion != null && !match.precio_promocion.equals(precio_promocion);
                boolean b10 = match.vigencia != null && !match.vigencia.equals(vigencia);
                boolean b11 = match.material_pop != null && !match.material_pop.equals(material_pop);

                if (b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9|| b10|| b11) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractTracking.Columnas.CUSTOMER, match.customer)
                            .withValue(ContractTracking.Columnas.CUENTAS, match.cuentas)
                            .withValue(ContractTracking.Columnas.MECANICA, match.mecanica)
                            .withValue(ContractTracking.Columnas.CATEGORIA, match.categoria)
                            .withValue(ContractTracking.Columnas.SUBCATEGORIA, match.subcategoria)
                            .withValue(ContractTracking.Columnas.MARCA, match.marca)
                            .withValue(ContractTracking.Columnas.DESCRIPCION, match.descripcion)
                            .withValue(ContractTracking.Columnas.PRECIO_PROMOCION, match.precio_promocion)
                            .withValue(ContractTracking.Columnas.VIGENCIA, match.vigencia)
                            .withValue(ContractTracking.Columnas.MATERIAL_POP, match.material_pop)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractTracking.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Tracking e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Tracking en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractTracking.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractTracking.Columnas.CUSTOMER, e.customer)
                    .withValue(ContractTracking.Columnas.CUENTAS, e.cuentas)
                    .withValue(ContractTracking.Columnas.MECANICA, e.mecanica)
                    .withValue(ContractTracking.Columnas.CATEGORIA, e.categoria)
                    .withValue(ContractTracking.Columnas.SUBCATEGORIA, e.subcategoria)
                    .withValue(ContractTracking.Columnas.MARCA, e.marca)
                    .withValue(ContractTracking.Columnas.DESCRIPCION, e.descripcion)
                    .withValue(ContractTracking.Columnas.PRECIO_PROMOCION, e.precio_promocion)
                    .withValue(ContractTracking.Columnas.VIGENCIA, e.vigencia)
                    .withValue(ContractTracking.Columnas.MATERIAL_POP, e.material_pop)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractTracking.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Tracking finalizada.");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_TRA);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Tracking");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Tracking");
        }

    }

    // TIPO_REGISTRO mpin
    public void realizarSincronizacionLocalTipoRegistro(final SyncResult syncResult, String operatorName){
        Log.i(TAG, "Realizando Sincronizacion Local de Tipo Registro." + operatorName);
        String operator = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operator", operator);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_TIPO_REGISTRO, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetTipoRegistro(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetTipoRegistro(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesTipoRegistro(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje+"TipoRegistro");
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesTipoRegistro(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.TIPO_REGISTRO);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_Tipo_Registro[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_Tipo_Registro[].class);
        List<Base_Tipo_Registro> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_Tipo_Registro> expenseMap = new HashMap<>();
        for (Base_Tipo_Registro e : data) {
            expenseMap.put(e.id, e);
            // expenseMap.put(e.tipo, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractTipoRegistro.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_TIPO_REGISTRO, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        //  String canal;
        String tipo;
        // String descripcion;
        //  String cuenta;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            //   canal = c.getString(Projection.CANAL);
            tipo = c.getString(Projection.TIPO_ACTIVIDADES);
            //    descripcion = c.getString(Projection.DESCRIP);
            //   cuenta = c.getString(Projection.CUENTAP);

            Base_Tipo_Registro match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractTipoRegistro.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                //  boolean b1=match.canal !=null && !match.canal.equals(canal);
                boolean b2=match.tipo !=null && !match.tipo.equals(tipo);
                // boolean b3=match.descripcion !=null && !match.descripcion.equals(descripcion);
                //  boolean b4=match.cuenta !=null && !match.cuenta.equals(cuenta);

                if (b2 ) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            // .withValue(ContractTipoVentas.Columnas.CANAL,match.canal)
                            .withValue(ContractTipoRegistro.Columnas.TIPO,match.tipo)
                            // .withValue(ContractTipoVentas.Columnas.DESCRIPCION,match.descripcion)
                            // .withValue(ContractTipoVentas.Columnas.CUENTA,match.cuenta)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractTipoRegistro.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_Tipo_Registro e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_Tipo_Registro en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractTipoRegistro.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    //    .withValue(ContractPromociones.Columnas.CANAL,e.canal)
                    .withValue(ContractTipoRegistro.Columnas.TIPO,e.tipo)
                    //  .withValue(ContractPromociones.Columnas.DESCRIPCION,e.descripcion)
                    //  .withValue(ContractPromociones.Columnas.CUENTA,e.cuenta)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractTipoRegistro.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base_Tipo_Registro finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_TIPO_REGISTRO);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla TipoRegistro");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "TipoRegistro");
        }

    }
    // TIPO_ACTIVIDADES mpin
    public void realizarSincronizacionLocalTipoActividades(final SyncResult syncResult, String operatorName){
        Log.i(TAG, "Realizando Sincronizacion Local de Tipo Actividades." + operatorName);
        String operator = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operator", operator);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_TIPO_ACTIVIDADES, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetTipoActividades(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetTipoActividades(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesTipoActividades(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje+"TipoActividades");
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesTipoActividades(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.TIPO_ACTIVIDADES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_Tipo_Actividades[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_Tipo_Actividades[].class);
        List<Base_Tipo_Actividades> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_Tipo_Actividades> expenseMap = new HashMap<>();
        for (Base_Tipo_Actividades e : data) {
            expenseMap.put(e.id, e);
            // expenseMap.put(e.tipo, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractTipoActividades.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_TIPO_ACTIVIDADES, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        //  String canal;
        String tipo;
        // String descripcion;
        //  String cuenta;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            //   canal = c.getString(Projection.CANAL);
            tipo = c.getString(Projection.TIPO_ACTIVIDADES);
            //    descripcion = c.getString(Projection.DESCRIP);
            //   cuenta = c.getString(Projection.CUENTAP);

            Base_Tipo_Actividades match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractTipoActividades.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                //  boolean b1=match.canal !=null && !match.canal.equals(canal);
                boolean b2=match.tipo !=null && !match.tipo.equals(tipo);
                // boolean b3=match.descripcion !=null && !match.descripcion.equals(descripcion);
                //  boolean b4=match.cuenta !=null && !match.cuenta.equals(cuenta);

                if (b2 ) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            // .withValue(ContractTipoVentas.Columnas.CANAL,match.canal)
                            .withValue(ContractTipoActividades.Columnas.TIPO,match.tipo)
                            // .withValue(ContractTipoVentas.Columnas.DESCRIPCION,match.descripcion)
                            // .withValue(ContractTipoVentas.Columnas.CUENTA,match.cuenta)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractTipoActividades.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_Tipo_Actividades e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_Tipo_Actividades en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractTipoActividades.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    //    .withValue(ContractPromociones.Columnas.CANAL,e.canal)
                    .withValue(ContractTipoActividades.Columnas.TIPO,e.tipo)
                    //  .withValue(ContractPromociones.Columnas.DESCRIPCION,e.descripcion)
                    //  .withValue(ContractPromociones.Columnas.CUENTA,e.cuenta)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractTipoActividades.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base_Tipo_Actividades finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_TIPO_ACTVIDADES);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla TipoActividades");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "TipoActividades");
        }

    }

    // TIPO_IMPLEMENTACIONES mpin
    public void realizarSincronizacionLocalTipoImplementaciones(final SyncResult syncResult, String operatorName){
        Log.i(TAG, "Realizando Sincronizacion Local de Tipo Implementaciones." + operatorName);
        String operator = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operator", operator);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_TIPO_IMPLEMENTACIONES, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetTipoImplementaciones(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetTipoImplementaciones(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesTipoImplementaciones(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje+"TipoImplementaciones");
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesTipoImplementaciones(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.TIPO_IMPLEMENTACIONES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_Tipo_Implementaciones[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_Tipo_Implementaciones[].class);
        List<Base_Tipo_Implementaciones> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_Tipo_Implementaciones> expenseMap = new HashMap<>();
        for (Base_Tipo_Implementaciones e : data) {
            expenseMap.put(e.id, e);
            // expenseMap.put(e.tipo, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractTipoImplementaciones.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_TIPO_IMPLEMENTACIONES, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
      //  String canal;
        String tipo;
       // String descripcion;
        //  String cuenta;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            //   canal = c.getString(Projection.CANAL);
            tipo = c.getString(Projection.TIPO_IMPLEMENTACIONES);
            //    descripcion = c.getString(Projection.DESCRIP);
            //   cuenta = c.getString(Projection.CUENTAP);

            Base_Tipo_Implementaciones match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractTipoImplementaciones.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                //  boolean b1=match.canal !=null && !match.canal.equals(canal);
                boolean b2=match.tipo !=null && !match.tipo.equals(tipo);
                // boolean b3=match.descripcion !=null && !match.descripcion.equals(descripcion);
                //  boolean b4=match.cuenta !=null && !match.cuenta.equals(cuenta);

                if (b2 ) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            // .withValue(ContractTipoVentas.Columnas.CANAL,match.canal)
                            .withValue(ContractTipoImplementaciones.Columnas.TIPO,match.tipo)
                            // .withValue(ContractTipoVentas.Columnas.DESCRIPCION,match.descripcion)
                            // .withValue(ContractTipoVentas.Columnas.CUENTA,match.cuenta)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractTipoImplementaciones.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_Tipo_Implementaciones e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_Tipo_Implementaciones en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractTipoImplementaciones.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    //    .withValue(ContractPromociones.Columnas.CANAL,e.canal)
                    .withValue(ContractTipoImplementaciones.Columnas.TIPO,e.tipo)
                    //  .withValue(ContractPromociones.Columnas.DESCRIPCION,e.descripcion)
                    //  .withValue(ContractPromociones.Columnas.CUENTA,e.cuenta)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractTipoImplementaciones.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base_Tipo_Implementaciones finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_TIPO_IMPLEMENTACIONES);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla TipoImplementaciones");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "TipoImplementaciones");
        }

    }

    // TIPO_VENTA mpin

    public void realizarSincronizacionLocalTipoVentas(final SyncResult syncResult, String operatorName){
        Log.i(TAG, "Realizando Sincronizacion Local de Tipo Ventas." + operatorName);
        String operator = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operator", operator);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_TIPO_VENTAS, jobject, new Response.Listener<JSONObject>() {  // CREAR PHP
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetTipoVentas(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetTipoVentas(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesTipoVentas(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje+"TipoVentas");
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesTipoVentas(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.TIPO_VENTAS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_Tipo_Ventas[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_Tipo_Ventas[].class);
        List<Base_Tipo_Ventas> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_Tipo_Ventas> expenseMap = new HashMap<>();
        for (Base_Tipo_Ventas e : data) {
            expenseMap.put(e.id, e);
           // expenseMap.put(e.tipo, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractTipoVentas.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_TIPO_VENTAS, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String canal;
        String tipo;
        String descripcion;
      //  String cuenta;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
         //   canal = c.getString(Projection.CANAL);
            tipo = c.getString(Projection.TIPO_VENTAS_NEW);
        //    descripcion = c.getString(Projection.DESCRIP);
         //   cuenta = c.getString(Projection.CUENTAP);

            Base_Tipo_Ventas match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractTipoVentas.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
              //  boolean b1=match.canal !=null && !match.canal.equals(canal);
                boolean b2=match.tipo !=null && !match.tipo.equals(tipo);
               // boolean b3=match.descripcion !=null && !match.descripcion.equals(descripcion);
              //  boolean b4=match.cuenta !=null && !match.cuenta.equals(cuenta);

                if (b2 ) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                           // .withValue(ContractTipoVentas.Columnas.CANAL,match.canal)
                            .withValue(ContractTipoVentas.Columnas.TIPO,match.tipo)
                           // .withValue(ContractTipoVentas.Columnas.DESCRIPCION,match.descripcion)
                           // .withValue(ContractTipoVentas.Columnas.CUENTA,match.cuenta)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractTipoVentas.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_Tipo_Ventas e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_Tipo_Ventas en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractTipoVentas.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                //    .withValue(ContractPromociones.Columnas.CANAL,e.canal)
                    .withValue(ContractTipoVentas.Columnas.TIPO,e.tipo)
                  //  .withValue(ContractPromociones.Columnas.DESCRIPCION,e.descripcion)
                  //  .withValue(ContractPromociones.Columnas.CUENTA,e.cuenta)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractTipoVentas.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base_Tipo_Ventas finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_TIPO_VENTAS);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla TipoVentas");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "TipoVentas");
        }

    }

    // TIPO_UNIDADES mpin

    public void realizarSincronizacionLocalTipoUnidades(final SyncResult syncResult, String operatorName){
        Log.i(TAG, "Realizando Sincronizacion Local de Tipo Unidades." + operatorName);
        String operator = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operator", operator);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_TIPO_UNIDADES, jobject, new Response.Listener<JSONObject>() {  // CREAR PHP
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetTipoUnidades(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetTipoUnidades(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesTipoUnidades(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje+"TipoUnidades");
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesTipoUnidades(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.TIPO_UNIDADES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_Tipo_Unidades[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_Tipo_Unidades[].class);
        List<Base_Tipo_Unidades> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_Tipo_Unidades> expenseMap = new HashMap<>();
        for (Base_Tipo_Unidades e : data) {
            expenseMap.put(e.id, e);
            // expenseMap.put(e.tipo, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractTipoUnidades.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_TIPO_UNIDADES, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String canal;
        String tipo;
        String descripcion;
        //  String cuenta;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            //   canal = c.getString(Projection.CANAL);
            tipo = c.getString(Projection.TIPO_UNIDADES);
            //    descripcion = c.getString(Projection.DESCRIP);
            //   cuenta = c.getString(Projection.CUENTAP);

            Base_Tipo_Unidades match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractTipoUnidades.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                //  boolean b1=match.canal !=null && !match.canal.equals(canal);
                boolean b2=match.tipo !=null && !match.tipo.equals(tipo);
                // boolean b3=match.descripcion !=null && !match.descripcion.equals(descripcion);
                //  boolean b4=match.cuenta !=null && !match.cuenta.equals(cuenta);

                if (b2 ) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            // .withValue(ContractTipoVentas.Columnas.CANAL,match.canal)
                            .withValue(ContractTipoUnidades.Columnas.TIPO,match.tipo)
                            // .withValue(ContractTipoVentas.Columnas.DESCRIPCION,match.descripcion)
                            // .withValue(ContractTipoVentas.Columnas.CUENTA,match.cuenta)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractTipoUnidades.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_Tipo_Unidades e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_Tipo_Unidades en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractTipoUnidades.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    //    .withValue(ContractPromociones.Columnas.CANAL,e.canal)
                    .withValue(ContractTipoUnidades.Columnas.TIPO,e.tipo)
                    //  .withValue(ContractPromociones.Columnas.DESCRIPCION,e.descripcion)
                    //  .withValue(ContractPromociones.Columnas.CUENTA,e.cuenta)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractTipoUnidades.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base_Tipo_Unidades finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_TIPO_UNIDADES);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla TipoUnidades");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "TipoUnidades");
        }

    }

    // MODULOS_ROLES mpin

    public void realizarSincronizacionLocalModulosRoles(final SyncResult syncResult, String operatorName){
        Log.i(TAG, "Realizando Sincronizacion Local de Modulos Roles." + operatorName);
        String operator = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operator", operator);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_MODULO_ROLES, jobject, new Response.Listener<JSONObject>() {  // CREAR PHP
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetModulosRoles(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetModulosRoles(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesModulosRoles(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje+"ModulosRoles");
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesModulosRoles(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.TIPO_MODULOS_ROLES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_Modulos_Roles[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_Modulos_Roles[].class);
        List<Base_Modulos_Roles> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_Modulos_Roles> expenseMap = new HashMap<>();
        for (Base_Modulos_Roles e : data) {
            expenseMap.put(e.id, e);
            // expenseMap.put(e.tipo, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractModulosRoles.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_MODULO_ROLES, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String canal;
        String rol;
        String modulo;
        //  String cuenta;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            //   canal = c.getString(Projection.CANAL);
            rol = c.getString(Projection.TIPO_ROL);
            modulo = c.getString(Projection.TIPO_MODULO);
            //    descripcion = c.getString(Projection.DESCRIP);
            //   cuenta = c.getString(Projection.CUENTAP);

            Base_Modulos_Roles match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractModulosRoles.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                //  boolean b1=match.canal !=null && !match.canal.equals(canal);
                boolean b2=match.rol !=null && !match.rol.equals(rol);
                boolean b3=match.modulo !=null && !match.modulo.equals(modulo);
                // boolean b3=match.descripcion !=null && !match.descripcion.equals(descripcion);
                //  boolean b4=match.cuenta !=null && !match.cuenta.equals(cuenta);

                if (b2 || b3) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            // .withValue(ContractTipoVentas.Columnas.CANAL,match.canal)
                            .withValue(ContractModulosRoles.Columnas.ROL,match.rol)
                            .withValue(ContractModulosRoles.Columnas.MODULO,match.modulo)
                            // .withValue(ContractTipoVentas.Columnas.DESCRIPCION,match.descripcion)
                            // .withValue(ContractTipoVentas.Columnas.CUENTA,match.cuenta)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractModulosRoles.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_Modulos_Roles e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_Modulos_Roles en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractModulosRoles.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    //    .withValue(ContractPromociones.Columnas.CANAL,e.canal)
                    .withValue(ContractModulosRoles.Columnas.ROL,e.rol)
                    .withValue(ContractModulosRoles.Columnas.MODULO,e.modulo)
                    //  .withValue(ContractPromociones.Columnas.DESCRIPCION,e.descripcion)
                    //  .withValue(ContractPromociones.Columnas.CUENTA,e.cuenta)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractModulosRoles.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base_Modulos_Roles finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_TIPO_MODELOS_ROLES);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla ModulosRoles");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "ModulosRoles");
        }

    }





    /**
     * CONVENIOS
     */
    public void realizarSincronizacionLocalConvenios(final SyncResult syncResult, String operatorName){
        Log.i(TAG, "Realizando Sincronizacion Local de Base_convenios." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_CONVENIOS, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetConvenios(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetConvenios(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    Log.i("EXITO WS CONVENIOS", "SI");
                    actualizarDatosLocalesConvenios(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesConvenios(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.CONVENIOSRESULT);
            Log.i("Sync Adapter", "resultado del ws" + loginResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_convenios[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_convenios[].class);
        List<Base_convenios> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_convenios> expenseMap = new HashMap<>();
        for (Base_convenios e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractConvenios.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_CONVENIOS, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        String codigo;
        String pdv;
        String categoria;
        String unidad_negocio;
        String fabricante;
        String marca;
        String tipo_exhibicion;
        String numero_exhibicion;
        String formato;
        String fecha_subida;
        String enlace;


        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            codigo = c.getString(Projection.COLUMNA_CODIGO);
            pdv = c.getString(Projection.COLUMNA_PDV);
            categoria = c.getString(Projection.COLUMNA_CATEGORIA_C);
            unidad_negocio = c.getString(Projection.COLUMNA_UNIDAD_NEGOCIO);
            fabricante = c.getString(Projection.COLUMNA_FABRICANTE);
            marca = c.getString(Projection.COLUMNA_MARCA_C);
            tipo_exhibicion = c.getString(Projection.COLUMNA_TIPO_EXHIBICION);
            numero_exhibicion = c.getString(Projection.COLUMNA_NUMERO_EXHIBICION);
            formato = c.getString(Projection.COLUMNA_FORMATO);
            fecha_subida = c.getString(Projection.COLUMNA_FECHA_SUBIDA);
            enlace = c.getString(Projection.COLUMNA_ENLACE);


            Base_convenios match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractConvenios.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1=match.codigo!=null && !match.codigo.equals(codigo);
                boolean b2=match.pdv!=null && !match.pdv.equals(pdv);
                boolean b3=match.categoria!=null && !match.categoria.equals(categoria);
                boolean b4=match.unidad_negocio!=null && !match.unidad_negocio.equals(unidad_negocio);
                boolean b5=match.fabricante!=null && !match.fabricante.equals(fabricante);
                boolean b6=match.marca!=null && !match.marca.equals(marca);
                boolean b7=match.tipo_exhibicion!=null && !match.tipo_exhibicion.equals(tipo_exhibicion);
                boolean b8=match.numero_exhibicion!=null && !match.numero_exhibicion.equals(numero_exhibicion);
                boolean b9=match.formato!=null && !match.formato.equals(formato);
                boolean b10=match.fecha_subida!=null && !match.fecha_subida.equals(fecha_subida);
                boolean b11=match.enlace!=null && !match.enlace.equals(enlace);

                Log.i("TIENE EL b1",b1+"");

                if (b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10 || b11) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractConvenios.Columnas.CODIGO,match.codigo)
                            .withValue(ContractConvenios.Columnas.PDV,match.pdv)
                            .withValue(ContractConvenios.Columnas.CATEGORIA,match.categoria)
                            .withValue(ContractConvenios.Columnas.UNIDAD_NEGOCIO,match.unidad_negocio)
                            .withValue(ContractConvenios.Columnas.FABRICANTE,match.fabricante)
                            .withValue(ContractConvenios.Columnas.MARCA,match.marca)
                            .withValue(ContractConvenios.Columnas.TIPO_EXHIBICION,match.tipo_exhibicion)
                            .withValue(ContractConvenios.Columnas.NUMERO_EXHIBICION,match.numero_exhibicion)
                            .withValue(ContractConvenios.Columnas.FORMATO,match.formato)
                            .withValue(ContractConvenios.Columnas.FECHA_SUBIDA,match.fecha_subida)
                            .withValue(ContractConvenios.Columnas.ENLACE,match.enlace)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractConvenios.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();
        Log.i(TAG, "ANTES DEL FOR CONVENIOS");
        // Insertar items resultantes
        for (Base_convenios e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_convenios en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractConvenios.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    .withValue(ContractConvenios.Columnas.CODIGO,e.codigo)
                    .withValue(ContractConvenios.Columnas.PDV,e.pdv)
                    .withValue(ContractConvenios.Columnas.CATEGORIA,e.categoria)
                    .withValue(ContractConvenios.Columnas.UNIDAD_NEGOCIO,e.unidad_negocio)
                    .withValue(ContractConvenios.Columnas.FABRICANTE,e.fabricante)
                    .withValue(ContractConvenios.Columnas.MARCA,e.marca)
                    .withValue(ContractConvenios.Columnas.TIPO_EXHIBICION,e.tipo_exhibicion)
                    .withValue(ContractConvenios.Columnas.NUMERO_EXHIBICION,e.numero_exhibicion)
                    .withValue(ContractConvenios.Columnas.FORMATO,e.formato)
                    .withValue(ContractConvenios.Columnas.FECHA_SUBIDA,e.fecha_subida)
                    .withValue(ContractConvenios.Columnas.ENLACE,e.enlace)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractConvenios.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base_convenios finalizada.");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_CONVENIOS);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Convenios");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Convenios");
        }

    }

    /**
     * ROTACION
     *
     * @param syncResult
     * @param operatorName
     */
    public void realizarSincronizacionLocalRotacion(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de Rotacion." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_ROTACION, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetRotacion(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetRotacion(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesRotacion(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesRotacion(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.ROTACION_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        BaseRotacion[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, BaseRotacion[].class);
        List<BaseRotacion> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, BaseRotacion> expenseMap = new HashMap<>();
        for (BaseRotacion e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractRotacion.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_ROTACION, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String categoria;
        String subcategoria;
        String marca;
        String producto;
        String promocional;
        String mecanica;
        String peso;
        String tipo;
        String plataforma;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            categoria = c.getString(Projection.ROT_CATEGORIA);
            subcategoria = c.getString(Projection.ROT_SUBCATEGORIA);
            marca = c.getString(Projection.ROT_MARCA);
            producto = c.getString(Projection.ROT_PRODUCTO);
            promocional = c.getString(Projection.ROT_PROMOCIONAL);
            mecanica = c.getString(Projection.ROT_MECANICA);
            peso = c.getString(Projection.ROT_PESO);
            tipo = c.getString(Projection.ROT_TIPO);
            plataforma = c.getString(Projection.ROT_PLATAFORMA);

            BaseRotacion match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractRotacion.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1 = match.categoria != null && !match.categoria.equals(categoria);
                boolean b2 = match.subcategoria != null && !match.subcategoria.equals(subcategoria);
                boolean b3 = match.marca != null && !match.marca.equals(marca);
                boolean b4 = match.producto != null && !match.producto.equals(producto);
                boolean b5 = match.promocional != null && !match.promocional.equals(promocional);
                boolean b6 = match.mecanica != null && !match.mecanica.equals(mecanica);
                boolean b7 = match.peso != null && !match.peso.equals(peso);
                boolean b8 = match.tipo != null && !match.tipo.equals(tipo);
                boolean b9 = match.plataforma != null && !match.plataforma.equals(plataforma);

                if (b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractRotacion.Columnas.CATEGORIA, match.categoria)
                            .withValue(ContractRotacion.Columnas.SUBCATEGORIA, match.subcategoria)
                            .withValue(ContractRotacion.Columnas.MARCA, match.marca)
                            .withValue(ContractRotacion.Columnas.PRODUCTO, match.producto)
                            .withValue(ContractRotacion.Columnas.PROMOCIONAL, match.promocional)
                            .withValue(ContractRotacion.Columnas.MECANICA, match.mecanica)
                            .withValue(ContractRotacion.Columnas.PESO, match.peso)
                            .withValue(ContractRotacion.Columnas.TIPO, match.tipo)
                            .withValue(ContractRotacion.Columnas.PLATAFORMA, match.plataforma)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractRotacion.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (BaseRotacion e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Rotacion en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractRotacion.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA, e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractRotacion.Columnas.CATEGORIA, e.categoria)
                    .withValue(ContractRotacion.Columnas.SUBCATEGORIA, e.subcategoria)
                    .withValue(ContractRotacion.Columnas.MARCA, e.marca)
                    .withValue(ContractRotacion.Columnas.PRODUCTO, e.producto)
                    .withValue(ContractRotacion.Columnas.PROMOCIONAL, e.promocional)
                    .withValue(ContractRotacion.Columnas.MECANICA, e.mecanica)
                    .withValue(ContractRotacion.Columnas.PESO, e.peso)
                    .withValue(ContractRotacion.Columnas.TIPO, e.tipo)
                    .withValue(ContractRotacion.Columnas.PLATAFORMA, e.plataforma)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractRotacion.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de BasePharmaValue finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Rotacion finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_ROTACION);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Rotacion");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Rotacion");
        }

    }

    /*POP SUGERIDO*/
  /*  public void realizarSincronizacionLocalPopSugerido(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de Pop Sugerido." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_POPSUGERIDO, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetPopSugerido(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetPopSugerido(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesPopSugerido(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesPopSugerido(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.POPSUGERIDORESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        BasePopSugerido[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, BasePopSugerido[].class);
        List<BasePopSugerido> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, BasePopSugerido> expenseMap = new HashMap<>();
        for (BasePopSugerido e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractPopSugerido.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_POPSUGERIDO, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String canal;
        String pop_sugerido;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            canal = c.getString(Projection.COLUMNA_CANAL_POP);
            pop_sugerido = c.getString(Projection.COLUMNA_POP_SUGERIDO);

            BasePopSugerido match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractPopSugerido.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1=match.canal !=null && !match.canal.equals(canal);
                boolean b2=match.pop_sugerido !=null && !match.pop_sugerido.equals(pop_sugerido);

                if (b1 || b2 ) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractPopSugerido.Columnas.CANAL,match.canal)
                            .withValue(ContractPopSugerido.Columnas.POP_SUGERIDO,match.pop_sugerido)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractPopSugerido.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (BasePopSugerido e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Pop Sugerido en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractPopSugerido.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractPopSugerido.Columnas.CANAL,e.canal)
                    .withValue(ContractPopSugerido.Columnas.POP_SUGERIDO,e.pop_sugerido)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractPopSugerido.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de BasePharmaValue finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla pop_sugerido finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_POPSUGERIDO);

        } else {
           // Log.i(TAG, "No se requiere sincronización de tabla pop_sugerido");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "pop_sugerido");
        }

    }*/

    /**
     * POPSUGERIDO
     *
     * @param syncResult
     * @param operatorName
     */
    public void realizarSincronizacionLocalPopSugerido(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de Pop_sugerido." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_POPSUGERIDO, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetPopSugeridos(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetPopSugeridos(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesPopSugeridos(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesPopSugeridos(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.POPSUGERIDO_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        BasePopSugerido[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, BasePopSugerido[].class);
        List<BasePopSugerido> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, BasePopSugerido> expenseMap = new HashMap<>();
        for (BasePopSugerido e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractPopSugerido.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_POPSUGERIDO, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String canal;
        String codigo_pdv;
        String pop_sugerido;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            canal = c.getString(Projection.COLUMNA_CANAL_POP);
            codigo_pdv = c.getString(Projection.COLUMNA_CODIGO_PDV_POP);
            pop_sugerido = c.getString(Projection.COLUMNA_POP_SUGERIDO);

            BasePopSugerido match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractPopSugerido.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1 = match.canal != null && !match.canal.equals(canal);
                boolean b2 = match.codigo_pdv != null && !match.codigo_pdv.equals(codigo_pdv);
                boolean b3 = match.pop_sugerido != null && !match.pop_sugerido.equals(pop_sugerido);

                if (b1 || b2 || b3) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractPopSugerido.Columnas.CANAL, match.canal)
                            .withValue(ContractPopSugerido.Columnas.CODIGO_PDV, match.codigo_pdv)
                            .withValue(ContractPopSugerido.Columnas.POP_SUGERIDO, match.pop_sugerido)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractPopSugerido.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (BasePopSugerido e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Pop_sugerido en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractPopSugerido.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA, e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractPopSugerido.Columnas.CANAL, e.canal)
                    .withValue(ContractPopSugerido.Columnas.CODIGO_PDV, e.codigo_pdv)
                    .withValue(ContractPopSugerido.Columnas.POP_SUGERIDO, e.pop_sugerido)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractPopSugerido.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de BasePharmaValue finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Pop_sugerido finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_POPSUGERIDO);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Pop_sugerido");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Pop_sugerido");
        }

    }

    /**
     * COMBO CANJES
     *
     * @param syncResult
     * @param operatorName
     */
    public void realizarSincronizacionLocalComboCanjes(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de Combo Canjes." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_COMBO_CANJES, jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuestaGetComboCanjes(response, syncResult);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetComboCanjes(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesComboCanjes(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesComboCanjes(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.COMBO_CANJES_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        BaseCombosCanjes[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null,
                BaseCombosCanjes[].class);
        List<BaseCombosCanjes> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, BaseCombosCanjes> expenseMap = new HashMap<>();
        for (BaseCombosCanjes e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractComboCanjes.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_COMBO_CANJES, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        String tipo_combo;
        String mecanica;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            tipo_combo = c.getString(Projection.TIPO_COMBO);
            mecanica = c.getString(Projection.MECANICA);

            BaseCombosCanjes match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractComboCanjes.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1 = match.tipo_combo != null && !match.tipo_combo.equals(tipo_combo);
                boolean b2 = match.mecanica != null && !match.mecanica.equals(mecanica);

                if (b1 || b2) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractComboCanjes.Columnas.TIPO_COMBO, match.tipo_combo)
                            .withValue(ContractComboCanjes.Columnas.MECANICA, match.mecanica)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractComboCanjes.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (BaseCombosCanjes e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Combo Canjes en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractComboCanjes.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA, e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractComboCanjes.Columnas.TIPO_COMBO, e.tipo_combo)
                    .withValue(ContractComboCanjes.Columnas.MECANICA, e.mecanica)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractComboCanjes.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de BasePharmaValue finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Combo Canjes finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_COMBO_CANJES);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Combo Canjes");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Combo Canjes");
        }

    }

    /**
     * CAUSALES MCI
     *
     * @param syncResult
     * @param operatorName
     */
    public void realizarSincronizacionLocalCausalesMCI(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de Causales MCI." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_CAUSALES_MCI, jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuestaGetCausalesMCI(response, syncResult);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetCausalesMCI(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesCausalesMCI(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesCausalesMCI(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.CAUSALES_MCI_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        BaseCausalesMCI[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, BaseCausalesMCI[].class);
        List<BaseCausalesMCI> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, BaseCausalesMCI> expenseMap = new HashMap<>();
        for (BaseCausalesMCI e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractCausalesMCI.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_CAUSALES_MCI, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        String causal;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            causal = c.getString(Projection.CAUSAL_MCI);

            BaseCausalesMCI match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractCausalesMCI.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                boolean b1 = match.causal != null && !match.causal.equals(causal);

                if (b1) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractCausalesMCI.Columnas.CAUSAL, match.causal)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractCausalesMCI.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (BaseCausalesMCI e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Causales MCI en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractCausalesMCI.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA, e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractCausalesMCI.Columnas.CAUSAL, e.causal)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractCausalesMCI.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de BasePharmaValue finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Causales MCI finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_CAUSALES_MCI);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Causales MCI");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Causales MCI");
        }

    }

    /**
     * CAUSALES OSA
     *
     * @param syncResult
     * @param operatorName
     */
    public void realizarSincronizacionLocalCausalesOSA(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de Causales OSA." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_CAUSALES_OSA, jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuestaGetCausalesOSA(response, syncResult);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetCausalesOSA(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesCausalesOSA(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesCausalesOSA(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.CAUSALES_OSA_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        BaseCausalesOSA[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, BaseCausalesOSA[].class);
        List<BaseCausalesOSA> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, BaseCausalesOSA> expenseMap = new HashMap<>();
        for (BaseCausalesOSA e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractCausalesOSA.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_CAUSALES_OSA, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        String canal;
        String responsable;
        String causal;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            canal = c.getString(Projection.CANAL_OSA);
            responsable = c.getString(Projection.RESPONSABLE_OSA);
            causal = c.getString(Projection.CAUSAL_OSA);

            BaseCausalesOSA match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractCausalesOSA.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                boolean b1 = match.canal != null && !match.canal.equals(canal);
                boolean b2 = match.responsable != null && !match.responsable.equals(causal);
                boolean b3 = match.causal != null && !match.causal.equals(causal);

                if (b1 || b2 || b3) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractCausalesOSA.Columnas.CANAL, match.canal)
                            .withValue(ContractCausalesOSA.Columnas.RESPONSABLE, match.responsable)
                            .withValue(ContractCausalesOSA.Columnas.CAUSAL, match.causal)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractCausalesOSA.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (BaseCausalesOSA e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Causales OSA en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractCausalesOSA.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA, e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractCausalesOSA.Columnas.CANAL, e.canal)
                    .withValue(ContractCausalesOSA.Columnas.RESPONSABLE, e.responsable)
                    .withValue(ContractCausalesOSA.Columnas.CAUSAL, e.causal)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractCausalesOSA.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de BasePharmaValue finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Causales OSA finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_CAUSALES_OSA);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Causales OSA");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Causales OSA");
        }

    }

    /**
     * MATERIALES ALERTAS
     *
     * @param syncResult
     * @param operatorName
     */
    public void realizarSincronizacionLocalMaterialesAlertas(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de Materiales Alertas." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_MATERIALES_ALERTAS, jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuestaGetMaterialesAlertas(response, syncResult);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetMaterialesAlertas(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesMaterialesAlertas(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesMaterialesAlertas(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.MATERIALES_ALERTAS_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        BaseAlertas[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, BaseAlertas[].class);
        List<BaseAlertas> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, BaseAlertas> expenseMap = new HashMap<>();
        for (BaseAlertas e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractAlertas.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_MATERIALES_ALERTAS, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        String tipo_alerta;
        String categoria_material;
        String material;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            tipo_alerta = c.getString(Projection.TIPO_ALERTA);
            categoria_material = c.getString(Projection.CATEGORIA_MATERIAL);
            material = c.getString(Projection.MATERIALES);

            BaseAlertas match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractAlertas.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                boolean b1 = match.tipo_alerta != null && !match.tipo_alerta.equals(tipo_alerta);
                boolean b2 = match.categoria != null && !match.categoria.equals(categoria_material);
                boolean b3 = match.material != null && !match.material.equals(material);

                if (b1 || b2 || b3) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractAlertas.Columnas.TIPO_ALERTA, match.tipo_alerta)
                            .withValue(ContractAlertas.Columnas.CATEGORIA, match.categoria)
                            .withValue(ContractAlertas.Columnas.MATERIAL, match.material)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractAlertas.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (BaseAlertas e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Materiales Alertas en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractAlertas.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA, e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractAlertas.Columnas.TIPO_ALERTA, e.tipo_alerta)
                    .withValue(ContractAlertas.Columnas.CATEGORIA, e.categoria)
                    .withValue(ContractAlertas.Columnas.MATERIAL, e.material)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractAlertas.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de BasePharmaValue finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Materiales Alertas finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_MATERIALES_ALERTAS);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Materiales Alertas");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Materiales Alertas");
        }

    }

    /**
     * PDI
     *
     * @param syncResult
     * @param operatorName
     */
    public void realizarSincronizacionLocalPDI(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de PDI." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        System.out.println("SYNC PDI: " + jobject);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_PDI, jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuestaGetPDI(response, syncResult);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetPDI(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesPDI(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesPDI(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.PDI_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        BasePDI[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, BasePDI[].class);
        List<BasePDI> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, BasePDI> expenseMap = new HashMap<>();
        for (BasePDI e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractPDI.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_PDI, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        String canal;
        String categoria;
        String subcategoria;
        String marca;
        String objetivo;
        String plataforma;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            canal = c.getString(Projection.CANAL_PDI);
            categoria = c.getString(Projection.CATEGORIA_PDI);
            subcategoria = c.getString(Projection.SUBCATEGORIA_PDI);
            marca = c.getString(Projection.MARCA_PDI);
            objetivo = c.getString(Projection.OBJETIVO_PDI);
            plataforma = c.getString(Projection.PLATAFORMA_PDI);

            BasePDI match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractPDI.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                boolean b1 = match.canal != null && !match.canal.equals(canal);
                boolean b2 = match.categoria != null && !match.categoria.equals(categoria);
                boolean b3 = match.subcategoria != null && !match.subcategoria.equals(subcategoria);
                boolean b4 = match.marca != null && !match.marca.equals(marca);
                boolean b5 = match.objetivo != null && !match.objetivo.equals(objetivo);
                boolean b6 = match.plataforma != null && !match.plataforma.equals(plataforma);

                if (b1 || b2 || b3 || b4 || b5 || b6) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractPDI.Columnas.CANAL, match.canal)
                            .withValue(ContractPDI.Columnas.CATEGORIA, match.categoria)
                            .withValue(ContractPDI.Columnas.SUBCATEGORIA, match.subcategoria)
                            .withValue(ContractPDI.Columnas.MARCA, match.marca)
                            .withValue(ContractPDI.Columnas.OBJETIVO, match.objetivo)
                            .withValue(ContractPDI.Columnas.PLATAFORMA, match.plataforma)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractPDI.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (BasePDI e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla PDI en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractPDI.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA, e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractPDI.Columnas.CANAL, e.canal)
                    .withValue(ContractPDI.Columnas.CATEGORIA, e.categoria)
                    .withValue(ContractPDI.Columnas.SUBCATEGORIA, e.subcategoria)
                    .withValue(ContractPDI.Columnas.MARCA, e.marca)
                    .withValue(ContractPDI.Columnas.OBJETIVO, e.objetivo)
                    .withValue(ContractPDI.Columnas.PLATAFORMA, e.plataforma)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractPDI.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de BasePharmaValue finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla PDI finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_PDI);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla PDI");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "PDI");
        }

    }

    /**
     * PORTAFOLIO PRIORITARIO
     *
     * @param syncResult
     * @param operatorName
     */
    public void realizarSincronizacionLocalPrioritario(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de Prioritario." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_PRIORITARIO, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetPrioritario(response, syncResult);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {
                            enviarBroadcast(true, Mensajes.TIME_OUT);
                        } else if (error instanceof NoConnectionError) {
                            //TODO
                            enviarBroadcast(true, Mensajes.NO_RED);
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                        } else if (error instanceof ServerError) {
                            //TODO
                            enviarBroadcast(true, Mensajes.SEVER_ERROR);
                        } else if (error instanceof NetworkError) {
                            //TODO
                            enviarBroadcast(true, Mensajes.RED_ERROR);
                        } else if (error instanceof ParseError) {
                            //TODO
                        }
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetPrioritario(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesPrioritario(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesPrioritario(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.PRIORITARIO_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        BasePortafolioPrioritario[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, BasePortafolioPrioritario[].class);
        List<BasePortafolioPrioritario> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, BasePortafolioPrioritario> expenseMap = new HashMap<>();
        for (BasePortafolioPrioritario e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractPrioritario.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_PRIORITARIO, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String canal;
        String codigo_pdv;
        String categoria;
        String subcategoria;
        String marca;
        String contenido;
        String sku;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            canal = c.getString(Projection.COLUMNA_CANAL_PRIORITARIOS);
            codigo_pdv = c.getString(Projection.COLUMNA_CODIGO_PDV_PRIORITARIOS);
            categoria = c.getString(Projection.COLUMNA_CATEGORIA_PRIORITARIOS);
            subcategoria = c.getString(Projection.COLUMNA_SUBCATEGORIA_PRIORITARIOS);
            marca = c.getString(Projection.COLUMNA_MARCA_PRIORITARIOS);
            contenido = c.getString(Projection.COLUMNA_CONTENIDO_PRIORITARIOS);
            sku = c.getString(Projection.COLUMNA_SKU_PRIORITARIOS);

            BasePortafolioPrioritario match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractPrioritario.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1 = match.canal != null && !match.canal.equals(canal);
                boolean b2 = match.codigo_pdv != null && !match.codigo_pdv.equals(codigo_pdv);
                boolean b3 = match.categoria != null && !match.categoria.equals(categoria);
                boolean b4 = match.subcategoria != null && !match.subcategoria.equals(subcategoria);
                boolean b5 = match.marca != null && !match.marca.equals(marca);
                boolean b6 = match.contenido != null && !match.contenido.equals(contenido);
                boolean b7 = match.sku != null && !match.sku.equals(sku);

                if (b1 || b2 || b3 || b4 || b5 || b6 || b7) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractPrioritario.Columnas.CANAL, match.canal)
                            .withValue(ContractPrioritario.Columnas.CODIGO_PDV, match.codigo_pdv)
                            .withValue(ContractPrioritario.Columnas.CATEGORIA, match.categoria)
                            .withValue(ContractPrioritario.Columnas.SUBCATEGORIA, match.subcategoria)
                            .withValue(ContractPrioritario.Columnas.MARCA, match.marca)
                            .withValue(ContractPrioritario.Columnas.CONTENIDO, match.contenido)
                            .withValue(ContractPrioritario.Columnas.SKU, match.sku)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractPrioritario.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (BasePortafolioPrioritario e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Portafolio Prioritario en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractPrioritario.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA, e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractPrioritario.Columnas.CANAL, e.canal)
                    .withValue(ContractPrioritario.Columnas.CODIGO_PDV, e.codigo_pdv)
                    .withValue(ContractPrioritario.Columnas.CATEGORIA, e.categoria)
                    .withValue(ContractPrioritario.Columnas.SUBCATEGORIA, e.subcategoria)
                    .withValue(ContractPrioritario.Columnas.MARCA, e.marca)
                    .withValue(ContractPrioritario.Columnas.CONTENIDO, e.contenido)
                    .withValue(ContractPrioritario.Columnas.SKU, e.sku)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractPrioritario.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de BasePharmaValue finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Portafolio Prioritario finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_PRIORITARIO);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Portafolio Prioritario");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Portafolio Prioritario");
        }

    }

    /**
     * TAREAS
     *
     * @param syncResult
     * @param operatorName
     */
    public void realizarSincronizacionLocalTareas(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de Tareas." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_TAREAS, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetTareas(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetTareas(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesTareas(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesTareas(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.TAREAS_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        BaseTareas[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, BaseTareas[].class);
        List<BaseTareas> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, BaseTareas> expenseMap = new HashMap<>();
        for (BaseTareas e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractTareas.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_TAREAS, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String canal;
        String codigopdv;
        String mercaderista;
        String tareas;
        String periodo;
        String fecha_ingreso;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            canal = c.getString(Projection.CANALTAR);
            codigopdv = c.getString(Projection.CODIGOPDVTAR);
            mercaderista = c.getString(Projection.MERCADERISTATAR);
            tareas = c.getString(Projection.TAREAS);
            periodo = c.getString(Projection.PERIODO);
            fecha_ingreso = c.getString(Projection.FECHA_INGRESOTAR);

            BaseTareas match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractTareas.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1 = match.channel != null && !match.channel.equals(canal);
                boolean b2 = match.codigo_pdv != null && !match.codigo_pdv.equals(codigopdv);
                boolean b3 = match.mercaderista != null && !match.mercaderista.equals(mercaderista);
                boolean b4 = match.tareas != null && !match.tareas.equals(tareas);
                boolean b5 = match.periodo != null && !match.periodo.equals(periodo);
                boolean b6 = match.fecha_ingreso != null && !match.fecha_ingreso.equals(fecha_ingreso);

                if (b1 || b2 || b3 || b4 || b5 || b6) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractTareas.Columnas.CANAL, match.channel)
                            .withValue(ContractTareas.Columnas.CODIGOPDV, match.codigo_pdv)
                            .withValue(ContractTareas.Columnas.MERCADERISTA, match.mercaderista)
                            .withValue(ContractTareas.Columnas.TAREAS, match.tareas)
                            .withValue(ContractTareas.Columnas.PERIODO, match.periodo)
                            .withValue(ContractTareas.Columnas.FECHA_INGRESO, match.fecha_ingreso)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractTareas.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (BaseTareas e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Tareas en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractTareas.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA, e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractTareas.Columnas.CANAL, e.channel)
                    .withValue(ContractTareas.Columnas.CODIGOPDV, e.codigo_pdv)
                    .withValue(ContractTareas.Columnas.MERCADERISTA, e.mercaderista)
                    .withValue(ContractTareas.Columnas.TAREAS, e.tareas)
                    .withValue(ContractTareas.Columnas.PERIODO, e.periodo)
                    .withValue(ContractTareas.Columnas.FECHA_INGRESO, e.fecha_ingreso)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractTareas.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de BasePharmaValue finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Tareas finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_TAREAS);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Tareas");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Tarea");
        }

    }
    private void realizarSincronizacionRemotaInsertEvaluacion() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor... N");

        iniciarActualizacion(ContractInsertEvaluacionEncuesta.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertEvaluacionEncuesta.CONTENT_URI, Projection.PROJECTION_INSERT_EVALUACION_DEMO);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Resultado en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_EVALUACION_ENCUESTA,
                                UtilidadesEvaluacionEncuesta.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertEvaluacion(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server "+error.getMessage());
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" ;//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertEvaluacion(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertEvaluacionEncuesta.CONTENT_URI, ContractInsertEvaluacionEncuesta.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
     *  SINCRONIZAR TABLA EVALUACIONES ENCUESTA
     */
    private void realizarSincronizacionLocalEvaluacionPromotor(final SyncResult syncResult, String mercaderista) {
        Log.i(TAG, "Realizando Sincronizacion Local de BaseEvaluacionEncuesta." + mercaderista);
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operator", mercaderista);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_EVALUACION_PROMOTOR, jobject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetEvaluacionPromotor(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetEvaluacionPromotor(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesEvaluacionGestor(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesEvaluacionGestor(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.EVALUACION_PROMOTOR_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        BaseEvaluacionPromotor[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, BaseEvaluacionPromotor[].class);
        List<BaseEvaluacionPromotor> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, BaseEvaluacionPromotor> expenseMap = new HashMap<>();
        for (BaseEvaluacionPromotor e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractEvaluacionEncuesta.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_EVALUACION_GESTOR, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos

        String id;
        String nombre_encuesta;
        String descripcion;
        String categoria;
        String re;
        String pregunta;
        String tipoPregunta;
        String opc_a;
        String opc_b;
        String opc_c;
        String opc_d;
        String opc_e;
        String foto;
        String tipo_campo;
        String puntaje_por_pregunta;
        String habilitado;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            nombre_encuesta = c.getString(Projection.COLUMNA_NOMBRE_ENCUESTA);
            descripcion = c.getString(Projection.COLUMNA_DESCRIPCION);
            categoria = c.getString(Projection.COLUMNA_CATEGORIA_E);
            re = c.getString(Projection.COLUMNA_RE_E);
            pregunta = c.getString(Projection.COLUMNA_PREGUNTA_E);
            tipoPregunta = c.getString(Projection.COLUMNA_TIPO_PREGUNTA_E);
            opc_a = c.getString(Projection.COLUMNA_OPC_A);
            opc_b = c.getString(Projection.COLUMNA_OPC_B);
            opc_c = c.getString(Projection.COLUMNA_OPC_C);
            opc_d = c.getString(Projection.COLUMNA_OPC_D);
            opc_e = c.getString(Projection.COLUMNA_OPC_E);
            foto = c.getString(Projection.COLUMNA_FOTO_E);
            tipo_campo = c.getString(Projection.COLUMNA_TIPO_CAMPO);
            puntaje_por_pregunta = c.getString(Projection.COLUMNA_PUNTAJE_POR_PREGUNTA);
            habilitado = c.getString(Projection.COLUMNA_HABILITADO);

            BaseEvaluacionPromotor match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractEvaluacionEncuesta.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                boolean b1 = match.nombre_encuesta != null && !match.nombre_encuesta.equals(nombre_encuesta);
                boolean b2 = match.descripcion != null && !match.descripcion.equals(descripcion);
                boolean b3 = match.categoria != null && !match.categoria.equals(categoria);
                boolean b4 = match.re != null && !match.re.equals(re);
                boolean b5 = match.pregunta != null && !match.pregunta.equals(pregunta);
                boolean b6 = match.tipo_pregunta != null && !match.tipo_pregunta.equals(tipoPregunta);
                boolean b7= match.opc_a != null && !match.opc_a.equals(opc_a);
                boolean b8 = match.opc_b != null && !match.opc_b.equals(opc_b);
                boolean b9 = match.opc_c != null && !match.opc_c.equals(opc_c);
                boolean b10 = match.opc_d != null && !match.opc_d.equals(opc_d);
                boolean b11 = match.opc_e != null && !match.opc_e.equals(opc_e);
                boolean b12 = match.foto != null && !match.foto.equals(foto);
                boolean b13 = match.tipo_campo != null && !match.tipo_campo.equals(tipo_campo);
                boolean b14 = match.puntaje_por_pregunta != null && !match.puntaje_por_pregunta.equals(puntaje_por_pregunta);
                boolean b15 = match.habilitado != null && !match.habilitado.equals(habilitado);

                if (b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10 || b11 || b12 || b13 || b14 || b15) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContractEvaluacionEncuesta.Columnas.NOMBRE_ENCUESTA, match.nombre_encuesta)
                            .withValue(ContractEvaluacionEncuesta.Columnas.DESCRIPCION, match.descripcion)
                            .withValue(ContractEvaluacionEncuesta.Columnas.CATEGORIA, match.categoria)
                            .withValue(ContractEvaluacionEncuesta.Columnas.RE, match.re)
                            .withValue(ContractEvaluacionEncuesta.Columnas.PREGUNTA, match.pregunta)
                            .withValue(ContractEvaluacionEncuesta.Columnas.TIPO_PREGUNTA, match.tipo_pregunta)
                            .withValue(ContractEvaluacionEncuesta.Columnas.OPC_A, match.opc_a)
                            .withValue(ContractEvaluacionEncuesta.Columnas.OPC_B, match.opc_b)
                            .withValue(ContractEvaluacionEncuesta.Columnas.OPC_C, match.opc_c)
                            .withValue(ContractEvaluacionEncuesta.Columnas.OPC_D, match.opc_d)
                            .withValue(ContractEvaluacionEncuesta.Columnas.OPC_E, match.opc_e)
                            .withValue(ContractEvaluacionEncuesta.Columnas.FOTO, match.foto)
                            .withValue(ContractEvaluacionEncuesta.Columnas.TIPO_CAMPO, match.tipo_campo)
                            .withValue(ContractEvaluacionEncuesta.Columnas.PUNTAJE_POR_PREGUNTA, match.puntaje_por_pregunta)
                            .withValue(ContractEvaluacionEncuesta.Columnas.HABILITADO, match.habilitado)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractEvaluacionEncuesta.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (BaseEvaluacionPromotor e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla BaseEvaluacionPromotor en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractEvaluacionEncuesta.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA, e.id) //Esta clave importante!!! ForeignKey del servidor
                    .withValue(ContractEvaluacionEncuesta.Columnas.NOMBRE_ENCUESTA, e.nombre_encuesta)
                    .withValue(ContractEvaluacionEncuesta.Columnas.DESCRIPCION, e.descripcion)
                    .withValue(ContractEvaluacionEncuesta.Columnas.CATEGORIA, e.categoria)
                    .withValue(ContractEvaluacionEncuesta.Columnas.RE, e.re)
                    .withValue(ContractEvaluacionEncuesta.Columnas.PREGUNTA, e.pregunta)
                    .withValue(ContractEvaluacionEncuesta.Columnas.TIPO_PREGUNTA, e.tipo_pregunta)
                    .withValue(ContractEvaluacionEncuesta.Columnas.OPC_A, e.opc_a)
                    .withValue(ContractEvaluacionEncuesta.Columnas.OPC_B, e.opc_b)
                    .withValue(ContractEvaluacionEncuesta.Columnas.OPC_C, e.opc_c)
                    .withValue(ContractEvaluacionEncuesta.Columnas.OPC_D, e.opc_d)
                    .withValue(ContractEvaluacionEncuesta.Columnas.OPC_E, e.opc_e)
                    .withValue(ContractEvaluacionEncuesta.Columnas.FOTO, e.foto)
                    .withValue(ContractEvaluacionEncuesta.Columnas.TIPO_CAMPO, e.tipo_campo)
                    .withValue(ContractEvaluacionEncuesta.Columnas.PUNTAJE_POR_PREGUNTA, e.puntaje_por_pregunta)
                    .withValue(ContractEvaluacionEncuesta.Columnas.HABILITADO, e.habilitado)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractEvaluacionEncuesta.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de BasePharmaValue finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla BaseEvaluacionPromotor finalizada.");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_EVALUACION_PROMOTOR);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla BaseEvaluacionPromotor");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "BaseEvaluacionPromotor");
        }
    }

    /**********************************************************EXHIBICIONES COLGATE***********************************************************************************/
    private void realizarSincronizacionRemotaInsertExhBassa() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(InsertExhBassa.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(InsertExhBassa.CONTENT_URI, Projection.PROJECTION_INSERTEXHIBICION_COLGATE);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");

        if (c.getCount() > 0) {
            try {
                while (c.moveToNext()) {
                    final int idLocal = c.getInt(Projection.COLUMNA_ID);
                    Log.i(TAG, "SyncAdapterSubida: Insertando datos de ExhibicionColgate en el servidor...");

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, Constantes.INSERTAR_EXHIBICION_BASSA, UtilidadesExhBassa.deCursorAJSONObject(c),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            procesarRespuestaInsertExhBassa(response, idLocal);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                                Log.i(TAG, "EXHIBICIONCOLGATE: Error Network Timeout" + error);
                                            } else if (error instanceof AuthFailureError) {
                                                Log.i(TAG, "EXHIBICIONCOLGATE: Error AuthFailure" + error);
                                            } else if (error instanceof ServerError) {
                                                Log.i(TAG, "EXHIBICIONCOLGATE: Error Server" + error);
                                            } else if (error instanceof NetworkError) {
                                                Log.i(TAG, "EXHIBICIONCOLGATE: Error Network" + error);
                                            } else if (error instanceof ParseError) {
                                                Log.i(TAG, "EXHIBICIONCOLGATE: Error Parse" + error);
                                            }
                                        }
                                    }) {
                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            headers.put("Accept", "application/json");
                            return headers;
                        }

                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";//+ getParamsEncoding();
                        }
                    };

                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    /**
     * Procesa los diferentes tipos de respuesta obtenidos del servidor
     *
     * @param response Respuesta en formato Json
     */
    public void procesarRespuestaInsertExhBassa(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.ID_EXH_SUPERVISORES);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(InsertExhBassa.CONTENT_URI, InsertExhBassa.Columnas._ID, idRemota, idLocal);
//                    Toast.makeText(getContext(),"Datos almacenados en el Servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local");
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /***************************************************************************************************************************************************************/
    private void realizarSincronizacionRemotaInsertExhAdNu() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(InsertExhibicionesAdNu.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(InsertExhibicionesAdNu.CONTENT_URI, Projection.PROJECTION_INSERTEXHIBICION_AD_NU);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");

        if (c.getCount() > 0) {
            try {
                while (c.moveToNext()) {
                    final int idLocal = c.getInt(Projection.COLUMNA_ID);
                    Log.i(TAG, "SyncAdapterSubida: Insertando datos de ExhibicionADNU en el servidor...");

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, Constantes.INSERTAR_EXHIBICION, UtilidadesExhibicionesAdNu.deCursorAJSONObject(c),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            procesarRespuestaInsertExhAdNu(response, idLocal);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                                Log.i(TAG, "EXHIBICION: Error Network Timeout" + error);
                                            } else if (error instanceof AuthFailureError) {
                                                Log.i(TAG, "EXHIBICION: Error AuthFailure" + error);
                                            } else if (error instanceof ServerError) {
                                                Log.i(TAG, "EXHIBICION: Error Server" + error);
                                            } else if (error instanceof NetworkError) {
                                                Log.i(TAG, "EXHIBICION: Error Network" + error);
                                            } else if (error instanceof ParseError) {
                                                Log.i(TAG, "EXHIBICION: Error Parse" + error);
                                            }
                                        }
                                    }) {
                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            headers.put("Accept", "application/json");
                            return headers;
                        }

                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";//+ getParamsEncoding();
                        }
                    };

                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    /**
     * Procesa los diferentes tipos de respuesta obtenidos del servidor
     *
     * @param response Respuesta en formato Json
     */
    public void procesarRespuestaInsertExhAdNu(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.ID_EXH);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(InsertExhibicionesAdNu.CONTENT_URI, InsertExhibicionesAdNu.Columnas._ID, idRemota, idLocal);
//                    Toast.makeText(getContext(),"Datos almacenados en el Servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local");
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /*
     *  SINCRONIZAR TABLA EXHIBICION SUPERVISORES
     */

    public void realizarSincronizacionLocalExhSupervisores(final SyncResult syncResult, String userName){
        Log.i(TAG, "Realizando Sincronizacion Local de HerramientasExhibicion." + userName);

        String user = userName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo
        map.put("exhsupervisor", user);
        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        //enviarBroadcast(true, Constantes.MENSAJE_READY);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_EXHIBITION_SUPERVISOR, jobject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetTipoExhSupervisores(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    /**
     * Procesa la respuesta del servidor al pedir que se retornen todos los productos.
     *
     * @param response   Respuesta en formato Json
     * @param syncResult Registro de resultados de sincronización
     */
    private void procesarRespuestaGetTipoExhSupervisores(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesTipoExhSupervisores(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Actualiza los registros locales a través de una comparación con los datos
     * del servidor
     *
     * @param response   Respuesta en formato Json obtenida del servidor
     * @param syncResult Registros de la sincronización
     */
    private void actualizarDatosLocalesTipoExhSupervisores(JSONObject response, SyncResult syncResult) {

        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.TYPERESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        ExhibicionSupervisor[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, ExhibicionSupervisor[].class);
        List<ExhibicionSupervisor> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, ExhibicionSupervisor> expenseMap = new HashMap<String, ExhibicionSupervisor>();
        for (ExhibicionSupervisor e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractExhibicionSupervisor.CONTENT_URI;
        String select = ContractExhibicionSupervisor.Columnas.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_EXH_SUPERVISOR, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        String cp_code;
        String canal;
        String supervisor;
        String exhibition_tool;
        String manufacturer;
        String category;
        String subcategory;
        String zone_exhibition;
        String personalization;
        String price_tag;
        String num_exhibitions;
        String observation;
        String photo;
        String date;
        String visual_access;
        String photo2;
        String tipo_herramienta;
        String campana;
        String convenio;
        String clasificacion;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;

            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            cp_code = c.getString(Projection.COLUMNA_EXH_CP_CODE);
            canal = c.getString(Projection.COLUMNA_EXH_CANAL);
            supervisor = c.getString(Projection.COLUMNA_EXH_SUPERVISOR);
            exhibition_tool = c.getString(Projection.COLUMNA_EXH_EXHIBITION_TOOL);
            manufacturer = c.getString(Projection.COLUMNA_EXH_MANUFACTURER);
            category = c.getString(Projection.COLUMNA_EXH_CATEGORY);
            subcategory = c.getString(Projection.COLUMNA_EXH_SUBCATEGORY);
            zone_exhibition = c.getString(Projection.COLUMNA_EXH_ZONE_EXHIBITION);
            personalization = c.getString(Projection.COLUMNA_EXH_PERSONALIZATION);
            price_tag = c.getString(Projection.COLUMNA_EXH_PRICE_TAG);
            num_exhibitions = c.getString(Projection.COLUMNA_EXH_NUM_EXHIBITIONS);
            observation = c.getString(Projection.COLUMNA_EXH_OBSERVATION);
            photo = c.getString(Projection.COLUMNA_EXH_PHOTO);
            date = c.getString(Projection.COLUMNA_EXH_DATE);
            visual_access = c.getString(Projection.COLUMNA_EXH_VISUAL_ACCESS);
            photo2 = c.getString(Projection.COLUMNA_EXH_PHOTO2);
            tipo_herramienta = c.getString(Projection.COLUMNA_EXH_TIPO_HERRAMIENTA);
            campana = c.getString(Projection.COLUMNA_EXH_CAMPANA);
            convenio = c.getString(Projection.COLUMNA_EXH_CONVENIO);
            clasificacion = c.getString(Projection.COLUMNA_EXH_CLASIFICACION);


            ExhibicionSupervisor match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractExhibicionSupervisor.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                boolean b = match.cp_code != null && !match.toString().equals(cp_code);
                boolean b1 = match.canal != null && !match.toString().equals(canal);
                boolean b2 = match.supervisor != null && !match.toString().equals(supervisor);
                boolean b3 = match.exhibition_tool != null && !match.toString().equals(exhibition_tool);
                boolean b4 = match.manufacturer != null && !match.toString().equals(manufacturer);
                boolean b5 = match.category != null && !match.toString().equals(category);
                boolean b6 = match.subcategory != null && !match.toString().equals(subcategory);
                boolean b7 = match.zone_exhibition != null && !match.toString().equals(zone_exhibition);
                boolean b8 = match.personalization != null && !match.toString().endsWith(personalization);
                boolean b9 = match.price_tag != null && !match.toString().endsWith(price_tag);
                boolean b10 = match.num_exhibitions != null && !match.toString().endsWith(num_exhibitions);
                boolean b11 = match.observation != null && !match.toString().endsWith(observation);
                boolean b12 = match.photo != null && !match.toString().endsWith(photo);
                boolean b13 = match.date != null && !match.toString().endsWith(date);
                boolean b14 = match.visual_access != null && !match.toString().endsWith(visual_access);
                boolean b15 = match.photo2 != null && !match.toString().endsWith(photo2);
                boolean b16 = match.tipo_herramienta != null && !match.toString().endsWith(tipo_herramienta);
                boolean b17 = match.campana != null && !match.toString().endsWith(campana);
                boolean b18 = match.convenio != null && !match.toString().endsWith(convenio);
                boolean b19 = match.clasificacion != null && !match.toString().equals(clasificacion);

                if (b || b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10 || b11 ||
                        b12 || b13 || b14 || b15 || b16 || b17 || b18 || b19) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContractExhibicionSupervisor.Columnas.CP_CODE, match.cp_code)
                            .withValue(ContractExhibicionSupervisor.Columnas.CANAL, match.canal)
                            .withValue(ContractExhibicionSupervisor.Columnas.SUPERVISOR, match.supervisor)
                            .withValue(ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL, match.exhibition_tool)
                            .withValue(ContractExhibicionSupervisor.Columnas.MANUFACTURER, match.manufacturer)
                            .withValue(ContractExhibicionSupervisor.Columnas.CATEGORY, match.category)
                            .withValue(ContractExhibicionSupervisor.Columnas.SUBCATEGORY, match.subcategory)
                            .withValue(ContractExhibicionSupervisor.Columnas.ZONE_EXHIBITION, match.zone_exhibition)
                            .withValue(ContractExhibicionSupervisor.Columnas.PERSONALIZATION, match.personalization)
                            .withValue(ContractExhibicionSupervisor.Columnas.PRICE_TAG, match.price_tag)
                            .withValue(ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS, match.num_exhibitions)
                            .withValue(ContractExhibicionSupervisor.Columnas.OBSERVATION, match.observation)
                            .withValue(ContractExhibicionSupervisor.Columnas.PHOTO, match.photo)
                            .withValue(ContractExhibicionSupervisor.Columnas.DATE, match.date)
                            .withValue(ContractExhibicionSupervisor.Columnas.VISUAL_ACCESS, match.visual_access)
                            .withValue(ContractExhibicionSupervisor.Columnas.PHOTO2, match.photo2)
                            .withValue(ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA, match.tipo_herramienta)
                            .withValue(ContractExhibicionSupervisor.Columnas.CAMPANA, match.campana)
                            .withValue(ContractExhibicionSupervisor.Columnas.CONVENIO, match.convenio)
                            .withValue(ContractExhibicionSupervisor.Columnas.CLASIFICACION, match.clasificacion)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractExhibicionSupervisor.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (ExhibicionSupervisor e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de HerramientasExhibicion en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractExhibicionSupervisor.CONTENT_URI)
                    .withValue(ContractExhibicionSupervisor.Columnas.ID_REMOTA, e.id) //Esta clave importante!!! ForeignKey del servidor
                    .withValue(ContractExhibicionSupervisor.Columnas.CP_CODE, e.cp_code)
                    .withValue(ContractExhibicionSupervisor.Columnas.CANAL, e.canal)
                    .withValue(ContractExhibicionSupervisor.Columnas.SUPERVISOR, e.supervisor)
                    .withValue(ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL, e.exhibition_tool)
                    .withValue(ContractExhibicionSupervisor.Columnas.MANUFACTURER, e.manufacturer)
                    .withValue(ContractExhibicionSupervisor.Columnas.CATEGORY, e.category)
                    .withValue(ContractExhibicionSupervisor.Columnas.SUBCATEGORY, e.subcategory)
                    .withValue(ContractExhibicionSupervisor.Columnas.ZONE_EXHIBITION, e.zone_exhibition)
                    .withValue(ContractExhibicionSupervisor.Columnas.PERSONALIZATION, e.personalization)
                    .withValue(ContractExhibicionSupervisor.Columnas.PRICE_TAG, e.price_tag)
                    .withValue(ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS, e.num_exhibitions)
                    .withValue(ContractExhibicionSupervisor.Columnas.OBSERVATION, e.observation)
                    .withValue(ContractExhibicionSupervisor.Columnas.PHOTO, e.photo)
                    .withValue(ContractExhibicionSupervisor.Columnas.DATE, e.date)
                    .withValue(ContractExhibicionSupervisor.Columnas.VISUAL_ACCESS, e.visual_access)
                    .withValue(ContractExhibicionSupervisor.Columnas.PHOTO2, e.photo2)
                    .withValue(ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA, e.tipo_herramienta)
                    .withValue(ContractExhibicionSupervisor.Columnas.CAMPANA, e.campana)
                    .withValue(ContractExhibicionSupervisor.Columnas.CONVENIO, e.convenio)
                    .withValue(ContractExhibicionSupervisor.Columnas.CLASIFICACION, e.clasificacion)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractExhibicionSupervisor.CONTENT_URI,
                    null,
                    false);
            Log.i(TAG, "Sincronización de tabla HerramientasExhibicion finalizada.");
//            enviarBroadcast(true, Constantes.MENSAJE_FIN_TIPO);
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_HERR_EXH);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla HerramientasExhibicion");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "HerramientasExhibicion");
//            enviarBroadcast(true, Constantes.MENSAJE_NO);
        }
    }
    /*
     *  NOVEDADES TIPOS NOVEDADES BASSA NUEVO
     */

    public void realizarSincronizacionLocalTipoNovedades(final SyncResult syncResult, String operatorName){
        Log.i(TAG, "Realizando Sincronizacion Local de Tipo Novedades." + operatorName);
        String operator = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operator", operator);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_TIPO_NOVEDADES, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetTipoNovedades(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetTipoNovedades(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesTipoNovedades(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesTipoNovedades(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.TIPONOVEDADESRESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_tipo_novedades[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_tipo_novedades[].class);
        List<Base_tipo_novedades> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_tipo_novedades> expenseMap = new HashMap<>();
        for (Base_tipo_novedades e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractTipoNovedades.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_TIPO_NOVEDADES, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String tipo_novedad;
        String cuenta;


        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            tipo_novedad = c.getString(Projection.TIPO_NOVEDAD);
            cuenta = c.getString(Projection.CUENTATP);


            Base_tipo_novedades match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractTipoNovedades.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1=match.tipo_novedad !=null && !match.tipo_novedad.equals(tipo_novedad);
                boolean b2=match.cuenta !=null && !match.cuenta.equals(cuenta);

                if (b1 || b2) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractTipoNovedades.Columnas.TIPO_NOVEDAD,match.tipo_novedad)
                            .withValue(ContractTipoNovedades.Columnas.CUENTA,match.cuenta)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractTipoNovedades.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_tipo_novedades e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base Tipo Novedades en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractTipoNovedades.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractTipoNovedades.Columnas.TIPO_NOVEDAD,e.tipo_novedad)
                    .withValue(ContractTipoNovedades.Columnas.CUENTA,e.cuenta)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractTipoNovedades.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base_Tipo_Novedades finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_TIPONOVEDADES);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Base_Tipo_Novedades");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Tipo Novedades");
        }

    }

    /*
     *  CANALES FOTOS NUEVO BASSA FOTOGRAFICO VALIDACION
     */

    public void realizarSincronizacionLocalCanalFotos(final SyncResult syncResult, String operatorName){
        Log.i(TAG, "Realizando Sincronizacion Local de Canales Fotografia." + operatorName);
        String operator = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operator", operator);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_CANALES_FOTOS, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetCanalFotos(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetCanalFotos(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesCanalFotos(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesCanalFotos(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.CANALESFOTOSRESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_canales_fotos[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_canales_fotos[].class);
        List<Base_canales_fotos> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_canales_fotos> expenseMap = new HashMap<>();
        for (Base_canales_fotos e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractCanalesFotos.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_CANALES_FOTOS, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String pdv;
        String canal;
        String activar;

        //todo revisar esto y poner mis propias variables


        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            pdv = c.getString(Projection.PDV);
            canal = c.getString(Projection.COLUMNA_CANAL);
            activar = c.getString(Projection.COLUMNA_ACTIVAR);


            Base_canales_fotos match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractCanalesFotos.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1=match.pdv !=null && !match.pdv.equals(pdv);
                boolean b2=match.canal !=null && !match.canal.equals(canal);
                boolean b3=match.activar !=null && !match.activar.equals(activar);

                if (b1 || b2) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractCanalesFotos.Columnas.PDV,match.pdv)
                            .withValue(ContractCanalesFotos.Columnas.CANAL,match.canal)
                            .withValue(ContractCanalesFotos.Columnas.ACTIVAR,match.activar)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractCanalesFotos.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_canales_fotos e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base Tipo Novedades en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractCanalesFotos.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractCanalesFotos.Columnas.PDV,e.pdv)
                    .withValue(ContractCanalesFotos.Columnas.CANAL,e.canal)
                    .withValue(ContractCanalesFotos.Columnas.ACTIVAR,e.activar)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractCanalesFotos.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base_Canal_Foto finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_PRODUCTOSCANALES);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Base_Canal_Foto");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "CANALES FOTOGRAFIA");
        }

    }


    /*
     *  NOVEDADES tipos insert
     */

    private void realizarSincronizacionRemotaInsertNovedades() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertNovedades.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertNovedades.CONTENT_URI, Projection.PROJECTION_INSERT_NOVEDADES);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Agotados en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_NOVEDADES,
                                UtilidadesNovedades.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertNovedades(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";// + getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertNovedades(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertNovedades.CONTENT_URI, ContractInsertNovedades.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    /*
     *  SINCRONIZAR TABLA LOCAL
     */
    public void realizarSincronizacionLocalPrecios(final SyncResult syncResult) {
        Log.i(TAG, "Realizando Sincronizacion Local de Precios.");

        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_PRECIOS, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetPrecios(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    /**
     * Procesa la respuesta del servidor al pedir que se retornen todos los productos.
     *
     * @param response   Respuesta en subchannel Json
     * @param syncResult Registro de resultados de sincronización
     */
    private void procesarRespuestaGetPrecios(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesPrecios(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Actualiza los registros locales a través de una comparación con los datos
     * del servidor
     *
     * @param response   Respuesta en subchannel Json obtenida del servidor
     * @param syncResult Registros de la sincronización
     */
    private void actualizarDatosLocalesPrecios(JSONObject response, SyncResult syncResult) {

        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.PRECIOS_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Precios[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Precios[].class);
        List<Precios> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Precios> expenseMap = new HashMap<>();
        for (Precios e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractPrecios.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_PRECIOS, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        String producto;
        String segmento;
        String marca;
        String categoria;
        String subcategoria;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;

            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            producto = c.getString(Projection.COLUMNA_PRODUCTO);
            segmento = c.getString(Projection.COLUMNA_SEGMENTO);
            marca = c.getString(Projection.COLUMNA_MARCA);
            categoria = c.getString(Projection.COLUMNA_CATEGORIA);
            subcategoria = c.getString(Projection.COLUMNA_SUBCATEGORIA);

            Precios match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractPrecios.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                boolean b = match.producto != null && !match.producto.equals(producto);
                boolean b1 = match.segmento != null && !match.segmento.equals(segmento);
                boolean b2 = match.marca != null && !match.marca.equals(marca);
                boolean b3 = match.categoria != null && !match.categoria.equals(categoria);
                boolean b4 = match.subcategoria != null && !match.subcategoria.equals(subcategoria);

                if (b || b1 || b2 || b3 || b4) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContractPrecios.Columnas.PRODUCTO, match.producto)
                            .withValue(ContractPrecios.Columnas.SEGMENTO, match.segmento)
                            .withValue(ContractPrecios.Columnas.MARCA, match.marca)
                            .withValue(ContractPrecios.Columnas.CATEGORIA, match.categoria)
                            .withValue(ContractPrecios.Columnas.SUBCATEGORIA, match.subcategoria)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractPrecios.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Precios e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Precios en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractPrecios.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA, e.id) //Esta clave importante!!! ForeignKey del servidor
                    .withValue(ContractPrecios.Columnas.PRODUCTO, e.producto)
                    .withValue(ContractPrecios.Columnas.SEGMENTO, e.segmento)
                    .withValue(ContractPrecios.Columnas.MARCA, e.marca)
                    .withValue(ContractPrecios.Columnas.CATEGORIA, e.categoria)
                    .withValue(ContractPrecios.Columnas.SUBCATEGORIA, e.subcategoria)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractPrecios.CONTENT_URI,
                    null,
                    false);
            Log.i(TAG, "Sincronización de tabla Precios finalizada.");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Precios");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Precios");
        }

    }

    /* CAMPOS X MODULOS */
    public void realizarSincronizacionLocalCamposPorModulos(final SyncResult syncResult, String operatorName){
        Log.i(TAG, "Realizando Sincronizacion Local de Campos x Modulos." + operatorName);
        String operator = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operator", operator);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_CAMPOS_X_MODULOS, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetCamposPorModulos(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetCamposPorModulos(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesCamposPorModulos(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje+"Campos x modulos");
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesCamposPorModulos(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.CAMPOSPORMODULOSRESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_campos_x_modulos[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_campos_x_modulos[].class);
        List<Base_campos_x_modulos> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_campos_x_modulos> expenseMap = new HashMap<>();
        for (Base_campos_x_modulos e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractCamposPorModulos.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_CAMPOS_POR_MODULOS, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String modulo;
        String campo;
        String obligatorio;
        String cuenta;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            modulo = c.getString(Projection.MODULO_C);
            campo = c.getString(Projection.CAMPO_C);
            obligatorio = c.getString(Projection.OBLIGATORIO_C);
            cuenta = c.getString(Projection.CUENTACM_C);

            Base_campos_x_modulos match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractCamposPorModulos.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1=match.modulo !=null && !match.modulo.equals(modulo);
                boolean b2=match.campo !=null && !match.campo.equals(campo);
                boolean b3=match.obligatorio !=null && !match.obligatorio.equals(obligatorio);
                boolean b4=match.cuenta !=null && !match.cuenta.equals(cuenta);

                if (b1 || b2 || b3 || b4) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractCamposPorModulos.Columnas.MODULO,match.modulo)
                            .withValue(ContractCamposPorModulos.Columnas.CAMPO,match.campo)
                            .withValue(ContractCamposPorModulos.Columnas.OBLIGATORIO,match.obligatorio)
                            .withValue(ContractCamposPorModulos.Columnas.CUENTA,match.cuenta)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractCamposPorModulos.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_campos_x_modulos e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_campos_modulos en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractCamposPorModulos.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractCamposPorModulos.Columnas.MODULO,e.modulo)
                    .withValue(ContractCamposPorModulos.Columnas.CAMPO,e.campo)
                    .withValue(ContractCamposPorModulos.Columnas.OBLIGATORIO,e.obligatorio)
                    .withValue(ContractCamposPorModulos.Columnas.CUENTA,e.cuenta)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractCamposPorModulos.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base Campos x modulos finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_CAMPOS_X_MODULOS);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Base Campos x modulos");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Campos x modulos");
        }

    }


    /*PROMOCIONAL VENTAS*/
    public void realizarSincronizacionLocalPromocionalVentas(final SyncResult syncResult, String operatorName){
        Log.i(TAG, "Realizando Sincronizacion Local de Promocional Ventas." + operatorName);
        String operator = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operator", operator);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_PROMOCIONAL_VENTAS, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetPromocionalVentas(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetPromocionalVentas(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesPromocionalVentas(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje+"Promocional ventas");
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesPromocionalVentas(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.PROMOCIONALVENTASESRESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_promocional_ventas[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_promocional_ventas[].class);
        List<Base_promocional_ventas> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_promocional_ventas> expenseMap = new HashMap<>();
        for (Base_promocional_ventas e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractPromocionalVentas.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_PROMOCIONAL_VENTAS, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String cadena;
        String promocional;
        String cuenta;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            cadena = c.getString(Projection.CADENAPE);
            promocional = c.getString(Projection.PROMOCIONALPE);
            cuenta = c.getString(Projection.CUENTAPE);

            Base_promocional_ventas match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractPromocionalVentas.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1=match.cadena !=null && !match.cadena.equals(cadena);
                boolean b2=match.promocional !=null && !match.promocional.equals(promocional);
                boolean b3=match.cuenta !=null && !match.cuenta.equals(cuenta);

                if (b1 || b2 || b3) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractPromocionalVentas.Columnas.CADENA,match.cadena)
                            .withValue(ContractPromocionalVentas.Columnas.PROMOCIONAL,match.promocional)
                            .withValue(ContractPromocionalVentas.Columnas.CUENTA,match.cuenta)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractPromocionalVentas.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_promocional_ventas e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_promocional_ventas en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractPromocionalVentas.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractPromocionalVentas.Columnas.CADENA,e.cadena)
                    .withValue(ContractPromocionalVentas.Columnas.PROMOCIONAL,e.promocional)
                    .withValue(ContractPromocionalVentas.Columnas.CUENTA,e.cuenta)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractPromocionalVentas.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base_promociones_ventas finalizada");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_PROMO_VENTAS);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla promocional ventas");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Promocional ventas");
        }

    }


    /*
     *  INSERTS
     */

    private void realizarSincronizacionRemotaInsertFlooring() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(InsertFlooring.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(InsertFlooring.CONTENT_URI, Projection.PROJECTION_INSERTFLOORING);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Inventario Sugerido en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_FLOORING,
                                UtilidadesFlooring.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertFlooring(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertFlooring(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(InsertFlooring.CONTENT_URI, InsertFlooring.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(), "Datos almacenados en el servidor", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local " + idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //MODULO ACTUAL DE VENTAS 13/06/2025
    private void realizarSincronizacionRemotaInsertVentas2() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertVentas2.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertVentas2.CONTENT_URI, Projection.PROJECTION_INSERT_VENTA2);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Ventas2 en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_VENTAS2,
                                UtilidadesVentas2.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertVentas2(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                            Log.i(TAG, "SyncAdapterSubida: "+ error.getMessage());
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertVentas2(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertVentas2.CONTENT_URI, ContractInsertVentas2.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(), "Datos almacenados en el servidor", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local " + idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void realizarSincronizacionRemotaInsertInicial() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertInicial.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertInicial.CONTENT_URI, Projection.PROJECTION_INSERTINICIAL);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Precios en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_INICIAL,
                                UtilidadesInicial.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertInicial(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertInicial(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertInicial.CONTENT_URI, ContractInsertInicial.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(), "Datos almacenados en el servidor", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local " + idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    //INSERT MUESTRAS MEDICAS -- mpin
    private void realizarSincronizacionRemotaInsertMuestras() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertMuestras.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertMuestras.CONTENT_URI, Projection.PROJECTION_INSERTMUESTRAS);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Muestras en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_MUESTRAS,
                                UtilidadesMuestras.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertMuestras(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertMuestras(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertMuestras.CONTENT_URI, ContractInsertMuestras.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(), "Datos almacenados en el servidor", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local " + idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //INSERT MUESTRAS PROBADORES -- mpin
    private void realizarSincronizacionRemotaInsertProbadores() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertProbadores.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertProbadores.CONTENT_URI, Projection.PROJECTION_INSERT_PROBADORES);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Probadores en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_PROBADORES,
                                UtilidadesProbadores.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertProbadores(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertProbadores(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertProbadores.CONTENT_URI, ContractInsertProbadores.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(), "Datos almacenados en el servidor", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local " + idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void realizarSincronizacionRemotaInsertPrecio() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertPrecios.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertPrecios.CONTENT_URI, Projection.PROJECTION_INSERTPRECIO);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Precios en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_PRECIO,
                                UtilidadesPrecios.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertPrecios(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertPrecios(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertPrecios.CONTENT_URI, ContractInsertPrecios.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(), "Datos almacenados en el servidor", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local " + idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void realizarSincronizacionRemotaInsertCanjes() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertCanjes.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertCanjes.CONTENT_URI, Projection.PROJECTION_INSERT_CANJES);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Canjes en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_CANJES,
                                UtilidadesCanjes.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertCanjes(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" ;//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertCanjes(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertCanjes.CONTENT_URI, ContractInsertCanjes.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * INSERT MATERIALES RECIBIDOS
     */
    private void realizarSincronizacionRemotaInsertMaterialesRecibidos() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertMaterialesRecibidos.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertMaterialesRecibidos.CONTENT_URI, Projection.PROJECTION_INSERT_MATERIALES_RECIBIDOS);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Materiales Recibidos en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_MATERIALES_RECIBIDOS,
                                UtilidadesMaterialesRecibidos.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertMaterialesRecibidos(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" ;//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertMaterialesRecibidos(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertMaterialesRecibidos.CONTENT_URI, ContractInsertMaterialesRecibidos.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * INSERT EJECUCION MATERIALES
     */
    private void realizarSincronizacionRemotaInsertEjecucionMateriales() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertEjecucionMateriales.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertEjecucionMateriales.CONTENT_URI, Projection.PROJECTION_INSERT_EJECUCION_MATERIALES);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Ejecucion de Materiales en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_EJECUCION_MATERIALES,
                                UtilidadesEjecucionMateriales.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertEjecucionMateriales(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" ;//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertEjecucionMateriales(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertEjecucionMateriales.CONTENT_URI, ContractInsertEjecucionMateriales.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**REPOSITORIO USER*/

    public void realizarSincronizacionLocalUser(final SyncResult syncResult, String operatorName){
        Log.i(TAG, "Realizando Sincronizacion Local de Base_User." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_USER, jobject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetUser(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetUser(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesUser(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesUser(JSONObject response, SyncResult syncResult) {

        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.USUARIOSRESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_user[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_user[].class);
        List<Base_user> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_user> expenseMap = new HashMap<>();
        for (Base_user e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractUser.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_USER, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String user;
        String mercaderista;
        String device_id;
        String color;
        String status;
        /*String manufacturer;
        String format;*/

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;

            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            user = c.getString(Projection.USER);
            mercaderista = c.getString(Projection.U_MERCADERISTA);
            device_id = c.getString(Projection.DEVICE_ID_USER);
            color = c.getString(Projection.COLOR);
            status = c.getString(Projection.U_STATUS);
            /*manufacturer = c.getString(Projection.MANUFACTURER);
            format = c.getString(Projection.FORMAT_PRODUCTO);*/

            Base_user match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractUser.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1=match.user!=null && !match.user.equals(user);
                boolean b2=match.mercaderista !=null && !match.mercaderista.equals(mercaderista);
                boolean b3=match.device_id !=null && !match.device_id.equals(device_id);
                boolean b4=match.color !=null && !match.color.equals(color);
                boolean b5 = match.status != null && !match.status.equals(status);
                /*boolean b12 = match.manufacturer != null && !match.manufacturer.equals(manufacturer);
                boolean b13 = match.format != null && !match.format.equals(format);*/

                if (b1 || b2 || b3 || b4 || b5 ){//  {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractUser.Columnas.USER,match.user)
                            .withValue(ContractUser.Columnas.MERCADERISTA,match.mercaderista)
                            .withValue(ContractUser.Columnas.DEVICE_ID,match.device_id)
                            .withValue(ContractUser.Columnas.COLOR,match.color)
                            .withValue(ContractUser.Columnas.STATUS, match.status)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractUser.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_user e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_User en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractUser.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractUser.Columnas.USER, e.user)
                    .withValue(ContractUser.Columnas.MERCADERISTA, e.mercaderista)
                    .withValue(ContractUser.Columnas.DEVICE_ID, e.device_id)
                    .withValue(ContractUser.Columnas.COLOR, e.color)
                    .withValue(ContractUser.Columnas.STATUS, e.status)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractUser.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base_User finalizada.");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_USER);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Base_User");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Base_User");
        }

    }


    /**********************************************************************/
    //REPOSITORIO JUSTIFICACION NO VISITA PDV
    public void realizarSincronizacionLocalJustificacion(final SyncResult syncResult, String operatorName){
        Log.i(TAG, "Realizando Sincronizacion Local de Justificacion." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_JUSTIFICACION, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetJustificacion(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetJustificacion(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    Log.i("EXITO WS JUSTIFICACIONES", "SI");
                    actualizarDatosLocalesJustificacion(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesJustificacion(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.JUSTIFICACIONESRESULT);
            Log.i("Sync Adapter", "resultado del ws" + loginResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_justificacion[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_justificacion[].class);
        List<Base_justificacion> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_justificacion> expenseMap = new HashMap<>();
        for (Base_justificacion e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractJustificacion.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_JUSTIFICACION, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String justificacion;


        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            justificacion = c.getString(Projection.JUSTIFICACION);


            Base_justificacion match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractJustificacion.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1=match.justificacion!=null && !match.justificacion.equals(justificacion);

                if (b1) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractJustificacion.Columnas.JUSTIFICACION,match.justificacion)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractJustificacion.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();
        Log.i(TAG, "ANTES DEL FOR JUSTIFICACION");
        // Insertar items resultantes
        for (Base_justificacion e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_justificacion en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractJustificacion.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractJustificacion.Columnas.JUSTIFICACION,e.justificacion)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractJustificacion.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base_Justificacion finalizada.");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_JUST);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Justificación");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Justificaciones");
        }

    }


    //REPOSITORIO TIPO INVENTARIO
    public void realizarSincronizacionLocalTipoInventario(final SyncResult syncResult, String operatorName){
        Log.i(TAG, "Realizando Sincronizacion Local de TipoInventario." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_TIPO_INVENTARIO, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetTipoInventario(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetTipoInventario(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    Log.i("EXITO WS TIPOINVENTARIO", "SI");
                    actualizarDatosLocalesTipoInventario(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesTipoInventario(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.TIPOINVENTARIOESRESULT);
            Log.i("Sync Adapter", "resultado del ws" + loginResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_tipo_inventario[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_tipo_inventario[].class);
        List<Base_tipo_inventario> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_tipo_inventario> expenseMap = new HashMap<>();
        for (Base_tipo_inventario e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractTipoInventario.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_TIPO_INVENTARIO, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String causales;


        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            causales = c.getString(Projection.CAUSALESTIPOINV);


            Base_tipo_inventario match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractTipoInventario.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1=match.causales!=null && !match.causales.equals(causales);

                if (b1) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractTipoInventario.Columnas.CAUSALES,match.causales)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractTipoInventario.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();
        Log.i(TAG, "ANTES DEL FOR TIPOINVENTARIO");
        // Insertar items resultantes
        for (Base_tipo_inventario e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_tipo_inventario en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractTipoInventario.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractTipoInventario.Columnas.CAUSALES,e.causales)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractTipoInventario.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla TipoInventario finalizada.");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_JUST);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla TipoInventario");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "TipoInventario");
        }

    }


    //REPOSITORIO PRODUCTO SECUNDARIO PARA ONPACKS
    public void realizarSincronizacionLocalProdSecundarios(final SyncResult syncResult, String operatorName) {
        Log.i(TAG, "Realizando Sincronizacion Local de Productos Secundarios." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_PRODUCTOS_SECUNDARIOS, jobject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetProdSecundarios(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                } else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetProdSecundarios(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocalesProdSecundarios(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesProdSecundarios(JSONObject response, SyncResult syncResult) {

        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.PROD_SECUNDARIOS_RESULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_productos_secundarios[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_productos_secundarios[].class);
        List<Base_productos_secundarios> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_productos_secundarios> expenseMap = new HashMap<>();
        for (Base_productos_secundarios e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractProductosSecundarios.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_PROD_SECUNDARIOS, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String sector;
        String categoria;
        String subcategoria;
        String segmento;
        String presentacion;
        String variante1;
        String variante2;
        String contenido;
        String sku;
        String marca;
        String fabricante;
        String pvp;
        String cadenas;
        String foto;
        String plataforma;
        /*String manufacturer;
        String format;*/

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;

            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            sector = c.getString(Projection.SECTOR_S);
            categoria = c.getString(Projection.CATEGORY_S);
            subcategoria = c.getString(Projection.SUBCATEGORIA_S);
            segmento = c.getString(Projection.SEGMENTO_S);
            presentacion = c.getString(Projection.PRESENTACION_S);
            variante1 = c.getString(Projection.VARIANTE1_S);
            variante2 = c.getString(Projection.VARIANTE2_S);
            contenido = c.getString(Projection.CONTENIDO_S);
            sku = c.getString(Projection.SKU_S);
            marca = c.getString(Projection.MARCA_S);
            fabricante = c.getString(Projection.FABRICANTE_S);
            pvp = c.getString(Projection.PVP_S);
            cadenas = c.getString(Projection.CADENAS_S);
            foto = c.getString(Projection.FOTO_S);
            plataforma = c.getString(Projection.PLATAFORMA_S);
            /*manufacturer = c.getString(Projection.MANUFACTURER);
            format = c.getString(Projection.FORMAT_PRODUCTO);*/

            Base_productos_secundarios match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractProductosSecundarios.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1 = match.sector != null && !match.sector.equals(sector);
                boolean b2 = match.categoria != null && !match.categoria.equals(categoria);
                boolean b3 = match.subcategoria != null && !match.subcategoria.equals(subcategoria);
                boolean b4 = match.segmento != null && !match.segmento.equals(segmento);
                boolean b5 = match.presentacion != null && !match.presentacion.equals(presentacion);
                boolean b6 = match.variante1 != null && !match.variante1.equals(variante1);
                boolean b7 = match.variante2 != null && !match.variante2.equals(variante2);
                boolean b9 = match.contenido != null && !match.contenido.equals(contenido);
                boolean b8 = match.sku != null && !match.sku.equals(sku);
                boolean b10 = match.marca != null && !match.marca.equals(marca);
                boolean b11 = match.fabricante != null && !match.fabricante.equals(fabricante);
                boolean b12 = match.pvp != null && !match.pvp.equals(pvp);
                boolean b13 = match.cadenas != null && !match.cadenas.equals(cadenas);
                boolean b14 = match.foto != null && !match.foto.equals(foto);
                /*boolean b15 = match.plataforma != null && !match.plataforma.equals(plataforma);
                boolean b12 = match.manufacturer != null && !match.manufacturer.equals(manufacturer);
                boolean b13 = match.format != null && !match.format.equals(format);*/

                if (b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10 || b11 || b12 || b13 || b14) {// || b12 || b13) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,match.codigo_producto)
                            .withValue(ContractProductosSecundarios.Columnas.SECTOR, match.sector)
                            .withValue(ContractProductosSecundarios.Columnas.CATEGORY, match.categoria)
                            .withValue(ContractProductosSecundarios.Columnas.SUBCATEGORIA, match.subcategoria)
                            .withValue(ContractProductosSecundarios.Columnas.SEGMENTO, match.segmento)
                            .withValue(ContractProductosSecundarios.Columnas.PRESENTACION, match.presentacion)
                            .withValue(ContractProductosSecundarios.Columnas.VARIANTE1, match.variante1)
                            .withValue(ContractProductosSecundarios.Columnas.VARIANTE2, match.variante2)
                            .withValue(ContractProductosSecundarios.Columnas.CONTENIDO, match.contenido)
                            .withValue(ContractProductosSecundarios.Columnas.SKU, match.sku)
                            .withValue(ContractProductosSecundarios.Columnas.MARCA, match.marca)
                            .withValue(ContractProductosSecundarios.Columnas.FABRICANTE, match.fabricante)
                            .withValue(ContractProductosSecundarios.Columnas.PVP, match.pvp)
                            .withValue(ContractProductosSecundarios.Columnas.CADENAS, match.cadenas)
                            .withValue(ContractProductosSecundarios.Columnas.FOTO, match.foto)
                            /*.withValue(ContractProductosSecundarios.Columnas.MANUFACTURER, match.manufacturer)
                            .withValue(ContractProductosSecundarios.Columnas.FORMAT,match.format)*/
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractProductosSecundarios.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_productos_secundarios e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_Productos_Secundarios en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractProductosSecundarios.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA, e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractProductosSecundarios.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractProductosSecundarios.Columnas.SECTOR, e.sector)
                    .withValue(ContractProductosSecundarios.Columnas.CATEGORY, e.categoria)
                    .withValue(ContractProductosSecundarios.Columnas.SUBCATEGORIA, e.subcategoria)
                    .withValue(ContractProductosSecundarios.Columnas.SEGMENTO, e.segmento)
                    .withValue(ContractProductosSecundarios.Columnas.PRESENTACION, e.presentacion)
                    .withValue(ContractProductosSecundarios.Columnas.VARIANTE1, e.variante1)
                    .withValue(ContractProductosSecundarios.Columnas.VARIANTE2, e.variante2)
                    .withValue(ContractProductosSecundarios.Columnas.CONTENIDO, e.contenido)
                    .withValue(ContractProductosSecundarios.Columnas.SKU, e.sku)
                    .withValue(ContractProductosSecundarios.Columnas.MARCA, e.marca)
                    .withValue(ContractProductosSecundarios.Columnas.FABRICANTE, e.fabricante)
                    .withValue(ContractProductosSecundarios.Columnas.PVP, e.pvp)
                    .withValue(ContractProductosSecundarios.Columnas.CADENAS, e.cadenas)
                    .withValue(ContractProductosSecundarios.Columnas.FOTO, e.foto)
                    /*.withValue(ContractProductosSecundarios.Columnas.MANUFACTURER, e.manufacturer)
                    .withValue(ContractProductosSecundarios.Columnas.FORMAT,e.format)*/
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractProductosSecundarios.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de BasePharmaValue finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base_Productos Secundarios finalizada.");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_PROD_SEC);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Productos Secundarios");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Productos Secundarios");
        }

    }

    //REPOSITORIO LINK DE ONEDRIVE
    public void realizarSincronizacionLocalLinkOnedrive(final SyncResult syncResult, String operatorName){
        Log.i(TAG, "Realizando Sincronizacion Local de Link Onedrive." + operatorName);
        String operators = operatorName;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("operators", operators);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);
        //GET METHOD
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constantes.GET_LINK_ONEDRIVE, jobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuestaGetLinkOnedrive(response, syncResult);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError) {
                                    enviarBroadcast(true, Mensajes.TIME_OUT);
                                } else if (error instanceof NoConnectionError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.NO_RED);
                                }else if (error instanceof AuthFailureError) {
                                    //TODO
                                } else if (error instanceof ServerError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.SEVER_ERROR);
                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    enviarBroadcast(true, Mensajes.RED_ERROR);
                                } else if (error instanceof ParseError) {
                                    //TODO
                                }
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void procesarRespuestaGetLinkOnedrive(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    Log.i("EXITO WS LINK ONEDRIVE", "SI");
                    actualizarDatosLocalesLinkOnedrive(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    enviarBroadcast(true, mensaje);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesLinkOnedrive(JSONObject response, SyncResult syncResult) {
        JSONArray loginResult = null;

        try {
            // Obtener array "gastos"
            loginResult = response.getJSONArray(Constantes.LINK_ONEDRIVE_RESULT);
            Log.i("Sync Adapter", "resultado del ws" + loginResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Base_link_oneDrive[] res = gson.fromJson(loginResult != null ? loginResult.toString() : null, Base_link_oneDrive[].class);
        List<Base_link_oneDrive> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Tabla hash para recibir los datos entrantes
        HashMap<String, Base_link_oneDrive> expenseMap = new HashMap<>();
        for (Base_link_oneDrive e : data) {
            expenseMap.put(e.id, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractLinkOnedrive.CONTENT_URI;
        String select = Constantes.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, Projection.PROJECTION_LINK_ONEDRIVE, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        //String codigo_producto;
        String modulo;
        String link;


        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getString(Projection.COLUMNA_ID_REMOTA);
            //codigo_producto = c.getString(Projection.CODIGO_PRODUCTO);
            modulo = c.getString(Projection.MODULO);
            link = c.getString(Projection.LINK);

            Base_link_oneDrive match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractLinkOnedrive.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                //boolean b=match.codigo_producto!=null && !match.codigo_producto.equals(codigo_producto);
                boolean b1=match.modulo!=null && !match.modulo.equals(modulo);
                boolean b2=match.link!=null && !match.link.equals(link);

                if (b1 || b2) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContractLinkOnedrive.Columnas.MODULO,match.modulo)
                            .withValue(ContractLinkOnedrive.Columnas.LINK,match.link)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractLinkOnedrive.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Base_link_oneDrive e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de Tabla Base_link_onedrive en Base Local: " + e.id);
            ops.add(ContentProviderOperation.newInsert(ContractLinkOnedrive.CONTENT_URI)
                    .withValue(Constantes.ID_REMOTA , e.id) //Esta clave importante!!! ForeignKey del servidor
                    //.withValue(ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,e.codigo_producto)
                    .withValue(ContractLinkOnedrive.Columnas.MODULO,e.modulo)
                    .withValue(ContractLinkOnedrive.Columnas.LINK,e.link)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(Constantes.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractLinkOnedrive.CONTENT_URI,
                    null,
                    false);
//            Toast.makeText(getContext(),"Sincronización de Base_pharma_value finalizada.",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Sincronización de tabla Base_link_onedrive finalizada.");
            enviarBroadcast(true, Mensajes.SYNC_FINALIZADA_LINK);

        } else {
            Log.i(TAG, "No se requiere sincronización de tabla Link Onedrive");
            enviarBroadcast(true, Mensajes.SYNC_NOREQUERIDA + "Link Onedrive");
        }

    }


    // MCI
    private void realizarSincronizacionRemotaInsertMCI() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertMCIPdv.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertMCIPdv.CONTENT_URI, Projection.PROJECTION_INSERT_MCI);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de MCI en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_MCI,
                                UtilidadesMCI.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertMCI(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" ;//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertMCI(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertMCIPdv.CONTENT_URI, ContractInsertMCIPdv.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    //PRODUCTOS A CADUCAR
    private void realizarSincronizacionRemotaInsertProdCad() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertProdCaducar.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertProdCaducar.CONTENT_URI, Projection.PROJECTION_INSERT_PROD_CADUCAR);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Precios en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_PROD_CAD,
                                UtilidadesProdCad.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertProdCad(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" ;//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertProdCad(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertProdCaducar.CONTENT_URI, ContractInsertProdCaducar.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //SUGERIDOS

    //PRODUCTOS A CADUCAR
    private void realizarSincronizacionRemotaInsertSugeridos() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertSugeridos.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertSugeridos.CONTENT_URI, Projection.PROJECTION_INSERT_SUGERIDOS);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Sugeridos en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_SUGERIDOS,
                                UtilidadesSugeridos.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertSugeridos(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" ;//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertSugeridos(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertSugeridos.CONTENT_URI, ContractInsertSugeridos.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    /*------------------------------------------------------------------------------------------------------------------------*/


    //ROTACION

    private void realizarSincronizacionRemotaInsertRotacion() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertRotacion.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertRotacion.CONTENT_URI, Projection.PROJECTION_INSERT_ROTACION);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Rotacion en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_ROTACION,
                                UtilidadesRotacion.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertRotacion(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" ;//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertRotacion(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertRotacion.CONTENT_URI, ContractInsertRotacion.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    private void realizarSincronizacionRemotaInsertPacks() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertPacks2.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertPacks2.CONTENT_URI, Projection.PROJECTION_INSERT_PACKS);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Precios en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_PACKS,
                                UtilidadesPacks.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertPacks(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" ;//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertPacks(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertPacks2.CONTENT_URI, ContractInsertPacks2.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*INSERT TAREAS*/
    private void realizarSincronizacionRemotaInsertTareas() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertTareas.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertTareas.CONTENT_URI, Projection.PROJECTION_INSERT_TAREAS);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Tareas en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_TAREAS,
                                UtilidadesTareas.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertTareas(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" ;//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertTareas(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertTareas.CONTENT_URI, ContractInsertTareas.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*--------------------------------------------------------------------------*/
    private void realizarSincronizacionRemotaInsertImpulso() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertImpulso.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertImpulso.CONTENT_URI, Projection.PROJECTION_INSERT_IMPULSO);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Precios en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_IMPULSO,
                                UtilidadesImpulso.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertImpulso(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" ;//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertImpulso(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertImpulso.CONTENT_URI, ContractInsertImpulso.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void realizarSincronizacionRemotaInsertCodificados() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertCodificados.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertCodificados.CONTENT_URI, Projection.PROJECTION_INSERT_CODIFICADOS);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Precios en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_CODIFICADOS,
                                UtilidadesCodificados.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertCodificados(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" ;//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertCodificados(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertCodificados.CONTENT_URI, ContractInsertCodificados.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void realizarSincronizacionRemotaInsertEvidencias() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertEvidencias.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertEvidencias.CONTENT_URI, Projection.PROJECTION_INSERT_EVIDENCIAS);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Fotografico en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_EVIDENCIA,
                                UtilidadesEvidencias.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertEvidencias(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";// + getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    /**
     * Procesa los diferentes tipos de respuesta obtenidos del servidor
     *
     * @param response Respuesta en subchannel Json
     */
    public void procesarRespuestaInsertEvidencias(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertEvidencias.CONTENT_URI, ContractInsertEvidencias.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void realizarSincronizacionRemotaInsertTracking() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertTracking.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertTracking.CONTENT_URI, Projection.PROJECTION_INSERT_TRACKING);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Tracking en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_TRACKING,
                                UtilidadesTracking.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertTracking(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";// + getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertTracking(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertTracking.CONTENT_URI, ContractInsertTracking.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*INTERT CONVENIOS*/
    private void realizarSincronizacionRemotaInsertConvenios() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertConvenios.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertConvenios.CONTENT_URI, Projection.PROJECTION_INSERT_CONVENIOS);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Convenios en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_CONVENIOS,
                                UtilidadesConvenios.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertConvenios(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" ;//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertConvenios(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertConvenios.CONTENT_URI, ContractInsertConvenios.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*PREGUNTAS*/
    private void realizarSincronizacionRemotaInsertPreguntas() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertPreguntas.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertPreguntas.CONTENT_URI, Projection.PROJECTION_INSERT_PREGUNTAS);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Precios en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_PREGUNTAS,
                                UtilidadesPreguntas.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertPreguntas(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" ;//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertPreguntas(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertPreguntas.CONTENT_URI, ContractInsertPreguntas.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /* RESULTADO PREGUNTAS */

    private void realizarSincronizacionRemotaInsertResultadoPreguntas() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertResultadoPreguntas.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertResultadoPreguntas.CONTENT_URI, Projection.PROJECTION_RESULTADO_PREGUNTAS);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Precios en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_RESULTADO_PREGUNTAS,
                                UtilidadesResultadoPreguntas.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertResultadoPreguntas(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" ;//+ getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertResultadoPreguntas(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertResultadoPreguntas.CONTENT_URI, ContractInsertResultadoPreguntas.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void realizarSincronizacionRemotaInsertPromo() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertPromocion.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertPromocion.CONTENT_URI, Projection.PROJECTION_INSERTPROMO);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Precios en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_PROMO,
                                UtilidadesPromocion.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertPromo(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";// + getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }


    public void procesarRespuestaInsertPromo(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertPromocion.CONTENT_URI, ContractInsertPromocion.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void realizarSincronizacionRemotaInsertImplementacion() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertImplementacion.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertImplementacion.CONTENT_URI, Projection.PROJECTION_INSERTIMPLEM);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Precios en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_IMPLEM,
                                UtilidadesImplementacion.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertImplementacion(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";// + getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }


    public void procesarRespuestaInsertImplementacion(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertImplementacion.CONTENT_URI, ContractInsertImplementacion.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void realizarSincronizacionRemotaInsertValores() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertValores.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertValores.CONTENT_URI, Projection.PROJECTION_INSERTVALORES);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Precios en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_VALORES,
                                UtilidadesValores.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertValores(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";// + getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }


    public void procesarRespuestaInsertValores(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertValores.CONTENT_URI, ContractInsertValores.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private void realizarSincronizacionRemotaInsertPDV() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertPdv.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertPdv.CONTENT_URI, Projection.PROJECTION_INSERTPDV);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de PDV en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_PDV,
                                UtilidadesPdv.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertPDV(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" + getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertPDV(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertPdv.CONTENT_URI, ContractInsertPdv.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private void realizarSincronizacionRemotaInsertAgotados() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertAgotados.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertAgotados.CONTENT_URI, Projection.PROJECTION_INSERTAGOTADOS);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Agotados en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_AGOTADOS,
                                UtilidadesAgotados.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertAgotados(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";// + getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertAgotados(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertAgotados.CONTENT_URI, ContractInsertAgotados.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void realizarSincronizacionRemotaInsertVenta() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertVenta.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertVenta.CONTENT_URI, Projection.PROJECTION_INSERTVENTA);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Agotados en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_VENTA,

                                UtilidadesVenta.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertVenta(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";// + getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertVenta(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertVenta.CONTENT_URI, ContractInsertVenta.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void realizarSincronizacionRemotaInsertExh() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertExh.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertExh.CONTENT_URI, Projection.PROJECTION_INSERTEXH);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Exh en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_EXH,
                                UtilidadesExh.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertExh(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";// + getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }



    /**
     * Procesa los diferentes tipos de respuesta obtenidos del servidor
     *
     * @param response Respuesta en subchannel Json
     */
    public void procesarRespuestaInsertExh(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertExh.CONTENT_URI, ContractInsertExh.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void realizarSincronizacionRemotaInsertFotografico() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertFotografico.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertFotografico.CONTENT_URI, Projection.PROJECTION_INSERTFOT);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Fotografico en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_FOTOGRAFICO,
                                UtilidadesFotografico.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertFotografico(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";// + getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }



    /**
     * Procesa los diferentes tipos de respuesta obtenidos del servidor
     *
     * @param response Respuesta en subchannel Json
     */
    public void procesarRespuestaInsertFotografico(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertFotografico.CONTENT_URI, ContractInsertFotografico.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*SHARE*/
    private void realizarSincronizacionRemotaInsertShare() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertShare.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertShare.CONTENT_URI, Projection.PROJECTION_INSERTSHARE);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Share en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_SHARE,
                                UtilidadesShare.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertShare(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";// + getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertShare(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertShare.CONTENT_URI, ContractInsertShare.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /*SHARE*/
    private void realizarSincronizacionRemotaInsertPDI() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertPDI.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertPDI.CONTENT_URI, Projection.PROJECTION_INSERT_PDI);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Share en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_PDI,
                                UtilidadesPDI.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertPDI(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";// + getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    public void procesarRespuestaInsertPDI(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertPDI.CONTENT_URI, ContractInsertPDI.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /*GPS*/
    private void realizarSincronizacionRemotaInsertGps() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertGps.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertGps.CONTENT_URI, Projection.PROJECTION_INSERT_GPS);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Gps en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_GPS,
                                UtilidadesGps.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertGps(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";// + getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }



    /**
     * Procesa los diferentes tipos de respuesta obtenidos del servidor
     *
     * @param response Respuesta en subchannel Json
     */
    public void procesarRespuestaInsertGps(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertGps.CONTENT_URI, ContractInsertGps.Columnas._ID, idRemota, idLocal);
                    enviarBroadcast(true, Mensajes.ON_SYNC_TRUE);
                    Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void realizarSincronizacionRemotaInsertGeo() {
        Log.i(TAG, "GEOREFERENCIA: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractInsertRastreo.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractInsertRastreo.CONTENT_URI, Projection.PROJECTION_INSERT_RASTREO);

        Log.i(TAG, "GEOREFERENCIA: Se encontraron " + c.getCount() + " registros sucios.");

        if (c.getCount() > 0) {
            try{
                while (c.moveToNext()) {
                    final int idLocal = c.getInt(Projection.COLUMNA_ID);

                    Log.i(TAG, "GEOREFERENCIA: Insertando datos en el servidor...");

                    VolleySingleton.getInstance(getContext()).addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.POST,
                                    Constantes.INSERTAR_GEO,
                                    UtilidadesRastreo.deCursorAJSONObject(c),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            procesarRespuestaInsertGeo(response, idLocal);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
//                                        Toast.makeText(getContext(),"Problema en el servidor: Datos almacenados localmente",Toast.LENGTH_SHORT).show();
                                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                                Log.i(TAG, "GEOREFERENCIA: Error Network Timeout");
                                                Log.i(TAG, "GEOREFERENCIA: Problema en respuesta del servidor...");
                                                Log.d(TAG, "Error Volley: " + error.getMessage());
                                            } else if (error instanceof AuthFailureError) {
                                                Log.i(TAG, "GEOREFERENCIA: Error AuthFailure");
                                                Log.i(TAG, "GEOREFERENCIA: Problema en respuesta del servidor...");
                                                Log.d(TAG, "Error Volley: " + error.getMessage());
                                            } else if (error instanceof ServerError) {
                                                Log.i(TAG, "GEOREFERENCIA: Error Server");
                                                Log.i(TAG, "GEOREFERENCIA: Problema en respuesta del servidor...");
                                                Log.d(TAG, "Error Volley: " + error.getMessage());
                                            } else if (error instanceof NetworkError) {
                                                Log.i(TAG, "GEOREFERENCIA: Error Network");
                                                Log.i(TAG, "GEOREFERENCIA: Problema en respuesta del servidor...");
                                                Log.d(TAG, "Error Volley: " + error.getMessage());
                                            } else if (error instanceof ParseError) {
                                                Log.i(TAG, "GEOREFERENCIA: Error Parse");
                                                Log.i(TAG, "GEOREFERENCIA: Problema en respuesta del servidor...");
                                                Log.d(TAG, "Error Volley: " + error.getMessage());
                                            }
                                        }
                                    }
                            ) {
                                @Override
                                public Map<String, String> getHeaders() {
                                    Map<String, String> headers = new HashMap<String, String>();
                                    headers.put("Content-Type", "application/json; charset=utf-8");
                                    headers.put("Accept", "application/json");
                                    return headers;
                                }

                                @Override
                                public String getBodyContentType() {
                                    return "application/json; charset=utf-8";// + getParamsEncoding();
                                }
                            }
                    );
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "GEOREFERENCIA: No se requiere sincronización: No hay datos en cola de sincronizacion");
        }
        c.close();
    }

    /**
     * Procesa los diferentes tipos de respuesta obtenidos del servidor
     * @param response Respuesta en subchannel Json
     */
    public void procesarRespuestaInsertGeo(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractInsertRastreo.CONTENT_URI, ContractInsertRastreo.Columnas._ID, idRemota, idLocal);
                    //Toast.makeText(getContext(),"Datos almacenados en el servidor",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "GEOREFERENCIA: Registros Insertados en el Servidor - LastId: "+idRemota);
                    break;

                case Constantes.FAILED:
                    //onResponse(false);
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void realizarSincronizacionRemotaInsertNot() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos a ser insertados en el servidor...");

        iniciarActualizacion(ContractNotificacion.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractNotificacion.CONTENT_URI, Projection.PROJECTION_NOTIFICACION);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de Gps en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_NOTIFICACION,
                                UtilidadesNotificacion.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertNot(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";// + getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    /**
     * Procesa los diferentes tipos de respuesta obtenidos del servidor
     *
     * @param response Respuesta en subchannel Json
     */
    public void procesarRespuestaInsertNot(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractNotificacion.CONTENT_URI, ContractNotificacion.Columnas._ID, idRemota, idLocal);
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void realizarSincronizacionRemotaInsertLog() {
        Log.i(TAG, "SyncAdapterSubida: Preparando datos de Log a ser insertados en el servidor...");

        iniciarActualizacion(ContractLog.CONTENT_URI);

        Cursor c = obtenerRegistrosSucios(ContractLog.CONTENT_URI, Projection.PROJECTION_LOG);

        Log.i(TAG, "SyncAdapterSubida: Se encontraron " + c.getCount() + " registros sucios.");


        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(Projection.COLUMNA_ID);

                Log.i(TAG, "SyncAdapterSubida: Insertando datos de log en el servidor...");

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERTAR_LOG,
                                UtilidadesLog.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsertLog(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(getContext(), "Datos no enviados, almacenados en el teléfono.", Toast.LENGTH_SHORT).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network Timeout");
                                        } else if (error instanceof AuthFailureError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error AuthFailure");
                                        } else if (error instanceof ServerError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Server");
                                        } else if (error instanceof NetworkError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Network");
                                        } else if (error instanceof ParseError) {
                                            Log.i(TAG, "SyncAdapterSubida: Error Parse");
                                        }
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";// + getParamsEncoding();
                            }
                        }
                );
            }
        } else {
            //Toast.makeText(getContext(),"No hay datos en cola de sincronizacion",Toast.LENGTH_SHORT).show();
//            StockActivity stockActivity = new StockActivity();
//            stockActivity.showToastFromBackground("No se requiere sincronización");
            Log.i(TAG, "SyncAdapterSubida: No se requiere sincronización: No hay datos en cola de sincronizacion");
            enviarBroadcast(true, Mensajes.SYNC_NODATOSCOLA);
        }
        c.close();
    }

    /**
     * Procesa los diferentes tipos de respuesta obtenidos del servidor
     *
     * @param response Respuesta en subchannel Json
     */
    public void procesarRespuestaInsertLog(JSONObject response, int idLocal) {
        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);

            switch (estado) {
                case Constantes.SUCCESS:
                    // Obtener identificador del nuevo registro creado en el servidor
                    String idRemota = response.getString(Constantes.LAST_ID);
                    Log.i(TAG, mensaje);
                    //Borrar datos sucios:
                    finalizarActualizacion(ContractLog.CONTENT_URI, ContractLog.Columnas._ID, idRemota, idLocal);
                    Log.i(TAG, "SyncAdapterSubida: Registros Insertados en el Servidor y en la Base Local "+idRemota);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
     * ***********************************************************************************************************************
     */


    /**
     * Cambia a estado "de sincronización" el registro que se acaba de insertar localmente
     */
    private void iniciarActualizacion(Uri ContentUri) {//Uri
        Uri uri = ContentUri;
        String selection = Constantes.PENDIENTE_INSERCION + "=? AND "
                + Constantes.ESTADO + "=?";
        String[] selectionArgs = new String[]{"1", Constantes.ESTADO_OK + ""};

        ContentValues v = new ContentValues();
        v.put(Constantes.ESTADO, Constantes.ESTADO_SYNC);

        int results = resolver.update(uri, v, selection, selectionArgs);
        Log.i(TAG, "SyncAdapterSubida: Registros puestos en cola de inserción: " + results);
    }

    /**
     * Limpia el registro que se sincronizó y le asigna la nueva id remota proveida
     * por el servidor
     *
     * @param idRemota id remota
     */
    private void finalizarActualizacion(Uri ContentUri, String ID, String idRemota, int idLocal) {
        Log.i(TAG, "SyncAdapterSubida:finalizando actualizacion");
        Uri uri = ContentUri;
        String selection =  ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(idLocal)};

        ContentValues v = new ContentValues();
        v.put(Constantes.PENDIENTE_INSERCION, "0");
        v.put(Constantes.ESTADO, Constantes.ESTADO_OK);
        v.put(Constantes.ID_REMOTA, idRemota);

        resolver.update(uri, v, selection, selectionArgs);
    }

    /**
     * Obtiene el registro que se acaba de marcar como "pendiente por sincronizar" y
     * con "estado de sincronización"
     * @return Cursor con el registro.
     */
    private Cursor obtenerRegistrosSucios(Uri ContentUri, String[] projection) {//Uri, projection
        Uri uri = ContentUri;
        String selection = Constantes.PENDIENTE_INSERCION + "=? AND "
                + Constantes.ESTADO + "=?";
        String[] selectionArgs = new String[]{"1", Constantes.ESTADO_SYNC + ""};

        return resolver.query(uri, projection, selection, selectionArgs, null);
    }

    private void enviarBroadcast(boolean estado, String mensaje) {
        Intent intentLocal = new Intent(Intent.ACTION_SYNC);
        intentLocal.putExtra(Mensajes.EXTRA_RESULTADO, estado);
        intentLocal.putExtra(Mensajes.EXTRA_MENSAJE, mensaje);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intentLocal);
    }

}