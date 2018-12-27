# Getting Started 

This is a step by step guide to get started using the Knapsack Optimizer Service. It also helps in steps for deploying the Knapsack Optimiser Stack.

## Deployment

We can deploy the Knapsack Optimiser stack using docker-compose.yml

The deployment was tested on

1. Docker Version 2.0.0.0-mac81 (29211)
2. Docker Engine: 18.09.0
3. Docker compose: 1.23.2

### Localhost deployment: 
cd to optimization-task directory

```
docker-compose -f docker-compose-prod.yml up
```

## User Guide
Pre requisite: 
[cURL](https://curl.haxx.se/docs/manpage.html)

### Create the task:
Make sure you create a valid task. A valid task will have:
1. Non negative capacity
2. Non negative weights
3. Equal number of indexes on both weight and values.
Create Knapsack task for capacity: 90, weights: [30,40,50], and values:[10,3,3]
POST request

```
curl -X POST \
  http://localhost:8080/knapsack/tasks/ \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{"capacity": 90, "weights": [30, 40, 50], "values": [10, 3, 3]}'
```

sample response
{
    "task": "321afeb8-3e07-4930-b3cd-b418e9adef36",
    "status": "SUBMITTED",
    "timestamps": {
        "submitted": "1545867402",
        "started": null,
        "completed": null
    }
}

You will receive an output as above, see the status of your request is SUBMITTED, you can query the status of task with the "task" field available in the response. The timestamps field indicates the time of submission, the value is in milliseconds from begining of epoch (00:00:00 Thursday, 1 January 1970 UTC).

### Check the status of the task:

Say taskId: 321afeb8-3e07-4930-b3cd-b418e9adef36 for the last job

```
curl -X GET \
  http://localhost:8080/knapsack/tasks/321afeb8-3e07-4930-b3cd-b418e9adef36 \
  -H 'cache-control: no-cache'
 ```
 
 sample response
 
{
    "task": "321afeb8-3e07-4930-b3cd-b418e9adef36",
    "status": "COMPLETED",
    "timestamps": {
        "submitted": "1545867402",
        "started": "1545867412",
        "completed": "1545867412"
    }
}

You will receive an output as above, see the status of your request is "COMPLETED". The timestamps field responds with the time when the task was submitted, started and completed.

### Check the solution of the task:

if you're checking the solution of an un-completed task it will show no task found, and 404 error. But once the task is done we can check it as follows:

```
curl -X GET \
  http://localhost:8080/knapsack/solutions/af8d105f-f16d-4e81-a475-7135411d0d64 \
  -H 'cache-control: no-cache'
```

sample response:

{
    "task": "321afeb8-3e07-4930-b3cd-b418e9adef36",
    "problem": {
        "capacity": 90,
        "weights": [
            30,
            40,
            50
        ],
        "values": [
            10,
            3,
            3
        ]
    },
    "solution": {
        "items": [
            0,
            1
        ],
        "time": 27600
    }
}

You will receive an output as above, see the status of your request is completed. In the field solution we have items which refers to the index of the weights/values being filled in knapsack, and time field which indicates nano seconds of time spends in solving this Knapsack task.

## Admin API 

All admin tasks need authentication, the ADMIN account username and passwords are admin and secret respectively.
###  Show all tasks with there status

```
curl -X GET \
  http://localhost:8080/knapsack/admin/tasks \
  -H 'Authorization: Basic YWRtaW46c2VjcmV0' \
  -H 'cache-control: no-cache'
```

### Delete all tasks

```
curl -X DELETE \
  http://localhost:8080/knapsack/admin/tasks/ \
  -H 'Authorization: Basic YWRtaW46c2VjcmV0' \
  -H 'cache-control: no-cache'
```

### Delete one task

Replace with appropriate taskId, whose information has to be deleted.

```
curl -X DELETE \
  http://localhost:8080/knapsack/admin/tasks/<taskid> \
  -H 'Authorization: Basic YWRtaW46c2VjcmV0' \
  -H 'cache-control: no-cache'
```

### Graceful Shutting down the service

```
curl -X GET \
  http://localhost:8080/knapsack/admin/shutdown \
  -H 'Authorization: Basic YWRtaW46c2VjcmV0' \
  -H 'cache-control: no-cache'
```