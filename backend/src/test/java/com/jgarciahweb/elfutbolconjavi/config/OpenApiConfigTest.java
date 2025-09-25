package com.jgarciahweb.elfutbolconjavi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigTest {

    @Test
    void apiInfo_shouldReturnConfiguredOpenAPI() {
        // given
        OpenApiConfig config = new OpenApiConfig();

        // when
        OpenAPI openAPI = config.apiInfo();

        // then
        assertNotNull(openAPI);

        Info info = openAPI.getInfo();
        assertNotNull(info);
        assertEquals("El Fútbol con Javi API", info.getTitle());
        assertEquals("API para la gestión de usuarios y contenidos del blog", info.getDescription());
        assertEquals("v1.0.0", info.getVersion());

        License license = info.getLicense();
        assertNotNull(license);
        assertEquals("MIT", license.getName());
        assertEquals("https://opensource.org/licenses/MIT", license.getUrl());

        ExternalDocumentation externalDocs = openAPI.getExternalDocs();
        assertNotNull(externalDocs);
        assertEquals("Repositorio en GitHub", externalDocs.getDescription());
        assertEquals("https://github.com/jgarciahweb/elfutbolconjavi", externalDocs.getUrl());
    }
}
