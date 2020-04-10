package jolyjdia.api.skin.response;

import jolyjdia.api.skin.Skin;
import jolyjdia.api.skin.SkinType;

public class SkinProperty {
    private final String name;
    private final String value;
    private final String signature;

    public SkinProperty(String name, String value, String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public Skin toSkin(String skinName, SkinType skinType) {
        return new Skin(skinName, value, signature, skinType, System.currentTimeMillis());
    }

    public String getName() {
        return name;
    }

    public String getSignature() {
        return signature;
    }

    public String getValue() {
        return value;
    }
}
