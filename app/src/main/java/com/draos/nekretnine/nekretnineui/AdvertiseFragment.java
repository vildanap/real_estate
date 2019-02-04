package com.draos.nekretnine.nekretnineui;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import android.widget.Button;
import com.draos.nekretnine.nekretnineui.Model.Advertise;
import com.draos.nekretnine.nekretnineui.Model.City;
import com.draos.nekretnine.nekretnineui.Model.Location;
import com.draos.nekretnine.nekretnineui.Model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdvertiseFragment extends Fragment {
    AdvertiseAdapter advertiseAdapter;
    RecyclerView recyclerView;
    List<Advertise> advertiseList = new ArrayList<Advertise>();
    Button btnPrice;
    Button btnArea;
    Boolean ascPrice= true;
    Boolean ascArea= true;
    Drawable imgArrowDown;
    Drawable imgArrowUp;

    private OnFragmentInteractionListener listener;
    public static AdvertiseFragment newInstance() {
        return new AdvertiseFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String cardClicked = getArguments().getString("ClickedCard");
        if(cardClicked.equals("sales")) {
            getSalesList();
        }
        else{ getRentalsList();}
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Advertises");


        View view = inflater.inflate(R.layout.fragment_advertise, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        imgArrowDown = getResources().getDrawable(R.drawable.ic_keyboard_arrow_down);
        imgArrowUp = getResources().getDrawable(R.drawable.ic_keyboard_arrow_up);
        advertiseAdapter = new AdvertiseAdapter(advertiseList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(advertiseAdapter);

        btnPrice = view.findViewById(R.id.button_sortPrice);
        btnArea = view.findViewById(R.id.button_sortArea);



        btnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               sortDatabyPrice(ascPrice);
                ascPrice = !ascPrice;

               if(ascPrice == true) {
                   btnPrice.setCompoundDrawablesWithIntrinsicBounds(imgArrowDown, null, null, null);
               }
               else {
                   btnPrice.setCompoundDrawablesWithIntrinsicBounds(imgArrowUp, null, null, null);
               }
                btnPrice.setTypeface(null, Typeface.BOLD);
                btnArea.setTypeface(null, Typeface.NORMAL);
            }
        });

        btnArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sortDatabyArea(ascArea);
                ascArea = !ascArea;

                if(ascArea == true) {
                    btnArea.setCompoundDrawablesWithIntrinsicBounds(imgArrowDown, null, null, null);
                }
                else {
                    btnArea.setCompoundDrawablesWithIntrinsicBounds(imgArrowUp, null, null, null);
                }
                btnArea.setTypeface(null, Typeface.BOLD);
                btnPrice.setTypeface(null, Typeface.NORMAL);
            }
        });

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
        User u= new User("Zerina","1234","Zerina","Dragnic","zdragnic@gmail.com","+38762554678");
        City c = new City("Sarajevo");
        Location l = new Location("Pofalici",c);
        Advertise a = new Advertise("Sale","Opis opis",6000,87.87,"Sale","House",23,3,"Avde Hume",u,l);
        Advertise b = new Advertise("Sale","Opis opis",100,80,"Sale","House",23,3,"Avde Hume",u,l);
        Advertise d = new Advertise("Sale","Opis opis",5000,40,"Sale","House",23,3,"Avde Hume",u,l);

        advertiseList.add(a);
        advertiseList.add(b);
        advertiseList.add(d);


    }

    //TODO retrofit getRentals
    public void getRentalsList() {
        User u= new User("Zerina","1234","Zerina","Dragnic","zdragnic@gmail.com","+38762554678");
        City c = new City("Sarajevo");
        Location l = new Location("Pofalici",c);
        Advertise a = new Advertise("Rental","Opis opis",6000,87.87,"Rental","House",23,3,"Avde Hume",u,l);
        Advertise b = new Advertise("Rental","Opis opis",100,80,"Rental","House",23,3,"Avde Hume",u,l);
        Advertise d = new Advertise("Rental","Opis opis",5000,40,"Rental","House",23,3,"Avde Hume",u,l);

        advertiseList.add(a);
        advertiseList.add(b);
        advertiseList.add(d);

    }
    //TODO search results

    //Sort
    private void sortDatabyPrice(boolean asc)
    {
        //SORT ARRAY ASCENDING AND DESCENDING
        if (asc)
        {
            Collections.sort(advertiseList, new Comparator<Advertise>() {
                public int compare(Advertise a1, Advertise a2) {
                    return String.valueOf(a1.getPrice()).compareTo(String.valueOf(a2.getPrice()));
                }
            });
        }
        else
        {
            Collections.reverse(advertiseList);
        }
        advertiseAdapter = new AdvertiseAdapter(advertiseList, getActivity());
        recyclerView.setAdapter(advertiseAdapter);
    }
    //Sort
    private void sortDatabyArea(boolean asc)
    {
        //SORT ARRAY ASCENDING AND DESCENDING
        if (asc)
        {
            Collections.sort(advertiseList, new Comparator<Advertise>() {
                public int compare(Advertise a1, Advertise a2) {
                    return String.valueOf(a1.getArea()).compareTo(String.valueOf(a2.getArea()));
                }
            });
        }
        else
        {
            Collections.reverse(advertiseList);
        }
        advertiseAdapter = new AdvertiseAdapter(advertiseList, getActivity());
        recyclerView.setAdapter(advertiseAdapter);
    }

    public interface OnFragmentInteractionListener {
    }
}