package com.matsyuk.testablecodemobius.di.transfer;

import android.content.Context;

import com.matsyuk.testablecodemobius.business.transfer.ITransferInteractor;
import com.matsyuk.testablecodemobius.business.transfer.TransferInteractor;
import com.matsyuk.testablecodemobius.business.transfer.validation.LocalTransferValidation;
import com.matsyuk.testablecodemobius.business.transfer.validation.ServerTransferValidateParser;
import com.matsyuk.testablecodemobius.data.repositories.transfer.ITransferRepository;
import com.matsyuk.testablecodemobius.data.repositories.transfer.TransferRepositoryDemo;
import com.matsyuk.testablecodemobius.presentation.transfer.presenter.ITransferPresenter;
import com.matsyuk.testablecodemobius.presentation.transfer.presenter.TransferPresenter;
import com.matsyuk.testablecodemobius.utils.rx.RxSchedulersAbs;

import dagger.Module;
import dagger.Provides;

/**
 * @author e.matsyuk
 */
@Module
public class TransferModule {

    @Provides
    @TransferScope
    ITransferRepository provideITransferRepository() {
        return new TransferRepositoryDemo();
    }

    @Provides
    @TransferScope
    LocalTransferValidation provideLocalTransferValidation(Context context) {
        return new LocalTransferValidation(context);
    }

    @Provides
    @TransferScope
    ServerTransferValidateParser provideServerTransferValidateParser(Context context) {
        return new ServerTransferValidateParser(context);
    }

    @Provides
    @TransferScope
    ITransferInteractor provideITransferInteractor(ITransferRepository iTransferRepository,
                                                   LocalTransferValidation localTransferValidation,
                                                   ServerTransferValidateParser serverTransferValidateParser) {
        return new TransferInteractor(iTransferRepository, localTransferValidation, serverTransferValidateParser);
    }

    @Provides
    @TransferScope
    ITransferPresenter provideITransferPresenter(ITransferInteractor iTransferInteractor, RxSchedulersAbs rxSchedulersAbs) {
        return new TransferPresenter(iTransferInteractor, rxSchedulersAbs);
    }

}
