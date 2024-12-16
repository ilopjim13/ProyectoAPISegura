package com.example.proyectoAPISegura.controller

import com.example.proyectoAPISegura.model.Usuario
import com.example.proyectoAPISegura.service.TokenService
import com.example.proyectoAPISegura.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/usuarios")
class UsuarioController {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var tokenService: TokenService

    @PostMapping("/register")
    fun register(
        @RequestBody newUsuario: Usuario
    ) : ResponseEntity<Any?>? {
        val usuarioCreado = usuarioService.insertUsuario(newUsuario)

        return ResponseEntity(usuarioCreado, HttpStatus.CREATED)
    }


    @PostMapping("/login")
    fun login(@RequestBody usuario: Usuario): ResponseEntity<Any>? {

        val authentication: Authentication

        try {
            authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(usuario.username, usuario.password))
        } catch (e: AuthenticationException) {
            return ResponseEntity(mapOf("Mensaje" to "Crendenciales incorrectas"), HttpStatus.UNAUTHORIZED)
        }

        var token = ""
        token = tokenService.generateToken(authentication)

        return ResponseEntity(mapOf("token" to token), HttpStatus.CREATED)

    }

    @DeleteMapping("/delete/{nombre}")
    fun delete(
        @PathVariable nombre:String,
        authentication: Authentication
    ) : ResponseEntity<Any>? {

        usuarioService.delete(nombre, authentication)

        return ResponseEntity("Usuario eliminado", HttpStatus.OK)

    }

}