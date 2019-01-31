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
import android.widget.ProgressBar;
import android.widget.Toast;

public class SignUpFragment extends Fragment {

    private SignUpFragment.OnFragmentInteractionListener listener;
    SessionManager session;


    public static SignUpFragment newInstance() {
        return new SignUpFragment();
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

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Sign Up");

        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        final Button signupbutton = view.findViewById(R.id.button4);
        final ProgressBar pb =  view.findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
       signupbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                pb.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),
                        "Account successfully created. You can now login.",
                        Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(2000); //1000 milliseconds is one second.
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
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
        if (context instanceof SignUpFragment.OnFragmentInteractionListener) {
            listener = (SignUpFragment.OnFragmentInteractionListener) context;
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