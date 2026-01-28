package com.luckyecuador.app.bassaApp.DataBase;


import com.luckyecuador.app.bassaApp.Conexion.Constantes;
import com.luckyecuador.app.bassaApp.Conexion.ContractTipoInventario;
import com.luckyecuador.app.bassaApp.Contracts.ContractCamposPorModulos;
import com.luckyecuador.app.bassaApp.Contracts.ContractCanalesFotos;
import com.luckyecuador.app.bassaApp.Contracts.ContractCategoriaTipo;
import com.luckyecuador.app.bassaApp.Contracts.ContractConvenios;
import com.luckyecuador.app.bassaApp.Contracts.ContractEvaluacionEncuesta;
import com.luckyecuador.app.bassaApp.Contracts.ContractExhibicionSupervisor;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertConvenios;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertEvaluacionEncuesta;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertMaterialesRecibidos;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertMuestras;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertNovedades;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPacks2;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertProbadores;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertTracking;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertVentas2;
import com.luckyecuador.app.bassaApp.Contracts.ContractJustificacion;
import com.luckyecuador.app.bassaApp.Contracts.ContractLinkOnedrive;
import com.luckyecuador.app.bassaApp.Contracts.ContractModulosRoles;
import com.luckyecuador.app.bassaApp.Contracts.ContractMotivoSugerido;
import com.luckyecuador.app.bassaApp.Contracts.ContractProductosSecundarios;
import com.luckyecuador.app.bassaApp.Contracts.ContractPromocionalVentas;
import com.luckyecuador.app.bassaApp.Contracts.ContractTipoActividades;
import com.luckyecuador.app.bassaApp.Contracts.ContractTipoImplementaciones;
import com.luckyecuador.app.bassaApp.Contracts.ContractTipoNovedades;
import com.luckyecuador.app.bassaApp.Contracts.ContractTipoRegistro;
import com.luckyecuador.app.bassaApp.Contracts.ContractTipoUnidades;
import com.luckyecuador.app.bassaApp.Contracts.ContractTipoVentas;
import com.luckyecuador.app.bassaApp.Contracts.ContractTracking;
import com.luckyecuador.app.bassaApp.Contracts.ContractUser;
import com.luckyecuador.app.bassaApp.Contracts.InsertExhBassa;
import com.luckyecuador.app.bassaApp.Contracts.InsertExhibicionesAdNu;
import com.luckyecuador.app.bassaApp.Contracts.InsertFlooring;
import com.luckyecuador.app.bassaApp.Contracts.ContractAlertas;
import com.luckyecuador.app.bassaApp.Contracts.ContractCausalesMCI;
import com.luckyecuador.app.bassaApp.Contracts.ContractCausalesOSA;
import com.luckyecuador.app.bassaApp.Contracts.ContractComboCanjes;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertCanjes;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertEjecucionMateriales;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertEvidencias;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertMCIPdv;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertCodificados;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPDI;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertResultadoPreguntas;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertSugeridos;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertTareas;
import com.luckyecuador.app.bassaApp.Contracts.ContractLog;
import com.luckyecuador.app.bassaApp.Contracts.ContractPDI;
import com.luckyecuador.app.bassaApp.Contracts.ContractPopSugerido;
import com.luckyecuador.app.bassaApp.Contracts.ContractPortafolioProductosAASS;
import com.luckyecuador.app.bassaApp.Contracts.ContractPortafolioProductosMAYO;
import com.luckyecuador.app.bassaApp.Contracts.ContractPrioritario;
import com.luckyecuador.app.bassaApp.Contracts.ContractPromociones;
import com.luckyecuador.app.bassaApp.Contracts.ContractRotacion;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertFotografico;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertImpulso;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPacks;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPreguntas;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertProdCaducar;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertRotacion;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertVenta;
import com.luckyecuador.app.bassaApp.Contracts.ContractPortafolioProductos;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertAgotados;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertExh;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertGps;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertImplementacion;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertInicial;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPdv;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPrecios;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPromocion;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertRastreo;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertShare;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertValores;
import com.luckyecuador.app.bassaApp.Contracts.ContractNotificacion;
import com.luckyecuador.app.bassaApp.Contracts.ContractPrecios;
import com.luckyecuador.app.bassaApp.Contracts.ContractPharmaValue;
import com.luckyecuador.app.bassaApp.Contracts.ContractPreguntas;
import com.luckyecuador.app.bassaApp.Contracts.ContractTareas;
import com.luckyecuador.app.bassaApp.Contracts.ContractTests;
import com.luckyecuador.app.bassaApp.Contracts.ContractTipoExh;

/**
 * Created by Lucky Ecuador on 24/11/2016.
 */

public class Projection {

    /**
     * Proyección para las consultas bajada
     */

    public static final String[] PROJECTION_PUNTOS = new String[]{
            ContractPharmaValue.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractPharmaValue.Columnas.CHANNEL,
            ContractPharmaValue.Columnas.SUBCHANNEL,
            ContractPharmaValue.Columnas.CHANNEL_SEGMENT,
            ContractPharmaValue.Columnas.FORMAT,
            ContractPharmaValue.Columnas.CUSTOMER_OWNER,
            ContractPharmaValue.Columnas.POS_ID,
            ContractPharmaValue.Columnas.POS_NAME,
            ContractPharmaValue.Columnas.POS_NAME_DPSM,
            ContractPharmaValue.Columnas.ZONA,
            ContractPharmaValue.Columnas.REGION,
            ContractPharmaValue.Columnas.PROVINCIA,
            ContractPharmaValue.Columnas.CIUDAD,
            ContractPharmaValue.Columnas.DIRECCION,
            ContractPharmaValue.Columnas.KAM,
            ContractPharmaValue.Columnas.SALES_EXECUTIVE,
            ContractPharmaValue.Columnas.MERCHANDISING,
            ContractPharmaValue.Columnas.SUPERVISOR,
            ContractPharmaValue.Columnas.ROL,
            ContractPharmaValue.Columnas.MERCADERISTA,
            ContractPharmaValue.Columnas.USER,
            ContractPharmaValue.Columnas.DPSM,
            ContractPharmaValue.Columnas.STATUS,
            ContractPharmaValue.Columnas.TIPO,
            ContractPharmaValue.Columnas.LATITUD,
            ContractPharmaValue.Columnas.LONGITUD,
            ContractPharmaValue.Columnas.FOTO,
            ContractPharmaValue.Columnas.SEGMENTACION,
            ContractPharmaValue.Columnas.COMPRAS,
            ContractPharmaValue.Columnas.PASS,
            ContractPharmaValue.Columnas.NUMERO_CONTROLLER,
            ContractPharmaValue.Columnas.FECHA_VISITA,
            ContractPharmaValue.Columnas.DEVICE_ID,
            ContractPharmaValue.Columnas.PERIMETRO,
            ContractPharmaValue.Columnas.DISTANCIA,
            ContractPharmaValue.Columnas.TERMOMETRO
    };

    public static final String[] PROJECTION_PROMOCIONES = new String[]{
            ContractPromociones.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractPromociones.Columnas.CANAL,
            ContractPromociones.Columnas.TIPO,
            ContractPromociones.Columnas.CADENA,
            ContractPromociones.Columnas.FABRICANTE,
            ContractPromociones.Columnas.DESCRIPCION,
            ContractPromociones.Columnas.CATEGORIA,
            ContractPromociones.Columnas.SUBCATEGORIA,
            ContractPromociones.Columnas.MARCA,
            ContractPromociones.Columnas.SKU,
            ContractPromociones.Columnas.CAMPANA
    };

    public static final String[] PROJECTION_COMBO_CANJES = new String[]{
            ContractComboCanjes.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractComboCanjes.Columnas.TIPO_COMBO,
            ContractComboCanjes.Columnas.MECANICA
    };

    public static final String[] PROJECTION_CAUSALES_MCI = new String[]{
            ContractCausalesMCI.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractCausalesMCI.Columnas.CAUSAL
    };

    public static final String[] PROJECTION_CAUSALES_OSA = new String[]{
            ContractCausalesOSA.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractCausalesOSA.Columnas.CANAL,
            ContractCausalesOSA.Columnas.RESPONSABLE,
            ContractCausalesOSA.Columnas.CAUSAL
    };

    public static final String[] PROJECTION_MATERIALES_ALERTAS = new String[]{
            ContractAlertas.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractAlertas.Columnas.TIPO_ALERTA,
            ContractAlertas.Columnas.CATEGORIA,
            ContractAlertas.Columnas.MATERIAL
    };

    public static final String[] PROJECTION_PDI = new String[]{
            ContractPDI.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractPDI.Columnas.CANAL,
            ContractPDI.Columnas.CATEGORIA,
            ContractPDI.Columnas.SUBCATEGORIA,
            ContractPDI.Columnas.MARCA,
            ContractPDI.Columnas.OBJETIVO,
            ContractPDI.Columnas.PLATAFORMA
    };

    public static final String[] PROJECTION_USER = new String[]{
            ContractUser.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractUser.Columnas.USER,
            ContractUser.Columnas.MERCADERISTA,
            ContractUser.Columnas.DEVICE_ID,
            ContractUser.Columnas.COLOR,
            ContractUser.Columnas.STATUS

            /*   ContractPortafolioProductos.Columnas.MANUFACTURER,*/
            /*   ContractPortafolioProductos.Columnas.FORMAT  */
    };
    public static final String[] PROJECTION_CONVENIOS = new String[]{
            ContractConvenios.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractConvenios.Columnas.CODIGO,
            ContractConvenios.Columnas.PDV,
            ContractConvenios.Columnas.CATEGORIA,
            ContractConvenios.Columnas.UNIDAD_NEGOCIO,
            ContractConvenios.Columnas.FABRICANTE,
            ContractConvenios.Columnas.MARCA,
            ContractConvenios.Columnas.TIPO_EXHIBICION,
            ContractConvenios.Columnas.NUMERO_EXHIBICION,
            ContractConvenios.Columnas.FORMATO,
            ContractConvenios.Columnas.FECHA_SUBIDA,
            ContractConvenios.Columnas.ENLACE
    };

    public static final String[] PROJECTION_POPSUGERIDO = new String[]{
            ContractPopSugerido.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractPopSugerido.Columnas.CANAL,
            ContractPopSugerido.Columnas.CODIGO_PDV,
            ContractPopSugerido.Columnas.POP_SUGERIDO
    };

    public static final String[] PROJECTION_PRIORITARIO = new String[]{
            ContractPrioritario.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractPrioritario.Columnas.CANAL,
            ContractPrioritario.Columnas.CODIGO_PDV,
            ContractPrioritario.Columnas.CATEGORIA,
            ContractPrioritario.Columnas.SUBCATEGORIA,
            ContractPrioritario.Columnas.MARCA,
            ContractPrioritario.Columnas.CONTENIDO,
            ContractPrioritario.Columnas.SKU
    };

    public static final String[] PROJECTION_ROTACION = new String[]{
            ContractRotacion.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractRotacion.Columnas.CATEGORIA,
            ContractRotacion.Columnas.SUBCATEGORIA,
            ContractRotacion.Columnas.MARCA,
            ContractRotacion.Columnas.PRODUCTO,
            ContractRotacion.Columnas.PROMOCIONAL,
            ContractRotacion.Columnas.MECANICA,
            ContractRotacion.Columnas.PESO,
            ContractRotacion.Columnas.TIPO,
            ContractRotacion.Columnas.PLATAFORMA
    };

