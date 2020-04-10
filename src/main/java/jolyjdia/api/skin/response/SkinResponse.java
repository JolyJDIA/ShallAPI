package jolyjdia.api.skin.response;

import jolyjdia.api.skin.exeptions.SkinRequestException;

public class SkinResponse extends SkinsResponse {

    private final SkinProperty properties;

    public SkinResponse(String id, String name, String message, String type, String error, int status, SkinProperty properties) {
        super(id, name, message, type, error, status);
        this.properties = properties;
    }

    /**
     * @throws SkinRequestException
     */
    @Override
    public void check() {
        super.check();
        assert properties != null : "properties";
    }

    public SkinProperty getProperties() {
        return properties;
    }
}
