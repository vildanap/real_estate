package com.draos.nekretnine.nekretnineui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.appyvet.materialrangebar.RangeBar;


public class SearchFragment extends Fragment {
    private OnFragmentInteractionListener listener;

    int iCurrentSelection;
    Button resetbtn;
    Button searchbtn;
    Switch rentals;
    Switch sales;
    com.travijuu.numberpicker.library.NumberPicker numberPickerRooms;
    Spinner cities;
    Spinner settlements;
    RangeBar rangeBarPrice;
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Search");

        //Fields
        sales = view.findViewById(R.id.switch1);
        rentals = view.findViewById(R.id.switch2);
        numberPickerRooms =  view.findViewById(R.id.number_picker);
        resetbtn = view.findViewById(R.id.buttonReset);
        searchbtn = view.findViewById(R.id.buttonSearch);
        cities =  view.findViewById(R.id.spinnercities);
        settlements = view.findViewById(R.id.spinnercities2);
        rangeBarPrice =  view.findViewById(R.id.rangebar1);

        //TODO Retrofit
        String[] items = new String[]{"All","Sarajevo", "Tuzla", "Mostar"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        cities.setAdapter(adapter);

        String[] items2 = new String[]{"All"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, items2);
        settlements.setAdapter(adapter2);

        cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (iCurrentSelection != i){
                    // Your code here
                    String[] SettlementSa= null;
                    if(i==0){
                        SettlementSa= new String[]{"All"};
                    }
                    if(i==1){
                        SettlementSa= new String[]{"All","Pofalici", "Ciglane", "Grbavica"};
                    }
                    else if(i==2){
                        SettlementSa = new String[]{"All","Mosnik", "Slatina", "Miladije"};
                    }
                    else if(i==3)
                    {
                        SettlementSa = new String[]{"All","Brankovac", "Bijeli Brijeg", "Zalik"};

                    }
                    ArrayAdapter<String> adapterProperty = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,SettlementSa );
                    settlements.setAdapter(adapterProperty);

                }
                iCurrentSelection = i;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        //Zato sto po defaultu dozvoljava samo 4 cifre, ovako citav broj prikaze
        rangeBarPrice.setPinTextFormatter(new RangeBar.PinTextFormatter() {
            @Override
            public String getText(String value) {
                return value;
            }
        });

        //Reset search
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                rangeBarPrice.setRangePinsByValue(100,200000);
                numberPickerRooms.setValue(1);
                rentals.setChecked(true);
                sales.setChecked(true);
                cities.setSelection(0);
                settlements.setSelection(0);
            }
        });

        //Search
        //Reset search
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Boolean sale = sales.isChecked();
                Boolean rent = rentals.isChecked();
                Integer minPrice= Integer.valueOf(rangeBarPrice.getLeftPinValue());
                Integer maxPrice= Integer.valueOf(rangeBarPrice.getRightPinValue());
                Integer rooms = numberPickerRooms.getValue();
                Long city = cities.getSelectedItemId();
                Long settlement = settlements.getSelectedItemId();
                Log.d("Search", "onClick: "+ sale +","+ rent+","+ minPrice+","+maxPrice+","+rooms+","+city+","+settlement);

                //TODO proslijediti parametre u obliku stringa
                Fragment fragment =  new AdvertiseFragment();
                //Put the value
                Bundle args = new Bundle();
                args.putString("Search", "Search");
                if(sale == true && rent==true){
                    args.putLong("advertType", 3);
                }
                else if(sale == true && rent == false){
                    args.putLong("advertType", 2);
                }
                else if(sale == false && rent == true){
                    args.putLong("advertType", 1);
                }

                args.putLong("minPrice", minPrice.longValue());
                args.putLong("maxPrice", maxPrice.longValue());
                args.putLong("rooms", rooms);

                if((settlement == 0 && city==0) || settlement==0){
                    args.putLong("settlement", 0);
                }
                else if (city==1){
                    args.putLong("settlement", settlement);
                }
                else if(city == 2){
                    args.putLong("settlement", settlement + 3);

                }
                else if (city == 3){
                    args.putLong("settlement", settlement+ 6);
                }
                args.putLong("city", city);
                Log.d("Searchhh", args.toString());
                fragment.setArguments(args);

                //Inflate the fragment
                AppCompatActivity activity = (AppCompatActivity) getView().getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment,"Tag_Search")
                        .addToBackStack("Tag_Search")
                        .commitAllowingStateLoss();
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

    public interface OnFragmentInteractionListener {
    }
}