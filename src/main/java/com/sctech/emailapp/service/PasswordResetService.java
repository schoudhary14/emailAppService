package com.sctech.emailapp.service;

import com.sctech.emailapp.model.Otp;
import com.sctech.emailapp.model.User;
import com.sctech.emailapp.repository.OtpRepository;
import com.sctech.emailapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createOtpForUser(String email) throws Exception {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new Exception("User with email " + email + " not found");
        }

        String otp = generateOtp();
        Otp otpEntity = new Otp();
        otpEntity.setOtp(otp);
        otpEntity.setEmail(email);
        otpEntity.setExpiryDate(LocalDateTime.now().plusMinutes(30) ); // 30 minutes expiration
        otpRepository.save(otpEntity);

        emailService.sendEmail(email, otp);
    }

    public void resetPassword(String email, String otp, String newPassword) throws Exception {
        Optional<Otp> otpOptional = otpRepository.findByEmail(email);
        if (otpOptional.isEmpty() || !otpOptional.get().getOtp().equals(otp) || otpOptional.get().getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new Exception("Invalid or expired OTP");
        }

        Otp otpEntity = otpOptional.get();
        Optional<User> userOptional = userRepository.findByEmail(otpEntity.getEmail());
        if (userOptional.isEmpty()) {
            throw new Exception("User not found");
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        otpRepository.delete(otpEntity);
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}