package com.pouso.repository;

import com.pouso.model.AdocaoSolicitacao;
import java.time.LocalDate;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdocaoRepository {

    private final JdbcTemplate jdbc;

    public AdocaoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Solicitações que o usuário logado recebeu como doador (dono do pet)
    public List<AdocaoSolicitacao> listarRecebidas(String cpfDono) {
        String sql = """
                SELECT ad.pet_nome, ad.pet_dono, ad.cpf_adotante, adotante.nome AS adotante_nome,
                       adotante_end.rua AS adotante_rua, adotante_end.numero AS adotante_numero,
                       especie.nome AS especie_nome, raca.nome AS raca_nome,
                       pt.sexo, pt.porte, pt.bio, pt.is_castrado, pt.is_permanente,
                       pt.data_nasc, pt.foto_pet,
                       ad.data_inicio, ad.data_solicitacao, ad.status
                FROM adocao ad
                INNER JOIN pessoa adotante ON adotante.cpf = ad.cpf_adotante
                LEFT JOIN endereco adotante_end ON adotante_end.usuario_cpf = ad.cpf_adotante
                INNER JOIN pet pt ON pt.nome = ad.pet_nome AND pt.cpf_dono = ad.pet_dono
                INNER JOIN tipo_pet raca ON raca.id = pt.tipo_pet
                LEFT JOIN tipo_pet especie ON especie.id = raca.tipo_mae
                WHERE ad.pet_dono = ? AND ad.status = 'EM_ANDAMENTO'
                ORDER BY ad.data_solicitacao DESC
            """;

        return jdbc.query(sql, (rs, rowNum) -> new AdocaoSolicitacao(
            rs.getString("pet_nome"),
            rs.getString("pet_dono"),
            rs.getString("cpf_adotante"),
            rs.getString("adotante_nome"),
            formatarEndereco(rs.getString("adotante_rua"), rs.getString("adotante_numero")),
            null,
            null,
            rs.getString("especie_nome"),
            rs.getString("raca_nome"),
            rs.getString("sexo"),
            rs.getString("porte"),
            rs.getString("bio"),
            (Boolean) rs.getObject("is_castrado"),
            (Boolean) rs.getObject("is_permanente"),
            rs.getObject("data_nasc", LocalDate.class),
            rs.getObject("data_inicio", LocalDate.class),
            rs.getObject("data_solicitacao", LocalDate.class),
            rs.getString("status"),
            rs.getString("foto_pet")
        ), cpfDono);
    }

    // Solicitações que o usuário logado realizou como adotante
    public List<AdocaoSolicitacao> listarRealizadas(String cpfAdotante) {
        String sql = """
                SELECT ad.pet_nome, ad.pet_dono, ad.cpf_adotante, dono.nome AS dono_nome,
                       dono_end.rua AS dono_rua, dono_end.numero AS dono_numero,
                       especie.nome AS especie_nome, raca.nome AS raca_nome,
                       pt.sexo, pt.porte, pt.bio, pt.is_castrado, pt.is_permanente,
                       pt.data_nasc, pt.foto_pet,
                       ad.data_inicio, ad.data_solicitacao, ad.status
                FROM adocao ad
                INNER JOIN pessoa dono ON dono.cpf = ad.pet_dono
                LEFT JOIN endereco dono_end ON dono_end.usuario_cpf = ad.pet_dono
                INNER JOIN pet pt ON pt.nome = ad.pet_nome AND pt.cpf_dono = ad.pet_dono
                INNER JOIN tipo_pet raca ON raca.id = pt.tipo_pet
                LEFT JOIN tipo_pet especie ON especie.id = raca.tipo_mae
                WHERE ad.cpf_adotante = ? AND ad.status = 'EM_ANDAMENTO'
                ORDER BY ad.data_solicitacao DESC
            """;

        return jdbc.query(sql, (rs, rowNum) -> new AdocaoSolicitacao(
            rs.getString("pet_nome"),
            rs.getString("pet_dono"),
            rs.getString("cpf_adotante"),
            null,
            null,
            rs.getString("dono_nome"),
            formatarEndereco(rs.getString("dono_rua"), rs.getString("dono_numero")),
            rs.getString("especie_nome"),
            rs.getString("raca_nome"),
            rs.getString("sexo"),
            rs.getString("porte"),
            rs.getString("bio"),
            (Boolean) rs.getObject("is_castrado"),
            (Boolean) rs.getObject("is_permanente"),
            rs.getObject("data_nasc", LocalDate.class),
            rs.getObject("data_inicio", LocalDate.class),
            rs.getObject("data_solicitacao", LocalDate.class),
            rs.getString("status"),
            rs.getString("foto_pet")
        ), cpfAdotante);
    }

    private String formatarEndereco(String rua, String numero) {
        if (rua == null) return null;
        return numero == null ? rua : rua + ", nº " + numero;
    }
}
