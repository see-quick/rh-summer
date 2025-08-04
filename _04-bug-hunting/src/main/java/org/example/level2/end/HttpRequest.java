package org.example.level2.end;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Represents an HTTP request, encapsulating method, path, and path parameters.
 */
public class HttpRequest {
    private String method;
    private String path;
    private Map<String, String> pathParameters = new HashMap<>(); // Map to store path parameters

    /**
     * Constructs a new HttpRequest with a specified method and path.
     *
     * @param method    The HTTP amethod (e.g., GET, POST, DELETE) of the request.
     * @param path      The path of the HTTP request.
     */
    public HttpRequest(String method, String path) {
        this.method = method;
        this.path = path;
    }

    /**
     * Sets the path parameters extracted from the path using a regex matcher.
     * Each group captured by the matcher is added to the pathParameters map with a key indicating its position.
     *
     * @param matcher   A Matcher object containing the results of a match against a path pattern.
     */
    public void setPathParameters(Matcher matcher) {
        for (int i = 1; i <= matcher.groupCount(); i++) {
            pathParameters.put("param" + i, matcher.group(i)); // Can be enhanced to use named groups
        }
    }

    // Getter for the method
    public String getMethod() {
        return method;
    }

    // Getter for the path
    public String getPath() {
        return path;
    }

    // Getter for retrieving a path parameter by key
    public String getPathParameter(String key) {
        return pathParameters.get(key);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
            "method='" + method + '\'' +
            ", path='" + path + '\'' +
            ", pathParameters=" + pathParameters +
            '}';
    }
}
