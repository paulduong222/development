import { Component, Input, OnInit, Output, ViewChild, EventEmitter, ViewChildren } from '@angular/core';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { IncidentResourceVo } from 'src/app/data/incident-resource-vo';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { DropdownData } from 'src/app/data/dropdowndata';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { AssignmentStatusTypeEnum } from 'src/app/data/enums/assignment-status-type-enum.enum';
import { TravelMethodVo } from 'src/app/data/travel-method-vo';
import { JetPortVo } from 'src/app/data/jet-port-vo';
import { IncidentResourceService } from 'src/app/service/incident-resource.service';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { DialogueVo } from 'src/app/data/dialogue/dialoguevo';
import { AirTravelVo } from 'src/app/data/air-travel-vo';
import { CostDataVo } from 'src/app/data/cost-data-vo';
import { AgencyVo } from 'src/app/data/agency-vo';
import { AccrualCodeVo } from 'src/app/data/accrual-code-vo';
import { EmploymentTypeEnum } from 'src/app/data/enums/employment-type-enum.enum';
import { QualWindowComponent } from '../modals/qual-window/qual-window.component';
import { ResourceKindVo } from 'src/app/data/resource-kind-vo';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { RestOvernightWindowComponent } from '../modals/rest-overnight-window/rest-overnight-window.component';
import { AssignmentStatusVo } from 'src/app/data/assignment-status-vo';
import { AdPaymentInfoVo } from 'src/app/data/ad--payment-info-vo';
import { OrganizationVo } from 'src/app/data/organization-vo';
import { ContractorWindowComponent } from '../modals/contractor-window/contractor-window.component';
import { AgreementWindowComponent } from '../modals/agreement-window/agreement-window.component';
import { ContractorAgreementVo } from 'src/app/data/contractor-agreement-vo';
import { ContractorPaymentInfoVo } from 'src/app/data/contractor-payment-info-vo';
import { ContractorVo } from 'src/app/data/contractor-vo';
import { ContractorRateWindowComponent } from '../modals/contractor-rate-window/contractor-rate-window.component';
import { ContractorRateService } from 'src/app/service/contractor-rate.service';
import { IncidentResourceGridVo } from 'src/app/data/incident-resource-grid-vo';

@Component({
  selector: 'app-resources-form',
  templateUrl: './resources-form.component.html',
  styleUrls: ['./resources-form.component.css']
})
export class ResourcesFormComponent implements OnInit {
  @ViewChild('promptModalResForm') promptModalResForm: PromptModalComponent;
  @ViewChild('qualWindow') qualWindow: QualWindowComponent;
  @ViewChild('restWindow') restWindow: RestOvernightWindowComponent;
  @ViewChild('contractorWindow') contractorWindow: ContractorWindowComponent;
  @ViewChild('agreementWindow') agreementWindow: AgreementWindowComponent;
  @ViewChild('contractorRateWindow') contractorRateWindow: ContractorRateWindowComponent;

  @Input() currentSelectedIncidentSelectorVo: IncidentSelector2Vo;
  @Output() saveResourceEvent = new EventEmitter();
  @ViewChild('ddIncident') ddIncident: EisDropdownComponent;
  @ViewChild('ddIncAcctCode') ddIncAcctCode: EisDropdownComponent;
  @ViewChild('ddStatusType') ddStatusType: EisDropdownComponent;
  @ViewChild('ddUnitType') ddUnitType: EisDropdownComponent;
  @ViewChild('ddKindType') ddKindType: EisDropdownComponent;
  @ViewChild('ddAgencyType') ddAgencyType: EisDropdownComponent;
  @ViewChild('ddDemobStateType') ddDemobStateType: EisDropdownComponent;
  @ViewChild('ddMobJetportType') ddMobJetportType: EisDropdownComponent;
  @ViewChild('ddMobTravelMethodType') ddMobTravelMethodType: EisDropdownComponent;
  @ViewChild('ddDemobTravelMethodType') ddDemobTravelMethodType: EisDropdownComponent;
  @ViewChild('ddEmploymentType') ddEmploymentType: EisDropdownComponent;
  @ViewChild('ddPaymentAgencyType') ddPaymentAgencyType: EisDropdownComponent;
  @ViewChild('ddAirJetportType') ddAirJetportType: EisDropdownComponent;
  @ViewChild('ddAccrualCodeType') ddAccrualCodeType: EisDropdownComponent;
  @ViewChild('ddLeaderType') ddLeaderType: EisDropdownComponent;
  @ViewChild('ddPointOfHire') ddPointOfHire: EisDropdownComponent;
  @ViewChild('ddAdClass') ddAdClass: EisDropdownComponent;
  @ViewChild('ddContractor') ddContractor: EisDropdownComponent;
  @ViewChild('ddAgreement') ddAgreement: EisDropdownComponent;

  @ViewChild('dtMobDate') dtMobDate: EisDatepickerComponent;
  @ViewChild('dtCheckInDate') dtCheckInDate: EisDatepickerComponent;
  @ViewChild('dtActualReleaseDate') dtActualReleaseDate: EisDatepickerComponent;
  @ViewChild('dtFirstWorkDay') dtFirstWorkDay: EisDatepickerComponent;
  @ViewChild('dtEstArrivalDate') dtEstArrivalDate: EisDatepickerComponent;
  @ViewChild('dtTentReleaseDate') dtTentReleaseDate: EisDatepickerComponent;
  @ViewChild('dtAssignDate') dtAssignDate: EisDatepickerComponent;
  @ViewChild('dtHiredDate') dtHiredDate: EisDatepickerComponent;

  @ViewChild('gridQuals') gridQuals: EisGridComponent;
  @ViewChild('gridRestOvernight') gridRestOvernight: EisGridComponent;
  @ViewChild('gridRates') gridRates: EisGridComponent;

  @Input() rateClassRateData = [];
  @Input() rateClassRateVos = [];
  @Input() contractorVos = [];
  @Input() contractorData = [];
  agreementData = [];
  @Input() checkinDemobRole = false;
  @Input() timeRole = false;
  @Input() costRole = false;

  // Dropdown collection lists
  statusTypeData: DropdownData[] = [
    {id: 0, code: 'F', desc: 'Filled'}
    , {id: 1, code: 'C', desc: 'Checked-In'}
    , {id: 2, code: 'P', desc: 'Pending Demob'}
    , {id: 3, code: 'D', desc: 'Demob'}
    , {id: 4, code: 'R', desc: 'Reassigned'}
  ];

  travelMethodTypeData: DropdownData[] = [
    {id: 0, code: 'A/R', desc: 'Air Travel then Rental'}
    , {id: 1, code: 'AIR', desc: 'Air Travel'}
    , {id: 2, code: 'AOV', desc: 'Agency owned vehicle'}
    , {id: 3, code: 'BUS', desc: 'Bus'}
    , {id: 4, code: 'OTH', desc: 'Other'}
    , {id: 5, code: 'PAS', desc: 'Passenger'}
    , {id: 6, code: 'POV', desc: 'Privately owned vehicle'}
    , {id: 7, code: 'REN', desc: 'Rental'}
  ];

  employmentTypeData: DropdownData[] = [
    {id: 0, code: 'FED', desc: ''}
    , {id: 1, code: 'AD', desc: ''}
    , {id: 2, code: 'OTHER', desc: ''}
  ];

  leaderTypeData: DropdownData[] = [
    {id: 0, code: 'NONE', desc: ''}
    , {id: 1, code: 'PRIMARY', desc: ''}
    , {id: 2, code: 'SECONDARY', desc: ''}
  ];

  // selected object holders for dropdown
  selectedIncidentTypeData: DropdownData;
  selectedIncAcctCodeTypeData: DropdownData;
  selectedAccrualCode: DropdownData;
  selectedEmploymentTypeData: DropdownData;
  selectedLeaderTypeData: DropdownData;
  statusTypeDropdownData: DropdownData;
  kindTypeDropdownData: DropdownData;
  unitTypeDropdownData: DropdownData;
  agencyTypeDropdownData: DropdownData;
  acctCodeTypeDropdownData: DropdownData;
  demobStateTypeDropdownData: DropdownData;
  mobJetportTypeDropdownData: DropdownData;
  mobTravelMethodTypeDropdownData: DropdownData;
  demobTravelMethodTypeDropdownData: DropdownData;
  selectedPaymentAgency: DropdownData;
  airJetportTypeDropdownData: DropdownData;
  selectedPointOfHire: DropdownData;
  selectedAdClass: DropdownData;
  selectedContractor: DropdownData;
  selectedAgreement: DropdownData;

