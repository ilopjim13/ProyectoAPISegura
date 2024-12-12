package com.example.proyectoAPISegura.model

import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
data class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    var username: String? = null,

    @Column(nullable = false)
    var password: String? = null,

    var roles: String? = null,

//    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY,mappedBy = "usuario")
//    val historiales: List<Historial>? = null
)