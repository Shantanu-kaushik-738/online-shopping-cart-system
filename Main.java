import java.io.*;
import java.util.*;

public class Main {

    static HashMap<String, Product> products = new LinkedHashMap<String, Product>();

    static Cart cart = new Cart();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadProducts();
        loadCart();

        int choice;
        do {
            showMenu();
            choice = readNumber("Enter your choice: ");

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    addToCart();
                    break;
                case 3:
                    viewCart();
                    break;
                case 4:
                    removeLastAddedItem();
                    break;
                case 5:
                    sortCart();
                    break;
                case 6:
                    checkout();
                    break;
                case 7:
                    saveCart();
                    System.out.println("Cart saved. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Enter a number from 1 to 7.");
            }
        } while (choice != 7);
    }

    static void showMenu() {
        System.out.println("\n========== ONLINE SHOPPING CART ==========");
        System.out.println("1. View products");
        System.out.println("2. Add to cart");
        System.out.println("3. View cart");
        System.out.println("4. Remove last added item");
        System.out.println("5. Sort cart (high to low)");
        System.out.println("6. Checkout");
        System.out.println("7. Save and exit");
    }

    // Reads one line; if the input ends (Ctrl+D / Ctrl+Z), save and exit safely
    static String readLine() {
        if (!sc.hasNextLine()) {
            saveCart();
            System.out.println("\nInput ended. Cart saved. Goodbye!");
            System.exit(0);
        }
        return sc.nextLine();
    }

    static int readNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(readLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    static void loadProducts() {
        try (BufferedReader br = new BufferedReader(new FileReader("products.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0 || line.startsWith("#")) {
                    continue;
                }

                String[] data = line.split("\\|");
                if (data.length != 3) {
                    System.out.println("Skipped invalid product line: " + line);
                    continue;
                }
                try {
                    String id = data[0].trim().toUpperCase();
                    String name = data[1].trim();
                    double price = Double.parseDouble(data[2].trim());
                    if (id.isEmpty() || name.isEmpty() || price <= 0) {
                        System.out.println("Skipped invalid product line: " + line);
                        continue;
                    }
                    products.put(id, new Product(id, name, price));
                } catch (NumberFormatException e) {
                    System.out.println("Skipped product with invalid price: " + line);
                }
            }
            System.out.println(products.size() + " products loaded.");
        } catch (IOException e) {
            System.out.println("Could not read products.txt. Keep it in the project folder.");
        }
    }

    static void viewProducts() {
        System.out.println("\n----- AVAILABLE PRODUCTS -----");
        for (Product p : products.values()) {
            System.out.println(p.id + " | " + p.name + " | Price: " + money(p.price));
        }
    }

    static void addToCart() {
        System.out.print("Product ID: ");
        String id = readLine().trim().toUpperCase();

        Product product = products.get(id);
        if (product == null) {
            System.out.println("Product " + id + " not found.");
            return;
        }

        int quantity = readNumber("Quantity: ");
        if (quantity <= 0) {
            System.out.println("Quantity must be a positive whole number.");
            return;
        }

        cart.add(product, quantity);
        System.out.println("Added: " + product.name + " x" + quantity + " = " + money(product.price * quantity));
    }

    static void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            System.out.println("Total: 0");
            return;
        }

        System.out.println("\n----- YOUR CART -----");
        CartNode current = cart.head;
        int count = 1;
        while (current != null) {
            System.out.println(count + ". " + current.product.name + " | " + money(current.getItemTotal()));
            count++;
            current = current.next;
        }
        System.out.println("Total: " + money(cart.getTotal()));
    }

    static void removeLastAddedItem() {
        CartNode removed = cart.removeLastAdded();
        if (removed == null) {
            System.out.println("Your cart is empty. Nothing to remove.");
        } else {
            System.out.println("Removed: " + removed.product.name + " x" + removed.quantity);
        }
    }

    static void sortCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Nothing to sort.");
            return;
        }
        cart.sortHighToLow();
        System.out.println("Cart sorted from high to low.");
        viewCart();
    }

    static void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Add products before checkout.");
            return;
        }

        System.out.println("Order placed. Total: " + money(cart.getTotal()));
        cart.clear();
        saveCart();
    }

    static void loadCart() {
        try (BufferedReader br = new BufferedReader(new FileReader("cart.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.trim().split("\\|");
                if (data.length != 2) {
                    continue;
                }
                try {
                    Product p = products.get(data[0].trim().toUpperCase());
                    int quantity = Integer.parseInt(data[1].trim());
                    if (p != null && quantity > 0) {
                        cart.add(p, quantity);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("A saved cart line was invalid and was skipped.");
                }
            }
        } catch (IOException e) {
            // No saved cart yet (first run): start with an empty cart
        }
    }

    static void saveCart() {
        // try-with-resources closes (and flushes) the file automatically
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("cart.txt"))) {
            CartNode current = cart.head;

            while (current != null) {
                bw.write(current.product.id + "|" + current.quantity);
                bw.newLine();
                current = current.next;
            }
        } catch (IOException e) {
            System.out.println("Could not save cart data.");
        }
    }

    static String money(double amount) {
        if (amount == (long) amount) {
            return String.valueOf((long) amount);
        }
        return String.format("%.2f", amount);
    }
}

class Product {
    String id;
    String name;
    double price;

    Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

class CartNode {
    Product product;
    int quantity;
    CartNode next;

    CartNode(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.next = null;
    }

    double getItemTotal() {
        return product.price * quantity;
    }
}

class Cart {
    CartNode head;
    CartNode tail;

    Stack<CartNode> addedItems = new Stack<CartNode>();

    boolean isEmpty() {
        return head == null;
    }

    void add(Product product, int quantity) {
        CartNode newNode = new CartNode(product, quantity);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        addedItems.push(newNode);
    }

    CartNode removeLastAdded() {
        if (addedItems.empty()) {
            return null;
        }

        CartNode target = addedItems.pop();
        CartNode current = head;
        CartNode previous = null;

        while (current != null && current != target) {
            previous = current;
            current = current.next;
        }

        if (current == null) {
            return null;
        }

        if (previous == null) {
            head = current.next;
        } else {
            previous.next = current.next;
        }

        if (current == tail) {
            tail = previous;
        }

        current.next = null;
        return current;
    }

    double getTotal() {
        double total = 0;
        CartNode current = head;

        while (current != null) {
            total = total + current.getItemTotal();
            current = current.next;
        }
        return total;
    }

    void sortHighToLow() {
        int size = 0;
        CartNode current = head;
        while (current != null) {
            size++;
            current = current.next;
        }
        if (size == 0) {
            return;
        }

        CartNode[] nodes = new CartNode[size];
        current = head;
        for (int i = 0; i < size; i++) {
            nodes[i] = current;
            current = current.next;
        }

        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (nodes[j].getItemTotal() < nodes[j + 1].getItemTotal()) {
                    CartNode temp = nodes[j];
                    nodes[j] = nodes[j + 1];
                    nodes[j + 1] = temp;
                }
            }
        }

        head = nodes[0];
        for (int i = 0; i < size - 1; i++) {
            nodes[i].next = nodes[i + 1];
        }
        tail = nodes[size - 1];
        tail.next = null;
    }

    void clear() {
        head = null;
        tail = null;
        addedItems.clear();
    }
}
