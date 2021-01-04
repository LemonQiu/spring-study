package org.example.factory;

import org.example.bean.Person;

/**
 * Person的静态工厂
 * @Author qiu
 * @Date 2021/1/5 2:33
 */
public class PersonStaticFactory {

    private static Person getInstance() {
        Person person = new Person();
        person.setAge(10);
        person.setGender(1);
        person.setIdCard("34987984243564865");
        person.setName("lisi");
        return person;
    }
}
