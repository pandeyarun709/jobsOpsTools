package com.quikr.jobsopstools.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.quikr.jobsopstools.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class JobsUserDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobsUserDTO.class);
        JobsUserDTO jobsUserDTO1 = new JobsUserDTO();
        jobsUserDTO1.setId(1L);
        JobsUserDTO jobsUserDTO2 = new JobsUserDTO();
        assertThat(jobsUserDTO1).isNotEqualTo(jobsUserDTO2);
        jobsUserDTO2.setId(jobsUserDTO1.getId());
        assertThat(jobsUserDTO1).isEqualTo(jobsUserDTO2);
        jobsUserDTO2.setId(2L);
        assertThat(jobsUserDTO1).isNotEqualTo(jobsUserDTO2);
        jobsUserDTO1.setId(null);
        assertThat(jobsUserDTO1).isNotEqualTo(jobsUserDTO2);
    }
}
