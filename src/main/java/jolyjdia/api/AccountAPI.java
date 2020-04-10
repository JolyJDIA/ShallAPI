package jolyjdia.api;

import jolyjdia.api.player.GamePlayer;
import java.util.*;

@Deprecated
public class AccountAPI {
    //чичирую игроков, чтобы не делать 1000 запросов в секунду, IdentityHashMap т к UUID - ссылка
    private static final Map<UUID, GamePlayer> GAMER_DATA_MAP = new IdentityHashMap<>();

    /*private static final LoadingCache<UUID, GamerData> OFFLINE_CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build(new CacheLoader<>() {
                @Override
                public GamerData load(@NotNull UUID key) {
                    return new GamerData(key);
                }
            });*/


    public static Optional<GamePlayer> getIfLoaded(UUID key) {
        return Optional.ofNullable(GAMER_DATA_MAP.get(key));
    }
    public static GamePlayer loadGamerIfAbsentOrGet(UUID key) {
        return GAMER_DATA_MAP.computeIfAbsent(key, uuid -> new GamePlayer(key));
    }
    public static void removeGamer(UUID uuid) {
        GAMER_DATA_MAP.remove(uuid);
    }
    public boolean containsGamer(UUID uuid) {
        return GAMER_DATA_MAP.containsKey(uuid);
    }
    public static Collection<GamePlayer> getAllGamerData() {
        return GAMER_DATA_MAP.values();
    }
    public static void saveAndDeleteGamer(UUID uuid) {
        //save
        GAMER_DATA_MAP.remove(uuid);
    }
}
