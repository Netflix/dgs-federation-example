package com.example.demo.services

import com.example.demo.generated.types.Show
import org.springframework.stereotype.Service

interface ShowsService {
    fun shows(): List<Show>
}

@Service
class BasicShowsService : ShowsService {
    override fun shows(): List<Show> {
        return listOf(
            Show("1","Stranger Things", 2016),
            Show("2","Ozark", 2017),
            Show("3","The Crown", 2016),
            Show("4","Dead to Me", 2019),
            Show("5","Orange is the New Black", 2013)
        )
    }
}