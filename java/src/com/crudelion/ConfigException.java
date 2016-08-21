package com.crudelion;

/**
 * Created by guliuzhong on 2016/8/21.
 */
public class ConfigException extends Exception {
    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigException() {
    }

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(Throwable cause) {
        super(cause);
    }

    public ConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
