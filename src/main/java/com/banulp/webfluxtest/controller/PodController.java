package com.banulp.webfluxtest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/webclient")
public class PodController {

    @GetMapping("/envs")
    public Flux<String> application(){
            WebClient client = WebClient.create();

            List<String> tl = Arrays.asList("https://jsonplaceholder.typicode.com/posts/1");
            return Flux.just(tl.toArray())
                    .flatMap(link -> {
                        return client.get()
                                .uri((String) link)
//                                .contentType(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToFlux(String.class);
                    });
    }

}
