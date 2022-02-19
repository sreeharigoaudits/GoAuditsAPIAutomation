package com.goaudits.automation.framework.api.v1.loginandRegistration.actions;


import com.goaudits.automation.framework.api.API_Client;
import com.goaudits.automation.framework.api.utils.Authenticable;
import com.goaudits.automation.framework.api.utils.DataHolder;
import com.goaudits.automation.framework.api.utils.DateUtils;
import com.goaudits.automation.framework.api.utils.Runtime_DataHolder;
import com.goaudits.automation.framework.api.v1.Common_Actions;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

@Slf4j
public class CommentsAndSignSave {
    private final API_Client client;
    private final Common_Actions actions;

    public CommentsAndSignSave(Common_Actions actions) {
        this.client = actions.masterClient;
        this.actions = actions;
    }

    /**
     * @param assertPositiveResponse If this is true and the response is not successful the test will fail
     * @return The details of required activity
     */
    @Step("Comments and Sign Save")
    public void perform(String uid, String guid, String client_id, String audit_type_id,String audit_date,String store_id,String seq_no,String comments,String auditor_name,String auditor_signature,
                        String signature2_text, String signature2, String signature3_text, String signature3,
                        Authenticable auth, boolean assertPositiveResponse) throws Exception {
        actions.masterClient.serverURL = DataHolder.getMasterServerURL_v1();
        client.headers().put("Content-Type", "application/json");
        client.headers().put("Authorization", "Bearer " + Runtime_DataHolder.getAuthToken());
        client.body().put("uid", uid);
        client.body().put("guid", guid);
        client.body().put("client_id", client_id);
        client.body().put("audit_type_id", audit_type_id);
        client.body().put("audit_date", audit_date);
        client.body().put("store_id", store_id);
        client.body().put("seq_no", seq_no);
        client.body().put("comments", comments);
        client.body().put("auditor_name", auditor_name);
        client.body().put("auditor_signature", auditor_signature);
        client.body().put("signature2_text", signature2_text);
        client.body().put("signature2", signature2);
        client.body().put("signature3_text", signature3_text);
        client.body().put("signature3", signature3);


        client.post("api/audits/savecommentsign");


        client.validator().assertPositiveResponse(assertPositiveResponse);


    }

    public void perform(Authenticable auth) throws Exception {
        perform(Runtime_DataHolder.getUID(),Runtime_DataHolder.getGUID(),Runtime_DataHolder.getCompanyID(),Runtime_DataHolder.getAuditNameID(),DateUtils.getCurrentDate(),"1",Runtime_DataHolder.getSeqNo(),"Over All Audit Comments", RandomStringUtils.randomAlphanumeric(10),"","null","","null","", auth, true);

    }


}
