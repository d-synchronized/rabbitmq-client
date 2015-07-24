package uk.co.techblue.rabbitmq.core.producer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.techblue.rabbitmq.core.RabbitMqConnectionFactory;
import uk.co.techblue.rabbitmq.core.exceptions.RabbitMqClientException;
import uk.co.techblue.rabbitmq.dto.Message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;

/**
 * Provides utilities to publish messages to queues.
 */
public final class RabbitMqPublish {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(RabbitMqPublish.class);

    /** The _instance. */
    private static RabbitMqPublish _instance;

    /** The _channel. */
    private Channel _channel;

    /** The _json object mapper. */
    private static ObjectMapper _jsonObjectMapper = new ObjectMapper();

    /**
     * Non-argument constructor.
     */
    public RabbitMqPublish() {
    }

    /**
     * Gets the singleton instance of this class.
     *
     * @return singleton instance of this class
     */
    public static synchronized RabbitMqPublish instance() {

        if (_instance == null) {
            _instance = new RabbitMqPublish();
        }
        return _instance;
    }

    /**
     * Constructs the message to publish.
     *
     * @param message Message to be published
     * 
     * @param rabbitMqConnection The {@link RabbitMqConnectionFactory} to provide the {@link Channel} where to publish messages
     *
     * @throws IOException if an I/O problem is encountered
     * @throws RabbitMqClientException if establishing a new channel fails
     */
    public void send(Message message, RabbitMqConnectionFactory rabbitMqConnection) throws IOException, RabbitMqClientException {

        _channel = rabbitMqConnection.newChannel();
        _channel.exchangeDeclare(message.getExchange(), message.getExchangeType(), true);

        _channel.basicPublish(message.getExchange(), message.getRoutingKey(), message.getBasicProperties().builder().build(),
                _jsonObjectMapper.writeValueAsBytes(message));

        LOG.info("RabbitMQ message sent: {}", message);
    }
}