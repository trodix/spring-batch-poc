import { Job } from "src/models/Job";
import { JobExecutionNotification } from "src/models/JobExecutionNotification";
import { JobNotification } from "src/models/JobNotification";

export interface JobExecution {
  jobExecutionId: number;
  output: JobExecutionNotification[]
}