package io.wiklandia.batch;

import static io.wiklandia.batch.CustomProperties.Supplier.VING;

import java.util.Map.Entry;
import java.util.Set;

import org.springframework.batch.item.file.transform.Range;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class BatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}

	@Bean
	public CommandLineRunner doIt(CustomProperties props, PersonRepository perseonRep) {
		return args -> {
			log.info("COOL! {}", props);

			Set<Entry<String, Range>> s = props.getSuppliers().get(VING).entrySet();

			for (Entry<String, Range> entries : s) {

			}
			// log.info("keys: " + props.getSuppliers().get(VING).keySet());
			// log.info("keys: " + props.getSuppliers().get(VING).values());

			log.info("Hoho {}", props.getSuppliers().get(VING));

			Person p1 = new Person();
			p1.setName("Erik");
			Person p2 = new Person();
			p2.setName("Adam");
			perseonRep.save(p1);
			perseonRep.save(p2);
		};
	}
}
