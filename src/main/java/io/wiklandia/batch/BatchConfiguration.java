package io.wiklandia.batch;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DefaultFieldSetFactory;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfiguration {
	public final JobBuilderFactory jobBuilderFactory;
	public final StepBuilderFactory stepBuilderFactory;

	public FlatFileItemReader<Trailer> reader() {
		FlatFileItemReader<Trailer> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("trailer.txt"));
		reader.setLineMapper(lineMapper());
		return reader;
	}

	@Bean
	public PersonLineMapper lineMapper() {
		return new PersonLineMapper();
	}

	public static class PersonLineMapper implements LineMapper<Trailer> {

		@Override
		public Trailer mapLine(String line, int lineNumber) throws Exception {

			FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
			tokenizer.setNames("name", "age", "yearOfBirth");
			tokenizer.setColumns(new Range(1, 7), new Range(8, 9), new Range(10, 18));
			tokenizer.setFieldSetFactory(new DefaultFieldSetFactory());
			tokenizer.setStrict(false);

			TrailerFieldSetMapper mapper = new TrailerFieldSetMapper();

			mapper.setTargetType(Trailer.class);
			return mapper.mapFieldSet(tokenizer.tokenize(line));

		}

	}

	public static class TrailerFieldSetMapper extends BeanWrapperFieldSetMapper<Trailer> {

		@Override
		public void registerCustomEditors(PropertyEditorRegistry registry) {
			super.registerCustomEditors(registry);

			registry.registerCustomEditor(String.class, "name", new PropertyEditorSupport() {

				@Override
				public void setAsText(String text) {
					setValue(text + "X");
				}

			});

			registry.registerCustomEditor(Date.class, new PropertyEditorSupport() {
				@Override
				public void setAsText(String text) {
					try {
						setValue(new SimpleDateFormat("yyyyMMdd").parse(text));
					} catch (ParseException e) {
						throw new IllegalArgumentException(e);
					}
				}

			});

		}
	}

	@Bean
	public Job importUserJob(Step step1) {
		return jobBuilderFactory.get("importTrailer").incrementer(new RunIdIncrementer()).flow(step1).end().build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<Trailer, Trailer>chunk(10).reader(reader()).processor(processor())
				.writer(null).build();
	}

	@Bean
	public TrailerItemProcessor processor() {
		return new TrailerItemProcessor();
	}

	public static class TrailerItemProcessor implements ItemProcessor<Trailer, Trailer> {

		@Override
		public Trailer process(Trailer item) throws Exception {
			log.info("This is the trailer: " + item);
			// TODO Auto-generated method stub
			return null;
		}

	}

}
