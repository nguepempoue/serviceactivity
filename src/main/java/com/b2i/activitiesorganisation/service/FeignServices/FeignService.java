package com.b2i.activitiesorganisation.service.FeignServices;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@FeignClient(name = "api-gateway", url = "localhost:9000")
@FeignClient(name = "organisation-service", url = "localhost:8083/")
//@FeignClient(name = "organisation-service", url = "http://185.98.139.14:8080/kt-organisation")
//@FeignClient(name = "organisation-service", url = "http://185.98.137.195:8080/kt-organisation")
public interface FeignService {

    String AUTH_TOKEN = "Authorization";
    // String ORGANISATION_SERVICE_BASE_URL = "/organisation";
    // CLUBS
    // GET CLUB BY ID
    @GetMapping("/clubs/{id}")
    ResponseEntity<Object> findClubById(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("id") Long id);

    // ACCOUNTS
    // CREATE ACCOUNT
    @PostMapping("/accounts/{idAccountType}")
    ResponseEntity<Object> createAccount(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("idAccountType") Long idAccountType);


    // FIND ACCOUNT BY ID
    @GetMapping("/accounts/{id}")
    ResponseEntity<Object> getAccountById(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("id") Long id);


    // DELETE ACCOUNT BY ID
    @DeleteMapping("/accounts/{id}")
    ResponseEntity<Object> deleteAccount(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("id") Long id);


    // SET BALANCE
    @PutMapping("/accounts/{id}/set-balance")
    ResponseEntity<Object> setBalance(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("id") Long id, @RequestParam("amount") Long amount);


    // FIND ACCOUNT BY USER AND ACCOUNT TYPE
    @GetMapping("/users/{idUser}/account/account-type/{idAccountType}")
    ResponseEntity<Object> findAccountByUserAndAccountType(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("idUser") Long idUser, @PathVariable("idAccountType") Long idAccountType);

    // USERS
    // GET USERS BY ID
    @GetMapping("/users/{id}")
    ResponseEntity<Object> getUserById(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("id") Long id);


    // GET CENTER USERS
    @GetMapping("/centers/{idCenter}/users")
    ResponseEntity<Object> getAllCenterUsers(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("idCenter") Long idCenter);


    // GET AREA USERS
    @GetMapping("/areas/{idArea}/users")
    ResponseEntity<Object> getAllAreaUsers(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("idArea") Long idArea);


    // GET CLUB USERS
    @GetMapping("/clubs/{idClub}/users")
    ResponseEntity<Object> getAllClubUsers(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("idClub") Long idClub);

    // AREAS
    // GET AREA OF A CLUB
    @GetMapping("/clubs/{idClub}/area")
    ResponseEntity<Object> getAreaOfClub(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("idClub") Long idClub);

    // CENTER
    // GET CENTER OF AREA
    @GetMapping("/areas/{idArea}/center")
    ResponseEntity<Object> getCenterOfArea(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("idArea") Long idArea);

    //CENTER
    //GET CENTER BY ID
    @GetMapping("/centers/{idCenter}")
    ResponseEntity<Object> getCenterById(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("idCenter") Long idCenter);

    //CENTER
    //GET CENTER BY ID User
    @GetMapping("/centers/user/{idUser}")
    ResponseEntity<Object> getCenterByIdUser(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("idUser") Long idUser);


}
