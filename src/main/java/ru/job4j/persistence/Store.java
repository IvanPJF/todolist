package ru.job4j.persistence;

import ru.job4j.model.Item;

import java.util.Collection;

public interface Store {

    Collection<Item> allItems();

    Item add(Item item);

    void changeDone(Item item);

    boolean delete(Item item);
}
