package com.example.repository;

import com.example.models.Actor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
@Slf4j
public class ActorRepository {

    private List<Actor> actors = new ArrayList<>();

    public List<Actor> getActors() {
        return actors;
    }

    @EventListener
    public void onStartup(StartupEvent event) {
//        // Wczytywanie aktorów z pliku JSON przy starcie serwisu
        ObjectMapper mapper = new ObjectMapper();

        try {
            File file = new File(
                    getClass().getClassLoader().getResource("actors.json").getFile()
            );
            actors = mapper.readValue(file, new TypeReference<List<Actor>>(){});

            // Przypisz unikalne ID dla każdego aktora
            for (Long i = 0L; i < actors.size(); i++) {
                actors.get(Math.toIntExact(i)).setId(i + 1);
            }
            log.info("Actors details loaded successfully!");
        } catch (IOException e) {
            throw new RuntimeException("Error loading actors: " + e.getMessage());
        }
    }


    public List<Actor> findAll() {
        return actors;
    }

    public Optional<Actor> findById(Long id) {
        return actors.stream().filter(actor -> actor.getId() == id).findFirst();
    }

    public void save(Actor actor) {
        actors.add(actor);
    }

    public boolean deleteById(Long id) {
        return actors.removeIf(actor -> actor.getId() == id);
    }

    public void deleteAll() {
        actors.clear();
    }
}