    public static final String[] PROJECTION_TAREAS = new String[]{
            ContractTareas.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractTareas.Columnas.CANAL,
            ContractTareas.Columnas.CODIGOPDV,
            ContractTareas.Columnas.MERCADERISTA,
            ContractTareas.Columnas.TAREAS,
            ContractTareas.Columnas.PERIODO,
            ContractTareas.Columnas.FECHA_INGRESO
    };

    public static final String[] PROJECTION_PREGUNTAS = new String[]{
            ContractPreguntas.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractPreguntas.Columnas.KEY_QUES,
            ContractPreguntas.Columnas.KEY_ANSWER,
            ContractPreguntas.Columnas.KEY_OPTA,
            ContractPreguntas.Columnas.KEY_OPTB,
            ContractPreguntas.Columnas.KEY_OPTC,
            ContractPreguntas.Columnas.KEY_CANAL,
            ContractPreguntas.Columnas.KEY_TIEMPO,
            ContractPreguntas.Columnas.KEY_TEST_ID
    };

    public static final String[] PROJECTION_TIPO_EXH = new String[]{
            ContractTipoExh.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractTipoExh.Columnas.CANAL,
            ContractTipoExh.Columnas.EXHIBICION,
            ContractTipoExh.Columnas.FOTO
    };

    public static final String[] PROJECTION_CATEGORIA_TIPO = new String[]{
            ContractTipoExh.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractCategoriaTipo.Columnas.TIPO,
            ContractCategoriaTipo.Columnas.CANAL,

    };

    public static final String[] PROJECTION_MOTIVO_SUGERIDO = new String[]{
            ContractMotivoSugerido.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractMotivoSugerido.Columnas.CANAL,
            ContractMotivoSugerido.Columnas.MOTIVO,
            ContractMotivoSugerido.Columnas.FOTO
    };

    public static final String[] PROJECTION_TESTS = new String[]{
            ContractTests.Columnas.KEY_TEST_ID,
            Constantes.ID_REMOTA,
            ContractTests.Columnas.KEY_TEST,
            ContractTests.Columnas.KEY_DESCRIPTION,
            ContractTests.Columnas.KEY_DATE_START,
            ContractTests.Columnas.KEY_HOUR_START,
            ContractTests.Columnas.KEY_DATE_LIMIT,
            ContractTests.Columnas.KEY_HOUR_LIMIT,
            ContractTests.Columnas.KEY_ACTIVE
    };

    public static final String[] PROJECTION_TRACKING = new String[]{
            ContractTracking.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractTracking.Columnas.CUSTOMER,
            ContractTracking.Columnas.CUENTAS,
            ContractTracking.Columnas.MECANICA,
            ContractTracking.Columnas.CATEGORIA,
            ContractTracking.Columnas.SUBCATEGORIA,
            ContractTracking.Columnas.MARCA,
            ContractTracking.Columnas.DESCRIPCION,
            ContractTracking.Columnas.PRECIO_PROMOCION,
            ContractTracking.Columnas.VIGENCIA,
            ContractTracking.Columnas.MATERIAL_POP
    };

    public static final String[] PROJECTION_INSERT_CONVENIOS = new String[]{
            ContractInsertConvenios.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertConvenios.Columnas.PHARMA_ID,
            ContractInsertConvenios.Columnas.FECHA,
            ContractInsertConvenios.Columnas.HORA,
            ContractInsertConvenios.Columnas.CODIGO,
            ContractInsertConvenios.Columnas.POS_NAME,
            ContractInsertConvenios.Columnas.USUARIO,
            ContractInsertConvenios.Columnas.LATITUD,
            ContractInsertConvenios.Columnas.LONGITUD,
            ContractInsertConvenios.Columnas.CATEGORIA,
            ContractInsertConvenios.Columnas.FABRICANTE,
            ContractInsertConvenios.Columnas.MARCA,
            ContractInsertConvenios.Columnas.TIPO_EXHIBICION,
            ContractInsertConvenios.Columnas.NUMERO_EXHIBICION,
            ContractInsertConvenios.Columnas.CONTRATADA,
            ContractInsertConvenios.Columnas.OBSERVACION,
            ContractInsertConvenios.Columnas.FOTO,
            ContractInsertConvenios.Columnas.MODULO,
            ContractInsertConvenios.Columnas.UNIDAD_DE_NEGOCIO,
            ContractInsertConvenios.Columnas.ROL
    };


    public static final String[] PROJECTION_INSERT_PREGUNTAS = new String[]{
            ContractInsertPreguntas.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertPreguntas.Columnas.USUARIO,
            ContractInsertPreguntas.Columnas.TEST_ID,
            ContractInsertPreguntas.Columnas.TEST,
            ContractInsertPreguntas.Columnas.P1,
            ContractInsertPreguntas.Columnas.P2,
            ContractInsertPreguntas.Columnas.P3,
            ContractInsertPreguntas.Columnas.P4,
            ContractInsertPreguntas.Columnas.P5,
            ContractInsertPreguntas.Columnas.P6,
            ContractInsertPreguntas.Columnas.P7,
            ContractInsertPreguntas.Columnas.P8,
            ContractInsertPreguntas.Columnas.P9,
            ContractInsertPreguntas.Columnas.P10,
            ContractInsertPreguntas.Columnas.P11,
            ContractInsertPreguntas.Columnas.P12,
            ContractInsertPreguntas.Columnas.P13,
            ContractInsertPreguntas.Columnas.P14,
            ContractInsertPreguntas.Columnas.P15,
            ContractInsertPreguntas.Columnas.CORRECTAS,
            ContractInsertPreguntas.Columnas.INCORRECTAS,
            ContractInsertPreguntas.Columnas.CALIFICACION,
            ContractInsertPreguntas.Columnas.OBSERVACION,
            ContractInsertPreguntas.Columnas.FECHA,
            ContractInsertPreguntas.Columnas.HORA,
            ContractInsertPreguntas.Columnas.CRONOMETO,
            ContractInsertPreguntas.Columnas.ESTADO_TEST
    };

    public static final String[] PROJECTION_RESULTADO_PREGUNTAS = new String[]{
            ContractInsertResultadoPreguntas.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertResultadoPreguntas.Columnas.KEY_USUARIO,
            ContractInsertResultadoPreguntas.Columnas.KEY_TEST_ID,
            ContractInsertResultadoPreguntas.Columnas.KEY_TEST,
            ContractInsertResultadoPreguntas.Columnas.KEY_QUES,
            ContractInsertResultadoPreguntas.Columnas.KEY_OPTA,
            ContractInsertResultadoPreguntas.Columnas.KEY_OPTB,
            ContractInsertResultadoPreguntas.Columnas.KEY_OPTC,
            ContractInsertResultadoPreguntas.Columnas.KEY_ANSWER,
            ContractInsertResultadoPreguntas.Columnas.KEY_ANSWER_USER,
            ContractInsertResultadoPreguntas.Columnas.KEY_RESULT,
            ContractInsertResultadoPreguntas.Columnas.KEY_FECHA,
            ContractInsertResultadoPreguntas.Columnas.KEY_HORA
    };




    public static final String[] PROJECTION_INSERT_CANJES = new String[]{
            ContractInsertCanjes.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertCanjes.Columnas.PHARMA_ID,
            ContractInsertCanjes.Columnas.CODIGO,
            ContractInsertCanjes.Columnas.CANAL,
            ContractInsertCanjes.Columnas.NOMBRE_COMERCIAL,
            ContractInsertCanjes.Columnas.LOCAL,
            ContractInsertCanjes.Columnas.REGION,
            ContractInsertCanjes.Columnas.PROVINCIA,
            ContractInsertCanjes.Columnas.CIUDAD,
            ContractInsertCanjes.Columnas.ZONA,
            ContractInsertCanjes.Columnas.DIRECCION,
            ContractInsertCanjes.Columnas.SUPERVISOR,
            ContractInsertCanjes.Columnas.MERCADERISTA,
            ContractInsertCanjes.Columnas.USUARIO,
            ContractInsertCanjes.Columnas.LATITUD,
            ContractInsertCanjes.Columnas.LONGITUD,
            ContractInsertCanjes.Columnas.TERRITORIO,
            ContractInsertCanjes.Columnas.ZONA_TERRITORIO,
            ContractInsertCanjes.Columnas.CATEGORIA,
            ContractInsertCanjes.Columnas.SUBCATEGORIA,
            ContractInsertCanjes.Columnas.MARCA,
            ContractInsertCanjes.Columnas.PRODUCTO,
            ContractInsertCanjes.Columnas.TIPO_COMBO,
            ContractInsertCanjes.Columnas.MECANICA,
            ContractInsertCanjes.Columnas.COMBOS_ARMADOS,
            ContractInsertCanjes.Columnas.STOCK,
            ContractInsertCanjes.Columnas.PVC_COMBO,
            ContractInsertCanjes.Columnas.PVC_UNITARIO,
            ContractInsertCanjes.Columnas.VISITA,
            ContractInsertCanjes.Columnas.MES,
            ContractInsertCanjes.Columnas.OBSERVACIONES,
            ContractInsertCanjes.Columnas.FOTO,
            ContractInsertCanjes.Columnas.FOTO_GUIA,
            ContractInsertCanjes.Columnas.FECHA,
            ContractInsertCanjes.Columnas.HORA,
            ContractInsertCanjes.Columnas.POS_NAME,
            ContractInsertCanjes.Columnas.PLATAFORMA
    };

    public static final String[] PROJECTION_JUSTIFICACION = new String[]{
            ContractJustificacion.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractJustificacion.Columnas.JUSTIFICACION
    };

    public static final String[] PROJECTION_TIPO_INVENTARIO = new String[]{
            ContractTipoInventario.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractTipoInventario.Columnas.CAUSALES
    };

    public static final String[] PROJECTION_LINK_ONEDRIVE = new String[]{
            ContractLinkOnedrive.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractLinkOnedrive.Columnas.MODULO,
            ContractLinkOnedrive.Columnas.LINK
    };

    public static final String[] PROJECTION_INSERT_MATERIALES_RECIBIDOS = new String[]{
            ContractInsertMaterialesRecibidos.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertMaterialesRecibidos.Columnas.PHARMA_ID,
            ContractInsertMaterialesRecibidos.Columnas.CODIGO,
            ContractInsertMaterialesRecibidos.Columnas.CANAL,
            ContractInsertMaterialesRecibidos.Columnas.NOMBRE_COMERCIAL,
            ContractInsertMaterialesRecibidos.Columnas.LOCAL,
            ContractInsertMaterialesRecibidos.Columnas.REGION,
            ContractInsertMaterialesRecibidos.Columnas.PROVINCIA,
            ContractInsertMaterialesRecibidos.Columnas.CIUDAD,
            ContractInsertMaterialesRecibidos.Columnas.ZONA,
            ContractInsertMaterialesRecibidos.Columnas.DIRECCION,
            ContractInsertMaterialesRecibidos.Columnas.SUPERVISOR,
            ContractInsertMaterialesRecibidos.Columnas.MERCADERISTA,
            ContractInsertMaterialesRecibidos.Columnas.USUARIO,
            ContractInsertMaterialesRecibidos.Columnas.LATITUD,
            ContractInsertMaterialesRecibidos.Columnas.LONGITUD,
            ContractInsertMaterialesRecibidos.Columnas.TERRITORIO,
            ContractInsertMaterialesRecibidos.Columnas.ZONA_TERRITORIO,
            ContractInsertMaterialesRecibidos.Columnas.ALERTA,
            ContractInsertMaterialesRecibidos.Columnas.TIPO,
            ContractInsertMaterialesRecibidos.Columnas.MATERIAL,
            ContractInsertMaterialesRecibidos.Columnas.CANTIDAD,
            ContractInsertMaterialesRecibidos.Columnas.ESTADO_MATERIAL,
            ContractInsertMaterialesRecibidos.Columnas.PRIORIDAD,
            ContractInsertMaterialesRecibidos.Columnas.OBSERVACIONES,
            ContractInsertMaterialesRecibidos.Columnas.FOTO,
            ContractInsertMaterialesRecibidos.Columnas.FECHA,
            ContractInsertMaterialesRecibidos.Columnas.HORA
    };

