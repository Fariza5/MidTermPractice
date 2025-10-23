package familytree;

import java.util.List;

public interface TraversalStrategy {
    // return list of Persons limited by generations (0=self, 1=parents, ...)
    List<Person> ancestors(Person p, int generations);
    List<Person> descendants(Person p, int generations);
}
