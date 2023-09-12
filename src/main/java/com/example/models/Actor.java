package com.example.models;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Introspected
@Serdeable.Serializable
@Serdeable.Deserializable
public class Actor {

    private static Long idCounter = 0L;

    private Long id;
    private String firstName;
    private String lastName;
    private int rating;
    private boolean oscarPrized;

    public Actor(String firstName, String lastName, int rating, boolean oscarPrized) {
        this.id = ++idCounter;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
        this.oscarPrized = oscarPrized;
    }
    public Actor(Long id, String firstName, String lastName, int rating, boolean oscarPrized) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
        this.oscarPrized = oscarPrized;
    }
}

