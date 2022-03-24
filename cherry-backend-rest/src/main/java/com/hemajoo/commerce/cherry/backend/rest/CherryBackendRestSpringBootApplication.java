/*
 * (C) Copyright Resse Christophe 2021 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Resse Christophe. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Resse C. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Resse Christophe (christophe.resse@gmail.com).
 * -----------------------------------------------------------------------------------------------
 */
package com.hemajoo.commerce.cherry.backend.rest;

import com.hemajoo.commerce.cherry.backend.persistence.configuration.PersistenceConfiguration;
import internal.org.springframework.content.s3.boot.autoconfigure.S3ContentAutoConfiguration;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Arrays;

/**
 * The {@code Cherry}'s REST APIs {@code Spring} (server) application.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Import({ PersistenceConfiguration.class })
@SpringBootApplication(exclude = { S3ContentAutoConfiguration.class })
public class CherryBackendRestSpringBootApplication implements CommandLineRunner
{
    @Autowired
    private ApplicationContext appContext;

    /**
     * Returns the <b>OpenAPI</b> information.
     * @return <b>OpenAPI</b> information.
     */
    @Bean
    public OpenAPI openApi()
    {
        return new OpenAPI()
                .info(new Info().title("Cherry Backend - REST-APIs")
                        .description("Hemajoo's Cherry Backend REST-API controllers")
                        .version("0.1.0")
                        .license(new License().name("Hemajoo (c) 2022 - All rights reserved").url("https://github.com/ressec/cherry-backend")))
                .externalDocs(new ExternalDocumentation()
                        .description("Cherry Backend Wiki Documentation")
                        .url("https://github.com/ressec/cherry-backend"));
    }

    /**
     * Main application entry point.
     * @param args Arguments.
     */
    public static void main(String[] args)
    {
        SpringApplication.run(CherryBackendRestSpringBootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        String[] beans = appContext.getBeanDefinitionNames();
        Arrays.sort(beans);
//        for (String bean : beans)
//        {
//            LOG.log(Level.INFO, bean);
//        }
    }
}
