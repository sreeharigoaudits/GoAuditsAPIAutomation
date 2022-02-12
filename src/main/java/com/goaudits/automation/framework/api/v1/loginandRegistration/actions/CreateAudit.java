package com.goaudits.automation.framework.api.v1.loginandRegistration.actions;


import com.goaudits.automation.framework.api.API_Client;
import com.goaudits.automation.framework.api.modules.loginAndRegistration.CreateNewAudit;
import com.goaudits.automation.framework.api.utils.*;
import com.goaudits.automation.framework.api.v1.Common_Actions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateAudit {
    private final API_Client client;
    private final Common_Actions actions;

    public CreateAudit(Common_Actions actions) {
        this.client = actions.masterClient;
        this.actions = actions;
    }

    /**
     * @param assertPositiveResponse If this is true and the response is not successful the test will fail
     * @return The details of required activity
     */
    @Step("Create Audit")
    public CreateNewAudit perform(String guid, String uid, String client_id, String audit_type_id, String store_id, String audit_date, String person_seen, String auditor_name, String fin_year,
                                  String quarter, String latitude, String longitude, String start_date, String end_date, String parent_audit_id, String group_audit_id, String status_id, String schedule_uuid, String type_of_audit, String customFieldsList, Authenticable auth, boolean assertPositiveResponse) throws Exception {
        actions.masterClient.serverURL = DataHolder.getMasterServerURL_v1();
        client.headers().put("Content-Type", "application/json");
        client.headers().put("Authorization", "Bearer " + Runtime_DataHolder.getAuthToken());


        JsonArray jsonArray = new JsonArray();

        JsonObject finalObj = new JsonObject();

        finalObj.addProperty("guid", guid);
        finalObj.addProperty("uid", uid);
        finalObj.addProperty("client_id", client_id);
        finalObj.addProperty("audit_type_id", audit_type_id);
        finalObj.addProperty("store_id", store_id);
        finalObj.addProperty("audit_date", audit_date);
        finalObj.addProperty("person_seen", person_seen);
        finalObj.addProperty("auditor_name", auditor_name);
        finalObj.addProperty("fin_year", fin_year);
        finalObj.addProperty("quarter", quarter);
        finalObj.addProperty("latitude", latitude);
        finalObj.addProperty("longitude", longitude);
        finalObj.addProperty("start_date", start_date);
        finalObj.addProperty("end_date", end_date);
        finalObj.addProperty("parent_audit_id", parent_audit_id);
        finalObj.addProperty("group_audit_id", group_audit_id);
        finalObj.addProperty("status_id", status_id);
        finalObj.addProperty("schedule_uuid", schedule_uuid);
        finalObj.addProperty("type_of_audit", type_of_audit);
        finalObj.add("customFieldsList", jsonArray);

        client.post("api/audits/create/audit", finalObj);

        client.validator().assertPositiveResponse(assertPositiveResponse);
        CreateNewAudit createAudit = ModuleFactory.parse(client.jsonResponseArray.get(0).getAsJsonObject(), CreateNewAudit.class);

        client.validator().assertPositiveResponse(assertPositiveResponse);
        Runtime_DataHolder.setSeqNo(createAudit.getSeq_no());

        return createAudit;
    }

    public void perform(Authenticable auth) throws Exception {
        perform(Runtime_DataHolder.getGUID(), Runtime_DataHolder.getUID(), Runtime_DataHolder.getCompanyID(), Runtime_DataHolder.getAuditNameID(), Runtime_DataHolder.getLocationID(), DateUtils.getCurrentDate(), "", "sre sree", "", "", "0", "0", DateUtils.getCurrentDateTime(), DateUtils.getCurrentDateTime(), "0", "0", "0", "", "0", "", auth, true);

    }


}
