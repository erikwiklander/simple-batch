package io.wiklandia.batch;

import java.util.Map;

import org.springframework.batch.item.file.transform.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("mapping")
public class CustomProperties {

	private Map<Supplier, Map<String, Range>> suppliers;

	public enum Supplier {
		VING, APOLLO
	}

}