    public static final String[] PROJECTION_INSERT_EJECUCION_MATERIALES = new String[]{
            ContractInsertEjecucionMateriales.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertEjecucionMateriales.Columnas.PHARMA_ID,
            ContractInsertEjecucionMateriales.Columnas.CODIGO,
            ContractInsertEjecucionMateriales.Columnas.CANAL,
            ContractInsertEjecucionMateriales.Columnas.NOMBRE_COMERCIAL,
            ContractInsertEjecucionMateriales.Columnas.LOCAL,
            ContractInsertEjecucionMateriales.Columnas.REGION,
            ContractInsertEjecucionMateriales.Columnas.PROVINCIA,
            ContractInsertEjecucionMateriales.Columnas.CIUDAD,
            ContractInsertEjecucionMateriales.Columnas.ZONA,
            ContractInsertEjecucionMateriales.Columnas.DIRECCION,
            ContractInsertEjecucionMateriales.Columnas.SUPERVISOR,
            ContractInsertEjecucionMateriales.Columnas.MERCADERISTA,
            ContractInsertEjecucionMateriales.Columnas.USUARIO,
            ContractInsertEjecucionMateriales.Columnas.LATITUD,
            ContractInsertEjecucionMateriales.Columnas.LONGITUD,
            ContractInsertEjecucionMateriales.Columnas.TERRITORIO,
            ContractInsertEjecucionMateriales.Columnas.ZONA_TERRITORIO,
            ContractInsertEjecucionMateriales.Columnas.TIPO,
            ContractInsertEjecucionMateriales.Columnas.MATERIAL,
            ContractInsertEjecucionMateriales.Columnas.ESTADO_MATERIAL,
            ContractInsertEjecucionMateriales.Columnas.PRIORIDAD,
            ContractInsertEjecucionMateriales.Columnas.OBSERVACIONES,
            ContractInsertEjecucionMateriales.Columnas.FOTO,
            ContractInsertEjecucionMateriales.Columnas.FECHA,
            ContractInsertEjecucionMateriales.Columnas.HORA
    };

    public static final String[] PROJECTION_INSERT_EVIDENCIAS = new String[]{
            ContractInsertEvidencias.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertEvidencias.Columnas.PHARMA_ID,
            ContractInsertEvidencias.Columnas.CODIGO,
            ContractInsertEvidencias.Columnas.USUARIO,
            ContractInsertEvidencias.Columnas.MARCA,
            ContractInsertEvidencias.Columnas.CATEGORIA,
            ContractInsertEvidencias.Columnas.SUBCATEGORIA,
            ContractInsertEvidencias.Columnas.COMENTARIO,
            ContractInsertEvidencias.Columnas.FOTO_ANTES,
            ContractInsertEvidencias.Columnas.FOTO_DESPUES,
            ContractInsertEvidencias.Columnas.FECHA,
            ContractInsertEvidencias.Columnas.HORA
    };
    //mpin - PARA EL REPOSITORIO DE PROBADORES
    public static final String[] PROJECTION_TIPO_REGISTRO = new String[]{
            ContractTipoRegistro.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractTipoRegistro.Columnas.TIPO
    };
    //mpin - MUESTRAS medicas
    public static final String[] PROJECTION_TIPO_ACTIVIDADES = new String[]{
            ContractTipoActividades.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractTipoActividades.Columnas.TIPO
    };
//mpin
    public static final String[] PROJECTION_TIPO_VENTAS = new String[]{
            ContractTipoVentas.Columnas._ID,
            Constantes.ID_REMOTA,
        ContractTipoVentas.Columnas.TIPO
    };

//mpin
public static final String[] PROJECTION_TIPO_IMPLEMENTACIONES = new String[]{
        ContractTipoImplementaciones.Columnas._ID,
        Constantes.ID_REMOTA,
        ContractTipoImplementaciones.Columnas.TIPO
};
//mpin
    public static final String[] PROJECTION_TIPO_UNIDADES = new String[]{
            ContractTipoUnidades.Columnas._ID,
            Constantes.ID_REMOTA,
        ContractTipoUnidades.Columnas.TIPO
    };
    //mpin
    public static final String[] PROJECTION_MODULO_ROLES = new String[]{
            ContractModulosRoles.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractModulosRoles.Columnas.ROL,
            ContractModulosRoles.Columnas.MODULO
    };

    public static final String[] PROJECTION_INSERT_MCI = new String[]{
            ContractInsertMCIPdv.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertMCIPdv.Columnas.PHARMA_ID,
            ContractInsertMCIPdv.Columnas.CODIGO,
            ContractInsertMCIPdv.Columnas.CANAL,
            ContractInsertMCIPdv.Columnas.NOMBRE_COMERCIAL,
            ContractInsertMCIPdv.Columnas.LOCAL,
            ContractInsertMCIPdv.Columnas.REGION,
            ContractInsertMCIPdv.Columnas.PROVINCIA,
            ContractInsertMCIPdv.Columnas.CIUDAD,
            ContractInsertMCIPdv.Columnas.ZONA,
            ContractInsertMCIPdv.Columnas.DIRECCION,
            ContractInsertMCIPdv.Columnas.SUPERVISOR,
            ContractInsertMCIPdv.Columnas.MERCADERISTA,
            ContractInsertMCIPdv.Columnas.USUARIO,
            ContractInsertMCIPdv.Columnas.LATITUD,
            ContractInsertMCIPdv.Columnas.LONGITUD,
            ContractInsertMCIPdv.Columnas.TERRITORIO,
            ContractInsertMCIPdv.Columnas.ZONA_TERRITORIO,
            ContractInsertMCIPdv.Columnas.CAUSAL,
            ContractInsertMCIPdv.Columnas.OBSERVACIONES,
            ContractInsertMCIPdv.Columnas.FOTO,
            ContractInsertMCIPdv.Columnas.FECHA,
            ContractInsertMCIPdv.Columnas.HORA,
            ContractInsertMCIPdv.Columnas.POS_NAME
    };

    public static final String[] PROJECTION_PRECIOS = new String[]{
            ContractPrecios.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractPrecios.Columnas.PRODUCTO,
            ContractPrecios.Columnas.SEGMENTO,
            ContractPrecios.Columnas.MARCA,
            ContractPrecios.Columnas.CATEGORIA,
            ContractPrecios.Columnas.SUBCATEGORIA
    };

    public static final String[] PROJECTION_FLOORING = new String[]{
            ContractPortafolioProductos.Columnas._ID,
            Constantes.ID_REMOTA,
            //ContractPortafolioProductos.Columnas.CODIGO_PRODUCTO,
            ContractPortafolioProductos.Columnas.SECTOR,
            ContractPortafolioProductos.Columnas.CATEGORY,
            ContractPortafolioProductos.Columnas.SUBCATEGORIA,
            ContractPortafolioProductos.Columnas.SEGMENTO,
            ContractPortafolioProductos.Columnas.PRESENTACION,
            ContractPortafolioProductos.Columnas.VARIANTE1,
            ContractPortafolioProductos.Columnas.VARIANTE2,
            ContractPortafolioProductos.Columnas.CONTENIDO,
            ContractPortafolioProductos.Columnas.SKU,
            ContractPortafolioProductos.Columnas.MARCA,
            ContractPortafolioProductos.Columnas.FABRICANTE,
            ContractPortafolioProductos.Columnas.PVP,
            ContractPortafolioProductos.Columnas.CADENAS,
            ContractPortafolioProductos.Columnas.FOTO,
            ContractPortafolioProductos.Columnas.PLATAFORMA
            /*ContractPortafolioProductos.Columnas.MANUFACTURER,
            ContractPortafolioProductos.Columnas.FORMAT*/
    };

    public static final String[] PROJECTION_CAMPOS_POR_MODULOS = new String[]{
            ContractCamposPorModulos.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractCamposPorModulos.Columnas.MODULO,
            ContractCamposPorModulos.Columnas.CAMPO,
            ContractCamposPorModulos.Columnas.OBLIGATORIO,
            ContractCamposPorModulos.Columnas.CUENTA
    };

    public static final String[] PROJECTION_PROD_SECUNDARIOS = new String[]{
            ContractProductosSecundarios.Columnas._ID,
            Constantes.ID_REMOTA,
            //ContractProductosSecundarios.Columnas.CODIGO_PRODUCTO,
            ContractProductosSecundarios.Columnas.SECTOR,
            ContractProductosSecundarios.Columnas.CATEGORY,
            ContractProductosSecundarios.Columnas.SUBCATEGORIA,
            ContractProductosSecundarios.Columnas.SEGMENTO,
            ContractProductosSecundarios.Columnas.PRESENTACION,
            ContractProductosSecundarios.Columnas.VARIANTE1,
            ContractProductosSecundarios.Columnas.VARIANTE2,
            ContractProductosSecundarios.Columnas.CONTENIDO,
            ContractProductosSecundarios.Columnas.SKU,
            ContractProductosSecundarios.Columnas.MARCA,
            ContractProductosSecundarios.Columnas.FABRICANTE,
            ContractProductosSecundarios.Columnas.PVP,
            ContractProductosSecundarios.Columnas.CADENAS,
            ContractProductosSecundarios.Columnas.FOTO,
            ContractProductosSecundarios.Columnas.PLATAFORMA
            /*ContractProductosSecundarios.Columnas.MANUFACTURER,
            ContractProductosSecundarios.Columnas.FORMAT*/
    };

    public static final String[] PROJECTION_FLOORING_AASS = new String[]{
            ContractPortafolioProductosAASS.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractPortafolioProductosAASS.Columnas.CATEGORIA,
            ContractPortafolioProductosAASS.Columnas.SUBCATEGORIA,
            ContractPortafolioProductosAASS.Columnas.MARCA,
            ContractPortafolioProductosAASS.Columnas.FABRICANTE,
            ContractPortafolioProductosAASS.Columnas.SKU,
            ContractPortafolioProductosAASS.Columnas.CADENAS
    };

    public static final String[] PROJECTION_FLOORING_MAYO = new String[]{
            ContractPortafolioProductosMAYO.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractPortafolioProductosMAYO.Columnas.CODIGO,
            ContractPortafolioProductosMAYO.Columnas.USUARIO,
            ContractPortafolioProductosMAYO.Columnas.CATEGORIA,
            ContractPortafolioProductosMAYO.Columnas.SUBCATEGORIA,
            ContractPortafolioProductosMAYO.Columnas.MARCA,
            ContractPortafolioProductosMAYO.Columnas.FABRICANTE,
            ContractPortafolioProductosMAYO.Columnas.SKU,
            ContractPortafolioProductosMAYO.Columnas.STATUS
    };

