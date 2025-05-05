
import java.util.Scanner;
public class App {

    enum Menu{
        ADD_CRAFT ("Add Craft"),
        ADD_CATEGORY("Add Category"),
        VIEW_CRAFTS ("View Crafts"),
        EDIT_CRAFT("Edit Craft"),
        DELETE_CRAFT("Delete Craft"),
        SEARCH_CRAFTS("Search Crafts"),
        EXIT("Exit");
        private String displayName;
        Menu(String displayName) {
            this.displayName = displayName;
        }
        @Override
        public String toString() {
            return displayName;
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        CraftManagement craftManagement = new CraftManagement();
        CategoryManager categoryManager = new CategoryManager();
        int choice = 0;
        int x = 1;
        do {
            System.out.println("Menu:");
            for (Menu menu : Menu.values()) {
                System.out.println(x++ + ". " + menu);
            }
            x= 1;
            choice = craftManagement.intValidator(scanner);
            switch (choice) {
                case 1 -> categoryManager.edit(craftManagement.getCrafts(), scanner);
                case 2 -> categoryManager.add(craftManagement.getCrafts(), scanner);
                case 3 -> craftManagement.viewCrafts();
                case 4 -> craftManagement.edit(categoryManager.getCategories(), scanner);
                case 5 -> craftManagement.delete(scanner);
                case 6 -> craftManagement.searchMenu(scanner);
                case 7 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 6);
    }
}
