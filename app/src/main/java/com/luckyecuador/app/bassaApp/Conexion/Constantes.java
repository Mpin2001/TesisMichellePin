package com.luckyecuador.app.bassaApp.Conexion;

/**
 * Created by Lucky Ecuador on 29/06/2016.
 */
public class Constantes {

    /**
     * URLs del Web Service
     */

    private static final String URL = "https://webecuador-desarrollo.azurewebsites.net";

    //private static final String IP = "http://192.168.96.231";
    private static final String IP = "https://webecuador-desarrollo.azurewebsites.net";

    //OBTENER TODOS LOS PUNTOVENTAS, PRODUCTOs
    public static final String LOGIN = URL + "/App/AppBassa/Web/login.php";
    public static final String GET_PUNTOVENTAS = URL + "/App/AppBassa/Web/get_locales_rutero.php";
    public static final String GET_USER = URL + "/App/AppBassa/Web/get_usuarios.php";
    public static final String GET_FLOORING = URL + "/App/AppBassa/Web/get_productos2021.php";
    public static final String GET_PRODUCTOS_SECUNDARIOS = URL + "/App/AppBassa/Web/get_productos_onpacks.php";
    public static final String GET_LINK_ONEDRIVE = URL + "/App/AppBassa/Web/get_links.php";
    public static final String GET_JUSTIFICACION = URL + "/App/AppBassa/Web/get_justificacion.php";
    public static final String GET_TIPO_INVENTARIO = URL + "/App/AppBassa/Web/get_tipo_inventario.php";
    public static final String GET_PREGUNTAS = URL + "/App/AppBassa/Web/get_preguntas.php";
    public static final String GET_CAMPOS_X_MODULOS = URL + "/App/AppBassa/Web/get_campos_x_modulos.php";
    public static final String GET_PROMOCIONAL_VENTAS = URL + "/App/AppBassa/Web/get_promocional_ventas.php";


    public static final String GET_TESTS = URL + "/App/AppBassa/Web/get_tests.php";

    public static final String GET_TIPO_EXH = URL + "/App/AppBassa/Web/get_tipo_exhibiciones.php";

    public static final String GET_CATEGORIA_TIPO = URL + "/App/AppBassa/Web/get_categoria_tipo.php";

    public static final String GET_MOTIVOS_SUGERIDO = URL + "/App/AppBassa/Web/get_motivos_sugerido.php";
    public static final String GET_PROMOCIONES = URL + "/App/AppBassa/Web/get_promociones.php";
    public static final String GET_TIPO_VENTAS = URL + "/App/AppBassa/Web/get_tipo_ventas.php"; //MPIN
    public static final String GET_TIPO_IMPLEMENTACIONES = URL + "/App/AppBassa/Web/get_tipo_implementaciones.php"; //NEW mpin novedades
    public static final String GET_TIPO_ACTIVIDADES = URL + "/App/AppBassa/Web/get_tipo_actividades.php"; //NEW mpin  para modulo muestras medicas modificar
    public static final String GET_TIPO_UNIDADES = URL + "/App/AppBassa/Web/get_tipo_unidades.php"; //NEW mpin CAMBIAR - cambiado
    public static final String GET_TIPO_REGISTRO = URL + "/App/AppBassa/Web/get_tipo_registro.php"; //NEW mpin CAMBIAR -cambiado
    public static final String GET_MODULO_ROLES = URL + "/App/AppBassa/Web/get_modulo_roles.php"; //NEW mpin CAMBIAR -
    public static final String GET_ROTACION = URL + "/App/AppBassa/Web/get_rotacion.php";
    public static final String GET_TAREAS = URL + "/App/AppBassa/Web/get_tareas.php";
    public static final String GET_POPSUGERIDO = URL + "/App/AppBassa/Web/get_pop_sugerido.php";
    public static final String GET_PRIORITARIO = URL + "/App/AppBassa/Web/get_prioritario.php";
    public static final String GET_COMBO_CANJES = URL + "/App/AppBassa/Web/get_combos_canjes.php";
    public static final String GET_CAUSALES_MCI = URL + "/App/AppBassa/Web/get_causales_mci.php";
    public static final String GET_CAUSALES_OSA = URL + "/App/AppBassa/Web/get_causales_osa.php";
    public static final String GET_MATERIALES_ALERTAS = URL + "/App/AppBassa/Web/get_materiales_alertas.php";
    public static final String GET_PDI = URL + "/App/AppBassa/Web/get_pdi.php";
    public static final String GET_TRACKING = URL + "/App/AppBassa/Web/get_tracking.php";
    public static final String GET_CONVENIOS = URL + "/App/AppBassa/Web/get_convenios.php";

