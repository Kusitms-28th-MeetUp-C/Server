package com.kusitms.mainservice.domain.template.domain;

import org.springframework.data.annotation.Id;

@Document(collection = "contents")
public class MongoDBTemplate {
    @Id
    private String id;

    private String content;

}
