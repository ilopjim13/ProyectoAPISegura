## PROYECTO API REST SEGURA
# Aplicación de información de alimentos

Para este proyecto he decido crear una aplicación para gestionar la información de alimentos, en la cual a través del
código de barras del producto se sacará la información del producto, también se podrán agregar o eliminar productos y editarlos.
Por ejemplo se podrán modificar las etiquetas de un producto para ponerle si es sin gluten, sin lactosa, etc.



Para ello he de crear varias tablas, entre ellas:
- La tabla usuarios para que se puedan registrar usuarios y puedan guardar sus alergias para que le salgan filtrados los alimentos
- La tabla alimentos la cual guardará información de todos los alimentos y sus etiquetas con todas las alergias.
- La tabla de historial la cual guardará de cada usuario todos los alimentos que ha buscado


```roomsql
    CREATE TABLE alimento (
      code INTEGER PRIMARY KEY,
      name VARCHAR(255),
      marca VARCHAR(255),
      labels VARCHAR(255),
      image_url VARCHAR(255)
    );
```

```roomsql
    CREATE TABLE usuario (
      id INTEGER PRIMARY KEY,
      username VARCHAR(255),
      password VARCHAR(255),
      role VARCHAR(255)
    );
```

```roomsql
    CREATE TABLE historial (
      id INTEGER PRIMARY KEY,
      usuario_id INTEGER,
      alimento_id INTEGER,
      fecha TIMESTAMP,
      FOREIGN KEY (usuario_id) REFERENCES usuario(id),
      FOREIGN KEY (alimento_id) REFERENCES alimento(code)
    );
```


![relacion.png](src%2Fmain%2Fresources%2Frelacion.png)