    //INSERTS
    public static final String INSERTAR_FLOORING = URL + "/App/AppBassa/Inserts/insert_inventario062025.php";
    public static final String INSERTAR_PRECIO = URL + "/App/AppBassa/Inserts/insert_precios122025.php"; //mpin new
    public static final String INSERTAR_MUESTRAS = URL + "/App/AppBassa/Inserts/insert_muestras.php"; //mpin new --- PARA MUESTRAS MEDICAS
    public static final String INSERTAR_PROBADORES = URL + "/App/AppBassa/Inserts/insert_probadores.php"; //mpin new --- PARA PROBADORES
    public static final String INSERTAR_EXH = URL + "/App/AppBassa/Inserts/insert_exhibicion112025.php"; //modificacion
    public static final String INSERTAR_VENTA = URL + "/App/AppBassa/Inserts/insert_ventampin.php";
    public static final String INSERTAR_VENTAS2 = URL + "/App/AppBassa/Inserts/insert_ventas2.php";
    public static final String INSERTAR_PROMO = URL + "/App/AppBassa/Inserts/insert_promocion022023.php";
    public static final String INSERTAR_SHARE = URL + "/App/AppBassa/Inserts/insert_share042024.php";
    public static final String INSERTAR_PDI = URL + "/App/AppBassa/Inserts/insert_pdi022023.php";
    public static final String INSERTAR_VALORES = URL + "/App/AppBassa/Inserts/insert_codificados022023.php";
    public static final String INSERTAR_GEO = URL + "/App/AppBassa/Inserts/insert_rastreo.php";
    public static final String INSERTAR_INICIAL = URL + "/App/AppBassa/Inserts/insert_inicial2018.php";
    public static final String INSERTAR_GPS = URL + "/App/AppBassa/Inserts/insert_registro08_2022.php";
    public static final String INSERTAR_IMPLEM = URL + "/App/AppBassa/Inserts/insert_implementacion.php";
    public static final String INSERTAR_AGOTADOS = URL + "/App/AppBassa/Inserts/insert_agotados2018.php";
    public static final String INSERTAR_ROTACION = URL + "/App/AppBassa/Inserts/insert_rotacion.php";
        public static final String INSERTAR_FOTOGRAFICO = URL + "/App/AppBassa/Inserts/insert_fotografico042024.php";

    public static final String INSERTAR_EVIDENCIA = URL + "/App/AppBassa/Inserts/insert_evidencias_10_2025.php";
    public static final String INSERTAR_PREGUNTAS = URL + "/App/AppBassa/Inserts/insert_preguntas112022.php";
    public static final String INSERTAR_TRACKING = URL + "/App/AppBassa/Inserts/insert_tracking.php";

    public static final String INSERTAR_RESULTADO_PREGUNTAS = URL + "/App/AppBassa/Inserts/insert_resultado_preguntas.php";

