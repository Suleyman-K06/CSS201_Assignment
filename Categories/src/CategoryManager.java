import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CategoryManager implements Manageable<Craft> {
    private ArrayList<Category> categories;

    public CategoryManager() {
        this.categories = new ArrayList<>();
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    enum EditChoice {
        NAME("Edit Name", 1),
        ADD("Add Crafts", 2), 
        DELETE("Delete Category", 3),
        REMOVE("Remove Crafts From Category", 4),
        EXIT("Exit Editing", 5);

        private final String label;
        public String getLabel() {
            return label;
        }

        private final int index;
        public int getIndex() {
            return index;
        }

        EditChoice(String label, int index) {
            this.label = label;
            this.index = index;
        }
    }

    @Override
    public void add(ArrayList<Craft> crafts, Scanner scanner) {
        System.out.println("Enter the name of new category: ");
        scanner.skip("\n"); 

        String name = getValidName(scanner);
        if (name == null) {
            System.out.println("Category name cannot be empty.");
            return;
        }


        ArrayList<Craft> selectedCrafts = selectFromList(crafts, scanner, "add");
        Category newCategory = new Category(name, selectedCrafts);
        categories.add(newCategory);
        System.out.println("Category " + name + " created with " + selectedCrafts.size() + " crafts.");
    }

    @Override
    public void edit(ArrayList<Craft> crafts, Scanner scanner) {        
        if (categories.isEmpty()) {
            System.out.println("No Categories!");
            return;
        }

        Category selectedCategory = selectCategory(scanner);
        if (selectedCategory == null) return;

        while (true) {
            displayEditMenu();
            EditChoice choice = getEditChoice(scanner);
            if (choice == null) continue;
            
            if (choice == EditChoice.EXIT) return;
            
            handleEditChoice(choice, selectedCategory, crafts, scanner);
        }
    }

    @Override
    public void delete(Scanner scanner) {
        if (categories.isEmpty()) {
            System.out.println("No categories available to delete.");
            return;
        }

        Category selectedCategory = selectCategory(scanner);
        if (selectedCategory == null) return;

        if (confirmDeletion(scanner, selectedCategory)) {
            categories.remove(selectedCategory);
            System.out.println("Category " + selectedCategory.getName() + " deleted successfully.");
        }
    }


    private String getValidName(Scanner scanner) {
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Category name cannot be empty.");
            return null;
        }
        return name;
    }

    private Category selectCategory(Scanner scanner) {
        displayCategoriesList();
        try {
            int selectedIndex = scanner.nextInt();
            scanner.nextLine();
            
            if (selectedIndex <= 0 || selectedIndex > categories.size()) {
                System.out.println("Invalid category selection.");
                return null;
            }
            
            return categories.get(selectedIndex - 1);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Consume invalid input
            return null;
        }
    }

    private void displayCategoriesList() {
        System.out.println("Choose a category:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }
    }

    private void displayEditMenu() {
        System.out.println("\nWhat would you like to edit?");
        for (EditChoice choice : EditChoice.values()) {
            System.out.println(choice.getIndex() + ". " + choice.getLabel());
        }
    }

    private EditChoice getEditChoice(Scanner scanner) {
        try {
            int input = Integer.parseInt(scanner.nextLine().trim());
            for (EditChoice choice : EditChoice.values()) {
                if (choice.getIndex() == input) {
                    return choice;
                }
            }
            System.out.println("Invalid choice! Please try again.");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number!");
        }
        return null;
    }

    private void handleEditChoice(EditChoice choice, 
                                  Category category, 
                                  ArrayList<Craft> crafts, 
                                  Scanner scanner) {
        switch (choice) {
            case NAME:
                System.out.println("Enter new name for the category (leave blank to keep current name): ");
                String newName = scanner.nextLine().trim();
                if (!newName.isEmpty()) {
                    category.setName(newName);
                }
                break;
            case ADD:
                ArrayList<Craft> selectedCrafts = selectFromList(crafts, scanner, "add");
                category.addCrafts(selectedCrafts);
                break;
            case REMOVE:
                removeFrom(category, scanner);
                break;
            case DELETE:
                if (confirmDeletion(scanner, category)) {
                    categories.remove(category);
                    System.out.println("Category " + category.getName() + " deleted!");
                }
                break;
            default:
                break;
        }
    }

    private boolean confirmDeletion(Scanner scanner, Category category) {
        String input = "";
        while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no")) {
            System.out.println("Are you sure you want to delete this category? (Enter Yes / No)");
            input = scanner.nextLine().trim();
        }
        return input.equalsIgnoreCase("yes");
    }

    public void removeFrom(Category category, Scanner scanner) {
        ArrayList<Craft> categoryCrafts = category.getCrafts();
        if (categoryCrafts.isEmpty()) {
            System.out.println("No crafts in this category to remove.");
            return;
        }

        ArrayList<Craft> selectedCrafts = selectFromList(categoryCrafts, scanner, "remove");
        if (selectedCrafts.isEmpty()) {
            System.out.println("No crafts selected for removal.");
            return;
        }

        category.getCrafts().removeAll(selectedCrafts);
        System.out.println("Selected crafts removed from category.");
    }

    public void generateCategoryReport() {
        if (categories.isEmpty()) {
            System.out.println("No Categories Available!");
            return;
        }

        System.out.println("\n=== Category Report ===");
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            System.out.println("\n" + (i + 1) + ". " + category.getName());
            System.out.println("   --------------------");

            ArrayList<Craft> crafts = category.getCrafts();
            if (crafts.isEmpty()) {
                System.out.println("   No crafts in this category.");
            } else {
                for (int j = 0; j < crafts.size(); j++) {
                    Craft craft = crafts.get(j);
                    System.out.println("   " + (j + 1) + ") " + craft.getName());
                }
            }
        }
    }
}
