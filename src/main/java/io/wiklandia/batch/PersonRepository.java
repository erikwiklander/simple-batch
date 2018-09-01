package io.wiklandia.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PersonRepository extends JpaRepository<Person, Long>, QuerydslPredicateExecutor<Person> {

}
