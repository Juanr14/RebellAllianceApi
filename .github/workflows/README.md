# Rebel Alliance API
# Documentacion del CI/CD

## Trasfondo
La tecnología avanza a pasos agigantados y cada vez es más necesario para nuestra industría buscar soluciones más innovadoras y mantenibles a lo largo del tiempo, aquí encaja perfectamente el CI / CD ya que trae multiples beneficios para los equipos de desarrollo e incluso para el cliente. Gracias a este concepto las fases de producción que incluyen el desarrollo, el control de calidad y la entrega, no son definitivas, sino que se repiten de forma automatizada una y otra vez durante todo el proceso de desarrollo a través de un pipeline de continuous delivery.

Además, hay un feedback constante que procede del pipeline, lo que nos permite mejorar el software de forma inmediata tras cada modificación introducida en el código fuente.

## Herramientas usadas

- **Proveedor de servicios cloud:** IBM
- **Contenedores:** Springboot 2.3.4
- **Gestor de contenedores:** Jubernetes
- **Gestor de versiones - Fuentes:** Git / Github
- **Herramienta CI / CD:** Github Actions
- **Enpoint (Para pruebas de kubernetes):** http://159.122.181.162:32554/api/v1/

## Continuous Integration CI
Este pipeline ayuda a ejecutar la compilacion y ejecucion de las pruebas unitarias del codigo cuando se ejecuta un pull request a la rama **main** ayudando a los aprobadores a tener un feedback positivo en caso de que se ejecute correctamente el pipeline o negativo en caso de que falle, evitando despliegues innecesarios.

Para continuous integration se creo el archivo maven.yml: 

```yaml
name: Java CI with Maven

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 1.8
      uses: actions/setup-java@v1
      with:
        java-version: 1.8
    - name: Build with Maven
      run: mvn -B package -Pprod --file pom.xml
```
Como se puede observar este pipeline ejecuta cuando bajo 2 acciones, realizar un pull request a master o realizar un push a master esto se declara usando el comando **on**.

Posteriormente se define ubuntu como sistema operativo donde se ejecutara el job **build** y finalmente se definen los **steps** que definen la version que Java que usaremos y el comando que ejecutaremos en este caso el comando para compilar y ejecutar tests usando mvn.

Este pipeline se agrego solo a main en este caso pero puede ser extrapolado a cada rama principal que tenga el repositorio por ejemplo la rama **test**, simplemente agregandolo en el comando **on** Ej: 

```
on:
  push:
    branches: [ main, test ]
```
## Continous delivery CD
Este pipeline de entrega continua permite abstraer a los desarrolladores e incluso a arquitectura y al cliente los despliegues que se realizan en los ambientes principales, ayudando a recortar tiempos y evitar errores al momento de desplegar el codigo.

Para continous delivery se creo el siguiente archivo ibm.yaml

