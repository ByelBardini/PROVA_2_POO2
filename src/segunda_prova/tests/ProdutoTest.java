package segunda_prova.tests;

import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.*;
import segunda_prova.ProdutoService;
import segunda_prova.model.Produto;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProdutoTest {
    @BeforeAll
    static void limpaTbl() {
        ProdutoService.limpaTblProduto();
    }

    @Test
    @Order(1)
    void insereProdutoInvalidoTest() {
        Produto p = new Produto(1, "kiwi", 1.50, 0);
        assertEquals(-1, ProdutoService.insereProduto(p));
        Produto p2 = new Produto(1, "kiwi", 1.50, -1);
        assertEquals(-1, ProdutoService.insereProduto(p2));
        Produto p3 = new Produto();
        assertEquals(-1, ProdutoService.insereProduto(p3));
        Produto p4 = new Produto();
        p4.setId(1);
        p4.setNome("kiwi");
        p4.setValor(2.3);
        assertEquals(-1, ProdutoService.insereProduto(p4));
    }

    @Test
    @Order(2)
    void insereProdutoValidoTest() {
        Produto p = new Produto(5, "kiwi", 1.50, 1);
        assertEquals(1, ProdutoService.insereProduto(p));
        Produto p2 = new Produto();
        p2.setId(6);
        p2.setNome("kiwi");
        p2.setValor(2.3);
        p2.setEstoque(5);
        assertEquals(1, ProdutoService.insereProduto(p2));
    }

    @Test
    @Order(3)
    void letraAFelizTest() {
        insereListaPadrão();
        ProdutoService.arquivoPraTabela();
        ArrayList<Produto> p = ProdutoService.listAll();
        Produto segundo = p.get(1);
        assertEquals("2-banana-2.0-5", segundo.toString());
    }

    @Test
    @Order(4)
    void letraAfalhaTest() {
        insereListaPadrãoErro();
        ProdutoService.arquivoPraTabela();
        ArrayList<Produto> vazio = new ArrayList<>();
        ArrayList<Produto> produtos = ProdutoService.listAll();
        assertEquals(vazio, produtos);
    }

    @Test
    @Order(5)
    void alteraProdutoExistente() {
        Produto p = new Produto(1, "Limão", 3, 5);
        insereListaPadrão();
        ProdutoService.arquivoPraTabela();
        ArrayList<Produto> produtos = ProdutoService.listAll();
        Produto primeiro = produtos.get(0);
        assertEquals("1-maçã-1.2-5", primeiro.toString());
        assertEquals(1, ProdutoService.alteraProduto(p));
        produtos = ProdutoService.listAll();
        primeiro = produtos.get(0);
        assertEquals("1-Limão-3.0-5", primeiro.toString());

    }

    @Test
    @Order(6)
    void alteraProdutoInexistente() {
        Produto p = new Produto(8, "Limão", 3, 5);
        insereListaPadrão();
        ProdutoService.arquivoPraTabela();
        ArrayList<Produto> produtos = ProdutoService.listAll();
        Produto primeiro = produtos.get(0);
        assertEquals("1-maçã-1.2-5", primeiro.toString());
        assertNotEquals(1, ProdutoService.alteraProduto(p));
        produtos = ProdutoService.listAll();
        primeiro = produtos.get(0);
        assertEquals("1-maçã-1.2-5", primeiro.toString());
    }

    @Test
    @Order(7)
    void letraBFelizTest() {
        insereListaPadrão();
        ProdutoService.arquivoPraTabela();
        ArrayList<Produto> produtos = ProdutoService.listAll();
        Produto segundo = produtos.get(1);
        assertEquals("2-banana-2.0-5", segundo.toString());
        assertEquals(6.0, ProdutoService.vendaProduto(2, 3, produtos));
        segundo = produtos.get(1);
        assertEquals("2-banana-2.0-2", segundo.toString());
        produtos = ProdutoService.listAll();
        segundo = produtos.get(1);
        assertEquals("2-banana-2.0-2", segundo.toString());
        assertEquals(4.0, ProdutoService.vendaProduto(2, 2, produtos));
        segundo = produtos.get(1);
        assertEquals("2-banana-2.0-0", segundo.toString());
        produtos = ProdutoService.listAll();
        segundo = produtos.get(1);
        assertEquals("2-banana-2.0-0", segundo.toString());

    }

    @Test
    @Order(8)
    void letraBErroTest() {
        insereListaPadrão();
        ProdutoService.arquivoPraTabela();
        ArrayList<Produto> produtos = ProdutoService.listAll();
        Produto segundo = produtos.get(1);
        assertEquals("2-banana-2.0-5", segundo.toString());
        assertEquals(0, ProdutoService.vendaProduto(2, 6, produtos));
        segundo = produtos.get(1);
        assertEquals("2-banana-2.0-5", segundo.toString());
        produtos = ProdutoService.listAll();
        segundo = produtos.get(1);
        assertEquals("2-banana-2.0-5", segundo.toString());
    }

    @Test
    @Order(9)
    void escreveArquivoTest() {
        ArrayList<Produto> produtos = new ArrayList<>();
        ArrayList<Produto> arquivo = new ArrayList<>();
        Produto p1 = new Produto(1, "Maçã", 2, 5);
        Produto p2 = new Produto(2, "Limão", 1.5, 5);
        Produto p3 = new Produto(3, "Kiwi", 1.2, 5);
        produtos.add(p1);
        produtos.add(p2);
        produtos.add(p3);
        insereListaPadrão();
        ProdutoService.arquivoPraTabela();
        arquivo = ProdutoService.listAll();
        Produto segundo = arquivo.get(1);
        assertEquals("2-banana-2.0-5", segundo.toString());
        ProdutoService.escreveProdutos(produtos);
        arquivo = ProdutoService.lerProdutos();
        segundo = arquivo.get(1);
        assertEquals("2-Limão-1.5-5", segundo.toString());
    }

    @Test
    @Order(10)
    void letraCFelizTest() {
        insereListaPadrão();
        ProdutoService.arquivoPraTabela();
        limpaLista();
        ArrayList<Produto> produtos = ProdutoService.lerProdutos();
        assertTrue(produtos.isEmpty());
        ProdutoService.tabelaProArquivo();
        produtos = ProdutoService.lerProdutos();
        assertFalse(produtos.isEmpty());

        insereListaPadrãoErro();
        produtos = ProdutoService.lerProdutos();
        assertTrue(produtos.isEmpty());
        ProdutoService.tabelaProArquivo();
        produtos = ProdutoService.lerProdutos();
        assertFalse(produtos.isEmpty());

    }

    @Test
    @Order(11)
    void letraCErroTest() {
        insereListaPadrão();
        ArrayList<Produto> produtos = ProdutoService.lerProdutos();
        ProdutoService.arquivoPraTabela();
        assertFalse(produtos.isEmpty());
        ProdutoService.limpaTblProduto();
        ProdutoService.tabelaProArquivo();
        produtos = ProdutoService.lerProdutos();
        assertFalse(produtos.isEmpty());
    }

    void insereListaPadrão() {
        String padrao = "1,maçã,1.2,5\n" +
                "2,banana,2.0,5\n" +
                "3,uva,1.5,5\n" +
                "4,pera,3.0,5";
        try {
            FileWriter fw = new FileWriter("produtos.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(padrao);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void insereListaPadrãoErro() {
        String padrao = "1,maçã,1.2\n" +
                "2,banana,5\n" +
                "3,1.5,5\n" +
                "pera,3.0,5";
        try {
            FileWriter fw = new FileWriter("produtos.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(padrao);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void limpaLista() {
        String padrao = "";
        try {
            FileWriter fw = new FileWriter("produtos.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(padrao);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}