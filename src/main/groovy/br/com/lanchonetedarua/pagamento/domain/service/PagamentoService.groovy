package br.com.lanchonetedarua.pagamento.domain.service

import br.com.lanchonetedarua.pagamento.domain.event.PagamentoEventPublisher
import br.com.lanchonetedarua.pagamento.domain.model.Pagamento
import br.com.lanchonetedarua.pagamento.domain.model.valueobject.InfoPagamento
import br.com.lanchonetedarua.pagamento.domain.model.valueobject.StatusPagamento
import br.com.lanchonetedarua.pagamento.domain.repository.PagamentoRepository

import io.micronaut.transaction.annotation.ReadOnly
import jakarta.inject.Inject
import jakarta.inject.Singleton
import jakarta.transaction.Transactional

@Singleton
class PagamentoService {

    @Inject
    private PagamentoRepository pagamentoRepository

    @Inject
    private PagamentoEventPublisher pagamentoEventPublisher

    @Transactional
    Pagamento criarPagamento(InfoPagamento infoPagamento) {
        Pagamento novoPagamento = Pagamento.criarNovoPagamento(infoPagamento)
        Pagamento pagamento = this.pagamentoRepository.criarPagamento(novoPagamento)

        this.pagamentoEventPublisher.notificaServiceExternoPagamento(pagamento)

        return pagamento
    }

    @Transactional
    Pagamento atualizarStatusPagamento(String pagamentoId, StatusPagamento statusPagamento) {
        Pagamento pagamento = this.pagamentoRepository.atualizarStatusPagamento(pagamentoId, statusPagamento)

        this.pagamentoEventPublisher.notificaServiceInternoPagamento(pagamento)

        return pagamento
    }

    @ReadOnly
    Pagamento obterPagamentoPorId(String id) {
        return this.pagamentoRepository.obterPagamentoPorId(id)
    }

    @ReadOnly
    List<Pagamento> obterPagamentosPorPedido(String pedidoId) {
        return this.pagamentoRepository.obterPagamentosPorPedido(pedidoId)
    }

    @ReadOnly
    List<Pagamento> obterPagamentos() {
        return this.pagamentoRepository.obterPagamentos()
    }
}