    public static final String INSERTAR_PROD_CAD = URL + "/App/AppBassa/Inserts/insert_prod_caducar.php";
    public static final String INSERTAR_PACKS = URL + "/App/AppBassa/Inserts/insert_packs052023.php";
    public static final String INSERTAR_IMPULSO = URL + "/App/AppBassa/Inserts/insert_impulso022023.php";
    public static final String INSERTAR_TAREAS = URL + "/App/AppBassa/Inserts/insert_tareas.php";
    public static final String INSERTAR_SUGERIDOS = URL + "/App/AppBassa/Inserts/insert_sugeridos_2022.php";
    public static final String INSERTAR_CANJES = URL + "/App/AppBassa/Inserts/insert_canjes_022023.php";
    public static final String INSERTAR_MCI = URL + "/App/AppBassa/Inserts/insert_mci.php";
    public static final String INSERTAR_MATERIALES_RECIBIDOS = URL + "/App/AppBassa/Inserts/insert_materiales_recibidos.php";
    public static final String INSERTAR_EJECUCION_MATERIALES = URL + "/App/AppBassa/Inserts/insert_ejecucion_materiales.php";
    public static final String INSERTAR_CODIFICADOS = URL + "/App/AppBassa/Inserts/insert_codificados26092023.php";
    public static final String INSERTAR_CONVENIOS = URL + "/App/AppBassa/Inserts/insert_convenios.php";
    public static final String INSERTAR_NOTIFICACION = URL + "/App/AppBassa/Inserts/insert_notificacion0622.php";
    public static final String INSERTAR_LOG = URL + "/App/AppBassa/Inserts/insert_log.php";
    public static final String INSERTAR_EVIDENCIAS = URL + "/App/AppBassa/Inserts/insert_evidencias.php";

    //MODIFICAR
    public static final String GET_PRECIOS = URL + "/App/CtaEpson/AppEpson/Web/epson_obtenerprecios.php";
    public static final String INSERTAR_PDV = URL + "/App/CtaEpson/AppEpson/Inserts/insert_noti.php";

    //PVC
    public static final String GET_PRODUCTOS_PVC = URL + "/App/AppBassa/Web/get_productos_pvc.php";
//    public static final String GET_CARAS_MARCA = URL + "/App/AppVilaseca/Web/get_caras_marca.php";
    public static final String GET_CARAS_MARCA = "localhost/control_asistencia_pdv/pruebaAndroid.php";

    public static final String UPDATE_COORDENADAS_PDV = URL + "/App/AppBassa/Inserts/update_coordenadas_pdv.php";


    /**
     * Rastreo
     */
    public static final String ACTION_GPS_UPDATE = "android.intent.action.GPS_UPDATE";
    public static final String ACTION_PDV_LOCATION_UPDATE = "android.intent.action.PDV_LOCATION_UPDATE";
    public static final String LATITUD = "latitud";
    public static final String LONGITUD = "longitud";
    public static final String LATITUD_PDV_ACTUAL = "latitude_pdv";
    public static final String LONGITUDE_PDV_ACTUAL = "longitude_pdv";
    public static final String CODIGO_PDV_ACTUAL = "codigo_pdv_pdv";
    public static final String NOMBRE_PDV_ACTUAL = "nombre_pdv_pdv";
    public static final String PERIMETRO_PDV_ACTUAL = "perimetro_pdv_pdv";
    public static final String DISTANCE_PDV_ACTUAL = "distance_pdv_pdv";
    /**
     * Campos de las respuestas Json
     */
    public static final String MENSAJE = "mensaje";
    public static final String NODATA = "NoDisponible";
    public static final String URI = "uri";
    public static final String PHARMA_ID = "id";

    public static final String ACTUALIZADO = "NO";
    public static final String USER = "User";
    public static final String SUPERVISOR = "supervisor";

    public static final String FECHA_SYNC = "FechaSync";

    public static final String NOM_MERCADERISTA = "nom_mercaderista";
    public static final String AUTOTIME = "autotime";
    public static final String PDV = "zone";
    public static final String CODIGO = "pos_id";
    public static final String NOM_COMERCIAL = "canal";
    public static final String CANAL = "canal";
    public static final String SUBCANAL = "subcanal";
    public static final String CUENTA ="cuenta";
    public static final String FECHA = "fecha";
    public static final String HORA = "hora";
    public static final String IDPDV = "idpdv";
    public static final String TIPO = "tipo";
    public static final String INTERNO="interno";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String FORMAT = "format";
    public static final String VENDEDOR = "vendedor";
    public static final String CELULAR = "celular";
    public static final String FALTA_SALIDA ="falta_salida";
    public static final String FECHA_FALTA_SALIDA = "fecha_falta_salida";
    public static final String ID_RUTA ="id_ruta";
    public static final String SHOW_PRIORITARIOS ="show_prioritarios";
    public static final String MODULO ="modulo";
    public static final String MODULO_PUNTOS_PRINCIPAL ="modulo_puntos_principal";
    public static final String MODULO_PUNTOS_TARDIO ="modulo_puntos_tardio";

