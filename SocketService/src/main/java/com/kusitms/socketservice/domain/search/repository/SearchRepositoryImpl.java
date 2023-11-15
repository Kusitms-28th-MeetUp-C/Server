package com.kusitms.socketservice.domain.search.repository;

import com.kusitms.socketservice.domain.search.domain.SearchUserTemplate;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class SearchRepositoryImpl implements SearchRepository {
    private final MongoConverter mongoConverter;
    private final MongoClient mongoClient;
    private final static String DATABASE_NAME = "meetful";
    private final static String COLLECTION_NAME = "search_user_template";
    private final static String SEARCH_NAME = "user_template";

    @Override
    public List<SearchUserTemplate> findAllBySearchText(String searchText, String userId) {
        AggregateIterable<Document> result = getResultFromSearchText(searchText, userId);
        List<SearchUserTemplate> searchUserTemplates = new ArrayList<>();
        result.forEach(doc -> searchUserTemplates.add(mongoConverter.read(SearchUserTemplate.class, doc)));
        return searchUserTemplates;
    }

    private AggregateIterable<Document> getResultFromSearchText(String searchText, String userId) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
        return collection.aggregate(Arrays.asList(
                new Document("$search", new Document("index", SEARCH_NAME)
                        .append("compound", new Document("must",
                                Arrays.asList(
                                        new Document(new Document("text",
                                            new Document("query", searchText)
                                                    .append("path", new Document("wildcard", "*")))),
                                        new Document(new Document("text",
                                            new Document("query", userId)
                                                    .append("path", "userId")))))))));
    }
}
