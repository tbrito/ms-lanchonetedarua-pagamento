package br.com.lanchonetedarua.pagamento.unit

import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento.BOLETO
import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento.CARTAO_CREDITO
import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento.CARTAO_DEBITO
import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento.PIX

import br.com.lanchonetedarua.pagamento.domain.model.Pagamento
import br.com.lanchonetedarua.pagamento.domain.model.PagamentoBoleto
import br.com.lanchonetedarua.pagamento.domain.model.PagamentoCartaoCredito
import br.com.lanchonetedarua.pagamento.domain.model.PagamentoCartaoDebito
import br.com.lanchonetedarua.pagamento.domain.model.PagamentoPix
import br.com.lanchonetedarua.pagamento.domain.model.valueobject.InfoPagamento
import br.com.lanchonetedarua.pagamento.domain.model.valueobject.StatusPagamento

import spock.lang.Specification
import spock.lang.Unroll

class PagamentoTest extends Specification {

    @Unroll
    def "deve criar um pagamento do tipo #formaPagamento"() {
        given:
        def infoPagamento = new InfoPagamento(pedidoId: 1, valorTotal: 10, formaPagamento: formaPagamento)

        when:
        Pagamento pagamento = Pagamento.criarNovoPagamento(infoPagamento)

        then:
        clazzExpected.isCase(pagamento)

        where:
        clazzExpected          | formaPagamento
        PagamentoCartaoCredito | CARTAO_CREDITO
        PagamentoCartaoDebito  | CARTAO_DEBITO
        PagamentoBoleto        | BOLETO
        PagamentoPix           | PIX
    }

    def "deve criar um pagamento do tipo cartao de credito"() {
        given:
        def infoPagamento = new InfoPagamento(
                pedidoId: "ABC123",
                valorTotal: 10,
                formaPagamento: CARTAO_CREDITO,
                numeroCartao: "1234 1234 1234",
                dataExpiracao: "05/26",
                cvv: "123"
        )

        when:
        Pagamento pagamento = Pagamento.criarNovoPagamento(infoPagamento)

        then:
        pagamento instanceof PagamentoCartaoCredito
        Pagamento pagamentoCartaoCredito = (PagamentoCartaoCredito) pagamento
        with(pagamentoCartaoCredito) {
            pedidoId == infoPagamento.pedidoId
            valorTotal == infoPagamento.valorTotal
            formaPagamento == infoPagamento.formaPagamento
            numeroCartao == infoPagamento.numeroCartao
            dataExpiracao == infoPagamento.dataExpiracao
            cvv == infoPagamento.cvv
            status == StatusPagamento.PENDENTE
        }
    }
}
