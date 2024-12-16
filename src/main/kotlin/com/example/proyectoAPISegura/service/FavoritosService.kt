package com.example.proyectoAPISegura.service

import com.example.proyectoAPISegura.error.exception.BadRequestException
import com.example.proyectoAPISegura.error.exception.NotFoundException
import com.example.proyectoAPISegura.model.Alimento
import com.example.proyectoAPISegura.model.Usuario
import com.example.proyectoAPISegura.repository.AlimentoRepository
import com.example.proyectoAPISegura.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class FavoritosService{

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository
    @Autowired
    private lateinit var alimentoRepository: AlimentoRepository

    fun addFav(alimentoId: Long, authentication: Authentication): Usuario {
        val usuario = usuarioRepository.findByUsername(authentication.name).orElseThrow { NotFoundException("Usuario no encontrado") }

        val alimento = alimentoRepository.findById(alimentoId).orElseThrow { NotFoundException("Alimento no encontrado") }

        usuario.favoritos?.add(alimento)
        return usuarioRepository.save(usuario)
    }

    fun deleteFav(alimentoId: Long, authentication: Authentication): Usuario {
        val usuario = usuarioRepository.findByUsername(authentication.name).orElseThrow { NotFoundException("Usuario no encontrado") }

        val alimento = alimentoRepository.findById(alimentoId).orElseThrow { NotFoundException("Alimento no encontrado") }

        if (usuario.favoritos?.isEmpty() == true) {
            throw BadRequestException("No tienes ningun alimento en favoritos")
        }

        usuario.favoritos?.remove(alimento)
        return usuarioRepository.save(usuario)
    }

    fun obtenerFav(username: String): MutableSet<Alimento>? {
        val usuario = usuarioRepository.findByUsername(username)

        return usuario.get().favoritos
    }
}