    public static final String[] PROJECTION_INSERTPRECIO = new String[]{
            ContractInsertPrecios.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertPrecios.Columnas.PHARMA_ID,
            ContractInsertPrecios.Columnas.CODIGO,
            ContractInsertPrecios.Columnas.USUARIO,
            ContractInsertPrecios.Columnas.SUPERVISOR,
            ContractInsertPrecios.Columnas.FECHA,
            ContractInsertPrecios.Columnas.HORA,
            ContractInsertPrecios.Columnas.CATEGORIA,
            ContractInsertPrecios.Columnas.SUBCATEGORIA,
            ContractInsertPrecios.Columnas.PRESENTACION,
            ContractInsertPrecios.Columnas.POFERTA,
            ContractInsertPrecios.Columnas.BRAND,
            ContractInsertPrecios.Columnas.FABRICANTE,
            ContractInsertPrecios.Columnas.PRECIO_DESCUENTO,
            ContractInsertPrecios.Columnas.C_DESCUENTO,
            /*ContractInsertPrecios.Columnas.TAMANO,
            ContractInsertPrecios.Columnas.CANTIDAD,*/
            ContractInsertPrecios.Columnas.SKU_CODE,
            ContractInsertPrecios.Columnas.PREGULAR,
            ContractInsertPrecios.Columnas.PPROMOCION,
            ContractInsertPrecios.Columnas.VAR_SUGERIDO,
            ContractInsertPrecios.Columnas.MANUFACTURER,
            ContractInsertPrecios.Columnas.POS_NAME,
            ContractInsertPrecios.Columnas.PLATAFORMA,
            ContractInsertPrecios.Columnas.FOTO,
    };
//NUEVO INSERT PARA EL NUEVO MODULO DE MUESTRAS MEDICAS MPIN
    public static final String[] PROJECTION_INSERTMUESTRAS= new String[]{
        ContractInsertMuestras.Columnas._ID,
        Constantes.ID_REMOTA,
        ContractInsertMuestras.Columnas.PHARMA_ID,
        ContractInsertMuestras.Columnas.CODIGO,
        ContractInsertMuestras.Columnas.USUARIO,
        ContractInsertMuestras.Columnas.SUPERVISOR,
        ContractInsertMuestras.Columnas.FECHA,
        ContractInsertMuestras.Columnas.HORA,
        ContractInsertMuestras.Columnas.MARCA,
        ContractInsertMuestras.Columnas.SKU,
        ContractInsertMuestras.Columnas.TIPO_ACTIVIDAD,
        ContractInsertMuestras.Columnas.CANTIDAD,
        ContractInsertMuestras.Columnas.POS_NAME,
        Constantes.PENDIENTE_INSERCION
};
    //NUEVO INSERT PARA EL NUEVO MODULO DE PROBADORES MPIN
    public static final String[] PROJECTION_INSERT_PROBADORES= new String[]{
            ContractInsertProbadores.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertProbadores.Columnas.PHARMA_ID,
            ContractInsertProbadores.Columnas.CODIGO,
            ContractInsertProbadores.Columnas.USUARIO,
            ContractInsertProbadores.Columnas.SUPERVISOR,
            ContractInsertProbadores.Columnas.FECHA,
            ContractInsertProbadores.Columnas.HORA,
            ContractInsertProbadores.Columnas.TIPO_REGISTRO,
            ContractInsertProbadores.Columnas.MARCA,
            ContractInsertProbadores.Columnas.SKU,
            ContractInsertProbadores.Columnas.CANTIDAD,
            ContractInsertProbadores.Columnas.LOTE,
            ContractInsertProbadores.Columnas.FECHA_VENCIMIENTO,
            ContractInsertProbadores.Columnas.FABRICANTE,
            ContractInsertProbadores.Columnas.FOTO,
            ContractInsertProbadores.Columnas.COMENTARIO
    };

    public static final String[] PROJECTION_INSERT_PACKS = new String[]{
            ContractInsertPacks.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertPacks2.Columnas.PHARMA_ID,
            ContractInsertPacks2.Columnas.CODIGO,
            ContractInsertPacks2.Columnas.USUARIO,
            ContractInsertPacks2.Columnas.SUPERVISOR,
            ContractInsertPacks2.Columnas.FECHA,
            ContractInsertPacks2.Columnas.HORA,
            ContractInsertPacks2.Columnas.CATEGORIA,
            ContractInsertPacks2.Columnas.SUBCATEGORIA,
            ContractInsertPacks2.Columnas.PRESENTACION,
            ContractInsertPacks2.Columnas.BRAND,
            ContractInsertPacks2.Columnas.SKU_CODE,
            ContractInsertPacks2.Columnas.CATEGORIASEC,
            ContractInsertPacks2.Columnas.SUBCATEGORIASEC,
            ContractInsertPacks2.Columnas.PRESENTACIONSEC,
            ContractInsertPacks2.Columnas.BRANDSEC,
            ContractInsertPacks2.Columnas.SKU_CODESEC,
            ContractInsertPacks2.Columnas.PVC,
            ContractInsertPacks2.Columnas.CANTIDAD_ARMADA,
            ContractInsertPacks2.Columnas.CANTIDAD_ENCONTRADA,
            ContractInsertPacks2.Columnas.DESCRIPCION,
            ContractInsertPacks2.Columnas.OBSERVACION,
            ContractInsertPacks2.Columnas.FOTO,
            ContractInsertPacks2.Columnas.FOTO_GUIA,
            ContractInsertPacks2.Columnas.MANUFACTURER,
            ContractInsertPacks2.Columnas.POS_NAME,
    };

    public static final String[] PROJECTION_INSERT_PROD_CADUCAR = new String[]{
            ContractInsertProdCaducar.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertProdCaducar.Columnas.PHARMA_ID,
            ContractInsertProdCaducar.Columnas.CODIGO,
            ContractInsertProdCaducar.Columnas.USUARIO,
            ContractInsertProdCaducar.Columnas.SUPERVISOR,
            ContractInsertProdCaducar.Columnas.FECHA,
            ContractInsertProdCaducar.Columnas.HORA,
            ContractInsertProdCaducar.Columnas.CATEGORIA,
            ContractInsertProdCaducar.Columnas.SUBCATEGORIA,
            ContractInsertProdCaducar.Columnas.BRAND,
            ContractInsertProdCaducar.Columnas.SKU_CODE,
            ContractInsertProdCaducar.Columnas.FECHA_PROD,
            ContractInsertProdCaducar.Columnas.CANTIDAD_PROD,
            ContractInsertProdCaducar.Columnas.MANUFACTURER
    };

    public static final String[] PROJECTION_INSERT_SUGERIDOS = new String[]{
            ContractInsertSugeridos.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertSugeridos.Columnas.PHARMA_ID,
            ContractInsertSugeridos.Columnas.CODIGO,
            ContractInsertSugeridos.Columnas.USUARIO,
            ContractInsertSugeridos.Columnas.SUPERVISOR,
            ContractInsertSugeridos.Columnas.FECHA,
            ContractInsertSugeridos.Columnas.HORA,
            ContractInsertSugeridos.Columnas.LOCAL,
            ContractInsertSugeridos.Columnas.CODIGO_FABRIL,
            ContractInsertSugeridos.Columnas.VENDEDOR_FABRIL,
            ContractInsertSugeridos.Columnas.CATEGORIA,
            ContractInsertSugeridos.Columnas.SUBCATEGORIA,
            ContractInsertSugeridos.Columnas.BRAND,
            ContractInsertSugeridos.Columnas.SKU_CODE,
            ContractInsertSugeridos.Columnas.QUIEBRE,
            ContractInsertSugeridos.Columnas.UNIDAD_DISPONIBLE,
            ContractInsertSugeridos.Columnas.SUGERIDO,
            ContractInsertSugeridos.Columnas.CANTIDAD,
            ContractInsertSugeridos.Columnas.OBSERVACIONES,
            ContractInsertSugeridos.Columnas.ENTREGA
    };


    public static final String[] PROJECTION_INSERT_TAREAS = new String[]{
            ContractInsertTareas.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertTareas.Columnas.PHARMA_ID,
            ContractInsertTareas.Columnas.CODIGO,
            ContractInsertTareas.Columnas.USUARIO,
            ContractInsertTareas.Columnas.SUPERVISOR,
            ContractInsertTareas.Columnas.FECHA,
            ContractInsertTareas.Columnas.HORA,
            ContractInsertTareas.Columnas.CHANNEL,
            ContractInsertTareas.Columnas.CODIGOPDV,
            ContractInsertTareas.Columnas.MERCADERISTA,
            ContractInsertTareas.Columnas.TAREAS,
            ContractInsertTareas.Columnas.REALIZADO,
            ContractInsertTareas.Columnas.FOTO
    };


    public static final String[] PROJECTION_INSERT_ROTACION= new String[]{
            ContractInsertRotacion.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertRotacion.Columnas.PHARMA_ID,
            ContractInsertRotacion.Columnas.CODIGO,
            ContractInsertRotacion.Columnas.USUARIO,
            ContractInsertRotacion.Columnas.SUPERVISOR,
            ContractInsertRotacion.Columnas.FECHA,
            ContractInsertRotacion.Columnas.HORA,
            ContractInsertRotacion.Columnas.CATEGORIA,
            ContractInsertRotacion.Columnas.PRODUCTO,
            ContractInsertRotacion.Columnas.PROMOCIONAL,
            ContractInsertRotacion.Columnas.MECANICA,
            ContractInsertRotacion.Columnas.PESO,
            ContractInsertRotacion.Columnas.CANTIDAD,
            ContractInsertRotacion.Columnas.FECHA_ROT,
            ContractInsertRotacion.Columnas.FOTO_GUIA,
            ContractInsertRotacion.Columnas.OBSERVACIONES
    };

    public static final String[] PROJECTION_INSERTVALORES = new String[]{
            ContractInsertValores.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertValores.Columnas.PHARMA_ID,
            ContractInsertValores.Columnas.CODIGO,
            ContractInsertValores.Columnas.USUARIO,
            ContractInsertValores.Columnas.SUPERVISOR,
            ContractInsertValores.Columnas.FECHA,
            ContractInsertValores.Columnas.HORA,
            ContractInsertValores.Columnas.SECTOR,
            ContractInsertValores.Columnas.CATEGORIA,
            ContractInsertValores.Columnas.SEGMENTO1,
            //ContractInsertValores.Columnas.POFERTA,
            ContractInsertValores.Columnas.BRAND,
            //ContractInsertValores.Columnas.TAMANO,
            //ContractInsertValores.Columnas.CANTIDAD,
            ContractInsertValores.Columnas.SKU_CODE,
            ContractInsertValores.Columnas.CODIFICA ,
            ContractInsertValores.Columnas.AUSENCIA,
            ContractInsertValores.Columnas.DISPONIBLE,
            ContractInsertValores.Columnas.RESPONSABLE,
            ContractInsertValores.Columnas.RAZONES,
            ContractInsertValores.Columnas.SUGERIDO,
            ContractInsertValores.Columnas.TIPO_SUGERIDO,
            ContractInsertValores.Columnas.PVP,
            ContractInsertValores.Columnas.PVC,
            ContractInsertValores.Columnas.POFERTA,
            ContractInsertValores.Columnas.MANUFACTURER,
            ContractInsertValores.Columnas.QUIEBRE_PERCHA,
            ContractInsertValores.Columnas.QUIEBRE_BODEGA,
            ContractInsertValores.Columnas.POS_NAME,
            ContractInsertValores.Columnas.PLATAFORMA
    };

