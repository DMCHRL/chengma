package com.suitong.devplatform.service.dto;

import java.util.List;

/**
 * Created by ddgui on 2017/8/30.
 */
public class ListObject {

    private Long id;

    private String englishName;

    private List list;

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
