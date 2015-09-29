package com.shuyun.sbd.utils.designPatternsDemo.observer.v1;

/**
 * Component: 具体通知者
 * Description:
 * Date: 15/8/6
 *
 * @author yue.zhang
 */
public class ConcreteSubject extends Subject {

    private String subjectState;

    public String getSubjectState() {
        return subjectState;
    }

    public void setSubjectState(String subjectState) {
        this.subjectState = subjectState;
    }
}
