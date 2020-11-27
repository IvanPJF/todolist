package ru.job4j.service;

import ru.job4j.model.Item;

import java.util.Collection;

public interface Service {

    Collection<Item> allItems();

    void add(Item item);

    void changeDone(Item item);

    boolean delete(Item item);
}
