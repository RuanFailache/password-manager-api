package dev.passwordmanager.utils.mocks;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

abstract public class BaseMock<T> {
    protected static final Faker faker = new Faker();

    abstract public T generate();

    public List<T> generateList(int count) {
        var list = new ArrayList<T>();
        for (int i = 0; i < count; i++) {
            var data = this.generate();
            list.add(data);
        }
        return list;
    }
}
