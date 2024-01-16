package br.com.lanchonetedarua.pagamento.driver.event

import br.com.lanchonetedarua.pagamento.domain.event.PagamentoEventPublisher
import br.com.lanchonetedarua.pagamento.domain.model.Pagamento

import jakarta.inject.Singleton

@Singleton
class PagamentoEventPublisherImpl implements PagamentoEventPublisher {

    @Override
    void notificaServiceExternoPagamento(Pagamento pagamento) {
        // TODO poderia chamar serviço externo de pagamento, como MercadoPago por exemplo
    }

    @Override
    void notificaServiceInternoPagamento(Pagamento pagamento) {
        // TODO poderia notificar todos os serviços internos interessados no pagamento
    }
}