  selectedContractorRateVo = null;

  public _selectedDataTab = '';
  resourceForm: FormGroup;
  dialogueVo: DialogueVo;
  showRental = false;
  airTravelQuestions = [];

  // quals grid
  selectedQual = null;
  qualsList = [];
  gridQualsColumnDefs = [
    {headerName: 'Trainee', field: 'trainee', width: 90, filter: false, cellRenderer: 'checkboxRenderer'},
    {headerName: 'Item Code', field: 'itemCode', width: 100},
    {headerName: 'Iten Name', field: 'itemName', width: 160},
 ];

  // rest overnight grid
  selectedRestOvernight = null;
  restOvernightList = [];
  gridRestOvernightColumnDefs = [
    {headerName: 'City', field: 'city', width: 250},
    {headerName: 'State', field: 'state', width: 100},
  ];

  // rates grid
  selectedRate = null;
  ratesList = [];
  gridRatesColumnDefs = [
    {headerName: 'Rate Type', field: 'rateType', width: 100},
    {headerName: 'Time UOM', field: 'unitOfMeasure', width: 85},
    {headerName: 'Rate', field: 'rateAmount', width: 75},
    {headerName: 'Guarantee', field: 'guaranteeAmount', width: 110},
    {headerName: 'Description', field: 'description', width: 120},
  ];

  public incidentResourceVo: IncidentResourceVo = <IncidentResourceVo>{id: 0};

  constructor( private formBuilder: FormBuilder
              , public incidentSelectorService: IncidentSelectorService
              , private notifyService: NotificationService
              , private contractorRateService: ContractorRateService
              , private incidentResourceService: IncidentResourceService) { }


  ngOnInit() {
    this.resetActiveTab();
    this.resourceForm = this.formBuilder.group({
        id: 0,
        person: false,
        incidentId: 0,
        firstName: '',
        lastName: '',
        resourceName: '',
        requestNumber: '',
        contractor: false,
        status: '',
        numberOfPersonnel: '',
        incidentVo: new FormControl({value: {}, disabled: false}),
        incidentAccountCodeVo: {},
        kindVo: {},
        kindName: new FormControl({value: '', disabled: true}),
        unitVo: {},
        agencyVo: {},
        mobilizationDate: '',
        checkInDate: '',
        checkInTime: '',
        actualReleaseDate: new FormControl({value: '', disabled: false}),
        actualReleaseTime: new FormControl({value: '', disabled: false}),
        tentReleaseDate: new FormControl({value: '', disabled: false}),
        tentReleaseTime: new FormControl({value: '', disabled: false}),
        // tentDemobRemarks: '',
        firstWorkDay: '',
        loa: '',
        other1: '',
        other2: '',
        other3: '',
        cellPhone: '',
        demobDate: new FormControl({value: '', disabled: true}),
        daysLeft: new FormControl({value: '', disabled: true}),
        demobCity: '',
        demobStateVo: {},
        mobJetportVo: {},
        checkInRemarks: '',
        mobTravelMethodVo: {},
        demobTravelMethodVo: {},
        dispatchNotifiedTent: false,
        checkoutFormPrinted: false,
        availReassign: false,
        rentalLocation: '',
        estArrivalDate: '',
        estArrivalTime: '',
        dispatchNotifiedActual: false,
        restOvernight: false,
        releaseRemarks: '',
        nameOnPictureId: '',
        airTravelDispatch: false,
        itineraryReceived: false,
        airline: '',
        flightNumber: '',
        flightTime: '',
        hoursToAirport: '',
        minutesToAirport: '',
        icpLeaveTime: '',
        airRemarks: '',
        assignDate: '',
        accrualLocked: false,
        generateCosts: false,
        costOther1: '',
        costOther2: '',
        costOther3: '',
        costRemarks: '',
        actualsOnly: false,
        eci: '',
        adRate: new FormControl({value: '', disabled: true}),
        otherRate: 0.00,
        hiringUnitName: '',
        hiringUnitPhone: '',
        hiringUnitFax: '',
        uniqueName: '',
        desc1: '',
        desc2: '',
        hiredDate: '',
        hiredTime: '',
        pointOfHire: '',
        govOperator: false,
        govSupplies: false,
        govWithdrawn: false,
        of286Remarks: ''
       });
  }

  resetActiveTab() {
    if ( this.checkinDemobRole ===  true ) {
      this._selectedDataTab = 'checkin';
    } else if ( this.timeRole === true ) {
      this._selectedDataTab = 'time';
    } else if ( this.costRole === true ) {
      this._selectedDataTab = 'cost';
    }
  }

  getIncidentSelectorClass() {
    if (this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
      return '';
    } else {
      return 'hidden';
    }
  }

  getLeaderClass() {
    if ( this.incidentResourceVo !== undefined
        && this.resourceForm.get('person').value === true
        && this.incidentResourceVo.resourceVo.parentResourceId !== null
        && this.incidentResourceVo.resourceVo.parentResourceId > 0) {
          return '';
    } else {
      return 'hidden';
    }
  }

  getTimeNonContractorDivClass() {
    if ( this.resourceForm.get('contractor').value === true
          || this.resourceForm.get('person').value === false) {
      return 'hidden';
    } else {
      return '';
    }
  }

  getTimeContractorDivClass() {
    if ( this.resourceForm.get('contractor').value === true ) {
      return '';
    } else {
      return 'hidden';
    }
  }

  getTimeAdDivClass() {
    if ( this.resourceForm.get('person').value === true
          && this.ddEmploymentType.selectedValue !== undefined
          && this.ddEmploymentType.selectedValue.code === 'AD') {
        return '';
    } else {
      return 'hidden';
    }
  }

  getTimeOtherDivClass() {
    if ( this.resourceForm.get('person').value === true
          && this.ddEmploymentType.selectedValue !== undefined
          && this.ddEmploymentType.selectedValue.code === 'OTHER') {
        return '';
    } else {
      return 'hidden';
    }
  }

  dataTabSelect(tabname) {
    this._selectedDataTab = tabname;
  }

  getActiveTab(tabname) {
    if ( this._selectedDataTab === tabname) {
      return '';
    } else {
      return 'hidden';
    }
  }

  getQualClass() {
    if ( this.resourceForm.get('person').value === false ) {
      return 'hidden';
    }
    return '';
  }

  getRestOvernightClass() {
    if ( this.resourceForm.get('restOvernight').value === false ) {
      return 'hidden';
    }
    return '';
  }

  getBtnDivClass(btn) {
    if ( btn === 'checkindemob' && this.checkinDemobRole === false ) { return 'hidden'; }
    if ( btn === 'time' && this.timeRole === false ) { return 'hidden'; }
    if ( btn === 'cost' && this.costRole === false ) { return 'hidden'; }
    return 'w3-left';
  }

  getActiveTabBtn(tabname) {

    if (tabname === 'airtravel' || tabname === 'airtravelquest') {
      let isAirTravel = false;
      if ( this.ddMobTravelMethodType.selectedValue !== undefined
        && (this.ddMobTravelMethodType.selectedValue.code === 'A/R' || this.ddMobTravelMethodType.selectedValue.code === 'AIR')) {
          isAirTravel = true;
      }
      if ( this.ddDemobTravelMethodType.selectedValue !== undefined
        && (this.ddDemobTravelMethodType.selectedValue.code === 'A/R' || this.ddDemobTravelMethodType.selectedValue.code === 'AIR')) {
          isAirTravel = true;
      }
      if ( isAirTravel === false ) { return 'hidden'; }
    }
    if ( this._selectedDataTab === tabname ) {
      return 'btn-selected';
    } else {
      return 'btn-normal';
    }
  }

