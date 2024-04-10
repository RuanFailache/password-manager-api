package dev.passwordmanager.shared.utils;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class EncryptionUtil {
    public String encrypt(String text) {
        var passwordAsBytes = text.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(passwordAsBytes);
    }

    public String decrypt(String text) {
        var passwordAsBytes = Base64.getDecoder().decode(text);
        return new String(passwordAsBytes, StandardCharsets.UTF_8);
    }
}
