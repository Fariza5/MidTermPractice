package familytree;

public class LineRenderer implements Renderer {
    @Override
    public void render(Person p, int generations, TraversalStrategy traversal) {
        System.out.printf("%s | %s | %s | b.%d | children=%d | parents=%d%n",
                p.getId(),
                p.getFullName(),
                p.getGender(),
                p.getBirthYear(),
                p.getChildren().size(),
                p.getParents().size()
        );
        if (p.getSpouse() != null) {
            System.out.printf("spouse=%s(m.%d)%n", p.getSpouse().getPartner().getId(), p.getSpouse().getYearMarried());
        }
    }
}
