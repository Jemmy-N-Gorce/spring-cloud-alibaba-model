package com.atguigu;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

public class test {


    public static void main(String[] args)
    {
        ZonedDateTime zbj = ZonedDateTime.now(); // 默认时区
        System.out.println(zbj);
    }
}
