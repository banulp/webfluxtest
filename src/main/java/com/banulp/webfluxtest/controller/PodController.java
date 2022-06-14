package com.ncsoft.gamedata.gdconfig.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/webclient")
public class PodController {

    @GetMapping("/envs")
    public Flux<String> application(@PathVariable("application") String application){
            WebClient client = WebClient.create();

            List<String> tl = Arrays.asList("http://172.19.66.127:30632", "http://172.19.66.127:30632");
            return Flux.just(tl.toArray())
//            return Flux.just(podCollect.toArray())
                    .flatMap(pod -> {
                        log.info("pod env call");
//                        return client.post()
                        return client.get()
                                .uri(pod + "/monitor/env")
//                                .contentType(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToFlux(String.class);
//                                .map(s -> {
//                                    return s.trim();
//                                });
                    });
        return null;
    }

    @PostMapping
    public Flux<String> refresh(@RequestBody List<String> podList) {
        WebClient client = WebClient.create();
//        log.info("pod refresh start only 12");

//        List<String> tl = Arrays.asList("http://172.19.66.127:30632", "http://172.19.66.127:30632");
//        return Flux.just(tl.toArray())
                return Flux.just(podList.toArray())
                .flatMap(pod -> {
                    log.info("pod refresh start");
                    return client.post()
                            .uri(pod + "/monitor/refresh")
                            .contentType(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(String.class);
                });
    }

}
