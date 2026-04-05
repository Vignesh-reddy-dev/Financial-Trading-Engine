package financial_trading_system;

public class Trade {
    private String stockId;
    private long timestamp;
    private double price;
    private int volume;

    public Trade(String stockId, long timestamp, double price, int volume) {
        this.stockId = stockId;
        this.timestamp = timestamp;
        this.price = price;
        this.volume = volume;
    }

    public String getStockId() {
        return stockId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "stockId='" + stockId + '\'' +
                ", timestamp=" + timestamp +
                ", price=" + price +
                ", volume=" + volume +
                '}';
    }
}