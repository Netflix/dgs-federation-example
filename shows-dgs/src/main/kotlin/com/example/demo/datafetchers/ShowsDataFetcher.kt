package com.example.demo.datafetchers

import com.example.demo.generated.types.Show
import com.example.demo.services.ShowsService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument
import org.springframework.beans.factory.annotation.Autowired

@DgsComponent
class ShowsDataFetcher {
    @Autowired
    lateinit var showsService : ShowsService

    @DgsData(parentType = "Query", field = "shows")
    fun shows(@InputArgument("titleFilter") titleFilter : String?): List<Show> {
        return if(titleFilter != null) {
            showsService.shows().filter { it.title?.contains(titleFilter) == true }
        } else {
            showsService.shows()
        }
    }
}