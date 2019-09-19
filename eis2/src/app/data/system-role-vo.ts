import { AbstractVo } from './abstract-vo';

export interface SystemRoleVo extends AbstractVo {
    roleName: string;
    displayName: string;
    privilegedRole: boolean;
  }
