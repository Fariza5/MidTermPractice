package familytree;

public class Adult extends Person {
    public Adult(String id, String fullName, Gender gender, int birthYear, Integer deathYear) {
        super(id, fullName, gender, birthYear, deathYear);
    }

    // Adult-specific behaviors could be added.
}
