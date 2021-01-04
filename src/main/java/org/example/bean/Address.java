package org.example.bean;

import lombok.Data;

/**
 * @Author qiu
 * @Date 2021/1/5 0:43
 */
@Data
public class Address {
    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 城镇
     */
    private String town;

    public Address() {
        super();
        System.out.println("Address is create");
    }
}
