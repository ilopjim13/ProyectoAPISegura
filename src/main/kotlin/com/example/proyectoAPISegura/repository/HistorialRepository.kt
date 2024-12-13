package com.example.proyectoAPISegura.repository

import com.example.proyectoAPISegura.model.Historial
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
interface HistorialRepository: JpaRepository<Historial, Long> {
}