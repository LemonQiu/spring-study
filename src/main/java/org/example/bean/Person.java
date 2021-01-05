package org.example.bean;

import lombok.Data;

/**
 * @Author qiu
 * @Date 2021/1/4 22:54
 */
@Data
public class Person {

    /**
     * 名称
     * eg: 张三
     */
    private String name;

    /**
     * 年龄
     * eg: 25
     */
    private Integer age;

    /**
     * 身份证号码
     * eg: 3408xxxxxxxxxxxx
     */
    private String idCard;

    /**
     * 性别：1为男，0为女
     */
    private Integer gender;

    public Person() {
        super();
        System.out.println("Person is create......");
    }

    public void init() {
        System.out.println("Person is init......");
    }

    public void destroy() {
        System.out.println("Person is destroy......");
    }

    public Person(String name, Integer age, String idCard, Integer gender) {
        this.name = name;
        this.age = age;
        this.idCard = idCard;
        this.gender = gender;
    }
}
