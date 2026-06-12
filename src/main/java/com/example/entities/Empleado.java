package com.example.entities;

import com.example.models.Genero;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//Si quieres cambiarle el nombre a la tabla, si no la tabla se llamara igual que la clase
@Table(name="empleados")
//Dependencias de Lombok
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Empleado {

    //Hacer que un campo sea la primary key, auto incremental y not null en la tabla a crear
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String primerApellido;
    private String segundApellido;

    //Especificar que para que el enum sea los valores escritos que no la posicion (HOMBRE, MUJER, OTRO en vez de 0, 1, 2)
    @Enumerated(EnumType.STRING)
    private Genero genero;
    
}
