package com.goaudits.automation.framework.api.v1.loginandRegistration;


import com.goaudits.automation.framework.api.v1.Common_Actions;
import com.goaudits.automation.framework.api.v1.loginandRegistration.actions.SignIn;


public class LoginAndRegistration_Actions {


    public final SignIn signIn;


    public LoginAndRegistration_Actions(Common_Actions actions) {

        this.signIn = new SignIn(actions);

    }
}
