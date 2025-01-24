import java.util.*;

public class Stock {
    private String stockId;
    private long initialTimestamp;
    private Float initialPrice;
    private List<PriceUpdateEvent> priceUpdateEvents;

    public Stock(String stockId, long initialTimestamp, Float initialPrice) {
        this.stockId = stockId;
        this.initialTimestamp = initialTimestamp;
        this.initialPrice = initialPrice;
        this.priceUpdateEvents = new ArrayList<>();
    }

    public String getStockId() {
        return stockId;
    }

    public Float getInitialPrice() {
        return initialPrice;
    }

    public void addPriceUpdateEvent(long timestamp, Float priceDifference) {
        priceUpdateEvents.add(new PriceUpdateEvent(timestamp, priceDifference, true));
    }

    public void invalidatePriceUpdateEvent(long timestamp) {
        for (PriceUpdateEvent event : priceUpdateEvents) {
            if (event.getTimestamp() == timestamp) {
                event.setRelevant(false);
                return;
            }
        }
        throw new IllegalArgumentException("Price update event not found");
    }

    public Float getCurrentPrice() {
        Float currentPrice = initialPrice;
        for (PriceUpdateEvent event : priceUpdateEvents) {
            if (event.isRelevant()) {
                currentPrice += event.getPriceDifference();
            }
        }
        return currentPrice;
    }

    private class PriceUpdateEvent {
        private long timestamp;
        private Float priceDifference;
        private boolean isRelevant;

        public PriceUpdateEvent(long timestamp, Float priceDifference, boolean isRelevant) {
            this.timestamp = timestamp;
            this.priceDifference = priceDifference;
            this.isRelevant = isRelevant;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public Float getPriceDifference() {
            return priceDifference;
        }

        public boolean isRelevant() {
            return isRelevant;
        }

        public void setRelevant(boolean isRelevant) {
            this.isRelevant = isRelevant;
        }
    }
}