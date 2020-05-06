package ru.job4j.service;

import ru.job4j.model.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiConsumer;

public class Dispatcher {

    private final Map<String, BiConsumer<Service, Item>> map = new HashMap<>();
    private static final Dispatcher DISPATCHER = new Dispatcher();

    private Dispatcher() {
    }

    public static Dispatcher getInstance() {
        return DISPATCHER;
    }

    public Dispatcher init() {
        load("add", Service::add);
        load("changeDone", Service::changeDone);
        return this;
    }

    public void load(String action, BiConsumer<Service, Item> service) {
        this.map.put(action, service);
    }

    public void send(String action, Service logic, Item item) {
        BiConsumer<Service, Item> biConsumer = this.map.get(action);
        if (Objects.isNull(biConsumer)) {
            throw new NoSuchElementException("No action found.");
        }
        biConsumer.accept(logic, item);
    }
}
