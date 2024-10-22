package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {

    public static void main(String[] args) {
        // Initialize variables
        ArrayList<Product> inventory = new ArrayList<Product>();
        ArrayList<Product> cart = new ArrayList<Product>();
        double totalAmount = 0.0;

        // Load inventory from CSV file
        loadInventory("products.csv", inventory);

        // Create scanner to read user input
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        // Display menu and get user choice until they choose to exit
        while (choice != 3) {
            System.out.println("Welcome to the Online com.pluralsight.Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");

            choice = scanner.nextInt();
            scanner.nextLine();

            // Call the appropriate method based on user choice
            switch (choice) {
                case 1:
                    displayProducts(inventory, cart, scanner);
                    break;
                case 2:
                    displayCart(cart, scanner, totalAmount);
                    break;
                case 3:
                    System.out.println("Thank you for shopping with us!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        String line;
        try {
            BufferedReader br = new BufferedReader( new FileReader(fileName));
            while (( line = br.readLine()) != null){
                String [] parts = line.split("\\|");
                String id = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                inventory.add(new Product(id, name, price));
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {
        // This method should display a list of products from the inventory,
        System.out.println("Available products:");
        for (Product product : inventory) {
            System.out.println(product.getId() + " | " + product.getName() + " | " + product.getPrice());
        }

        // and prompt the user to add items to their cart.
        System.out.println("Enter the product ID to add it to your cart : ");
        String inputId = scanner.nextLine();
        Product product = findProductById(inputId,inventory);

        // The method should prompt the user to enter the ID of the product they want to add to their cart.
        if (product != null){
            cart.add(product);
            System.out.println(product.getName() + " added to your shopping cart.");
        }else{
            System.out.println("Sorry, product not found. ");
        }
    }

    public static void displayCart(ArrayList<Product> cart, Scanner scanner, double totalAmount) {
        //handling cases for empty cart
        if (cart.isEmpty()){
            System.out.println("Oops, your cart is empty. ");
            return;
        }
        // sum of items
        System.out.println("Your Cart: ");
        totalAmount = 0; //resets
        for (Product product : cart) {
            System.out.println(product.getId() + " | " + product.getName() + " | $" + product.getPrice());
            totalAmount += product.getPrice();
        }
        System.out.printf("Total Amount of Products: $%.2f%n", totalAmount);
        //removing items
        System.out.println("Would you like to remove any item from your cart? YES OR NO ");
        String answer = scanner.nextLine().trim();
        if (answer.equalsIgnoreCase("yes")){
        System.out.println("Please enter product ID to remove it from the cart: ");
        String productId = scanner.nextLine().trim();
        Product removeProduct = findProductById(productId, cart); //helper looking productId in cart

            if (removeProduct != null) {
                cart.remove(removeProduct);
                totalAmount -= removeProduct.getPrice(); // Update total amount
                System.out.println(removeProduct.getName() + " has been removed from your cart.");
                System.out.printf("Total Amount of Products: $%.2f%n", totalAmount);
            } else {
                System.out.println("Product not found in cart.");
            }
        }
    }

    public static void checkOut(ArrayList<Product> cart, double totalAmount) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("Check Out Summary: ");
        totalAmount = 0; //resets
        for (Product product : cart) {
            System.out.println(product.getId() + " | " + product.getName() + " | $" + product.getPrice());
            totalAmount += product.getPrice();
        }
        System.out.printf("Total Amount: $%.2f%n", totalAmount);

        // This method should calculate the total cost of all items in the cart,
        // and display a summary of the purchase to the user. The method should
        // prompt the user to confirm the purchase, and deduct the total cost
        // from their account if they confirm.
    }

    public static Product findProductById(String id, ArrayList<Product> inventory) {

        for (Product product : inventory) {
            if (product.getId().equalsIgnoreCase(id.trim())) {
                return product;
            }
        }
        return null;
    }
}