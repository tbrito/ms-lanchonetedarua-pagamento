package br.com.lanchonetedarua.pagamento.driver.messaging

import br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento
import br.com.lanchonetedarua.pagamento.domain.model.valueobject.InfoPagamento
import br.com.lanchonetedarua.pagamento.domain.service.PagamentoService

import groovy.util.logging.Log
import io.micronaut.jms.annotations.JMSListener
import io.micronaut.jms.annotations.Queue
import io.micronaut.jms.sqs.configuration.SqsConfiguration
import io.micronaut.messaging.annotation.MessageBody
import jakarta.inject.Inject

@Log
@JMSListener(SqsConfiguration.CONNECTION_FACTORY_BEAN_NAME)
class SQSPagamentoConsumer {

    @Inject
    private PagamentoService pagamentoService

    @Queue("pedido-para-pagamento")
    void receiveMessage(@MessageBody PedidoDTO pedido) {
        log.info("Info pedido pagamento recebido fila SQS: ${pedido}")
        this.pagamentoService.criarPagamento(toInfoPagamento(pedido))
    }

    private static InfoPagamento toInfoPagamento(PedidoDTO pedidoDTO) {
        return new InfoPagamento(
                pedidoId: pedidoDTO.id,
                valorTotal: pedidoDTO.total,
                formaPagamento: FormaPagamento.CARTAO_CREDITO,
                numeroCartao: pedidoDTO.informacaoDePagamento.numeroDoCartao,
                dataExpiracao: pedidoDTO.informacaoDePagamento.dataExpiracao,
                cvv: pedidoDTO.informacaoDePagamento.cvv
        )
    }
}
