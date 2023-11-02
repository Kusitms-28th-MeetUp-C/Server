package com.kusitms.mainservice.domain.template.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TemplateService {
//    public List<> getAllData() {
//        List<MySQLTemplate> mySQLDataList = mySQLRepository.findAll();
//        List<> dataCombinations = new ArrayList<>();
//
//        for (MySQLTemplate mySQLTemplate : mySQLTemplateList) {
//            TemplateContent templateContent = mongoDBRepository.findById(mySQLTemplate.getId().toString()).orElse(null);
//            if (templateContent != null) {
//                dataCombinations.add(new MySQLMongoDB(mySQLTemplate, templateContent));
//            }
//        }
//
//        return ;
//    }

}
