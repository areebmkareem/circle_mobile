package com.flukycoder.circle.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.flukycoder.circle.HomeActivity;
import com.flukycoder.circle.MainActivity;
import com.flukycoder.circle.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class LoginFragment extends Fragment {

    TextView loginEmail,loginPassword;
    Button loginButton;
    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_login, container, false);

        loginEmail=rootView.findViewById(R.id.login_email_tv);
        loginPassword=rootView.findViewById(R.id.login_password_tv);
        loginButton=rootView.findViewById(R.id.login_fragment_btn);

        loginButton.setOnClickListener(v -> {
            if((loginEmail.getText()!=null) && (loginPassword.getText()!=null)) {
                if(!(loginEmail.getText().toString().isEmpty()) && !(loginPassword.getText().toString().isEmpty())) {
                    callLoginAPI(loginEmail.getText().toString(), loginPassword.getText().toString());
                }
            }
        });

        return rootView;
    }

    public void callLoginAPI( String email, String password){
        AndroidNetworking.post("https://circle-alpha-api.herokuapp.com/login")
                .addBodyParameter("email", email)
                .addBodyParameter("password", password)
                .setTag("Register")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.i("null","response: "+response);
                        if(response!=null) {
                            try {
                                if(response.get("error")==null) {
                                    startActivity(new Intent(getContext(), HomeActivity.class));
                                    Objects.requireNonNull(getActivity()).finish();
                                } else Toast.makeText(getContext(), "Error Login", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.i("null","error: "+error);
                    }
                });
    }
}
