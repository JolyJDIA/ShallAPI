package jolyjdia.api.skin.response;

import com.google.gson.*;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

public class SkinSerializer implements JsonDeserializer<SkinProperty> {

    /**
     * @param json
     * @param typeOfT
     * @param context
     * @return
     * @throws JsonParseException
     */
    @Nullable
    @Override
    public SkinProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonArray)) {
            return null;
        }
        for (JsonElement jsonElement : json.getAsJsonArray()) {
            if (jsonElement instanceof JsonObject) {
                JsonObject jsonObject = (JsonObject) jsonElement;

                if (jsonObject.has("signature")) {
                    String name = jsonObject.getAsJsonPrimitive("name").getAsString();
                    String value = jsonObject.getAsJsonPrimitive("value").getAsString();
                    String signature = jsonObject.getAsJsonPrimitive("signature").getAsString();
                    return new SkinProperty(name, value, signature);
                }
            }
        }
        return null;
    }
}
