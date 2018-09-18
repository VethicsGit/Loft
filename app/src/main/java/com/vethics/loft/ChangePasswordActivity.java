package com.vethics.loft;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import model.ChangePasswordSuccess.ChangePasswordData;
import model.ChangePasswordSuccess.ChangePasswordError;
import model.ChangePasswordSuccess.ChangePasswordSuccess;
import retrofit2.Call;
import retrofit2.Callback;
import utils.API;
import utils.APIClient;
import utils.AndroidUtils;
import utils.SessionManager;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout tilChangePasswordOldPassword, tilChangePasswordNewPass, tilChangePasswordConfirmPass;
    EditText edtChangePassOldPassword, edtChangePassNewPass, edtChangePassConfirmPass;
    Button btnChangePasssword;
    SharedPreferences spLogin;
    String strUserEmail;
    private final String TAG = getClass().getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tilChangePasswordOldPassword = (TextInputLayout) findViewById(R.id.til_change_password_old_pass);
        tilChangePasswordNewPass = (TextInputLayout) findViewById(R.id.til_change_password_new_pass);
        tilChangePasswordConfirmPass = (TextInputLayout) findViewById(R.id.til_change_password_confirm_pass);

        edtChangePassOldPassword = (EditText) findViewById(R.id.edt_change_pass_old_pass);
        edtChangePassNewPass = (EditText) findViewById(R.id.edt_change_pass_new_pass);
        edtChangePassConfirmPass = (EditText) findViewById(R.id.edt_change_pass_confirm_pass);

        btnChangePasssword = (Button) findViewById(R.id.btn_change_password);

        spLogin = getSharedPreferences(SessionManager.PREF_NAME, Context.MODE_PRIVATE);
        strUserEmail = spLogin.getString(SessionManager.KEY_EMAIL, "");

        btnChangePasssword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnChangePasssword) {
            AndroidUtils.hideSoftKeyboard(ChangePasswordActivity.this);
            tilChangePasswordOldPassword.setError(null);
            tilChangePasswordOldPassword.setHintTextAppearance(R.style.MyHintText1);
            tilChangePasswordNewPass.setError(null);
            tilChangePasswordNewPass.setHintTextAppearance(R.style.MyHintText1);
            tilChangePasswordConfirmPass.setError(null);
            tilChangePasswordConfirmPass.setHintTextAppearance(R.style.MyHintText1);

            if (edtChangePassOldPassword.getText().toString().trim().isEmpty()) {
                tilChangePasswordOldPassword.setError("Old Password Required!");
                tilChangePasswordOldPassword.setHintTextAppearance(R.style.MyHintText);
            } else if (edtChangePassNewPass.getText().toString().trim().isEmpty()) {
                tilChangePasswordNewPass.setError("New Password Required!");
                tilChangePasswordNewPass.setHintTextAppearance(R.style.MyHintText);
            } else if (edtChangePassNewPass.getText().toString().trim().length() < 6) {
                tilChangePasswordNewPass.setError("New Password must be at least 6 digits long!!");
                tilChangePasswordNewPass.setHintTextAppearance(R.style.MyHintText);
            } else if (edtChangePassNewPass.getText().toString().trim().contains(" ")) {
                tilChangePasswordNewPass.setError("Password must not contain space!");
                tilChangePasswordNewPass.setHintTextAppearance(R.style.MyHintText);
            } else if (edtChangePassConfirmPass.getText().toString().trim().isEmpty()) {
                tilChangePasswordConfirmPass.setError("Confirm Password Required!");
                tilChangePasswordConfirmPass.setHintTextAppearance(R.style.MyHintText);
            } else if (!edtChangePassConfirmPass.getText().toString().trim().matches(edtChangePassNewPass.getText().toString().trim())) {
                tilChangePasswordConfirmPass.setError("Passwords do not match!");
                tilChangePasswordConfirmPass.setHintTextAppearance(R.style.MyHintText);
            } else {
                if (AndroidUtils.hasConnection(ChangePasswordActivity.this)) {
                    doChangePassword();
                } else {
                    Snackbar snackbar = Snackbar.make(btnChangePasssword, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        }
    }

    private void doChangePassword() {
        String strOldPassword = edtChangePassOldPassword.getText().toString().trim();
        String strNewPassword = edtChangePassNewPass.getText().toString().trim();
        String strConfirmPassword = edtChangePassConfirmPass.getText().toString().trim();

        final ProgressDialog loading = new ProgressDialog(ChangePasswordActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        API apiService = APIClient.getClient().create(API.class);

        Call<ChangePasswordSuccess> call1 = apiService.change_password(strUserEmail, strOldPassword, strNewPassword, strConfirmPassword);
        call1.enqueue(new Callback<ChangePasswordSuccess>() {

            @Override
            public void onResponse(@NonNull Call<ChangePasswordSuccess> call, @NonNull retrofit2.Response<ChangePasswordSuccess> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    Log.e(TAG, "Response Success : " + new GsonBuilder().setPrettyPrinting().create().toJson(response));

                    ChangePasswordSuccess changepasswordresonse = response.body();
                    String strStatus = changepasswordresonse.getStatus();
                    if (strStatus.equalsIgnoreCase("Success")) {
                        String strMessage = changepasswordresonse.getMessage();
                        ChangePasswordData changepasswordData = changepasswordresonse.getData();
                        Toast.makeText(ChangePasswordActivity.this, "Your password changed successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ChangePasswordActivity.this, StudentProfileActivity.class);
                        startActivity(i);
                        finish();
                    }

                } else {
                    Log.e(TAG, "Response Error : " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    try {
                        Gson gson = new Gson();
                        ChangePasswordError changepasswordError = gson.fromJson(response.errorBody().string(), ChangePasswordError.class);
                        if (changepasswordError.getMessage().getOldPassword() != null) {
                            tilChangePasswordOldPassword.setError(changepasswordError.getMessage().getOldPassword());
                            tilChangePasswordOldPassword.setHintTextAppearance(R.style.MyErrorText);
                            tilChangePasswordOldPassword.requestFocus();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChangePasswordSuccess> call, @NonNull Throwable t) {
                loading.dismiss();
                t.printStackTrace();
                Log.e(TAG, "changepasswordData : " + t.getMessage());
            }
        });
    }
}
