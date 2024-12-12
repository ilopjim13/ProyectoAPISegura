package com.example.proyectoAPISegura.model

import jakarta.persistence.*

@Entity
@Table(name = "alimentos")
data class Alimento(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val code: Long? = null,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val marca: String? = null,
    @Column(nullable = false)
    val labels: String?,
    @Column(nullable = false)
    val imageUrl: String,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY,mappedBy = "alimento")
    val historiales: List<Historial>? = null
)