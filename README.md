# ChatApp

El programa fue desarrollado con Java 8, Spring boot, Broker de mensajeria interno de Spring, Javascript y html.

Para poder ejecutarlo es necesario tener:

JRE 8
Maven

Para poder ejecutar la aplicacion se debe correr el siguiente comando,

mvn clean package

En el directorio donde se encuentre el archivo pom.xml

Una vez hecho esto, dirijase a la carpeta target donde va a encontrar un archivo .jar

Ejecutar con linea de comando 

java -jar demo-0.0.1-SNAPSHOT.jar

Una vez hecho esto dirigirse a

http://localhost:8080/

Ingresar un numero bajo el cual se va a conectar y un nick de usuario.

Una vez adentro va a poder utilizar el chat de la sala

![image](https://user-images.githubusercontent.com/38226033/220199988-db9ff1ff-267e-42b6-bbe8-7d631cc41b31.png)



De lado izquierdo figuran los usuarios activos, y la opcion para poder agregar el contacto. Una vez agregado los mensajes van a llegar con el nombre de la persona en vez del numero de telefono en el chat.

Tanto el chat como los contactos quedan guardados en la base de datos h2 que se vuelva a un archivo para su uso futuro en la aplicación.

