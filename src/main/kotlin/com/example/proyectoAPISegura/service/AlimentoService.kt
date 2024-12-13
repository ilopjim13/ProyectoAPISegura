package com.example.proyectoAPISegura.service

import com.example.proyectoAPISegura.error.exception.BadRequestException
import com.example.proyectoAPISegura.model.Alimento
import com.example.proyectoAPISegura.model.Historial
import com.example.proyectoAPISegura.repository.AlimentoRepository
import com.example.proyectoAPISegura.repository.HistorialRepository
import com.example.proyectoAPISegura.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class AlimentoService {
    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var historialRepository: HistorialRepository

    @Autowired
    private lateinit var alimentoRepository: AlimentoRepository

    fun findByCode(code:Long, authentication: Authentication): Optional<Alimento> {

        val usuarioBd = usuarioRepository.findByUsername(authentication.name)

        val alimento = alimentoRepository.findById(code)

        if (!usuarioBd.isEmpty && !alimento.isEmpty) {
            val historial = Historial(null, usuarioBd.get(), alimento.get(), Date())
            historialRepository.save(historial)
        }

        return alimento
    }

    fun insertAlimento(alimento: Alimento):Optional<Alimento> {
        if (alimento.name.isBlank() && alimento.marca.isBlank() && alimento.labels.isBlank()) {
            throw BadRequestException("El nombre, la marca y las etiquetas no pueden estar vacías")
        }

        alimentoRepository.save(alimento)

        val alimentoBd = alimentoRepository.findById(alimento.code?:0)

        return alimentoBd
    }

    fun updateAlimento(alimento: Alimento, code: Long):Optional<Alimento> {
        val alimentoBd = alimentoRepository.findById(code)

        if (alimentoBd.isEmpty) {
            throw BadRequestException("No existe este alimento")
        }

        alimentoBd.get().name = alimento.name
        alimentoBd.get().marca = alimento.marca
        alimentoBd.get().labels = alimento.labels
        alimentoBd.get().imageUrl = alimento.imageUrl

        alimentoRepository.save(alimentoBd.get())

        return alimentoBd
    }

    fun deleteAlimento(code: Long) {
        val alimentoBd = alimentoRepository.findById(code)

        if (alimentoBd.isEmpty) throw BadRequestException("No existe este alimento")

        alimentoRepository.deleteById(code)
    }

}