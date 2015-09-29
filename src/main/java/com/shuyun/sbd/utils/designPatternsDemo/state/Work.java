package com.shuyun.sbd.utils.designPatternsDemo.state;

/**
 * Component:
 * Description:
 * Date: 15/8/8
 *
 * @author yue.zhang
 */
public class Work {

    private State current;

    private double hour;
    private boolean taskFinished;


    public Work(){
        current = new ForenoonState();
    }

    public void writeProgram(){
        current.writeProgram(this);
    }

    public State getCurrent() {
        return current;
    }

    public void setCurrent(State current) {
        this.current = current;
    }

    public double getHour() {
        return hour;
    }

    public void setHour(double hour) {
        this.hour = hour;
    }

    public boolean isTaskFinished() {
        return taskFinished;
    }

    public void setTaskFinished(boolean taskFinished) {
        this.taskFinished = taskFinished;
    }
}
