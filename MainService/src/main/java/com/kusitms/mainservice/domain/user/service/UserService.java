package com.kusitms.mainservice.domain.user.service;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateRepository;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.dto.response.DetailUserResponseDto;
import com.kusitms.mainservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TemplateRepository templateRepository;
    private final RoadmapRepository roadmapRepository;

    public User getUserByUserId(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.get();
    }
    public DetailUserResponseDto createDetailUserResponseDto(User user){
        int templateNum = getTemplateCountByUser(user);
        int roadmapNum = getRoadmapCountByUser(user);
        return DetailUserResponseDto.of(user, templateNum,roadmapNum);
    }
    private int getTemplateCountByUser(User user) {
        return templateRepository.countByUser(user);
    }
    private int getRoadmapCountByUser(User user) {
        return roadmapRepository.countByUser(user);
    }

}
