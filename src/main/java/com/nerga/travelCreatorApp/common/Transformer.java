package com.nerga.travelCreatorApp.common;

@FunctionalInterface
public interface Transformer<T, R> {
    R transform(T object);
}
