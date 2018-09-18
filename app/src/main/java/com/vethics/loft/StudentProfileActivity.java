package com.vethics.loft;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import adapter.PlaceAutocompleteAdapter;
import model.UpdateProfile.UpdateProfileData;
import model.UpdateProfile.UpdateProfileError;
import model.UpdateProfile.UpdateProfileSuccess;
import model.UserDetails.UserDetailsData;
import model.UserDetails.UserDetailsError;
import model.UserDetails.UserDetailsSuccess;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import utils.API;
import utils.APIClient;
import utils.AndroidUtils;
import utils.FileUtils;
import utils.SessionManager;

public class StudentProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivProfileImage, ivProfilePicEdit;
    TextInputLayout tilProfileName, tilProfileEmail, tilProfileAddress, tilProfileCity;
    Button btnProfileUpdate;
    RadioGroup rgGender;
    RadioButton rbMale, rbFemale;
    TextView tvProfileName, tvSelectBirthday;
    String sGender = "", sDOB = "", sName, sEmail;
    EditText edtProfileName, edtProfileEmail, edtProfileAddress;
    SharedPreferences spLogin;
    String strStudentId = "", strProfilePath, strAddress, strCity, strState, strCountry;

    protected GeoDataClient mGeoDataClient;

    private PlaceAutocompleteAdapter mAdapter;

    private AutoCompleteTextView actCIty;
    private static final int RESULT_GALLERY = 0;
    private static final int RESULT_CAMERA = 1;

    private static final int GALLERY_REQUEST_CODE = 2;
    private static final int CAMERA_REQUEST_CODE = 3;
    final int CROP_PIC = 5;

    final String[] items = {"Choose from gallery", "Capture from camera"};
    private final String TAG = getClass().getCanonicalName();

    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private AlertDialog.Builder builder;
    private String uri;
    private String ProfileImage;
    File mPhotoFile;

    /*private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));*/
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        btnProfileUpdate = (Button) findViewById(R.id.btn_profile_update);

        spLogin = getSharedPreferences(SessionManager.PREF_NAME, Context.MODE_PRIVATE);
        strStudentId = spLogin.getString(SessionManager.KEY_ID, "");

        if (AndroidUtils.hasConnection(StudentProfileActivity.this)) {
            getUserDetails();
        } else {
            finish();
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_app_menu_overflow));

        mGeoDataClient = Places.getGeoDataClient(this, null);

        ivProfileImage = (ImageView) findViewById(R.id.iv_profile_image);
        ivProfilePicEdit = (ImageView) findViewById(R.id.iv_profile_pic_edit);
        tvProfileName = (TextView) findViewById(R.id.tv_profile_name);

        tilProfileName = (TextInputLayout) findViewById(R.id.til_profile_name);
        tilProfileEmail = (TextInputLayout) findViewById(R.id.til_profile_email);
        tilProfileAddress = (TextInputLayout) findViewById(R.id.til_profile_address);
        tilProfileCity = (TextInputLayout) findViewById(R.id.til_profile_city);

        edtProfileName = (EditText) findViewById(R.id.edt_profile_name);
        edtProfileEmail = (EditText) findViewById(R.id.edt_profile_email);
        edtProfileAddress = (EditText) findViewById(R.id.edt_profile_address);

        rgGender = (RadioGroup) findViewById(R.id.rg_gender);
        rbMale = (RadioButton) findViewById(R.id.rb_male);
        rbFemale = (RadioButton) findViewById(R.id.rb_female);

        tvSelectBirthday = (TextView) findViewById(R.id.tv_profile_birthdate);

        final Drawable drawableChecked = AppCompatResources.getDrawable(this, R.drawable.ic_radio_active);
        final Drawable drawableUnchecked = AppCompatResources.getDrawable(this, R.drawable.ic_radio_inactive);
        drawableChecked.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        drawableUnchecked.setColorFilter(getResources().getColor(R.color.colorDarkGray), PorterDuff.Mode.SRC_ATOP);

        rbMale.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableUnchecked, null, null, null);
        rbFemale.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableUnchecked, null, null, null);

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_male) {
                    sGender = "m";
                    rbMale.setTextColor(getResources().getColor(R.color.colorAccent));
                    rbMale.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableChecked, null, null, null);
                    rbFemale.setTextColor(getResources().getColor(R.color.colorDarkGray));
                    rbFemale.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableUnchecked, null, null, null);
                } else if (checkedId == R.id.rb_female) {
                    sGender = "f";
                    rbFemale.setTextColor(getResources().getColor(R.color.colorAccent));
                    rbFemale.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableChecked, null, null, null);
                    rbMale.setTextColor(getResources().getColor(R.color.colorDarkGray));
                    rbMale.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableUnchecked, null, null, null);
                }
            }
        });

        actCIty = (AutoCompleteTextView) findViewById(R.id.auto_complete_city);
        // Register a listener that receives callbacks when a suggestion has been selected
        actCIty.setOnItemClickListener(mAutocompleteClickListener);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();

        // Set up the adapter that will retrieve suggestions from the Places Geo ChangePasswordData Client.
        mAdapter = new PlaceAutocompleteAdapter(this, mGeoDataClient, null, typeFilter);
        actCIty.setAdapter(mAdapter);

        btnProfileUpdate.setOnClickListener(this);
        tvSelectBirthday.setOnClickListener(this);
        btnProfileUpdate.setOnClickListener(this);
        ivProfilePicEdit.setOnClickListener(this);
    }

    private void getUserDetails() {
        final ProgressDialog loading = new ProgressDialog(StudentProfileActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        if (strStudentId.isEmpty()) {
            loading.dismiss();
            Toast.makeText(this, "Invalid User!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        API apiService = APIClient.getClient().create(API.class);

        Call<UserDetailsSuccess> call1 = apiService.user_details(strStudentId);//strVerificationCode, strNewPassword, strConfirmPassword);
        call1.enqueue(new Callback<UserDetailsSuccess>() {

            @Override
            public void onResponse(@NonNull Call<UserDetailsSuccess> call, @NonNull retrofit2.Response<UserDetailsSuccess> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    Log.e(TAG, "Response Success : " + new GsonBuilder().setPrettyPrinting().create().toJson(response));

                    UserDetailsSuccess userDetails = response.body();
                    String strStatus = userDetails.getStatus();
                    if (strStatus.equalsIgnoreCase("Success")) {
                        String strMessage = userDetails.getMessage();
                        UserDetailsData userDetailsData = userDetails.getData();
                        String name = userDetailsData.getName();
                        String email = userDetailsData.getEmail();
                        String dob = userDetailsData.getDateOfBirth();
                        String gender = userDetailsData.getGender();
                        strProfilePath = userDetailsData.getProfilePic();//"http://demo.vethics.in/loft/uploads/images/profile_picture/";
                        strAddress = userDetailsData.getAddress();
                        strCity = userDetailsData.getCity();
                        strState = userDetailsData.getState();
                        strCountry = userDetailsData.getCountry();

                        if (!name.isEmpty()) {
                            tvProfileName.setText(AndroidUtils.wordFirstCap(name));
                            edtProfileName.setText(AndroidUtils.wordFirstCap(name));
                        }
                        if (!email.isEmpty()) {
                            edtProfileEmail.setText(email);
                        }

                        if (!strAddress.isEmpty()) {
                            edtProfileAddress.setText(AndroidUtils.wordFirstCap(strAddress));
                        }

                        if (!strCity.isEmpty() && !strState.isEmpty() && !strCountry.isEmpty()) {
                            actCIty.setText(AndroidUtils.wordFirstCap(strCity) + ", " + strState + ", " + strCountry);
                        }

                        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        Date newDate = null;
                        try {
                            newDate = spf.parse(dob);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        spf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
                        dob = spf.format(newDate);

                        tvSelectBirthday.setText(dob);

                        spf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
                        dob = spf.format(newDate);
                        sDOB = dob;

                        sGender = gender;
                        if (gender.equalsIgnoreCase("m")) {
                            rbMale.setChecked(true);
                            rbFemale.setChecked(false);
                            //strProfilePath = profileImage + "profile_male.jpg";

                        } else if (gender.equalsIgnoreCase("f")) {
                            rbMale.setChecked(false);
                            rbFemale.setChecked(true);
                            //strProfilePath = profileImage + "profile_female.jpg";
                        }

                        if (strProfilePath.isEmpty()) {
                            Glide.with(StudentProfileActivity.this).load(strProfilePath).into(ivProfileImage);
                        } else {
                            Glide.with(StudentProfileActivity.this).load(strProfilePath).into(ivProfileImage);
                        }
                        Log.e("Success : ", "Success");
                    }

                } else {
                    Log.e(TAG, "Response Error : " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    try {
                        Gson gson = new Gson();
                        UserDetailsError userDatailsError = gson.fromJson(response.errorBody().string(), UserDetailsError.class);
                        Toast.makeText(StudentProfileActivity.this, userDatailsError.getMessage().getUserId(), Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserDetailsSuccess> call, @NonNull Throwable t) {
                loading.dismiss();
                t.printStackTrace();
                Log.e("userDetailsData", t.getMessage());
            }
        });
    }

    private void doUpdateProfile() {
        final ProgressDialog loading = new ProgressDialog(StudentProfileActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        API apiService = APIClient.getClient().create(API.class);
        // Log.e("File Name : ", mPhotoFile.getName());
        RequestBody reqFile;
        MultipartBody.Part body;
        if (mPhotoFile != null) {
            reqFile = RequestBody.create(MediaType.parse("image/*"), mPhotoFile);
            body = MultipartBody.Part.createFormData("file", mPhotoFile.getName(), reqFile);
        } else {
            reqFile = RequestBody.create(null, new byte[0]);
            body = MultipartBody.Part.createFormData("", "", reqFile);
        }

        // RequestBody name = RequestBody.create(MediaType.parse("text/plain"), mPhotoFile.getName());
        RequestBody rbStudentId = RequestBody.create(MediaType.parse("text/plain"), strStudentId);
        RequestBody rbName = RequestBody.create(MediaType.parse("text/plain"), sName);
        RequestBody rbGender = RequestBody.create(MediaType.parse("text/plain"), sGender);
        RequestBody rbDOB = RequestBody.create(MediaType.parse("text/plain"), sDOB);
        RequestBody rbAddress = RequestBody.create(MediaType.parse("text/plain"), strAddress);
        RequestBody rbCity = RequestBody.create(MediaType.parse("text/plain"), strCity);
        RequestBody rbCountry = RequestBody.create(MediaType.parse("text/plain"), strCountry);
        RequestBody rbState = RequestBody.create(MediaType.parse("text/plain"), strState);
        RequestBody rbEmail = RequestBody.create(MediaType.parse("text/plain"), sEmail);

        Call<UpdateProfileSuccess> call1 = apiService.update_profile(strStudentId, rbName, rbGender, rbDOB, rbAddress, rbCity, rbCountry, rbState, rbEmail, body);
        call1.enqueue(new Callback<UpdateProfileSuccess>() {

            @Override
            public void onResponse(@NonNull Call<UpdateProfileSuccess> call, @NonNull retrofit2.Response<UpdateProfileSuccess> response) {
                loading.dismiss();
                // Log.e("Response : ", new GsonBuilder().setPrettyPrinting().create().toJson(response));

                if (response.isSuccessful()) {
                    Log.e("Response Success : ", new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getData()));

                    UpdateProfileSuccess updateProfileSuccess = response.body();
                    String strStatus = updateProfileSuccess.getStatus();
                    if (strStatus.equalsIgnoreCase("Success")) {
                        String strMessage = updateProfileSuccess.getMessage();
                        UpdateProfileData updateProfileData = updateProfileSuccess.getData();
                        getUserDetails();
                        //Log.e("strMessage", strMessage);
                    }

                } else {
                    Log.e("Response Error:", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));

                    try {
                        Gson gson = new Gson();
                        UpdateProfileError registerError = gson.fromJson(response.errorBody().string(), UpdateProfileError.class);
                        /*Log.e("error :", "" + registerError.getMessage().getUserId() + "\n" + registerError.getMessage().getAddress() + "\n" + registerError.getMessage().getCity() + "\n" + registerError.getMessage().getCountry() + "\n" + registerError.getMessage().getDateOfBirth() + "\n" + registerError.getMessage().getEmail() + "\n" + registerError.getMessage().getFirstName() + "\n" + registerError.getMessage().getGender() + "\n" + registerError.getMessage().getLanguageId() + "\n" + registerError.getMessage().getLastName() + "\n" + registerError.getMessage().getState() + "\n");*/

                        if (registerError.getMessage().getStudentId() != null) {
                            tilProfileName.setError(registerError.getMessage().getStudentId());
                            tilProfileName.setHintTextAppearance(R.style.MyErrorText);
                            tilProfileName.requestFocus();
                        } /*else if (registerError.getMessage().getEmail() != null) {
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
                        }*/
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateProfileSuccess> call, @NonNull Throwable t) {
                loading.dismiss();
                t.printStackTrace();
            }
        });

    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            /* Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title. */

            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.i(TAG, "Autocomplete item selected: " + primaryText);

             /* Issue a request to the Places Geo ChangePasswordData Client to retrieve a Place object with
             additional details about the place. */

            Task<PlaceBufferResponse> placeResult = mGeoDataClient.getPlaceById(placeId);
            placeResult.addOnCompleteListener(mUpdatePlaceDetailsCallback);
            Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
        }
    };

    private OnCompleteListener<PlaceBufferResponse> mUpdatePlaceDetailsCallback
            = new OnCompleteListener<PlaceBufferResponse>() {
        @Override
        public void onComplete(Task<PlaceBufferResponse> task) {
            try {
                PlaceBufferResponse places = task.getResult();

                // Get the Place object from the buffer.
                final Place place = places.get(0);

                // Format details of the place for display and show it in a TextView.
                actCIty.setText(place.getAddress());
                StringTokenizer tokens = new StringTokenizer(place.getAddress().toString(), ",");
                if (tokens.hasMoreTokens())
                    strCity = tokens.nextToken();
                if (tokens.hasMoreTokens())
                    strState = tokens.nextToken();
                if (tokens.hasMoreTokens())
                    strCountry = tokens.nextToken();
                actCIty.dismissDropDown();
                AndroidUtils.hideSoftKeyboard(StudentProfileActivity.this);
                Log.e(TAG, "Place details received : " + strCity + "," + strState + "," + strCountry);

                places.release();
            } catch (RuntimeRemoteException e) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete.", e);
                return;
            }
        }
    };

    private Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                       CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }

    public static Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
            throws FileNotFoundException {

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                StudentProfileActivity.this.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_change_password, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_change_password) {
            Intent i = new Intent(StudentProfileActivity.this, ChangePasswordActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
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
                    sDOB = sendsdf.format(myCalendar.getTime());
                    Log.e("date", sDOB);
                }
            };

            DatePickerDialog datePickerDialog = new DatePickerDialog(StudentProfileActivity.this, R.style.DialogTheme, date, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            datePickerDialog.show();
        } else if (v == btnProfileUpdate) {
            AndroidUtils.hideSoftKeyboard(StudentProfileActivity.this);
            tilProfileName.setError(null);
            tilProfileEmail.setError(null);
            tilProfileAddress.setError(null);
            tilProfileCity.setError(null);
            tilProfileName.setHintTextAppearance(R.style.MyHintText1);
            tilProfileEmail.setHintTextAppearance(R.style.MyHintText1);
            tilProfileAddress.setHintTextAppearance(R.style.MyHintText1);
            tilProfileCity.setHintTextAppearance(R.style.MyHintText1);

            /*tvGenderTag.setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            tvBirthdayTag.setTextColor(getResources().getColor(R.color.colorTransparentWhite));*/

            sName = edtProfileName.getText().toString().trim();
            sEmail = edtProfileEmail.getText().toString().trim();
            strAddress = edtProfileAddress.getText().toString().trim();
            String city = actCIty.getText().toString().trim();
            String email_validate = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            if (sName.isEmpty()) {
                tilProfileName.setError("Name required!");
                tilProfileName.setHintTextAppearance(R.style.MyErrorText);
                tilProfileName.requestFocus();
            } else if (sEmail.isEmpty()) {
                tilProfileEmail.setError("Email required!");
                tilProfileEmail.setHintTextAppearance(R.style.MyErrorText);
                tilProfileEmail.requestFocus();
            } else if (!sEmail.matches(email_validate)) {
                tilProfileEmail.setError("Email is not valid!");
                tilProfileEmail.setHintTextAppearance(R.style.MyErrorText);
                tilProfileEmail.requestFocus();
            } else if (strAddress.isEmpty()) {
                tilProfileAddress.setError("Address required!");
                tilProfileAddress.setHintTextAppearance(R.style.MyErrorText);
                tilProfileAddress.requestFocus();
            } else if (city.isEmpty()) {
                tilProfileCity.setError("City required!");
                tilProfileCity.setHintTextAppearance(R.style.MyErrorText);
                tilProfileCity.requestFocus();
            } else if (sGender.trim().isEmpty()) {
                //tvGenderTag.setTextColor(Color.parseColor("#ff2a2a"));
                Snackbar snackbar = Snackbar
                        .make(btnProfileUpdate, "Select Gender!", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else if (sDOB.isEmpty()) {
                //tvBirthdayTag.setTextColor(Color.parseColor("#ff2a2a"));
                Snackbar snackbar = Snackbar
                        .make(btnProfileUpdate, "Select Date of Birth!", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else {
                if (AndroidUtils.hasConnection(StudentProfileActivity.this)) {
                    StringTokenizer tokens = new StringTokenizer(city, ",");
                    if (tokens.hasMoreTokens())
                        strCity = tokens.nextToken().trim();
                    if (tokens.hasMoreTokens())
                        strState = tokens.nextToken().trim();
                    if (tokens.hasMoreTokens())
                        strCountry = tokens.nextToken().trim();
                    doUpdateProfile();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(btnProfileUpdate, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        } else if (v == ivProfilePicEdit) {
            builder = new AlertDialog.Builder(StudentProfileActivity.this);
            builder.setTitle("Select your profile picture");

            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (items[which].equalsIgnoreCase("Choose from gallery")) {
                        if (ContextCompat.checkSelfPermission(StudentProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(galleryIntent, RESULT_GALLERY);
                        } else {
                            ActivityCompat.requestPermissions(StudentProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);
                        }
                    } else if (items[which].equalsIgnoreCase("Capture from camera")) {
                        if (ContextCompat.checkSelfPermission(StudentProfileActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            // cameraIntent.putExtra("return data", true);
                            startActivityForResult(cameraIntent, RESULT_CAMERA);
                        } else {
                            ActivityCompat.requestPermissions(StudentProfileActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_REQUEST_CODE);
                        }
                    }

                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(cameraIntent, RESULT_CAMERA);
                } else {
                    Toast.makeText(StudentProfileActivity.this, "Permission Denied!", Toast.LENGTH_LONG).show();
                }
                break;
            case GALLERY_REQUEST_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(galleryIntent, RESULT_GALLERY);
                } else {
                    Toast.makeText(StudentProfileActivity.this, "Permission Denied!", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            mPhotoFile = FileUtils.getFile(this, data.getData());
            uri = String.valueOf(data.getData());
            Log.e("uri : ", uri);

            if (requestCode == RESULT_GALLERY) {
                try {
                    //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(uri));
                    ivProfileImage.setImageBitmap(decodeUri(StudentProfileActivity.this, data.getData(), 100));
                    //ProfileImage = AndroidUtils.BitMapToString(decodeUri(StudentProfileActivity.this, Uri.parse(uri), 100));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == RESULT_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                mPhotoFile = FileUtils.getFile(StudentProfileActivity.this, getImageUri(StudentProfileActivity.this, thumbnail));
                ivProfileImage.setImageBitmap(thumbnail);
                Log.e("reached", "activity results");
            }
        }
    }

}
