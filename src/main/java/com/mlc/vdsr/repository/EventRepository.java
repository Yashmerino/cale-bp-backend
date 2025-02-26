package com.mlc.vdsr.repository;


import com.mlc.vdsr.entity.Event;
import com.mlc.vdsr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Event JPA repository.
 */
public interface EventRepository extends JpaRepository<Event, Long> {

}
