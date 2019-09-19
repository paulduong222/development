import { AbstractVo } from './abstract-vo';

export interface QuestionVo extends AbstractVo {
    questionType: string;
    question: string;
    standard: boolean;
}
