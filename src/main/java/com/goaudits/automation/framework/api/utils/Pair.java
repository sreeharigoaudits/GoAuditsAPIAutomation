package com.goaudits.automation.framework.api.utils;

import java.io.Serializable;
import java.util.Objects;

public class Pair<K, V> implements Serializable {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }

    @Override
    public int hashCode() {
        return key.hashCode() * 13 + (value == null ? 0 : value.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Pair) {
            Pair pair = (Pair) o;
            if (!Objects.equals(key, pair.getKey())) return false;
            return Objects.equals(value, pair.getValue());
        }
        return false;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}