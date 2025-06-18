
package com.ideau.controlepatrimonio_api.model.DetalhesPatr;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Categoria")
@Table(name  = "Categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idCategoria;
    private String nome;
    private String ativo;  
}
