package com.example.proyectoAPISegura.service

import com.example.proyectoAPISegura.error.exception.BadRequestException
import com.example.proyectoAPISegura.model.Alimento
import com.example.proyectoAPISegura.repository.AlimentoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AlimentoService {
    @Autowired
    private lateinit var alimentoRepository: AlimentoRepository

    fun findByCode(code:Long): Optional<Alimento> {

        val alimento = alimentoRepository.findById(code)

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