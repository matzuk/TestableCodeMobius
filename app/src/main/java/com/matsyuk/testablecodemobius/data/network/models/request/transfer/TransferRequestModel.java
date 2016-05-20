package com.matsyuk.testablecodemobius.data.network.models.request.transfer;

import android.support.annotation.Nullable;

import java.math.BigDecimal;

/**
 * @author e.matsyuk
 */
public class TransferRequestModel {

    private String orgName;
    private String BIK;
    private String INN;
    private String accountNumber;
    private BigDecimal amount;

    public TransferRequestModel(@Nullable String orgName, @Nullable String BIK, @Nullable String INN, @Nullable String accountNumber, @Nullable BigDecimal amount) {
        this.orgName = orgName;
        this.BIK = BIK;
        this.INN = INN;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    @Nullable
    public String getOrgName() {
        return orgName;
    }

    @Nullable
    public String getBIK() {
        return BIK;
    }

    @Nullable
    public String getINN() {
        return INN;
    }

    @Nullable
    public String getAccountNumber() {
        return accountNumber;
    }

    @Nullable
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "TransferRequestModel{" +
                "orgName='" + orgName + '\'' +
                ", BIK='" + BIK + '\'' +
                ", INN='" + INN + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                '}';
    }

}
