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

package com.example.demo.datafetchers

import com.example.demo.generated.types.Review
import com.example.demo.generated.types.Show
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsEntityFetcher

@DgsComponent
class ReviewsDatafetcher {

    val reviews = listOf(
        Pair("1", Review(5)),
        Pair("1", Review(4)),
        Pair("1", Review(5)),
        Pair("2", Review(3)),
        Pair("2", Review(5))
    )

    @DgsEntityFetcher(name = "Show")
    fun movie(values : Map<String, Any>): Show {
        return Show(id = values["id"] as String)
    }

    @DgsData(parentType = "Show", field = "reviews")
    fun reviewsFetcher(dataFetchingEnvironment: DgsDataFetchingEnvironment): List<Review> {
        val show = dataFetchingEnvironment.getSource<Show>()
        return reviews
            .filter { it.first == show.id }
            .fold(emptyList()) {reviews, review -> reviews.plus(review.second)}
    }
}