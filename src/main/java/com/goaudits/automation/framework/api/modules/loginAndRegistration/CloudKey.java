package com.goaudits.automation.framework.api.modules.loginAndRegistration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@JsonDeserialize(as = CloudKey.class)
@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloudKey {
    @EqualsAndHashCode.Include @ToString.Include private String guid;
    private String uid,client_id,store_id,audit_type_id, audit_date,folder_path,file_name,signedKey,
            timeStamp,uploadcategory;


}
