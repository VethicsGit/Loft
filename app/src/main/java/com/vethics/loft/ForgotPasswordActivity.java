package com.vethics.loft;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.google.gson.Gson;

import java.io.IOException;

import model.ForgotPassword.ForgotPasswordData;
import model.ForgotPassword.ForgotPasswordError;
import model.ForgotPassword.ForgotPasswordSuccess;
import retrofit2.Call;
import retrofit2.Callback;
import utils.API;
import utils.APIClient;
import utils.AndroidUtils;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout tilForgotPasswordUsername;
    EditText edtForgotPasswordUsername;
    Button btnResetPassword;
    private final String TAG = getClass().getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_app_close);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

//        tilForgotPasswordUsername = (TextInputLayout) findViewById(R.id.til_forgot_password_username);
        edtForgotPasswordUsername = (EditText) findViewById(R.id.edt_forgot_pass_username);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
            }
        });

        btnResetPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String email_validate = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (v == btnResetPassword) {
            AndroidUtils.hideSoftKeyboard(ForgotPasswordActivity.this);
            tilForgotPasswordUsername.setError(null);
            tilForgotPasswordUsername.setHintTextAppearance(R.style.MyHintText);
            if (!edtForgotPasswordUsername.getText().toString().trim().matches(email_validate)) {
                tilForgotPasswordUsername.setError("Enter Valid Email Address!");
                tilForgotPasswordUsername.setHintTextAppearance(R.style.MyHintText);
            } else {
                if (AndroidUtils.hasConnection(ForgotPasswordActivity.this)) {
                    doResetPassword();
                } else {
                    Snackbar snackbar = Snackbar.make(btnResetPassword, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        }
    }

    private void doResetPassword() {
        String strUserName = edtForgotPasswordUsername.getText().toString().trim();
        Log.e(TAG, "Username : " + strUserName);

        final ProgressDialog loading = new ProgressDialog(ForgotPasswordActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        API apiService = APIClient.getClient().create(API.class);
        Call<ForgotPasswordSuccess> call1 = apiService.forgot_password(strUserName);
        call1.enqueue(new Callback<ForgotPasswordSuccess>() {

            @Override
            public void onResponse(@NonNull Call<ForgotPasswordSuccess> call, @NonNull retrofit2.Response<ForgotPasswordSuccess> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ForgotPasswordSuccess forgotpasswordresonse = response.body();
                    String strStatus = forgotpasswordresonse.getStatus();
                    if (strStatus.equalsIgnoreCase("Success")) {
                        String strMessage = forgotpasswordresonse.getMessage();
                        ForgotPasswordData forgotpasswordData = forgotpasswordresonse.getData();

                        String strToken = forgotpasswordData.getToken();
                        Log.e(TAG, "strToken : " + strToken);
                        Intent i = new Intent(ForgotPasswordActivity.this, ResetNewPasswordActivity.class);
                        i.putExtra("verificationCode", strToken);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                        /*finishAffinity();*/
                    }

                } else {
                    Log.e(TAG, "Response Error : " + response.code());
                    try {
                        Gson gson = new Gson();
                        ForgotPasswordError forgotpasswordError = gson.fromJson(response.errorBody().string(), ForgotPasswordError.class);
                        if (forgotpasswordError.getMessage().getUserName() != null) {
                            tilForgotPasswordUsername.setError(forgotpasswordError.getMessage().getUserName());
                            tilForgotPasswordUsername.setHintTextAppearance(R.style.MyErrorText);
                            tilForgotPasswordUsername.requestFocus();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ForgotPasswordSuccess> call, @NonNull Throwable t) {
                loading.dismiss();
                t.printStackTrace();
                Log.e(TAG, "forgotpasswordData : " + t.getMessage());
            }
        });
    }
}
