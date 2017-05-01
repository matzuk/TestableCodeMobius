package com.matsyuk.testablecodemobius.presentation.profile.presenter;

import com.matsyuk.testablecodemobius.presentation.profile.models.PersonalFullDataModel;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author e.matsyuk
 */
public class ProfilePresenterCacheTest {

    @Test
    public void common_test() {
        // new balance
        BigDecimal balance = new BigDecimal(200);
        // create cache
        ProfilePresenterCache profilePresenterCache = new ProfilePresenterCache();
        // assert isCacheExist
        assertThat(profilePresenterCache.isCacheExist()).isFalse();
        // test no null exceptions
        profilePresenterCache.updateBalance(balance);
        // update cache
        profilePresenterCache.updateData(getData());
        // assert isCacheExist
        assertThat(profilePresenterCache.isCacheExist()).isTrue();
        // update balance
        profilePresenterCache.updateBalance(balance);
        // assert new balance
        assertThat(profilePresenterCache.getPersonalFullDataModel().getBalance()).isEqualTo("200");
    }

    private PersonalFullDataModel getData() {
        return new PersonalFullDataModel(
                "Ivanov S.P.",
                "1234567",
                "4321",
                "100",
                "Moscow"
        );
    }

}