package br.com.dev.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class PageableResponse<T> extends PageImpl {
    private boolean last;
    private boolean first;
    private int totalPages;
    public PageableResponse(@JsonProperty("content") List content,
                            @JsonProperty("number") int page,
                            @JsonProperty("size") int size,
                            @JsonProperty("pageable") JsonNode pageable,
                            @JsonProperty("totalElements") long totalElements,
                            @JsonProperty("sort") JsonNode sort) {

        super(content, PageRequest.of(page,size), totalElements);
    }

    public PageableResponse(){
        super(new ArrayList<>());
    }

    @Override
    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    @Override
    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
