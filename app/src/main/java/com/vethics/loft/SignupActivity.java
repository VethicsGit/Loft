package com.vethics.loft;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import customview.FontChangeCrawler;
import model.RegisterResponse.RegisterData;
import model.RegisterResponse.RegisterError;
import model.RegisterResponse.RegisterSuccess;
import retrofit2.Call;
import retrofit2.Callback;
import utils.API;
import utils.APIClient;
import utils.AndroidUtils;
import utils.SessionManager;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    CoordinatorLayout coordinatorLayout;
    TextInputLayout tilRegisterName, tilRegisterEmail, tilRegisterPassword;
    RadioGroup rgGender;
    RadioButton rbMale, rbFemale;
    TextView tvGenderTag, tvBirthdayTag, tvSelectBirthday, tvTnC,alredylogin;
    TextView tvErrorGender, tvErrorBirthday;
    Button btnRegisterContinue;
    String sGender = "", sDOB = "", sName, sEmail, sPassword;
    EditText edtRegisterName, edtRegisterEmail, edtRegisterPassword;
    CheckBox cbTnc;
    private final String TAG = getClass().getCanonicalName();

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_app_close);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "font/avenirltstd_light.otf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.container);

        tilRegisterName = (TextInputLayout) findViewById(R.id.til_register_name);
        tilRegisterEmail = (TextInputLayout) findViewById(R.id.til_register_email);
        tilRegisterPassword = (TextInputLayout) findViewById(R.id.til_register_password);

        tilRegisterName.setTypeface(Typeface.createFromAsset(getAssets(), "font/avenirltstd_light.otf"));
        tilRegisterEmail.setTypeface(Typeface.createFromAsset(getAssets(), "font/avenirltstd_light.otf"));
        tilRegisterPassword.setTypeface(Typeface.createFromAsset(getAssets(), "font/avenirltstd_light.otf"));

        edtRegisterName = (EditText) findViewById(R.id.edt_register_name);
        edtRegisterEmail = (EditText) findViewById(R.id.edt_register_email);
        edtRegisterPassword = (EditText) findViewById(R.id.edt_register_password);

        rgGender = (RadioGroup) findViewById(R.id.rg_gender);
        rbMale = (RadioButton) findViewById(R.id.rb_male);
        rbFemale = (RadioButton) findViewById(R.id.rb_female);

