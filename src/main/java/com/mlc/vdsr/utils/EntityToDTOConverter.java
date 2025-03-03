package com.mlc.vdsr.utils;

import com.mlc.vdsr.dto.*;
import com.mlc.vdsr.entity.*;

/**
 * Util class that converts entities to DTOs.
 */
public class EntityToDTOConverter {

    /**
     * Converts User Entity to User DTO.
     *
     * @param user is the user entity.
     *
     * @return UserDTO.
     */
    public static UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());

        return userDTO;
    }

    /**
     * Converts Event Entity to Event DTO.
     *
     * @param event is the event entity.
     *
     * @return EventDTO.
     */
    public static EventDTO converToEventDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setDate(event.getDate());
        eventDTO.setIsImportant(event.getIsImportant());

        return eventDTO;
    }

    /**
     * Converts Recruitment entity to Recruitment DTO.
     *
     * @param recruitment is the Recruitment's entity.
     *
     * @return Recruitment DTO.
     */
    public static RecruitmentDTO convertToRecruitmentDTO(Recruitment recruitment) {
        RecruitmentDTO recruitmentDTO = new RecruitmentDTO();
        recruitmentDTO.setId(recruitment.getId());
        recruitmentDTO.setTitle(recruitment.getTitle());
        recruitmentDTO.setDepartment(recruitment.getDepartment());
        recruitmentDTO.setAvailability(recruitment.getAvailability());
        recruitmentDTO.setOpen(recruitment.getOpen());

        return recruitmentDTO;
    }

    /**
     * Converts Project entity to Project DTO.
     *
     * @param project is the Project's entity.
     *
     * @return Recruitment DTO.
     */
    public static ProjectDTO convertToProjectDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setTitle(project.getTitle());

        return projectDTO;
    }

    /**
     * Converts Member entity to Member DTO.
     *
     * @param member is the Member's entity.
     *
     * @return Member DTO.
     */
    public static MemberDTO convertToMemberDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setFirstName(member.getFirstName());
        memberDTO.setLastName(member.getLastName());
        memberDTO.setDateOfBirth(member.getDateOfBirth());
        memberDTO.setProjects(member.getProjects());

        return memberDTO;
    }
}
