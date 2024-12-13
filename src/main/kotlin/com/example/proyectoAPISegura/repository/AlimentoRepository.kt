package com.example.proyectoAPISegura.repository

import com.example.proyectoAPISegura.model.Alimento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AlimentoRepository: JpaRepository<Alimento, Long> {
}