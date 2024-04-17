package ru.and.restapp.cache;

class CacheEntity<T> {
    private T value;
    private long timeAddition;

    CacheEntity(final T value, final long timestamp) {
        this.value = value;
        this.timeAddition = timestamp;
    }

    public T getValue() {
        return value;
    }

    public long getTimeAddition() {
        return timeAddition;
    }
}