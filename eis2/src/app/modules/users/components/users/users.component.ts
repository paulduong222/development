import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { UserData } from '../../../../data/user-data';
import { UserService } from '../../../../service/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  public _selectedTab = '';
  users2: UserData[];
  constructor(public router: Router, private userService: UserService) {
  }

  ngOnInit() {
    this._selectedTab = 'useraccounts';
    this.loadUsers();
  }

   onMenuSelect($event) {
     this._selectedTab = $event;
   }

  loadUsers() {
    this.userService.getUserList()
    .subscribe(users => {
        this.users2 = users as UserData[];
    });
  }
}
