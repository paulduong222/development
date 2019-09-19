import { AbstractVo } from './abstract-vo';
import { IncidentVo } from './incident-vo';
import { UserVo } from './user-vo';
import { SystemRoleVo } from './system-role-vo';

export interface RestrictedIncidentUserVo extends AbstractVo {
    incidentVo: IncidentVo;
    userVo: UserVo;
    userType: string;
    accessGrantedBy: string;
    accessEndDate: Date;
    userRoleVos: SystemRoleVo[];
    defaultCheckinDate: Date;
    defaultCheckinType: string;
}
