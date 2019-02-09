package com.draos.nekretnine.nekretnineui;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.draos.nekretnine.nekretnineui.Model.User;
import com.draos.nekretnine.nekretnineui.Services.RealEstateServiceGenerator;
import com.draos.nekretnine.nekretnineui.Services.UserService;
import okhttp3.ResponseBody;
import org.w3c.dom.Text;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class AccountFragment extends Fragment {
    private SearchFragment.OnFragmentInteractionListener listener;
    SessionManager session;


    public static AccountFragment newInstance() {
        return new AccountFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Account");

        final View view = inflater.inflate(R.layout.fragment_account, container, false);
        // recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        final EditText password = (EditText) view.findViewById(R.id.editPassword);
        final EditText username = (EditText) view.findViewById(R.id.editText);
        final TextView signUp = (TextView) view.findViewById(R.id.textView);
        // show password when check button is selected
        CheckBox box = view.findViewById(R.id.checkBox);
        final ProgressBar pb =  view.findViewById(R.id.loadingProgress);
        Button loginbutton = view.findViewById(R.id.button);

        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                EditText password = (EditText)getView().findViewById(R.id.editPassword);
                if(checked){
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else{
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                pb.setVisibility(View.VISIBLE);

               final String usernametxt = username.getText().toString();
                final String passwordtxt = password.getText().toString();
                UserService service = RealEstateServiceGenerator.createService(UserService.class);
                final User user = new User();
                user.setUsername(usernametxt);
                user.setPassword(passwordtxt);
               final Call<User> call = service.login(user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()) {
                            session.createLoginSession(usernametxt, String.valueOf(response.body().getId()),response.body().getFirstName(),response.body().getLastName());
                            pb.setVisibility(View.INVISIBLE);
                            Bundle b = new Bundle();
                            //TODO: add arguments ->user favourites, user adverts (number)
                            Toast.makeText(getContext(),
                                    "Successfully logged in",
                                    Toast.LENGTH_LONG).show();

                            LoggedUserFragment newfragment = new LoggedUserFragment();
                          //  newfragment.setArguments(data);
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(((ViewGroup) (getView().getParent())).getId(), newfragment);
                            fragmentTransaction.commit();
                        } else {
                            System.out.println(response.message());
                            Toast.makeText(getContext(),
                                    "Username or password not correct",
                                    Toast.LENGTH_LONG).show();
                            pb.setVisibility(View.INVISIBLE);
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getContext(),
                                "An error has ocurred.Try again.",
                                Toast.LENGTH_LONG).show();
                        pb.setVisibility(View.INVISIBLE);
                    }
                });
             /* if (usernametxt.equals("zerina") && passwordtxt.equals("123")) {
                    session.createLoginSession(usernametxt, passwordtxt);
                    pb.setVisibility(View.INVISIBLE);
                    //logoutbutton.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(),
                            "Successfully logged in",
                            Toast.LENGTH_LONG).show();
                    // open LoggedUser fragment
                    LoggedUserFragment newfragment = new LoggedUserFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(((ViewGroup) (getView().getParent())).getId(), newfragment);
                    fragmentTransaction.commit();
                }*/
            }
        });
               signUp.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg0) {
                            // open SignUp fragment
                            SignUpFragment newfragment = new SignUpFragment();
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), newfragment);
                            fragmentTransaction.commit();
                        }
          /*      usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();

                // Send data to Parse.com for verification
                ParseUser.logInInBackground(usernametxt, passwordtxt,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                // If user exist and authenticated, send user to Welcome.class
                                if(user !=null){
                                    mProgress.dismiss();
                                    Intent intent = new Intent(
                                            LoginActivity.this,
                                            AddUserPage.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),
                                            "Successfully Logged in",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                }else{
                                    mProgress.dismiss();
                                    Toast.makeText(getApplicationContext(), "No such user", Toast.LENGTH_LONG).show();
                                    username.setText("");
                                    password.setText("");
                                }}
                        });*/
            });

        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchFragment.OnFragmentInteractionListener) {
            listener = (SearchFragment.OnFragmentInteractionListener) context;
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
