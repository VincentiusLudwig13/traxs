package com.luviana.traxs.helper;

import org.springframework.context.annotation.Bean;

import java.util.List;

public class SortValidator {

    private static final List<String> ALLOWED_FIELDS = List.of(
            "orderDate",
            "customerName",
            "status"
    );

    public static boolean isValidSort(String sortBy) {
        return ALLOWED_FIELDS.contains(sortBy);
    }
}
