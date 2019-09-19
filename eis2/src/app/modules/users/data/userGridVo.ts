import { SystemRoleVo } from './systemRoleVo';

export class UserGridVo {
   id: number = 0;
   loginName: string = '';
   firstName: string = '';
   lastName: string = '';
   homeUnitId: number = 0;
   homeUnitCode: string = '';
   homeUnitName: string = '';
   pdcUnitId: number = 0;
   pdcUnitCode: string = '';
   pdcUnitName: string = '';
   createdDate: any = '';
   enabled: boolean = true;
   deletable: boolean = false;
   userRoles: SystemRoleVo[] = [{}];  
   roleNames: string = '';  
  }
 