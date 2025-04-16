package com.ideau.controlepatrimonio_api.model.Usuario;

public enum UsuarioCargo {
    DEV("dev"),
    ADMIN("admin"),
    REGISTRADOR("registrador"),
    VISUALIZADOR("visualizador");

    private String cargo;
    
    UsuarioCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }
}

//CORRIGIR!!!!!!

// ✅ Como corrigir o uso de UsuarioCargo com Spring Security e o banco de dados
// 🔎 Situação atual:
// Você está usando um enum chamado UsuarioCargo com valores fixos (DEV, ADMIN, REGISTRADOR, VISUALIZADOR) no seu modelo Usuario, mas o banco já possui uma tabela chamada Cargos com relacionamento (idCargo). O ideal, nesse caso, é trocar o enum por uma entidade JPA para alinhar com o banco de dados.

// ✅ Passo a passo para migrar do enum UsuarioCargo para a entidade Cargo
// 1. 🧱 Crie a entidade Cargo
// java
// Copiar
// Editar
// package com.ideau.controlepatrimonio_api.model.Usuario;

// import jakarta.persistence.*;

// @Entity(name = "Cargos")
// @Table(name = "Cargos")
// public class Cargo {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Integer idCargo;

//     private String nome;

//     private Boolean ativo = true;

//     // Getters e setters
// }
// 2. 🔄 Atualize o modelo Usuario
// Troque o campo UsuarioCargo por Cargo:

// java
// Copiar
// Editar
// @ManyToOne
// @JoinColumn(name = "idCargo", nullable = false)
// private Cargo cargo;
// 3. 🔐 Atualize o método getAuthorities() no Usuario
// java
// Copiar
// Editar
// @Override
// public Collection<? extends GrantedAuthority> getAuthorities() {
//     String nomeCargo = this.cargo.getNome().toUpperCase();

//     return switch (nomeCargo) {
//         case "DEV" -> List.of(
//             new SimpleGrantedAuthority("ROLE_DEV"),
//             new SimpleGrantedAuthority("ROLE_ADMIN"),
//             new SimpleGrantedAuthority("ROLE_REGISTRADOR"),
//             new SimpleGrantedAuthority("ROLE_VISUALIZADOR")
//         );
//         case "ADMIN" -> List.of(
//             new SimpleGrantedAuthority("ROLE_ADMIN"),
//             new SimpleGrantedAuthority("ROLE_REGISTRADOR"),
//             new SimpleGrantedAuthority("ROLE_VISUALIZADOR")
//         );
//         case "REGISTRADOR" -> List.of(
//             new SimpleGrantedAuthority("ROLE_REGISTRADOR"),
//             new SimpleGrantedAuthority("ROLE_VISUALIZADOR")
//         );
//         default -> List.of(new SimpleGrantedAuthority("ROLE_VISUALIZADOR"));
//     };
// }
// ✅ Alternativa: manter enum UsuarioCargo
// Se quiser manter o enum, adicione a anotação:

// java
// Copiar
// Editar
// @Enumerated(EnumType.STRING)
// private UsuarioCargo cargo;
// E deixe a coluna cargo do banco como VARCHAR(20).

// 👀 Observação sobre colunas "extras"
// Caso esteja vendo colunas como id_usuario ou data_criacao sendo criadas com nomes errados, verifique se:

// Os nomes dos atributos batem com os nomes do banco.

// A anotação @Column(name = "nome_exato") está sendo usada caso os nomes sejam diferentes.

// O schema.sql e as entidades estão sincronizados corretamente.

// Se quiser depois, posso montar o script SQL para popular a tabela Cargos com os mesmos nomes dos enums. É só chamar! 💪