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
docker-compose up
```

## User Guide
Pre requisite: 
[cURL](https://curl.haxx.se/docs/manpage.html)

1. Create the task:
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
        "submitted": "2018-12-25T12:59:18.944Z",
        "started": null,
        "completed": null
    }
}

You will receive an output as above, see the status of your request is submitted, you can query the status of task with the "task" field available in the response.

2. Check the status of the task:

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
        "submitted": "2018-12-25T12:59:18.944Z",
        "started": "2018-12-25T12:59:42.927Z",
        "completed": "2018-12-25T12:59:42.937Z"
    }
}

You will receive an output as above, see the status of your request is completed. The timestamps field responds with the time when the task was started and completed.

3. Check the solution of the task:

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

You will receive an output as above, see the status of your request is completed. In the field solution we have items which refers to the index of the weights/values being filled in knapsack.

 