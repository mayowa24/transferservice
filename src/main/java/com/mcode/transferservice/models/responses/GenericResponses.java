package com.mcode.transferservice.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GenericResponses<T> {
    private boolean isRequestSuccessful;
    private String message;
    private T data;
}
