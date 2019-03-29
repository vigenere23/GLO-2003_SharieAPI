package com.github.glo2003.exceptions;

public class ParameterParsingException extends Exception implements HttpException {

    private static final long serialVersionUID = -7911052375365487019L;

    public ParameterParsingException(String paramName, String paramType) {
        super(String.format("Parameter %s should be of type %s", paramName, paramType));
    }

    @Override
    public int getHttpStatus() {
        return 400;
    }
}
