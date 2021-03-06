package com.draos.nekretnine.nekretnineui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.draos.nekretnine.nekretnineui.Model.User;
import com.draos.nekretnine.nekretnineui.Services.RealEstateServiceGenerator;
import com.draos.nekretnine.nekretnineui.Services.UserService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {

    private SignUpFragment.OnFragmentInteractionListener listener;
    SessionManager session;


    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Session class instance
        session = new SessionManager(this.getContext());
    }
    EditText ed_email,ed_password,ed_name,ed_surname,ed_username,ed_phone,ed_passwordRetype;
    TextInputLayout email_layout,password_layout,name_layout,surname_layout,username_layout,phone_layout,passwordRetype_layout;
    boolean validation =true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Sign Up");

        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        final Button signupbutton = view.findViewById(R.id.button4);
        final ProgressBar pb =  view.findViewById(R.id.progressBar);
        ed_name = view.findViewById(R.id.ed_name);
        ed_surname = view.findViewById(R.id.ed_surname);
        ed_username = view.findViewById(R.id.ed_username);
        ed_phone = view.findViewById(R.id.ed_phone);
        ed_email = view.findViewById(R.id.ed_email);
        ed_password = view.findViewById(R.id.ed_password);
        ed_passwordRetype = view.findViewById(R.id.ed_passwordRetype);
        email_layout = view.findViewById(R.id.email_layout);
        password_layout = view.findViewById(R.id.password_layout);
        passwordRetype_layout = view.findViewById(R.id.passwordRetype_layout);
        name_layout = view.findViewById(R.id.name_layout);
        surname_layout = view.findViewById(R.id.surname_layout);
        username_layout = view.findViewById(R.id.username_layout);
        phone_layout = view.findViewById(R.id.phone_layout);
        pb.setVisibility(View.INVISIBLE);

        signupbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

            boolean validationResult = validate();
            if(validation==true && validationResult==true){


                String firstName = ed_name.getText().toString().trim();
                String lastName = ed_surname.getText().toString().trim();
                String username = ed_username.getText().toString().trim();
                String password = ed_password.getText().toString().trim();
                String email = ed_email.getText().toString().trim();
                String telephone = ed_phone.getText().toString().trim();

                final User user = new User(username,password,firstName,lastName,email,telephone);
                UserService service1 = RealEstateServiceGenerator.createService(UserService.class);
                final Call<User> call1 = service1.getUserByUsername(username);
                call1.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(!response.isSuccessful()) {
                            pb.setVisibility(View.VISIBLE);

                            UserService service = RealEstateServiceGenerator.createService(UserService.class);
                            final Call<ResponseBody> callCreate = service.createUser(user);
                            callCreate.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if(response.code()==201) {
                                        Toast.makeText(getContext(),
                                                "Account successfully created. You can now login.",
                                                Toast.LENGTH_LONG).show();
                                        AccountFragment newfragment = new AccountFragment();
                                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        //fragmentTransaction.addToBackStack("Sign up");
                                        fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), newfragment);
                                        fragmentTransaction.commit();
                                    } else {
                                        System.out.println(response.errorBody());
                                    }
                                }
                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(getContext(),
                                            t.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                        } else {
                            System.out.println(response.message());
                            Toast.makeText(getContext(),
                                    "User with username '"+ed_username.getText().toString()+"' exists. Try with other username.",
                                    Toast.LENGTH_LONG).show();
                            ed_username.requestFocus();
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



            }}
        });

     /*   TextView tv = (TextView) view.findViewById(R.id.tvFavourites);

        if (!session.isLoggedIn()) {
            tv.setText("Please log in to see Favourites.");
        }
        else{
            tv.setText("Favourites");
        }
*/ ed_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateEditText(s);
            }
        });
        ed_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEditText(((EditText) v).getText());
                }
            }
        });
        ed_passwordRetype.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateRetypePassword(s);
            }
        });
        ed_passwordRetype.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateRetypePassword(((EditText) v).getText());
                }
            }
        });
        ed_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateName(s);
            }
        });
        ed_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateName(((EditText) v).getText());
                }
            }
        });
        ed_surname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateSurname(s);
            }
        });
        ed_surname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateSurname(((EditText) v).getText());
                }
            }
        });
        ed_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateUsername(s);
            }
        });
        ed_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateUsername(((EditText) v).getText());
                }
            }
        });
        ed_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateEmail(s);
            }
        });
        ed_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEmail(((EditText) v).getText());
                }
            }
        });
        ed_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validatePhone(s);
            }
        });
        ed_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validatePhone(((EditText) v).getText());
                }
            }
        });
        return view;
    }
    private void validateEditText(Editable s) {
        if (!TextUtils.isEmpty(s) && ed_password.getText().toString().length()>=5) {
            password_layout.setError(null);
            validation=true;
        }
        else{
            password_layout.setError("Password should contain min.5 characters");
            validation=false;
        }
    }
    private void validateRetypePassword(Editable s) {
        if (!TextUtils.isEmpty(s) && ed_password.getText().toString().equals(ed_passwordRetype.getText().toString())) {
            passwordRetype_layout.setError(null);
            validation=true;
        }
        else{
            passwordRetype_layout.setError("Must match the previous entry");
            validation=false;
        }
    }
    private void validateName(Editable s) {
        if (!TextUtils.isEmpty(s) && ed_name.getText().toString().length()>=2) {
            name_layout.setError(null);
            validation=true;
        }
        else{
            name_layout.setError("Name should contain min.2 characters");
            validation=false;
        }
    }
    private void validateSurname(Editable s) {
        if (!TextUtils.isEmpty(s) && ed_surname.getText().toString().length()>=2) {
            surname_layout.setError(null);
            validation=true;
        }
        else{
            surname_layout.setError("Surname should contain min.2 characters");
            validation=false;
        }
    }
    private void validateUsername(Editable s) {
        if (!TextUtils.isEmpty(s) && ed_username.getText().toString().length()>=5) {
            username_layout.setError(null);
            validation=true;
        }
        else{
            username_layout.setError("Username should contain min.5 characters");
            validation=false;
        }
    }
    private void validateEmail(Editable s) {
        if (!TextUtils.isEmpty(s) && ed_email.getText().toString().matches("[A-Za-z0-9._-]+@[a-z0-9.-]+\\.[a-z]{2,4}")) {
            email_layout.setError(null);
            validation=true;
        }
        else{
            email_layout.setError("Email format not valid");
            validation=false;
        }
    }
    private void validatePhone(Editable s) {
        if (!TextUtils.isEmpty(s) && ed_phone.getText().toString().matches("\\+[0-9]{11,12}")) {
            phone_layout.setError(null);
            validation=true;
        }
        else{
            phone_layout.setError("Phone format not valid");
            validation=false;
        }
    }
    private boolean validate() {
        boolean validation =true;

        if(ed_name.getText().toString().equals("")){
            name_layout.setError("Name should not be empty");
            validation=false;
        }

        if(ed_surname.getText().toString().equals("")){
            surname_layout.setError("Surname should not be empty");
            validation=false;
        }

        if(ed_username.getText().toString().equals("")){
            username_layout.setError("Username should not be empty");
            validation=false;
        }

        if(ed_phone.getText().toString().equals("")){
            phone_layout.setError("Phone should not be empty");
            validation=false;
        }

        if(ed_email.getText().toString().equals("")){
            email_layout.setError("Email should not be empty");
            validation=false;
        }

        if(ed_passwordRetype.getText().toString().equals("")) {
            passwordRetype_layout.setError("Retype password should not be empty");
        validation=false;}
        if(ed_password.getText().toString().equals("")) {
            password_layout.setError("Password should not be empty");
            validation=false;}
        return  validation;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignUpFragment.OnFragmentInteractionListener) {
            listener = (SignUpFragment.OnFragmentInteractionListener) context;
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