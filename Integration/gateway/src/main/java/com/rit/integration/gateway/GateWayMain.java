package com.rit.integration.gateway;

import com.rit.integration.gateway.config.GateWaySpringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by nirbo on 11/24/2015.
 */

/**
 * Use this inorder to run gateWay as stand alone process
 * Not in use - TCP server is used GateWay as dependency lib .
 */

@SpringBootApplication
@Configuration
@Import(GateWaySpringConfig.class)
public class GateWayMain {


    public static void main(String[] args) {
        SpringApplication.run(GateWayMain.class, args);
    }


}
