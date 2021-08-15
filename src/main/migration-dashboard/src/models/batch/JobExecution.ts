import { ExecutionContext } from "src/models/batch/ExecutionContext";
import { ExitStatus } from "src/models/batch/ExitStatus";
import { JobInstance } from "src/models/batch/JobInstance";
import { JobParameters } from "src/models/batch/JobParameters";

export interface JobExecution {
  id: number;
  version: number;
  jobParameters: JobParameters;
  jobInstance: JobInstance;
  status: string;
  startTime: Date;
  createTime: Date;
  endTime: Date;
  lastUpdated: Date;
  exitStatus: ExitStatus;
  executionContext: ExecutionContext;
  failureExceptions: any[];
  jobConfigurationName?: string;
  jobId: number;
  allFailureException: any[];
  stopping: boolean;
  running: boolean;
}