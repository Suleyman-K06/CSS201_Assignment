import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CraftManagement implements Manageable<Category> {
    private ArrayList<Craft> crafts = new ArrayList<>();

    public void hr() {
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    public int intValidator(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number: ");
                scanner.nextLine();
            }
        }
    }

    public double doubleValidator(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a decimal number: ");
                scanner.nextLine();
            }
        }
    }

    @Override
    public void add(ArrayList<Category> categories, Scanner scanner) {
        String name = getNonEmptyInput(scanner, "Enter craft name: ");

        System.out.println("Enter quantity:");
        int quantity = intValidator(scanner);

        System.out.println("Enter price:");
        double price = doubleValidator(scanner);

        int categoryIndex = -1;
        if (!categories.isEmpty()) {
            displayList(categories);
            System.out.println("Select a category by index ");
            
            categoryIndex = getValidIndex(scanner, categories.size());
        }

        String description = getNonEmptyInput(scanner, "Enter description: ");

        Craft craft = new Craft(name, quantity, price, description);
        if (categoryIndex > -1) craft.setCategory(categories.get(categoryIndex));
        crafts.add(craft);
        System.out.println("Craft added successfully!");
    }



    public boolean viewCrafts() {
        if (crafts.isEmpty()) {
            System.out.println("No crafts available.");
            return false;
        }
        hr();
        System.out.printf("|%-5s|%-20s|%-5s|%-10s|%-15s|%-30s|%n", "ID", "Name", "Qty", "Price", "Category", "Description");
        hr();
        for (int i = 0; i < crafts.size(); i++) {
            printCraft(crafts.get(i), i);
        }
        hr();
        return true;
    }


    @Override
    public void edit(ArrayList<Category> categories, Scanner scanner) {
        int index = getValidIndex(scanner, crafts.size());
        Craft craft = crafts.get(index);

        System.out.printf("Current name (%s): ", craft.getName());
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) craft.setName(name);

        System.out.printf("Current quantity (%d): ", craft.getQuantity());
        int qty = intValidator(scanner);
        if (qty > 0) craft.setQuantity(qty);

        System.out.printf("Current price (%.2f): ", craft.getPrice());
        double price = doubleValidator(scanner);
        if (price > 0) craft.setPrice(price);

        System.out.printf("Current category (%s): ", craft.getCategory());


        System.out.printf("Current description (%s): ", craft.getDescription());
        String desc = scanner.nextLine().trim();
        if (!desc.isEmpty()) craft.setDescription(desc);

        System.out.println("Craft updated successfully.");
    }

    enum SearchField {
        NAME("name", 1), 
        QUANTITY("quantity", 2), 
        PRICE("price", 3), 
        DESCRIPTION("description", 4);

        private final String label;
        private final int index;
        SearchField(String label, int index) {
            this.label = label;
            this.index = index;
        }

        public String getLabel() {
            return label;
        }

        public int getIndex() {
            return index;
        }
    }

    public void searchMenu(Scanner scanner) {
        for (SearchField field : SearchField.values()) {
            System.out.println(field.getIndex() + ". Search by " + field.getLabel());
        }

        int choice = intValidator(scanner);
        SearchField searchField = SearchField.values()[choice];

        switch (choice) {
            case 1 -> searchByField(scanner, searchField);
            case 2 -> searchByField(scanner, searchField);
            case 3 -> searchByField(scanner, searchField);
            case 4 -> searchByField(scanner, searchField);
            default -> System.out.println("Invalid option.");
        }
    }

    public void searchByField(Scanner scanner, SearchField field) {
        boolean found = false;

        String input = getNonEmptyInput(scanner, ("Enter search term for %s: " + field)).toLowerCase();

        hr();
        System.out.printf("|%-5s|%-20s|%-5s|%-10s|%-15s|%-30s|%n", "ID", "Name", "Qty", "Price", "Category", "Description");
        hr();
        for (int i = 0; i < crafts.size(); i++) {
            Craft craft = crafts.get(i);
            boolean match = switch (field) {
                case NAME -> craft.getName().toLowerCase().contains(input);
                case QUANTITY -> String.valueOf(craft.getQuantity()).equals(input);
                case PRICE -> String.valueOf(craft.getPrice()).equals(input);
                case DESCRIPTION -> craft.getDescription().toLowerCase().contains(input);
                default -> false;
            };
            if (match) {
                printCraft(craft, i);
                found = true;
            }
        }
        if (!found) System.out.println("No matching crafts found.");
        hr();
    }

    public void printCraft(Craft craft, int index) {
        System.out.printf("|%-5d|%-20.20s|%-5d|$%-9.2f|%-15.15s|%-30.30s|%n",
                index + 1,
                craft.getName(),
                craft.getQuantity(),
                craft.getPrice(),
                new Category("null", crafts),
                craft.getDescription());
    }

    @Override
    public void delete(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // Clear the buffer
        }
        ArrayList<Craft> selectedList = new ArrayList<>();
    
        if (crafts.isEmpty()) {
            System.out.println("No crafts available to assign.");
            return;
        }
    
        viewCrafts();
        System.out.println("Enter index numbers of crafts to assign to " + "delete" + "\" (e.g. 1, 3, 5):");
        String input = scanner.nextLine().trim();
        String[] indexStrings = input.split(",");
    

        for (String indexString : indexStrings) {
            try {
                int index = Integer.parseInt(indexString.trim()) - 1;
                if (index >= 0 && index < crafts.size()) {
                    selectedList.add(crafts.get(index));
                } else {
                    System.out.println("Index out of bounds: " + (index + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + indexString + " is not a number.");
            }
        }
        
        crafts.removeAll(selectedList);
    }

    public CraftManagement() {
        this.crafts = new ArrayList<>();
    }

    public ArrayList<Craft> getCrafts() {
        return crafts;
    }
}

