package com.draos.nekretnine.nekretnineui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.draos.nekretnine.nekretnineui.Model.Advertise;

import java.util.ArrayList;
import java.util.List;

public class AdvertiseFragment extends Fragment {
    AdvertiseAdapter movieAdapter;
    RecyclerView recyclerView;
    List<Advertise> movieList = new ArrayList<Advertise>();

    private OnFragmentInteractionListener listener;
    public static AdvertiseFragment newInstance() {
        return new AdvertiseFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Advertises");

        String cardClicked = getArguments().getString("ClickedCard");

        View view = inflater.inflate(R.layout.fragment_advertise, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);

        movieAdapter = new AdvertiseAdapter(movieList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(movieAdapter);

        if(cardClicked.equals("sales")) {
            getSalesList();
        }
        else{ getRentalsList();}
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
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

    //TODO retrofit getSales
    public void getSalesList() {
    Advertise a = new Advertise();
    a.setTitle("Prvi sale");
    a.setPrice("500000KM");
    Advertise b = new Advertise();
    b.setTitle("Drugi sale");
    b.setPrice("100000KM");
    movieList.add(a);
    movieList.add(b);
    movieList.add(a);
    }

    //TODO retrofit getRentals
    public void getRentalsList() {
        Advertise a = new Advertise();
        a.setTitle("Prvi rental");
        a.setPrice("250KM");
        Advertise b = new Advertise();
        b.setTitle("Drugi rental");
        b.setPrice("500KM");
        movieList.add(a);
        movieList.add(b);
        movieList.add(a);
    }
    //TODO search results

    public interface OnFragmentInteractionListener {
    }
}