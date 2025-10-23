package familytree;

import java.util.*;
import java.util.stream.Collectors;

public class FamilyTree {
    private final Map<String, Person> people = new LinkedHashMap<>();
    private TraversalStrategy traversal;

    public FamilyTree(TraversalStrategy traversal) {
        this.traversal = traversal;
    }

    public void setTraversalStrategy(TraversalStrategy traversal) {
        this.traversal = traversal;
    }

    public String addPerson(Person p) {
        if (people.containsKey(p.getId())) throw new IllegalArgumentException("Duplicate id");
        people.put(p.getId(), p);
        return p.getId();
    }

    public Person getPerson(String id) {
        Person p = people.get(id);
        if (p == null) throw new IllegalArgumentException("Unknown id: " + id);
        return p;
    }

    public void linkParentChild(String parentId, String childId) {
        Person parent = getPerson(parentId);
        Person child = getPerson(childId);
        if (child.getParents().contains(parent)) return; // already linked
        if (child.getParents().size() >= 2) throw new IllegalArgumentException("Child already has two parents");
        // Prevent cycles: parent cannot be descendant of child
        List<Person> childDescendants = traversal.descendants(child, Integer.MAX_VALUE);
        if (childDescendants.contains(parent)) {
            throw new IllegalArgumentException("Link would create cycle");
        }
        // link both sides
        child.addParentInternal(parent);
        parent.addChild(child);
    }

    public void marry(String aId, String bId, int year) {
        Person a = getPerson(aId);
        Person b = getPerson(bId);
        if (a.getSpouse() != null) throw new IllegalArgumentException(aId + " is already married");
        if (b.getSpouse() != null) throw new IllegalArgumentException(bId + " is already married");
        Marriage ma = new Marriage(b, year, null);
        Marriage mb = new Marriage(a, year, null);
        a.setMarriage(ma);
        b.setMarriage(mb);
    }

    public List<Person> ancestorsOf(String id, int generations) {
        Person p = getPerson(id);
        if (generations < 0) throw new IllegalArgumentException("generations >= 0 required");
        List<Person> raw = traversal.ancestors(p, generations);
        // raw includes nodes starting from parents; might include duplicates, preserve order.
        return raw;
    }

    public List<Person> descendantsOf(String id, int generations) {
        Person p = getPerson(id);
        if (generations < 0) throw new IllegalArgumentException("generations >= 0 required");
        List<Person> raw = traversal.descendants(p, generations);
        return raw;
    }

    public List<Person> siblingsOf(String id) {
        Person p = getPerson(id);
        Set<Person> sibs = new LinkedHashSet<>();
        for (Person parent : p.getParents()) {
            for (Person child : parent.getChildren()) {
                if (!child.equals(p)) sibs.add(child);
            }
        }
        return new ArrayList<>(sibs);
    }

    public List<Person> childrenOf(String id) {
        Person p = getPerson(id);
        return new ArrayList<>(p.getChildren());
    }

    public Marriage spouseOf(String id) {
        Person p = getPerson(id);
        return p.getSpouse();
    }

    public Collection<Person> allPeople() {
        return people.values();
    }
}
