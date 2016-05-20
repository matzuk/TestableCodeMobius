package com.matsyuk.testablecodemobius.utils.rx;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * @author e.matsyuk
 */
public class RxSchedulersTest extends RxSchedulersAbs {

    @Override
    public Scheduler getMainThreadScheduler() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler getIOScheduler() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler getComputationScheduler() {
        return Schedulers.immediate();
    }

}
