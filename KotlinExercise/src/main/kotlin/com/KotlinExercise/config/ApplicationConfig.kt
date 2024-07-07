package com.kotlinexercise.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ApplicationConfig {

    @Bean
    fun rest(): RestTemplate {
        return RestTemplate()
    }

    @Autowired
    lateinit var tokenInterceptor: TokenInterceptor

    @Bean
    fun webMvcConfigure(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addInterceptors(registry: InterceptorRegistry) {
                registry.addInterceptor(tokenInterceptor)
                        .addPathPatterns("/api/albums/**")
            }
        }
    }
}