package trabalho.model.Tickets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitCategoria {
    private List<Categoria> categorias;

    public InitCategoria() {
        categorias = new ArrayList<>();
        inicializarCategorias();
    }

    private void inicializarCategorias() {

        Categoria categoria = new Categoria(0, "Selecione uma Categoria");
        Subcategoria subcategoria = new Subcategoria(0, "Selecione uma Subcategoria", categoria);

        categoria = new Categoria(1, "Portal do Fabricante");
        subcategoria = new Subcategoria(1, "Informações do Produto", categoria);
        subcategoria = new Subcategoria(2, "Suporte Técnico", categoria);
        subcategoria = new Subcategoria(3, "Garantia e Serviços", categoria);

        categoria = new Categoria(2, "Aguardando Concessionária");
        subcategoria = new Subcategoria(4, "Agendamento de Serviço", categoria);
        subcategoria = new Subcategoria(5, "Status da Solicitação", categoria);
        subcategoria = new Subcategoria(6, "Documentação Pendente", categoria);

        categoria = new Categoria(3, "Limpeza dos Módulos");
        subcategoria = new Subcategoria(7, "Programação de Limpeza", categoria);
        subcategoria = new Subcategoria(8, "Manutenção Preventiva", categoria);
        subcategoria = new Subcategoria(9, "Relatório de Limpeza", categoria);

        categoria = new Categoria(4, "Corte de Vegetação");
        subcategoria = new Subcategoria(10, "Programação de Corte", categoria);
        subcategoria = new Subcategoria(11, "Manutenção de Área", categoria);
        subcategoria = new Subcategoria(12, "Relatório de Corte", categoria);

        categoria = new Categoria(5, "Baixo Desempenho");
        subcategoria = new Subcategoria(13, "Diagnóstico de Problemas", categoria);
        subcategoria = new Subcategoria(14, "Soluções de Melhoria", categoria);
        subcategoria = new Subcategoria(15, "Monitoramento de Desempenho", categoria);

        categoria = new Categoria(6, "Erro na Fatura");
        subcategoria = new Subcategoria(16, "Revisão de Fatura", categoria);
        subcategoria = new Subcategoria(17, "Correção de Erros", categoria);
        subcategoria = new Subcategoria(18, "Histórico de Faturas", categoria);

        categoria = new Categoria(7, "Erro de Cadastro");
        subcategoria = new Subcategoria(19, "Atualização de Dados", categoria);
        subcategoria = new Subcategoria(20, "Verificação de Cadastro", categoria);
        subcategoria = new Subcategoria(21, "Correção de Erros", categoria);

        categoria = new Categoria(8, "Dessincronização entre Portais");
        subcategoria = new Subcategoria(22, "Sincronização de Dados", categoria);
        subcategoria = new Subcategoria(23, "Solução de Problemas de Conexão", categoria);
        subcategoria = new Subcategoria(24, "Atualização de Portal", categoria);

        categoria = new Categoria(9, "Manutenção Elétrica");
        subcategoria = new Subcategoria(25, "Manutenção Preventiva", categoria);
        subcategoria = new Subcategoria(26, "Reparos Elétricos", categoria);
        subcategoria = new Subcategoria(27, "Inspeção de Segurança", categoria);

        categoria = new Categoria(10, "Comunicação");
        subcategoria = new Subcategoria(28, "Atendimento ao Cliente", categoria);
        subcategoria = new Subcategoria(29, "Notificações e Atualizações", categoria);
        subcategoria = new Subcategoria(30, "Feedback e Sugestões", categoria);

        categoria = new Categoria(11, "Manutenção Eletromecânica");
        subcategoria = new Subcategoria(31, "Manutenção Preventiva", categoria);
        subcategoria = new Subcategoria(32, "Reparos Eletromecânicos", categoria);
        subcategoria = new Subcategoria(33, "Inspeção de Segurança", categoria);

        categoria = new Categoria(12, "Dados");
        subcategoria = new Subcategoria(34, "Análise de Dados", categoria);
        subcategoria = new Subcategoria(35, "Relatórios e Métricas", categoria);
        subcategoria = new Subcategoria(36, "Segurança de Dados", categoria);

    }

    public List<Categoria> getCategorias() {
        return this.categorias;
    }

    // public Subcategoria getSubcategoriaPorNome(String nome) {
    //     for (Subcategoria subcategoria : getSubcategorias()) {
    //         if (subcategoria.getNome().equals(nome)) {
    //             return subcategoria;
    //         }
    //     }
    //     return null;
    // }

    // public List<Subcategoria> getSubcategorias() {
    //     List<Subcategoria> subcategorias = new ArrayList<>();
    //     for (Categoria categoria : getCategorias()) {
    //         subcategorias.addAll(categoria.getSubcategorias());
    //     }
    //     return subcategorias;
    // }
}
