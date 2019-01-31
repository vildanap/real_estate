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

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Your Profile");

        View view = inflater.inflate(R.layout.fragment_loggeduser, container, false);
        final Button logoutbutton = view.findViewById(R.id.button2);

        logoutbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                session.logoutUser();
                AccountFragment newfragment = new AccountFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), newfragment);
                fragmentTransaction.commit();
            }
        });

     /*   TextView tv = (TextView) view.findViewById(R.id.tvFavourites);

        if (!session.isLoggedIn()) {
            tv.setText("Please log in to see Favourites.");
        }
        else{
            tv.setText("Favourites");
        }
*/
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