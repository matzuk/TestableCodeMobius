package com.matsyuk.testablecodemobius.dagger.transfer;

import com.matsyuk.testablecodemobius.ui.transfer.view.TransferFragment;

import dagger.Subcomponent;

/**
 * @author e.matsyuk
 */
@Subcomponent(modules = {TransferModule.class})
@TransferScope
public interface TransferComponent {

    void inject(TransferFragment transferFragment);

}
