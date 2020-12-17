package com.quikr.jobsopstools.web.rest;

import com.quikr.jobsopstools.service.JobsUserService;
import com.quikr.jobsopstools.service.dto.JobsUserDTO;
import com.quikr.jobsopstools.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.quikr.jobsopstools.domain.JobsUser}.
 */
@RestController
@RequestMapping("/api")
public class JobsUserResource {
    private final Logger log = LoggerFactory.getLogger(JobsUserResource.class);

    private static final String ENTITY_NAME = "jobsUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobsUserService jobsUserService;

    public JobsUserResource(JobsUserService jobsUserService) {
        this.jobsUserService = jobsUserService;
    }

    /**
     * {@code POST  /jobs-users} : Create a new jobsUser.
     *
     * @param jobsUserDTO the jobsUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobsUserDTO, or with status {@code 400 (Bad Request)} if the jobsUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jobs-users")
    public ResponseEntity<JobsUserDTO> createJobsUser(@Valid @RequestBody JobsUserDTO jobsUserDTO) throws URISyntaxException {
        log.debug("REST request to save JobsUser : {}", jobsUserDTO);
        if (jobsUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new jobsUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobsUserDTO result = jobsUserService.save(jobsUserDTO);
        return ResponseEntity
            .created(new URI("/api/jobs-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jobs-users} : Updates an existing jobsUser.
     *
     * @param jobsUserDTO the jobsUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobsUserDTO,
     * or with status {@code 400 (Bad Request)} if the jobsUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobsUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jobs-users")
    public ResponseEntity<JobsUserDTO> updateJobsUser(@Valid @RequestBody JobsUserDTO jobsUserDTO) throws URISyntaxException {
        log.debug("REST request to update JobsUser : {}", jobsUserDTO);
        if (jobsUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobsUserDTO result = jobsUserService.save(jobsUserDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobsUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /jobs-users} : get all the jobsUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobsUsers in body.
     */
    @GetMapping("/jobs-users")
    public ResponseEntity<List<JobsUserDTO>> getAllJobsUsers(Pageable pageable) {
        log.debug("REST request to get a page of JobsUsers");
        Page<JobsUserDTO> page = jobsUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /jobs-users/:id} : get the "id" jobsUser.
     *
     * @param id the id of the jobsUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobsUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jobs-users/{id}")
    public ResponseEntity<JobsUserDTO> getJobsUser(@PathVariable Long id) {
        log.debug("REST request to get JobsUser : {}", id);
        Optional<JobsUserDTO> jobsUserDTO = jobsUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobsUserDTO);
    }

    /**
     * {@code DELETE  /jobs-users/:id} : delete the "id" jobsUser.
     *
     * @param id the id of the jobsUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jobs-users/{id}")
    public ResponseEntity<Void> deleteJobsUser(@PathVariable Long id) {
        log.debug("REST request to delete JobsUser : {}", id);
        jobsUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
