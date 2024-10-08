package dig.france.insee.sirene.search;

import io.micronaut.context.annotation.Value;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import util.RabbitMQContainer;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Implement TestPropertyProvider => must use annotation to create a single class instance for all tests
class SireneControllerTest implements TestPropertyProvider {

    @Inject
    EmbeddedServer server;

    @Container
    public static RabbitMQContainer rabbitMQContainer = RabbitMQContainer.getInstance();

    @Value("${sirene.api.prefix}${sirene.api.version-3-11}${sirene.api.sirene-search}")
    private String searchUrl;

    @BeforeAll
    public void setUp() {
        RestAssured.port = server.getPort();
        RestAssured.basePath = "/insee/sirene";
    }

    @Override
    public @NonNull Map<String, String> getProperties() {
        if (!rabbitMQContainer.isRunning()) {
            rabbitMQContainer.start();
        }
        return Collections.singletonMap(
                "rabbitmq.uri",
                "amqp://guest:guest@%s:%s".formatted(
                        rabbitMQContainer.getHost(),
                        rabbitMQContainer.getMappedPort(5672)));
    }

    @Test
    void startingRabbitMqContainer_shouldRunContainer() {
        assertTrue(rabbitMQContainer.isRunning());
    }

    @Test
    void callingPing_shouldSendMessageAndResponseOk() {
        RestAssured
                .given().param("message", "hello")
                .when().get("/ping")
                .then().statusCode(200);
    }

    @Test
    void validNaturalPersonSearch_shouldReturnOk() { //TODO -> mock insee API
        //stubFor(get())
    }

}