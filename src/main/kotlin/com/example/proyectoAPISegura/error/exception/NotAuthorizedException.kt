package com.example.proyectoAPISegura.error.exception

class NotAuthorizedException(message: String) : RuntimeException("Not Authorized Exception (401). $message") {
}