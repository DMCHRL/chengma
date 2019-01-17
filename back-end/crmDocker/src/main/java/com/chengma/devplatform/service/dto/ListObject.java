package com.chengma.devplatform.service.dto;

import java.util.List;

/**
 * Created by ddgui on 2017/8/30.
 */
public class ListObject {

    private String id;

    private String englishName;

    private List list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
