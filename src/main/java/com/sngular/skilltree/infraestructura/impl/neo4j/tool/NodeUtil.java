package com.sngular.skilltree.infraestructura.impl.neo4j.tool;

import org.neo4j.driver.Value;
import org.neo4j.driver.internal.InternalNode;

import java.time.LocalDate;

public class NodeUtil {

    private NodeUtil() {
    }

    public static String getValueAsString(Value value, String name) {
        var data = value.get(name);
        return !data.isNull() ? data.asString() : "";
    }

    public static LocalDate getValueAsLocalDate(Value value, String name) {
        var data = value.get(name);
        return !data.isNull() ? data.asLocalDate() : null;
    }

    public static LocalDate getValueAsLocalDate(InternalNode value, String name) {
        var data = value.get(name);
        return !data.isNull() ? data.asLocalDate() : null;
    }

    public static boolean getValueAsBoolean(Value value, String name) {
        var data = value.get(name);
        return !data.isNull() && data.asBoolean();
    }
}
