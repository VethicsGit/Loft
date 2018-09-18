
package model.Stripe.MakePaymentSuccess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Outcome {

    @SerializedName("network_status")
    @Expose
    private String networkStatus;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("risk_level")
    @Expose
    private String riskLevel;
    @SerializedName("seller_message")
    @Expose
    private String sellerMessage;
    @SerializedName("type")
    @Expose
    private String type;

    public String getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(String networkStatus) {
        this.networkStatus = networkStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getSellerMessage() {
        return sellerMessage;
    }

    public void setSellerMessage(String sellerMessage) {
        this.sellerMessage = sellerMessage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
