package com.example.proyectoAPISegura.service

import com.example.proyectoAPISegura.error.exception.BadRequestException
import com.example.proyectoAPISegura.model.Alimento
import com.example.proyectoAPISegura.model.Historial
import com.example.proyectoAPISegura.model.Usuario
import com.example.proyectoAPISegura.repository.HistorialRepository
import com.example.proyectoAPISegura.repository.UsuarioRepository
import org.hibernate.Hibernate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

@Service
class HistorialService {

    @Autowired
    private lateinit var historialRepository: HistorialRepository

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var usuarioRepository:UsuarioRepository

    fun agregarHistorial(alimento: Alimento, usuario: Usuario) {

        val historiales = historialRepository.findAll()

        val historialBd = historiales.find { it.alimento == alimento }

        if (historialBd == null) {
            val historial = Historial(null, usuario, alimento, Timestamp(Date().time))
            historialRepository.save(historial)
        } else {
            historialBd.fecha = Timestamp(Date().time)
            historialRepository.save(historialBd)
        }

    }

    fun getHistoriales(nombre: String, authentication: Authentication):List<Historial> {

        val usuarioBd = usuarioRepository.findByUsername(nombre)

        if (usuarioBd.isEmpty) {
            throw BadRequestException("Este usuario no existe")
        }

        if(!usuarioService.checkUserOrAdmin(authentication, nombre)) {
            throw BadRequestException("No tienes permisos para ver los historiales de este usuario")
        }

        return if (usuarioBd.get().historiales == null) {
            emptyList()
        } else {
            usuarioBd.get().historiales!!
        }

    }

    @Transactional
    fun delete(id:Long, authentication: Authentication) {

        val usuarioBd = usuarioRepository.findByUsername(authentication.name)

        val historialBd = historialRepository.findById(id)

        if (usuarioBd.get().historiales?.contains(historialBd.get()) == true) {
            historialRepository.deleteById(id)
        } else {
            throw BadRequestException("Este historial no esta en tu historial")
        }

    }

    fun getHistorialesDate(fecha:String, authentication: Authentication):List<Historial>{

        val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        try {
            val date = format.parse(fecha)
            val time = Timestamp(date.time)

            val usuarioBd = usuarioRepository.findByUsername(authentication.name)

            if (!usuarioBd.isEmpty) {
                val lista = usuarioBd.get().historiales?.filter { time.toLocalDateTime().toLocalDate() ==  it.fecha.toLocalDateTime().toLocalDate()}
                    if (lista.isNullOrEmpty()) return emptyList()

                    return lista
            } else {
                throw BadRequestException("Este usuario no existe")
            }

        } catch (e:Exception) {
            throw BadRequestException("El formato de la fecha es incorrecto. Formato correcto: dd-MM-yyy")
        }

    }

    @Transactional
    fun obtenerUltimosAlimentos(authentication: Authentication):List<Alimento?> {

        val usuario = usuarioRepository.findByUsername(authentication.name)

        if (usuario.get().historiales?.isEmpty() == true)  throw BadRequestException("No has buscado ningún alimento")

        usuario.get().historiales?.forEach { Hibernate.initialize(it.alimento) }

        return historialRepository.findTop5ByUsuarioOrderByFechaDesc(usuario.get()).map { it.alimento }

    }


}