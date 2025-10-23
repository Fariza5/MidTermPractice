package familytree;

import java.util.List;

public interface FamilyComponent {
    void addChild(Person child);
    void removeChild(Person child);
    java.util.List<Person> getChildren();
}
