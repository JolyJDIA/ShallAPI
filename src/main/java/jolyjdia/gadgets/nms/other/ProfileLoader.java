package jolyjdia.gadgets.nms.other;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import jolyjdia.api.skin.Skin;

import java.util.UUID;

class ProfileLoader {
    private final String name;

    ProfileLoader(String var1) {
        name = var1;
    }

    final GameProfile loadProfile(Skin var1) {
        GameProfile var2 = new GameProfile(UUID.randomUUID(), this.name);
        var2.getProperties().put(this.name, new Property(this.name, var1.getValue(), var1.getSignature()));
        return var2;
    }
}
