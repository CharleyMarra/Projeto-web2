package br.ifg.urt.gamercatalog_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GamercatalogApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamercatalogApiApplication.class, args);
	}

}
