package com.nss.nss;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.github.anastr.speedviewlib.SpeedView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link pruebas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link pruebas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pruebas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SpeedView speedView;
    private TextView tw;
    private TelephonyManager tm;
    private int mSignalStrength;
    private int ss;
    private OnFragmentInteractionListener mListener;


    public pruebas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pruebas.
     */
    // TODO: Rename and change types and number of parameters
    public static pruebas newInstance(String param1, String param2) {
        pruebas fragment = new pruebas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        tm = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        MyPhoneStateListener phoneListen = new MyPhoneStateListener();
        tm.listen(phoneListen,PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

    class MyPhoneStateListener extends PhoneStateListener {

        //este metodo imforma mediante un Toask la velocidad de asu y dbm para las redes 2G
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            int ss =signalStrength.getGsmSignalStrength();//as
            mSignalStrength = signalStrength.getGsmSignalStrength();
            mSignalStrength = (2 * mSignalStrength) - 113; // -> dBm
            //al poner esto y detruir la actividad da un error
            speedView.speedTo(ss);
            tw.setText(String.valueOf(ss));

            //Toast.makeText(getContext()," "+ss+mSignalStrength,Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_pruebas, container, false);
        speedView = vista.findViewById(R.id.speedView);
        tw = vista.findViewById(R.id.dbm);

        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
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



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
