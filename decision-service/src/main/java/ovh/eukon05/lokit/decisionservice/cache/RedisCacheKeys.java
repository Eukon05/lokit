package ovh.eukon05.lokit.decisionservice.cache;

public final class RedisCacheKeys {
    private RedisCacheKeys() {
    }

    public static final String REDIS_ACTIVE_ROOMS_KEY = "active.rooms";
    public static final String REDIS_ACTIVE_CARDS_KEY = "active.cards";
    public static final String REDIS_ACTIVE_ROLES_KEY = "active.roles";

    public static final String REDIS_CARD_USER_MAPPING_KEY = "cards.%s.user";
    public static final String REDIS_DEVICE_ROOM_MAPPING_KEY = "devices.%s.room";
    public static final String REDIS_DEVICE_TOKEN_HASH_MAPPING_KEY = "devices.%s.token";
    public static final String REDIS_TOKEN_HASH_DEVICE_MAPPING_KEY = "tokens.%s.device";

    public static final String REDIS_USER_ROLES_SET_KEY = "users.%s.roles";
    public static final String REDIS_USER_CARDS_SET_KEY = "users.%s.cards";
    public static final String REDIS_ROOM_ROLES_SET_KEY = "rooms.%s.roles";
}
