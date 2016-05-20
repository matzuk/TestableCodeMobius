package com.matsyuk.testablecodemobius.data.network.models.response.profile;

import android.support.annotation.Nullable;

import java.math.BigDecimal;

/**
 * @author e.matsyuk
 */
public class PersonalBalanceModel {

    private BigDecimal balance;

    public PersonalBalanceModel(@Nullable BigDecimal balance) {
        this.balance = balance;
    }

    @Nullable
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "PersonalBalanceModel{" +
                "balance=" + balance +
                '}';
    }

}
