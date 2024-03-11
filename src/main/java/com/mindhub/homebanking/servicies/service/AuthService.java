package com.mindhub.homebanking.servicies.service;

import com.mindhub.homebanking.dtos.LoginDTO;
import com.mindhub.homebanking.dtos.RegisterDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.servicies.JwtUtilService;
import com.mindhub.homebanking.utilitis.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    public Map<String, Object> login(LoginDTO loginDTO) {
        Map<String, Object> response = new HashMap<>();
        if(loginDTO.email().isBlank() || loginDTO.password().isBlank()){
            response.put("success", false);
            response.put("message", "Incorrect");
            return response;
        }
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.email());
            final String jwt = jwtUtilService.generateToken(userDetails);
            response.put("success", true);
            response.put("token", jwt);
        }catch(Exception e){
            response.put("success", false);
            response.put("message", "Incorrect");
        }
        return response;
    }

    public Map<String, Object> register(RegisterDTO registerDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (clientRepository.existsByEmail(registerDTO.email())) {
                response.put("success", false);
//                response.put("message", "Email already in use");
                return response;
            }

            Client client = new Client(registerDTO.firstName(), registerDTO.lastName(), registerDTO.email(), passwordEncoder.encode(registerDTO.password()));
            clientRepository.save(client);

            String accountNumber = RandomUtil.generateAccountNumber(8);
            while (accountRepository.existsByNumber(accountNumber)) {
                accountNumber = RandomUtil.generateAccountNumber(8);
            }

            Account account = new Account(accountNumber, LocalDate.now(), 0.0);
            account.setClient(client);
            accountRepository.save(account);

            response.put("success", true);
            response.put("message", "Successfully registered");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An error occurred while registering: " + e.getMessage());
        }
        return response;
    }
}