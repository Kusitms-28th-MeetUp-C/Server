package com.kusitms.socketservice.domain.search.repository;

import com.kusitms.socketservice.domain.search.domain.SearchUserTemplate;

import java.util.List;

public interface SearchRepository {
    List<SearchUserTemplate> findAllBySearchText(String searchText, String userId);

    List<SearchUserTemplate> findAllByUserId(String userId);
}
