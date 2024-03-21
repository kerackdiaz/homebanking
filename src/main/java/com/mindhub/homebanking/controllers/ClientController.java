package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.servicies.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;




    @GetMapping("/")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        ClientDTO clientDTO = clientService.getClientById(id);
        if (clientDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok("Hello " + mail);
    }



    @GetMapping("/current")
    public ResponseEntity<?> getClient(){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(new ClientDTO(clientService.getClientByEmail(userMail)));
    }


    @PutMapping("/newpassword")
    public ResponseEntity<?> changePassword(@RequestBody String password) {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(clientService.changePassword(userMail, password));
    }


    @PutMapping("/avatar")
    public ResponseEntity<?> changeProfilePicture(@RequestBody String img) throws IOException {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(clientService.changeProfilePicture(userMail, img));
    }




}