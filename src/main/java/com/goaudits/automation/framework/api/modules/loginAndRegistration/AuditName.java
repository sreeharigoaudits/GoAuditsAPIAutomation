package com.goaudits.automation.framework.api.modules.loginAndRegistration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@JsonDeserialize(as = AuditName.class)
@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditName {
    @EqualsAndHashCode.Include @ToString.Include private String guid;
    private String client_id,client_name,audit_group_id,audit_group_name ,audit_type_id, audit_type_name,inputLogo,active,logo,
            uid,signature1_label,  signature2_label, signature3_label, sign1_flag, sign2_flag,sign3_flag,
            is_man_sign1,is_man_sign2,is_man_sign3,group_id,images_peraudit,images_perquestion,pl_code,person_seen,
            person_seen_mandatory,showif_optional,audit_type_title,sort_order,drag_index,drop_index,image_required,hide_signature_app,count;


}
