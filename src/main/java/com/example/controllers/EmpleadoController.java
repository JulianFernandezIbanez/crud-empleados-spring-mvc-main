package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.services.EmpleadoService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/empleados")
public class EmpleadoController {

	private final EmpleadoService empleadoService;

    @GetMapping("/listar")
    public String  listarempleados(Model model){

		model.addAttribute("empleados", empleadoService.getAllEmpleados());

        return "listadoEmpleados";

    }

}
