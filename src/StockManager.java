import java.util.*;

public class StockManager {
    private Operations<String> tree;

    private Map<String, Stock> stocks;

    public StockManager() {
        this.tree = new Operations<>();
        this.stocks = new HashMap<>();
    }

    public void initStocks() {
        // Initialize the stock manager
    }

    public void addStock(String stockId, long timestamp, Float price) {
        if (stocks.containsKey(stockId)) {
            throw new IllegalArgumentException("Stock already exists");
        }
        Stock stock = new Stock(stockId, timestamp, price);
        stocks.put(stockId, stock);
        tree.add(stockId);
    }

    public void removeStock(String stockId) {
        if (!stocks.containsKey(stockId)) {
            throw new IllegalArgumentException("Stock does not exist");
        }
        stocks.remove(stockId);
        tree.remove(stockId);
    }

    public void updateStock(String stockId, long timestamp, Float priceDifference) {
        Stock stock = stocks.get(stockId);
        if (stock == null) {
            throw new IllegalArgumentException("Stock does not exist");
        }
        stock.addPriceUpdateEvent(timestamp, priceDifference);
    }

    public Float getStockPrice(String stockId) {
        Stock stock = stocks.get(stockId);
        if (stock == null) {
            throw new IllegalArgumentException("Stock does not exist");
        }
        return stock.getCurrentPrice();
    }

    public void removeStockTimestamp(String stockId, long timestamp) {
        Stock stock = stocks.get(stockId);
        if (stock == null) {
            throw new IllegalArgumentException("Stock does not exist");
        }
        stock.invalidatePriceUpdateEvent(timestamp);
    }

    public int getAmountStocksInPriceRange(Float price1, Float price2) {
        int count = 0;
        for (Stock stock : stocks.values()) {
            Float currentPrice = stock.getCurrentPrice();
            if (currentPrice >= price1 && currentPrice <= price2) {
                count++;
            }
        }
        return count;
    }

    public String[] getStocksInPriceRange(Float price1, Float price2) {
        List<Stock> stockListInRange = new ArrayList<>();
        for (Stock stock : stocks.values()) {
            Float currentPrice = stock.getCurrentPrice();
            if (currentPrice >= price1 && currentPrice <= price2) {
                stockListInRange.add(stock);
            }
        }
        stockListInRange.sort(Comparator.comparing(Stock::getCurrentPrice));
        return stockListInRange.stream().map(Stock::getStockId).toArray(String[]::new);
    }
}