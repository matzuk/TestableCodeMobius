package com.matsyuk.testablecodemobius.data.network.models.response.transfer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.matsyuk.testablecodemobius.data.network.models.request.transfer.TransferRequestModel;

import java.util.List;

/**
 * @author e.matsyuk
 */
public class TransferValidateModel {

    private String status;
    private TransferRequestModel result;
    private List<ServerValidateErrorModel> errors;

    public TransferValidateModel(@NonNull String status, @NonNull TransferRequestModel result, @Nullable List<ServerValidateErrorModel> errors) {
        this.status = status;
        this.result = result;
        this.errors = errors;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    @NonNull
    public TransferRequestModel getResult() {
        return result;
    }

    @Nullable
    public List<ServerValidateErrorModel> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        return "TransferValidateModel{" +
                "status='" + status + '\'' +
                ", result=" + result +
                ", errors=" + errors +
                '}';
    }

}
