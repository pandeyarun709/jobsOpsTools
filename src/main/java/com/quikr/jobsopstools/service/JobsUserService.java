package com.quikr.jobsopstools.service;

import com.quikr.jobsopstools.service.dto.JobsUserDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.quikr.jobsopstools.domain.JobsUser}.
 */
public interface JobsUserService {
    /**
     * Save a jobsUser.
     *
     * @param jobsUserDTO the entity to save.
     * @return the persisted entity.
     */
    JobsUserDTO save(JobsUserDTO jobsUserDTO);

    /**
     * Get all the jobsUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JobsUserDTO> findAll(Pageable pageable);

    /**
     * Get the "id" jobsUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobsUserDTO> findOne(Long id);

    /**
     * Delete the "id" jobsUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
