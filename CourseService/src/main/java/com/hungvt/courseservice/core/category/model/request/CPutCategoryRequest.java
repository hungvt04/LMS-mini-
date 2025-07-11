package com.hungvt.courseservice.core.category.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CPutCategoryRequest {

    private String categoryCode;

    private String categoryName;

    private String description;

}
