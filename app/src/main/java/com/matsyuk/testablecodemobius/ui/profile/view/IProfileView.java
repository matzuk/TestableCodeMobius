package com.matsyuk.testablecodemobius.ui.profile.view;

/**
 * @author e.matsyuk
 */
public interface IProfileView {

    void showProgress();
    void hideProgress();
    void showError();

    void setName(String name);

    void setAccountNumber(String accountNumber);
    void setCardNumber(String cardNumber);

    void setBalance(String balance);

    void setNearestDepartment(String nearestDepartment);

}
