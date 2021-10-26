package br.com.finchsolucoes.xgracco.service;

import br.com.finchsolucoes.xgracco.core.handler.exception.EntityNotFoundException;
import br.com.finchsolucoes.xgracco.core.locale.MessageLocale;
import br.com.finchsolucoes.xgracco.domain.dto.output.PermissaoOutDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Permissao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPermissao;
import br.com.finchsolucoes.xgracco.domain.repository.PermissaoRepository;
import br.com.finchsolucoes.xgracco.domain.transformers.PermissaoTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static br.com.finchsolucoes.xgracco.core.constants.ValidationConstants.REGISTER_NOT_FOUND_CUSTOM;
import static br.com.finchsolucoes.xgracco.domain.entity.Permissao.GESTAO_PROCESSOS;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Serviços de permissão da aplicação.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Service
@Slf4j
public class PermissaoService {

    private final PermissaoRepository permissaoRepository;
    private final PermissaoTransformer permissaoTransformer;
    private final MessageLocale messageLocale;
    private final Environment env;

    private static final String CONSTANTE = "{table}";
    private static final String MODULOS_PREFIX = "modulos.";

    public PermissaoService(PermissaoRepository permissaoRepository, PermissaoTransformer permissaoTransformer, MessageLocale messageLocale, Environment env) {
        this.permissaoRepository = permissaoRepository;
        this.permissaoTransformer = permissaoTransformer;
        this.messageLocale = messageLocale;
        this.env = env;
    }

    public List<PermissaoOutDTO> findByPai(String path, final HttpServletRequest request, Boolean recursive){
        String codigo = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString()
                .replaceFirst(path, "")
                .replaceFirst("/", "")
                .replace("/", ":");
        if (codigo.endsWith(":")) {
            codigo = codigo.substring(0, codigo.length() - 1);
        }
        List<Permissao> permissoes = (isBlank(codigo) ?
                this.findByRoot(recursive) :
                Arrays.asList(this.findByCodigo(codigo, recursive).orElseThrow(() -> new EntityNotFoundException(messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace(CONSTANTE, "Permissao")))))
                .stream()
                .filter(p -> (!p.getTipo().equals(EnumTipoPermissao.MODULO)) || (p.getTipo().equals(EnumTipoPermissao.MODULO) && isNotBlank(getUrl(p.getCodigo()))))
                .collect(Collectors.toList());
        return this.permissaoTransformer.toPermissaoForListPermissaoOutDTO(permissoes);
    }

    public List<Permissao> findByRoot(boolean recursive) {
        if (recursive) {
            final List<Permissao> permissoes = permissaoRepository.findAll();
            return permissoes
                    .stream()
                    .filter(p -> Objects.isNull(p.getPermissaoPai()))
                    .map(p -> setPermissoesRecursive(p, permissoes))
                    .collect(Collectors.toList());
        }
        return permissaoRepository.findByRoot();
    }

    public Optional<Permissao> findByCodigo(String codigo, boolean recursive) {
        if (recursive) {
            final List<Permissao> permissoes = permissaoRepository.findAll();
            return permissoes
                    .stream()
                    .filter(p -> Objects.equals(codigo, p.getCodigo()))
                    .map(p -> setPermissoesRecursive(p, permissoes))
                    .findAny();
        }
        return permissaoRepository.findByCodigo(codigo);
    }

    public Permissao setPermissoesRecursive(final Permissao permissao, final List<Permissao> permissoes) {
        permissao.setPermissoes(permissoes
                .stream()
                .filter(p -> permissao.equals(p.getPermissaoPai()))
                .map(p -> setPermissoesRecursive(p, permissoes))
                .sorted(Comparator.comparingInt(Permissao::getOrdem))
                .collect(Collectors.toList()));
        return permissao;
    }

    public List<Permissao> setPermissoesIterative(final List<Permissao> permissoes) {
        final List<Permissao> permissoesIterative = new ArrayList<>();
        permissoes.forEach(p -> {
            permissoesIterative.add(p);
            Optional.ofNullable(p).map(Permissao::getPermissoes).ifPresent(ps -> permissoesIterative.addAll(this.setPermissoesIterative(ps)));
        });
        return permissoesIterative;
    }

    public String getUrl(String codigo) {
        return codigo.equals(GESTAO_PROCESSOS) ? "/".concat(GESTAO_PROCESSOS) : env.getProperty(MODULOS_PREFIX.concat(codigo));
    }

}
