package jolyjdia.api.player.section;

import jolyjdia.api.constant.GroupImp;
import jolyjdia.api.player.IBaseGamer;
import jolyjdia.api.skin.Skin;

@Deprecated
public class BaseSection extends Section {
    private GroupImp groupImp;
    private Skin skin;

    public BaseSection(IBaseGamer iBaseGamer) {
        super(iBaseGamer);
    }

    public Skin getSkin() {
        return skin;
    }

    public GroupImp getGroupImp() {
        return groupImp;
    }

    public void setGroupImp(GroupImp groupImp) {
        this.groupImp = groupImp;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }
}
