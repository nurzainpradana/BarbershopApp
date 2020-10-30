package com.firmansyah.barbershop.view.updatepassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firmansyah.barbershop.R;
import com.firmansyah.barbershop.model.User;
import com.firmansyah.barbershop.util.Const;
import com.firmansyah.barbershop.util.EncryptMd5Java;
import com.firmansyah.barbershop.util.SharePref;
import com.firmansyah.barbershop.view.updateprofile.UpdateProfileActivity;
import com.firmansyah.barbershop.viewmodel.BarbershopViewModel;

public class UpdatePasswordAct extends AppCompatActivity implements View.OnClickListener{

    private ImageView btnBack;
    private TextView titleBar;
    private EditText edtNewPassword;
    private EditText edtReEnterPassword;
    private Button btnSave;

    BarbershopViewModel barbershopViewModel;

    private User user;

    private String oldPasword;
    private String newPasword;
    private String reEnterPasword;

    EncryptMd5Java encryptMd5Java;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        barbershopViewModel = new BarbershopViewModel();

        View topBar = findViewById(R.id.top_bar_layout);
        titleBar = topBar.findViewById(R.id.title_bar);
        btnBack = topBar.findViewById(R.id.btn_back);
        btnSave = findViewById(R.id.btn_save);

        encryptMd5Java = new EncryptMd5Java();

        titleBar.setText(R.string.update_password);
        btnBack.setOnClickListener(this);

        edtNewPassword = findViewById(R.id.edt_new_password);
        edtReEnterPassword = findViewById(R.id.edt_password_confirmation);

        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back :
                Intent goToUpdateProfile = new Intent(UpdatePasswordAct.this, UpdateProfileActivity.class);
                startActivity(goToUpdateProfile);
                finish();
                break;

            case R.id.btn_save :
                if (edtNewPassword.getText() == null) {
                    edtNewPassword.setError(getString(R.string.not_null));
                } else if (edtReEnterPassword.getText() == null) {
                    edtReEnterPassword.setError(getString(R.string.not_null));
                } else if (edtNewPassword.getText() != null &&  edtReEnterPassword.getText() != null) {
                    newPasword = edtNewPassword.getText().toString();
                    reEnterPasword = edtReEnterPassword.getText().toString();

                    if (newPasword.equals(reEnterPasword)){
                        SharePref sharePref = new SharePref(getApplicationContext());
                        String username = sharePref.getString(Const.ID_USER_KEY);
                        //barbershopViewModel.setUpdatePassword(getApplicationContext(), username, encryptMd5Java.encrypt(encryptMd5Java.encrypt(newPasword)));
                    } else {
                        edtReEnterPassword.setError(getString(R.string.password_not_equal));
                    }
                }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent goToUpdateProfile = new Intent(UpdatePasswordAct.this, UpdateProfileActivity.class);
        startActivity(goToUpdateProfile);
        finish();
    }
}