    public static final String[] PROJECTION_INSERT_CODIFICADOS = new String[]{
            ContractInsertCodificados.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertCodificados.Columnas.PHARMA_ID,
            ContractInsertCodificados.Columnas.CODIGO,
            ContractInsertCodificados.Columnas.POS_NAME,
            ContractInsertCodificados.Columnas.USUARIO,
            ContractInsertCodificados.Columnas.SUPERVISOR,
            ContractInsertCodificados.Columnas.FECHA,
            ContractInsertCodificados.Columnas.HORA,
            ContractInsertCodificados.Columnas.SECTOR,
            ContractInsertCodificados.Columnas.CATEGORIA,
            ContractInsertCodificados.Columnas.SUBCATEGORIA,
            ContractInsertCodificados.Columnas.PRESENTACION,
            ContractInsertCodificados.Columnas.FABRICANTE,
            ContractInsertCodificados.Columnas.BRAND,
            ContractInsertCodificados.Columnas.CONTENIDO,
            ContractInsertCodificados.Columnas.VARIANTE,
            ContractInsertCodificados.Columnas.SKU_CODE,
            ContractInsertCodificados.Columnas.PRESENCIA
    };

    public static final String[] PROJECTION_INSERTPROMO = new String[]{
            ContractInsertPromocion.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertPromocion.Columnas.PHARMA_ID,
            ContractInsertPromocion.Columnas.CODIGO,
            ContractInsertPromocion.Columnas.USUARIO,
            ContractInsertPromocion.Columnas.SUPERVISOR,
            ContractInsertPromocion.Columnas.FECHA,
            ContractInsertPromocion.Columnas.HORA,
            ContractInsertPromocion.Columnas.CATEGORIA,
            ContractInsertPromocion.Columnas.SUBCATEGORIA,
            ContractInsertPromocion.Columnas.MARCA,
            ContractInsertPromocion.Columnas.OTRAS_MARCAS,
            ContractInsertPromocion.Columnas.CANAL ,
            ContractInsertPromocion.Columnas.TIPO_PROMOCION ,
            ContractInsertPromocion.Columnas.DESCRIPCION_PROMOCION ,
            ContractInsertPromocion.Columnas.MECANICA ,
            ContractInsertPromocion.Columnas.INI_PROMO,
            ContractInsertPromocion.Columnas.FIN_PROMO,
            ContractInsertPromocion.Columnas.AGOTAR_STOCK,
            ContractInsertPromocion.Columnas.PVC_ANTERIOR,
            ContractInsertPromocion.Columnas.PVC_ACTUAL,
            ContractInsertPromocion.Columnas.FOTO,
            ContractInsertPromocion.Columnas.MANUFACTURER,
            ContractInsertPromocion.Columnas.SKU,
            ContractInsertPromocion.Columnas.POS_NAME,
            ContractInsertPromocion.Columnas.PLATAFORMA,
            ContractInsertPromocion.Columnas.CAMPANA
    };


    public static final String[] PROJECTION_INSERTIMPLEM = new String[]{
            ContractInsertImplementacion.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertImplementacion.Columnas.USUARIO,
            ContractInsertImplementacion.Columnas.FECHA,
            ContractInsertImplementacion.Columnas.HORA,
            ContractInsertImplementacion.Columnas.CIUDAD,
            ContractInsertImplementacion.Columnas.CANAL,
            ContractInsertImplementacion.Columnas.CLIENTE,
            ContractInsertImplementacion.Columnas.FORMATO,
            ContractInsertImplementacion.Columnas.ZONA,
            ContractInsertImplementacion.Columnas.PDV,
            ContractInsertImplementacion.Columnas.DIRECCION,
            ContractInsertImplementacion.Columnas.LOCAL,
            ContractInsertImplementacion.Columnas.LATITUD,
            ContractInsertImplementacion.Columnas.LONGITUD,
            ContractInsertImplementacion.Columnas.FOTO
    };

    public static final String[] PROJECTION_INSERTAGOTADOS = new String[]{
            ContractInsertAgotados.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertAgotados.Columnas.PHARMA_ID,
            ContractInsertAgotados.Columnas.CODIGO,
            ContractInsertAgotados.Columnas.USUARIO,
            ContractInsertAgotados.Columnas.SUPERVISOR,
            ContractInsertAgotados.Columnas.TIEMPO_INICIO,
            ContractInsertAgotados.Columnas.TIEMPO_FIN,
            ContractInsertAgotados.Columnas.FECHA,
            ContractInsertAgotados.Columnas.HORA
    };

    public static final String[] PROJECTION_INSERTVENTA = new String[]{
            ContractInsertVenta.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertVenta.Columnas.PHARMA_ID,
            ContractInsertVenta.Columnas.CODIGO,
            ContractInsertVenta.Columnas.USUARIO,
            ContractInsertVenta.Columnas.SUPERVISOR,
            ContractInsertVenta.Columnas.CATEGORIA,
            ContractInsertVenta.Columnas.SUBCATEGORIA,
            ContractInsertVenta.Columnas.SKU,
            ContractInsertVenta.Columnas.POS_NAME,
            ContractInsertVenta.Columnas.TIPO_FACTURA,
            ContractInsertVenta.Columnas.TIPO_VENTA,
            ContractInsertVenta.Columnas.TOTAL_FACTURA,
            ContractInsertVenta.Columnas.NUMERO_FACTURA,
            ContractInsertVenta.Columnas.ENTREGO_PROMOCIONAL,
            ContractInsertVenta.Columnas.PROMOCIONAL,
            ContractInsertVenta.Columnas.CANT_PROMOCIONAL,
            ContractInsertVenta.Columnas.NUM_FACTURA,
            ContractInsertVenta.Columnas.MECANICA,
            ContractInsertVenta.Columnas.MONTO_FACTURA,
            ContractInsertVenta.Columnas.TOTAL_STOCK,
            ContractInsertVenta.Columnas.FECHA_VENTA,
            ContractInsertVenta.Columnas.KEY_IMAGE,
            ContractInsertVenta.Columnas.FOTO_ADICIONAL,
            ContractInsertVenta.Columnas.FOTO_ACTIVIDAD,
            ContractInsertVenta.Columnas.COMENTARIO_FACTURA,
            ContractInsertVenta.Columnas.COMENTARIO_ADICIONAL,
            ContractInsertVenta.Columnas.COMENTARIO_ACTIVIDAD,
          //  ContractInsertVenta.Columnas.KEY_IMAGE,
            ContractInsertVenta.Columnas.FECHA,
            ContractInsertVenta.Columnas.HORA
    };

    public static final String[] PROJECTION_INSERTSHARE = new String[]{
            ContractInsertShare.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertShare.Columnas.PHARMA_ID,
            ContractInsertShare.Columnas.CODIGO,
            ContractInsertShare.Columnas.USUARIO,
            ContractInsertShare.Columnas.SUPERVISOR,
            ContractInsertShare.Columnas.FECHA,
            ContractInsertShare.Columnas.HORA,
            ContractInsertShare.Columnas.SECTOR,
            ContractInsertShare.Columnas.CATEGORIA,
            ContractInsertShare.Columnas.SEGMENTO,
            ContractInsertShare.Columnas.MARCA_SELECCIONADA,
            ContractInsertShare.Columnas.BRAND,
            ContractInsertShare.Columnas.CTMS_PERCHA,
            ContractInsertShare.Columnas.CTMS_MARCA,
            ContractInsertShare.Columnas.OTROS,
            ContractInsertShare.Columnas.CUMPLIMIENTO,
            ContractInsertShare.Columnas.MANUFACTURER,
            ContractInsertShare.Columnas.RAZONES,
            ContractInsertShare.Columnas.FOTO,
            ContractInsertShare.Columnas.POS_NAME,
            ContractInsertShare.Columnas.PLATAFORMA
    };

    public static final String[] PROJECTION_INSERT_PDI = new String[]{
            ContractInsertPDI.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertPDI.Columnas.PHARMA_ID,
            ContractInsertPDI.Columnas.CODIGO,
            ContractInsertPDI.Columnas.USUARIO,
            ContractInsertPDI.Columnas.SUPERVISOR,
            ContractInsertPDI.Columnas.FECHA,
            ContractInsertPDI.Columnas.HORA,
            ContractInsertPDI.Columnas.CATEGORIA,
            ContractInsertPDI.Columnas.MARCA_SELECCIONADA,
            ContractInsertPDI.Columnas.CUMPLIMIENTO,
            ContractInsertPDI.Columnas.UNIVERSO,
            ContractInsertPDI.Columnas.CARAS,
            ContractInsertPDI.Columnas.OTROS,
            ContractInsertPDI.Columnas.OBJ_CATEGORIA,
            ContractInsertPDI.Columnas.PART_CATEGORIA,
            ContractInsertPDI.Columnas.FOTO,
            ContractInsertPDI.Columnas.CANAL,
            ContractInsertPDI.Columnas.POS_NAME,
            ContractInsertPDI.Columnas.PLATAFORMA
    };

    public static final String[] PROJECTION_INSERTPDV = new String[]{
            ContractInsertPdv.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertPdv.Columnas.IDPDV,
            ContractInsertPdv.Columnas.ESTADOVISITA,
            ContractInsertPdv.Columnas.NOVEDADES,
            ContractInsertPdv.Columnas.FOTO,
            ContractInsertPdv.Columnas.FECHA,
            ContractInsertPdv.Columnas.HORA
    };

