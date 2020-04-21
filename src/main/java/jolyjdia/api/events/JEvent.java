package jolyjdia.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class JEvent extends Event {
    public static final HandlerList HANDLER_LIST = new HandlerList();

    public JEvent(boolean async) {
        super(async);
    }

    protected JEvent() {}

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
