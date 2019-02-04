package io.github.xantorohara.rocket_table;

import java.util.logging.Logger;

public class SmartWatch {
    private static final Logger log = Logger.getLogger("rocket_table");

    private String name;
    private long time = System.currentTimeMillis();

    public SmartWatch(String name) {
        this.name = name;
    }

    public static SmartWatch start(String name) {
        return new SmartWatch(name);
    }

    public void stop() {
        log.info(name + ": " + (System.currentTimeMillis() - time));
    }
}
