package com.example.androidserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.androidserver.util.KeyPairGeneratorUtil;

import java.security.PublicKey;
import java.util.Base64;

@RestController
public class KeyController {

    @GetMapping("/api/publicKey")
    public String getPublicKey() {
        PublicKey publicKey = KeyPairGeneratorUtil.getKeyPair().getPublic();
        System.out.println(publicKey);
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
}
