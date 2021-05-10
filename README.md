# Selenium 4 Grid & Kubernetes & Docker

This project demonstrates how to execute the distributed tests parallel via deploying Selenium Grid 4 to a Kubernetes cluster.

## Requirements

- This project requires [Java 11 JDK](https://adoptopenjdk.net/).
- Install [Docker](https://www.docker.com/).
- Install [Kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/), Kubernetes command-line tool. (This item
  is optional. **kubectl.exe** is added with Docker installation.)
- Install [Minikube](https://kubernetes.io/docs/setup/learning-environment/minikube/) to run Kubernetes as locally.
- Specify any VM driver appropriate for your OS. Review the doc for driver
  list : [https://minikube.sigs.k8s.io/docs/drivers/](https://minikube.sigs.k8s.io/docs/drivers/)

### Deploying the Kubernetes

```shell
# Configure the vm-driver
$ minikube config set vm-driver '<vm-driver-name>'

# Start minikube with configured CPU & memory
$ minikube start --cpus 4 --memory 8192

# Deploy all the grid components to kubernetes
$ kubectl apply -f deploy.yml

# Expose the router
$ kubectl expose deployment selenium-router-deployment --type=NodePort --port=4444

# Get the router URL to access the grid from outside K8s cluster
$ minikube service selenium-router-deployment --url

## To access the dashboard
$ minikube dashboard
```
