package com.K1dou.Loja.virtual;

import com.K1dou.Loja.virtual.util.ValidaCNPJ;
import com.K1dou.Loja.virtual.util.ValidarCPF;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TesteCPFCNPJ {

    public static void main(String[] args) {
        boolean isCNPJ = ValidaCNPJ.isCNPJ("94.747.024/0001-52");
        System.out.println("Cnpj valido: "+isCNPJ);


        boolean isCPF = ValidarCPF.isCPF("453.702.240-05");
        System.out.println("Cpf valido: "+isCPF);






    }

}
