package com.flukycoder.circle.fragment;


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
import com.flukycoder.circle.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;


public class RegisterFragment extends Fragment {

    TextView registerName,registerEmail,registerPassword;
    Button registerButton;

    public RegisterFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_register, container, false);

        registerName=rootView.findViewById(R.id.register_name_tv);
        registerEmail=rootView.findViewById(R.id.register_email_tv);
        registerPassword=rootView.findViewById(R.id.register_password_tv);
        registerButton=rootView.findViewById(R.id.register_fragment_btn);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), ""+registerName.getText(), Toast.LENGTH_SHORT).show();
                if((registerName.getText()!=null) && (registerEmail.getText()!=null) &&
                        (registerPassword.getText()!=null)){
                    if(!(registerName.getText().toString().isEmpty()) && !(registerEmail.getText().toString().isEmpty()) &&
                            !(registerPassword.getText().toString().isEmpty())) {
                        callRegisterAPI(registerName.getText().toString(),
                                registerEmail.getText().toString(), registerPassword.getText().toString());
                    }
                }
            }
        });

        return rootView;
    }

    public void callRegisterAPI( String name,String email, String password){
        AndroidNetworking.post("https://circle-alpha-api.herokuapp.com/register")
                .addBodyParameter("name", name)
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
                                    Toast.makeText(getContext(), "Register Successful", Toast.LENGTH_SHORT).show();
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
