package br.com.attornatuschallenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.attornatuschallenge.adapters.outbound.SavePersonAdapter;
import br.com.attornatuschallenge.application.core.useCases.SavePerson;

@Configuration
public class SavePersonConfig {
  @Bean
  public SavePerson savePersonService(SavePersonAdapter savePersonAdapter) {
    return new SavePerson(savePersonAdapter);
  }
}
