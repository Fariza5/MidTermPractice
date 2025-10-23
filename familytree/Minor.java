package familytree;

public class Minor extends Person {
    public Minor(String id, String fullName, Gender gender, int birthYear, Integer deathYear) {
        super(id, fullName, gender, birthYear, deathYear);
    }

    // Minor-specific behaviors (guardianship) could be added.
}
