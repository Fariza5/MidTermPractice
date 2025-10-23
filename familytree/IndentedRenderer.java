package familytree;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class IndentedRenderer implements Renderer {
    @Override
    public void render(Person p, int generations, TraversalStrategy traversal) {
        // We'll render descendant tree by default for readability. If callers want ancestors they can pass ancestors list.
        // Here use traversal.descendants with generation limit and then print recursively.
        printPerson(p, 0);
        printDescendants(p, 1, generations);
    }

    private void printPerson(Person p, int indent) {
        System.out.println("  ".repeat(indent) + "- " + p.getId() + " " + p.getFullName() + " (b." + p.getBirthYear() + ")");
    }

    private void printDescendants(Person p, int currentGen, int maxGen) {
        if (currentGen > maxGen) return;
        for (Person child : p.getChildren()) {
            printPerson(child, currentGen);
            printDescendants(child, currentGen + 1, maxGen);
        }
    }
}
