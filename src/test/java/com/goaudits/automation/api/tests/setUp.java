package com.goaudits.automation.api.tests;

import com.goaudits.automation.framework.api.utils.Authenticable;

import com.goaudits.automation.framework.api.utils.Enumerations;
import com.goaudits.automation.framework.api.v1.Common_Actions;

import org.testng.annotations.BeforeSuite;

public class setUp {
    private Common_Actions actions;
    Authenticable authenticable = Enumerations.ClientType.SUPER_ADMIN;

    @BeforeSuite(description = "Test_Data Creation")
    public void init() throws Exception {
        this.actions = new Common_Actions();
    }
}