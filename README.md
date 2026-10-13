# Alquiler de vehiculos

Es una API para oficinas, clientes, carros, alquileres y mantenimientos.

Cuando se crea un alquiler el carro pasa a `ALQUILADO` y el precio se calcula con los dias por la tarifa diaria. Cuando se termina, el carro vuelve a `DISPONIBLE`.

La hice con Java 21, Spring Boot y MySQL, con JPA para guardar todo.

## Como correrla

Hay que tener MySQL prendido y Java 21.

```bash
export JAVA_HOME="/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"
export DB_PASSWORD=tu_contrasena

cd alquilada
./mvnw spring-boot:run
```

Queda en `http://localhost:1700`.

La base se llama `alquilaya` y se crea sola si MySQL lo permite. El usuario por defecto es `root`. Se puede cambiar con `DB_USER` y `DB_URL`.

## Rutas

- Oficinas: `/api/oficinas`
- Usuarios: `/api/usuarios`
- Vehiculos: `/api/vehiculos`
- Alquileres: `/api/alquileres`
- Mantenimientos: `/api/mantenimientos`

En casi todas se puede listar, ver uno, crear, actualizar y borrar.

Cosas que no son un CRUD normal:

- `GET /api/vehiculos/estado/{estado}` filtra por `DISPONIBLE`, `ALQUILADO` o `MANTENIMIENTO`.
- `PUT /api/vehiculos/{id}/disponible` lo regresa a disponible si no esta alquilado.
- `PUT /api/alquileres/{id}/finalizar` cierra el alquiler y libera el carro.
- Al crear un mantenimiento el carro queda en mantenimiento.

Si la placa o el email ya existen, o el carro no esta disponible, la API responde con un error.

No hay login. La contrasena no se devuelve en las respuestas.

## Ejemplos

Crear una oficina:

```http
POST http://localhost:1700/api/oficinas
Content-Type: application/json

{
  "nombreOficina": "Centro",
  "direccion": "Av. Juarez 10",
  "ciudad": "Ciudad de Mexico",
  "region": "Centro"
}
```

Registrar un cliente:

```http
POST http://localhost:1700/api/usuarios
Content-Type: application/json

{
  "nombreUsuario": "Mario",
  "apellidos": "Diaz",
  "edad": 28,
  "email": "mario@correo.com",
  "rol": "CLIENTE",
  "activo": true
}
```

Registrar un carro:

```http
POST http://localhost:1700/api/vehiculos
Content-Type: application/json

{
  "modelo": "Versa",
  "marca": "Nissan",
  "anio": 2023,
  "tarifaDiaria": 800,
  "placa": "ABC123",
  "estado": "DISPONIBLE",
  "oficina": { "id": 1 }
}
```

Alquilarlo 2 dias:

```http
POST http://localhost:1700/api/alquileres
Content-Type: application/json

{
  "vehiculo": { "id": 1 },
  "cliente": { "id": 1 },
  "fechaInicio": "2026-11-10",
  "fechaFin": "2026-11-12"
}
```

Con tarifa de 800 el precio total queda en 1600 y el estado en `ACTIVO`. Si el carro ya esta alquilado o en mantenimiento, responde que no esta disponible.
