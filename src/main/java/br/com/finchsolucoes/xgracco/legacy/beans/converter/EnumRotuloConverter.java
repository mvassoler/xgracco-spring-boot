package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumRotulo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class EnumRotuloConverter extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
        	try{
	        	Integer codigo = Integer.valueOf(text);
	            for (EnumRotulo rotulo : EnumRotulo.values()) {
	            	if(codigo.equals(rotulo.getId())){
	            		this.setValue(rotulo);
	            		break;
	            	}
				}
        	}catch (NumberFormatException ex){
        		this.setValue(null);
        	}
            
        }
    }
}
