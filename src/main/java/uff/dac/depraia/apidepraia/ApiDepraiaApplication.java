package uff.dac.depraia.apidepraia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.mappers.ModelMapper;

@SpringBootApplication
public class ApiDepraiaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiDepraiaApplication.class, args);
    }
}
