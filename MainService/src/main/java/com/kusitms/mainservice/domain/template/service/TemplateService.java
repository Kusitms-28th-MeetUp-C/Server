//package com.kusitms.mainservice.domain.template.service;
//
//import com.kusitms.mainservice.domain.template.domain.TemplateContent;
//import com.kusitms.mainservice.domain.template.domain.MySQLTemplate;
//import com.kusitms.mainservice.domain.template.repository.MakerRepository;
//import com.kusitms.mainservice.domain.template.repository.MongoDBRepository;
//import com.kusitms.mainservice.domain.template.repository.MongoRepository;
//import com.kusitms.mainservice.domain.template.repository.MySQLRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@RequiredArgsConstructor
//@Transactional
//@Service
//public class TemplateService {
//    private final MySQLRepository mySQLRepository;
//    private final MongoDBRepository mongoDBRepository;
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
//}
