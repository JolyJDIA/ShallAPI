package jolyjdia.api.skin.services;

import jolyjdia.api.skin.Skin;
import jolyjdia.api.skin.SkinType;
import jolyjdia.api.skin.exeptions.SkinRequestException;
import jolyjdia.api.skin.response.SkinProperty;
import jolyjdia.api.skin.response.SkinResponse;
import jolyjdia.api.skin.response.UUIDResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public final class MojangSkinService extends SkinService {

    public MojangSkinService(String uuidUrl, String skinUrl) {
        super(uuidUrl, skinUrl);
    }

    @Override
    public SkinType getSkinType() {
        return SkinType.MOJANG;
    }

    /**
     * @param name
     * @return
     * @throws SkinRequestException
     */
    @Override
    public Skin getSkinByName(String name) {
        try {
            return getSkinByUUID(getUUID(name));
        } catch (RuntimeException e) {
            //return GlobalLoader.getSkin(name);
        }
        return Skin.DEFAULT;
    }

    /**
     * @param name
     * @return
     * @throws SkinRequestException
     */
    private String getUUID(String name) {
        UUIDResponse response;
        try {
            response = readResponse(uuidUrl + URLEncoder.encode(name, StandardCharsets.UTF_8), UUIDResponse.class);
            if (!response.getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("name");
            }
        } catch (IOException e) {
            throw new SkinRequestException(name + " не имеет лицензии", e);
        }

        return response.getId();
    }

    /**
     * @param name
     * @return
     * @throws SkinRequestException
     */
    public Skin getSkinByUUID(String name) {
        try {
            SkinResponse skinResponse = readResponse(skinUrl + URLEncoder.encode(name, StandardCharsets.UTF_8)
                    + "?unsigned=false", SkinResponse.class);
            SkinProperty property = skinResponse.getProperties();
            return property.toSkin(skinResponse.getName(), getSkinType());
        } catch (IOException e) {
            throw new SkinRequestException("Произошла ошибка при загрузке скина", e);
        }
    }
}