package segunda_prova;

import segunda_prova.model.Produto;

import java.io.*;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class ProdutoService {
    public static int limpaTblProduto() {
        try {
            Connection conn = Conexao.concectaMySql();

            String sql = "delete from produto where id>-1";

            PreparedStatement pr = conn.prepareStatement(sql);
            int total = pr.executeUpdate();
            pr.executeUpdate();

            conn.close();
            return total;
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Erro de escrita na sintaxe do comando");
            return -1;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Operação viola a integridade do banco");
            return -1;
        } catch (SQLTimeoutException e) {
            System.out.println("Tempo limite excedido");
            return -1;
        } catch (SQLException e) {
            System.out.println("Erro genérico no SQL");
            return -1;
        } catch (Exception e) {
            System.out.println("Erro genérico");
            return -1;
        }
    }

    public static ArrayList<Produto> lerProdutos(){
        ArrayList<Produto> produtos = new ArrayList<>();
        try {
            FileReader fr = new FileReader("produtos.txt");
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 4 && Integer.parseInt(dados[3]) > 0) {
                    Produto p = new Produto(Integer.parseInt(dados[0]), dados[1], Double.parseDouble(dados[2]), Integer.parseInt(dados[3]));
                    produtos.add(p);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (IOException e) {
            System.out.println("Erro de leitura do arquivo");
        } catch (NumberFormatException e) {
            System.out.println("Erro na formatação do arquivo (leitura de id,valor e/ou estoque)");
        } catch (Exception e) {
            System.out.println("Erro genérico");
        }
        return produtos;
    }

    public static int insereProduto(Produto p) {
        if(p.getEstoque()>0 && p.getId()>0 && p.getEstoque()>0 && p.getNome()!=null && p.getValor()>0){
            try {
                Connection conn = Conexao.concectaMySql();

                String sql = "insert into produto (id,nome,valor,estoque) values(?,?,?,?)";

                PreparedStatement pr = conn.prepareStatement(sql);
                pr.setInt(1, p.getId());
                pr.setString(2, p.getNome());
                pr.setDouble(3, p.getValor());
                pr.setInt(4, p.getEstoque());
                int total = pr.executeUpdate();
                conn.close();
                return total;
            } catch (SQLSyntaxErrorException e) {
                System.out.println("Erro de escrita na sintaxe do comando");
                return -1;
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("Operação viola a integridade do banco");
                return -1;
            } catch (SQLTimeoutException e) {
                System.out.println("Tempo limite excedido");
                return -1;
            } catch (SQLException e) {
                System.out.println("Erro genérico no SQL");
                return -1;
            } catch (Exception e) {
                System.out.println("Erro genérico");
                return -1;
            }
        }else{
            return -1;
        }
    }
    public static void arquivoPraTabela(){
        ProdutoService.limpaTblProduto();
        ArrayList<Produto> produtos = ProdutoService.lerProdutos();
        for(Produto p : produtos){
            ProdutoService.insereProduto(p);
        }
    }
    public static int alteraProduto(Produto p) {
        try {
            Connection conn = Conexao.concectaMySql();

            String sql = "update produto set nome=?, valor=?, estoque=? where id=?";

            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, p.getNome());
            pr.setDouble(2, p.getValor());
            pr.setInt(3,p.getEstoque());
            pr.setInt(4,p.getId());
            int total = pr.executeUpdate();
            conn.close();
            return total;
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Erro de escrita na sintaxe do comando");
            return -1;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Operação viola a integridade do banco");
            return -1;
        } catch (SQLTimeoutException e) {
            System.out.println("Tempo limite excedido");
            return -1;
        } catch (SQLException e) {
            System.out.println("Erro genérico no SQL");
            return -1;
        } catch (Exception e) {
            System.out.println("Erro genérico");
            return -1;
        }
    }
    public static double vendaProduto(int idProduto, int qtd, ArrayList<Produto> produtos){
        double retorno = 0;
        for(Produto p: produtos){
            if(p.getId()==idProduto){
                if(p.getEstoque()>=qtd){
                    int aux = p.getEstoque();
                    p.setEstoque(aux-qtd);
                    ProdutoService.alteraProduto(p);
                    retorno = qtd*p.getValor();
                }
            }
        }
        return retorno;
    }
    public static ArrayList<Produto> listAll(){
        ArrayList<Produto> lista = new ArrayList<>();
        try{
            Connection conn = Conexao.concectaMySql();

            String sql = "select * from produto";

            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor((rs.getDouble("valor")));
                p.setEstoque(rs.getInt("estoque"));
                lista.add(p);
            }
            conn.close();
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Erro de escrita na sintaxe do comando");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Operação viola a integridade do banco");
        } catch (SQLTimeoutException e) {
            System.out.println("Tempo limite excedido");
        } catch (SQLException e) {
            System.out.println("Erro genérico no SQL");
        } catch (Exception e) {
            System.out.println("Erro genérico");
        }
        return lista;
    }
    public static void escreveProdutos(ArrayList<Produto> produtos){
        try{
            FileWriter fw = new FileWriter("produtos.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for(Produto p : produtos){
                bw.append(p.toString().replace("-",",")+"\n");
            }
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (IOException e) {
            System.out.println("Erro de leitura do arquivo");
        } catch (NumberFormatException e) {
            System.out.println("Erro na formatação do arquivo (leitura de id,valor e/ou estoque)");
        } catch (Exception e) {
            System.out.println("Erro genérico");
        }
    }
    public static void tabelaProArquivo(){
        ArrayList<Produto> produtos = new ArrayList<>();
        produtos = ProdutoService.listAll();
        if(!produtos.isEmpty()){
            ProdutoService.escreveProdutos(produtos);
        }
    }
}
