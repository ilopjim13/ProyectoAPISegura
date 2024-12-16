package com.example.proyectoAPISegura.service

import com.example.proyectoAPISegura.error.exception.BadRequestException
import com.example.proyectoAPISegura.error.exception.NotFoundException
import com.example.proyectoAPISegura.model.Alimento
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
    private lateinit var historialService: HistorialService

    @Autowired
    private lateinit var historialRepository: HistorialRepository

    @Autowired
    private lateinit var alimentoRepository: AlimentoRepository

    fun findByCode(code:Long, authentication: Authentication): Optional<Alimento> {

        val usuarioBd = usuarioRepository.findByUsername(authentication.name)

        val alimento = alimentoRepository.findById(code)

        if (alimento.isEmpty) throw NotFoundException("El existe ningún alimento con ese códgio")

        if (!usuarioBd.isEmpty && !alimento.isEmpty) {
            historialService.agregarHistorial(alimento.get(), usuarioBd.get())
            alimento.get().busqueda++
            alimentoRepository.save(alimento.get())
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

        if (alimento.name.isBlank() && alimento.marca.isBlank() && alimento.labels.isBlank()) {
            throw BadRequestException("El nombre, la marca y las etiquetas no pueden estar vacías")
        }

        alimentoBd.get().name = alimento.name
        alimentoBd.get().marca = alimento.marca
        alimentoBd.get().labels = alimento.labels
        alimentoBd.get().imageUrl = alimento.imageUrl

        alimentoRepository.save(alimentoBd.get())

        return alimentoBd
    }

    fun alimentoPopulares(authentication: Authentication):List<Alimento> {

        val alimentos = alimentoRepository.findAll()

        val top5 = mutableListOf<Alimento>()

        alimentos.sortByDescending { it.busqueda }

        alimentos.forEachIndexed { index, alimento ->
            if (index < 5 && alimento.busqueda > 0) {
                top5.add(alimento)
            }
        }

        if (top5.isEmpty()) throw BadRequestException("No se ha buscado ningún alimento")

        return top5

    }

    fun deleteAlimento(code: Long, authentication: Authentication) {
        val usuarioBd = usuarioRepository.findByUsername(authentication.name)

        if (usuarioBd.get().roles == "ADMIN") {
            val alimentoBd = alimentoRepository.findById(code)

            val historialBd = historialRepository.findAll()

            val historialAlimento = historialBd.filter {
                it.alimento == alimentoBd.get()
            }

            if (alimentoBd.isEmpty) throw NotFoundException("No existe este alimento")
            if (historialAlimento.isNotEmpty()) {
                historialAlimento.forEach { historial ->
                    historial.alimento = null
                    historialRepository.save(historial)
                }

            }
            alimentoRepository.deleteById(code)

        } else throw BadRequestException("No tienes permisos para eliminar un alimento")


    }

}