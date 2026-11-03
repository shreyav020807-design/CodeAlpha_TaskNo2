import java.util.Scanner;
public class Stock {
    private String ticker;
    private double price;

    public Stock(String ticker, double price) {
        this.ticker = ticker;
        this.price = price;
    }

    public String getTicker() { return ticker; }
    public double getPrice() { return price; }

    // Simulates stock market changes by fluctuating price up or down slightly
    public void updatePrice() {
        double percentChange = (Math.random() * 10 - 5) / 100; // -5% to +5% change
        this.price += this.price * percentChange;
        if (this.price < 0.01) this.price = 0.01; // Prevent stock from hitting 0
    }
}
class StockPlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Setup Portfolio Variables
        double balance = 10000.00; // Initial Cash
        int appleSharesOwned = 0;
        int googleSharesOwned = 0;

        // Setup Market Assets (Using Mock Data)
        Stock apple = new Stock("AAPL", 150.00);
        Stock google = new Stock("GOOG", 2800.00);
        while (true) {
            // Update stock prices dynamically on each turn
            apple.updatePrice();
            google.updatePrice();

            // Calculate Portfolio Valuation
            double totalValue = balance + (appleSharesOwned * apple.getPrice()) + (googleSharesOwned * google.getPrice());

            System.out.println("\n====== LIVE MARKET DASHBOARD ======");
            System.out.printf("Available Cash: $%.2f\n", balance);
            System.out.printf("Total Portfolio Valuation: $%.2f\n", totalValue);
            System.out.println("-----------------------------------");
            System.out.printf("1. %s | Current Price: $%.2f (Owned: %d)\n", apple.getTicker(), apple.getPrice(), appleSharesOwned);
            System.out.printf("2. %s | Current Price: $%.2f (Owned: %d)\n", google.getTicker(), google.getPrice(), googleSharesOwned);
            System.out.println("3. Refresh Market / Skip Turn");
            System.out.println("4. Exit Application");
            System.out.print("Select an action option (1-4): ");
            
            int choice = scanner.nextInt();
            if (choice == 4) break;
            
            if (choice == 3) {
                System.out.println("Market tickers updated!");
                continue;
            }
            // Determine targeted stock asset based on selection choice
            Stock targetStock = (choice == 1) ? apple : google;
            
            System.out.print("Type 1 to BUY or Type 2 to SELL: ");
            int action = scanner.nextInt();
            System.out.print("Enter quantity of shares: ");
            int quantity = scanner.nextInt();
            
            double totalCost = targetStock.getPrice() * quantity;

            if (action == 1) { // BUY LOGIC
                if (balance >= totalCost) {
                    balance -= totalCost;
                    if (choice == 1) appleSharesOwned += quantity;
                    else googleSharesOwned += quantity;
                    System.out.println("Purchase successful!");
                } else {
                    System.out.println("Error: Insufficient cash balance.");
                }
            } else if (action == 2) { // SELL LOGIC
                int currentlyOwned = (choice == 1) ? appleSharesOwned : googleSharesOwned;
                if (currentlyOwned >= quantity) {
                    balance += totalCost;
                    if (choice == 1) appleSharesOwned -= quantity;
                    else googleSharesOwned -= quantity;
                    System.out.println("Sale transaction complete!");
                } else {
                    System.out.println("Error: You don't own that many shares.");
                }
            } else {
                System.out.println("Invalid selection option.");
            }
        }
        
        System.out.println("Thank you for using the Trading Simulator!");
        scanner.close();
    }
}

   