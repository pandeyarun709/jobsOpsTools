package com.quikr.jobsopstools.repository;

import com.quikr.jobsopstools.domain.JobsUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the JobsUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobsUserRepository extends JpaRepository<JobsUser, Long> {}
