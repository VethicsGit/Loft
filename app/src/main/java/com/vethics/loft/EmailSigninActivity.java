package com.vethics.loft;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import customview.FontChangeCrawler;
import model.LoginResponse.LoginData;
import model.LoginResponse.LoginError;
import model.LoginResponse.LoginSuccess;
import retrofit2.Call;
import retrofit2.Callback;
import utils.API;
import utils.APIClient;
import utils.AndroidUtils;
import utils.SessionManager;

public class EmailSigninActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout tilLoginUsername, tilLoginPassword;
    EditText edtLoginUsername, edtLoginPassword;
    TextView tvSignUp,ivLoginForgotPassword;
    Button btnSignin;

    Drawable close_icon;
    private final String TAG = getClass().getCanonicalName();

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_signin);
        edtLoginUsername = (EditText) findViewById(R.id.edt_login_username);
        edtLoginPassword = (EditText) findViewById(R.id.edt_login_password);
        ivLoginForgotPassword =  findViewById(R.id.tv_login_forgot_password);

        tvSignUp = (TextView) findViewById(R.id.tv_sign_up);
        btnSignin = (Button) findViewById(R.id.btn_signin);
        tilLoginUsername = (TextInputLayout) findViewById(R.id.til_login_username);
        tilLoginPassword = (TextInputLayout) findViewById(R.id.til_login_password);

//        edtLoginPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_login_forgot, 0);
        close_icon = AppCompatResources.getDrawable(this, R.drawable.ic_app_close);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "font/avenirltstd_light.otf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        tilLoginUsername.setTypeface(Typeface.createFromAsset(getAssets(), "font/avenirltstd_light.otf"));
        tilLoginPassword.setTypeface(Typeface.createFromAsset(getAssets(), "font/avenirltstd_light.otf"));

      /*  edtLoginPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    edtLoginPassword.performClick();
                    if (event.getRawX() >= (edtLoginPassword.getRight() - edtLoginPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Intent i = new Intent(EmailSigninActivity.this, ForgotPasswordActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
                        return true;
                    }
                }
                return false;
            }
        });*/
        btnSignin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        ivLoginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EmailSigninActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        AndroidUtils.hideSoftKeyboard(EmailSigninActivity.this);
        tilLoginUsername.setError(null);
        tilLoginPassword.setError(null);
        tilLoginUsername.setHintTextAppearance(R.style.MyHintText);
        tilLoginPassword.setHintTextAppearance(R.style.MyHintText);

        if (v == btnSignin) {
            String email_validate = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            if (edtLoginUsername.getText().toString().trim().isEmpty()) {
                tilLoginUsername.setError("Username required!");
                tilLoginUsername.setHintTextAppearance(R.style.MyErrorText);
                tilLoginUsername.requestFocus();
            } else if (!edtLoginUsername.getText().toString().trim().matches(email_validate)) {
                tilLoginUsername.setError("Email is not valid!");
                tilLoginUsername.setHintTextAppearance(R.style.MyErrorText);
                tilLoginUsername.requestFocus();
            } else if (edtLoginPassword.getText().toString().trim().isEmpty()) {
                tilLoginPassword.setError("Password required!");
                tilLoginPassword.setHintTextAppearance(R.style.MyErrorText);
                tilLoginPassword.requestFocus();
            } else if (edtLoginPassword.getText().toString().trim().length() < 6) {
                tilLoginPassword.setError("Password must be at least 6 digits long!");
                tilLoginPassword.setHintTextAppearance(R.style.MyErrorText);
                tilLoginPassword.requestFocus();
            } else if (edtLoginPassword.getText().toString().trim().contains(" ")) {
                tilLoginPassword.setError("Password must not contain space!");
                tilLoginPassword.setHintTextAppearance(R.style.MyErrorText);
                tilLoginPassword.requestFocus();
            } else {
                if (AndroidUtils.hasConnection(EmailSigninActivity.this)) {
                    doLogin();
                } else {
                    Snackbar snackbar = Snackbar.make(btnSignin, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        } else if (v == tvSignUp) {
            Intent i = new Intent(this, SignupActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (v == ivLoginForgotPassword) {

        }
    }

    private void doLogin() {
        String strUserName = edtLoginUsername.getText().toString().trim();
        String strPassword = edtLoginPassword.getText().toString().trim();

        final ProgressDialog loading = new ProgressDialog(EmailSigninActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        API apiService = APIClient.getClient().create(API.class);
        Call<LoginSuccess> call1 = apiService.login(strUserName, strPassword);
        call1.enqueue(new Callback<LoginSuccess>() {

            @Override
            public void onResponse(@NonNull Call<LoginSuccess> call, @NonNull retrofit2.Response<LoginSuccess> response) {
                loading.dismiss();

                if (response.isSuccessful()) {
                    //Log.e(TAG, "Response Success : " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getData()));
                    LoginSuccess loginResponse = response.body();
                    String strStatus = loginResponse.getStatus();
                    if (strStatus.equalsIgnoreCase("Success")) {
                        String strMessage = loginResponse.getMessage();
                        LoginData loginData = loginResponse.getData();

                        String strStudentId = loginData.getStudentId();
                        String email = loginData.getEmail();
                        String name = loginData.getName();

                        Log.e(TAG, "strMessage : " + strMessage);
                        SessionManager sessionManager = new SessionManager(EmailSigninActivity.this);
                        sessionManager.createLoginSession(strStudentId, email, name);
                        Intent i = new Intent(EmailSigninActivity.this, DashBoardActivity.class);
                        i.putExtra("flag", "");
                        startActivity(i);
                        finishAffinity();
                    }

                } else {
                    Log.e(TAG, "Response Error : " + response.code());
                    try {
                        Gson gson = new Gson();
                        LoginError loginError = gson.fromJson(response.errorBody().string(), LoginError.class);
                        if (loginError.getMessage().getUserName() != null) {
                            tilLoginUsername.setError(loginError.getMessage().getUserName());
                            tilLoginUsername.setHintTextAppearance(R.style.MyErrorText);
                            tilLoginUsername.requestFocus();
                        } else if (loginError.getMessage().getPassword() != null) {
                            tilLoginPassword.setError(loginError.getMessage().getPassword());
                            tilLoginPassword.setHintTextAppearance(R.style.MyErrorText);
                            tilLoginPassword.requestFocus();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginSuccess> call, @NonNull Throwable t) {
                loading.dismiss();
                t.printStackTrace();
            }
        });
    }
}
