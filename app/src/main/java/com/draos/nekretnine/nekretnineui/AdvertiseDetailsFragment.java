package com.draos.nekretnine.nekretnineui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdvertiseDetailsFragment extends Fragment {
    private AdvertiseDetailsFragment.OnFragmentInteractionListener listener;
    SessionManager session;

    public static AdvertiseDetailsFragment newInstance() {
        return new AdvertiseDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Session class instance
        session = new SessionManager(this.getContext());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_advertise_details, container, false);
        TextView adTitle = view.findViewById(R.id.textView_adTitle);
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            String title = bundle.get("title").toString();
            String author = bundle.get("price").toString();
            adTitle.setText(title);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);

        }


               return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AdvertiseDetailsFragment.OnFragmentInteractionListener) {
            listener = (AdvertiseDetailsFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
    }

}
