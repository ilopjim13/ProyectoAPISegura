package com.example.proyectoAPISegura.repository

import com.example.proyectoAPISegura.model.Historial
import com.example.proyectoAPISegura.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Service

@Service
interface HistorialRepository: JpaRepository<Historial, Long> {

    @Query("SELECT h FROM Historial h JOIN FETCH h.alimento WHERE h.usuario = :usuario ORDER BY h.fecha DESC")
    fun findTop5ByUsuarioOrderByFechaDesc(usuario: Usuario):List<Historial>

}