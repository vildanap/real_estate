package com.draos.nekretnine.nekretnineui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

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

        final View view = inflater.inflate(R.layout.fragment_account, container, false);
        // recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        final EditText password = (EditText) view.findViewById(R.id.editPassword);
        final EditText username = (EditText) view.findViewById(R.id.editText);

        // show password when check button is selected
        CheckBox box = view.findViewById(R.id.checkBox);

        final ProgressBar pb =  view.findViewById(R.id.loadingProgress);
       Button loginbutton = view.findViewById(R.id.button);
       final Button logoutbutton = view.findViewById(R.id.logout);
       logoutbutton.setVisibility(View.INVISIBLE);

       if(session.isLoggedIn()){
            logoutbutton.setVisibility(View.VISIBLE);
        }

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

        logoutbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                session.logoutUser();

            }
        });

                loginbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                pb.setVisibility(View.VISIBLE);

                String usernametxt = username.getText().toString();
                String passwordtxt = password.getText().toString();

                if(usernametxt.equals("zerina") && passwordtxt.equals("123")) {
                    session.createLoginSession(usernametxt, passwordtxt);
                    pb.setVisibility(View.INVISIBLE);
                    logoutbutton.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(),
                            "Successfully Logged in",
                            Toast.LENGTH_SHORT).show();
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
            }
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
