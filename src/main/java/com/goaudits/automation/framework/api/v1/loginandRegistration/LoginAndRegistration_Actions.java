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

    public LoginAndRegistration_Actions(Common_Actions actions) {

        this.signIn = new SignIn(actions);
        this.companyList = new CompanyList(actions);
        this.myAudits = new MyAudits(actions);
        this.locationList = new LocationList(actions);
        this.customFieldsList = new CustomFieldsList(actions);
        this.auditNameList = new AuditNameList(actions);
        this.createAudit=new CreateAudit(actions);
    }
}
