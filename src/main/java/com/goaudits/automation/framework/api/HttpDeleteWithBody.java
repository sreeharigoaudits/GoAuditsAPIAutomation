package com.goaudits.automation.framework.api;

import org.apache.http.client.methods.HttpPost;

import java.net.URI;

public class HttpDeleteWithBody extends HttpPost {
    private static final String METHOD_NAME = "DELETE";

    public HttpDeleteWithBody(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    public HttpDeleteWithBody(final URI uri) {
        super();
        setURI(uri);
    }

    public HttpDeleteWithBody() {
        super();
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}