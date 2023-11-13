package com.kusitms.mainservice.domain.template.dto.response;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveTemplateResponseDto {
    private Long templateid;
    private Long userid;

public static SaveTemplateResponseDto of(Long templateid,Long userid){
    return SaveTemplateResponseDto.builder()
            .templateid(templateid)
            .userid(userid)
            .build();
}
}
