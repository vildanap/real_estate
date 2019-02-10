package com.draos.nekretnine.nekretnineui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.draos.nekretnine.nekretnineui.Model.Advertise;
import com.draos.nekretnine.nekretnineui.Services.AdvertService;
import com.draos.nekretnine.nekretnineui.Services.RealEstateServiceGenerator;
import com.draos.nekretnine.nekretnineui.Services.UserService;
import okhttp3.MediaType;
import okhttp3.RequestBody;
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
    String title,price,description,area,rooms,advertType, propertyType, address, settlement, phone, email;
    Long id,userId;
    Boolean isFavourite;
    ImageButton favoritebtn,imageCell,imageEmail;
    TextView tvDescription, tvPrice,tvArea,tvRooms,tvViews,tvPropertyType,tvAdvertType,tvSettlement,tvAddress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_advertise_details, container, false);
        TextView adTitle = view.findViewById(R.id.textView_adTitle);

        final int imgStarFull = R.drawable.ic_star;
        final int imgStarBorder = R.drawable.ic_star_border;
        tvAddress = view.findViewById(R.id.textView36Address);
        tvAdvertType = view.findViewById(R.id.textView28adType);
        tvPropertyType = view.findViewById(R.id.textView30Property);
        tvArea = view.findViewById(R.id.textViewArea);
        tvDescription = view.findViewById(R.id.textViewDescription);
        tvPrice = view.findViewById(R.id.TextViewPrice);
        tvViews = view.findViewById(R.id.textView32Views);
        tvSettlement = view.findViewById(R.id.textView34Settlement);
        tvRooms = view.findViewById(R.id.textViewRooms);
        imageCell = view.findViewById(R.id.imageButton2Call);
        imageEmail = view.findViewById(R.id.imageButton3Email);
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            title = bundle.get("title").toString();
            price = bundle.get("price").toString();
            description = bundle.get("description").toString();
            area=bundle.getString("area");
            rooms=bundle.getString("rooms");
            advertType=bundle.getString("advertType");
            address = bundle.getString("address");
            settlement = bundle.getString("settlement");
            propertyType = bundle.getString("propertyType");
            id=bundle.getLong("id");
            userId=bundle.getLong("userId");
            phone = bundle.getString("phone");
            email = bundle.getString("email");

            adTitle.setText(title);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        }
        //postavljanje oglasa
        tvPrice.setText(price + " BAM");
        tvDescription.setText(description);
        tvArea.setText(area + " squares");
        tvAdvertType.setText(advertType);
        tvRooms.setText(rooms);
        tvPropertyType.setText(propertyType);
        tvSettlement.setText(settlement);
        tvAddress.setText(address);
        //views

            // phone dialer
        imageCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                // Send phone number to intent as data
                intent.setData(Uri.parse("tel:" + phone));
                // Start the dialer app activity with number
                startActivity(intent);
            }
        });

        // email open
        imageEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                getActivity().startActivity(intent);*/

                Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject=" + title + "&body=" + "" + "&to=" + email);
                mailIntent.setData(data);
                startActivity(Intent.createChooser(mailIntent, "Send mail to contact owner"));
            }
        });

        //dodavanje favorita
        if(session.isLoggedIn()){
            favoritebtn = view.findViewById(R.id.imageButtonFavorite);
            AdvertService service = RealEstateServiceGenerator.createService(AdvertService.class);
            final Call<ResponseBody> callCheckFavourite = service.checkIfFavourite(Long.parseLong(session.getUserDetails().get("email")),id);
            callCheckFavourite.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        favoritebtn.setImageResource(imgStarFull);
                        isFavourite = true;
                    } else {
                        favoritebtn.setImageResource(imgStarBorder);
                        isFavourite = false;
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(),
                            "An error has occured.",
                            Toast.LENGTH_LONG).show();
                }
            });

            favoritebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(!isFavourite){
                        favoritebtn.setImageResource(imgStarFull);
                        isFavourite = true;
                        AdvertService service = RealEstateServiceGenerator.createService(AdvertService.class);
                        final Call<ResponseBody> call = service.addFavorite(Long.parseLong(session.getUserDetails().get("email")),id);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.code() == 201) {
                                    Toast.makeText(getContext(),
                                            "Advert added to favourites.",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    System.out.println(response.errorBody());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getContext(),
                                        "An error has occured. Please try again.",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else{
                        isFavourite=false;
                        favoritebtn.setImageResource(imgStarBorder);
                        AdvertService service = RealEstateServiceGenerator.createService(AdvertService.class);
                        final Call<ResponseBody> call = service.removeFavourite(Long.parseLong(session.getUserDetails().get("email")),id);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.code() == 200) {
                                    Toast.makeText(getContext(),
                                            "Advert removed from favourites.",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    System.out.println(response.errorBody());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getContext(),
                                        "An error has occured. Please try again.",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                }
            });
            // edit and delete buttons only if user created ad
        }
        if(session.isLoggedIn() && Long.valueOf(userId).toString().equals(session.getUserDetails().get("email"))) {
            editAdvert=view.findViewById(R.id.editAdvert);
            deleteAdvert=view.findViewById(R.id.deleteAdvert);
            editAdvert.setVisibility(View.VISIBLE);
            deleteAdvert.setVisibility(View.VISIBLE);
            imageEmail.setVisibility(View.INVISIBLE);
            imageCell.setVisibility(View.INVISIBLE);
            Bundle b = new Bundle();
            b.putLong("id",id);

            AdvertService service = RealEstateServiceGenerator.createService(AdvertService.class);
            final Call<Advertise> callUpdateViewsCount = service.updateViewsCount(id);
            callUpdateViewsCount.enqueue(new Callback<Advertise>() {
                @Override
                public void onResponse(Call<Advertise> call, Response<Advertise> response) {
                    if(response.isSuccessful()) {

                    }
                }
                @Override
                public void onFailure(Call<Advertise> call, Throwable t) {
                    Toast.makeText(getContext(),
                            "An error has ocurred. Try again.",
                            Toast.LENGTH_LONG).show();
                }
            });

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
