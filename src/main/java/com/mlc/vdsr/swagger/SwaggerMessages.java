package com.mlc.vdsr.swagger;

/**
 * Messages displayed in the swagger UI.
 */
public class SwaggerMessages {

    /**
     * Private constructor to now allow instantiation.
     */
    private SwaggerMessages() {

    }

    /**
     * Message when a problem occurred on the server.
     */
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error.";

    /**
     * Message when the request is bad.
     */
    public static final String BAD_REQUEST = "Bad Request.";

    /**
     * Message when the endpoint is forbidden.
     */
    public static final String FORBIDDEN = "You have no access to this endpoint!";

    /**
     * Message when the user isn't authorized.
     */
    public static final String UNAUTHORIZED = "You are not authorized!";

    /**
     * Message when a user does not exist.
     */
    public static final String USER_DOES_NOT_EXIST = "User doesn't exist.";

    /**
     * Message when operation was performed successfully.
     */
    public static final String SUCCESS = "Success.";
}