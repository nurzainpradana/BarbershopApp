package com.firmansyah.barbershop.view.signin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firmansyah.barbershop.R;
import com.firmansyah.barbershop.model.User;
import com.firmansyah.barbershop.util.Const;
import com.firmansyah.barbershop.util.EncryptMd5Java;
import com.firmansyah.barbershop.util.SharePref;
import com.firmansyah.barbershop.view.main.MainActivity;
import com.firmansyah.barbershop.view.registerone.RegisterOneAct;
import com.firmansyah.barbershop.viewmodel.BarbershopViewModel;

import java.util.List;

import static com.firmansyah.barbershop.util.NetworkUtility.isNetworkConnected;

public class SignInAct{
/*
    private EditText edtSignInUsername;
    private EditText edtSignInPassword;

    private String result;

    private EncryptMd5Java encryptMd5Java;

    private BarbershopViewModel barbershopViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button btnSignIn = findViewById(R.id.btn_sign_in);
        TextView registerNow = findViewById(R.id.text_register_now);
        edtSignInUsername = findViewById(R.id.edt_sign_in_username);
        edtSignInPassword = findViewById(R.id.edt_sign_in_password);

        registerNow.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

        barbershopViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(BarbershopViewModel.class);
    }


    @Override
    public void onClick(View v) {
        if (isNetworkConnected(this)) {
            if (v.getId() == R.id.btn_sign_in) {

                encryptMd5Java = new EncryptMd5Java();
                String username = edtSignInUsername.getText().toString();
                String password = encryptMd5Java.encrypt(encryptMd5Java.encrypt(edtSignInPassword.getText().toString()));

                if (username.isEmpty()) {
                    edtSignInUsername.setError(getString(R.string.not_null));
                } else {
                    if (password.isEmpty()) {
                        edtSignInPassword.setError(getString(R.string.not_null));
                    } else {
                        verification(username, password);
                    }
                }
            } else if (v.getId() == R.id.text_register_now) {
                Intent goToRegisterOne = new Intent(SignInAct.this, RegisterOneAct.class);
                startActivity(goToRegisterOne);
            }
        } else {
            Toast.makeText(this, getString(R.string.network_not_connect), Toast.LENGTH_SHORT).show();
        }

    }

    private void verification(String username, String password) {
        //Cek Verifikasi Username Password
        barbershopViewModel.setUser(username, this);
        barbershopViewModel.getUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (username.equals(users.get(0).getmUsername())) {
                    if (password.equals(users.get(0).getmPassword())) {
                        SharePref sharePref = new SharePref(SignInAct.this.getBaseContext());
                        sharePref.setInt(Const.ID_USER_KEY, users.get(0).getmIdUser());
                        sharePref.setString(Const.USERNAME_KEY, users.get(0).getmUsername());
                        result = getString(R.string.verification_success);
                        Toast.makeText(SignInAct.this, result, Toast.LENGTH_SHORT).show();
                        Intent goToHome = new Intent(SignInAct.this, MainActivity.class);
                        SignInAct.this.startActivity(goToHome);
                    } else {
                        result = SignInAct.this.getString(R.string.wrong_password);
                        Toast.makeText(SignInAct.this, result, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    result = SignInAct.this.getString(R.string.username_not_found);
                    Toast.makeText(SignInAct.this, result, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



 */
}
