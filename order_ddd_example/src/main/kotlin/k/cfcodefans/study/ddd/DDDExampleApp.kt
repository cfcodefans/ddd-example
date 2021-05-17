package k.cfcodefans.study.ddd

import k.cfcodefans.study.ddd.DDDExampleApp.Companion.appCtx
import k.cfcodefans.study.ddd.DDDExampleApp.Companion.log
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext

/**
 * Central application class containing both general application and web configuration as well as a main-method to
 * bootstrap the application using Spring Boot.
 *
 * Kotlin's main method does not belong to any class
 *
 * @see #main(String[])
 * @see SpringApplication
 * @author cfcodefans
 */
open class DDDExampleApp {
    companion object {
        val log: Logger = LoggerFactory.getLogger(DDDExampleApp::class.java)

        /**
         * Keep a static reference of Spring context for some user cases
         */
        @JvmStatic
        var appCtx: ConfigurableApplicationContext? = null
    }
}

fun main(args: Array<String>) {
    log.info("DDDExampleApp starting...")
    appCtx = SpringApplication.run(DDDExampleApp::class.java, *args)
    log.info("DDDExampleApp started...")
}