package com.draos.nekretnine.nekretnineui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import com.appyvet.materialrangebar.RangeBar;


public class SearchFragment extends Fragment {
    private OnFragmentInteractionListener listener;


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


            Spinner cities = (Spinner) view.findViewById(R.id.spinnercities);

            String[] items = new String[]{"Sarajevo", "Tuzla", "Mostar"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
            cities.setAdapter(adapter);

        Spinner settlements = (Spinner) view.findViewById(R.id.spinnercities2);
        String[] items2 = new String[]{"Pofalici", "Grbavica", "Ciglane"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, items2);
        settlements.setAdapter(adapter2);
        RangeBar rangeBar =  view.findViewById(R.id.rangebar1);
        //Zato sto po defaultu dozvoljava samo 4 cifre, ovako citav broj prikaze
        rangeBar.setPinTextFormatter(new RangeBar.PinTextFormatter() {
            @Override
            public String getText(String value) {
                return value;
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