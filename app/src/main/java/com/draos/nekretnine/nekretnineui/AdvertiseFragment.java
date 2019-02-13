package com.draos.nekretnine.nekretnineui;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.draos.nekretnine.nekretnineui.Model.*;
import com.draos.nekretnine.nekretnineui.Services.AdvertService;
import com.draos.nekretnine.nekretnineui.Services.RealEstateServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

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

    SessionManager session;
    Long userId;
    String cardClicked;
    private OnFragmentInteractionListener listener;
    public static AdvertiseFragment newInstance() {
        return new AdvertiseFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       cardClicked = getArguments().getString("ClickedCard");
        String favourites = getArguments().getString("favourites");
        String myAdverts = getArguments().getString("myAdverts");
        String search =  getArguments().getString("Search");
        session = new SessionManager(this.getContext());
        if(session.isLoggedIn())
         userId = Long.valueOf(session.getUserDetails().get("email"));

        if(cardClicked!=null) {
            if (cardClicked.equals("sales")) {
                getSalesList();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Sales");

            }
            else if (cardClicked.equals("rentals"))
            {
                getRentalsList();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Rentals");

            }
        }
        if(favourites!=null){
            //Log.d("ID",session.getUserDetails().toString());
            getFavourites(userId);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Favourites");
        }
        if(myAdverts != null){
            getPostedBy(userId);
        }

        if(search != null){
            AdvertSearch as = new AdvertSearch();
            as.setAdvertType(getArguments().getLong("advertType"));
            as.setCityId(getArguments().getLong("city"));
            as.setLocationId(getArguments().getLong("settlement"));
            as.setNumberOfRooms(getArguments().getLong("rooms"));
            as.setMinPrice(getArguments().getLong("minPrice"));
            as.setMaxPrice(getArguments().getLong("maxPrice"));
            searchAdverts(as);
        }

        if(advertiseList != null) {
            advertiseAdapter = new AdvertiseAdapter(advertiseList, getContext());
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_advertise, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        imgArrowDown = getResources().getDrawable(R.drawable.ic_keyboard_arrow_down);
        imgArrowUp = getResources().getDrawable(R.drawable.ic_keyboard_arrow_up);
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

        AdvertService service = RealEstateServiceGenerator.createService(AdvertService.class);
        try {
            final Call<List<Advertise>> call = service.getSale();
            call.enqueue(new Callback<List<Advertise>>() {
                @Override
                public void onResponse(Call<List<Advertise>> call, Response<List<Advertise>> response) {
                    if (response.isSuccessful()) {
                        if(response.body()!=null) {
                            for (int i = 0; i < response.body().size(); i++) {

                                Advertise a = new Advertise();
                                a = response.body().get(i);
                                advertiseList.add(a);
                            }
                            advertiseAdapter.notifyDataSetChanged();
                        }

                    } else {
                        System.out.println(response.message());
                        Toast.makeText(getContext(),
                                "No adverts for sale yet",
                                LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Advertise>> call, Throwable t) {
                    Toast.makeText(getContext(),
                            "An error has ocurred.Try again.",
                            LENGTH_LONG).show();
                    Log.d("GRESKA", "onFailure: "+ t.getMessage());
                }

            });
        }
        catch(Exception e){
            Toast.makeText(getContext(),e.getMessage(),LENGTH_LONG).show();
        }


    }

    public void getRentalsList() {
        AdvertService service = RealEstateServiceGenerator.createService(AdvertService.class);
        try {
            final Call<List<Advertise>> call = service.getRent();
            call.enqueue(new Callback<List<Advertise>>() {
                @Override
                public void onResponse(Call<List<Advertise>> call, Response<List<Advertise>> response) {
                    if (response.isSuccessful()) {

                        if(response.body()!=null) {
                            for (int i = 0; i < response.body().size(); i++) {

                                Advertise a = new Advertise();
                                a = response.body().get(i);
                                advertiseList.add(a);
                            }
                            advertiseAdapter.notifyDataSetChanged();
                        }

                    } else {
                        System.out.println(response.message());
                        Toast.makeText(getContext(),
                                "No adverts for rent yet",
                                LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Advertise>> call, Throwable t) {
                    Toast.makeText(getContext(),
                            "An error has ocurred.Try again.",
                            LENGTH_LONG).show();
                    Log.d("GRESKA", "onFailure: "+ t.getMessage());
                }

            });


        }
        catch(Exception e){
            Toast.makeText(getContext(),e.getMessage(),LENGTH_LONG).show();
        }
    }

    public void getPostedBy(long userId) {
        AdvertService service = RealEstateServiceGenerator.createService(AdvertService.class);
        try {
            final Call<List<Advertise>> call = service.getAdvertsPostedBy(userId);
            call.enqueue(new Callback<List<Advertise>>() {
                @Override
                public void onResponse(Call<List<Advertise>> call, Response<List<Advertise>> response) {
                    if (response.isSuccessful()) {

                        if(response.body()!=null) {
                            for (int i = 0; i < response.body().size(); i++) {

                                Advertise a = new Advertise();
                                a = response.body().get(i);
                                advertiseList.add(a);
                            }
                            advertiseAdapter.notifyDataSetChanged();
                        }
                        else {
                            System.out.println(response.message());
                            Toast.makeText(getContext(),
                                    "No posted adverts yet",
                                    LENGTH_LONG).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<List<Advertise>> call, Throwable t) {
                    Toast.makeText(getContext(),
                            "An error has ocurred.Try again.",
                            LENGTH_LONG).show();
                    Log.d("GRESKA", "onFailure: "+ t.getMessage());
                }

            });


        }
        catch(Exception e){
            Toast.makeText(getContext(),e.getMessage(),LENGTH_LONG).show();
        }

    }
    //TODO search results
    public void searchAdverts(AdvertSearch as) {
        AdvertService service = RealEstateServiceGenerator.createService(AdvertService.class);
        try {
            final Call<List<Advertise>> call = service.searchAdverts(as);
            call.enqueue(new Callback<List<Advertise>>() {
                @Override
                public void onResponse(Call<List<Advertise>> call, Response<List<Advertise>> response) {
                    if (response.isSuccessful()) {

                        if(response.body()!=null) {
                            for (int i = 0; i < response.body().size(); i++) {

                                Advertise a = new Advertise();
                                a = response.body().get(i);
                                advertiseList.add(a);
                            }
                            advertiseAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(getContext(),
                                    "No results for your search",
                                    LENGTH_LONG).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<Advertise>> call, Throwable t) {
                    Toast.makeText(getContext(),
                            "An error has ocurred.Try again.",
                            LENGTH_LONG).show();
                    Log.d("GRESKA", "onFailure: "+ t.getMessage());
                }

            });

        }
        catch(Exception e){
            Toast.makeText(getContext(),e.getMessage(),LENGTH_LONG).show();
            Log.d("GRESKA", "onFailure: "+ e.getMessage());

        }

    }
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

    private void getFavourites(long userId){
        AdvertService service = RealEstateServiceGenerator.createService(AdvertService.class);
        try {
            final Call<List<Advertise>> call = service.getFavorites(userId);
            call.enqueue(new Callback<List<Advertise>>() {
                @Override
                public void onResponse(Call<List<Advertise>> call, Response<List<Advertise>> response) {
                    if (response.isSuccessful()) {

                        if(response.body()!=null) {
                            for (int i = 0; i < response.body().size(); i++) {

                                Advertise a = new Advertise();
                                a = response.body().get(i);
                                advertiseList.add(a);
                            }
                            advertiseAdapter.notifyDataSetChanged();
                        }

                    } else {
                        System.out.println(response.message());
                        Toast.makeText(getContext(),
                                "No favourites adverts yet",
                                LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Advertise>> call, Throwable t) {
                    Toast.makeText(getContext(),
                            "An error has ocurred.Try again.",
                            LENGTH_LONG).show();
                    Log.d("GRESKA", "onFailure: "+ t.getMessage());
                }

            });


        }
        catch(Exception e){
            Toast.makeText(getContext(),e.getMessage(),LENGTH_LONG).show();
        }

    }

    public interface OnFragmentInteractionListener {
    }
}