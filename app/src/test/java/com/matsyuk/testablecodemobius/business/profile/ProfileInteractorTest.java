package com.matsyuk.testablecodemobius.business.profile;

import com.matsyuk.testablecodemobius.data.network.models.response.profile.PersonalAccountModel;
import com.matsyuk.testablecodemobius.data.network.models.response.profile.PersonalBalanceModel;
import com.matsyuk.testablecodemobius.data.network.models.response.profile.PersonalDepartmentModel;
import com.matsyuk.testablecodemobius.data.network.models.response.profile.PersonalInfoModel;
import com.matsyuk.testablecodemobius.data.repositories.profile.IProfileRepository;
import com.matsyuk.testablecodemobius.ui.profile.models.PersonalFullDataModel;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import rx.Single;
import rx.observers.TestSubscriber;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author e.matsyuk
 */
public class ProfileInteractorTest {

    private IProfileRepository iProfileRepository;
    private ProfileInteractor profileInteractor;

    @Before
    public void beforeEachTest() {
        iProfileRepository = mock(IProfileRepository.class);
        profileInteractor = new ProfileInteractor(iProfileRepository);
    }

    @Test
    public void getPersonalData_allSuccess() {
        // mock
        when(iProfileRepository.getPersonalAccount()).thenReturn(Single.fromCallable(() ->
                new PersonalAccountModel("123", "456")));
        when(iProfileRepository.getPersonalBalance()).thenReturn(Single.fromCallable(() ->
                new PersonalBalanceModel(new BigDecimal(100))));
        when(iProfileRepository.getPersonalDepartments()).thenReturn(
                Single.fromCallable(() -> asList(new PersonalDepartmentModel("first", "Moscow", "info"),
                        new PersonalDepartmentModel("second", "Tver", "info")))
        );
        when(iProfileRepository.getPersonalInfo()).thenReturn(Single.fromCallable(() ->
                new PersonalInfoModel("matsyuk e.v.")));
        // create TestSubscriber
        TestSubscriber<PersonalFullDataModel> testSubscriber = TestSubscriber.create();
        // call method and get result
        profileInteractor.getPersonalData().subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        // test no errors was not occurred
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        // test of the received PersonalFullDataModel
        PersonalFullDataModel personalFullDataModel = testSubscriber.getOnNextEvents().get(0);
        assertThat(personalFullDataModel.getName()).isEqualTo("matsyuk e.v.");
        assertThat(personalFullDataModel.getAccountNumber()).isEqualTo("123");
        assertThat(personalFullDataModel.getCardNumber()).isEqualTo("456");
        assertThat(personalFullDataModel.getBalance()).isEqualTo("100");
        assertThat(personalFullDataModel.getNearestDepartment()).isEqualTo("Moscow");
    }

    @Test
    public void getPersonalData_personalInfoError() {
        // mock
        when(iProfileRepository.getPersonalAccount()).thenReturn(Single.fromCallable(() ->
                new PersonalAccountModel("123", "456")));
        when(iProfileRepository.getPersonalBalance()).thenReturn(Single.fromCallable(() ->
                new PersonalBalanceModel(new BigDecimal(100))));
        when(iProfileRepository.getPersonalDepartments()).thenReturn(
                Single.fromCallable(() -> asList(new PersonalDepartmentModel("first", "Moscow", "info"),
                        new PersonalDepartmentModel("second", "Moscow", "info")))
        );
        when(iProfileRepository.getPersonalInfo()).thenReturn(Single.error(new IllegalStateException()));
        // create TestSubscriber
        TestSubscriber<PersonalFullDataModel> testSubscriber = TestSubscriber.create();
        // call method and get result
        profileInteractor.getPersonalData().subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        // test error was occurred
        testSubscriber.assertError(ProfileInteractorException.class);
    }

