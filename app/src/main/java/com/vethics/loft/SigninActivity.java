package com.vethics.loft;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import customview.FontChangeCrawler;
import model.CheckSocialLogin.CheckSocialSuccess;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.API;
import utils.APIClient;
import utils.SessionManager;

public class SigninActivity extends AppCompatActivity  {
   /* Button btnSigninFacebook, btnSigninGoogle, btnSigninEmail;
    TextView tvDontHaveAccount;

    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 0;
    private final String TAG = getClass().getCanonicalName();
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;

    String strName, strEmail, strGender, strDob, strProfilePic, strMediaType, strMediaId;
*/


    private ViewPager view_pager;
    private LinearLayout layoutDots;

    private TextView[] mDots;
    private Button skip;
    private SlideAdapter slideAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        view_pager = findViewById(R.id.view_pager);
        layoutDots = findViewById(R.id.layoutDots);

        slideAdapter = new SlideAdapter(this);
        view_pager.setAdapter(slideAdapter);

        skip = findViewById(R.id.btnskip);

        addDotsIndicator(0);

        view_pager.addOnPageChangeListener(viewlistner);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(SigninActivity.this,EmailSigninActivity.class);
                startActivity(i);
            }
        });



    }



    public void addDotsIndicator(int position) {
        mDots = new TextView[3];

        layoutDots.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.white));

            layoutDots.addView(mDots[i]);

        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.grey));
        }

    }


    ViewPager.OnPageChangeListener viewlistner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(i);

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }

