package com.kusitms.socketservice.domain.search.controller;

import com.kusitms.socketservice.domain.search.dto.request.SearchRequestDto;
import com.kusitms.socketservice.domain.search.dto.response.SearchResultResponseDto;
import com.kusitms.socketservice.domain.search.service.SearchService;
import com.kusitms.socketservice.global.common.MessageSuccessCode;
import com.kusitms.socketservice.global.common.MessageSuccessResponse;
import com.kusitms.socketservice.global.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SearchController {
    private final SimpMessagingTemplate template;
    private final SearchService searchService;

    @MessageMapping("/search")
    public void getSearchResult(@UserId Long userId,
                                @Header("sessionId") final String sessionId,
                                final SearchRequestDto searchRequestDto) {
        final SearchResultResponseDto responseDto = searchService.getSearchResult(userId, searchRequestDto);
        template.convertAndSend("/sub/search/" + sessionId, MessageSuccessResponse.of(MessageSuccessCode.SEARCH, responseDto));
    }
}