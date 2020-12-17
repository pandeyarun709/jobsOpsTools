import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction,
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IJobsUser, defaultValue } from 'app/shared/model/jobs-user.model';

export const ACTION_TYPES = {
  FETCH_JOBSUSER_LIST: 'jobsUser/FETCH_JOBSUSER_LIST',
  FETCH_JOBSUSER: 'jobsUser/FETCH_JOBSUSER',
  CREATE_JOBSUSER: 'jobsUser/CREATE_JOBSUSER',
  UPDATE_JOBSUSER: 'jobsUser/UPDATE_JOBSUSER',
  DELETE_JOBSUSER: 'jobsUser/DELETE_JOBSUSER',
  RESET: 'jobsUser/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IJobsUser>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type JobsUserState = Readonly<typeof initialState>;

// Reducer

export default (state: JobsUserState = initialState, action): JobsUserState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_JOBSUSER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_JOBSUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_JOBSUSER):
    case REQUEST(ACTION_TYPES.UPDATE_JOBSUSER):
    case REQUEST(ACTION_TYPES.DELETE_JOBSUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_JOBSUSER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_JOBSUSER):
    case FAILURE(ACTION_TYPES.CREATE_JOBSUSER):
    case FAILURE(ACTION_TYPES.UPDATE_JOBSUSER):
    case FAILURE(ACTION_TYPES.DELETE_JOBSUSER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_JOBSUSER_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_JOBSUSER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_JOBSUSER):
    case SUCCESS(ACTION_TYPES.UPDATE_JOBSUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_JOBSUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/jobs-users';

// Actions

export const getEntities: ICrudGetAllAction<IJobsUser> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_JOBSUSER_LIST,
    payload: axios.get<IJobsUser>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IJobsUser> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_JOBSUSER,
    payload: axios.get<IJobsUser>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IJobsUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_JOBSUSER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IJobsUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_JOBSUSER,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IJobsUser> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_JOBSUSER,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
