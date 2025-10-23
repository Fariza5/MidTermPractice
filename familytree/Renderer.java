package familytree;

public interface Renderer {
    void render(Person p, int generations, TraversalStrategy traversal);
}
