package com.matsyuk.testablecodemobius.data.repositories.profile;

import com.matsyuk.testablecodemobius.data.network.models.response.profile.PersonalAccountModel;
import com.matsyuk.testablecodemobius.data.network.models.response.profile.PersonalBalanceModel;
import com.matsyuk.testablecodemobius.data.network.models.response.profile.PersonalDepartmentModel;
import com.matsyuk.testablecodemobius.data.network.models.response.profile.PersonalInfoModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Single;

import static java.util.Arrays.asList;

/**
 * @author e.matsyuk
 */
public class ProfileRepositoryDemo implements IProfileRepository {

    @Override
    public Single<PersonalAccountModel> getPersonalAccount() {
        return Observable.timer(2, TimeUnit.SECONDS)
                .toSingle()
                .flatMap(aLong -> Single.fromCallable(() ->
                        new PersonalAccountModel("12345678901234567890", "1234567890123456"))
                );
    }

    @Override
    public Single<PersonalBalanceModel> getPersonalBalance() {
        return Single.fromCallable(() -> new PersonalBalanceModel(new BigDecimal(123456)));
    }

    @Override
    public Single<PersonalInfoModel> getPersonalInfo() {
        return Single.fromCallable(() -> new PersonalInfoModel("Matsyuk E.V."));
    }

    @Override
    public Single<List<PersonalDepartmentModel>> getPersonalDepartments() {
        return Single.fromCallable(() -> asList(
                new PersonalDepartmentModel(
                        "first departments", "Russia, Moscow", "open in hours 9-18"),
                new PersonalDepartmentModel(
                        "second departments", "Russia, Tver", "open in hours 10-18"),
                new PersonalDepartmentModel(
                        "third departments", "Russia, Krasnodar", "open in hours 8-20")
        ));
    }

    @Override
    public Single<BigDecimal> updateBalance() {
        return Single.fromCallable(() -> new BigDecimal(980));
    }

}
