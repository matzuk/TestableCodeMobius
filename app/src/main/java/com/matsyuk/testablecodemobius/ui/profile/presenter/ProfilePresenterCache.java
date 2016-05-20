package com.matsyuk.testablecodemobius.ui.profile.presenter;

import android.support.annotation.NonNull;

import com.matsyuk.testablecodemobius.ui.profile.models.PersonalFullDataModel;

import java.math.BigDecimal;

/**
 * @author e.matsyuk
 */
public class ProfilePresenterCache {

    private PersonalFullDataModel personalFullDataModel;

    public void updateData(@NonNull PersonalFullDataModel personalFullDataModel) {
        this.personalFullDataModel = personalFullDataModel;
    }

    public void updateBalance(@NonNull BigDecimal balance) {
        if (personalFullDataModel == null) {
            return;
        }
        personalFullDataModel.setBalance(balance.toPlainString());
    }

    public boolean isCacheExist() {
        return personalFullDataModel != null;
    }

    public PersonalFullDataModel getPersonalFullDataModel() {
        return personalFullDataModel;
    }

}
