package ovh.eukon05.lokit.common.config;

public final class RabbitConstants {
    private RabbitConstants() {
    }
    // Exchanged
    public static final String EXCHANGE_NAME = "lokit.events";

    //Routing keys
    public static final String ROLE_CREATED_ROUTING_KEY = "role.created";
    public static final String ROLE_DELETED_ROUTING_KEY = "role.deleted";
    public static final String ROLE_ENABLED_ROUTING_KEY = "role.enabled";
    public static final String ROLE_DISABLED_ROUTING_KEY = "role.disabled";

    public static final String USER_ROLE_ASSIGNED_ROUTING_KEY = "user.role.assigned";
    public static final String USER_ROLE_REMOVED_ROUTING_KEY = "user.role.removed";
    public static final String USER_DELETED_ROUTING_KEY = "user.deleted";

    public static final String ROOM_CREATED_ROUTING_KEY = "room.created";
    public static final String ROOM_ENABLED_ROUTING_KEY = "room.enabled";
    public static final String ROOM_DISABLED_ROUTING_KEY = "room.disabled";
    public static final String ROOM_ROLE_ASSIGNED = "room.roles.assigned";
    public static final String ROOM_ROLE_REMOVED = "room.roles.removed";

    public static final String CARD_CREATED_ROUTING_KEY = "card.created";
    public static final String CARD_DELETED_ROUTING_KEY = "card.deleted";
    public static final String CARD_ENABLED_ROUTING_KEY = "card.enabled";
    public static final String CARD_DISABLED_ROUTING_KEY = "card.disabled";

    public static final String DEVICE_CREATED_ROUTING_KEY = "device.created";
    public static final String DEVICE_DELETED_ROUTING_KEY = "device.deleted";
    public static final String DEVICE_TOKEN_ASSIGNED_ROUTING_KEY = "device.token.assigned";
    public static final String DEVICE_TOKEN_REVOKED_ROUTING_KEY = "device.token.revoked";
    public static final String DEVICE_ROOM_ASSIGNED_ROUTING_KEY = "device.room.assigned";
    public static final String DEVICE_ROOM_REMOVED_ROUTING_KEY = "device.room.removed";

    // Queues
    public static final String ROOM_SERVICE_QUEUE = "lokit.roomservice.queue";
    public static final String ROLE_SERVICE_QUEUE = "lokit.roleservice.queue";
    public static final String CARD_SERVICE_QUEUE = "lokit.cardservice.queue";
    public static final String DECISION_SERVICE_QUEUE = "lokit.decisionservice.queue";
}
