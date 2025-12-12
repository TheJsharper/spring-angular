package com.jsharper.dyndns.server.entities.gen;

import org.hibernate.annotations.IdGeneratorType;
import org.hibernate.annotations.ValueGenerationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@ValueGenerationType(generatedBy = CustomRandomIDGenerator.class)
@IdGeneratorType(CustomRandomIDGenerator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CustomIdGenerator {
    String name();
}
