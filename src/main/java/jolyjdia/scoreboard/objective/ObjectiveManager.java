package jolyjdia.scoreboard.objective;

import java.util.HashSet;
import java.util.Set;

public class ObjectiveManager {
    private final Set<CraftObjective> objectives = new HashSet<>();

    public void addObjective(CraftObjective craftObjective) {
        objectives.add(craftObjective);
    }

    public void removeObjective(CraftObjective craftObjective) {
        objectives.remove(craftObjective);
    }

    public Set<CraftObjective> getObjectives() {
        return objectives;
    }
}
