package com.draos.nekretnine.nekretnineui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FavouritesFragment extends Fragment {

    private FavouritesFragment.OnFragmentInteractionListener listener;
    SessionManager session;

    public static FavouritesFragment newInstance() {
        return new FavouritesFragment();
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

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Favourites");

        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        TextView tv = (TextView) view.findViewById(R.id.tvFavourites);

        if (!session.isLoggedIn()) {
            tv.setText("Click here to log in in order to see your Favourites.");
        }
        else{
            tv.setVisibility(View.INVISIBLE);
            Fragment fragment =  new AdvertiseFragment();
            //Put the value
            Bundle args = new Bundle();
            args.putString("favourites","favourites");
            fragment.setArguments(args);

            //Inflate the fragment
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).addToBackStack(null).commitAllowingStateLoss();


        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AccountFragment newfragment = new AccountFragment();
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
        if (context instanceof FavouritesFragment.OnFragmentInteractionListener) {
            listener = (FavouritesFragment.OnFragmentInteractionListener) context;
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
