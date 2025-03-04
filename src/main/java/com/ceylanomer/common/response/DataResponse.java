package com.ceylanomer.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Paginated data response")
public class DataResponse<T> {
    
    @Schema(description = "List of items in the current page")
    private List<T> items = new ArrayList<>();
    
    @Schema(description = "Current page number (zero-based)")
    private Integer page;
    
    @Schema(description = "Page size")
    private Integer size;
    
    @Schema(description = "Total number of items")
    private Long totalSize;
    
    @Schema(description = "Total number of pages")
    private Integer totalPage;
    
    public DataResponse() {
    }
    
    public DataResponse(List<T> items) {
        this.items = items;
    }
    
    public DataResponse(List<T> items, Integer page, Integer size, Long totalSize) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalSize = totalSize;
    }
    
    public DataResponse(List<T> items, Integer page, Integer size, Long totalSize, Integer totalPage) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalSize = totalSize;
        this.totalPage = totalPage;
    }
    
    public List<T> getItems() {
        return items;
    }
    
    public Integer getPage() {
        return page;
    }
    
    public Integer getSize() {
        return size;
    }
    
    public Long getTotalSize() {
        return totalSize;
    }
    
    public Integer getTotalPage() {
        return totalPage;
    }
} 