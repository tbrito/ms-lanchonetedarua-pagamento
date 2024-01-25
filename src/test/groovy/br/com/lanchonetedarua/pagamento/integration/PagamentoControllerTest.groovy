package br.com.lanchonetedarua.pagamento.integration

import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento.CARTAO_CREDITO
import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento.CARTAO_DEBITO
import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.FormaPagamento.PIX
import static br.com.lanchonetedarua.pagamento.domain.model.valueobject.StatusPagamento.NEGADO
import static org.hamcrest.CoreMatchers.equalTo

import br.com.lanchonetedarua.pagamento.domain.model.Pagamento
import br.com.lanchonetedarua.pagamento.domain.service.PagamentoService

import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class PagamentoControllerTest extends Specification {

    @Inject
    RequestSpecification spec

    @Inject
    PagamentoService pagamentoService

    void "deve recuperar todos os pagamentos"() {
        given:
        List<Pagamento> pagamentos = [
                new Pagamento(id: "PAY-001", pedidoId: "1", valorTotal: 10, formaPagamento: CARTAO_DEBITO),
                new Pagamento(id: "PAY-002", pedidoId: "2", valorTotal: 20, formaPagamento: CARTAO_CREDITO),
                new Pagamento(id: "PAY-003", pedidoId: "3", valorTotal: 30, formaPagamento: PIX),
        ]

        and:
        pagamentoService.obterPagamentos() >> pagamentos

        expect:
        spec
                .when()
                    .get('/pagamentos')
                .then()
                    .statusCode(200)
                    .contentType("application/json")
                    .body("size()", equalTo(3))
                    .body("every { it.id in ['PAY-001', 'PAY-002', 'PAY-003'] }", equalTo(true))
    }

    void "deve recuperar pagamento por id"() {
        given:
        Pagamento pagamento = new Pagamento(id: "PAY-001", pedidoId: "1", valorTotal: 10, formaPagamento: PIX)

        and:
        pagamentoService.obterPagamentoPorId("PAY-001") >> pagamento

        expect:
        spec
                .when()
                    .get('/pagamentos/PAY-001')
                .then()
                    .statusCode(200)
                    .contentType("application/json")
                    .body("id", equalTo("PAY-001"))
                    .body("pedidoId", equalTo("1"))
                    .body("valorTotal", equalTo(10))
    }

    void "deve recuperar pagamentos por pedido"() {
        given:
        List<Pagamento> pagamentos = [
                new Pagamento(id: "PAY-001", pedidoId: "1", valorTotal: 10, formaPagamento: CARTAO_DEBITO),
                new Pagamento(id: "PAY-002", pedidoId: "1", valorTotal: 20, formaPagamento: CARTAO_CREDITO)
        ]

        and:
        pagamentoService.obterPagamentosPorPedido("1") >> pagamentos

        expect:
        spec
                .when()
                    .get('/pagamentos/pedido/1')
                .then()
                    .statusCode(200)
                    .contentType("application/json")
                    .body("size()", equalTo(2))
                    .body("every { it.id in ['PAY-001', 'PAY-002'] }", equalTo(true))
    }

    void "deve criar um novo pagamento"() {
        given:
        def pagamento = """ 
            {
                "pedidoId"      : "1",
                "valorTotal"    : 10,
                "formaPagamento": "CARTAO_CREDITO"
            }    
        """

        when:
        def response = spec
                .given()
                    .contentType(ContentType.JSON)
                    .body(pagamento)
                .when()
                    .post('/pagamentos')

        then:
        response
                .then()
                    .statusCode(201)
                    .contentType(ContentType.JSON)
        1 * pagamentoService.criarPagamento(_) >> new Pagamento(id: "PAY-001", pedidoId: "1", valorTotal: 10, formaPagamento: CARTAO_CREDITO)
    }

    void "deve atualizar o status do pagamento"() {
        given:
        def status = """ 
            {
                "id"     : "PAY-001",
                "status" : "NEGADO"
            }    
        """

        when:
        def response = spec
                .given()
                    .contentType(ContentType.JSON)
                    .body(status)
                .when()
                    .post('/pagamentos/status-atualizacao')

        then:
        response
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
        1 * pagamentoService.atualizarStatusPagamento("PAY-001", NEGADO) >> new Pagamento(id: "PAY-001", status: NEGADO)
    }

    @MockBean(PagamentoService)
    PagamentoService pagamentoService() {
        return Mock(PagamentoService)
    }

}
