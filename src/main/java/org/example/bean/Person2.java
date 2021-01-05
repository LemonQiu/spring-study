package org.example.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @Author qiu
 * @Date 2021/1/4 22:54
 */
@Data
public class Person2 {

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

    private String[] loves;

    private List<Address> list;

    private Set<String> set;

    private Map<String, Object> map;

    private Address address;

    private Properties properties;

    public Person2() {
    }

    public Person2(Address address) {
        this.address = address;
    }
}
