package familytree;

public final class Marriage {
    private final Person partner;
    private final int yearMarried;
    private final Integer yearDivorced; // nullable

    public Marriage(Person partner, int yearMarried, Integer yearDivorced) {
        this.partner = partner;
        this.yearMarried = yearMarried;
        this.yearDivorced = yearDivorced;
    }

    public Person getPartner() { return partner; }
    public int getYearMarried() { return yearMarried; }
    public Integer getYearDivorced() { return yearDivorced; }
}