    public static final String[] PROJECTION_INSERTEXH = new String[]{
            ContractInsertExh.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertExh.Columnas.PHARMA_ID,
            ContractInsertExh.Columnas.CODIGO,
            ContractInsertExh.Columnas.USUARIO,
            ContractInsertExh.Columnas.SUPERVISOR,
            ContractInsertExh.Columnas.FECHA,
            ContractInsertExh.Columnas.HORA,
            ContractInsertExh.Columnas.SECTOR,
            ContractInsertExh.Columnas.CATEGORIA,
            ContractInsertExh.Columnas.SUBCATEGORIA,
            ContractInsertExh.Columnas.SEGMENTO,
            ContractInsertExh.Columnas.BRAND,
            ContractInsertExh.Columnas.TIPO_EXH,
            ContractInsertExh.Columnas.FABRICANTE,
            ContractInsertExh.Columnas.ZONA_EX,
            ContractInsertExh.Columnas.NIVEL,
            ContractInsertExh.Columnas.TIPO,
            ContractInsertExh.Columnas.CONTRATADA,
            ContractInsertExh.Columnas.CONDICION,
            ContractInsertExh.Columnas.FOTO,
            ContractInsertExh.Columnas.COMENTARIO_REVISOR,
            ContractInsertExh.Columnas.POS_NAME,
            ContractInsertExh.Columnas.PLATAFORMA
    };
    public static final String[] PROJECTION_INSERTEXHIBICION_COLGATE = new String[]{
            InsertExhBassa.Columnas._ID,
            Constantes.ID_REMOTA,
            InsertExhBassa.Columnas.ID_PDV,
            InsertExhBassa.Columnas.ID_R_EXH,
            InsertExhBassa.Columnas.USUARIO,
            InsertExhBassa.Columnas.NOMBRE,
            InsertExhBassa.Columnas.SUPERVISOR,
            InsertExhBassa.Columnas.NOMBRE_PDV,
            InsertExhBassa.Columnas.CODIGO,
            InsertExhBassa.Columnas.REPORTE_NUEVA,
            InsertExhBassa.Columnas.REPORTE_MANT,
            InsertExhBassa.Columnas.HERRAMIENTA,
            InsertExhBassa.Columnas.TIPO_EXHIBICION,
            InsertExhBassa.Columnas.FABRICANTE,
            InsertExhBassa.Columnas.TIPO_HERRAMIENTA,
            InsertExhBassa.Columnas.TIPO_NOVEDAD,
            InsertExhBassa.Columnas.CONVENIO,
            InsertExhBassa.Columnas.ELIMINAR,
            InsertExhBassa.Columnas.SUBCAT,
            InsertExhBassa.Columnas.CAT,
            InsertExhBassa.Columnas.CAMPANA,
            InsertExhBassa.Columnas.OBSERVACION,
            InsertExhBassa.Columnas.CLASIFICACION,
            InsertExhBassa.Columnas.NUMEROEXH,
            InsertExhBassa.Columnas.RESPUESTA,
            InsertExhBassa.Columnas.KEY_IMAGE,
            InsertExhBassa.Columnas.FECHA,
            InsertExhBassa.Columnas.HORA,
            InsertExhBassa.Columnas.PUNTO_APOYO
    };
    public static final String[] PROJECTION_INSERTEXHIBICION_AD_NU = new String[]{
            InsertExhibicionesAdNu.Columnas._ID,
            Constantes.ID_REMOTA,
            InsertExhibicionesAdNu.Columnas.ID_PDV,
            InsertExhibicionesAdNu.Columnas.USUARIO,
            InsertExhibicionesAdNu.Columnas.NOMBRE,
            InsertExhibicionesAdNu.Columnas.SUPERVISOR,
            InsertExhibicionesAdNu.Columnas.NOMBRE_PDV,
            InsertExhibicionesAdNu.Columnas.CODIGO,
            InsertExhibicionesAdNu.Columnas.TIPO_HERRAMIENTA,
            InsertExhibicionesAdNu.Columnas.TIPO,
            InsertExhibicionesAdNu.Columnas.FABRICANTE,
            InsertExhibicionesAdNu.Columnas.CATEGORIA,
            InsertExhibicionesAdNu.Columnas.TIPO_NOVEDAD,
            InsertExhibicionesAdNu.Columnas.SUBCAT,
            InsertExhibicionesAdNu.Columnas.ES_CORRECCION,
            InsertExhibicionesAdNu.Columnas.ZONAEXH,
            InsertExhibicionesAdNu.Columnas.PERSONALIZACION,
            InsertExhibicionesAdNu.Columnas.ESTRELLA,
            InsertExhibicionesAdNu.Columnas.NUMEROEXH,
            InsertExhibicionesAdNu.Columnas.OBSERVACION,
            InsertExhibicionesAdNu.Columnas.OBSERVACION_CORRECCION,
            InsertExhibicionesAdNu.Columnas.KEY_IMAGE,
            InsertExhibicionesAdNu.Columnas.FECHA,
            InsertExhibicionesAdNu.Columnas.HORA,
            InsertExhibicionesAdNu.Columnas.IMEI,
            InsertExhibicionesAdNu.Columnas.CLASIFICACION_PRIMARIA,
            InsertExhibicionesAdNu.Columnas.CLASIFICACION_SECUNDARIA,
            InsertExhibicionesAdNu.Columnas.PUNTO_APOYO
    };
    public static final String[] PROJECTION_EXH_SUPERVISOR = new String[]{
            ContractExhibicionSupervisor.Columnas._ID,
            ContractExhibicionSupervisor.Columnas.ID_REMOTA,
            ContractExhibicionSupervisor.Columnas.CP_CODE,
            ContractExhibicionSupervisor.Columnas.CANAL,
            ContractExhibicionSupervisor.Columnas.SUPERVISOR,
            ContractExhibicionSupervisor.Columnas.EXHIBITION_TOOL,
            ContractExhibicionSupervisor.Columnas.MANUFACTURER,
            ContractExhibicionSupervisor.Columnas.CATEGORY,
            ContractExhibicionSupervisor.Columnas.SUBCATEGORY,
            ContractExhibicionSupervisor.Columnas.ZONE_EXHIBITION,
            ContractExhibicionSupervisor.Columnas.PERSONALIZATION,
            ContractExhibicionSupervisor.Columnas.PRICE_TAG,
            ContractExhibicionSupervisor.Columnas.NUM_EXHIBITIONS,
            ContractExhibicionSupervisor.Columnas.OBSERVATION,
            ContractExhibicionSupervisor.Columnas.PHOTO,
            ContractExhibicionSupervisor.Columnas.DATE,
            ContractExhibicionSupervisor.Columnas.VISUAL_ACCESS,
            ContractExhibicionSupervisor.Columnas.PHOTO2,
            ContractExhibicionSupervisor.Columnas.TIPO_HERRAMIENTA,
            ContractExhibicionSupervisor.Columnas.CAMPANA,
            ContractExhibicionSupervisor.Columnas.CONVENIO,
            ContractExhibicionSupervisor.Columnas.CLASIFICACION
    };

    public static final String[] PROJECTION_EVALUACION_GESTOR = new String[]{
            ContractEvaluacionEncuesta.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractEvaluacionEncuesta.Columnas.NOMBRE_ENCUESTA,
            ContractEvaluacionEncuesta.Columnas.DESCRIPCION,
            ContractEvaluacionEncuesta.Columnas.CATEGORIA,
            ContractEvaluacionEncuesta.Columnas.RE,
            ContractEvaluacionEncuesta.Columnas.PREGUNTA,
            ContractEvaluacionEncuesta.Columnas.TIPO_PREGUNTA,
            ContractEvaluacionEncuesta.Columnas.OPC_A,
            ContractEvaluacionEncuesta.Columnas.OPC_B,
            ContractEvaluacionEncuesta.Columnas.OPC_C,
            ContractEvaluacionEncuesta.Columnas.OPC_D,
            ContractEvaluacionEncuesta.Columnas.OPC_E,
            ContractEvaluacionEncuesta.Columnas.FOTO,
            ContractEvaluacionEncuesta.Columnas.TIPO_CAMPO,
            ContractEvaluacionEncuesta.Columnas.PUNTAJE_POR_PREGUNTA,
            ContractEvaluacionEncuesta.Columnas.HABILITADO
    };
    public static final String[] PROJECTION_INSERT_EVALUACION_DEMO = new String[]{
            ContractInsertEvaluacionEncuesta.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertEvaluacionEncuesta.Columnas.CODIGO,
            ContractInsertEvaluacionEncuesta.Columnas.USUARIO,
            ContractInsertEvaluacionEncuesta.Columnas.CIUDAD,
            ContractInsertEvaluacionEncuesta.Columnas.LOCAL,
            ContractInsertEvaluacionEncuesta.Columnas.GESTOR_ASIGNADO,
            ContractInsertEvaluacionEncuesta.Columnas.NOMBRE_ENCUESTA,
            ContractInsertEvaluacionEncuesta.Columnas.CATEGORIA,
            ContractInsertEvaluacionEncuesta.Columnas.RE,
            ContractInsertEvaluacionEncuesta.Columnas.PREGUNTA,
            ContractInsertEvaluacionEncuesta.Columnas.TIPO_PREGUNTA,
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
            ContractInsertEvaluacionEncuesta.Columnas.HORA
    };

    public static final String[] PROJECTION_TIPO_NOVEDADES = new String[]{
            ContractTipoNovedades.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractTipoNovedades.Columnas.TIPO_NOVEDAD,
            ContractTipoNovedades.Columnas.CUENTA
    };
    public static final String[] PROJECTION_CANALES_FOTOS = new String[]{
            ContractCanalesFotos.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractCanalesFotos.Columnas.PDV,
            ContractCanalesFotos.Columnas.CANAL,
            ContractCanalesFotos.Columnas.ACTIVAR
    };

    public static final String[] PROJECTION_INSERT_NOVEDADES = new String[]{
            ContractInsertNovedades.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertNovedades.Columnas.PHARMA_ID,
            ContractInsertNovedades.Columnas.CODIGO,
            ContractInsertNovedades.Columnas.USUARIO,
            ContractInsertNovedades.Columnas.SUPERVISOR,
            ContractInsertNovedades.Columnas.POS_NAME,
            ContractInsertNovedades.Columnas.TIPO_NOVEDAD,
            ContractInsertNovedades.Columnas.MARCA,
            ContractInsertNovedades.Columnas.LOTE,
            ContractInsertNovedades.Columnas.SKU,
            ContractInsertNovedades.Columnas.TIPO,
            ContractInsertNovedades.Columnas.FECHA_VENCIMIENTO,
            ContractInsertNovedades.Columnas.FECHA_ELABORACION,
            ContractInsertNovedades.Columnas.NUMERO_FACTURA,
            ContractInsertNovedades.Columnas.COMENTARIO_LOTE,
            ContractInsertNovedades.Columnas.COMENTARIO_FACTURA,
            ContractInsertNovedades.Columnas.COMENTARIO_SKU,
            ContractInsertNovedades.Columnas.OBSERVACION,
            ContractInsertNovedades.Columnas.TIPO_IMPLEMENTACION,
            ContractInsertNovedades.Columnas.MECANICA,
            ContractInsertNovedades.Columnas.FECHA_INICIO,
            ContractInsertNovedades.Columnas.AGOTAR_STOCK,
            ContractInsertNovedades.Columnas.PRECIO_ANTERIOR,
            ContractInsertNovedades.Columnas.PRECIO_PROMOCION,
            ContractInsertNovedades.Columnas.FOTO,
            ContractInsertNovedades.Columnas.SKU_CODE,
            ContractInsertNovedades.Columnas.CANTIDAD,
            ContractInsertNovedades.Columnas.FOTO_PRODUCTO,
            ContractInsertNovedades.Columnas.FOTO_FACTURA,
            ContractInsertNovedades.Columnas.FECHA,
            ContractInsertNovedades.Columnas.HORA,
            ContractInsertNovedades.Columnas.CUENTA
    };

    public static final String[] PROJECTION_INSERTFOT = new String[]{
            ContractInsertFotografico.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertFotografico.Columnas.PHARMA_ID,
            ContractInsertFotografico.Columnas.CODIGO,
            ContractInsertFotografico.Columnas.POS_NAME,
            ContractInsertFotografico.Columnas.USUARIO,
            ContractInsertFotografico.Columnas.CATEGORIA,
            ContractInsertFotografico.Columnas.SUBCATEGORIA,
            ContractInsertFotografico.Columnas.MARCA,
            ContractInsertFotografico.Columnas.LOGRO,
            ContractInsertFotografico.Columnas.STATUS,
            ContractInsertFotografico.Columnas.OBSERVACION,
            ContractInsertFotografico.Columnas.KEY_IMAGE,
            ContractInsertFotografico.Columnas.FECHA,
            ContractInsertFotografico.Columnas.HORA,
            ContractInsertFotografico.Columnas.TIPO_EXH,
            ContractInsertFotografico.Columnas.CONTRATADA
    };


