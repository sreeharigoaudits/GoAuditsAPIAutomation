package com.goaudits.automation.framework.api.v1.loginandRegistration;


import com.goaudits.automation.framework.api.v1.Common_Actions;
import com.goaudits.automation.framework.api.v1.loginandRegistration.actions.*;


public class LoginAndRegistration_Actions {


    public final SignIn signIn;
    public final CompanyList companyList;
    public final MyAudits myAudits;
    public final LocationList locationList;
    public final CustomFieldsList customFieldsList;
    public final AuditNameList auditNameList;
    public final CreateAudit createAudit;
    public final SectionList sectionList;
    public final GetCloudKey getCloudKey;
    public final QuestionList questionList;
    public final QuestionSave questionSave;
    public final GetCommentsAndSign getCommentsAndSign;
    public final CommentsAndSignSave commentsAndSignSave;
    public final PreviewOpenQuestions previewOpenQuestions;
    public final GetPdf getPdf;
    public final EmailList emailList;
    public final AuditSubmission auditSubmission;
    public LoginAndRegistration_Actions(Common_Actions actions) {

        this.signIn = new SignIn(actions);
        this.companyList = new CompanyList(actions);
        this.myAudits = new MyAudits(actions);
        this.locationList = new LocationList(actions);
        this.customFieldsList = new CustomFieldsList(actions);
        this.auditNameList = new AuditNameList(actions);
        this.createAudit=new CreateAudit(actions);
        this.sectionList=new SectionList(actions);
        this.getCloudKey=new GetCloudKey(actions);
        this.questionList=new QuestionList(actions);
        this.questionSave=new QuestionSave(actions);
        this.getCommentsAndSign=new GetCommentsAndSign(actions);
        this.commentsAndSignSave=new CommentsAndSignSave(actions);
        this.previewOpenQuestions=new PreviewOpenQuestions(actions);
        this.getPdf=new GetPdf(actions);
        this.emailList=new EmailList(actions);
        this.auditSubmission=new AuditSubmission(actions);

    }
}
