package com.quikr.jobsopstools.service.impl;

import com.quikr.jobsopstools.domain.JobsUser;
import com.quikr.jobsopstools.repository.JobsUserRepository;
import com.quikr.jobsopstools.service.JobsUserService;
import com.quikr.jobsopstools.service.dto.JobsUserDTO;
import com.quikr.jobsopstools.service.mapper.JobsUserMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link JobsUser}.
 */
@Service
@Transactional
public class JobsUserServiceImpl implements JobsUserService {
    private final Logger log = LoggerFactory.getLogger(JobsUserServiceImpl.class);

    private final JobsUserRepository jobsUserRepository;

    private final JobsUserMapper jobsUserMapper;

    public JobsUserServiceImpl(JobsUserRepository jobsUserRepository, JobsUserMapper jobsUserMapper) {
        this.jobsUserRepository = jobsUserRepository;
        this.jobsUserMapper = jobsUserMapper;
    }

    @Override
    public JobsUserDTO save(JobsUserDTO jobsUserDTO) {
        log.debug("Request to save JobsUser : {}", jobsUserDTO);
        JobsUser jobsUser = jobsUserMapper.toEntity(jobsUserDTO);
        jobsUser = jobsUserRepository.save(jobsUser);
        return jobsUserMapper.toDto(jobsUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobsUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JobsUsers");
        return jobsUserRepository.findAll(pageable).map(jobsUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JobsUserDTO> findOne(Long id) {
        log.debug("Request to get JobsUser : {}", id);
        return jobsUserRepository.findById(id).map(jobsUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobsUser : {}", id);
        jobsUserRepository.deleteById(id);
    }
}
