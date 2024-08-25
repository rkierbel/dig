package util;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public class RabbitMQContainer extends GenericContainer<RabbitMQContainer> {

    public static final String RABBIT_IMAGE_NAME = "rabbitmq";
    public static final String RABBIT_IMAGE_TAG = "4.0-rc-management-alpine";
    private static RabbitMQContainer container;

    public RabbitMQContainer() {
        this(DockerImageName.parse(RABBIT_IMAGE_NAME).withTag(RABBIT_IMAGE_TAG));
    }

    public RabbitMQContainer(DockerImageName dockerImage) {
        super(dockerImage);
        this.logger().info("Starting RabbitMQ container using [{}]", dockerImage);
        this.withExposedPorts(5672, 15672);
        this.withExtraHost("localhost", "127.0.0.1");
        this.waitingFor(Wait.forLogMessage(".*Server startup complete.*", 1));
    }

    public synchronized static RabbitMQContainer getInstance() {
        if (container == null) {
            container = new RabbitMQContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
    }
}
