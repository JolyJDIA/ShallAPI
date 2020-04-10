package jolyjdia.api.skin;

import jolyjdia.api.skin.exeptions.SkinRequestException;
import jolyjdia.api.skin.services.ElySkinService;
import jolyjdia.api.skin.services.MojangSkinService;
import jolyjdia.api.skin.services.SkinService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SkinAPI {
    private static final SkinService ELY_API = new ElySkinService(
            "https://account.ely.by/api/mojang/profiles/",
            "http://skinsystem.ely.by/textures/signed/"
    );
    private static final SkinService MOJANG_API = new MojangSkinService(
            "https://api.mojang.com/users/profiles/minecraft/",
            "https://sessionserver.mojang.com/session/minecraft/profile/"
    );
    private static final ExecutorService SKIN_EXECUTOR = Executors.newSingleThreadExecutor();

    private SkinAPI() {}

    /**
     * ВНИМАНИЕ! Если вызывать часто, то mojang или Елу забанят!
     * получить скин по нику игрока со скином
     * @param skinName - ник игрока со скином
     * @return - скин
     */
    public static CompletableFuture<Skin> getSkinAsync(String skinName) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return ELY_API.getSkinByName(skinName);
            } catch (SkinRequestException e) {
                try {
                    return MOJANG_API.getSkinByName(skinName);
                } catch (SkinRequestException i) {
                    return Skin.DEFAULT;
                }
            }
        }, SKIN_EXECUTOR);
    }
    /**
     * ВНИМАНИЕ! Если вызывать часто, то mojang или Елу забанят!
     * получить скин по нику игрока со скином
     * @param skinName - ник игрока со скином
     * @return - скин
     */
    public static Skin getSkin(String skinName) {
        try {
            return ELY_API.getSkinByName(skinName);
        } catch (SkinRequestException e) {
            try {
                return MOJANG_API.getSkinByName(skinName);
            } catch (SkinRequestException i) {
                return Skin.DEFAULT;
            }
        }
    }
}
