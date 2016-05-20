package com.matsyuk.testablecodemobius.data.network.models.response.profile;

import android.support.annotation.Nullable;

/**
 * @author e.matsyuk
 */
public class PersonalDepartmentModel {

    private String name;
    private String address;
    private String info;

    public PersonalDepartmentModel(@Nullable String name, @Nullable String address, @Nullable String info) {
        this.name = name;
        this.address = address;
        this.info = info;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    @Nullable
    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "PersonalDepartmentModel{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

}
