package br.com.finchsolucoes.xgracco.legacy.beans.listeners;

import br.com.finchsolucoes.xgracco.domain.entity.LogAuditoria;
import org.hibernate.envers.RevisionListener;

import java.util.Date;

/**
 * Created by paulomarcon
 */
public class LogAuditoriaListener implements RevisionListener {

    @Override
    public void newRevision(Object revision) {
        if ((revision == null) || (!(revision instanceof LogAuditoria))) {
            return;
        }

        LogAuditoria rev = (LogAuditoria) revision;
        String user;
        try {
             user = "Marcos"; //Util.getUsuarioLogado().getUsuarioSistema().getLogin();
        } catch (Exception e) {
            user = "robô";
        }
        rev.setUsuario(user);
        rev.setDataAlteracao(new Date());
    }
}
