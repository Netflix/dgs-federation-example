package com.example.demo.datafetchers;

import com.example.demo.generated.types.Review;
import com.example.demo.generated.types.Show;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsEntityFetcher;
//import javafx.util.Pair;


import java.util.*;
import java.util.stream.Collectors;

@DgsComponent
public class ReviewsDatafetcher {

    Map<String, Review> reviews = new HashMap<>();

    public ReviewsDatafetcher() {
        reviews.put("1", new Review(5));
        reviews.put("1", new Review(4));
        reviews.put("1", new Review(5));
        reviews.put("2", new Review(3));
        reviews.put("2", new Review(5));
    }

    @DgsEntityFetcher(name = "Show")
    public Show movie(Map<String, Object> values) {
        return new Show((String) values.get("id"), null);
    }

    @DgsData(parentType = "Show", field = "reviews")
    public List<Review> reviewsFetcher(DgsDataFetchingEnvironment dataFetchingEnvironment)  {
        Show show = dataFetchingEnvironment.getSource();
        return reviews.entrySet().stream()
                .filter(it -> it.getKey().equals(show.getId()))
                .map(it  -> it.getValue())
                .collect(Collectors.toList());
    }
}
