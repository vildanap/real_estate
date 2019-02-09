package com.draos.nekretnine.nekretnineui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.draos.nekretnine.nekretnineui.Services.AdvertService;
import com.draos.nekretnine.nekretnineui.Services.RealEstateServiceGenerator;
import com.draos.nekretnine.nekretnineui.Services.UserService;
import okhttp3.ResponseBody;
import org.w3c.dom.Text;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvertiseDetailsFragment extends Fragment {
    private AdvertiseDetailsFragment.OnFragmentInteractionListener listener;
    SessionManager session;

    public static AdvertiseDetailsFragment newInstance() {
        return new AdvertiseDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Session class instance
        session = new SessionManager(this.getContext());
    }

    Button editAdvert,deleteAdvert;
    ImageView adTypePhoto;
    String title,price,description,area,rooms,advertType;
    Long id,userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_advertise_details, container, false);
        TextView adTitle = view.findViewById(R.id.textView_adTitle);
        Bundle bundle = this.getArguments();
        if(bundle != null) {
           title = bundle.get("title").toString();
            price = bundle.get("price").toString();
            description = bundle.get("description").toString();
            area=bundle.getString("area");
            rooms=bundle.getString("rooms");
            advertType=bundle.getString("advertType");
            id=bundle.getLong("id");
           userId=bundle.getLong("userId");

            adTitle.setText(title);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);

        }

        if(session.isLoggedIn() && Long.valueOf(userId).toString().equals(session.getUserDetails().get("email"))) {
            editAdvert=view.findViewById(R.id.editAdvert);
            deleteAdvert=view.findViewById(R.id.deleteAdvert);
            editAdvert.setVisibility(View.VISIBLE);
            deleteAdvert.setVisibility(View.VISIBLE);
            Bundle b = new Bundle();
            b.putLong("id",id);

        deleteAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog myDialog = new Dialog(getContext());

                TextView txtclose,titleField,descriptionField,priceField,areaField,roomsField,txtWarning;
                Button btnDelete;
                myDialog.setContentView(R.layout.deletepopup);
                txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                txtclose.setText("X");
                btnDelete = (Button) myDialog.findViewById(R.id.btndelete);
                titleField = (TextView) myDialog.findViewById(R.id.title);
                descriptionField = (TextView) myDialog.findViewById(R.id.location);
                priceField=(TextView)myDialog.findViewById(R.id.pricenumber);
                areaField=(TextView)myDialog.findViewById(R.id.areanumber);
                roomsField=(TextView)myDialog.findViewById(R.id.roomsnumber);
                txtWarning=(TextView)myDialog.findViewById(R.id.textView17);
                adTypePhoto=(ImageView)myDialog.findViewById(R.id.advertType);

               if(advertType.toString().equals("Rent")){

                   adTypePhoto.setImageResource(R.drawable.rent);
                   LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(500,300);
                   layoutParams.gravity=Gravity.CENTER;
               adTypePhoto.setLayoutParams(layoutParams);}
                titleField.setText(title);
                descriptionField.setText(description);
                priceField.setText(price);
                areaField.setText(area);
                roomsField.setText(rooms);
                txtWarning.setText("If you click on the 'DELETE ADVERT' button, your advert will be permanently deleted.");
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AdvertService service = RealEstateServiceGenerator.createService(AdvertService.class);
                        final Call<ResponseBody> callCreate = service.deleteAdvert(id);
                        callCreate.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.code()==200) {
                                    myDialog.dismiss();
                                    Toast.makeText(getContext(),
                                            "Your advert has been permanently deleted",
                                            Toast.LENGTH_LONG).show();
                                    MyAdvertsFragment newfragment = new MyAdvertsFragment();
                                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), newfragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                } else {
                                    System.out.println(response.errorBody());
                                }
                            }
                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getContext(),
                                        "An error has ocurred. Try again.",
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });
            }

        });

        editAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putLong("id",id);
                EditAdvertFragment newfragment = new EditAdvertFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), newfragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                newfragment.setArguments(bundle);

            }
        });}
               return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AdvertiseDetailsFragment.OnFragmentInteractionListener) {
            listener = (AdvertiseDetailsFragment.OnFragmentInteractionListener) context;
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
