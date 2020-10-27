# Rebel Alliance API

## Descripción
Aplicacion REST que permite descifrar y hallar el mensaje enviado por una de las naves de la alianza. Haciendo uso de tres satelites en posiciones arbitrarias, la aplicación permite mediante trilateración, calcular la posición aproximada del emisor del mensaje, además, usando el mensaje que recibe cada uno de los satelites descifrar el mensaje que en ocasiones pierde información debido al campo de asteroides y que tiene ciertos desfasajes.

## Herramientas usadas

- **Lenguaje de programación:** Java 8
- **Framework:** Springboot 2.3.4
- **Base de datos:** H2 (SQL) embebida
- **Gestor de versiones - Fuentes:** Git / Github
- **CI / CD:** Github Actions, IBM Cloud docker y kubernetes revisar [Wiki](https:google.com)
- **Cloud deployment con:** [Heroku](https://www.heroku.com/).
- **Enpoint (Para pruebas):** https://afternoon-beyond-22649.herokuapp.com/api/v1/

## Pasos para instalar y ejecutar el proyecto
1. Instalar [JDK 8](https://www.oracle.com/co/java/technologies/javase/javase-jdk8-downloads.html).
2. Configurar variables de entorno JAVA_HOME.
3. Instalar [Maven](https://maven.apache.org/)
4. Configurar variables de entorno M2 / M2_HOME
5. Clonar el protecto: `git clone https://github.com/Juanr14/RebellAllianceApi.git`
6. Instalar dependencias Maven: `mvn install`
7. Compilar paquete: `mvn -B package -Pdev -f pom.xml`
8. Ejecutar aplicación SpringBoot: `java -jar target/rebell-alliance-api-0.0.1-SNAPSHOT.jar`
9. Probar recursos: `http://localhost:8080/api/v1/xxx`



## Capas de la aplicación
- **entity:** En este paquete se alojan las entidadas encargadas de definir el esquema y configuración de las BD.
- **repository:** En este paquete se alojan las interfaces encargadas de gestionar los métodos brindados por el ORM (JPA) para interactuar con la BD. Allí se han definido unas consultas adicionales a las que vienen por defecto en el framework, para los requerimientos establecidos.
- **service:** En este paquete se alojan las interfaces y respectivas implementaciones de los servicios, los cuales se encargan de manejar la lógica de negocio del API.
- **dto:** En este paquete se almacenan los DataTransferObject (DTOs), usados para serializar información en distintas respuestas JSON.
- **controller:** En este paquete se alojan los Controladores Rest, encargados de exponer los recursos REST emplados.
- **config:** Clases encargadas de configurar la aplicación springboot. 


## Manejo del repositorio

 Para el versionamiento del código se utlizaron las siguientes ramas principales:

- **main:** Rama con el código productivo, previamente certificado en ambiente de test, cuenta con el CI/CD para desplegar automaticamente cuando se apruebe un Pull Request o se libere un release.
- **test:** En esta rama se prueba el código que pasara a la rama productiva **main** y que ha sido traido de la rama de develop.
- **develop:** Rama con el código en desarrollo centralizado, allí desembocan todos los feature que se vayan desarrollando, para luego disponerlos en la rama de test para la ejecución de pruebas funcionales.

Para pasar codigo a las ramás principales se hace uso de los Pull Request de modo que cada feature que se vaya integrar se le pueda hacer un seguimiento y previa aprobación antes de hacerle merge con otra rama, además gracias al CI/CD implementado se pueden compilar los desarrollos y dar una garantia adicional de que el codigo esta en buen estado antes de hacer algun merge.

