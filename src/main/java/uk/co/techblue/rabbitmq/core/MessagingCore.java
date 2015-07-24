package uk.co.techblue.rabbitmq.core;

import uk.co.techblue.rabbitmq.core.exceptions.RabbitMqClientException;
import uk.co.techblue.rabbitmq.dto.Connection;
import uk.co.techblue.rabbitmq.dto.Message;

import com.rabbitmq.client.Channel;

/**
 * The Interface RabbitMqHandler.
 */
public interface MessagingCore {

    /**
     * Publish.
     *
     * @param message the message
     * @param connection the connection
     * @throws RabbitMqClientException the rabbit mq client exception
     */
    void publish(Message message, Connection connection) throws RabbitMqClientException;

    /**
     * Subscribe.
     *
     * @param exchangeName the exchange name
     * @param routingKey the routing key
     * @param connection the connection
     * @return the channel
     * @throws RabbitMqClientException the rabbit mq client exception
     */
    Channel subscribe(String exchangeName, String routingKey, Connection connection) throws RabbitMqClientException;

}
