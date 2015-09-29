package com.shuyun.sbd.utils.calculator;

/**
 * Component:
 * Description:
 * Date: 15/7/26
 *
 * @author yue.zhang
 */
public class OperationFactory {

    public static Operation createOperation(String operate){
        Operation operation = null;
        switch (operate.charAt(0)){
            case '+' :
                operation = new AddOperation();
                break;
            case '-' :
                operation = new SubOperation();
                break;
            default:
                break;
        }

        return operation;
    }

}
