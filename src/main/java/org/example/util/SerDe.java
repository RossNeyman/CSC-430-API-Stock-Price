package org.example.util;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Stock;

public class SerDe {
    static ObjectMapper mapper = new ObjectMapper();
    public static Stock deserializeJson(String json){
        try{
            JsonNode rootNode = mapper.readTree(json);
            JsonNode resultsNode = rootNode.get("results");

            return mapper.treeToValue(resultsNode, Stock.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
