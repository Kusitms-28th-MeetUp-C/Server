package com.kusitms.socketservice.repository;

import com.kusitms.socketservice.domain.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<MeetingRoom, Long> {
}
