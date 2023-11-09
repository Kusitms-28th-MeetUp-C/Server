package com.kusitms.mainservice;

import com.kusitms.mainservice.domain.template.domain.TemplateContent;
import com.kusitms.mainservice.domain.template.repository.TemplateContentRepository;
import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

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

        Optional<TemplateContent> opTemplateContent = Optional.of(new TemplateContent());
        TemplateContent templateContent = opTemplateContent.get();

        templateContent.setId("5");
        templateContent.setContent("테스트5");
        templateContent.setIntroduction("테스트5");
        templateContentRepository.save(templateContent);
    }

}
