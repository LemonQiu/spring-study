package org.example.factory;

import org.example.bean.Person;
import org.springframework.beans.factory.FactoryBean;

/**
 * @Author qiu
 * @Date 2021/1/5 2:48
 */
public class PersonFactoryBean implements FactoryBean<Person> {

    @Override
    public Person getObject() throws Exception {
        Person person = new Person();
        person.setName("xiao bai");
        person.setGender(1);
        person.setAge(15);
        person.setIdCard("456461231354666");
        return person;
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
