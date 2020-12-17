import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import JobsUser from './jobs-user';
import JobsUserDetail from './jobs-user-detail';
import JobsUserUpdate from './jobs-user-update';
import JobsUserDeleteDialog from './jobs-user-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={JobsUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={JobsUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={JobsUserDetail} />
      <ErrorBoundaryRoute path={match.url} component={JobsUser} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={JobsUserDeleteDialog} />
  </>
);

export default Routes;
