package familytree;

import java.util.*;

public class DFSTraversal implements TraversalStrategy {
    @Override
    public List<Person> ancestors(Person p, int generations) {
        List<Person> out = new ArrayList<>();
        dfsAnc(p, 0, generations, out, new HashSet<>());
        return out;
    }

    private void dfsAnc(Person current, int level, int maxLevel, List<Person> out, Set<Person> visited) {
        if (current == null || visited.contains(current)) return;
        visited.add(current);
        if (level > 0) out.add(current);
        if (level >= maxLevel) return;
        for (Person parent : current.getParents()) {
            dfsAnc(parent, level + 1, maxLevel, out, visited);
        }
    }

    @Override
    public List<Person> descendants(Person p, int generations) {
        List<Person> out = new ArrayList<>();
        dfsDesc(p, 0, generations, out, new HashSet<>());
        return out;
    }

    private void dfsDesc(Person current, int level, int maxLevel, List<Person> out, Set<Person> visited) {
        if (current == null || visited.contains(current)) return;
        visited.add(current);
        if (level > 0) out.add(current);
        if (level >= maxLevel) return;
        for (Person child : current.getChildren()) {
            dfsDesc(child, level + 1, maxLevel, out, visited);
        }
    }
}
