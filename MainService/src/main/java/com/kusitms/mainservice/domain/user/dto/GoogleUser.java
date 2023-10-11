package com.kusitms.mainservice.domain.user.dto;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;




@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class GoogleUser {
    @Id
    private String id;
    private String email;
    private Boolean verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;
}

