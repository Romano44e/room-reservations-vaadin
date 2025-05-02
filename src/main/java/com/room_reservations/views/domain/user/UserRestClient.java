package com.room_reservations.views.domain.user;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserRestClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://localhost:8080/roomreservations/users";

    public List<User> fetchAllUsers() {
        try {
            ResponseEntity<User[]> response = restTemplate.getForEntity(BASE_URL, User[].class);
            return Arrays.asList(response.getBody());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<User> fetchUsersByName(String name) {
        try {
            String url = BASE_URL + "/name/" + name;
            ResponseEntity<User[]> response = restTemplate.getForEntity(url, User[].class);
            return Arrays.asList(response.getBody());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<User> fetchUsersByEmail(String email) {
        try {
            String url = BASE_URL + "/email/" + email;
            ResponseEntity<User[]> response = restTemplate.getForEntity(url, User[].class);
            return Arrays.asList(response.getBody());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public void createUser(User user) {
        try {
            restTemplate.postForEntity(BASE_URL, user, Void.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void updateUser(User user) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            HttpEntity<User> entity = new HttpEntity<>(user, headers);
//            restTemplate.exchange(BASE_URL, HttpMethod.PUT, entity, Void.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void updateUserByPassword(User user) {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<User> entity = new HttpEntity<>(user, headers);
            restTemplate.exchange(BASE_URL, HttpMethod.PUT, entity, Void.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void deleteUserByName(String name) {
//        try {
//            restTemplate.delete(BASE_URL + "/name/" + name);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void deleteUserByPassword(String password) {
        try {
            restTemplate.delete(BASE_URL + "/password/" + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
