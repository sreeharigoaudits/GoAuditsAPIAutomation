package com.goaudits.automation.framework.api.modules.loginAndRegistration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.lang.reflect.Array;

@JsonDeserialize(as = User.class)
@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @EqualsAndHashCode.Include @ToString.Include private String user_name;
    private String authToken, guid, uid, app_access, active, schedule_count, is_enable_schedule, message, role_type_code;
    private String days_remaining, fullname, is_cstore, license_type, subscription_model, spec_char_allowed, lockout_user, expiry_required, lockout_count,expiry_months,password_policy,password_modified,pass_fail_count,strict_password_policy,character_count,qr_registered,using2fa;
}
