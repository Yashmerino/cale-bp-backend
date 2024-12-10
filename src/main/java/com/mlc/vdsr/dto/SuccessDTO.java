package com.mlc.vdsr.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for success response returned in controllers.
 */
@Getter
@Setter
public class SuccessDTO {

    /**
     * Creates a new SuccessDTO with given status and message.
     *
     * @param status is the response's status.
     * @param message is the response's message.
     *
     * @return newSuccessDTO.
     */
    public static SuccessDTO returnNewDTO(int status, String message) {
        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setStatus(status);
        successDTO.setMessage(message);

        return successDTO;
    }

    /**
     * Status of the response.
     */
    private int status;

    /**
     * Message for the response.
     */
    private String message;
}