  reset() {
    this.selectedQual = null;
    this.selectedRestOvernight = null;
    this.selectedContractorRateVo = null;
    this.resetActiveTab();
  }

  populateForm() {
    this.reset();
    this.selectedIncidentTypeData = this.ddIncident.getDropdownDataObjectById(-2);
    this.selectedIncAcctCodeTypeData = this.ddIncAcctCode.getDropdownDataObjectById(-2);
    this.statusTypeDropdownData = this.ddStatusType.getDropdownDataObjectById(-2);
    this.kindTypeDropdownData = this.ddKindType.getDropdownDataObjectById(-2);
    this.unitTypeDropdownData = this.ddUnitType.getDropdownDataObjectById(-2);
    this.agencyTypeDropdownData = this.ddAgencyType.getDropdownDataObjectById(-2);
    this.demobStateTypeDropdownData = this.ddDemobStateType.getDropdownDataObjectById(-2);
    this.mobJetportTypeDropdownData = this.ddMobJetportType.getDropdownDataObjectById(-2);
    this.mobTravelMethodTypeDropdownData = this.ddMobTravelMethodType.getDropdownDataObjectById(-2);
    this.demobTravelMethodTypeDropdownData = this.ddDemobTravelMethodType.getDropdownDataObjectById(-2);
    this.selectedPaymentAgency = this.ddPaymentAgencyType.getDropdownDataObjectById(-2);
    this.airJetportTypeDropdownData = this.ddAirJetportType.getDropdownDataObjectById(-2);
    this.selectedAccrualCode = this.ddAccrualCodeType.getDropdownDataObjectById(-2);
    this.selectedLeaderTypeData = this.ddLeaderType.getDropdownDataObjectById(-2);
    this.selectedEmploymentTypeData = this.ddEmploymentType.getDropdownDataObjectById(-2);
    this.selectedAdClass = this.ddAdClass.getDropdownDataObjectById(-2);
    this.selectedPointOfHire = this.ddPointOfHire.getDropdownDataObjectById(-2);
    this.selectedContractor = this.ddContractor.getDropdownDataObjectById(-2);
    this.selectedAgreement = this.ddAgreement.getDropdownDataObjectById(-2);

    this.airTravelQuestions = this.incidentResourceVo.workPeriodVo.airTravelQuestions;

    if ( this.incidentResourceVo.resourceVo.resourceKindVos === undefined
        || this.incidentResourceVo.resourceVo.resourceKindVos === null ) {
        this.incidentResourceVo.resourceVo.resourceKindVos = [] as ResourceKindVo[];
    }

    if ( this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.adPaymentInfoVo === undefined
            || this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.adPaymentInfoVo === null ) {
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.adPaymentInfoVo
              = <AdPaymentInfoVo> {
                id: 0
                , eci: ''
                , pointOfHireOrgVo: <OrganizationVo>{
                  id: 0
                }
              };
    }

    this.qualsList = [];
    this.incidentResourceVo.resourceVo.resourceKindVos.forEach(rk => {
      this.qualsList.push(
        {
          id: rk.id
          , trainee: rk.training
          , itemCode: rk.kindVo.code
          , itemName: rk.kindVo.description
        }
      );
    });

    this.restOvernightList = [];
    this.incidentResourceVo.workPeriodVo.workPeriodOvernightStayInfoVos.forEach(rest => {
      this.restOvernightList.push(
        {
          id: rest.id
          , city: rest.city
          , state: rest.countrySubdivisionVo.countrySubAbbreviation
        }
      );
    });

    this.resourceForm.setValue(
      {
        id: this.incidentResourceVo.id,
        person: this.incidentResourceVo.resourceVo.person,
        incidentId: this.incidentResourceVo.incidentVo.id,
        firstName: this.incidentResourceVo.resourceVo.firstName,
        lastName: this.incidentResourceVo.resourceVo.lastName,
        resourceName: this.incidentResourceVo.resourceVo.resourceName,
        requestNumber: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.requestNumber,
        contractor: false,
        status: '',
        incidentVo: {},
        numberOfPersonnel: this.incidentResourceVo.resourceVo.numberOfPersonnel,
        incidentAccountCodeVo: {},
        kindVo: {},
        kindName: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.kindVo.description,
        unitVo: {},
        agencyVo: {},
        mobilizationDate: this.incidentResourceVo.workPeriodVo.ciResourceMobilizationVo.startDateVo.dateString,
        checkInDate: this.incidentResourceVo.workPeriodVo.ciCheckInDateVo.dateString,
        checkInTime: this.incidentResourceVo.workPeriodVo.ciCheckInDateVo.timeString,
        actualReleaseDate: this.incidentResourceVo.workPeriodVo.dmReleaseDateVo.dateString,
        actualReleaseTime: this.incidentResourceVo.workPeriodVo.dmReleaseDateVo.timeString,
        tentReleaseDate: this.incidentResourceVo.workPeriodVo.dmTentativeReleaseDateVo.dateString,
        tentReleaseTime: this.incidentResourceVo.workPeriodVo.dmTentativeReleaseDateVo.timeString,
        // tentDemobRemarks: this.incidentResourceVo.workPeriodVo.dmPlanningRemarks,
        firstWorkDay: this.incidentResourceVo.workPeriodVo.ciFirstWorkDateVo.dateString,
        loa: this.incidentResourceVo.workPeriodVo.ciLengthAtAssignment,
        other1: this.incidentResourceVo.resourceVo.other1,
        other2: this.incidentResourceVo.resourceVo.other2,
        other3: this.incidentResourceVo.resourceVo.other3,
        cellPhone: this.incidentResourceVo.resourceVo.phone,
        demobDate: '',
        daysLeft: '',
        demobCity: this.incidentResourceVo.workPeriodVo.dmTentativeDemobCity,
        demobStateVo: {},
        mobJetportVo: {},
        checkInRemarks: this.incidentResourceVo.workPeriodVo.ciPrePlanningRemarks,
        mobTravelMethodVo: {},
        demobTravelMethodVo: {},
        dispatchNotifiedTent: this.incidentResourceVo.workPeriodVo.dmPlanningDispatchNotified,
        checkoutFormPrinted: this.incidentResourceVo.workPeriodVo.dmCheckoutFormPrinted,
        availReassign: this.incidentResourceVo.workPeriodVo.dmReAssignable,
        rentalLocation: this.incidentResourceVo.workPeriodVo.ciRentalLocation,
        estArrivalDate: this.incidentResourceVo.workPeriodVo.dmTentativeArrivalDateVo.dateString,
        estArrivalTime: this.incidentResourceVo.workPeriodVo.dmTentativeArrivalDateVo.timeString,
        dispatchNotifiedActual: this.incidentResourceVo.workPeriodVo.dmReleaseDispatchNotified,
        restOvernight: this.incidentResourceVo.workPeriodVo.dmRestOvernight,
        releaseRemarks: this.incidentResourceVo.workPeriodVo.dmReleaseRemarks,
        nameOnPictureId: this.incidentResourceVo.resourceVo.nameOnPictureId,
        airTravelDispatch: this.incidentResourceVo.workPeriodVo.dmAirTravelVo.dispatchNotified,
        itineraryReceived: this.incidentResourceVo.workPeriodVo.dmAirTravelVo.itineraryReceived,
        airline: this.incidentResourceVo.workPeriodVo.dmAirTravelVo.airline,
        flightNumber: this.incidentResourceVo.workPeriodVo.dmAirTravelVo.flightNumber,
        flightTime: this.incidentResourceVo.workPeriodVo.dmAirTravelVo.flightTime,
        hoursToAirport: this.incidentResourceVo.workPeriodVo.dmAirTravelVo.hoursToAirport,
        minutesToAirport: this.incidentResourceVo.workPeriodVo.dmAirTravelVo.minutesToAirport,
        icpLeaveTime: this.incidentResourceVo.workPeriodVo.dmAirTravelVo.leaveTime,
        airRemarks: this.incidentResourceVo.workPeriodVo.dmAirTravelVo.remarks,
        assignDate: this.incidentResourceVo.costDataVo.assignDateVo.dateString,
        accrualLocked: this.incidentResourceVo.costDataVo.accrualLocked,
        generateCosts: this.incidentResourceVo.costDataVo.generateCosts,
        actualsOnly: this.incidentResourceVo.costDataVo.useAccrualsOnly,
        costOther1: this.incidentResourceVo.costDataVo.costOther1,
        costOther2: this.incidentResourceVo.costDataVo.costOther2,
        costOther3: this.incidentResourceVo.costDataVo.costOther3,
        costRemarks: this.incidentResourceVo.costDataVo.costRemarks,
        eci: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.adPaymentInfoVo.eci,
        adRate: 0,
        otherRate: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.otherDefaultRate,
        hiringUnitName: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.hiringUnitName,
        hiringUnitPhone: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.hiringUnitPhone,
        hiringUnitFax: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.hiringUnitFax,
        uniqueName: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.vinName,
        desc1: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.desc1,
        desc2: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.desc2,
        hiredDate: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.hiredDateVo.dateString,
        hiredTime: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.hiredDateVo.timeString,
        pointOfHire: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.pointOfHire,
        govOperator: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.operation,
        govSupplies: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.supplies,
        govWithdrawn: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.withdrawn,
        of286Remarks: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.ofRemarks
      });

      this.ratesList = [];
      if ( this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType !== null
             && this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType
              === EmploymentTypeEnum.CONTRACTOR) {
         this.resourceForm.get('contractor').patchValue(true);
         this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo
                .contractorPaymentInfoVo.contractorRateVos.forEach(rate => {
            this.ratesList.push(rate);
         });
     }

      if ( this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentStatusVo.code === 'D'
              || this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentStatusVo.code === 'R') {
        this.resourceForm.controls['actualReleaseDate'].enable();
        this.resourceForm.controls['actualReleaseTime'].enable();
      } else {
        this.resourceForm.controls['actualReleaseDate'].disable();
        this.resourceForm.controls['actualReleaseTime'].disable();
      }

    let mobTM = '';
    if ( null !== this.incidentResourceVo.workPeriodVo.ciTravelMethodVo) {
      if ( this.incidentResourceVo.workPeriodVo.ciTravelMethodVo.code === 'AR') {
        mobTM = 'A/R';
      } else {
        mobTM = this.incidentResourceVo.workPeriodVo.ciTravelMethodVo.code;
      }
    }

    if ( mobTM === 'A/R' || mobTM === 'REN') {
      this.showRental = true;
    } else {
      this.showRental = false;
    }
    let demobTM = '';
    if ( null !== this.incidentResourceVo.workPeriodVo.dmTravelMethodVo) {
      if ( this.incidentResourceVo.workPeriodVo.dmTravelMethodVo.code === 'AR') {
        demobTM = 'A/R';
      } else {
        demobTM = this.incidentResourceVo.workPeriodVo.dmTravelMethodVo.code;
      }
    }

    if (this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.contractorVo === null) {
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.contractorVo =
        <ContractorVo>{id: 0};
    }

    if (this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.contractorAgreementVo === null) {
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.contractorAgreementVo =
        <ContractorAgreementVo>{id: 0};
    }

    setTimeout(() => {
      this.selectedIncidentTypeData =
        this.ddIncident.getDropdownDataObjectById(this.incidentResourceVo.incidentVo.id);
      this.selectedIncAcctCodeTypeData =
        this.ddIncAcctCode.getDropdownDataObjectById(this.incidentResourceVo.workPeriodVo.wpDefaultIncidentAccountCodeVo.id);
      this.statusTypeDropdownData =
        this.ddStatusType.getDropdownDataObjectByCode(this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentStatusVo.code);
      this.kindTypeDropdownData =
        this.ddKindType.getDropdownDataObjectById(this.incidentResourceVo.workPeriodVo.currentAssignmentVo.kindVo.id);
      this.unitTypeDropdownData =
        this.ddUnitType.getDropdownDataObjectById(this.incidentResourceVo.resourceVo.organizationVo.id);
      if ( this.incidentResourceVo.resourceVo.agencyVo !== null &&
        this.incidentResourceVo.resourceVo.agencyVo.id > 0 ) {
          this.agencyTypeDropdownData =
            this.ddAgencyType.getDropdownDataObjectById(this.incidentResourceVo.resourceVo.agencyVo.id);
      }
      if ( this.incidentResourceVo.resourceVo.leaderType !== null ) {
        this.selectedLeaderTypeData = this.ddLeaderType.getDropdownDataObjectById(this.incidentResourceVo.resourceVo.leaderType);
      }
      this.demobStateTypeDropdownData =
        this.ddDemobStateType.getDropdownDataObjectById(this.incidentResourceVo.workPeriodVo.dmTentativeDemobStateVo.id);
      if (this.mobJetportTypeDropdownData !== null && this.mobJetportTypeDropdownData !== undefined) {
        this.mobJetportTypeDropdownData =
          this.ddMobJetportType.getDropdownDataObjectById(this.incidentResourceVo.workPeriodVo.ciArrivalJetPortVo.id);
      }
      this.mobTravelMethodTypeDropdownData =
        this.ddMobTravelMethodType.getDropdownDataObjectByCode(mobTM);
      this.demobTravelMethodTypeDropdownData =
        this.ddDemobTravelMethodType.getDropdownDataObjectByCode(demobTM);
      if ( this.incidentResourceVo.costDataVo.paymentAgencyVo !== null &&
          this.incidentResourceVo.costDataVo.paymentAgencyVo.id > 0 ) {
            this.selectedPaymentAgency =
              this.ddPaymentAgencyType.getDropdownDataObjectById(this.incidentResourceVo.costDataVo.paymentAgencyVo.id);
      }
      if ( this.incidentResourceVo.costDataVo.accrualCodeVo !== null &&
          this.incidentResourceVo.costDataVo.accrualCodeVo.id > 0 ) {
            this.selectedAccrualCode =
              this.ddAccrualCodeType.getDropdownDataObjectById(this.incidentResourceVo.costDataVo.accrualCodeVo.id);
      }

      if (this.incidentResourceVo.workPeriodVo.dmAirTravelVo.jetPortVo !== null
           && this.incidentResourceVo.workPeriodVo.dmAirTravelVo.jetPortVo !== undefined) {
          this.airJetportTypeDropdownData =
            this.ddAirJetportType.getDropdownDataObjectById(this.incidentResourceVo.workPeriodVo.dmAirTravelVo.jetPortVo.id);
        }

      if ( this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType !== null
            && this.resourceForm.get('person').value === true && this.resourceForm.get('contractor').value === false) {
          const empType = this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType.valueOf();
          this.selectedEmploymentTypeData = this.ddEmploymentType.getDropdownDataObjectByCode(empType);
      }

      if ( this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.adPaymentInfoVo !== null
            && this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.adPaymentInfoVo.pointOfHireOrgVo !== null) {
        this.selectedPointOfHire = this.ddPointOfHire.getDropdownDataObjectById(
          this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.adPaymentInfoVo.pointOfHireOrgVo.id
        );
      }
      if ( this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.adPaymentInfoVo !== null
        && this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.adPaymentInfoVo.rateClassRateVo !== null) {
          this.selectedAdClass = this.ddAdClass.getDropdownDataObjectById(
            this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.adPaymentInfoVo.rateClassRateVo.id
          );
          const idx = this.rateClassRateVos.findIndex(f => f.id === this.selectedAdClass.id);
          if ( idx > -1 ) {
            this.resourceForm.get('adRate').patchValue(this.rateClassRateVos[idx].rate);
          }
      }
      if ( this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType !== null
              && this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType
                   === EmploymentTypeEnum.CONTRACTOR) {
        this.selectedContractor = this.ddContractor.getDropdownDataObjectById(
          this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.contractorVo.id
        );
        this.agreementData = [];
        const idx = this.contractorVos.findIndex(d => d.id === this.selectedContractor.id);
        if ( idx > -1 ) {
          this.contractorVos[idx].contractorAgreementVos.forEach(a => {
            const ddata = {
              id: a.id
              , code: a.agreementNumber
            };
            this.agreementData.push(ddata);
          });
        }
        setTimeout(() => {
          this.selectedAgreement = this.ddAgreement.getDropdownDataObjectById(
            this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.contractorAgreementVo.id
          );
        });
      }

    });
  }

