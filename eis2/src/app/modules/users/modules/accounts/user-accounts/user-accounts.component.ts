import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { UserData } from '../../../../../data/user-data';
import { UserService } from '../../../../../service/user.service';
import { NotificationService } from 'src/app/service/notification-service';
import { Subject } from 'rxjs';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { UserAccountFormComponent } from './user-account-form/user-account-form.component';
import { DropdownData } from 'src/app/data/dropdowndata';
import { SystemService } from 'src/app/service/system.service';
import { SystemRoleData } from 'src/app/data/system-role-data';
import { UserVo } from 'src/app/data/user-vo';
import { RowDataTransaction } from 'ag-grid-community/dist/lib/rowModels/clientSide/clientSideRowModel';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { OrganizationVo } from 'src/app/data/organization-vo';
import { HeightCalc } from '../../../../../height-calc';

@Component({
  selector: 'app-user-accounts',
  templateUrl: './user-accounts.component.html',
  styleUrls: ['./user-accounts.component.css']
})
export class UserAccountsComponent implements OnInit {
  userList = [];
  userSelected = false;
  userVo: UserVo;
  splitAreaLeftSize = 30;
  splitAreaRightSize = 70;
  promptMode = '';
  @ViewChild('promptModal') promptModal: PromptModalComponent;
  @ViewChild('userGrid') userGrid: EisGridComponent;
  @ViewChild(UserAccountFormComponent) userFormComponent: UserAccountFormComponent;
  gridColumnDefs = [
    {headerName: 'User Name', field: 'loginName', width: 135, sort: 'asc'},
    {headerName: 'First Name', field: 'firstName', width: 135},
    {headerName: 'Last Name', field: 'lastName', width: 135},
    {headerName: 'Unit ID', field: 'homeUnitCode', width: 135},
    {headerName: 'Roles', field: 'roleNames', filter: false, width: 400},
    {headerName: 'Created', field: 'createdDate', width: 135},
    {headerName: 'Enabled', field: 'enabled', filter: false, cellRenderer: 'checkboxRenderer'}
  ];

  calcHt(){
    return HeightCalc.calculateHeight('useracct');
  }

  constructor(private userService: UserService
              , private systemService: SystemService
              , private refDataService: ReferenceDataService
              , private notifyService: NotificationService) { }

  ngOnInit() {
    this.loadOrgs();
  }

