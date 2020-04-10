package jolyjdia.api.skin.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import jolyjdia.api.skin.Skin;
import jolyjdia.api.skin.SkinType;
import jolyjdia.api.skin.exeptions.SkinRequestException;
import jolyjdia.api.skin.response.SkinProperty;
import jolyjdia.api.skin.response.SkinSerializer;
import jolyjdia.api.skin.response.SkinsResponse;
import org.jetbrains.annotations.NonNls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public abstract class SkinService {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(SkinProperty.class, new SkinSerializer())
            .create();

    @NonNls protected final String uuidUrl;
    @NonNls protected final String skinUrl;

    protected SkinService(String uuidUrl, String skinUrl) {
        this.uuidUrl = uuidUrl;
        this.skinUrl = skinUrl;
    }

    /**
     * @param url
     * @param responseClass
     * @param <T>
     * @return
     * @throws IOException
     * @throws SkinRequestException
     */
    protected <T extends SkinsResponse> T readResponse(String url, Class<T> responseClass) throws IOException {
        try(InputStream inputStream = makeConnection(url).getInputStream();
            ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while (true) {
                int read = inputStream.read(buffer);
                if((length = read) == -1) {
                    break;
                }
                result.write(buffer, 0, length);
            }

            String json = result.toString(StandardCharsets.UTF_8);

            try {
                T response = GSON.fromJson(json, responseClass);
                if (response == null) {
                    throw new NullPointerException("response");
                } else {
                    response.check();
                    return response;
                }
            } catch (JsonSyntaxException e) {
                throw new IOException("[Skins]: JsonParse пошел нахуй крч " + json, e);
            }
        }
    }

    protected HttpURLConnection makeConnection(String spec) throws IOException {
        try {
            URL url = new URL(spec);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "ZA_WARDO");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setDoOutput(true);
            return connection;
        } catch (MalformedURLException e) {
            throw new SkinRequestException("[Skins]: Ты еблан, ты еблан, соси хуй, еблан " + spec, e);
        }
    }

    public abstract SkinType getSkinType();

    /**
     * @param name
     * @return
     * @throws SkinRequestException
     */
    public abstract Skin getSkinByName(String name);
}