  statusSelectEvent(event) {
    if ( event.desc !== '' && (event.code === 'D' || event.code === 'R')) {
      this.resourceForm.controls['actualReleaseDate'].enable();
      this.resourceForm.controls['actualReleaseTime'].enable();
    } else {
      this.resourceForm.controls['actualReleaseDate'].disable();
      this.resourceForm.controls['actualReleaseTime'].disable();
    }
  }

  contractorSelectEvent(contractor) {
    this.selectedAgreement = this.ddAgreement.getDropdownDataObjectById(-2);

    if ( contractor.id > 0) {
      this.agreementData = [];
      const idx = this.contractorVos.findIndex(d => d.id === contractor.id);
      if ( idx > -1 ) {
        this.contractorVos[idx].contractorAgreementVos.forEach(a => {
          const ddata = {
            id: a.id
            , code: a.agreementNumber
          };
          this.agreementData.push(ddata);
        });
        if ( this.agreementData.length === 1) {
          // set default if only 1 in list
          this.selectedAgreement = this.ddAgreement.getDropdownDataObjectById(this.agreementData[0].id);
        }
      }
    } else {
      this.agreementData = [];
    }
  }

  kindSelectEvent(event) {
    if ( event.desc !== '') {
      this.resourceForm.get('kindName').patchValue(event.desc);
    } else {
      this.resourceForm.get('kindName').patchValue('');
    }
  }

