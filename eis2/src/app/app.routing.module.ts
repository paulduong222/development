import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminAuthGuard } from './service/admin-auth.guard';
import { UserAuthGuard } from './service/user-auth.guard';


const routes: Routes = [
    { path: '', pathMatch: 'full', redirectTo: 'welcome' },
    { path: 'isuite', pathMatch: 'full', redirectTo: 'welcome' },
    { path: 'welcome', loadChildren: './modules/welcome/welcome.module#WelcomeModule' },
    { path: 'home', loadChildren: './modules/home/home.module#HomeModule' },
    { path: 'users', canActivate: [AdminAuthGuard], canActivateChild: [AdminAuthGuard], 
            loadChildren: './modules/users/users.module#UsersModule' },
    { path: 'databasemgmt', canActivate: [AdminAuthGuard], canActivateChild: [AdminAuthGuard], 
            loadChildren: './modules/databasemgmt/databasemgmt.module#DatabasemgmtModule' },
    { path: 'messageboard', canActivate: [AdminAuthGuard], canActivateChild: [AdminAuthGuard],
            loadChildren: './modules/messageboard/messageboard.module#MessageboardModule' },
    { path: 'incidents', canActivate: [UserAuthGuard], canActivateChild: [UserAuthGuard],
            loadChildren: './modules/incidents/incidents.module#IncidentsModule' },
];

@NgModule({
    imports: [RouterModule.forRoot(
        routes,
        { enableTracing: true })], // <-- debugging purposes only)],
    exports: [RouterModule]
})

export class AppRoutingModule {}

export const routingComponents = [
];
