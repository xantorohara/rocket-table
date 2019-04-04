package io.github.xantorohara.rocket_table.engine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmartWatch {

    private String name;
    private long time = System.currentTimeMillis();

    public SmartWatch(String name) {
        this.name = name;
    }

    public static SmartWatch start(String name) {
        return new SmartWatch(name);
    }

    public void stop() {
        log.info("[{}]: [{}]", name, System.currentTimeMillis() - time);
    }
}
