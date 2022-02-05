package com.goaudits.automation.framework.api.modules.Documents;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@JsonDeserialize(as = Document.class)
@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @EqualsAndHashCode.Include @ToString.Include private String actual_path;
    private String file_type, media_path;
}
