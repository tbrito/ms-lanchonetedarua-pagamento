package br.com.lanchonetedarua.pagamento.driver.messaging

import br.com.lanchonetedarua.pagamento.domain.event.PagamentoEventPublisher
import br.com.lanchonetedarua.pagamento.domain.model.Pagamento

import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class PagamentoEventPublisherImpl implements PagamentoEventPublisher {

    @Inject
    private SQSPagamentoProducer producer

    @Override
    void notificaServiceExternoPagamento(Pagamento pagamento) {
        // TODO poderia chamar serviço externo de pagamento, como MercadoPago por exemplo
    }

    @Override
    void notificaServiceInternoPagamento(Pagamento pagamento) {
        this.producer.sendMessage(new PedidoPagamentoRetornoDTO(
                pedidoId: pagamento.pedidoId,
                statusPagamento: pagamento.status
        ))
    }
}
