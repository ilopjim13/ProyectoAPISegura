package com.example.proyectoAPISegura.controller

import com.example.proyectoAPISegura.error.exception.BadRequestException
import com.example.proyectoAPISegura.model.Alimento
import com.example.proyectoAPISegura.service.AlimentoService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/alimentos")
class AlimentoController {

    @Autowired
    private lateinit var alimentoService: AlimentoService

    @GetMapping("/informacion/{code}")
    fun findByCode(
        @PathVariable code:String,
        authentication: Authentication
    ):ResponseEntity<Any>? {

        val codeOk = code.toLongOrNull() ?: throw BadRequestException("El codigo debe ser un número")

        val alimento = alimentoService.findByCode(codeOk, authentication)

        return ResponseEntity(alimento.get(), HttpStatus.OK)

    }

    @GetMapping("/top")
    fun topAlimentos(
        authentication: Authentication
    ):ResponseEntity<Any>? {

        val alimentos = alimentoService.alimentoPopulares(authentication)

        return ResponseEntity(alimentos, HttpStatus.OK)

    }

    @PostMapping("/insert")
    fun insertByCode(
        @RequestBody alimento:Alimento,
        authentication: Authentication
    ):ResponseEntity<Any>? {

        val alimentoAct = alimentoService.insertAlimento(alimento)

        return ResponseEntity(alimentoAct.get(), HttpStatus.CREATED)
    }


    @PostMapping("/actualizar/{code}")
    fun updateByCode(
        @PathVariable code: String,
        @RequestBody alimento:Alimento,
        authentication: Authentication
    ):ResponseEntity<Any>? {

        val codeOk = code.toLongOrNull() ?: throw BadRequestException("El codigo debe ser un número")

        val alimentoAct = alimentoService.updateAlimento(alimento, codeOk)

        return ResponseEntity(alimentoAct.get(), HttpStatus.OK)
    }


    @DeleteMapping("/delete/{code}")
    fun deleteByCode(
        @PathVariable code: String,
        authentication: Authentication
    ):ResponseEntity<Any> {
        val codeOk = code.toLongOrNull() ?: throw BadRequestException("El codigo debe ser un número")

        alimentoService.deleteAlimento(codeOk, authentication)

        return ResponseEntity("Alimento eliminado", HttpStatus.OK)

    }

}