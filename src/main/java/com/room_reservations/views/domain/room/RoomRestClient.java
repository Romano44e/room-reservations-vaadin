package com.room_reservations.views.domain.room;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

public class RoomRestClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://localhost:8080/roomreservations/rooms";

    public void createRoom(Room room) {
        try {
            restTemplate.postForEntity(BASE_URL, room, Void.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRoom(Room room) {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Room> entity = new HttpEntity<>(room, headers);
            restTemplate.exchange(BASE_URL, HttpMethod.PUT, entity, Void.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRoomByCipher(Room room) {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Room> entity = new HttpEntity<>(room, headers);
            restTemplate.exchange(BASE_URL, HttpMethod.PUT, entity, Void.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteRoomByName(String name) {
        try {
            restTemplate.delete(BASE_URL + "/name/" + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteRoomByCipher(String cipher) {
        try {
            restTemplate.delete(BASE_URL + "/cipher/" + cipher);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Room> getAllRooms() {
        try {
            ResponseEntity<List<Room>> response = restTemplate.exchange(
                    BASE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Room> getRoomsByLocation(String location) {
        try {
            String url = BASE_URL + "/location/" + location;
            ResponseEntity<List<Room>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Room> getRoomsByCapacity(String capacity) {
        try {
            String url = BASE_URL + "/capacity/" + capacity;
            ResponseEntity<List<Room>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Room> getRoomsByPrice(String price) {
        try {
            String url = BASE_URL + "/price/" + price;
            ResponseEntity<List<Room>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public String isRoomAvailableViaPost(RoomByDateTimeInputDto dto) {
        try {
            String url = BASE_URL + "/availability";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<RoomByDateTimeInputDto> request = new HttpEntity<>(dto, headers);
            ResponseEntity<RoomByDateTimeOutputDto> response = restTemplate.postForEntity(url, request, RoomByDateTimeOutputDto.class);
            return response.getBody() != null ? response.getBody().getAvailibility() : "Empty response from server.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error checking availability.";
        }
    }

}
