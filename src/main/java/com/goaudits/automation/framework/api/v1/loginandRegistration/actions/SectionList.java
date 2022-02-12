package com.goaudits.automation.framework.api.v1.loginandRegistration.actions;


import com.goaudits.automation.framework.api.API_Client;
import com.goaudits.automation.framework.api.modules.loginAndRegistration.Company;
import com.goaudits.automation.framework.api.utils.*;
import com.goaudits.automation.framework.api.v1.Common_Actions;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SectionList {
    private final API_Client client;
    private final Common_Actions actions;

    public SectionList(Common_Actions actions) {
        this.client = actions.masterClient;
        this.actions = actions;
    }

    /**
     * @param assertPositiveResponse If this is true and the response is not successful the test will fail
     * @return The details of required activity
     */
    @Step("Section List")
    public void perform(String uid, String guid, String client_id, String audit_type_id,String store_id,String audit_date,String seq_no, Authenticable auth, boolean assertPositiveResponse) throws Exception {
        actions.masterClient.serverURL = DataHolder.getMasterServerURL_v1();
        client.headers().put("Content-Type", "application/json");
        client.headers().put("Authorization", "Bearer " + Runtime_DataHolder.getAuthToken());
        client.body().put("uid", uid);
        client.body().put("guid", guid);
        client.body().put("client_id", client_id);
        client.body().put("audit_type_id", audit_type_id);
        client.body().put("store_id", store_id);
        client.body().put("audit_date", audit_date);
        client.body().put("seq_no", seq_no);
        client.post("api/audits/section/list");


        client.validator().assertPositiveResponse(assertPositiveResponse);


    }

    public void perform(Authenticable auth) throws Exception {
        perform(Runtime_DataHolder.getUID(),Runtime_DataHolder.getGUID(),Runtime_DataHolder.getCompanyID(),Runtime_DataHolder.getAuditNameID(),Runtime_DataHolder.getLocationID(), DateUtils.getCurrentDate(),Runtime_DataHolder.getSeqNo(), auth, true);

    }


}
