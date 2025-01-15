public class StockManager {
    // add code here
    TwoThreeTree<String, Float> stocks;
    public StockManager() {
        // add code here
        stocks = new TwoThreeTree<>();
    }

    // 1. Initialize the system
    public void initStocks() {
        // add code here
        stocks.init();
        System.out.println("The root before:"+stocks.root);
        stocks.root.getLeftChild().setKey("");
        stocks.root.getMiddleChild().setKey("ZZZZZZZZZZZZZZZZZZ");
        stocks.root.setKey("ZZZZZZZZZZZZZZZZZZ");
    }

    // 2. Add a new stock
    public void addStock(String stockId, long timestamp, Float price) {
        // add code here
        stocks.insert(stockId, price,timestamp);
    }

    // 3. Remove a stock
    public void removeStock(String stockId) {
        // add code here
    }

    // 4. Update a stock price
    public void updateStock(String stockId, long timestamp, Float priceDifference) {
        // add code here
    }

    // 5. Get the current price of a stock
    public Float getStockPrice(String stockId) {
        Node<String, Float> temp = stocks.search(stocks.root, stockId);
        if (temp == null) {
            throw new IllegalArgumentException("Stock ID " + stockId + " not found.");
        }
        return temp.value;
    }


    // 6. Remove a specific timestamp from a stock's history
    public void removeStockTimestamp(String stockId, long timestamp) {
        // add code here
    }

    // 7. Get the amount of stocks in a given price range
    public int getAmountStocksInPriceRange(Float price1, Float price2) {
        // add code here
        return 0;
    }

    // 8. Get a list of stock IDs within a given price range
    public String[] getStocksInPriceRange(Float price1, Float price2) {
        // add code here
        return null;
    }

}


