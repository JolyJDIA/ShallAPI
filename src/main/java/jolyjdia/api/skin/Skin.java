package jolyjdia.api.skin;

import java.util.concurrent.TimeUnit;

public class Skin {
    public static final Skin DEFAULT = SkinAPI.getSkin("JolyHohol");
    private final String skinName;
    private final String value;
    private final String signature;
    private final SkinType skinType;
    private final long updateTime;

    public Skin(String skinName, String value, String signature, SkinType skinType, long updateTime) {
        this.skinName = skinName;
        this.value = value;
        this.signature = signature;
        this.skinType = skinType;
        this.updateTime = updateTime;
    }

    public boolean isExpired() {
        return updateTime != 0 && System.currentTimeMillis() > updateTime + TimeUnit.HOURS.toMillis(24);
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }

    public String getSkinName() {
        return skinName;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public SkinType getSkinType() {
        return skinType;
    }

    public static Skin createSkin(String value, String signature) {
        return new Skin("SKIN_DEFAULT", value, signature, SkinType.CUSTOM, 0);
    }
}