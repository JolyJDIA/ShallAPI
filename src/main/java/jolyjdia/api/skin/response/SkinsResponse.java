package jolyjdia.api.skin.response;

import jolyjdia.api.skin.exeptions.SkinRequestException;
import org.jetbrains.annotations.NonNls;

public class SkinsResponse {
    private final String id;
    private final String name;

    @NonNls private final String error;
    @NonNls private final String message;
    private final String type;
    private final int status;

    public SkinsResponse(String id, String name, String message, String type, String error, int status) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.type = type;
        this.error = error;
        this.status = status;
    }

    /**
     * @throws SkinRequestException
     */
    public void check() {
        if (error != null) {
            throw new SkinRequestException("Произошла ошибка при получении данных скина " + "\nError: " + error);
        } else if (name != null && message != null && type != null && status != 200) {
            throw new SkinRequestException("Произошла ошибка при получении данных скина" + "\nMessage: " + message);
        } else if (id != null && !id.isEmpty()) {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("name");
            }
        } else {
            throw new IllegalArgumentException("id");
        }
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
