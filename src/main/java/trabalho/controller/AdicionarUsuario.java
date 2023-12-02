package trabalho.controller;
import trabalho.view.TelaUsuario;
import trabalho.model.PerfilUsuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdicionarUsuario implements ActionListener {

    private TelaUsuario tela;

    public AdicionarUsuario(TelaUsuario tela) {
        this.tela = tela;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nomeUsuario = tela.getNomeUsuario();
        String senha = tela.getSenha();
        PerfilUsuario perfil = tela.getPerfil();
        tela.adicionarUsuario(nomeUsuario, senha, perfil);
    }
}