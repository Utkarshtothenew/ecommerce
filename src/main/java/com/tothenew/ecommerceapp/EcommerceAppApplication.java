package com.tothenew.ecommerceapp;

//import com.tothenew.ecommerceapp.services.RedisService;
import com.tothenew.ecommerceapp.entities.utils.AuditorAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
//        (exclude = {RedisRepositoriesAutoConfiguration.class})
@EnableAsync
@EnableCaching
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class EcommerceAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceAppApplication.class, args);
    }


    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }




//    @Bean
//    public RedisService service(){
//        return new RedisService() ;
//    }

}
