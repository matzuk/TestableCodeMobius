package com.matsyuk.testablecodemobius.business.transfer;

import android.widget.TextView;

import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.matsyuk.testablecodemobius.business.transfer.validation.LocalTransferValidation;
import com.matsyuk.testablecodemobius.business.transfer.validation.ServerTransferValidateParser;
import com.matsyuk.testablecodemobius.data.repositories.transfer.ITransferRepository;

import org.junit.Before;
import org.junit.Test;

import rx.observers.TestSubscriber;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.mock;

/**
 * @author e.matsyuk
 */
public class TransferInteractorTest {

    private TransferInteractor transferInteractor;

    @Before
    public void beforeEachTest() {
        transferInteractor = new TransferInteractor(mock(ITransferRepository.class), mock(LocalTransferValidation.class), mock(ServerTransferValidateParser.class));
    }

    @Test
    public void controlSendButton_test() {
        // mock observables
        PublishSubject<TextViewTextChangeEvent> orgNameFieldListener = PublishSubject.create();
        PublishSubject<TextViewTextChangeEvent> amountFieldListener = PublishSubject.create();
        // subscribe to controlSendButton Observable
        TestSubscriber<Boolean> testSubscriber = TestSubscriber.create();
        transferInteractor.controlSendButton(orgNameFieldListener, amountFieldListener)
                .subscribe(testSubscriber);
        // test first false emit because of orgName is empty and amount is empty
        testSubscriber.assertValueCount(1);
        testSubscriber.assertValues(false);
        // change orgName
        orgNameFieldListener.onNext(TextViewTextChangeEvent.create(mock(TextView.class), "a", 0, 1, 1));
        orgNameFieldListener.onNext(TextViewTextChangeEvent.create(mock(TextView.class), "as", 1, 2, 2));
        orgNameFieldListener.onNext(TextViewTextChangeEvent.create(mock(TextView.class), "asd", 2, 3, 3));
        // test first false emit and no retry emits because of orgName is not empty and amount is empty
        testSubscriber.assertValueCount(1);
        testSubscriber.assertValues(false);
        // change amount
        amountFieldListener.onNext(TextViewTextChangeEvent.create(mock(TextView.class), "q", 0, 1, 1));
        amountFieldListener.onNext(TextViewTextChangeEvent.create(mock(TextView.class), "qw", 1, 2, 2));
        // test second true emit and no retry emits because of orgName is not empty and amount is not empty
        testSubscriber.assertValueCount(2);
        testSubscriber.assertValues(false, true);
        // clear orgName
        orgNameFieldListener.onNext(TextViewTextChangeEvent.create(mock(TextView.class), "", 0, 1, 1));
        // test third false emit because of orgName is empty
        testSubscriber.assertValueCount(3);
        testSubscriber.assertValues(false, true, false);
        // clear amount
        amountFieldListener.onNext(TextViewTextChangeEvent.create(mock(TextView.class), "", 0, 1, 1));
        // test third false emit because of orgName is empty and amount is empty
        testSubscriber.assertValueCount(3);
        testSubscriber.assertValues(false, true, false);
        // change amount
        orgNameFieldListener.onNext(TextViewTextChangeEvent.create(mock(TextView.class), "a", 0, 1, 1));
        amountFieldListener.onNext(TextViewTextChangeEvent.create(mock(TextView.class), "q", 0, 1, 1));
        // test fourth true emit because of orgName is not empty and amount is not empty
        testSubscriber.assertValueCount(4);
        testSubscriber.assertValues(false, true, false, true);
    }

}