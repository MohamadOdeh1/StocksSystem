public class Stock{
    String stockId;
    Float price;
    Long timestamp;
    public Stock(String stockId, Float price, Long timestamp){
        this.stockId = stockId;
        this.price = price;
        this.timestamp = timestamp;
    }
    public String getStockId(){
        return this.stockId;
    }
    public Float getPrice(){
        return this.price;
    }
    public Long getTimestamp(){
        return this.timestamp;
    }
    public void setPrice(Float price){
        this.price = price;
    }
    public void setTimestamp(Long timestamp){
        this.timestamp = timestamp;
    }
    public void setStockId(String stockId){
        this.stockId = stockId;
    }
    @Override
    public String toString(){
        return "Stock{" +
                "stockId='" + stockId + '\'' +
                ", price=" + price +
                ", timestamp=" + timestamp +
                '}';
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof Stock){
            Stock stock = (Stock) o;
            return stockId.equals(stock.getStockId());
        }
        return false;
    }
}
