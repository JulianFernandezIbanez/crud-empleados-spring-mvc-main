package com.example.services;

import java.util.List;

import com.example.entities.Correo;
import com.example.entities.Empleado;

public interface CorreoService {

    List<Correo> getAllCorreos();
    Correo saveCorreo(Correo correo);
    boolean existsByEmpleado(Empleado empleado);
    void deleteByEmpleado(Empleado empleado);
    List<Correo> findByEmpleado(Empleado empleado);

}
