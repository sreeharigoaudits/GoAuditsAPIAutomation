package com.goaudits.automation.api.tests.testdata;


import com.goaudits.automation.api.tests.setUp;

import com.goaudits.automation.framework.api.v1.Common_Actions;
import com.goaudits.automation.framework.api.v1.loginandRegistration.LoginAndRegistrationBuilder;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Stories;



public class End_To_End_Audit_Creation extends setUp {
    private Common_Actions actions;


    @Stories({"Pre-Conditions (End_To_End_Audit_Creation)"})
    @BeforeClass
    @Step
    public void setup() {
        actions = new Common_Actions();

    }

    @Stories({"End_To_End_Audit_Creation"})
    @Test
    public void End_To_End_Audit_Creation() throws Exception {

        LoginAndRegistrationBuilder.builder(actions).signIn().build();

    }
}