```yaml
# This workflow will build a docker container, publish it to IBM Container Registry, and deploy it to IKS when a release is created
#
# To configure this workflow:
#
# 1. Ensure that your repository contains a Dockerfile
# 2. Setup secrets in your repository by going to settings: Create ICR_NAMESPACE and IBM_CLOUD_API_KEY
# 3. Change the values for the IBM_CLOUD_REGION, REGISTRY_HOSTNAME, IMAGE_NAME, IKS_CLUSTER, DEPLOYMENT_NAME, and PORT

name: Build and Deploy to IKS

on:
  release:
    types: [created]
  push:
    branches: [ main, deploy ]

# Environment variables available to all jobs and steps in this workflow
env:
  GITHUB_SHA: ${{ github.sha }}
  IBM_CLOUD_API_KEY: ${{ secrets.IBM_CLOUD_API_KEY }}
  IBM_CLOUD_REGION: us-south
  ICR_NAMESPACE: ${{ secrets.ICR_NAMESPACE }}
  REGISTRY_HOSTNAME: us.icr.io
  IMAGE_NAME: rebel-alliance
  IKS_CLUSTER: buaeuslf0rgum8ighsrg
  DEPLOYMENT_NAME: rebel-alliance
  PORT: 8080

jobs:
  setup-build-publish-deploy:
    name: Setup, Build, Publish, and Deploy
    runs-on: ubuntu-latest
    steps:

    - name: Checkout
      uses: actions/checkout@v2

    # Download and Install IBM Cloud CLI
    - name: Install IBM Cloud CLI
      run: |
        curl -fsSL https://clis.cloud.ibm.com/install/linux | sh
        ibmcloud --version
        ibmcloud config --check-version=false
        ibmcloud plugin install -f kubernetes-service
        ibmcloud plugin install -f container-registry

    # Authenticate with IBM Cloud CLI
    - name: Authenticate with IBM Cloud CLI
      run: |
        ibmcloud login --apikey "${IBM_CLOUD_API_KEY}" -r "${IBM_CLOUD_REGION}" -g Default
        ibmcloud cr region-set "${IBM_CLOUD_REGION}"
        ibmcloud cr login

    # Build the Docker image
    - name: Build with Docker
      run: |
        docker build -t "$REGISTRY_HOSTNAME"/"$ICR_NAMESPACE"/"$IMAGE_NAME":"$GITHUB_SHA" \
          --build-arg GITHUB_SHA="$GITHUB_SHA" \
          --build-arg GITHUB_REF="$GITHUB_REF" .

    # Push the image to IBM Container Registry
    - name: Push the image to ICR
      run: |
        docker push $REGISTRY_HOSTNAME/$ICR_NAMESPACE/$IMAGE_NAME:$GITHUB_SHA

    # Deploy the Docker image to the IKS cluster
    - name: Deploy to IKS
      run: |
        ibmcloud ks cluster config --cluster $IKS_CLUSTER
        kubectl config current-context
        kubectl create deployment $DEPLOYMENT_NAME --image=$REGISTRY_HOSTNAME/$ICR_NAMESPACE/$IMAGE_NAME:$GITHUB_SHA --dry-run -o yaml > deployment.yaml
        kubectl apply -f deployment.yaml
        kubectl rollout status deployment/$DEPLOYMENT_NAME
        kubectl create service loadbalancer $DEPLOYMENT_NAME --tcp=80:$PORT --dry-run -o yaml > service.yaml
        kubectl apply -f service.yaml
        kubectl get services -o wide
```
Este pipeline se dispara cuando se genera un release o cuando se hace un push a la rama master, de este modo la aplicación despliega automaticamente en ambiente productivo y notifica si hay errores al momento de desplegar para que se corrijan de manera inmediata. En este pundo se ejecutan los siguientes steps:

- Se instala el CLI de IBM Cloud 
- Usando el IBM_CLOUD_API_KEY y el IBM_CLOUD_REGION el usuario se autentica con la consola
- Se construye la imagen docker usando el dockerfile del repositorio
- Se realiza el push de la imagen al IBM Cloud Container
- Finalmente se despliega la imagen docker generada al IBM Cloud Kubernetes Service cluster


## Consideraciones:
- Esta aplicacion fue desplegada en etapas tempranas del desarrollo, por lo que solo cuenta con el endpoint post **/test** que recibe cualquier body y retorna el ambiente actual y un endpoint **/topsecret** que responde el mismo body que se le envia.
- Debido a un problema con la cuenta de IBM donde se desplego el kubernetes no se pudo desplegar la versión final en este endpoint, este error se puede evidenciar si se revisan los actions donde se muestra que hay un error al autenticarse con IBMCloud ya que el usuario esta bloqueado.
- Como se puede observar se expone la ip publica del cluster y el puerto que se expuso para la aplicacion, pero usa el protocolo http lo que es inseguro, esto es una limitante el cluster gratuito ya que se debería crear un Ingress para exponer la aplicacion que queramos y poder aplicar un protcolo de seguridad.


