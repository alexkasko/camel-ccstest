package ru.pensionsoft.rostelecom.ccstest.common;

import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * Aggregation strategy, puts enrichment data into header of original message in JSON form
 *
 * @author alexey
 * Date: 9/16/12
 */
public class JsonHeaderAggregationStrategy implements AggregationStrategy {
    private Gson gson = new Gson();
    private final String headerName;

    /**
     * @param headerName original message header name to store enrichment data
     */
    public JsonHeaderAggregationStrategy(String headerName) {
        checkArgument(isNotBlank(headerName), "Provided header name is blank: '%s'", headerName);
        this.headerName = headerName;
    }

    /**
     * @param gson optional configured {@code Gson} instance
     */
    public void setGson(Gson gson) {
        checkNotNull(gson, "Provided gson is null");
        this.gson = gson;
    }

    /**
     * @param oldExchange original message
     * @param newExchange enrichment data
     * @return original message
     * @throws NullPointerException on null data or input
     * @throws IllegalArgumentException if header already exists
     */
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        checkNotNull(oldExchange, "Provided oldExchange is null");
        checkNotNull(newExchange, "Provided newExchange is null");
        Object enrichment = newExchange.getIn().getBody();
        checkNotNull(enrichment, "Null enrichment in newExchange: '%s'", newExchange);
        Object existed = oldExchange.getIn().getHeader(headerName);
        checkArgument(null == existed, "Original message already contain header: '%s' with content: '%s'", headerName, existed);
        oldExchange.getIn().setHeader(headerName, gson.toJson(enrichment));
        return oldExchange;
    }
}
