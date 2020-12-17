import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './jobs-user.reducer';
import { IJobsUser } from 'app/shared/model/jobs-user.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IJobsUserDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const JobsUserDetail = (props: IJobsUserDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { jobsUserEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="jobsOpsToolsApp.jobsUser.detail.title">JobsUser</Translate> [<b>{jobsUserEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="babelUserId">
              <Translate contentKey="jobsOpsToolsApp.jobsUser.babelUserId">Babel User Id</Translate>
            </span>
          </dt>
          <dd>{jobsUserEntity.babelUserId}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="jobsOpsToolsApp.jobsUser.email">Email</Translate>
            </span>
          </dt>
          <dd>{jobsUserEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="jobsOpsToolsApp.jobsUser.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{jobsUserEntity.phone}</dd>
          <dt>
            <span id="permission">
              <Translate contentKey="jobsOpsToolsApp.jobsUser.permission">Permission</Translate>
            </span>
          </dt>
          <dd>{jobsUserEntity.permission}</dd>
        </dl>
        <Button tag={Link} to="/jobs-user" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/jobs-user/${jobsUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ jobsUser }: IRootState) => ({
  jobsUserEntity: jobsUser.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(JobsUserDetail);
