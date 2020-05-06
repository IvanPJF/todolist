package ru.job4j.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Item;

import java.util.Collection;
import java.util.function.Function;

public class HiberStore implements Store {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();
    private static final Store STORE = new HiberStore();

    private HiberStore() {
    }

    public static Store getInstance() {
        return STORE;
    }

    @Override
    public Collection<Item> allItems() {
        return execute(session -> session.createQuery("from Item order by created", Item.class).list());
    }

    @Override
    public Item add(Item item) {
        return execute(session -> {
            session.save(item);
            return item;
        });
    }

    @Override
    public void changeDone(Item item) {
        execute(session -> session.createQuery("update Item set done = :newDone where id = :id")
                .setParameter("newDone", item.isDone())
                .setParameter("id", item.getId())
                .executeUpdate());
    }

    private <T> T execute(Function<Session, T> function) {
        try (final Session session = SESSION_FACTORY.openSession()) {
            try {
                session.beginTransaction();
                T result = function.apply(session);
                session.getTransaction().commit();
                return result;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw e;
            }
        }
    }

    private static SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
        }
        return null;
    }
}
