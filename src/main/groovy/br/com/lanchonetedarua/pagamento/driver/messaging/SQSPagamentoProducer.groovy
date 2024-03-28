package br.com.lanchonetedarua.pagamento.driver.messaging

import groovy.transform.CompileStatic
import io.micronaut.jms.annotations.JMSProducer
import io.micronaut.jms.annotations.Queue
import io.micronaut.jms.sqs.configuration.SqsConfiguration
import io.micronaut.messaging.annotation.MessageBody

@CompileStatic
@JMSProducer(SqsConfiguration.CONNECTION_FACTORY_BEAN_NAME)
interface SQSPagamentoProducer {

    @Queue("retorno-pedidos")
    void sendMessage(@MessageBody PedidoPagamentoRetornoDTO pedidoPagamentoRetorno)
}
