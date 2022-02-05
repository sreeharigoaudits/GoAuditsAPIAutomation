package com.goaudits.automation.framework.api.v1.loginandRegistration.actions;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.goaudits.automation.framework.api.API_Client;

import com.goaudits.automation.framework.api.modules.loginAndRegistration.User;
import com.goaudits.automation.framework.api.utils.*;

import com.goaudits.automation.framework.api.v1.Common_Actions;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SignIn {
    private final API_Client client;
    private final Common_Actions actions;

    public SignIn(Common_Actions actions) {
        this.client = actions.masterClient;
        this.actions = actions;
    }

    /**
     * @param assertPositiveResponse If this is true and the response is not successful the test will fail
     * @return The details of required activity
     */
    @Step("SignIn")
    public User perform(String user_name, String usr_pwd, Authenticable auth, boolean assertPositiveResponse) throws Exception {
        actions.masterClient.serverURL = DataHolder.getMasterServerURL_v1();
        client.headers().put("Content-Type", "application/json");
        client.body().put("user_name", user_name);
        client.body().put("usr_pwd", usr_pwd);

        client.post("api/auth/signin");
        User user = ModuleFactory.parse(client, User.class);
        client.validator().assertPositiveResponse(assertPositiveResponse);
        Runtime_DataHolder.setGUID(user.getGuid());
        Runtime_DataHolder.setUID(user.getUid());
        Runtime_DataHolder.setUserName(user.getUser_name());
        Runtime_DataHolder.setAuthToken(user.getAuthToken());

        return user;

    }

    public void perform(Authenticable auth) throws Exception {
        perform("sreehari4a43@gmail.com", "123456@Aa", auth, true);

    }


}
