package br.com.lanchonetedarua.pagamento.domain.service

import br.com.lanchonetedarua.pagamento.domain.model.Pagamento
import br.com.lanchonetedarua.pagamento.domain.model.PagamentoCartaoCredito
import br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento
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

    @Transactional
    Pagamento criarPagamento(InfoPagamento infoPagamento) {
        Pagamento novoPagamento = criarNovoPagamento(infoPagamento)
        Pagamento pagamento = this.pagamentoRepository.criarPagamento(novoPagamento)

        // TODO poderia chamar ws externo de pagamento, como MercadoPago por exemplo

        return pagamento
    }

    @Transactional
    Pagamento atualizarStatusPagamento(String pagamentoId, StatusPagamento statusPagamento) {
        Pagamento pagamento = this.pagamentoRepository.atualizarStatusPagamento(pagamentoId, statusPagamento)

        // TODO chamar microservico pedido para atualizar status pagamento

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

    private static Pagamento criarNovoPagamento(InfoPagamento infoPagamento) {
        Pagamento pagamento

        if (infoPagamento.formaPagamento == FormaPagamento.CARTAO_CREDITO) {
            pagamento = new PagamentoCartaoCredito(
                    numeroCartao: infoPagamento.numeroCartao,
                    dataExpiracao: infoPagamento.dataExpiracao,
                    cvv: infoPagamento.cvv
            )
        } else {
            pagamento = new Pagamento()
        }

        pagamento.id = "PAY-" + UUID.randomUUID().toString()
        pagamento.pedidoId = infoPagamento.pedidoId
        pagamento.valorTotal = infoPagamento.valorTotal
        pagamento.formaPagamento = infoPagamento.formaPagamento
        pagamento.status = StatusPagamento.PENDENTE

        return pagamento

    }
}
