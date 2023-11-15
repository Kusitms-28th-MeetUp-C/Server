package com.kusitms.mainservice.domain.template.dto.response;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveTemplateResponseDto {
    private Long templateId;
    private Long userId;

public static SaveTemplateResponseDto of(Long templateId,Long userId){
    return SaveTemplateResponseDto.builder()
            .templateId(templateId)
            .userId(userId)
            .build();
}
}
