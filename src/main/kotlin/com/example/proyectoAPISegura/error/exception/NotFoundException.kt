package com.example.proyectoAPISegura.error.exception

class NotFoundException(message: String) : RuntimeException("Not Found Exception (404). $message") {
}