    public static final String AUTO = "AUTOSERVICIO";
    public static final String MAYO = "MAYORISTA";
    public static final String DIST = "DISTRIBUIDOR";

    public static final String PDV_RESULT = "promotores";
    public static final String PROD_SECUNDARIOS_RESULT = "flooring_onpacks";
    public static final String LINK_ONEDRIVE_RESULT = "links";
    public static final String TESTS_RESULT = "tests";
    public static final String USUARIOSRESULT="usuarios";
    public static final String JUSTIFICACIONESRESULT="justificaciones";
    public static final String CONVENIOSRESULT="convenios";
    public static final String TIPOINVENTARIOESRESULT="tipo_inventario";
    public static final String TIPO_EXH_RESULT = "tipo_exhibiciones";

    public static final String CATEGORIA_TIPO_RESULT = "tipo";
    public static final String TIPO_MOTIVOS_SUGERIDO = "motivos_sugerido";
    public static final String PRECIOS_RESULT = "precios";
    public static final String FLOORING_RESULT = "flooring";
    public static final String PREGUNTAS_RESULT = "preguntas";
    public static final String PROMOCIONES_RESULT = "promocion";
    public static final String ROTACION_RESULT = "rotacion";
    public static final String TAREAS_RESULT = "tareas";
    public static final String POPSUGERIDO_RESULT = "pop_sugerido";
    public static final String PRIORITARIO_RESULT = "prioritario";
    public static final String COMBO_CANJES_RESULT = "combo_canjes";
    public static final String CAUSALES_MCI_RESULT = "causales_mci";
    public static final String CAUSALES_OSA_RESULT = "causales_osa";
    public static final String MATERIALES_ALERTAS_RESULT = "materiales_alertas";
    public static final String TRACKING_RESULT = "tracking";
    public static final String TIPO_VENTAS = "tipo_ventas";
    public static final String TIPO_UNIDADES = "tipo_unidades";
    public static final String TIPO_MODULOS_ROLES = "tipo";
    public static final String TIPO_IMPLEMENTACIONES = "tipo";
    public static final String TIPO_ACTIVIDADES = "tipo";
    public static final String TIPO_REGISTRO = "tipo";
    public static final String CAMPOSPORMODULOSRESULT ="campos_x_modulos";


    public static final String PDI_RESULT = "pdi";
    public static final String MARCA_PROPIA = "P";
    //recupera el id del ultimo registro insertado
    public static final String LAST_ID = "ultimoId";

    //CONSTANTES BAJADA
    public static final String bajar_operadores_mercaderismo = "bajarOperMerc";
    public static final String bajar_Oper = "bajarOper";
    public static final String bajar_user="usuarios";
    public static final String bajar_motivos_sugerido="motivos_sugerido";
    public static final String bajar_prod_secundarios = "bajarProdcutosSecundarios";
    public static final String bajar_link_onedrive = "bajarLinkOnedrive";
    public static final String bajar_campos_x_modulos ="campos_x_modulos";
    public static final String bajar_promocional_ventas ="promocional_ventas";


    public static final String bajar_Oper_Check = "bajarOperCheck";
    public static final String bajar_Precios = "bajarDif";
    public static final String bajar_floo = "flooring";
    public static final String bajar_preg = "preguntas";

