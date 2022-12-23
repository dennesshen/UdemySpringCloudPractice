package com.example.carservice;

import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@SpringBootApplication
@EnableEurekaClient
public class CarServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarServiceApplication.class, args);
	}
	
	@Bean
	ApplicationRunner init(CarRepository repository) {
		return new ApplicationRunner() {
			
			@Override
			public void run(ApplicationArguments args) throws Exception {
				
				Stream.of("Ferrari", "Jaguar", "Porsche", "Lamborghini", "Bugatti",
	                    "AMC Gremlin", "Triumph Stag", "Ford Pinto", "Yugo GV").forEach(name -> {
	                repository.save(new Car(name));
	            });
	            repository.findAll().forEach(System.out::println);
				
			}
		};
	}

}


@Data
@Entity
@NoArgsConstructor
class Car {
	
	public Car(String name) {
        this.name = name;
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NonNull
	@Column
    private String name;
}



@RepositoryRestResource
interface CarRepository extends JpaRepository<Car, Long>{
		
}



