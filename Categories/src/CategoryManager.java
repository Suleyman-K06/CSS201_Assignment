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
        String name = getNonEmptyInput(scanner, "Enter the name of new category:");

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
            EditChoice choice = selectEditChoice(scanner);
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

        if (confirmAction(scanner, "delete this category")) {
            categories.remove(selectedCategory);
            System.out.println("Category " + selectedCategory.getName() + " deleted successfully.");
        }
    }


    private Category selectCategory(Scanner scanner) {
        displayCategoriesList();
        int selectedIndex = getValidIndex(scanner, categories.size());
            
        return categories.get(selectedIndex);
    }

    private void displayCategoriesList() {
        System.out.println("Choose a category:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }
    }


    private EditChoice selectEditChoice(Scanner scanner) {
        System.out.println("\nWhat would you like to edit?");
        for (EditChoice choice : EditChoice.values()) {
            System.out.println(choice.getIndex() + ". " + choice.getLabel());
        }

        int input = getValidIndex(scanner, EditChoice.values().length) + 1;
        for (EditChoice choice : EditChoice.values()) {
            if (choice.getIndex() == input) {
                return choice;
            }
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
                if (confirmAction(scanner, "delete this category")) {
                    categories.remove(category);
                    System.out.println("Category " + category.getName() + " deleted!");
                }
                break;
            default:
                break;
        }
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
