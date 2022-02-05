package com.goaudits.automation.framework.api.v1.loginandRegistration;


import com.goaudits.automation.framework.api.modules.loginAndRegistration.User;
import com.goaudits.automation.framework.api.utils.Authenticable;
import com.goaudits.automation.framework.api.utils.Enumerations;
import com.goaudits.automation.framework.api.v1.Common_Actions;

public class LoginAndRegistrationBuilder {
    public static Builder builder(Common_Actions actions) {
        return new Builder(actions);
    }

    public static Builder builder(Common_Actions actions, Authenticable auth) {
        return new Builder(actions, auth);
    }

    public static class Builder {
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





    }
}
