package org.landy.springbootlesson3.client;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.landy.springbootlesson3.controller.User;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestClient {

    public static void main(String[] args) {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        HttpClient httpClient = httpClientBuilder.build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

//        RestTemplate restTemplate = new RestTemplate();
        //适配HttpClient的方式
        RestTemplate restTemplate = new RestTemplate(factory);

        String content = restTemplate.getForObject("http://localhost:7070/json/user", String.class);
        System.out.println(content);
//        User user = restTemplate.getForObject("http://localhost:7070/json/user", User.class);

        User user = restTemplate.getForObject("http://localhost:7070/xml/user", User.class);

        System.out.println(user);

    }
}
