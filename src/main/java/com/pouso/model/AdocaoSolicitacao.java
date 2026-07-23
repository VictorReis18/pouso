package com.pouso.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class AdocaoSolicitacao {

    private String petNome;
    private String cpfDono;
    private String cpfAdotante;
    private String adotanteNome;
    private String adotanteEndereco;
    private String donoNome;
    private String donoEndereco;
    private String especieNome;
    private String racaNome;
    private String sexo;
    private String porte;
    private String bio;
    private Boolean isCastrado;
    private Boolean isPermanente;
    private LocalDate dataNasc;
    private LocalDate dataInicio;
    private LocalDate dataSolicitacao;
    private String status;
    private String fotoPet;

    public AdocaoSolicitacao(String petNome, String cpfDono, String cpfAdotante, String adotanteNome,
                              String adotanteEndereco, String donoNome, String donoEndereco,
                              String especieNome, String racaNome, String sexo, String porte, String bio,
                              Boolean isCastrado, Boolean isPermanente, LocalDate dataNasc,
                              LocalDate dataInicio, LocalDate dataSolicitacao, String status, String fotoPet) {
        this.petNome = petNome;
        this.cpfDono = cpfDono;
        this.cpfAdotante = cpfAdotante;
        this.adotanteNome = adotanteNome;
        this.adotanteEndereco = adotanteEndereco;
        this.donoNome = donoNome;
        this.donoEndereco = donoEndereco;
        this.especieNome = especieNome;
        this.racaNome = racaNome;
        this.sexo = sexo;
        this.porte = porte;
        this.bio = bio;
        this.isCastrado = isCastrado;
        this.isPermanente = isPermanente;
        this.dataNasc = dataNasc;
        this.dataInicio = dataInicio;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
        this.fotoPet = fotoPet;
    }

    public String getPetNome() { return petNome; }
    public String getCpfDono() { return cpfDono; }
    public String getCpfAdotante() { return cpfAdotante; }
    public String getAdotanteNome() { return adotanteNome; }
    public String getAdotanteEndereco() { return adotanteEndereco; }
    public String getDonoNome() { return donoNome; }
    public String getDonoEndereco() { return donoEndereco; }
    public String getEspecieNome() { return especieNome; }
    public String getRacaNome() { return racaNome; }
    public String getSexo() { return sexo; }
    public String getPorte() { return porte; }
    public String getBio() { return bio; }
    public Boolean getIsCastrado() { return isCastrado; }
    public Boolean getIsPermanente() { return isPermanente; }
    public LocalDate getDataNasc() { return dataNasc; }
    public LocalDate getDataInicio() { return dataInicio; }
    public LocalDate getDataSolicitacao() { return dataSolicitacao; }
    public String getStatus() { return status; }
    public String getFotoPet() { return fotoPet; }

    public boolean isAguardandoResposta() { return "EM_ANDAMENTO".equals(status); }

    public String getTitulo() {
        return especieNome != null ? especieNome : racaNome;
    }

    // id seguro pra usar em atributos HTML (sem espaço/acento)
    public String getIdHtml() {
        return (petNome + "-" + cpfAdotante + "-" + dataInicio).replaceAll("[^a-zA-Z0-9]", "");
    }

    public String getSexoDescricao() {
        if (sexo == null) return "-";
        return "M".equals(sexo) ? "Macho" : "Fêmea";
    }

    public String getIdadeDescricao() {
        if (dataNasc == null) return "Idade desconhecida";
        Period p = Period.between(dataNasc, LocalDate.now());
        if (p.getYears() > 0) {
            return p.getYears() + (p.getYears() == 1 ? " ano" : " anos");
        }
        int meses = p.getMonths();
        return meses + (meses == 1 ? " mês" : " meses");
    }

    public String getPorteDescricao() {
        if (porte == null) return "-";
        return switch (porte) {
            case "P" -> "Pequeno";
            case "M" -> "Médio";
            case "G" -> "Grande";
            default -> porte;
        };
    }

    public String getDataNascFormatada() {
        return dataNasc == null ? "-" : dataNasc.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getDataSolicitacaoFormatada() {
        return dataSolicitacao == null ? "-" : dataSolicitacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
