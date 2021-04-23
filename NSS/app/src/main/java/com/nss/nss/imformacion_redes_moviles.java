package com.nss.nss;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class imformacion_redes_moviles extends Fragment {


    private List<String> ListaDatosRM = new ArrayList<>();
    private ArrayAdapter AdapterDatosRedes;
    private GridView GridListaDatos;
    private imformacionDispositivos info;
    private TelephonyManager tm;
    private TelefonoMedida telefonoMedida;
    private int escucharTelefono = PhoneStateListener.LISTEN_DATA_ACTIVITY | PhoneStateListener.LISTEN_DATA_CONNECTION_STATE;


    private OnFragmentInteractionListener mListener;

    public imformacion_redes_moviles() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tm = (TelephonyManager) Objects.requireNonNull(getContext()).getSystemService(Context.TELEPHONY_SERVICE);
        info = new imformacionDispositivos(getContext());
        AdapterDatosRedes = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, ListaDatosRM);
        info.getImformationRedesMoviles(ListaDatosRM);
        telefonoMedida = new TelefonoMedida(AdapterDatosRedes, getActivity(), ListaDatosRM);
        tm.listen(telefonoMedida, escucharTelefono);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_imformacion_redes_moviles, container, false);

        GridListaDatos = vista.findViewById(R.id.FIRM_gridViewDatos);
        GridListaDatos.setAdapter(AdapterDatosRedes);

        return vista;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
