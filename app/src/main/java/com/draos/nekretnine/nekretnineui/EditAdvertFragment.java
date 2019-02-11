package com.draos.nekretnine.nekretnineui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.draos.nekretnine.nekretnineui.Model.Advertise;
import com.draos.nekretnine.nekretnineui.Model.User;
import com.draos.nekretnine.nekretnineui.Services.AdvertService;
import com.draos.nekretnine.nekretnineui.Services.RealEstateServiceGenerator;
import com.draos.nekretnine.nekretnineui.Services.UserService;
import com.travijuu.numberpicker.library.NumberPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAdvertFragment extends Fragment {
    private EditAdvertFragment.OnFragmentInteractionListener listener;
    SessionManager session;
    Spinner spinnerCities;
    Spinner spinnerSettlement;
    Spinner spinnerAdvertType;
    Spinner spinnerPropertyType;
    int iCurrentSelection, settlementId;
    Button btnCreate;
    EditText title;
    EditText description;
    NumberPicker rooms;
    EditText price;
    EditText area;
    EditText address;
    Button btnReset;

    public static EditAdvertFragment newInstance() {
        return new EditAdvertFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Session class instance
        session = new SessionManager(this.getContext());
    }
    // validation fields
    boolean validation =true;

    // advert id
    Long id;
    TextInputLayout title_layout,description_layout,price_layout,area_layout,address_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Advert");

        View view = inflater.inflate(R.layout.fragment_edit_advertise, container, false);
        Bundle extras = this.getArguments();
        if(extras!=null)
        id = extras.getLong("id");

        //validation fields
        title_layout = view.findViewById(R.id.title_layout);
        description_layout=view.findViewById(R.id.description_layout);
        price_layout=view.findViewById(R.id.price_layout);
        area_layout=view.findViewById(R.id.area_layout);
        address_layout=view.findViewById(R.id.address_layout);
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
        String [] sarajevoSettlement = new String[]{"Pofalici","Ciglane","Grbavica"};

        ArrayAdapter<String> adaptercity = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, cities);
        spinnerCities.setAdapter(adaptercity);

        ArrayAdapter<String> adapteradType = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, adType);
        spinnerAdvertType.setAdapter(adapteradType);

        ArrayAdapter<String> adapterProperty = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, propertyType);
        spinnerPropertyType.setAdapter(adapterProperty);

        ArrayAdapter<String> adapterSettlement = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,sarajevoSettlement );
        spinnerSettlement.setAdapter(adapterSettlement);

        iCurrentSelection = 0;

        AdvertService service = RealEstateServiceGenerator.createService(AdvertService.class);
        final Call<Advertise> call = service.getAdvertise(id);
        call.enqueue(new Callback<Advertise>() {
            @Override
            public void onResponse(Call<Advertise> call, Response<Advertise> response) {
                if(response.isSuccessful()) {
                    System.out.println(response.body().toString());
                  title.setText((response.body().getTitle()));
                  description.setText(response.body().getDescription());
                  price.setText(String.valueOf(Math.round(response.body().getPrice())));
                  area.setText(String.valueOf(response.body().getArea()));
                  rooms.setValue(Integer.valueOf(String.valueOf(response.body().getNumberOfRooms())));
                  address.setText(response.body().getAddress());
                  String settlement=response.body().getLocation().getSettlement();
              // String city = response.body().getLocation().getCity().getName();
               //   if(city.equals("Mostar"))
                //      spinnerCities.setSelection(2);

                  if(settlement.equals("Brankovac")){
                  spinnerCities.setSelection(2);
                  spinnerSettlement.setSelection(0);
                 }
                  if(settlement.equals("Bijeli Brijeg")){
                      spinnerCities.setSelection(2);
                      spinnerSettlement.setSelection(1);
                     }
                  if(settlement.equals("Zalik")){
                      spinnerCities.setSelection(2);
                      spinnerSettlement.setSelection(2);
                     }
                  if(settlement.equals("Grbavica")) {
                      spinnerCities.setSelection(0);
                      spinnerSettlement.setSelection(2);
                  }
                  if(settlement.equals("Pofalici"))
                  {
                      spinnerCities.setSelection(0);
                      spinnerSettlement.setSelection(0);
                  }
                    if(settlement.equals("Ciglane"))
                    {
                        spinnerCities.setSelection(0);
                        spinnerSettlement.setSelection(1);
                    }
                    if(settlement.equals("Mosnik")){
                        spinnerCities.setSelection(1);
                        spinnerSettlement.setSelection(0);
                    }
                    if(settlement.equals("Slatina")){
                        spinnerCities.setSelection(1);
                        spinnerSettlement.setSelection(1);
                    }
                    if(settlement.equals("Miladije")){
                        spinnerCities.setSelection(1);
                        spinnerSettlement.setSelection(2);
                    }

                //TODO: spinnerAdvert and spinnerCity
                } else {
                    System.out.println(response.message());
                    Toast.makeText(getContext(),
                            "Error",
                            Toast.LENGTH_LONG).show();
                 //   pb.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onFailure(Call<Advertise> call, Throwable t) {
                Toast.makeText(getContext(),
                        "An error has ocurred.Try again.",
                        Toast.LENGTH_LONG).show();
              //  pb.setVisibility(View.INVISIBLE);
            }
        });


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
                    else if(i==2)
                    {
                        SettlementSa = new String[]{"Brankovac", "Bijeli Brijeg", "Zalik"};

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
                spinnerCities.setSelection(0);
                title.setText("");
                description.setText("");
                price.setText("");
                area.setText("");
                spinnerSettlement.setSelection(0);
                spinnerAdvertType.setSelection(0);
                spinnerPropertyType.setSelection(0);
                address.setText("");
                rooms.setValue(1);
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
                //                //slike prazan fajl
                boolean validationResult = validate();
                if(validation==true && validationResult==true){
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
                    args.putString("locationId",spinnerSettlement.getSelectedItem().toString());
                    args.putLong("userId",Long.valueOf(session.getUserDetails().get("email")));
                    args.putString("address",address.getText().toString());
                    args.putLong("viewsCount",0);
                    args.putLong("numberOfRooms",rooms.getValue());
                    args.putString("type","edit");
                    args.putLong("id",id);
                    //args.putString("city",spinnerSettlement);
                    fragment.setArguments(args);


                    //Inflate the fragment
                    AppCompatActivity activity = (AppCompatActivity) getView().getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment,"Advert").addToBackStack("Advert").commitAllowingStateLoss();
                }
                else {

                }
            }
        });
        // VALIDATION
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateTitle(s);
            }
        });
        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateTitle(((EditText) v).getText());
                }
            }
        });
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateDescription(s);
            }
        });
        description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateDescription(((EditText) v).getText());
                }
            }
        });
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validatePrice(s);
            }
        });
        price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validatePrice(((EditText) v).getText());
                }
            }
        });
        area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateArea(s);
            }
        });
        area.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateArea(((EditText) v).getText());
                }
            }
        });
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateAddress(s);
            }
        });
        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateAddress(((EditText) v).getText());
                }
            }
        });
        return view;
    }
    private void validateTitle(Editable s) {
        if (!TextUtils.isEmpty(s) && title.getText().toString().length()>=1 && title.getText().toString().length()<201) {
            title_layout.setError(null);
            validation=true;
        }
        else{
            title_layout.setError("Title should contain min. 1 and max. 200 characters");
            validation=false;
        }
    }
    private void validateDescription(Editable s) {
        if (!TextUtils.isEmpty(s) && description.getText().toString().length()>=1 && description.getText().toString().length()<201) {
            description_layout.setError(null);
            validation=true;
        }
        else{
            description_layout.setError("Description should contain min. 1 and max. 200 characters");
            validation=false;
        }
    }
    private void validatePrice(Editable s) {
        if (!TextUtils.isEmpty(s) && price.getText().toString().length()>=1 && TextUtils.isDigitsOnly(price.getText().toString())) {
            price_layout.setError(null);
            validation=true;
        }
        else{
            price_layout.setError("Price should not be empty and should contain only digits");
            validation=false;
        }
    }
    private void validateArea(Editable s) {
        if (!TextUtils.isEmpty(s) && area.getText().toString().length()>=1 && area.getText().toString().matches("^\\d*\\.\\d+$")) {
            area_layout.setError(null);
            validation=true;
        }
        else{
            area_layout.setError("Area should not be empty, e.g. 80.0");
            validation=false;
        }
    }
    private void validateAddress(Editable s) {
        if (!TextUtils.isEmpty(s) && address.getText().toString().length()>=4 && address.getText().toString().length()<201) {
            address_layout.setError(null);
            validation=true;
        }
        else{
            address_layout.setError("Address should contain min. 4 and max. 200 characters");
            validation=false;
        }
    }
    private boolean validate() {
        boolean validation = true;
        if (title.getText().toString().equals("")) {
            title_layout.setError("Title should not be empty");
            validation = false;
        }
        if (description.getText().toString().equals("")) {
            description_layout.setError("Description should not be empty");
            validation = false;
        }
        if (price.getText().toString().length()==0) {
            price_layout.setError("Price should not be empty");
            validation = false;
        }
        if (area.getText().toString().length()==0) {
            area_layout.setError("Area should not be empty");
            validation = false;
        }
        if (address.getText().toString().equals("")) {
            address_layout.setError("Address should not be empty");
            validation = false;
        }
        return validation;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EditAdvertFragment.OnFragmentInteractionListener) {
            listener = (EditAdvertFragment.OnFragmentInteractionListener) context;
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
