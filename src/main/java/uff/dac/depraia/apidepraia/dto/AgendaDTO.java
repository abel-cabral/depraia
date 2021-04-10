package uff.dac.depraia.apidepraia.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import lombok.Getter;
import uff.dac.depraia.apidepraia.model.Agenda;
import uff.dac.depraia.apidepraia.model.Praia;

@Getter
public class AgendaDTO {  
    private PraiaIdDTO praia;
    private String data;
    private Integer vagas;
    
    public Agenda conversor(Praia p) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return new Agenda(p, sdf.parse(data), vagas);
    }
}
