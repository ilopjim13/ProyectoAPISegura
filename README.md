# PROYECTO API REST SEGURA
## Aplicación de información de alimentos

Para este proyecto he decidido crear una aplicación para gestionar la información de alimentos, en la cual a través del
código de barras del producto se sacará la información del producto, también se podrán agregar o eliminar productos y editarlos.
Por ejemplo se podrán modificar las etiquetas de un producto para ponerle si es sin gluten, sin lactosa, etc.



Para ello he de crear varias tablas, entre ellas:
- La tabla usuarios para que se puedan registrar usuarios y puedan guardar sus alergias para que le salgan filtrados los alimentos
- La tabla alimentos la cual guardará información de todos los alimentos y sus etiquetas con todas las alergias.
- La tabla de historial la cual guardará de cada usuario todos los alimentos que ha buscado


```roomsql
    CREATE TABLE alimentos (
    code BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    marca VARCHAR(255) NOT NULL,
    labels VARCHAR(255) NOT NULL,
    image_url VARCHAR(255),
    busqueda INT NOT NULL DEFAULT 0
    );
```

```roomsql
    CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    roles VARCHAR(255)
    );
```

```roomsql
    CREATE TABLE historial (
    id BIGINT PRIMARY KEY,
    id_usuario BIGINT,
    alimento_id BIGINT,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_historial_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_historial_alimento FOREIGN KEY (alimento_id) REFERENCES alimentos(code) ON DELETE CASCADE
    );
```

```roomsql
    CREATE TABLE usuario_favoritos (
    usuario_id BIGINT NOT NULL,
    alimento_id BIGINT NOT NULL,
    CONSTRAINT pk_usuario_favoritos PRIMARY KEY (usuario_id, alimento_id),
    CONSTRAINT fk_usuario_favoritos_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_usuario_favoritos_alimento FOREIGN KEY (alimento_id) REFERENCES alimentos(code) ON DELETE CASCADE
    );
```


![relacion.png](src%2Fmain%2Fresources%2Frelacion.png)

## Endpoints

Para este proyecto voy a desarrollar varios endpoints por cada tabla:
 
### AlimentoController ("/alimentos")

- GetMapping -> "/informacion/{code}" : En este endpoint el usuario indicará por la path el código de un alimento y este
le devolverá la información del alimento.
  - Si el código que introduce el usuario no es un número dará un error 400
  - En caso de no haber ningún alimento con ese código saltará el error 404 indicando el error.
  - En caso contrario mostrará el alimento con un código 200


- GetMapping -> "/top" : Este endpoint se va a encargar de mostrar los alimentos mas buscados en orden para saber que producto
está de moda.
    - Si no se ha buscado ningún alimento este saltará un error 400 indicando el error.
    - En caso contrario mostrará una lista con los top 5 alimentos más buscados en la aplicación.


- PostMapping -> "/insert" : Desde aquí cualquier usuario autorizado puede agregar un alimento que no exista en la base 
de datos, comprobando que los alimentos no tengan campos vacíos o incorrectos.
  - Si el alimento que se intenta insertar tiene campos importantes vacíos saltará una excepción con el código 400.
  - Si todo está correcto entonces se insertará el producto y mostrará un código 200.


- PostMapping -> "/actualizar" : Los usuarios autorizados podrán actualizar los alimentos que ya existan en la base de datos
comprobando nuevamente que los campos sean correctos.
  - Si el código que introduce el usuario no es un número dará un error 400
  - Si los campos importantes están vacíos saltará un 400.
  - Si todo está correcto se actualizará y mostrará un 201


- DeleteMapping -> "/delete{code}" : En este caso solo los administradores podrán eliminar alimentos de la base de datos
evitando así que cualquier usuario pueda borrarlo todo.
  - Si el código que introduce el usuario no es un número dará un error 400
  - Si el alimento a eliminar no existe dará un error 404 
  - Si no tienes permisos para eliminar saltará un error 400
  - Si todo ha ido bien saltará un mensaje con el código 200

### UsuarioController ("/usuarios")

- PostMapping -> "/register" : Permite a cualquier usuario sin autorizar acceder y registrarse, deberá tener bien los datos 
antes de registrarse y podrá entrar a los demás endpoints con esa autorización.
  - Si el usuario ya existe saltará un error con el código 400
  - Si los campos importantes del usuario están mal saltará el código 400
  - Si todo está bien saltará el código 201


- PostMapping -> "/login" : El login permite a los usuarios guardados en la base de datos autorizarse con sus credenciales
y poder acceder a los demás endpoints.
  - Si el usuario no se ha autenticado bien saltará el error 401
  - Si no se le dará el token y saldrá el código 201


- DeleteMapping -> "/delete" : Permite al propio usuario eliminar su cuenta o al administrador eliminar la cuenta de cualquier
usuario registrado.
  - Si el usuario no tiene permisos para eliminarlo o es otra persona saltará el código 400
  - Si no saldrá el código 200


### HistorialController ("/historial")

- GetMapping -> "/mostrar/{nombre}" : Muestra el historial de las búsquedas realizadas por el usuario que está autorizado.
  - Si el usuario no existe saldrá un 400.
  - Si no tiene permisos para ver los historiales saldrá otro 400.
  - Si todo va bien saldrá un 200.


- GetMapping -> "/fecha/{fecha}" : Permite buscar por fecha los historiales de búsquedas que ha realizado el usuario la 
fecha indicada.
  - Si la fecha introducida es errónea saldrá un error 400.
  - Si el usuario no existe saldrá un error 400.
  - Si todo va bien saldrá un 200.


- GetMapping -> "/recientes" : Muestra los alimentos recientes que ha buscado el usuario en orden por la fecha que se han buscado.
  - Si no hay nada mostrará una lista vacía.
  - Si no mostrará la lista de alimentos con un 200.


- DeleteMapping -> "/eliminar/{id}" : Permite al usuario eliminar una búsqueda de su propio historial.
  - Si el código es erróneo saldrá un 400.
  - Si el historial al que quieres acceder no está en tus historiales saldrá un 400.
  - Si todo va bien saldrá un 200.


### FavoritosController ("/historial")

- PostMapping -> "/agregar/{code}" : Permite al usuario agregar un alimento a favoritos para poder ver su información sin
tener que buscar de nuevo su código.
  - Si el código introducido es erróneo saldrá un 400.
  - Si no se agregará y dará un 200.


- DeleteMapping -> "/eliminar/{code}" : Le permite al usuario eliminar alimentos de su lista de favoritos.
  - Si el código introducido es erróneo saldrá un 400.
  - Si no se eliminará de la lista y dará un 200.


- GetMapping -> "/mostrar" : Muestra la lista de favoritos del usuario que lo ha buscado.
  - Si está vacía saldrá un 400
  - Si no mostrará la lista y dará un 200