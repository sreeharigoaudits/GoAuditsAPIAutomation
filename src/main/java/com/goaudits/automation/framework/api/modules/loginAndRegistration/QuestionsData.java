package com.goaudits.automation.framework.api.modules.loginAndRegistration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@JsonDeserialize(as = QuestionsData.class)
@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsData {
    @EqualsAndHashCode.Include @ToString.Include private String section_id;
    private String group_id,group_name,client_name,audit_type_name ,store_name, photo_size_web,images_perquestion;

}
