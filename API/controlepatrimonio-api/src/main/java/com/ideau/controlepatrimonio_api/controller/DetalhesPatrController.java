package com.ideau.controlepatrimonio_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ideau.controlepatrimonio_api.model.DetalhesPatr.Categoria;
import com.ideau.controlepatrimonio_api.model.DetalhesPatr.Fornecedor;
import com.ideau.controlepatrimonio_api.model.DetalhesPatr.SetResp;
import com.ideau.controlepatrimonio_api.services.DetalhesPatrServices;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class DetalhesPatrController {
    
    private final DetalhesPatrServices detalhesPatrServices;
    
    DetalhesPatrController (
        DetalhesPatrServices detalhesPatrServices
    ) {
        this.detalhesPatrServices = detalhesPatrServices;
    }

    @PostMapping("/categoria/")
    public Categoria postCategoria(@RequestBody Categoria objCategoria) {
        return detalhesPatrServices.postCategoriaService(objCategoria);
    }

    @GetMapping("/categoria")
        public List<Categoria> getCategoria(@RequestParam(required = false) Integer ativo) {
            return detalhesPatrServices.getCategoriaService(ativo);
    }
        
    @PutMapping("/categoria/")
    public Categoria putCategoria(@RequestBody Categoria objCategoria) {
        return detalhesPatrServices.updateCategoriaService(objCategoria);
    }

    @PostMapping("/setresp/")
    public SetResp postSetResp(@RequestBody SetResp objSetResp) {
        return detalhesPatrServices.postSetRespService(objSetResp);
    }

    @GetMapping("/setresp")
        public List<SetResp> getSetResp(@RequestParam(required = false) Integer ativo) {
            return detalhesPatrServices.getSetRespService(ativo);
    }
        
    @PutMapping("/setresp/")
    public SetResp putSetResp(@RequestBody SetResp objSetResp) {
        return detalhesPatrServices.updateSetRespService(objSetResp);
    }
    
    @PostMapping("/fornecedor/")
    public Fornecedor postFornecedor(@RequestBody Fornecedor objFornecedor) {
        return detalhesPatrServices.postFornecedorService(objFornecedor);
    }

    @GetMapping("/fornecedor")
        public List<Fornecedor> getFornecedor(@RequestParam(required = false) Integer ativo) {
            return detalhesPatrServices.getFornecedorService(ativo);
    }
        
    @PutMapping("/fornecedor/")
    public Fornecedor putFornecedor(@RequestBody Fornecedor objFornecedor) {
        return detalhesPatrServices.updateFornecedorService(objFornecedor);
    }
}
