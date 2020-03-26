package com.vm.explore.batch.springbatchprocessing.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InvoiceGeneratorRoute extends RouteBuilder {

    private Logger logger = LoggerFactory.getLogger(InvoiceGeneratorRoute.class);
    @Override
    public void configure() throws Exception {
        from("timer://invoiceTimer?delay=10s&period=30s").routeId("invoiceGeneratorRoute")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        logger.info("Creating file");
                        StringBuilder builder = new StringBuilder();
                        for (int index = 0;index <1000;index++){
                            builder.append(UUID.randomUUID().toString()).append(",").append(UUID.randomUUID().toString()).append(System.lineSeparator());
                        }
                        exchange.getIn().setBody(builder.toString());
                        exchange.getIn().setHeader(Exchange.FILE_NAME,UUID.randomUUID().toString()+".csv");
                    }
                })
                .to("file://src/main/resources");
    }
}
