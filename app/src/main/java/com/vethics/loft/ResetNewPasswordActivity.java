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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import model.ResetPasssword.ResetPasswordData;
import model.ResetPasssword.ResetPasswordError;
import model.ResetPasssword.ResetPasswordSuccess;
import retrofit2.Call;
import retrofit2.Callback;
import utils.API;
import utils.APIClient;
import utils.AndroidUtils;

public class ResetNewPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    String verificationCode;
    TextInputLayout tilChangePasswordVerificationCode, tilChangePasswordNewPass, tilChangePasswordConfirmPass;
    EditText edtChangePassVerificationCode, edtChangePassNewPass, edtChangePassConfirmPass;
    Button btnChangePasssword;
    private final String TAG = getClass().getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_new_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
            }
        });

        if (getIntent().getExtras() != null)
            verificationCode = getIntent().getExtras().getString("verificationCode");

       /* tilChangePasswordVerificationCode = (TextInputLayout) findViewById(R.id.til_change_password_verification_code);
        tilChangePasswordNewPass = (TextInputLayout) findViewById(R.id.til_change_password_new_pass);
        tilChangePasswordConfirmPass = (TextInputLayout) findViewById(R.id.til_change_password_confirm_pass);
*/
        edtChangePassVerificationCode = (EditText) findViewById(R.id.edt_change_pass_verification_code);
        edtChangePassNewPass = (EditText) findViewById(R.id.edt_change_pass_new_pass);
        edtChangePassConfirmPass = (EditText) findViewById(R.id.edt_change_pass_confirm_pass);

        btnChangePasssword = (Button) findViewById(R.id.btn_change_password);

        btnChangePasssword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnChangePasssword) {
            AndroidUtils.hideSoftKeyboard(ResetNewPasswordActivity.this);
            tilChangePasswordVerificationCode.setError(null);
            tilChangePasswordVerificationCode.setHintTextAppearance(R.style.MyHintText);
            tilChangePasswordNewPass.setError(null);
            tilChangePasswordNewPass.setHintTextAppearance(R.style.MyHintText);
            tilChangePasswordConfirmPass.setError(null);
            tilChangePasswordConfirmPass.setHintTextAppearance(R.style.MyHintText);

            if (edtChangePassVerificationCode.getText().toString().trim().isEmpty()) {
                tilChangePasswordVerificationCode.setError("Verification Code Required!");
                tilChangePasswordVerificationCode.setHintTextAppearance(R.style.MyHintText);
            } else if (!edtChangePassVerificationCode.getText().toString().trim().matches(verificationCode)) {
                tilChangePasswordVerificationCode.setError("Invalid Verification Code!");
                tilChangePasswordVerificationCode.setHintTextAppearance(R.style.MyHintText);
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
                if (AndroidUtils.hasConnection(ResetNewPasswordActivity.this)) {
                    doChangePassword();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(btnChangePasssword, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        }
    }

    private void doChangePassword() {
        String strVerificationCode = edtChangePassVerificationCode.getText().toString().trim();
        String strNewPassword = edtChangePassNewPass.getText().toString().trim();
        String strConfirmPassword = edtChangePassConfirmPass.getText().toString().trim();

        final ProgressDialog loading = new ProgressDialog(ResetNewPasswordActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        API apiService = APIClient.getClient().create(API.class);

        Call<ResetPasswordSuccess> call1 = apiService.reset_password(strVerificationCode, strNewPassword, strConfirmPassword);
        call1.enqueue(new Callback<ResetPasswordSuccess>() {

            @Override
            public void onResponse(@NonNull Call<ResetPasswordSuccess> call, @NonNull retrofit2.Response<ResetPasswordSuccess> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    Log.e(TAG, "Response Success : " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getData()));

                    ResetPasswordSuccess resetpasswordresonse = response.body();
                    String strStatus = resetpasswordresonse.getStatus();
                    if (strStatus.equalsIgnoreCase("Success")) {
                        String strMessage = resetpasswordresonse.getMessage();
                        ResetPasswordData resetpasswordData = resetpasswordresonse.getData();
                        Toast.makeText(ResetNewPasswordActivity.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ResetNewPasswordActivity.this, EmailSigninActivity.class);
                        startActivity(i);
                    }

                } else {
                    Log.e(TAG, "Response Error : " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    try {
                        Gson gson = new Gson();
                        ResetPasswordError resetpasswordError = gson.fromJson(response.errorBody().string(), ResetPasswordError.class);
                        if (resetpasswordError.getMessage().getVerificationToken() != null) {
                            tilChangePasswordVerificationCode.setError(resetpasswordError.getMessage().getVerificationToken());
                            tilChangePasswordVerificationCode.setHintTextAppearance(R.style.MyErrorText);
                            tilChangePasswordVerificationCode.requestFocus();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResetPasswordSuccess> call, @NonNull Throwable t) {
                loading.dismiss();
                t.printStackTrace();
                Log.e(TAG, "Reset Password CheckSocialSuccessData : " + t.getMessage());
            }
        });
    }
}
