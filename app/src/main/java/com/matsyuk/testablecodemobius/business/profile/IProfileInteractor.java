package com.matsyuk.testablecodemobius.business.profile;

import com.matsyuk.testablecodemobius.ui.profile.models.PersonalFullDataModel;

import java.math.BigDecimal;

import rx.Single;

/**
 * @author e.matsyuk
 */
public interface IProfileInteractor {

    /**
     * @return onError if PersonalInfo loading error was occurred in Repository, onSuccess in other cases
     */
    Single<PersonalFullDataModel> getPersonalData();

    /**
     * @return onError if loading was occurred or returned value == null, onSuccess in other cases
     */
    Single<BigDecimal> updateBalance();

}
