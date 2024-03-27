package com.dobe.locmns.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OperationNonPermittedException extends RuntimeException{
    private final String errorMsg;
    private final String operationId;
    private final String source;
    private final String dependency;

}
