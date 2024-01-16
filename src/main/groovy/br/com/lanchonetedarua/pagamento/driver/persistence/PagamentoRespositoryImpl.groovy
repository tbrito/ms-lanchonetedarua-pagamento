package br.com.lanchonetedarua.pagamento.driver.persistence

import br.com.lanchonetedarua.pagamento.domain.model.Pagamento
import br.com.lanchonetedarua.pagamento.domain.model.valueobject.StatusPagamento
import br.com.lanchonetedarua.pagamento.domain.repository.PagamentoRepository

import groovy.transform.CompileStatic
import io.micronaut.transaction.annotation.ReadOnly
import jakarta.inject.Inject
import jakarta.inject.Singleton
import jakarta.persistence.EntityManager
import jakarta.persistence.Query
import jakarta.transaction.Transactional

@CompileStatic
@Singleton
class PagamentoRespositoryImpl implements PagamentoRepository {

    @Inject
    private EntityManager em

    @Override
    @Transactional
    Pagamento criarPagamento(Pagamento pagamento) {
        this.em.persist(pagamento)
        return pagamento
    }

    @Override
    @Transactional
    Pagamento atualizarStatusPagamento(String pagamentoId, StatusPagamento statusPagamento) {
        Pagamento pagamento = this.obterPagamentoPorId(pagamentoId)
        pagamento.status = statusPagamento
        return this.em.merge(pagamento)
    }

    @Override
    @ReadOnly
    Pagamento obterPagamentoPorId(String id) {
        return this.em.find(Pagamento, id)
    }

    @Override
    @ReadOnly
    List<Pagamento> obterPagamentosPorPedido(String pedidoId) {
        String sql = "SELECT p from Pagamento p where p.pedidoId = :pedidoId"
        Query query = this.em.createQuery(sql).setParameter("pedidoId", pedidoId)
        return query.getResultList()
    }

    @Override
    @ReadOnly
    List<Pagamento> obterPagamentos() {
        Query query = this.em.createQuery("SELECT p FROM Pagamento p")
        return query.getResultList()
    }
}
