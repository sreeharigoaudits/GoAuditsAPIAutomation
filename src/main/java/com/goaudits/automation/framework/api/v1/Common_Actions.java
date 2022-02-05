package com.goaudits.automation.framework.api.v1;


import com.goaudits.automation.framework.api.API_Client;
import com.goaudits.automation.framework.api.utils.DataHolder;
import com.goaudits.automation.framework.api.utils.DataProviderUtils;
import com.goaudits.automation.framework.api.utils.ResponseValidator;
import com.goaudits.automation.framework.api.v1.loginandRegistration.LoginAndRegistration_Actions;


public class Common_Actions {
    public API_Client masterClient;

    public LoginAndRegistration_Actions loginAndRegistration_actions;

    public ResponseValidator responseValidator;
    public DataProviderUtils dataProviderUtils;


    public Common_Actions() {
        this.masterClient = new API_Client(DataHolder.getMasterServerURL_v1());

        this.loginAndRegistration_actions = new LoginAndRegistration_Actions(this);


        this.responseValidator = new ResponseValidator(masterClient);
        this.dataProviderUtils = new DataProviderUtils();
    }

    public Common_Actions(String type) throws Exception {
        //this();
        //this.login_actions.login.perform(userName, password, auth_token, true);
    }
}