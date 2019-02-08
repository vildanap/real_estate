package com.draos.nekretnine.nekretnineui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdvertsFragment extends Fragment {

    private MyAdvertsFragment.OnFragmentInteractionListener listener;
    SessionManager session;

    public static MyAdvertsFragment newInstance() {
        return new MyAdvertsFragment();
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

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Adverts");
        View view = inflater.inflate(R.layout.fragment_myadverts, container, false);

        Fragment fragment =  new AdvertiseFragment();
        //Put the value
        Bundle args = new Bundle();
        args.putString("myAdverts","myAdverts");
        fragment.setArguments(args);

        //Inflate the fragment
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).addToBackStack(null).commitAllowingStateLoss();


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyAdvertsFragment.OnFragmentInteractionListener) {
            listener = (MyAdvertsFragment.OnFragmentInteractionListener) context;
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
