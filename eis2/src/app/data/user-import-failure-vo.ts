
import { AbstractVo } from './abstract-vo';
import { SystemRoleVo } from './system-role-vo';
import { OrganizationVo } from './organization-vo';

export interface UserImportFailureVo extends AbstractVo {
    confirmDefaultPassword: string;
    confirmPassword: string;
    defaultPassword: string;
    failureReason: string;
    firstName: string;
    homeUnitVo: OrganizationVo;
    homeUnitCode: string;
    lastName: string;
    loginName: string;
    password: string;
    primaryDispatchCenterVo: OrganizationVo;
    pdcUnitCode: string;
    roleVos: SystemRoleVo[];
    isPrivilegedUser: boolean;
}
