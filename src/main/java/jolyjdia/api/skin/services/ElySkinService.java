package jolyjdia.api.skin.services;

import jolyjdia.api.skin.Skin;
import jolyjdia.api.skin.SkinType;
import jolyjdia.api.skin.exeptions.SkinRequestException;
import jolyjdia.api.skin.exeptions.TooManyRequestsSkinException;
import jolyjdia.api.skin.response.SkinProperty;
import jolyjdia.api.skin.response.SkinResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ElySkinService extends SkinService {

    public ElySkinService(String uuidUrl, String skinUrl) {
        super(uuidUrl, skinUrl);
    }

    /**
     * @param spec
     * @return
     * @throws IOException
     * @throws SkinRequestException
     */
    @Override
    protected HttpURLConnection makeConnection(String spec) throws IOException {
        HttpURLConnection connection = super.makeConnection(spec);
        switch (connection.getResponseCode()) {
            case 204:
                throw new SkinRequestException("Скин не загружен... кажется что-то пошло не так");
            case 429:
                throw new TooManyRequestsSkinException();
            default:
                return connection;
        }
    }

    @Override
    public SkinType getSkinType() {
        return SkinType.ELY;
    }

    /**
     * @param name
     * @return
     * @throws SkinRequestException
     */
    @Override
    public Skin getSkinByName(String name) {
        try {
            SkinResponse skinResponse = readResponse(skinUrl + URLEncoder.encode(name, StandardCharsets.UTF_8)
                    + "?unsigned=false&token=", SkinResponse.class);
            SkinProperty property = skinResponse.getProperties();
            return property.toSkin(skinResponse.getName(), getSkinType());
        } catch (IOException e) {
            throw new SkinRequestException("Произошла ошибка при загрузке скина", e);
        }
    }
}