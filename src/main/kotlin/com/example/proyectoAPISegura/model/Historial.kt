package com.example.proyectoAPISegura.model

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "historial")
data class Historial(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    var usuario: Usuario? = null,

    @ManyToOne
    @JoinColumn(name = "alimento_id")
    var alimento: Alimento? = null,

    @Column
    var fecha: Date? = null
)
