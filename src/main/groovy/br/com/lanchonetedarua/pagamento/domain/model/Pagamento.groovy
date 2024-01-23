package br.com.lanchonetedarua.pagamento.domain.model

import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento.BOLETO
import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento.CARTAO_CREDITO
import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento.CARTAO_DEBITO
import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento.PIX

import br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento
import br.com.lanchonetedarua.pagamento.domain.model.valueobject.InfoPagamento
import br.com.lanchonetedarua.pagamento.domain.model.valueobject.StatusPagamento

import groovy.transform.CompileStatic
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.Table

@CompileStatic
@Serdeable
@Entity
@Table(name = "pagamento")
@Inheritance
class Pagamento {

    @Id
    String id
    String pedidoId
    BigDecimal valorTotal
    FormaPagamento formaPagamento
    StatusPagamento status

    static Pagamento criarNovoPagamento(InfoPagamento infoPagamento) {
        Pagamento pagamento = switch (infoPagamento.formaPagamento) {
            case CARTAO_CREDITO -> new PagamentoCartaoCredito(
                    numeroCartao: infoPagamento.numeroCartao,
                    dataExpiracao: infoPagamento.dataExpiracao,
                    cvv: infoPagamento.cvv
            )
            case CARTAO_DEBITO -> new PagamentoCartaoDebito()
            case BOLETO -> new PagamentoBoleto()
            case PIX -> new PagamentoPix()
        }

        pagamento.id = "PAY-" + UUID.randomUUID().toString()
        pagamento.pedidoId = infoPagamento.pedidoId
        pagamento.valorTotal = infoPagamento.valorTotal
        pagamento.formaPagamento = infoPagamento.formaPagamento
        pagamento.status = StatusPagamento.PENDENTE

        return pagamento
    }
}
