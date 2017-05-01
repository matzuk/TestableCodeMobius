package com.matsyuk.testablecodemobius.presentation.main.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.matsyuk.testablecodemobius.R;
import com.matsyuk.testablecodemobius.TCApplication;
import com.matsyuk.testablecodemobius.di.main.MainModule;
import com.matsyuk.testablecodemobius.presentation.main.presenter.IMainPresenter;
import com.matsyuk.testablecodemobius.presentation.profile.view.ProfileFragment;
import com.matsyuk.testablecodemobius.presentation.transfer.view.TransferFragment;

import javax.inject.Inject;

/**
 * @author e.matsyuk
 */
public class MainFragment extends Fragment implements IMainView {

    @Inject
    IMainPresenter iMainPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        TCApplication.get(getContext()).applicationComponent().plus(new MainModule()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fmt_main, container, false);
        // init buttons
        Button profileButton = (Button) view.findViewById(R.id.profile_button);
        if (profileButton != null) profileButton.setOnClickListener(v -> iMainPresenter.clickToProfileButton());
        Button transferButton = (Button) view.findViewById(R.id.transfer_button);
        if (transferButton != null) transferButton.setOnClickListener(v -> iMainPresenter.clickToTransferButton());
        // bind Presenter
        iMainPresenter.bindView(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        iMainPresenter.unbindView();
        super.onDestroyView();
    }

    @Override
    public void showProfileScreen() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fmt_container, new ProfileFragment());
        transaction.commit();
    }

    @Override
    public void showTransferScreen() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fmt_container, new TransferFragment());
        transaction.commit();
    }

}
