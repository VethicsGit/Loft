
package model.Stripe.MakePaymentSuccess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StripeMakePaymentSuccess {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("object")
    @Expose
    private String object;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("amount_refunded")
    @Expose
    private Integer amountRefunded;
    @SerializedName("application")
    @Expose
    private String application;
    @SerializedName("application_fee")
    @Expose
    private String applicationFee;
    @SerializedName("balance_transaction")
    @Expose
    private String balanceTransaction;
    @SerializedName("captured")
    @Expose
    private Boolean captured;
    @SerializedName("created")
    @Expose
    private Integer created;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("dispute")
    @Expose
    private String dispute;
    @SerializedName("failure_code")
    @Expose
    private String failureCode;
    @SerializedName("failure_message")
    @Expose
    private String failureMessage;
    @SerializedName("fraud_details")
    @Expose
    private FraudDetails fraudDetails;
    @SerializedName("invoice")
    @Expose
    private String invoice;
    @SerializedName("livemode")
    @Expose
    private Boolean livemode;
    @SerializedName("metadata")
    @Expose
    private Metadata metadata;
    @SerializedName("on_behalf_of")
    @Expose
    private String onBehalfOf;
    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("outcome")
    @Expose
    private Outcome outcome;
    @SerializedName("paid")
    @Expose
    private Boolean paid;
    @SerializedName("receipt_email")
    @Expose
    private String receiptEmail;
    @SerializedName("receipt_number")
    @Expose
    private String receiptNumber;
    @SerializedName("refunded")
    @Expose
    private Boolean refunded;
    @SerializedName("refunds")
    @Expose
    private Refunds refunds;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("shipping")
    @Expose
    private String shipping;
    @SerializedName("source")
    @Expose
    private Source source;
    @SerializedName("source_transfer")
    @Expose
    private String sourceTransfer;
    @SerializedName("statement_descriptor")
    @Expose
    private String statementDescriptor;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("transfer_group")
    @Expose
    private String transferGroup;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getString() {
        return object;
    }

    public void setString(String object) {
        this.object = object;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmountRefunded() {
        return amountRefunded;
    }

    public void setAmountRefunded(Integer amountRefunded) {
        this.amountRefunded = amountRefunded;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getApplicationFee() {
        return applicationFee;
    }

    public void setApplicationFee(String applicationFee) {
        this.applicationFee = applicationFee;
    }

    public String getBalanceTransaction() {
        return balanceTransaction;
    }

    public void setBalanceTransaction(String balanceTransaction) {
        this.balanceTransaction = balanceTransaction;
    }

    public Boolean getCaptured() {
        return captured;
    }

    public void setCaptured(Boolean captured) {
        this.captured = captured;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDispute() {
        return dispute;
    }

    public void setDispute(String dispute) {
        this.dispute = dispute;
    }

    public String getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public FraudDetails getFraudDetails() {
        return fraudDetails;
    }

    public void setFraudDetails(FraudDetails fraudDetails) {
        this.fraudDetails = fraudDetails;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public Boolean getLivemode() {
        return livemode;
    }

    public void setLivemode(Boolean livemode) {
        this.livemode = livemode;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getOnBehalfOf() {
        return onBehalfOf;
    }

    public void setOnBehalfOf(String onBehalfOf) {
        this.onBehalfOf = onBehalfOf;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public String getReceiptEmail() {
        return receiptEmail;
    }

    public void setReceiptEmail(String receiptEmail) {
        this.receiptEmail = receiptEmail;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Boolean getRefunded() {
        return refunded;
    }

    public void setRefunded(Boolean refunded) {
        this.refunded = refunded;
    }

    public Refunds getRefunds() {
        return refunds;
    }

    public void setRefunds(Refunds refunds) {
        this.refunds = refunds;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getSourceTransfer() {
        return sourceTransfer;
    }

    public void setSourceTransfer(String sourceTransfer) {
        this.sourceTransfer = sourceTransfer;
    }

    public String getStatementDescriptor() {
        return statementDescriptor;
    }

    public void setStatementDescriptor(String statementDescriptor) {
        this.statementDescriptor = statementDescriptor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransferGroup() {
        return transferGroup;
    }

    public void setTransferGroup(String transferGroup) {
        this.transferGroup = transferGroup;
    }

}
