package com.example.proyectoAPISegura.controller

import com.example.proyectoAPISegura.error.exception.BadRequestException
import com.example.proyectoAPISegura.model.Alimento
import com.example.proyectoAPISegura.service.FavoritosService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/favorito")
class FavoritosController(
    private val favoritosService: FavoritosService
) {

    @PostMapping("/agregar/{code}")
    fun agregarAFavoritos(
        @PathVariable code: String,
        authentication: Authentication
    ): ResponseEntity<String> {
        val codeLong = code.toLongOrNull() ?: throw BadRequestException("El codigo debe de ser un número")
        favoritosService.addFav(codeLong,authentication)
        return ResponseEntity.ok("Alimento agregado a favoritos")
    }

    @DeleteMapping("/eliminar/{code}")
    fun quitarDeFavoritos(
        @PathVariable code: String,
        authentication: Authentication
    ): ResponseEntity<String> {
        val codeLong = code.toLongOrNull() ?: throw BadRequestException("El codigo debe de ser un número")
        favoritosService.deleteFav(codeLong,authentication)
        return ResponseEntity.ok("Alimento eliminado de favoritos")
    }

    @GetMapping("/mostrar")
    fun obtenerFavoritos(
        authentication: Authentication
    ): Set<Alimento>? {
        return favoritosService.obtenerFav(authentication.name)
    }
}
