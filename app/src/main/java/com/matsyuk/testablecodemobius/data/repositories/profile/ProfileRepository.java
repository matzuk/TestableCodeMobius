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
public class ProfileRepository implements IProfileRepository {

    @Override
    public Single<PersonalAccountModel> getPersonalAccount() {
        return null;
    }

    @Override
    public Single<PersonalBalanceModel> getPersonalBalance() {
        return null;
    }

    @Override
    public Single<PersonalInfoModel> getPersonalInfo() {
        return null;
    }

    @Override
    public Single<List<PersonalDepartmentModel>> getPersonalDepartments() {
        return null;
    }

    @Override
    public Single<BigDecimal> updateBalance() {
        return null;
    }

}
