package com.mlc.vdsr.utils;

import com.mlc.vdsr.dto.*;
import com.mlc.vdsr.entity.*;

/**
 * Util class to convert DTOs to entities.
 */
public class DTOToEntityConverter {

    /**
     * Converts User DTO to User Entity.
     *
     * @param userDTO is the User's DTO.
     *
     * @return User entity.
     */
    public static User convertToUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());

        return user;
    }

    /**
     * Converts Event DTO to Event Entity.
     *
     * @param eventDTO is the Event's DTO.
     *
     * @return User entity.
     */
    public static Event convertToEventEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setTitle(eventDTO.getTitle());
        event.setDate(eventDTO.getDate());
        event.setIsImportant(eventDTO.getIsImportant());

        return event;
    }

    /**
     * Converts Recruitment DTO to Recruitment Entity.
     *
     * @param recruitmentDTO is the Recruitment's DTO.
     *
     * @return Recruitment entity.
     */
    public static Recruitment convertToRecruitmentEntity(RecruitmentDTO recruitmentDTO) {
        Recruitment recruitment = new Recruitment();
        recruitment.setId(recruitmentDTO.getId());
        recruitment.setTitle(recruitmentDTO.getTitle());
        recruitment.setDepartment(recruitmentDTO.getDepartment());
        recruitment.setAvailability(recruitmentDTO.getAvailability());
        recruitment.setOpen(recruitmentDTO.getOpen());

        return recruitment;
    }

    /**
     * Converts Project DTO to Project Entity.
     *
     * @param projectDTO is the Project's DTO.
     *
     * @return Recruitment entity.
     */
    public static Project convertToProjectEntity(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setTitle(projectDTO.getTitle());

        return project;
    }

    /**
     * Converts Member DTO to Member Entity.
     *
     * @param memberDTO is the Member's DTO.
     *
     * @return Member entity.
     */
    public static Member convertToMemberEntity(MemberDTO memberDTO) {
        Member member = new Member();
        member.setFirstName(memberDTO.getFirstName());
        member.setLastName(memberDTO.getLastName());
        member.setDateOfBirth(memberDTO.getDateOfBirth());
        member.setProjects(memberDTO.getProjects());

        return member;
    }
}
