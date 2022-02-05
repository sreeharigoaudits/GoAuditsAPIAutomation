package com.goaudits.automation.framework.api.modules.loginAndRegistration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@JsonDeserialize(as = DeviceToken.class)
@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceToken {
    @EqualsAndHashCode.Include @ToString.Include private String device_uid;
    private String mobile_imei, google_ad_id, mobile_device_token,appsflyer_advertising_id;
    
}
