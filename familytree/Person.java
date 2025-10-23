package familytree;

import java.util.*;

public abstract class Person implements FamilyComponent {
    private final String id;
    private String fullName;
    private final Gender gender;
    private final int birthYear;
    private Integer deathYear; // nullable
    private final List<Person> parents = new ArrayList<>(2);
    private final List<Person> children = new ArrayList<>();
    private Marriage spouse; // active marriage (null if none)

    protected Person(String id, String fullName, Gender gender, int birthYear, Integer deathYear) {
        validateName(fullName);
        validateYears(birthYear, deathYear);
        this.id = Objects.requireNonNull(id, "id required");
        this.fullName = fullName.trim();
        this.gender = Objects.requireNonNull(gender);
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be blank");
        }
    }

    private void validateYears(int birthYear, Integer deathYear) {
        if (deathYear != null && deathYear < birthYear) {
            throw new IllegalArgumentException("Death year cannot be before birth year");
        }
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public Gender getGender() { return gender; }
    public int getBirthYear() { return birthYear; }
    public Integer getDeathYear() { return deathYear; }
    public List<Person> getParents() { return Collections.unmodifiableList(parents); }
    public Marriage getSpouse() { return spouse; }

    public void setFullName(String fullName) {
        validateName(fullName);
        this.fullName = fullName.trim();
    }

    public void setDeathYear(Integer deathYear) {
        if (deathYear != null && deathYear < this.birthYear)
            throw new IllegalArgumentException("Death before birth");
        this.deathYear = deathYear;
    }

    public boolean isAlive() {
        return deathYear == null;
    }

    public int ageIn(int year) {
        if (year < birthYear)
            throw new IllegalArgumentException("Year earlier than birth year");
        int last = (deathYear == null) ? year : Math.min(year, deathYear);
        return last - birthYear;
    }

    // Parent management
    void addParentInternal(Person parent) {
        if (parents.size() >= 2)
            throw new IllegalArgumentException("Already has two parents");
        if (parents.contains(parent)) return;
        parents.add(parent);
    }

    void removeParentInternal(Person parent) {
        parents.remove(parent);
    }

    // Composite (children)
    @Override
    public void addChild(Person child) {
        if (!children.contains(child)) children.add(child);
    }

    @Override
    public void removeChild(Person child) {
        children.remove(child);
    }

    @Override
    public List<Person> getChildren() {
        return Collections.unmodifiableList(children);
    }

    // Spouse handling
    void setMarriage(Marriage marriage) {
        this.spouse = marriage;
    }

    void endMarriage() {
        this.spouse = null;
    }

    @Override
    public String toString() {
        String s = String.format("%s %s (b.%d", id, fullName, birthYear);
        if (deathYear != null) s += ", d." + deathYear;
        s += ")";
        return s;
    }
}
