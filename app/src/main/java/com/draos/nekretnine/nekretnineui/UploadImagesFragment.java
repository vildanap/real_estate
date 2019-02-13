package com.draos.nekretnine.nekretnineui;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.draos.nekretnine.nekretnineui.Services.AdvertService;
import com.draos.nekretnine.nekretnineui.Services.RealEstateServiceGenerator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_LONG;

public class UploadImagesFragment extends Fragment {

    private UploadImagesFragment.OnFragmentInteractionListener listener;
    SessionManager session;
    Button btnBack;
    Button btnSelect;
    String imageEncoded;
    List<String> imagesEncodedList;
    Button btnCreate;
    ArrayList<Uri> mArrayUri;
    List<String> images;
    Long id;
    ProgressBar pb;

    private static final int PICK_IMAGE_MULTIPLE = 1;

    private static final int WRITE_PERMISSION = 0x01;

    private GridView gvGallery;
    private GalleryAdapter galleryAdapter;

    public static UploadImagesFragment newInstance() {
        return new UploadImagesFragment();
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

        View view = inflater.inflate(R.layout.fragment_upload_photos, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Create Advert");
        gvGallery = (GridView)view.findViewById(R.id.gv);
        btnCreate = view.findViewById(R.id.buttonCreateAd);
        btnBack = view.findViewById(R.id.buttonBack);
        pb=view.findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        final String title = getArguments().getString("title");
        final String description = getArguments().getString("description");
        final String pType = getArguments().getString("propertyType");
        final String aType = getArguments().getString("advertType");
        final Double price = getArguments().getDouble("price");
        final Double area = getArguments().getDouble("area");
        final String address = getArguments().getString("address");
        final Long rooms = getArguments().getLong("numberOfRooms");
        final String location = getArguments().getString("locationId");
        final Long user = getArguments().getLong("userId");
        final String type = getArguments().getString("type");
        id = getArguments().getLong("id");
      if(type!=null) {
            btnCreate.setText("UPDATE");
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Update Advert");
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });

       //SELEECT IMAGES FROM GALLERY
        btnSelect = view.findViewById(R.id.buttonSelectImages);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openGallery();
            }
        });

        //Create
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {   pb.setVisibility(View.VISIBLE);
                // create list of file parts (photo, video, ...)
                List<MultipartBody.Part> parts = new ArrayList<>();
                // create upload service client
                AdvertService service = RealEstateServiceGenerator.createService(AdvertService.class);
                if (mArrayUri != null) {
                    // create part for file (photo, video, ...)
                    for (int i = 0; i < images.size(); i++) {
                        parts.add(prepareFilePart (images.get(i)));
                    }
                }
                // create a map of data to pass along
                // finally, execute the request
                try {
                    JSONObject ad = new JSONObject();
                    ad.put("title", title);
                    ad.put("description", description);
                    ad.put("advertType",aType);
                    ad.put("propertyType",pType);
                    ad.put("price",price);
                    ad.put("area",area);
                    ad.put("location",location);
                    ad.put("userId",user);
                    ad.put("address",address);
                    ad.put("viewsCount",0);
                    ad.put("numberOfRooms",rooms);

                    if(type==null){
                    final Call<ResponseBody> call = service.createAdvert(parts,RequestBody.create(MediaType.parse("multipart/form-data"), ad.toString()));
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code() == 201) {
                                pb.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(),
                                        "Advert successfully created.",
                                        Toast.LENGTH_LONG).show();

                                MyAdvertsFragment newfragment = new MyAdvertsFragment();
                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), newfragment);
                                fragmentTransaction.commit();

                            } else {
                                pb.setVisibility(View.INVISIBLE);
                                System.out.println(response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            pb.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(),
                                    "An error has occured. Please try again.",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else {
                        final Call<ResponseBody> call = service.updateAdvert(id,parts,RequestBody.create(MediaType.parse("multipart/form-data"), ad.toString()));
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.code() == 200) {
                                    pb.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getContext(),
                                            "Advert successfully updated.",
                                            Toast.LENGTH_LONG).show();

                                    MyAdvertsFragment newfragment = new MyAdvertsFragment();
                                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), newfragment);
                                    fragmentTransaction.commit();

                                } else {
                                    pb.setVisibility(View.INVISIBLE);
                                    System.out.println(response.errorBody());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                pb.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(),
                                        "An error has occured. Please try again.",
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                    }


                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UploadImagesFragment.OnFragmentInteractionListener) {
            listener = (UploadImagesFragment.OnFragmentInteractionListener) context;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == WRITE_PERMISSION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("w", "Write Permission Failed");
                Toast.makeText(getContext(), "You must allow permission write external storage to your mobile device.", Toast.LENGTH_SHORT).show();
                //finish();
            }
        }
    }
    private void requestWritePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, WRITE_PERMISSION);
            }
        }
    }
    private void openGallery(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestWritePermission();
        }
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/jpeg");
        photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        //photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(photoPickerIntent,PICK_IMAGE_MULTIPLE );

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
               /* if(data.getData()!=null){

                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    images = new ArrayList<>();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    images.add(cursor.getString(columnIndex));
                    cursor.close();

                    mArrayUri = new ArrayList<Uri>();
                    mArrayUri.add(mImageUri);
                    galleryAdapter = new GalleryAdapter(getContext(),mArrayUri);
                    gvGallery.setAdapter(galleryAdapter);
                    gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                            .getLayoutParams();
                    mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                }*/
                 if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        mArrayUri = new ArrayList<Uri>();
                        images = new ArrayList<>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();


                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            images.add(cursor.getString(columnIndex));

                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                            galleryAdapter = new GalleryAdapter(getContext(),mArrayUri);
                            gvGallery.setAdapter(galleryAdapter);
                            gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                                    .getLayoutParams();
                            mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }

            } else {
                Toast.makeText(getContext(), "You haven't picked Image",LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", LENGTH_LONG).show();
            Log.d("e", "onActivityResult: "+e.getMessage());
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private boolean DownloadImage(ResponseBody body) {
        try {
            Log.d("DownloadImage", "Reading and writing file");
            InputStream in = null;
            FileOutputStream out = null;

            try {
                in = body.byteStream();
                out = new FileOutputStream(getActivity().getExternalFilesDir(null) + File.separator + "a.jpg");
                int c;

                while ((c = in.read()) != -1) {
                    out.write(c);
                }
            }
            catch (IOException e) {
                Log.d("DownloadImage",e.toString());
                return false;
            }
            finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }

            int width, height;
            ImageView image = (ImageView) getView().findViewById(R.id.imageView);
            Bitmap bMap = BitmapFactory.decodeFile(getActivity().getExternalFilesDir(null) + File.separator + "a.jpg");
            width = bMap.getWidth();
            height = bMap.getHeight();
            Bitmap bMap2 = Bitmap.createScaledBitmap(bMap, width, height, false);
            image.setImageBitmap(bMap2);

            return true;

        } catch (IOException e) {
            Log.d("DownloadImage",e.toString());
            return false;
        }
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }
    @NonNull
    private MultipartBody.Part prepareFilePart(String fileUri) {

        File file = new File(fileUri);
        // create RequestBody instance from file
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/jpeg"), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData("files", file.getName(), reqFile);
    }


}
