# Microservicios Spring Cloud Eureka & Angular Full-stack

## Sección 3: Backend: Eureka Server registrando microservicios

### 21. Añadiendo librería commons para un service genérico y reutilizar código

## Importante
- Con este proyecto estamos generando una librería que contendrá servicios y controladores genéricos.
- Como será una librería, eliminamos la clase principal ya que no será una aplicación (un ejecutable),
además en el pom.xml eliminamos la etiqueta <build>...</build> que precisamente permite que nuestra
aplicación arranque, pero como eliminamos la clase principal, no será necesario.
- Esta forma de trabajar no tiene nada que ver con microservicios, pero sí con buenas prácticas para 
la reutilización de código, uso de herencia, etc..

## ¿Cómo usarlo?
- **PRIMER PASO**
Para usar esta librería, debemos copiar su **groupId**, **artifactId**, y **version**
y colocarlo en el pom.xml del proyecto que lo requiere. Por ejemplo, en el
microservicios usuarios requerimos hacer uso de esta librería, por lo tanto en su 
pom.xml agregaremos la siguiente dependencia:

```
<dependency>
	<groupId>com.magadiflo.commons</groupId>
	<artifactId>fs-microservicio-commons</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
```

- **SEGUNDO PASO**
Debemos agregar una anotación en la clase de test, ya que Spring busca esta anotación 
cuando se va a crear el .jar, sino agregamos esa anotación nos generará un error y no 
podrá generarse el .jar

```
@SpringBootConfiguration <---------- Importante agregar esta anotación
@SpringBootTest
class FsMicroservicioCommonsApplicationTests {
```

- **TERCER PASO**
Generar el .jar de nuestra librería commons. Para eso nos ubicamos en la raíz del 
proyecto commons y ejecutamos el siguiente comando mediante cmd

```
mvnw clean install
```

