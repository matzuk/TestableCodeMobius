package com.matsyuk.testablecodemobius.data.network.models.response.transfer;

import android.support.annotation.Nullable;

/**
 * @author e.matsyuk
 */
public class TransferResultModel {

    private String status;
    private String info;

    public TransferResultModel(String status, String info) {
        this.status = status;
        this.info = info;
    }

    @Nullable
    public String getStatus() {
        return status;
    }

    @Nullable
    public String getInfo() {
        return info;
    }

}
