package familytree;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        FamilyTree tree = new FamilyTree(new DFSTraversal());
        Renderer indentedRenderer = new IndentedRenderer();
        Renderer lineRenderer = new LineRenderer();

        System.out.println("FamilyTree CLI — type HELP for commands");

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine();
            if (line == null) break;
            line = line.trim();
            if (line.isEmpty()) continue;
            if (line.equalsIgnoreCase("EXIT") || line.equalsIgnoreCase("QUIT")) break;
            if (line.equalsIgnoreCase("HELP")) {
                printHelp();
                continue;
            }

            try {
                String[] parts = tokenize(line);
                String cmd = parts[0].toUpperCase();
                switch (cmd) {
                    case "ADD_PERSON" -> {
                        // syntax: ADD_PERSON "Full Name" GENDER birthYear [deathYear]
                        String fullName = parts[1];
                        Gender g = Gender.valueOf(parts[2].toUpperCase());
                        int b = Integer.parseInt(parts[3]);
                        Integer d = parts.length > 4 ? Integer.parseInt(parts[4]) : null;
                        Person p = PersonFactory.createPerson(fullName, g, b, d);
                        String id = tree.addPerson(p);
                        System.out.println("-> " + id);
                    }
                    case "ADD_PARENT_CHILD" -> {
                        String parentId = parts[1];
                        String childId = parts[2];
                        tree.linkParentChild(parentId, childId);
                        System.out.println("OK");
                    }
                    case "MARRY" -> {
                        String a = parts[1], b = parts[2];
                        int year = Integer.parseInt(parts[3]);
                        tree.marry(a, b, year);
                        System.out.println("OK");
                    }
                    case "ANCESTORS" -> {
                        String id = parts[1];
                        int g = Integer.parseInt(parts[2]);
                        List<Person> anc = tree.ancestorsOf(id, g);
                        if (anc.isEmpty()) {
                            System.out.println("<none>");
                        } else {
                            // print parents first-level grouping:
                            // We'll print hierarchical by recursively expanding parents up to g
                            printAncestorsIndented(tree, id, g);
                        }
                    }
                    case "DESCENDANTS" -> {
                        String id = parts[1];
                        int g = Integer.parseInt(parts[2]);
                        System.out.println(tree.getPerson(id).getId() + " " + tree.getPerson(id).getFullName());
                        printDescendantsIndented(tree, id, 1, g);
                    }
                    case "SIBLINGS" -> {
                        String id = parts[1];
                        List<Person> s = tree.siblingsOf(id);
                        if (s.isEmpty()) System.out.println("<none>");
                        else s.forEach(p -> System.out.println(p));
                    }
                    case "SHOW" -> {
                        String id = parts[1];
                        Person p = tree.getPerson(id);
                        lineRenderer.render(p, 0, null);
                    }
                    default -> System.out.println("Unknown command. Type HELP.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("ERROR: " + ex.getMessage());
                ex.printStackTrace(System.out);
            }
        }

        System.out.println("Bye.");
    }

    private static void printHelp() {
        System.out.println("""
                Commands:
                ADD_PERSON "<Full Name>" <Gender> <BirthYear> [DeathYear]
                ADD_PARENT_CHILD <parentId> <childId>
                MARRY <personAId> <personBId> <Year>
                ANCESTORS <personId> <generations>
                DESCENDANTS <personId> <generations>
                SIBLINGS <personId>
                SHOW <personId>
                HELP
                EXIT
                """);
    }

    // Basic tokenizer that supports quoted full name as first arg after command.
    private static String[] tokenize(String line) {
        List<String> out = new ArrayList<>();
        String trimmed = line.trim();
        String[] firstSplit = trimmed.split("\\s+", 2);
        String cmd = firstSplit[0];
        out.add(cmd);
        if (firstSplit.length == 1) return out.toArray(new String[0]);
        String rest = firstSplit[1].trim();
        if (rest.startsWith("\"")) {
            int end = rest.indexOf('"', 1);
            while (end > 0 && rest.charAt(end - 1) == '\\') { // escaped quote
                end = rest.indexOf('"', end + 1);
            }
            if (end < 0) throw new IllegalArgumentException("Unterminated quote for name");
            String name = rest.substring(1, end).replace("\\\"", "\"");
            out.add(name);
            String after = rest.substring(end + 1).trim();
            if (!after.isEmpty()) {
                String[] parts = after.split("\\s+");
                out.addAll(Arrays.asList(parts));
            }
        } else {
            String[] parts = rest.split("\\s+");
            out.addAll(Arrays.asList(parts));
        }
        return out.toArray(new String[0]);
    }

    private static void printDescendantsIndented(FamilyTree tree, String id, int currentGen, int maxGen) {
        if (currentGen > maxGen) return;
        Person p = tree.getPerson(id);
        for (Person child : p.getChildren()) {
            System.out.println("  ".repeat(currentGen) + "- " + child.getId() + " " + child.getFullName() + " (b." + child.getBirthYear() + ")");
            printDescendantsIndented(tree, child.getId(), currentGen + 1, maxGen);
        }
    }

    private static void printAncestorsIndented(FamilyTree tree, String id, int maxGen) {
        Person p = tree.getPerson(id);
        System.out.println("- " + p.getId() + " " + p.getFullName() + " (b." + p.getBirthYear() + ")");
        printParentsIndented(tree, p, 1, maxGen);
    }

    private static void printParentsIndented(FamilyTree tree, Person p, int currentGen, int maxGen) {
        if (currentGen > maxGen) return;
        for (Person parent : p.getParents()) {
            System.out.println("  ".repeat(currentGen) + "- " + parent.getId() + " " + parent.getFullName() + " (b." + parent.getBirthYear() + ")");
            printParentsIndented(tree, parent, currentGen + 1, maxGen);
        }
    }
}
