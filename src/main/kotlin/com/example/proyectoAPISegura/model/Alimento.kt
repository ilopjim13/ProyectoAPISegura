package com.example.proyectoAPISegura.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "alimentos")
data class Alimento(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val code: Long? = null,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var marca: String,
    @Column(nullable = false)
    var labels: String,
    @Column
    var imageUrl: String? = null,
    @Column(nullable = false)
    var busqueda: Int = 0,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY,mappedBy = "alimento", orphanRemoval = false)
    @JsonIgnore
    val historiales: List<Historial>? = null
)