package com.example.proyectoAPISegura.model

import com.fasterxml.jackson.annotation.JsonManagedReference
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
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY,mappedBy = "alimento", orphanRemoval = false)
    @JsonManagedReference
    val historiales: List<Historial>? = null
)