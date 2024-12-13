package com.example.proyectoAPISegura.service

import com.example.proyectoAPISegura.repository.HistorialRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HistorialService {

    @Autowired
    private lateinit var historialRepository: HistorialRepository

}