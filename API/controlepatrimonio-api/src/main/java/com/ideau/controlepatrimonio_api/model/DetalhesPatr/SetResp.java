package com.ideau.controlepatrimonio_api.model.DetalhesPatr;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetResp {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idSetResp;
    private String nome;
    private String ativo;  
}