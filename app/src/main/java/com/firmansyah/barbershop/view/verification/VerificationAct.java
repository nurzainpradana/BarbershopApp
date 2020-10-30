package com.firmansyah.barbershop.view.verification;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.firmansyah.barbershop.R;
import com.firmansyah.barbershop.model.User;
import com.firmansyah.barbershop.util.Const;
import com.firmansyah.barbershop.view.registertwo.RegisterTwoAct;
import com.firmansyah.barbershop.view.updatepassword.UpdatePasswordAct;

import java.util.concurrent.TimeUnit;

public class VerificationAct extends AppCompatActivity implements View.OnClickListener{
    TextView tvNoPhone;
    TextView tvResendCode;
    EditText edtPin1;
    EditText edtPin2;
    EditText edtPin3;
    EditText edtPin4;
    EditText edtPin5;
    EditText edtPin6;
    Button btn_verification_next;

    //Var untuk authentikasi
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener stateListener;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    String verificationId;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        tvNoPhone = findViewById(R.id.tv_send_code);
        tvResendCode = findViewById(R.id.tv_resend_code);
        edtPin1 = findViewById(R.id.pin_one);
        edtPin2 = findViewById(R.id.pin_two);
        edtPin3 = findViewById(R.id.pin_three);
        edtPin4 = findViewById(R.id.pin_four);
        edtPin5 = findViewById(R.id.pin_five);
        edtPin6 = findViewById(R.id.pin_six);
        btn_verification_next = findViewById(R.id.btn_verification_next);

        btn_verification_next.setOnClickListener(this);
        tvResendCode.setOnClickListener(this);

        User user = getIntent().getParcelableExtra(new Const().EXTRA_USER);
        if (user != null) {
            String phoneNumber = user.getmNoPhone();
            tvNoPhone.setText(String.format("%s%s%s", getString(R.string.code_send_to), " ", phoneNumber));
            setupVerificationCallback();
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber, // No telepon untuk verifikasi
                    60, //Durasi waktu habis
                    TimeUnit.SECONDS, //Unit TimeOut
                    this, //Activity
                    callbacks);
            Toast.makeText(this, "Memverifikasi, Mohon Tunggu", Toast.LENGTH_SHORT).show();
        }

        //Menghubungkan project dengan firebase auth
        auth = FirebaseAuth.getInstance();
        stateListener = firebaseAuth -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            //Mendeteksi apakah ada firebaseUser yang sedang login (belum logout)
            if (firebaseUser != null) {
                //Jika ada, User tidak perlu login lagi dan langsung menuju
                //Welcome ACtivity
                if (getIntent().getStringExtra(new Const().EXTRA_TYPE) == new Const().REGISTRATION_KEY) {
                    startActivity(new Intent(VerificationAct.this, RegisterTwoAct.class));
                    finish();
                } else if (getIntent().getStringExtra(new Const().EXTRA_TYPE) == new Const().UPDATE_KEY) {
                    startActivity(new Intent(VerificationAct.this, UpdatePasswordAct.class));
                    finish();
                }

            }
        };

        //Menghubungkan project dengan firebase auth
        auth = FirebaseAuth.getInstance();
        moveEditTextPin();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Melampirkan listener pada FirebaseAuth saat activity dimulai
        auth.addAuthStateListener(stateListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Menghapus listener pada FirebaseAuth saat activity dihentikan
        auth.removeAuthStateListener(stateListener);
    }

    private void moveEditTextPin() {
        edtPin1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtPin1.getText().toString().length() == 1) {
                    edtPin2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtPin2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtPin2.getText().toString().length() == 1) {
                    edtPin3.requestFocus();
                } else {
                    edtPin1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtPin3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtPin3.getText().toString().length() == 1) {
                    edtPin4.requestFocus();
                } else {
                    edtPin2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtPin4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtPin4.getText().toString().length() == 1) {
                    edtPin5.requestFocus();
                } else {
                    edtPin3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtPin5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtPin5.getText().toString().length() == 1) {
                    edtPin6.requestFocus();
                } else {
                    edtPin4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setupVerificationCallback() {
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                //Callback didalam sini akan dipanggil saat terjadi proses pengiriman kode
                //Dan user diminta untuk memasukan kode verifikasi

                //untuk menyimpan ID verifikasi dan kirim ulang token
                verificationId = s;
                resendingToken = forceResendingToken;
                tvResendCode.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Mendapatkan Kode Verifikasi", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                //Callback disini akan dipanggil saat verifikasi berhasil
                Toast.makeText(getApplicationContext(), "Verifikasi Selesai", Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                //Callback disini akan dipanggil saat permintaan tidak valid atau terdapat kesalahan
                Toast.makeText(getApplicationContext(), "Verifikasi Gagal, Silahkan Coba Lagi ", Toast.LENGTH_SHORT).show();
                Log.d("ERROR VERIFICARTION", e.getMessage());
                tvResendCode.setEnabled(true);
            }
        };
    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //Sign in berhasil
                        String type = getIntent().getStringExtra(new Const().EXTRA_TYPE);
                        User user = getIntent().getParcelableExtra(new Const().EXTRA_USER);
                        if (type.equals(new Const().REGISTRATION_KEY)) {
                            Intent goToRegisterTwo = new Intent(VerificationAct.this, RegisterTwoAct.class);
                            goToRegisterTwo.putExtra(new Const().EXTRA_USER, user);
                            startActivity(goToRegisterTwo);
                            finish();
                        } else if (type.equals(new Const().UPDATE_KEY)) {
                                Intent goToUpdatePassword = new Intent(VerificationAct.this, UpdatePasswordAct.class);
                                startActivity(goToUpdatePassword);
                                finish();
                        }
                    } else {
                        //Sign gagal
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            //Kode yang dimasukkan tidak valid
                            Toast.makeText(VerificationAct.this, "Kode yang dimasukkan tidak valid", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_verification_next:
                String verificationCodes = edtPin1.getText().toString()
                        + edtPin2.getText().toString()
                        + edtPin3.getText().toString()
                        + edtPin4.getText().toString()
                        + edtPin5.getText().toString()
                        + edtPin6.getText().toString();

                if (TextUtils.isEmpty(verificationCodes)) {
                    //Memberi pesan pada user bahwa kode verifikasi tidak boleh kosong
                    Toast.makeText(this, "Masukan Kode Verifikasi", Toast.LENGTH_SHORT).show();
                } else {
                    //Memverifikasi nomor telepon, saat tombol verifikasi ditekan
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, verificationCodes);
                    signInWithPhoneAuthCredential(credential);
                    Toast.makeText(this, "Sedang Memverifikasi", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.tv_resend_code:
                Toast.makeText(this, "Mengirim Ulang Kode Verifikasi", Toast.LENGTH_SHORT).show();
                User user = getIntent().getParcelableExtra(new Const().EXTRA_USER);
                if (user != null) {
                    phoneNumber = user.getmNoPhone();
                    setupVerificationCallback();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phoneNumber,
                            60,
                            TimeUnit.SECONDS,
                            this,
                            callbacks,
                            resendingToken);
                    Toast.makeText(this, "Mengirim ulang kode verifikasi", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
