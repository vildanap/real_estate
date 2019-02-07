package com.draos.nekretnine.nekretnineui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.draos.nekretnine.nekretnineui.Services.RealEstateServiceGenerator;
import com.draos.nekretnine.nekretnineui.Services.UserService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoggedUserFragment extends Fragment {

    private LoggedUserFragment.OnFragmentInteractionListener listener;
    SessionManager session;

    public static LoggedUserFragment newInstance() {
        return new LoggedUserFragment();
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
        //final Bundle extras = getArguments();
       // final Long userId = extras.getLong("id");
       //final long d = extras.getLong("id");
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Long.valueOf(d).toString());

        View view = inflater.inflate(R.layout.fragment_loggeduser, container, false);
        final Button logoutbutton = view.findViewById(R.id.button2);
        final Button editProfile = view.findViewById(R.id.button11);
        final Button createADbutton = view.findViewById(R.id.button9);
        final Button deleteButton = view.findViewById(R.id.button6);


        logoutbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                session.logoutUser();
                Toast.makeText(getContext(),
                        "Sucessfully logged out.",
                        Toast.LENGTH_LONG).show();
                AccountFragment newfragment = new AccountFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), newfragment);
                fragmentTransaction.commit();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                EditProfileFragment newfragment = new EditProfileFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), newfragment);
                fragmentTransaction.commit();
            }
        });

        createADbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                AdvertCreateFragment newfragment = new AdvertCreateFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), newfragment);
                fragmentTransaction.commit();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog myDialog = new Dialog(getContext());

                TextView txtclose,usernameField,fullnameField;
                Button btnDelete;
                myDialog.setContentView(R.layout.custompopup);
                txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                txtclose.setText("X");
                btnDelete = (Button) myDialog.findViewById(R.id.btndelete);
                usernameField = (TextView) myDialog.findViewById(R.id.username);
                fullnameField = (TextView) myDialog.findViewById(R.id.fullname);

                String usernameText;
                String fullnameText;

                usernameField.setText(session.getUserDetails().get("name"));
                final Long userId = Long.valueOf(session.getUserDetails().get("email"));
                fullnameField.setText(session.getUserDetails().get("firstname")+" " +session.getUserDetails().get("surname"));
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
                        session.logoutUser();
                        UserService service = RealEstateServiceGenerator.createService(UserService.class);
                        final Call<ResponseBody> callCreate = service.deleteUser(userId);
                        callCreate.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.code()==200) {
                                    myDialog.dismiss();
                                    Toast.makeText(getContext(),
                                            "Your account and adverts have been permanently deleted.",
                                            Toast.LENGTH_LONG).show();
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
      return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoggedUserFragment.OnFragmentInteractionListener) {
            listener = (LoggedUserFragment.OnFragmentInteractionListener) context;
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