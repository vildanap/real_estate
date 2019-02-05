package com.draos.nekretnine.nekretnineui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LoggedUserFragment extends Fragment {

    private LoggedUserFragment.OnFragmentInteractionListener listener;
    SessionManager session;

    public static LoggedUserFragment newInstance() {
        return new LoggedUserFragment();
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
       final Bundle extras = getArguments();
       //final long d = extras.getLong("id");
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Long.valueOf(d).toString());

        View view = inflater.inflate(R.layout.fragment_loggeduser, container, false);
        final Button logoutbutton = view.findViewById(R.id.button2);
        final Button editProfile = view.findViewById(R.id.button11);
        final Button createADbutton = view.findViewById(R.id.button9);

        logoutbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                session.logoutUser();
                AccountFragment newfragment = new AccountFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), newfragment);
                fragmentTransaction.commit();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                EditProfileFragment newfragment = new EditProfileFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), newfragment);
                fragmentTransaction.commit();
            }
        });

        createADbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                AdvertCreateFragment newfragment = new AdvertCreateFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), newfragment);
                fragmentTransaction.commit();
            }
        });
      return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoggedUserFragment.OnFragmentInteractionListener) {
            listener = (LoggedUserFragment.OnFragmentInteractionListener) context;
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