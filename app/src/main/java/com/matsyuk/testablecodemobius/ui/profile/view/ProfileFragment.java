package com.matsyuk.testablecodemobius.ui.profile.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.matsyuk.testablecodemobius.R;
import com.matsyuk.testablecodemobius.TCApplication;
import com.matsyuk.testablecodemobius.dagger.profile.ProfileModule;
import com.matsyuk.testablecodemobius.ui.profile.presenter.IProfilePresenter;

import javax.inject.Inject;

/**
 * @author e.matsyuk
 */
public class ProfileFragment extends Fragment implements IProfileView {

    @Inject
    IProfilePresenter iProfilePresenter;

    private TextView nameTV;
    private TextView accountNumberTV;
    private TextView cardNumberTV;
    private TextView balanceTV;
    private TextView nearestDepartmentTV;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        TCApplication.get(getContext()).applicationComponent().plus(new ProfileModule()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fmt_profile, container, false);
        nameTV = (TextView)view.findViewById(R.id.name);
        accountNumberTV = (TextView)view.findViewById(R.id.account_number);
        cardNumberTV = (TextView)view.findViewById(R.id.card_number);
        balanceTV = (TextView)view.findViewById(R.id.balance);
        nearestDepartmentTV = (TextView)view.findViewById(R.id.nearest_department);
        TextView updateBalanceTV = (TextView) view.findViewById(R.id.balance_update);
        updateBalanceTV.setOnClickListener(v -> iProfilePresenter.updateBalance());
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.main_profile_layout);
        // presenter
        iProfilePresenter.bindView(this);
        iProfilePresenter.loadPersonalInfo();
        return view;
    }

    @Override
    public void onDestroyView() {
        iProfilePresenter.unbindView();
        super.onDestroyView();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, getString(R.string.common_error), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void setName(String name) {
        nameTV.setText(name);
    }

    @Override
    public void setAccountNumber(String accountNumber) {
        accountNumberTV.setText(accountNumber);
    }

    @Override
    public void setCardNumber(String cardNumber) {
        cardNumberTV.setText(cardNumber);
    }

    @Override
    public void setBalance(String balance) {
        balanceTV.setText(balance);
    }

    @Override
    public void setNearestDepartment(String nearestDepartment) {
        nearestDepartmentTV.setText(nearestDepartment);
    }

}
