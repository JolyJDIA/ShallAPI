package jolyjdia.api.player.section;

import jolyjdia.api.player.IBaseGamer;

@Deprecated
public class Section {
    private final IBaseGamer iBaseGamer;
    public Section(IBaseGamer iBaseGamer) {
        this.iBaseGamer = iBaseGamer;
    }

    public IBaseGamer getBaseGamer() {
        return iBaseGamer;
    }
}
