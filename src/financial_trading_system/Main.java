package financial_trading_system;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bplustree tradingSystem = new Bplustree(5); // Order of the B+ tree

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Financial Trading System Menu ---");
            System.out.println("1. Insert New Trade");
            System.out.println("2. Run Range Query");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    insertTrade(tradingSystem, scanner);
                    break;
                case 2:
                    runRangeQuery(tradingSystem, scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);

        scanner.close();
    }

    private static void insertTrade(Bplustree tree, Scanner scanner) {
        System.out.print("Enter Stock ID: ");
        String stockId = scanner.next();
        System.out.print("Enter Timestamp: ");
        long timestamp = scanner.nextLong();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Volume: ");
        int volume = scanner.nextInt();

        Trade newTrade = new Trade(stockId, timestamp, price, volume);
        tree.insert(newTrade);
        System.out.println("Trade inserted successfully.");
    }

    private static void runRangeQuery(Bplustree tree, Scanner scanner) {
        System.out.print("Enter Start Timestamp: ");
        long start = scanner.nextLong();
        System.out.print("Enter End Timestamp: ");
        long end = scanner.nextLong();

        tree.rangeQuery(start, end);
    }
}
