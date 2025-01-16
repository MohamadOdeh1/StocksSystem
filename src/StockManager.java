public class StockManager {
    TwoThreeTree<String, Float> stocks;

    public StockManager() {
        stocks = new TwoThreeTree<>();
    }

    public void initStocks() {
        stocks.init();
        stocks.root.getLeftChild().setKey("");
        stocks.root.getMiddleChild().setKey("ZZZZZZZZZ");
        stocks.root.setKey("ZZZZZZZZZ");
    }

    public void addStock(String stockId, long timestamp, Float price) {
        if (stockId == null || stockId.isEmpty()) {
            throw new IllegalArgumentException("Stock ID cannot be null or empty.");
        }

        if (price == null || price < 0) {
            throw new IllegalArgumentException("Price must be a non-negative value.");
        }

        stocks.insert(stockId, price, timestamp);
    }

    public void removeStock(String stockId) {
        if (stockId == null || stockId.isEmpty()) {
            throw new IllegalArgumentException("Stock ID cannot be null or empty.");
        }

        stocks.remove(stockId);
    }

    public void updateStock(String stockId, long timestamp, Float priceDifference) {
        System.out.printf("Updating stock ID: %s with timestamp: %d and price difference: %f\n", stockId, timestamp, priceDifference);
        Node<String, Float> stockNode = stocks.search(stocks.root, stockId);
        if (stockNode == null) {
            System.out.printf("Stock ID %s not found during update\n", stockId);
            throw new IllegalArgumentException("Stock ID " + stockId + " not found.");

        }
        if (priceDifference == null) {
            throw new IllegalArgumentException("Price difference cannot be null.");
        }
        stockNode.updatePrice(timestamp, priceDifference);
        System.out.println("Updated stock ID: " + stockId + " with new price: " + stockNode.value);
    }

    public Float getStockPrice(String stockId) {
        Node<String, Float> stockNode = stocks.search(stocks.root, stockId);
        if (stockNode == null) {
            throw new IllegalArgumentException("Stock ID " + stockId + " not found.");
        }
        return stockNode.getValue();
    }

    public void removeStockTimestamp(String stockId, long timestamp) {
        Node<String, Float> stockNode = stocks.search(stocks.root, stockId);
        if (stockNode == null) {
            throw new IllegalArgumentException("Stock ID " + stockId + " not found.");
        }
        stockNode.removePriceUpdate(timestamp);
        System.out.println("Removed timestamp: " + timestamp + " from stock ID: " + stockId);
    }

    public int getAmountStocksInPriceRange(Float price1, Float price2) {
        if (price1 == null || price2 == null) {
            throw new IllegalArgumentException("Prices cannot be null.");
        }

        // Implement logic to count stocks in the given price range
        return 0;
    }

    public String[] getStocksInPriceRange(Float price1, Float price2) {
        if (price1 == null || price2 == null) {
            throw new IllegalArgumentException("Prices cannot be null.");
        }

        // Implement logic to get stock IDs in the given price range
        return null;
    }
}