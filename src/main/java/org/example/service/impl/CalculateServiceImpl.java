package org.example.service.impl;

import org.example.service.CalculateService;
import org.springframework.stereotype.Service;

/**
 * @Author qiu
 * @Date 2021/1/8 23:29
 */
@Service
public class CalculateServiceImpl implements CalculateService {

    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int sub(int a, int b) {
        return a - b;
    }

    @Override
    public int mul(int a, int b) {
        return a * b;
    }

    @Override
    public int div(int a, int b) {
        return a / b;
    }
}
