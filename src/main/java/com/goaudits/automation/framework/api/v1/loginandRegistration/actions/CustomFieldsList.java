package com.goaudits.automation.framework.api.v1.loginandRegistration.actions;


import com.goaudits.automation.framework.api.API_Client;
import com.goaudits.automation.framework.api.utils.Authenticable;
import com.goaudits.automation.framework.api.utils.DataHolder;
import com.goaudits.automation.framework.api.utils.Runtime_DataHolder;
import com.goaudits.automation.framework.api.v1.Common_Actions;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomFieldsList {
    private final API_Client client;
    private final Common_Actions actions;

    public CustomFieldsList(Common_Actions actions) {
        this.client = actions.masterClient;
        this.actions = actions;
    }

    /**
     * @param assertPositiveResponse If this is true and the response is not successful the test will fail
     * @return The details of required activity
     */
    @Step("SignIn")
    public void perform(String uid, String guid, String active, Authenticable auth, boolean assertPositiveResponse) throws Exception {
        actions.masterClient.serverURL = DataHolder.getMasterServerURL_v1();
        client.headers().put("Content-Type", "application/json");
        client.headers().put("Authorization", "Bearer " + Runtime_DataHolder.getAuthToken());
        client.body().put("uid", uid);
        client.body().put("guid", guid);
        client.body().put("active", active);
        client.post("api/audits/customfield/list");

        client.validator().assertPositiveResponse(assertPositiveResponse);


    }

    public void perform(Authenticable auth) throws Exception {
        perform(Runtime_DataHolder.getUID(), Runtime_DataHolder.getGUID(), "1", auth, true);

    }


}
