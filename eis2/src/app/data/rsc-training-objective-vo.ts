import { AbstractVo } from "./abstract-vo";
import { ResourceTrainingVo } from "./resource-training-vo";

export interface RscTrainingObjectiveVo extends AbstractVo {
    resourceTrainingVo: ResourceTrainingVo;
	objective: string;
	positionNum: number;
}