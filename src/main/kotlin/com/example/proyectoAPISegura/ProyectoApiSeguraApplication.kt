package com.example.proyectoAPISegura

import com.example.proyectoAPISegura.security.RSAKeysProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RSAKeysProperties::class)
class ProyectoApiSeguraApplication

fun main(args: Array<String>) {
	runApplication<ProyectoApiSeguraApplication>(*args)
}

