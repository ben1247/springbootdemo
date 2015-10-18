package impl;

import interfaces.IBook;

/**
 * Component:
 * Description:
 * Date: 15/10/15
 *
 * @author yue.zhang
 */
public class LinuxBook implements IBook {

    @Override
    public void printBookName() {
        System.out.println("Linux Book");
    }
}
