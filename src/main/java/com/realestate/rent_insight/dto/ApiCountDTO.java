package com.realestate.rent_insight.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiCountDTO {
    int totalDeletedCount;
    int totalInsertedCount;
}
