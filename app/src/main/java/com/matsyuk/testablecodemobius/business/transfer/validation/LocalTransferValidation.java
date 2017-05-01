package com.matsyuk.testablecodemobius.business.transfer.validation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.matsyuk.testablecodemobius.R;
import com.matsyuk.testablecodemobius.business.transfer.models.LocalValidateResultModel;
import com.matsyuk.testablecodemobius.data.network.models.request.transfer.TransferRequestModel;
import com.matsyuk.testablecodemobius.presentation.transfer.models.TransferFilledDataModel;
import com.matsyuk.testablecodemobius.presentation.transfer.models.ValidateErrorModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import rx.Single;

/**
 * @author e.matsyuk
 */
public class LocalTransferValidation {

    private static final int ACCOUNT_NUMBER_LENGTH = 20;
    private static final double MIN_AMOUNT = 0;

    private Context context;

    public LocalTransferValidation(Context context) {
        this.context = context;
    }

    public Single<TransferRequestModel> getLocalValidateSingle(
            @NonNull TransferFilledDataModel transferFilledDataModel) {
        return Single.fromCallable(() -> transferFilledDataModel)
                .map(this::localValidate)
                .flatMap(localValidateResultModel -> {
                    if (!localValidateResultModel.getValidateErrors().isEmpty()) {
                        return Single.error(new TransferValidateException(
                                localValidateResultModel.getValidateErrors())
                        );
                    }
                    return Single.fromCallable(localValidateResultModel::getTransferFilledDataModel);
                })
                .map(this::mapModel);
    }

    @NonNull
    private LocalValidateResultModel localValidate(@NonNull TransferFilledDataModel transferFilledDataModel) {
        List<ValidateErrorModel> validateErrorModelList = new ArrayList<>();

        ValidateErrorModel orgNameValidateErrorModel = orgNameLocalValidate(transferFilledDataModel.getOrgName());
        if (orgNameValidateErrorModel != null) validateErrorModelList.add(orgNameValidateErrorModel);

        ValidateErrorModel bikValidateErrorModel = bikLocalValidate(transferFilledDataModel.getBIK());
        if (bikValidateErrorModel != null) validateErrorModelList.add(bikValidateErrorModel);

        ValidateErrorModel innValidateErrorModel = innLocalValidate(transferFilledDataModel.getINN());
        if (innValidateErrorModel != null) validateErrorModelList.add(innValidateErrorModel);

        ValidateErrorModel accountNumberValidateErrorModel = accountNumberLocalValidate(
                transferFilledDataModel.getAccountNumber());
        if (accountNumberValidateErrorModel != null) validateErrorModelList.add(accountNumberValidateErrorModel);

        ValidateErrorModel amountValidateErrorModel = amountLocalValidate(transferFilledDataModel.getAmount());
        if (amountValidateErrorModel != null) validateErrorModelList.add(amountValidateErrorModel);

        return new LocalValidateResultModel(transferFilledDataModel, validateErrorModelList);
    }

    @Nullable
    ValidateErrorModel orgNameLocalValidate(@Nullable String orgName) {
        if (TextUtils.isEmpty(orgName)) {
            return new ValidateErrorModel(ValidateErrorModel.Field.ORG_NAME, context.getString(R.string.validation_org_name_field_empty));
        }
        return null;
    }

    @Nullable
    ValidateErrorModel bikLocalValidate(@Nullable String bik) {
        if (TextUtils.isEmpty(bik)) {
            return new ValidateErrorModel(ValidateErrorModel.Field.BIK, context.getString(R.string.validation_bik_field_empty));
        }
        return null;
    }

    @Nullable
    ValidateErrorModel innLocalValidate(@Nullable String inn) {
        if (TextUtils.isEmpty(inn)) {
            return new ValidateErrorModel(ValidateErrorModel.Field.INN, context.getString(R.string.validation_inn_field_empty));
        }
        return null;
    }

    @Nullable
    ValidateErrorModel accountNumberLocalValidate(@Nullable String accountNumber) {
        if (TextUtils.isEmpty(accountNumber)) {
            return new ValidateErrorModel(ValidateErrorModel.Field.ACCOUNT_NUMBER, context.getString(R.string.validation_account_number_field_empty));
        }
        String trimmedAccountNumber = accountNumber.trim();
        if (trimmedAccountNumber.length() != ACCOUNT_NUMBER_LENGTH) {
            return new ValidateErrorModel(ValidateErrorModel.Field.ACCOUNT_NUMBER, context.getString(R.string.validation_account_number_field_length));
        }
        return null;
    }

    @Nullable
    ValidateErrorModel amountLocalValidate(@Nullable String amount) {
        if (TextUtils.isEmpty(amount)) {
            return new ValidateErrorModel(ValidateErrorModel.Field.AMOUNT, context.getString(R.string.validation_amount_field_empty));
        }
        BigDecimal amountBD;
        try {
            amountBD = new BigDecimal(amount);
        } catch (NumberFormatException exception) {
            return new ValidateErrorModel(ValidateErrorModel.Field.AMOUNT, context.getString(R.string.validation_amount_field_not_digit));
        }
        if (amountBD.doubleValue() <= MIN_AMOUNT) {
            return new ValidateErrorModel(ValidateErrorModel.Field.AMOUNT, context.getString(R.string.validation_amount_field_negative));
        }
        return null;
    }

    @NonNull
    private TransferRequestModel mapModel(TransferFilledDataModel transferFilledDataModel) {
        return new TransferRequestModel(
                transferFilledDataModel.getOrgName(),
                transferFilledDataModel.getBIK(),
                transferFilledDataModel.getINN(),
                transferFilledDataModel.getAccountNumber(),
                new BigDecimal(transferFilledDataModel.getAmount())
        );
    }

}
