package com.example;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.IgnoreNoPactsToVerify;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.example.models.Actor;
import com.example.repository.ActorRepository;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@MicronautTest(transactional = false)
@Provider("actor")
@PactBroker
//@PactFolder("pact")
@IgnoreNoPactsToVerify
@Tag("pact")
public class MovieContractVerificationTest {

    @Inject
    ActorRepository repository;
    @Inject
    EmbeddedServer server;

    @BeforeEach
    void configurePact(PactVerificationContext pactContext) {
        if (pactContext != null) {
            pactContext.setTarget(new HttpTestTarget("localhost", server.getPort(), "/"));
        }
        repository.deleteAll();
    }

    @State("Tomasz Karolak exists")
    void actorExists() {
        Actor karolak = new Actor(99L,"Tomasz", "Karolak", 99, false);
        repository.save(karolak);
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerification(PactVerificationContext pactVerificationContext) {
        if (pactVerificationContext != null) {
            pactVerificationContext.verifyInteraction();
        }
    }
}
