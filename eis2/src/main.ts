import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';

import {LicenseManager} from 'ag-grid-enterprise';
LicenseManager.setLicenseKey('Criterion_Systems__eISuite_3Devs11_March_2020__MTU4Mzg4NDgwMDAwMA==ce6975fee0e58323ef163044eea72d5f');

if (environment.production) {
  enableProdMode();
}

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));

