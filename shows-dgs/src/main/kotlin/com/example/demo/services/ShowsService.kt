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