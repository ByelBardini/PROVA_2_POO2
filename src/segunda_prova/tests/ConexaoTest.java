package segunda_prova.tests;

import org.junit.jupiter.api.Test;
import segunda_prova.Conexao;

import static org.junit.jupiter.api.Assertions.*;

class ConexaoTest {

    @Test
    void conectaTest(){
        assertNotNull(Conexao.concectaMySql());
    }
}