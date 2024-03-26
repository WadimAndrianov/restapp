package ru.and.restapp.model.Cache;

class CacheEntity<T> {
    private T value;
    private long timeAddition;

    public CacheEntity(T value, long timestamp) {
        this.value = value;
        this.timeAddition = timestamp;
    }

    public T getValue() {
        return value;
    }

    public long timeAddition() {
        return timeAddition;
    }
}