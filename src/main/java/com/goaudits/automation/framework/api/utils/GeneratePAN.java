package com.goaudits.automation.framework.api.utils;

import com.mifmif.common.regex.Generex;

public class GeneratePAN {
    public static String generatePAN(){
        String pan_no = null;
        Generex generex = new Generex("[A-Z]{3}P[A-Z]{1}[0-9]{4}[A-Z]{1}");
        pan_no = generex.random();
        return pan_no;
    }
}
