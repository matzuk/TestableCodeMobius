package com.matsyuk.testablecodemobius.business.transfer.validation;

import android.support.annotation.NonNull;

import com.matsyuk.testablecodemobius.ui.transfer.models.ValidateErrorModel;

import java.util.List;

/**
 * @author e.matsyuk
 */
public class TransferValidateException extends RuntimeException {

    private List<ValidateErrorModel> validateErrorList;

    public TransferValidateException(@NonNull List<ValidateErrorModel> validateErrorList) {
        super();
        this.validateErrorList = validateErrorList;
    }

    @NonNull
    public List<ValidateErrorModel> getValidateErrorList() {
        return validateErrorList;
    }

}
