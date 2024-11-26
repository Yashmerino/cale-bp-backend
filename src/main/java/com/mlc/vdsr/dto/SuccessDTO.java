package com.mlc.vdsr.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for success response returned in controllers.
 */
@Getter
@Setter
public class SuccessDTO {
    public static SuccessDTO returnNewDTO(int status, String message) {
        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setStatus(status);
        successDTO.setMessage(message);

        return successDTO;
    }

    private int status;
    private String message;
}
