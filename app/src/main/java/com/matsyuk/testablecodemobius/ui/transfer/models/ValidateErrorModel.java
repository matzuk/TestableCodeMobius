package com.matsyuk.testablecodemobius.ui.transfer.models;

import android.support.annotation.NonNull;

/**
 * @author e.matsyuk
 */
public class ValidateErrorModel {

    public enum Field {
        ORG_NAME,
        BIK,
        INN,
        ACCOUNT_NUMBER,
        AMOUNT
    }

    private Field field;
    private String description;

    public ValidateErrorModel(@NonNull Field field, @NonNull String description) {
        this.field = field;
        this.description = description;
    }

    @NonNull
    public Field getField() {
        return field;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ValidateErrorModel{" +
                "field=" + field +
                ", description='" + description + '\'' +
                '}';
    }

}
