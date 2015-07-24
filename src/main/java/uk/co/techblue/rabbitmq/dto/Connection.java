package uk.co.techblue.rabbitmq.dto;

/**
 * Class to make valid AMQP connection URI to be used to construct new AMQP connections.
 * 
 */
public class Connection {

    /** The _virtual host. */
    private String _virtualHost;

    /** The _rabbit mq user name. */
    private String _rabbitMqUserName;

    /** The _rabbit mq password. */
    private String _rabbitMqPassword;

    /** The _server address. */
    private String _serverAddress;

    /**
     * Constructs the AMQP URI to used to initialize new AMQP connection. Valid Connection properties fields in an AMQP URI are:
     * host, port, user name, password and virtual host.
     * 
     * @param virtualHost virtual host to use
     * @param rabbitMqUserName user name to use
     * @param rabbitMqPassword password to use
     * @param serverAddress server address to use to make the connection also should contain the port
     */
    public Connection(String virtualHost, String rabbitMqUserName, String rabbitMqPassword, String serverAddress) {
        _virtualHost = virtualHost;
        _rabbitMqUserName = rabbitMqUserName;
        _rabbitMqPassword = rabbitMqPassword;
        _serverAddress = serverAddress;
    }

    /**
     * Gets the connection uri.
     *
     * @return AMQP URI to construct new AMQP connection
     * @see <a href="https://www.rabbitmq.com/uri-spec.html>AMQP URI Specification</a>
     */
    public String getConnectionUri() {

        StringBuilder builder = new StringBuilder("amqp://");
        builder.append(_rabbitMqUserName);
        builder.append(":");
        builder.append(_rabbitMqPassword);
        builder.append("@");
        builder.append(_serverAddress);
        builder.append(_virtualHost);

        return builder.toString();
    }

    /**
     * Get the virtual host.
     *
     * @return virtual host
     */
    public String getVirtualHost() {
        return _virtualHost;
    }

    /**
     * Get the user name.
     *
     * @return user name
     */
    public String getUserName() {
        return _rabbitMqUserName;
    }

    /**
     * Get the password.
     *
     * @return password
     */
    public String getPassword() {
        return _rabbitMqPassword;
    }

    /**
     * Get the server address.
     *
     * @return server address
     */
    public String getServerAddress() {
        return _serverAddress;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("[virtualHost = %s, userName = %s, password = %s, " + "serverAddress = %s]", _virtualHost,
                _rabbitMqUserName, _rabbitMqPassword, _serverAddress);
    }
}