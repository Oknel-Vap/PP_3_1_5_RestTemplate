package org.example;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        String url = "http://94.198.50.185:7081/api/users";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<User>> listUsers = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<User>>() {});
        HttpHeaders headers = listUsers.getHeaders();
        String set_cookie = headers.getFirst(HttpHeaders.SET_COOKIE);

        HttpHeaders headerWithId = new HttpHeaders();
        headerWithId.add("Cookie", set_cookie);

        User user = new User(3L, "James", "Brown", (byte)37);
        HttpEntity<?> requestEntity1 = new HttpEntity<>(user, headerWithId);
        String response1 = restTemplate.exchange(url, HttpMethod.POST, requestEntity1,String.class).getBody();

        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<?> requestEntity2 = new HttpEntity<>(user, headerWithId);
        String response2 = restTemplate.exchange(url, HttpMethod.POST, requestEntity2, String.class).getBody();

        String response3 = restTemplate.exchange(url + "/" + user.getId(), HttpMethod.DELETE, requestEntity2, String.class).getBody();

        System.out.println(response1 + response2 + response3);
    }
}