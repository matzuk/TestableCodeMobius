package com.matsyuk.testablecodemobius.business.transfer.validation;

import android.app.Activity;

import com.matsyuk.testablecodemobius.BuildConfig;
import com.matsyuk.testablecodemobius.data.network.models.request.transfer.TransferRequestModel;
import com.matsyuk.testablecodemobius.presentation.transfer.models.TransferFilledDataModel;
import com.matsyuk.testablecodemobius.presentation.transfer.models.ValidateErrorModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

/**
 * @author e.matsyuk
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class LocalTransferValidationTest {

    private LocalTransferValidation localTransferValidation;

    @Before
    public void beforeEachTest() {
        Activity activity = Robolectric.setupActivity(Activity.class);
        localTransferValidation = new LocalTransferValidation(activity);
    }

    @Test
    public void orgNameLocalValidate_emptyName() {
        ValidateErrorModel validateErrorModel = localTransferValidation.orgNameLocalValidate("");
        assertThat(validateErrorModel).isNotNull();
        assertThat(validateErrorModel.getField()).isEqualTo(ValidateErrorModel.Field.ORG_NAME);
        assertThat(validateErrorModel.getDescription()).isEqualTo("Org name is empty");
    }

    @Test
    public void orgNameLocalValidate_normName() {
        ValidateErrorModel validateErrorModel = localTransferValidation.orgNameLocalValidate("name");
        assertThat(validateErrorModel).isNull();
    }

    @Test
    public void bikLocalValidate_emptyName() {
        ValidateErrorModel validateErrorModel = localTransferValidation.bikLocalValidate("");
        assertThat(validateErrorModel).isNotNull();
        assertThat(validateErrorModel.getField()).isEqualTo(ValidateErrorModel.Field.BIK);
        assertThat(validateErrorModel.getDescription()).isEqualTo("BIK is empty");
    }

    @Test
    public void bikLocalValidate_normName() {
        ValidateErrorModel validateErrorModel = localTransferValidation.bikLocalValidate("bik");
        assertThat(validateErrorModel).isNull();
    }

    @Test
    public void innLocalValidate_emptyName() {
        ValidateErrorModel validateErrorModel = localTransferValidation.innLocalValidate("");
        assertThat(validateErrorModel).isNotNull();
        assertThat(validateErrorModel.getField()).isEqualTo(ValidateErrorModel.Field.INN);
        assertThat(validateErrorModel.getDescription()).isEqualTo("INN is empty");
    }

    @Test
    public void innLocalValidate_normName() {
        ValidateErrorModel validateErrorModel = localTransferValidation.innLocalValidate("inn");
        assertThat(validateErrorModel).isNull();
    }

    @Test
    public void accountNumberLocalValidate_emptyName() {
        ValidateErrorModel validateErrorModel = localTransferValidation.accountNumberLocalValidate("");
        assertThat(validateErrorModel).isNotNull();
        assertThat(validateErrorModel.getField()).isEqualTo(ValidateErrorModel.Field.ACCOUNT_NUMBER);
        assertThat(validateErrorModel.getDescription()).isEqualTo("Account number is empty");
    }

    @Test
    public void accountNumberLocalValidate_length() {
        ValidateErrorModel validateErrorModel = localTransferValidation.accountNumberLocalValidate("1122");
        assertThat(validateErrorModel).isNotNull();
        assertThat(validateErrorModel.getField()).isEqualTo(ValidateErrorModel.Field.ACCOUNT_NUMBER);
        assertThat(validateErrorModel.getDescription()).isEqualTo("Account number length must be 20 symbols");
    }

    @Test
    public void accountNumberLocalValidate_normNumber() {
        ValidateErrorModel validateErrorModel = localTransferValidation.accountNumberLocalValidate("12345678901234567890 ");
        assertThat(validateErrorModel).isNull();
    }

    @Test
    public void amountLocalValidate_emptyName() {
        ValidateErrorModel validateErrorModel = localTransferValidation.amountLocalValidate("");
        assertThat(validateErrorModel).isNotNull();
        assertThat(validateErrorModel.getField()).isEqualTo(ValidateErrorModel.Field.AMOUNT);
        assertThat(validateErrorModel.getDescription()).isEqualTo("Amount is empty");
    }

    @Test
    public void amountLocalValidate_notDigits() {
        ValidateErrorModel validateErrorModel = localTransferValidation.amountLocalValidate("qwerty");
        assertThat(validateErrorModel).isNotNull();
        assertThat(validateErrorModel.getField()).isEqualTo(ValidateErrorModel.Field.AMOUNT);
        assertThat(validateErrorModel.getDescription()).isEqualTo("Amount must be consist of only digits");
    }

    @Test
    public void amountLocalValidate_negativeAmount() {
        ValidateErrorModel validateErrorModel = localTransferValidation.amountLocalValidate("-123");
        assertThat(validateErrorModel).isNotNull();
        assertThat(validateErrorModel.getField()).isEqualTo(ValidateErrorModel.Field.AMOUNT);
        assertThat(validateErrorModel.getDescription()).isEqualTo("Amount must be positive");
    }

    @Test
    public void accountNumberLocalValidate_normAmount() {
        ValidateErrorModel validateErrorModel = localTransferValidation.amountLocalValidate("123");
        assertThat(validateErrorModel).isNull();
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test
    public void getLocalValidateSingle_errorValidation() {
        TransferFilledDataModel transferFilledDataModel = new TransferFilledDataModel(
                "name", "34250", "", "1234", "qwerty"
        );
        TestSubscriber<TransferRequestModel> testSubscriber = TestSubscriber.create();
        // subscribe to Single
        localTransferValidation.getLocalValidateSingle(transferFilledDataModel).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        // assert
        testSubscriber.assertError(TransferValidateException.class);
        TransferValidateException transferValidateException =
                (TransferValidateException) testSubscriber.getOnErrorEvents().get(0);
        assertThat(transferValidateException.getValidateErrorList())
                .isNotNull()
                .hasSize(3)
                .extracting("field", "description")
                .containsOnly((tuple(ValidateErrorModel.Field.INN, "INN is empty")),
                        (tuple(ValidateErrorModel.Field.ACCOUNT_NUMBER, "Account number length must be 20 symbols")),
                        (tuple(ValidateErrorModel.Field.AMOUNT, "Amount must be consist of only digits")));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void getLocalValidateSingle_successValidation() {
        TransferFilledDataModel transferFilledDataModel = new TransferFilledDataModel(
                "name", "34250", "1234", "12345678901234567890", "1000"
        );
        TestSubscriber<TransferRequestModel> testSubscriber = TestSubscriber.create();
        // subscribe to Single
        localTransferValidation.getLocalValidateSingle(transferFilledDataModel).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        // assert
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        // assert returned TransferRequestModel
        TransferRequestModel transferRequestModel = testSubscriber.getOnNextEvents().get(0);
        assertThat(transferRequestModel.getOrgName()).isEqualTo("name");
        assertThat(transferRequestModel.getBIK()).isEqualTo("34250");
        assertThat(transferRequestModel.getINN()).isEqualTo("1234");
        assertThat(transferRequestModel.getAccountNumber()).isEqualTo("12345678901234567890");
        assertThat(transferRequestModel.getAmount().doubleValue()).isEqualTo(1000);
    }

}