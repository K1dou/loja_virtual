package com.K1dou.Loja.virtual.model.Dtos;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObjetoRetornoCobrancaDTO {

    private String object;
    private Boolean hasMore;
    private Integer totalCount;
    private Integer limit;
    private Integer offset;
    private List<DataRetornoCobrancaDTO> data = new ArrayList<>();

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public List<DataRetornoCobrancaDTO> getData() {
        return data;
    }

    public void setData(List<DataRetornoCobrancaDTO> data) {
        this.data = data;
    }
}