    public static final String[] PROJECTION_INSERTFLOORING = new String[]{
            InsertFlooring.Columnas._ID,
            Constantes.ID_REMOTA,
            InsertFlooring.Columnas.PHARMA_ID,
            InsertFlooring.Columnas.CODIGO,
            InsertFlooring.Columnas.USUARIO,
            InsertFlooring.Columnas.SUPERVISOR,
            InsertFlooring.Columnas.FECHA,
            InsertFlooring.Columnas.HORA,
            InsertFlooring.Columnas.SECTOR,
            InsertFlooring.Columnas.CATEGORIA,
            InsertFlooring.Columnas.SUBCATEGORIA,
            InsertFlooring.Columnas.PRESENTACION,
            InsertFlooring.Columnas.BRAND,
            InsertFlooring.Columnas.CONTENIDO,
            InsertFlooring.Columnas.SKU_CODE,
            InsertFlooring.Columnas.INVENTARIOS,
            InsertFlooring.Columnas.SEMANA,
            InsertFlooring.Columnas.SUGERIDOS,
            InsertFlooring.Columnas.TIPO,
            InsertFlooring.Columnas.ENTREGA,
            InsertFlooring.Columnas.CAUSAL,
            InsertFlooring.Columnas.OTROS,
            InsertFlooring.Columnas.FECHA_CADUCIDAD,
            InsertFlooring.Columnas.POS_NAME,
            InsertFlooring.Columnas.PLATAFORMA,
            InsertFlooring.Columnas.MOTIVO_SUGERIDO
    };

    public static final String[] PROJECTION_INSERT_VENTA2 = new String[]{
            ContractInsertVentas2.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertVentas2.Columnas.PHARMA_ID,
            ContractInsertVentas2.Columnas.CODIGO,
            ContractInsertVentas2.Columnas.USUARIO,
            ContractInsertVentas2.Columnas.SUPERVISOR,
            ContractInsertVentas2.Columnas.FECHA,
            ContractInsertVentas2.Columnas.HORA,
            ContractInsertVentas2.Columnas.NOMBRE_IMPULSADOR,
            ContractInsertVentas2.Columnas.FECHA_VENTA,
            ContractInsertVentas2.Columnas.CATEGORIA,
            ContractInsertVentas2.Columnas.SUBCATEGORIA,
            ContractInsertVentas2.Columnas.PRESENTACION,
            ContractInsertVentas2.Columnas.POFERTA,
            ContractInsertVentas2.Columnas.BRAND,
            /*ContractInsertVentas2.Columnas.TAMANO,*/
            ContractInsertVentas2.Columnas.SKU_CODE,
            ContractInsertVentas2.Columnas.TIPO_VENTA,
            ContractInsertVentas2.Columnas.STOCK_INICIAL,
            ContractInsertVentas2.Columnas.CANTIDAD,
            ContractInsertVentas2.Columnas.PREGULAR,
            ContractInsertVentas2.Columnas.PPROMOCION,
            ContractInsertVentas2.Columnas.STOCK_FINAL,
            ContractInsertVentas2.Columnas.VALOR_TOTAL,
            ContractInsertVentas2.Columnas.TOTAL_FACTURA,
            ContractInsertVentas2.Columnas.NUMERO_FACTURA,
            ContractInsertVentas2.Columnas.ENTREGO_PROMOCIONAL,
            ContractInsertVentas2.Columnas.PROMOCIONAL,
            ContractInsertVentas2.Columnas.CANT_PROMOCIONAL,
            ContractInsertVentas2.Columnas.MANUFACTURER,
            ContractInsertVentas2.Columnas.FOTO,
            ContractInsertVentas2.Columnas.FOTO_ADICIONAL,
            ContractInsertVentas2.Columnas.CUENTA
    };

    public static final String[] PROJECTION_INSERT_IMPULSO = new String[]{
            ContractInsertImpulso.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertImpulso.Columnas.PHARMA_ID,
            ContractInsertImpulso.Columnas.CODIGO,
            ContractInsertImpulso.Columnas.USUARIO,
            ContractInsertImpulso.Columnas.SUPERVISOR,
            ContractInsertImpulso.Columnas.FECHA,
            ContractInsertImpulso.Columnas.HORA,
            ContractInsertImpulso.Columnas.CATEGORIA,
            ContractInsertImpulso.Columnas.BRAND,
            ContractInsertImpulso.Columnas.SKU_CODE,
            ContractInsertImpulso.Columnas.CANTIDAD_ASIGNADA,
            ContractInsertImpulso.Columnas.CANTIDAD_VENDIDA,
            ContractInsertImpulso.Columnas.CANTIDAD_ADICIONAL,
            ContractInsertImpulso.Columnas.CUMPLIMIENTO,
            ContractInsertImpulso.Columnas.IMPULSADORA,
            ContractInsertImpulso.Columnas.OBSERVACION,
            ContractInsertImpulso.Columnas.FOTO,
            ContractInsertImpulso.Columnas.POS_NAME,
            ContractInsertImpulso.Columnas.PRECIO_VENTA,
            ContractInsertImpulso.Columnas.ALERTA_STOCK,
            ContractInsertImpulso.Columnas.PLATAFORMA
    };

    public static final String[] PROJECTION_INSERTINICIAL= new String[]{
            ContractInsertInicial.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertInicial.Columnas.IDPDV,
            ContractInsertInicial.Columnas.CODIGO,
            ContractInsertInicial.Columnas.TIPO,
            ContractInsertInicial.Columnas.DEALER,
            ContractInsertInicial.Columnas.UBICACION,
            ContractInsertInicial.Columnas.CORREO,
            ContractInsertInicial.Columnas.LATITUD,
            ContractInsertInicial.Columnas.LONGITUD,
            ContractInsertInicial.Columnas.FECHA,
            ContractInsertInicial.Columnas.HORA
    };

    public static final String[] PROJECTION_INSERT_GPS = new String[]{
            ContractInsertGps.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertGps.Columnas.IDPDV,
            ContractInsertGps.Columnas.USUARIO,
            ContractInsertGps.Columnas.TIPO,
            ContractInsertGps.Columnas.VERSION,
            ContractInsertGps.Columnas.LATITUDE,
            ContractInsertGps.Columnas.LONGITUDE,
            ContractInsertGps.Columnas.FECHA,
            ContractInsertGps.Columnas.HORA,
            ContractInsertGps.Columnas.CAUSAL,
            ContractInsertGps.Columnas.FOTO,
            ContractInsertGps.Columnas.DISTANCIA,
            ContractInsertGps.Columnas.TIPO_RELEVO,
            ContractInsertGps.Columnas.POS_NAME,
            Constantes.ID_REMOTA_RUTA
    };

    public static final String[] PROJECTION_INSERT_RASTREO = new String[]{
            ContractInsertRastreo.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertRastreo.Columnas.USUARIO,
            ContractInsertRastreo.Columnas.LATITUD,
            ContractInsertRastreo.Columnas.LONGITUD,
            ContractInsertRastreo.Columnas.FECHA,
            ContractInsertRastreo.Columnas.HORA
    };

    public static final String[] PROJECTION_PROMOCIONAL_VENTAS = new String[]{
            ContractPromocionalVentas.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractPromocionalVentas.Columnas.CADENA,
            ContractPromocionalVentas.Columnas.PROMOCIONAL,
            ContractPromocionalVentas.Columnas.CUENTA
    };

    public static final String[] PROJECTION_INSERT_TRACKING = new String[]{
            ContractInsertTracking.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractInsertTracking.Columnas.PHARMA_ID,
            ContractInsertTracking.Columnas.FECHA,
            ContractInsertTracking.Columnas.HORA,
            ContractInsertTracking.Columnas.CODIGO,
            ContractInsertTracking.Columnas.LOCAL,
            ContractInsertTracking.Columnas.USUARIO,
            ContractInsertTracking.Columnas.LATITUD,
            ContractInsertTracking.Columnas.LONGITUD,
            ContractInsertTracking.Columnas.MECANICA,
            ContractInsertTracking.Columnas.CATEGORIA,
            ContractInsertTracking.Columnas.DESCRIPCION,
            ContractInsertTracking.Columnas.VIGENCIA,
            ContractInsertTracking.Columnas.STATUS_ACTIVIDAD,
            ContractInsertTracking.Columnas.COMENTARIO,
            ContractInsertTracking.Columnas.FOTO,
            ContractInsertTracking.Columnas.PRECIO_PROMOCION,
            ContractInsertTracking.Columnas.MATERIAL_POP,
            ContractInsertTracking.Columnas.IMPLEMENTACION_POP,
            ContractInsertTracking.Columnas.CUENTA,
            ContractInsertTracking.Columnas.MODULO,
            ContractInsertTracking.Columnas.UNIDAD_DE_NEGOCIO
    };


    public static final String[] PROJECTION_NOTIFICACION = new String[]{
            ContractNotificacion.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractNotificacion.Columnas.USER,
            ContractNotificacion.Columnas.SUPERVISOR,
            ContractNotificacion.Columnas.DESCRIPCION,
            ContractNotificacion.Columnas.FECHA,
            ContractNotificacion.Columnas.HORA
    };

    public static final String[] PROJECTION_LOG = new String[]{
            ContractLog.Columnas._ID,
            Constantes.ID_REMOTA,
            ContractLog.Columnas.USUARIO,
            ContractLog.Columnas.FECHA,
            ContractLog.Columnas.HORA,
            ContractLog.Columnas.ACCION
    };


    /*
    *** Indices para las columnas indicadas en la proyección
     */

    public static final int COLUMNA_ID = 0;
    public static final int COLUMNA_ID_REMOTA = 1;

    //PUNTO DE VENTAS
    public final static int CHANNEL = 2;
    public final static int SUBCHANNEL = 3;
    public final static int CHANNEL_SEGMENT = 4;
    public final static int FORMAT = 5;
    public final static int CUSTOMER_OWNER = 6;
    public final static int POS_ID = 7;
    public final static int POS_NAME = 8;
    public final static int POS_NAME_DPSM = 9;
    public final static int ZONA = 10;
    public final static int REGION = 11;
    public final static int PROVINCIA = 12;
    public final static int CIUDAD = 13;
    public final static int DIRECCION = 14;
    public final static int KAM = 15;
    public final static int SALES_EXECUTIVE = 16;
    public final static int MERCHANDISING = 17;
    public final static int SUPERVISOR = 18;
    public final static int ROL = 19;
    public final static int MERCADERISTA = 20;
    public final static int USUARIO = 21;
    public final static int DPSM = 22;
    public final static int STATUS = 23;
    public final static int TIPO_PDV = 24;
    public final static int LATITUD = 25;
    public final static int LONGITUD = 26;
    public final static int FOTO_PDV = 27;
    public final static int SEGMENTACION = 28;
    public final static int COMPRAS = 29;
    public final static int PASS = 30;
    public final static int NUMERO_CONTROLLER = 31;
    public final static int FECHA_VISITA = 32;
    public final static int DEVICE_ID = 33;
    public final static int PERIMETRO = 34;
    public final static int DISTANCIA = 35;
    public final static int TERMOMETRO = 36;

    //PRECIOS
    public static final int COLUMNA_PRODUCTO = 2;
    public static final int COLUMNA_SEGMENTO = 3;
    public static final int COLUMNA_MARCA = 4;
    public static final int COLUMNA_CATEGORIA = 5;
    public static final int COLUMNA_SUBCATEGORIA = 6;

    //MUESTRAS
//    public static final int COLUMNA_PRODUCTO = 2;
//    public static final int COLUMNA_SEGMENTO = 3;
//    public static final int COLUMNA_MARCA = 4;
//    public static final int COLUMNA_CATEGORIA = 5;
//    public static final int COLUMNA_SUBCATEGORIA = 6;


    //TIPO EXHIBICION
    public static final int CANAL_E = 2;
    public static final int EXHIBICION = 3;
    public static final int FOTO_E = 4;


    //CATEGORIA TIPO
    public static final int TIPO_CAT = 2;
    public static final int CANAL_CAT = 3;


    //MOTIVO SUGERIDO
    public static final int CANAL_M = 2;
    public static final int MOTIVO_M = 3;
    public static final int FOTO_M= 4;

