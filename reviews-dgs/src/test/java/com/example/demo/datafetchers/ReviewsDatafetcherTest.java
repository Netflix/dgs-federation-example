package com.example.demo.datafetchers;

import com.example.demo.generated.client.EntitiesProjectionRoot;
import com.example.demo.generated.client.ShowRepresentation;
import com.example.demo.generated.types.Review;
import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import com.netflix.graphql.dgs.client.codegen.EntitiesGraphQLQuery;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = {DgsAutoConfiguration.class, ReviewsDatafetcher.class})
class ReviewssDatafetcherTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    void shows() {
        Map<String,Object> representation = new HashMap<>();
        representation.put("__typename", "Show");
        representation.put("id", "1");
        List<Map<String, Object>> representationsList = new ArrayList<>();
        representationsList.add(representation);

        Map<String, Object> variables = new HashMap<>();
        variables.put("representations", representationsList);
        List<Review> reviewsList = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                "query ($representations:[_Any!]!) {" +
                        "_entities(representations:$representations) {" +
                        "... on Show {" +
                        "   reviews {" +
                        "       starRating" +
                        "}}}}",
                "data['_entities'][0].reviews", variables, new TypeRef<>() {
                });

        assertThat(reviewsList).isNotNull();
        assertThat(reviewsList.size()).isEqualTo(3);
    }

    @Test
    void showsWithEntitiesQueryBuilder() {
        EntitiesGraphQLQuery entitiesQuery = new EntitiesGraphQLQuery.Builder().addRepresentationAsVariable(ShowRepresentation.newBuilder().id("1").build()).build();
        GraphQLQueryRequest request = new GraphQLQueryRequest(entitiesQuery, new EntitiesProjectionRoot().onShow().reviews().starRating());
        List<Review> reviewsList = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                request.serialize(),
                "data['_entities'][0].reviews", entitiesQuery.getVariables(), new TypeRef<>() {
                });
        assertThat(reviewsList).isNotNull();
        assertThat(reviewsList.size()).isEqualTo(3);
    }
}