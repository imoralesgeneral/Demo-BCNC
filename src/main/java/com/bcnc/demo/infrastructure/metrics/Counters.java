package com.bcnc.demo.infrastructure.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;

public class Counters {

    private static final String APP_DEMO = "DemoApp";
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";

    public static final Counter ALBUM_SUCCESS = Metrics.counter(APP_DEMO, "album", SUCCESS);
    public static final Counter ALBUM_FAILURE = Metrics.counter(APP_DEMO, "album", FAILURE);
    public static final Counter ENRICH_SUCCESS = Metrics.counter(APP_DEMO, "enrich", SUCCESS);
    public static final Counter ENRICH_FAILURE = Metrics.counter(APP_DEMO, "enrich", FAILURE);
    public static final Counter SAVE_SUCCESS = Metrics.counter(APP_DEMO, "save", SUCCESS);
    public static final Counter SAVE_FAILURE = Metrics.counter(APP_DEMO, "save", FAILURE);

    private Counters(){

    }

}
