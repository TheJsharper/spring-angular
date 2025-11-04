package configs;

import dbs.TenantBeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import dbs.TentantBean;

import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:8080", "http://127.0.0.1:8080", "http://localhost:4200", "http://127.0.0.1:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.anyRequest().permitAll())
                .cors(c -> c.configurationSource(source));
        return httpSecurity.build();
    }


    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return new TenantBeanFactoryPostProcessor();
    }


    @Scope(scopeName = "tenant")
    @Bean(name = "foo")
    public TentantBean foo() {
        return new TentantBean("foo");
    }

    @Scope(scopeName = "tenant")
    @Bean(name = "bar")
    public TentantBean bar() {
        return new TentantBean("bar");
    }


}