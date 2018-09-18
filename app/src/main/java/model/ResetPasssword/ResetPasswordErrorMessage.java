
package model.ResetPasssword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPasswordErrorMessage {

    @SerializedName("verification_token")
    @Expose
    private String verificationToken;

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

}
