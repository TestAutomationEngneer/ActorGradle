package com.example;

import com.example.models.Actor;
import com.example.repository.ActorRepository;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
@Slf4j
public class ActorApiTest {

    @Inject
    ActorRepository actorRepository;

    @Test
    void shouldGetSingleActor() {
        Actor expectedActor = new Actor("Tom", "Hanks", 1, true);
        log.info("Actual actor: " + expectedActor);
        Actor actualActor = actorRepository.findById(1L).get();
        log.info("Expected actor: " + actualActor);
        assertThat(actualActor.getFirstName()).isEqualTo(expectedActor.getFirstName());
        assertThat(actualActor.getLastName()).isEqualTo(expectedActor.getLastName());
        assertThat(actualActor.getRating()).isEqualTo(expectedActor.getRating());
        assertThat(actualActor.isOscarPrized()).isEqualTo(expectedActor.isOscarPrized());
    }

    @Test
    void shouldLoadActorsRepository() {
      var loadedActors = actorRepository.getActors();
        assertThat(loadedActors).hasSize(20);
        log.info("All actors loaded successfully.");
    }

    @Test
    void shouldSaveNewActor() {
        Long newActorId = 21L;
        Actor newActor = new Actor(newActorId,"Cezary", "Pazura", 99, false);
        actorRepository.save(newActor);
        Actor actualActor = actorRepository.findById(newActorId).get();
        assertThat(actualActor.getFirstName()).isEqualTo(newActor.getFirstName());
        assertThat(actualActor.getLastName()).isEqualTo(newActor.getLastName());
        assertThat(actualActor.getRating()).isEqualTo(newActor.getRating());
        assertThat(actualActor.isOscarPrized()).isEqualTo(newActor.isOscarPrized());
        log.info(String.format("New actor %s has been saved", actualActor));
        actorRepository.deleteById(newActorId);
    }
}
