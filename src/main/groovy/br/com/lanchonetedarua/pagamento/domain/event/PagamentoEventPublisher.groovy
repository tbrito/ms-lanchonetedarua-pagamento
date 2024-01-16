package br.com.lanchonetedarua.pagamento.domain.event

import br.com.lanchonetedarua.pagamento.domain.model.Pagamento

interface PagamentoEventPublisher {

    void notificaServiceExternoPagamento(Pagamento pagamento)

    void notificaServiceInternoPagamento(Pagamento pagamento)
}
