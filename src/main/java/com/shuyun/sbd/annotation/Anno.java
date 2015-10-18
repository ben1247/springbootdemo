package com.shuyun.sbd.annotation;

/**
 * Component:
 * Description:
 * Date: 15/10/17
 *
 * @author yue.zhang
 */
@FirstAnnotation("override first annotation")
public class Anno {

    @ThirdAnnotation("override third annotation")
    private String fieldTest;

    private String noAnnotationField;

    @SecondAnnotation()
    public String getDefault() {
        return "get default Annotation";
    }

    @SecondAnnotation(name="baidu",url="www.baidu.com")
    public String getDefine() {
        return "get define Annotation";
    }
}
