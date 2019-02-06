package com.draos.nekretnine.nekretnineui;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.draos.nekretnine.nekretnineui.Model.Advertise;
import com.draos.nekretnine.nekretnineui.Model.City;
import com.draos.nekretnine.nekretnineui.Model.Location;
import com.draos.nekretnine.nekretnineui.Model.User;
import com.draos.nekretnine.nekretnineui.Services.AdvertService;
import com.draos.nekretnine.nekretnineui.Services.RealEstateServiceGenerator;
import com.draos.nekretnine.nekretnineui.Services.UserService;
import com.travijuu.numberpicker.library.NumberPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;

public class AdvertCreateFragment extends Fragment {
    private AdvertCreateFragment.OnFragmentInteractionListener listener;
    SessionManager session;
    Spinner spinnerCities;
    Spinner spinnerSettlement;
    Spinner spinnerAdvertType;
    Spinner spinnerPropertyType;
    int iCurrentSelection;
    Button btnCreate;
    EditText title;
    EditText description;
    NumberPicker rooms;
    EditText price;
    EditText area;
    EditText address;
    Button btnReset;


    public static AdvertCreateFragment newInstance() {
        return new AdvertCreateFragment();
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

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Create Advert");

        View view = inflater.inflate(R.layout.fragment_create_advertise, container, false);

        //fields
        spinnerCities =  view.findViewById(R.id.spinnerCities);
        spinnerSettlement = view.findViewById(R.id.spinnerSettlement);
        spinnerAdvertType = view.findViewById(R.id.spinneradvertType);
        spinnerPropertyType = view.findViewById(R.id.spinnerPropertyType);
        btnCreate = view.findViewById(R.id.buttonCreateAdvert);
        title = view.findViewById(R.id.editTextadvertTitle);
        description = view.findViewById(R.id.editTextadvertDescription);
        price = view.findViewById(R.id.editTextadPrice);
        area = view.findViewById(R.id.editTextadArea);
        rooms = view.findViewById(R.id.number_picker2);
        address = view.findViewById(R.id.editTextadAddress);

        //TODO Retrofit populate spinneres
        String[] cities = new String[]{"Sarajevo", "Tuzla", "Mostar"};
        String[] adType = new String[]{"Sale","Rent"};
        String[] propertyType = new String[]{"Apartment","House"};

        ArrayAdapter<String> adaptercity = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, cities);
        spinnerCities.setAdapter(adaptercity);

        ArrayAdapter<String> adapteradType = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, adType);
        spinnerAdvertType.setAdapter(adapteradType);

        ArrayAdapter<String> adapterProperty = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, propertyType);
        spinnerPropertyType.setAdapter(adapterProperty);

        iCurrentSelection = 0;

        spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (iCurrentSelection != i){
                    // Your code here
                    String[] SettlementSa= null;
                    if(i==0){
                   SettlementSa= new String[]{"Pofalici", "Ciglane", "Grbavica"};
                    }
                    else if(i==1){
                    SettlementSa = new String[]{"Mosnik", "Slatina", "Miladije"};
                    }
                    ArrayAdapter<String> adapterProperty = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,SettlementSa );
                    spinnerSettlement.setAdapter(adapterProperty);

                }
                iCurrentSelection = i;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        btnReset = view.findViewById(R.id.buttonResetAdvert);
        //TODO Upload slika
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
        //create advert
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
              /*  User u= new User("Zerina","1234","Zerina","Dragnic","zdragnic@gmail.com","+38762554678");
                City c = new City("Sarajevo");
                c.setId(1);
                u.setId(1);
                Location l = new Location("Pofalici",c);
                l.setId(1);

                Advertise a = new Advertise(title.getText().toString(),description.getText().toString(),Double.valueOf(price.getText().toString()),Double.valueOf(area.getText().toString()),spinnerAdvertType.getSelectedItem().toString(),spinnerPropertyType.getSelectedItem().toString(),0,rooms.getValue(),address.getText().toString(),u,l);*/
                //slike prazan fajl
                RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("image/jpeg"), "");
                MultipartBody.Part body = MultipartBody.Part.createFormData("files", "", attachmentEmpty);

                Fragment fragment =  new UploadImagesFragment();

                //Put the values
                Bundle args = new Bundle();
                args.putString("title", title.getText().toString());
                args.putString("description", description.getText().toString());
                args.putString("advertType",spinnerAdvertType.getSelectedItem().toString());
                args.putString("propertyType",spinnerPropertyType.getSelectedItem().toString());
                args.putDouble("price",Double.valueOf(price.getText().toString()));
                args.putDouble("area",Double.valueOf(area.getText().toString()));
                args.putLong("locationId",1);
                args.putLong("userId",1);
                args.putString("address",address.getText().toString());
                args.putLong("viewsCount",0);
                args.putLong("numberOfRooms",rooms.getValue());

               fragment.setArguments(args);


                //Inflate the fragment
               AppCompatActivity activity = (AppCompatActivity) getView().getContext();
               activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment,"Advert").addToBackStack("Advert").commitAllowingStateLoss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AdvertCreateFragment.OnFragmentInteractionListener) {
            listener = (AdvertCreateFragment.OnFragmentInteractionListener) context;
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
