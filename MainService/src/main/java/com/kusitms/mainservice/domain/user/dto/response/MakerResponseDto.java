package com.kusitms.mainservice.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MakerResponseDto {
    String name;
    int templateCount;
    int roadmapCount;
    Long sessionId;

    public static MakerResponseDto of(String name, int templateCount, int roadmapCount, Long sessionId) {
        return MakerResponseDto.builder()
                .name(name)
                .templateCount(templateCount)
                .roadmapCount(roadmapCount)
                .sessionId(sessionId)
                .build();
    }
}
