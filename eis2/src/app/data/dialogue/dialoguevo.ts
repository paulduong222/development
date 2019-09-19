import { CourseOfActionVo } from './courseofactionvo';

export interface DialogueVo {
    serviceCallEventsType ?: string;
    courseOfActionVo ?:  CourseOfActionVo;
    suppliementalCoursesOfAction ?: object;
    processCourseOfActionVos ?: object;
    recordset ?: object;
    resultObject ?: object;
    resultObjectAlternate ?: object;
    resultObjectAlternate2 ?: object;
    resultObjectAlternate3 ?: object;
    resultObjectAlternate4 ?: object;
}
