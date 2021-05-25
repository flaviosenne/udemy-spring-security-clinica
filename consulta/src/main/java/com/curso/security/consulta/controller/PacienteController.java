package com.curso.security.consulta.controller;

import com.curso.security.consulta.domain.Paciente;
import com.curso.security.consulta.domain.Usuario;
import com.curso.security.consulta.service.PacienteService;
import com.curso.security.consulta.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/dados")
    public String cadastro(Paciente paciente, ModelMap model, @AuthenticationPrincipal User user){
        paciente = pacienteService.buscarPorUsuarioEmail(user.getUsername());

        if(paciente.hasNotId()){
         paciente.setUsuario(new Usuario(user.getUsername()));

        }
        model.addAttribute("paciente", paciente);
        return "paciente/cadastro";
    }

    @PostMapping("/salvar")
    public String salvar(Paciente paciente, ModelMap model, @AuthenticationPrincipal User user){

        Usuario usuario = usuarioService.buscarPorEmail(user.getUsername());
//        if(UsuarioService.isSenhaCorreta(paciente.getUsuario().getSenha(), user.getUsername())){
            paciente.setUsuario(usuario);
            pacienteService.salvar(paciente);
            model.addAttribute("sucesso","Seus dados foram inseridos com sucesso");

//        }else{
//            model.addAttribute("falha","Sua senha não confere. Tente novamente");
//        }
        return "paciente/cadastro";
    }

    @PostMapping("/editar")
    public String editar(Paciente paciente, ModelMap model, @AuthenticationPrincipal User user){

        Usuario usuario = usuarioService.buscarPorEmail(user.getUsername());
//        if(UsuarioService.isSenhaCorreta(paciente.getUsuario().getSenha(), user.getUsername())){
        pacienteService.editar(paciente);
        model.addAttribute("sucesso","Seus dados foram editados com sucesso");

//        }else{
//            model.addAttribute("falha","Sua senha não confere. Tente novamente");
//        }
        return "paciente/cadastro";
    }
}
