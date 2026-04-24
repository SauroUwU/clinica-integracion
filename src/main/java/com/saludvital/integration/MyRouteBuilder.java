package com.saludvital.integration;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;

public class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        // 1. Configuración de CSV
        CsvDataFormat csv = new CsvDataFormat();
        csv.setUseMaps("true"); 

        // 2. Definimos PRIMERO la ruta de archivado (Ruta de respaldo)
        from("direct:archivar")
            .routeId("RutaRespaldo")
            .setHeader(Exchange.FILE_NAME, simple("${file:onlyname.noext}_${date:now:yyyy-MM-dd_HHmmss}.${file:ext}"))
            .to("file:data/archive");

        // 3. RUTA PRINCIPAL (Procesamiento de Admisiones)
        from("file:data/input?delete=true")
            .routeId("ProcesadorPrincipal")
            .log(">>> Procesando archivo: ${header.CamelFileName}")
            
            // Guardamos el cuerpo original para mover el archivo entero
            .setProperty("OriginalBody", body())
            
            // Enviamos copia a la ruta de respaldo [cite: 80]
            .wireTap("direct:archivar")
            
            // Unmarshal y Validación [cite: 78, 91-101]
            .unmarshal(csv)
            .choice()
                .when(method(new AdmissionValidator(), "validate"))
                    .log("RESULTADO: Válido. Enviando a Facturación...")
                    .setBody(exchangeProperty("OriginalBody"))
                    .to("file:data/output")
                .otherwise()
                    .log("RESULTADO: Inválido. Enviando a Error...")
                    .setBody(exchangeProperty("OriginalBody"))
                    .to("file:data/error")
            .end();
    }
}