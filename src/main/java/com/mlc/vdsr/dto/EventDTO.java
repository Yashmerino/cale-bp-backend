package com.mlc.vdsr.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

/**
 * Event DTO class.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    /**
     * Event's ID.
     */
    private Long id;

    /**
     * Event's title
     */
    @NotNull(message = "title_is_required")
    @NotBlank(message = "title_is_required")
    @Size(max = 64, message = "title_length_invalid")
    @Size(max = 64, min = 1, message = "title_length_invalid")
    private String title;

    /**
     * Event's date.
     */
    @NotNull(message = "date_is_required")
    private Instant date;

    /**
     * Is event important or no.
     */
    @Column(nullable = false)
    private Boolean isImportant = false;
}
