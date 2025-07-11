package com.hungvt.courseservice.core.category.model.response;

import com.hungvt.courseservice.entity.base.IsIdentified;

public interface CCategoryResponse extends IsIdentified {

    String getCategoryCode();

    String getCategoryName();

    String getDescription();

    Boolean getIsDeleted();

}
