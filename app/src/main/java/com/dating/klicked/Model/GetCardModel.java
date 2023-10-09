package com.dating.klicked.Model;

import com.dating.klicked.Model.ResponseRepo.CardResponse;

import java.util.List;

public class GetCardModel {
    String name;
    List<CardResponse> list = null;

    public GetCardModel(String name, List<CardResponse> list) {
        this.name = name;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CardResponse> getList() {
        return list;
    }

    public void setList(List<CardResponse> list) {
        this.list = list;
    }
}
