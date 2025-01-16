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
        System.out.println("Stock added: " + stockId);
    }

    public void removeStock(String stockId) {
        if (stockId == null || stockId.isEmpty()) {
            throw new IllegalArgumentException("Stock ID cannot be null or empty.");
        }

        Node<String, Float> node = stocks.search(stocks.root, stockId);
        if (node == null) {
            throw new IllegalArgumentException("Stock ID " + stockId + " not found.");
        }

        // Implement the logic to remove the node from the tree
    }

    public void updateStock(String stockId, long timestamp, Float priceDifference) {
        if (stockId == null || stockId.isEmpty()) {
            throw new IllegalArgumentException("Stock ID cannot be null or empty.");
        }

        if (priceDifference == null) {
            throw new IllegalArgumentException("Price difference cannot be null.");
        }

        Node<String, Float> node = stocks.search(stocks.root, stockId);
        if (node == null) {
            throw new IllegalArgumentException("Stock ID " + stockId + " not found.");
        }

        node.setValue(node.getValue() + priceDifference);
    }

    public Float getStockPrice(String stockId) {
        if (stockId == null || stockId.isEmpty()) {
            throw new IllegalArgumentException("Stock ID cannot be null or empty.");
        }

        System.out.println("Searching for stock ID: " + stockId);
        Node<String, Float> temp = stocks.search(stocks.root, stockId);
        if (temp == null) {
            throw new IllegalArgumentException("Stock ID " + stockId + " not found.");
        }

        System.out.println("Found stock ID: " + stockId + " with price: " + temp.getValue());
        return temp.getValue();
    }

    public void removeStockTimestamp(String stockId, long timestamp) {
        // Implement the logic to remove a specific timestamp from a stock's history
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