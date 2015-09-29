package com.shuyun.sbd.utils.calculator;

/**
 * Component:
 * Description:
 * Date: 15/7/26
 *
 * @author yue.zhang
 */
public class CalculatorMain {
    public static void main(String [] args){
        Operation operation = OperationFactory.createOperation("+");
        operation.setNumber1(2);
        operation.setNumber2(3);
        System.out.println(operation.getResult());
    }
}
