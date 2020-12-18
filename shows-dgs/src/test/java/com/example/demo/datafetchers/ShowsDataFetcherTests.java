/*
 * Copyright 2020 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.demo.datafetchers;

import com.example.demo.generated.client.ShowsGraphQLQuery;
import com.example.demo.generated.client.ShowsProjectionRoot;
import com.example.demo.generated.types.Show;
import com.example.demo.services.ShowsService;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {DgsAutoConfiguration.class, ShowsDataFetcher.class})
public class ShowsDataFetcherTests {
    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @MockBean
    ShowsService showsService;

    @BeforeEach
    public void before() {
        Mockito.when(showsService.shows()).thenAnswer(invocation -> List.of(new Show("1","mock title", 2020)));
    }

    @Test
    public void showsWithQueryApi() {
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                new ShowsGraphQLQuery.Builder().build(),
                new ShowsProjectionRoot().title()
        );

        List<String> titles = dgsQueryExecutor.executeAndExtractJsonPath(graphQLQueryRequest.serialize(), "data.shows[*].title");
        assertThat(titles).containsExactly("mock title");
    }
}
