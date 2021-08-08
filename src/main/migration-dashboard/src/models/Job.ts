import { JobExecution } from "src/models/JobExecution";

export interface Job {
  name: string;
  jobExecutions: JobExecution[];
  jobNotifications: string[];
}