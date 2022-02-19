package com.goaudits.automation.framework.api.v1.loginandRegistration.actions;


import com.goaudits.automation.framework.api.API_Client;
import com.goaudits.automation.framework.api.modules.loginAndRegistration.Company;
import com.goaudits.automation.framework.api.modules.loginAndRegistration.User;
import com.goaudits.automation.framework.api.utils.Authenticable;
import com.goaudits.automation.framework.api.utils.DataHolder;
import com.goaudits.automation.framework.api.utils.ModuleFactory;
import com.goaudits.automation.framework.api.utils.Runtime_DataHolder;
import com.goaudits.automation.framework.api.v1.Common_Actions;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CompanyList {
    private final API_Client client;
    private final Common_Actions actions;

    public CompanyList(Common_Actions actions) {
        this.client = actions.masterClient;
        this.actions = actions;
    }

    /**
     * @param assertPositiveResponse If this is true and the response is not successful the test will fail
     * @return The details of required activity
     */
    @Step("Company List")
    public Company perform(String uid, String guid, String active, String image_required, Authenticable auth, boolean assertPositiveResponse) throws Exception {
        actions.masterClient.serverURL = DataHolder.getMasterServerURL_v1();
        client.headers().put("Content-Type", "application/json");
        client.headers().put("Authorization", "Bearer " + Runtime_DataHolder.getAuthToken());
        client.body().put("uid", uid);
        client.body().put("guid", guid);
        client.body().put("active", active);
        client.body().put("image_required", image_required);
        client.post("api/audits/company/list");
        Company company = ModuleFactory.parse(client.jsonResponseArray.get(0).getAsJsonObject(), Company.class);

        client.validator().assertPositiveResponse(assertPositiveResponse);
        Runtime_DataHolder.setCompanyID(company.getClient_id());
        Runtime_DataHolder.setCompanyName(company.getClient_name());

return company;
    }

    public void perform(Authenticable auth) throws Exception {
        perform(Runtime_DataHolder.getUID(),Runtime_DataHolder.getGUID(),"1", "false", auth, true);

    }


}
