package org.egx.notifications.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class EmailServiceUnitTest {

    @InjectMocks
    private EmailService emailService;
    @Mock
    private JavaMailSender javaMailSender;
    @BeforeEach
    void setUp() {
    }

    @Test
    void testSendEmail_whenNullValuesProvided_shouldNotThrowException() {
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        Assertions.assertDoesNotThrow(()->emailService.sendEmail(null, null, null));
    }
}