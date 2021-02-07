package cc.lovesq;

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
@ImportResource(locations={"classpath:spring.xml"})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
