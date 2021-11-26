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

package com.example.demo

import graphql.execution.instrumentation.Instrumentation
import graphql.execution.instrumentation.tracing.TracingInstrumentation
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class DemoApplication {

    /**
     * If you want to leverage Apollo Tracing, as supported by java-graphql, you can just create a bean of type [TracingInstrumentation].
     * In this example we added a conditional property on the bean to enable/disable the Apollo Tracing.
     * Enabled by default, you can turn it off by setting `graphql.tracing.enabled=false` in your application properties.
     *
     * @see [Apollo Tracing](https://github.com/apollographql/apollo-tracing)
     */
    @Bean
    @ConditionalOnProperty(prefix = "graphql.tracing", name = ["enabled"], matchIfMissing = true)
    open fun tracingInstrumentation(): Instrumentation? {
        return TracingInstrumentation()
    }
}

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
