package familytree;

import java.time.Year;

public final class PersonFactory {
    private PersonFactory() {}

    public static Person createPerson(String fullName, Gender gender, int birthYear, Integer deathYear) {
        String id = IdGenerator.nextId();
        int now = Year.now().getValue();
        int age = now - birthYear;
        if (age >= 18) {
            return new Adult(id, fullName, gender, birthYear, deathYear);
        } else {
            return new Minor(id, fullName, gender, birthYear, deathYear);
        }
    }
}
