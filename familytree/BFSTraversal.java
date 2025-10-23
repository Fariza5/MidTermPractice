package familytree;

import java.util.*;

public class BFSTraversal implements TraversalStrategy {
    @Override
    public List<Person> ancestors(Person p, int generations) {
        List<Person> out = new ArrayList<>();
        Queue<Person> q = new LinkedList<>();
        Map<Person, Integer> depth = new HashMap<>();
        q.add(p);
        depth.put(p, 0);
        Set<Person> seen = new HashSet<>();
        seen.add(p);
        while (!q.isEmpty()) {
            Person cur = q.remove();
            int d = depth.get(cur);
            if (d > 0) out.add(cur);
            if (d >= generations) continue;
            for (Person parent : cur.getParents()) {
                if (!seen.contains(parent)) {
                    seen.add(parent);
                    depth.put(parent, d + 1);
                    q.add(parent);
                }
            }
        }
        return out;
    }

    @Override
    public List<Person> descendants(Person p, int generations) {
        List<Person> out = new ArrayList<>();
        Queue<Person> q = new LinkedList<>();
        Map<Person, Integer> depth = new HashMap<>();
        q.add(p);
        depth.put(p, 0);
        Set<Person> seen = new HashSet<>();
        seen.add(p);
        while (!q.isEmpty()) {
            Person cur = q.remove();
            int d = depth.get(cur);
            if (d > 0) out.add(cur);
            if (d >= generations) continue;
            for (Person child : cur.getChildren()) {
                if (!seen.contains(child)) {
                    seen.add(child);
                    depth.put(child, d + 1);
                    q.add(child);
                }
            }
        }
        return out;
    }
}
