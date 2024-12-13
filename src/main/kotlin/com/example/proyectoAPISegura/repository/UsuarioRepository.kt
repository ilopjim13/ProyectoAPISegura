package com.example.proyectoAPISegura.repository

import com.example.proyectoAPISegura.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long> {

    fun findByUsername(username:String): Optional<Usuario>

    fun deleteByUsername(username: String)

}