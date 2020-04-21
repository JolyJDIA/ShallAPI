package jolyjdia.api.player.section;

import jolyjdia.api.constant.GroupImpl;
import jolyjdia.api.player.IBaseGamer;
import jolyjdia.api.skin.Skin;

@Deprecated
public class BaseSection extends Section {
    private GroupImpl groupImp;
    private Skin skin;

    public BaseSection(IBaseGamer iBaseGamer) {
        super(iBaseGamer);
    }

    public Skin getSkin() {
        return skin;
    }

    public GroupImpl getGroupImp() {
        return groupImp;
    }

    public void setGroupImp(GroupImpl groupImp) {
        this.groupImp = groupImp;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }
}
