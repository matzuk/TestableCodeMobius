package com.matsyuk.testablecodemobius.ui.profile.models;

import android.support.annotation.NonNull;

/**
 * @author e.matsyuk
 */
public class PersonalFullDataModel {

    private String name;
    private String accountNumber;
    private String cardNumber;
    private String balance;
    private String nearestDepartment;

    public PersonalFullDataModel(@NonNull String name, @NonNull String accountNumber, @NonNull String cardNumber,
                                 @NonNull String balance, @NonNull String nearestDepartment) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.nearestDepartment = nearestDepartment;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getAccountNumber() {
        return accountNumber;
    }

    @NonNull
    public String getCardNumber() {
        return cardNumber;
    }

    @NonNull
    public String getBalance() {
        return balance;
    }

    @NonNull
    public String getNearestDepartment() {
        return nearestDepartment;
    }

    public void setBalance(@NonNull String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "PersonalFullDataModel{" +
                "name='" + name + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", balance='" + balance + '\'' +
                ", nearestDepartment='" + nearestDepartment + '\'' +
                '}';
    }

}
