package com.matsyuk.testablecodemobius.ui.transfer.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.matsyuk.testablecodemobius.R;
import com.matsyuk.testablecodemobius.TCApplication;
import com.matsyuk.testablecodemobius.dagger.transfer.TransferModule;
import com.matsyuk.testablecodemobius.ui.transfer.models.TransferFilledDataModel;
import com.matsyuk.testablecodemobius.ui.transfer.presenter.ITransferPresenter;

import javax.inject.Inject;

/**
 * @author e.matsyuk
 */
public class TransferFragment extends Fragment implements ITransferView {

    @Inject
    ITransferPresenter iTransferPresenter;

    private EditText orgNameET;
    private EditText bikET;
    private EditText innET;
    private EditText accountNumberET;
    private EditText amountET;
    private Button sendTransferButton;
    private LinearLayout progress;
    private CoordinatorLayout coordinatorLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        TCApplication.get(getContext()).applicationComponent().plus(new TransferModule()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fmt_transfer, container, false);
        orgNameET = (EditText) view.findViewById(R.id.org_name_et);
        bikET = (EditText) view.findViewById(R.id.bik_et);
        innET = (EditText) view.findViewById(R.id.inn_et);
        accountNumberET = (EditText) view.findViewById(R.id.account_number_et);
        amountET = (EditText) view.findViewById(R.id.amount_et);
        sendTransferButton = (Button) view.findViewById(R.id.send_transfer);
        sendTransferButton.setOnClickListener(v -> iTransferPresenter.sendTransfer(getTransferFilledDataModel()));
        progress = (LinearLayout) view.findViewById(R.id.transfer_progress);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.main_transfer_layout);
        // presenter
        iTransferPresenter.bindView(this);
        iTransferPresenter.listenFields(RxTextView.textChangeEvents(orgNameET), RxTextView.textChangeEvents(amountET));
        return view;
    }

    private TransferFilledDataModel getTransferFilledDataModel() {
        return new TransferFilledDataModel(orgNameET.getText().toString(), bikET.getText().toString(),
                innET.getText().toString(), accountNumberET.getText().toString(), amountET.getText().toString());
    }

    @Override
    public void onDestroyView() {
        iTransferPresenter.unbindView();
        super.onDestroyView();
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showOrgNameError(String error) {
        orgNameET.setError(error);
    }

    @Override
    public void showBIKError(String error) {
        bikET.setError(error);
    }

    @Override
    public void showINNError(String error) {
        innET.setError(error);
    }

    @Override
    public void showCommonError() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, getString(R.string.common_error),
                Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showSuccess() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, getString(R.string.transfer_success_text),
                Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showAccountNumberError(String error) {
        accountNumberET.setError(error);
    }

    @Override
    public void showAmountError(String error) {
        amountET.setError(error);
    }

    // another ITransferView methods

    @Override
    public void setEnableSendTransferButton(boolean enable) {
        sendTransferButton.setEnabled(enable);
    }

}
