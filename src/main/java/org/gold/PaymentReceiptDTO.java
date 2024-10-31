package org.gold;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentReceiptDTO {

    private String transactionTimestamp;
    private String txnRefNum;
    private String amount;
    private String currency;
    private String debtorAccountTitle;
    private String debtorAccountNumber;
    private String creditorTitle;
    private String creditorIdentifier;
    private String creditorBankDisplayName;
    private String modeOfPayment;
    private String purposeOfPayment;
    private String paymentNotes; //your message

}
