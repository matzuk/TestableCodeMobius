package com.matsyuk.testablecodemobius.business.transfer.validation;

import android.app.Activity;

import com.matsyuk.testablecodemobius.BuildConfig;
import com.matsyuk.testablecodemobius.data.network.models.request.transfer.TransferRequestModel;
import com.matsyuk.testablecodemobius.data.network.models.response.transfer.ServerValidateErrorModel;
import com.matsyuk.testablecodemobius.data.network.models.response.transfer.TransferValidateModel;
import com.matsyuk.testablecodemobius.presentation.transfer.models.ValidateErrorModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.math.BigDecimal;
import java.util.List;

import rx.observers.TestSubscriber;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

/**
 * @author e.matsyuk
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class ServerTransferValidateParserTest {

    private ServerTransferValidateParser serverTransferValidateParser;

    @Before
    public void beforeEachTest() {
        Activity activity = Robolectric.setupActivity(Activity.class);
        serverTransferValidateParser = new ServerTransferValidateParser(activity);
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test
    public void getServerValidateParseSingle_errors() {
        TestSubscriber<TransferRequestModel> testSubscriber = TestSubscriber.create();
        // subscribe to Single
        serverTransferValidateParser.getServerValidateParseSingle(getErrorTransferValidateModel()).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        // assert error was occurred
        testSubscriber.assertError(TransferValidateException.class);
        // assert TransferValidateException
        TransferValidateException transferValidateException = (TransferValidateException)testSubscriber.getOnErrorEvents().get(0);
        assertThat(transferValidateException.getValidateErrorList())
                .isNotNull()
                .hasSize(5) // 5 errors (1009 error ignored)
                .extracting("field", "description")
                .containsOnly((tuple(ValidateErrorModel.Field.ORG_NAME, "Incorrect org name")),
                        (tuple(ValidateErrorModel.Field.BIK, "Incorrect BIK")),
                        (tuple(ValidateErrorModel.Field.INN, "Incorrect INN")),
                        (tuple(ValidateErrorModel.Field.ACCOUNT_NUMBER, "Incorrect account number")),
                        (tuple(ValidateErrorModel.Field.AMOUNT, "Incorrect amount")));
    }

    private TransferValidateModel getErrorTransferValidateModel() {
        String status = "error";
        TransferRequestModel result = new TransferRequestModel("org", "123", "456", "12345678901234567890", new BigDecimal(100));
        List<ServerValidateErrorModel> errors = asList(
                new ServerValidateErrorModel(1001, "error"),
                new ServerValidateErrorModel(1002, "error"),
                new ServerValidateErrorModel(1009, "error"),
                new ServerValidateErrorModel(1003, "error"),
                new ServerValidateErrorModel(1004, "error"),
                new ServerValidateErrorModel(1005, "error")
        );
        return new TransferValidateModel(status, result, errors);
    }

    @Test
    public void getServerValidateParseSingle_success() {
        TestSubscriber<TransferRequestModel> testSubscriber = TestSubscriber.create();
        // subscribe to Single
        serverTransferValidateParser.getServerValidateParseSingle(getSuccessTransferValidateModel()).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        // assert success
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
    }

    private TransferValidateModel getSuccessTransferValidateModel() {
        String status = "error";
        TransferRequestModel result = new TransferRequestModel("org", "123", "456", "12345678901234567890", new BigDecimal(100));
        return new TransferValidateModel(status, result, null);
    }

}