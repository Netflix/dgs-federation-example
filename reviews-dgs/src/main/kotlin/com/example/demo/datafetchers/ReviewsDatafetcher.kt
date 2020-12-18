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