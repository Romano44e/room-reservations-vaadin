package com.room_reservations.views.domain.reservation;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

public class ReservationRestClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://localhost:8080/roomreservations/reservations";

    public void createReservation(Reservation reservation) {
        try {
            restTemplate.postForEntity(BASE_URL, reservation, Void.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateReservationByCode(Reservation reservation) {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Reservation> entity = new HttpEntity<>(reservation, headers);
            restTemplate.exchange(BASE_URL, HttpMethod.PUT, entity, Void.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteReservationByCode(String code) {
        try {
            restTemplate.delete(BASE_URL + "/code/" + code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Reservation> getAllReservations() {
        try {
            ResponseEntity<List<Reservation>> response = restTemplate.exchange(
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

    public List<Reservation> getByUserId(String userId) {
        return getListFromEndpoint("/userId/" + userId);
    }

    public List<Reservation> getByRoomId(String roomId) {
        return getListFromEndpoint("/roomId/" + roomId);
    }

    public List<Reservation> getByDateTime(String dateTime) {
        return getListFromEndpoint("/dateTime/" + dateTime);
    }

    public List<Reservation> getByPaymentStatus(String status) {
        return getListFromEndpoint("/paymentStatus/" + status);
    }

    public List<Reservation> getByReservationStatus(String status) {
        return getListFromEndpoint("/reservationStatus/" + status);
    }

    public List<Reservation> getByAmount(String amount) {
        return getListFromEndpoint("/amount/" + amount);
    }

    private List<Reservation> getListFromEndpoint(String endpoint) {
        try {
            ResponseEntity<List<Reservation>> response = restTemplate.exchange(
                    BASE_URL + endpoint,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
