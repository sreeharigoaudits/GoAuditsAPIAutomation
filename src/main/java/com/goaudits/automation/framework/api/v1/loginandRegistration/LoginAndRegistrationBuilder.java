package com.goaudits.automation.framework.api.v1.loginandRegistration;


import com.goaudits.automation.framework.api.modules.loginAndRegistration.User;
import com.goaudits.automation.framework.api.utils.Authenticable;
import com.goaudits.automation.framework.api.utils.Enumerations;
import com.goaudits.automation.framework.api.utils.Runtime_DataHolder;
import com.goaudits.automation.framework.api.v1.Common_Actions;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.RandomStringUtils;

public class LoginAndRegistrationBuilder {

    public static Builder builder(Common_Actions actions) {
        return new Builder(actions);
    }

    public static Builder builder(Common_Actions actions, Authenticable auth) {
        return new Builder(actions, auth);
    }

    public static class Builder {
        String sectionId;
        String groupId;
        String questionNo;
        int NoOfQuestions;
        String QuestionText;
        String ChoiceType;
        String ChoicePatternID;
        String ChoiceTypeID;
        int noOfSection;
        private Common_Actions actions;
        private User user = new User();
        private Authenticable authenticable = Enumerations.ClientType.SUPER_ADMIN;

        Builder(Common_Actions actions) {
            this.actions = actions;
        }

        Builder(Common_Actions actions, Authenticable auth) {
            this.actions = actions;
            this.authenticable = auth;
        }

        public User build() {
            return user;
        }

        public Builder setAuth(Authenticable auth) {
            this.authenticable = auth;
            return this;
        }


        public Builder signIn() throws Exception {
            actions.loginAndRegistration_actions.signIn.perform(authenticable);
            return this;
        }

        public Builder companyList() throws Exception {
            actions.loginAndRegistration_actions.companyList.perform(authenticable);
            return this;
        }

        public Builder myAudits() throws Exception {
            actions.loginAndRegistration_actions.myAudits.perform(authenticable);
            return this;
        }

        public Builder locationList() throws Exception {
            actions.loginAndRegistration_actions.locationList.perform(authenticable);
            return this;
        }

        public Builder customFieldList() throws Exception {
            actions.loginAndRegistration_actions.customFieldsList.perform(authenticable);
            return this;
        }

        public Builder auditNameList() throws Exception {
            actions.loginAndRegistration_actions.auditNameList.perform(authenticable);
            return this;
        }

        public Builder createAudit() throws Exception {
            actions.loginAndRegistration_actions.createAudit.perform(authenticable);
            return this;
        }

        public Builder sectionList() throws Exception {
            actions.loginAndRegistration_actions.sectionList.perform(authenticable);
            return this;
        }

        public Builder getCloudKey() throws Exception {
            actions.loginAndRegistration_actions.getCloudKey.perform(authenticable);
            return this;
        }

        public Builder questionList(String section_id) throws Exception {
            actions.loginAndRegistration_actions.questionList.perform(section_id, authenticable);
            return this;
        }

        public Builder questionSave(String section_id, String group_id, String question_no, String choice_pat_id, String choice_id, String comments, String free_text, String number_value, String date_time, String temperature_value, String date_value) throws Exception {
            actions.loginAndRegistration_actions.questionSave.perform(section_id, group_id, question_no, choice_pat_id, choice_id, comments, free_text, number_value, date_time, temperature_value, date_value, authenticable);


            return this;
        }

        public Builder getCommentsAndSign() throws Exception {
            actions.loginAndRegistration_actions.getCommentsAndSign.perform(authenticable);
            return this;
        }

        public Builder CommentsAndSignSave() throws Exception {
            actions.loginAndRegistration_actions.commentsAndSignSave.perform(authenticable);
            return this;
        }

        public Builder previewOpenQuestions() throws Exception {
            actions.loginAndRegistration_actions.previewOpenQuestions.perform(authenticable);
            return this;
        }

        public Builder getPdf() throws Exception {
            actions.loginAndRegistration_actions.getPdf.perform(authenticable);
            return this;
        }

        public Builder emailList() throws Exception {
            actions.loginAndRegistration_actions.emailList.perform(authenticable);
            return this;
        }

        public Builder auditFinalSubmission() throws Exception {
            actions.loginAndRegistration_actions.auditSubmission.perform(authenticable);
            return this;
        }

        public Builder performAudit() throws Exception {
            signIn();
            myAudits();
            companyList();
            locationList();
            customFieldList();
            auditNameList();
            createAudit();
            sectionList();
            getCloudKey();
            noOfSection = Runtime_DataHolder.getNoOfSections();

            for (int j = 0; j <= noOfSection; j++) {
                String sectionCount = Integer.toString(j);
                questionList(sectionCount);


                for (JsonElement jsonElement : Runtime_DataHolder.getJsonArrayObject()) {
                    NoOfQuestions = jsonElement.getAsJsonObject().get("questionList").getAsJsonArray().size();
                    Runtime_DataHolder.setNoofQuestions(NoOfQuestions);
                    for (int i = 0; i < NoOfQuestions; i++) {
                        sectionId = jsonElement.getAsJsonObject().get("section_id").getAsString();
                        groupId = jsonElement.getAsJsonObject().get("group_id").getAsString();
                        questionNo = jsonElement.getAsJsonObject().get("questionList").getAsJsonArray().get(i).getAsJsonObject().get("question_no").getAsString();
                        QuestionText = jsonElement.getAsJsonObject().get("questionList").getAsJsonArray().get(i).getAsJsonObject().get("question_text").getAsString();
                        ChoiceType = jsonElement.getAsJsonObject().get("questionList").getAsJsonArray().get(i).getAsJsonObject().get("choice_type").getAsString();
                        ChoicePatternID = jsonElement.getAsJsonObject().get("questionList").getAsJsonArray().get(i).getAsJsonObject().get("choiceList").getAsJsonArray().get(0).getAsJsonObject().get("choice_pat_id").getAsString();
                        ChoiceTypeID = jsonElement.getAsJsonObject().get("questionList").getAsJsonArray().get(i).getAsJsonObject().get("choiceList").getAsJsonArray().get(0).getAsJsonObject().get("choice_id").getAsString();

                        questionSave(sectionId, groupId, questionNo, ChoicePatternID, ChoiceTypeID, RandomStringUtils.randomAlphanumeric(30), "", null, null, null, null);
                    }

                }
            }
            getCommentsAndSign();
            CommentsAndSignSave();
            previewOpenQuestions();
            getPdf();
            emailList();
            auditFinalSubmission();
            return this;
        }

    }
}
