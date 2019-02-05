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
import android.widget.*;
import com.draos.nekretnine.nekretnineui.Model.User;
import com.draos.nekretnine.nekretnineui.Services.RealEstateServiceGenerator;
import com.draos.nekretnine.nekretnineui.Services.UserService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {

    private EditProfileFragment.OnFragmentInteractionListener listener;
    SessionManager session;

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Session class instance
        session = new SessionManager(this.getContext());
    }
    EditText ed_email,ed_password,ed_name,ed_surname,ed_username,ed_phone,ed_passwordRetype,ed_passwordnew,oldpassword,oldusername;
    TextInputLayout email_layout,password_layout,name_layout,surname_layout,username_layout,phone_layout,passwordRetype_layout,passwordnew_layout2;
    final String oldpass=null;
    final String olduser = null;
    boolean validation =true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Profile");

        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        final Button buttonsave = view.findViewById(R.id.buttonsave);
        oldpassword = view.findViewById(R.id.oldpassword);
        oldusername = view.findViewById(R.id.oldusername);
        ed_name =  view.findViewById(R.id.ed_name2);
         ed_surname =  view.findViewById(R.id.ed_surname2);
         ed_username =  view.findViewById(R.id.ed_username2);
       ed_password =  view.findViewById(R.id.ed_password2);
       ed_passwordnew =view.findViewById(R.id.ed_passwordnew);
       ed_passwordRetype =  view.findViewById(R.id.ed_passwordRetype2);
       ed_email =  view.findViewById(R.id.ed_email2);
       ed_phone =  view.findViewById(R.id.ed_phone2);
        email_layout = view.findViewById(R.id.email_layout2);
        password_layout = view.findViewById(R.id.password_layout2);
        passwordRetype_layout = view.findViewById(R.id.passwordRetype_layout2);
        name_layout = view.findViewById(R.id.name_layout2);
        surname_layout = view.findViewById(R.id.surname_layout2);
        username_layout = view.findViewById(R.id.username_layout2);
        phone_layout = view.findViewById(R.id.phone_layout2);
        final ProgressBar pb =  view.findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        final String username = session.getUserDetails().get("name");
        UserService service = RealEstateServiceGenerator.createService(UserService.class);
        final Call<User> call = service.getUserByUsername(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                   ed_name.setText(response.body().getFirstName());
                   ed_surname.setText(response.body().getLastName());
                   ed_username.setText(response.body().getUsername());
                   oldpassword.setText(response.body().getPassword());
                   oldusername.setText(response.body().getUsername());
                   ed_email.setText(response.body().getEmail());
                   ed_phone.setText(response.body().getTelephone());
                } else {
                    System.out.println(response.message());
                    Toast.makeText(getContext(),
                            "Error",
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

        ed_password.addTextChangedListener(new TextWatcher() {
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

        buttonsave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                final boolean validationResult = validate();
                final String pass=ed_password.getText().toString();
                final String usernameCreated = oldusername.getText().toString();
                final User userEdited = new User();
                userEdited.setUsername(ed_username.getText().toString());
                UserService service1 = RealEstateServiceGenerator.createService(UserService.class);

                if(!(userEdited.getUsername().equals(oldusername.getText().toString()))){
                final Call<User> call1 = service1.getUserByUsername(userEdited.getUsername());
                call1.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(!response.isSuccessful()) {
                            pb.setVisibility(View.VISIBLE);
                if(validation==true && validationResult==true && oldpassword.getText().toString().equals(pass)){
                    pb.setVisibility(View.VISIBLE);
                        editCall();
          /*          final User user = new User();
                    user.setFirstName(ed_name.getText().toString());
                    user.setLastName(ed_surname.getText().toString());
                    user.setUsername(ed_username.getText().toString());
                    if(ed_passwordnew.getText().toString().isEmpty())
                    user.setPassword(ed_password.getText().toString());
                    else user.setPassword(ed_passwordnew.getText().toString());
                    user.setEmail(ed_email.getText().toString());
                    user.setTelephone(ed_phone.getText().toString());
                    UserService service = RealEstateServiceGenerator.createService(UserService.class);
                    final Call<ResponseBody> callUpdate = service.updateUser(username,user);
                    callUpdate.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.code()==200) {

                                Toast.makeText(getContext(),
                                        "Account successfully edited",
                                        Toast.LENGTH_LONG).show();
                            session.createLoginSession(user.getUsername(),user.getPassword());
                                LoggedUserFragment newfragment = new LoggedUserFragment();
                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(((ViewGroup) (getView().getParent())).getId(), newfragment);
                                fragmentTransaction.commit();
                            } else {
                                System.out.println(response.errorBody());
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                           // textView.setText("Something went wrong: " + t.getMessage());
                        }
                    });*/
                    }
                    else {
                    Toast.makeText(getContext(),
                            "Password you entered is not correct",
                            Toast.LENGTH_LONG).show();
                    pb.setVisibility(View.INVISIBLE);
                    }
                        }
                    else {
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
                });}
                else {
                    pb.setVisibility(View.VISIBLE);
                    if(validation==true && validationResult==true && oldpassword.getText().toString().equals(pass)){
                        pb.setVisibility(View.VISIBLE);
                        editCall(); }
                    else {
                        Toast.makeText(getContext(),
                                "Password you entered is not correct",
                                Toast.LENGTH_LONG).show();
                        pb.setVisibility(View.INVISIBLE);
                    }
                }
                }});

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
    private void validateNewPassword(Editable s) {
        if (ed_passwordnew.getText().toString().length()>=5) {
            passwordnew_layout2.setError(null);
            validation=true;
        }
        else{
            passwordnew_layout2.setError("Password should contain min.5 characters");
            validation=false;
        }
    }
    private void validateRetypePassword(Editable s) {
        if (ed_passwordnew.getText().toString().equals(ed_passwordRetype.getText().toString())) {
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

        if(ed_passwordRetype.getText().toString().equals("") && (!ed_passwordnew.getText().toString().isEmpty())) {
            passwordRetype_layout.setError("Re-type new password should not be empty");
            validation=false;}
        if(ed_password.getText().toString().equals("")) {
            password_layout.setError("Password should not be empty");
            validation=false;}

        return  validation;
    }

    public void editCall ()
    {
        final User user = new User();
        user.setFirstName(ed_name.getText().toString());
        user.setLastName(ed_surname.getText().toString());
        user.setUsername(ed_username.getText().toString());
        if(ed_passwordnew.getText().toString().isEmpty())
            user.setPassword(ed_password.getText().toString());
        else user.setPassword(ed_passwordnew.getText().toString());
        user.setEmail(ed_email.getText().toString());
        user.setTelephone(ed_phone.getText().toString());
        UserService service = RealEstateServiceGenerator.createService(UserService.class);
        final Call<ResponseBody> callUpdate = service.updateUser(oldusername.getText().toString(),user);
        callUpdate.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200) {

                    Toast.makeText(getContext(),
                            "Account successfully edited",
                            Toast.LENGTH_LONG).show();
                    session.createLoginSession(user.getUsername(),user.getPassword());
                    LoggedUserFragment newfragment = new LoggedUserFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(((ViewGroup) (getView().getParent())).getId(), newfragment);
                    fragmentTransaction.commit();
                } else {
                    System.out.println(response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // textView.setText("Something went wrong: " + t.getMessage());
            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EditProfileFragment.OnFragmentInteractionListener) {
            listener = (EditProfileFragment.OnFragmentInteractionListener) context;
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


