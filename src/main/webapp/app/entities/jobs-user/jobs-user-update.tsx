import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './jobs-user.reducer';
import { IJobsUser } from 'app/shared/model/jobs-user.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IJobsUserUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const JobsUserUpdate = (props: IJobsUserUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { jobsUserEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/jobs-user');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...jobsUserEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jobsOpsToolsApp.jobsUser.home.createOrEditLabel">
            <Translate contentKey="jobsOpsToolsApp.jobsUser.home.createOrEditLabel">Create or edit a JobsUser</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : jobsUserEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="jobs-user-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="jobs-user-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="babelUserIdLabel" for="jobs-user-babelUserId">
                  <Translate contentKey="jobsOpsToolsApp.jobsUser.babelUserId">Babel User Id</Translate>
                </Label>
                <AvField id="jobs-user-babelUserId" type="string" className="form-control" name="babelUserId" />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="jobs-user-email">
                  <Translate contentKey="jobsOpsToolsApp.jobsUser.email">Email</Translate>
                </Label>
                <AvField
                  id="jobs-user-email"
                  type="text"
                  name="email"
                  validate={{
                    pattern: { value: '^(.+)@(.+)$', errorMessage: translate('entity.validation.pattern', { pattern: '^(.+)@(.+)$' }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="phoneLabel" for="jobs-user-phone">
                  <Translate contentKey="jobsOpsToolsApp.jobsUser.phone">Phone</Translate>
                </Label>
                <AvField
                  id="jobs-user-phone"
                  type="string"
                  className="form-control"
                  name="phone"
                  validate={{
                    min: { value: 1000000000, errorMessage: translate('entity.validation.min', { min: 1000000000 }) },
                    max: { value: 9999999999, errorMessage: translate('entity.validation.max', { max: 9999999999 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="permissionLabel" for="jobs-user-permission">
                  <Translate contentKey="jobsOpsToolsApp.jobsUser.permission">Permission</Translate>
                </Label>
                <AvInput
                  id="jobs-user-permission"
                  type="select"
                  className="form-control"
                  name="permission"
                  value={(!isNew && jobsUserEntity.permission) || 'ADMIN_ACCESS'}
                >
                  <option value="ADMIN_ACCESS">{translate('jobsOpsToolsApp.Permission.ADMIN_ACCESS')}</option>
                  <option value="VIEW_ONLY_ACCESS">{translate('jobsOpsToolsApp.Permission.VIEW_ONLY_ACCESS')}</option>
                  <option value="WRITE_ACCESS">{translate('jobsOpsToolsApp.Permission.WRITE_ACCESS')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/jobs-user" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  jobsUserEntity: storeState.jobsUser.entity,
  loading: storeState.jobsUser.loading,
  updating: storeState.jobsUser.updating,
  updateSuccess: storeState.jobsUser.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(JobsUserUpdate);