    public static final String bajar_tests = "tests";
    public static final String bajar_promociones = "promociones";
    public static final String bajar_tipo_ventas = "tipo_ventas"; //mpin
    public static final String bajar_tipo_implementaciones= "tipo_implementaciones"; //mpin
    public static final String bajar_tipo_actividades= "tipo_actividades"; //mpin  muestra medicas
    public static final String bajar_tipo_unidades= "tipo_unidades"; //mpin
    public static final String bajar_tipo_registro= "tipo_registro"; //mpin
    public static final String bajar_modulo_roles= "tipo_modulo"; //mpin
    public static final String bajar_tracking = "tracking";
    public static final String bajar_convenios = "convenios";
    public static final String bajar_rotacion = "rotacion";
    public static final String bajar_tareas = "tareas";
    public static final String bajar_pop_sugerido = "pop_sugerido";
    public static final String bajar_prioritario = "prioritario";
    public static final String bajar_combo_canjes = "combo_canjes";
    public static final String bajar_causales_mci = "causales_mci";
    public static final String bajar_causales_osa = "causales_osa";
    public static final String bajar_materiales_alertas = "materiales_alertas";
    public static final String bajar_pdi = "pdi";
    public static final String bajar_justificaciones = "justificaciones";
    public static final String bajar_tipo_inventario = "tipo_inventario";
    public static final String bajar_tipo_exhibicion = "tipo_exhibicion";
    public static final String bajar_tipo_categoria = "tipo_categoria";

    //CONSTANTES SUBIDA
    public static final String SUBIR_TODO = "subir";
    public static final String insertPrecio = "insertPrecio";
    public static final String insertMuestras = "insertMuestras"; //mpin
    public static final String insertProbadores = "insertProbadores"; //mpin
    public static final String insertExh = "insertExh";
    public static final String insertConvenios = "insertConvenios";
    public static final String insertVenta = "insertVenta";
    public static final String insertVentas2 = "insertVentas2";
    public static final String insertGps = "insertGps";
    public static final String insertFlooring = "insertflooring";
    public static final String insertinicial = "insertInicial";
    public static final String insertPromocion = "insertpromocion";
    public static final String insertTracking="insertTracking";
    public static final String insertCodificados = "insertCodificados";
    public static final String insertImplementacion = "insertimplementacion";
    public static final String insertvalores = "insertvalores";
    public static final String insertpdv = "insertpdv";
    public static final String insertShare = "insertshare";
    public static final String insertarPdI = "insertpdi";
    public static final String insertAgotados = "insertagotados";
    public static final String insertRotacion = "insertrotacion";
    public static final String insertFoto = "insertfoto";
    public static final String insertPreguntas = "insertpreguntas";
    public static final String insertProdCad = "insertProdCad";

    public static final String insertResultadoPreguntas="insertResultadoPreguntas";
    public static final String insertPacks = "insertPacks2";
    public static final String insertTareas = "insertTareas";
    public static final String insertSugeridos = "insertSugeridos";
    public static final String insertImpulso = "insertImpulso";
    public static final String insertGeo = "insertgeo";
    public static final String insertCanjes = "insertCanjes";
    public static final String insertMci = "insertMci";
    public static final String insertMaterialesRecibidos = "insertMaterialesRecibidos";
    public static final String insertEjecucionMateriales = "insertEjecucionMateriales";
    public static final String insertNot="insertNot";
    public static final String insertMallaCodificados="insertMallaCodificados";
    public static final String insertEvidencias="insertEvidencias";

    //SHARED PREFERENCES
    public static final String SHARED_PREFERENCES = "MisPreferencias";

    /**
     * Constantes Timeline
     */
    public static String SUN = "Domingo";
    public static String MON = "Lunes";
    public static String TUE = "Martes";
    public static String WED = "Miércoles";
    public static String THU = "Jueves";
    public static String FRI = "Viernes";
    public static String SAT = "Sábado";

    public static String JAN = "Enero";
    public static String FEB = "Febrero";
    public static String MAR = "Marzo";
    public static String APR = "Abril";
    public static String MAY = "Mayo";
    public static String JUN = "Junio";
    public static String JUL = "Julio";
    public static String AUG = "Agosto";
    public static String SEP = "Septiembre";
    public static String OCT = "Octubre";
    public static String NOV = "Noviembre";
    public static String DEC = "Diciembre";

