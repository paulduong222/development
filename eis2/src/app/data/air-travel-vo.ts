import { AbstractVo } from './abstract-vo';
import { JetPortVo } from './jet-port-vo';

export interface AirTravelVo extends AbstractVo {
    jetPortVo: JetPortVo;
    airline: string;
    dispatchNotified: boolean;
    hoursToAirport: number;
    minutesToAirport: number;
    leaveTime: string;
    flightNumber: string;
    flightTime: string;
    remarks: string;
    itineraryReceived: boolean;
}
