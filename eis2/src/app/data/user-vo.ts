import { AbstractVo } from './abstract-vo';
import { SystemRoleVo } from './system-role-vo';
import { OrganizationVo } from './organization-vo';

export interface UserVo extends AbstractVo {
    loginName: string;
    firstName: string;
    lastName: string;
    lastLoginDate: object;
    homeUnitVo: OrganizationVo;
    primaryDispatchCenterVo: OrganizationVo;
    password: string;
    enteredPassword: string;
    desiredPassword: string;
    confirmPassword: string;
    resetPassword: boolean;
    showDataSavedMsg: boolean;
    email: string;
    eauthId: string;
    workPhone: string;
    cellPhone: string;
    lockedDate: Date;
    failedLoginAttempts: number;
    accountExpirationDate: Date;
    passwordCreatedDate: Date;
    enabled: boolean;
    userRoleVos: SystemRoleVo[];
    dateOfLastPasswordChange: Date;
    adminUser: boolean;
    dbname: string;
    dbpwd: string;
    dbconfirmpwd: string;
    dbinitial: string;
}
