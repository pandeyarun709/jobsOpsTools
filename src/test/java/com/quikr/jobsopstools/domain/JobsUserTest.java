package com.quikr.jobsopstools.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.quikr.jobsopstools.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class JobsUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobsUser.class);
        JobsUser jobsUser1 = new JobsUser();
        jobsUser1.setId(1L);
        JobsUser jobsUser2 = new JobsUser();
        jobsUser2.setId(jobsUser1.getId());
        assertThat(jobsUser1).isEqualTo(jobsUser2);
        jobsUser2.setId(2L);
        assertThat(jobsUser1).isNotEqualTo(jobsUser2);
        jobsUser1.setId(null);
        assertThat(jobsUser1).isNotEqualTo(jobsUser2);
    }
}
