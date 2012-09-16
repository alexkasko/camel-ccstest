package ru.pensionsoft.rostelecom.ccstest.common;

import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 * User: alexey
 * Date: 9/16/12
 */
public class JsonHeaderAggregationStrategy implements AggregationStrategy {
    // todo: provide setter for serialization configuration
    private Gson gson = new Gson();
    private final String headerName;

    public JsonHeaderAggregationStrategy(String headerName) {
        this.headerName = headerName;
    }

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Object enrichment = newExchange.getIn().getBody();
        oldExchange.getIn().setHeader(headerName, gson.toJson(enrichment));
        return oldExchange;
    }
}
