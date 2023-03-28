package ru.tinkoff.edu.java.scrapper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.List;
@Data
@AllArgsConstructor
public class ApiErrorResponse{
    private String description;
    private @NonNull String code;
    private String exceptionName;
    private String exceptionMessage;
    private List<String> stacktrace;

}
