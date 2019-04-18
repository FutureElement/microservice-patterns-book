package com.ms.zg.book.tdd;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

//计算器测试类
public class CalculatorTest {
    //声明一个计算器
    private Calculator calculator;

    //使用Junit的Before方法在测试之前new一个计算器实例
    @Before
    public void before() {
        calculator = new Calculator();
    }

    @Test
    public void when_add_1_and_1_should_return_2() {
        //调用计算器的add方法
        int result = calculator.add(1, 1);
        //断言计算结果是否符合预期
        assertThat(result).isEqualTo(2);
    }

    //测试更多的场景，一般会增加一些边界值的测试，尽量是测试失败
    @Test
    public void when_add_1_and_2_should_return_3() {
        int result = calculator.add(1, 2);

        assertThat(result).isEqualTo(3);
    }

    @Test
    public void when_add_3_and_2_should_return_5() {
        int result = calculator.add(3, 2);
        assertThat(result).isEqualTo(5);
    }


}

