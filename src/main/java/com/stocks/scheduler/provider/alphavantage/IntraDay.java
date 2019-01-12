package com.stocks.scheduler.provider.alphavantage;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Representation of intra day response from api.
 *
 * @see TimeSeriesResponse
 */
public class IntraDay extends TimeSeriesResponse {

    private IntraDay(final Map<String, String> metaData,
                     final List<StockData> stocks) {
        super(metaData, stocks);
    }

    /**
     * Creates {@code IntraDay} instance from json.
     *
     * @param json string to parse
     * @return IntraDay instance
     */
    public static IntraDay from(Interval interval, String json)  {
        Parser parser = new Parser(interval);
        return parser.parseJson(json);
    }

    /**
     * Helper class for parsing json to {@code IntraDay}.
     *
     * @see TimeSeriesParser
     * @see JsonParser
     */
    private static class Parser extends TimeSeriesParser<IntraDay> {
        private final Interval interval;

        Parser(Interval interval) {
            this.interval = interval;
        }

        @Override
        String getStockDataKey() {
            return "Time Series (" + interval.getValue() + ")";
        }

        @Override
        IntraDay resolve(Map<String, String> metaData,
                         Map<String, Map<String, String>> stockData)  {
            List<StockData> stocks = new ArrayList<>();
            try {
                stockData.forEach((key, values) -> stocks.add(new StockData(
                        LocalDateTime.parse(key, DATE_WITH_TIME_FORMAT),
                        Double.parseDouble(values.get("1. open")),
                        Double.parseDouble(values.get("2. high")),
                        Double.parseDouble(values.get("3. low")),
                        Double.parseDouble(values.get("4. close")),
                        Long.parseLong(values.get("5. volume"))
                )));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return new IntraDay(metaData, stocks);
        }
    }

}
