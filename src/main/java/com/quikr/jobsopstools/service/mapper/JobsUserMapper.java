package com.quikr.jobsopstools.service.mapper;

import com.quikr.jobsopstools.domain.*;
import com.quikr.jobsopstools.service.dto.JobsUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link JobsUser} and its DTO {@link JobsUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JobsUserMapper extends EntityMapper<JobsUserDTO, JobsUser> {
    default JobsUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobsUser jobsUser = new JobsUser();
        jobsUser.setId(id);
        return jobsUser;
    }
}
