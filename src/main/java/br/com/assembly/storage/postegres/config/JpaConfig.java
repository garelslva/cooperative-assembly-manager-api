package br.com.assembly.storage.postegres.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"br.com.assembly.storage.postegres.repository"})
@Profile("!tests")
public class JpaConfig {
}
