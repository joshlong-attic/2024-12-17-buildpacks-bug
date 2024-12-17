package com.example.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;


@SpringBootApplication
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

//    @Bean
//    ApplicationRunner youIncompleteMe(IncompleteEventPublications publications) {
//        return args ->
//                publications.resubmitIncompletePublications(ep -> Math.random() > .5);
//    }

    @Bean
    RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }
}


@Controller
@ResponseBody
class CoraIberkleidDemo {

    private final RestClient http;

    CoraIberkleidDemo(RestClient http) {
        this.http = http;
    }


    @GetMapping("/delay")
    String delay() {
        var msg = "";
        msg += (Thread.currentThread().toString());
        var result = this.http
                .get()
                .uri("https://httpbin.org/delay/5")
                .retrieve()
                .body(String.class);
        msg += "::";
        msg += (Thread.currentThread().toString());
        System.out.println(msg);
        return result;
    }
}

