//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.student.details.config;

import jakarta.servlet.SessionTrackingMode;
import java.util.Collections;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfig {
    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return (servletContext) -> servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));
    }
}
