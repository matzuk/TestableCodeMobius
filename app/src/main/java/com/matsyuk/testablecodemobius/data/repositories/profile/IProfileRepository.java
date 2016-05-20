package com.matsyuk.testablecodemobius.data.repositories.profile;

import com.matsyuk.testablecodemobius.data.network.models.response.profile.PersonalAccountModel;
import com.matsyuk.testablecodemobius.data.network.models.response.profile.PersonalBalanceModel;
import com.matsyuk.testablecodemobius.data.network.models.response.profile.PersonalDepartmentModel;
import com.matsyuk.testablecodemobius.data.network.models.response.profile.PersonalInfoModel;

import java.math.BigDecimal;
import java.util.List;

import rx.Single;

/**
 * @author e.matsyuk
 */
public interface IProfileRepository {

    Single<PersonalAccountModel> getPersonalAccount();
    Single<PersonalBalanceModel> getPersonalBalance();
    Single<PersonalInfoModel> getPersonalInfo();
    Single<List<PersonalDepartmentModel>> getPersonalDepartments();
    Single<BigDecimal> updateBalance();

}


