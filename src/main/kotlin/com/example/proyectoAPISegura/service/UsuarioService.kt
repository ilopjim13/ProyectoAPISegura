package com.example.proyectoAPISegura.service

import com.example.proyectoAPISegura.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UsuarioService: UserDetailsService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = usuarioRepository.findByUsername(username!!).orElseThrow()

        return User.builder()
            .username(usuario.username)
            .password(usuario.password)
            .roles(usuario.roles)
            .build()
    }

    fun checkUserOrAdmin(authentication: Authentication, nombre:String):Boolean {
        authentication.authorities.forEach {
            return it.authority == "SCOPE_ROLE_ADMIN" || authentication.name == nombre
        }
        return false
    }



}