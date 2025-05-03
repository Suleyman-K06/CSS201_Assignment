import java.util.ArrayList;
import java.util.Scanner;

public interface Manageable<T extends Nameable> {
    public void add(ArrayList<T> firstList, Scanner scanner);

    public void edit(ArrayList<T> firstList, Scanner scanner);
    
    public void delete(Scanner scanner);

    default ArrayList<T> selectFromList(ArrayList<T> list, Scanner scanner, String action) {
        ArrayList<T> selectedList = new ArrayList<>();
    
        if (list.isEmpty()) {
            System.out.println("No crafts available to assign.");
            return selectedList;
        }
    
        displayList(list);
        System.out.println("Enter index numbers of crafts to assign to " + action + "\" (e.g. 1, 3, 5):");
        String input = scanner.nextLine().trim();
        String[] indexStrings = input.split(",");
    
        displayList(list);
        for (String indexString : indexStrings) {
            try {
                int index = Integer.parseInt(indexString.trim()) - 1;
                if (index >= 0 && index < list.size()) {
                    selectedList.add(list.get(index));
                } else {
                    System.out.println("Index out of bounds: " + (index + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + indexString + " is not a number.");
            }
        }
        
        return selectedList;
    }

    default void displayList(ArrayList<T> list) {
        if (list.isEmpty()) {
            System.out.println("No items available.");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getName());
        }
    }

    default boolean confirmAction(Scanner scanner, String action) {
        String input = "";
        while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no")) {
            System.out.println("Are you sure you want to " + action + "? (Enter Yes / No)");
            input = scanner.nextLine().trim();
        }
        return input.equalsIgnoreCase("yes");
    }

    default int getValidIndex(Scanner scanner, int maxIndex) {
        while (true) {
            try {
                System.out.print("Enter your choice (1-" + maxIndex + "): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Input cannot be empty. Please try again.");
                    continue;
                }
                int index = Integer.parseInt(input) - 1; // Convert to zero-based index
                if (index >= 0 && index < maxIndex) {
                    return index; // Valid index
                }
                System.out.println("Invalid index. Please enter a number between 1 and " + maxIndex + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    default String getNonEmptyInput(Scanner scanner, String prompt) {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // Clear the buffer
        }
        String input;
        do {
            System.out.println(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty.");
            }
        } while (input.isEmpty());
        return input;
    }
}