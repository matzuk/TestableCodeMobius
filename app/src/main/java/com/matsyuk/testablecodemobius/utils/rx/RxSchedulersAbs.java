package com.matsyuk.testablecodemobius.utils.rx;

import rx.Observable;
import rx.Scheduler;
import rx.Single;

/**
 * Utils class that provide Rx Schedulers and appropriate Rx Transformers
 *
 * @author e.matsyuk
 */
public abstract class RxSchedulersAbs {

    abstract public Scheduler getMainThreadScheduler();
    abstract public Scheduler getIOScheduler();
    abstract public Scheduler getComputationScheduler();

    public <T> Observable.Transformer<T, T> getIOToMainTransformer()  {
        return objectObservable -> objectObservable
                .subscribeOn(getIOScheduler())
                .observeOn(getMainThreadScheduler());
    }

    public <T> Single.Transformer<T, T> getIOToMainTransformerSingle()  {
        return objectObservable -> objectObservable
                .subscribeOn(getIOScheduler())
                .observeOn(getMainThreadScheduler());
    }

    public <T> Observable.Transformer<T, T> getComputationToMainTransformer()  {
        return objectObservable -> objectObservable
                .subscribeOn(getComputationScheduler())
                .observeOn(getMainThreadScheduler());
    }

    public <T> Single.Transformer<T, T> getComputationToMainTransformerSingle()  {
        return objectObservable -> objectObservable
                .subscribeOn(getComputationScheduler())
                .observeOn(getMainThreadScheduler());
    }

}
