package com.example.proyectoAPISegura.controller

import com.example.proyectoAPISegura.error.exception.BadRequestException
import com.example.proyectoAPISegura.service.HistorialService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/historial")
class HistorialController {

    @Autowired
    private lateinit var historialService: HistorialService

    @GetMapping("/mostrar/{nombre}")
    fun getHistoriales(
        @PathVariable nombre:String,
        authentication: Authentication
    ) : ResponseEntity<Any>? {

        val historiales = historialService.getHistoriales(nombre, authentication)

        return ResponseEntity(historiales, HttpStatus.OK)

    }

    @GetMapping("/fecha/{fecha}")
    fun getHistorialDate(
        @PathVariable fecha:String,
        authentication: Authentication
    ):ResponseEntity<Any>? {
        val historiales = historialService.getHistorialesDate(fecha, authentication)

        return ResponseEntity(historiales, HttpStatus.OK)
    }

    @GetMapping("/recientes")
    fun alimentosRecientes(
        authentication: Authentication
    ):ResponseEntity<Any>? {

        val alimentos = historialService.obtenerUltimosAlimentos(authentication)

        return ResponseEntity(alimentos, HttpStatus.OK)
    }


    @DeleteMapping("/eliminar/{id}")
    fun eliminarHistorial(
        @PathVariable id:String,
        authentication: Authentication
    ):ResponseEntity<Any>? {

        val idLong = id.toLongOrNull() ?: throw BadRequestException("El id del historial debe de ser un número")

        val alimentos = historialService.delete(idLong, authentication)

        return ResponseEntity(alimentos, HttpStatus.OK)
    }



}