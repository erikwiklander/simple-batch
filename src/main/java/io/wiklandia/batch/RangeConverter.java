package io.wiklandia.batch;

import org.springframework.batch.item.file.transform.Range;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RangeConverter implements Converter<String, Range> {

	@Override
	public Range convert(String source) {
		String[] interval = source.split(",");
		return new Range(Integer.parseInt(interval[0]), Integer.parseInt(interval[1]));
	}

}