  onAdClassSelect(event) {
    if ( event.code !== '') {
      const idx = this.rateClassRateVos.findIndex(f => f.id === event.id);
      if ( idx > -1 ) {
        this.resourceForm.get('adRate').patchValue(this.rateClassRateVos[idx].rate);
      } else {
        this.resourceForm.get('adRate').patchValue('');
      }
    } else {
      this.resourceForm.get('adRate').patchValue('');
    }

  }

  saveResource(resubmit: boolean) {
    const irVoToSave = this.getIncidentResourceVo();

    if (resubmit === false ) {
      this.dialogueVo = <DialogueVo>{};
    }

    this.showMessage('Saving Resource ...', 'Processing Request', true, '');
    this.incidentResourceService.saveIncidentResource(irVoToSave, false, this.dialogueVo)
    .subscribe(data => {
      this.promptModalResForm.closeModal();
      this.notifyService.inspectResult(data);
      this.dialogueVo = data as DialogueVo;
      this.inspectCoa(data);
    });

  }

  inspectCoa(data) {
    const coaName = data['courseOfActionVo']['coaName'];
    switch (coaName) {
      case 'CHECK_FIRST_WORK_DAY':
      case 'CHECK_FIVE_DAY_ESTARRIVAL_DATE':
      case 'CHECK_FIVE_DAY_RELEASE_DATE':
      case 'CHECK_MOB_DATE':
      case 'CHECK_FIVE_DAY_CHECKIN_DATE':
              this.showPrompt( coaName, 'Resource', data['courseOfActionVo']['promptVo']['messageProperty'], 'Yes', 'No', '');
        break;
      case 'SAVE_INCIDENT_RESOURCE_COMPLETE':
        const irvo = data['resultObject'] as IncidentResourceVo;
        const irGridVo = data['resultObjectAlternate'] as IncidentResourceGridVo;
        this.incidentResourceVo = irvo;

        this.saveResourceEvent.emit(irGridVo);
        setTimeout(() => {
          this.populateForm();
        });
        break;
    }
  }

  promptActionResult(btnEvent) {
    this.promptModalResForm.closeModal();
    const coaName = this.promptModalResForm.promptMode;
    switch (coaName) {
      case 'CHECK_FIRST_WORK_DAY':
      case 'CHECK_FIVE_DAY_ESTARRIVAL_DATE':
      case 'CHECK_FIVE_DAY_RELEASE_DATE':
      case 'CHECK_MOB_DATE':
      case 'CHECK_FIVE_DAY_CHECKIN_DATE':
              if ( btnEvent === 'Yes') {
            // PromptResult.Yes == 1
            this.dialogueVo.courseOfActionVo.promptVo['promptResult'] = 1;
            this.saveResource(true);
          }
          break;
    }
  }

  showMessage(msg, title, updateMsgOnly, btn1Label) {
    if ( updateMsgOnly === true && this.promptModalResForm.isOpen === true) {
      this.promptModalResForm.promptMessage1 = msg;
    } else {
      this.promptModalResForm.reset();
      this.promptModalResForm.promptTitle = title;
      this.promptModalResForm.promptMessage1 = msg;
      if ( btn1Label !== '' ) { this.promptModalResForm.button1Label = btn1Label; }
      this.promptModalResForm.openModal();
    }
  }

  showPrompt(mode, title, msg, btn1, btn2, btn3) {
    this.promptModalResForm.reset();
    this.promptModalResForm.promptMode = mode;
    this.promptModalResForm.promptTitle = title;
    this.promptModalResForm.promptMessage1 = msg;
    this.promptModalResForm.button1Label = btn1;
    this.promptModalResForm.button2Label = btn2;
    this.promptModalResForm.button3Label = btn3;
    this.promptModalResForm.openModal();
  }

  getIncidentResourceVo() {
    this.setIncidentResCommonData();
    if ( this.checkinDemobRole === true) {
      this.setIncidentResCheckInTabData();
      this.setIncidentResDemobTabData();
      this.setIncidentResDemobAirTravTabData();
    }
    if ( this.timeRole === true) {
      this.incidentResourceVo.sourcePage = 'time';
      this.setIncidentResTimeTabData();
    }
    if ( this.costRole === true) {
      this.setIncidentResCostTabData();
    }

    setTimeout(() => {
    });
    return this.incidentResourceVo;
  }

  setQuestionValue(idx, event) {
    this.incidentResourceVo.workPeriodVo.airTravelQuestions[idx].questionValue = event.target.checked;
  }


  setIncidentResCommonData() {
    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
      this.incidentResourceVo.incidentVo.id = this.currentSelectedIncidentSelectorVo.incidentId;
    } else {
      this.incidentResourceVo.incidentVo.id = this.ddIncident.selectedValue.id;
    }
    if ( this.ddIncAcctCode.selectedValue !== undefined && this.ddIncAcctCode.selectedValue.id > -1 ) {
      this.incidentResourceVo.workPeriodVo.wpDefaultIncidentAccountCodeVo.id =
        this.ddIncAcctCode.selectedValue.id;
    } else {
      this.incidentResourceVo.workPeriodVo.wpDefaultIncidentAccountCodeVo.id = 0;
    }
    this.incidentResourceVo.workPeriodVo.currentAssignmentVo.requestNumber =
      this.resourceForm.get('requestNumber').value;

    if ( this.ddStatusType.selectedValue !== null && this.ddStatusType.selectedValue.code !== '') {
      // this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentStatus
      //  = AssignmentStatusTypeEnum[this.ddStatusType.selectedValue.code];
      if (this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentStatusVo === null) {
        this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentStatusVo =
          <AssignmentStatusVo>{
            id: 0
            , code: ''
            , description: ''
          };
      }
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentStatusVo.code =
        this.ddStatusType.selectedValue.code;
    } else {
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentStatus = null;
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentStatusVo = null;
    }

