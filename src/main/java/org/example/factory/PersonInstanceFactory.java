package org.example.factory;

import org.example.bean.Person;

/**
 * Person的实例工厂
 * @Author qiu
 * @Date 2021/1/5 2:37
 */
public class PersonInstanceFactory {

    private Person getInstance() {
        Person person = new Person();
        person.setAge(18);
        person.setGender(1);
        person.setIdCard("349879454455564865");
        person.setName("xiao ming");
        return person;
    }
}
