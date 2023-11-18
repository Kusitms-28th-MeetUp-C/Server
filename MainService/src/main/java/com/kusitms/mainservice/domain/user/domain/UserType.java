package com.kusitms.mainservice.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserType {
    PM,
    마케터,
    디자이너,
    개발자,
    기획자,
    에디터
}
