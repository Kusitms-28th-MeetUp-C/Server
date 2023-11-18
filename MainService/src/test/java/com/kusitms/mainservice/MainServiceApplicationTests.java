package com.kusitms.mainservice;

import com.kusitms.mainservice.domain.template.domain.TemplateContent;
import com.kusitms.mainservice.domain.template.mongoRepository.TemplateContentRepository;
import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
class MainServiceApplicationTests {
    @Autowired
    TemplateContentRepository templateContentRepository;
    @Autowired
    private MongoClient mongoClient;
    @Test
    void contextLoads() {




// ...


//            String host = mongoClient.getClusterDescription().getClusterSettings().getSrvHost();
//            int port = mongoClient.getClusterDescription().getClusterSettings().getHosts().get(0).getPort();
//            String databaseName = mongoClient.getDatabase("meetful").getName();
//            // 연결 정보 출력
//            System.out.println("MongoDB Host: " + host);
//            System.out.println("MongoDB Port: " + port);
//            System.out.println("Database Name: " + databaseName);

        TemplateContent templateContent = new TemplateContent();


        templateContent.setTemplateId(1L);
        templateContent.setContent("내용2");

        templateContentRepository.save(templateContent);
    }

}
