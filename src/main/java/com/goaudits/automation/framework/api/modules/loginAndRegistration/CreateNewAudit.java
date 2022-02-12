package com.goaudits.automation.framework.api.modules.loginAndRegistration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@JsonDeserialize(as = CreateNewAudit.class)
@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewAudit {
    @EqualsAndHashCode.Include @ToString.Include private String guid;
    private String uid,client_id,audit_type_id,store_id,audit_date ,seq_no, schedule_uuid,type_of_audit,hide_signature_app;

}
