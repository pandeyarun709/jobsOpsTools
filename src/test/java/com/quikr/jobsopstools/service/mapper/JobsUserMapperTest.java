package com.quikr.jobsopstools.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JobsUserMapperTest {
    private JobsUserMapper jobsUserMapper;

    @BeforeEach
    public void setUp() {
        jobsUserMapper = new JobsUserMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(jobsUserMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(jobsUserMapper.fromId(null)).isNull();
    }
}
