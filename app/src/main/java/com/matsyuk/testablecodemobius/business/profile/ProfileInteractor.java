package com.matsyuk.testablecodemobius.business.profile;

import com.matsyuk.testablecodemobius.data.network.models.response.profile.PersonalAccountModel;
import com.matsyuk.testablecodemobius.data.network.models.response.profile.PersonalBalanceModel;
import com.matsyuk.testablecodemobius.data.network.models.response.profile.PersonalDepartmentModel;
import com.matsyuk.testablecodemobius.data.network.models.response.profile.PersonalInfoModel;
import com.matsyuk.testablecodemobius.data.repositories.profile.IProfileRepository;
import com.matsyuk.testablecodemobius.ui.profile.models.PersonalFullDataModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Single;

/**
 * @author e.matsyuk
 */
public class ProfileInteractor implements IProfileInteractor {

    private static final String EMPTY_STRING = "";
    private static final String DEFAULT_VALUE = "0";

    private IProfileRepository iProfileRepository;

    public ProfileInteractor(IProfileRepository iProfileRepository) {
        this.iProfileRepository = iProfileRepository;
    }

    @Override
    public Single<PersonalFullDataModel> getPersonalData() {
        return Single.zip(
                getParsedPersonalInfo(),
                getParsedPersonalAccount(),
                getParsedPersonalBalance(),
                getParsedPersonalDepartments(),
                this::convert
        );
    }

    private Single<PersonalInfoModel> getParsedPersonalInfo() {
        return iProfileRepository.getPersonalInfo()
                .toObservable()
                .onErrorResumeNext(throwable -> Observable.error(
                        new ProfileInteractorException("Incorrect personal info")))
                .toSingle();
    }

    private Single<PersonalAccountModel> getParsedPersonalAccount() {
        return iProfileRepository.getPersonalAccount()
                .onErrorReturn(throwable ->
                        new PersonalAccountModel(EMPTY_STRING, EMPTY_STRING));
    }

    private Single<PersonalBalanceModel> getParsedPersonalBalance() {
        return iProfileRepository.getPersonalBalance()
                .onErrorReturn(throwable -> new PersonalBalanceModel(new BigDecimal(DEFAULT_VALUE)));
    }

    private Single<List<PersonalDepartmentModel>> getParsedPersonalDepartments() {
        return  iProfileRepository.getPersonalDepartments()
                .onErrorReturn(throwable -> new ArrayList<>());
    }

    private PersonalFullDataModel convert(PersonalInfoModel personalInfoModel,
                                          PersonalAccountModel personalAccountModel,
                                          PersonalBalanceModel personalBalanceModel,
                                          List<PersonalDepartmentModel> personalDepartmentModelList) {
        return new PersonalFullDataModel(
                personalInfoModel == null || personalInfoModel.getName() == null ?
                        EMPTY_STRING : personalInfoModel.getName(),
                personalAccountModel == null || personalAccountModel.getAccountNumber() == null ?
                        EMPTY_STRING : personalAccountModel.getAccountNumber(),
                personalAccountModel == null || personalAccountModel.getCardNumber() == null ?
                        EMPTY_STRING : personalAccountModel.getCardNumber(),
                personalBalanceModel == null || personalBalanceModel.getBalance() == null ?
                        DEFAULT_VALUE : personalBalanceModel.getBalance().toPlainString(),
                getDepartmentAddress(personalDepartmentModelList)
        );
    }

    private String getDepartmentAddress(List<PersonalDepartmentModel> personalDepartmentModelList) {
        if (personalDepartmentModelList != null && !personalDepartmentModelList.isEmpty() &&
                personalDepartmentModelList.get(0) != null && personalDepartmentModelList.get(0).getAddress() != null){
            return personalDepartmentModelList.get(0).getAddress();
        } else {
            return EMPTY_STRING;
        }
    }

    @Override
    public Single<BigDecimal> updateBalance() {
        return iProfileRepository.updateBalance()
                .map(balance -> {
                    if (balance == null) {
                        throw new ProfileInteractorException("Null balance");
                    }
                    return balance;
                });
    }

}
