package com.pwr.bzapps.plwordnetmobile.service.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response<T> {
    @JsonProperty("content")
    private T content;

    public Response(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
