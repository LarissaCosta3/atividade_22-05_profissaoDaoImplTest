/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.entidade.Endereco;
import br.com.senac.entidade.PessoaFisica;
import br.com.senac.entidade.Profissao;
import static br.com.senac.util.GeradorUtil.*;
import com.github.javafaker.Faker;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jessica.santos19
 */
public class ProfissaoDaoImplTest {

    private Profissao profissao;
    private ProfissaoDao profissaoDao;
    private Session sessao;

    public ProfissaoDaoImplTest() {
        profissaoDao = new ProfissaoDaoImpl();
    }

    @Test
    public void testSalvar() {
        Faker faker = new Faker();
        profissao = new Profissao(gerarProfissao(), faker.lorem().sentence());
        sessao = HibernateUtil.abrirConexao();
        profissaoDao.salvarOuAlterar(profissao, sessao);
        sessao.close();
        assertNotNull(profissao.getId());
    }

    //    @Test
    public void testAlterar() {
        System.out.println("alterar");
        Faker faker = new Faker();
        buscarProfissaoBd();
        profissao.setNome(gerarNome());
        profissao.setDescricao(faker.lorem().sentence());
        sessao = HibernateUtil.abrirConexao();
        profissaoDao.salvarOuAlterar(profissao, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirConexao();
        Profissao profiAlt = profissaoDao.pesquisarPorId(profissao.getId(), sessao);
        sessao.close();
        assertEquals(profissao.getNome(), profiAlt.getNome());
        assertEquals(profissao.getDescricao(), profiAlt.getDescricao());
    }

    //@Test
    public void testExcluir() {
        System.out.println("Excluir");
        buscarProfissaoBd();
        sessao = HibernateUtil.abrirConexao();
        profissaoDao.excluir(profissao, sessao);

        Profissao proExc = profissaoDao
                .pesquisarPorId(profissao.getId(), sessao);
        sessao.close();
        assertNull(proExc);
    }

    //    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
        buscarProfissaoBd();
        sessao = HibernateUtil.abrirConexao();
        Profissao profi = profissaoDao.pesquisarPorId(profissao.getId(), sessao);
        sessao.close();
        assertNotNull(profi);
    }

//    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscarProfissaoBd();
        sessao = HibernateUtil.abrirConexao();

        List<Profissao> profissoes = profissaoDao
                .pesquisarPorNome(profissao.getNome(), sessao);
        sessao.close();
        assertTrue(!profissoes.isEmpty());
    }

    public Profissao buscarProfissaoBd() {
        sessao = HibernateUtil.abrirConexao();

        Query<Profissao> consulta = sessao.createQuery("from Profissao p ");
        List<Profissao> profissaos = consulta.list();
        sessao.close();
        if (profissaos.isEmpty()) {
            testSalvar();
        } else {
            profissao = profissaos.get(0);
        }
        return profissao;
    }
}
//crud cartao
//gerar cartao escolhendo um cliente pf ou pj
