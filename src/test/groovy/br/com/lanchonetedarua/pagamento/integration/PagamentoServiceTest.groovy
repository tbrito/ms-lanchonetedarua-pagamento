package br.com.lanchonetedarua.pagamento.integration

import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento.BOLETO
import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento.CARTAO_CREDITO
import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento.CARTAO_DEBITO
import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento.PIX
import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.StatusPagamento.NEGADO
import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.StatusPagamento.PENDENTE

import br.com.lanchonetedarua.pagamento.domain.model.Pagamento
import br.com.lanchonetedarua.pagamento.domain.model.PagamentoCartaoCredito
import br.com.lanchonetedarua.pagamento.domain.model.PagamentoPix
import br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento
import br.com.lanchonetedarua.pagamento.domain.model.valueobject.InfoPagamento
import br.com.lanchonetedarua.pagamento.domain.service.PagamentoService

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Ignore
import spock.lang.Specification

@MicronautTest
@Ignore
class PagamentoServiceTest extends Specification {

    @Inject
    PagamentoService pagamentoService

    void "deve salvar um novo pagamento"() {
        given:
        InfoPagamento infoPagamento = criarInfoPagamentoBase(CARTAO_CREDITO)
        infoPagamento.numeroCartao = "1234 1234 1234"
        infoPagamento.dataExpiracao = "05/26"
        infoPagamento.cvv = "123"

        when:
        Pagamento pagamento = this.pagamentoService.criarPagamento(infoPagamento)

        then:
        Pagamento pagamentoSalvo = this.pagamentoService.obterPagamentoPorId(pagamento.id)
        pagamentoSalvo != null
        pagamentoSalvo instanceof PagamentoCartaoCredito
        pagamentoSalvo.status == PENDENTE
    }

    void "deve atualizar status de pagamento"() {
        given:
        InfoPagamento infoPagamento = criarInfoPagamentoBase(PIX)

        and:
        Pagamento pagamento = this.pagamentoService.criarPagamento(infoPagamento)

        when:
        this.pagamentoService.atualizarStatusPagamento(pagamento.id, NEGADO)

        then:
        Pagamento pagamentoSalvo = this.pagamentoService.obterPagamentoPorId(pagamento.id)
        pagamentoSalvo != null
        pagamentoSalvo instanceof PagamentoPix
        pagamentoSalvo.status == NEGADO
    }

    void "deve obter pagamentos por pedido id"() {
        given:
        String pedidoId = "ABC123"

        and:
        InfoPagamento infoPagamentoPix = criarInfoPagamentoBase(pedidoId, PIX)
        InfoPagamento infoPagamentoBoleto = criarInfoPagamentoBase(pedidoId, BOLETO)
        InfoPagamento infoPagamentoDebito = criarInfoPagamentoBase(pedidoId, CARTAO_DEBITO)

        and:
        this.pagamentoService.criarPagamento(infoPagamentoPix)
        this.pagamentoService.criarPagamento(infoPagamentoBoleto)
        this.pagamentoService.criarPagamento(infoPagamentoDebito)

        when:
        List<Pagamento> pagamentos = this.pagamentoService.obterPagamentosPorPedido(pedidoId)

        then:
        pagamentos.size() == 3
        List<FormaPagamento> formasPagemento = pagamentos*.formaPagamento
        formasPagemento.containsAll([PIX, BOLETO, CARTAO_DEBITO])
    }

    void "deve obter todos os pagamentos"() {
        given:
        InfoPagamento infoPagamentoPix = criarInfoPagamentoBase("1", PIX)
        InfoPagamento infoPagamentoBoleto = criarInfoPagamentoBase("2", BOLETO)
        InfoPagamento infoPagamentoDebito = criarInfoPagamentoBase("3", CARTAO_DEBITO)
        InfoPagamento infoPagamentoCredito = criarInfoPagamentoBase("4", CARTAO_CREDITO)

        and:
        this.pagamentoService.criarPagamento(infoPagamentoPix)
        this.pagamentoService.criarPagamento(infoPagamentoBoleto)
        this.pagamentoService.criarPagamento(infoPagamentoDebito)
        this.pagamentoService.criarPagamento(infoPagamentoCredito)

        when:
        List<Pagamento> pagamentos = this.pagamentoService.obterPagamentos()

        then:
        pagamentos.size() == 4
        List<FormaPagamento> formasPagamento = pagamentos*.formaPagamento
        formasPagamento.containsAll([PIX, BOLETO, CARTAO_DEBITO, CARTAO_CREDITO])
    }

    private static InfoPagamento criarInfoPagamentoBase(FormaPagamento formaPagamento) {
        return criarInfoPagamentoBase("1", formaPagamento)
    }

    private static InfoPagamento criarInfoPagamentoBase(String pedidoId, FormaPagamento formaPagamento) {
        return new InfoPagamento(
                pedidoId: pedidoId,
                valorTotal: 10,
                formaPagamento: formaPagamento
        )
    }

}
