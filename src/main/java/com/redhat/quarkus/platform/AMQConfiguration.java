package com.redhat.quarkus.platform;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "amq.config")
public interface AMQConfiguration {

    String queueOne();
    String queueTwo();
}
