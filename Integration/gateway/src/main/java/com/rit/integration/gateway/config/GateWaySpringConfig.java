package com.rit.integration.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by nirbo on 11/24/2015.
 */

//@AnnotationDrivenConfig // enable the @Autowired annotation

//TODO - USE PROFILE TO LOAD ONLY gateway context in case rabbitmq is not needed (rabbit context should not be loaded)
@Configuration
@ImportResource(locations = {"classpath:com/rit/integration/gateway/spring/rabbitmq-consumer-context.xml","classpath:com/rit/integration/gateway/spring/gateway-context.xml"})
public class GateWaySpringConfig {








}
