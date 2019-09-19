import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { IncidentSelector2Vo } from '../data/incident-selector2-vo';
import { Subject } from 'rxjs';
import { IncidentGroupVo } from '../data/incident-group-vo';
import { IncidentVo } from '../data/incident-vo';
import { last } from 'rxjs/operators';
import { DropdownData } from 'src/app/data/dropdowndata';

@Injectable({
  providedIn: 'root'
})
export class IncidentSelectorService {
  eventTypeData: DropdownData[] = [];
  stateTypeData: DropdownData[] = [];
  agencyTypeData: DropdownData[] = [];
  kindTypeData: DropdownData[] = [];
  organizationTypeData: DropdownData[] = [];

  resourceAgencyTypeData: DropdownData[] = [];
  resourceJetportTypeData: DropdownData[] = [];
  resourceKindTypeData: DropdownData[] = [];
  resourceUnitTypeData: DropdownData[] = [];
  resourceIncidentTypeData: DropdownData[] = [];
  resourceIncAcctCodeTypeData: DropdownData[] = [];
  resourceAccrualCodeTypeData: DropdownData[] = [];

  tnspKindTypeData: DropdownData[] = [];
  tnspUnitTypeData: DropdownData[] = [];

  // selected row from incidents grid
  selectedGridRow: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  selectedIncidentSelectorVo = new Subject<IncidentSelector2Vo>();

  // current working oject for incident group and incident
  // need to have this object as temporary solution so
  // the incgroup/inc child components have a default vo in their init methods
  currentVo: any;

  // isManageAsGroup and selectedDropdownIncidentId are holders for the
  // selected incidentId/group from the nav bar
  isManagedAsGroup = true;
  selectedDropdownIncidentId;

  // selectedTab is main nav selected tab (incidents, check-in, demob, time, ....)
  selectedTab: string;

  // selectedSubTab is tab within the main selecetd area
  selectedSubTab: string;

  currentResourceTab = new Subject<string>();

  /*
    resourcesMode
    This variable holds the current mode of resources area.
    The following are the options:
       resview
       timeview
       adjview
       costview
  */
  resourcesMode: string;

  constructor(private http: HttpClient) { }

  setIsManagedAsGroup( val: boolean ) {
    this.isManagedAsGroup = val;
  }

  setCurrentResourceTab(name) {
    this.currentResourceTab.next(name);
  }

  getCurrentResourceTab(): string {
    let name = '';
    this.currentResourceTab
      .pipe( last() )
      .subscribe(d => {
        name = Object.assign({}, d);
    });
    return name;
  }

  setSelectedDropdownIncidentId(id: number ) {
    this.selectedDropdownIncidentId = id;
  }

  setSelectedIncidentSelectorVo(vo: IncidentSelector2Vo) {
    this.selectedIncidentSelectorVo.next(vo);
  }

  getSelectedIncidentSelectorVo(): IncidentSelector2Vo {
    let vo: IncidentSelector2Vo;
    this.selectedIncidentSelectorVo
      .pipe( last() )
      .subscribe(d => {
        vo = Object.assign({}, d);
      });
    return vo;
  }

  getGrid(userId): Observable<DialogueVo> {
    // TODO: Provide HTTP query params for incidentId, incidentGroupId, incidentsOnly and filterExcluded if desired.
    return this.http.get<DialogueVo>(`/service/users/${userId}/incidents/grid`);
  }


}
