package io.github.xantorohara.rocket_table;

import java.util.Objects;

public class Const {
    public static final String VERSION = "Rocket table v" +
            Objects.toString(Const.class.getPackage().getImplementationVersion(), "DEV");
}
