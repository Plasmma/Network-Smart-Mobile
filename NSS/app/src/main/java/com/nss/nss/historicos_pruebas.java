package com.nss.nss;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import java.util.ArrayList;
import java.util.Objects;


public class historicos_pruebas extends Fragment {


    private TableLayout table;
    private ArrayList<String> registros;
    private Button btnBuscar;
    public static EditText txtBuscar;
    private boolean buscando;
    private AdminSql adminSql;
    private CalendarioDialog calendarioFecha;
    private TableLayoutDinamico tablaDinamica;
    private String[] cabezera = {"Id", "Fecha", "Dbm", "Asu", "Codigo", "Red", "Tipo red"};
    private Typeface letra;
    private Spinner spinerFiltrar;
    private String CBuscada = "fecha";
    private OnFragmentInteractionListener mListener;

    public historicos_pruebas() {
        // Required empty public constructor

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_redes_moviles,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuExportar: {
                adminSql.exportarBase();
                break;
            }
            case R.id.menuAcercaDe:{
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.action_acerca_de);
                builder.setMessage(R.string.contenido_acerca_de);
                builder.show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        letra = Typeface.createFromAsset(Objects.requireNonNull(getContext()).getAssets(), "fuentes/TitilliumWeb-Black.ttf");
        adminSql = new AdminSql(getContext(), "mydb", null, 1);
        calendarioFecha = new CalendarioDialog(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_historicos_pruebas, container, false);
        table = vista.findViewById(R.id.tablelayout);
        spinerFiltrar = vista.findViewById(R.id.spinner);
        spinerFiltrar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        CBuscada = "fecha";
                        break;
                    case 1:
                        CBuscada = "dbm";
                        break;
                    case 2:
                        CBuscada = "asu";
                        break;
                    case 3:
                        CBuscada = "tipo_de_red";
                        break;
                    case 4:
                        CBuscada = "tipo_de_red_telefonica";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnBuscar = vista.findViewById(R.id.btnBuscar);
        txtBuscar = vista.findViewById(R.id.txtBuscar);
        btnBuscar.setTypeface(letra);

        txtBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CBuscada.equals("fecha"))
                    calendarioFecha.mostar();
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table.removeAllViews();
                buscando = true;
                agregarRegistrosAtabla();
            }
        });
        agregarRegistrosAtabla();
        return vista;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void agregarRegistrosAtabla() {
        if (buscando) {
            registros = adminSql.regresarRegistrosConsulta(txtBuscar.getText().toString(), registros, CBuscada);
            buscando = false;
        } else
            registros = adminSql.regresarRegistros(registros);
        Log.w("Registros", adminSql.getTotalRegistros() + " " + registros.size());
        tablaDinamica = new TableLayoutDinamico(table, getContext());
        tablaDinamica.agregarCabezeras(cabezera);
        tablaDinamica.agregarRegistrosTable(adminSql.getTotalRegistros(), registros);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
