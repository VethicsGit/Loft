package com.vethics.loft;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputListener;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.view.CardNumberEditText;
import com.stripe.android.view.ExpiryDateEditText;
import com.stripe.android.view.StripeEditText;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import model.CourseEnroll.CourseEnrollSuccess;
import retrofit2.Call;
import retrofit2.Callback;
import utils.API;
import utils.APIClient;
import utils.SessionManager;

public class StripePaymentActivity extends AppCompatActivity {
    private static final int REQUEST_SCAN = 100;
    private static final int REQUEST_AUTOTEST = 101;
    SharedPreferences spLogin;
    CardNumberEditText cardNumberEditText;

    Button btnCardSubmit;
    TextView tvPayCourseName;
    ImageView ivScanCard;
    private final String TAG = getClass().getCanonicalName();
    private String strCoursePrice, strCourseName, strCourseId, strStudentId, strStudentEmail, strStudentName;
    private CardInputListener mCardInputListener;
    ConstraintLayout constraintLayout;
    CardInputWidget mCardInputWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        constraintLayout = (ConstraintLayout) findViewById(R.id.main_payment_layout);

        Intent i = getIntent();
        strCourseId = i.getExtras().getString("courseid");
        strCourseName = i.getExtras().getString("coursename");
        strCoursePrice = i.getExtras().getString("courseprice");

        spLogin = getSharedPreferences(SessionManager.PREF_NAME, Context.MODE_PRIVATE);
        strStudentId = spLogin.getString(SessionManager.KEY_ID, "");
        strStudentEmail = spLogin.getString(SessionManager.KEY_EMAIL, "");
        strStudentName = spLogin.getString(SessionManager.KEY_NAME, "");
        if (strCoursePrice.equalsIgnoreCase("0")) {
            constraintLayout.setVisibility(View.GONE);
            makePayment("");
        } else {
            constraintLayout.setVisibility(View.VISIBLE);
        }

        tvPayCourseName = (TextView) findViewById(R.id.tv_pay_coursename);
        ivScanCard = (ImageView) findViewById(R.id.iv_scan_card);
        cardNumberEditText = (CardNumberEditText) findViewById(R.id.cardNumberEditText);
        btnCardSubmit = (Button) findViewById(R.id.btnCardSubmit);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(StripePaymentActivity.this, ConfirmEnrollActivity.class);
                startActivity(i);*/
                finish();
                //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        tvPayCourseName.setText(strCourseName);
        btnCardSubmit.setText("Pay \u20b9" + strCoursePrice);
        btnCardSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

        ivScanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StripePaymentActivity.this, CardIOActivity.class)
                        .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true)
                        .putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, true)
                        .putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true)
                        .putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, true)
                        .putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, "en")
                        .putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, Color.GREEN)
                        .putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true);
                startActivityForResult(intent, REQUEST_SCAN);
            }
        });
    }

    private void startPayment() {
        mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);
        final ExpiryDateEditText expiryDateEditText = (ExpiryDateEditText) findViewById(R.id.expiryDateEditText);
        final StripeEditText stripeEditText = (StripeEditText) findViewById(R.id.stripeEditText);

        cardNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (cardNumberEditText.isCardNumberValid()) {

                } else {
                    cardNumberEditText.setShouldShowError(true);//setTextColor(getResources().getColor(R.color.colorError));
                }
            }
        });


        //Card cardToSave = new Card(cardNumberEditText.getCardNumber(), expiryDateEditText.getValidDateFields()[0], expiryDateEditText.getValidDateFields()[1], stripeEditText.getText().toString());
        Card cardToSave = mCardInputWidget.getCard();
       /* if (cardToSave == null) {
            // mErrorDialogHandler.showError("Invalid Card Data");
        }*/

        cardToSave.setCurrency("USD");

        if (!cardToSave.validateCard()) {
            // Show errors
        } else {
            Log.e("Card details : ", cardToSave.toString());
            Stripe stripe = new Stripe(this, "pk_test_f90WNmRLQN3gQGMjqHrA5lCw");
            stripe.createToken(
                    cardToSave,
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            makePayment(token.getId());
                            Log.e("Token : ", token.getId());
                        }

                        public void onError(Exception error) {
                            Toast.makeText(StripePaymentActivity.this,
                                    error.getLocalizedMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
            );
        }
    }

    private void makePayment(String token) {
        // "Bearer sk_test_G7bCXp7hRAhJomakuLSBVqM2"
        final ProgressDialog loading = new ProgressDialog(StripePaymentActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        API apiService = APIClient.getClient().create(API.class);
        Call<CourseEnrollSuccess> call1;
        if (token.isEmpty() || token.equalsIgnoreCase("") || token == null) {
            call1 = apiService.make_payment_without_token(strStudentId, strStudentEmail, strStudentName, strCourseId, strCoursePrice, strCourseName);
        } else {
            call1 = apiService.make_payment_token(token, strStudentId, strStudentEmail, strStudentName, strCourseId, strCoursePrice, strCourseName);
        }

        call1.enqueue(new Callback<CourseEnrollSuccess>() {
            @Override
            public void onResponse(@NonNull Call<CourseEnrollSuccess> call, @NonNull retrofit2.Response<CourseEnrollSuccess> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    showSuccessDialog();
                    Log.e(TAG, "Response Success : " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                } else {
                    showFailureDialog();
                    Log.e(TAG, "Response Error : " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<CourseEnrollSuccess> call, @NonNull Throwable t) {
                loading.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void showFailureDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView title = new TextView(this);
        title.setText("Failure!");
        title.setPadding(20, 20, 0, 0);
        title.setTextColor(getResources().getColor(R.color.colorError));
        title.setTextSize(18);
        title.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_app_failure), null, null, null);
        title.setCompoundDrawablePadding(10);
        builder.setCustomTitle(title)
                .setMessage("Something went terribly wrong. \n Would you like to try again?")
                //.setIcon(R.drawable.ic_app_failure)
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent i = new Intent(StripePaymentActivity.this, MyCoursesActivity.class);
                                startActivity(i);
                                finish();
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent i = new Intent(StripePaymentActivity.this, CourseDetailsActivity.class);
                        startActivity(i);
                        finish();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(getResources().getColor(R.color.colorError));
       /* Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(getResources().getColor(R.color.colorDarkGreen));*/
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView title = new TextView(this);
        title.setText("Success!");
        title.setPadding(20, 20, 0, 0);
        title.setTextColor(getResources().getColor(R.color.colorDarkGreen));
        title.setTextSize(18);
        title.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_app_success), null, null, null);
        title.setCompoundDrawablePadding(10);
        builder.setCustomTitle(title)
                .setMessage("You have successfully enrolled to " + strCourseName + ". \n Would you like to download the invoice?")
                //.setIcon(R.drawable.ic_app_success)
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent i = new Intent(StripePaymentActivity.this, MyCoursesActivity.class);
                                startActivity(i);
                                finish();
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent i = new Intent(StripePaymentActivity.this, CourseDetailsActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        /*Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.MAGENTA);*/
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(getResources().getColor(R.color.colorDarkGreen));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_SCAN || requestCode == REQUEST_AUTOTEST) && data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = scanResult.getRedactedCardNumber();
                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );
               /* if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }
                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }
                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }*/
            } else {
                resultDisplayStr = "Scan was canceled.";
            }
            Log.e("Card details : ", resultDisplayStr);
            mCardInputWidget.setCardNumber(resultDisplayStr);
            cardNumberEditText.setText(resultDisplayStr);
        }
    }
}