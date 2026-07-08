package ovh.eukon05.lokit.common.dto.config;

public final class RabbitConstants {
    public static final String EXCHANGE_NAME = "lokit.events";
    public static final String ROLE_DELETED_ROUTING_KEY = "role.deleted";
    public static final String ROLE_ENABLED_ROUTING_KEY = "role.enabled";
    public static final String ROLE_DISABLED_ROUTING_KEY = "role.disabled";

    public static final String ROOM_ENABLED_ROUTING_KEY = "room.enabled";
    public static final String ROOM_DISABLED_ROUTING_KEY = "room.disabled";
    public static final String ROOM_SERVICE_QUEUE = "lokit.roomservice.queue";

    public static final String CARD_CREATED_ROUTING_KEY = "card.created";
    public static final String CARD_DELETED_ROUTING_KEY = "card.deleted";
    public static final String CARD_ENABLED_ROUTING_KEY = "card.enabled";
    public static final String CARD_DISABLED_ROUTING_KEY = "card.disabled";

    public static final String DEVICE_CREATED_ROUTING_KEY = "device.created";
    public static final String DEVICE_DELETED_ROUTING_KEY = "device.deleted";
    public static final String DEVICE_ENABLED_ROUTING_KEY = "device.enabled";
    public static final String DEVICE_DISABLED_ROUTING_KEY = "device.disabled";
    public static final String DEVICE_ROOM_ASSIGNED_ROUTING_KEY = "device.room.assigned";
    public static final String DEVICE_ROOM_REMOVED_ROUTING_KEY = "device.room.removed";
}
