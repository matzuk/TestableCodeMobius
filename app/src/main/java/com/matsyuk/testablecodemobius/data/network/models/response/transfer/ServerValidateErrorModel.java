package com.matsyuk.testablecodemobius.data.network.models.response.transfer;

import android.support.annotation.NonNull;

/**
 * @author e.matsyuk
 */
public class ServerValidateErrorModel {

    private int code;
    private String description;

    public ServerValidateErrorModel(int code, @NonNull String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ServerValidateErrorModel{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }

}
