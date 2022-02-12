package com.goaudits.automation.framework.api.modules.loginAndRegistration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@JsonDeserialize(as = Company.class)
@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @EqualsAndHashCode.Include @ToString.Include private String guid;
    private String client_id,client_name,submit_button_text,inputLogo ,active, logo,logo_binary,uid,short_name, sort_order,drag_index,  drop_index, image_required, company_clone, count,photo_size_web;

}