    @Test
    public void getPersonalData_personalInfoSuccess_otherError() {
        // mock
        when(iProfileRepository.getPersonalAccount()).thenReturn(Single.error(new RuntimeException()));
        when(iProfileRepository.getPersonalBalance()).thenReturn(Single.error(new RuntimeException()));
        when(iProfileRepository.getPersonalDepartments()).thenReturn(Single.error(new RuntimeException()));
        when(iProfileRepository.getPersonalInfo()).thenReturn(Single.fromCallable(() ->
                new PersonalInfoModel("matsyuk e.v.")));
        // create TestSubscriber
        TestSubscriber<PersonalFullDataModel> testSubscriber = TestSubscriber.create();
        // call method and get result
        profileInteractor.getPersonalData().subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        // test no errors was not occurred in getPersonalData method
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        // test of the received PersonalFullDataModel
        PersonalFullDataModel personalFullDataModel = testSubscriber.getOnNextEvents().get(0);
        assertThat(personalFullDataModel.getName()).isEqualTo("matsyuk e.v.");
        assertThat(personalFullDataModel.getAccountNumber()).isEqualTo("");
        assertThat(personalFullDataModel.getCardNumber()).isEqualTo("");
        assertThat(personalFullDataModel.getBalance()).isEqualTo("0");
        assertThat(personalFullDataModel.getNearestDepartment()).isEqualTo("");
    }

    @Test
    public void getPersonalData_allSuccess_butAllNull() {
        // mock
        when(iProfileRepository.getPersonalAccount()).thenReturn(Single.fromCallable(() -> null));
        when(iProfileRepository.getPersonalBalance()).thenReturn(Single.fromCallable(() ->
                new PersonalBalanceModel(null)));
        when(iProfileRepository.getPersonalDepartments()).thenReturn(Single.fromCallable(() -> null));
        when(iProfileRepository.getPersonalInfo()).thenReturn(Single.fromCallable(() ->
                new PersonalInfoModel(null)));
        // create TestSubscriber
        TestSubscriber<PersonalFullDataModel> testSubscriber = TestSubscriber.create();
        // call method and get result
        profileInteractor.getPersonalData().subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        // test no errors was not occurred
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        // test of the received PersonalFullDataModel
        PersonalFullDataModel personalFullDataModel = testSubscriber.getOnNextEvents().get(0);
        assertThat(personalFullDataModel.getName()).isEqualTo("");
        assertThat(personalFullDataModel.getAccountNumber()).isEqualTo("");
        assertThat(personalFullDataModel.getCardNumber()).isEqualTo("");
        assertThat(personalFullDataModel.getBalance()).isEqualTo("0");
        assertThat(personalFullDataModel.getNearestDepartment()).isEqualTo("");
    }

    @Test
    public void updateBalance_error() {
        // mock
        when(iProfileRepository.updateBalance()).thenReturn(Single.error(new RuntimeException()));
        // create TestSubscriber
        TestSubscriber<BigDecimal> testSubscriber = TestSubscriber.create();
        // call method and get result
        profileInteractor.updateBalance().subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        // test error was occurred
        testSubscriber.assertError(RuntimeException.class);
    }

    @Test
    public void updateBalance_success_returnNull() {
        // mock
        when(iProfileRepository.updateBalance()).thenReturn(Single.fromCallable(() -> null));
        // create TestSubscriber
        TestSubscriber<BigDecimal> testSubscriber = TestSubscriber.create();
        // call method and get result
        profileInteractor.updateBalance().subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        // test error was occurred
        testSubscriber.assertError(ProfileInteractorException.class);
    }

    @Test
    public void updateBalance_success_returnNonNull() {
        // mock
        BigDecimal balance = new BigDecimal(100);
        when(iProfileRepository.updateBalance()).thenReturn(Single.fromCallable(() -> balance));
        // create TestSubscriber
        TestSubscriber<BigDecimal> testSubscriber = TestSubscriber.create();
        // call method and get result
        profileInteractor.updateBalance().subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        // test no errors was not occurred
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        // test received value
        BigDecimal receivedBalance = testSubscriber.getOnNextEvents().get(0);
        assertThat(receivedBalance).isEqualTo(balance);
    }

}