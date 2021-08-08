export interface MigrationConfig {
  sourceApiEndpoint: string;
  sourceTodoEndpoint: string;
  destinationApiEndpoint: string;
  destinationTodoEndpoint: string;
  destinationAuthEndpoint: string;
  destinationUsername: string;
  destinationPassword: string;
}