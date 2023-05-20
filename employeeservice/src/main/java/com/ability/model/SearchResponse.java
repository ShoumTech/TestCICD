package com.ability.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(Include. NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SearchResponse {

    private Integer totalPages;
    private Long totalCount;
    private Integer pageNo;
    private String totalNumberOfUnReadEmployees;

    private List<SearchData> dataList;

}
