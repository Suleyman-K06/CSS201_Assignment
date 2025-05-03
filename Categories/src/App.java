import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CategoryManager categoryManager = new CategoryManager();
        ArrayList<Craft> crafts = new ArrayList<>();

        // Initialize some sample crafts
        initializeCrafts(crafts);
        
        System.out.println("=== Category Management System ===");
        System.out.println("1. Add Category");
        System.out.println("2. Edit Category");
        System.out.println("3. Delete Category");
        System.out.println("4. Generate Report");
        System.out.println("5. Exit");
        
        while (true) {
            System.out.print("\nEnter your choice (1-5): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        System.out.println("\n=== Adding New Category ===");
                        categoryManager.add(crafts, scanner);
                        break;
                    case 2:
                        System.out.println("\n=== Editing Category ===");
                        categoryManager.edit(crafts, scanner);
                        break;
                    case 3:
                        System.out.println("\n=== Deleting Category ===");
                        categoryManager.delete(scanner);
                        break;
                    case 4:
                        System.out.println("\n=== Category Report ===");
                        categoryManager.generateCategoryReport();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static void initializeCrafts(ArrayList<Craft> crafts) {
        // Add some sample crafts
        crafts.add(new Craft("Wooden Carving", 5, 25.50, null, "Handcrafted wooden decoration"));
        crafts.add(new Craft("Clay Pot", 10, 15.75, null, "Handmade clay pot for plants"));
        crafts.add(new Craft("Beaded Necklace", 20, 8.99, null, "Colorful handmade beaded necklace"));
        crafts.add(new Craft("Glass Painting", 2, 45.00, null, "Beautiful painting on glass"));
        crafts.add(new Craft("Handwoven Basket", 15, 12.50, null, "Eco-friendly handwoven basket"));
        crafts.add(new Craft("Resin Keychain", 30, 5.99, null, "Personalized resin keychains"));
        crafts.add(new Craft("Embroidered Cushion", 8, 29.99, null, "Hand-stitched decorative cushion"));
        crafts.add(new Craft("Origami Paper Art", 50, 3.50, null, "Intricate origami paper sculpture"));
        crafts.add(new Craft("Ceramic Mug", 12, 18.75, null, "Hand-painted ceramic mug"));
        crafts.add(new Craft("Macrame Wall Hanging", 4, 55.00, null, "Elegant macrame wall decor"));
    }
}
