package com.kusitms.socketservice.sevice;

import com.kusitms.socketservice.domain.MeetingRoom;
import com.kusitms.socketservice.dto.request.MessageRequestDto;
import com.kusitms.socketservice.global.error.socketException.EntityNotFoundException;
import com.kusitms.socketservice.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kusitms.socketservice.global.error.ErrorCode.MEETING_NOT_FOUND;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MessageService {
    private final MeetingRepository meetingRepository;

    public void sendEmojiMessageResponse(MessageRequestDto messageResponseDto) {
        MeetingRoom meetingRoom = getMeetingRoomWithId(messageResponseDto.getMeetingId());
    }

    private MeetingRoom getMeetingRoomWithId(Long meetingId) {
        return meetingRepository.findById(meetingId)
                .orElseThrow(() -> new EntityNotFoundException(MEETING_NOT_FOUND));
    }
}
