package com.goaudits.automation.framework.api.v1.loginandRegistration.actions;


import com.goaudits.automation.framework.api.API_Client;
import com.goaudits.automation.framework.api.utils.Authenticable;
import com.goaudits.automation.framework.api.utils.DataHolder;
import com.goaudits.automation.framework.api.utils.DateUtils;
import com.goaudits.automation.framework.api.utils.Runtime_DataHolder;
import com.goaudits.automation.framework.api.v1.Common_Actions;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuestionSave {
    private final API_Client client;
    private final Common_Actions actions;

    public QuestionSave(Common_Actions actions) {
        this.client = actions.masterClient;
        this.actions = actions;
    }

    /**
     * @param assertPositiveResponse If this is true and the response is not successful the test will fail
     * @return The details of required activity
     */
    @Step("Question Save")
    public void perform(String uid, String guid, String client_id, String audit_type_id,String section_id,String group_id,String seq_no,String question_no,String choice_pat_id,String choice_id,
                        String comments, String free_text, String number_value, String date_time,String temperature_value,String date_value, String is_multichoice, String audit_date, String store_id,
                        Authenticable auth, boolean assertPositiveResponse) throws Exception {
        actions.masterClient.serverURL = DataHolder.getMasterServerURL_v1();
        client.headers().put("Content-Type", "application/json");
        client.headers().put("Authorization", "Bearer " + Runtime_DataHolder.getAuthToken());
        client.body().put("uid", uid);
        client.body().put("guid", guid);
        client.body().put("client_id", client_id);
        client.body().put("audit_type_id", audit_type_id);
        client.body().put("section_id", section_id);
        client.body().put("group_id", group_id);
        client.body().put("seq_no", seq_no);
        client.body().put("question_no", question_no);
        client.body().put("choice_pat_id", choice_pat_id);
        client.body().put("choice_id", choice_id);
        client.body().put("comments", comments);
        client.body().put("free_text", free_text);
        client.body().put("number_value", number_value);
        client.body().put("date_time", date_time);
        client.body().put("temperature_value", temperature_value);
        client.body().put("date_value", date_value);
        client.body().put("is_multichoice", is_multichoice);
        client.body().put("audit_date", audit_date);
        client.body().put("store_id", store_id);


        client.post("api/audits/question/save");


        client.validator().assertPositiveResponse(assertPositiveResponse);


    }

    public void perform(Authenticable auth) throws Exception {
        perform(Runtime_DataHolder.getUID(),Runtime_DataHolder.getGUID(),Runtime_DataHolder.getCompanyID(),Runtime_DataHolder.getAuditNameID(),"1","1",Runtime_DataHolder.getSeqNo(),"1","1","1","first Question commetns","",null,null,null,null,"false" ,DateUtils.getCurrentDate(),Runtime_DataHolder.getLocationID(), auth, true);

    }


}
