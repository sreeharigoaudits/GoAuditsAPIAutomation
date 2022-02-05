package com.goaudits.automation.api.tests.testdata;



import com.goaudits.automation.api.tests.setUp;
import com.goaudits.automation.framework.api.modules.loginAndRegistration.User;
import com.goaudits.automation.framework.api.utils.*;
import com.goaudits.automation.framework.api.v1.Common_Actions;
import com.goaudits.automation.framework.api.v1.loginandRegistration.LoginAndRegistrationBuilder;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Stories;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Student_Approved_User_Creation extends setUp {
    private Common_Actions actions;


    @Stories({"Pre-Conditions (Student_Approved_User_Creation)"})
    @BeforeClass
    @Step
    public void setup() {
        actions = new Common_Actions();


    }

    @Stories({"Student_Approved_User_Creation"})
    @Test
    public void student_Approved_User_Creation() throws Exception {


         LoginAndRegistrationBuilder.builder(actions).signIn().build();


    }
}