//        callbackManager = CallbackManager.Factory.create();
     /*   setContentView(R.layout.activity_signin);
      *//*  btnSigninFacebook = (Button) findViewById(R.id.btn_signin_facebook);
        btnSigninGoogle = (Button) findViewById(R.id.btn_signin_google);*//*
        btnSigninEmail = (Button) findViewById(R.id.btn_signin_email);
        tvDontHaveAccount = (TextView) findViewById(R.id.tv_sign_up);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "font/avenir_lt_std_35_light.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        Typeface face2 = Typeface.createFromAsset(getAssets(),
                "font/breeserif_regular.ttf");
        btnSigninEmail.setTypeface(face2);
      *//*  btnSigninFacebook.setTypeface(face2);
        btnSigninGoogle.setTypeface(face2);
*//*
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestEmail()
                .requestProfile()
                .build();
     *//*   mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
*//*
        *//*mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this *//**//* FragmentActivity *//**//*,0, this *//**//* OnConnectionFailedListener *//**//*)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();*//*

        //updateUI(account);

        facebookLogin();

//        btnSigninFacebook.setOnClickListener(this);
//        btnSigninGoogle.setOnClickListener(this);
        btnSigninEmail.setOnClickListener(this);
        tvDontHaveAccount.setOnClickListener(this);
    }

    private void facebookLogin() {
        boolean loggedIn = AccessToken.getCurrentAccessToken() == null;

        if (loggedIn) {
            Toast.makeText(this, "Not Logged In", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "Access Token : " + AccessToken.getCurrentAccessToken().getToken());
            Intent i = new Intent(SigninActivity.this, DashBoardActivity.class);
            startActivity(i);
            finish();
        }

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String accessToken = loginResult.getAccessToken()
                        .getToken();
                Log.e(TAG, "accessToken : " + accessToken);

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {

                                Log.e(TAG, "LoginActivity : " + response.toString());
                                try {
                                    String fbId = object.getString("id");
                                    try {
                                        URL profile_pic = new URL(
                                                "http://graph.facebook.com/" + fbId + "/picture?type=large");
                                        Log.e(TAG, "profile_pic : " + profile_pic);
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                    String fbName = object.getString("name");
                                    String fbEmail = object.getString("email");
                                    String fbGender = object.getString("gender");
                                    String fbBirthday = object.getString("birthday");

                                    strName = fbName;
                                    strEmail = fbEmail;
                                    strGender = fbGender;

                                    if (strGender.equalsIgnoreCase("male")) {
                                        strGender = "m";
                                    } else if (strGender.equalsIgnoreCase("female")) {
                                        strGender = "f";
                                    } else {
                                        strGender = "";
                                    }

                                    SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                                    Date newDate = null;
                                    try {
                                        newDate = spf.parse(fbBirthday);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    spf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
                                    fbBirthday = spf.format(newDate);

                                    strDob = fbBirthday;

                                    strProfilePic = "http://graph.facebook.com/" + fbId + "/picture?type=large";
                                    strMediaType = "fb";
                                    strMediaId = fbId;
                                    checkSocialLogin();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email, gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(SigninActivity.this, "Cancelled!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(SigninActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    *//* Google sign in *//*
   *//* private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }*//*

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount acct) {
        // GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            Log.e("account", acct.toString());
            String gName = acct.getDisplayName();
            String gGivenName = acct.getGivenName();
            String gFamilyName = acct.getFamilyName();
            String gEmail = acct.getEmail();
            String gId = acct.getId();
            Uri gPhoto = acct.getPhotoUrl();

            if (gPhoto != null) {
                strProfilePic = gPhoto.toString();
            } else {
                strProfilePic = "";
            }

            for (Scope scope : acct.getGrantedScopes()) {
                Log.e(TAG, scope.toString());
            }

            Log.e(TAG, "Google Login CheckSocialSuccessData : " + gEmail + " " + gFamilyName + " " + gGivenName + " " + gName + " " + gId + " " + gPhoto);

            strName = gName;
            strEmail = gEmail;
            strGender = "";
            strDob = "";
            strMediaType = "gmail";
            strMediaId = gId;
            checkSocialLogin();
        }
    }

    private void checkSocialLogin() {
        final ProgressDialog progressDialog = new ProgressDialog(SigninActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        API apiService = APIClient.getClient().create(API.class);
        Call<CheckSocialSuccess> call = apiService.check_social_login(strMediaType, strMediaId);
        call.enqueue(new Callback<CheckSocialSuccess>() {

            @Override
            public void onResponse(Call<CheckSocialSuccess> call, Response<CheckSocialSuccess> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    Log.e(TAG, "Response Success : " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getData()));
                    CheckSocialSuccess checkSocialSuccess = response.body();
                    if (checkSocialSuccess.getStatus().equalsIgnoreCase("Success")) {
                        socialLogin();
                    }
                } else {
                    socialLogin();
                    Log.e(TAG, "Response Error : " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<CheckSocialSuccess> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void socialLogin() {
        final ProgressDialog progressDialog = new ProgressDialog(SigninActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        API apiService = APIClient.getClient().create(API.class);
        Log.e(TAG, "Dob : " + strDob);

        Call<CheckSocialSuccess> call = apiService.social_login(strMediaType, strMediaId, strName, strEmail, strGender, strDob, strProfilePic);
        call.enqueue(new Callback<CheckSocialSuccess>() {

            @Override
            public void onResponse(Call<CheckSocialSuccess> call, Response<CheckSocialSuccess> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    Log.e(TAG, "Response Success : " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getData()));
                    CheckSocialSuccess checkSocialSuccess = response.body();
                    if (checkSocialSuccess.getStatus().equalsIgnoreCase("Success")) {
                        String student_id = checkSocialSuccess.getData().getStudentId();
                        String email = checkSocialSuccess.getData().getEmail();
                        String name = checkSocialSuccess.getData().getName();

                        SessionManager sessionManager = new SessionManager(SigninActivity.this);
                        sessionManager.createLoginSession(student_id, email,name);

                        Intent i = new Intent(SigninActivity.this, DashBoardActivity.class);
                        startActivity(i);

                        boolean loggedIn = AccessToken.getCurrentAccessToken() == null;

                        if (!loggedIn) {
                            LoginManager.getInstance().logOut();
                        }
                        mGoogleSignInClient.signOut()
                                .addOnCompleteListener(SigninActivity.this, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                    }
                                });
                    }
                } else {
                    Log.e(TAG, "Response Error : " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    *//*try {
                        Gson gson = new Gson();
                        CheckSocialError userDatailsError = gson.fromJson(response.errorBody().string(), CheckSocialError.class);
                        Toast.makeText(SigninActivity.this, userDatailsError.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*//*
                }
            }

            @Override
            public void onFailure(Call<CheckSocialSuccess> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sign_up:
                Intent i = new Intent(this, SignupActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;

           *//* case R.id.btn_signin_google:
                signIn();
                break;

            case R.id.btn_signin_facebook:
                //Facebook sign in
                LoginManager.getInstance().logInWithReadPermissions(
                        this,
                        Arrays.asList("user_photos", "email", "user_birthday", "public_profile")
                );
                *//**//*Intent i1 = new Intent(this, DashBoardActivity.class);
                startActivity(i1);*//**//*
                break;*//*

            case R.id.btn_signin_email:
                Intent i2 = new Intent(this, EmailSigninActivity.class);
                startActivity(i2);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // CourseListingResult returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

            // G+
            *//*if (mGoogleApiClient.hasConnectedApi(Plus.API)) {
                Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                if (person != null) {
                    Log.i(TAG, "--------------------------------");
                    Log.i(TAG, "Display Name: " + person.getDisplayName());
                    Log.i(TAG, "Gender: " + person.getGender());
                    Log.i(TAG, "About Me: " + person.getAboutMe());
                    Log.i(TAG, "Birthday: " + person.getBirthday());
                    Log.i(TAG, "Current Location: " + person.getCurrentLocation());
                    Log.i(TAG, "Language: " + person.getLanguage());
                } else {
                    Log.e(TAG, "Error!");
                }
            } else {
                Log.e(TAG, "Google+ not connected");
            }*//*
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }*/
    };
}
