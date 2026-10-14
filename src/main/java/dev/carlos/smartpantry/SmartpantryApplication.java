package dev.carlos.smartpantry;

import dev.carlos.smartpantry.entities.User;
import dev.carlos.smartpantry.repository.UserRepository;
import dev.carlos.smartpantry.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@SpringBootApplication
public class SmartpantryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartpantryApplication.class, args);
	}
	}

