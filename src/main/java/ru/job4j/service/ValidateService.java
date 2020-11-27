package ru.job4j.service;

import ru.job4j.model.Item;
import ru.job4j.persistence.HiberStore;
import ru.job4j.persistence.Store;

import java.util.Collection;

public class ValidateService implements Service {

    private static final Store STORE = HiberStore.getInstance();
    private static final ValidateService SERVICE = new ValidateService();

    private ValidateService() {
    }

    public static Service getInstance() {
        return SERVICE;
    }

    @Override
    public Collection<Item> allItems() {
        return STORE.allItems();
    }

    @Override
    public void add(Item item) {
        STORE.add(item);
    }

    @Override
    public void changeDone(Item item) {
        STORE.changeDone(item);
    }

    @Override
    public boolean delete(Item item) {
        return STORE.delete(item);
    }
}
