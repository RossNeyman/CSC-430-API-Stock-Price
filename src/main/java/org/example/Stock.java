package org.example;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a stock with details such as ticker symbol, name, market, exchange, and other attributes.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {
    private String ticker;
    private String name;
    private String description;
    private String market;
    private String primary_exchange;
    private String type;
    private String currency_name;
    private String list_date;

    /**
     * Sets the ticker symbol of the stock.
     * @param ticker The ticker symbol.
     */
    public void setTicker(String ticker) { this.ticker = ticker; }

    /**
     * Sets the name of the stock.
     * @param name The name of the stock.
     */
    public void setName(String name) { this.name = name; }

    /**
     * Sets the market in which the stock is traded.
     * @param market The stock market.
     */
    public void setMarket(String market) { this.market = market; }

    /**
     * Sets the primary exchange where the stock is listed.
     * @param primary_exchange The primary exchange.
     */
    public void setPrimaryExchange(String primary_exchange) { this.primary_exchange = primary_exchange; }

    /**
     * Sets the type of stock.
     * @param type The type of stock.
     */
    public void setType(String type) { this.type = type; }

    /**
     * Sets the currency name used for trading the stock.
     * @param currency_name The currency name.
     */
    public void setCurrency_name(String currency_name) { this.currency_name = currency_name; }

    /**
     * Sets the date when the stock was listed.
     * @param list_date The listing date.
     */
    public void setList_date(String list_date) { this.list_date = list_date; }

    /**
     * Sets the description of the stock.
     * @param description A brief description of the stock.
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Gets the description of the stock.
     * @return The stock description.
     */
    public String getDescription() { return description; }

    /**
     * Gets the listing date of the stock.
     * @return The stock listing date.
     */
    public String getListDate() { return list_date; }

    /**
     * Gets the ticker symbol of the stock.
     * @return The ticker symbol.
     */
    public String getTicker() { return ticker; }

    /**
     * Gets the name of the stock.
     * @return The stock name.
     */
    public String getName() { return name; }

    /**
     * Gets the market where the stock is traded.
     * @return The stock market.
     */
    public String getMarket() { return market; }

    /**
     * Gets the primary exchange where the stock is listed.
     * @return The primary exchange.
     */
    public String getPrimaryExchange() { return primary_exchange; }

    /**
     * Gets the type of stock.
     * @return The stock type.
     */
    public String getType() { return type; }

    /**
     * Gets the currency name used for trading the stock.
     * @return The currency name.
     */
    public String getCurrencyName() { return currency_name; }

    public Stock(){}
}
