package com.kusitms.mainservice.domain.mypage.dto.response;

import com.kusitms.mainservice.domain.roadmap.dto.response.SearchBaseRoadmapResponseDto;
import com.kusitms.mainservice.domain.user.dto.response.DetailUserResponseDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
public class NotMyPageRoadmapResponseDto {
    private DetailUserResponseDto user;
    private Page<SearchBaseRoadmapResponseDto> roadmapList;
    public static NotMyPageRoadmapResponseDto of(DetailUserResponseDto user, Page<SearchBaseRoadmapResponseDto> roadmapList ){
        return NotMyPageRoadmapResponseDto.builder()
                .user(user)
                .roadmapList(roadmapList)
                .build();
    }
}
