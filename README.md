# GESTOR DE RECETAS ELECTRÓNICAS

## 1. Introducción

Aplicación web para la gestión de *recetas electrónicas*. Implementa dos vistas diferentes, ideadas para dos perfiles de usuario distintos:

1. **Administrador**. Perfil técnico, encargado de mantener los datos en el sistema.
2. **Usuario**. Persona encargada de hacer recetas de medicamentos (un *médico*).


## 2. Vista de Administrador

El sistema opera sobre las siguientes entidades:

+ **MEDICAMENTO**. Cada medicamento está relacionado con un *tipo de medicamento*.
+ **TIPO de MEDICAMENTO**
+ **ENFERMEDAD**. Cada enfermedad tiene un conjunto de **medicamentos recomendados**.

La vista de administración permite **añadir**,  **editar** y **eliminar** cada una de estas entidades, estableciendo sus relaciones entre ellas.


## 3. Vista de Usuario

En este caso, la vista es sólo un prototipo para mostrar el tipo de funcionalidad que se podría implementar en el sistema. El propósito de esta vista sería el de crear nuevas recetas.

Una **receta** consiste en una lista de medicamentos.

El sistema utiliza, además, la información de enfermedades y sus medicaciones recomendadas para informar al usuario en caso de que para un conjunto de enfermedades seleccionado (el que sería el cuadro clínico presentado por el enfermo), se intente recetar un medicamento no recomendado.

Quedaría pendiente extender el sistema para poder incluir información de los enfermos con sus respectivos cuadros clínicos. Una vez extendido el sistema, se debería replantear la interfaz.

