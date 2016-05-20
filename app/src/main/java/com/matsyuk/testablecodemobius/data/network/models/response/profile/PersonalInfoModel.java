package com.matsyuk.testablecodemobius.data.network.models.response.profile;

import android.support.annotation.Nullable;

/**
 * @author e.matsyuk
 */
public class PersonalInfoModel {

    private String name;

    public PersonalInfoModel(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "PersonalInfoModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