  loadOrgs(){
    this.refDataService.getStandardOrgList()
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
          this.userFormComponent.organizationVos = data['recordset'] as OrganizationVo[];
          // this.userFormComponent.orgList = data['recordset'] as any[];
          for ( let i in data['recordset'] ) {
            var recordSet = data['recordset'][i];
            this.userFormComponent.orgList.push(
              {
                id: recordSet.id,
                code: recordSet.unitCode,
                desc: recordSet.name,
                adddata: recordSet.dispatchCenters
              });
          }
          /*
          this.userFormComponent.orgList.forEach(element => {
              this.userFormComponent.orgList2.push(
                {id: element['id']
                  , code: element['unitCode']
                , desc: element['name']}
              );
          });
          */
          this.loadUsers();
        }
    });
  }

  clearFilter() {
    this.userGrid.clearFilters();
  }

  loadUsers() {
    this.userGrid.clearSelected();
    this.userSelected = false;
    this.userFormComponent.prepareUser();
    this.userService.getUserList()
    .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
          this.userList = data['recordset'] as any[];
          this.userList.forEach(row => {
            let d = new Date(row['createdDate']);
            row.createdDate = d.toLocaleString('en-US', { hour12: false });
          });
         }
    });
  }

  onSelectUser(user: any) {
    if ( user !== undefined ) {
      const userId = user['id'];
      this.getUserById(userId);
    }
  }

  getUserById( userId ) {
    this.userService.getById(userId).subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_BY_ID_USER_ACCOUNT'
             && data['courseOfActionVo']['coaType'] === 'HANDLE_RESULT_OBJECT' ) {
          this.userFormComponent.userVo = data['resultObject'] as UserVo;
          this.userFormComponent.populateForm();
          this.userSelected = true;
      }
    });
  }

  addUser() {
    this.userGrid.clearSelected();
    this.userSelected = false;
    this.userFormComponent.prepareUser();
    if ( this.splitAreaLeftSize > 30 ) {
      this.splitAreaLeftSize = 30;
      this.splitAreaRightSize = 70;
    }
  }

  editUser() {
    if ( this.userSelected === true ) {
      if ( this.splitAreaLeftSize > 30 ) {
        this.splitAreaLeftSize = 30;
        this.splitAreaRightSize = 70;
      }
        this.getUserById(this.userFormComponent.userVo.id);
    } else {
      this.promptMode = 'SelectUserPrompt';
      this.promptModal.reset();
      this.promptModal.promptTitle = 'Users';
      this.promptModal.promptMessage1 = 'Please select a user to edit.';
      this.promptModal.button1Label = 'Ok';
      this.promptModal.openModal();
    }
  }

  deleteUser() {
    if ( this.userSelected === true ) {
      this.promptMode = 'DeletePrompt';
      this.promptModal.reset();
      this.promptModal.promptTitle = 'Users';
      this.promptModal.promptMessage1 = 'Do you really want to remove the User Account ' + this.userFormComponent.userVo.loginName + '?';
      this.promptModal.button1Label = 'Yes';
      this.promptModal.button2Label = 'No';
      this.promptModal.openModal();
    } else {
      this.promptMode = 'SelectUserPrompt';
      this.promptModal.reset();
      this.promptModal.promptTitle = 'Users';
      this.promptModal.promptMessage1 = 'Please select a user to delete.';
      this.promptModal.button1Label = 'Ok';
      this.promptModal.openModal();
    }
  }

  doUserDelete() {
    this.promptModal.reset();
    this.promptModal.promptTitle = 'Processing';
    this.promptModal.promptMessage1 = 'Removing User Account...';
    this.promptModal.openModal();
    this.userService.deleteUser(this.userFormComponent.userVo.id)
    .subscribe(data => {
      this.promptModal.closeModal();
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'DELETE_USER_COMPLETE') {
        this.userGrid.removeSelectedRows();
        const t = this.userList.findIndex(row => row['id'] === this.userFormComponent.userVo.id );
        if ( t > -1 ) {
          this.userGrid.removeSelectedRows();
          // const selectedData = this.userGrid.gridOptions.api.getSelectedRows();
          // this.userGrid.gridOptions.api.updateRowData({ remove: selectedData });
          const rowToDelete = this.userList.splice(t , 1 );
          this.addUser();
        }
        this.userGrid.clearSelected();
      }
    });
  }

  userProcessingEvent(evt) {
    if (evt['action'] === 'msg') {
      this.promptModal.reset();
      this.promptModal.promptTitle = 'Processing';
      this.promptModal.promptMessage1 = evt['msg'];
      this.promptModal.openModal();
    } else if (evt['action'] === 'close') {
      this.promptModal.closeModal();
    }
  }

  promptActionResult(evt: any) {
    this.promptModal.closeModal();
    if ( this.promptMode === 'DeletePrompt') {
      if (evt === 'Yes') {
        this.doUserDelete();
      }
     } else {
     }
   }

   save(updatedVo: any) {
    this.userGrid.updateRowById(updatedVo);
    const sort = [
      {
        colId: 'User Name',
        sort: 'asc'
      }
    ];
    this.userGrid.sortGrid(sort);

    if (this.userFormComponent.newUser === true ) {
      this.addUser();
    }

    /*
    let itemsToUpdate = [];
    this.userGrid.gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
      let rowdata = rowNode.data;
      if ( rowdata.id === updatedVo.id ) {
        itemsToUpdate.push(updatedVo);
      } else {
        // itemsToUpdate.push(rowdata);
      }
    });
    this.userGrid.gridOptions.api.updateRowData({ update: itemsToUpdate });
    // this.userGrid.gridOptions.api.setRowData(itemsToUpdate);
    */
  }

  cancel(evt: any) {
    if ( this.userSelected === true ) {
      // reload selected user
      this.editUser();
    } else {
      // reset user
      this.addUser();
    }
  }

  refreshGrid() {
    this.userList = [];
    this.loadUsers();
  }

  expandRetract() {
    if ( this.splitAreaLeftSize > 30 ) {
      this.splitAreaLeftSize = 30;
      this.splitAreaRightSize = 70;
    } else {
      this.splitAreaLeftSize = 100;
      this.splitAreaRightSize = 0;
    }
  }
}
