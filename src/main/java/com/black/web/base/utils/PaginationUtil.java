package com.black.web.base.utils;

import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.black.web.base.bean.BaseModel;

public class PaginationUtil implements Serializable {

    private static final long serialVersionUID = -4011041220069718198L;

    private JsonElement data;

    private int draw;

    private long count;

    private long recordsTotal;

    private long recordsFiltered;

    private List<? extends BaseModel> list;

    public PaginationUtil(List<? extends BaseModel> list, Long count) {
        this.list = list;
        this.count = count;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    private void init() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        JsonParser jp = new JsonParser();
        this.data = jp.parse(gson.toJson(list));
        this.recordsTotal = count;
        this.recordsFiltered = count;
    }

    public String page() {
        init();
        JsonObject result = new JsonObject();
        result.add("data", this.data);
        result.addProperty("draw", this.draw);
        result.addProperty("recordsTotal", this.recordsTotal);
        result.addProperty("recordsFiltered", this.recordsFiltered);
        return result.toString();
    }

}
