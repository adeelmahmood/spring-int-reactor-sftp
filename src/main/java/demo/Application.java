package demo;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.IdGenerator;

import reactor.Environment;
import reactor.bus.EventBus;
import reactor.core.Dispatcher;
import reactor.core.support.UUIDUtils;
import reactor.spring.context.config.EnableReactor;

@SpringBootApplication
@EnableReactor
// @ImportResource("classpath:sftp.xml")
public class Application implements CommandLineRunner {

	static {
		Environment.initializeIfEmpty().assignErrorJournal();
	}

	@Value("${reactor.thread.count}")
	private int threadCount;

	@Value("${reactor.backlog}")
	private int backlog;

	@Bean
	public EventBus eventBus() {
		return EventBus.config().env(Environment.get())
				.dispatcher(dispatcher())
				.get();
	}

	@Bean
	public Dispatcher dispatcher() {
		return Environment.newDispatcher(backlog, threadCount);
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

	@Autowired
	private EventBus eventBus;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Checking reactor configuration");
		System.out.println("backlog = " + eventBus.getDispatcher().backlogSize());
	}
}
