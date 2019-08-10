package com.flukycoder.circle;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.flukycoder.circle.fragment.LoginFragment;
import com.flukycoder.circle.fragment.RegisterFragment;

public class MainActivity extends AppCompatActivity {

    Button loginButton,registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton=findViewById(R.id.main_login_button);
        registerButton=findViewById(R.id.main_register_button);


        loginButton.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new LoginFragment()).addToBackStack(null).commit();
            ConstraintLayout layout=(ConstraintLayout)loginButton.getParent();
            layout.setVisibility(View.GONE);
        });

        registerButton.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new RegisterFragment()).addToBackStack(null).commit();
            ConstraintLayout layout=(ConstraintLayout)loginButton.getParent();
            layout.setVisibility(View.GONE);
        });
    }

    @Override
    public void onBackPressed() {
        ConstraintLayout layout=(ConstraintLayout)loginButton.getParent();
        layout.setVisibility(View.VISIBLE);
        if(getSupportFragmentManager().getBackStackEntryCount()!=0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
