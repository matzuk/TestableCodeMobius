package com.matsyuk.testablecodemobius.data.network.models.response.profile;

import android.support.annotation.Nullable;

/**
 * @author e.matsyuk
 */
public class PersonalAccountModel {

    private String accountNumber;
    private String cardNumber;

    public PersonalAccountModel(@Nullable String accountNumber, @Nullable String cardNumber) {
        this.accountNumber = accountNumber;
        this.cardNumber = cardNumber;
    }

    @Nullable
    public String getAccountNumber() {
        return accountNumber;
    }

    @Nullable
    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public String toString() {
        return "PersonalAccountModel{" +
                "accountNumber='" + accountNumber + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }

}
