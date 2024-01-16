package br.com.lanchonetedarua.pagamento.domain.repository

import br.com.lanchonetedarua.pagamento.domain.model.Pagamento
import br.com.lanchonetedarua.pagamento.domain.model.valueobject.StatusPagamento

interface PagamentoRepository {

    Pagamento criarPagamento(Pagamento pagamento)

    Pagamento obterPagamentoPorId(String id)

    List<Pagamento> obterPagamentosPorPedido(String pedidoId)

    Pagamento atualizarStatusPagamento(String pagamentoId, StatusPagamento statusPagamento)

    List<Pagamento> obterPagamentos()
}
