package com.mindhub.homebanking.servicies.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        return client != null ? new ClientDTO(client) : null;
    }


    public Map<String, Object> changePassword(String email, String newPassword) {
        Map<String, Object> response = new HashMap<>();
        try {
            Client client = clientRepository.findByEmail(email);
            if (client != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(newPassword);
                String password = jsonNode.get("data").asText();
                if (password.length() < 8) {
                    response.put("error", false);
                    response.put("message", "Password must be at least 8 characters long");
                    return response;
                }
                if (!password.matches(".*\\d.*")) {
                    response.put("error", false);
                    response.put("message", "Password must contain at least one number");
                    return response;
                }
                if (!password.matches(".*[a-z].*")) {
                    response.put("error", false);
                    response.put("message", "Password must contain at least one lowercase letter");
                    return response;
                }

                if (!password.matches(".*[A-Z].*")) {
                    response.put("error", false);
                    response.put("message", "Password must contain at least one uppercase letter");
                    return response;
                }
                client.setPassword(passwordEncoder.encode(password));
                clientRepository.save(client);
                response.put("success", true);
                response.put("message", "Password updated successfully");
            } else {
                response.put("error", true);
                response.put("message", "Client not found");
            }
        } catch (Exception e) {
            response.put("error", true);
            response.put("message", "An error occurred while updating password: " + e.getMessage());
        }
        return response;
    }

    public Map<String, Object> changeProfilePicture(String email, String img)  {
        Map<String, Object> response = new HashMap<>();
        Client client = clientRepository.findByEmail(email);
        try{


        if (client != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(img);
            String url = jsonNode.get("img").asText();
            System.out.println(url);
            client.setImage(url);
            clientRepository.save(client);
            response.put("success", true);
            response.put("message", "Profile picture updated successfully");

        } else {
            response.put("error", false);
            response.put("message", "Client not found");
        }
        } catch (Exception e) {
            response.put("error", false);
            response.put("message", "An error occurred while updating profile picture: " + e.getMessage());
        }
        return response;
    }


    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }




}

