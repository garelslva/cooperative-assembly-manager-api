package br.com.assembly.storage.postegres.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@Profile("!tests")
public class SchemaConfiguration {

    private final Environment env;

    public SchemaConfiguration(Environment env) {
        this.env = env;
    }

    @Bean(name = "defaultDataSource")
    @ConfigurationProperties(prefix = "db-default.datasource")
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create()
                .url(env.getProperty("spring.datasource.url"))
                .driverClassName("org.postgresql.Driver")
                .username(env.getProperty("spring.datasource.username"))
                .password(env.getProperty("spring.datasource.password"))
                .build();
    }

}
