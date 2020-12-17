import { Permission } from 'app/shared/model/enumerations/permission.model';

export interface IJobsUser {
  id?: number;
  babelUserId?: number;
  email?: string;
  phone?: number;
  permission?: Permission;
}

export const defaultValue: Readonly<IJobsUser> = {};
