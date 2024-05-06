package prova_2.tests;

import org.junit.jupiter.api.Test;
import prova_2.Conexao;

import static org.junit.jupiter.api.Assertions.*;

class ConexaoTest {

    @Test
    void conectaTest(){
        assertNotNull(Conexao.concectaMySql());
    }
}