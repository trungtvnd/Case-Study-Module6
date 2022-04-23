package codegym.com.vn.service.impl;


import codegym.com.vn.model.User;
import codegym.com.vn.repository.IRegistrationUserTokenRepository;
import codegym.com.vn.service.Account.IUserService;
import codegym.com.vn.service.interfaceService.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailService implements IEmailService {
    @Autowired
    private IUserService userService;

    @Autowired
    public JavaMailSender mailSender;

    @Autowired
    private IRegistrationUserTokenRepository registrationUserTokenRepository;


    @Override
    public void SendRegistrationUserConfirm(String email) {
        Optional<User> account = userService.findByEmail(email);

        String token = registrationUserTokenRepository.findByAccount_Id(account.get().getId()).getToken();

        String confirmationUrl = "http://localhost:8080/api/auth/active/" + token;
        String subject = "Xác Nhận Đăng Ký Account";
        String content = "Click vào link dưới đây để kích hoạt tài khoản \n" + confirmationUrl;

        sendEmail(email, subject, content);
    }

    private void sendEmail(final String recipientEmail, final String subject, final String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }
}
