package com.ideau.controlepatrimonio_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideau.controlepatrimonio_api.model.DetalhesPatr.Categoria;
import com.ideau.controlepatrimonio_api.model.DetalhesPatr.Fornecedor;
import com.ideau.controlepatrimonio_api.model.DetalhesPatr.SetResp;
import com.ideau.controlepatrimonio_api.repositories.CategRepository;
import com.ideau.controlepatrimonio_api.repositories.FornecRepository;
import com.ideau.controlepatrimonio_api.repositories.SetRespRepository;
@Service
public class DetalhesPatrServices {
    private final CategRepository categRepository;
    private final FornecRepository fornecRepository;
    private final SetRespRepository setRespRepository;
    DetalhesPatrServices (
        CategRepository categRepository,
        FornecRepository fornecRepository,
        SetRespRepository setRespRepository
    ) {
        this.categRepository = categRepository;
        this.fornecRepository = fornecRepository;
        this.setRespRepository = setRespRepository;
    }

    public Categoria postCategoriaService (Categoria objCategoria) {
        categRepository.save(objCategoria);
        return objCategoria;
    }
    public List<Categoria> getCategoriaService (Integer ativo) {
        if (ativo != null) {
            return categRepository.findAllByAtivo(ativo);
        } else {
            return categRepository.findAll();
        }
    }
    public Categoria updateCategoriaService (Categoria objCategoria) {
        categRepository.save(objCategoria);
        return objCategoria;
    }
    
    public SetResp postSetRespService (SetResp objSetResp) {
        setRespRepository.save(objSetResp);
        return objSetResp;
    }
    public List<SetResp> getSetRespService (Integer ativo) {
        if (ativo != null) {
            return setRespRepository.findAllByAtivo(ativo);
        } else {
            return setRespRepository.findAll();
        }
    }
    public SetResp updateSetRespService (SetResp objSetResp) {
        setRespRepository.save(objSetResp);
        return objSetResp;
    }
    public Fornecedor postFornecedorService (Fornecedor objFornecedor) {
        fornecRepository.save(objFornecedor);
        return objFornecedor;
    }
    public List<Fornecedor> getFornecedorService (Integer ativo) {
        if (ativo != null) {
            return fornecRepository.findAllByAtivo(ativo);
        } else {
            return fornecRepository.findAll();
        }
    }
    public Fornecedor updateFornecedorService (Fornecedor objFornecedor) {
        fornecRepository.save(objFornecedor);
        return objFornecedor;
    }
}
