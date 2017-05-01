package com.matsyuk.testablecodemobius.di.transfer;

import com.matsyuk.testablecodemobius.presentation.transfer.view.TransferFragment;

import dagger.Subcomponent;

/**
 * @author e.matsyuk
 */
@Subcomponent(modules = {TransferModule.class})
@TransferScope
public interface TransferComponent {

    void inject(TransferFragment transferFragment);

}
