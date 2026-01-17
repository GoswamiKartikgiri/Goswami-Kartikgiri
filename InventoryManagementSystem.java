import java.io.*;
import java.util.*;

class Item {
    private String name;
    private double price;
    private double quantity;

    public Item(String name, double price, double quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item: " + name + ", Price: Rs." + price + ", Quantity: " + quantity;
    }
}

public class InventoryManagementSystem {
    private static final String FILENAME = "inventory.txt";
    private List<Item> inventory;

    public InventoryManagementSystem() {
        this.inventory = new ArrayList<>();
        loadInventory();
    }

    public void addNewItem(Item item) {
        inventory.add(item);
        saveInventory();
        System.out.println("Item added successfully.");
    }

    public void removeItem(String name) {
        Iterator<Item> iterator = inventory.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                saveInventory();
                System.out.println("Item removed successfully.");
                return;
            }
        }
        System.out.println("Item not found in inventory.");
    }

    public void updateItemPrice(String name, double newPrice) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                item.setPrice(newPrice);
                saveInventory();
                System.out.println("Price updated successfully.");
                return;
            }
        }
        System.out.println("Item not found in inventory.");
    }

    public void updateItemQuantity(String name, double newQuantity) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                item.setQuantity(newQuantity);
                saveInventory();
                System.out.println("Quantity updated successfully.");
                return;
            }
        }
        System.out.println("Item not found in inventory.");
    }

    public void searchItem(String name) {
        boolean found = false;
        for (Item item : inventory) {
            if (item.getName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(item);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Item not found in inventory.");
        }
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Item item : inventory) {
            System.out.println(item);
        }
    }

    private void saveInventory() {
        try (PrintWriter writer = new PrintWriter(FILENAME)) {
            for (Item item : inventory) {
                writer.println(item.getName() + "," + item.getPrice() + "," + item.getQuantity());
            }
        } catch (IOException e) {
            System.err.println("Error saving inventory: " + e.getMessage());
        }
    }

    private void loadInventory() {
        File file = new File(FILENAME);
        if (!file.exists()) {
            System.out.println("No previous inventory found.");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String name = parts[0];
                double price = Double.parseDouble(parts[1]);
                double quantity = Double.parseDouble(parts[2]);
                inventory.add(new Item(name, price, quantity));
            }
        } catch (IOException e) {
            System.err.println("Error loading inventory: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        InventoryManagementSystem ims = new InventoryManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            
            System.out.println("\n**INVENTORY MANAGEMENT SYSTEM**");
            System.out.println("===================================\n");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Update Item Price");
            System.out.println("4. Update Item Quantity");
            System.out.println("5. Search Item");
            System.out.println("6. Display Inventory");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter item name: ");
                    String name = "";
                    while (true) {
                        name = scanner.nextLine();
                        if (!name.matches(".*\\d.*")) { // Check if name contains any digit
                            break;
                        } else {
                            System.out.print("Invalid input. Please enter a valid item name:");
                        }
                    }
                    double price = 0;
                    while (true) {
                        try {
                            System.out.print("Enter item price: ");
                            price = scanner.nextDouble();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.print("Invalid input. Please enter a valid number.");
                            scanner.next(); // Clear the input buffer
                        }
                    }

                    double quantity = 0;
                    while (true) {
                        try {
                            System.out.print("Enter item quantity: ");
                            quantity = scanner.nextDouble();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.print("Invalid input. Please enter a valid number.");
                            scanner.next(); // Clear the input buffer
                        }
                    }

                    ims.addNewItem(new Item(name, price, quantity));
                    break;
                case 2:
                    System.out.print("Enter item name to remove: ");                    
                    String itemToRemove = "";
                    while (true) {
                        itemToRemove = scanner.nextLine();
                        if (!itemToRemove.matches(".*\\d.*")) { // Check if name contains any digit
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter a valid item name:");
                        }
                    }
                    ims.removeItem(itemToRemove);
                    break;
                case 3:
                    // System.out.print("Enter item name to update price: ");
                    // String itemNamePrice = scanner.nextLine();
                    
                    System.out.print("Enter item name to update price: ");                    
                    String itemNamePrice = "";
                    while (true) {
                        itemNamePrice = scanner.nextLine();
                        if (!itemNamePrice.matches(".*\\d.*")) { // Check if name contains any digit
                            break;
                        } else {
                            System.out.print("Invalid input. Please enter a valid item name:");
                        }
                    }

                    
                    System.out.print("Enter new price: ");
                    double newPrice = 0;
                    while (true) {
                        try {
                            newPrice = scanner.nextDouble();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number: ");
                            scanner.next(); // Clear the input buffer
                        }
                    }
                    ims.updateItemPrice(itemNamePrice, newPrice);
                    break;
                case 4:
                    // String itemNameQuantity = scanner.nextLine();

                    System.out.print("Enter item name to update quantity: ");
                    String itemNameQuantity = "";
                    while (true) {
                        itemNameQuantity = scanner.nextLine();
                        if (!itemNameQuantity.matches(".*\\d.*")) { // Check if name contains any digit
                            break;
                        } else {
                            System.out.print("Invalid input. Please enter a valid item name: ");
                        }
                    }
                    
                    System.out.print("Enter new quantity: ");
                    double newQuantity = 0;
                    while (true) {
                        try {
                            newQuantity = scanner.nextDouble();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.next(); // Clear the input buffer
                        }
                    }
                    ims.updateItemQuantity(itemNameQuantity, newQuantity);
                    break;
                case 5:
                    // String searchItemName = scanner.nextLine();
                    System.out.print("Enter item name to search: ");
                    String searchItemName = "";
                    while (true) {
                        searchItemName = scanner.nextLine();
                        if (!searchItemName.matches(".*\\d.*")) { // Check if name contains any digit
                            break;
                        } else {
                            System.out.print("Invalid input. Please enter a valid item name: ");
                        }
                    }
                    
                    ims.searchItem(searchItemName);
                    break;
                case 6:
                    ims.displayInventory();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }
}
