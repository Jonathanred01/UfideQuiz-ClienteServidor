# 🧠 UfideQuiz — Sistema Cliente/Servidor en Java

## 📌 Descripción

**UfideQuiz** es un sistema distribuido basado en arquitectura cliente-servidor desarrollado en Java, que implementa un juego interactivo de preguntas y respuestas en tiempo real utilizando sockets TCP/IP.

El sistema permite la conexión de múltiples clientes a un servidor central, encargado de gestionar la lógica del juego, validar respuestas, asignar puntajes y coordinar la interacción entre los participantes.

Este proyecto fue desarrollado como parte del curso SC302, con enfoque en redes, concurrencia y diseño estructurado de sistemas distribuidos.

---

## 🎯 Objetivos del Proyecto

* Implementar comunicación cliente-servidor mediante sockets en Java
* Diseñar un sistema concurrente capaz de manejar múltiples clientes
* Aplicar principios de programación orientada a objetos
* Gestionar lógica de negocio (validación, puntuación, flujo del juego)
* Simular un entorno distribuido básico en tiempo real

---

## ⚙️ Tecnologías Utilizadas

* **Java SE**
* **Sockets TCP/IP**
* **Programación Orientada a Objetos (POO)**
* **Multithreading (Threads)**
* **I/O Streams**

---

## 🏗️ Arquitectura del Sistema

El sistema sigue una arquitectura cliente-servidor clásica:

### 🔹 Servidor

* Escucha conexiones entrantes
* Maneja múltiples clientes simultáneamente mediante hilos
* Envía preguntas
* Valida respuestas
* Calcula y actualiza puntajes
* Controla el flujo del juego

### 🔹 Cliente

* Se conecta al servidor
* Recibe preguntas
* Envía respuestas
* Muestra resultados al usuario

---

## 🔄 Flujo de Ejecución

1. El servidor inicia y queda en estado de escucha
2. Uno o más clientes se conectan al servidor
3. El servidor envía preguntas a los clientes
4. Cada cliente responde en tiempo real
5. El servidor valida las respuestas
6. Se asignan puntos según la respuesta
7. Se actualiza el estado del juego
8. Se continúa el ciclo hasta finalizar

---

## 📂 Estructura del Proyecto

```
UfideQuiz-ClienteServidor/
│
├── servidor/
│   ├── Servidor.java
│   ├── GestorClientes.java
│   └── LogicaJuego.java
│
├── cliente/
│   ├── Cliente.java
│   └── InterfazCliente.java
│
├── modelo/
│   ├── Jugador.java
│   ├── Pregunta.java
│   └── Respuesta.java
│
└── README.md
```

> ⚠️ Nota: La estructura puede variar según la implementación final.

---

## ▶️ Ejecución del Proyecto

### 🔹 1. Compilar

```bash
javac servidor/*.java cliente/*.java modelo/*.java
```

### 🔹 2. Ejecutar servidor

```bash
java servidor.Servidor
```

### 🔹 3. Ejecutar cliente

```bash
java cliente.Cliente
```

---

## 📊 Funcionalidades Principales

* Conexión cliente-servidor mediante sockets
* Manejo concurrente de múltiples jugadores
* Sistema de preguntas y respuestas
* Validación de respuestas en servidor
* Sistema de puntuación dinámico
* Comunicación bidireccional en tiempo real

---

## 🧪 Consideraciones Técnicas

* Uso de hilos para manejar múltiples conexiones simultáneas
* Separación de responsabilidades (cliente, servidor, modelo)
* Manejo de entrada/salida mediante streams
* Sincronización básica en la lógica del juego

---

## 🚀 Mejoras Futuras

* Implementación de interfaz gráfica (JavaFX o Swing)
* Persistencia de datos con base de datos (MySQL / PostgreSQL)
* Sistema de autenticación de usuarios
* Ranking global de jugadores
* Manejo avanzado de sesiones
* Migración a arquitectura REST o WebSockets
* Despliegue en entorno cloud

---

## 📸 Evidencia (Opcional)

*Agregar capturas de pantalla del sistema en ejecución*

---

## 👥 Autores

Proyecto desarrollado por:

* Allan Fauricio Fonseca Batista
* [Agregar compañeros]

---

## 📄 Licencia

Uso académico — Universidad Fidélitas
Curso SC302

---

## 🧠 Reflexión Técnica

Este proyecto permite comprender los fundamentos de los sistemas distribuidos, la comunicación en red mediante sockets y la importancia de diseñar correctamente la concurrencia y el flujo de información en aplicaciones multiusuario.

Más allá de su implementación académica, sienta bases sólidas para el desarrollo de sistemas backend escalables y aplicaciones en tiempo real.

---
