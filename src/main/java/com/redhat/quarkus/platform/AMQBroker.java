package com.redhat.quarkus.platform;


import org.apache.camel.builder.RouteBuilder;

public class AMQBroker extends RouteBuilder {  
    
    @Override
    public void configure() throws Exception {
        
        rest()
            .post("/send-one")
            .routeId("RouteSendMQOne")
            .consumes("text/plain")
            .produces("text/plain")
            .to("direct:producerMQOne");
        
        rest()
            .post("/send-two")
            .routeId("RouteSendMQTwo")
            .consumes("text/plain")
            .produces("text/plain")
            .to("direct:producerMQTwo");
        
        //Queue One
        from("direct:producerMQOne")
            .routeId("SendMQOne")
            .log("[1] Sending message to AMQ: ${body}")
            .to("amqp:queue:{{amq.config.queue-one}}");

        from("amqp:queue:{{amq.config.queue-one}}?exchangePattern=InOut")
            .routeId("ReceivedMQOne")
            .log("[2] Received message from AMQ: ${body}")
            .setBody(simple("Receive: ${body}"));
        
        //Queue Two
        from("direct:producerMQTwo")
            .routeId("SendMQTwo")
            .log("[1] Sending message to AMQ: ${body}")
            .to("amqp:queue:{{amq.config.queue-two}}?exchangePattern=InOnly");
    }
}
