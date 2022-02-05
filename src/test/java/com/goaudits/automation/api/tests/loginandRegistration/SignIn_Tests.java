package com.goaudits.automation.api.tests.loginandRegistration;


import com.goaudits.automation.api.tests.setUp;
import com.goaudits.automation.framework.api.utils.*;
import com.goaudits.automation.framework.api.v1.Common_Actions;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Parameter;
import ru.yandex.qatools.allure.annotations.Stories;

import java.lang.reflect.Method;


@Slf4j
@Epic("LoginAndRegistration")
@Feature("SignIn")
public class SignIn_Tests extends setUp {
    private Common_Actions actions;

    @Stories({"Pre-Conditions (SignIn)"})
    @BeforeClass
    @Step
    public void setup() throws Exception {
        actions = new Common_Actions();
    }

    @DataProvider(name = "MPK_DataProvider")
    public Object[][] dataProvider(Method method) throws Exception {
        return actions.dataProviderUtils.getDataProvider(this.getClass().getName(), method.getName(), Utils.getWorkingDirectory());
    }

    @Stories({"SignIn_Tests"})
    @Test(dataProvider = "MPK_DataProvider")
    public void SignIn(@Parameter("sNo") String sNo, @Parameter("testCaseName") String testCaseName, @Parameter("Priority") String Priority, @Parameter("user_name") String user_name,
                      @Parameter("usr_pwd") String usr_pwd,@Parameter("userType") String userType,

                       @Parameter("responseCode") String responseCode, @Parameter("errorCode") String errorCode, @Parameter("errorParam") String errorParam, @Parameter("errorMessage") String errorMessage) throws Exception {
        log.info("Setting up test data for SignIn");
        Authenticable authenticable = Enumerations.ClientType.valueOf(userType);

        String req_user_name = user_name;
        String req_usr_pwd = usr_pwd;


        log.info("Executing Login API");
        actions.loginAndRegistration_actions.signIn.perform(req_user_name, req_usr_pwd,authenticable,  false);
    System.out.println("THe user guid is "+ Runtime_DataHolder.getUID());
        log.info("Response Validations");
        actions.responseValidator.assertResponseCode(responseCode);

    }
}