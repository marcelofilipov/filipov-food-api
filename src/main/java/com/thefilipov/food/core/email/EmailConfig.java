package com.thefilipov.food.core.email;

import com.thefilipov.food.domain.service.EnvioEmailService;
import com.thefilipov.food.infrastructure.service.email.FakeEnvioEmailService;
import com.thefilipov.food.infrastructure.service.email.SandboxEnvioEmailService;
import com.thefilipov.food.infrastructure.service.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeEnvioEmailService();
            case SMTP:
                return new SmtpEnvioEmailService();
            case SANDBOX:
                return new SandboxEnvioEmailService();
            default:
                return null;
        }
    }
}
