package com.example.androidserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.androidserver.model.AppUser;
import com.example.androidserver.repository.AppUserRepository;
import com.example.androidserver.util.KeyPairGeneratorUtil;
import com.example.androidserver.util.PasswordUtil;
import com.example.androidserver.util.RSAUtil;

import java.security.PrivateKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    @Autowired
    private AppUserRepository appUserRepository;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody AppUser appUser) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 암호화된 비밀번호를 서버의 개인 키로 복호화합니다.
            PrivateKey privateKey = KeyPairGeneratorUtil.getKeyPair().getPrivate();
            byte[] decodedBytes = Base64.getDecoder().decode(appUser.getSecNum());
            String decryptedSecNum = RSAUtil.decrypt(decodedBytes, privateKey);

            appUser.setSecNum(decryptedSecNum);

            if (appUser.getUserId() == null || appUser.getSecNum() == null || appUser.getNam() == null) {
                response.put("message", "Missing required fields");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            String hashedPassword = PasswordUtil.hashPassword(appUser.getSecNum());
            appUser.setSecNum(hashedPassword);

            appUserRepository.save(appUser);
            response.put("message", "User registered successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Error registering user");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody AppUser loginUser) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 암호화된 비밀번호를 서버의 개인 키로 복호화합니다.
            PrivateKey privateKey = KeyPairGeneratorUtil.getKeyPair().getPrivate();
            byte[] decodedBytes = Base64.getDecoder().decode(loginUser.getSecNum());
            String decryptedSecNum = RSAUtil.decrypt(decodedBytes, privateKey);

            AppUser appUser = appUserRepository.findByUserId(loginUser.getUserId());

            if (appUser == null || !PasswordUtil.checkPassword(decryptedSecNum, appUser.getSecNum())) {
                response.put("message", "Invalid user ID or password");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            response.put("message", "Login successful");
            response.put("userId", appUser.getUserId());
            response.put("nam", appUser.getNam());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Error logging in");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
