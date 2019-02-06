package com.draos.nekretnine.nekretnineui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class HomeFragment extends Fragment {
    private SearchFragment.OnFragmentInteractionListener listener;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");

        // recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        CardView sales = (CardView) view.findViewById(R.id.sales_card);
        CardView rentals = (CardView) view.findViewById(R.id.rentals_card);
        ImageView help = (ImageView) view.findViewById(R.id.imageView2);
        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Fragment fragment = new AdvertiseFragment();
                //Put the value
                Bundle args = new Bundle();
                args.putString("ClickedCard", "sales");
                fragment.setArguments(args);

                //Inflate the fragment
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).addToBackStack(null).commit();

            }
        });

        rentals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Fragment fragment =  new AdvertiseFragment();
                //Put the value
                Bundle args = new Bundle();
                args.putString("ClickedCard", "rentals");
                fragment.setArguments(args);

                //Inflate the fragment
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).addToBackStack(null).commitAllowingStateLoss();

            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                //Inflate the fragment
             //   AppCompatActivity activity = (AppCompatActivity) v.getContext();
              //  activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).addToBackStack(null).commitAllowingStateLoss();
                HelpFragment newfragment = new HelpFragment();
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
        if (context instanceof SearchFragment.OnFragmentInteractionListener) {
            listener = (SearchFragment.OnFragmentInteractionListener) context;
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
