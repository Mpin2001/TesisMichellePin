package com.luckyecuador.app.bassaApp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.luckyecuador.app.bassaApp.Adaptadores.AdapterConvenios;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterEncuesta;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterEvidencias;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterMuestrasMedicas;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterNovedades;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterProbadores;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterTracking;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertConvenios;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertEvaluacionEncuesta;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertMuestras;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertNovedades;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPacks2;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertProbadores;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertTracking;
import com.luckyecuador.app.bassaApp.DataBase.Provider;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterAgotados;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterCanjes;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterEjecucionMateriales;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterExh;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterFlooring;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterFotografico;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterGps;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterImplementacion;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterImpulso;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterMCI;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterCodificados;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterMaterialesRecibidos;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterPDI;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterPDV;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterPacks;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterPrecios;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterPreguntas;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterProdCad;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterPromo;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterRotacion;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterShare;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterTareas;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterValores;
import com.luckyecuador.app.bassaApp.Adaptadores.AdapterVenta;
import com.luckyecuador.app.bassaApp.Adaptadores.SugeridosAdapter;
import com.luckyecuador.app.bassaApp.Conexion.Constantes;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertAgotados;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertCanjes;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertEjecucionMateriales;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertEvidencias;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertExh;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertFotografico;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertGps;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertImplementacion;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertImpulso;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertMCIPdv;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertCodificados;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertMaterialesRecibidos;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPDI;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPacks;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPrecios;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPreguntas;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertProdCaducar;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertPromocion;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertRotacion;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertShare;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertSugeridos;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertTareas;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertValores;
import com.luckyecuador.app.bassaApp.Contracts.ContractInsertVenta;
import com.luckyecuador.app.bassaApp.Contracts.ContractNotificacion;
import com.luckyecuador.app.bassaApp.Contracts.InsertFlooring;
import com.luckyecuador.app.bassaApp.DataBase.DatabaseHelper;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistorialActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener{

    private static final org.apache.commons.logging.Log log = LogFactory.getLog(HistorialActivity.class);
    DatabaseHelper handler;
    Spinner spMod, spCategoria, spSubcategoria, spStatus; //spPdv,

    Spinner spPdv;
    Spinner spTipoNovedad;
    private String tipoNovedadSeleccionada = "TIPO DE NOVEDAD";

    Spinner spFabricante;
    Spinner spMarca;
    LinearLayout llCategoria, llSubcategoria, llMarca,llFabricante, llCategorias, llPdv, llTipoNovedad;
    ImageButton btnFinicio;
    ImageButton btnFfin;
    TextView txtFinicio;
    TextView txtFfin;
    String tablaInsert, columnaPdv,columnaTipoNovedad, columnaMarca, columnaCategoria, columnaSubcategoria, columnaFabricante,columnaPrecioDescuento;
    SwipeRefreshLayout swipeRefresh;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdapterPrecios adapterPrecio;
    private AdapterExh adapterExh;
    private AdapterGps adapterGps;
    private AdapterFlooring adapterFlooring;
    private AdapterPromo adapterPromo;
    private AdapterValores adapterValores;
    private AdapterImplementacion adapterImplementacion;
    private AdapterPDV adapterPDV;
    private AdapterShare adapterShare;
    private AdapterAgotados adapterAgotados;
    private AdapterVenta adapterVenta;
    private AdapterFotografico adapterFotografico;
    private AdapterPreguntas adapterPreguntas;
    private AdapterPacks adapterPacks;
    private AdapterProdCad adapterProdCad;
    private AdapterImpulso adapterImpulso;
    private AdapterRotacion adapterRotacion;
    private SugeridosAdapter adapterSugeridos;
    private AdapterTareas adapterTareas;
    private AdapterCanjes adapterCanjes;
    private AdapterMCI adapterMCI;
    private AdapterMaterialesRecibidos adapterMaterialesRecibidos;
    private AdapterEjecucionMateriales adapterEjecucionMateriales;
    private AdapterPDI adapterPDI;
    private AdapterCodificados adapterCodificados;
    private AdapterEvidencias adapterEvidencias;
    private AdapterTracking adapterTracking;
    private AdapterConvenios adapterConvenios;
    private AdapterEncuesta adapterEncuesta;
    private AdapterNovedades adapterNovedades;
    private AdapterProbadores adapterProbadores;
    private AdapterMuestrasMedicas adapterMuestras;

    private TextView emptyView;

    private static int idPrecio = 0;
    private static int idExh = 1;
    private static int idGps = 2;
    private static int idFlooring = 3;
    private static int idPromo = 4;
    private static int idValores = 5;
    private static int idImple = 6;
    private static int idNotificacion = 7;
    private static int idShare = 8;
    private static int idAgotados = 9;
    private static int idVenta = 10;
    private static int idExhAntDes = 11;
    private static int idPreg = 12;
    private static int idPacks = 13;
    private static int idProdCad = 14;
    private static int idImpul = 15;
    private static int idRotacion = 16;
    private static int idSugeridos = 17;
    private static int idTareas = 18;
    private static int idCanjes = 19;
    private static int idMCI = 20;
    private static int idMaterialesRecibidos = 21;
    private static int idEjecMateriales = 22;
    private static int idPDI = 23;
    private static int idCodificados = 24;
    private static int idEvidencias = 25;
    private static int idTracking = 26;
    private static int idConvenios = 27;
    private static int idEncuestas = 28;
    private static int idNovedades = 29;
    private static int idVentas = 30;
    private static int idProbadores = 31;
    private static int idMuestras = 32;
    String tipo, rol, modulo ="", pdv="", categoria="", subcategoria="", status="", fabricante="", marca="", columna = "";
    String fDesde = "", fHasta = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        setToolbar();

        LoadData();
        handler = new DatabaseHelper(this, Provider.DATABASE_NAME,null,1);

        //startService(new Intent(getApplicationContext(), MyService.class));

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        spMod = (Spinner) findViewById(R.id.spModulo);
        spPdv = (Spinner) findViewById(R.id.spPdv);
     //   spFabricante = (Spinner) findViewById(R.id.spFabricante); //nuevo
        spMarca = (Spinner) findViewById(R.id.spMarca); //nuevo
        spCategoria = (Spinner) findViewById(R.id.spCategoria);
        spTipoNovedad = findViewById(R.id.spTipoNovedad); // nuevo para tipo novedad
        spSubcategoria = (Spinner) findViewById(R.id.spSubcategoria);
        spStatus = (Spinner) findViewById(R.id.spStatus);
        llCategoria = (LinearLayout) findViewById(R.id.layout_categoria);
        llSubcategoria = (LinearLayout) findViewById(R.id.layout_subcategoria);
        llCategorias = (LinearLayout) findViewById(R.id.layout_categorias);
        //llFabricante = (LinearLayout) findViewById(R.id.layout_fabricante);
       // llMarca = (LinearLayout) findViewById(R.id.layout_marca);
        llPdv = (LinearLayout) findViewById(R.id.layout_pdv);
        llTipoNovedad = (LinearLayout) findViewById(R.id.layout_tipo_novedad);
        btnFinicio = (ImageButton) findViewById(R.id.btnFechaInicio);
        btnFfin = (ImageButton) findViewById(R.id.btnFechaFin);
        txtFinicio = (TextView) findViewById((R.id.txtFechaI));
        txtFfin = (TextView) findViewById((R.id.txtFechaF));

        swipeRefresh.setOnRefreshListener(this);
        btnFinicio.setOnClickListener(this);
        btnFfin.setOnClickListener(this);

        ArrayAdapter adaptadorTipoNovedad = ArrayAdapter.createFromResource(this,
                R.array.tipos_novedad, android.R.layout.simple_spinner_item);
        adaptadorTipoNovedad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoNovedad.setAdapter(adaptadorTipoNovedad);
        spTipoNovedad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoNovedadSeleccionada = parent.getItemAtPosition(position).toString();

                Log.i("TipoNovedad", "Seleccionado: " + tipoNovedadSeleccionada);

                // Solo filtrar si el módulo es NOVEDADES
                if (modulo.equals("NOVEDADES")) {
                    // No llamar directamente a filtrarHistorial aquí
                    // Dejar que se active cuando se cambie el status
                    // O podrías llamarlo si todos los campos están listos:
                    if (!tipoNovedadSeleccionada.equals("TIPO DE NOVEDAD") &&
                            !status.isEmpty() &&
                            !fDesde.isEmpty() &&
                            !fHasta.isEmpty() &&
                            !pdv.equalsIgnoreCase("PDV")) {
                        filtrarHistorial(modulo);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
//        spTipoNovedad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                tipoNovedadSeleccionada = parent.getItemAtPosition(position).toString();
//
//                // Recargar novedades con filtro
//                Bundle args = new Bundle();
//                args.putString("TIPO_NOVEDAD", tipoNovedadSeleccionada);
//
//                getSupportLoaderManager().restartLoader(idNovedades, args, HistorialActivity.this);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {}
//        });


        ArrayAdapter adaptadorKeyword2 = ArrayAdapter.createFromResource(this,R.array.modR,android.R.layout.simple_spinner_item);
        adaptadorKeyword2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMod.setAdapter(adaptadorKeyword2);
        spMod.setOnItemSelectedListener(this);
        spStatus.setOnItemSelectedListener(this);

        //filtrarPdv();

//        if (tipo.equals("PROMOTOR R"))
//        {
//            ArrayAdapter adaptadorKeyword1 = ArrayAdapter.createFromResource(this,R.array.modR,android.R.layout.simple_spinner_item);
//            adaptadorKeyword1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spMod.setAdapter(adaptadorKeyword1);
//            spMod.setOnItemSelectedListener(this);
//        }else if (tipo.equals("PROMOTOR C"))
//        {
//            ArrayAdapter adaptadorKeyword1 = ArrayAdapter.createFromResource(this,R.array.modC,android.R.layout.simple_spinner_item);
//            adaptadorKeyword1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spMod.setAdapter(adaptadorKeyword1);
//            spMod.setOnItemSelectedListener(this);
//        }else if (tipo.equals("MERCADERISTA"))
//        {
//            ArrayAdapter adaptadorKeyword1 = ArrayAdapter.createFromResource(this,R.array.merca,android.R.layout.simple_spinner_item);
//            adaptadorKeyword1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spMod.setAdapter(adaptadorKeyword1);
//            spMod.setOnItemSelectedListener(this);
//        }

        recyclerView = (RecyclerView) findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        emptyView = (TextView) findViewById(R.id.recyclerview_data_empty);



//        spPdv.setOnItemClickListener((parent, view, position, id) -> {
//            String pdv = parent.getItemAtPosition(position).toString();
//            filtrarCategoria(pdv);
//            spStatus.setSelection(0); // Asegúrate de que spStatus sigue siendo un Spinner
//        });

    }


    public void LoadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        tipo = sharedPreferences.getString(Constantes.TIPO,Constantes.NODATA);
        rol = sharedPreferences.getString(Constantes.ROLNUEVO,Constantes.NODATA);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView == spMod){
            modulo = adapterView.getItemAtPosition(i).toString();
            setContractByModulo(modulo);
            hideItems(modulo);
            filtrarPdv();
            spStatus.setSelection(0);
        }

        if(adapterView == spPdv){
            pdv = adapterView.getItemAtPosition(i).toString();
           // filtrarCategoria(pdv);
          //  filtrarFabricante(pdv);
            spStatus.setSelection(0);
        }
//        if(adapterView == spFabricante){
//            fabricante = adapterView.getItemAtPosition(i).toString();
//            // filtrarCategoria(pdv);
//            //filtrarFabricante(marca);
//            filtrarMarca(pdv,fabricante);
//            spStatus.setSelection(0);
//        }
//        if(adapterView == spMarca){
//            marca = adapterView.getItemAtPosition(i).toString();
//            // filtrarCategoria(pdv);
//            //filtrarFabricante(marca);
//           // filtrarMarca(pdv,fabricante);
//            spStatus.setSelection(0);
//        }

//        if(adapterView == spCategoria){
//            categoria = adapterView.getItemAtPosition(i).toString();
//            filtrarSubcategoria(pdv, categoria);
//            spStatus.setSelection(0);
//        }

//        if(adapterView == spSubcategoria){
//            subcategoria = adapterView.getItemAtPosition(i).toString();
//            spStatus.setSelection(0);
//        }

        if(adapterView == spStatus) {

            status = adapterView.getItemAtPosition(i).toString();
            if(status.equals("NO ENVIADO")){
                status = "1";
            } else {
                status = "0";
            }

            if(camposValidados()){
                filtrarHistorial(modulo);
            }


        }
    }

    public void hideItems(String modulo){

        if(modulo.equalsIgnoreCase("REGISTRO")){
            llCategorias.setVisibility(View.GONE);
            llPdv.setVisibility(View.VISIBLE);
            llSubcategoria.setVisibility(View.GONE);
        }else if (modulo.equalsIgnoreCase("NUEVO PDV")){
            llCategorias.setVisibility(View.GONE);
            llPdv.setVisibility(View.GONE);
            llSubcategoria.setVisibility(View.GONE);
        }else if (modulo.equalsIgnoreCase("PRECIOS")){
            llCategorias.setVisibility(View.GONE);
            llPdv.setVisibility(View.VISIBLE);
           // llFabricante.setVisibility(View.GONE);
           // llMarca.setVisibility(View.GONE);
            llSubcategoria.setVisibility(View.GONE);
        }else if(modulo.equalsIgnoreCase("NOVEDADES")){
            llCategorias.setVisibility(View.GONE);
            llTipoNovedad.setVisibility(View.VISIBLE);
            llPdv.setVisibility(View.VISIBLE);
            llSubcategoria.setVisibility(View.GONE);
        }else if (modulo.equalsIgnoreCase("LOGROS_OLD")){
            llCategorias.setVisibility(View.VISIBLE);
            llPdv.setVisibility(View.VISIBLE);
            llSubcategoria.setVisibility(View.GONE);

        }
        else if (modulo.equalsIgnoreCase("ENCUESTA APP")){
            llCategorias.setVisibility(View.GONE);
            llPdv.setVisibility(View.GONE);
            llSubcategoria.setVisibility(View.GONE);
        }else if (modulo.equalsIgnoreCase("EXHIBICIONES")){
            llSubcategoria.setVisibility(View.GONE);
            llCategorias.setVisibility(View.GONE);

        }else {
            llCategorias.setVisibility(View.VISIBLE);
            llPdv.setVisibility(View.VISIBLE);
            llSubcategoria.setVisibility(View.VISIBLE);
            llTipoNovedad.setVisibility(View.GONE);
        }
    }

    public void filtrarPdv(){

        List<String> operadores = handler.filtrarPdvsHistorial(tablaInsert, columnaPdv);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPdv.setAdapter(dataAdapter);
        spPdv.setOnItemSelectedListener(this);

    }
    public void filtrarCategoria(String pdv){

        List<String> operadores = handler.filtrarCategoriasHistorial(tablaInsert, columnaPdv, columnaCategoria, pdv);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(dataAdapter);
        spCategoria.setOnItemSelectedListener(this);

    }
    public void filtrarFabricante(String pdv) {
        List<String> operadores = handler.filtrarFabriHistorial(tablaInsert, columnaPdv, columnaFabricante, pdv);
        if (operadores.size()==2) {
            operadores.remove(0);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
       // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                android.R.layout.simple_spinner_item, operadores);
        spFabricante.setAdapter(dataAdapter);
        spFabricante.setOnItemSelectedListener(this);
    }


    public void filtrarSubcategoria(String pdv, String categoria){

        List<String> operadores = handler.filtrarSubcategoriasHistorial(tablaInsert,
                columnaPdv, columnaCategoria, columnaSubcategoria, pdv, categoria);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubcategoria.setAdapter(dataAdapter);
        spSubcategoria.setOnItemSelectedListener(this);

    }

    public void filtrarMarca(String pdv, String fabricante){

        List<String> operadores = handler.filtrarMarcaNewHistorial(tablaInsert,
                columnaPdv, columnaFabricante, columnaMarca, pdv, fabricante);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, operadores);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(dataAdapter);
        spMarca.setOnItemSelectedListener(this);

    }
    public void filtrarHistorial(String modulo){
        Log.i("entra FA", "filtrar historia");
        switch (modulo) {
            case "PRECIOS":
                getPrecios();
                break;
            case "EXHIBICIONES":
                getExh();
                break;
            case "REGISTRO":
                getGps();
                break;
            case "INVENTARIO+SUGERIDO":
                getFlooring();
                break;
            case "PROMOCIONES":
                getPromocion();
                break;
            case "OSA":
                getValores();
                break;
            case "NOTIFICACION":
                getNotificacion();
                break;
            case "NUEVO PDV":
                getImplementacion();
                break;
            case "SOD":
                getShare();
                break;
            case "TIEMPO GESTION":
                getAgotados();
                break;
            case "VENTA - FACTURA":
                getVenta();
                break;
            case "LOGROS_OLD":
                getLogros();
                break;
            case "TEST":
                getTest();
                break;
            case "ON PACKS":
                getOnPacks();
                break;
            case "ENCUESTA APP":
                getEncuesta();
                break;
            case "NOVEDADES":
                getNovedades();
                break;
            case "MUESTRAS MEDICAS":
                getMuestrasMedicas();
                break;
            case "PROBADORES":
                getProbadores();
                break;
            case "VENTAS":
                getVentas();
                break;
            case "PRODUCTOS A CADUCAR":
                getProdCad();
                break;
            case "IMPULSO":
                getImpulso();
                break;
            case "ROTACION":
                getRotacion();
                break;
            case "SMS":
                getSugeridos();
                break;
            case "TAREAS":
                getTareas();
                break;
            case "CANJES":
                getCanjes();
                break;
            case "MCI":
                getMCI();
                break;
            case "MATERIALES RECIBIDOS":
                getMaterialesRecibidos();
                break;
            case "EJECUCION DE MATERIALES":
                getEjecucionMateriales();
                break;
            case "PDI":
                getPDI();
                break;
            case "CODIFICADOS":
                getCodificados();
                break;
            case "LOGROS":
                getAntesDespues();
                break;
            case "TRACKING":
                getTracking();
                break;
            case "CONVENIOS":
                getConvenios();
                break;
        }
    }
    public boolean camposValidados(){

        if (!modulo.equalsIgnoreCase("MÓDULO")){
            if(fDesde.isEmpty() || fHasta.isEmpty()) {
                Toast.makeText(this, "Elija fecha inicio y fin", Toast.LENGTH_SHORT).show();
                spStatus.setSelection(0);
                return false;
            }
        }
//        if(pdv.equalsIgnoreCase("PDV")){
//            Toast.makeText(this, "Elija un PDV", Toast.LENGTH_SHORT).show();
//            spStatus.setSelection(0);
//            return false;
//        }

        if (!modulo.equalsIgnoreCase("REGISTRO") && !modulo.equalsIgnoreCase("NUEVO PDV")  && !modulo.equalsIgnoreCase("ENCUESTA APP") && !modulo.equalsIgnoreCase("NOVEDADES") ){
            if(categoria.equalsIgnoreCase("CATEGORIA")){
                Toast.makeText(this, "Elija una categoría", Toast.LENGTH_SHORT).show();
                spStatus.setSelection(0);
                return false;
            }
        }

        if (modulo.equalsIgnoreCase("NOVEDADES")) {
            if(tipoNovedadSeleccionada.equals("TIPO DE NOVEDAD")){
                Toast.makeText(this, "Elija un tipo de novedad", Toast.LENGTH_SHORT).show();
                spStatus.setSelection(0);
                return false;
            }
        }


        if (!modulo.equalsIgnoreCase("REGISTRO") && !modulo.equalsIgnoreCase("NUEVO PDV") &&
                !modulo.equalsIgnoreCase("LOGROS_OLD") && !modulo.equalsIgnoreCase("ENCUESTA APP") && !modulo.equalsIgnoreCase("NOVEDADES")){

            if(subcategoria.equalsIgnoreCase("SUBCATEGORIA") && !modulo.equalsIgnoreCase("EXHIBICIONES")){
                Toast.makeText(this, "Elija una subcategoría", Toast.LENGTH_SHORT).show();
                spStatus.setSelection(0);
                return false;
            }

        }

        return true;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    public void getPrecios() {
        Log.i("precios loader", "getPrecios()");
        adapterPrecio = new AdapterPrecios(this);
        recyclerView.setAdapter(adapterPrecio);
        //getSupportLoaderManager().initLoader(idPrecio, null,this); //funciona cuando no se envían parametros
        getSupportLoaderManager().restartLoader(idPrecio, null, this); //vuelve a iniciar el loader con los nuevos paramtros de consulta
    }
    public void getVentas() {
        adapterVenta = new AdapterVenta(this);
        recyclerView.setAdapter(adapterVenta);
        //getSupportLoaderManager().initLoader(idPrecio, null,this); //funciona cuando no se envían parametros
        getSupportLoaderManager().restartLoader(idVentas, null, this); //vuelve a iniciar el loader con los nuevos paramtros de consulta
    }


    public void getExh() {
        adapterExh = new AdapterExh(this);
        recyclerView.setAdapter(adapterExh);
        getSupportLoaderManager().restartLoader(idExh, null,this);
    }

    public void getGps() {
        adapterGps = new AdapterGps(this);
        recyclerView.setAdapter(adapterGps);
        getSupportLoaderManager().restartLoader(idGps, null,this);
    }
    public void getNovedades(){
        adapterNovedades = new AdapterNovedades(this);
        recyclerView.setAdapter(adapterNovedades);
        getSupportLoaderManager().restartLoader(idNovedades, null,this);
    }
 //   nuevos modulos mpin
    public void getMuestrasMedicas(){
        adapterMuestras= new AdapterMuestrasMedicas(this); //cambiar
        recyclerView.setAdapter(adapterMuestras);
        getSupportLoaderManager().restartLoader(idMuestras, null,this);
    }
    public void getProbadores(){
        adapterProbadores = new AdapterProbadores(this);//cambiar
        recyclerView.setAdapter(adapterProbadores);
        getSupportLoaderManager().restartLoader(idProbadores, null,this);
    }

    public void getFlooring() {
        adapterFlooring= new AdapterFlooring(this);
        recyclerView.setAdapter(adapterFlooring);
        getSupportLoaderManager().restartLoader(idFlooring,null,this);
    }

    public void getPromocion() {
        adapterPromo= new AdapterPromo(this);
        recyclerView.setAdapter(adapterPromo);
        getSupportLoaderManager().restartLoader(idPromo,null,this);
    }

    public void getValores() {
        adapterValores= new AdapterValores(this);
        recyclerView.setAdapter(adapterValores);
        getSupportLoaderManager().restartLoader(idValores,null,this);
    }

    public void getImplementacion() {
        adapterImplementacion= new AdapterImplementacion(this);
        recyclerView.setAdapter(adapterImplementacion);
        getSupportLoaderManager().restartLoader(idImple,null,this);
    }

    public void getNotificacion() {
        adapterPDV= new AdapterPDV(this);
        recyclerView.setAdapter(adapterPDV);
        getSupportLoaderManager().restartLoader(idNotificacion,null,this);
    }

    public void getShare() {
        adapterShare= new AdapterShare(this);
        recyclerView.setAdapter(adapterShare);
        getSupportLoaderManager().restartLoader(idShare,null,this);
    }

    public void getAgotados() {
        adapterAgotados= new AdapterAgotados(this);
        recyclerView.setAdapter(adapterAgotados);
        getSupportLoaderManager().restartLoader(idAgotados,null,this);
    }

    public void getVenta() {
        adapterVenta = new AdapterVenta(this);
        recyclerView.setAdapter(adapterVenta);
        getSupportLoaderManager().restartLoader(idVenta, null,this);
    }

    public void getLogros() {
        adapterFotografico = new AdapterFotografico(this);
        recyclerView.setAdapter(adapterFotografico);
        getSupportLoaderManager().restartLoader( idExhAntDes, null,this);
    }

    public void getTest() {
        adapterPreguntas = new AdapterPreguntas(this);
        recyclerView.setAdapter(adapterPreguntas);
        getSupportLoaderManager().restartLoader(idPreg, null,this);
    }

    public void getOnPacks() {
        adapterPacks = new AdapterPacks(this);
        recyclerView.setAdapter(adapterPacks);
        getSupportLoaderManager().restartLoader(idPacks, null,this);
    }


    public void getEncuesta(){
        Log.i("nombtes","entro 1");
        adapterEncuesta = new AdapterEncuesta(this);
        recyclerView.setAdapter(adapterEncuesta);
        Log.i("nombtes","entro 2");
        getSupportLoaderManager().restartLoader(idEncuestas, null,this);
    }

    public void getProdCad() {
        adapterProdCad = new AdapterProdCad(this);
        recyclerView.setAdapter(adapterProdCad);
        getSupportLoaderManager().restartLoader(idProdCad, null,this);
    }

    public void getImpulso() {
        adapterImpulso = new AdapterImpulso(this);
        recyclerView.setAdapter(adapterImpulso);
        getSupportLoaderManager().restartLoader(idImpul, null,this);
    }
    public void getRotacion() {
        adapterRotacion = new AdapterRotacion(this);
        recyclerView.setAdapter(adapterRotacion);
        getSupportLoaderManager().restartLoader(idRotacion, null,this);
    }

    public void getSugeridos() {
        adapterSugeridos = new SugeridosAdapter(this);
        recyclerView.setAdapter(adapterSugeridos);
        getSupportLoaderManager().restartLoader(idSugeridos, null,this);
    }

    public void getTareas() {
        adapterTareas = new AdapterTareas(this);
        recyclerView.setAdapter(adapterTareas);
        getSupportLoaderManager().restartLoader(idTareas, null,this);
    }

    public void getCanjes() {
        adapterCanjes = new AdapterCanjes(this);
        recyclerView.setAdapter(adapterCanjes);
        getSupportLoaderManager().restartLoader(idCanjes, null,this);
    }

    public void getMCI() {
        adapterMCI = new AdapterMCI(this);
        recyclerView.setAdapter(adapterMCI);
        getSupportLoaderManager().restartLoader(idMCI, null,this);
    }

    public void getMaterialesRecibidos() {
        adapterMaterialesRecibidos = new AdapterMaterialesRecibidos(this);
        recyclerView.setAdapter(adapterMaterialesRecibidos);
        getSupportLoaderManager().restartLoader(idMaterialesRecibidos, null,this);
    }

    public void getEjecucionMateriales() {
        adapterEjecucionMateriales = new AdapterEjecucionMateriales(this);
        recyclerView.setAdapter(adapterEjecucionMateriales);
        getSupportLoaderManager().restartLoader(idEjecMateriales, null,this);
    }

    public void getPDI() {
        adapterPDI = new AdapterPDI(this);
        recyclerView.setAdapter(adapterPDI);
        getSupportLoaderManager().restartLoader(idPDI, null,this);
    }

    public void getCodificados() {
        adapterCodificados = new AdapterCodificados(this);
        recyclerView.setAdapter(adapterCodificados);
        getSupportLoaderManager().restartLoader(idCodificados, null,this);
    }

    public void getAntesDespues() {
        adapterEvidencias = new AdapterEvidencias(this);
        recyclerView.setAdapter(adapterEvidencias);
        getSupportLoaderManager().restartLoader(idEvidencias, null,this);
    }

    public void getTracking() {
        adapterTracking = new AdapterTracking(this);
        recyclerView.setAdapter(adapterTracking);
        getSupportLoaderManager().restartLoader(idTracking, null,this);
    }

    public void getConvenios() {
        adapterConvenios = new AdapterConvenios(this);
        recyclerView.setAdapter(adapterConvenios);
        getSupportLoaderManager().restartLoader(idConvenios, null,this);
    }

    public void setContractByModulo(String modulo){
        //aqui se colocan los filtros que aplican para cada modulo y las respectivas tablas y columnas del contract
        switch (modulo) {
            case "PRECIOS":
                Log.i("Entra HA case", "precios");
                tablaInsert = ContractInsertPrecios.INSERT_PRECIOS;
                columnaPdv = ContractInsertPrecios.Columnas.POS_NAME;
           //     columnaMarca = ContractInsertPrecios.Columnas.BRAND;
           //    columnaCategoria = ContractInsertPrecios.Columnas.CATEGORIA;
              //  columnaSubcategoria = ContractInsertPrecios.Columnas.SUBCATEGORIA;
            //    columnaFabricante = ContractInsertPrecios.Columnas.FABRICANTE;
             //   columnaPrecioDescuento = ContractInsertPrecios.Columnas.PRECIO_DESCUENTO;
                break;
            case "NOVEDADES":
                tablaInsert = ContractInsertNovedades.INSERT_NOVEDADES;
                columnaPdv = ContractInsertNovedades.Columnas.POS_NAME;
                columnaCategoria = "";
                columnaSubcategoria = "";
                // Agregar columna para tipo de novedad si la necesitas
                break;


                /// / nuevos campos
            case "PRODUCTO CADUCADO":
                tablaInsert = ContractInsertNovedades.INSERT_NOVEDADES;
                columnaPdv = ContractInsertNovedades.Columnas.POS_NAME;
                columna = "PRODUCTO CADUCADO"; //NUEVO CAMPO MEJOR FILTRO
               // columnaCategoria = "";
                //columnaSubcategoria = "";
                break;
            case "VENTAS":
                tablaInsert = ContractInsertVenta.INSERT_VENTA;
                columnaPdv = ContractInsertVenta.Columnas.POS_NAME;
                columnaCategoria = ContractInsertVenta.Columnas.CATEGORIA;
                columnaSubcategoria = ContractInsertVenta.Columnas.SUBCATEGORIA;
                break;
            case "EXHIBICIONES":
                tablaInsert = ContractInsertExh.INSERT_EXH;
                columnaPdv = ContractInsertExh.Columnas.POS_NAME;
               // columnaCategoria = ContractInsertExh.Columnas.CATEGORIA;
                //columnaSubcategoria = "";
                break;
            case "PROBADORES":
                tablaInsert = ContractInsertProbadores.INSERT_PROBADORES;
                columnaPdv = ContractInsertProbadores.Columnas.SUPERVISOR;
                // columnaCategoria = ContractInsertExh.Columnas.CATEGORIA;
                //columnaSubcategoria = "";
                break;
            case "MUESTRAS MEDICAS":
                tablaInsert = ContractInsertMuestras.INSERT_MUESTRAS;
                columnaPdv = ContractInsertMuestras.Columnas.SUPERVISOR;
                // columnaCategoria = ContractInsertExh.Columnas.CATEGORIA;
                //columnaSubcategoria = "";
                break;
            case "REGISTRO":
                tablaInsert = ContractInsertGps.INSERT_GPS;
                columnaPdv = ContractInsertGps.Columnas.POS_NAME;
                columnaCategoria = "";
                columnaSubcategoria = "";
                break;
            case "INVENTARIO+SUGERIDO":
                tablaInsert = InsertFlooring.INSERT_FLOORING;
                columnaPdv = InsertFlooring.Columnas.POS_NAME;
                //columnaCategoria = InsertFlooring.Columnas.CATEGORIA;
                //columnaSubcategoria = InsertFlooring.Columnas.SUBCATEGORIA;
                break;
            case "PROMOCIONES":
                tablaInsert = ContractInsertPromocion.INSERT_PROMO;
                columnaPdv = ContractInsertPromocion.Columnas.POS_NAME;
               // columnaCategoria = ContractInsertPromocion.Columnas.CATEGORIA;
              //  columnaSubcategoria = ContractInsertPromocion.Columnas.SUBCATEGORIA;
                break;
            case "OSA": //no
                tablaInsert = ContractInsertValores.INSERT_VALORES;
                columnaPdv = "";
                columnaCategoria = "";
                columnaSubcategoria = "";
                break;
            case "NOTIFICACION": //no
                tablaInsert = ContractNotificacion.NOTIFICACION;
                break;
            case "NUEVO PDV":
                tablaInsert = ContractInsertImplementacion.INSERT_IMPLEM;
                columnaPdv = "";
                columnaCategoria = "";
                columnaSubcategoria = "";
                break;
            case "ENCUESTA APP":
                tablaInsert = ContractInsertImplementacion.INSERT_IMPLEM;
                columnaPdv = "";
                columnaCategoria = "";
                columnaSubcategoria = "";
                break;
            case "SOD":
                tablaInsert = ContractInsertShare.INSERT_SHARE;
                columnaPdv = ContractInsertShare.Columnas.POS_NAME;
                columnaCategoria = ContractInsertShare.Columnas.SECTOR;
                columnaSubcategoria = ContractInsertShare.Columnas.CATEGORIA;
                break;
            case "TIEMPO GESTION": //no
                tablaInsert = ContractInsertAgotados.INSERT_AGOTADOS;
                columnaPdv = "";
                columnaCategoria = "";
                columnaSubcategoria = "";
                break;
            case "VENTA - FACTURA": //no
                tablaInsert = ContractInsertVenta.INSERT_VENTA;
                columnaPdv = "";
                columnaCategoria = "";
                columnaSubcategoria = "";
                break;
            case "LOGROS_OLD":
                tablaInsert = ContractInsertFotografico.INSERT_FOTOGRAFICO;
                columnaPdv = ContractInsertFotografico.Columnas.POS_NAME;
                columnaCategoria = ContractInsertFotografico.Columnas.CATEGORIA;
                break;
            case "TEST": //no
                tablaInsert = ContractInsertPreguntas.INSERT_PREGUNTAS;
                columnaPdv = "";
                columnaCategoria = "";
                columnaSubcategoria = "";
                break;
            case "ON PACKS":
                tablaInsert = ContractInsertPacks2.INSERT_PACKS;
                columnaPdv = ContractInsertPacks2.Columnas.POS_NAME;
                columnaCategoria = ContractInsertPacks2.Columnas.CATEGORIA;
                break;
            case "PRODUCTOS A CADUCAR": //no
                tablaInsert = ContractInsertProdCaducar.INSERT_PROD_CADUCAR;
                break;
            case "IMPULSO": //no
                tablaInsert = ContractInsertImpulso.INSERT_IMPUSLO;
                break;
            case "ROTACION": //no
                tablaInsert = ContractInsertRotacion.INSERT_ROTACION;
                break;
            case "SMS": //no
                tablaInsert = ContractInsertSugeridos.INSERT_SUGERIDOS;
                break;
            case "TAREAS": //no
                tablaInsert = ContractInsertTareas.INSERT_TAREAS;
                break;
            case "CANJES": //no
                tablaInsert = ContractInsertCanjes.INSERT_CANJES;
                break;
            case "MCI"://no
                tablaInsert = ContractInsertMCIPdv.INSERT_MCI;
                break;
            case "MATERIALES RECIBIDOS"://no
                tablaInsert = ContractInsertMaterialesRecibidos.INSERT_MATERIALES_RECIBIDOS;
                break;
            case "EJECUCION DE MATERIALES"://no
                tablaInsert = ContractInsertEjecucionMateriales.INSERT_EJECUCION_MATERIALES;
                break;
            case "PDI"://no
                tablaInsert = ContractInsertPDI.INSERT_PDI;
                break;
            case "CODIFICADOS":
                tablaInsert = ContractInsertCodificados.INSERT_CODIFICADOS;
                columnaPdv = ContractInsertCodificados.Columnas.POS_NAME;
                columnaCategoria = ContractInsertCodificados.Columnas.SECTOR;
                columnaSubcategoria = ContractInsertCodificados.Columnas.SUBCATEGORIA;
                break;
            case "LOGROS":
                tablaInsert = ContractInsertEvidencias.INSERT_EVIDENCIAS;
                columnaPdv = ContractInsertEvidencias.Columnas.POS_NAME; // crear
                columnaCategoria = ContractInsertEvidencias.Columnas.CATEGORIA;
                columnaSubcategoria = ContractInsertEvidencias.Columnas.SUBCATEGORIA;
                break;
            case "TRACKING":
                tablaInsert = ContractInsertTracking.INSERT_TRACKING;
                columnaPdv = ContractInsertTracking.Columnas.LOCAL;
                columnaCategoria = ContractInsertTracking.Columnas.CATEGORIA;
                columnaSubcategoria = ContractInsertTracking.Columnas.CUENTA; //hacer que guarde en columna cuentas LA SUBCATEGORIA
                break;
            case "CONVENIOS":
                tablaInsert = ContractInsertConvenios.INSERT_CONVENIOS;
                columnaPdv = ContractInsertConvenios.Columnas.POS_NAME; //ver por qué no guarda
                columnaCategoria = ContractInsertConvenios.Columnas.CATEGORIA;
                columnaSubcategoria = ContractInsertConvenios.Columnas.FABRICANTE;
                break;
            default:
                tablaInsert = "";
                columnaPdv = "";
                columnaCategoria = "";
                columnaSubcategoria = "";
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        emptyView.setText("Cargando datos...");
        CursorLoader loader = null;
        // Consultar todos los registros
        if (id==idPrecio) {
            Log.i("Entra HA", "precios");

            Log.i("onCreateLoader","vars prec: " + pdv +","+status);


            String selection = columnaPdv + "=? AND " + Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?";
            String[] selectArgs = {pdv, status, fDesde, fHasta};
            loader= new CursorLoader(
                    this,
                    ContractInsertPrecios.CONTENT_URI,
                    null, selection, selectArgs, null);
        }else
        if (id==idVentas) {
            Log.i("Entra HA", "precios");

            Log.i("onCreateLoader","vars prec: " + pdv +","+categoria+","+subcategoria+","+status);


            String selection = columnaPdv + "=? AND " + columnaCategoria + "=? AND " +
                    columnaSubcategoria + "=? AND " + Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {pdv, categoria, subcategoria, status, fDesde, fHasta};
            loader= new CursorLoader(
                    this,
                    ContractInsertVenta.CONTENT_URI,
                    null, selection, selectArgs, null);
        }else
        if (id==idProbadores) {
            Log.i("Entra HA", "probadores");

            Log.i("onCreateLoader","vars michin: " + pdv +","+status);


            String selection = columnaPdv + "=? AND " + Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?";
            String[] selectArgs = {pdv, status, fDesde, fHasta};
            loader= new CursorLoader(
                    this,
                    ContractInsertProbadores.CONTENT_URI,
                    null, selection, selectArgs, null);
        }else
        if (id==idMuestras) {
            Log.i("Entra HA", "muestras");

            Log.i("onCreateLoader","vars michin1: " + pdv +","+status);


            String selection = columnaPdv + "=? AND " + Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?";
            String[] selectArgs = {pdv, status, fDesde, fHasta};
            loader= new CursorLoader(
                    this,
                    ContractInsertMuestras.CONTENT_URI,
                    null, selection, selectArgs, null);
        }else

        if (id==idExh) {
            Log.i("onCreateLoader","vars exhibiciones: " + pdv +","+status);


            String selection = columnaPdv + "=? AND " + Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?";
            String[] selectArgs = {pdv, status, fDesde, fHasta};
            loader = new CursorLoader(
                    this,
                    ContractInsertExh.CONTENT_URI,
                    null, selection, selectArgs, null);
        }else
        if (id==idGps) {
            String selection = columnaPdv + "=? AND " + Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {pdv, status, fDesde, fHasta};
            loader = new CursorLoader(
                    this,
                    ContractInsertGps.CONTENT_URI,
                    null, selection, selectArgs, null);
        }else
        if (id==idNovedades) {
            Log.i("onCreateLoader", "=== FILTRO NOVEDADES ===");
            Log.i("onCreateLoader", "pdv: " + pdv);
            Log.i("onCreateLoader", "tipoNovedad: " + tipoNovedadSeleccionada);
            Log.i("onCreateLoader", "status: " + status);
            Log.i("onCreateLoader", "fDesde: " + fDesde);
            Log.i("onCreateLoader", "fHasta: " + fHasta);
            Log.i("onCreateLoader","vars novedades: pdv=" + pdv + ",tipo=" + tipoNovedadSeleccionada + ",status=" + status + ",fDesde=" + fDesde + ",fHasta=" + fHasta);

            String selection;
            String[] selectArgs;

            if (!tipoNovedadSeleccionada.equals("TIPO DE NOVEDAD")) {
                // Con tipo de novedad específico
                selection = columnaPdv + "=? AND " +
                        ContractInsertNovedades.Columnas.TIPO_NOVEDAD + "=? AND " +
                        Constantes.PENDIENTE_INSERCION + "=? AND " +
                        "fecha BETWEEN ? AND ?";
                selectArgs = new String[]{pdv, tipoNovedadSeleccionada, status, fDesde, fHasta};
            } else {
                // Sin tipo de novedad específico
                selection = columnaPdv + "=? AND " +
                        Constantes.PENDIENTE_INSERCION + "=? AND " +
                        "fecha BETWEEN ? AND ?";
                selectArgs = new String[]{pdv, status, fDesde, fHasta};
            }

            Log.i("onCreateLoader","selection: " + selection);

            loader = new CursorLoader(
                    this,
                    ContractInsertNovedades.CONTENT_URI,
                    null, selection, selectArgs, null);
        }else

        if (id==idFlooring) {
            Log.i("onCreateLoader","vars FLORING: " + pdv +","+status);


            String selection = columnaPdv + "=? AND " + Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?";
            String[] selectArgs = {pdv, status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    InsertFlooring.CONTENT_URI,
                    null,selection,selectArgs,null);

        }else
        if (id==idPromo) {
            Log.i("onCreateLoader","vars promo: " + pdv +","+status);


            String selection = columnaPdv + "=? AND " + Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?";
            String[] selectArgs = {pdv, status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertPromocion.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idValores) {
            String selection = columnaPdv + "=? AND " + columnaCategoria + "=? AND " +
                    Constantes.PENDIENTE_INSERCION + "=? AND "+
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {pdv, categoria, status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertValores.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idImple) {
            String selection = Constantes.PENDIENTE_INSERCION + "=? AND "+
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertImplementacion.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else if (id==idEncuestas) {
            String selection = Constantes.PENDIENTE_INSERCION + "=? AND "+
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertEvaluacionEncuesta.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idNotificacion) {
            String selection = Constantes.PENDIENTE_INSERCION + "=? AND "+
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractNotificacion.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idShare) {
            String selection = columnaPdv + "=? AND " + columnaCategoria + "=? AND " +
                    columnaSubcategoria + "=? AND " + Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {pdv, categoria, subcategoria, status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertShare.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idAgotados) {
            String selection = Constantes.PENDIENTE_INSERCION + "=? AND "+
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertAgotados.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idVenta) {
            String selection = Constantes.PENDIENTE_INSERCION + "=? AND "+
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertVenta.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idExhAntDes) {
            String selection = Constantes.PENDIENTE_INSERCION + "=? AND "+
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertFotografico.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idPreg) {
            String selection = Constantes.PENDIENTE_INSERCION + "=? AND "+
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertPreguntas.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idPacks) {
            String selection = columnaPdv + "=? AND " + columnaCategoria + "=? " +
                    " AND " + Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {pdv, categoria, status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertPacks.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idProdCad) {
            String selection = Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertProdCaducar.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idImpul) {
            String selection = Constantes.PENDIENTE_INSERCION + "=? AND" +
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertImpulso.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idRotacion) {
            String selection = Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertRotacion.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idSugeridos) {
            String selection = Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertSugeridos.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idTareas) {
            String selection = Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertTareas.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idCanjes) {
            String selection = Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertCanjes.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idMCI) {
            String selection = Constantes.PENDIENTE_INSERCION + "=? AND" +
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertMCIPdv.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idMaterialesRecibidos) {
            String selection = Constantes.PENDIENTE_INSERCION + "=? AND" +
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertMaterialesRecibidos.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idEjecMateriales) {
            String selection = Constantes.PENDIENTE_INSERCION + "=?;";
            String[] selectArgs = {status};
            loader=new CursorLoader(
                    this,
                    ContractInsertEjecucionMateriales.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idPDI) {
            String selection = Constantes.PENDIENTE_INSERCION + "=?;";
            String[] selectArgs = {status};
            loader=new CursorLoader(
                    this,
                    ContractInsertPDI.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idCodificados) {
            String selection = columnaPdv + "=? AND " + columnaCategoria + "=? AND " +
                    Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {pdv, categoria, status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertCodificados.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idEvidencias) {
            String selection = columnaPdv + "=? AND " + columnaCategoria + "=? AND " +
                    columnaSubcategoria + "=? AND " + Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {pdv, categoria, subcategoria, status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertEvidencias.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idTracking) {
            String selection = columnaPdv + "=? AND " + columnaCategoria + "=? AND " +
                    columnaSubcategoria + "=? AND " + Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {pdv, categoria, subcategoria, status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertTracking.CONTENT_URI,
                    null,selection,selectArgs,null);
        }else
        if (id==idConvenios) {
            String selection = columnaPdv + "=? AND " + columnaCategoria + "=? AND " +
                    columnaSubcategoria + "=? AND " + Constantes.PENDIENTE_INSERCION + "=? AND " +
                    "fecha BETWEEN ? AND ?; ";
            String[] selectArgs = {pdv, categoria, subcategoria, status, fDesde, fHasta};
            loader=new CursorLoader(
                    this,
                    ContractInsertConvenios.CONTENT_URI,
                    null,selection,selectArgs,null);
        }

        return loader ;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i("onLoadFinished",columnaPdv + " colCat: " + columnaCategoria + "colSub " + columnaSubcategoria + "vars: " + pdv +","+categoria+","+subcategoria+","+status);
        data.moveToFirst(); // Mueve al primer registro
        while (!data.isAfterLast()) {
            // Obtén el valor de la columna 'columna_nombre'
            String columnaValor = data.getString(5);
            String columnaValor2 = data.getString(6);
            Log.i("data onloadfinished","Fecha: " + columnaValor+" hora: "+columnaValor2+"");

            data.moveToNext(); // Mueve al siguiente registro
        }
        switch (loader.getId()) {
            case 0:
                if (loader!=null && adapterPrecio != null) {
                    Log.d("TAG" , "||onLoadFinished called precios||");
                    adapterPrecio.swapCursor(data);
                    emptyView.setText("");
                }
            case 1:
                if (loader != null && adapterExh != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterExh.swapCursor(data);
                    emptyView.setText("");
                }
            case 2:
                if (loader != null && adapterGps != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterGps.swapCursor(data);
                    emptyView.setText("");
                }
            case 3:
                if (loader != null && adapterFlooring != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    Log.d("TAG" , "TRACKING = " + data);
                    adapterFlooring.swapCursor(data);
                    emptyView.setText("");
                }
            case 4:
                if (loader != null && adapterPromo != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    Log.d("TAG" , "Promo = " + data);
                    adapterPromo.swapCursor(data);
                    emptyView.setText("");
                }
            case 5:
                if (loader != null && adapterValores != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterValores.swapCursor(data);
                    emptyView.setText("");
                }
            case 6:
                if (loader != null && adapterImplementacion != null) {
                    Log.d("TAG" , "||onLoadFinished called npdv||");
                    adapterImplementacion.swapCursor(data);
                    emptyView.setText("");
                }
            case 7:
                if (loader != null && adapterPDV != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    Log.d("TAG" , "Implementacion = " + data);
                    adapterPDV.swapCursor(data);
                    emptyView.setText("");
                }
            case 8:
                if (loader != null && adapterShare != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterShare.swapCursor(data);
                    emptyView.setText("");
                }
            case 9:
                if (loader != null && adapterAgotados != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterAgotados.swapCursor(data);
                    emptyView.setText("");
                }
            case 10:
                if (loader != null && adapterVenta != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterVenta.swapCursor(data);
                    emptyView.setText("");
                }
            case 11:
                if (loader != null && adapterFotografico != null) {
                    Log.d("TAG" , "||onLoadFinished called fotografico||");
                    adapterFotografico.swapCursor(data);
                    emptyView.setText("");
                }
            case 12:
                if (loader != null && adapterPreguntas != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterPreguntas.swapCursor(data);
                    emptyView.setText("");
                }
            case 13:
                if (loader != null && adapterPacks != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterPacks.swapCursor(data);
                    emptyView.setText("");
                }
            case 14:
                if (loader != null && adapterProdCad != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterProdCad.swapCursor(data);
                    emptyView.setText("");
                }
            case 15:
                if (loader != null && adapterImpulso != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterImpulso.swapCursor(data);
                    emptyView.setText("");
                }
            case 16:
                if (loader != null && adapterRotacion != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterRotacion.swapCursor(data);
                    emptyView.setText("");
                }
            case 17:
                if (loader != null && adapterSugeridos != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterSugeridos.swapCursor(data);
                    emptyView.setText("");
                }
            case 18:
                if (loader != null && adapterTareas != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterTareas.swapCursor(data);
                    emptyView.setText("");
                }
            case 19:
                if (loader != null && adapterCanjes != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterCanjes.swapCursor(data);
                    emptyView.setText("");
                }
            case 20:
                if (loader != null && adapterMCI != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterMCI.swapCursor(data);
                    emptyView.setText("");
                }
            case 21:
                if (loader != null && adapterMaterialesRecibidos != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterMaterialesRecibidos.swapCursor(data);
                    emptyView.setText("");
                }
            case 22:
                if (loader != null && adapterEjecucionMateriales != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterEjecucionMateriales.swapCursor(data);
                    emptyView.setText("");
                }
            case 23:
                if (loader != null && adapterPDI != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterPDI.swapCursor(data);
                    emptyView.setText("");
                }
            case 24:
                if (loader != null && adapterCodificados != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterCodificados.swapCursor(data);
                    emptyView.setText("");
                }
            case 25:
                if (loader != null && adapterEvidencias != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterEvidencias.swapCursor(data);
                    emptyView.setText("");
                }
            case 26:
                if (loader != null && adapterTracking != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterTracking.swapCursor(data);
                    emptyView.setText("");
                }
            case 27:
                if (loader != null && adapterConvenios != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterConvenios.swapCursor(data);
                    emptyView.setText("");
                }

            case 28:
            if (loader != null && adapterEncuesta != null) {
                Log.d("TAG" , "||onLoadFinished called||");
                adapterEncuesta.swapCursor(data);
                emptyView.setText("");
            }
            case 29:  // PARA NOVEDADES
                if (loader != null && adapterNovedades != null) {
                    Log.d("TAG", "||onLoadFinished called NOVEDADES - ID: " + loader.getId() +
                            ", Registros: " + (data != null ? data.getCount() : 0) + "||");
                    adapterNovedades.swapCursor(data);

                    // Actualizar emptyView según si hay datos o no
                    if (data == null || data.getCount() == 0) {
                        emptyView.setText("No hay registros de novedades");
                    } else {
                        emptyView.setText("");
                    }
                }
                break;
            case 30:
                if (loader != null && adapterVenta != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterVenta.swapCursor(data);
                    emptyView.setText("");
                }
            case 31:
                if (loader != null && adapterProbadores != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterProbadores.swapCursor(data);
                    emptyView.setText("");
                }
            case 32:
                if (loader != null && adapterMuestras != null) {
                    Log.d("TAG" , "||onLoadFinished called||");
                    adapterMuestras.swapCursor(data);
                    emptyView.setText("");
                }

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
       // adapterPrecio.swapCursor(null);

    }

    public void vaciarHistorial() {
        try{
            //Reportes: Eliminar todos los registros que han sido enviados (Pendienteinsercion=0)
            String selection =  Constantes.PENDIENTE_INSERCION + "=?";
            String[] selectionArgs = new String[]{"0"};
            getContentResolver().delete(ContractInsertPrecios.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertExh.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertGps.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(InsertFlooring.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertPromocion.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertProbadores.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertMuestras.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertValores.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertImplementacion.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractNotificacion.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertShare.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertAgotados.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertVenta.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertFotografico.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertPreguntas.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertProdCaducar.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertPacks.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertImpulso.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertRotacion.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertCanjes.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertMCIPdv.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertMaterialesRecibidos.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertEjecucionMateriales.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertPDI.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertCodificados.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertEvidencias.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertTracking.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertConvenios.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertEvaluacionEncuesta.CONTENT_URI,selection,selectionArgs);
            getContentResolver().delete(ContractInsertNovedades.CONTENT_URI,selection,selectionArgs);

//            getContentResolver().delete(ContractLog.CONTENT_URI,selection,selectionArgs);

            //Reload la actividad
            finish();
            startActivity(getIntent());
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_hist) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setTitle("Vaciar Historial");
            builder.setMessage("¿Desea eliminar los registros?");
            builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    vaciarHistorial();
                }
            });

            builder.setNeutralButton("NO",null);

            AlertDialog ad = builder.create();
            ad.show();
            Button pButton = ad.getButton(DialogInterface.BUTTON_POSITIVE);
            //pButton.setTextColor(Color.rgb(79, 195, 247));
            Button cButton = ad.getButton(DialogInterface.BUTTON_NEUTRAL);
            //cButton.setTextColor(Color.rgb(79, 195, 247));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onClick(View v) {
        if (v == btnFinicio) {
            inicioPromo();
        }
        if (v == btnFfin) {
            String fechaInicio = txtFinicio.getText().toString();
            if (!fechaInicio.trim().isEmpty()) {
                finPromo();
            } else {
                Toast.makeText(this, "Primero seleccione fecha incio", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void inicioPromo() {
        try {
            final Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            int anio = calendar.get(Calendar.YEAR);
            int mes = calendar.get(Calendar.MONTH);
            int dia = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog from_dateListener = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = null;
                    try {
                        date = dateFormat.parse(dayOfMonth + "/" + (month + 1) + "/" + year);
                        String outDate = dateFormat.format(date);
                        txtFinicio.setText(outDate);

                        fDesde = outDate;

                        if (!txtFfin.getText().toString().trim().isEmpty()) {
                            txtFfin.setText("Fecha fin");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, anio, mes, dia);
            from_dateListener.show();
        } catch (Exception e) {
            System.out.println("INI PROMO: " + e.getMessage());
        }
    }

    public void finPromo() {
        try {
            final Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            int anio = calendar.get(Calendar.YEAR);
            int mes = calendar.get(Calendar.MONTH);
            int dia = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog from_dateListener = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = null;
                    try {
                        date = dateFormat.parse(dayOfMonth + "/" + (month+1) + "/" + year);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String outDate = dateFormat.format(date);
                    txtFfin.setText(outDate);

                    fHasta = outDate;
                }
            },anio,mes,dia);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.setLenient(false);

            String oldTime = txtFinicio.getText().toString();
            Date oldDate = formatter.parse(oldTime);

            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(oldDate);
            calendar1.add(Calendar.DAY_OF_MONTH, 6); // Suma 6 días
            Date newDate = calendar1.getTime();

            long newMillis = newDate.getTime();
            long oldMillis = oldDate.getTime();

            // set maximum date to be selected as today
            from_dateListener.getDatePicker().setMinDate(oldMillis);
            from_dateListener.getDatePicker().setMaxDate(newMillis);
            from_dateListener.show();
        } catch (Exception e) {
            System.out.println("FIN PROMO: " + e.getMessage());
        }
    }



    /*public void generatePDF(RecyclerView view) {

        RecyclerView.Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {

                    bitmaCache.put(String.valueOf(i), drawingCache);
                }

                height += holder.itemView.getMeasuredHeight();
            }

            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            bigCanvas.drawColor(Color.WHITE);

            Document document = new Document(PageSize.A4);
            final File file = new File(getStorageDir("PDF"), "print.pdf");
            try {
                PdfWriter.getInstance(document, new FileOutputStream(file));
            } catch (DocumentException | FileNotFoundException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < size; i++) {

                try {
                    //Adding the content to the document
                    Bitmap bmp = bitmaCache.get(String.valueOf(i));
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Image image = Image.getInstance(stream.toByteArray());
                    float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                            - document.rightMargin() - 0) / image.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
                    image.scalePercent(scaler);
                    image.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER | com.itextpdf.text.Image.ALIGN_TOP);
                    if (!document.isOpen()) {
                        document.open();
                    }
                    document.add(image);

                } catch (Exception ex) {
                    Log.e("TAG-ORDER PRINT ERROR", ex.getMessage());
                }
            }

            if (document.isOpen()) {
                document.close();
            }
            // Set on UI Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    AlertDialog.Builder builder = new AlertDialog.Builder(HistorialActivity.this);
                    builder.setTitle("Success")
                            .setMessage("PDF File Generated Successfully.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(intent);
                                }

                            }).show();
                }
            });

        }

    }*/

}
