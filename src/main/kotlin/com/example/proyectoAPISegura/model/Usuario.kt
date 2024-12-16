package com.example.proyectoAPISegura.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
@JsonIgnoreProperties(value = ["historiales", "favoritos"], ignoreUnknown = true)
data class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    var username: String? = null,

    @Column(nullable = false)
    var password: String? = null,

    var roles: String? = null,

    @ManyToMany
    @JoinTable(
        name = "usuario_favoritos",
        joinColumns = [JoinColumn(name = "usuario_id")],
        inverseJoinColumns = [JoinColumn(name = "alimento_id")]
    )
    @JsonIgnore
    val favoritos: MutableSet<Alimento>? = null,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER ,mappedBy = "usuario", orphanRemoval = false)
    @JsonIgnore
    val historiales: List<Historial>? = null
)