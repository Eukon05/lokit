package ovh.eukon05.lokit.deviceservice.client;

public interface MqttDynsecClient {
    void createClient(String clientId, String username);

    void setClientPassword(String username, String password);

    void enableClient(String username);

    void disableClient(String username);

    void deleteClient(String username);
}
