package com.example.wohnungsuchen.services.filters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FilterAssistant {
    private String query;
    private List<String> parametersList;
}
