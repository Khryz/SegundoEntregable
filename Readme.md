En este segundo entregable se encuentran las peticiones de postman y el proyecto desarrollado durante las clases:

Peticiones postman:
  POST - /api/createUser - Params:user, pass:  Crean a un usuario
  POST - /api/createUsers - requestBody: Crea multiples usuarios
  
  GET - /api/login - Params: user,pass: Inicia sesion
  GET - /api/getCredentials - Params: user, pass, fecha: Obtiene las credenciales del usuario
  GET - /api/getAccounts: Obtiene las cuentas hardcodeadas
  GET - /api/getAccountsGroupByType: Ordena las cuentas creadas por grupos
  
 Proyecto:
  Para hacer mas facil la busqueda de cada cosa referenciada en la tabla he puesto comentarios relacionados en el código del proyecto,
  para buscar en todo el proyecto basta con presionar: Ctrl + Shift + F (Esto en intelliJIdea), podemos buscar los siguientes comentarios
  y debajo de ellos se encuentra el ejemplo relacionado:


  Herencia en alguna clase: LearningJava
  Sobrecarga de almenos un metodo: login
  Sobrecarga de almenos un constructor: ResponseDTO
  
  Encapsulacion de almenos una clase: Clase BankAccount
  Encapsulacion de almenos una clase: Clase ResponseDTO
  Encapsulacion de almenos una clase: Clase UserDTO

  Clase interna dentro de otra clase: MiResponseDTOBuilder

  Uso de por lo menos una interfaz propia: BankAccountBO
  Uso de por lo menos una interfaz propia: UserBO
  Uso de por lo menos una interfaz propia: Notifications

  Uso de por lo menos dos tipos de datos primitivos: long, double y boolean

  Uso de dos tipos de datos encapsulados: long, String, double, boolean

  Uso de por lo menos un tipo de dato abstracto: List

  Uso de por lo menos una expresión regular: Exp reg valida contraseña

  Uso de por lo menos un arreglo: JSONArray

  Uso de por lo menos un mapa: Mapa para agrupar cuentas

  Uso de por lo menos una lista: Lista de cuentas obtenidas

  Uso de api de fechas y tiempos en un método

  Concurrencia usando 3 hilos y su ejecución: triplica la creacion de los mismos usuarios

  Uso de por lo menos una excepción de creación propia: ExcepcionGenerica

  Uso de al menos tres anotaciones: Deprecated(sice) //java 11
  Uso de al menos tres anotaciones: Override
  Uso de al menos tres anotaciones: SuppressWarnings

  Uso de por lo menos dos beans: Bean BankAccount
  Uso de por lo menos dos beans: Bean ErrorDTO
  Uso de por lo menos dos beans: Bean ResponseDTO
  Uso de por lo menos dos beans: Bean UserDTO

  Patron de diseño creacional: Builder
  Patron de diseño creacional: Factory method
