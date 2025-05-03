import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CraftManagement implements Manageable<Category> {
    private List<Craft> crafts = new ArrayList<>();

    // public void initializeCrafts() {
    //     crafts.add(new Craft("Wooden Carving", 5, 25.50, "Woodwork", "Handcrafted wooden decoration."));
    //     crafts.add(new Craft("Clay Pot", 10, 15.75, "Pottery", "Handmade clay pot for plants."));
    //     crafts.add(new Craft("Beaded Necklace", 20, 8.99, "Jewelry", "Colorful handmade beaded necklace."));
    //     crafts.add(new Craft("Glass Painting", 2, 45.00, "Painting", "Beautiful painting on glass."));
    //     crafts.add(new Craft("Handwoven Basket", 15, 12.50, "Weaving", "Eco-friendly handwoven basket."));
    //     crafts.add(new Craft("Resin Keychain", 30, 5.99, "Resin Art", "Personalized resin keychains."));
    //     crafts.add(new Craft("Embroidered Cushion", 8, 29.99, "Embroidery", "Hand-stitched decorative cushion."));
    //     crafts.add(new Craft("Origami Paper Art", 50, 3.50, "Paper Craft", "Intricate origami paper sculpture."));
    //     crafts.add(new Craft("Ceramic Mug", 12, 18.75, "Pottery", "Hand-painted ceramic mug."));
    //     crafts.add(new Craft("Macrame Wall Hanging", 4, 55.00, "Macrame", "Elegant macrame wall decor."));
    // }

    public void hr() {
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    public int intValidator(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number: ");
            }
        }
    }

    public double doubleValidator(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a decimal number: ");
            }
        }
    }

    @Override
    public void add(ArrayList<Category> categories, Scanner scanner) {
        System.out.println("Enter name:");
        String name = scanner.nextLine().trim();

        System.out.println("Enter quantity:");
        int quantity = intValidator(scanner);

        System.out.println("Enter price:");
        double price = doubleValidator(scanner);

        displayList(categories);

        System.out.println("Enter description:");
        String description = scanner.nextLine().trim();

        Craft craft = new Craft(name, quantity, price, category, description);
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
        System.out.printf("Enter search term for %s: ", field);
        String input = scanner.nextLine().toLowerCase();

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
                craft.getCategory(),
                craft.getDescription());
    }

    @Override
    public void delete(Scanner scanner) {
       
    }
}

