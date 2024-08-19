package br.com.alfa11.msfeatureflag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MsFeatureFlagApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsFeatureFlagApplication.class, args);
    }

}
