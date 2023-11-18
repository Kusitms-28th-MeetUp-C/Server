package com.kusitms.socketservice.domain.chat.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private String platformId;
    private String email;
    private String name;
    private String profile;
    private String refreshToken;
}
