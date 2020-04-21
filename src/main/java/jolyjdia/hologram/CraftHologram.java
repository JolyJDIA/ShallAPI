package jolyjdia.hologram;

import jolyjdia.api.entity.CraftGamer;
import jolyjdia.api.hologram.HoloLine;
import jolyjdia.api.hologram.Hologram;
import jolyjdia.api.hologram.lines.AnimationHoloLine;
import jolyjdia.api.hologram.lines.ItemDropLine;
import jolyjdia.api.hologram.lines.ItemFloatingLine;
import jolyjdia.api.hologram.lines.TextHoloLine;
import jolyjdia.hologram.lines.CraftHoloLine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class CraftHologram implements Hologram {
    private final HologramManager hologramManager;
    private Player owner;
    private Location location;
    private final List<CraftHoloLine> lines = new ArrayList<>();
    private boolean visionAll;
    private final CraftHologramDepend depend;

    CraftHologram(HologramManager hologramManager, Location location) {
        this.hologramManager = hologramManager;

        this.location = location.clone().subtract(0, 0.5, 0);
        this.depend = new CraftHologramDepend(this);

        hologramManager.addHologram(this);
    }

    @Override
    public boolean isVisibleTo(Player player) {
        return !lines.isEmpty() && lines.get(0).isVisibleTo(player);
    }

    @Override
    public Collection<? extends Player> getVisiblePlayers() {
        return visionAll ? Bukkit.getOnlinePlayers() : depend.playersVision;
    }

    @Override
    public void showTo(Player player) {
        if (player == null || !player.isOnline())
            return;
        if (isVisibleTo(player))
            return;

        depend.playersVision.add(player);
        lines.forEach(holoLine -> holoLine.showTo(player));
    }

    @Override
    public void showTo(CraftGamer gamer) {
        if (gamer == null)
            return;

        Player player = gamer.getPlayer();
        if (player == null || !player.isOnline())
            return;

        showTo(player);
    }

    @Override
    public void removeTo(Player player) {
        if (!getVisiblePlayers().remove(player))
            return;

        lines.forEach(holoLine -> holoLine.hideTo(player));
    }

    @Override
    public void removeTo(CraftGamer gamer) {
        if (gamer == null)
            return;

        Player player = gamer.getPlayer();
        if (player == null || !player.isOnline())
            return;

        removeTo(player);
    }

    @Override
    public void hideAll() {
        setPublic(false);
        Bukkit.getOnlinePlayers().forEach(this::removeTo);
    }

    @Override
    public Location getLocation() {
        return location.clone();
    }

    public List<CraftHoloLine> getLines() {
        return lines;
    }

    @Override
    public boolean isPublic() {
        return visionAll;
    }

    @Override
    public void setPublic(boolean vision) {
        visionAll = vision;
        lines.forEach(holoLine -> holoLine.setPublic(vision));
    }

    @Override
    public void onTeleport(Location location) {
        this.location = location.clone().subtract(0, 0.5, 0);
        depend.updateLines();
    }

    @Override
    public AnimationHoloLine addAnimationLine(int speed, Supplier<String> replacerLine) {
        return depend.setAnimationLine(lines.size(), speed, replacerLine);
    }

    @Override
    public TextHoloLine addTextLine(String text) {
        return depend.setTextLine(lines.size(), text);
    }

    @Override
    public void addTextLine(Collection<String> listText) {
        listText.forEach(this::addTextLine);
    }

    @Override
    public ItemDropLine addDropLine(boolean pickup, Material item) {
        return depend.setItemDropLine(lines.size(), pickup, item);
    }

    @Override
    public ItemFloatingLine addBigItemLine(boolean rotate, Material item) {
        return depend.setItemFloatingLine(rotate, lines.size(), item);
    }

    @Override
    public TextHoloLine insertTextLine(int index, String text) {
        TextHoloLine textHoloLine = depend.setTextLine(index, text);
        depend.updateLines();
        return textHoloLine;
    }

    @Override
    public ItemDropLine insertDropLine(int index, boolean pickup, Material item) {
        ItemDropLine itemDropLine = depend.setItemDropLine(index, pickup, item);
        depend.updateLines();
        return itemDropLine;
    }

    @Override
    public ItemFloatingLine insertBigItemLine(int index, boolean rotate, Material item) {
        ItemFloatingLine itemFloatingLine = depend.setItemFloatingLine(rotate, index, item);
        depend.updateLines();
        return itemFloatingLine;
    }

    @Override
    public List<HoloLine> getHoloLines() {
        return new ArrayList<>(lines);
    }

    @Override
    public <T extends HoloLine> T getHoloLine(int index) {
        if (lines.size() <= index) {
            return null;
        }
        return (T) lines.get(index);
    }

    @Override
    public int getSize() {
        return lines.size();
    }

    @Override
    public void removeLine(int index) {
        if (lines.isEmpty())
            return;

        if (index >= 0 && index < lines.size()) {
            CraftHoloLine craftHoloLine = lines.get(index);
            lines.remove(craftHoloLine);
            craftHoloLine.remove();
            depend.updateLines();
        }
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Player owner) {
        this.owner = owner;
        showTo(owner);
    }

    @Override
    public void removeLine(HoloLine line) {
        CraftHoloLine craftHoloLine = (CraftHoloLine) line;
        if (lines.isEmpty() || !lines.contains(craftHoloLine)) {
            return;
        }
        lines.remove(craftHoloLine);
        craftHoloLine.remove();
        depend.updateLines();
    }

    @Override
    public void remove() {
        hologramManager.removeHologram(this);
    }

    public HologramManager getHologramManager() {
        return hologramManager;
    }
}
