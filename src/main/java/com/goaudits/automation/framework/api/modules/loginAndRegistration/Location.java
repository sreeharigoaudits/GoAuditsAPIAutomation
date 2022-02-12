package com.goaudits.automation.framework.api.modules.loginAndRegistration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@JsonDeserialize(as = Location.class)
@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @EqualsAndHashCode.Include @ToString.Include private String guid;
    private String client_id,client_name,store_id,store_name ,latitude, longitude,radius,active,address,
            uid,logo,  group_id, to_email, cc_email, storemgr_email,location_code,pl_code,time_zone,
            logo_binary,map_address,offset,limit,tags,postcode,gps_location_filter_enabled;

}
