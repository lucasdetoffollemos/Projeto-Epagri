package com.example.projetoEpagri.Classes;

public class Tutorial {
    int id, introducao_index, criar_propriedade, inserir_piquetes, inserir_animais, conclusao_index;

    public Tutorial(){}

    public Tutorial(int introducao_index, int criar_propriedade, int inserir_piquetes, int inserir_animais, int conclusao_index) {
        this.introducao_index = introducao_index;
        this.criar_propriedade = criar_propriedade;
        this.inserir_piquetes = inserir_piquetes;
        this.inserir_animais = inserir_animais;
        this.conclusao_index = conclusao_index;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIntroducao_index() {
        return introducao_index;
    }

    public void setIntroducao_index(int introducao_index) {
        this.introducao_index = introducao_index;
    }

    public int getCriar_propriedade() {
        return criar_propriedade;
    }

    public void setCriar_propriedade(int criar_propriedade) {
        this.criar_propriedade = criar_propriedade;
    }

    public int getInserir_piquetes() {
        return inserir_piquetes;
    }

    public void setInserir_piquetes(int inserir_piquetes) {
        this.inserir_piquetes = inserir_piquetes;
    }

    public int getInserir_animais() {
        return inserir_animais;
    }

    public void setInserir_animais(int inserir_animais) {
        this.inserir_animais = inserir_animais;
    }

    public int getConclusao_index() {
        return conclusao_index;
    }

    public void setConclusao_index(int conclusao_index) {
        this.conclusao_index = conclusao_index;
    }
}
