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


        StockManager stockmanger = new StockManager();
        stockmanger.initStocks();
        boolean expression = false;
        for (int i = 0; i < stockIds.size(); i++) {
            stockmanger.addStock(stockIds.get(i), initialTimestamps.get(i), prices.get(i));
            expression = stockmanger.getStockPrice(stockIds.get(i)).equals(prices.get(i));
            Assert(expression);
        }
        for (int i = 0; i < 3; i++) {
            String stockIdToRemove = stockIds.remove(0);
            prices.remove(0);
            initialTimestamps.remove(0);
            stockmanger.removeStock(stockIdToRemove);
            try {
                stockmanger.getStockPrice(stockIdToRemove);
                System.out.println("Test failed: Exception was not thrown");
            } catch (IllegalArgumentException e) {
                System.out.println("Test passed: Exception was thrown as expected");
            } catch (Exception e) {
                System.out.println("Test failed: An unexpected exception was thrown");
            }
            Assert(expression);
            System.out.println("Removed Stock: " + stockIdToRemove);

        }
    }
    public static void Assert(boolean expression){
        if (!expression){
            throw new AssertionError();
        }

    }
}
