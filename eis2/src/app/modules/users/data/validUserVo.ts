import { OrganizationVo } from '../../../data/organization-vo';

export class ValidUserVo{
    id: number = 0;
    loginName:String = '';
	firstName:String = '';
	lastName:String = '';
	lastLoginDate?:Date;
	homeUnitVo:OrganizationVo = null;
	primaryDispatchCenterVo:OrganizationVo = null;
    password:String = ''; // password used to login to the system (this is hashed)
    enteredPassword:String = ''; // password entered when changing password
    desiredPassword:String = ''; // new password desired
    confirmPassword:String = ''; // confirmation of desired password
	resetPassword?:Boolean;
	showDataSavedMsg?:Boolean;
	email:String = '';
	eauthId?:String = '';
	workPhone?:String = '';
	cellPhone?:String = '';
	lockedDate:Date = null;
	failedLoginAttempts:number = 0;
	accountExpirationDate?:Date;
	passwordCreatedDate?:Date;
	enabled?:Boolean;
}