//        tvGenderTag = (TextView) findViewById(R.id.tv_gender_tag);
//        tvBirthdayTag = (TextView) findViewById(R.id.tv_birthday_tag);
        tvSelectBirthday = (TextView) findViewById(R.id.tv_select_birthday);
        tvErrorGender = (TextView) findViewById(R.id.tv_error_gender);
        tvErrorBirthday = (TextView) findViewById(R.id.tv_error_birthday);
        alredylogin =findViewById(R.id.alredylogin);

        alredylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignupActivity.this,EmailSigninActivity.class);
                startActivity(intent);
            }
        });


        btnRegisterContinue = (Button) findViewById(R.id.btn_register_continue);
       tvTnC = (TextView) findViewById(R.id.tv_tnc);

        cbTnc = (CheckBox) findViewById(R.id.cb_tnc);

        final Drawable drawableChecked = AppCompatResources.getDrawable(this, R.drawable.ic_radio_active);
        final Drawable drawableUnchecked = AppCompatResources.getDrawable(this, R.drawable.ic_radio_inactive);

        rbMale.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableUnchecked, null, null, null);
        rbFemale.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableUnchecked, null, null, null);

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_male) {
                    sGender = "m";
                    rbMale.setTextColor(getResources().getColor(R.color.colorWhite));
                    rbMale.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableChecked, null, null, null);
                    rbFemale.setTextColor(getResources().getColor(R.color.colorTransparentWhite));
                    rbFemale.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableUnchecked, null, null, null);
                } else if (checkedId == R.id.rb_female) {
                    sGender = "f";
                    rbFemale.setTextColor(getResources().getColor(R.color.colorWhite));
                    rbFemale.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableChecked, null, null, null);
                    rbMale.setTextColor(getResources().getColor(R.color.colorTransparentWhite));
                    rbMale.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableUnchecked, null, null, null);
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        tvSelectBirthday.setOnClickListener(this);
        btnRegisterContinue.setOnClickListener(this);
        tvTnC.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tvSelectBirthday) {
            final Calendar myCalendar = Calendar.getInstance();

            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, month);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    String setFormat = "MMM dd, yyyy";
                    SimpleDateFormat setsdf = new SimpleDateFormat(setFormat, Locale.US);
                    String date = setsdf.format(myCalendar.getTime());
                    tvSelectBirthday.setText(date);

                    String sendFormat = "yyyy-MM-dd";
                    SimpleDateFormat sendsdf = new SimpleDateFormat(sendFormat, Locale.US);
                    tvSelectBirthday.setTextColor(getResources().getColor(R.color.colorWhite));
                    sDOB = sendsdf.format(myCalendar.getTime());
                    Log.e(TAG, "dob : " + sDOB);
                }
            };

            DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this, R.style.DialogTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            datePickerDialog.show();
        } else if (v == btnRegisterContinue) {
            AndroidUtils.hideSoftKeyboard(SignupActivity.this);
            tilRegisterName.setError(null);
            tilRegisterEmail.setError(null);
            tilRegisterPassword.setError(null);
            tilRegisterName.setHintTextAppearance(R.style.MyHintText);
            tilRegisterEmail.setHintTextAppearance(R.style.MyHintText);
            tilRegisterPassword.setHintTextAppearance(R.style.MyHintText);

//            tvGenderTag.setTextColor(getResources().getColor(R.color.colorTransparentWhite));
//            tvBirthdayTag.setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            tvErrorGender.setVisibility(View.INVISIBLE);
            tvErrorBirthday.setVisibility(View.INVISIBLE);
//            findViewById(R.id.viewGender).setBackgroundColor(getResources().getColor(R.color.colorTransparentWhite));
//            findViewById(R.id.viewBirthday).setBackgroundColor(getResources().getColor(R.color.colorTransparentWhite));

            sName = edtRegisterName.getText().toString().trim();
            sEmail = edtRegisterEmail.getText().toString().trim();
            sPassword = edtRegisterPassword.getText().toString().trim();
            String email_validate = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            if (sName.isEmpty()) {
                tilRegisterName.setError("Name required!");
                tilRegisterName.setHintTextAppearance(R.style.MyErrorText);
                tilRegisterName.setErrorTextAppearance(R.style.MyErrorText);
                tilRegisterName.requestFocus();
            }
            if (sEmail.isEmpty()) {
                tilRegisterEmail.setError("Email required!");
                tilRegisterEmail.setHintTextAppearance(R.style.MyErrorText);
                tilRegisterEmail.requestFocus();
            }
            if (!sEmail.matches(email_validate)) {
                tilRegisterEmail.setError("Email is not valid!");
                tilRegisterEmail.setHintTextAppearance(R.style.MyErrorText);
                tilRegisterEmail.requestFocus();
            }
            if (sPassword.isEmpty()) {
                tilRegisterPassword.setError("Password required!");
                tilRegisterPassword.setHintTextAppearance(R.style.MyErrorText);
                tilRegisterPassword.requestFocus();
            }
            if (sPassword.length() < 6) {
                tilRegisterPassword.setError("Password must be of at least 6 digits long!");
                tilRegisterPassword.setHintTextAppearance(R.style.MyErrorText);
                tilRegisterPassword.requestFocus();
            }
            if (sPassword.contains(" ")) {
                tilRegisterPassword.setError("Password must not contain space!");
                tilRegisterPassword.setHintTextAppearance(R.style.MyErrorText);
                tilRegisterPassword.requestFocus();
            }
            if (sGender.trim().isEmpty()) {
                tvErrorGender.setVisibility(View.VISIBLE);
//                findViewById(R.id.viewGender).setBackgroundColor(getResources().getColor(R.color.colorError));
//                tvGenderTag.setTextColor(getResources().getColor(R.color.colorError));
                /*Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Select Gender!", Snackbar.LENGTH_LONG);
                snackbar.show();*/
            }
            if (sDOB.isEmpty()) {
                tvErrorBirthday.setVisibility(View.VISIBLE);
//                findViewById(R.id.viewBirthday).setBackgroundColor(getResources().getColor(R.color.colorError));
//                tvBirthdayTag.setTextColor(getResources().getColor(R.color.colorError));
               /* Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Select Date of Birth!", Snackbar.LENGTH_LONG);
                snackbar.show();*/
            }
          /*  if (!cbTnc.isChecked()) {
                cbTnc.setTextAppearance(SignupActivity.this, R.style.MyErrorCheckbox);
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Please accept the terms and conditions!", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else {
                if (AndroidUtils.hasConnection(SignupActivity.this)) {
                    doRegister();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }*/
        } else if (v == tvTnC) {
            Intent i = new Intent(SignupActivity.this, TermsConditionsActivity.class);
            startActivity(i);
        }
    }

    private void doRegister() {
        final ProgressDialog loading = new ProgressDialog(SignupActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        API apiService = APIClient.getClient().create(API.class);
        Call<RegisterSuccess> call1 = apiService.register(sName, sEmail, sPassword, sGender, sDOB);
        call1.enqueue(new Callback<RegisterSuccess>() {

            @Override
            public void onResponse(@NonNull Call<RegisterSuccess> call, @NonNull retrofit2.Response<RegisterSuccess> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    RegisterSuccess registerSuccess = response.body();
                    String strStatus = registerSuccess.getStatus();
                    if (strStatus.equalsIgnoreCase("Success")) {
                        String strMessage = registerSuccess.getMessage();
                        RegisterData registerData = registerSuccess.getData();

                        String strStudentId = registerData.getStudentId();
                        String email = registerData.getEmail();
                        String name = registerData.getName();

                        Log.e(TAG, "strMessage : " + strMessage);
                        SessionManager sessionManager = new SessionManager(SignupActivity.this);
                        sessionManager.createLoginSession(strStudentId, email, name);
                        Intent i = new Intent(SignupActivity.this, DashBoardActivity.class);
                        i.putExtra("flag", "");
                        startActivity(i);
                        finishAffinity();
                    }

                } else {
                    Log.e(TAG, "Response Error : " + response.code());
                    try {
                        Gson gson = new Gson();
                        RegisterError registerError = gson.fromJson(response.errorBody().string(), RegisterError.class);
                        if (registerError.getMessage().getName() != null) {
                            tilRegisterName.setError(registerError.getMessage().getName());
                            tilRegisterName.setHintTextAppearance(R.style.MyErrorText);
                            tilRegisterName.requestFocus();
                        } else if (registerError.getMessage().getEmail() != null) {
                            tilRegisterEmail.setError(registerError.getMessage().getEmail());
                            tilRegisterEmail.setHintTextAppearance(R.style.MyErrorText);
                            tilRegisterEmail.requestFocus();
                        } else if (registerError.getMessage().getPassword() != null) {
                            tilRegisterPassword.setError(registerError.getMessage().getPassword());
                            tilRegisterPassword.setHintTextAppearance(R.style.MyErrorText);
                            tilRegisterPassword.requestFocus();
                        } else if (registerError.getMessage().getGender() != null) {
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, registerError.getMessage().getGender(), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else if (registerError.getMessage().getDob() != null) {
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, registerError.getMessage().getDob(), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterSuccess> call, @NonNull Throwable t) {
                loading.dismiss();
                t.printStackTrace();
            }
        });
    }

}
