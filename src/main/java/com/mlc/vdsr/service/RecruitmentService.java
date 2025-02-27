package com.mlc.vdsr.service;

import com.mlc.vdsr.dto.RecruitmentDTO;

import java.util.List;

/**
 * Recruitment service interface.
 */
public interface RecruitmentService {
    /**
     * Returns all recruitments.
     *
     * @return List of RecruitmentDTOs.
     */
    List<RecruitmentDTO> getAllRecruitments();

    /**
     * Create a recruitment.
     *
     * @param recruitmentDTO is the recruitment's DTO.
     */
    void createRecruitment(final RecruitmentDTO recruitmentDTO);

    /**
     * Deletes a recruitment.
     *
     * @param id is the recruitment's id.
     */
    void deleteRecruitment(Long id);

    /**
     * Closes a recruitment.
     *
     * @param id is the recruitment's id.
     */
    void closeRecruitment(Long id);

    /**
     * Opens a recruitment.
     *
     * @param id is the recruitment's id.
     */
    void openRecruitment(Long id);
}
