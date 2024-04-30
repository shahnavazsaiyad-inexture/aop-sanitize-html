package com.dto;

import com.annotations.SanitizeHtml;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class TestDto implements Serializable {


    private String property1;

    @SanitizeHtml(cleanType = SanitizeType.SIMPLE_TEXT)
    private String property2;


    @SanitizeHtml
    private NestedDto nestedDto;

    @SanitizeHtml
    private List<NestedDto> nestedDtoList;

    @SanitizeHtml
    private Set<NestedDto> nestedDtoSet;

    public Set<NestedDto> getNestedDtoSet() {
        return nestedDtoSet;
    }

    public void setNestedDtoSet(Set<NestedDto> nestedDtoSet) {
        this.nestedDtoSet = nestedDtoSet;
    }

    public NestedDto getNestedDto() {
        return nestedDto;
    }

    public void setNestedDto(NestedDto nestedDto) {
        this.nestedDto = nestedDto;
    }

    public List<NestedDto> getNestedDtoList() {
        return nestedDtoList;
    }

    public void setNestedDtoList(List<NestedDto> nestedDtoList) {
        this.nestedDtoList = nestedDtoList;
    }

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
