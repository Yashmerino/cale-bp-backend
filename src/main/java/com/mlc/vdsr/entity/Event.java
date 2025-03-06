package com.mlc.vdsr.entity;

import com.mlc.vdsr.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Event entity class.
 */
@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event extends BaseEntity {

    /**
     * Event's title
     */
    @NotNull(message = "title_is_required")
    @NotBlank(message = "title_is_required")
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
    @NotNull(message = "importance_is_required")
    private Boolean isImportant = false;
}
