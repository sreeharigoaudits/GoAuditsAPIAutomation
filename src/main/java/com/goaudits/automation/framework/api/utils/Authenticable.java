package com.goaudits.automation.framework.api.utils;


import com.goaudits.automation.framework.api.modules.loginAndRegistration.User;


public interface Authenticable {

    User getUser();
    void setUser(User user);

}
