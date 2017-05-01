package com.matsyuk.testablecodemobius.presentation.transfer.models;

import android.support.annotation.NonNull;

/**
 * @author e.matsyuk
 */
public class TransferFilledDataModel {

    private String orgName;
    private String BIK;
    private String INN;
    private String accountNumber;
    private String amount;

    public TransferFilledDataModel(@NonNull String orgName, @NonNull String BIK, @NonNull String INN, @NonNull String accountNumber, @NonNull String amount) {
        this.orgName = orgName;
        this.BIK = BIK;
        this.INN = INN;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransferFilledDataModel{" +
                "orgName='" + orgName + '\'' +
                ", BIK='" + BIK + '\'' +
                ", INN='" + INN + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }

    @NonNull
    public String getOrgName() {
        return orgName;
    }

    @NonNull
    public String getBIK() {
        return BIK;
    }

    @NonNull
    public String getINN() {
        return INN;
    }

    @NonNull
    public String getAccountNumber() {
        return accountNumber;
    }

    @NonNull
    public String getAmount() {
        return amount;
    }
}
