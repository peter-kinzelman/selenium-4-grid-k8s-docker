# Selenium Automation Architecture

This demo that created as a template, demonstrates how to do the settings of the Chrome ve Firefox web driver for distributed parallel testing with Selenium 4 Grid using Kubernetes and Docker.


## Requirements

 - This project requires [Java 8 JDK](https://adoptopenjdk.net/).
 - Install [Docker](https://www.docker.com/) for parallel testing.
 - Install [Kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/), Kubernetes command-line tool. (This item is optional. **kubectl.exe** is added with Docker installation.)
 - Install [Minikube](https://kubernetes.io/docs/setup/learning-environment/minikube/) to run Kubernetes as locally.
 - Specify any VM driver appropriate for your OS. Review the doc for driver list : [https://minikube.sigs.k8s.io/docs/drivers/](https://minikube.sigs.k8s.io/docs/drivers/)
 
## Commands
 
 - Start the minikube.
  ```sh
  $ minikube start
  ```
 - Create the deployment.
 ```sh
 $ kubectl create -f deploy.yml
 ```
 - Check the details of deployment.
 ```sh
 $ kubectl describe deploy.yml
 ```
 - Create the service.
 ```sh
 $ kubectl create -f service.yml
 ```
 - Check the details of services and reach the hub URL in the Endpoints section with the port number in the NodePort section. Add that values in URL methods of **DriverFactory** class.
 ```sh
 $ kubectl describe service
 ```
 - Create the replication controller(s).
 ```sh
 $ kubectl create -f repchrome.yml
 ```
 - Get all created pods.
 ```sh
 $ kubectl get pods
 ```
