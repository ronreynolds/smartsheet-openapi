package com.ronreynolds.util.logging;

import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Logger;

/**
 * the static block is guaranteed to only happen once (per ClassLoader hierarchy) and it will happen before install() is called
 */
public class JULIntoSLF4J {
    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        Logger.getLogger(JULIntoSLF4J.class.getName()).info("JUL->SLF4J installed");
    }
    public static void install() {
        // triggers static block above ;-)
    }
}
