

## KOService

Quick start the deployment

```
docker-compose -f docker-compose-prod.yml up
```
Service is available at http://localhost:8080/.

Use docs/GettingStarted.md to start exploring the webservice interface.

## More documentation available:

1. Design : docs/Design.md
2. User manual: GettingStarted.md
3. Quick Developer Manual: DevelopersGuide.md
4. Quick Deployment Manual: DeploymentGuide.md

### Quick reference on deliverables

* include a concise description of the design (architecture) of your solution  **(Please refer docs/Design.md)**

* include justifications for you choices (especially if you've taken any shortcuts) **(Please refer docs/Design.md)**

* include complete code needed to execute the solution **(Please refer the repo for code and docs/DeveloperGuide for more details)**

* include tests **(code available in repo, refer docs/DeveloperGuide.md for HowTo locate and run them)**

* include a concise instruction on how to test, start and use the solution **(Please refer docs/GettingStarted.md for user manual, and README.md (docker-compose -f docker-compose-prod.yml up) to start application)**

* start your solution as a service on a local machine using Docker or Kubernetes 
**(docker-compose -f docker-compose-prod.yml up)**

* provide a REST API with endpoints to submit optimization tasks asynchnronously (especially important for larger problems that may take substantial time to compute), checking the status of a specific task (submitted, running, completed), and retrieving the solution of a complete task
**(code available in repo, and usage in docs/GettingStarted.md)**

* your solution must be containerized (i.e. it should be executable through Docker. If you have multiple containers you can orchestrate them with either docker-compose or Kubernetes)
**(docker compose available as docker-compose.yml in projects root directory)**

* processes in your containers should handle SIGTERM, so that the container exits cleanly
**(supported, the application on kill will clean up the database connections and other resources before stopping)**

* interact with your solution through the HTTP-based REST API
**(available to do both programmatically (optimization-client) and manually using browser or postman tool using rest api, please refer javadoc for client-api)**

* There should be no requirement to install anything on the host other than what is needed to provide an environment for executing containers (e.g., docker / kubernetes).
**(the application is containerised to be executed with docker using docker-compose)**