    // TIPO NOVEDAD
    public final static int TIPO_NOVEDAD = 2;
    public final static int CUENTATP = 3;

    // TIPO canal fotos
    public final static int PDV = 2;
    public final static int COLUMNA_CANAL = 3;
    public final static int COLUMNA_ACTIVAR = 4;

    //TRACKING
    public static final int COLUMNA_CUSTOMER_T = 2;
    public static final int COLUMNA_CUENTAS_T = 3;
    public static final int COLUMNA_MECANICA_T = 4;
    public static final int COLUMNA_CATEGORIA_T = 5;
    public static final int COLUMNA_SUBCATEGORIA_T = 6;
    public static final int COLUMNA_MARCA_T = 7;
    public static final int COLUMNA_DESCRIPCION_T = 8;
    public static final int COLUMNA_PRECIO_PROMOCION_T = 9;
    public static final int COLUMNA_VIGENCIA_T = 10;
    public static final int COLUMNA_MATERIAL_POP_T = 11;


    //TESTS
    public static final int TEST = 2;
    public static final int DESCRIPCION = 3;
    public static final int F_INICIO = 4;
    public static final int H_INICIO = 5;
    public static final int F_LIMITE = 6;
    public static final int H_LIMITE = 7;
    public static final int ACTIVE = 8;

    //PREGUNTAS
    public static final int QUESTION = 2;
    public static final int ANSWER = 3;
    public static final int OPTA = 4;
    public static final int OPTB = 5;
    public static final int OPTC = 6;
    public static final int QCANAL = 7;
    public static final int QTIEMPO = 8;
    public static final int QTEST_ID = 9;

    // JUSTIFICACION
    public static final int JUSTIFICACION = 2;

    // TIPO INVENTARIO
    public static final int CAUSALESTIPOINV = 2;

    // LINK DE ONE DRIVE
    public static final int MODULO = 2;
    public static final int LINK = 3;

    //CAMPOS X MODULOS
    public final static int MODULO_C = 2;
    public final static int CAMPO_C = 3;
    public final static int OBLIGATORIO_C = 4;
    public final static int CUENTACM_C = 5;

    //PROMOCIONAL VENTAS
    public final static int CADENAPE = 2;
    public final static int PROMOCIONALPE = 3;
    public final static int CUENTAPE = 4;

    //PRODUCTOS
    //public final static int CODIGO_PRODUCTO= 2;
    public final static int SECTOR = 2;
    public final static int CATEGORY = 3;
    public final static int SUBCATEGORIA = 4;
    public final static int SEGMENTO = 5;
    public final static int PRESENTACION = 6;
    public final static int VARIANTE1 = 7;
    public final static int VARIANTE2 = 8;
    public final static int CONTENIDO = 9;
    public final static int SKU = 10;
    public final static int MARCA = 11;
    public final static int FABRICANTE = 12;
    public final static int PVP = 13;
    public final static int CADENAS = 14;
    public final static int FOTO = 15;
    public final static int PLATAFORMA = 16;
    //public final static int FORMAT_PRODUCTO= 14;

    //PRODUCTOS SECUNDARIOS ONPACKS
    //public final static int CODIGO_PRODUCTO= 2;
    public final static int SECTOR_S = 2;
    public final static int CATEGORY_S = 3;
    public final static int SUBCATEGORIA_S = 4;
    public final static int SEGMENTO_S = 5;
    public final static int PRESENTACION_S = 6;
    public final static int VARIANTE1_S = 7;
    public final static int VARIANTE2_S = 8;
    public final static int CONTENIDO_S = 9;
    public final static int SKU_S = 10;
    public final static int MARCA_S = 11;
    public final static int FABRICANTE_S = 12;
    public final static int PVP_S = 13;
    public final static int CADENAS_S = 14;
    public final static int FOTO_S = 15;
    public final static int PLATAFORMA_S = 16;

    //PROMO
    public final static int CANAL = 2;
    public final static int TIPO = 3;
    public final static int CADENA = 4;
    public final static int FABRICANTE_P = 5;
    public final static int DESCRIP = 6;
    public final static int CATEGORIA_P = 7;
    public final static int SUBCATEGORIA_P = 8;
    public final static int MARCA_P = 9;
    public final static int SKU_P = 10;
    public final static int CAMPANA_P = 11;

    //PRODUCTOS_AASS
    public final static int CATEGORIA_AASS = 2;
    public final static int SUBCATEGORIA_AASS = 3;
    public final static int MARCA_AASS = 4;
    public final static int FABRICANTE_AASS = 5;
    public final static int SKU_AASS = 6;
    public final static int CADENAS_AASS = 7;

    //PRODUCTOS_MAYO
    public final static int CODIGO_MAYO = 2;
    public final static int USUARIO_MAYO = 3;
    public final static int CATEGORIA_MAYO = 4;
    public final static int SUBCATEGORIA_MAYO = 5;
    public final static int MARCA_MAYO = 6;
    public final static int FABRICANTE_MAYO = 7;
    public final static int SKU_MAYO = 8;
    public final static int STATUS_MAYO = 9;

    //ROTACION
    public final static int ROT_CATEGORIA = 2;
    public final static int ROT_SUBCATEGORIA = 3;
    public final static int ROT_MARCA = 4;
    public final static int ROT_PRODUCTO = 5;
    public final static int ROT_PROMOCIONAL = 6;
    public final static int ROT_MECANICA = 7;
    public final static int ROT_PESO = 8;
    public final static int ROT_TIPO = 9;
    public final static int ROT_PLATAFORMA = 10;

    //POPSUGERIDO
    public static final int COLUMNA_CANAL_POP = 2;
    public static final int COLUMNA_CODIGO_PDV_POP = 3;
    public static final int COLUMNA_POP_SUGERIDO = 4;

    //PRIOTIRARIOS
    public static final int COLUMNA_CANAL_PRIORITARIOS = 2;
    public static final int COLUMNA_CODIGO_PDV_PRIORITARIOS = 3;
    public static final int COLUMNA_CATEGORIA_PRIORITARIOS = 4;
    public static final int COLUMNA_SUBCATEGORIA_PRIORITARIOS = 5;
    public static final int COLUMNA_MARCA_PRIORITARIOS = 6;
    public static final int COLUMNA_CONTENIDO_PRIORITARIOS = 7;
    public static final int COLUMNA_SKU_PRIORITARIOS = 8;

    //TAREAS
    public final static int CANALTAR = 2;
    public final static int CODIGOPDVTAR = 3;
    public final static int MERCADERISTATAR = 4;
    public final static int TAREAS = 5;
    public final static int PERIODO = 6;
    public final static int FECHA_INGRESOTAR = 7;

    //COMBO CANJES
    public final static int TIPO_COMBO = 2;
    public final static int MECANICA = 3;

    //CAUSAL MCI
    public final static int CAUSAL_MCI = 2;

    //CAUSAL OSA
    public final static int CANAL_OSA = 2;
    public final static int RESPONSABLE_OSA = 3;
    public final static int CAUSAL_OSA = 4;

    //ALERTAS
    public final static int TIPO_ALERTA = 2;
    public final static int CATEGORIA_MATERIAL = 3;
    public final static int MATERIALES = 4;

    //PDI
    public final static int CANAL_PDI = 2;
    public final static int CATEGORIA_PDI = 3;
    public final static int SUBCATEGORIA_PDI = 4;
    public final static int MARCA_PDI = 5;
    public final static int OBJETIVO_PDI = 6;
    public final static int PLATAFORMA_PDI = 7;

    //REPO CONVENIOS
    public static final int COLUMNA_CODIGO = 2;
    public static final int COLUMNA_PDV = 3;
    public static final int COLUMNA_CATEGORIA_C = 4;
    public static final int COLUMNA_UNIDAD_NEGOCIO = 5;
    public static final int COLUMNA_FABRICANTE = 6;
    public static final int COLUMNA_MARCA_C = 7;
    public static final int COLUMNA_TIPO_EXHIBICION = 8;
    public static final int COLUMNA_NUMERO_EXHIBICION = 9;
    public static final int COLUMNA_FORMATO = 10;
    public static final int COLUMNA_FECHA_SUBIDA = 11;
    public static final int COLUMNA_ENLACE = 12;


    // TIPO VENTAS mpin
    public static final int TIPO_VENTAS_NEW = 2;
    // TIPO IMPLEMENTACIONES mpin
    public static final int TIPO_IMPLEMENTACIONES = 2;
    // TIPO UNIDADES mpin
    public static final int TIPO_UNIDADES = 2;
    // TIPO ACTIVIDADES mpin
    public static final int TIPO_ACTIVIDADES = 2;
    // TIPO REGISTRO mpin
    public static final int TIPO_REGISTRO = 2;

    //  MODULOS POR ROLES
    public static final int TIPO_ROL = 2;
    public static final int TIPO_MODULO = 3;


    //EXHIBICION SUPERVISOR
    public static final int COLUMNA_EXH_CP_CODE = 2;
    public static final int COLUMNA_EXH_CANAL = 3;
    public static final int COLUMNA_EXH_SUPERVISOR = 4;
    public static final int COLUMNA_EXH_EXHIBITION_TOOL = 5;
    public static final int COLUMNA_EXH_MANUFACTURER = 6;
    public static final int COLUMNA_EXH_CATEGORY = 7;
    public static final int COLUMNA_EXH_SUBCATEGORY = 8;
    public static final int COLUMNA_EXH_ZONE_EXHIBITION = 9;
    public static final int COLUMNA_EXH_PERSONALIZATION = 10;
    public static final int COLUMNA_EXH_PRICE_TAG = 11;
    public static final int COLUMNA_EXH_NUM_EXHIBITIONS = 12;
    public static final int COLUMNA_EXH_OBSERVATION = 13;
    public static final int COLUMNA_EXH_PHOTO = 14;
    public static final int COLUMNA_EXH_DATE = 15;
    public static final int COLUMNA_EXH_VISUAL_ACCESS = 16;
    public static final int COLUMNA_EXH_PHOTO2 = 17;
    public static final int COLUMNA_EXH_TIPO_HERRAMIENTA = 18;
    public static final int COLUMNA_EXH_CAMPANA = 19;
    public static final int COLUMNA_EXH_CONVENIO = 20;
    public static final int COLUMNA_EXH_CLASIFICACION = 21;


    //EVALUACION ENCUESTA
    public static final int COLUMNA_NOMBRE_ENCUESTA = 2;
    public static final int COLUMNA_CATEGORIA_E = 3;
    public static final int COLUMNA_DESCRIPCION = 4;
    public static final int COLUMNA_RE_E = 5;
    public static final int COLUMNA_PREGUNTA_E = 6;
    public static final int COLUMNA_TIPO_PREGUNTA_E = 7;
    public static final int COLUMNA_OPC_A = 8;
    public static final int COLUMNA_OPC_B = 9;
    public static final int COLUMNA_OPC_C = 10;
    public static final int COLUMNA_OPC_D = 11;
    public static final int COLUMNA_OPC_E = 12;
    public static final int COLUMNA_FOTO_E = 13;
    public static final int COLUMNA_TIPO_CAMPO = 14;
    public static final int COLUMNA_PUNTAJE_POR_PREGUNTA = 15;
    public static final int COLUMNA_HABILITADO = 16;

    // USER

    public static final int USER = 2;
    public static final int U_MERCADERISTA = 3;
    public static final int DEVICE_ID_USER = 4;
    public static final int COLOR = 5;
    public static final int U_STATUS = 6;



}