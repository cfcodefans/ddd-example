package com.metamagic.ddd

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.jdo.JDOHelper
import javax.jdo.PersistenceManagerFactory

@Configuration
open class Config {
    @Bean
    fun persistenceManagerFactory(): PersistenceManagerFactory = JDOHelper.getPersistenceManagerFactory("PersistenceUnit")
}