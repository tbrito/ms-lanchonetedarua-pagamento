package br.com.lanchonetedarua.pagamento.driver.web

import br.com.lanchonetedarua.pagamento.domain.model.Pagamento
import br.com.lanchonetedarua.pagamento.domain.model.valueobject.InfoPagamento
import br.com.lanchonetedarua.pagamento.domain.service.PagamentoService
import br.com.lanchonetedarua.pagamento.driver.web.dtos.PagamentoConsultaDTO
import br.com.lanchonetedarua.pagamento.driver.web.dtos.PagamentoStatusDTO

import groovy.transform.CompileStatic
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.swagger.v3.oas.annotations.Operation
import jakarta.inject.Inject

@CompileStatic
@Controller("/pagamentos")
class PagamentoController {

    @Inject
    private PagamentoService pagamentoService

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Cria um novo pagamento", description = "Cria um novo pagamento para o pedido")
    HttpResponse<PagamentoConsultaDTO> criarPagamento(@Body InfoPagamento infoPagamento) {
        Pagamento pagamento = this.pagamentoService.criarPagamento(infoPagamento)
        HttpResponse.created(toPagamentoConsulta(pagamento))
        return HttpResponse.created(toPagamentoConsulta(pagamento))
    }

    @Post("/status-atualizacao")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Webook de atualização de status de pagamento",
            description = "Webhook para sistemas como MercadoPago atualize o status do pagamento"
    )
    PagamentoConsultaDTO atualizarStatusPagamento(@Body PagamentoStatusDTO pagamentoStatus) {
        Pagamento pagamento = this.pagamentoService.atualizarStatusPagamento(pagamentoStatus.id, pagamentoStatus.status)
        return toPagamentoConsulta(pagamento)
    }

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtém pagamento por ID", description = "Retorna um pagamento com base em um ID fornecido")
    PagamentoConsultaDTO obterPagamentoPorId(String id) {
        Pagamento pagamento = this.pagamentoService.obterPagamentoPorId(id)
        return toPagamentoConsulta(pagamento)
    }

    @Get("/pedido/{pedidoId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtém pagamento por pedidoId", description = "Retorna um pagamento com base em um ID de pedido fornecido")
    List<PagamentoConsultaDTO> obterPagamentosPorPedido(String pedidoId) {
        List<Pagamento> pagamentos = this.pagamentoService.obterPagamentosPorPedido(pedidoId)
        return pagamentos.collect { toPagamentoConsulta(it) }
    }

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    List<PagamentoConsultaDTO> obterPagamentos() {
        List<Pagamento> pagamentos = this.pagamentoService.obterPagamentos()
        return pagamentos.collect { toPagamentoConsulta(it) }
    }

    private static PagamentoConsultaDTO toPagamentoConsulta(Pagamento pagamento) {
        new PagamentoConsultaDTO(
                id: pagamento.id,
                pedidoId: pagamento.pedidoId,
                valorTotal: pagamento.valorTotal,
                formaPagamento: pagamento.formaPagamento,
                status: pagamento.status
        )
    }
}
