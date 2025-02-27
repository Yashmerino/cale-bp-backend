package com.mlc.vdsr.service.impl;

import com.mlc.vdsr.dto.RecruitmentDTO;
import com.mlc.vdsr.entity.Event;
import com.mlc.vdsr.entity.Recruitment;
import com.mlc.vdsr.exception.EventNotFoundException;
import com.mlc.vdsr.exception.RecruitmentNotFoundException;
import com.mlc.vdsr.repository.RecruitmentRepository;
import com.mlc.vdsr.service.RecruitmentService;
import com.mlc.vdsr.utils.DTOToEntityConverter;
import com.mlc.vdsr.utils.EntityToDTOConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Recruitment service.
 */
@Service
public class RecruitmentServiceImpl implements RecruitmentService {

    /**
     * Recruitment's repository.
     */
    private final RecruitmentRepository recruitmentRepository;

    /**
     * Constructor.
     *
     * @param recruitmentRepository is the recruitment's repository.
     */
    public RecruitmentServiceImpl(RecruitmentRepository recruitmentRepository) {
        this.recruitmentRepository = recruitmentRepository;
    }

    /**
     * Returns all recruitments.
     *
     * @return List of RecruitmentDTOs.
     */
    public List<RecruitmentDTO> getAllRecruitments() {
        List<Recruitment> recruitments = this.recruitmentRepository.findAll();

        List<RecruitmentDTO> recruitmentDTOS = new ArrayList<>();
        for (Recruitment recruitment : recruitments) {
            recruitmentDTOS.add(EntityToDTOConverter.convertToRecruitmentEntity(recruitment));
        }

        return recruitmentDTOS;
    }

    /**
     * Create a recruitment.
     *
     * @param recruitmentDTO is the recruitment's DTO.
     */
    public void createRecruitment(final RecruitmentDTO recruitmentDTO) {
        Recruitment recruitment = DTOToEntityConverter.convertToRecruitmentEntity(recruitmentDTO);

        this.recruitmentRepository.save(recruitment);
    }

    /**
     * Deletes a recruitment.
     *
     * @param id is the recruitment's id.
     */
    public void deleteRecruitment(Long id) {
        Recruitment recruitment = this.recruitmentRepository.findById(id)
                .orElseThrow(RecruitmentNotFoundException::new);

        this.recruitmentRepository.delete(recruitment);
    }

    /**
     * Closes a recruitment.
     *
     * @param id is the recruitment's id.
     */
    public void closeRecruitment(Long id) {
        Recruitment recruitment = this.recruitmentRepository.findById(id)
                .orElseThrow(RecruitmentNotFoundException::new);

        recruitment.setOpen(false);

        this.recruitmentRepository.save(recruitment);
    }

    /**
     * Opens a recruitment.
     *
     * @param id is the recruitment's id.
     */
    public void openRecruitment(Long id) {
        Recruitment recruitment = this.recruitmentRepository.findById(id)
                .orElseThrow(RecruitmentNotFoundException::new);

        recruitment.setOpen(true);

        this.recruitmentRepository.save(recruitment);
    }

}