    /**
     * Constantes Contracts
     */
    public static final int ESTADO_OK = 0;
    public static final int ESTADO_SYNC = 1;

    public static final String ESTADO = "estado";
    public static final String ID_REMOTA = "idRemota";
    public static final String ID_REMOTA_RUTA = "idRemotaRuta";
    public final static String PENDIENTE_INSERCION = "pendiente_insercion";
    /**
     * new basa consts
     */
    public static final String EVALUACION_PROMOTOR_RESULT = "evaluacion_promotor";
    public static final String GET_EVALUACION_PROMOTOR = URL + "/App/AppBassa/Web/get_evaluacion_promotor092024.php";  //agregar web services
    public static final String INSERTAR_EVALUACION_ENCUESTA = URL + "/App/AppBassa/Inserts/insert_evaluacion_encuesta_102024.php";


    public static final String bajar_Precio = "bajarPre";
    public static final String RE = "Re";
    public static final String NO_DATA = "noData";
    public static final String CODE = "Code";
    public static final String LOCAL = "Local";
    public static final String ID = "id";
    public static final String MERCADERISTA = "mercaderista";
    public static final String PUNTO_APOYO = "punto_apoyo";

    public static final String insertExhBassa = "insertexhbassa";

    public static final String INSERTAR_EXHIBICION_BASSA = URL + "/App/AppBassa/Inserts/insert_exhibicion_colgate042024.php";
    public static final String INSERTAR_EXHIBICION = URL + "/App/AppBassa/Inserts/insert_exhibicion042024.php";


    public static final String ID_EXH_SUPERVISORES = "idExhColgate";
    public static final String ID_EXH = "idPdvExh";
    public static final String insertExhAdNu = "insertexhadnu";
    public static final String bajar_Exh_Supervisor = "bajarExhSupervisor";
    public static final String TYPERESULT = "typeresult";

    public static final String GET_EXHIBITION_SUPERVISOR = URL + "/App/AppBassa/Web/get_exhibiciones_supervisor_new.php";

    public final static String CIUDAD = "city";
    public final static String VAFOTO = "foto";
    public final static String CHANNEL_SEGMENT = "channel_segment";
    public static final String ROL = "rol";
    public static final String ROLNUEVO = "rol_nuevo";
    public static final String insertEvaluacionDemo ="insertevaluacion";

    public static final String CADENA = "cadena";
    public static final String CUSTOMER_OWNER = "customer_owner";
    public static final String FABRICANTE ="fabricante";

    public static final String insertNovedades="insertNovedades";
    public static final String GET_TIPO_NOVEDADES = URL + "/App/AppBassa/Web/get_tipo_novedades.php"; //TODO REVISARRR
    public static final String GET_CANALES_FOTOS = URL + "/App/AppBassa/Web/get_canales_fotos.php"; //CREAR
    public static final String CONSULTAR_NOVEDADES_DIARIO = URL + "/App/AppBassa/Web/consultar_novedades_diario.php";
    public static final String TIPONOVEDADESRESULT ="tipo_novedades";
    public static final String bajar_tipo_novedades ="tipo_novedades";
    public static final String INSERTAR_NOVEDADES = URL + "/App/AppBassa/Inserts/insert_novedades112025.php"; //mpin
    public static final String PROMOCIONALVENTASESRESULT ="promocional_ventas";


    public static final String CANALESFOTOSRESULT ="canales_fotos";
    public static final String bajar_canales_fotos ="canales_fotos";
    /**
     * Códigos del campo {@link //ESTADO}
     */
    public static final String SUCCESS = "1";
    public static final String FAILED = "2";

    /**
     * Tipo de cuenta para la sincronización
     */
    public static final String ACCOUNT_TYPE = "com.luckyecuador.app.bassaApp.account";

    /**
     * Autoridad del Content Provider
     */
    public final static String AUTHORITY = "com.luckyecuador.app.bassaApp";
}
