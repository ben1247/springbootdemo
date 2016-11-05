package com.shuyun.sbd.utils.guava.comparisonChain;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

/**
 * Component:
 * Description:
 * Date: 16/10/23
 *
 * @author yue.zhang
 */
public class ComparisonChainObj {

    private Integer id;

    private String name;

    private String content;

    public ComparisonChainObj(Integer id, String name,String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int compareTo(ComparisonChainObj that) {
        return ComparisonChain.start()
                .compare(this.id, that.id)
                .compare(this.name, that.name)
                .compare(this.content, that.content)
                .result();
    }

}