    this.incidentResourceVo.resourceVo.person = this.resourceForm.get('person').value;
    if ( this.incidentResourceVo.resourceVo.person === true ) {
      this.incidentResourceVo.resourceVo.resourceName = '';
      this.incidentResourceVo.resourceVo.lastName = this.resourceForm.get('lastName').value;
      this.incidentResourceVo.resourceVo.firstName = this.resourceForm.get('firstName').value;
    } else {
      this.incidentResourceVo.resourceVo.resourceName = this.resourceForm.get('resourceName').value;
      this.incidentResourceVo.resourceVo.lastName = '';
      this.incidentResourceVo.resourceVo.firstName = '';
    }
    this.incidentResourceVo.resourceVo.contracted = this.resourceForm.get('contractor').value;
    this.incidentResourceVo.resourceVo.phone = this.resourceForm.get('cellPhone').value;
    this.incidentResourceVo.workPeriodVo.currentAssignmentVo.kindVo.id = this.ddKindType.selectedValue.id;
    this.incidentResourceVo.workPeriodVo.currentAssignmentVo.kindVo.code = this.ddKindType.selectedValue.code;
    this.incidentResourceVo.workPeriodVo.currentAssignmentVo.kindVo.description = this.ddKindType.selectedValue.desc;
    this.incidentResourceVo.resourceVo.organizationVo.id = this.ddUnitType.selectedValue.id;
    if ( this.incidentResourceVo.resourceVo.agencyVo === null ) {
      this.incidentResourceVo.resourceVo.agencyVo = <AgencyVo>{id: 0, agencyCd: ''};
    }
    this.incidentResourceVo.resourceVo.agencyVo.id = this.ddAgencyType.selectedValue.id;
    this.incidentResourceVo.workPeriodVo.ciResourceMobilizationVo.startDateVo.dateString
     = this.dtMobDate.getFormattedDate();
    this.incidentResourceVo.workPeriodVo.ciCheckInDateVo.dateString = this.dtCheckInDate.getFormattedDate();
    this.incidentResourceVo.workPeriodVo.ciCheckInDateVo.timeString = this.resourceForm.get('checkInTime').value;
    this.incidentResourceVo.workPeriodVo.dmReleaseDateVo.dateString = this.dtActualReleaseDate.getFormattedDate();
    this.incidentResourceVo.workPeriodVo.dmReleaseDateVo.timeString = this.resourceForm.get('actualReleaseTime').value;

