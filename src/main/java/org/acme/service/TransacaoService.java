package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.domain.entity.Conta;
import org.acme.domain.entity.Transacao;
import org.acme.domain.enums.TipoTransacao;
import java.time.LocalDateTime;
import jakarta.inject.Inject; // Adicione este import
import org.eclipse.microprofile.jwt.JsonWebToken; // Adicione este import


@ApplicationScoped
public class TransacaoService {

    @Inject
    JsonWebToken jwt; // <-- Peça 1: O sistema agora sabe quem está logado

    @Transactional
    public Transacao realizarTransacao(Long contaOrigemId, Double valor, TipoTransacao tipo, String bancoDestino, String contaDestino) {

        Conta origem = Conta.findById(contaOrigemId);
        if (origem == null) throw new RuntimeException("Conta origem não encontrada.");

        // --- Peça 2: A Trava de Segurança (Regra do Dono da Conta) ---
        if (jwt.getGroups().contains("CLIENTE")) {
            String emailLogado = jwt.getName(); // Pega o email do Token
            if (!origem.cliente.email.equals(emailLogado)) {
                throw new RuntimeException("Segurança: Você só pode realizar débitos em sua própria conta.");
            }
        }
        // ------------------------------------------------------------

        Double tarifa = 0.0;
        // ... restante do seu código de TED/TEV continua igual abaixo ...

        if (tipo == TipoTransacao.TED) {
            tarifa = 10.00;
        } else if (tipo == TipoTransacao.TEV) {
            if (!"104".equals(bancoDestino)) {
                throw new RuntimeException("TEV só é permitido entre contas da CAIXA (Banco 104).");
            }
        tarifa = 0.0;
        } else if (tipo == TipoTransacao.PIX) {
            tarifa = 0.0;
            
        }

        if (origem.saldo < (valor + tarifa)) {
            throw new RuntimeException("Saldo insuficiente (Valor + Tarifa: R$ " + (valor + tarifa) + ")");
        }

        origem.saldo -= (valor + tarifa);
        origem.persist();

        if (tipo == TipoTransacao.TEV) {
            Conta destino = Conta.find("numero", contaDestino).firstResult();
            if (destino != null) {
                destino.saldo += valor;
                destino.persist();
            } else {
                throw new RuntimeException("Conta destino não encontrada no sistema.");
            }
        }

        Transacao t = new Transacao();
        t.valor = valor;
        t.tarifa = tarifa;
        t.tipo = tipo;
        t.bancoDestino = bancoDestino;
        t.contaDestino = contaDestino;
        t.dataHora = LocalDateTime.now();
        t.conta = origem;
        t.persist();

        return t;

    }

    @Transactional
    public void realizarDeposito(Long contaId, Double valor) {
        Conta conta = Conta.findById(contaId);

        // Regra: Valida se a conta existe e se NÃO é DIGITAL
        validarOperacaoManual(conta);

        conta.saldo += valor;
        conta.persist();
    }

    @Transactional
    public void realizarSaque(Long contaId, Double valor) {
        Conta conta = Conta.findById(contaId);

        // Regra: Valida se a conta existe e se NÃO é DIGITAL
        validarOperacaoManual(conta);

        if (conta.saldo < valor) {
            throw new RuntimeException("Saldo insuficiente para o saque solicitado.");
        }

        conta.saldo -= valor;
        conta.persist();
    }

    // Método auxiliar para evitar repetição de código
    private void validarOperacaoManual(Conta conta) {
        if (conta == null) {
            throw new RuntimeException("Conta não encontrada.");
        }

        // Se o tipo for DIGITAL, bloqueia Saque e Depósito manual
        if (org.acme.domain.enums.TipoConta.DIGITAL.equals(conta.tipo)) {
            throw new RuntimeException("Operação não permitida: contas do tipo DIGITAL só aceitam transferências (PIX/TED/TEV).");
        }
    }

}