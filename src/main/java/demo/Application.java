package demo;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.util.IdGenerator;

import reactor.Environment;
import reactor.bus.EventBus;
import reactor.core.support.UUIDUtils;
import reactor.spring.context.config.EnableReactor;

@SpringBootApplication
@EnableReactor
@ImportResource("classpath:sftp.xml")
public class Application {

	static {
		Environment.initializeIfEmpty().assignErrorJournal();
	}

	@Bean
	public EventBus eventBus() {
		return EventBus.config().env(Environment.get()).dispatcher(Environment.SHARED).get();
	}

	@Bean
	public IdGenerator randomUUIDGenerator() {
		return new IdGenerator() {
			@Override
			public UUID generateId() {
				return UUIDUtils.random();
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
