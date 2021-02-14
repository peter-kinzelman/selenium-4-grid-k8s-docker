# Selenium 4 Grid & K8s & Docker

This demo that created as a template, demonstrates how to do the settings of the Chrome ve Firefox web driver for
distributed parallel testing with Selenium 4 Grid using Kubernetes and Docker.

## Requirements

- This project requires [Java 11 JDK](https://adoptopenjdk.net/).
- Install [Docker](https://www.docker.com/) for parallel testing.
- Install [Kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/), Kubernetes command-line tool. (This item
  is optional. **kubectl.exe** is added with Docker installation.)
- Install [Minikube](https://kubernetes.io/docs/setup/learning-environment/minikube/) to run Kubernetes as locally.
- Specify any VM driver appropriate for your OS. Review the doc for driver
  list : [https://minikube.sigs.k8s.io/docs/drivers/](https://minikube.sigs.k8s.io/docs/drivers/)

### Deploying the Kubernetes

```shell

# Deploying all the grid components to kubernetes
$ kubectl apply -f deploy.yml

# Exposing the router
$ kubectl expose deployment selenium-router-deployment --type=NodePort --port=4444

# Get the router URL to access the grid from outside K8s cluster
$ minikube service selenium-router-deployment --url
```
