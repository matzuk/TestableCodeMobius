package com.matsyuk.testablecodemobius.business.transfer.validation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.matsyuk.testablecodemobius.R;
import com.matsyuk.testablecodemobius.business.transfer.models.ServerValidateResultModel;
import com.matsyuk.testablecodemobius.data.network.models.request.transfer.TransferRequestModel;
import com.matsyuk.testablecodemobius.data.network.models.response.transfer.ServerValidateErrorModel;
import com.matsyuk.testablecodemobius.data.network.models.response.transfer.TransferValidateModel;
import com.matsyuk.testablecodemobius.presentation.transfer.models.ValidateErrorModel;

import java.util.ArrayList;
import java.util.List;

import rx.Single;

/**
 * @author e.matsyuk
 */
public class ServerTransferValidateParser {

    private Context context;

    public ServerTransferValidateParser(Context context) {
        this.context = context;
    }

    public Single<TransferRequestModel> getServerValidateParseSingle(TransferValidateModel transferValidateModel) {
        return Single.fromCallable(() -> transferValidateModel)
                .map(this::parseServerResponse)
                .flatMap(serverValidateResultModel -> {
                    if (!serverValidateResultModel.getValidateErrors().isEmpty()) {
                        return Single.error(new TransferValidateException(serverValidateResultModel.getValidateErrors()));
                    }
                    return Single.fromCallable(serverValidateResultModel::getTransferRequestModel);
                });
    }

    @NonNull
    private ServerValidateResultModel parseServerResponse(TransferValidateModel transferValidateModel) {
        TransferRequestModel transferRequestModel = transferValidateModel.getResult();
        List<ValidateErrorModel> validateErrorModelList = parseServerErrorList(transferValidateModel.getErrors());
        return new ServerValidateResultModel(transferRequestModel, validateErrorModelList);
    }

    @NonNull
    private List<ValidateErrorModel> parseServerErrorList(@Nullable List<ServerValidateErrorModel> errors) {
        List<ValidateErrorModel> validateErrorModelList = new ArrayList<>();
        if (errors == null) {
            return validateErrorModelList;
        }
        for (ServerValidateErrorModel serverValidateErrorModel : errors) {
            ValidateErrorModel validateErrorModel = parseServerError(serverValidateErrorModel);
            if (validateErrorModel != null) validateErrorModelList.add(validateErrorModel);
        }
        return validateErrorModelList;
    }

    @Nullable
    private ValidateErrorModel parseServerError(ServerValidateErrorModel serverValidateErrorModel) {
        switch (serverValidateErrorModel.getCode()) {
            case 1001: {
                return new ValidateErrorModel(ValidateErrorModel.Field.ORG_NAME, context.getString(R.string.server_validation_org_name));
            }
            case 1002: {
                return new ValidateErrorModel(ValidateErrorModel.Field.BIK, context.getString(R.string.server_validation_bik));
            }
            case 1003: {
                return new ValidateErrorModel(ValidateErrorModel.Field.INN, context.getString(R.string.server_validation_inn));
            }
            case 1004: {
                return new ValidateErrorModel(ValidateErrorModel.Field.ACCOUNT_NUMBER, context.getString(R.string.server_validation_account_number));
            }
            case 1005: {
                return new ValidateErrorModel(ValidateErrorModel.Field.AMOUNT, context.getString(R.string.server_validation_amount));
            }
            default: return null;
        }
    }

}
