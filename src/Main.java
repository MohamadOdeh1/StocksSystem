import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> stockIds = new ArrayList<>();
        stockIds.add("ABCDEF");
        stockIds.add("GHIJKL");
        stockIds.add("MNOPQR");
        stockIds.add("STUVWX");
        stockIds.add("YZABCD");
        stockIds.add("EFGHIJ");
        stockIds.add("KLMNOP");
        stockIds.add("QRSTUV");
        stockIds.add("WXYZAB");
        stockIds.add("CDEFGH");

        ArrayList<Float> prices = new ArrayList<>();
        prices.add(123.45f);
        prices.add(67.89f);
        prices.add(45.67f);
        prices.add(234.56f);
        prices.add(89.01f);
        prices.add(150.34f);
        prices.add(98.76f);
        prices.add(210.45f);
        prices.add(115.99f);
        prices.add(300.12f);

        ArrayList<Long> initialTimestamps = new ArrayList<>();
        initialTimestamps.add(1708647300000L);
        initialTimestamps.add(1708650900000L);
        initialTimestamps.add(1708654500000L);
        initialTimestamps.add(1708658100000L);
        initialTimestamps.add(1708661700000L);
        initialTimestamps.add(1708665300000L);
        initialTimestamps.add(1708668900000L);
        initialTimestamps.add(1708672500000L);
        initialTimestamps.add(1708676100000L);
        initialTimestamps.add(1708679700000L);

        StockManager stockManager = new StockManager();
        stockManager.initStocks();
        boolean expression = false;
        for (int i = 0; i < stockIds.size(); i++) {
            stockManager.addStock(stockIds.get(i), initialTimestamps.get(i), prices.get(i));
            float currentPrice = stockManager.getStockPrice(stockIds.get(i));
            expression = currentPrice == prices.get(i);
            System.out.printf("Assert: Stock %s price is %f, expected %f\n", stockIds.get(i), currentPrice, prices.get(i));
            Assert(expression);
        }

        System.out.println("Stock IDs after addition: " + stockIds); // Debugging print
        stockManager.stocks.printTree(); // Print all stocks

        for (int i = 0; i < 3; i++) {
            String stockIdToRemove = stockIds.remove(0);
            prices.remove(0);
            initialTimestamps.remove(0);
            stockManager.removeStock(stockIdToRemove);
            try {
                stockManager.getStockPrice(stockIdToRemove);
                System.out.println("Test failed: Exception was not thrown");
            } catch (IllegalArgumentException e) {
                System.out.println("Test passed: Exception was thrown as expected");
            } catch (Exception e) {
                System.out.println("Test failed: An unexpected exception was thrown");
            }
            expression = !stockIds.contains(stockIdToRemove);
            System.out.printf("Assert: Stock %s removed\n", stockIdToRemove);
            Assert(expression);
            System.out.println("Removed Stock: " + stockIdToRemove);
        }

        System.out.println("Stock IDs after removal: " + stockIds); // Debugging print
        stockManager.stocks.printTree(); // Print all stocks

        Map<String, ArrayList<Map.Entry<Long, Float>>> additionalData = new HashMap<>();

        // Adding additional timestamps and price changes based on original timestamps
        ArrayList<Map.Entry<Long, Float>> data1 = new ArrayList<>();
        data1.add(Map.entry(initialTimestamps.get(0) + 60000L, 5.67f)); // Add 1 minute
        data1.add(Map.entry(initialTimestamps.get(0) + 120000L, -3.21f)); // Add 2 minutes
        additionalData.put("STUVWX", data1);

        ArrayList<Map.Entry<Long, Float>> data2 = new ArrayList<>();
        data2.add(Map.entry(initialTimestamps.get(1) + 300000L, 7.89f)); // Add 5 minutes
        additionalData.put("YZABCD", data2);

        ArrayList<Map.Entry<Long, Float>> data3 = new ArrayList<>();
        data3.add(Map.entry(initialTimestamps.get(2) + 60000L, -2.34f)); // Add 1 minute
        data3.add(Map.entry(initialTimestamps.get(2) + 180000L, 4.56f)); // Add 3 minutes
        data3.add(Map.entry(initialTimestamps.get(2) + 300000L, -1.78f)); // Add 5 minutes
        additionalData.put("EFGHIJ", data3);

        for (int i = 0; i < 3; i++) {
            String stockId = stockIds.get(i);
            System.out.printf("Updating stock ID: %s\n", stockId); // Debugging print
            ArrayList<Map.Entry<Long, Float>> data = additionalData.get(stockId);
            if (data != null) {
                for (Map.Entry<Long, Float> entry : data) {
                    stockManager.updateStock(stockId, entry.getKey(), entry.getValue());
                }
            }
        }

        expression = stockManager.getStockPrice("STUVWX") == 237.02f;
        System.out.printf("Assert: Stock STUVWX price is %f, expected 237.02\n", stockManager.getStockPrice("STUVWX"));
        Assert(expression);
        expression = stockManager.getStockPrice("YZABCD") == 96.9f;
        System.out.printf("Assert: Stock YZABCD price is %f, expected 96.9\n", stockManager.getStockPrice("YZABCD"));
        Assert(expression);
        expression = stockManager.getStockPrice("EFGHIJ") == 150.78f;
        System.out.printf("Assert: Stock EFGHIJ price is %f, expected 150.78\n", stockManager.getStockPrice("EFGHIJ"));
        Assert(expression);

        for (String stockId : stockIds) {
            double price = stockManager.getStockPrice(stockId);
            System.out.println("Stock ID: " + stockId + ", Current Price: " + price);
        }

        if (additionalData.containsKey("STUVWX")) {
            ArrayList<Map.Entry<Long, Float>> stockData = additionalData.get("STUVWX");
            stockManager.removeStockTimestamp("STUVWX", stockData.get(1).getKey());
            stockData.remove(1);
        }
        if (additionalData.containsKey("EFGHIJ")) {
            ArrayList<Map.Entry<Long, Float>> stockData = additionalData.get("EFGHIJ");
            stockManager.removeStockTimestamp("EFGHIJ", stockData.get(2).getKey());
            stockManager.removeStockTimestamp("EFGHIJ", stockData.get(0).getKey());
        }

        for (String stockId : stockIds) {
            double price = stockManager.getStockPrice(stockId);
            System.out.println("Stock ID: " + stockId + ", Current Price: " + price);
        }

        expression = stockManager.getStockPrice("STUVWX") == 240.23f;
        System.out.printf("Assert: Stock STUVWX price is %f, expected 240.23\n", stockManager.getStockPrice("STUVWX"));
        Assert(expression);
        expression = stockManager.getStockPrice("EFGHIJ") == 154.9f;
        System.out.printf("Assert: Stock EFGHIJ price is %f, expected 154.9\n", stockManager.getStockPrice("EFGHIJ"));
        Assert(expression);

        Float price1 = 10f;
        Float price2 = 30f;
        int stockAmount = stockManager.getAmountStocksInPriceRange(price1, price2);
        expression = stockAmount == 0;
        System.out.printf("Assert: Stocks in range %.2f to %.2f is %d, expected 0\n", price1, price2, stockAmount);
        Assert(expression);
        System.out.println(stockManager.getStocksInPriceRange(price1, price2));

        price1 = 50f;
        price2 = 170f;
        stockAmount = stockManager.getAmountStocksInPriceRange(price1, price2);
        expression = stockAmount == 4;
        System.out.printf("Assert: Stocks in range %.2f to %.2f is %d, expected 4\n", price1, price2, stockAmount);
        Assert(expression);
        System.out.println(stockAmount);

        String[] stocksInRange = stockManager.getStocksInPriceRange(price1, price2);
        expression = stocksInRange[0].equals("YZABCD");
        System.out.printf("Assert: Stocks in range %.2f to %.2f, first stock is %s, expected YZABCD\n", price1, price2, stocksInRange[0]);
        Assert(expression);
        expression = stocksInRange[1].equals("KLMNOP");
        System.out.printf("Assert: Stocks in range %.2f to %.2f, second stock is %s, expected KLMNOP\n", price1, price2, stocksInRange[1]);
        Assert(expression);
        expression = stocksInRange[2].equals("WXYZAB");
        System.out.printf("Assert: Stocks in range %.2f to %.2f, third stock is %s, expected WXYZAB\n", price1, price2, stocksInRange[2]);
        Assert(expression);
        expression = stocksInRange[3].equals("EFGHIJ");
        System.out.printf("Assert: Stocks in range %.2f to %.2f, fourth stock is %s, expected EFGHIJ\n", price1, price2, stocksInRange[3]);
        Assert(expression);

        for (String stock : stocksInRange) {
            System.out.println(stock);
        }
    }

    public static void Assert(boolean expression) {
        if (!expression) {
            throw new AssertionError();
        }
    }
}