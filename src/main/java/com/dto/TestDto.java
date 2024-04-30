package com.dto;

import com.annotations.SanitizeHtml;

import java.io.Serializable;

public class TestDto implements Serializable {


    private String property1;

    @SanitizeHtml(cleanType = SanitizeType.SIMPLE_TEXT)
    private String property2;

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    @Override
    public String toString() {
        return "TestDto{" +
                "property1='" + property1 + '\'' +
                ", property2='" + property2 + '\'' +
                '}';
    }
}
