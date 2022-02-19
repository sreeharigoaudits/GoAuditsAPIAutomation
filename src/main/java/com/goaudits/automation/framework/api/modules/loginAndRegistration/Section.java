package com.goaudits.automation.framework.api.modules.loginAndRegistration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.util.ArrayList;

@JsonDeserialize(as = Section.class)
@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Section {
    @EqualsAndHashCode.Include @ToString.Include private String guid;
    private String uid,client_id,client_name,audit_type_id ,audit_type_name, section_id,section_name,active,store_name, store_id,section_help,  help_color, is_help_bold, is_help_italic, help_text_position,question_no,
            seq_no,audit_date,group_id,section_comments,open_questions,question_mandatory,image_mandatory,comment_mandatory,action_mandatory,choice_text,section_answered_count,section_question_count,section_progress_percentage,audit_answered_count,audit_question_count,audit_progress_percentage;
    private ArrayList groupList;

}