    if ( this.incidentResourceVo.resourceVo.parentResourceId > 0
            && this.resourceForm.get('person').value === true
            && this.ddLeaderType.selectedValue !== undefined && this.ddLeaderType.selectedValue !== null) {
      this.incidentResourceVo.resourceVo.leaderType =
        this.ddLeaderType.selectedValue.id;
    } else {
      this.incidentResourceVo.resourceVo.leaderType = 0;
    }
  }

  setIncidentResCheckInTabData() {
    this.incidentResourceVo.workPeriodVo.ciFirstWorkDateVo.dateString = this.dtFirstWorkDay.getFormattedDate();
    this.incidentResourceVo.workPeriodVo.ciLengthAtAssignment = this.resourceForm.get('loa').value;
    this.incidentResourceVo.resourceVo.numberOfPersonnel = this.resourceForm.get('numberOfPersonnel').value;
    this.incidentResourceVo.workPeriodVo.dmTentativeDemobCity = this.resourceForm.get('demobCity').value;

    if ( this.ddDemobStateType.selectedValue !== undefined && this.ddDemobStateType.selectedValue.id > -1 ) {
      this.incidentResourceVo.workPeriodVo.dmTentativeDemobStateVo.id =
        this.ddDemobStateType.selectedValue.id;
    } else {
      this.incidentResourceVo.workPeriodVo.dmTentativeDemobStateVo.id = 0;
    }

    if ( this.incidentResourceVo.workPeriodVo.ciTravelMethodVo === undefined
      || this.incidentResourceVo.workPeriodVo.ciTravelMethodVo === null ) {
        this.incidentResourceVo.workPeriodVo.ciTravelMethodVo = <TravelMethodVo>{id : 0};
    }
    if ( this.ddMobTravelMethodType.selectedValue !== undefined && this.ddMobTravelMethodType.selectedValue.id > -1 ) {
      if (this.ddMobTravelMethodType.selectedValue.code === 'A/R' ) {
        this.incidentResourceVo.workPeriodVo.ciTravelMethodVo.code = 'AR';
      } else {
        this.incidentResourceVo.workPeriodVo.ciTravelMethodVo.code =
        this.ddMobTravelMethodType.selectedValue.code;
      }
    } else {
      this.incidentResourceVo.workPeriodVo.ciTravelMethodVo.code = null;
    }
    this.incidentResourceVo.workPeriodVo.ciRentalLocation = this.resourceForm.get('rentalLocation').value;

    if ( this.incidentResourceVo.workPeriodVo.ciArrivalJetPortVo === undefined
      || this.incidentResourceVo.workPeriodVo.ciArrivalJetPortVo === null ) {
        this.incidentResourceVo.workPeriodVo.ciArrivalJetPortVo = <JetPortVo>{id : 0};
    }
    if ( this.ddMobJetportType.selectedValue !== undefined && this.ddMobJetportType.selectedValue.id > -1 ) {
      this.incidentResourceVo.workPeriodVo.ciArrivalJetPortVo.id =
        this.ddMobJetportType.selectedValue.id;
    } else {
      this.incidentResourceVo.workPeriodVo.ciArrivalJetPortVo.id = 0;
    }

    this.incidentResourceVo.resourceVo.other1 = this.resourceForm.get('other1').value;
    this.incidentResourceVo.resourceVo.other2 = this.resourceForm.get('other2').value;
    this.incidentResourceVo.resourceVo.other3 = this.resourceForm.get('other3').value;
    this.incidentResourceVo.workPeriodVo.ciPrePlanningRemarks = this.resourceForm.get('checkInRemarks').value;
  }

  setIncidentResDemobTabData() {
    this.incidentResourceVo.workPeriodVo.dmTentativeReleaseDateVo.dateString = this.dtTentReleaseDate.getFormattedDate();
    this.incidentResourceVo.workPeriodVo.dmTentativeReleaseDateVo.timeString = this.resourceForm.get('tentReleaseTime').value;
    this.incidentResourceVo.workPeriodVo.dmTentativeArrivalDateVo.dateString = this.dtEstArrivalDate.getFormattedDate();
    this.incidentResourceVo.workPeriodVo.dmTentativeArrivalDateVo.timeString = this.resourceForm.get('estArrivalTime').value;
    this.incidentResourceVo.workPeriodVo.dmPlanningDispatchNotified = this.resourceForm.get('dispatchNotifiedTent').value;
    this.incidentResourceVo.workPeriodVo.dmCheckoutFormPrinted = this.resourceForm.get('checkoutFormPrinted').value;
    this.incidentResourceVo.workPeriodVo.dmReAssignable = this.resourceForm.get('availReassign').value;

    if ( this.incidentResourceVo.workPeriodVo.dmTravelMethodVo === null ) {
      this.incidentResourceVo.workPeriodVo.dmTravelMethodVo = <TravelMethodVo>{code: null};
    }
    if ( this.ddDemobTravelMethodType.selectedValue !== undefined && this.ddDemobTravelMethodType.selectedValue.id > -1 ) {
      if (this.ddDemobTravelMethodType.selectedValue.code === 'A/R' ) {
        this.incidentResourceVo.workPeriodVo.dmTravelMethodVo.code = 'AR';
      } else {
        this.incidentResourceVo.workPeriodVo.dmTravelMethodVo.code =
        this.ddDemobTravelMethodType.selectedValue.code;
      }
    } else {
      this.incidentResourceVo.workPeriodVo.dmTravelMethodVo.code = null;
    }

    this.incidentResourceVo.workPeriodVo.dmReleaseDispatchNotified = this.resourceForm.get('dispatchNotifiedActual').value;
    this.incidentResourceVo.workPeriodVo.dmRestOvernight = this.resourceForm.get('restOvernight').value;
    this.incidentResourceVo.workPeriodVo.dmReleaseRemarks = this.resourceForm.get('releaseRemarks').value;
  }

  setIncidentResDemobAirTravTabData() {
    if ( this.incidentResourceVo.workPeriodVo.dmAirTravelVo === undefined) {
      this.incidentResourceVo.workPeriodVo.dmAirTravelVo = <AirTravelVo>{};
    }
    if ( this.incidentResourceVo.workPeriodVo.dmAirTravelVo.jetPortVo === undefined
      || this.incidentResourceVo.workPeriodVo.dmAirTravelVo.jetPortVo === null ) {
        this.incidentResourceVo.workPeriodVo.dmAirTravelVo.jetPortVo = <JetPortVo>{id : 0};
    }
    if ( this.ddAirJetportType.selectedValue !== undefined && this.ddAirJetportType.selectedValue.id > -1 ) {
      this.incidentResourceVo.workPeriodVo.dmAirTravelVo.jetPortVo.id =
        this.ddAirJetportType.selectedValue.id;
    } else {
      this.incidentResourceVo.workPeriodVo.dmAirTravelVo.jetPortVo.id = 0;
    }

    this.incidentResourceVo.resourceVo.nameOnPictureId = this.resourceForm.get('nameOnPictureId').value;
    this.incidentResourceVo.workPeriodVo.dmAirTravelVo.dispatchNotified = this.resourceForm.get('airTravelDispatch').value;
    this.incidentResourceVo.workPeriodVo.dmAirTravelVo.itineraryReceived = this.resourceForm.get('itineraryReceived').value;
    this.incidentResourceVo.workPeriodVo.dmAirTravelVo.airline = this.resourceForm.get('airline').value;
    this.incidentResourceVo.workPeriodVo.dmAirTravelVo.flightNumber = this.resourceForm.get('flightNumber').value;
    this.incidentResourceVo.workPeriodVo.dmAirTravelVo.flightTime = this.resourceForm.get('flightTime').value;
    this.incidentResourceVo.workPeriodVo.dmAirTravelVo.hoursToAirport = this.resourceForm.get('hoursToAirport').value;
    this.incidentResourceVo.workPeriodVo.dmAirTravelVo.minutesToAirport = this.resourceForm.get('minutesToAirport').value;
    this.incidentResourceVo.workPeriodVo.dmAirTravelVo.leaveTime = this.resourceForm.get('icpLeaveTime').value;
    this.incidentResourceVo.workPeriodVo.dmAirTravelVo.remarks = this.resourceForm.get('airRemarks').value;

  }

  setIncidentResTimeTabData() {
    if ( this.resourceForm.get('contractor').value === true ) {
      if ( this.ddContractor.selectedValue.id > 0 ) {
        this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo
          .contractorPaymentInfoVo.contractorVo.id = this.ddContractor.selectedValue.id;
      }
      if ( this.ddAgreement.selectedValue.id > 0 ) {
        this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo
          .contractorPaymentInfoVo.contractorAgreementVo.id = this.ddAgreement.selectedValue.id;
      }
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType =
        EmploymentTypeEnum.CONTRACTOR;
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.ofRemarks =
        this.resourceForm.get('of286Remarks').value;
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.vinName =
        this.resourceForm.get('uniqueName').value;
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.desc1 =
        this.resourceForm.get('desc1').value;
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.desc2 =
        this.resourceForm.get('desc2').value;
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.hiredDateVo.dateString =
        this.dtHiredDate.getFormattedDate();
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.hiredDateVo.timeString =
        this.resourceForm.get('hiredTime').value;
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.pointOfHire =
        this.resourceForm.get('pointOfHire').value;
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.operation =
        this.resourceForm.get('govOperator').value;
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.supplies =
        this.resourceForm.get('govSupplies').value;
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.withdrawn =
        this.resourceForm.get('govWithdrawn').value;
     } else {
      if ( this.resourceForm.get('person').value === true ) {
        if ( this.ddEmploymentType.selectedValue !== undefined
              && this.ddEmploymentType.selectedValue !== null
              && this.ddEmploymentType.selectedValue.code !== '') {
          const empType = this.ddEmploymentType.selectedValue.code;
          switch (empType) {
            case 'AD':
                this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType =
                  EmploymentTypeEnum.AD;
                this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.adPaymentInfoVo.eci =
                  this.resourceForm.get('eci').value;
                this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.adPaymentInfoVo.pointOfHireOrgVo.id =
                  this.ddPointOfHire.selectedValue.id;
                this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.adPaymentInfoVo.rateClassRateVo.id =
                  this.ddAdClass.selectedValue.id;
              break;
            case 'FED':
                this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType =
                EmploymentTypeEnum.FED;
              break;
            case 'OTHER':
                this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType =
                EmploymentTypeEnum.OTHER;
                this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.otherDefaultRate =
                this.resourceForm.get('otherRate').value;
              break;
          }
          this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.hiringUnitName
             = this.resourceForm.get('hiringUnitName').value;
          this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.hiringUnitPhone
             = this.resourceForm.get('hiringUnitPhone').value;
          this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.hiringUnitFax
             = this.resourceForm.get('hiringUnitFax').value;
        } else {
          this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType = null;
        }
      } else {
        this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType = null;
      }
    }
  }

  setIncidentResCostTabData() {
    if ( this.incidentResourceVo.costDataVo === undefined
          || this.incidentResourceVo.costDataVo === null) {
      this.incidentResourceVo.costDataVo = <CostDataVo>{id: 0};
    }
    if ( this.incidentResourceVo.costDataVo.paymentAgencyVo === undefined
            || this.incidentResourceVo.costDataVo.paymentAgencyVo === null ) {
      this.incidentResourceVo.costDataVo.paymentAgencyVo = <AgencyVo>{id: 0};
    }
    if ( this.ddPaymentAgencyType.selectedValue !== undefined
        && this.ddPaymentAgencyType.selectedValue.code !== '' ) {
          this.incidentResourceVo.costDataVo.paymentAgencyVo.id = this.ddPaymentAgencyType.selectedValue.id;
          this.incidentResourceVo.costDataVo.paymentAgencyVo.agencyCd = this.ddPaymentAgencyType.selectedValue.code;
    } else {
      this.incidentResourceVo.costDataVo.paymentAgencyVo = null;
    }

    if ( this.incidentResourceVo.costDataVo.accrualCodeVo === undefined
      || this.incidentResourceVo.costDataVo.accrualCodeVo === null ) {
        this.incidentResourceVo.costDataVo.accrualCodeVo = <AccrualCodeVo>{id: 0};
    }
    if ( this.ddAccrualCodeType.selectedValue !== undefined
          && this.ddAccrualCodeType.selectedValue.code !== '' ) {
            this.incidentResourceVo.costDataVo.accrualCodeVo.id = this.ddAccrualCodeType.selectedValue.id;
            this.incidentResourceVo.costDataVo.accrualCodeVo.code = this.ddAccrualCodeType.selectedValue.code;
    } else {
        this.incidentResourceVo.costDataVo.accrualCodeVo = null;
    }

    this.incidentResourceVo.costDataVo.assignDateVo.dateString = this.dtAssignDate.getFormattedDate();

    this.incidentResourceVo.costDataVo.accrualLocked = this.resourceForm.get('accrualLocked').value;
    this.incidentResourceVo.costDataVo.generateCosts = this.resourceForm.get('generateCosts').value;
    this.incidentResourceVo.costDataVo.useAccrualsOnly = this.resourceForm.get('actualsOnly').value;
    this.incidentResourceVo.costDataVo.costOther1 = this.resourceForm.get('costOther1').value;
    this.incidentResourceVo.costDataVo.costOther2 = this.resourceForm.get('costOther2').value;
    this.incidentResourceVo.costDataVo.costOther3 = this.resourceForm.get('costOther3').value;
    this.incidentResourceVo.costDataVo.costRemarks = this.resourceForm.get('costRemarks').value;
  }

  openQualWindow(qmode) {
    if ( qmode === 'add' ) {
      this.gridQuals.clearSelected();
      this.selectedQual = null;
      this.qualWindow.loadWindow(null, this.incidentResourceVo.resourceVo.id);
    } else {
      if ( this.selectedQual !== null ) {
        const idx = this.incidentResourceVo.resourceVo.resourceKindVos.findIndex(q => q.id === this.selectedQual.id);
        if ( idx > -1 ) {
          this.qualWindow.loadWindow(this.incidentResourceVo.resourceVo.resourceKindVos[idx], this.incidentResourceVo.resourceVo.id);
        }
      } else {
        // show message select qual
        return;
      }
    }
    this.qualWindow.openModal();
  }

  onSelectQualRow(qualRow) {
    this.selectedQual = qualRow;
  }

  deleteQual() {
    if ( this.selectedQual === null ) {
      // show message
    } else {
      this.gridQuals.removeRowById(this.selectedQual.id);
      const idx = this.qualsList.findIndex(q => q.id === this.selectedQual.id);
      if ( idx > -1 ) { this.qualsList.splice(idx, 1); }
      const idx2 = this.incidentResourceVo.resourceVo.resourceKindVos.findIndex(q => q.id === this.selectedQual.id);
      if ( idx2 > -1 ) { this.incidentResourceVo.resourceVo.resourceKindVos.splice(idx2, 1); }
      this.selectedQual = null;
    }
  }

  addQualEvent(resourceKindVo: ResourceKindVo) {
    this.qualWindow.closeModal();
    const qualRow = {
      id: resourceKindVo.id
      , trainee: resourceKindVo.training
      , itemCode: resourceKindVo.kindVo.code
      , itemName: resourceKindVo.kindVo.description
    };

    if ( resourceKindVo.id > 0 ) {
      const idx = this.qualsList.findIndex(q => q.id === resourceKindVo.id);
      if ( idx > -1 ) { this.qualsList[idx] = qualRow; }
      const idx2 = this.incidentResourceVo.resourceVo.resourceKindVos.findIndex(q => q.id === resourceKindVo.id);
      if ( idx2 > -1 ) { this.incidentResourceVo.resourceVo.resourceKindVos[idx2] = resourceKindVo; }
      this.gridQuals.updateRowById(qualRow);
    } else {
      for (let id = -1; id > -30; id--) {
        const idx = this.qualsList.findIndex(q => q.id === id);
        if ( idx < 0 ) {
          resourceKindVo.id = id;
          qualRow.id = id;
          break;
        }
      }
      this.incidentResourceVo.resourceVo.resourceKindVos.push(resourceKindVo);
      this.qualsList.push(qualRow);
      this.gridQuals.updateRowByStringFieldId(qualRow, 'id');
    }
  }

  onSelectRestOvernightRow(restRow) {
    this.selectedRestOvernight = restRow;
  }

  openRestOvernightWindow(rmode) {
    this.restWindow.loadWindow();
    this.restWindow.openModal();

  }

  removeRestOvernight() {
    if ( this.selectedRestOvernight !== null ) {
      this.gridRestOvernight.removeRowById(this.selectedRestOvernight.id);
      const idx = this.restOvernightList.findIndex(q => q.id === this.selectedRestOvernight.id);
      if ( idx > -1 ) { this.restOvernightList.splice(idx, 1); }
      const idx2 =
        this.incidentResourceVo.workPeriodVo.workPeriodOvernightStayInfoVos.findIndex(q => q.id === this.selectedRestOvernight.id);
      if ( idx2 > -1 ) { this.incidentResourceVo.workPeriodVo.workPeriodOvernightStayInfoVos.splice(idx2, 1); }
      this.selectedRestOvernight = null;
   }
 }

  addRestOvernightEvent(stayInfoVo) {
    this.restWindow.closeModal();
    for (let id = -1; id > -30; id--) {
      const idx = this.restOvernightList.findIndex(q => q.id === id);
      if ( idx < 0 ) {
        stayInfoVo.id = id;
        break;
      }
    }

    const gridRow = {
      id: stayInfoVo.id
      , city: stayInfoVo.city
      , state: stayInfoVo.countrySubdivisionVo.countrySubAbbreviation
    };

    this.incidentResourceVo.workPeriodVo.workPeriodOvernightStayInfoVos.push(stayInfoVo);
    this.restOvernightList.push(gridRow);
    this.gridRestOvernight.updateRowByStringFieldId(gridRow, 'id');

  }

  openContractorWindow() {
    this.contractorWindow.openModal();
    this.contractorWindow.incidentId = this.currentSelectedIncidentSelectorVo.incidentId;
    this.contractorWindow.loadWindow();
  }

  saveContractorEvent(newVo) {
    this.selectedAgreement = this.ddAgreement.getDropdownDataObjectById(-2);
    this.agreementData = [];
    newVo.contractorAgreementVos = [];
    this.contractorWindow.closeModal();
    this.contractorVos.push(newVo);
    this.contractorData.push(
      <DropdownData>{
        id: newVo.id
        , code: newVo.name
        , desc: ''
      }
    );

    this.selectedContractor = this.ddContractor.getDropdownDataObjectById(newVo.id);
  }

  openAgreementWindow() {
    if ( this.ddContractor.selectedValue.id > 0 ) {
      const idx = this.contractorVos.findIndex(d => d.id === this.ddContractor.selectedValue.id);
      if ( idx > -1 ) {
        this.agreementWindow.openModal();
        this.agreementWindow.contractorVo = this.contractorVos[idx];
        setTimeout(() => {
          this.agreementWindow.loadWindow();
        });
        }
    } else {
      // show message
    }
  }

  saveAgreementEvent(newAgreementVo: ContractorAgreementVo) {
    this.agreementWindow.closeModal();

    const idx = this.contractorVos.findIndex(d => d.id === newAgreementVo.contractorVo.id);
    if ( idx > -1 ) {
      this.contractorVos[idx].contractorAgreementVos.push(newAgreementVo);
      const ddata = {
        id: newAgreementVo.id
        , code: newAgreementVo.agreementNumber
      };
      this.agreementData.push(ddata);
      this.selectedAgreement = this.ddAgreement.getDropdownDataObjectById(newAgreementVo.id);
    }
  }

  onSelectContractorRate(vo) {
    if ( vo !== undefined && vo !== null ) {
      this.selectedContractorRateVo = vo;
    } else {
      this.selectedContractorRateVo = null;
    }
  }

  getContractorRateBtnDiv() {
    if ( this.incidentResourceVo.id < 1 ||
          this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.id < 1 ||
          this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.id < 1 ) {
      return 'hidden';
    } else {
      return '';
    }
  }
  deleteContractorRate() {
    if ( this.selectedContractorRateVo !== null ) {
      this.contractorRateService.deleteRate(this.selectedContractorRateVo.id
            , this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.id
            , this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.contractorPaymentInfoVo.id)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'DELETE_CONTRACTOR_RATE') {
          this.gridRates.clearSelected();
          this.gridRates.removeRowById(this.selectedContractorRateVo.id);
          const id = this.selectedContractorRateVo.id;
          this.selectedContractorRateVo = null;
          const idx = this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo
            .contractorPaymentInfoVo.contractorRateVos.findIndex(r => r.id === id);
          if ( idx > -1 ) {
            this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo
              .contractorPaymentInfoVo.contractorRateVos.splice(idx, 1);
          }
        }
      });
    } else {
      this.showPrompt( 'CONTRACTORRATE', 'Resource', 'Please select a Contractor Rate to delete.', 'Ok', '', '');
    }
  }

  openContractorRateWindow(mode) {
    if ( mode === 'add') {
      this.contractorRateWindow.openModal();
      this.contractorRateWindow.incidentResourceVo = this.incidentResourceVo;
        this.gridRates.clearSelected();
      this.selectedContractorRateVo = null;
      this.contractorRateWindow.loadWindow(null);
    } else {
      if ( this.selectedContractorRateVo !== null ) {
        this.contractorRateWindow.openModal();
        this.contractorRateWindow.incidentResourceVo = this.incidentResourceVo;
        this.contractorRateWindow.loadWindow(this.selectedContractorRateVo);
      } else {
        // show message
        this.showPrompt( 'CONTRACTORRATE', 'Resource', 'Please select a Contractor Rate to edit.', 'Ok', '', '');
      }
    }
  }

  saveRateEvent(vo) {
    this.contractorRateWindow.closeModal();
    const idx = this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo
                .contractorPaymentInfoVo.contractorRateVos.findIndex(r => r.id === vo.id);

    if ( idx > -1 ) {
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo
                .contractorPaymentInfoVo.contractorRateVos[idx] = vo;
    } else {
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo
        .contractorPaymentInfoVo.contractorRateVos.push(vo);
    }
    this.gridRates.updateRowById(vo);

  }


}
