package org.example;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {
    private String ticker;
    private String name;
    private String market;
    private String primary_exchange;
    private String type;
    private String currency_name;
    private String list_date;

    public void setTicker(String ticker) { this.ticker = ticker;}
    public void setName(String name) { this.name = name; }
    public void setMarket(String market) { this.market = market; }
    public void setPrimaryExchange(String primary_exchange) { this.primary_exchange = primary_exchange; }
    public void setType(String type) { this.type = type; }
    public void setCurrency_name(String currency_name) { this.currency_name = currency_name; }
    public void setList_date(String list_date) { this.list_date = list_date; }

    public String getListDate(){return list_date;}
    public String getTicker(){return ticker;}
    public String getName(){return name;}
    public String getMarket(){return market;}
    public String getPrimaryExchange(){return primary_exchange;}
    public String getType(){return type;}
    public String getCurrencyName(){return currency_name;}
}
