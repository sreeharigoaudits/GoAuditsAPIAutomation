package com.goaudits.automation.api.tests;


import com.goaudits.automation.framework.api.modules.loginAndRegistration.User;
import com.goaudits.automation.framework.api.utils.Authenticable;
import com.goaudits.automation.framework.api.utils.DataHolder;
import com.goaudits.automation.framework.api.utils.Enumerations;
import com.goaudits.automation.framework.api.v1.Common_Actions;
import com.goaudits.automation.framework.api.v1.loginandRegistration.LoginAndRegistrationBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeSuite;

public class setUp {
    private Common_Actions actions;
    Authenticable authenticable = Enumerations.ClientType.SUPER_ADMIN;

    @BeforeSuite(description = "Test_Data Creation")
    public void init() throws Exception {
        this.actions = new Common_Actions();
      //  AuthToken authToken = actions.loginAndRegistration_actions.getAuthToken.perform();
     //   Runtime_DataHolder.setAuthToken(authToken.getAccess_token());
      //  AuthToken adminAuthToken = actions.loginAndRegistration_actions.getAdminAuthToken.perform();
        //Runtime_DataHolder.setAdminAuthToken(adminAuthToken.getAccess_token());

        // Create a user and login as the newly created user
/*        String student_user = "6161" + RandomStringUtils.random(6, "123456789");
        String salaried_user = "6161" + RandomStringUtils.random(6, "123456789");
        LoginAndRegistrationBuilder.builder(actions, Enumerations.ClientType.STUDENT_USER).registerAndLoginAsStudentUser(student_user).build();
        LoginAndRegistrationBuilder.builder(actions, Enumerations.ClientType.SALARIED_USER).registerAndLoginAsSalariedUser(salaried_user).build();*/

        //Login as Admins (all types of admins)
     //   AdminUserBuilder.builder(actions).loginAsSuperAdmin().loginAsSubAdmin().loginAsApproverAdmin().loginAsRmApproverAdmin().loginAsSalariedApproverAdmin().loginAsSalariedRmApproverAdmin().loginAsScholarshipApproverAdmin().build();//.loginAsRmCallerAdmin().
//                loginAsRmApproverAdmin().loginAsFinanceAdmin().loginAsReKycCallerAdmin().loginAsReKycApproverAdmin().loginAsCollectionManagerAdmin().loginAsCollectionManagerRCAdmin().loginAsCollectionManagerHVODAdmin().
//                loginAsLRMAdmin().loginAsValidatorAdmin().loginAsLRMAdmin().build();


       // Enumerations.ClientType.EXPIRED_ADMIN.setAdminUser(adminUser);

     //   User user = new User();
     //   user.setPasskey(DataHolder.getExpiredUserToken());
     //   Enumerations.ClientType.EXPIRED_USER.setUser(user);
    }

/*    @AfterSuite(description = "Deleting the Resources & Closing the Connections")
    public void tearDown() throws SQLException {
        if(DB_Utils.getMysql_connection()!=null){
            DB_Utils.getMysql_connection().close();
        }

        if(DB_Utils.getRoach_connection()!=null) {
            DB_Utils.getRoach_connection().close();
        }
    }*/
}