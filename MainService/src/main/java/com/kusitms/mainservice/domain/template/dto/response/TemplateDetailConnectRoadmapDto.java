package com.kusitms.mainservice.domain.template.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TemplateDetailConnectRoadmapDto {
    private Long roadmapId;
    private String connectedRoadmap;
    public static TemplateDetailConnectRoadmapDto of(String connectedRoadmap, Long roadmapId){
        return TemplateDetailConnectRoadmapDto.builder()
                .roadmapId(roadmapId)
                .connectedRoadmap(connectedRoadmap)
                .build();
    }

}
