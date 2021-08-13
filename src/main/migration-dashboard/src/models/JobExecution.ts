import { JobExecutionNotification } from "src/models/JobExecutionNotification";

export interface JobExecution {
  jobExecutionId: number;
  output: JobExecutionNotification[]
}